package com.guard.wallet.msg;

import android.support.annotation.NonNull;
import com.guard.wallet.entity.Point;
import java.util.LinkedList;
import java.util.List;

public class ReadScreenEvent extends BaseMsgBody {
   private int eventType;
   private List<Point> points;

   public ReadScreenEvent(int var1) {
      this.points = new LinkedList<>();
      this.eventType = var1;
   }

   public ReadScreenEvent(int var1, List<Point> var2) {
      new LinkedList();
      this.eventType = var1;
      this.points = var2;
   }

   public int getEventType() {
      return this.eventType;
   }

   public List<Point> getPoints() {
      return this.points;
   }

   public void setEventType(int var1) {
      this.eventType = var1;
   }

   public void setPoints(List<Point> var1) {
      this.points = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReadScreenEvent{eventType=");
      var1.append(this.eventType);
      var1.append(", points=");
      var1.append(this.points);
      var1.append('}');
      return var1.toString();
   }
}
