package v0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public final class e {
   public final a1.e a;
   public int b = Integer.MAX_VALUE;
   public boolean c;
   public int d;
   public c[] e = new c[8];
   public int f = 7;
   public int g = 0;
   public int h = 0;

   public e(a1.e var1) {
      this.d = 4096;
      this.a = var1;
   }

   public final void a(int var1) {
      if (var1 > 0) {
         int var3 = this.e.length - 1;
         int var4 = 0;
         int var2 = var1;
         var1 = var4;

         while (true) {
            var4 = this.f;
            if (var3 < var4 || var2 <= 0) {
               c[] var5 = this.e;
               var2 = var4 + 1;
               System.arraycopy(var5, var2, var5, var2 + var1, this.g);
               var5 = this.e;
               var2 = this.f + 1;
               Arrays.fill(var5, var2, var2 + var1, null);
               this.f += var1;
               break;
            }

            var4 = this.e[var3].c;
            var2 -= var4;
            this.h -= var4;
            this.g--;
            var1++;
            var3--;
         }
      }
   }

   public final void b(c var1) {
      int var3 = this.d;
      int var2 = var1.c;
      if (var2 > var3) {
         Arrays.fill(this.e, null);
         this.f = this.e.length - 1;
         this.g = 0;
         this.h = 0;
      } else {
         this.a(this.h + var2 - var3);
         var3 = this.g;
         c[] var4 = this.e;
         if (var3 + 1 > var4.length) {
            c[] var5 = new c[var4.length * 2];
            System.arraycopy(var4, 0, var5, var4.length, var4.length);
            this.f = this.e.length - 1;
            this.e = var5;
         }

         var3 = this.f--;
         this.e[var3] = var1;
         this.g++;
         this.h += var2;
      }
   }

   public final void c(a1.h var1) {
      long var8 = 0L;
      int var3 = 0;
      long var6 = 0L;

      for (int var2 = 0; var2 < var1.j(); var2++) {
         byte var4 = var1.e(var2);
         var6 += (long)b0.c[var4 & 255];
      }

      if ((int)(var6 + 7L >> 3) < var1.j()) {
         a1.e var10 = new a1.e();
         byte var11 = 0;

         for (var6 = var8; var3 < var1.j(); var3++) {
            int var5 = var1.e(var3) & 255;
            int var12 = b0.b[var5];
            byte var13 = b0.c[var5];
            var6 = var6 << var13 | (long)var12;
            var11 += var13;

            while (var11 >= 8) {
               var11 -= 8;
               var10.J((int)(var6 >> var11));
            }
         }

         if (var11 > 0) {
            var10.J((int)(var6 << 8 - var11 | (long)(255 >>> var11)));
         }

         byte[] var15 = var10.m();
         var1 = new a1.h(var15);
         this.e(var15.length, 127, 128);
      } else {
         this.e(var1.j(), 127, 0);
      }

      this.a.H(var1);
   }

   public final void d(ArrayList var1) {
      if (this.c) {
         int var2 = this.b;
         if (var2 < this.d) {
            this.e(var2, 31, 32);
         }

         this.c = false;
         this.b = Integer.MAX_VALUE;
         this.e(this.d, 31, 32);
      }

      int var8 = var1.size();

      for (int var4 = 0; var4 < var8; var4++) {
         int var3;
         c var10;
         a1.h var11;
         a1.h var12;
         int var14;
         label57: {
            label56: {
               var10 = (c)var1.get(var4);
               var11 = var10.a.l();
               Integer var13 = (Integer)v0.f.b.get(var11);
               var12 = var10.b;
               if (var13 != null) {
                  var3 = var13 + 1;
                  if (var3 <= 1 || var3 >= 8) {
                     break label56;
                  }

                  c[] var17 = v0.f.a;
                  if (!Objects.equals(var17[var3 - 1].b, var12)) {
                     if (Objects.equals(var17[var3].b, var12)) {
                        var14 = var3 + 1;
                        break label57;
                     }
                     break label56;
                  }
               } else {
                  var3 = -1;
               }

               var14 = var3;
               break label57;
            }

            var14 = -1;
         }

         int var6 = var3;
         int var7 = var14;
         if (var14 == -1) {
            int var5 = this.f + 1;
            int var9 = this.e.length;

            while (true) {
               var6 = var3;
               var7 = var14;
               if (var5 >= var9) {
                  break;
               }

               var6 = var3;
               if (Objects.equals(this.e[var5].a, var11)) {
                  if (Objects.equals(this.e[var5].b, var12)) {
                     var14 = this.f;
                     var7 = v0.f.a.length + (var5 - var14);
                     var6 = var3;
                     break;
                  }

                  var6 = var3;
                  if (var3 == -1) {
                     var6 = var5 - this.f + v0.f.a.length;
                  }
               }

               var5++;
               var3 = var6;
            }
         }

         if (var7 != -1) {
            this.e(var7, 127, 128);
         } else {
            if (var6 == -1) {
               this.a.J(64);
               this.c(var11);
            } else {
               a1.h var18 = v0.c.d;
               var11.getClass();
               if (var11.i(var18, var18.j()) && !v0.c.i.equals(var11)) {
                  this.e(var6, 15, 0);
                  this.c(var12);
                  continue;
               }

               this.e(var6, 63, 64);
            }

            this.c(var12);
            this.b(var10);
         }
      }
   }

   public final void e(int var1, int var2, int var3) {
      a1.e var4 = this.a;
      if (var1 < var2) {
         var4.J(var1 | var3);
      } else {
         var4.J(var3 | var2);

         for (var1 -= var2; var1 >= 128; var1 >>>= 7) {
            var4.J(128 | var1 & 127);
         }

         var4.J(var1);
      }
   }
}
