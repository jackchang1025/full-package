package com.guard.wallet.condition;

import android.support.annotation.NonNull;
import com.guard.wallet.entity.Point;
import com.guard.wallet.filter.PointFilter;
import java.io.Serializable;

public class PointCondition implements Serializable {
   private int filterType;
   private float x;
   private float y;

   public PointCondition() {
   }

   public PointCondition(float var1, float var2, int var3) {
      this.x = var1;
      this.y = var2;
      this.filterType = var3;
   }

   public int getFilterType() {
      return this.filterType;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public void setFilterType(int var1) {
      this.filterType = var1;
   }

   public void setX(float var1) {
      this.x = var1;
   }

   public void setY(float var1) {
      this.y = var1;
   }

   public PointFilter toPointFilter() {
      return new PointFilter(new Point(this.x, this.y), this.filterType);
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("PointCondition{x=");
      var1.append(this.x);
      var1.append(", y=");
      var1.append(this.y);
      var1.append(", filterType=");
      var1.append(this.filterType);
      var1.append('}');
      return var1.toString();
   }
}
