package com.guard.wallet.utils;

import a1.q;
import com.google.json.Gson;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class f {
   public static final ConcurrentHashMap a = new ConcurrentHashMap();
   public static final AtomicBoolean b = new AtomicBoolean(false);

   public static void a() {
      ConcurrentHashMap var0 = a;
      if (var0.keySet().isEmpty()) {
         String var1 = g.i0();
         if (!q.B(var1)) {
            var1 = var1.concat("/").concat("locateValues.json");
            if (q.w(var1)) {
               var1 = q.K(var1);
               if (!q.B(var1)) {
                  Type var2 = new LocateValuesUtils$1().getType();
                  HashMap var5 = new Gson().fromJson(var1, var2);
                  if (var5 != null && !var5.keySet().isEmpty()) {
                     var0.putAll(var5);
                  }
               }
            }
         }
      }
   }

   public static String b(String var0) {
      if (!q.B(var0)) {
         try {
            ConcurrentHashMap var1 = a;
            if (var1.keySet().isEmpty()) {
               a();
            }

            return (String)var1.get(var0);
         } catch (Exception var2) {
            q.s("LocateValuesUtils", var2);
         }
      }

      return "";
   }
}
