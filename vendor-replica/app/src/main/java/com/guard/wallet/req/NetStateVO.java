package com.guard.wallet.req;

public class NetStateVO extends MessageBodyVO {
   private Integer isConnected;
   private Integer isWifiConnected;
   private String localIp;
   private String macAddress;
   private String wifiId;

   public NetStateVO() {
      Integer var1 = -1;
      this.isConnected = var1;
      this.isWifiConnected = var1;
   }

   public NetStateVO(Integer var1, Integer var2, String var3, String var4, String var5) {
      this.isConnected = -1;
      this.isConnected = var1;
      this.isWifiConnected = var2;
      this.macAddress = var3;
      this.wifiId = var4;
      this.localIp = var5;
   }

   public Integer getIsConnected() {
      return this.isConnected;
   }

   public Integer getIsWifiConnected() {
      return this.isWifiConnected;
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

   public void setIsConnected(Integer var1) {
      this.isConnected = var1;
   }

   public void setIsWifiConnected(Integer var1) {
      this.isWifiConnected = var1;
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
}
