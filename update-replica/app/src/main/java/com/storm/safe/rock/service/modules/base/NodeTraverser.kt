package com.storm.safe.rock.service.modules.base

import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.modules.cipher.UiObject
import java.util.LinkedList

object NodeTraverser {

    /**
     * DFS: find first node whose text contains [text] (case-insensitive)
     */
    fun findByText(root: AccessibilityNodeInfo?, text: String): UiObject? {
        if (root == null || text.isEmpty()) return null
        val rootObj = UiObject.createRoot(root) ?: return null
        return rootObj.findFirst { obj ->
            obj.getText()?.contains(text, ignoreCase = true) == true
        }
    }

    /**
     * DFS: find first node whose className contains [className]
     */
    fun findByClassName(root: AccessibilityNodeInfo?, className: String): UiObject? {
        if (root == null || className.isEmpty()) return null
        val rootObj = UiObject.createRoot(root) ?: return null
        return rootObj.findFirst { obj ->
            try {
                obj.nodeInfo.className?.toString()?.contains(className, ignoreCase = true) == true
            } catch (_: Exception) { false }
        }
    }

    /**
     * Find first node whose viewIdResourceName ends with [idSuffix]
     */
    fun findById(root: AccessibilityNodeInfo?, idSuffix: String): UiObject? {
        if (root == null || idSuffix.isEmpty()) return null
        val rootObj = UiObject.createRoot(root) ?: return null
        return rootObj.findFirst { obj ->
            obj.getResourceId()?.endsWith(idSuffix, ignoreCase = true) == true
        }
    }

    /**
     * Find all nodes matching [predicate]
     */
    fun findAll(root: AccessibilityNodeInfo?, predicate: (UiObject) -> Boolean): List<UiObject> {
        if (root == null) return emptyList()
        val rootObj = UiObject.createRoot(root) ?: return emptyList()
        val results = mutableListOf<UiObject>()
        rootObj.findAll(predicate, results)
        return results
    }

    /**
     * Find all nodes whose text contains [text]
     */
    fun findAllByText(root: AccessibilityNodeInfo?, text: String): List<UiObject> {
        return findAll(root) { it.getText()?.contains(text, ignoreCase = true) == true }
    }

    /**
     * Walk up from [node] to find nearest clickable ancestor
     */
    fun findClickableParent(node: AccessibilityNodeInfo?): UiObject? {
        var current = node
        var depth = 0
        while (current != null && depth < 20) { // safety limit
            try {
                if (current.isClickable) return UiObject(current, depth)
                current = current.parent
                depth++
            } catch (_: Exception) { return null }
        }
        return null
    }

    /**
     * BFS traversal returning all nodes level by level
     */
    fun bfsAll(root: AccessibilityNodeInfo?): List<UiObject> {
        if (root == null) return emptyList()
        val result = mutableListOf<UiObject>()
        val queue = LinkedList<Pair<AccessibilityNodeInfo, Int>>()
        queue.add(Pair(root, 0))
        while (queue.isNotEmpty()) {
            val (node, depth) = queue.poll()
            result.add(UiObject(node, depth))
            try {
                for (i in 0 until node.childCount) {
                    node.getChild(i)?.let { queue.add(Pair(it, depth + 1)) }
                }
            } catch (_: Exception) {}
        }
        return result
    }
}
