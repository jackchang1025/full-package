package com.storm.safe.rock.service.modules.yw5xud.common

import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class BatteryEntryFinderTest {
    @Test
    fun `keywords list includes legacy vendor keyword first`() {
        assertEquals("电池", BatteryEntryFinder.KEYWORDS.first())
    }

    @Test
    fun `keywords list covers HarmonyOS variants`() {
        val list = BatteryEntryFinder.KEYWORDS
        assertTrue("缺'电池优化'", list.contains("电池优化"))
        assertTrue("缺'电池与性能'", list.contains("电池与性能"))
        assertTrue("缺'省电管理'", list.contains("省电管理"))
        assertTrue("缺 'Battery'", list.contains("Battery"))
    }

    @Test fun `keywords list covers 电源 and 耗电 from vendor doc`() {
        assertTrue("缺'电源'", BatteryEntryFinder.KEYWORDS.contains("电源"))
        assertTrue("缺'耗电'", BatteryEntryFinder.KEYWORDS.contains("耗电"))
    }

    @Test
    fun `find returns null when root is null`() {
        assertEquals(null, BatteryEntryFinder.find(null))
    }

    @Test
    fun `find returns first matching visible node by text`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isVisibleToUser).thenReturn(true)
        `when`(node.text).thenReturn("电池与性能")
        `when`(root.findAccessibilityNodeInfosByText("电池")).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("电池优化")).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("电池与性能")).thenReturn(listOf(node))
        assertEquals(node, BatteryEntryFinder.find(root))
    }

    @Test
    fun `find skips invisible nodes`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val invis = mock(AccessibilityNodeInfo::class.java)
        `when`(invis.isVisibleToUser).thenReturn(false)
        `when`(invis.text).thenReturn("电池")
        `when`(root.findAccessibilityNodeInfosByText("电池")).thenReturn(listOf(invis))
        assertEquals(null, BatteryEntryFinder.find(root))
    }

    @Test
    fun `find falls back to contentDescription when text miss`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isVisibleToUser).thenReturn(true)
        `when`(node.text).thenReturn(null)
        `when`(node.contentDescription).thenReturn("电池")
        `when`(root.findAccessibilityNodeInfosByText(any())).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByViewId(any())).thenReturn(emptyList())
        // collectNodesByContentDescription DFS
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(node)
        `when`(node.childCount).thenReturn(0)
        assertEquals(node, BatteryEntryFinder.find(root))
    }
}
