package com.guard.wallet.req;

import androidx.annotation.NonNull;

public class ScreenMetricsVO extends MessageBodyVO {
   private Integer density;
   private Integer height;
   private Integer inKeyguardRestrictedInputMode;
   private Integer isBlocked;
   private Integer isDeviceLocked;
   private Integer isDeviceSecure;
   private Integer isKeyguardLocked;
   private Integer isKeyguardSecure;
   private Integer isScreenOn;
   private Integer isScreenRound;
   private Integer navigationBarHeight;
   private Integer quality;
   private Float scaledDensity;
   private Long screenOffTimeout;
   private Integer state;
   private Integer statusBarHeight;
   private Integer width;
   private Float xdpi;
   private Float ydpi;

   public ScreenMetricsVO() {
      this.quality = -1;
   }

   public ScreenMetricsVO(
      Integer var1,
      Integer var2,
      Integer var3,
      Float var4,
      Float var5,
      Float var6,
      Integer var7,
      Integer var8,
      Long var9,
      Integer var10,
      Integer var11,
      Integer var12,
      Integer var13,
      Integer var14,
      Integer var15,
      Integer var16,
      Integer var17,
      Integer var18,
      Integer var19
   ) {
      this.width = var1;
      this.height = var2;
      this.density = var3;
      this.scaledDensity = var4;
      this.xdpi = var5;
      this.ydpi = var6;
      this.isScreenOn = var7;
      this.state = var8;
      this.screenOffTimeout = var9;
      this.isKeyguardLocked = var10;
      this.isDeviceLocked = var11;
      this.isKeyguardSecure = var12;
      this.isDeviceSecure = var13;
      this.inKeyguardRestrictedInputMode = var14;
      this.quality = var15;
      this.statusBarHeight = var16;
      this.navigationBarHeight = var17;
      this.isScreenRound = var18;
      this.isBlocked = var19;
   }

   public Integer getDensity() {
      return this.density;
   }

   public Integer getHeight() {
      return this.height;
   }

   public Integer getInKeyguardRestrictedInputMode() {
      return this.inKeyguardRestrictedInputMode;
   }

   public Integer getIsBlocked() {
      return this.isBlocked;
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

   public Integer getIsScreenRound() {
      return this.isScreenRound;
   }

   public Integer getNavigationBarHeight() {
      return this.navigationBarHeight;
   }

   public Integer getQuality() {
      return this.quality;
   }

   public Float getScaledDensity() {
      return this.scaledDensity;
   }

   public Long getScreenOffTimeout() {
      return this.screenOffTimeout;
   }

   public Integer getState() {
      return this.state;
   }

   public Integer getStatusBarHeight() {
      return this.statusBarHeight;
   }

   public Integer getWidth() {
      return this.width;
   }

   public Float getXdpi() {
      return this.xdpi;
   }

   public Float getYdpi() {
      return this.ydpi;
   }

   public void setDensity(Integer var1) {
      this.density = var1;
   }

   public void setHeight(Integer var1) {
      this.height = var1;
   }

   public void setInKeyguardRestrictedInputMode(Integer var1) {
      this.inKeyguardRestrictedInputMode = var1;
   }

   public void setIsBlocked(Integer var1) {
      this.isBlocked = var1;
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

   public void setIsScreenRound(Integer var1) {
      this.isScreenRound = var1;
   }

   public void setNavigationBarHeight(Integer var1) {
      this.navigationBarHeight = var1;
   }

   public void setQuality(Integer var1) {
      this.quality = var1;
   }

   public void setScaledDensity(Float var1) {
      this.scaledDensity = var1;
   }

   public void setScreenOffTimeout(Long var1) {
      this.screenOffTimeout = var1;
   }

   public void setState(Integer var1) {
      this.state = var1;
   }

   public void setStatusBarHeight(Integer var1) {
      this.statusBarHeight = var1;
   }

   public void setWidth(Integer var1) {
      this.width = var1;
   }

   public void setXdpi(Float var1) {
      this.xdpi = var1;
   }

   public void setYdpi(Float var1) {
      this.ydpi = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ScreenMetricsVO{width=");
      var1.append(this.width);
      var1.append(", height=");
      var1.append(this.height);
      var1.append(", density=");
      var1.append(this.density);
      var1.append(", scaledDensity=");
      var1.append(this.scaledDensity);
      var1.append(", xdpi=");
      var1.append(this.xdpi);
      var1.append(", ydpi=");
      var1.append(this.ydpi);
      var1.append(", isScreenOn=");
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
      var1.append(", statusBarHeight=");
      var1.append(this.statusBarHeight);
      var1.append(", navigationBarHeight=");
      var1.append(this.navigationBarHeight);
      var1.append(", isScreenRound=");
      var1.append(this.isScreenRound);
      var1.append(", isBlocked=");
      var1.append(this.isBlocked);
      var1.append('}');
      return var1.toString();
   }
}
