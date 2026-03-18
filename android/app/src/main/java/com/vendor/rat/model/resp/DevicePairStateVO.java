package com.vendor.rat.model.resp;

// ADAPT: vendor = com.guard.wallet.resp.DevicePairStateVO
import androidx.annotation.NonNull;
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

    public DevicePairStateVO(Integer netConnected, Integer isWifiConnected, String wifiId, Integer supportPair, Integer devicePaired, Integer deviceConnected, Integer deviceDebugPort, Integer ratHatImplant, Integer ratHatRunning) {
        this.netConnected = netConnected;
        this.isWifiConnected = isWifiConnected;
        this.wifiId = wifiId;
        this.supportPair = supportPair;
        this.devicePaired = devicePaired;
        this.deviceConnected = deviceConnected;
        this.deviceDebugPort = deviceDebugPort;
        this.ratHatImplant = ratHatImplant;
        this.ratHatRunning = ratHatRunning;
    }

    // ADAPT: vendor of() static factory method omitted
    // It references vendor-specific utilities (g.z0(), h.J(), Build.VERSION.SDK_INT, e.S())
    // Callers should construct and populate fields directly

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

    public void setDeviceConnected(Integer deviceConnected) {
        this.deviceConnected = deviceConnected;
    }

    public void setDeviceDebugPort(Integer deviceDebugPort) {
        this.deviceDebugPort = deviceDebugPort;
    }

    public void setDevicePaired(Integer devicePaired) {
        this.devicePaired = devicePaired;
    }

    public void setIsWifiConnected(Integer isWifiConnected) {
        this.isWifiConnected = isWifiConnected;
    }

    public void setNetConnected(Integer netConnected) {
        this.netConnected = netConnected;
    }

    public void setRatHatImplant(Integer ratHatImplant) {
        this.ratHatImplant = ratHatImplant;
    }

    public void setRatHatRunning(Integer ratHatRunning) {
        this.ratHatRunning = ratHatRunning;
    }

    public void setSupportPair(Integer supportPair) {
        this.supportPair = supportPair;
    }

    public void setWifiId(String wifiId) {
        this.wifiId = wifiId;
    }

    @NonNull
    public String toString() {
        return "DevicePairStateVO{netConnected=" + this.netConnected
                + ", isWifiConnected=" + this.isWifiConnected
                + ", wifiId='" + this.wifiId
                + "', supportPair=" + this.supportPair
                + ", devicePaired=" + this.devicePaired
                + ", deviceConnected=" + this.deviceConnected
                + ", deviceDebugPort=" + this.deviceDebugPort
                + ", ratHatImplant=" + this.ratHatImplant
                + ", ratHatRunning=" + this.ratHatRunning + '}';
    }
}
