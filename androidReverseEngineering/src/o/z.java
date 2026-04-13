package o;

import java.util.Objects;

public final class z implements Runnable {
   public final int a;
   public final a0 b;

   @Override
   public final void run() {
      int var1 = this.a;
      a0 var2 = this.b;
      switch (var1) {
         case 0:
            var2.D0();
            return;
         default:
            if (Objects.equals(var2.p.get(), r.g.b)) {
               if (com.guard.wallet.utils.g.F0(3)) {
                  com.guard.wallet.utils.g.T0(5);
               }

               if (com.guard.wallet.utils.g.F0(1)) {
                  com.guard.wallet.utils.g.T0(5);
               }

               if (var2.k() != null) {
                  var2.k().refresh();
               }
            }
      }
   }
}
