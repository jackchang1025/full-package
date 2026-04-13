package com.guard.wallet.stat;

import androidx.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;
import com.guard.wallet.resp.UiObjectVO;

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

   public AccessibilityEventStatVO(
      String var1,
      String var2,
      String var3,
      String var4,
      String var5,
      int var6,
      long var7,
      Integer var9,
      Integer var10,
      KeyboardEventVO var11,
      UiObjectVO var12
   ) {
      System.currentTimeMillis();
      this.containerCode = var1;
      this.activeWindowClassName = var3;
      this.activePackageName = var2;
      this.eventPackageName = var4;
      this.eventClassName = var5;
      this.eventValue = var6;
      this.eventTime = var7;
      this.isDeviceLocked = var9;
      this.isDeviceSecure = var10;
      this.keyboardEvent = var11;
      this.source = var12;
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

   public void setActivePackageName(String var1) {
      this.activePackageName = var1;
   }

   public void setActiveWindowClassName(String var1) {
      this.activeWindowClassName = var1;
   }

   public void setContainerCode(String var1) {
      this.containerCode = var1;
   }

   public void setEventClassName(String var1) {
      this.eventClassName = var1;
   }

   public void setEventPackageName(String var1) {
      this.eventPackageName = var1;
   }

   public void setEventTime(long var1) {
      this.eventTime = var1;
   }

   public void setEventValue(int var1) {
      this.eventValue = var1;
   }

   public void setIsDeviceLocked(Integer var1) {
      this.isDeviceLocked = var1;
   }

   public void setIsDeviceSecure(Integer var1) {
      this.isDeviceSecure = var1;
   }

   public void setKeyboardEvent(KeyboardEventVO var1) {
      this.keyboardEvent = var1;
   }

   public void setSource(UiObjectVO var1) {
      this.source = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("AccessibilityEventStat{activePackageName='");
      var1.append(this.activePackageName);
      var1.append("', activeWindowClassName='");
      var1.append(this.activeWindowClassName);
      var1.append("', eventPackageName='");
      var1.append(this.eventPackageName);
      var1.append("', eventClassName='");
      var1.append(this.eventClassName);
      var1.append("', eventValue=");
      var1.append(this.eventValue);
      var1.append(", eventTime=");
      var1.append(this.eventTime);
      var1.append(", isDeviceLocked=");
      var1.append(this.isDeviceLocked);
      var1.append(", isDeviceSecure=");
      var1.append(this.isDeviceSecure);
      var1.append(", containerCode=");
      var1.append(this.containerCode);
      var1.append(", keyboardEvent=");
      var1.append(this.keyboardEvent);
      var1.append(", source=");
      var1.append(this.source);
      var1.append('}');
      return var1.toString();
   }
}
