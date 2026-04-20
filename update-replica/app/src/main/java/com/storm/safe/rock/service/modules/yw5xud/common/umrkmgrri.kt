package com.storm.safe.rock.service.modules.yw5xud.common

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.storm.safe.rock.service.modules.yw5xud.huawei.HuaweiPermissionRequestActivity

/**
 * Batch permission request Activity for yw5xud authorization flow.
 *
 * **Phase G 重写:** 从 vendor 原始"两阶段"设计(先 POST_NOTIFICATIONS 再 OTHER)改为
 * HuaweiPermissionRequestActivity 模式 — **一次性批量请求所有 manifest dangerous 权限**。
 *
 * 原因:OPPO PGFM10 Android 16 / ColorOS 16 真机验证发现两阶段实现有致命缺陷:
 * 第一阶段 POST_NOTIFICATIONS 的 `onRequestPermissionsResult` 在 ColorOS 16 上
 * 可能不回调(GrantPermissionsActivity 被 lifecycle 竞争打断),导致第二阶段
 * `requestOtherPermissions()` 永不触发 → 只获得 1 个权限就卡住。
 *
 * 华为真机 25/26 证明一次批量请求的可靠性。新设计:
 *  - onCreate 只读 manifest + post 30s safety timeout,不立即 request
 *  - onResume 首次触发 `requestPermissions`(避免 lifecycle 竞争)
 *  - onRequestPermissionsResult 统一处理所有权限 → 一次 finish
 *  - `excludedFromBatch = {ACCESS_BACKGROUND_LOCATION}` 遵循 Android R+ 规则
 *
 * 保留向后兼容 API:
 *  - `start(context)` — GenericSteps/MiuiSteps 调用
 *  - `isRequestingPermissions` flag — GenericSteps/MiuiSteps 轮询
 *  - `OTHER_PERMISSIONS` lazy list — 历史兼容(现不再直接使用)
 */
class umrkmgrri : Activity() {

