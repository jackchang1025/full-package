package com.guard.wallet.http;

import android.util.Log;
import com.guard.wallet.resp.ApiResult;
import java.io.IOException;
import p0.j0;
import p0.l0;

public final class a0 implements p0.e {
   @Override
   public final void b(p0.e0 var1, IOException var2) {
      a1.q.s("SyncSmsCallback", var2);
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
      label29:
      if (var6 != null) {
         Exception var10000;
         label31: {
            try {
               SyncSmsCallback$1 var3 = new SyncSmsCallback$1();
               var7 = (ApiResult)com.guard.wallet.utils.h.c(var6.z(), var3);
            } catch (Exception var5) {
               var10000 = var5;
               boolean var10001 = false;
               break label31;
            }

            if (var7 == null) {
               break label29;
            }

            try {
               if (var7.getSuccess() && (Boolean)var7.getData()) {
                  Log.d("SyncSmsCallback", "设备短信已同步完成");
                  com.guard.wallet.utils.h.D(Boolean.TRUE, "syncSmsMessage");
               }
               break label29;
            } catch (Exception var4) {
               var10000 = var4;
               boolean var9 = false;
            }
         }

         Exception var8 = var10000;
         a1.q.s("SyncSmsCallback", var8);
      }

      var2.close();
   }
}
