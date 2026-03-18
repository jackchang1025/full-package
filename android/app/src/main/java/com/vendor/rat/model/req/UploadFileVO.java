package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class UploadFileVO implements Serializable {
    private String deviceId;
    private String spaceId;
    public UploadFileVO() {
    }
    public UploadFileVO(String str, String str2) {
        this.deviceId = str;
        this.spaceId = str2;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public String getSpaceId() {
        return this.spaceId;
    }
    public void setDeviceId(String str) {
        this.deviceId = str;
    }
    public void setSpaceId(String str) {
        this.spaceId = str;
    }
    @NonNull
    public String toString() {
        return "UploadFileVO{deviceId='" + this.deviceId + "', spaceId='" + this.spaceId + "'}";
    }
}
