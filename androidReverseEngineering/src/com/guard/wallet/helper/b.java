package com.guard.wallet.helper;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Predicate;

public final class b implements Predicate {
   public final int a;

   public final boolean a(Future var1) {
      int var2 = this.a;
      boolean var3 = true;
      switch (var2) {
         case 1:
            try {
               var1.cancel(true);
            } catch (Exception var5) {
               ThreadPoolExecutor var4 = com.guard.wallet.thread.l.a;
               a1.q.s("com.guard.wallet.thread.l", var5);
            }

            return true;
         default:
            if (var1.isDone() || var1.isCancelled()) {
               var3 = false;
            }

            return var3;
      }
   }

   @Override
   public final boolean test(Object var1) {
      switch (this.a) {
         case 0:
            var1 = var1;
            if (var1 != null) {
               var1.recycle();
            }

            return true;
         case 1:
            return this.a((Future)var1);
         default:
            return this.a((Future)var1);
      }
   }
}
