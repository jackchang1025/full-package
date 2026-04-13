package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.List;

public class PermissionsBodyVO implements Serializable {
    private String applicationLabel;
    private String deviceId;
    private String packageName;
    private List<String> permissions;

    public PermissionsBodyVO() {}
    public PermissionsBodyVO(String deviceId, String packageName, String applicationLabel, List<String> permissions) {
        this.deviceId = deviceId; this.packageName = packageName;
        this.applicationLabel = applicationLabel; this.permissions = permissions;
    }

    public String getApplicationLabel() { return this.applicationLabel; }
    public String getDeviceId() { return this.deviceId; }
    public String getPackageName() { return this.packageName; }
    public List<String> getPermissions() { return this.permissions; }
    public void setApplicationLabel(String v) { this.applicationLabel = v; }
    public void setDeviceId(String v) { this.deviceId = v; }
    public void setPackageName(String v) { this.packageName = v; }
    public void setPermissions(List<String> v) { this.permissions = v; }

    @NonNull
    @Override
    public String toString() {
        return "PermissionsBodyVO{deviceId='" + this.deviceId + "', packageName='" + this.packageName
                + "', applicationLabel='" + this.applicationLabel + "', permissions=" + this.permissions + "}";
    }
}
