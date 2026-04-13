package f0;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public final class w extends x {
   public final byte b = 0;
   public final g0.b c;

   public w(k0.d var1) {
      super(1);
      this.c = var1;
   }

   @Override
   public final x a(o var1, m var2) {
      m var6 = new m();
      boolean var3 = true;

      boolean var4;
      while (true) {
         var4 = var3;
         if (var2.a.size() <= 0) {
            break;
         }

         ByteBuffer var7 = var2.l();
         ((Buffer)var7).mark();
         int var5 = 0;

         while (true) {
            var4 = var3;
            if (var7.remaining() <= 0) {
               break;
            }

            if (var7.get() == this.b) {
               var3 = true;
            } else {
               var3 = false;
            }

            var4 = var3;
            if (var3) {
               break;
            }

            var5++;
         }

         ((Buffer)var7).reset();
         if (var4) {
            var2.b(var7);
            var2.d(var6, var5);
            var2.i(1).get();
            var2.c--;
            break;
         }

         var6.a(var7);
         var3 = var4;
      }

      this.c.b(var1, var6);
      return var4 ? null : this;
   }
}
