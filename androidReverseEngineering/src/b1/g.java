package b1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class g {
   public static final byte[] a = com.guard.wallet.utils.g.Y("host::\u0000");

   public static byte[] a(int var0, int var1, int var2, byte[] var3, int var4, int var5) {
      int var6;
      if (var3 != null) {
         var6 = var5 + 24;
      } else {
         var6 = 24;
      }

      ByteBuffer var7 = ByteBuffer.allocate(var6).order(ByteOrder.LITTLE_ENDIAN);
      var7.putInt(var0);
      var7.putInt(var1);
      var7.putInt(var2);
      var6 = 0;
      var1 = 0;
      if (var3 != null) {
         var7.putInt(var5);
         var2 = var4;

         while (true) {
            var6 = var1;
            if (var2 >= var4 + var5) {
               break;
            }

            var1 += var3[var2] & 255;
            var2++;
         }
      } else {
         var7.putInt(0);
      }

      var7.putInt(var6);
      var7.putInt(~var0);
      if (var3 != null) {
         var7.put(var3, var4, var5);
      }

      return var7.array();
   }

   public static byte[] b(int var0, int var1, byte[] var2, int var3) {
      int var4;
      if (var2 == null) {
         var4 = 0;
      } else {
         var4 = var2.length;
      }

      return a(var0, var1, var3, var2, 0, var4);
   }
}
