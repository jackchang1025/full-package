package k0;

import com.guard.wallet.http.h;
import f0.m;
import f0.o;
import f0.u;
import f0.v;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.CRC32;

public final class d implements v, g0.b {
   public final int d;
   public final e e;

   public final void a(byte[] var1) {
      int var2 = this.d;
      e var4 = this.e;
      switch (var2) {
         case 0:
            if (var4.e) {
               var4.h.l.update(var1, 0, 2);
            }

            short var8 = f.l(var1, ByteOrder.LITTLE_ENDIAN);
            h var6 = new h(this, 6);
            var4.g.d.add(new u(var8 & '\uffff', var6));
            return;
         default:
            short var7 = f.l(var1, ByteOrder.LITTLE_ENDIAN);
            short var3 = (short)((int)var4.h.l.getValue());
            f var5 = var4.h;
            if (var3 != var7) {
               var5.c(new IOException("CRC mismatch"));
            } else {
               var5.l.reset();
               var5.k = false;
               var5.i(var4.f);
            }
      }
   }

   @Override
   public final void b(o var1, m var2) {
      e var9 = this.e;
      boolean var4 = var9.e;
      f var8 = var9.h;
      if (var4) {
         while (var2.a.size() > 0) {
            ByteBuffer var5 = var2.l();
            CRC32 var6 = var8.l;
            byte[] var7 = var5.array();
            int var3 = var5.arrayOffset();
            var6.update(var7, var5.position() + var3, var5.remaining());
            m.j(var5);
         }
      }

      var2.k();
      if (var9.e) {
         d var10 = new d(var9, 2);
         var9.g.d.add(new u(2, var10));
      } else {
         var8.k = false;
         var8.i(var9.f);
      }
   }
}
