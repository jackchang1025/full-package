package s0;

import a1.t;
import a1.v;
import java.io.IOException;
import java.net.ProtocolException;

public final class d implements t {
   public final t a;
   public final long b;
   public long c;
   public boolean d;
   public boolean e;
   public final e f;

   public d(e var1, t var2, long var3) {
      this.f = var1;
      if (var2 != null) {
         this.a = var2;
         this.b = var3;
         if (var3 == 0L) {
            this.y(null);
         }
      } else {
         throw new IllegalArgumentException("delegate == null");
      }
   }

   @Override
   public final v a() {
      return this.a.a();
   }

   @Override
   public final void close() {
      if (!this.e) {
         this.e = true;

         try {
            this.x();
            this.y(null);
         } catch (IOException var2) {
            throw this.y(var2);
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final long u(a1.e var1, long var2) {
      if (!this.e) {
         IOException var10000;
         label55: {
            try {
               var2 = this.a.u(var1, var2);
            } catch (IOException var14) {
               var10000 = var14;
               boolean var10001 = false;
               break label55;
            }

            if (var2 == -1L) {
               try {
                  this.y(null);
                  return -1L;
               } catch (IOException var9) {
                  var10000 = var9;
                  boolean var19 = false;
               }
            } else {
               label59: {
                  long var4;
                  try {
                     var4 = this.c;
                  } catch (IOException var13) {
                     var10000 = var13;
                     boolean var20 = false;
                     break label59;
                  }

                  long var6 = var4 + var2;
                  var4 = this.b;
                  if (var4 != -1L && var6 > var4) {
                     try {
                        StringBuilder var16 = new StringBuilder("expected ");
                        var16.append(var4);
                        var16.append(" bytes but received ");
                        var16.append(var6);
                        ProtocolException var8 = new ProtocolException(var16.toString());
                        throw var8;
                     } catch (IOException var10) {
                        var10000 = var10;
                        boolean var23 = false;
                     }
                  } else {
                     label60: {
                        try {
                           this.c = var6;
                        } catch (IOException var12) {
                           var10000 = var12;
                           boolean var21 = false;
                           break label60;
                        }

                        if (var6 == var4) {
                           try {
                              this.y(null);
                           } catch (IOException var11) {
                              var10000 = var11;
                              boolean var22 = false;
                              break label60;
                           }
                        }

                        return var2;
                     }
                  }
               }
            }
         }

         IOException var15 = var10000;
         throw this.y(var15);
      } else {
         throw new IllegalStateException("closed");
      }
   }

   public final void x() {
      this.a.close();
   }

   public final IOException y(IOException var1) {
      if (this.d) {
         return var1;
      } else {
         this.d = true;
         e var2 = this.f;
         if (var1 != null) {
            var2.c(var1);
         }

         var2.b.getClass();
         return var2.a.c(var2, false, true, var1);
      }
   }

   public final String z() {
      StringBuilder var1 = new StringBuilder();
      var1.append(d.class.getSimpleName());
      var1.append("(");
      var1.append(this.a.toString());
      var1.append(")");
      return var1.toString();
   }
}
