package a0;

import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Build;
import android.util.Log;
import com.guard.wallet.service.LocalHotspotService;

/* renamed from: a0.e */
/* loaded from: classes.dex */
public final class C0005e extends WifiManager.LocalOnlyHotspotCallback {

    /* renamed from: a */
    public final /* synthetic */ LocalHotspotService f6a;

    public C0005e(LocalHotspotService localHotspotService) {
        this.f6a = localHotspotService;
    }

    @Override // android.net.wifi.WifiManager.LocalOnlyHotspotCallback
    public final void onFailed(int i2) {
        super.onFailed(i2);
    }

    @Override // android.net.wifi.WifiManager.LocalOnlyHotspotCallback
    public final void onStarted(WifiManager.LocalOnlyHotspotReservation localOnlyHotspotReservation) {
        WifiNetworkSpecifier.Builder ssid;
        WifiNetworkSpecifier.Builder wpa2Passphrase;
        WifiNetworkSpecifier.Builder isHiddenSsid;
        WifiNetworkSpecifier build;
        super.onStarted(localOnlyHotspotReservation);
        LocalHotspotService localHotspotService = this.f6a;
        localHotspotService.f319a = localOnlyHotspotReservation;
        if (localOnlyHotspotReservation == null || localOnlyHotspotReservation.getWifiConfiguration() == null) {
            return;
        }
        String str = localOnlyHotspotReservation.getWifiConfiguration().SSID;
        String str2 = localOnlyHotspotReservation.getWifiConfiguration().preSharedKey;
        int i2 = LocalHotspotService.f318b;
        Log.d("com.guard.wallet.service.LocalHotspotService", "ssid:" + str);
        Log.d("com.guard.wallet.service.LocalHotspotService", "pwd:" + str2);
        ConnectivityManager connectivityManager = (ConnectivityManager) localHotspotService.getSystemService("connectivity");
        if (connectivityManager == null || Build.VERSION.SDK_INT < 29) {
            return;
        }
        AbstractC0004d.m46o();
        ssid = AbstractC0004d.m37f().setSsid(str);
        wpa2Passphrase = ssid.setWpa2Passphrase(str2);
        isHiddenSsid = wpa2Passphrase.setIsHiddenSsid(true);
        build = isHiddenSsid.build();
        connectivityManager.requestNetwork(new NetworkRequest.Builder().addTransportType(1).setNetworkSpecifier(build).build(), new C0006f());
    }

    @Override // android.net.wifi.WifiManager.LocalOnlyHotspotCallback
    public final void onStopped() {
        super.onStopped();
    }
}
