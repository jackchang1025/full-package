package com.guard.wallet.req;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class ReqWifiSettingDialogVO implements Serializable {
   private String deviceId;

   public ReqWifiSettingDialogVO(String var1) {
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
      return new StringBuilder("ReqWifiSettingDialogVO{deviceId='").append(this.deviceId).append("'}").toString();
   }
}
