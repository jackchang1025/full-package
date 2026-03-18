package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class RewriteDebugPortVO implements Serializable {
    private Integer debugPort;
    private String deviceId;
    public RewriteDebugPortVO() {
    }
    public RewriteDebugPortVO(String str, Integer num) {
        this.deviceId = str;
        this.debugPort = num;
    }
    public Integer getDebugPort() {
        return this.debugPort;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setDebugPort(Integer num) {
        this.debugPort = num;
    }
    public void setDeviceId(String str) {
        this.deviceId = str;
    }
    @NonNull
    public String toString() {
        return "RewriteDebugPortVO{deviceId='" + this.deviceId + "', debugPort=" + this.debugPort + '}';
    }
}
