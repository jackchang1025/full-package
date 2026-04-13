package com.guard.wallet.receiver;

import com.guard.wallet.core.AppUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.guard.wallet.utils.DeviceUtils;
import com.guard.wallet.utils.SharedPrefsManager;

/**
 * vendor LocaleChangeReceiver — detects locale changes and persists the new language code.
 */
public class LocaleChangeReceiver extends BroadcastReceiver {
    @Override
    public final void onReceive(Context var1, Intent var2) {
        if (var2 != null) {
            try {
                if (AppUtils.B(var2.getAction()) || !"android.intent.action.LOCALE_CHANGED".equals(var2.getAction())) {
                    return;
                }

                if (var1 == null) {
                    return;
                }

                String langCode = DeviceUtils.getLanguageTag(var1);
                if (!AppUtils.B(langCode)) {
                    SharedPrefsManager.E(langCode);
                }
            } catch (Exception var5) {
                AppUtils.s("com.guard.wallet.receiver.LocaleChangeReceiver", var5);
            }
        }
    }
}
