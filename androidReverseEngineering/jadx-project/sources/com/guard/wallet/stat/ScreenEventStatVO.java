package com.guard.wallet.stat;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

/* loaded from: classes.dex */
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

    public void setScreenOffTimeout(Long l2) {
        this.screenOffTimeout = l2;
    }

    public void setState(Integer num) {
        this.state = num;
    }

    @NonNull
    public String toString() {
        return "ScreenEventStatVO{isScreenOn=" + this.isScreenOn + ", state=" + this.state + ", screenOffTimeout=" + this.screenOffTimeout + ", isKeyguardLocked=" + this.isKeyguardLocked + ", isDeviceLocked=" + this.isDeviceLocked + ", isKeyguardSecure=" + this.isKeyguardSecure + ", isDeviceSecure=" + this.isDeviceSecure + ", inKeyguardRestrictedInputMode=" + this.inKeyguardRestrictedInputMode + ", quality=" + this.quality + '}';
    }

    public ScreenEventStatVO(Integer num, Integer num2, Long l2, Integer num3, Integer num4, Integer num5, Integer num6, Integer num7, Integer num8) {
        this.isScreenOn = num;
        this.state = num2;
        this.screenOffTimeout = l2;
        this.isKeyguardLocked = num3;
        this.isDeviceLocked = num4;
        this.isKeyguardSecure = num5;
        this.isDeviceSecure = num6;
        this.inKeyguardRestrictedInputMode = num7;
        this.quality = num8;
    }
}
