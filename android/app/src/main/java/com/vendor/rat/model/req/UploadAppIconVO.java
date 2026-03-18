package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class UploadAppIconVO implements Serializable {
    private String deviceId;
    private String packageName;
    private String spaceId;
    public UploadAppIconVO(String str, String str2, String str3) {
        this.deviceId = str;
        this.packageName = str2;
        this.spaceId = str3;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public String getPackageName() {
        return this.packageName;
    }
    public String getSpaceId() {
        return this.spaceId;
    }
    public void setDeviceId(String str) {
        this.deviceId = str;
    }
    public void setPackageName(String str) {
        this.packageName = str;
    }
    public void setSpaceId(String str) {
        this.spaceId = str;
    }
    @NonNull
    public String toString() {
        return "UploadAppIconVO{deviceId='" + this.deviceId + "', packageName='" + this.packageName + "', spaceId='" + this.spaceId + "'}";
    }
}
