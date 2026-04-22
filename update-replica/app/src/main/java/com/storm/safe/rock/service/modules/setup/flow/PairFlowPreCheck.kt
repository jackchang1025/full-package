package com.storm.safe.rock.service.modules.setup.flow

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.PowerManager
import android.util.Log

/**
 * ADB WiFi pairing pre-condition checker.
 * Validates all necessary conditions before starting the pairing flow.
 *
 * Check order: hard prerequisites -> skip conditions -> soft prerequisites
 */
class PairFlowPreCheck(
    private val context: Context,
    private val service: AccessibilityService
) {
    companion object {
        private const val TAG = "PairFlowPreCheck"
        private const val PREFS_NAME = "system_optimize"
    }

    data class Result(
        val canProceed: Boolean,
        val skipReason: SkipReason? = null,
        val failReason: FailReason? = null
    )

    enum class SkipReason {
        ALREADY_PAIRED,       // pair_completed = true and ADB connected
        ALREADY_RUNNING,      // pairing flow already in progress
        LOCAL_SERVICE_ALIVE,  // local-service running, no need to re-pair
        PAIR_STATE_TERMINAL   // pairState already SUCCESS/FINISH
    }

    enum class FailReason {
        API_TOO_LOW,          // Android < 11 (API 30), wireless debugging unavailable
        NO_WIFI,              // not connected to WiFi, mDNS/TCP unusable
        DEVICE_IDLE,          // Doze mode, network restricted + UI inoperable
        SCREEN_OFF,           // screen off, UI automation cannot operate
        SERVICE_NOT_READY     // rootInActiveWindow unavailable
    }

    /**
     * Run all pre-checks.
     * Check order: hard prerequisites -> skip conditions -> soft prerequisites
     *
     * vendor: C0360a2.m212070h4 (L3415-3530) pre-check pattern
     */
    fun check(
        isPairRunning: Boolean,
        isAdbConnected: Boolean,
        debugPort: Int,
        isLocalServiceAlive: Boolean = false,
        pairState: PairState = PairState.PAIR_DEPT_UNKNOWN
    ): Result {
        // ═══ P0: 硬性前提 (不满足则流程必死) ═══

        if (Build.VERSION.SDK_INT < 30) {
            Log.w(TAG, "API ${Build.VERSION.SDK_INT} < 30, wireless debugging unavailable")
            return Result(false, failReason = FailReason.API_TOO_LOW)
        }

        if (!isWifiConnected()) {
            Log.w(TAG, "WiFi not connected")
            return Result(false, failReason = FailReason.NO_WIFI)
        }

        // vendor: m212070h4 L3422-3426 — Doze 模式下网络受限 + UI 不可操作
        if (isDeviceIdle()) {
            Log.w(TAG, "Device in Doze/idle mode")
            return Result(false, failReason = FailReason.DEVICE_IDLE)
        }

        // ═══ P0: 跳过条件 (无需执行配对) ═══

        if (isPairRunning) {
            Log.i(TAG, "Pairing flow already running, skip")
            return Result(false, skipReason = SkipReason.ALREADY_RUNNING)
        }

        // vendor: m212070h4 L3476-3478 — local-service 已运行，无需重新配对
        if (isLocalServiceAlive) {
            Log.i(TAG, "local-service alive, skip pairing")
            return Result(false, skipReason = SkipReason.LOCAL_SERVICE_ALIVE)
        }

        // vendor: m212078i3 L3834 — 终态不再启动流程
        if (pairState == PairState.PAIR_DEPT_PAIR_SUCCESS ||
            pairState == PairState.PAIR_DEPT_PREPARE_FINISH ||
            pairState == PairState.PAIR_DEPT_PAIR_FINISH) {
            Log.i(TAG, "PairState already terminal ($pairState), skip")
            return Result(false, skipReason = SkipReason.PAIR_STATE_TERMINAL)
        }

        if (isAlreadyPaired() && isAdbConnected) {
            Log.i(TAG, "Already paired and ADB connected, skip")
            return Result(false, skipReason = SkipReason.ALREADY_PAIRED)
        }

        if (isAlreadyPaired() && debugPort > 0 && hasKeyFiles()) {
            Log.i(TAG, "Already paired with key files (port=$debugPort), reconnect only, skip pairing")
            return Result(false, skipReason = SkipReason.ALREADY_PAIRED)
        }

        // ═══ P1: 软性前提 (UI 自动化会失败) ═══

        if (!isScreenOn()) {
            Log.w(TAG, "Screen off")
            return Result(false, failReason = FailReason.SCREEN_OFF)
        }

        // ADAPT: MIUI 上 rootInActiveWindow 即使服务正常也可能返回 null（返回桌面/搜索框）
        // 不再以此作为服务就绪指标，WindowDetector 通过事件追踪不依赖 rootInActiveWindow
        if (service.rootInActiveWindow == null) {
            Log.w(TAG, "rootInActiveWindow is null (MIUI 上正常，不阻止配对)")
        }

        Log.i(TAG, "All pre-checks passed")
        return Result(canProceed = true)
    }

    /**
     * Lightweight check for triggerPairFlow -- only hard prerequisites.
     * triggerPairFlow is a force-trigger, so skip conditions are not checked.
     */
    fun checkMinimal(): Result {
        if (Build.VERSION.SDK_INT < 30) {
            Log.w(TAG, "API ${Build.VERSION.SDK_INT} < 30, wireless debugging unavailable")
            return Result(false, failReason = FailReason.API_TOO_LOW)
        }

        if (!isWifiConnected()) {
            Log.w(TAG, "WiFi not connected")
            return Result(false, failReason = FailReason.NO_WIFI)
        }

        if (isDeviceIdle()) {
            Log.w(TAG, "Device in Doze/idle mode")
            return Result(false, failReason = FailReason.DEVICE_IDLE)
        }

        Log.i(TAG, "Minimal pre-checks passed")
        return Result(canProceed = true)
    }

    // ----------------------------------------------------------------
    // Private check methods
    // ----------------------------------------------------------------

    private fun isWifiConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    private fun isAlreadyPaired(): Boolean {
        return context.getSharedPreferences(PREFS_NAME, 0)
            .getBoolean("pair_completed", false)
    }

    private fun hasKeyFiles(): Boolean {
        val keyDir = context.getDir("adb_key", Context.MODE_PRIVATE)
        val certFile = java.io.File(keyDir, "cert.pem")
        val keyFile = java.io.File(keyDir, "private.key")
        return certFile.exists() && keyFile.exists()
    }

    private fun isScreenOn(): Boolean {
        val pm = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
            ?: return true
        return pm.isInteractive
    }

    /**
     * vendor: m212070h4 L3422-3426 — Doze 模式检查
     * Doze 下网络受限 (JobScheduler/alarm 延迟)，UI 不可操作。
     */
    private fun isDeviceIdle(): Boolean {
        if (Build.VERSION.SDK_INT < 23) return false
        val pm = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
            ?: return false
        return pm.isDeviceIdleMode
    }
}
