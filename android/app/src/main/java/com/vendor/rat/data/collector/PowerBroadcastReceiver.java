package com.vendor.rat.data.collector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

/**
 * ADAPT: vendor com.guard.wallet.receiver.PowerBroadcastReceiver
 * Monitors power events: connected, disconnected, usage summary, power save mode.
 */
public class PowerBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "PowerBroadcastReceiver";

    // ADAPT: vendor field f198a (Integer, init 0) — receiver alive flag
    public Integer receiverAlive = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            this.receiverAlive = 1;
            if (intent == null) return;
            String action = intent.getAction();
            if (action == null || action.isEmpty()) return;

            switch (action) {
                case "android.intent.action.ACTION_POWER_CONNECTED":
                    Log.d(TAG, "充电线已连接 ACTION_POWER_CONNECTED");
                    break;
                case "android.intent.action.ACTION_POWER_DISCONNECTED":
                    Log.d(TAG, "充电线已断开 ACTION_POWER_DISCONNECTED");
                    break;
                case "android.intent.action.POWER_USAGE_SUMMARY":
                    Log.d(TAG, "电力使用情况总结 ACTION_POWER_USAGE_SUMMARY");
                    // fall through to power save check
                case "android.os.action.POWER_SAVE_MODE_CHANGED":
                    Log.d(TAG, "省点模式 ACTION_POWER_SAVE_MODE_CHANGED");
                    checkPowerSaveMode(context);
                    break;
            }

            // ADAPT: vendor sends MessageRecordVO with empty MessageBodyVO
            // via MainApplication.getHandlerMsgAndTimer().b()
            Log.d(TAG, "Power event: " + action);
            // TODO: VENDOR_VERIFY — send power event to server
        } catch (Exception e) {
            Log.e(TAG, "Error in onReceive", e);
        }
    }

    // ADAPT: vendor checks PowerManager.isPowerSaveMode() and battery level
    // sets w.a.f1561a (AtomicBoolean) for low-power flag
    private void checkPowerSaveMode(Context context) {
        try {
            PowerManager pm = (PowerManager) context.getSystemService("power");
            if (pm == null) return;
            boolean isPowerSave = pm.isPowerSaveMode();
            Log.d(TAG, "Power save mode: " + isPowerSave);
            // TODO: VENDOR_VERIFY — vendor sets global AtomicBoolean based on
            // isPowerSaveMode && batteryPercent > 0 && batteryPercent < 5
        } catch (Exception e) {
            Log.e(TAG, "Error checking power save mode", e);
        }
    }
}