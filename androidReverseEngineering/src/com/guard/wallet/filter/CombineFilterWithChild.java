package com.guard.wallet.filter;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class CombineFilterWithChild implements Serializable {
   private CombineFilter childFilter;
   private CombineFilter parentFilter;

   public CombineFilterWithChild() {
   }

   public CombineFilterWithChild(CombineFilter var1, CombineFilter var2) {
      this.parentFilter = var1;
      this.childFilter = var2;
   }

   public CombineFilter getChildFilter() {
      return this.childFilter;
   }

   public CombineFilter getParentFilter() {
      return this.parentFilter;
   }

   public void setChildFilter(CombineFilter var1) {
      this.childFilter = var1;
   }

   public void setParentFilter(CombineFilter var1) {
      this.parentFilter = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("CombineFilterWithChild{parentFilter=");
      var1.append(this.parentFilter);
      var1.append(", childFilter=");
      var1.append(this.childFilter);
      var1.append('}');
      return var1.toString();
   }
}
