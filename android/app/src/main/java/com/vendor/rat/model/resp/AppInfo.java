package com.vendor.rat.model.resp;
import androidx.annotation.NonNull;
import com.vendor.rat.model.req.MessageBodyVO;
/* loaded from: classes.dex */
public class AppInfo extends MessageBodyVO {
    private String appClassName;
    private String applicationLabel;
    private Integer externalApp;
    private Integer isEnable;
    private String mainAction;
    private String mainClassName;
    private String packageName;
    private String permission;
    private String processName;
    private Integer systemApp;
    private Integer uninstalled;
    public AppInfo() {
    }
    public AppInfo(String str, String str2, String str3, String str4, String str5, String str6, String str7, Integer num, Integer num2, Integer num3, Integer num4) {
        this.packageName = str;
        this.mainClassName = str2;
        this.mainAction = str3;
        this.permission = str4;
        this.processName = str5;
        this.appClassName = str6;
        this.applicationLabel = str7;
        this.isEnable = num;
        this.uninstalled = num2;
        this.systemApp = num3;
        this.externalApp = num4;
    }
    public String getAppClassName() {
        return this.appClassName;
    }
    public String getApplicationLabel() {
        return this.applicationLabel;
    }
    public Integer getExternalApp() {
        return this.externalApp;
    }
    public Integer getIsEnable() {
        return this.isEnable;
    }
    public String getMainAction() {
        return this.mainAction;
    }
    public String getMainClassName() {
        return this.mainClassName;
    }
    public String getPackageName() {
        return this.packageName;
    }
    public String getPermission() {
        return this.permission;
    }
    public String getProcessName() {
        return this.processName;
    }
    public Integer getSystemApp() {
        return this.systemApp;
    }
    public Integer getUninstalled() {
        return this.uninstalled;
    }
    public void setAppClassName(String str) {
        this.appClassName = str;
    }
    public void setApplicationLabel(String str) {
        this.applicationLabel = str;
    }
    public void setExternalApp(Integer num) {
        this.externalApp = num;
    }
    public void setIsEnable(Integer num) {
        this.isEnable = num;
    }
    public void setMainAction(String str) {
        this.mainAction = str;
    }
    public void setMainClassName(String str) {
        this.mainClassName = str;
    }
    public void setPackageName(String str) {
        this.packageName = str;
    }
    public void setPermission(String str) {
        this.permission = str;
    }
    public void setProcessName(String str) {
        this.processName = str;
    }
    public void setSystemApp(Integer num) {
        this.systemApp = num;
    }
    public void setUninstalled(Integer num) {
        this.uninstalled = num;
    }
    @NonNull
    public String toString() {
        return "AppInfo{packageName='" + this.packageName + "', mainClassName='" + this.mainClassName + "', mainAction='" + this.mainAction + "', permission='" + this.permission + "', processName='" + this.processName + "', appClassName='" + this.appClassName + "', applicationLabel='" + this.applicationLabel + "', isEnable=" + this.isEnable + "', uninstalled=" + this.uninstalled + "', systemApp=" + this.systemApp + "', externalApp=" + this.externalApp + '}';
    }
}
