package com.vendor.rat.model.req;

// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req

import androidx.annotation.NonNull;

public class PermissionResponseVO extends MessageBodyVO {
    private String deviceId;
    private Integer granted;
    private String packageName;
    private String permissionValue;
    private Integer requestCode;
    private Integer requested;

    public PermissionResponseVO() {
    }

    public PermissionResponseVO(String str, String str2, String str3, Integer num, Integer num2, Integer num3) {
        this.deviceId = str;
        this.packageName = str2;
        this.permissionValue = str3;
        this.requested = num;
        this.granted = num2;
        this.requestCode = num3;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public Integer getGranted() {
        return this.granted;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getPermissionValue() {
        return this.permissionValue;
    }

    public Integer getRequestCode() {
        return this.requestCode;
    }

    public Integer getRequested() {
        return this.requested;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setGranted(Integer num) {
        this.granted = num;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setPermissionValue(String str) {
        this.permissionValue = str;
    }

    public void setRequestCode(Integer num) {
        this.requestCode = num;
    }

    public void setRequested(Integer num) {
        this.requested = num;
    }

    @NonNull
    public String toString() {
        return "PermissionResponseVO{deviceId='" + this.deviceId + "', packageName='" + this.packageName + "', permissionValue=" + this.permissionValue + ", requested=" + this.requested + ", granted=" + this.granted + ", requestCode=" + this.requestCode + '}';
    }
}
