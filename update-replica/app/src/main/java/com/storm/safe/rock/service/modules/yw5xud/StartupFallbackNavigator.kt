package com.storm.safe.rock.service.modules.yw5xud

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log

/**
 * StartupFallbackNavigator — 华为自启动管理启动失败 fallback。
 *
 * ADAPT: real-device hardening — vendor `m212196f3` (L6861-6880) 尝试 4 个
 * `com.huawei.systemmanager.*` Intent，全部被华为 HarmonyOS 4.2 的权限检查
 * (`com.huawei.permission.external_app_settings.USE_COMPONENT`) 拒绝（见
 * /tmp/huawei-t19e.log FIN-AL60 真机日志）。vendor 原版无 fallback，catch(Exception)
 * 仅 log 后直接 return false（L6872-6877）。
 *
 * 本 fallback 打开**通用 APPLICATION_DETAILS_SETTINGS**（无系统权限要求），
 * 让用户看到目标 app 的应用详情页。下游逻辑可选：
 *   a) 继续走 UI 自动化（从应用详情页进入"启动管理"子项）
 *   b) 仅标记此 step 失败但不 crash 整个流程
 *
 * 本类只负责**打开详情页**；UI 导航由调用方编排。
 */
object StartupFallbackNavigator {
    private const val TAG = "HuaweiSteps"

    /**
     * 尝试打开 APPLICATION_DETAILS_SETTINGS 页面（无 USE_COMPONENT 权限要求）。
     *
     * ADAPT: real-device hardening — vendor 无此 fallback；
     * 本方法为 FIN-AL60 真机 4 个 STARTUP_COMPONENTS 全部 Permission Denial 后的降级路径。
     *
     * @return true 当 startActivity 无异常；false 当 service null 或被系统拒绝
     */
    fun launchAppDetailsSettings(service: AccessibilityService?): Boolean {
        if (service == null) return false
        return try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:${service.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            }
            service.startActivity(intent)
            Log.i(TAG, "[StartupFallback] ✅ 打开应用详情页 fallback: ${service.packageName}")
            true
        } catch (e: SecurityException) {
            Log.w(TAG, "[StartupFallback] ❌ APPLICATION_DETAILS_SETTINGS 被拒: ${e.message}")
            false
        } catch (e: Exception) {
            Log.w(TAG, "[StartupFallback] ❌ 异常: ${e.message}")
            false
        }
    }
}
