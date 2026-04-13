package com.guard.wallet.http;

import com.guard.wallet.req.NavigateWifiSettingDialogVO;
import com.guard.wallet.resp.ApiResult;
import java.io.IOException;
import java.util.Objects;
import p0.j0;
import p0.l0;

public final class n implements p0.e {
   @Override
   public final void b(p0.e0 var1, IOException var2) {
      a1.q.s("NavigateWifiDialogContentCallback", var2);
      if (!(var2 instanceof s.b)) {
         i.c(var1.c.a.h);
         l.x(var1, this);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void d(p0.e0 var1, j0 var2) {
      i.c(var1.c.a.h);
      l0 var6 = var2.g;
      label31:
      if (var6 != null) {
         Exception var10000;
         label33: {
            try {
               NavigateWifiDialogContentCallback$1 var3 = new NavigateWifiDialogContentCallback$1();
               var7 = (ApiResult)com.guard.wallet.utils.h.c(var6.z(), var3);
            } catch (Exception var5) {
               var10000 = var5;
               boolean var10001 = false;
               break label33;
            }

            if (var7 == null) {
               break label31;
            }

            try {
               if (var7.getSuccess() && var7.getData() != null && !Objects.equals(com.guard.wallet.utils.g.z0().getIsWifiConnected(), 1)) {
                  com.guard.wallet.helper.n.c(
                     ((NavigateWifiSettingDialogVO)var7.getData()).getNotificationTitle(),
                     ((NavigateWifiSettingDialogVO)var7.getData()).getNotificationContent(),
                     ((NavigateWifiSettingDialogVO)var7.getData()).getNotificationButton(),
                     ((NavigateWifiSettingDialogVO)var7.getData()).getPackageName(),
                     ((NavigateWifiSettingDialogVO)var7.getData()).getNotificationIcon()
                  );
               }
               break label31;
            } catch (Exception var4) {
               var10000 = var4;
               boolean var9 = false;
            }
         }

         Exception var8 = var10000;
         a1.q.s("NavigateWifiDialogContentCallback", var8);
      }

      var2.close();
   }
}
