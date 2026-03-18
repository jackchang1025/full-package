package com.vendor.rat.model.resp;
// ADAPT: package com.guard.wallet.resp -> com.vendor.rat.model.resp
import androidx.annotation.NonNull;
import com.vendor.rat.model.req.MessageBodyVO;
public class DeviceLocationVO extends MessageBodyVO {
    private Float accuracy;
    private Double altitude;
    private Float bearing;
    private Double latitude;
    private Double longitude;
    private Float speed;
    public DeviceLocationVO() {
    }
    public DeviceLocationVO(Float f2, Double d2, Double d3, Double d4, Float f3, Float f4) {
        this.accuracy = f2;
        this.longitude = d2;
        this.latitude = d3;
        this.altitude = d4;
        this.speed = f3;
        this.bearing = f4;
    }
    public Float getAccuracy() { return this.accuracy; }
    public Double getAltitude() { return this.altitude; }
    public Float getBearing() { return this.bearing; }
    public Double getLatitude() { return this.latitude; }
    public Double getLongitude() { return this.longitude; }
    public Float getSpeed() { return this.speed; }
    public void setAccuracy(Float f2) { this.accuracy = f2; }
    public void setAltitude(Double d2) { this.altitude = d2; }
    public void setBearing(Float f2) { this.bearing = f2; }
    public void setLatitude(Double d2) { this.latitude = d2; }
    public void setLongitude(Double d2) { this.longitude = d2; }
    public void setSpeed(Float f2) { this.speed = f2; }
    @NonNull
    public String toString() {
        return "DeviceLocationVO{accuracy=" + this.accuracy + ", longitude=" + this.longitude + ", latitude=" + this.latitude + ", altitude=" + this.altitude + ", speed=" + this.speed + ", bearing=" + this.bearing + '}';
    }
}
