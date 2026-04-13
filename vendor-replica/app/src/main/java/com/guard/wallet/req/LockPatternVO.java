package com.guard.wallet.req;

import androidx.annotation.NonNull;

public class LockPatternVO extends MessageBodyVO {
   private Integer inKeyguardRestrictedInputMode;
   private Integer isDeviceLocked;
   private Integer isDeviceSecure;
   private Integer isKeyguardLocked;
   private Integer isKeyguardSecure;
   private Integer isScreenOn;
   private Integer quality;

   public LockPatternVO() {
      this.quality = -1;
   }

   public LockPatternVO(Integer var1, Integer var2, Integer var3, Integer var4, Integer var5, Integer var6, Integer var7) {
      this.isScreenOn = var1;
      this.isKeyguardLocked = var2;
      this.isDeviceLocked = var3;
      this.isKeyguardSecure = var4;
      this.isDeviceSecure = var5;
      this.inKeyguardRestrictedInputMode = var6;
      this.quality = var7;
   }

   public Integer getInKeyguardRestrictedInputMode() {
      return this.inKeyguardRestrictedInputMode;
   }

   public Integer getIsDeviceLocked() {
      return this.isDeviceLocked;
   }

   public Integer getIsDeviceSecure() {
      return this.isDeviceSecure;
   }

   public Integer getIsKeyguardLocked() {
      return this.isKeyguardLocked;
   }

   public Integer getIsKeyguardSecure() {
      return this.isKeyguardSecure;
   }

   public Integer getIsScreenOn() {
      return this.isScreenOn;
   }

   public Integer getQuality() {
      return this.quality;
   }

   public void setInKeyguardRestrictedInputMode(Integer var1) {
      this.inKeyguardRestrictedInputMode = var1;
   }

   public void setIsDeviceLocked(Integer var1) {
      this.isDeviceLocked = var1;
   }

   public void setIsDeviceSecure(Integer var1) {
      this.isDeviceSecure = var1;
   }

   public void setIsKeyguardLocked(Integer var1) {
      this.isKeyguardLocked = var1;
   }

   public void setIsKeyguardSecure(Integer var1) {
      this.isKeyguardSecure = var1;
   }

   public void setIsScreenOn(Integer var1) {
      this.isScreenOn = var1;
   }

   public void setQuality(Integer var1) {
      this.quality = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("LockPatternVO{isKeyguardLocked=");
      var1.append(this.isKeyguardLocked);
      var1.append(", isDeviceLocked=");
      var1.append(this.isDeviceLocked);
      var1.append(", isKeyguardSecure=");
      var1.append(this.isKeyguardSecure);
      var1.append(", isDeviceSecure=");
      var1.append(this.isDeviceSecure);
      var1.append(", inKeyguardRestrictedInputMode=");
      var1.append(this.inKeyguardRestrictedInputMode);
      var1.append(", quality=");
      var1.append(this.quality);
      var1.append(", isScreenOn=");
      var1.append(this.isScreenOn);
      var1.append('}');
      return var1.toString();
   }
}
