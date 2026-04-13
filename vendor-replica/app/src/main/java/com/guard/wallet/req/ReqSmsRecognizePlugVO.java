package com.guard.wallet.req;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class ReqSmsRecognizePlugVO implements Serializable {
   private String deviceId;

   public ReqSmsRecognizePlugVO() {
   }

   public ReqSmsRecognizePlugVO(String var1) {
      this.deviceId = var1;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   @NonNull
   @Override
   public String toString() {
      return new StringBuilder("ReqSmsRecognizePlugVO{deviceId='").append(this.deviceId).append("'}").toString();
   }
}
