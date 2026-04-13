package a1;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.RandomAccess;

public final class m extends AbstractList implements RandomAccess {
   public static final int c = 0;
   public final h[] a;
   public final int[] b;

   public m(h[] var1, int[] var2) {
      this.a = var1;
      this.b = var2;
   }

   public static void a(long var0, e var2, int var3, ArrayList var4, int var5, int var6, ArrayList var7) {
      if (var5 >= var6) {
         throw new AssertionError();
      } else {
         for (int var8 = var5; var8 < var6; var8++) {
            if (((h)var4.get(var8)).j() < var3) {
               throw new AssertionError();
            }
         }

         h var12 = (h)var4.get(var5);
         h var13 = (h)var4.get(var6 - 1);
         int var17;
         if (var3 == var12.j()) {
            var17 = (Integer)var7.get(var5);
            var12 = (h)var4.get(++var5);
         } else {
            var17 = -1;
         }

         if (var12.e(var3) != var13.e(var3)) {
            int var10 = var5 + 1;
            int var11 = 1;

            while (var10 < var6) {
               int var9 = var11;
               if (((h)var4.get(var10 - 1)).e(var3) != ((h)var4.get(var10)).e(var3)) {
                  var9 = var11 + 1;
               }

               var10++;
               var11 = var9;
            }

            var0 = var0 + (long)((int)(var2.b / 4L)) + 2L + (long)(var11 * 2);
            var2.M(var11);
            var2.M(var17);

            for (int var18 = var5; var18 < var6; var18++) {
               byte var21 = ((h)var4.get(var18)).e(var3);
               if (var18 == var5 || var21 != ((h)var4.get(var18 - 1)).e(var3)) {
                  var2.M(var21 & 255);
               }
            }

            e var28 = new e();
            var17 = var5;

            while (var17 < var6) {
               byte var24 = ((h)var4.get(var17)).e(var3);
               int var22 = var17 + 1;
               var5 = var22;

               while (true) {
                  if (var5 >= var6) {
                     var5 = var6;
                     break;
                  }

                  if (var24 != ((h)var4.get(var5)).e(var3)) {
                     break;
                  }

                  var5++;
               }

               if (var22 == var5 && var3 + 1 == ((h)var4.get(var17)).j()) {
                  var2.M((Integer)var7.get(var17));
               } else {
                  var2.M((int)(((long)((int)(var28.b / 4L)) + var0) * -1L));
                  a(var0, var28, var3 + 1, var4, var17, var5, var7);
               }

               var17 = var5;
            }

            var2.i(var28, var28.b);
         } else {
            int var27 = Math.min(var12.j(), var13.j());
            int var23 = 0;

            for (int var25 = var3; var25 < var27 && var12.e(var25) == var13.e(var25); var25++) {
               var23++;
            }

            var0 = 1L + var0 + (long)((int)(var2.b / 4L)) + 2L + (long)var23;
            var2.M(-var23);
            var2.M(var17);
            var17 = var3;

            while (true) {
               int var26 = var3 + var23;
               if (var17 >= var26) {
                  if (var5 + 1 == var6) {
                     if (var26 != ((h)var4.get(var5)).j()) {
                        throw new AssertionError();
                     }

                     var2.M((Integer)var7.get(var5));
                  } else {
                     e var29 = new e();
                     var2.M((int)(((long)((int)(var29.b / 4L)) + var0) * -1L));
                     a(var0, var29, var26, var4, var5, var6, var7);
                     var2.i(var29, var29.b);
                  }
                  break;
               }

               var2.M(var12.e(var17) & 255);
               var17++;
            }
         }
      }
   }

   @Override
   public final Object get(int var1) {
      return this.a[var1];
   }

   @Override
   public final int size() {
      return this.a.length;
   }
}
