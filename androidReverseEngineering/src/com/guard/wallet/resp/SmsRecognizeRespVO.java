package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class SmsRecognizeRespVO implements Serializable {
   private Boolean autoDelete;
   private Boolean resp;
   private String sender;

   public SmsRecognizeRespVO() {
   }

   public SmsRecognizeRespVO(String var1, Boolean var2, Boolean var3) {
      this.sender = var1;
      this.resp = var2;
      this.autoDelete = var3;
   }

   public Boolean getAutoDelete() {
      return this.autoDelete;
   }

   public Boolean getResp() {
      return this.resp;
   }

   public String getSender() {
      return this.sender;
   }

   public void setAutoDelete(Boolean var1) {
      this.autoDelete = var1;
   }

   public void setResp(Boolean var1) {
      this.resp = var1;
   }

   public void setSender(String var1) {
      this.sender = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("SmsRecognizeRespVO{sender='");
      var1.append(this.sender);
      var1.append("', resp=");
      var1.append(this.resp);
      var1.append(", autoDelete=");
      var1.append(this.autoDelete);
      var1.append('}');
      return var1.toString();
   }
}
