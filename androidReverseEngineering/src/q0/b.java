package q0;

import java.util.concurrent.ThreadFactory;

// $VF: synthetic class
public final class b implements ThreadFactory {
   public final String a;
   public final boolean b;

   @Override
   public final Thread newThread(Runnable var1) {
      Thread var2 = new Thread(var1, this.a);
      var2.setDaemon(this.b);
      return var2;
   }
}
