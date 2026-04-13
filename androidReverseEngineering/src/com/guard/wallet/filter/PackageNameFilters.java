package com.guard.wallet.filter;

import j.e;
import t.b;

public class PackageNameFilters {
   private static final b PACKAGE_NAME_GETTER = new e(15);

   public static Filter contains(String var0) {
      return new StringContainsFilter(PACKAGE_NAME_GETTER, var0);
   }

   public static Filter endsWith(String var0) {
      return new StringEndsWithFilter(PACKAGE_NAME_GETTER, var0);
   }

   public static Filter equals(String var0) {
      return new StringEqualsFilter(PACKAGE_NAME_GETTER, var0);
   }

   public static Filter matches(String var0) {
      return new StringMatchesFilter(PACKAGE_NAME_GETTER, var0);
   }

   public static Filter startsWith(String var0) {
      return new StringStartsWithFilter(PACKAGE_NAME_GETTER, var0);
   }
}
