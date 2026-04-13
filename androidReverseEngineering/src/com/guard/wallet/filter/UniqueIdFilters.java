package com.guard.wallet.filter;

import j.e;
import t.b;

public class UniqueIdFilters {
   private static final b UNIQUE_ID_GETTER = new e(25);

   public static Filter contains(String var0) {
      return new StringContainsFilter(UNIQUE_ID_GETTER, var0);
   }

   public static Filter endsWith(String var0) {
      return new StringEndsWithFilter(UNIQUE_ID_GETTER, var0);
   }

   public static Filter equals(String var0) {
      return new StringEqualsFilter(UNIQUE_ID_GETTER, var0);
   }

   public static Filter matches(String var0) {
      return new StringMatchesFilter(UNIQUE_ID_GETTER, var0);
   }

   public static Filter startsWith(String var0) {
      return new StringStartsWithFilter(UNIQUE_ID_GETTER, var0);
   }
}
