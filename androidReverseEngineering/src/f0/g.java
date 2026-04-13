package f0;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class g implements ThreadFactory {
   public final ThreadGroup a;
   public final AtomicInteger b = new AtomicInteger(1);
   public final String c;

   public g(String var1) {
      SecurityManager var2 = System.getSecurityManager();
      ThreadGroup var3;
      if (var2 != null) {
         var3 = var2.getThreadGroup();
      } else {
         var3 = Thread.currentThread().getThreadGroup();
      }

      this.a = var3;
      this.c = var1;
   }

   @Override
   public final Thread newThread(Runnable var1) {
      ThreadGroup var2 = this.a;
      StringBuilder var3 = new StringBuilder();
      var3.append(this.c);
      var3.append(this.b.getAndIncrement());
      Thread var4 = new Thread(var2, var1, var3.toString(), 0L);
      if (var4.isDaemon()) {
         var4.setDaemon(false);
      }

      if (var4.getPriority() != 5) {
         var4.setPriority(5);
      }

      return var4;
   }
}
