package com.guard.wallet.http;

import com.google.json.JsonObject;
import java.util.concurrent.Callable;

public final class k implements Callable {
   public final int a;
   public final Object b;
   public final String c;
   public final String d;

   public final JsonObject a() {
      int var1 = this.a;
      String var3 = this.d;
      l0.m var4 = (l0.m)this.b;
      String var2 = this.c;
      switch (var1) {
         case 0:
            i var6 = new i(var2);
            var3 = var6.e(var4, var3);
            var4 = new l0.m();
            var4.d(var3);
            var4.b("GET", null);
            return var6.b(var4.a());
         default:
            i var5 = new i(var2);
            return var5.b(var5.i(var4, var3));
      }
   }
}
