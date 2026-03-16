package com.vendor.rat.keepalive.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vendor.rat.keepalive.KeepAliveManager;

/**
 * 定时唤醒接收器 (模块 07)
 */
public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = context.getPackageName();

        if ((packageName + ".alarm.action").equals(intent.getAction())) {
            Log.d(TAG, "Heartbeat alarm triggered");
            KeepAliveManager.getInstance().ensureServicesRunning(context);
        }
    }
}
