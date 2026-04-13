package com.guard.wallet.filter;

import com.guard.wallet.entity.UiObject;
import j.e;
import t.a;

public class IntLessThanOrEqualFilter implements Filter {
   private final a intProperty;
   private final int value;

   public IntLessThanOrEqualFilter(a var1, int var2) {
      this.intProperty = var1;
      this.value = var2;
   }

   @Override
   public Boolean filter(UiObject var1) {
      boolean var2;
      if (((e)this.intProperty).e(var1) <= this.value) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}
