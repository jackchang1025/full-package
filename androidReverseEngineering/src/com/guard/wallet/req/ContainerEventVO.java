package com.guard.wallet.req;

import android.support.annotation.NonNull;

public class ContainerEventVO extends MessageBodyVO {
   private String containerCode;
   private Integer isOpened;
   private String packageName;
   private Integer serviceState;

   public ContainerEventVO() {
   }

   public ContainerEventVO(String var1, String var2, Integer var3, Integer var4) {
      this.packageName = var1;
      this.containerCode = var2;
      this.isOpened = var3;
      this.serviceState = var4;
   }

   public String getContainerCode() {
      return this.containerCode;
   }

   public Integer getIsOpened() {
      return this.isOpened;
   }

   public String getPackageName() {
      return this.packageName;
   }

   public Integer getServiceState() {
      return this.serviceState;
   }

   public void setContainerCode(String var1) {
      this.containerCode = var1;
   }

   public void setIsOpened(Integer var1) {
      this.isOpened = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   public void setServiceState(Integer var1) {
      this.serviceState = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ContainerEventBodyVO{containerCode='");
      var1.append(this.containerCode);
      var1.append("', packageName=");
      var1.append(this.packageName);
      var1.append(", isOpened=");
      var1.append(this.isOpened);
      var1.append(", serviceState=");
      var1.append(this.serviceState);
      var1.append('}');
      return var1.toString();
   }
}
