package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class PermissionInfoVO implements Serializable {
    private String description;
    private String gradeCode;
    private String groupValue;
    private Integer isGranted;
    private String permissionDescription;
    private String permissionName;
    private String permissionValue;
    private Integer protectionLevel;

    public PermissionInfoVO() {}
    public PermissionInfoVO(String permissionValue, String permissionName, String gradeCode, String groupValue, String description) {
        this.permissionValue = permissionValue; this.permissionName = permissionName;
        this.gradeCode = gradeCode; this.groupValue = groupValue; this.description = description;
    }

    public String getDescription() { return this.description; }
    public String getGradeCode() { return this.gradeCode; }
    public String getGroupValue() { return this.groupValue; }
    public Integer getIsGranted() { return this.isGranted; }
    public String getPermissionDescription() { return this.permissionDescription; }
    public String getPermissionName() { return this.permissionName; }
    public String getPermissionValue() { return this.permissionValue; }
    public Integer getProtectionLevel() { return this.protectionLevel; }
    public void setDescription(String v) { this.description = v; }
    public void setGradeCode(String v) { this.gradeCode = v; }
    public void setGroup(String v) { this.groupValue = v; }
    public void setGroupValue(String v) { this.groupValue = v; }
    public void setIsGranted(Integer v) { this.isGranted = v; }
    public void setPermissionDescription(String v) { this.permissionDescription = v; }
    public void setPermissionName(String v) { this.permissionName = v; }
    public void setPermissionValue(String v) { this.permissionValue = v; }
    public void setProtectionLevel(Integer v) { this.protectionLevel = v; }

    @NonNull
    @Override
    public String toString() {
        return "PermissionInfoVO{permissionValue='" + this.permissionValue + "', permissionName='" + this.permissionName
                + "', gradeCode='" + this.gradeCode + "', groupValue='" + this.groupValue
                + "', description='" + this.description + "'}";
    }
}
