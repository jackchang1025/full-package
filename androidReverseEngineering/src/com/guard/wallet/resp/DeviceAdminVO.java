package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

public class DeviceAdminVO extends MessageBodyVO {
   private Integer isAdminActive;
   private Integer isDeviceOwner;
   private Integer isProfileOwner;
   private String packageName;

   public DeviceAdminVO() {
   }

   public DeviceAdminVO(String var1, Integer var2, Integer var3, Integer var4) {
      this.packageName = var1;
      this.isAdminActive = var2;
      this.isDeviceOwner = var3;
      this.isProfileOwner = var4;
   }

   public Integer getIsAdminActive() {
      return this.isAdminActive;
   }

   public Integer getIsDeviceOwner() {
      return this.isDeviceOwner;
   }

   public Integer getIsProfileOwner() {
      return this.isProfileOwner;
   }

   public String getPackageName() {
      return this.packageName;
   }

   public void setIsAdminActive(Integer var1) {
      this.isAdminActive = var1;
   }

   public void setIsDeviceOwner(Integer var1) {
      this.isDeviceOwner = var1;
   }

   public void setIsProfileOwner(Integer var1) {
      this.isProfileOwner = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("DeviceAdminVO{packageName=");
      var1.append(this.packageName);
      var1.append(",isAdminActive=");
      var1.append(this.isAdminActive);
      var1.append(", isDeviceOwner=");
      var1.append(this.isDeviceOwner);
      var1.append(", isProfileOwner=");
      var1.append(this.isProfileOwner);
      var1.append('}');
      return var1.toString();
   }
}
