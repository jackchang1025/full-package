package com.vendor.rat.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 设备管理员接收器 (模块 02)
 */
public class AppDeviceAdminReceiver extends android.app.admin.DeviceAdminReceiver {

    private static final String TAG = "DeviceAdminReceiver";

    @Override
    public void onEnabled(Context context, Intent intent) {
        Log.d(TAG, "Device admin enabled");
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        Log.d(TAG, "Device admin disabled");
    }
}
