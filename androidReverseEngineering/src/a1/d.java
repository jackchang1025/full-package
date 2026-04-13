package a1;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

public class d extends v {
   public static final long h;
   public static final long i;
   public static d j;
   public boolean e;
   public d f;
   public long g;

   static {
      long var0 = TimeUnit.SECONDS.toMillis(60L);
      h = var0;
      i = TimeUnit.MILLISECONDS.toNanos(var0);
   }

   public static d h() {
      d var4 = j.f;
      Object var5 = null;
      long var0 = System.nanoTime();
      if (var4 == null) {
         d.class.wait(h);
         var4 = (d)var5;
         if (j.f == null) {
            var4 = (d)var5;
            if (System.nanoTime() - var0 >= i) {
               var4 = j;
            }
         }

         return var4;
      } else {
         long var2 = var4.g - var0;
         if (var2 > 0L) {
            var0 = var2 / 1000000L;
            d.class.wait(var0, (int)(var2 - 1000000L * var0));
            return null;
         } else {
            j.f = var4.f;
            var4.f = null;
            return var4;
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void i() {
      if (this.e) {
         throw new IllegalStateException("Unbalanced enter/exit");
      } else {
         long var2 = super.c;
         boolean var6 = super.a;
         long var133;
         int var1 = (var133 = var2 - 0L) == 0L ? 0 : (var133 < 0L ? -1 : 1);
         if (var1 || var6) {
            this.e = true;
            synchronized (d.class){} // $VF: monitorenter 

            Throwable var10000;
            label752: {
               try {
                  if (j == null) {
                     d var7 = new d();
                     j = var7;
                     c var120 = new c();
                     var120.start();
                  }
               } catch (Throwable var118) {
                  var10000 = var118;
                  boolean var10001 = false;
                  break label752;
               }

               long var4;
               try {
                  var4 = System.nanoTime();
               } catch (Throwable var117) {
                  var10000 = var117;
                  boolean var124 = false;
                  break label752;
               }

               label759: {
                  if (var1 && var6) {
                     try {
                        var2 = Math.min(var2, this.c() - var4);
                     } catch (Throwable var116) {
                        var10000 = var116;
                        boolean var127 = false;
                        break label752;
                     }
                  } else if (!var1) {
                     if (!var6) {
                        try {
                           AssertionError var121 = new AssertionError();
                           throw var121;
                        } catch (Throwable var113) {
                           var10000 = var113;
                           boolean var126 = false;
                           break label752;
                        }
                     }

                     try {
                        this.g = this.c();
                        break label759;
                     } catch (Throwable var114) {
                        var10000 = var114;
                        boolean var125 = false;
                        break label752;
                     }
                  }

                  try {
                     this.g = var2 + var4;
                  } catch (Throwable var115) {
                     var10000 = var115;
                     boolean var128 = false;
                     break label752;
                  }
               }

               d var122;
               try {
                  var2 = this.g;
                  var122 = j;
               } catch (Throwable var111) {
                  var10000 = var111;
                  boolean var129 = false;
                  break label752;
               }

               d var8;
               while (true) {
                  try {
                     var8 = var122.f;
                  } catch (Throwable var110) {
                     var10000 = var110;
                     boolean var130 = false;
                     break label752;
                  }

                  if (var8 == null) {
                     break;
                  }

                  try {
                     if (var2 - var4 < var8.g - var4) {
                        break;
                     }
                  } catch (Throwable var112) {
                     var10000 = var112;
                     boolean var131 = false;
                     break label752;
                  }

                  var122 = var8;
               }

               try {
                  this.f = var8;
                  var122.f = this;
                  if (var122 == j) {
                     d.class.notify();
                  }
               } catch (Throwable var109) {
                  var10000 = var109;
                  boolean var132 = false;
                  break label752;
               }

               // $VF: monitorexit
               return;
            }

            Throwable var123 = var10000;
            // $VF: monitorexit
            throw var123;
         }
      }
   }

   public final IOException j(IOException var1) {
      return (IOException)(!this.l() ? var1 : this.m(var1));
   }

   public final void k(boolean var1) {
      if (this.l() && var1) {
         throw this.m(null);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final boolean l() {
      boolean var2 = this.e;
      boolean var1 = false;
      if (!var2) {
         return false;
      } else {
         this.e = false;
         synchronized (d.class){} // $VF: monitorenter 

         Throwable var10000;
         label116: {
            d var3;
            try {
               var3 = j;
            } catch (Throwable var16) {
               var10000 = var16;
               boolean var10001 = false;
               break label116;
            }

            while (true) {
               if (var3 == null) {
                  // $VF: monitorexit
                  var1 = true;
                  return var1;
               }

               d var4;
               try {
                  var4 = var3.f;
               } catch (Throwable var15) {
                  var10000 = var15;
                  boolean var18 = false;
                  break;
               }

               if (var4 == this) {
                  try {
                     var3.f = this.f;
                     this.f = null;
                  } catch (Throwable var14) {
                     var10000 = var14;
                     boolean var19 = false;
                     break;
                  }

                  // $VF: monitorexit
                  return var1;
               }

               var3 = var4;
            }
         }

         Throwable var17 = var10000;
         // $VF: monitorexit
         throw var17;
      }
   }

   public InterruptedIOException m(IOException var1) {
      InterruptedIOException var2 = new InterruptedIOException("timeout");
      if (var1 != null) {
         var2.initCause(var1);
      }

      return var2;
   }

   public void n() {
   }
}
