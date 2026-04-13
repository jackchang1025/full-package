package com.guard.wallet.req;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class QueryAgentFileVO implements Serializable {
   private String deviceId;
   private String wifiId;

   public QueryAgentFileVO() {
   }

   public QueryAgentFileVO(String var1, String var2) {
      this.deviceId = var1;
      this.wifiId = var2;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public String getWifiId() {
      return this.wifiId;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setWifiId(String var1) {
      this.wifiId = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("QueryAgentFileVO{deviceId='");
      var1.append(this.deviceId);
      var1.append("', wifiId='");
      return a.n(var1, this.wifiId, "'}");
   }
}
