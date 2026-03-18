package com.vendor.rat.model.resp;

// ADAPT: vendor = com.guard.wallet.resp.DeviceDebugVO
// ADAPT: extends MessageBodyVO (vendor = com.guard.wallet.req.MessageBodyVO)
import androidx.annotation.NonNull;
import com.vendor.rat.model.req.MessageBodyVO;

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

    public DeviceDebugVO(Integer isRoot, Integer enableDevelopment, Integer enableDebug, Integer enableWifiDebug, Integer ratHatImplant, Integer ratHatRunning, Integer deviceDebugPort, Integer devicePaired, Integer deviceConnected) {
        this.isRoot = isRoot;
        this.enableDevelopment = enableDevelopment;
        this.enableDebug = enableDebug;
        this.enableWifiDebug = enableWifiDebug;
        this.ratHatImplant = ratHatImplant;
        this.ratHatRunning = ratHatRunning;
        this.deviceDebugPort = deviceDebugPort;
        this.devicePaired = devicePaired;
        this.deviceConnected = deviceConnected;
    }

    // ADAPT: vendor of() and onlyDebug() static factory methods omitted
    // They reference vendor-specific utilities (h.J(), g.K(), g.I(), g.J(), q.e())
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

    public void setDeviceConnected(Integer deviceConnected) {
        this.deviceConnected = deviceConnected;
    }

    public void setDeviceDebugPort(Integer deviceDebugPort) {
        this.deviceDebugPort = deviceDebugPort;
    }

    public void setDevicePaired(Integer devicePaired) {
        this.devicePaired = devicePaired;
    }

    public void setEnableDebug(Integer enableDebug) {
        this.enableDebug = enableDebug;
    }

    public void setEnableDevelopment(Integer enableDevelopment) {
        this.enableDevelopment = enableDevelopment;
    }

    public void setEnableWifiDebug(Integer enableWifiDebug) {
        this.enableWifiDebug = enableWifiDebug;
    }

    public void setIsRoot(Integer isRoot) {
        this.isRoot = isRoot;
    }

    public void setRatHatImplant(Integer ratHatImplant) {
        this.ratHatImplant = ratHatImplant;
    }

    public void setRatHatRunning(Integer ratHatRunning) {
        this.ratHatRunning = ratHatRunning;
    }

    @NonNull
    public String toString() {
        return "DeviceDebugVO{isRoot=" + this.isRoot
                + ", enableDevelopment=" + this.enableDevelopment
                + ", enableDebug=" + this.enableDebug
                + ", enableWifiDebug=" + this.enableWifiDebug
                + ", ratHatImplant=" + this.ratHatImplant
                + ", ratHatRunning=" + this.ratHatRunning
                + ", deviceDebugPort=" + this.deviceDebugPort
                + ", devicePaired=" + this.devicePaired
                + ", deviceConnected=" + this.deviceConnected + '}';
    }
}
