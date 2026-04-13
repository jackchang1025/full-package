package h;

import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

// $VF: synthetic class
public final class c implements c1.b {
   public final AtomicInteger a;
   public final CountDownLatch b;

   @Override
   public final void a(InetAddress var1, int var2) {
      this.a.set(var2);
      this.b.countDown();
   }
}
