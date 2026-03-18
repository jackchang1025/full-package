package com.vendor.rat.model.req;

// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req

import androidx.annotation.NonNull;
import java.io.Serializable;

public class PermissionRequestVO implements Serializable {
    private String groupCode;
    private String groupValue;
    private String packageName;
    private String permissionCode;
    private String permissionValue;
    private Integer requestCode;

    public PermissionRequestVO() {
    }

    public PermissionRequestVO(String str, String str2, String str3, String str4, String str5, Integer num) {
        this.packageName = str;
        this.permissionCode = str2;
        this.permissionValue = str3;
        this.groupCode = str4;
        this.groupValue = str5;
        this.requestCode = num;
    }

    public String getGroupCode() {
        return this.groupCode;
    }

    public String getGroupValue() {
        return this.groupValue;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getPermissionCode() {
        return this.permissionCode;
    }

    public String getPermissionValue() {
        return this.permissionValue;
    }

    public Integer getRequestCode() {
        return this.requestCode;
    }

    public void setGroupCode(String str) {
        this.groupCode = str;
    }

    public void setGroupValue(String str) {
        this.groupValue = str;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setPermissionCode(String str) {
        this.permissionCode = str;
    }

    public void setPermissionValue(String str) {
        this.permissionValue = str;
    }

    public void setRequestCode(Integer num) {
        this.requestCode = num;
    }

    @NonNull
    public String toString() {
        return "RequestPermissionVO{packageName='" + this.packageName + "', permissionCode='" + this.permissionCode + "', permissionValue='" + this.permissionValue + "', groupCode='" + this.groupCode + "', groupValue='" + this.groupValue + "', requestCode=" + this.requestCode + '}';
    }
}
