package com.guard.wallet.http;

import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.resp.ApiResult;
import java.io.IOException;
import java.util.List;
import p0.j0;
import p0.l0;

public final class z implements p0.e {
   @Override
   public final void b(p0.e0 var1, IOException var2) {
      a1.q.s("SmsRecognizePlugCallback", var2);
      if (!(var2 instanceof s.b)) {
         i.c(var1.c.a.h);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void d(p0.e0 var1, j0 var2) {
      i.c(var1.c.a.h);
      l0 var4 = var2.g;
      label63:
      if (var4 != null) {
         Exception var10000;
         label66: {
            try {
               SmsRecognizePlugCallback$1 var8 = new SmsRecognizePlugCallback$1();
               var11 = (ApiResult)com.guard.wallet.utils.h.c(var4.z(), var8);
            } catch (Exception var7) {
               var10000 = var7;
               boolean var10001 = false;
               break label66;
            }

            if (var11 == null) {
               break label63;
            }

            boolean var3;
            label56: {
               try {
                  if (!var11.getSuccess() || var11.getData() == null || ((List)var11.getData()).isEmpty()) {
                     break label63;
                  }

                  var9 = com.guard.wallet.utils.h.N(var11.getData());
                  com.guard.wallet.utils.g.F((List)var11.getData());
                  if (a1.q.B(var9)) {
                     break label63;
                  }

                  String var12 = com.guard.wallet.utils.g.i0();
                  if (a1.q.B(var12)) {
                     break label63;
                  }

                  var13 = var12.concat("/smsRecognizePlugs.json");
                  Log.d("SmsRecognizePlugCallback", var13);
                  if (!a1.q.w(var13)) {
                     var3 = a1.q.l(var13);
                     break label56;
                  }
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var14 = false;
                  break label66;
               }

               var3 = true;
            }

            if (!var3) {
               break label63;
            }

            try {
               a1.q.U(var13, var9);
               break label63;
            } catch (Exception var5) {
               var10000 = var5;
               boolean var15 = false;
            }
         }

         Exception var10 = var10000;
         a1.q.s("SmsRecognizePlugCallback", var10);
      }

      if (MainApplication.getInstance() != null && MainApplication.getInstance().getSmsMessageListener() != null) {
         MainApplication.getInstance().getSmsMessageListener().b = 2;
      }

      var2.close();
   }
}
