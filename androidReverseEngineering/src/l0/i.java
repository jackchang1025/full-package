package l0;

import f0.t;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

// $VF: synthetic class
public final class i implements Runnable {
   public final int a;
   public final Object b;
   public final Object c;
   public final Object d;

   @Override
   public final void run() {
      int var1 = this.a;
      t var5 = (t)this.d;
      f0.m var6 = (f0.m)this.c;
      k var4 = (k)this.b;
      switch (var1) {
         case 0:
            var4 = var4;
            var6 = var6;
            String var7 = (String)var5;
            var4.getClass();
            long var2 = (long)var6.c;
            var4.e = var2;
            var5 = Long.toString(var2);
            com.guard.wallet.http.h var8 = var4.d;
            var8.k("Content-Length", (String)var5);
            if (var7 != null) {
               var8.k("Content-Type", var7);
            }

            var5 = new t(var4, var6, new f0.l(var4), 1);
            var4.d(var5);
            var5.c();
            return;
         default:
            b1.p var10 = (b1.p)var4;
            AtomicBoolean var15 = (AtomicBoolean)var6;
            CountDownLatch var12 = (CountDownLatch)var5;

            try {
               var10.z();
               var15.set(true);
            } catch (Exception var9) {
               var15.set(false);
               a1.q.s("AbsAdbConnectionManager", var9);
            }

            var12.countDown();
      }
   }
}
