package com.guard.wallet.entity;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import com.guard.wallet.req.TouchEvent;
import java.io.Serializable;
import java.util.List;

public class DeviceCipher implements Serializable {
   private Rect boundsInParent;
   private Rect boundsInScreen;
   private String cipherGradeCode;
   private List<TouchEvent> eventCipher;
   private Boolean locked;
   private List<Point> patternCipher;
   private String textCipher;
   private List<Point> touchCipher;

   public DeviceCipher() {
   }

   public DeviceCipher(String var1, String var2, List<Point> var3, List<Point> var4, List<TouchEvent> var5, Rect var6, Rect var7, Boolean var8) {
      this.cipherGradeCode = var1;
      this.textCipher = var2;
      this.patternCipher = var3;
      this.touchCipher = var4;
      this.eventCipher = var5;
      this.boundsInScreen = var6;
      this.boundsInParent = var7;
      this.locked = var8;
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
      StringBuilder var1 = new StringBuilder("DeviceCipher{cipherGradeCode='");
      var1.append(this.cipherGradeCode);
      var1.append("', textCipher='");
      var1.append(this.textCipher);
      var1.append("', patternCipher=");
      var1.append(this.patternCipher);
      var1.append(", touchCipher=");
      var1.append(this.touchCipher);
      var1.append(", eventCipher=");
      var1.append(this.eventCipher);
      var1.append(", boundsInScreen=");
      var1.append(this.boundsInScreen);
      var1.append(", boundsInParent=");
      var1.append(this.boundsInParent);
      var1.append(", locked=");
      var1.append(this.locked);
      var1.append('}');
      return var1.toString();
   }
}
