package v0;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class u implements a1.t {
   public final a1.g a;
   public int b;
   public byte c;
   public int d;
   public int e;
   public short f;

   public u(a1.g var1) {
      this.a = var1;
   }

   @Override
   public final a1.v a() {
      return this.a.a();
   }

   @Override
   public final void close() {
   }

   @Override
   public final long u(a1.e var1, long var2) {
      while (true) {
         int var5 = this.e;
         a1.g var7 = this.a;
         if (var5 == 0) {
            var7.skip((long)this.f);
            this.f = 0;
            if ((this.c & 4) != 0) {
               return -1L;
            }

            var5 = this.d;
            int var6 = (var7.readByte() & 255) << 16 | (var7.readByte() & 255) << 8 | var7.readByte() & 255;
            this.e = var6;
            this.b = var6;
            byte var4 = (byte)(var7.readByte() & 255);
            this.c = (byte)(var7.readByte() & 255);
            Logger var8 = v.e;
            if (var8.isLoggable(Level.FINE)) {
               var8.fine(g.a(true, this.d, this.b, var4, this.c));
            }

            var6 = var7.readInt() & 2147483647;
            this.d = var6;
            if (var4 == 9) {
               if (var6 == var5) {
                  continue;
               }

               g.b(new Object[0], "TYPE_CONTINUATION streamId changed");
               throw null;
            }

            g.b(new Object[]{var4}, "%s != TYPE_CONTINUATION");
            throw null;
         }

         var2 = var7.u(var1, Math.min(var2, (long)var5));
         if (var2 == -1L) {
            return -1L;
         }

         this.e = (int)((long)this.e - var2);
         return var2;
      }
   }
}
