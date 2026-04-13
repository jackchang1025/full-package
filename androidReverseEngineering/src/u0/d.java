package u0;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;

public final class d extends a {
   public long d;
   public final g e;

   public d(g var1, long var2) {
      super(var1);
      this.e = var1;
      this.d = var2;
      if (var2 == 0L) {
         this.x();
      }
   }

   @Override
   public final void close() {
      if (!super.b) {
         if (this.d != 0L) {
            TimeUnit var2 = TimeUnit.MILLISECONDS;

            boolean var1;
            try {
               var1 = q0.c.q(this, 100, var2);
            } catch (IOException var3) {
               var1 = false;
            }

            if (!var1) {
               this.e.b.h();
               this.x();
            }
         }

         super.b = true;
      }
   }

   @Override
   public final long u(a1.e var1, long var2) {
      if (var2 >= 0L) {
         if (!super.b) {
            long var4 = this.d;
            if (var4 == 0L) {
               return -1L;
            } else {
               var4 = super.u(var1, Math.min(var4, var2));
               if (var4 != -1L) {
                  var2 = this.d - var4;
                  this.d = var2;
                  if (var2 == 0L) {
                     this.x();
                  }

                  return var4;
               } else {
                  this.e.b.h();
                  ProtocolException var7 = new ProtocolException("unexpected end of stream");
                  this.x();
                  throw var7;
               }
            }
         } else {
            throw new IllegalStateException("closed");
         }
      } else {
         StringBuilder var6 = new StringBuilder("byteCount < 0: ");
         var6.append(var2);
         throw new IllegalArgumentException(var6.toString());
      }
   }
}
