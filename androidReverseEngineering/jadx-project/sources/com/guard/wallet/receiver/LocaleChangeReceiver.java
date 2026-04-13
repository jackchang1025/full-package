package com.guard.wallet.receiver;

import a1.AbstractC0026q;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0252h;

/* loaded from: classes.dex */
public class LocaleChangeReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent != null) {
            try {
                if (AbstractC0026q.m151B(intent.getAction()) || !"android.intent.action.LOCALE_CHANGED".equals(intent.getAction()) || context == null) {
                    return;
                }
                String m615d = AbstractC0249e.m615d(context);
                if (AbstractC0026q.m151B(m615d)) {
                    return;
                }
                AbstractC0252h.m684E(m615d);
            } catch (Exception e2) {
                AbstractC0026q.m186s("com.guard.wallet.receiver.LocaleChangeReceiver", e2);
            }
        }
    }
}
