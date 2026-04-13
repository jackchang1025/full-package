package com.guard.wallet.helper;

import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.ApiResult;
import java.util.List;

public abstract class i {
   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void a(String var0) {
      if (!a1.q.B(var0)) {
         Exception var10000;
         label52: {
            try {
               LockCipherHelper$1 var1 = new LockCipherHelper$1();
               var5 = (ApiResult)com.guard.wallet.utils.h.c(var0, var1);
            } catch (Exception var4) {
               var10000 = var4;
               boolean var10001 = false;
               break label52;
            }

            if (var5 == null) {
               return;
            }

            try {
               if (!var5.getSuccess() || var5.getData() == null || ((List)var5.getData()).isEmpty()) {
                  return;
               }

               var6 = ((List)var5.getData()).iterator();
            } catch (Exception var3) {
               var10000 = var3;
               boolean var9 = false;
               break label52;
            }

            while (true) {
               try {
                  if (!var6.hasNext()) {
                     return;
                  }

                  ReqUnlockDeviceVO var8 = (ReqUnlockDeviceVO)var6.next();
                  if (com.guard.wallet.utils.h.t(var8)) {
                     com.guard.wallet.utils.h.C(var8);
                  }
               } catch (Exception var2) {
                  var10000 = var2;
                  boolean var10 = false;
                  break;
               }
            }
         }

         Exception var7 = var10000;
         a1.q.s("com.guard.wallet.helper.i", var7);
      }
   }
}
