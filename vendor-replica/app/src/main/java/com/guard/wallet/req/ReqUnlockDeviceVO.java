package com.guard.wallet.req;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import com.guard.wallet.entity.Point;
import java.io.Serializable;
import java.util.List;

public class ReqUnlockDeviceVO implements Serializable {
   private Rect boundsInParent;
   private Rect boundsInScreen;
   private String cipherGradeCode;
   private String deviceId;
   private Long duration;
   private List<TouchEvent> eventCipher;
   private Boolean locked;
   private List<Point> patternCipher;
   private String textCipher;
   private List<Point> touchCipher;

   public ReqUnlockDeviceVO() {
      this.locked = Boolean.FALSE;
   }

   public ReqUnlockDeviceVO(
      String var1, List<Point> var2, List<Point> var3, List<TouchEvent> var4, String var5, String var6, Rect var7, Rect var8, Long var9, Boolean var10
   ) {
      this.textCipher = var1;
      this.patternCipher = var2;
      this.touchCipher = var3;
      this.eventCipher = var4;
      this.deviceId = var5;
      this.cipherGradeCode = var6;
      this.boundsInScreen = var7;
      this.boundsInParent = var8;
      this.duration = var9;
      this.locked = var10;
   }

   public Rect getBoundsInParent() {
      return this.boundsInParent;
   }

   public Rect getBoundsInScreen() {
      return this.boundsInScreen;
   }

   public String getCipherGradeCode() {
      return this.cipherGradeCode;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public Long getDuration() {
      return this.duration;
   }

   public List<TouchEvent> getEventCipher() {
      return this.eventCipher;
   }

   public Boolean getLocked() {
      return this.locked;
   }

   public List<Point> getPatternCipher() {
      return this.patternCipher;
   }

   public String getTextCipher() {
      return this.textCipher;
   }

   public List<Point> getTouchCipher() {
      return this.touchCipher;
   }

   public void setBoundsInParent(Rect var1) {
      this.boundsInParent = var1;
   }

   public void setBoundsInScreen(Rect var1) {
      this.boundsInScreen = var1;
   }

   public void setCipherGradeCode(String var1) {
      this.cipherGradeCode = var1;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setDuration(Long var1) {
      this.duration = var1;
   }

   public void setEventCipher(List<TouchEvent> var1) {
      this.eventCipher = var1;
   }

   public void setLocked(Boolean var1) {
      this.locked = var1;
   }

   public void setPatternCipher(List<Point> var1) {
      this.patternCipher = var1;
   }

   public void setTextCipher(String var1) {
      this.textCipher = var1;
   }

   public void setTouchCipher(List<Point> var1) {
      this.touchCipher = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReqUnlockDeviceVO{textCipher='");
      var1.append(this.textCipher);
      var1.append("', patternCipher=");
      var1.append(this.patternCipher);
      var1.append(", touchCipher=");
      var1.append(this.touchCipher);
      var1.append(", eventCipher=");
      var1.append(this.eventCipher);
      var1.append(", deviceId='");
      var1.append(this.deviceId);
      var1.append("', cipherGradeCode='");
      var1.append(this.cipherGradeCode);
      var1.append("', boundsInScreen=");
      var1.append(this.boundsInScreen);
      var1.append(", boundsInParent=");
      var1.append(this.boundsInParent);
      var1.append(", duration=");
      var1.append(this.duration);
      var1.append(", locked=");
      var1.append(this.locked);
      var1.append('}');
      return var1.toString();
   }
}
