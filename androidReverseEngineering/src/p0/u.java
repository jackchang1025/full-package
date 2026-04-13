package p0;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class u {
   public static final char[] i = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
   public final String a;
   public final String b;
   public final String c;
   public final String d;
   public final int e;
   public final List f;
   public final String g;
   public final String h;

   public u(t var1) {
      this.a = (String)var1.e;
      String var3 = (String)var1.f;
      this.b = i(var3, 0, var3.length(), false);
      var3 = (String)var1.g;
      this.c = i(var3, 0, var3.length(), false);
      this.d = (String)var1.h;
      int var2 = var1.c;
      if (var2 == -1) {
         var2 = c((String)var1.e);
      }

      this.e = var2;
      j(false, var1.b);
      List var7 = var1.d;
      Object var4 = null;
      List var8;
      if (var7 != null) {
         var8 = j(true, var7);
      } else {
         var8 = null;
      }

      this.f = var8;
      String var5 = (String)var1.i;
      var3 = (String)var4;
      if (var5 != null) {
         var3 = i(var5, 0, var5.length(), false);
      }

      this.g = var3;
      this.h = var1.toString();
   }

   public static String a(String var0, int var1, int var2, String var3, boolean var4, boolean var5, boolean var6, boolean var7) {
      int var8 = var1;

      while (var8 < var2) {
         int var9 = var0.codePointAt(var8);
         if (var9 < 32
            || var9 == 127
            || var9 >= 128 && var7
            || var3.indexOf(var9) != -1
            || var9 == 37 && (!var4 || var5 && !k(var0, var8, var2))
            || var9 == 43 && var6) {
            a1.e var13 = new a1.e();
            var13.O(var0, var1, var8);
            a1.e var10 = null;

            while (var8 < var2) {
               a1.e var12;
               label124: {
                  var9 = var0.codePointAt(var8);
                  if (var4) {
                     var12 = var10;
                     if (var9 == 9) {
                        break label124;
                     }

                     var12 = var10;
                     if (var9 == 10) {
                        break label124;
                     }

                     var12 = var10;
                     if (var9 == 12) {
                        break label124;
                     }

                     if (var9 == 13) {
                        var12 = var10;
                        break label124;
                     }
                  }

                  if (var9 == 43 && var6) {
                     String var17;
                     if (var4) {
                        var17 = "+";
                     } else {
                        var17 = "%2B";
                     }

                     var13.O(var17, 0, var17.length());
                     var12 = var10;
                  } else if (var9 < 32
                     || var9 == 127
                     || var9 >= 128 && var7
                     || var3.indexOf(var9) != -1
                     || var9 == 37 && (!var4 || var5 && !k(var0, var8, var2))) {
                     a1.e var11 = var10;
                     if (var10 == null) {
                        var11 = new a1.e();
                     }

                     var11.P(var9);

                     while (true) {
                        var12 = var11;
                        if (var11.n()) {
                           break;
                        }

                        var1 = var11.readByte() & 255;
                        var13.J(37);
                        char[] var16 = i;
                        var13.J(var16[var1 >> 4 & 15]);
                        var13.J(var16[var1 & 15]);
                     }
                  } else {
                     var13.P(var9);
                     var12 = var10;
                  }
               }

               var8 += Character.charCount(var9);
               var10 = var12;
            }

            return var13.D();
         }

         var8 += Character.charCount(var9);
      }

      return var0.substring(var1, var2);
   }

   public static String b(String var0, String var1, boolean var2, boolean var3, boolean var4, boolean var5) {
      return a(var0, 0, var0.length(), var1, var2, var3, var4, var5);
   }

   public static int c(String var0) {
      if (var0.equals("http")) {
         return 80;
      } else {
         return var0.equals("https") ? 443 : -1;
      }
   }

   public static String i(String var0, int var1, int var2, boolean var3) {
      for (int var4 = var1; var4 < var2; var4++) {
         int var5 = var0.charAt(var4);
         if (var5 == 37 || var5 == 43 && var3) {
            a1.e var8 = new a1.e();
            var8.O(var0, var1, var4);
            var1 = var4;

            while (var1 < var2) {
               label37: {
                  label36: {
                     var5 = var0.codePointAt(var1);
                     if (var5 == 37) {
                        var4 = var1 + 2;
                        if (var4 < var2) {
                           int var7 = q0.c.e(var0.charAt(var1 + 1));
                           int var6 = q0.c.e(var0.charAt(var4));
                           if (var7 != -1 && var6 != -1) {
                              var8.J((var7 << 4) + var6);
                              var1 = var4;
                              break label37;
                           }
                           break label36;
                        }
                     }

                     if (var5 == 43 && var3) {
                        var8.J(32);
                        break label37;
                     }
                  }

                  var8.P(var5);
               }

               var1 += Character.charCount(var5);
            }

            return var8.D();
         }
      }

      return var0.substring(var1, var2);
   }

   public static List j(boolean var0, List var1) {
      int var3 = var1.size();
      ArrayList var5 = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         String var4 = (String)var1.get(var2);
         if (var4 != null) {
            var4 = i(var4, 0, var4.length(), var0);
         } else {
            var4 = null;
         }

         var5.add(var4);
      }

      return Collections.unmodifiableList(var5);
   }

   public static boolean k(String var0, int var1, int var2) {
      int var3 = var1 + 2;
      if (var3 < var2 && var0.charAt(var1) == '%') {
         boolean var4 = true;
         if (q0.c.e(var0.charAt(var1 + 1)) != -1 && q0.c.e(var0.charAt(var3)) != -1) {
            return var4;
         }
      }

      return false;
   }

   public static ArrayList l(String var0) {
      ArrayList var5 = new ArrayList();
      int var1 = 0;

      while (var1 <= var0.length()) {
         int var3 = var0.indexOf(38, var1);
         int var2 = var3;
         if (var3 == -1) {
            var2 = var0.length();
         }

         var3 = var0.indexOf(61, var1);
         String var4;
         if (var3 != -1 && var3 <= var2) {
            var5.add(var0.substring(var1, var3));
            var4 = var0.substring(var3 + 1, var2);
         } else {
            var5.add(var0.substring(var1, var2));
            var4 = null;
         }

         var5.add(var4);
         var1 = var2 + 1;
      }

      return var5;
   }

   public final String d() {
      if (this.c.isEmpty()) {
         return "";
      } else {
         int var1 = this.a.length();
         String var2 = this.h;
         return var2.substring(var2.indexOf(58, var1 + 3) + 1, var2.indexOf(64));
      }
   }

   public final String e() {
      int var1 = this.a.length();
      String var2 = this.h;
      var1 = var2.indexOf(47, var1 + 3);
      return var2.substring(var1, q0.c.h(var2, var1, var2.length(), "?#"));
   }

   @Override
   public final boolean equals(Object var1) {
      boolean var2;
      if (var1 instanceof u && ((u)var1).h.equals(this.h)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final ArrayList f() {
      int var1 = this.a.length();
      String var5 = this.h;
      var1 = var5.indexOf(47, var1 + 3);
      int var2 = q0.c.h(var5, var1, var5.length(), "?#");
      ArrayList var4 = new ArrayList();

      while (var1 < var2) {
         int var3 = var1 + 1;
         var1 = q0.c.g(var5, var3, var2, '/');
         var4.add(var5.substring(var3, var1));
      }

      return var4;
   }

   public final String g() {
      if (this.f == null) {
         return null;
      } else {
         String var2 = this.h;
         int var1 = var2.indexOf(63) + 1;
         return var2.substring(var1, q0.c.g(var2, var1, var2.length(), '#'));
      }
   }

   public final String h() {
      if (this.b.isEmpty()) {
         return "";
      } else {
         int var1 = this.a.length() + 3;
         String var2 = this.h;
         return var2.substring(var1, q0.c.h(var2, var1, var2.length(), ":@"));
      }
   }

   @Override
   public final int hashCode() {
      return this.h.hashCode();
   }

   public final String m() {
      t var1;
      try {
         var1 = new t();
         var1.b(this, "/...");
      } catch (IllegalArgumentException var2) {
         var1 = null;
      }

      var1.getClass();
      var1.f = b("", " \"':;<=>@[]^`{}|/\\?#", false, false, false, true);
      var1.g = b("", " \"':;<=>@[]^`{}|/\\?#", false, false, false, true);
      return var1.a().h;
   }

   public final URI n() {
      t var6 = new t();
      String var4 = this.a;
      var6.e = var4;
      var6.f = this.h();
      var6.g = this.d();
      var6.h = this.d;
      int var2 = c(var4);
      int var1 = this.e;
      if (var1 == var2) {
         var1 = -1;
      }

      var6.c = var1;
      var6.b.clear();
      var6.b.addAll(this.f());
      var4 = this.g();
      Object var5 = null;
      ArrayList var14;
      if (var4 != null) {
         var14 = l(b(var4, " \"'<>#", true, false, true, true));
      } else {
         var14 = null;
      }

      var6.d = var14;
      if (this.g == null) {
         var4 = (String)var5;
      } else {
         var4 = this.h;
         var4 = var4.substring(var4.indexOf(35) + 1);
      }

      var6.i = var4;
      int var3 = var6.b.size();
      byte var11 = 0;

      for (int var9 = 0; var9 < var3; var9++) {
         var4 = (String)var6.b.get(var9);
         var6.b.set(var9, b(var4, "[]", true, true, false, true));
      }

      List var18 = var6.d;
      if (var18 != null) {
         var3 = var18.size();

         for (int var10 = var11; var10 < var3; var10++) {
            var4 = (String)var6.d.get(var10);
            if (var4 != null) {
               var6.d.set(var10, b(var4, "\\^`{|}", true, true, true, true));
            }
         }
      }

      var4 = (String)var6.i;
      if (var4 != null) {
         var6.i = b(var4, " \"#<>\\^`{|}", true, true, false, false);
      }

      var5 = var6.toString();

      try {
         return new URI((String)var5);
      } catch (URISyntaxException var8) {
         try {
            return URI.create(var5.replaceAll("[\\u0000-\\u001F\\u007F-\\u009F\\p{javaWhitespace}]", ""));
         } catch (Exception var7) {
            throw new RuntimeException(var8);
         }
      }
   }

   @Override
   public final String toString() {
      return this.h;
   }
}
