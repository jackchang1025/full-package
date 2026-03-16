package com.vendor.rat.keepalive.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

import com.vendor.rat.keepalive.KeepAliveManager;

/**
 * 电池状态接收器 (模块 07)
 */
public class BatteryLevelReceiver extends BroadcastReceiver {

    private static final String TAG = "BatteryReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            float batteryPct = level * 100f / scale;
            boolean isCharging = (status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL);

            if (batteryPct < 15 && !isCharging) {
                KeepAliveManager.getInstance().enterLowPowerMode();
            } else {
                KeepAliveManager.getInstance().exitLowPowerMode();
            }
        }

        if (Intent.ACTION_POWER_CONNECTED.equals(intent.getAction())
                || Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction())) {
            KeepAliveManager.getInstance().ensureServicesRunning(context);
        }
    }
}
