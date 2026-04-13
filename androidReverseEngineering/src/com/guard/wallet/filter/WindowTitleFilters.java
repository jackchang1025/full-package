package com.guard.wallet.filter;

public class WindowTitleFilters {
   public static Filter contains(String var0, String var1) {
      return new WindowTitleContainsFilter(var0, var1);
   }

   public static Filter endsWith(String var0, String var1) {
      return new WindowTitleEndsWithFilter(var0, var1);
   }

   public static Filter equals(String var0, String var1) {
      return new WindowTitleEqualFilter(var0, var1);
   }

   public static Filter matches(String var0, String var1) {
      return new WindowTitleMatchesFilter(var0, var1);
   }

   public static Filter startsWith(String var0, String var1) {
      return new WindowTitleStartsWithFilter(var0, var1);
   }
}
