package com.guard.wallet.condition;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import com.guard.wallet.filter.BoundsFilter;
import java.io.Serializable;

public class BoundsCondition implements Serializable {
   private int bottom;
   private int filterType;
   private int left;
   private int right;
   private int top;

   public BoundsCondition() {
   }

   public BoundsCondition(int var1, int var2, int var3, int var4, int var5) {
      this.left = var1;
      this.top = var2;
      this.right = var3;
      this.bottom = var4;
      this.filterType = var5;
   }

   public int getBottom() {
      return this.bottom;
   }

   public int getFilterType() {
      return this.filterType;
   }

   public int getLeft() {
      return this.left;
   }

   public int getRight() {
      return this.right;
   }

   public int getTop() {
      return this.top;
   }

   public void setBottom(int var1) {
      this.bottom = var1;
   }

   public void setFilterType(int var1) {
      this.filterType = var1;
   }

   public void setLeft(int var1) {
      this.left = var1;
   }

   public void setRight(int var1) {
      this.right = var1;
   }

   public void setTop(int var1) {
      this.top = var1;
   }

   public BoundsFilter toBoundsFilter() {
      int var1 = this.filterType;
      return var1 != 0 && var1 != 1 && var1 != 2 ? null : new BoundsFilter(new Rect(this.left, this.top, this.right, this.bottom), this.filterType);
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("BoundsCondition{left=");
      var1.append(this.left);
      var1.append(", top=");
      var1.append(this.top);
      var1.append(", right=");
      var1.append(this.right);
      var1.append(", bottom=");
      var1.append(this.bottom);
      var1.append(", filterType=");
      var1.append(this.filterType);
      var1.append('}');
      return var1.toString();
   }
}
