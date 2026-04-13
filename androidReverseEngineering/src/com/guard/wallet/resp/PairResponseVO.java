package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class PairResponseVO implements Serializable {
   private Integer debugPort;
   private String deviceId;
   private boolean isConnected;
   private boolean isPaired;

   public PairResponseVO() {
   }

   public PairResponseVO(String var1, boolean var2, Integer var3, boolean var4) {
      this.deviceId = var1;
      this.isPaired = var2;
      this.debugPort = var3;
      this.isConnected = var4;
   }

   public Integer getDebugPort() {
      return this.debugPort;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public boolean isConnected() {
      return this.isConnected;
   }

   public boolean isPaired() {
      return this.isPaired;
   }

   public void setConnected(boolean var1) {
      this.isConnected = var1;
   }

   public void setDebugPort(Integer var1) {
      this.debugPort = var1;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setPaired(boolean var1) {
      this.isPaired = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("PairResponseVO{deviceId='");
      var1.append(this.deviceId);
      var1.append("', isPaired=");
      var1.append(this.isPaired);
      var1.append("', debugPort=");
      var1.append(this.debugPort);
      var1.append("', isConnected=");
      var1.append(this.isConnected);
      var1.append('}');
      return var1.toString();
   }
}
