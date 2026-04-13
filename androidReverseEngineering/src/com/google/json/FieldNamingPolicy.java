package com.google.json;

import java.lang.reflect.Field;
import java.util.Locale;

public enum FieldNamingPolicy implements FieldNamingStrategy {
   IDENTITY {
      @Override
      public String translateName(Field var1) {
         return var1.getName();
      }
   },
   LOWER_CASE_WITH_DASHES {
      @Override
      public String translateName(Field var1) {
         return FieldNamingPolicy.separateCamelCase(var1.getName(), '-').toLowerCase(Locale.ENGLISH);
      }
   },
   LOWER_CASE_WITH_DOTS {
      @Override
      public String translateName(Field var1) {
         return FieldNamingPolicy.separateCamelCase(var1.getName(), '.').toLowerCase(Locale.ENGLISH);
      }
   },
   LOWER_CASE_WITH_UNDERSCORES {
      @Override
      public String translateName(Field var1) {
         return FieldNamingPolicy.separateCamelCase(var1.getName(), '_').toLowerCase(Locale.ENGLISH);
      }
   },
   UPPER_CAMEL_CASE {
      @Override
      public String translateName(Field var1) {
         return FieldNamingPolicy.upperCaseFirstLetter(var1.getName());
      }
   },
   UPPER_CAMEL_CASE_WITH_SPACES {
      @Override
      public String translateName(Field var1) {
         return FieldNamingPolicy.upperCaseFirstLetter(FieldNamingPolicy.separateCamelCase(var1.getName(), ' '));
      }
   },
   UPPER_CASE_WITH_UNDERSCORES {
      @Override
      public String translateName(Field var1) {
         return FieldNamingPolicy.separateCamelCase(var1.getName(), '_').toUpperCase(Locale.ENGLISH);
      }
   };
   private static final FieldNamingPolicy[] $VALUES = $values();

   private FieldNamingPolicy() {
   }

   public static String separateCamelCase(String var0, char var1) {
      StringBuilder var5 = new StringBuilder();
      int var4 = var0.length();

      for (int var3 = 0; var3 < var4; var3++) {
         char var2 = var0.charAt(var3);
         if (Character.isUpperCase(var2) && var5.length() != 0) {
            var5.append(var1);
         }

         var5.append(var2);
      }

      return var5.toString();
   }

   public static String upperCaseFirstLetter(String var0) {
      int var3 = var0.length();

      for (int var2 = 0; var2 < var3; var2++) {
         char var1 = var0.charAt(var2);
         if (Character.isLetter(var1)) {
            if (Character.isUpperCase(var1)) {
               return var0;
            }

            var1 = Character.toUpperCase(var1);
            if (var2 == 0) {
               StringBuilder var6 = new StringBuilder();
               var6.append(var1);
               var6.append(var0.substring(1));
               return var6.toString();
            }

            StringBuilder var4 = new StringBuilder();
            var4.append(var0.substring(0, var2));
            var4.append(var1);
            var4.append(var0.substring(var2 + 1));
            return var4.toString();
         }
      }

      return var0;
   }
}
