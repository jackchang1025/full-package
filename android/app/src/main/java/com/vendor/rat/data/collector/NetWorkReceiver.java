package com.vendor.rat.data.collector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * ADAPT: vendor com.guard.wallet.receiver.NetWorkReceiver
 * Monitors network connectivity changes.
 */
public class NetWorkReceiver extends BroadcastReceiver {

    private static final String TAG = "NetWorkReceiver";

    // ADAPT: vendor field f196a (Integer, init 0) — receiver alive flag
    public Integer receiverAlive = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            this.receiverAlive = 1;
            if (intent != null) {
                String action = intent.getAction();
                if (action != null && !action.isEmpty()) {
                    Log.d(TAG, action);
                }
            }
            // ADAPT: vendor checks g.z(context) for wifi
            // then calls MainApplication.getInstance().offerStrategyEvent("LOCAL_WIFI_NETWORK_PREPARED")
            // then calls h.F() to update network state
            // TODO: VENDOR_VERIFY — integrate with MainApplication strategy events
        } catch (Exception e) {
            Log.e(TAG, "Error in onReceive", e);
        }
    }
}
