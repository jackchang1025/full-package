package com.guard.wallet.utils;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import androidx.core.content.ContextCompat;

/**
 * ADB/调试管理工具类 — 原 g.java 中 ADB、无线调试、开发者选项相关方法。
 */
public final class AdbUtils {
    private static final String TAG = "ApplicationUtil";

    private AdbUtils() {}

    private static Context ctx() { return AppManagerUtils.getContext(); }

    /** g.I() — ADB 是否开启 */
    public static boolean isAdbEnabled() {
        Context context = ctx();
        if (context == null) return false;
        boolean enabled = Settings.Global.getInt(context.getContentResolver(), "adb_enabled", 0) > 0;
        if (!enabled) Log.d(TAG, "未开启ADB调试");
        return enabled;
    }

    /** g.J() — 无线调试是否开启 */
    public static boolean isWirelessDebugEnabled() {
        Context context = ctx();
        if (context == null) return false;
        boolean enabled = Settings.Global.getInt(context.getContentResolver(), "adb_wifi_enabled", 0) > 0;
        if (!enabled) Log.d(TAG, "未开启无线调试");
        return enabled;
    }

    /** g.K() — 开发者选项是否开启 */
    public static boolean isDeveloperOptionsEnabled() {
        Context context = ctx();
        if (context == null) return false;
        return Settings.Global.getInt(context.getContentResolver(), "development_settings_enabled", 0) > 0;
    }

    /** g.D() — 禁用 ADB 安装确认 */
    public static void disableAdbInstallConfirm() {
        Context context = ctx();
        if (context == null) return;
        if (ContextCompat.checkSelfPermission(context, "android.permission.WRITE_SECURE_SETTINGS") != 0) return;
        try {
            int current = Settings.Secure.getInt(context.getContentResolver(), "adb_install_need_confirm");
            if (current != 0) {
                Settings.Secure.putInt(context.getContentResolver(), "adb_install_need_confirm", 0);
            }
        } catch (Exception e) {
            Log.e(TAG, "disableAdbInstallConfirm error", e);
        }
    }

    /** g.b() — ADB secure write 是否启用 */
    public static boolean isSecureWriteEnabled() {
        Context context = ctx();
        if (context == null) return false;
        boolean enabled = Settings.Secure.getInt(context.getContentResolver(), "enable_secure_write", 0) == 1;
        if (enabled) Log.d(TAG, "ADB Enable Secure Write");
        return enabled;
    }

    /** g.c() — ADB 安装是否需要确认 */
    public static boolean isAdbInstallConfirmRequired() {
        Context context = ctx();
        if (context == null) return true;
        try {
            return Settings.Secure.getInt(context.getContentResolver(), "adb_install_need_confirm") != 0;
        } catch (Exception e) {
            Log.e(TAG, "isAdbInstallConfirm error", e);
            return true;
        }
    }
}
