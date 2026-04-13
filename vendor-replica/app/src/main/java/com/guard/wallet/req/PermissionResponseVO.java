package com.guard.wallet.req;

import androidx.annotation.NonNull;

public class PermissionResponseVO extends MessageBodyVO {
   private String deviceId;
   private Integer granted;
   private String packageName;
   private String permissionValue;
   private Integer requestCode;
   private Integer requested;

   public PermissionResponseVO() {
   }

   public PermissionResponseVO(String var1, String var2, String var3, Integer var4, Integer var5, Integer var6) {
      this.deviceId = var1;
      this.packageName = var2;
      this.permissionValue = var3;
      this.requested = var4;
      this.granted = var5;
      this.requestCode = var6;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public Integer getGranted() {
      return this.granted;
   }

   public String getPackageName() {
      return this.packageName;
   }

   public String getPermissionValue() {
      return this.permissionValue;
   }

   public Integer getRequestCode() {
      return this.requestCode;
   }

   public Integer getRequested() {
      return this.requested;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setGranted(Integer var1) {
      this.granted = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   public void setPermissionValue(String var1) {
      this.permissionValue = var1;
   }

   public void setRequestCode(Integer var1) {
      this.requestCode = var1;
   }

   public void setRequested(Integer var1) {
      this.requested = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("PermissionResponseVO{deviceId='");
      var1.append(this.deviceId);
      var1.append("', packageName='");
      var1.append(this.packageName);
      var1.append("', permissionValue=");
      var1.append(this.permissionValue);
      var1.append(", requested=");
      var1.append(this.requested);
      var1.append(", granted=");
      var1.append(this.granted);
      var1.append(", requestCode=");
      var1.append(this.requestCode);
      var1.append('}');
      return var1.toString();
   }
}
