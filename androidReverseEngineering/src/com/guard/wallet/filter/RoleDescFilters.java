package com.guard.wallet.filter;

import j.e;
import t.b;

public class RoleDescFilters {
   private static final b ROLE_DESC_GETTER = new e(18);

   public static Filter contains(String var0) {
      return new StringContainsFilter(ROLE_DESC_GETTER, var0);
   }

   public static Filter endsWith(String var0) {
      return new StringEndsWithFilter(ROLE_DESC_GETTER, var0);
   }

   public static Filter equals(String var0) {
      return new StringEqualsFilter(ROLE_DESC_GETTER, var0);
   }

   public static Filter matches(String var0) {
      return new StringMatchesFilter(ROLE_DESC_GETTER, var0);
   }

   public static Filter startsWith(String var0) {
      return new StringStartsWithFilter(ROLE_DESC_GETTER, var0);
   }
}
