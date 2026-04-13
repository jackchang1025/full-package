package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes.dex */
public class PermissionsBodyVO implements Serializable {
    private String applicationLabel;
    private String deviceId;
    private String packageName;
    private List<String> permissions;

    public PermissionsBodyVO() {
    }

    public PermissionsBodyVO(String str, String str2, String str3, List<String> list) {
        this.deviceId = str;
        this.packageName = str2;
        this.applicationLabel = str3;
        this.permissions = list;
    }

    public String getApplicationLabel() {
        return this.applicationLabel;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public List<String> getPermissions() {
        return this.permissions;
    }

    public void setApplicationLabel(String str) {
        this.applicationLabel = str;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setPermissions(List<String> list) {
        this.permissions = list;
    }

    @NonNull
    public String toString() {
        return "PermissionsBodyVO{deviceId='" + this.deviceId + "', packageName='" + this.packageName + "', applicationLabel='" + this.applicationLabel + "', permissions=" + this.permissions + '}';
    }
}
