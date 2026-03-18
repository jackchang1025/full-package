package com.vendor.rat.control.server;

import android.util.Log;

/**
 * 设备控制处理器 — vendor b.java 设备控制路由
 *
 * 覆盖路由: /global/lockScreen, /global/wakeUpScreen,
 *   /global/action, /global/setText, /global/clear,
 *   /global/copy, /global/paste, /global/delete,
 *   /global/moveHome, /global/moveEnd, /global/keepScreenOn,
 *   /global/execCommand, /unlock, /enterCipher,
 *   /blockView, /screenOffTimeout, /writeScreenOffTimeout,
 *   /ignoreBatteryOptimization, /removeAccount,
 *   /requestPermission, /uninstallPolicy
 */
public class DeviceControlHandler {

    private static final String TAG = "DeviceControlHandler";

    /** /global/lockScreen — vendor D1(k) */
    public void lockScreen() {
        Log.d(TAG, "lockScreen");
    }

    /** /global/wakeUpScreen */
    public void wakeUpScreen() {
        Log.d(TAG, "wakeUpScreen");
    }

    /** /global/action — vendor f1() */
    public void globalAction(String actionName, String start,
            String duration, String xList, String yList) {
        Log.d(TAG, "globalAction: " + actionName);
    }

    /** /global/setText — vendor k1(k, text) */
    public void setText(String text) {
        Log.d(TAG, "setText");
    }

    /** /global/clear — vendor g1(k) */
    public void clearText() {
        Log.d(TAG, "clearText");
    }

    /** /global/copy — vendor i1(k) */
    public void copy() {
        Log.d(TAG, "copy");
    }

    /** /global/paste — vendor K1(k) */
    public void paste() {
        Log.d(TAG, "paste");
    }

    /** /global/delete — vendor J1(k) */
    public void delete() {
        Log.d(TAG, "delete");
    }

    /** /global/moveHome — vendor h1(k) */
    public void moveHome() {
        Log.d(TAG, "moveHome");
    }

    /** /global/moveEnd — vendor j1(k) */
    public void moveEnd() {
        Log.d(TAG, "moveEnd");
    }

    /** /global/keepScreenOn — vendor u1(k, keep) */
    public void keepScreenOn(boolean keep) {
        Log.d(TAG, "keepScreenOn: " + keep);
    }

    /** /global/execCommand — vendor G() */
    public void execCommand(String[] commands) {
        Log.d(TAG, "execCommand");
    }

    /** /unlock — vendor A3() */
    public void unlockDevice(String cipherGradeCode,
            String textCipher, String patternCipher,
            String touchCipher, String eventCipher,
            String boundsInScreen, String boundsInParent) {
        Log.d(TAG, "unlockDevice");
    }

    /** /enterCipher — vendor F() */
    public void enterCipher(String cipherState) {
        Log.d(TAG, "enterCipher");
    }

    /** /blockView — vendor j() */
    public void blockView(boolean show, boolean transparent,
            String hint, boolean zeroBrightness,
            boolean destroyLock) {
        Log.d(TAG, "blockView");
    }

    /** /screenOffTimeout — vendor F3(k, offTimeout) */
    public void screenOffTimeout(Long offTimeout) {
        Log.d(TAG, "screenOffTimeout");
    }

    /** /writeScreenOffTimeout */
    public void writeScreenOffTimeout() {
        Log.d(TAG, "writeScreenOffTimeout");
    }

    /** /ignoreBatteryOptimization — vendor I2(k) */
    public void ignoreBatteryOptimization() {
        Log.d(TAG, "ignoreBatteryOptimization");
    }

    /** /removeAccount — vendor h2(k, accountType) */
    public void removeAccount(String accountType) {
        Log.d(TAG, "removeAccount");
    }

    /** /requestPermission */
    public void requestPermission() {
        Log.d(TAG, "requestPermission");
    }

    /** /uninstallPolicy */
    public void uninstallPolicy() {
        Log.d(TAG, "uninstallPolicy");
    }
}
