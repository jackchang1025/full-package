package com.vendor.rat.auto.selector

import android.view.accessibility.AccessibilityNodeInfo
import li.songe.selector.QueryContext
import li.songe.selector.Transform

fun createGkdTransform() = Transform<AccessibilityNodeInfo>(
    getAttr = { target, name ->
        when (target) {
            is QueryContext<*> -> {
                val node = target.current as? AccessibilityNodeInfo
                    ?: return@Transform null
                getNodeAttr(node, name)
            }
            is AccessibilityNodeInfo -> getNodeAttr(target, name)
            is CharSequence -> getCharSequenceAttr(target, name)
            else -> null
        }
    },
    getName = { node -> node.className },
    getChildren = { node ->
        sequence {
            try {
                for (i in 0 until node.childCount) {
                    node.getChild(i)?.let { yield(it) }
                }
            } catch (e: Exception) {
                // 节点已回收
            }
        }
    },
    getParent = { node ->
        try {
            node.parent
        } catch (e: Exception) {
            null
        }
    },
    getRoot = { node ->
        var current: AccessibilityNodeInfo = node
        var parentVar: AccessibilityNodeInfo? = try { node.parent } catch (e: Exception) { null }
        while (parentVar != null) {
            current = parentVar
            parentVar = try { current.parent } catch (e: Exception) { null }
        }
        current
    }
)

private fun getNodeAttr(node: AccessibilityNodeInfo, name: String): Any? {
    return when (name) {
        "text" -> node.text
        "desc" -> node.contentDescription
        "id", "vid" -> node.viewIdResourceName
        "clickable" -> node.isClickable
        "checked" -> node.isChecked
        "enabled" -> node.isEnabled
        "focusable" -> node.isFocusable
        "scrollable" -> node.isScrollable
        "selected" -> node.isSelected
        "checkable" -> node.isCheckable
        "visibleToUser" -> node.isVisibleToUser
        "childCount" -> node.childCount
        "index" -> {
            try {
                val parent = node.parent ?: return@getNodeAttr null
                for (i in 0 until parent.childCount) {
                    if (parent.getChild(i) == node) return@getNodeAttr i
                }
                null
            } catch (e: Exception) { null }
        }
        "depth" -> {
            var depth = 0
            var p: AccessibilityNodeInfo? = try { node.parent } catch (e: Exception) { null }
            while (p != null) {
                depth++
                p = try { p.parent } catch (e: Exception) { null }
            }
            depth
        }
        else -> null
    }
}

private fun getCharSequenceAttr(target: CharSequence, name: String): Any? {
    return when (name) {
        "length" -> target.length
        else -> null
    }
}
