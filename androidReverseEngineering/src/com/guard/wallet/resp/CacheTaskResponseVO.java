package com.guard.wallet.resp;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class CacheTaskResponseVO implements Serializable {
   private String containerCode;
   private String deviceId;
   private String reqUri;
   private String response;

   public CacheTaskResponseVO() {
   }

   public CacheTaskResponseVO(String var1, String var2, String var3, String var4) {
      this.deviceId = var1;
      this.reqUri = var2;
      this.containerCode = var3;
      this.response = var4;
   }

   public String getContainerCode() {
      return this.containerCode;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public String getReqUri() {
      return this.reqUri;
   }

   public String getResponse() {
      return this.response;
   }

   public void setContainerCode(String var1) {
      this.containerCode = var1;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setReqUri(String var1) {
      this.reqUri = var1;
   }

   public void setResponse(String var1) {
      this.response = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("CacheTaskResultVO{deviceId='");
      var1.append(this.deviceId);
      var1.append("', reqUri='");
      var1.append(this.reqUri);
      var1.append("', containerCode='");
      var1.append(this.containerCode);
      var1.append("', response='");
      return a.n(var1, this.response, "'}");
   }
}
