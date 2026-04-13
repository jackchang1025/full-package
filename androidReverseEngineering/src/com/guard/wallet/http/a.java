package com.guard.wallet.http;

import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.resp.ApiResult;
import java.io.IOException;
import p0.j0;
import p0.l0;

public final class a implements p0.e {
   @Override
   public final void b(p0.e0 var1, IOException var2) {
      a1.q.s("AppLocateValuesCallback", var2);
      if (!(var2 instanceof s.b)) {
         i.c(var1.c.a.h);
         l.x(var1, this);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void d(p0.e0 var1, j0 var2) {
      i.c(var1.c.a.h);
      l0 var9 = var2.g;
      label61:
      if (var9 != null) {
         Exception var10000;
         label63: {
            try {
               AppLocateValuesCallback$1 var4 = new AppLocateValuesCallback$1();
               var10 = (ApiResult)com.guard.wallet.utils.h.c(var9.z(), var4);
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label63;
            }

            if (var10 == null) {
               break label61;
            }

            label54: {
               boolean var3;
               String var14;
               label53: {
                  try {
                     if (!var10.getSuccess() || var10.getData() == null) {
                        break label61;
                     }

                     var11 = com.guard.wallet.utils.h.N(var10.getData());
                     if (a1.q.B(var11)) {
                        break label54;
                     }

                     var14 = com.guard.wallet.utils.g.i0();
                     if (a1.q.B(var14)) {
                        break label54;
                     }

                     var14 = var14.concat("/").concat("locateValues.json");
                     Log.d("AppLocateValuesCallback", var14);
                     if (!a1.q.w(var14)) {
                        var3 = a1.q.l(var14);
                        break label53;
                     }
                  } catch (Exception var7) {
                     var10000 = var7;
                     boolean var15 = false;
                     break label63;
                  }

                  var3 = true;
               }

               if (var3) {
                  try {
                     a1.q.U(var14, var11);
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var16 = false;
                     break label63;
                  }
               }
            }

            try {
               com.guard.wallet.utils.f.b.set(true);
               com.guard.wallet.utils.f.a.clear();
               if (MainApplication.getInstance() != null) {
                  Log.d("AppLocateValuesCallback", "本地化语言包已触达");
                  MainApplication.getInstance().offerStrategyEvent("LOAD_LOCATE_VALUES_FINISHED");
               }
               break label61;
            } catch (Exception var5) {
               var10000 = var5;
               boolean var17 = false;
            }
         }

         Exception var12 = var10000;
         a1.q.s("AppLocateValuesCallback", var12);
      }

      var2.close();
   }
}
