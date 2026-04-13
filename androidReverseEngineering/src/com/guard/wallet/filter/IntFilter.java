package com.guard.wallet.filter;

import android.support.annotation.NonNull;
import com.guard.wallet.entity.UiObject;
import j.e;
import t.a;

public class IntFilter implements Filter {
   private a intProperty;
   private int value;

   public IntFilter(a var1, int var2) {
      this.intProperty = var1;
      this.value = var2;
   }

   @Override
   public Boolean filter(UiObject var1) {
      boolean var2;
      if (((e)this.intProperty).e(var1) == this.value) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public a getIntProperty() {
      return this.intProperty;
   }

   public int getValue() {
      return this.value;
   }

   public void setIntProperty(a var1) {
      this.intProperty = var1;
   }

   public void setValue(int var1) {
      this.value = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.intProperty.toString());
      var1.append("(");
      return a.a.m(var1, this.value, ")");
   }
}
