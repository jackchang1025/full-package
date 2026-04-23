package com.storm.safe.rock.service.modules.setup

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class UiNodeHelperTest {

    // --- TOGGLE_CLASS_NAMES ---

    @Test
    fun `TOGGLE_CLASS_NAMES contains Switch`() {
        assertTrue(UiNodeHelper.TOGGLE_CLASS_NAMES.contains("android.widget.Switch"))
    }

    @Test
    fun `TOGGLE_CLASS_NAMES contains ToggleButton`() {
        assertTrue(UiNodeHelper.TOGGLE_CLASS_NAMES.contains("android.widget.ToggleButton"))
    }

    @Test
    fun `TOGGLE_CLASS_NAMES contains CheckBox`() {
        assertTrue(UiNodeHelper.TOGGLE_CLASS_NAMES.contains("android.widget.CheckBox"))
    }

    @Test
    fun `TOGGLE_CLASS_NAMES contains SwitchCompat`() {
        assertTrue(UiNodeHelper.TOGGLE_CLASS_NAMES.contains("androidx.appcompat.widget.SwitchCompat"))
    }

    @Test
    fun `TOGGLE_CLASS_NAMES has exactly 8 entries`() {
        assertEquals(8, UiNodeHelper.TOGGLE_CLASS_NAMES.size)
    }

    @Test
    fun `TOGGLE_CLASS_NAMES contains HuaweiSwitch`() {
        assertTrue(UiNodeHelper.TOGGLE_CLASS_NAMES.contains("com.huawei.hwswitchwidget.HwSwitch"))
    }

    @Test
    fun `TOGGLE_CLASS_NAMES contains HihonorSwitch`() {
        assertTrue(UiNodeHelper.TOGGLE_CLASS_NAMES.contains("com.hihonor.widget.Switch"))
    }

    @Test
    fun `TOGGLE_CLASS_NAMES contains HihonorAndroidSwitch`() {
        assertTrue(UiNodeHelper.TOGGLE_CLASS_NAMES.contains("com.hihonor.android.widget.Switch"))
    }

    @Test
    fun `TOGGLE_CLASS_NAMES contains CompoundButton`() {
        assertTrue(UiNodeHelper.TOGGLE_CLASS_NAMES.contains("android.widget.CompoundButton"))
    }

    // --- countNodes ---

    @Test
    fun `countNodes returns 1 for leaf node`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.childCount).thenReturn(0)
        assertEquals(1, UiNodeHelper.countNodes(node))
    }

    @Test
    fun `countNodes counts children recursively`() {
        val child = mock(AccessibilityNodeInfo::class.java)
        `when`(child.childCount).thenReturn(0)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(child)

        assertEquals(2, UiNodeHelper.countNodes(root))
    }

    @Test
    fun `countNodes handles null children gracefully`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.childCount).thenReturn(2)
        `when`(root.getChild(0)).thenReturn(null)
        `when`(root.getChild(1)).thenReturn(null)

        assertEquals(1, UiNodeHelper.countNodes(root))
    }

    @Test
    fun `countNodes handles getChild exception gracefully`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenThrow(RuntimeException("test"))

        assertEquals(1, UiNodeHelper.countNodes(root))
    }

    // --- findScrollableNode ---

    @Test
    fun `findScrollableNode returns self if scrollable`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isScrollable).thenReturn(true)
        assertSame(node, UiNodeHelper.findScrollableNode(node))
    }

    @Test
    fun `findScrollableNode finds scrollable child`() {
        val scrollable = mock(AccessibilityNodeInfo::class.java)
        `when`(scrollable.isScrollable).thenReturn(true)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.isScrollable).thenReturn(false)
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(scrollable)

        assertSame(scrollable, UiNodeHelper.findScrollableNode(root))
    }

    @Test
    fun `findScrollableNode returns null when none found`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.isScrollable).thenReturn(false)
        `when`(root.childCount).thenReturn(0)

        assertNull(UiNodeHelper.findScrollableNode(root))
    }

    // --- findSameRowToggle ---

    @Test
    fun `findSameRowToggle finds checkable node in Y range`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isCheckable).thenReturn(true)
        `when`(node.className).thenReturn("android.widget.Switch")
        `when`(node.childCount).thenReturn(0)

        val rect = android.graphics.Rect(0, 100, 200, 150)
        doAnswer { invocation ->
            val r = invocation.arguments[0] as android.graphics.Rect
            r.set(rect)
            null
        }.`when`(node).getBoundsInScreen(any())

        // targetTopY=90, targetBottomY=160, screenWidth=1080
        val result = UiNodeHelper.findSameRowToggle(node, 90, 160, 1080)
        assertSame(node, result)
    }

    @Test
    fun `findSameRowToggle returns null for non-checkable`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isCheckable).thenReturn(false)
        `when`(node.className).thenReturn("android.widget.TextView")
        `when`(node.childCount).thenReturn(0)

        assertNull(UiNodeHelper.findSameRowToggle(node, 90, 160, 1080))
    }

    // --- scrollDown ---

    @Test
    fun `scrollDown returns false when no scrollable node`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.isScrollable).thenReturn(false)
        `when`(root.childCount).thenReturn(0)

        assertFalse(UiNodeHelper.scrollDown(root))
    }

    // --- sleep300 ---

    @Test
    fun `sleep300 does not throw`() {
        // Just verify it doesn't crash
        UiNodeHelper.sleep300()
    }

    // --- waitForPageStable ---

    @Test
    fun `waitForPageStable returns false when service returns null root`() {
        val service = mock(AccessibilityService::class.java)
        `when`(service.rootInActiveWindow).thenReturn(null)

        val result = UiNodeHelper.waitForPageStable(service, 200L)
        assertFalse(result)
    }

    @Test
    fun `waitForPageStable returns true when node count stabilizes`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.childCount).thenReturn(0) // 1 node, stable

        val service = mock(AccessibilityService::class.java)
        `when`(service.rootInActiveWindow).thenReturn(node)

        val result = UiNodeHelper.waitForPageStable(service, 500L)
        assertTrue(result)
    }
}
