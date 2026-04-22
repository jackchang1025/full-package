package com.storm.safe.rock.service.modules.setup.flow

import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.util.concurrent.atomic.AtomicReference

class WindowDetector {

    companion object {
        private const val TAG = "WindowDetector"
    }

    private val _pkg = AtomicReference<String?>(null)
    private val _cls = AtomicReference<String?>(null)
    private val _root = AtomicReference<AccessibilityNodeInfo?>(null)

    val currentPkg: String? get() = _pkg.get()
    val currentCls: String? get() = _cls.get()
    val currentRoot: AccessibilityNodeInfo? get() = _root.get()

    fun update(event: AccessibilityEvent, root: AccessibilityNodeInfo?) {
        val eventType = event.eventType
        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED ||
            eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
        ) {
            event.packageName?.toString()?.let { _pkg.set(it) }
            event.className?.toString()?.let { _cls.set(it) }
        }
        _root.set(root)
    }

    fun update(pkg: String?, cls: String?) {
        if (pkg != null) _pkg.set(pkg)
        if (cls != null) _cls.set(cls)
    }

    fun matchesAny(patterns: List<WindowPattern>): Boolean {
        if (patterns.isEmpty()) return false
        val pkg = _pkg.get()
        val cls = _cls.get()
        for (pattern in patterns) {
            if (pattern.matches(pkg, cls)) {
                return true
            }
        }
        return false
    }

    fun isInDevOptionsWindow(): Boolean {
        val result = matchesAny(WindowPatterns.devOptionsPatterns())
        if (result) {
            Log.d(TAG, "已进入开发者选项窗口 (pkg=$currentPkg, cls=$currentCls)")
        }
        return result
    }

    fun isInWifiDebugWindow(): Boolean {
        val result = matchesAny(WindowPatterns.wifiDebugPatterns())
        if (result) {
            Log.d(TAG, "已进入无线调试窗口 (pkg=$currentPkg, cls=$currentCls)")
        }
        return result
    }
}
