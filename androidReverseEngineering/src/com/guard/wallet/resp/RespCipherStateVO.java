package com.guard.wallet.resp;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.Point;
import com.guard.wallet.req.MessageBodyVO;
import com.guard.wallet.req.TouchEvent;
import java.util.List;

public class RespCipherStateVO extends MessageBodyVO {
   private Rect boundsInParent;
   private Rect boundsInScreen;
   private String cipherGradeCode;
   private String deviceId;
   private List<TouchEvent> eventCipher;
   private String listenId;
   private Integer listenType;
   private List<Point> patternCipher;
   private String subscribeId;
   private String textCipher;
   private List<Point> touchCipher;

   public RespCipherStateVO() {
   }

   public RespCipherStateVO(
      String var1,
      Integer var2,
      String var3,
      String var4,
      String var5,
      String var6,
      List<Point> var7,
      List<Point> var8,
      List<TouchEvent> var9,
      Rect var10,
      Rect var11
   ) {
      this.deviceId = var1;
      this.listenType = var2;
      this.listenId = var3;
      this.subscribeId = var4;
      this.cipherGradeCode = var5;
      this.textCipher = var6;
      this.patternCipher = var7;
      this.touchCipher = var8;
      this.eventCipher = var9;
      this.boundsInScreen = var10;
      this.boundsInParent = var11;
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

   public List<TouchEvent> getEventCipher() {
      return this.eventCipher;
   }

   public String getListenId() {
      return this.listenId;
   }

   public Integer getListenType() {
      return this.listenType;
   }

   public List<Point> getPatternCipher() {
      return this.patternCipher;
   }

   public String getSubscribeId() {
      return this.subscribeId;
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

   public void setEventCipher(List<TouchEvent> var1) {
      this.eventCipher = var1;
   }

   public void setListenId(String var1) {
      this.listenId = var1;
   }

   public void setListenType(Integer var1) {
      this.listenType = var1;
   }

   public void setPatternCipher(List<Point> var1) {
      this.patternCipher = var1;
   }

   public void setSubscribeId(String var1) {
      this.subscribeId = var1;
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
      StringBuilder var1 = new StringBuilder("RespCipherStateVO{deviceId='");
      var1.append(this.deviceId);
      var1.append("', listenType=");
      var1.append(this.listenType);
      var1.append(", listenId='");
      var1.append(this.listenId);
      var1.append("', subscribeId='");
      var1.append(this.subscribeId);
      var1.append("', cipherGradeCode='");
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
      var1.append('}');
      return var1.toString();
   }
}
