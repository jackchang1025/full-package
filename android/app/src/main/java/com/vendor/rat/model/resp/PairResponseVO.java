package com.vendor.rat.model.resp;
// ADAPT: package com.guard.wallet.resp -> com.vendor.rat.model.resp
import androidx.annotation.NonNull;
import java.io.Serializable;
public class PairResponseVO implements Serializable {
    private Integer debugPort;
    private String deviceId;
    private boolean isConnected;
    private boolean isPaired;
    public PairResponseVO() {
    }
    public PairResponseVO(String str, boolean z2, Integer num, boolean z3) {
        this.deviceId = str;
        this.isPaired = z2;
        this.debugPort = num;
        this.isConnected = z3;
    }
    public Integer getDebugPort() { return this.debugPort; }
    public String getDeviceId() { return this.deviceId; }
    public boolean isConnected() { return this.isConnected; }
    public boolean isPaired() { return this.isPaired; }
    public void setConnected(boolean z2) { this.isConnected = z2; }
    public void setDebugPort(Integer num) { this.debugPort = num; }
    public void setDeviceId(String str) { this.deviceId = str; }
    public void setPaired(boolean z2) { this.isPaired = z2; }
    @NonNull
    public String toString() {
        return "PairResponseVO{deviceId='" + this.deviceId + "', isPaired=" + this.isPaired + "', debugPort=" + this.debugPort + "', isConnected=" + this.isConnected + '}';
    }
}
