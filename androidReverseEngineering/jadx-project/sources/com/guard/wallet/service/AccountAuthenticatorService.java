package com.guard.wallet.service;

import a0.C0009i;
import android.app.Service;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;
import com.guard.wallet.http.AbstractC0207l;
import p019w.AbstractC0957b;

/* loaded from: classes.dex */
public class AccountAuthenticatorService extends Service {

    /* renamed from: c */
    public static final Object f312c = new Object();

    /* renamed from: a */
    public WifiManager.WifiLock f313a;

    /* renamed from: b */
    public C0009i f314b;

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.f314b.getIBinder();
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        synchronized (f312c) {
            if (this.f314b == null) {
                this.f314b = new C0009i(this);
            }
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService("wifi");
            if (wifiManager != null) {
                this.f313a = wifiManager.createWifiLock(3, "MyWifiLockTag");
            }
        }
    }

    @Override // android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        synchronized (f312c) {
            WifiManager.WifiLock wifiLock = this.f313a;
            if (wifiLock != null && wifiLock.isHeld()) {
                this.f313a.release();
                this.f313a = null;
            }
        }
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i2, int i3) {
        synchronized (f312c) {
            Log.d("com.guard.wallet.service.AccountAuthenticatorService", "AccountAuthenticatorService onStartCommand rebootApp");
            AbstractC0207l.m427j();
            AbstractC0957b.m1444a();
            WifiManager.WifiLock wifiLock = this.f313a;
            if (wifiLock != null && !wifiLock.isHeld()) {
                this.f313a.acquire();
            }
        }
        return super.onStartCommand(intent, i2, i3);
    }
}
