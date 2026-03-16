package com.vendor.rat.keepalive.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * WiFi 后台服务 (模块 07)
 *
 * 保持网络连接活跃
 */
public class WIFIBackgroundService extends Service {

    private static final String TAG = "WIFIBgService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "WiFi background service created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
