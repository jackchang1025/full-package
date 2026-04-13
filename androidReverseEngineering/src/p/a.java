package p;

import a1.q;
import android.util.Log;
import com.guard.wallet.entity.CheckPortResult;
import com.guard.wallet.utils.g;
import h.e;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public final class a implements Callable {
   public final int a;
   public final Object b;
   public Object c;

   public final String a() {
      int var1 = this.a;
      Object var3 = null;
      Object var4 = null;
      String var2 = (String)this.b;
      switch (var1) {
         case 0:
            String var10 = var2;
            var2 = (String)var4;
            if (!q.B(var10)) {
               if (q.B((String)this.c)) {
                  this.c = q.x(var10);
               }

               if (q.B((String)this.c)) {
                  this.c = "unknown";
               }

               var3 = g.i0().concat("/").concat((String)this.c);
               Log.d("DownLoadCallable", (String)var3);
               var2 = (String)var4;
               if (p.b.a(var10, (String)var3)) {
                  var2 = (String)var3;
               }
            }

            return var2;
         default:
            String var5 = var2;
            var2 = (String)var3;
            if (!q.B(var5)) {
               if (q.B((String)this.c)) {
                  this.c = q.x(var5);
               }

               if (q.B((String)this.c)) {
                  this.c = "unknown";
               }

               var4 = g.i0().concat("/").concat((String)this.c);
               Log.d("DownLoadCallable", (String)var4);
               var2 = (String)var3;
               if (p.b.b(var5, (String)var4)) {
                  var2 = (String)var4;
               }
            }

            return var2;
      }
   }

   @Override
   public final Object call() {
      switch (this.a) {
         case 0:
            return this.a();
         case 1:
            return this.a();
         default:
            int var1 = (Integer)this.c;
            Integer var2 = (Integer)this.b;
            if (var1 >= var2 && e.S() != null) {
               AtomicInteger var3 = new AtomicInteger(var2);

               while (true) {
                  var1 = var3.getAndIncrement();
                  if (var1 > (Integer)this.c || e.S().v.get()) {
                     break;
                  }

                  if (!q.E(var1)) {
                     CheckPortResult var5 = e.S().J(var1);
                     if (var5 != null && var5.isConnected()) {
                        return var5;
                     }
                  }
               }
            }

            return null;
      }
   }
}
