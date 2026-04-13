package p0;

import java.util.Comparator;

// $VF: synthetic class
public final class h implements Comparator {
   public final int a;

   @Override
   public final int compare(Object var1, Object var2) {
      switch (this.a) {
         case 0:
            var1 = var1;
            var2 = var2;
            int var4 = Math.min(var1.length(), var2.length());
            int var3 = 4;

            while (true) {
               if (var3 < var4) {
                  char var5 = var1.charAt(var3);
                  char var6 = var2.charAt(var3);
                  if (var5 == var6) {
                     var3++;
                     continue;
                  }

                  if (var5 < var6) {
                     break;
                  }
               } else {
                  var4 = var1.length();
                  var3 = var2.length();
                  if (var4 == var3) {
                     return 0;
                  }

                  if (var4 < var3) {
                     break;
                  }
               }

               return 1;
            }

            return -1;
         default:
            return var1.compareTo(var2);
      }
   }
}
