package com.vendor.rat.control.server;

import android.util.Log;

/**
 * ADB 操作处理器 — vendor b.java ADB 路由
 *
 * 覆盖路由: /localAdbShell, /localAdbPair, /localAdbPush,
 *   /localAdbConnect, /requestLocalAdbPair, /requestLocalKeepAlive,
 *   /syncADBConfig, /shareADBConfig, /openWifiDebug,
 *   /closeWifiDebug, /enableWifiDebug, /reloadPairKeyFiles,
 *   /installRatHat, /updateRatHat, /startRatHat, /stopRatHat
 */
public class AdbOperationHandler {

    private static final String TAG = "AdbOperationHandler";

    /** /localAdbShell — vendor A1(k, command) */
    public void localAdbShell(String command) {
        Log.d(TAG, "localAdbShell");
    }

    /** /localAdbPair — vendor y1() */
    public void localAdbPair(String host, String pairPort,
            String pairCode, boolean directConnect) {
        Log.d(TAG, "localAdbPair");
    }

    /** /localAdbPush — vendor z1/p1() */
    public void localAdbPush(String logId, String fileUrl,
            String fileName, String startCommand) {
        Log.d(TAG, "localAdbPush");
    }

    /** /localAdbConnect */
    public void localAdbConnect() {
        Log.d(TAG, "localAdbConnect");
    }

    /** /requestLocalAdbPair */
    public void requestLocalAdbPair() {
        Log.d(TAG, "requestLocalAdbPair");
    }

    /** /requestLocalKeepAlive */
    public void requestLocalKeepAlive() {
        Log.d(TAG, "requestLocalKeepAlive");
    }

    /** /syncADBConfig — vendor G2(k) */
    public void syncADBConfig() {
        Log.d(TAG, "syncADBConfig");
    }

    /** /shareADBConfig */
    public void shareADBConfig() {
        Log.d(TAG, "shareADBConfig");
    }

    /** /openWifiDebug */
    public void openWifiDebug() {
        Log.d(TAG, "openWifiDebug");
    }

    /** /closeWifiDebug */
    public void closeWifiDebug() {
        Log.d(TAG, "closeWifiDebug");
    }

    /** /enableWifiDebug */
    public void enableWifiDebug() {
        Log.d(TAG, "enableWifiDebug");
    }

    /** /reloadPairKeyFiles */
    public void reloadPairKeyFiles() {
        Log.d(TAG, "reloadPairKeyFiles");
    }

    /** /installRatHat — vendor B3(k) */
    public void installRatHat() {
        Log.d(TAG, "installRatHat");
    }

    /** /updateRatHat */
    public void updateRatHat() {
        Log.d(TAG, "updateRatHat");
    }

    /** /startRatHat — vendor T2(k) */
    public void startRatHat() {
        Log.d(TAG, "startRatHat");
    }

    /** /stopRatHat */
    public void stopRatHat() {
        Log.d(TAG, "stopRatHat");
    }
}
