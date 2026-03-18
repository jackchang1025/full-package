package com.vendor.rat.model.req;

// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req

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

    public LockPatternVO(Integer num, Integer num2, Integer num3, Integer num4, Integer num5, Integer num6, Integer num7) {
        this.isScreenOn = num;
        this.isKeyguardLocked = num2;
        this.isDeviceLocked = num3;
        this.isKeyguardSecure = num4;
        this.isDeviceSecure = num5;
        this.inKeyguardRestrictedInputMode = num6;
        this.quality = num7;
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

    public void setInKeyguardRestrictedInputMode(Integer num) {
        this.inKeyguardRestrictedInputMode = num;
    }

    public void setIsDeviceLocked(Integer num) {
        this.isDeviceLocked = num;
    }

    public void setIsDeviceSecure(Integer num) {
        this.isDeviceSecure = num;
    }

    public void setIsKeyguardLocked(Integer num) {
        this.isKeyguardLocked = num;
    }

    public void setIsKeyguardSecure(Integer num) {
        this.isKeyguardSecure = num;
    }

    public void setIsScreenOn(Integer num) {
        this.isScreenOn = num;
    }

    public void setQuality(Integer num) {
        this.quality = num;
    }

    @NonNull
    public String toString() {
        return "LockPatternVO{isKeyguardLocked=" + this.isKeyguardLocked + ", isDeviceLocked=" + this.isDeviceLocked + ", isKeyguardSecure=" + this.isKeyguardSecure + ", isDeviceSecure=" + this.isDeviceSecure + ", inKeyguardRestrictedInputMode=" + this.inKeyguardRestrictedInputMode + ", quality=" + this.quality + ", isScreenOn=" + this.isScreenOn + '}';
    }
}
