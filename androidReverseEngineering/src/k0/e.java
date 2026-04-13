package k0;

import f0.o;
import f0.u;
import f0.v;
import f0.w;
import f0.y;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.Locale;

public final class e implements v {
   public int d;
   public boolean e;
   public final o f;
   public final y g;
   public final f h;

   public e(f var1, o var2, y var3) {
      this.h = var1;
      this.f = var2;
      this.g = var3;
   }

   public final void a() {
      o var2 = this.f;
      y var4 = new y(var2);
      d var3 = new d(this, 1);
      int var1 = this.d;
      LinkedList var7 = var4.d;
      if ((var1 & 8) != 0) {
         var7.add(new w(var3));
      } else if ((var1 & 16) != 0) {
         var7.add(new w(var3));
      } else {
         if (this.e) {
            d var5 = new d(this, 2);
            this.g.d.add(new u(2, var5));
         } else {
            f var6 = this.h;
            var6.k = false;
            var6.i(var2);
         }
      }
   }

   @Override
   public final void c(Object var1) {
      var1 = var1;
      short var2 = k0.f.l(var1, ByteOrder.LITTLE_ENDIAN);
      boolean var4 = true;
      f var5 = this.h;
      if (var2 != -29921) {
         var5.c(new IOException(String.format(Locale.ENGLISH, "unknown format (magic number %x)", var2)));
         b0.b var7 = new b0.b(24);
         this.f.h(var7);
      } else {
         byte var3 = var1[3];
         this.d = var3;
         if ((var3 & 2) == 0) {
            var4 = false;
         }

         this.e = var4;
         if (var4) {
            var5.l.update(var1, 0, var1.length);
         }

         if ((this.d & 4) != 0) {
            d var8 = new d(this, 0);
            this.g.d.add(new u(2, var8));
         } else {
            this.a();
         }
      }
   }
}
