package com.guard.wallet.filter;

import j.e;
import t.b;

public class IdFilters {
   private static final b ID_GETTER = new e(13);

   public static Filter contains(String var0) {
      return new StringContainsFilter(ID_GETTER, var0);
   }

   public static Filter endsWith(String var0) {
      return new StringEndsWithFilter(ID_GETTER, var0);
   }

   public static Filter equals(String var0) {
      return new StringEqualsFilter(ID_GETTER, var0);
   }

   public static Filter matches(String var0) {
      return new StringMatchesFilter(ID_GETTER, var0);
   }

   public static Filter startsWith(String var0) {
      return new StringStartsWithFilter(ID_GETTER, var0);
   }
}
