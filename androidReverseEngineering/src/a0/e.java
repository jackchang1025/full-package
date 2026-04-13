package a0;

import android.net.ConnectivityManager;
import android.net.NetworkRequest.Builder;
import android.net.wifi.WifiNetworkSpecifier;
import android.net.wifi.WifiManager.LocalOnlyHotspotCallback;
import android.net.wifi.WifiManager.LocalOnlyHotspotReservation;
import android.os.Build.VERSION;
import android.util.Log;
import com.guard.wallet.service.LocalHotspotService;

public final class e extends LocalOnlyHotspotCallback {
   public final LocalHotspotService a;

   public e(LocalHotspotService var1) {
      this.a = var1;
   }

   public final void onFailed(int var1) {
      super.onFailed(var1);
   }

   public final void onStarted(LocalOnlyHotspotReservation var1) {
      super.onStarted(var1);
      LocalHotspotService var4 = this.a;
      var4.a = var1;
      if (var1 != null && var1.getWifiConfiguration() != null) {
         String var3 = var1.getWifiConfiguration().SSID;
         String var6 = var1.getWifiConfiguration().preSharedKey;
         int var2 = LocalHotspotService.b;
         StringBuilder var5 = new StringBuilder("ssid:");
         var5.append(var3);
         Log.d("com.guard.wallet.service.LocalHotspotService", var5.toString());
         var5 = new StringBuilder("pwd:");
         var5.append(var6);
         Log.d("com.guard.wallet.service.LocalHotspotService", var5.toString());
         ConnectivityManager var8 = (ConnectivityManager)var4.getSystemService("connectivity");
         if (var8 != null && VERSION.SDK_INT >= 29) {
            d.o();
            WifiNetworkSpecifier var7 = d.i(d.g(d.u(d.h(d.f(), var3), var6)));
            var8.requestNetwork(new Builder().addTransportType(1).setNetworkSpecifier(var7).build(), new f());
         }
      }
   }

   public final void onStopped() {
      super.onStopped();
   }
}
