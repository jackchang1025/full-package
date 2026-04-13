package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class PermissionRequestVO implements Serializable {
   private String groupCode;
   private String groupValue;
   private String packageName;
   private String permissionCode;
   private String permissionValue;
   private Integer requestCode;

   public PermissionRequestVO() {
   }

   public PermissionRequestVO(String var1, String var2, String var3, String var4, String var5, Integer var6) {
      this.packageName = var1;
      this.permissionCode = var2;
      this.permissionValue = var3;
      this.groupCode = var4;
      this.groupValue = var5;
      this.requestCode = var6;
   }

   public String getGroupCode() {
      return this.groupCode;
   }

   public String getGroupValue() {
      return this.groupValue;
   }

   public String getPackageName() {
      return this.packageName;
   }

   public String getPermissionCode() {
      return this.permissionCode;
   }

   public String getPermissionValue() {
      return this.permissionValue;
   }

   public Integer getRequestCode() {
      return this.requestCode;
   }

   public void setGroupCode(String var1) {
      this.groupCode = var1;
   }

   public void setGroupValue(String var1) {
      this.groupValue = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   public void setPermissionCode(String var1) {
      this.permissionCode = var1;
   }

   public void setPermissionValue(String var1) {
      this.permissionValue = var1;
   }

   public void setRequestCode(Integer var1) {
      this.requestCode = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("RequestPermissionVO{packageName='");
      var1.append(this.packageName);
      var1.append("', permissionCode='");
      var1.append(this.permissionCode);
      var1.append("', permissionValue='");
      var1.append(this.permissionValue);
      var1.append("', groupCode='");
      var1.append(this.groupCode);
      var1.append("', groupValue='");
      var1.append(this.groupValue);
      var1.append("', requestCode=");
      var1.append(this.requestCode);
      var1.append('}');
      return var1.toString();
   }
}
