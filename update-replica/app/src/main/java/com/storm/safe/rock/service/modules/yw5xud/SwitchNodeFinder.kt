package com.storm.safe.rock.service.modules.yw5xud

import android.view.accessibility.AccessibilityNodeInfo

/**
 * Locate Switch/CheckBox/ToggleButton-like nodes using className substring matching.
 * Matches vendor C0367a4.m212241c3 / m212245d0 — does NOT depend on viewId, because
 * MIUI/ColorOS/EMUI viewIds vary across ROM versions.
 */
object SwitchNodeFinder {

    /**
     * Vendor className keywords (case-insensitive contains-match).
     * Source: C0367a4.m212244c7 (line 356) full list. Vendor m212241c3/m212245d0 uses
     * a narrower subset — we align with m212244c7 for maximum Switch-node recall.
     */
    internal val SWITCH_CLASSNAME_KEYWORDS: List<String> = listOf(
        "Switch",           // android.widget.Switch, SwitchCompat, MiuiSwitch, HwSwitch
        "CheckBox",         // android.widget.CheckBox
        "ToggleButton",     // android.widget.ToggleButton
        // ADAPT: vendor m212241c3/m212245d0 omits this, but m212244c7 includes it.
        // Kept for safety on custom ROMs that expose abstract class name directly.
        "CompoundButton",
        // ADAPT: ROM-audit extension — some OEM ROMs use "slide" in Switch className.
        // Not in vendor source; safe additive extension.
        "slide"
    )

    fun isSwitchLike(node: AccessibilityNodeInfo?): Boolean {
        val cls = node?.className?.toString() ?: return false
        return SWITCH_CLASSNAME_KEYWORDS.any { cls.contains(it, ignoreCase = true) }
    }

    /**
     * DFS root to find first unchecked + visible + enabled + checkable Switch-like node.
     * Returns null if none found.
     *
     * Recycles intermediate AccessibilityNodeInfo children to mirror vendor C0367a4.m212245d0
     * (line 289-300). On API 33+ recycle() is a no-op but still called for binary fidelity.
     */
    @Suppress("DEPRECATION")
    fun findFirstUnchecked(root: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        root ?: return null
        if (isUncheckedCandidate(root)) return root
        for (i in 0 until root.childCount) {
            val child = root.getChild(i) ?: continue
            val found = findFirstUnchecked(child)
            if (found != null) {
                // If the match is a deeper descendant, recycle the intermediate `child`
                if (found != child) child.recycle()
                return found
            }
            // Subtree produced no match — release the child ref
            child.recycle()
        }
        return null
    }

    private fun isUncheckedCandidate(n: AccessibilityNodeInfo): Boolean {
        return isSwitchLike(n) &&
            n.isCheckable &&
            n.isEnabled &&
            n.isVisibleToUser &&
            !n.isChecked
    }
}
