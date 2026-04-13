package p0;

import java.util.List;

public final class z extends a1.q {
   public static final x s = x.a("multipart/mixed");
   public static final x t = x.a("multipart/form-data");
   public static final byte[] u = new byte[]{58, 32};
   public static final byte[] v = new byte[]{13, 10};
   public static final byte[] w = new byte[]{45, 45};
   public final a1.h o;
   public final x p;
   public final List q;
   public long r = -1L;

   static {
      x.a("multipart/alternative");
      x.a("multipart/digest");
      x.a("multipart/parallel");
   }

   public z(a1.h var1, x var2, List var3) {
      this.o = var1;
      StringBuilder var4 = new StringBuilder();
      var4.append(var2);
      var4.append("; boundary=");
      var4.append(var1.m());
      this.p = x.a(var4.toString());
      this.q = q0.c.k(var3);
   }

   public static void W(StringBuilder var0, String var1) {
      var0.append('"');
      int var4 = var1.length();

      for (int var3 = 0; var3 < var4; var3++) {
         char var2 = var1.charAt(var3);
         String var5;
         if (var2 != '\n') {
            if (var2 != '\r') {
               if (var2 != '"') {
                  var0.append(var2);
                  continue;
               }

               var5 = "%22";
            } else {
               var5 = "%0D";
            }
         } else {
            var5 = "%0A";
         }

         var0.append(var5);
      }

      var0.append('"');
   }

   @Override
   public final void V(a1.f var1) {
      this.X(var1, false);
   }

   public final long X(a1.f var1, boolean var2) {
      Object var11;
      if (var2) {
         var1 = new a1.e();
         var11 = var1;
      } else {
         var11 = null;
      }

      List var12 = this.q;
      int var5 = var12.size();
      long var7 = 0L;
      int var3 = 0;

      while (true) {
         a1.h var16 = this.o;
         byte[] var17 = w;
         byte[] var13 = v;
         if (var3 >= var5) {
            ((a1.f)var1).p(var17);
            ((a1.f)var1).g(var16);
            ((a1.f)var1).p(var17);
            ((a1.f)var1).p(var13);
            long var18 = var7;
            if (var2) {
               var18 = var7 + ((a1.e)var11).b;
               ((a1.e)var11).x();
            }

            return var18;
         }

         y var15 = (y)var12.get(var3);
         s var14 = var15.a;
         ((a1.f)var1).p(var17);
         ((a1.f)var1).g(var16);
         ((a1.f)var1).p(var13);
         if (var14 != null) {
            int var6 = var14.a.length / 2;

            for (int var4 = 0; var4 < var6; var4++) {
               ((a1.f)var1).s(var14.d(var4)).p(u).s(var14.f(var4)).p(var13);
            }
         }

         a1.q var20 = var15.b;
         x var19 = var20.j();
         if (var19 != null) {
            ((a1.f)var1).s("Content-Type: ").s(var19.a).p(var13);
         }

         long var9 = var20.i();
         if (var9 != -1L) {
            ((a1.f)var1).s("Content-Length: ").t(var9).p(var13);
         } else if (var2) {
            ((a1.e)var11).x();
            return -1L;
         }

         ((a1.f)var1).p(var13);
         if (var2) {
            var7 += var9;
         } else {
            var20.V((a1.f)var1);
         }

         ((a1.f)var1).p(var13);
         var3++;
      }
   }

   @Override
   public final long i() {
      long var1 = this.r;
      if (var1 != -1L) {
         return var1;
      } else {
         var1 = this.X(null, true);
         this.r = var1;
         return var1;
      }
   }

   @Override
   public final x j() {
      return this.p;
   }
}
