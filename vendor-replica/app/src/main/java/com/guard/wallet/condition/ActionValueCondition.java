package com.guard.wallet.condition;

import java.io.Serializable;

public class ActionValueCondition implements Serializable {
   private String key;
   private String type;
   private String value;

   public ActionValueCondition() {
   }

   public ActionValueCondition(String var1, String var2, String var3) {
      this.type = var1;
      this.key = var2;
      this.value = var3;
   }

   public String getKey() {
      return this.key;
   }

   public String getType() {
      return this.type;
   }

   public String getValue() {
      return this.value;
   }

   public void setKey(String var1) {
      this.key = var1;
   }

   public void setType(String var1) {
      this.type = var1;
   }

   public void setValue(String var1) {
      this.value = var1;
   }

   /**
    * Returns the X ratio from the value field.
    * Supports formats: "x,y" (comma-separated) or single float (uses value directly).
    */
   public float getXRatio() {
      if (value == null || value.isEmpty()) return 0.5f;
      try {
         int comma = value.indexOf(',');
         if (comma >= 0) {
            return Float.parseFloat(value.substring(0, comma).trim());
         }
         return Float.parseFloat(value.trim());
      } catch (NumberFormatException e) {
         return 0.5f;
      }
   }

   /**
    * Returns the Y ratio from the value field.
    * Supports formats: "x,y" (comma-separated) or single float (defaults to 0.5).
    */
   public float getYRatio() {
      if (value == null || value.isEmpty()) return 0.5f;
      try {
         int comma = value.indexOf(',');
         if (comma >= 0) {
            return Float.parseFloat(value.substring(comma + 1).trim());
         }
         return 0.5f;
      } catch (NumberFormatException e) {
         return 0.5f;
      }
   }
}
