package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class DistanceTouchNode implements Serializable {
   private double distance;
   private UiObject touchNode;

   public DistanceTouchNode() {
   }

   public DistanceTouchNode(UiObject var1, double var2) {
      this.touchNode = var1;
      this.distance = var2;
   }

   public double getDistance() {
      return this.distance;
   }

   public UiObject getTouchNode() {
      return this.touchNode;
   }

   public void setDistance(double var1) {
      this.distance = var1;
   }

   public void setTouchNode(UiObject var1) {
      this.touchNode = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("DistanceTouchNode{touchNode=");
      var1.append(this.touchNode);
      var1.append(", distance=");
      var1.append(this.distance);
      var1.append('}');
      return var1.toString();
   }
}
