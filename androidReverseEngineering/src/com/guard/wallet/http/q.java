package com.guard.wallet.http;

import android.util.Log;
import com.guard.wallet.resp.ApiResult;
import java.io.IOException;
import p0.f0;
import p0.j0;
import p0.l0;

public final class q implements p0.e {
   @Override
   public final void b(p0.e0 var1, IOException var2) {
      a1.q.s("OpenDevelopmentCallback", var2);
      if (!(var2 instanceof s.b)) {
         i.c(var1.c.a.h);
         f0 var3 = var1.c;
         if (!a1.q.B(var3.a.h) && var3.a.h.contains("127.0.0.1:7911")) {
            l.l("http://127.0.0.1:7912");
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void d(p0.e0 var1, j0 var2) {
      f0 var8 = var1.c;
      i.c(var8.a.h);
      l0 var4 = var2.g;
      label39:
      if (var4 != null) {
         Exception var10000;
         label41: {
            ApiResult var10;
            try {
               OpenDevelopmentCallback$1 var3 = new OpenDevelopmentCallback$1();
               var10 = (ApiResult)com.guard.wallet.utils.h.c(var4.z(), var3);
            } catch (Exception var7) {
               var10000 = var7;
               boolean var10001 = false;
               break label41;
            }

            if (var10 != null) {
               try {
                  if (var10.getSuccess() && (Boolean)var10.getData()) {
                     Log.d("OpenDevelopmentCallback", "开启开发者选项成功");
                     break label39;
                  }
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var11 = false;
                  break label41;
               }
            }

            try {
               if (!a1.q.B(var8.a.h) && var8.a.h.contains("127.0.0.1:7911")) {
                  l.l("http://127.0.0.1:7912");
               }
               break label39;
            } catch (Exception var5) {
               var10000 = var5;
               boolean var12 = false;
            }
         }

         Exception var9 = var10000;
         a1.q.s("OpenDevelopmentCallback", var9);
      }

      var2.close();
   }
}
