package com.guard.wallet.gkd

import android.view.accessibility.AccessibilityNodeInfo
import li.songe.selector.QueryContext
import li.songe.selector.Transform

/**
 * GKD Transform 适配层 — 将 AccessibilityNodeInfo 映射到 GKD 的树遍历接口。
 *
 * 从 android 项目 (com.vendor.rat.auto.selector.GkdTransform) 复制并适配。
 * vendor-replica 使用 AccessibilityNodeInfoCompat 包装, 需要通过 .unwrap() 获取原始节点。
 */
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
            } catch (_: Exception) {
                // node recycled
            }
        }
    },
    getParent = { node ->
        try {
            node.parent
        } catch (_: Exception) {
            null
        }
    },
    getRoot = { node ->
        var current: AccessibilityNodeInfo = node
        var parentVar: AccessibilityNodeInfo? = try { node.parent } catch (_: Exception) { null }
        while (parentVar != null) {
            current = parentVar
            parentVar = try { current.parent } catch (_: Exception) { null }
        }
        current
    }
)

private fun getNodeAttr(node: AccessibilityNodeInfo, name: String): Any? {
    return when (name) {
        "text" -> node.text
        "desc" -> node.contentDescription
        "id", "vid" -> node.viewIdResourceName
        "name" -> node.className
        "clickable" -> node.isClickable
        "longClickable" -> node.isLongClickable
        "checked" -> node.isChecked
        "enabled" -> node.isEnabled
        "focusable" -> node.isFocusable
        "focused" -> node.isFocused
        "scrollable" -> node.isScrollable
        "selected" -> node.isSelected
        "checkable" -> node.isCheckable
        "visibleToUser" -> node.isVisibleToUser
        "editable" -> node.isEditable
        "password" -> node.isPassword
        "childCount" -> node.childCount
        "index" -> {
            try {
                val parent = node.parent ?: return@getNodeAttr null
                try {
                    for (i in 0 until parent.childCount) {
                        val child = parent.getChild(i) ?: continue
                        val match = child == node
                        child.recycle()
                        if (match) return@getNodeAttr i
                    }
                    null
                } finally {
                    parent.recycle()
                }
            } catch (_: Exception) { null }
        }
        "depth" -> {
            var depth = 0
            var p: AccessibilityNodeInfo? = try { node.parent } catch (_: Exception) { null }
            while (p != null) {
                depth++
                p = try { p.parent } catch (_: Exception) { null }
            }
            depth
        }
        "packageName" -> node.packageName
        "hintText" -> if (android.os.Build.VERSION.SDK_INT >= 26) node.hintText else null
        "tooltipText" -> if (android.os.Build.VERSION.SDK_INT >= 28) node.tooltipText else null
        "paneTitle" -> if (android.os.Build.VERSION.SDK_INT >= 28) node.paneTitle else null
        "stateDescription", "stateDesc" -> if (android.os.Build.VERSION.SDK_INT >= 30) node.stateDescription else null
        else -> null
    }
}

private fun getCharSequenceAttr(target: CharSequence, name: String): Any? {
    return when (name) {
        "length" -> target.length
        else -> null
    }
}
