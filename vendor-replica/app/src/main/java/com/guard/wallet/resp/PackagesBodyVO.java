package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.List;

public class PackagesBodyVO implements Serializable {
    private String deviceId;
    private List<AppInfo> packages;

    public PackagesBodyVO() {}
    public PackagesBodyVO(String deviceId, List<AppInfo> packages) {
        this.deviceId = deviceId; this.packages = packages;
    }

    public String getDeviceId() { return this.deviceId; }
    public List<AppInfo> getPackages() { return this.packages; }
    public void setDeviceId(String v) { this.deviceId = v; }
    public void setPackages(List<AppInfo> v) { this.packages = v; }

    @NonNull
    @Override
    public String toString() {
        return "PackagesBodyVO{deviceId='" + this.deviceId + "', packages=" + this.packages + "}";
    }
}
