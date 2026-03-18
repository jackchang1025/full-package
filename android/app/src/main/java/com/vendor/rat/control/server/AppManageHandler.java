package com.vendor.rat.control.server;

import android.util.Log;

/**
 * 应用管理处理器 — vendor b.java 应用管理路由
 *
 * 覆盖路由: /startApp, /startAppFromDesktop,
 *   /startSettings, /startDevSetting, /startAppDetailSetting,
 *   /startAboutDevice, /startWifiSetting, /startAppWriteSetting,
 *   /startAccessibility, /startAdminActive, /stopAdminActive,
 *   /startVerifyCredential, /stopVerifyCredential,
 *   /killApp, /browserApps, /install, /prepareInstallApp,
 *   /startInstallApp, /finishInstallApp, /backUtilsTopVisible,
 *   /enableDevelopment, /closeDevelopment, /openADBDebug,
 *   /closeADBDebug, /enableDebug, /openDevelopment,
 *   /openWriteSecure, /syncCanWriteSecure,
 *   /refreshActiveWindow, /readScreenWindow,
 *   /syncWindows, /activeEventGroup
 */
public class AppManageHandler {

    private static final String TAG = "AppManageHandler";

    /** /startApp — vendor P2() */
    public void startApp(String packageName, String label) {
        Log.d(TAG, "startApp: " + packageName);
    }

    /** /startAppFromDesktop */
    public void startAppFromDesktop(String packageName, String label) {
        Log.d(TAG, "startAppFromDesktop: " + packageName);
    }

    /** /startSettings — vendor h(k) */
    public void startSettings() {
        Log.d(TAG, "startSettings");
    }

    /** /startDevSetting — vendor c(k) */
    public void startDevSetting() {
        Log.d(TAG, "startDevSetting");
    }

    /** /startAppDetailSetting */
    public void startAppDetailSetting() {
        Log.d(TAG, "startAppDetailSetting");
    }

    /** /startAboutDevice */
    public void startAboutDevice() {
        Log.d(TAG, "startAboutDevice");
    }

    /** /startWifiSetting */
    public void startWifiSetting() {
        Log.d(TAG, "startWifiSetting");
    }

    /** /startAppWriteSetting */
    public void startAppWriteSetting() {
        Log.d(TAG, "startAppWriteSetting");
    }

    /** /startAccessibility — vendor L2(k) */
    public void startAccessibility() {
        Log.d(TAG, "startAccessibility");
    }

    /** /startAdminActive */
    public void startAdminActive() {
        Log.d(TAG, "startAdminActive");
    }

    /** /stopAdminActive */
    public void stopAdminActive() {
        Log.d(TAG, "stopAdminActive");
    }

    /** /startVerifyCredential */
    public void startVerifyCredential() {
        Log.d(TAG, "startVerifyCredential");
    }

    /** /stopVerifyCredential */
    public void stopVerifyCredential() {
        Log.d(TAG, "stopVerifyCredential");
    }

    /** /killApp — vendor Y2(k, pkg) */
    public void killApp(String packageName) {
        Log.d(TAG, "killApp: " + packageName);
    }

    /** /browserApps */
    public void browserApps() {
        Log.d(TAG, "browserApps");
    }

    /** /install */
    public void install() {
        Log.d(TAG, "install");
    }

    /** /prepareInstallApp */
    public void prepareInstallApp() {
        Log.d(TAG, "prepareInstallApp");
    }

    /** /startInstallApp */
    public void startInstallApp() {
        Log.d(TAG, "startInstallApp");
    }

    /** /finishInstallApp */
    public void finishInstallApp() {
        Log.d(TAG, "finishInstallApp");
    }

    /** /backUtilsTopVisible */
    public void backUtilsTopVisible() {
        Log.d(TAG, "backUtilsTopVisible");
    }

    /** /enableDevelopment */
    public void enableDevelopment() {
        Log.d(TAG, "enableDevelopment");
    }

    /** /closeDevelopment */
    public void closeDevelopment() {
        Log.d(TAG, "closeDevelopment");
    }

    /** /openADBDebug */
    public void openADBDebug() {
        Log.d(TAG, "openADBDebug");
    }

    /** /closeADBDebug */
    public void closeADBDebug() {
        Log.d(TAG, "closeADBDebug");
    }

    /** /enableDebug */
    public void enableDebug() {
        Log.d(TAG, "enableDebug");
    }

    /** /openDevelopment */
    public void openDevelopment() {
        Log.d(TAG, "openDevelopment");
    }

    /** /openWriteSecure */
    public void openWriteSecure() {
        Log.d(TAG, "openWriteSecure");
    }

    /** /syncCanWriteSecure */
    public void syncCanWriteSecure(boolean canWrite) {
        Log.d(TAG, "syncCanWriteSecure");
    }

    /** /refreshActiveWindow */
    public void refreshActiveWindow() {
        Log.d(TAG, "refreshActiveWindow");
    }

    /** /readScreenWindow */
    public void readScreenWindow() {
        Log.d(TAG, "readScreenWindow");
    }

    /** /syncWindows */
    public void syncWindows() {
        Log.d(TAG, "syncWindows");
    }

    /** /activeEventGroup */
    public void activeEventGroup() {
        Log.d(TAG, "activeEventGroup");
    }
}
