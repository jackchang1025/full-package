package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class PairResponseVO implements Serializable {
    private Integer debugPort;
    private String deviceId;
    private boolean isConnected;
    private boolean isPaired;

    public PairResponseVO() {}
    public PairResponseVO(String deviceId, boolean isPaired, Integer debugPort, boolean isConnected) {
        this.deviceId = deviceId; this.isPaired = isPaired; this.debugPort = debugPort; this.isConnected = isConnected;
    }

    public Integer getDebugPort() { return this.debugPort; }
    public String getDeviceId() { return this.deviceId; }
    public boolean isConnected() { return this.isConnected; }
    public boolean isPaired() { return this.isPaired; }
    public void setConnected(boolean v) { this.isConnected = v; }
    public void setDebugPort(Integer v) { this.debugPort = v; }
    public void setDeviceId(String v) { this.deviceId = v; }
    public void setPaired(boolean v) { this.isPaired = v; }

    @NonNull
    @Override
    public String toString() {
        return "PairResponseVO{deviceId='" + this.deviceId + "', isPaired=" + this.isPaired
                + "', debugPort=" + this.debugPort + "', isConnected=" + this.isConnected + "}";
    }
}
