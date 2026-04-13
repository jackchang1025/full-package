package com.guard.wallet.receiver;

import com.guard.wallet.core.AppUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.utils.SystemHelper;
import com.guard.wallet.utils.SharedPrefsManager;

/**
 * vendor NetWorkReceiver — detects network connectivity changes,
 * triggers strategy event and refreshes network state.
 */
public class NetWorkReceiver extends BroadcastReceiver {
    public Integer a = 0;

    @Override
    public final void onReceive(Context var1, Intent var2) {
        try {
            this.a = 1;

            if (var2 != null) {
                if (!AppUtils.B(var2.getAction())) {
                    Log.d("NetWorkReceiver", var2.getAction());
                }
            }

            if (SystemHelper.z(var1) != null) {
                MainApplication.getInstance().offerStrategyEvent("LOCAL_WIFI_NETWORK_PREPARED");
            }

            SharedPrefsManager.F();
        } catch (Exception var7) {
            AppUtils.s("NetWorkReceiver", var7);
        }
    }
}
