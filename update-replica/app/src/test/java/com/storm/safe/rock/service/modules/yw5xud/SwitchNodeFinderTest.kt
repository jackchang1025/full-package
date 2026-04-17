package com.storm.safe.rock.service.modules.yw5xud

import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.*

class SwitchNodeFinderTest {

    private fun mockNode(
        className: String?,
        checkable: Boolean = true,
        enabled: Boolean = true,
        visible: Boolean = true,
        checked: Boolean = false,
        childCount: Int = 0
    ): AccessibilityNodeInfo {
        val n = mock(AccessibilityNodeInfo::class.java)
        `when`(n.className).thenReturn(className)
        `when`(n.isCheckable).thenReturn(checkable)
        `when`(n.isEnabled).thenReturn(enabled)
        `when`(n.isVisibleToUser).thenReturn(visible)
        `when`(n.isChecked).thenReturn(checked)
        `when`(n.childCount).thenReturn(childCount)
        return n
    }

    @Test
    fun `isSwitchLike matches android widget Switch`() {
        val n = mockNode("android.widget.Switch")
        assertTrue(SwitchNodeFinder.isSwitchLike(n))
    }

    @Test
    fun `isSwitchLike matches androidx SwitchCompat`() {
        val n = mockNode("androidx.appcompat.widget.SwitchCompat")
        assertTrue(SwitchNodeFinder.isSwitchLike(n))
    }

    @Test
    fun `isSwitchLike matches MiuiSwitch and HwSwitch regardless of case`() {
        assertTrue(SwitchNodeFinder.isSwitchLike(mockNode("miui.widget.MiuiSwitch")))
        assertTrue(SwitchNodeFinder.isSwitchLike(mockNode("com.huawei.uikit.hwcheckbox.widget.HWSWITCH")))
    }

    @Test
    fun `isSwitchLike matches CheckBox ToggleButton CompoundButton`() {
        assertTrue(SwitchNodeFinder.isSwitchLike(mockNode("android.widget.CheckBox")))
        assertTrue(SwitchNodeFinder.isSwitchLike(mockNode("android.widget.ToggleButton")))
        assertTrue(SwitchNodeFinder.isSwitchLike(mockNode("android.widget.CompoundButton")))
    }

    @Test
    fun `isSwitchLike rejects plain Button or TextView`() {
        assertFalse(SwitchNodeFinder.isSwitchLike(mockNode("android.widget.Button")))
        assertFalse(SwitchNodeFinder.isSwitchLike(mockNode("android.widget.TextView")))
    }

    @Test
    fun `isSwitchLike rejects null className`() {
        assertFalse(SwitchNodeFinder.isSwitchLike(mockNode(null)))
    }

    @Test
    fun `findFirstUnchecked returns first unchecked visible enabled Switch`() {
        val root = mockNode("android.view.ViewGroup", checkable = false, childCount = 2)
        val child1 = mockNode("android.widget.Switch", checked = true)
        val child2 = mockNode("android.widget.Switch", checked = false)
        `when`(root.getChild(0)).thenReturn(child1)
        `when`(root.getChild(1)).thenReturn(child2)

        val result = SwitchNodeFinder.findFirstUnchecked(root)

        assertSame(child2, result)
    }

    @Test
    fun `findFirstUnchecked returns null when all Switches are checked`() {
        val root = mockNode("android.view.ViewGroup", checkable = false, childCount = 1)
        val child = mockNode("android.widget.Switch", checked = true)
        `when`(root.getChild(0)).thenReturn(child)

        assertNull(SwitchNodeFinder.findFirstUnchecked(root))
    }

    @Test
    fun `findFirstUnchecked skips invisible disabled Switch`() {
        val root = mockNode("android.view.ViewGroup", checkable = false, childCount = 2)
        val invisible = mockNode("android.widget.Switch", visible = false)
        val disabled = mockNode("android.widget.Switch", enabled = false)
        `when`(root.getChild(0)).thenReturn(invisible)
        `when`(root.getChild(1)).thenReturn(disabled)

        assertNull(SwitchNodeFinder.findFirstUnchecked(root))
    }

    @Test
    fun `findFirstUnchecked returns null for null root`() {
        assertNull(SwitchNodeFinder.findFirstUnchecked(null))
    }

    @Test
    fun `findFirstUnchecked returns root itself when root is unchecked Switch`() {
        val rootSwitch = mockNode("android.widget.Switch", checked = false, childCount = 0)
        assertSame(rootSwitch, SwitchNodeFinder.findFirstUnchecked(rootSwitch))
    }
}
