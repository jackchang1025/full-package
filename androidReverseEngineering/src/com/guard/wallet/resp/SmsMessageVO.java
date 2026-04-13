package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

public class SmsMessageVO extends MessageBodyVO {
   private String content;
   private String sender;
   private String senderName;
   private String smsFormat;
   private String smsTime;
   private Integer smsType;

   public SmsMessageVO() {
   }

   public SmsMessageVO(String var1, String var2, String var3, String var4, String var5, Integer var6) {
      this.sender = var1;
      this.senderName = var2;
      this.content = var3;
      this.smsFormat = var4;
      this.smsTime = var5;
      this.smsType = var6;
   }

   public String getContent() {
      return this.content;
   }

   public String getSender() {
      return this.sender;
   }

   public String getSenderName() {
      return this.senderName;
   }

   public String getSmsFormat() {
      return this.smsFormat;
   }

   public String getSmsTime() {
      return this.smsTime;
   }

   public Integer getSmsType() {
      return this.smsType;
   }

   public void setContent(String var1) {
      this.content = var1;
   }

   public void setSender(String var1) {
      this.sender = var1;
   }

   public void setSenderName(String var1) {
      this.senderName = var1;
   }

   public void setSmsFormat(String var1) {
      this.smsFormat = var1;
   }

   public void setSmsTime(String var1) {
      this.smsTime = var1;
   }

   public void setSmsType(Integer var1) {
      this.smsType = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("SmsMessageVO{sender='");
      var1.append(this.sender);
      var1.append("', senderName='");
      var1.append(this.senderName);
      var1.append("', content='");
      var1.append(this.content);
      var1.append("', smsFormat='");
      var1.append(this.smsFormat);
      var1.append("', smsTime='");
      var1.append(this.smsTime);
      var1.append("', smsType='");
      var1.append(this.smsType);
      var1.append("'}");
      return var1.toString();
   }
}
