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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is UiObject) return false
        return nodeInfo == other.nodeInfo
    }

    override fun hashCode(): Int = nodeInfo.hashCode()
}
