package com.vendor.rat.auto.selector

import android.graphics.Rect
import android.view.accessibility.AccessibilityNodeInfo
import li.songe.selector.Transform

class GkdTransform : Transform<AccessibilityNodeInfo> {
    override fun getName(node: AccessibilityNodeInfo): CharSequence {
        return node.className ?: ""
    }

    override fun getAttr(node: AccessibilityNodeInfo, name: String): CharSequence? {
        return when (name) {
            "text" -> node.text
            "desc" -> node.contentDescription
            "id", "vid" -> node.viewIdResourceName
            "clickable" -> node.isClickable.toString()
            "checked" -> node.isChecked.toString()
            else -> null
        }
    }

    override fun getParent(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        return try {
            node.parent
        } catch (e: Exception) {
            null
        }
    }

    override fun getChildren(node: AccessibilityNodeInfo): Sequence<AccessibilityNodeInfo> {
        return sequence {
            try {
                for (i in 0 until node.childCount) {
                    node.getChild(i)?.let { yield(it) }
                }
            } catch (e: Exception) {
                // 节点已回收
            }
        }
    }
}
