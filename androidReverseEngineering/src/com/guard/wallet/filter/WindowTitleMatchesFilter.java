package com.guard.wallet.filter;

import com.guard.wallet.entity.UiObject;

public class WindowTitleMatchesFilter implements Filter {
   private final String regex;
   private final String windowTitle;

   public WindowTitleMatchesFilter(String var1, String var2) {
      this.windowTitle = var1;
      this.regex = var2;
   }

   @Override
   public Boolean filter(UiObject var1) {
      String var3 = this.windowTitle;
      boolean var2;
      if (var3 != null && var3.matches(this.regex)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}
