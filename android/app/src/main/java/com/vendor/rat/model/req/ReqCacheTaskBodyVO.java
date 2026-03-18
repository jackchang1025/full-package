package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class ReqCacheTaskBodyVO implements Serializable {
    private String containerCode;
    private String deviceId;
    public ReqCacheTaskBodyVO() {
    }
    public ReqCacheTaskBodyVO(String str, String str2) {
        this.deviceId = str;
        this.containerCode = str2;
    }
    public String getContainerCode() {
        return this.containerCode;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setContainerCode(String str) {
        this.containerCode = str;
    }
    public void setDeviceId(String str) {
        this.deviceId = str;
    }
    @NonNull
    public String toString() {
        return "ReqCacheTaskBodyVO{deviceId='" + this.deviceId + "', containerCode='" + this.containerCode + "'}";
    }
}
