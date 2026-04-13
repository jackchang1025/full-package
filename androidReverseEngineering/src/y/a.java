package y;

import a1.q;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class a extends ContentObserver {
   public static final ConcurrentHashMap c = new ConcurrentHashMap(5);
   public final ExecutorService a = Executors.newFixedThreadPool(5);
   public final Integer b = 0;

   public a() {
      super(new Handler(Looper.getMainLooper()));
      this.b = 1;
   }

   public final void onChange(boolean var1, Uri var2) {
      if (var2 != null && this.b == 1) {
         String var3 = var2.toString();
         if (!q.B(var3)) {
            ConcurrentHashMap var4 = c;
            if (!var4.containsKey(var3)) {
               var4.put(var3, var2);
               com.guard.wallet.thread.a var5 = new com.guard.wallet.thread.a(var2, 0);
               this.a.submit(var5);
            }
         }
      }
   }
}
