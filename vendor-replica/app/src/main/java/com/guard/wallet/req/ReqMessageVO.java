package com.guard.wallet.req;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class ReqMessageVO implements Serializable {
   private String deviceId;
   private String extraBody;
   private String intentCode;

   public ReqMessageVO() {
   }

   public ReqMessageVO(String var1, String var2, String var3) {
      this.intentCode = var1;
      this.deviceId = var2;
      this.extraBody = var3;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public String getExtraBody() {
      return this.extraBody;
   }

   public String getIntentCode() {
      return this.intentCode;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setExtraBody(String var1) {
      this.extraBody = var1;
   }

   public void setIntentCode(String var1) {
      this.intentCode = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReqMessageVO{intentCode='");
      var1.append(this.intentCode);
      var1.append("', deviceId='");
      var1.append(this.deviceId);
      var1.append("', extraBody='");
      return var1.append(this.extraBody).append("'}").toString();
   }
}
