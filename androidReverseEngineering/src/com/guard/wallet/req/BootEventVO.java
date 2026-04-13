package com.guard.wallet.req;

import android.support.annotation.NonNull;

public class BootEventVO extends MessageBodyVO {
   private Integer hasReceiveCompleted;
   private String packageName;

   public BootEventVO() {
   }

   public BootEventVO(String var1, Integer var2) {
      this.packageName = var1;
      this.hasReceiveCompleted = var2;
   }

   public Integer getHasReceiveCompleted() {
      return this.hasReceiveCompleted;
   }

   public String getPackageName() {
      return this.packageName;
   }

   public void setHasReceiveCompleted(Integer var1) {
      this.hasReceiveCompleted = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("BootEventVO{packageName='");
      var1.append(this.packageName);
      var1.append("', hasReceiveCompleted=");
      var1.append(this.hasReceiveCompleted);
      var1.append('}');
      return var1.toString();
   }
}
