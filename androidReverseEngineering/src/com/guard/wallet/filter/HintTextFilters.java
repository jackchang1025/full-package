package com.guard.wallet.filter;

import j.e;
import t.b;

public class HintTextFilters {
   private static final b HINT_TEXT_GETTER = new e(12);

   public static Filter contains(String var0) {
      return new StringContainsFilter(HINT_TEXT_GETTER, var0);
   }

   public static Filter endsWith(String var0) {
      return new StringEndsWithFilter(HINT_TEXT_GETTER, var0);
   }

   public static Filter equals(String var0) {
      return new StringEqualsFilter(HINT_TEXT_GETTER, var0);
   }

   public static Filter matches(String var0) {
      return new StringMatchesFilter(HINT_TEXT_GETTER, var0);
   }

   public static Filter startsWith(String var0) {
      return new StringStartsWithFilter(HINT_TEXT_GETTER, var0);
   }
}
