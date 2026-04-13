package com.guard.wallet.filter;

import j.e;
import t.b;

public class DescFilters {
   private static final b DESC_GETTER = new e(10);

   public static Filter contains(String var0) {
      return new StringContainsFilter(DESC_GETTER, var0);
   }

   public static Filter endsWith(String var0) {
      return new StringEndsWithFilter(DESC_GETTER, var0);
   }

   public static Filter equals(String var0) {
      return new StringEqualsFilter(DESC_GETTER, var0);
   }

   public static Filter matches(String var0) {
      return new StringMatchesFilter(DESC_GETTER, var0);
   }

   public static Filter startsWith(String var0) {
      return new StringStartsWithFilter(DESC_GETTER, var0);
   }
}
