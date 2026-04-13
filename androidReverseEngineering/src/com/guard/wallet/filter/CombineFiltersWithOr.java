package com.guard.wallet.filter;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.List;

public class CombineFiltersWithOr implements Serializable {
   private String delegateId;
   private List<CombineFilter> filters;
   private String resUnique;
   private int target;

   public CombineFiltersWithOr() {
      this.target = 0;
   }

   public CombineFiltersWithOr(String var1, String var2, int var3, List<CombineFilter> var4) {
      this.delegateId = var1;
      this.resUnique = var2;
      this.target = var3;
      this.filters = var4;
   }

   public CombineFiltersWithOr(List<CombineFilter> var1) {
      this.target = 0;
      this.filters = var1;
   }

   public String getDelegateId() {
      return this.delegateId;
   }

   public List<CombineFilter> getFilters() {
      return this.filters;
   }

   public String getResUnique() {
      return this.resUnique;
   }

   public int getTarget() {
      return this.target;
   }

   public void setDelegateId(String var1) {
      this.delegateId = var1;
   }

   public void setFilters(List<CombineFilter> var1) {
      this.filters = var1;
   }

   public void setResUnique(String var1) {
      this.resUnique = var1;
   }

   public void setTarget(int var1) {
      this.target = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("CombineFiltersWithOr{delegateId='");
      var1.append(this.delegateId);
      var1.append("', resUnique='");
      var1.append(this.resUnique);
      var1.append("', target=");
      var1.append(this.target);
      var1.append(", filters=");
      var1.append(this.filters);
      var1.append('}');
      return var1.toString();
   }
}
