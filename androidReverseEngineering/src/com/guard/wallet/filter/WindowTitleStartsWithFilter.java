package com.guard.wallet.filter;

import a1.q;
import com.guard.wallet.entity.UiObject;

public class WindowTitleStartsWithFilter implements Filter {
   private final String prefix;
   private final String windowTitle;

   public WindowTitleStartsWithFilter(String var1, String var2) {
      this.windowTitle = var1;
      this.prefix = var2;
   }

   @Override
   public Boolean filter(UiObject var1) {
      if (q.B(this.prefix)) {
         return Boolean.FALSE;
      } else {
         String var3 = this.windowTitle;
         boolean var2;
         if (var3 != null && var3.toLowerCase().startsWith(this.prefix.toLowerCase())) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   }
}
