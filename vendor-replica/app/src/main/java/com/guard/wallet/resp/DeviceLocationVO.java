package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

public class DeviceLocationVO extends MessageBodyVO {
    private Float accuracy;
    private Double altitude;
    private Float bearing;
    private Double latitude;
    private Double longitude;
    private Float speed;

    public DeviceLocationVO() {}
    public DeviceLocationVO(Float accuracy, Double longitude, Double latitude, Double altitude, Float speed, Float bearing) {
        this.accuracy = accuracy; this.longitude = longitude; this.latitude = latitude;
        this.altitude = altitude; this.speed = speed; this.bearing = bearing;
    }

    public Float getAccuracy() { return this.accuracy; }
    public Double getAltitude() { return this.altitude; }
    public Float getBearing() { return this.bearing; }
    public Double getLatitude() { return this.latitude; }
    public Double getLongitude() { return this.longitude; }
    public Float getSpeed() { return this.speed; }

    public void setAccuracy(Float v) { this.accuracy = v; }
    public void setAltitude(Double v) { this.altitude = v; }
    public void setBearing(Float v) { this.bearing = v; }
    public void setLatitude(Double v) { this.latitude = v; }
    public void setLongitude(Double v) { this.longitude = v; }
    public void setSpeed(Float v) { this.speed = v; }

    @NonNull
    @Override
    public String toString() {
        return "DeviceLocationVO{accuracy=" + this.accuracy + ", longitude=" + this.longitude
                + ", latitude=" + this.latitude + ", altitude=" + this.altitude
                + ", speed=" + this.speed + ", bearing=" + this.bearing + "}";
    }
}
