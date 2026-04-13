package com.guard.wallet.entity;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class WIFIState implements Serializable {
   private String localIp;
   private String macAddress;
   private String wifiId;

   public WIFIState() {
   }

   public WIFIState(String var1, String var2, String var3) {
      this.macAddress = var1;
      this.wifiId = var2;
      this.localIp = var3;
   }

   public String getLocalIp() {
      return this.localIp;
   }

   public String getMacAddress() {
      return this.macAddress;
   }

   public String getWifiId() {
      return this.wifiId;
   }

   public void setLocalIp(String var1) {
      this.localIp = var1;
   }

   public void setMacAddress(String var1) {
      this.macAddress = var1;
   }

   public void setWifiId(String var1) {
      this.wifiId = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("WIFIState{macAddress='");
      var1.append(this.macAddress);
      var1.append("', wifiId='");
      var1.append(this.wifiId);
      var1.append("', localIp='");
      return a.n(var1, this.localIp, "'}");
   }
}
