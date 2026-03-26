package com.vendor.rat.auto.selector

import android.view.accessibility.AccessibilityNodeInfo
import li.songe.selector.Transform

fun createGkdTransform() = Transform<AccessibilityNodeInfo>(
    getAttr = { node, name ->
        (node as? AccessibilityNodeInfo)?.let {
            when (name) {
                "text" -> it.text
                "desc" -> it.contentDescription
                "id", "vid" -> it.viewIdResourceName
                "clickable" -> it.isClickable.toString()
                "checked" -> it.isChecked.toString()
                else -> null
            }
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
    }
)
