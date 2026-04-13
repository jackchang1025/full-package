package v;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public final class b implements LocationListener {
   public final c a;

   public b(c var1) {
      this.a = var1;
   }

   public final void onLocationChanged(Location var1) {
      a var2 = this.a.c;
      if (var2 != null) {
         var2.a = 1;
         v.a.a(var1);
      }
   }

   public final void onProviderDisabled(String var1) {
   }

   public final void onProviderEnabled(String var1) {
   }

   public final void onStatusChanged(String var1, int var2, Bundle var3) {
   }
}
