package v;

import android.location.Location;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import com.guard.wallet.req.ReqMonitorLocationVO;
import com.guard.wallet.utils.g;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class c {
   public static volatile c f;
   public LocationManager a;
   public String b;
   public final a c;
   public b d;
   public final AtomicReference e = new AtomicReference(null);

   public c() {
      if (v.a.b == null) {
         v.a.b = new a();
      }

      this.c = v.a.b;
      this.a();
   }

   public final void a() {
      if (g.Z() != null
         && ContextCompat.checkSelfPermission(g.Z(), "android.permission.ACCESS_FINE_LOCATION") == 0
         && ContextCompat.checkSelfPermission(g.Z(), "android.permission.ACCESS_COARSE_LOCATION") == 0) {
         LocationManager var1 = (LocationManager)g.Z().getSystemService("location");
         this.a = var1;
         List var4 = var1.getProviders(true);
         a var2 = null;
         Object var3 = null;
         Location var5 = var2;
         if (var4 != null) {
            var5 = var2;
            if (!var4.isEmpty()) {
               var2 = var3;
               if (VERSION.SDK_INT >= 31) {
                  var2 = var3;
                  if (var4.contains("fused")) {
                     this.b = "fused";
                     var2 = this.a.getLastKnownLocation("fused");
                  }
               }

               Location var6 = (Location)var2;
               if (var2 == null) {
                  var6 = (Location)var2;
                  if (var4.contains("gps")) {
                     var2 = this.a.getLastKnownLocation("gps");
                     var6 = (Location)var2;
                     if (this.b == null) {
                        this.b = "gps";
                        var6 = (Location)var2;
                     }
                  }
               }

               var2 = var6;
               if (var6 == null) {
                  var2 = var6;
                  if (var4.contains("network")) {
                     Location var7 = this.a.getLastKnownLocation("network");
                     var2 = var7;
                     if (this.b == null) {
                        this.b = "network";
                        var2 = var7;
                     }
                  }
               }

               var5 = (Location)var2;
               if (var2 == null) {
                  var5 = (Location)var2;
                  if (var4.contains("passive")) {
                     var2 = this.a.getLastKnownLocation("passive");
                     var5 = (Location)var2;
                     if (this.b == null) {
                        this.b = "passive";
                        var5 = (Location)var2;
                     }
                  }
               }
            }
         }

         if (var5 != null) {
            var2 = this.c;
            if (var2 != null) {
               var2.a = 1;
               v.a.a(var5);
            }
         }
      }
   }

   public final boolean b(ReqMonitorLocationVO var1) {
      AtomicReference var2 = this.e;
      if (var2.get() == null && var1 != null) {
         var2.set(var1);
         return true;
      } else if (var2.get() != null && var1 == null) {
         var2.set(null);
         return true;
      } else {
         return false;
      }
   }
}
