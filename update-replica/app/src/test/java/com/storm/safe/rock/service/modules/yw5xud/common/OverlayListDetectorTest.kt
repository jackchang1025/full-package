package com.storm.safe.rock.service.modules.yw5xud.common

import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class OverlayListDetectorTest {

    @Test
    fun `detectPageType returns LIST when 搜索应用 visible`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val n = mock(AccessibilityNodeInfo::class.java)
        `when`(n.isVisibleToUser).thenReturn(true)
        `when`(root.findAccessibilityNodeInfosByText("搜索应用")).thenReturn(listOf(n))
        assertEquals(OverlayListDetector.PageType.LIST, OverlayListDetector.detect(root))
    }

    @Test
    fun `detectPageType returns LIST when 显示在其他应用的上层 visible`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val n = mock(AccessibilityNodeInfo::class.java)
        `when`(n.isVisibleToUser).thenReturn(true)
        `when`(root.findAccessibilityNodeInfosByText("搜索应用")).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("显示在其他应用的上层")).thenReturn(listOf(n))
        assertEquals(OverlayListDetector.PageType.LIST, OverlayListDetector.detect(root))
    }

    @Test
    fun `detectPageType returns DETAIL when 允许显示在其他应用的上层 switch visible`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val n = mock(AccessibilityNodeInfo::class.java)
        `when`(n.isVisibleToUser).thenReturn(true)
        `when`(n.className).thenReturn("android.widget.Switch")
        `when`(root.findAccessibilityNodeInfosByText(any())).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("允许显示在其他应用的上层")).thenReturn(listOf(n))
        assertEquals(OverlayListDetector.PageType.DETAIL, OverlayListDetector.detect(root))
    }

    @Test
    fun `detectPageType returns UNKNOWN when root is null`() {
        assertEquals(OverlayListDetector.PageType.UNKNOWN, OverlayListDetector.detect(null))
    }

    @Test
    fun `detectPageType returns UNKNOWN when no keyword matches`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByText(any())).thenReturn(emptyList())
        assertEquals(OverlayListDetector.PageType.UNKNOWN, OverlayListDetector.detect(root))
    }
}
