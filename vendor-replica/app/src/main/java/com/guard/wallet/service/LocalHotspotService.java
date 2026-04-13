package com.guard.wallet.service;

import com.guard.wallet.infra.LocalHotspotCallback;
import android.app.Service;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.LocalOnlyHotspotReservation;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import androidx.core.content.ContextCompat;

/**
 * LocalHotspotService — starts a local-only hotspot for P2P communication.
 * Uses WifiManager.startLocalOnlyHotspot with LocalHotspotCallback.
 */
public class LocalHotspotService extends Service {
    public static final int b = 0;
    public LocalOnlyHotspotReservation a;

    @Override
    public final IBinder onBind(Intent var1) {
        return null;
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
        LocalOnlyHotspotReservation var1 = this.a;
        if (var1 != null) {
            var1.close();
        }
    }

    @Override
    public final int onStartCommand(Intent var1, int var2, int var3) {
        WifiManager wifiMgr = (WifiManager) this.getSystemService("wifi");
        if (wifiMgr != null
                && ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0
                && ContextCompat.checkSelfPermission(this, "android.permission.NEARBY_WIFI_DEVICES") == 0) {
            wifiMgr.startLocalOnlyHotspot(new LocalHotspotCallback(this), new Handler(Looper.getMainLooper()));
        }
        return 1;
    }
}
