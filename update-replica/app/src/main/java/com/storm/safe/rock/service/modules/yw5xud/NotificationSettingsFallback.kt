package com.storm.safe.rock.service.modules.yw5xud

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log

/**
 * NotificationSettingsFallback — 通知权限 CHANNEL 启动失败后的 app 级 fallback。
 *
 * 真机 FIN-AL60 证据: CHANNEL_NOTIFICATION_SETTINGS + CHANNEL_ID="OFF" 启动成功但
 * `waitForChannelNotifPage` 找不到"允许通知" × 2 次。可能因为 app 没注册 "OFF" channel，
 * 华为回退到 APP 级通知设置页。
 *
 * ADAPT: real-device hardening — vendor 只用 CHANNEL_NOTIFICATION_SETTINGS (L4276) 加
 * 单一 keyword "允许通知" (L4302)。replica 提供：
 *   1. APP_NOTIFICATION_SETTINGS fallback（app 级别通知开关，无需 CHANNEL_ID）
 *   2. 扩展 CHANNEL_KEYWORDS list（7 条，覆盖华为 HarmonyOS 4.2 变体）
 */
object NotificationSettingsFallback {
    private const val TAG = "HuaweiSteps"

    /**
     * ADAPT: real-device hardening — 扩展 vendor 的单一 "允许通知" keyword (vendor L4302)。
     * 首位保留 vendor 原始 keyword，后续为华为/AOSP 变体。
     */
    val CHANNEL_KEYWORDS: List<String> = listOf(
        "允许通知",           // vendor 主路径 (L4302)
        "显示通知",           // HarmonyOS 4.2 APP 级页面变体
        "通知管理",           // HarmonyOS 4.2 app 通知管理页标题
        "此应用通知",         // EMUI 变体
        "应用通知",           // EMUI / ColorOS 变体
        "Show notifications", // 英文 locale
        "Allow notifications" // 英文 locale 变体
    )

    /**
     * 打开 APP_NOTIFICATION_SETTINGS（app 级通知开关页）。
     * 无需 CHANNEL_ID — 即使 app 未注册 "OFF" channel 也可进入。
     *
     * ADAPT: real-device hardening — vendor 无此 fallback；replica 在 CHANNEL 路径
     * 2 次失败后（waitForChannelNotifPage 均未找到 keyword）激活。
     *
     * @return true 当 startActivity 无异常；false 当 service 为 null 或 startActivity 抛异常
     */
    fun launchAppNotificationSettings(service: AccessibilityService?): Boolean {
        if (service == null) return false
        return try {
            val intent = Intent("android.settings.APP_NOTIFICATION_SETTINGS").apply {
                putExtra("android.provider.extra.APP_PACKAGE", service.packageName)
                flags = 276824064 // vendor flag pattern (FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP | ...)
            }
            service.startActivity(intent)
            Log.i(TAG, "[NotifFallback] ✅ 已打开 APP_NOTIFICATION_SETTINGS: ${service.packageName}")
            true
        } catch (e: Exception) {
            Log.w(TAG, "[NotifFallback] ❌ APP_NOTIFICATION_SETTINGS 失败: ${e.message}")
            false
        }
    }
}
