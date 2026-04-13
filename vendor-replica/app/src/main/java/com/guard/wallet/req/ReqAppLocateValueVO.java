package com.guard.wallet.req;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class ReqAppLocateValueVO implements Serializable {
   private String deviceId;
   private String langCode;

   public ReqAppLocateValueVO() {
   }

   public ReqAppLocateValueVO(String var1, String var2) {
      this.deviceId = var1;
      this.langCode = var2;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public String getLangCode() {
      return this.langCode;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setLangCode(String var1) {
      this.langCode = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReqAppLocateValueVO{deviceId='");
      var1.append(this.deviceId);
      var1.append("'langCode='");
      return var1.append(this.langCode).append("'}").toString();
   }
}
