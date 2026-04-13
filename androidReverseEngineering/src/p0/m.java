package p0;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class m {
   public static final Pattern j = Pattern.compile("(\\d{2,4})[^\\d]*");
   public static final Pattern k = Pattern.compile("(?i)(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec).*");
   public static final Pattern l = Pattern.compile("(\\d{1,2})[^\\d]*");
   public static final Pattern m = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})[^\\d]*");
   public final String a;
   public final String b;
   public final long c;
   public final String d;
   public final String e;
   public final boolean f;
   public final boolean g;
   public final boolean h;
   public final boolean i;

   public m(String var1, String var2, long var3, String var5, String var6, boolean var7, boolean var8, boolean var9, boolean var10) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var5;
      this.e = var6;
      this.f = var7;
      this.g = var8;
      this.i = var9;
      this.h = var10;
   }

   public m(l var1) {
      String var3 = var1.a;
      if (var3 != null) {
         String var4 = var1.b;
         if (var4 != null) {
            String var2 = var1.d;
            if (var2 != null) {
               this.a = var3;
               this.b = var4;
               this.c = var1.c;
               this.d = var2;
               this.e = var1.e;
               this.f = var1.f;
               this.g = var1.g;
               this.h = var1.h;
               this.i = var1.i;
            } else {
               throw new NullPointerException("builder.domain == null");
            }
         } else {
            throw new NullPointerException("builder.value == null");
         }
      } else {
         throw new NullPointerException("builder.name == null");
      }
   }

   public static int a(String var0, int var1, int var2, boolean var3) {
      while (var1 < var2) {
         char var4 = var0.charAt(var1);
         boolean var5;
         if ((var4 >= ' ' || var4 == '\t')
            && var4 < 127
            && (var4 < '0' || var4 > '9')
            && (var4 < 'a' || var4 > 'z')
            && (var4 < 'A' || var4 > 'Z')
            && var4 != ':') {
            var5 = false;
         } else {
            var5 = true;
         }

         if (var5 == (var3 ^ true)) {
            return var1;
         }

         var1++;
      }

      return var2;
   }

   public static long b(String var0, int var1) {
      int var14 = a(var0, 0, var1, false);
      Pattern var19 = m;
      Matcher var17 = var19.matcher(var0);
      int var8 = -1;
      int var7 = -1;
      int var3 = var7;
      int var5 = var7;
      int var6 = var7;
      int var4 = var7;
      int var2 = var8;

      while (var14 < var1) {
         int var15 = a(var0, var14 + 1, var1, true);
         var17.region(var14, var15);
         int var9;
         int var10;
         int var11;
         int var12;
         int var13;
         if (var7 == -1 && var17.usePattern(var19).matches()) {
            var9 = Integer.parseInt(var17.group(1));
            var12 = Integer.parseInt(var17.group(2));
            var13 = Integer.parseInt(var17.group(3));
            var8 = var2;
            var10 = var4;
            var11 = var3;
         } else if (var4 == -1 && var17.usePattern(l).matches()) {
            var10 = Integer.parseInt(var17.group(1));
            var8 = var2;
            var9 = var7;
            var11 = var3;
            var12 = var6;
            var13 = var5;
         } else {
            label88: {
               if (var3 == -1) {
                  Pattern var16 = k;
                  if (var17.usePattern(var16).matches()) {
                     String var18 = var17.group(1).toLowerCase(Locale.US);
                     var11 = var16.pattern().indexOf(var18) / 4;
                     var8 = var2;
                     var9 = var7;
                     var10 = var4;
                     var12 = var6;
                     var13 = var5;
                     break label88;
                  }
               }

               var8 = var2;
               var9 = var7;
               var10 = var4;
               var11 = var3;
               var12 = var6;
               var13 = var5;
               if (var2 == -1) {
                  var8 = var2;
                  var9 = var7;
                  var10 = var4;
                  var11 = var3;
                  var12 = var6;
                  var13 = var5;
                  if (var17.usePattern(j).matches()) {
                     var8 = Integer.parseInt(var17.group(1));
                     var13 = var5;
                     var12 = var6;
                     var11 = var3;
                     var10 = var4;
                     var9 = var7;
                  }
               }
            }
         }

         var14 = a(var0, var15 + 1, var1, false);
         var2 = var8;
         var7 = var9;
         var4 = var10;
         var3 = var11;
         var6 = var12;
         var5 = var13;
      }

      var1 = var2;
      if (var2 >= 70) {
         var1 = var2;
         if (var2 <= 99) {
            var1 = var2 + 1900;
         }
      }

      var2 = var1;
      if (var1 >= 0) {
         var2 = var1;
         if (var1 <= 69) {
            var2 = var1 + 2000;
         }
      }

      if (var2 < 1601) {
         throw new IllegalArgumentException();
      } else if (var3 == -1) {
         throw new IllegalArgumentException();
      } else if (var4 < 1 || var4 > 31) {
         throw new IllegalArgumentException();
      } else if (var7 < 0 || var7 > 23) {
         throw new IllegalArgumentException();
      } else if (var6 < 0 || var6 > 59) {
         throw new IllegalArgumentException();
      } else if (var5 >= 0 && var5 <= 59) {
         GregorianCalendar var20 = new GregorianCalendar(q0.c.h);
         var20.setLenient(false);
         var20.set(1, var2);
         var20.set(2, var3 - 1);
         var20.set(5, var4);
         var20.set(11, var7);
         var20.set(12, var6);
         var20.set(13, var5);
         var20.set(14, 0);
         return var20.getTimeInMillis();
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public final boolean equals(Object var1) {
      boolean var2 = var1 instanceof m;
      boolean var3 = false;
      if (!var2) {
         return false;
      } else {
         var1 = var1;
         var2 = var3;
         if (var1.a.equals(this.a)) {
            var2 = var3;
            if (var1.b.equals(this.b)) {
               var2 = var3;
               if (var1.d.equals(this.d)) {
                  var2 = var3;
                  if (var1.e.equals(this.e)) {
                     var2 = var3;
                     if (var1.c == this.c) {
                        var2 = var3;
                        if (var1.f == this.f) {
                           var2 = var3;
                           if (var1.g == this.g) {
                              var2 = var3;
                              if (var1.h == this.h) {
                                 var2 = var3;
                                 if (var1.i == this.i) {
                                    var2 = true;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         return var2;
      }
   }

   @Override
   public final int hashCode() {
      int var4 = this.a.hashCode();
      int var2 = this.b.hashCode();
      int var3 = this.d.hashCode();
      int var1 = this.e.hashCode();
      long var5 = this.c;
      return (
               ((((var1 + (var3 + (var2 + (var4 + 527) * 31) * 31) * 31) * 31 + (int)(var5 ^ var5 >>> 32)) * 31 + (this.f ^ 1)) * 31 + (this.g ^ 1)) * 31
                  + (this.h ^ 1)
            )
            * 31
         + (this.i ^ 1);
   }

   @Override
   public final String toString() {
      StringBuilder var4 = new StringBuilder();
      var4.append(this.a);
      var4.append('=');
      var4.append(this.b);
      if (this.h) {
         long var1 = this.c;
         String var3;
         if (var1 == Long.MIN_VALUE) {
            var3 = "; max-age=0";
         } else {
            var4.append("; expires=");
            Date var5 = new Date(var1);
            var3 = ((DateFormat)t0.d.a.get()).format(var5);
         }

         var4.append(var3);
      }

      if (!this.i) {
         var4.append("; domain=");
         var4.append(this.d);
      }

      var4.append("; path=");
      var4.append(this.e);
      if (this.f) {
         var4.append("; secure");
      }

      if (this.g) {
         var4.append("; httponly");
      }

      return var4.toString();
   }
}
