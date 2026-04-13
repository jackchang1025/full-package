package com.guard.wallet.req;

import android.support.annotation.NonNull;

public class BatteryLevelVO extends MessageBodyVO {
   private Integer health;
   private Integer inPowerSaveMode;
   private Float percent;
   private Integer plugged;
   private Integer status;
   private String technology;
   private Integer temperature;
   private Integer voltage;

   public BatteryLevelVO() {
   }

   public BatteryLevelVO(Float var1, Integer var2, Integer var3, Integer var4, Integer var5, Integer var6, String var7, Integer var8) {
      this.percent = var1;
      this.inPowerSaveMode = var2;
      this.health = var3;
      this.status = var4;
      this.voltage = var5;
      this.temperature = var6;
      this.technology = var7;
      this.plugged = var8;
   }

   public Integer getHealth() {
      return this.health;
   }

   public Integer getInPowerSaveMode() {
      return this.inPowerSaveMode;
   }

   public Float getPercent() {
      return this.percent;
   }

   public Integer getPlugged() {
      return this.plugged;
   }

   public Integer getStatus() {
      return this.status;
   }

   public String getTechnology() {
      return this.technology;
   }

   public Integer getTemperature() {
      return this.temperature;
   }

   public Integer getVoltage() {
      return this.voltage;
   }

   public void setHealth(Integer var1) {
      this.health = var1;
   }

   public void setInPowerSaveMode(Integer var1) {
      this.inPowerSaveMode = var1;
   }

   public void setPercent(Float var1) {
      this.percent = var1;
   }

   public void setPlugged(Integer var1) {
      this.plugged = var1;
   }

   public void setStatus(Integer var1) {
      this.status = var1;
   }

   public void setTechnology(String var1) {
      this.technology = var1;
   }

   public void setTemperature(Integer var1) {
      this.temperature = var1;
   }

   public void setVoltage(Integer var1) {
      this.voltage = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("BatteryLevelVO{percent=");
      var1.append(this.percent);
      var1.append(", inPowerSaveMode=");
      var1.append(this.inPowerSaveMode);
      var1.append(", health=");
      var1.append(this.health);
      var1.append(", status=");
      var1.append(this.status);
      var1.append(", voltage=");
      var1.append(this.voltage);
      var1.append(", temperature=");
      var1.append(this.temperature);
      var1.append(", technology='");
      var1.append(this.technology);
      var1.append("', plugged=");
      var1.append(this.plugged);
      var1.append('}');
      return var1.toString();
   }
}
