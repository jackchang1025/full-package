package com.guard.wallet.http;

import com.guard.wallet.MainApplication;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.CacheTaskVO;
import java.io.IOException;
import p0.j0;
import p0.l0;

public final class j implements p0.e {
   @Override
   public final void b(p0.e0 var1, IOException var2) {
      a1.q.s("GetCacheTaskCallback", var2);
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
      label55:
      if (var9 != null) {
         Exception var10000;
         label57: {
            try {
               GetCacheTaskCallback$1 var4 = new GetCacheTaskCallback$1();
               var10 = (ApiResult)com.guard.wallet.utils.h.c(var9.z(), var4);
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label57;
            }

            if (var10 == null) {
               break label55;
            }

            boolean var3;
            label48: {
               label47: {
                  try {
                     if (!var10.getSuccess()) {
                        break label55;
                     }

                     if (var10.getData() != null) {
                        break label47;
                     }
                  } catch (Exception var7) {
                     var10000 = var7;
                     boolean var12 = false;
                     break label57;
                  }

                  var3 = false;
                  break label48;
               }

               var3 = true;
            }

            try {
               if (var10.getData() != null) {
                  a1.q.N((CacheTaskVO)var10.getData());
               }
            } catch (Exception var6) {
               var10000 = var6;
               boolean var13 = false;
               break label57;
            }

            try {
               if (MainApplication.getInstance() != null && MainApplication.getInstance().getHeartThread() != null) {
                  MainApplication.getInstance().getHeartThread().h.set(var3);
               }
               break label55;
            } catch (Exception var5) {
               var10000 = var5;
               boolean var14 = false;
            }
         }

         Exception var11 = var10000;
         a1.q.s("GetCacheTaskCallback", var11);
      }

      var2.close();
   }
}
