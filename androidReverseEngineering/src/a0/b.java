package a0;

import com.guard.wallet.service.AccessibilityDelegateManager;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Predicate;

public final class b implements Predicate {
   public final int a;
   public final String b;
   public final AccessibilityDelegateManager c;

   @Override
   public final boolean test(Object var1) {
      int var2 = this.a;
      boolean var5 = true;
      boolean var4 = true;
      String var6 = this.b;
      switch (var2) {
         case 0:
            o.e var14 = (o.e)var1;
            if (var14 != null && Objects.equals(var14.c, var6)) {
               var14.d();
               var1 = new LinkedList(var14.d);
               var6 = var14.getClass().getName();
               this.c.C(var6, var1);
            } else {
               var4 = false;
            }

            return var4;
         default:
            String var7 = (String)var1;
            String[] var8 = var6.split(":");
            String[] var12 = var7.split(":");
            if (var8.length >= 2 && var12.length >= 2) {
               boolean var3;
               if (!"NULL".equals(var8[0]) && !"NULL".equals(var12[0]) && !Objects.equals(var8[0], var12[0])) {
                  var3 = false;
               } else {
                  var3 = true;
               }

               boolean var10;
               if (!"NULL".equals(var8[1]) && !"NULL".equals(var12[1]) && !Objects.equals(var8[1], var12[1])) {
                  var10 = false;
               } else {
                  var10 = true;
               }

               if ("android.inputmethodservice.SoftInputWindow".equals(var8[1])) {
                  var10 = true;
               }

               if (var3 && var10) {
                  return var5;
               }
            }

            return false;
      }
   }
}
