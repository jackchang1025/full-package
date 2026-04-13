package com.guard.wallet.resp;

import a.a;
import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

public class DeviceSmsRecognizeVO extends MessageBodyVO {
   private String content;
   private String deviceId;
   private String plugId;
   private String recognizeContent;
   private String sender;

   public DeviceSmsRecognizeVO() {
   }

   public DeviceSmsRecognizeVO(String var1, String var2, String var3, String var4, String var5) {
      this.deviceId = var1;
      this.plugId = var2;
      this.sender = var3;
      this.content = var4;
      this.recognizeContent = var5;
   }

   public String getContent() {
      return this.content;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public String getPlugId() {
      return this.plugId;
   }

   public String getRecognizeContent() {
      return this.recognizeContent;
   }

   public String getSender() {
      return this.sender;
   }

   public void setContent(String var1) {
      this.content = var1;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setPlugId(String var1) {
      this.plugId = var1;
   }

   public void setRecognizeContent(String var1) {
      this.recognizeContent = var1;
   }

   public void setSender(String var1) {
      this.sender = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("DeviceSmsRecognizeVO{deviceId='");
      var1.append(this.deviceId);
      var1.append("',plugId='");
      var1.append(this.plugId);
      var1.append("', sender='");
      var1.append(this.sender);
      var1.append("', content='");
      var1.append(this.content);
      var1.append("', recognizeContent='");
      return a.n(var1, this.recognizeContent, "'}");
   }
}
