package com.guard.wallet.filter;

import com.guard.wallet.entity.UiObject;
import java.util.Objects;

public class WindowTitleEqualFilter implements Filter {
   private final String value;
   private final String windowTitle;

   public WindowTitleEqualFilter(String var1, String var2) {
      this.windowTitle = var1;
      this.value = var2;
   }

   @Override
   public Boolean filter(UiObject var1) {
      String var3 = this.windowTitle;
      boolean var2;
      if (var3 != null) {
         var2 = var3.equalsIgnoreCase(this.value);
      } else {
         var2 = Objects.equals(this.value, "NULL");
      }

      return var2;
   }
}
