package o1;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class b implements ThreadFactory {
   public final ThreadFactory a = Executors.defaultThreadFactory();
   public final AtomicInteger b = new AtomicInteger(1);
   public final String c = "WebSocketConnectionLostChecker";
   public final boolean d;

   public b(boolean var1) {
      this.d = var1;
   }

   @Override
   public final Thread newThread(Runnable var1) {
      Thread var2 = this.a.newThread(var1);
      var2.setDaemon(this.d);
      StringBuilder var3 = new StringBuilder();
      var3.append(this.c);
      var3.append("-");
      var3.append(this.b);
      var2.setName(var3.toString());
      return var2;
   }
}
