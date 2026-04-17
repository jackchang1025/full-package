package com.storm.safe.rock.service.modules.yw5xud

import android.graphics.Rect
import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AppCardMatcherTest {

    @Test
    fun `findCardRect returns rect when node matches by appLabel`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val n = mock(AccessibilityNodeInfo::class.java)
        `when`(n.isVisibleToUser).thenReturn(true)
        `when`(n.text).thenReturn("系统服务")
        `when`(n.getBoundsInScreen(any(Rect::class.java))).thenAnswer {
            (it.arguments[0] as Rect).set(100, 200, 400, 500)
            null
        }
        `when`(root.findAccessibilityNodeInfosByText("系统服务")).thenReturn(listOf(n))
        val rect = AppCardMatcher.findCardRect(root, appLabel = "系统服务", packageName = "com.x")
        assertNotNull(rect)
        assertEquals(100, rect!!.left)
    }

    @Test
    fun `findCardRect falls back to packageName when appLabel miss`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val n = mock(AccessibilityNodeInfo::class.java)
        `when`(n.isVisibleToUser).thenReturn(true)
        `when`(n.text).thenReturn("com.storm.safe.rock")
        `when`(n.getBoundsInScreen(any(Rect::class.java))).thenAnswer {
            (it.arguments[0] as Rect).set(10, 20, 30, 40)
            null
        }
        `when`(root.findAccessibilityNodeInfosByText("系统服务")).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("com.storm.safe.rock")).thenReturn(listOf(n))
        val rect = AppCardMatcher.findCardRect(root, "系统服务", "com.storm.safe.rock")
        assertNotNull(rect)
    }

    @Test
    fun `findCardRect falls back to contentDescription DFS`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val child = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByText(any())).thenReturn(emptyList())
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(child)
        `when`(child.isVisibleToUser).thenReturn(true)
        `when`(child.contentDescription).thenReturn("系统服务")
        `when`(child.childCount).thenReturn(0)
        `when`(child.getBoundsInScreen(any(Rect::class.java))).thenAnswer {
            (it.arguments[0] as Rect).set(50, 60, 70, 80)
            null
        }
        val rect = AppCardMatcher.findCardRect(root, "系统服务", "com.x")
        assertNotNull(rect)
    }

    @Test
    fun `findCardRect returns null when nothing matches`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByText(any())).thenReturn(emptyList())
        `when`(root.childCount).thenReturn(0)
        assertNull(AppCardMatcher.findCardRect(root, "x", "y"))
    }

    @Test
    fun `findCardRect returns null when root is null`() {
        assertNull(AppCardMatcher.findCardRect(null, "x", "y"))
    }
}
