package com.vendor.rat.model.resp;
// ADAPT: package com.guard.wallet.resp -> com.vendor.rat.model.resp
import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.List;
public class PackagesBodyVO implements Serializable {
    private String deviceId;
    private List<AppInfo> packages;
    public PackagesBodyVO() {
    }
    public PackagesBodyVO(String str, List<AppInfo> list) {
        this.deviceId = str;
        this.packages = list;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public List<AppInfo> getPackages() {
        return this.packages;
    }
    public void setDeviceId(String str) {
        this.deviceId = str;
    }
    public void setPackages(List<AppInfo> list) {
        this.packages = list;
    }
    @NonNull
    public String toString() {
        return "PackagesBodyVO{deviceId='" + this.deviceId + "', packages=" + this.packages + '}';
    }
}
