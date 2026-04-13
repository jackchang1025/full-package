package com.guard.wallet.condition;

import a1.q;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.Point;
import com.guard.wallet.utils.h;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TargetActionCondition implements Serializable {
   private static final String TAG = "com.guard.wallet.condition.TargetActionCondition";
   private String actionName;
   private Integer actionType;
   private String delegateId;
   private String resUnique;
   private int target;
   private List<ActionValueCondition> values;

   public TargetActionCondition() {
      this.target = 0;
      this.actionName = "click";
   }

   public TargetActionCondition(String var1, String var2, int var3, Integer var4, String var5, List<ActionValueCondition> var6) {
      this.delegateId = var1;
      this.resUnique = var2;
      this.target = var3;
      this.actionType = var4;
      this.actionName = var5;
      this.values = var6;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public void destroy() {
      Exception var10000;
      label25: {
         List var1;
         try {
            var1 = this.values;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var10001 = false;
            break label25;
         }

         if (var1 == null) {
            return;
         }

         try {
            var1.clear();
            this.values = null;
            return;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var5 = false;
         }
      }

      Exception var4 = var10000;
      q.s(TAG, var4);
   }

   public String getActionName() {
      return this.actionName;
   }

   public Integer getActionType() {
      return this.actionType;
   }

   public String getDelegateId() {
      return this.delegateId;
   }

   public String getResUnique() {
      return this.resUnique;
   }

   public int getTarget() {
      return this.target;
   }

   public List<ActionValueCondition> getValues() {
      return this.values;
   }

   public void setActionName(String var1) {
      this.actionName = var1;
   }

   public void setActionType(Integer var1) {
      this.actionType = var1;
   }

   public void setDelegateId(String var1) {
      this.delegateId = var1;
   }

   public void setResUnique(String var1) {
      this.resUnique = var1;
   }

   public void setTarget(int var1) {
      this.target = var1;
   }

   public void setValues(List<ActionValueCondition> var1) {
      this.values = var1;
   }

   public GlobalActionCondition toGlobalActionCondition() {
      GlobalActionCondition var5 = new GlobalActionCondition();
      var5.setActionName(this.actionName);
      List var3 = this.values;
      if (var3 != null && !var3.isEmpty()) {
         Iterator var6 = this.values.iterator();
         Point var10 = null;

         while (var6.hasNext()) {
            ActionValueCondition var7 = (ActionValueCondition)var6.next();
            if (var7 != null) {
               String var4 = var7.getKey();
               var4.getClass();
               int var2 = var4.hashCode();
               int var1 = -1;
               switch (var2) {
                  case -1992012396:
                     if (var4.equals("duration")) {
                        var1 = 0;
                     }
                     break;
                  case -982754077:
                     if (var4.equals("points")) {
                        var1 = 1;
                     }
                     break;
                  case 120:
                     if (var4.equals("x")) {
                        var1 = 2;
                     }
                     break;
                  case 121:
                     if (var4.equals("y")) {
                        var1 = 3;
                     }
                     break;
                  case 106845584:
                     if (var4.equals("point")) {
                        var1 = 4;
                     }
                     break;
                  case 109757538:
                     if (var4.equals("start")) {
                        var1 = 5;
                     }
                     break;
                  case 1571519540:
                     if (var4.equals("repeatCount")) {
                        var1 = 6;
                     }
               }

               switch (var1) {
                  case 0:
                     if (q.D(var7.getValue())) {
                        var5.setDuration(Long.parseLong(var7.getValue()));
                     }
                     break;
                  case 1:
                     if (Objects.equals(var7.getType(), "ObjectArray") && !q.B(var7.getValue())) {
                        List var14 = h.P(var7.getValue());
                        if (var14 != null && !var14.isEmpty()) {
                           if (var5.getPoints() == null) {
                              var5.setPoints(new LinkedList<>());
                           }

                           var5.getPoints().addAll(var14);
                        }
                     }
                     break;
                  case 2:
                     if (q.D(var7.getValue())) {
                        var1 = Integer.parseInt(var7.getValue());
                        Point var13 = var10;
                        if (var10 == null) {
                           var13 = new Point();
                        }

                        var13.setX((float)var1);
                        var10 = var13;
                     }
                     break;
                  case 3:
                     if (q.D(var7.getValue())) {
                        var1 = Integer.parseInt(var7.getValue());
                        Point var12 = var10;
                        if (var10 == null) {
                           var12 = new Point();
                        }

                        var12.setY((float)var1);
                        var10 = var12;
                     }
                     break;
                  case 4:
                     if (Objects.equals(var7.getType(), "Object") && !q.B(var7.getValue())) {
                        Point var11 = h.O(var7.getValue());
                        if (var11 != null) {
                           if (var5.getPoints() == null) {
                              var5.setPoints(new LinkedList<>());
                           }

                           var5.getPoints().add(var11);
                        }
                     }
                     break;
                  case 5:
                     if (q.D(var7.getValue())) {
                        var5.setStart(Long.parseLong(var7.getValue()));
                     }
                     break;
                  case 6:
                     if (q.D(var7.getValue())) {
                        var5.setRepeatCount(Integer.parseInt(var7.getValue()));
                     }
                     break;
                  default:
                     if (!q.B(var7.getValue()) && var5.getValue() == null) {
                        var5.setValue(var7);
                     }
               }
            }
         }

         if (var10 != null && var10.getX() >= 0.0F && var10.getY() >= 0.0F) {
            if (var5.getPoints() == null) {
               var5.setPoints(new LinkedList<>());
            }

            var5.getPoints().add(var10);
         }
      }

      return var5;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("TargetActionCondition{delegateId='");
      var1.append(this.delegateId);
      var1.append("', resUnique='");
      var1.append(this.resUnique);
      var1.append("', target=");
      var1.append(this.target);
      var1.append(", actionType='");
      var1.append(this.actionType);
      var1.append("', actionName='");
      var1.append(this.actionName);
      var1.append("', values=");
      var1.append(this.values);
      var1.append('}');
      return var1.toString();
   }
}
