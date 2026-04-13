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
}
