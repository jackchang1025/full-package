package com.vendor.rat.auto.selector

import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import li.songe.selector.MatchOption
import li.songe.selector.Selector

object GkdSelectorHelper {
    private const val TAG = "GkdSelectorHelper"
    private val transform = GkdTransform()
    private val defaultOption = MatchOption()

    fun match(root: AccessibilityNodeInfo?, selector: String): AccessibilityNodeInfo? {
        if (root == null) return null
        return try {
            val sel = Selector.parse(selector)
            sel.match(root, transform, defaultOption)
        } catch (e: Exception) {
            Log.e(TAG, "Selector match failed: $selector", e)
            null
        }
    }
}
