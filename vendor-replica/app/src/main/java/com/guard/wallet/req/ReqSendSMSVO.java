package com.guard.wallet.req;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class ReqSendSMSVO implements Serializable {
   private String content;
   private String phoneNumber;

   public ReqSendSMSVO() {
   }

   public ReqSendSMSVO(String var1, String var2) {
      this.phoneNumber = var1;
      this.content = var2;
   }

   public String getContent() {
      return this.content;
   }

   public String getPhoneNumber() {
      return this.phoneNumber;
   }

   public void setContent(String var1) {
      this.content = var1;
   }

   public void setPhoneNumber(String var1) {
      this.phoneNumber = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReqSendSMSVO{phoneNumber='");
      var1.append(this.phoneNumber);
      var1.append("', content='");
      return var1.append(this.content).append("'}").toString();
   }
}
