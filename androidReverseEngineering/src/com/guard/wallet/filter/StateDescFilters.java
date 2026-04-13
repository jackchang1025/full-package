package com.guard.wallet.filter;

import j.e;
import t.b;

public class StateDescFilters {
   private static final b STATE_DESC_GETTER = new e(22);

   public static Filter contains(String var0) {
      return new StringContainsFilter(STATE_DESC_GETTER, var0);
   }

   public static Filter endsWith(String var0) {
      return new StringEndsWithFilter(STATE_DESC_GETTER, var0);
   }

   public static Filter equals(String var0) {
      return new StringEqualsFilter(STATE_DESC_GETTER, var0);
   }

   public static Filter matches(String var0) {
      return new StringMatchesFilter(STATE_DESC_GETTER, var0);
   }

   public static Filter startsWith(String var0) {
      return new StringStartsWithFilter(STATE_DESC_GETTER, var0);
   }
}
