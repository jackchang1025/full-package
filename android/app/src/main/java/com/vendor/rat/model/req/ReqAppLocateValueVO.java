package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class ReqAppLocateValueVO implements Serializable {
    private String deviceId;
    private String langCode;
    public ReqAppLocateValueVO() {
    }
    public ReqAppLocateValueVO(String str, String str2) {
        this.deviceId = str;
        this.langCode = str2;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public String getLangCode() {
        return this.langCode;
    }
    public void setDeviceId(String str) {
        this.deviceId = str;
    }
    public void setLangCode(String str) {
        this.langCode = str;
    }
    @NonNull
    public String toString() {
        return "ReqAppLocateValueVO{deviceId='" + this.deviceId + "'langCode='" + this.langCode + "'}";
    }
}
