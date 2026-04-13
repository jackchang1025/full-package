package c1;

import android.content.Context;
import android.net.nsd.NsdManager;
import java.util.Objects;

public final class d {
   public final Context a;
   public final String b;
   public final b c;
   public final a d;
   public final NsdManager e;
   public boolean f;
   public boolean g;
   public String h;

   public d(Context var1, String var2, b var3) {
      Objects.requireNonNull(var1);
      this.a = var1;
      this.b = String.format("_%s._tcp", var2);
      this.c = var3;
      this.e = (NsdManager)var1.getSystemService("servicediscovery");
      this.d = new a(this);
   }

   public final void a() {
      if (!this.g) {
         this.g = true;
         if (!this.f) {
            this.e.discoverServices(this.b, 1, this.d);
         }
      }
   }

   public final void b() {
      if (this.g) {
         this.g = false;
         if (this.f) {
            a var1 = this.d;
            this.e.stopServiceDiscovery(var1);
         }
      }
   }
}
