package com.guard.wallet.stat;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;
import com.guard.wallet.resp.UiObjectVO;

/* loaded from: classes.dex */
public class AccessibilityEventStatVO extends MessageBodyVO {
    private String activePackageName;
    private String activeWindowClassName;
    private String containerCode;
    private String eventClassName;
    private String eventPackageName;
    private long eventTime;
    private int eventValue;
    private Integer isDeviceLocked;
    private Integer isDeviceSecure;
    private KeyboardEventVO keyboardEvent;
    private UiObjectVO source;

    public AccessibilityEventStatVO() {
        this.eventTime = System.currentTimeMillis();
    }

    public String getActivePackageName() {
        return this.activePackageName;
    }

    public String getActiveWindowClassName() {
        return this.activeWindowClassName;
    }

    public String getContainerCode() {
        return this.containerCode;
    }

    public String getEventClassName() {
        return this.eventClassName;
    }

    public String getEventPackageName() {
        return this.eventPackageName;
    }

    public long getEventTime() {
        return this.eventTime;
    }

    public int getEventValue() {
        return this.eventValue;
    }

    public Integer getIsDeviceLocked() {
        return this.isDeviceLocked;
    }

    public Integer getIsDeviceSecure() {
        return this.isDeviceSecure;
    }

    public KeyboardEventVO getKeyboardEvent() {
        return this.keyboardEvent;
    }

    public UiObjectVO getSource() {
        return this.source;
    }

    public void setActivePackageName(String str) {
        this.activePackageName = str;
    }

    public void setActiveWindowClassName(String str) {
        this.activeWindowClassName = str;
    }

    public void setContainerCode(String str) {
        this.containerCode = str;
    }

    public void setEventClassName(String str) {
        this.eventClassName = str;
    }

    public void setEventPackageName(String str) {
        this.eventPackageName = str;
    }

    public void setEventTime(long j2) {
        this.eventTime = j2;
    }

    public void setEventValue(int i2) {
        this.eventValue = i2;
    }

    public void setIsDeviceLocked(Integer num) {
        this.isDeviceLocked = num;
    }

    public void setIsDeviceSecure(Integer num) {
        this.isDeviceSecure = num;
    }

    public void setKeyboardEvent(KeyboardEventVO keyboardEventVO) {
        this.keyboardEvent = keyboardEventVO;
    }

    public void setSource(UiObjectVO uiObjectVO) {
        this.source = uiObjectVO;
    }

    @NonNull
    public String toString() {
        return "AccessibilityEventStat{activePackageName='" + this.activePackageName + "', activeWindowClassName='" + this.activeWindowClassName + "', eventPackageName='" + this.eventPackageName + "', eventClassName='" + this.eventClassName + "', eventValue=" + this.eventValue + ", eventTime=" + this.eventTime + ", isDeviceLocked=" + this.isDeviceLocked + ", isDeviceSecure=" + this.isDeviceSecure + ", containerCode=" + this.containerCode + ", keyboardEvent=" + this.keyboardEvent + ", source=" + this.source + '}';
    }

    public AccessibilityEventStatVO(String str, String str2, String str3, String str4, String str5, int i2, long j2, Integer num, Integer num2, KeyboardEventVO keyboardEventVO, UiObjectVO uiObjectVO) {
        System.currentTimeMillis();
        this.containerCode = str;
        this.activeWindowClassName = str3;
        this.activePackageName = str2;
        this.eventPackageName = str4;
        this.eventClassName = str5;
        this.eventValue = i2;
        this.eventTime = j2;
        this.isDeviceLocked = num;
        this.isDeviceSecure = num2;
        this.keyboardEvent = keyboardEventVO;
        this.source = uiObjectVO;
    }
}
