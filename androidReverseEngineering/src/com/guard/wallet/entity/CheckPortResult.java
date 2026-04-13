package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class CheckPortResult implements Serializable {
   private boolean connected;
   private String connectedDevice;
   private Integer debugPort;

   public CheckPortResult() {
   }

   public CheckPortResult(boolean var1, Integer var2, String var3) {
      this.connected = var1;
      this.debugPort = var2;
      this.connectedDevice = var3;
   }

   public String getConnectedDevice() {
      return this.connectedDevice;
   }

   public Integer getDebugPort() {
      return this.debugPort;
   }

   public boolean isConnected() {
      return this.connected;
   }

   public void setConnected(boolean var1) {
      this.connected = var1;
   }

   public void setConnectedDevice(String var1) {
      this.connectedDevice = var1;
   }

   public void setDebugPort(Integer var1) {
      this.debugPort = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("CheckPortResult{connected=");
      var1.append(this.connected);
      var1.append(", debugPort=");
      var1.append(this.debugPort);
      var1.append(", connectedDevice=");
      var1.append(this.connectedDevice);
      var1.append('}');
      return var1.toString();
   }
}
