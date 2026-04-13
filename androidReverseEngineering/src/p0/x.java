package p0;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class x {
   public static final Pattern d = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");
   public static final Pattern e = Pattern.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");
   public final String a;
   public final String b;
   public final String c;

   public x(String var1, String var2, String var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public static x a(String var0) {
      Matcher var2 = d.matcher(var0);
      if (var2.lookingAt()) {
         String var4 = var2.group(1);
         Locale var3 = Locale.US;
         String var5 = var4.toLowerCase(var3);
         var2.group(2).toLowerCase(var3);
         Matcher var6 = e.matcher(var0);
         int var1 = var2.end();
         String var10 = null;

         while (var1 < var0.length()) {
            var6.region(var1, var0.length());
            if (!var6.lookingAt()) {
               StringBuilder var9 = new StringBuilder("Parameter is not formatted correctly: \"");
               var9.append(var0.substring(var1));
               var9.append("\" for: \"");
               var9.append(var0);
               var9.append('"');
               throw new IllegalArgumentException(var9.toString());
            }

            var4 = var6.group(1);
            String var8 = var10;
            if (var4 != null) {
               if (!var4.equalsIgnoreCase("charset")) {
                  var8 = var10;
               } else {
                  var4 = var6.group(2);
                  if (var4 != null) {
                     var8 = var4;
                     if (var4.startsWith("'")) {
                        var8 = var4;
                        if (var4.endsWith("'")) {
                           var8 = var4;
                           if (var4.length() > 2) {
                              var8 = var4.substring(1, var4.length() - 1);
                           }
                        }
                     }
                  } else {
                     var8 = var6.group(3);
                  }

                  if (var10 != null && !var8.equalsIgnoreCase(var10)) {
                     StringBuilder var13 = new StringBuilder("Multiple charsets defined: \"");
                     var13.append(var10);
                     var13.append("\" and: \"");
                     var13.append(var8);
                     var13.append("\" for: \"");
                     var13.append(var0);
                     var13.append('"');
                     throw new IllegalArgumentException(var13.toString());
                  }
               }
            }

            var1 = var6.end();
            var10 = var8;
         }

         return new x(var0, var5, var10);
      } else {
         StringBuilder var7 = new StringBuilder("No subtype found for: \"");
         var7.append(var0);
         var7.append('"');
         throw new IllegalArgumentException(var7.toString());
      }
   }

   @Override
   public final boolean equals(Object var1) {
      boolean var2;
      if (var1 instanceof x && ((x)var1).a.equals(this.a)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Override
   public final int hashCode() {
      return this.a.hashCode();
   }

   @Override
   public final String toString() {
      return this.a;
   }
}
