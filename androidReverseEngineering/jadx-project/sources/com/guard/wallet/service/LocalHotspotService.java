package com.guard.wallet.service;

import a0.C0005e;
import android.app.Service;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.content.ContextCompat;

/* loaded from: classes.dex */
public class LocalHotspotService extends Service {

    /* renamed from: b */
    public static final /* synthetic */ int f318b = 0;

    /* renamed from: a */
    public WifiManager.LocalOnlyHotspotReservation f319a;

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        WifiManager.LocalOnlyHotspotReservation localOnlyHotspotReservation = this.f319a;
        if (localOnlyHotspotReservation != null) {
            localOnlyHotspotReservation.close();
        }
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i2, int i3) {
        WifiManager wifiManager = (WifiManager) getSystemService("wifi");
        if (wifiManager == null || ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0 || ContextCompat.checkSelfPermission(this, "android.permission.NEARBY_WIFI_DEVICES") != 0) {
            return 1;
        }
        wifiManager.startLocalOnlyHotspot(new C0005e(this), new Handler(Looper.getMainLooper()));
        return 1;
    }
}
