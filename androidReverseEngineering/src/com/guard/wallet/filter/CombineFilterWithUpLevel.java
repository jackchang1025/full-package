package com.guard.wallet.filter;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class CombineFilterWithUpLevel implements Serializable {
   private CombineFilter childFilter;
   private Integer upLevel;

   public CombineFilterWithUpLevel() {
   }

   public CombineFilterWithUpLevel(Integer var1, CombineFilter var2) {
      this.upLevel = var1;
      this.childFilter = var2;
   }

   public CombineFilter getChildFilter() {
      return this.childFilter;
   }

   public Integer getUpLevel() {
      return this.upLevel;
   }

   public void setChildFilter(CombineFilter var1) {
      this.childFilter = var1;
   }

   public void setUpLevel(Integer var1) {
      this.upLevel = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("CombineFilterWithUpLevel{upLevel=");
      var1.append(this.upLevel);
      var1.append(", childFilter=");
      var1.append(this.childFilter);
      var1.append('}');
      return var1.toString();
   }
}
