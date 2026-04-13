package o;

import android.os.Build.VERSION;
import android.util.Log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public final class r {
   public final ExecutorService a = Executors.newSingleThreadExecutor();
   public final com.guard.wallet.thread.k b = new com.guard.wallet.thread.k(true);
   public final AtomicLong c = new AtomicLong(0L);

   public final void a() {
      int var1 = VERSION.SDK_INT;
      String var8;
      if (var1 < 30) {
         var8 = "MiniCap use Media Projection";
      } else {
         label37: {
            long var2 = System.currentTimeMillis();
            if (!com.guard.wallet.utils.e.j()) {
               AtomicLong var4 = this.c;
               if (var2 - var4.get() < 30000L) {
                  var8 = "黑屏中,等待30秒...";
                  break label37;
               }

               var4.set(var2);
            }

            com.guard.wallet.thread.k var5;
            label26: {
               var5 = this.b;
               if (var1 >= 30) {
                  u.a var7 = (u.a)var5.b;
                  if (var7 != null && !var7.b()) {
                     var6 = true;
                     break label26;
                  }
               } else {
                  var5.getClass();
               }

               var6 = false;
            }

            if (!var6) {
               this.a.submit(var5);
            }

            return;
         }
      }

      Log.d("o.r", var8);
   }
}
