package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class ReqWifiSettingDialogVO implements Serializable {
    private String deviceId;
    public ReqWifiSettingDialogVO(String str) {
        this.deviceId = str;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setDeviceId(String str) {
        this.deviceId = str;
    }
    @NonNull
    public String toString() {
        return "ReqWifiSettingDialogVO{deviceId='" + this.deviceId + "'}";
    }
}
