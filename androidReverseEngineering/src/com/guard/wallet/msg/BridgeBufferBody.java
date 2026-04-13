package com.guard.wallet.msg;

import android.support.annotation.NonNull;

public class BridgeBufferBody extends BaseMsgBody {
   private String bridgePath;
   private String buffer;
   private String deviceId;
   private Boolean toDesktop;

   public BridgeBufferBody() {
   }

   public BridgeBufferBody(String var1, String var2, Boolean var3, String var4) {
      this.deviceId = var1;
      this.bridgePath = var2;
      this.toDesktop = var3;
      this.buffer = var4;
   }

   public String getBridgePath() {
      return this.bridgePath;
   }

   public String getBuffer() {
      return this.buffer;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public Boolean getToDesktop() {
      return this.toDesktop;
   }

   public void setBridgePath(String var1) {
      this.bridgePath = var1;
   }

   public void setBuffer(String var1) {
      this.buffer = var1;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setToDesktop(Boolean var1) {
      this.toDesktop = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("BridgeBufferBody{deviceId='");
      var1.append(this.deviceId);
      var1.append("', bridgePath='");
      var1.append(this.bridgePath);
      var1.append("', toDesktop=");
      var1.append(this.toDesktop);
      var1.append(", buffer=");
      var1.append(this.buffer);
      var1.append('}');
      return var1.toString();
   }
}
