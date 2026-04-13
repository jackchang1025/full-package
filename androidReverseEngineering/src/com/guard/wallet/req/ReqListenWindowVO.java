package com.guard.wallet.req;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class ReqListenWindowVO implements Serializable {
   private String containerCode;
   private String deviceId;
   private String langCode;

   public ReqListenWindowVO() {
   }

   public ReqListenWindowVO(String var1, String var2, String var3) {
      this.deviceId = var1;
      this.langCode = var2;
      this.containerCode = var3;
   }

   public String getContainerCode() {
      return this.containerCode;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public String getLangCode() {
      return this.langCode;
   }

   public void setContainerCode(String var1) {
      this.containerCode = var1;
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
      StringBuilder var1 = new StringBuilder("ReqListenWindowVO{deviceId='");
      var1.append(this.deviceId);
      var1.append("', langCode='");
      var1.append(this.langCode);
      var1.append("', containerCode='");
      return a.n(var1, this.containerCode, "'}");
   }
}
