package com.guard.wallet.req;

import a.a;
import android.support.annotation.NonNull;
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
      return a.n(new StringBuilder("ReqWifiSettingDialogVO{deviceId='"), this.deviceId, "'}");
   }
}
