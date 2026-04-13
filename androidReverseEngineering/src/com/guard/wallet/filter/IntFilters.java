package com.guard.wallet.filter;

import t.a;

public class IntFilters {
   public static Filter equals(a var0, int var1) {
      return new IntEqualsFilter(var0, var1);
   }

   public static Filter gt(a var0, int var1) {
      return new IntGreaterThanFilter(var0, var1);
   }

   public static Filter gte(a var0, int var1) {
      return new IntGreaterThanOrEqualFilter(var0, var1);
   }

   public static Filter lt(a var0, int var1) {
      return new IntLessThanFilter(var0, var1);
   }

   public static Filter lte(a var0, int var1) {
      return new IntLessThanOrEqualFilter(var0, var1);
   }

   public static Filter notEquals(a var0, int var1) {
      return new IntNotEqualsFilter(var0, var1);
   }
}
