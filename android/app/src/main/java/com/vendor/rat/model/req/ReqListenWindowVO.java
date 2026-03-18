package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class ReqListenWindowVO implements Serializable {
    private String containerCode;
    private String deviceId;
    private String langCode;
    public ReqListenWindowVO() {
    }
    public ReqListenWindowVO(String str, String str2, String str3) {
        this.deviceId = str;
        this.langCode = str2;
        this.containerCode = str3;
    }
    public String getContainerCode() {
        return this.containerCode;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public String getLangCode() {
        return this.langCode;
    }
    public void setContainerCode(String str) {
        this.containerCode = str;
    }
    public void setDeviceId(String str) {
        this.deviceId = str;
    }
    public void setLangCode(String str) {
        this.langCode = str;
    }
    @NonNull
    public String toString() {
        return "ReqListenWindowVO{deviceId='" + this.deviceId + "', langCode='" + this.langCode + "', containerCode='" + this.containerCode + "'}";
    }
}
