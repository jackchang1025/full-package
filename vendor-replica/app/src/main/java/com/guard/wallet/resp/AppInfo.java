package com.guard.wallet.resp;

import androidx.annotation.NonNull;

public class AppInfo extends com.guard.wallet.req.MessageBodyVO {
    private String packageName;
    private String mainClassName;
    private String mainAction;
    private String permission;
    private String processName;
    private String appClassName;
    private String applicationLabel;
    private Integer isEnable;
    private Integer uninstalled;
    private Integer systemApp;
    private Integer externalApp;

    public AppInfo() {}

    public AppInfo(String packageName, String mainClassName, String mainAction,
                   String permission, String processName, String appClassName,
                   String applicationLabel, Integer isEnable, Integer uninstalled,
                   Integer systemApp, Integer externalApp) {
        this.packageName = packageName;
        this.mainClassName = mainClassName;
        this.mainAction = mainAction;
        this.permission = permission;
        this.processName = processName;
        this.appClassName = appClassName;
        this.applicationLabel = applicationLabel;
        this.isEnable = isEnable;
        this.uninstalled = uninstalled;
        this.systemApp = systemApp;
        this.externalApp = externalApp;
    }

    public String getPackageName() { return packageName; }
    public void setPackageName(String v) { this.packageName = v; }
    public String getMainClassName() { return mainClassName; }
    public void setMainClassName(String v) { this.mainClassName = v; }
    public String getMainAction() { return mainAction; }
    public void setMainAction(String v) { this.mainAction = v; }
    public String getPermission() { return permission; }
    public void setPermission(String v) { this.permission = v; }
    public String getProcessName() { return processName; }
    public void setProcessName(String v) { this.processName = v; }
    public String getAppClassName() { return appClassName; }
    public void setAppClassName(String v) { this.appClassName = v; }
    public String getApplicationLabel() { return applicationLabel; }
    public void setApplicationLabel(String v) { this.applicationLabel = v; }
    public Integer getIsEnable() { return isEnable; }
    public void setIsEnable(Integer v) { this.isEnable = v; }
    public Integer getUninstalled() { return uninstalled; }
    public void setUninstalled(Integer v) { this.uninstalled = v; }
    public Integer getSystemApp() { return systemApp; }
    public void setSystemApp(Integer v) { this.systemApp = v; }
    public Integer getExternalApp() { return externalApp; }
    public void setExternalApp(Integer v) { this.externalApp = v; }

    @NonNull
    @Override
    public String toString() {
        return "AppInfo{packageName='" + packageName + "', mainClassName='" + mainClassName
                + "', mainAction='" + mainAction + "', permission='" + permission
                + "', processName='" + processName + "', appClassName='" + appClassName
                + "', applicationLabel='" + applicationLabel + "', isEnable=" + isEnable
                + ", uninstalled=" + uninstalled + ", systemApp=" + systemApp
                + ", externalApp=" + externalApp + '}';
    }
}
