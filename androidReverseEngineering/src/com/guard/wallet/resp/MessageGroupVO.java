package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class MessageGroupVO implements Serializable {
   private Integer enable;
   private String groupCode;

   public MessageGroupVO() {
   }

   public MessageGroupVO(String var1, Integer var2) {
      this.groupCode = var1;
      this.enable = var2;
   }

   public Integer getEnable() {
      return this.enable;
   }

   public String getGroupCode() {
      return this.groupCode;
   }

   public void setEnable(Integer var1) {
      this.enable = var1;
   }

   public void setGroupCode(String var1) {
      this.groupCode = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("MessageGroupVO{groupCode='");
      var1.append(this.groupCode);
      var1.append("', enable=");
      var1.append(this.enable);
      var1.append('}');
      return var1.toString();
   }
}
