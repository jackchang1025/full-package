package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class RewriteDebugPortVO implements Serializable {
   private Integer debugPort;
   private String deviceId;

   public RewriteDebugPortVO() {
   }

   public RewriteDebugPortVO(String var1, Integer var2) {
      this.deviceId = var1;
      this.debugPort = var2;
   }

   public Integer getDebugPort() {
      return this.debugPort;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public void setDebugPort(Integer var1) {
      this.debugPort = var1;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("RewriteDebugPortVO{deviceId='");
      var1.append(this.deviceId);
      var1.append("', debugPort=");
      var1.append(this.debugPort);
      var1.append('}');
      return var1.toString();
   }
}
