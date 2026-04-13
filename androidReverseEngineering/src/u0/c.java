package u0;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import p0.s;
import p0.u;

public final class c extends a {
   public final u d;
   public long e;
   public boolean f;
   public final g g;

   public c(g var1, u var2) {
      super(var1);
      this.g = var1;
      this.e = -1L;
      this.f = true;
      this.d = var2;
   }

   @Override
   public final void close() {
      if (!super.b) {
         if (this.f) {
            TimeUnit var2 = TimeUnit.MILLISECONDS;

            boolean var1;
            try {
               var1 = q0.c.q(this, 100, var2);
            } catch (IOException var3) {
               var1 = false;
            }

            if (!var1) {
               this.g.b.h();
               this.x();
            }
         }

         super.b = true;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final long u(a1.e var1, long var2) {
      if (var2 < 0L) {
         StringBuilder var14 = new StringBuilder("byteCount < 0: ");
         var14.append(var2);
         throw new IllegalArgumentException(var14.toString());
      } else if (!super.b) {
         if (!this.f) {
            return -1L;
         } else {
            NumberFormatException var10000;
            label75: {
               long var5 = this.e;
               g var8 = this.g;
               if (var5 == 0L || var5 == -1L) {
                  if (var5 != -1L) {
                     var8.c.l();
                  }

                  label56: {
                     String var7;
                     label55: {
                        boolean var4;
                        try {
                           this.e = var8.c.v();
                           var7 = var8.c.l().trim();
                           if (this.e < 0L) {
                              break label55;
                           }

                           if (var7.isEmpty()) {
                              break label56;
                           }

                           var4 = var7.startsWith(";");
                        } catch (NumberFormatException var10) {
                           var10000 = var10;
                           boolean var10001 = false;
                           break label75;
                        }

                        if (var4) {
                           break label56;
                        }
                     }

                     try {
                        StringBuilder var13 = new StringBuilder("expected chunk size and optional extensions but was \"");
                        var13.append(this.e);
                        var13.append(var7);
                        var13.append("\"");
                        ProtocolException var17 = new ProtocolException(var13.toString());
                        throw var17;
                     } catch (NumberFormatException var9) {
                        var10000 = var9;
                        boolean var18 = false;
                        break label75;
                     }
                  }

                  if (this.e == 0L) {
                     this.f = false;
                     s var16 = var8.k();
                     t0.e.d(var8.a.h, this.d, var16);
                     this.x();
                  }

                  if (!this.f) {
                     return -1L;
                  }
               }

               var2 = super.u(var1, Math.min(var2, this.e));
               if (var2 != -1L) {
                  this.e -= var2;
                  return var2;
               }

               var8.b.h();
               ProtocolException var11 = new ProtocolException("unexpected end of stream");
               this.x();
               throw var11;
            }

            NumberFormatException var12 = var10000;
            throw new ProtocolException(var12.getMessage());
         }
      } else {
         throw new IllegalStateException("closed");
      }
   }
}
