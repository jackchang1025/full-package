package com.guard.wallet.stat;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

public class ScreenEventStatVO extends MessageBodyVO {
   private Integer inKeyguardRestrictedInputMode;
   private Integer isDeviceLocked;
   private Integer isDeviceSecure;
   private Integer isKeyguardLocked;
   private Integer isKeyguardSecure;
   private Integer isScreenOn;
   private Integer quality;
   private Long screenOffTimeout;
   private Integer state;

   public ScreenEventStatVO() {
      this.quality = -1;
   }

   public ScreenEventStatVO(Integer var1, Integer var2, Long var3, Integer var4, Integer var5, Integer var6, Integer var7, Integer var8, Integer var9) {
      this.isScreenOn = var1;
      this.state = var2;
      this.screenOffTimeout = var3;
      this.isKeyguardLocked = var4;
      this.isDeviceLocked = var5;
      this.isKeyguardSecure = var6;
      this.isDeviceSecure = var7;
      this.inKeyguardRestrictedInputMode = var8;
      this.quality = var9;
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

   public Long getScreenOffTimeout() {
      return this.screenOffTimeout;
   }

   public Integer getState() {
      return this.state;
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

   public void setScreenOffTimeout(Long var1) {
      this.screenOffTimeout = var1;
   }

   public void setState(Integer var1) {
      this.state = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ScreenEventStatVO{isScreenOn=");
      var1.append(this.isScreenOn);
      var1.append(", state=");
      var1.append(this.state);
      var1.append(", screenOffTimeout=");
      var1.append(this.screenOffTimeout);
      var1.append(", isKeyguardLocked=");
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
      var1.append('}');
      return var1.toString();
   }
}
