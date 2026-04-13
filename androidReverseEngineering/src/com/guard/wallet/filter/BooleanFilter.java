package com.guard.wallet.filter;

import android.support.annotation.NonNull;
import b0.a;
import com.guard.wallet.entity.UiObject;

public class BooleanFilter implements Filter {
   private a booleanSupplier;
   public Boolean exceptedValue;

   public BooleanFilter(a var1, Boolean var2) {
      this.booleanSupplier = var1;
      this.exceptedValue = var2;
   }

   @Override
   public Boolean filter(UiObject var1) {
      boolean var2;
      if (this.booleanSupplier.c(var1) == this.exceptedValue) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public a getBooleanSupplier() {
      return this.booleanSupplier;
   }

   public Boolean getExceptedValue() {
      return this.exceptedValue;
   }

   public void setBooleanSupplier(a var1) {
      this.booleanSupplier = var1;
   }

   public void setExceptedValue(Boolean var1) {
      this.exceptedValue = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.booleanSupplier.toString());
      var1.append("(");
      var1.append(this.exceptedValue);
      var1.append(")");
      return var1.toString();
   }
}
