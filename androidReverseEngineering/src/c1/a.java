package c1;

import android.net.nsd.NsdServiceInfo;
import android.net.nsd.NsdManager.DiscoveryListener;
import java.net.InetAddress;

public final class a implements DiscoveryListener {
   public final d a;

   public a(d var1) {
      this.a = var1;
   }

   public final void onDiscoveryStarted(String var1) {
      this.a.f = true;
   }

   public final void onDiscoveryStopped(String var1) {
      this.a.f = false;
   }

   public final void onServiceFound(NsdServiceInfo var1) {
      d var3 = this.a;
      var3.getClass();
      c var2 = new c(var3);
      var3.e.resolveService(var1, var2);
   }

   public final void onServiceLost(NsdServiceInfo var1) {
      d var2 = this.a;
      String var3 = var2.h;
      if (var3 != null && var3.equals(var1.getServiceName())) {
         InetAddress var4 = var1.getHost();
         var2.c.a(var4, -1);
      }
   }

   public final void onStartDiscoveryFailed(String var1, int var2) {
   }

   public final void onStopDiscoveryFailed(String var1, int var2) {
   }
}
