package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.List;

public class PermissionsBodyVO implements Serializable {
   private String applicationLabel;
   private String deviceId;
   private String packageName;
   private List<String> permissions;

   public PermissionsBodyVO() {
   }

   public PermissionsBodyVO(String var1, String var2, String var3, List<String> var4) {
      this.deviceId = var1;
      this.packageName = var2;
      this.applicationLabel = var3;
      this.permissions = var4;
   }

   public String getApplicationLabel() {
      return this.applicationLabel;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public String getPackageName() {
      return this.packageName;
   }

   public List<String> getPermissions() {
      return this.permissions;
   }

   public void setApplicationLabel(String var1) {
      this.applicationLabel = var1;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   public void setPermissions(List<String> var1) {
      this.permissions = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("PermissionsBodyVO{deviceId='");
      var1.append(this.deviceId);
      var1.append("', packageName='");
      var1.append(this.packageName);
      var1.append("', applicationLabel='");
      var1.append(this.applicationLabel);
      var1.append("', permissions=");
      var1.append(this.permissions);
      var1.append('}');
      return var1.toString();
   }
}
