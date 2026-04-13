package com.guard.wallet.filter;

import j.e;
import t.b;

public class ClassNameFilters {
   private static final b CLASS_NAME_GETTER = new e(5);

   public static Filter contains(String var0) {
      return new StringContainsFilter(CLASS_NAME_GETTER, var0);
   }

   public static Filter endsWith(String var0) {
      return new StringEndsWithFilter(CLASS_NAME_GETTER, var0);
   }

   public static Filter equals(String var0) {
      String var1 = var0;
      if (!var0.contains(".")) {
         var1 = "android.widget.$className";
      }

      return new StringEqualsFilter(CLASS_NAME_GETTER, var1);
   }

   public static Filter matches(String var0) {
      return new StringMatchesFilter(CLASS_NAME_GETTER, var0);
   }

   public static Filter startsWith(String var0) {
      return new StringStartsWithFilter(CLASS_NAME_GETTER, var0);
   }
}
