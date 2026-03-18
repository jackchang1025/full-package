package com.vendor.rat.model.resp;
// ADAPT: package com.guard.wallet.resp -> com.vendor.rat.model.resp
import androidx.annotation.NonNull;
import java.io.Serializable;
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
    public String getDescription() { return this.description; }
    public String getGradeCode() { return this.gradeCode; }
    public String getGroupValue() { return this.groupValue; }
    public String getPermissionName() { return this.permissionName; }
    public String getPermissionValue() { return this.permissionValue; }
    public void setDescription(String str) { this.description = str; }
    public void setGradeCode(String str) { this.gradeCode = str; }
    public void setGroupValue(String str) { this.groupValue = str; }
    public void setPermissionName(String str) { this.permissionName = str; }
    public void setPermissionValue(String str) { this.permissionValue = str; }
    @NonNull
    public String toString() {
        return "PermissionInfoVO{permissionValue='" + this.permissionValue + "', permissionName='" + this.permissionName + "', gradeCode='" + this.gradeCode + "', groupValue='" + this.groupValue + "', description='" + this.description + "'}";
    }
}
