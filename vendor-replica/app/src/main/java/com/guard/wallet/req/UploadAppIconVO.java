package com.guard.wallet.req;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class UploadAppIconVO implements Serializable {
   private String deviceId;
   private String packageName;
   private String spaceId;

   public UploadAppIconVO(String var1, String var2, String var3) {
      this.deviceId = var1;
      this.packageName = var2;
      this.spaceId = var3;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public String getPackageName() {
      return this.packageName;
   }

   public String getSpaceId() {
      return this.spaceId;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   public void setSpaceId(String var1) {
      this.spaceId = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("UploadAppIconVO{deviceId='");
      var1.append(this.deviceId);
      var1.append("', packageName='");
      var1.append(this.packageName);
      var1.append("', spaceId='");
      return var1.append(this.spaceId).append("'}").toString();
   }
}
