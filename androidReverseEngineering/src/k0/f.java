package k0;

import f0.m;
import f0.o;
import f0.u;
import f0.y;
import java.nio.ByteOrder;
import java.util.zip.CRC32;
import java.util.zip.Inflater;

public final class f extends g {
   public boolean k = true;
   public final CRC32 l = new CRC32();

   public f() {
      super(new Inflater(true));
   }

   public static short l(byte[] var0, ByteOrder var1) {
      byte var2;
      int var3;
      if (var1 == ByteOrder.BIG_ENDIAN) {
         var3 = var0[0] << 8;
         var2 = var0[1];
      } else {
         var3 = var0[1] << 8;
         var2 = var0[0];
      }

      return (short)(var2 & 255 | var3);
   }

   @Override
   public final void b(o var1, m var2) {
      if (this.k) {
         y var4 = new y(var1);
         e var3 = new e(this, var1, var4);
         var4.d.add(new u(10, var3));
      } else {
         super.b(var1, var2);
      }
   }
}