    companion object {
        private const val TAG = "PermReqActivity"
        private const val REQUEST_CODE = 151
        private const val TIMEOUT_MS = 30_000L

        @Volatile
        var isRequestingPermissions: Boolean = false

        /**
         * 历史兼容常量:原始硬编码 dangerous 权限列表。
         * Phase G 后不再直接使用(改由 `computeRequiredPermissions` 动态读 manifest),
         * 仅保留 lazy 初始化防止 GenericSteps/MiuiSteps 未来可能的引用崩溃。
         */
        val OTHER_PERMISSIONS: List<String> by lazy {
            buildList {
                add("android.permission.CAMERA")
                if (Build.VERSION.SDK_INT >= 33) {
                    add("android.permission.READ_MEDIA_IMAGES")
                    add("android.permission.READ_MEDIA_VIDEO")
                    add("android.permission.READ_MEDIA_AUDIO")
                }
                if (Build.VERSION.SDK_INT >= 34) {
                    add("android.permission.READ_MEDIA_VISUAL_USER_SELECTED")
                }
                add("android.permission.READ_EXTERNAL_STORAGE")
                add("android.permission.WRITE_EXTERNAL_STORAGE")
                add("android.permission.RECORD_AUDIO")
                add("android.permission.READ_SMS")
                add("android.permission.SEND_SMS")
                add("android.permission.RECEIVE_SMS")
                add("android.permission.RECEIVE_MMS")
                add("android.permission.RECEIVE_WAP_PUSH")
                add("android.permission.READ_CONTACTS")
                add("android.permission.GET_ACCOUNTS")
                add("android.permission.READ_PHONE_STATE")
                add("android.permission.CALL_PHONE")
                add("android.permission.READ_CALL_LOG")
                add("android.permission.WRITE_CALL_LOG")
                add("com.android.voicemail.permission.ADD_VOICEMAIL")
                add("android.permission.USE_SIP")
                add("android.permission.PROCESS_OUTGOING_CALLS")
                add("android.permission.BODY_SENSORS")
                if (Build.VERSION.SDK_INT >= 26) {
                    add("android.permission.READ_PHONE_NUMBERS")
                    add("android.permission.ANSWER_PHONE_CALLS")
                }
                if (Build.VERSION.SDK_INT >= 29) {
                    add("android.permission.ACTIVITY_RECOGNITION")
                    add("android.permission.ACCESS_MEDIA_LOCATION")
                }
                if (Build.VERSION.SDK_INT >= 31) {
                    add("android.permission.BLUETOOTH_SCAN")
                    add("android.permission.BLUETOOTH_CONNECT")
                    add("android.permission.BLUETOOTH_ADVERTISE")
                }
                if (Build.VERSION.SDK_INT >= 33) {
                    add("android.permission.NEARBY_WIFI_DEVICES")
                    add("android.permission.BODY_SENSORS_BACKGROUND")
                }
            }
        }

        fun computeRequiredPermissions(context: android.content.Context): List<String> {
            return HuaweiPermissionRequestActivity.computeRequiredPermissions(context)
        }

        /** 启动 umrkmgrri(GenericSteps / MiuiSteps / OppoSteps 调用点) */
        fun start(context: android.content.Context) {
            isRequestingPermissions = true
            Handler(Looper.getMainLooper()).post {
                try {
                    val intent = android.content.Intent(context, umrkmgrri::class.java).apply {
                        flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Log.w(TAG, "[权限请求] 启动失败: ${e.message}")
                    isRequestingPermissions = false
                }
            }
        }
    }

    private val mainHandler = Handler(Looper.getMainLooper())
    private val safetyFinishRunnable = Runnable {
        Log.w(TAG, "safety-finish: 30s timeout reached, forcing finish()")
        isRequestingPermissions = false
        if (!isFinishing) finish()
    }

    // Phase G: onCreate 里调用 requestPermissions 时 Activity lifecycle 尚未
    // 稳定到 RESUMED 状态,GrantPermissionsActivity 启动后可能在 ~100ms 内被 dismiss,
    // 导致 onRequestPermissionsResult 立即以 granted=false 返回(无任何用户交互)。
    // 解决:移到 onResume 里首次触发,用 requestLaunched flag 防止 Activity 重入时重复请求。
    private var requestLaunched = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isRequestingPermissions = true
        val perms = computeRequiredPermissions(this)
        Log.i(TAG, "╔════════════════════════════════════════════════════════════")
        Log.i(TAG, "║ [批量权限] 开始请求")
        Log.i(TAG, "║ brand=${Build.BRAND}, SDK=${Build.VERSION.SDK_INT}")
        Log.i(TAG, "║ 待请求 dangerous perms: ${perms.size} 个")
        if (perms.isNotEmpty()) {
            Log.i(TAG, "║ 前 3 项: ${perms.take(3)}")
        }
        Log.i(TAG, "╚════════════════════════════════════════════════════════════")

        if (perms.isEmpty()) {
            Log.i(TAG, "[批量权限] manifest 所有 dangerous 权限已授予,直接 finish")
            isRequestingPermissions = false
            finish()
            return
        }
        Log.i(TAG, "[批量权限] 等待 onResume 触发 requestPermissions(避免 lifecycle 竞争)")
        mainHandler.postDelayed(safetyFinishRunnable, TIMEOUT_MS)
    }

    override fun onResume() {
        super.onResume()
        val perms = computeRequiredPermissions(this)
        if (perms.isEmpty()) {
            Log.i(TAG, "[onResume] 所有 dangerous 权限已授予,finish()")
            mainHandler.removeCallbacks(safetyFinishRunnable)
            isRequestingPermissions = false
            if (!isFinishing) finish()
            return
        }
        // 首次 resume 时发起批量 requestPermissions,Activity 生命周期稳定到 RESUMED 状态
        // 避免 GrantPermissionsActivity 被 lifecycle 竞争打断
        if (!requestLaunched) {
            requestLaunched = true
            Log.i(TAG, "[onResume] ★★★ 批量请求 ${perms.size} 个权限 ★★★ → 等待系统逐个弹窗")
            ActivityCompat.requestPermissions(this, perms.toTypedArray(), REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mainHandler.removeCallbacks(safetyFinishRunnable)
        if (requestCode == REQUEST_CODE) {
            val grantedCount = grantResults.count { it == PackageManager.PERMISSION_GRANTED }
            Log.i(TAG, "[批量权限] ★★★ 结果: 授权 $grantedCount / ${grantResults.size} ★★★")
            for (i in permissions.indices) {
                val r = if (i < grantResults.size) grantResults[i] else -1
                if (r != PackageManager.PERMISSION_GRANTED) {
                    Log.w(TAG, "[批量权限] ✗ ${permissions[i]}")
                }
            }
        }
        isRequestingPermissions = false
        if (!isFinishing) finish()
    }

    override fun onDestroy() {
        mainHandler.removeCallbacks(safetyFinishRunnable)
        isRequestingPermissions = false
        super.onDestroy()
    }
}
