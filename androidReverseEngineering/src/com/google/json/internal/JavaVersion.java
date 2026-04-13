package com.google.json.internal;

public final class JavaVersion {
   private static final int majorJavaVersion = determineMajorJavaVersion();

   private JavaVersion() {
   }

   private static int determineMajorJavaVersion() {
      return getMajorJavaVersion(System.getProperty("java.version"));
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   private static int extractBeginningInt(String var0) {
      StringBuilder var3;
      try {
         var3 = new StringBuilder();
      } catch (NumberFormatException var6) {
         boolean var10001 = false;
         return -1;
      }

      int var2 = 0;

      while (true) {
         try {
            if (var2 >= var0.length()) {
               break;
            }

            char var1 = var0.charAt(var2);
            if (!Character.isDigit(var1)) {
               break;
            }

            var3.append(var1);
         } catch (NumberFormatException var5) {
            boolean var7 = false;
            return -1;
         }

         var2++;
      }

      try {
         return Integer.parseInt(var3.toString());
      } catch (NumberFormatException var4) {
         boolean var8 = false;
         return -1;
      }
   }

   public static int getMajorJavaVersion() {
      return majorJavaVersion;
   }

   public static int getMajorJavaVersion(String var0) {
      int var2 = parseDotted(var0);
      int var1 = var2;
      if (var2 == -1) {
         var1 = extractBeginningInt(var0);
      }

      return var1 == -1 ? 6 : var1;
   }

   public static boolean isJava9OrLater() {
      boolean var0;
      if (majorJavaVersion >= 9) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   private static int parseDotted(String var0) {
      int var1;
      try {
         var0 = var0.split("[._]");
         var1 = Integer.parseInt(var0[0]);
      } catch (NumberFormatException var3) {
         boolean var10001 = false;
         return -1;
      }

      if (var1 != 1) {
         return var1;
      } else {
         try {
            return var0.length > 1 ? Integer.parseInt(var0[1]) : var1;
         } catch (NumberFormatException var2) {
            boolean var5 = false;
            return -1;
         }
      }
   }
}
