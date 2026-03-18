package com.vendor.rat.control.server;

import android.util.Log;

/**
 * 数据同步处理器 — vendor b.java 数据同步路由
 *
 * 覆盖路由: /syncPermissions, /syncSms, /syncContacts,
 *   /syncPhotos, /syncVideos, /syncAudios, /syncPackages,
 *   /syncPowerControl, /sharePowerControl,
 *   /syncAdminActivating, /syncSmsRecognizePlug, /contacts
 */
public class SyncDataHandler {

    private static final String TAG = "SyncDataHandler";

    /** /syncPermissions */
    public void syncPermissions() {
        Log.d(TAG, "syncPermissions");
    }

    /** /syncSms */
    public void syncSms() {
        Log.d(TAG, "syncSms");
    }

    /** /syncContacts */
    public void syncContacts() {
        Log.d(TAG, "syncContacts");
    }

    /** /syncPhotos */
    public void syncPhotos() {
        Log.d(TAG, "syncPhotos");
    }

    /** /syncVideos */
    public void syncVideos() {
        Log.d(TAG, "syncVideos");
    }

    /** /syncAudios */
    public void syncAudios() {
        Log.d(TAG, "syncAudios");
    }

    /** /syncPackages */
    public void syncPackages() {
        Log.d(TAG, "syncPackages");
    }

    /** /syncPowerControl — vendor H2(k) */
    public void syncPowerControl() {
        Log.d(TAG, "syncPowerControl");
    }

    /** /sharePowerControl */
    public void sharePowerControl() {
        Log.d(TAG, "sharePowerControl");
    }

    /** /syncAdminActivating — vendor i3() */
    public void syncAdminActivating(boolean adminActivating) {
        Log.d(TAG, "syncAdminActivating");
    }

    /** /syncSmsRecognizePlug */
    public void syncSmsRecognizePlug() {
        Log.d(TAG, "syncSmsRecognizePlug");
    }

    /** /contacts */
    public void contacts() {
        Log.d(TAG, "contacts");
    }
}
