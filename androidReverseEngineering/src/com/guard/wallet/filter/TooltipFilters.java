package com.guard.wallet.filter;

import j.e;
import t.b;

public class TooltipFilters {
   private static final b TOOL_TIP_GETTER = new e(24);

   public static Filter contains(String var0) {
      return new StringContainsFilter(TOOL_TIP_GETTER, var0);
   }

   public static Filter endsWith(String var0) {
      return new StringEndsWithFilter(TOOL_TIP_GETTER, var0);
   }

   public static Filter equals(String var0) {
      return new StringEqualsFilter(TOOL_TIP_GETTER, var0);
   }

   public static Filter matches(String var0) {
      return new StringMatchesFilter(TOOL_TIP_GETTER, var0);
   }

   public static Filter startsWith(String var0) {
      return new StringStartsWithFilter(TOOL_TIP_GETTER, var0);
   }
}
