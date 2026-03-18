package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class QueryAgentFileVO implements Serializable {
    private String deviceId;
    private String wifiId;
    public QueryAgentFileVO() {
    }
    public QueryAgentFileVO(String str, String str2) {
        this.deviceId = str;
        this.wifiId = str2;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public String getWifiId() {
        return this.wifiId;
    }
    public void setDeviceId(String str) {
        this.deviceId = str;
    }
    public void setWifiId(String str) {
        this.wifiId = str;
    }
    @NonNull
    public String toString() {
        return "QueryAgentFileVO{deviceId='" + this.deviceId + "', wifiId='" + this.wifiId + "'}";
    }
}
