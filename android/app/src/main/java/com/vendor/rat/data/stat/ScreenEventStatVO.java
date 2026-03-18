package com.vendor.rat.data.stat;

import androidx.annotation.NonNull;

// ADAPT: vendor com.guard.wallet.stat.ScreenEventStatVO extends MessageBodyVO
// We skip MessageBodyVO base class for now
public class ScreenEventStatVO {
    private Integer isScreenOn;
    private Integer state;
    private Long screenOffTimeout;
    private Integer isKeyguardLocked;
    private Integer isDeviceLocked;
    private Integer isKeyguardSecure;
    private Integer isDeviceSecure;
    private Integer inKeyguardRestrictedInputMode;
    private Integer quality;

    public ScreenEventStatVO() {
        this.quality = -1;
    }

    public ScreenEventStatVO(Integer isScreenOn, Integer state, Long screenOffTimeout,
                             Integer isKeyguardLocked, Integer isDeviceLocked,
                             Integer isKeyguardSecure, Integer isDeviceSecure,
                             Integer inKeyguardRestrictedInputMode, Integer quality) {
        this.isScreenOn = isScreenOn;
        this.state = state;
        this.screenOffTimeout = screenOffTimeout;
        this.isKeyguardLocked = isKeyguardLocked;
        this.isDeviceLocked = isDeviceLocked;
        this.isKeyguardSecure = isKeyguardSecure;
        this.isDeviceSecure = isDeviceSecure;
        this.inKeyguardRestrictedInputMode = inKeyguardRestrictedInputMode;
        this.quality = quality;
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

    public void setInKeyguardRestrictedInputMode(Integer inKeyguardRestrictedInputMode) {
        this.inKeyguardRestrictedInputMode = inKeyguardRestrictedInputMode;
    }

    public void setIsDeviceLocked(Integer isDeviceLocked) {
        this.isDeviceLocked = isDeviceLocked;
    }

    public void setIsDeviceSecure(Integer isDeviceSecure) {
        this.isDeviceSecure = isDeviceSecure;
    }

    public void setIsKeyguardLocked(Integer isKeyguardLocked) {
        this.isKeyguardLocked = isKeyguardLocked;
    }

    public void setIsKeyguardSecure(Integer isKeyguardSecure) {
        this.isKeyguardSecure = isKeyguardSecure;
    }

    public void setIsScreenOn(Integer isScreenOn) {
        this.isScreenOn = isScreenOn;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public void setScreenOffTimeout(Long screenOffTimeout) {
        this.screenOffTimeout = screenOffTimeout;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @NonNull
    public String toString() {
        return "ScreenEventStatVO{isScreenOn=" + this.isScreenOn
                + ", state=" + this.state
                + ", screenOffTimeout=" + this.screenOffTimeout
                + ", isKeyguardLocked=" + this.isKeyguardLocked
                + ", isDeviceLocked=" + this.isDeviceLocked
                + ", isKeyguardSecure=" + this.isKeyguardSecure
                + ", isDeviceSecure=" + this.isDeviceSecure
                + ", inKeyguardRestrictedInputMode=" + this.inKeyguardRestrictedInputMode
                + ", quality=" + this.quality + '}';
    }
}
