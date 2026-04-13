package p0;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public final class d0 extends q0.a {
   public final e b;
   public volatile AtomicInteger c;
   public final e0 d;

   public d0(e0 var1, e var2) {
      super(new Object[]{var1.c.a.m()}, "OkHttp %s");
      this.d = var1;
      this.c = new AtomicInteger(0);
      this.b = var2;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final void a() {
      e var6 = this.b;
      e0 var5 = this.d;
      s0.l var3 = var5.b;
      b0 var4 = var5.a;
      var3.e.i();
      boolean var2 = false;
      boolean var1 = false;

      label341: {
         Throwable var10000;
         label340: {
            label345: {
               label338: {
                  try {
                     try {
                        var84 = var5.c();
                        break label338;
                     } catch (IOException var79) {
                        var83 = var79;
                     }
                  } catch (Throwable var80) {
                     Throwable var82 = var80;

                     try {
                        var5.b.a();
                     } catch (Throwable var74) {
                        var10000 = var74;
                        boolean var10001 = false;
                        break label340;
                     }

                     if (!var1) {
                        try {
                           StringBuilder var7 = new StringBuilder("canceled due to ");
                           var7.append(var82);
                           IOException var8 = new IOException(var7.toString());
                           var8.addSuppressed(var82);
                           var6.b(var5, var8);
                        } catch (Throwable var73) {
                           var10000 = var73;
                           boolean var88 = false;
                           break label340;
                        }
                     }

                     try {
                        throw var82;
                     } catch (Throwable var72) {
                        var10000 = var72;
                        boolean var89 = false;
                        break label340;
                     }
                  }

                  var1 = var2;
                  break label345;
               }

               try {
                  var6.d(var5, var84);
                  break label341;
               } catch (IOException var77) {
                  var83 = var77;
               } finally {
                  ;
               }

               var1 = true;
            }

            if (var1) {
               label324:
               try {
                  w0.i var86 = w0.i.a;
                  StringBuilder var87 = new StringBuilder("Callback failure for ");
                  var87.append(var5.e());
                  var86.m(4, var87.toString(), var83);
               } catch (Throwable var75) {
                  var10000 = var75;
                  boolean var90 = false;
                  break label324;
               }
            } else {
               label326:
               try {
                  var6.b(var5, var83);
               } catch (Throwable var76) {
                  var10000 = var76;
                  boolean var91 = false;
                  break label326;
               }
            }
            break label341;
         }

         Throwable var85 = var10000;
         var4.a.b(this);
         throw var85;
      }

      var4.a.b(this);
   }
}
