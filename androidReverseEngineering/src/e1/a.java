package e1;

import android.util.Log;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import k1.e;

public abstract class a extends c {
   public boolean b;
   public boolean c;
   public ScheduledExecutorService d;
   public ScheduledFuture e;
   public final long f = TimeUnit.SECONDS.toNanos(60L);
   public boolean g = false;
   public final Object h = new Object();

   public static void q(a var0, b var1, long var2) {
      var0.getClass();
      if (var1 instanceof d) {
         d var7 = (d)var1;
         long var5 = var7.r;
         boolean var4 = false;
         if (var5 < var2) {
            Log.d("e1.a", "Closing connection due to no pong received");
            var7.k(
               "The connection was closed because the other endpoint did not respond with a pong in time. For more information check: https://github.com/TooTallNate/Java-WebSocket/wiki/Lost-connection-detection",
               false,
               1006
            );
         } else {
            if (var7.h == 2) {
               var4 = true;
            }

            if (var4) {
               c var8 = var7.c;
               if (var8.a == null) {
                  var8.a = new e();
               }

               e var9 = var8.a;
               if (var9 == null) {
                  throw new NullPointerException("onPreparePing(WebSocket) returned null. PingFrame to sent can't be null.");
               }

               var7.s(Collections.singletonList(var9));
            } else {
               Log.d("e1.a", "Trying to ping a non open connection");
            }
         }
      }
   }

   public abstract Collection r();

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void s() {
      Object var3 = this.h;
      synchronized (var3){} // $VF: monitorenter 

      Throwable var10000;
      label310: {
         try {
            if (this.f <= 0L) {
               Log.d("e1.a", "Connection lost timer deactivated");
               // $VF: monitorexit
               return;
            }
         } catch (Throwable var61) {
            var10000 = var61;
            boolean var10001 = false;
            break label310;
         }

         ScheduledExecutorService var4;
         try {
            Log.d("e1.a", "Connection lost timer started");
            var4 = this.d;
         } catch (Throwable var60) {
            var10000 = var60;
            boolean var66 = false;
            break label310;
         }

         if (var4 != null) {
            try {
               var4.shutdownNow();
               this.d = null;
            } catch (Throwable var59) {
               var10000 = var59;
               boolean var67 = false;
               break label310;
            }
         }

         try {
            var62 = this.e;
         } catch (Throwable var58) {
            var10000 = var58;
            boolean var68 = false;
            break label310;
         }

         if (var62 != null) {
            try {
               var62.cancel(false);
               this.e = null;
            } catch (Throwable var57) {
               var10000 = var57;
               boolean var69 = false;
               break label310;
            }
         }

         label286:
         try {
            o1.b var64 = new o1.b(this.g);
            this.d = Executors.newSingleThreadScheduledExecutor(var64);
            o.d var5 = new o.d(this);
            var4 = this.d;
            long var1 = this.f;
            this.e = var4.scheduleAtFixedRate(var5, var1, var1, TimeUnit.NANOSECONDS);
            // $VF: monitorexit
            return;
         } catch (Throwable var56) {
            var10000 = var56;
            boolean var70 = false;
            break label286;
         }
      }

      while (true) {
         Throwable var63 = var10000;

         try {
            // $VF: monitorexit
            throw var63;
         } catch (Throwable var55) {
            var10000 = var55;
            boolean var71 = false;
            continue;
         }
      }
   }
}
