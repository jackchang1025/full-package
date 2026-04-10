package com.guard.wallet.condition;

import androidx.annotation.NonNull;
import com.guard.wallet.entity.Point;
import java.io.Serializable;
import java.util.List;

public class GlobalActionCondition implements Serializable {
   private String actionName = "click";
   private Long duration;
   private List<Point> points;
   private Integer repeatCount;
   private Long start = 10L;
   private ActionValueCondition value;

   public GlobalActionCondition() {
      this.duration = 100L;
   }

   public GlobalActionCondition(String var1, List<Point> var2, Long var3, Long var4, Integer var5, ActionValueCondition var6) {
      this.actionName = var1;
      this.points = var2;
      this.start = var3;
      this.duration = var4;
      this.repeatCount = var5;
      this.value = var6;
   }

   public String getActionName() {
      return this.actionName;
   }

   public Long getDuration() {
      return this.duration;
   }

   public List<Point> getPoints() {
      return this.points;
   }

   public Integer getRepeatCount() {
      return this.repeatCount;
   }

   public Long getStart() {
      return this.start;
   }

   public ActionValueCondition getValue() {
      return this.value;
   }

   public void setActionName(String var1) {
      this.actionName = var1;
   }

   public void setDuration(Long var1) {
      this.duration = var1;
   }

   public void setPoints(List<Point> var1) {
      this.points = var1;
   }

   public void setRepeatCount(Integer var1) {
      this.repeatCount = var1;
   }

   public void setStart(Long var1) {
      this.start = var1;
   }

   public void setValue(ActionValueCondition var1) {
      this.value = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("GlobalActionCondition{actionName='");
      var1.append(this.actionName);
      var1.append("', points=");
      var1.append(this.points);
      var1.append(", start=");
      var1.append(this.start);
      var1.append(", duration=");
      var1.append(this.duration);
      var1.append(", repeatCount=");
      var1.append(this.repeatCount);
      var1.append(", value=");
      var1.append(this.value);
      var1.append('}');
      return var1.toString();
   }
}
