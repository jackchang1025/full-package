package a1;

import java.nio.charset.Charset;
import java.util.Arrays;

public final class r extends h {
   public final transient byte[][] f;
   public final transient int[] g;

   public r(e var1, int var2) {
      super(null);
      w.a(var1.b, 0L, (long)var2);
      p var8 = var1.a;
      int var5 = 0;
      int var4 = 0;

      int var3;
      for (var3 = 0; var4 < var2; var8 = var8.f) {
         int var6 = var8.c;
         int var7 = var8.b;
         if (var6 == var7) {
            throw new AssertionError("s.limit == s.pos");
         }

         var4 += var6 - var7;
         var3++;
      }

      this.f = new byte[var3][];
      this.g = new int[var3 * 2];
      p var10 = var1.a;
      var4 = 0;

      for (int var11 = var5; var11 < var2; var10 = var10.f) {
         byte[][] var15 = this.f;
         var15[var4] = var10.a;
         int var14 = var10.c;
         var5 = var10.b;
         var11 += var14 - var5;
         if (var11 > var2) {
            var11 = var2;
         }

         int[] var9 = this.g;
         var9[var4] = var11;
         var9[var15.length + var4] = var5;
         var10.d = true;
         var4++;
      }
   }

   @Override
   public final String a() {
      return this.p().a();
   }

   @Override
   public final byte e(int var1) {
      byte[][] var6 = this.f;
      int var2 = var6.length;
      int[] var5 = this.g;
      w.a((long)var5[var2 - 1], (long)var1, 1L);
      int var3 = this.o(var1);
      if (var3 == 0) {
         var2 = 0;
      } else {
         var2 = var5[var3 - 1];
      }

      int var4 = var5[var6.length + var3];
      return var6[var3][var1 - var2 + var4];
   }

   @Override
   public final boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else {
         if (var1 instanceof h) {
            var1 = var1;
            if (var1.j() == this.j() && this.i(var1, this.j())) {
               return var2;
            }
         }

         return false;
      }
   }

   @Override
   public final String f() {
      return this.p().f();
   }

   @Override
   public final boolean h(int var1, int var2, byte[] var3, int var4) {
      if (var1 >= 0 && var1 <= this.j() - var4 && var2 >= 0 && var2 <= var3.length - var4) {
         int var6 = this.o(var1);
         int var5 = var1;
         var1 = var6;

         while (true) {
            boolean var9 = true;
            if (var4 <= 0) {
               return true;
            }

            int[] var12 = this.g;
            if (var1 == 0) {
               var6 = 0;
            } else {
               var6 = var12[var1 - 1];
            }

            int var10 = Math.min(var4, var12[var1] - var6 + var6 - var5);
            byte[][] var13 = this.f;
            int var11 = var12[var13.length + var1];
            byte[] var16 = var13[var1];
            Charset var17 = w.a;
            int var7 = 0;

            boolean var8;
            while (true) {
               var8 = var9;
               if (var7 >= var10) {
                  break;
               }

               if (var16[var7 + var5 - var6 + var11] != var3[var7 + var2]) {
                  var8 = false;
                  break;
               }

               var7++;
            }

            if (!var8) {
               return false;
            }

            var5 += var10;
            var2 += var10;
            var4 -= var10;
            var1++;
         }
      } else {
         return false;
      }
   }

   @Override
   public final int hashCode() {
      int var1 = super.b;
      if (var1 != 0) {
         return var1;
      } else {
         byte[][] var10 = this.f;
         int var7 = var10.length;
         int var3 = 0;
         int var4 = 1;
         int var2 = 0;

         while (var3 < var7) {
            byte[] var9 = var10[var3];
            int[] var8 = this.g;
            int var6 = var8[var7 + var3];
            int var5 = var8[var3];

            for (int var11 = var6; var11 < var5 - var2 + var6; var11++) {
               var4 = var4 * 31 + var9[var11];
            }

            var3++;
            var2 = var5;
         }

         super.b = var4;
         return var4;
      }
   }

   @Override
   public final boolean i(h var1, int var2) {
      if (this.j() - var2 < 0) {
         return false;
      } else {
         int var4 = this.o(0);
         int var5 = 0;

         for (int var3 = 0; var2 > 0; var4++) {
            int[] var9 = this.g;
            int var6;
            if (var4 == 0) {
               var6 = 0;
            } else {
               var6 = var9[var4 - 1];
            }

            int var7 = Math.min(var2, var9[var4] - var6 + var6 - var5);
            byte[][] var8 = this.f;
            if (!var1.h(var3, var5 - var6 + var9[var8.length + var4], var8[var4], var7)) {
               return false;
            }

            var5 += var7;
            var3 += var7;
            var2 -= var7;
         }

         return true;
      }
   }

   @Override
   public final int j() {
      int var1 = this.f.length;
      return this.g[var1 - 1];
   }

   @Override
   public final h k() {
      return this.p().k();
   }

   @Override
   public final h l() {
      return this.p().l();
   }

   @Override
   public final String m() {
      return this.p().m();
   }

   @Override
   public final void n(e var1) {
      byte[][] var7 = this.f;
      int var5 = var7.length;
      int var3 = 0;
      int var2 = 0;

      while (var3 < var5) {
         int[] var8 = this.g;
         int var6 = var8[var5 + var3];
         int var4 = var8[var3];
         p var10 = new p(var7[var3], var6, var6 + var4 - var2);
         p var9 = var1.a;
         if (var9 == null) {
            var10.g = var10;
            var10.f = var10;
            var1.a = var10;
         } else {
            var9.g.b(var10);
         }

         var3++;
         var2 = var4;
      }

      var1.b += (long)var2;
   }

   public final int o(int var1) {
      int var2 = this.f.length;
      var1 = Arrays.binarySearch(this.g, 0, var2, var1 + 1);
      if (var1 < 0) {
         var1 = ~var1;
      }

      return var1;
   }

   public final h p() {
      byte[][] var8 = this.f;
      int var1 = var8.length;
      int[] var7 = this.g;
      byte[] var6 = new byte[var7[var1 - 1]];
      int var4 = var8.length;
      int var2 = 0;
      var1 = 0;

      while (var2 < var4) {
         int var5 = var7[var4 + var2];
         int var3 = var7[var2];
         System.arraycopy(var8[var2], var5, var6, var1, var3 - var1);
         var2++;
         var1 = var3;
      }

      return new h(var6);
   }

   @Override
   public final String toString() {
      return this.p().toString();
   }
}
