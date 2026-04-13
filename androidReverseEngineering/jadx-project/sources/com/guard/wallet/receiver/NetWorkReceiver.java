package com.guard.wallet.receiver;

import a1.AbstractC0026q;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;

/* loaded from: classes.dex */
public class NetWorkReceiver extends BroadcastReceiver {

    /* renamed from: a */
    public Integer f280a = 0;

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        try {
            this.f280a = 1;
            if (intent != null && !AbstractC0026q.m151B(intent.getAction())) {
                Log.d("NetWorkReceiver", intent.getAction());
            }
            if (AbstractC0251g.m679z(context) != null) {
                MainApplication.getInstance().offerStrategyEvent("LOCAL_WIFI_NETWORK_PREPARED");
            }
            AbstractC0252h.m685F();
        } catch (Exception e2) {
            AbstractC0026q.m186s("NetWorkReceiver", e2);
        }
    }
}
