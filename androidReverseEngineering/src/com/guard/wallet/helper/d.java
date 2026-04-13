package com.guard.wallet.helper;

import android.util.Log;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class d {
   public static final ConcurrentHashMap a = new ConcurrentHashMap();

   public static void a() {
      try {
         ConcurrentHashMap var0 = a;
         if (!var0.isEmpty()) {
            Set var2 = var0.keySet();
            c var1 = new c();
            var2.forEach(var1);
            var0.clear();
         }
      } catch (Exception var3) {
         a1.q.s("com.guard.wallet.helper.d", var3);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void b(String var0) {
      Exception var10000;
      label35: {
         ConcurrentHashMap var1;
         ConcurrentLinkedQueue var2;
         try {
            if (a1.q.B(var0)) {
               return;
            }

            var1 = a;
            var2 = (ConcurrentLinkedQueue)var1.get(var0);
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10001 = false;
            break label35;
         }

         if (var2 != null) {
            try {
               if (!var2.isEmpty()) {
                  StringBuilder var3 = new StringBuilder("归还委托节点:");
                  var3.append(var0);
                  Log.d("com.guard.wallet.helper.d", var3.toString());
                  b var8 = new b(0);
                  var2.removeIf(var8);
                  var2.clear();
               }
            } catch (Exception var5) {
               var10000 = var5;
               boolean var9 = false;
               break label35;
            }
         }

         try {
            var1.remove(var0);
            return;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10 = false;
         }
      }

      Exception var7 = var10000;
      a1.q.s("com.guard.wallet.helper.d", var7);
   }
}
