package com.guard.wallet.resp;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class PermissionInfoVO implements Serializable {
   private String description;
   private String gradeCode;
   private String groupValue;
   private String permissionName;
   private String permissionValue;

   public PermissionInfoVO() {
   }

   public PermissionInfoVO(String var1, String var2, String var3, String var4, String var5) {
      this.permissionValue = var1;
      this.permissionName = var2;
      this.gradeCode = var3;
      this.groupValue = var4;
      this.description = var5;
   }

   public String getDescription() {
      return this.description;
   }

   public String getGradeCode() {
      return this.gradeCode;
   }

   public String getGroupValue() {
      return this.groupValue;
   }

   public String getPermissionName() {
      return this.permissionName;
   }

   public String getPermissionValue() {
      return this.permissionValue;
   }

   public void setDescription(String var1) {
      this.description = var1;
   }

   public void setGradeCode(String var1) {
      this.gradeCode = var1;
   }

   public void setGroupValue(String var1) {
      this.groupValue = var1;
   }

   public void setPermissionName(String var1) {
      this.permissionName = var1;
   }

   public void setPermissionValue(String var1) {
      this.permissionValue = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("PermissionInfoVO{permissionValue='");
      var1.append(this.permissionValue);
      var1.append("', permissionName='");
      var1.append(this.permissionName);
      var1.append("', gradeCode='");
      var1.append(this.gradeCode);
      var1.append("', groupValue='");
      var1.append(this.groupValue);
      var1.append("', description='");
      return a.n(var1, this.description, "'}");
   }
}
