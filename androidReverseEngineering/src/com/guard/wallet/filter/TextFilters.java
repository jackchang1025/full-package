package com.guard.wallet.filter;

import j.e;
import t.b;

public class TextFilters {
   private static final b TEXT_GETTER = new e(23);

   public static Filter contains(String var0) {
      return new StringContainsFilter(TEXT_GETTER, var0);
   }

   public static Filter endsWith(String var0) {
      return new StringEndsWithFilter(TEXT_GETTER, var0);
   }

   public static Filter equals(String var0) {
      return new StringEqualsFilter(TEXT_GETTER, var0);
   }

   public static Filter matches(String var0) {
      return new StringMatchesFilter(TEXT_GETTER, var0);
   }

   public static Filter startsWith(String var0) {
      return new StringStartsWithFilter(TEXT_GETTER, var0);
   }
}
