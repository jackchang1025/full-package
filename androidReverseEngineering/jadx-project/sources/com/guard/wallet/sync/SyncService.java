package com.guard.wallet.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import c0.C0097a;

/* loaded from: classes.dex */
public class SyncService extends Service {

    /* renamed from: a */
    public static C0097a f334a;

    /* renamed from: b */
    public static final Object f335b = new Object();

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return f334a.getSyncAdapterBinder();
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        synchronized (f335b) {
            f334a = new C0097a(getApplicationContext());
        }
    }
}
