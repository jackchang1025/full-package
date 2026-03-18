package com.vendor.rat.data.collector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * ADAPT: vendor com.guard.wallet.receiver.ShutDownBroadcastReceiver
 * Handles ACTION_SHUTDOWN and QUICKBOOT_POWEROFF.
 */
public class ShutDownBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "ShutDownBroadcastReceiver";

    // ADAPT: vendor field f200a (Integer, init 0)
    public Integer receiverAlive = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            this.receiverAlive = 1;
            if (intent == null) return;
            String action = intent.getAction();
            if (action == null || action.isEmpty()) return;

            if ("android.intent.action.ACTION_SHUTDOWN".equals(action)) {
                Log.d(TAG, "手机关机了 ACTION_SHUTDOWN");
            } else if ("android.intent.action.QUICKBOOT_POWEROFF".equals(action)) {
                Log.d(TAG, "手机关机了 QUICKBOOT_POWEROFF");
            }

            // ADAPT: vendor sends MessageRecordVO with empty MessageBodyVO
            // via MainApplication.getHandlerMsgAndTimer().b()
            Log.d(TAG, "Shutdown event: " + action);
            // TODO: VENDOR_VERIFY — send shutdown event to server
        } catch (Exception e) {
            Log.e(TAG, "Error in onReceive", e);
        }
    }
}
