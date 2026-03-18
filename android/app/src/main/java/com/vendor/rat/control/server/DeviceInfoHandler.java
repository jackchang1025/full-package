package com.vendor.rat.control.server;

import android.util.Log;

/**
 * 设备信息处理器 — vendor b.java 设备信息路由
 *
 * 覆盖路由: /info, /deviceId, /version, /screenState,
 *   /lockState, /batteryState, /netState, /callState,
 *   /containerState, /recordState, /pairState,
 *   /permissions, /permissionInfo, /packages,
 *   /deviceAdmin, /mainPackageName, /mainServerHost,
 *   /activeWindowClassName, /activePackageName,
 *   /isTopVisible, /debugPort, /pairPort,
 *   /backAppState, /localBackAppState, /localDebugPort
 */
public class DeviceInfoHandler {

    private static final String TAG = "DeviceInfoHandler";

    /** /info — vendor B(k) */
    public void getDeviceInfo() {
        // TODO: VENDOR_VERIFY — DeviceInfoVO.of()
        Log.d(TAG, "getDeviceInfo");
    }

    /** /deviceId — vendor A(k) */
    public void getDeviceId() {
        Log.d(TAG, "getDeviceId");
    }

    /** /version — vendor D3(k) */
    public void getVersion() {
        Log.d(TAG, "getVersion");
    }

    /** /screenState — vendor v(k) */
    public void getScreenState() {
        Log.d(TAG, "getScreenState");
    }

    /** /lockState — vendor E1(k) */
    public void getLockState() {
        Log.d(TAG, "getLockState");
    }

    /** /batteryState — vendor a(k) */
    public void getBatteryState() {
        Log.d(TAG, "getBatteryState");
    }

    /** /netState — vendor d2(k) */
    public void getNetState() {
        Log.d(TAG, "getNetState");
    }

    /** /callState — vendor m(k) */
    public void getCallState() {
        Log.d(TAG, "getCallState");
    }

    /** /containerState — vendor z(k) */
    public void getContainerState() {
        Log.d(TAG, "getContainerState");
    }

    /** /recordState — vendor c2(k) */
    public void getRecordState() {
        Log.d(TAG, "getRecordState");
    }

    /** /pairState — vendor U1(k) */
    public void getPairState() {
        Log.d(TAG, "getPairState");
    }

    /** /permissions — vendor k2(k) */
    public void getPermissions() {
        Log.d(TAG, "getPermissions");
    }

    /** /permissionInfo — vendor V1(k, pkg) */
    public void getPermissionInfo(String packageName) {
        Log.d(TAG, "getPermissionInfo: " + packageName);
    }

    /** /packages — vendor o(k) */
    public void getPackages() {
        Log.d(TAG, "getPackages");
    }

    /** /deviceAdmin — vendor d(k) */
    public void getDeviceAdmin() {
        Log.d(TAG, "getDeviceAdmin");
    }

    /** /mainPackageName — vendor F1(k) */
    public void getMainPackageName() {
        Log.d(TAG, "getMainPackageName");
    }

    /** /mainServerHost — vendor G1(k) */
    public void getMainServerHost() {
        Log.d(TAG, "getMainServerHost");
    }

    /** /activeWindowClassName — vendor s1(k) */
    public void getActiveWindowClassName() {
        Log.d(TAG, "getActiveWindowClassName");
    }

    /** /activePackageName — vendor d1(k) */
    public void getActivePackageName() {
        Log.d(TAG, "getActivePackageName");
    }

    /** /isTopVisible — vendor g(k) */
    public void isTopVisible() {
        Log.d(TAG, "isTopVisible");
    }

    /** /debugPort — vendor q1(k) */
    public void getDebugPort() {
        Log.d(TAG, "getDebugPort");
    }

    /** /pairPort — vendor x1(k) */
    public void getPairPort() {
        Log.d(TAG, "getPairPort");
    }

    /** /backAppState — vendor B1(k, state) */
    public void getBackAppState(String state) {
        Log.d(TAG, "getBackAppState");
    }

    /** /localBackAppState — vendor C1(k) */
    public void getLocalBackAppState() {
        Log.d(TAG, "getLocalBackAppState");
    }

    /** /localDebugPort — vendor t1(k) */
    public void getLocalDebugPort() {
        Log.d(TAG, "getLocalDebugPort");
    }
}
