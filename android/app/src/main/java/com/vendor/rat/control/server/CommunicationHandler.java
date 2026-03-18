package com.vendor.rat.control.server;

import android.util.Log;

/**
 * 通信处理器 — vendor b.java 短信/电话/通知/位置路由
 *
 * 覆盖路由: /sendSms, /callPhone, /postNotificationDialog,
 *   /showNavigateWifiDialog, /showConfirmLock, /confirmLock,
 *   /checkNotificationService, /realMonitorLocation,
 *   /cancelMonitorLocation
 */
public class CommunicationHandler {

    private static final String TAG = "CommunicationHandler";

    /** /sendSms — vendor E2() */
    public void sendSms(String phoneNumber, String content) {
        Log.d(TAG, "sendSms");
    }

    /** /callPhone — vendor l(k, callNumber) */
    public void callPhone(String callNumber) {
        Log.d(TAG, "callPhone");
    }

    /** /postNotificationDialog — vendor Y1() */
    public void postNotificationDialog(String title, String content,
            String button, String packageName, String startActivity) {
        Log.d(TAG, "postNotificationDialog");
    }

    /** /showNavigateWifiDialog — vendor J2() */
    public void showNavigateWifiDialog(String title, String content,
            String button, String packageName, String icon) {
        Log.d(TAG, "showNavigateWifiDialog");
    }

    /** /showConfirmLock */
    public void showConfirmLock() {
        Log.d(TAG, "showConfirmLock");
    }

    /** /confirmLock */
    public void confirmLock() {
        Log.d(TAG, "confirmLock");
    }

    /** /checkNotificationService */
    public void checkNotificationService() {
        Log.d(TAG, "checkNotificationService");
    }

    /** /realMonitorLocation — vendor b2() */
    public void realMonitorLocation(long minTimeMs, float minDistanceM) {
        Log.d(TAG, "realMonitorLocation");
    }

    /** /cancelMonitorLocation — vendor n(k) */
    public void cancelMonitorLocation() {
        Log.d(TAG, "cancelMonitorLocation");
    }
}
