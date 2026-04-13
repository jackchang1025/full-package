package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

public class DeviceLocationVO extends MessageBodyVO {
   private Float accuracy;
   private Double altitude;
   private Float bearing;
   private Double latitude;
   private Double longitude;
   private Float speed;

   public DeviceLocationVO() {
   }

   public DeviceLocationVO(Float var1, Double var2, Double var3, Double var4, Float var5, Float var6) {
      this.accuracy = var1;
      this.longitude = var2;
      this.latitude = var3;
      this.altitude = var4;
      this.speed = var5;
      this.bearing = var6;
   }

   public Float getAccuracy() {
      return this.accuracy;
   }

   public Double getAltitude() {
      return this.altitude;
   }

   public Float getBearing() {
      return this.bearing;
   }

   public Double getLatitude() {
      return this.latitude;
   }

   public Double getLongitude() {
      return this.longitude;
   }

   public Float getSpeed() {
      return this.speed;
   }

   public void setAccuracy(Float var1) {
      this.accuracy = var1;
   }

   public void setAltitude(Double var1) {
      this.altitude = var1;
   }

   public void setBearing(Float var1) {
      this.bearing = var1;
   }

   public void setLatitude(Double var1) {
      this.latitude = var1;
   }

   public void setLongitude(Double var1) {
      this.longitude = var1;
   }

   public void setSpeed(Float var1) {
      this.speed = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("DeviceLocationVO{accuracy=");
      var1.append(this.accuracy);
      var1.append(", longitude=");
      var1.append(this.longitude);
      var1.append(", latitude=");
      var1.append(this.latitude);
      var1.append(", altitude=");
      var1.append(this.altitude);
      var1.append(", speed=");
      var1.append(this.speed);
      var1.append(", bearing=");
      var1.append(this.bearing);
      var1.append('}');
      return var1.toString();
   }
}
