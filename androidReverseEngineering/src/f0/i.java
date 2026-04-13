package f0;

import java.util.Comparator;

public final class i implements Comparator {
   public static final i a = new i();

   @Override
   public final int compare(Object var1, Object var2) {
      var1 = var1;
      var2 = var2;
      long var7;
      int var3 = (var7 = var1.c - var2.c) == 0L ? 0 : (var7 < 0L ? -1 : 1);
      byte var6;
      if (var3 == 0) {
         var6 = 0;
      } else if (var3 > 0) {
         var6 = 1;
      } else {
         var6 = -1;
      }

      return var6;
   }
}
