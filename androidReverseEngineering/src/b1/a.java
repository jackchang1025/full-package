package b1;

import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

// $VF: synthetic class
public final class a implements c1.b {
   public final int a;
   public final AtomicReference b;
   public final AtomicInteger c;
   public final CountDownLatch d;

   @Override
   public final void a(InetAddress var1, int var2) {
      int var3 = this.a;
      AtomicInteger var5 = this.c;
      AtomicReference var6 = this.b;
      CountDownLatch var4 = this.d;
      switch (var3) {
         case 0:
            if (var1 != null) {
               var6.set(var1.getHostAddress());
               var5.set(var2);
            }

            var4.countDown();
            return;
         default:
            if (var1 != null) {
               var6.set(var1.getHostAddress());
               var5.set(var2);
            }

            var4.countDown();
      }
   }
}
