package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class ADBConfig implements Serializable {
   private int connectErrorCount;
   private boolean connected;
   private String connectedDevice;
   private Integer debugPort;
   private int enableDebug;
   private int enableDevelopment;
   private int enableWifiDebug;
   private int installedRatHat;
   private int isRatHatRunning;
   private boolean paired;
   private long updateTime;

   public ADBConfig() {
   }

   public ADBConfig(int var1, int var2, int var3, boolean var4, boolean var5, int var6, Integer var7, String var8, int var9, int var10) {
      this.enableDevelopment = var1;
      this.enableDebug = var2;
      this.enableWifiDebug = var3;
      this.paired = var4;
      this.connected = var5;
      this.connectErrorCount = var6;
      this.debugPort = var7;
      this.connectedDevice = var8;
      this.installedRatHat = var9;
      this.isRatHatRunning = var10;
   }

   public int getConnectErrorCount() {
      return this.connectErrorCount;
   }

   public String getConnectedDevice() {
      return this.connectedDevice;
   }

   public Integer getDebugPort() {
      return this.debugPort;
   }

   public int getEnableDebug() {
      return this.enableDebug;
   }

   public int getEnableDevelopment() {
      return this.enableDevelopment;
   }

   public int getEnableWifiDebug() {
      return this.enableWifiDebug;
   }

   public int getInstalledRatHat() {
      return this.installedRatHat;
   }

   public int getIsRatHatRunning() {
      return this.isRatHatRunning;
   }

   public long getUpdateTime() {
      return this.updateTime;
   }

   public boolean isConnected() {
      return this.connected;
   }

   public boolean isPaired() {
      return this.paired;
   }

   public void setConnectErrorCount(int var1) {
      this.connectErrorCount = var1;
   }

   public void setConnected(boolean var1) {
      this.connected = var1;
      int var2;
      if (var1) {
         var2 = 0;
      } else {
         var2 = this.connectErrorCount + 1;
      }

      this.connectErrorCount = var2;
   }

   public void setConnectedDevice(String var1) {
      this.connectedDevice = var1;
   }

   public void setDebugPort(Integer var1) {
      this.debugPort = var1;
   }

   public void setEnableDebug(int var1) {
      this.enableDebug = var1;
   }

   public void setEnableDevelopment(int var1) {
      this.enableDevelopment = var1;
   }

   public void setEnableWifiDebug(int var1) {
      this.enableWifiDebug = var1;
   }

   public void setInstalledRatHat(int var1) {
      this.installedRatHat = var1;
   }

   public void setIsRatHatRunning(int var1) {
      this.isRatHatRunning = var1;
   }

   public void setPaired(boolean var1) {
      this.paired = var1;
   }

   public void setUpdateTime(long var1) {
      this.updateTime = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ADBConfig{', enableDevelopment=");
      var1.append(this.enableDevelopment);
      var1.append("', enableDebug=");
      var1.append(this.enableDebug);
      var1.append("', enableWifiDebug=");
      var1.append(this.enableWifiDebug);
      var1.append("', paired=");
      var1.append(this.paired);
      var1.append("', connected=");
      var1.append(this.connected);
      var1.append("', connectErrorCount=");
      var1.append(this.connectErrorCount);
      var1.append("', debugPort=");
      var1.append(this.debugPort);
      var1.append("', connectedDevice='");
      var1.append(this.connectedDevice);
      var1.append("', installedRatHat='");
      var1.append(this.installedRatHat);
      var1.append("', isRatHatRunning='");
      var1.append(this.isRatHatRunning);
      var1.append("', updateTime='");
      var1.append(this.updateTime);
      var1.append("'}");
      return var1.toString();
   }
}
