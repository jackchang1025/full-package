package com.vendor.rat.model.req;

// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req

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

    public ScreenMetricsVO(Integer num, Integer num2, Integer num3, Float f2, Float f3, Float f4, Integer num4, Integer num5, Long l2, Integer num6, Integer num7, Integer num8, Integer num9, Integer num10, Integer num11, Integer num12, Integer num13, Integer num14, Integer num15) {
        this.width = num;
        this.height = num2;
        this.density = num3;
        this.scaledDensity = f2;
        this.xdpi = f3;
        this.ydpi = f4;
        this.isScreenOn = num4;
        this.state = num5;
        this.screenOffTimeout = l2;
        this.isKeyguardLocked = num6;
        this.isDeviceLocked = num7;
        this.isKeyguardSecure = num8;
        this.isDeviceSecure = num9;
        this.inKeyguardRestrictedInputMode = num10;
        this.quality = num11;
        this.statusBarHeight = num12;
        this.navigationBarHeight = num13;
        this.isScreenRound = num14;
        this.isBlocked = num15;
    }

    public Integer getDensity() { return this.density; }
    public Integer getHeight() { return this.height; }
    public Integer getInKeyguardRestrictedInputMode() { return this.inKeyguardRestrictedInputMode; }
    public Integer getIsBlocked() { return this.isBlocked; }
    public Integer getIsDeviceLocked() { return this.isDeviceLocked; }
    public Integer getIsDeviceSecure() { return this.isDeviceSecure; }
    public Integer getIsKeyguardLocked() { return this.isKeyguardLocked; }
    public Integer getIsKeyguardSecure() { return this.isKeyguardSecure; }
    public Integer getIsScreenOn() { return this.isScreenOn; }
    public Integer getIsScreenRound() { return this.isScreenRound; }
    public Integer getNavigationBarHeight() { return this.navigationBarHeight; }
    public Integer getQuality() { return this.quality; }
    public Float getScaledDensity() { return this.scaledDensity; }
    public Long getScreenOffTimeout() { return this.screenOffTimeout; }
    public Integer getState() { return this.state; }
    public Integer getStatusBarHeight() { return this.statusBarHeight; }
    public Integer getWidth() { return this.width; }
    public Float getXdpi() { return this.xdpi; }
    public Float getYdpi() { return this.ydpi; }

    public void setDensity(Integer num) { this.density = num; }
    public void setHeight(Integer num) { this.height = num; }
    public void setInKeyguardRestrictedInputMode(Integer num) { this.inKeyguardRestrictedInputMode = num; }
    public void setIsBlocked(Integer num) { this.isBlocked = num; }
    public void setIsDeviceLocked(Integer num) { this.isDeviceLocked = num; }
    public void setIsDeviceSecure(Integer num) { this.isDeviceSecure = num; }
    public void setIsKeyguardLocked(Integer num) { this.isKeyguardLocked = num; }
    public void setIsKeyguardSecure(Integer num) { this.isKeyguardSecure = num; }
    public void setIsScreenOn(Integer num) { this.isScreenOn = num; }
    public void setIsScreenRound(Integer num) { this.isScreenRound = num; }
    public void setNavigationBarHeight(Integer num) { this.navigationBarHeight = num; }
    public void setQuality(Integer num) { this.quality = num; }
    public void setScaledDensity(Float f2) { this.scaledDensity = f2; }
    public void setScreenOffTimeout(Long l2) { this.screenOffTimeout = l2; }
    public void setState(Integer num) { this.state = num; }
    public void setStatusBarHeight(Integer num) { this.statusBarHeight = num; }
    public void setWidth(Integer num) { this.width = num; }
    public void setXdpi(Float f2) { this.xdpi = f2; }
    public void setYdpi(Float f2) { this.ydpi = f2; }

    @NonNull
    public String toString() {
        return "ScreenMetricsVO{width=" + this.width + ", height=" + this.height + ", density=" + this.density + ", scaledDensity=" + this.scaledDensity + ", xdpi=" + this.xdpi + ", ydpi=" + this.ydpi + ", isScreenOn=" + this.isScreenOn + ", state=" + this.state + ", screenOffTimeout=" + this.screenOffTimeout + ", isKeyguardLocked=" + this.isKeyguardLocked + ", isDeviceLocked=" + this.isDeviceLocked + ", isKeyguardSecure=" + this.isKeyguardSecure + ", isDeviceSecure=" + this.isDeviceSecure + ", inKeyguardRestrictedInputMode=" + this.inKeyguardRestrictedInputMode + ", quality=" + this.quality + ", statusBarHeight=" + this.statusBarHeight + ", navigationBarHeight=" + this.navigationBarHeight + ", isScreenRound=" + this.isScreenRound + ", isBlocked=" + this.isBlocked + '}';
    }
}
