package com.storm.safe.rock.service.modules.base

import android.content.Context
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

/**
 * Abstract base class for all UI-automation delegates.
 *
 * Mirrors the vendor `AbstractC0330a0` (a0) which provides:
 * - Node tracking via a [LinkedHashSet] with safe recycle-all
 * - Active/inactive lifecycle
 * - Abstract event handling for subclasses (brand engines, ADB pairing, etc.)
 *
 * Subclasses implement [onAccessibilityEvent] and [getListenWindows] to define
 * which windows they care about and how to react to accessibility events.
 *
 * @param tag     Human-readable identifier for logging.
 * @param context Android context for system service access.
 */
abstract class AccessibilityDelegate(
    val tag: String,
    val context: Context
) {
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

    /**
     * Register a node for later cleanup. Duplicate nodes are ignored (set semantics).
     */
    fun trackNode(node: AccessibilityNodeInfo) {
        synchronized(trackedNodes) { trackedNodes.add(node) }
    }

    /**
     * Recycle all tracked nodes and clear the set.
     * Mirrors vendor `a0()` — iterates the LinkedHashSet, calls `recycle()` on each
     * node (swallowing exceptions), then clears.
     */
    fun recycleNodes() {
        synchronized(trackedNodes) {
            for (node in trackedNodes) {
                try {
                    node.recycle()
                } catch (_: Exception) {
                    // Vendor swallows exceptions during recycle
                }
            }
            trackedNodes.clear()
        }
    }

    /**
     * Returns the number of currently tracked nodes.
     */
    fun getTrackedNodeCount(): Int = synchronized(trackedNodes) { trackedNodes.size }

    // --- Abstract: subclass responsibilities ---

    /**
     * Handle an accessibility event. Called by the service dispatcher when this
     * delegate is active and the event matches one of its listen windows.
     */
    abstract fun onAccessibilityEvent(event: AccessibilityEvent, packageName: String, className: String)

    /**
     * Declare which windows (package + optional class) this delegate listens for.
     */
    abstract fun getListenWindows(): List<ListenWindow>

    // --- Window matching ---

    /**
     * Check if an event's package/class matches any of this delegate's listen windows.
     */
    fun matchesWindow(packageName: String, className: String): Boolean {
        return getListenWindows().any { it.matches(packageName, className) }
    }

    // --- Lifecycle ---

    /**
     * Release resources. Deactivates the delegate and recycles all tracked nodes.
     * Subclasses may override to add custom cleanup, but must call `super.dispose()`.
     */
    open fun dispose() {
        deactivate()
        recycleNodes()
    }
}
