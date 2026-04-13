package com.guard.wallet.msg;

import a.a;
import android.support.annotation.NonNull;

public class BridgeBody extends BaseMsgBody {
   private String bridgePath;
   private String deviceId;

   public BridgeBody() {
   }

   public BridgeBody(String var1, String var2) {
      this.deviceId = var1;
      this.bridgePath = var2;
   }

   public String getBridgePath() {
      return this.bridgePath;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public void setBridgePath(String var1) {
      this.bridgePath = var1;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("BridgeBody{deviceId='");
      var1.append(this.deviceId);
      var1.append("', bridgePath='");
      return a.n(var1, this.bridgePath, "'}");
   }
}
