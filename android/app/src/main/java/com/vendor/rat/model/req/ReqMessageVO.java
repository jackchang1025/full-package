package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class ReqMessageVO implements Serializable {
    private String deviceId;
    private String extraBody;
    private String intentCode;
    public ReqMessageVO() {
    }
    public ReqMessageVO(String str, String str2, String str3) {
        this.intentCode = str;
        this.deviceId = str2;
        this.extraBody = str3;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public String getExtraBody() {
        return this.extraBody;
    }
    public String getIntentCode() {
        return this.intentCode;
    }
    public void setDeviceId(String str) {
        this.deviceId = str;
    }
    public void setExtraBody(String str) {
        this.extraBody = str;
    }
    public void setIntentCode(String str) {
        this.intentCode = str;
    }
    @NonNull
    public String toString() {
        return "ReqMessageVO{intentCode='" + this.intentCode + "', deviceId='" + this.deviceId + "', extraBody='" + this.extraBody + "'}";
    }
}
