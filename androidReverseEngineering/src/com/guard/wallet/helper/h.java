package com.guard.wallet.helper;

import com.guard.wallet.entity.Point;

public final class h implements Runnable {
   public final int a;
   public final long b;
   public final Point[] c;

   @Override
   public final void run() {
      Long var4 = 10L;
      int var1 = this.a;
      Point[] var5 = this.c;
      long var2 = this.b;
      switch (var1) {
         case 0:
            com.guard.wallet.utils.g.S(var4, var2, var5);
            return;
         default:
            com.guard.wallet.utils.g.S(var4, var2, var5);
      }
   }
}
