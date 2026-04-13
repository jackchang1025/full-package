package com.guard.wallet.filter;

import a1.q;
import com.guard.wallet.entity.UiObject;

public class WindowTitleContainsFilter implements Filter {
   private final String contains;
   private final String windowTitle;

   public WindowTitleContainsFilter(String var1, String var2) {
      this.windowTitle = var1;
      this.contains = var2;
   }

   @Override
   public Boolean filter(UiObject var1) {
      if (q.B(this.contains)) {
         return Boolean.FALSE;
      } else {
         String var3 = this.windowTitle;
         boolean var2;
         if (var3 != null && var3.toLowerCase().contains(this.contains.toLowerCase())) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   }
}
