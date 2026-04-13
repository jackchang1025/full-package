package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.Objects;

public class Point implements Serializable {
   private float x;
   private float y;

   public Point() {
   }

   public Point(float var1, float var2) {
      this.x = var1;
      this.y = var2;
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (Float.compare(var1.x, this.x) != 0 || Float.compare(var1.y, this.y) != 0) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.x, this.y);
   }

   public void setX(float var1) {
      this.x = var1;
   }

   public void setY(float var1) {
      this.y = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("Point{x=");
      var1.append(this.x);
      var1.append(", y=");
      var1.append(this.y);
      var1.append('}');
      return var1.toString();
   }
}
