package i;

import a1.q;

public final class a implements b {
   public final int a;
   public final String b;
   public final boolean c;

   public final int a(String var1) {
      int var6 = this.a;
      byte var4 = 1;
      byte var3 = 1;
      boolean var7 = this.c;
      byte var5 = 0;
      byte var2 = 0;
      String var8 = this.b;
      switch (var6) {
         case 0:
            if (!q.B(var1)) {
               if (var1.contains(var8)) {
                  if (var7) {
                     var2 = var3;
                  } else {
                     var2 = 2;
                  }
               }
            } else {
               var2 = -1;
            }

            return var2;
         default:
            if (!q.B(var1)) {
               var2 = var5;
               if (var1.endsWith(var8)) {
                  if (var7) {
                     var2 = var4;
                  } else {
                     var2 = 2;
                  }
               }
            } else {
               var2 = -1;
            }

            return var2;
      }
   }
}
