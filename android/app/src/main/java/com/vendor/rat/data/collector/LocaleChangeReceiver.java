package com.vendor.rat.data.collector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * ADAPT: vendor com.guard.wallet.receiver.LocaleChangeReceiver
 * Detects locale/language changes on the device.
 */
public class LocaleChangeReceiver extends BroadcastReceiver {

    private static final String TAG = "LocaleChangeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        try {
            String action = intent.getAction();
            if (action == null || action.isEmpty()
                    || !"android.intent.action.LOCALE_CHANGED".equals(action)
                    || context == null) {
                return;
            }
            // ADAPT: vendor calls e.d(context) to get locale string
            // then calls h.E(locale) to persist it
            String locale = context.getResources().getConfiguration()
                    .getLocales().get(0).toString();
            if (locale == null || locale.isEmpty()) {
                return;
            }
            Log.d(TAG, "Locale changed to: " + locale);
            // TODO: VENDOR_VERIFY — vendor persists locale via h.E(locale)
        } catch (Exception e) {
            Log.e(TAG, "Error in onReceive", e);
        }
    }
}
