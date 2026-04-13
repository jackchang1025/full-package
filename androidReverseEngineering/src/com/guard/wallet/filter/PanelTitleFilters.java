package com.guard.wallet.filter;

import j.e;
import t.b;

public class PanelTitleFilters {
   private static final b PANEL_TITLE_GETTER = new e(16);

   public static Filter contains(String var0) {
      return new StringContainsFilter(PANEL_TITLE_GETTER, var0);
   }

   public static Filter endsWith(String var0) {
      return new StringEndsWithFilter(PANEL_TITLE_GETTER, var0);
   }

   public static Filter equals(String var0) {
      return new StringEqualsFilter(PANEL_TITLE_GETTER, var0);
   }

   public static Filter matches(String var0) {
      return new StringMatchesFilter(PANEL_TITLE_GETTER, var0);
   }

   public static Filter startsWith(String var0) {
      return new StringStartsWithFilter(PANEL_TITLE_GETTER, var0);
   }
}
