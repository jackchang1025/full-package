package v0;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public final class d {
   public final ArrayList a = new ArrayList();
   public final a1.o b;
   public final int c;
   public int d;
   public c[] e = new c[8];
   public int f = 7;
   public int g = 0;
   public int h = 0;

   public d(u var1) {
      this.c = 4096;
      this.d = 4096;
      Logger var2 = a1.l.a;
      this.b = new a1.o(var1);
   }

   public final int a(int var1) {
      int var2 = 0;
      int var4 = 0;
      if (var1 > 0) {
         var2 = this.e.length - 1;
         int var3 = var1;
         var1 = var4;

         while (true) {
            var4 = this.f;
            if (var2 < var4 || var3 <= 0) {
               c[] var5 = this.e;
               System.arraycopy(var5, var4 + 1, var5, var4 + 1 + var1, this.g);
               this.f += var1;
               var2 = var1;
               break;
            }

            var4 = this.e[var2].c;
            var3 -= var4;
            this.h -= var4;
            this.g--;
            var1++;
            var2--;
         }
      }

      return var2;
   }

   public final a1.h b(int var1) {
      boolean var2;
      if (var1 >= 0 && var1 <= v0.f.a.length - 1) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      if (var2) {
         c var8 = v0.f.a[var1];
         return var8.a;
      } else {
         var2 = v0.f.a.length;
         var2 = this.f + 1 + (var1 - var2);
         if (var2 >= 0) {
            c[] var3 = this.e;
            if (var2 < var3.length) {
               c var7 = var3[var2];
               return var7.a;
            }
         }

         StringBuilder var6 = new StringBuilder("Header index too large ");
         var6.append(var1 + 1);
         throw new IOException(var6.toString());
      }
   }

   public final void c(c var1) {
      this.a.add(var1);
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

   public final a1.h d() {
      a1.o var8 = this.b;
      int var3 = var8.readByte() & 255;
      int var2 = 0;
      boolean var1;
      if ((var3 & 128) == 128) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      var3 = this.e(var3, 127);
      if (!var1) {
         return var8.h((long)var3);
      } else {
         b0 var7 = b0.d;
         long var5 = (long)var3;
         var8.r(var5);
         byte[] var11 = var8.a.B(var5);
         var7.getClass();
         ByteArrayOutputStream var10 = new ByteArrayOutputStream();
         a0 var17 = var7.a;
         a0 var18 = var17;
         var3 = 0;
         var1 = 0;

         while (true) {
            int var4 = var1;
            a0 var9 = var18;
            if (var2 >= var11.length) {
               while (var4 > 0) {
                  a0 var19 = ((a0[])var9.c)[var3 << 8 - var4 & 0xFF];
                  if ((a0[])var19.c != null) {
                     break;
                  }

                  var1 = var19.b;
                  if (var1 > var4) {
                     break;
                  }

                  var10.write(var19.a);
                  var4 -= var1;
                  var9 = var17;
               }

               return a1.h.g(var10.toByteArray());
            }

            var3 = var3 << 8 | var11[var2] & 255;
            var1 += 8;

            while (var1 >= 8) {
               var4 = var1 - 8;
               var18 = ((a0[])var18.c)[var3 >>> var4 & 0xFF];
               if ((a0[])var18.c == null) {
                  var10.write(var18.a);
                  var1 -= var18.b;
                  var18 = var17;
               } else {
                  var1 = var4;
               }
            }

            var2++;
         }
      }
   }

   public final int e(int var1, int var2) {
      var1 &= var2;
      if (var1 < var2) {
         return var1;
      } else {
         byte var5 = 0;

         while (true) {
            int var3 = this.b.readByte() & 255;
            if ((var3 & 128) == 0) {
               return var2 + (var3 << var5);
            }

            var2 += (var3 & 127) << var5;
            var5 += 7;
         }
      }
   }
}
