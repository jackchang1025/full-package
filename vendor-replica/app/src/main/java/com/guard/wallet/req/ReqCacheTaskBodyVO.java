package com.guard.wallet.req;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class ReqCacheTaskBodyVO implements Serializable {
   private String containerCode;
   private String deviceId;

   public ReqCacheTaskBodyVO() {
   }

   public ReqCacheTaskBodyVO(String var1, String var2) {
      this.deviceId = var1;
      this.containerCode = var2;
   }

   public String getContainerCode() {
      return this.containerCode;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public void setContainerCode(String var1) {
      this.containerCode = var1;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReqCacheTaskBodyVO{deviceId='");
      var1.append(this.deviceId);
      var1.append("', containerCode='");
      return var1.append(this.containerCode).append("'}").toString();
   }
}
