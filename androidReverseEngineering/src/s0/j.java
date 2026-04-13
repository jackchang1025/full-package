package s0;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import v0.s;
import v0.y;

public final class j extends a1.d {
   public final int k;
   public final Object l;

   @Override
   public final InterruptedIOException m(IOException var1) {
      switch (this.k) {
         case 1:
            SocketTimeoutException var3 = new SocketTimeoutException("timeout");
            if (var1 != null) {
               var3.initCause(var1);
            }

            return var3;
         case 2:
            SocketTimeoutException var2 = new SocketTimeoutException("timeout");
            if (var1 != null) {
               var2.initCause(var1);
            }

            return var2;
         default:
            return super.m(var1);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final void n() {
      int var2 = this.k;
      boolean var1 = true;
      switch (var2) {
         case 0:
            ((l)this.l).a();
            return;
         case 1:
            ((y)this.l).e(v0.b.g);
            s var48 = ((y)this.l).d;
            synchronized (var48){} // $VF: monitorenter 

            Throwable var10000;
            label249: {
               long var3;
               long var5;
               try {
                  var3 = var48.n;
                  var5 = var48.m;
               } catch (Throwable var45) {
                  var10000 = var45;
                  boolean var10001 = false;
                  break label249;
               }

               if (var3 < var5) {
                  try {
                     // $VF: monitorexit
                  } catch (Throwable var43) {
                     var10000 = var43;
                     boolean var54 = false;
                     break label249;
                  }
               } else {
                  try {
                     var48.m = var5 + 1L;
                     var48.o = System.nanoTime() + 1000000000L;
                     // $VF: monitorexit
                  } catch (Throwable var44) {
                     var10000 = var44;
                     boolean var55 = false;
                     break label249;
                  }

                  try {
                     ScheduledThreadPoolExecutor var52 = var48.h;
                     v0.j var51 = new v0.j(var48, "OkHttp %s ping", new Object[]{var48.d}, 0);
                     var52.execute(var51);
                  } catch (RejectedExecutionException var41) {
                  }

                  return;
               }

               return;
            }

            while (true) {
               Throwable var50 = var10000;

               try {
                  // $VF: monitorexit
                  throw var50;
               } catch (Throwable var42) {
                  var10000 = var42;
                  boolean var56 = false;
                  continue;
               }
            }
         default:
            Object var12 = this.l;

            StringBuilder var9;
            Object var10;
            Logger var11;
            Level var49;
            try {
               ((Socket)var12).close();
               return;
            } catch (Exception var46) {
               Logger var7 = a1.l.a;
               Level var53 = Level.WARNING;
               var9 = new StringBuilder("Failed to close timed out socket ");
               var10 = var46;
               var49 = var53;
               var11 = var7;
            } catch (AssertionError var47) {
               Logger var8 = a1.l.a;
               if (var47.getCause() == null || var47.getMessage() == null || !var47.getMessage().contains("getsockname failed")) {
                  var1 = false;
               }

               if (!var1) {
                  throw var47;
               }

               var11 = a1.l.a;
               var49 = Level.WARNING;
               var9 = new StringBuilder("Failed to close timed out socket ");
               var10 = var47;
            }

            var9.append((Socket)var12);
            var11.log(var49, var9.toString(), (Throwable)var10);
      }
   }

   public final void o() {
      if (this.l()) {
         throw this.m(null);
      }
   }
}
