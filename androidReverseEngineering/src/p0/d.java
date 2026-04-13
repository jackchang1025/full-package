package p0;

import java.util.concurrent.TimeUnit;

public final class d {
   public final boolean a;
   public final boolean b;
   public final int c;
   public final int d;
   public final boolean e;
   public final boolean f;
   public final boolean g;
   public final int h;
   public final int i;
   public final boolean j;
   public final boolean k;
   public final boolean l;
   public String m;

   static {
      c var3 = new c();
      var3.a = true;
      new d(var3);
      var3 = new c();
      var3.c = true;
      TimeUnit var4 = TimeUnit.SECONDS;
      int var0 = Integer.MAX_VALUE;
      long var1 = var4.toSeconds((long)Integer.MAX_VALUE);
      if (var1 <= 2147483647L) {
         var0 = (int)var1;
      }

      var3.b = var0;
      new d(var3);
   }

   public d(c var1) {
      this.a = var1.a;
      this.b = false;
      this.c = -1;
      this.d = -1;
      this.e = false;
      this.f = false;
      this.g = false;
      this.h = var1.b;
      this.i = -1;
      this.j = var1.c;
      this.k = false;
      this.l = false;
   }

   public d(
      boolean var1,
      boolean var2,
      int var3,
      int var4,
      boolean var5,
      boolean var6,
      boolean var7,
      int var8,
      int var9,
      boolean var10,
      boolean var11,
      boolean var12,
      String var13
   ) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.e = var5;
      this.f = var6;
      this.g = var7;
      this.h = var8;
      this.i = var9;
      this.j = var10;
      this.k = var11;
      this.l = var12;
      this.m = var13;
   }

   public static d a(s var0) {
      int var13 = var0.a.length / 2;
      int var7 = 0;
      boolean var2 = true;
      String var30 = null;
      boolean var21 = false;
      boolean var20 = false;
      int var6 = -1;
      int var5 = -1;
      boolean var19 = false;
      boolean var18 = false;
      boolean var17 = false;
      int var4 = -1;
      int var3 = -1;
      boolean var16 = false;
      boolean var14 = false;
      boolean var15 = false;

      while (var7 < var13) {
         int var1;
         int var8;
         int var9;
         int var10;
         int var12;
         boolean var22;
         boolean var23;
         boolean var24;
         boolean var25;
         boolean var26;
         boolean var27;
         boolean var28;
         boolean var29;
         String var31;
         label107: {
            String var32;
            label100: {
               String var33 = var0.d(var7);
               var32 = var0.f(var7);
               if (var33.equalsIgnoreCase("Cache-Control")) {
                  if (var30 == null) {
                     var30 = var32;
                     break label100;
                  }
               } else {
                  var12 = var2;
                  var31 = var30;
                  var23 = var21;
                  var27 = var20;
                  var9 = var6;
                  var8 = var5;
                  var29 = var19;
                  var26 = var18;
                  var24 = var17;
                  var1 = var4;
                  var10 = var3;
                  var25 = var16;
                  var22 = var14;
                  var28 = var15;
                  if (!var33.equalsIgnoreCase("Pragma")) {
                     break label107;
                  }
               }

               var2 = false;
            }

            int var11 = 0;

            while (true) {
               var12 = var2;
               var31 = var30;
               var23 = var21;
               var27 = var20;
               var9 = var6;
               var8 = var5;
               var29 = var19;
               var26 = var18;
               var24 = var17;
               var1 = var4;
               var10 = var3;
               var25 = var16;
               var22 = var14;
               var28 = var15;
               if (var11 >= var32.length()) {
                  break;
               }

               String var54;
               label92: {
                  var8 = t0.e.e(var32, var11, "=,;");
                  var54 = var32.substring(var11, var8).trim();
                  if (var8 != var32.length() && var32.charAt(var8) != ',') {
                     var1 = var8;
                     if (var32.charAt(var8) != ';') {
                        while (true) {
                           var8 = var1 + 1;
                           if (var8 >= var32.length()) {
                              break;
                           }

                           char var42 = var32.charAt(var8);
                           var1 = var8;
                           if (var42 != ' ') {
                              var1 = var8;
                              if (var42 != '\t') {
                                 break;
                              }
                           }
                        }

                        if (var8 < var32.length() && var32.charAt(var8) == '"') {
                           var1 = var8 + 1;
                           var8 = t0.e.e(var32, var1, "\"");
                           var31 = var32.substring(var1, var8);
                           var1 = var8 + 1;
                           break label92;
                        }

                        var1 = t0.e.e(var32, var8, ",;");
                        var31 = var32.substring(var8, var1).trim();
                        break label92;
                     }
                  }

                  var1 = var8 + 1;
                  var31 = null;
               }

               if ("no-cache".equalsIgnoreCase(var54)) {
                  var22 = true;
                  var23 = var20;
                  var8 = var6;
                  var9 = var5;
                  var24 = var19;
                  var25 = var18;
                  var26 = var17;
                  var10 = var4;
                  var12 = var3;
                  var27 = var16;
                  var28 = var14;
               } else if ("no-store".equalsIgnoreCase(var54)) {
                  var23 = true;
                  var22 = var21;
                  var8 = var6;
                  var9 = var5;
                  var24 = var19;
                  var25 = var18;
                  var26 = var17;
                  var10 = var4;
                  var12 = var3;
                  var27 = var16;
                  var28 = var14;
               } else if ("max-age".equalsIgnoreCase(var54)) {
                  var8 = t0.e.c(-1, var31);
                  var22 = var21;
                  var23 = var20;
                  var9 = var5;
                  var24 = var19;
                  var25 = var18;
                  var26 = var17;
                  var10 = var4;
                  var12 = var3;
                  var27 = var16;
                  var28 = var14;
               } else if ("s-maxage".equalsIgnoreCase(var54)) {
                  var9 = t0.e.c(-1, var31);
                  var22 = var21;
                  var23 = var20;
                  var8 = var6;
                  var24 = var19;
                  var25 = var18;
                  var26 = var17;
                  var10 = var4;
                  var12 = var3;
                  var27 = var16;
                  var28 = var14;
               } else if ("private".equalsIgnoreCase(var54)) {
                  var24 = true;
                  var22 = var21;
                  var23 = var20;
                  var8 = var6;
                  var9 = var5;
                  var25 = var18;
                  var26 = var17;
                  var10 = var4;
                  var12 = var3;
                  var27 = var16;
                  var28 = var14;
               } else if ("public".equalsIgnoreCase(var54)) {
                  var25 = true;
                  var22 = var21;
                  var23 = var20;
                  var8 = var6;
                  var9 = var5;
                  var24 = var19;
                  var26 = var17;
                  var10 = var4;
                  var12 = var3;
                  var27 = var16;
                  var28 = var14;
               } else if ("must-revalidate".equalsIgnoreCase(var54)) {
                  var26 = true;
                  var22 = var21;
                  var23 = var20;
                  var8 = var6;
                  var9 = var5;
                  var24 = var19;
                  var25 = var18;
                  var10 = var4;
                  var12 = var3;
                  var27 = var16;
                  var28 = var14;
               } else if ("max-stale".equalsIgnoreCase(var54)) {
                  var10 = t0.e.c(Integer.MAX_VALUE, var31);
                  var22 = var21;
                  var23 = var20;
                  var8 = var6;
                  var9 = var5;
                  var24 = var19;
                  var25 = var18;
                  var26 = var17;
                  var12 = var3;
                  var27 = var16;
                  var28 = var14;
               } else if ("min-fresh".equalsIgnoreCase(var54)) {
                  var12 = t0.e.c(-1, var31);
                  var22 = var21;
                  var23 = var20;
                  var8 = var6;
                  var9 = var5;
                  var24 = var19;
                  var25 = var18;
                  var26 = var17;
                  var10 = var4;
                  var27 = var16;
                  var28 = var14;
               } else if ("only-if-cached".equalsIgnoreCase(var54)) {
                  var27 = true;
                  var22 = var21;
                  var23 = var20;
                  var8 = var6;
                  var9 = var5;
                  var24 = var19;
                  var25 = var18;
                  var26 = var17;
                  var10 = var4;
                  var12 = var3;
                  var28 = var14;
               } else if ("no-transform".equalsIgnoreCase(var54)) {
                  var28 = true;
                  var22 = var21;
                  var23 = var20;
                  var8 = var6;
                  var9 = var5;
                  var24 = var19;
                  var25 = var18;
                  var26 = var17;
                  var10 = var4;
                  var12 = var3;
                  var27 = var16;
               } else {
                  var22 = var21;
                  var23 = var20;
                  var8 = var6;
                  var9 = var5;
                  var24 = var19;
                  var25 = var18;
                  var26 = var17;
                  var10 = var4;
                  var12 = var3;
                  var27 = var16;
                  var28 = var14;
                  if ("immutable".equalsIgnoreCase(var54)) {
                     var15 = true;
                     var28 = var14;
                     var27 = var16;
                     var12 = var3;
                     var10 = var4;
                     var26 = var17;
                     var25 = var18;
                     var24 = var19;
                     var9 = var5;
                     var8 = var6;
                     var23 = var20;
                     var22 = var21;
                  }
               }

               var11 = var1;
               var21 = var22;
               var20 = var23;
               var6 = var8;
               var5 = var9;
               var19 = var24;
               var18 = var25;
               var17 = var26;
               var4 = var10;
               var3 = var12;
               var16 = var27;
               var14 = var28;
            }
         }

         var7++;
         var2 = (boolean)var12;
         var30 = var31;
         var21 = var23;
         var20 = var27;
         var6 = var9;
         var5 = var8;
         var19 = var29;
         var18 = var26;
         var17 = var24;
         var4 = var1;
         var3 = var10;
         var16 = var25;
         var14 = var22;
         var15 = var28;
      }

      String var34;
      if (!var2) {
         var34 = null;
      } else {
         var34 = var30;
      }

      return new d(var21, var20, var6, var5, var19, var18, var17, var4, var3, var16, var14, var15, var34);
   }

   @Override
   public final String toString() {
      String var2 = this.m;
      if (var2 == null) {
         StringBuilder var6 = new StringBuilder();
         if (this.a) {
            var6.append("no-cache, ");
         }

         if (this.b) {
            var6.append("no-store, ");
         }

         int var1 = this.c;
         if (var1 != -1) {
            var6.append("max-age=");
            var6.append(var1);
            var6.append(", ");
         }

         var1 = this.d;
         if (var1 != -1) {
            var6.append("s-maxage=");
            var6.append(var1);
            var6.append(", ");
         }

         if (this.e) {
            var6.append("private, ");
         }

         if (this.f) {
            var6.append("public, ");
         }

         if (this.g) {
            var6.append("must-revalidate, ");
         }

         var1 = this.h;
         if (var1 != -1) {
            var6.append("max-stale=");
            var6.append(var1);
            var6.append(", ");
         }

         var1 = this.i;
         if (var1 != -1) {
            var6.append("min-fresh=");
            var6.append(var1);
            var6.append(", ");
         }

         if (this.j) {
            var6.append("only-if-cached, ");
         }

         if (this.k) {
            var6.append("no-transform, ");
         }

         if (this.l) {
            var6.append("immutable, ");
         }

         if (var6.length() == 0) {
            var2 = "";
         } else {
            var6.delete(var6.length() - 2, var6.length());
            var2 = var6.toString();
         }

         this.m = var2;
      }

      return var2;
   }
}
