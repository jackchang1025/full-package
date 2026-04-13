package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PermissionInfoVO implements Serializable {
    private String description;
    private String gradeCode;
    private String groupValue;
    private String permissionName;
    private String permissionValue;

    public PermissionInfoVO() {
    }

    public PermissionInfoVO(String str, String str2, String str3, String str4, String str5) {
        this.permissionValue = str;
        this.permissionName = str2;
        this.gradeCode = str3;
        this.groupValue = str4;
        this.description = str5;
    }

    public String getDescription() {
        return this.description;
    }

    public String getGradeCode() {
        return this.gradeCode;
    }

    public String getGroupValue() {
        return this.groupValue;
    }

    public String getPermissionName() {
        return this.permissionName;
    }

    public String getPermissionValue() {
        return this.permissionValue;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setGradeCode(String str) {
        this.gradeCode = str;
    }

    public void setGroupValue(String str) {
        this.groupValue = str;
    }

    public void setPermissionName(String str) {
        this.permissionName = str;
    }

    public void setPermissionValue(String str) {
        this.permissionValue = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("PermissionInfoVO{permissionValue='");
        sb.append(this.permissionValue);
        sb.append("', permissionName='");
        sb.append(this.permissionName);
        sb.append("', gradeCode='");
        sb.append(this.gradeCode);
        sb.append("', groupValue='");
        sb.append(this.groupValue);
        sb.append("', description='");
        return AbstractC0000a.m18n(sb, this.description, "'}");
    }
}
