package com.storm.safe.rock.auto.a11y

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class UiAutomationTest {

    private lateinit var service: AccessibilityService
    private lateinit var context: Context
    private lateinit var ui: UiAutomation

    @Before
    fun setup() {
        service = mock(AccessibilityService::class.java)
        context = mock(Context::class.java)
        `when`(context.packageName).thenReturn("com.test.app")
        ui = UiAutomation(service, context)
    }

    private fun mockNode(
        text: CharSequence? = null,
        className: CharSequence? = "android.widget.TextView",
        viewId: String? = null,
        clickable: Boolean = false,
        checked: Boolean = false,
        checkable: Boolean = false,
        visible: Boolean = true,
        scrollable: Boolean = false,
        childCount: Int = 0
    ): AccessibilityNodeInfo {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.text).thenReturn(text)
        `when`(node.className).thenReturn(className)
        `when`(node.viewIdResourceName).thenReturn(viewId)
        `when`(node.isClickable).thenReturn(clickable)
        `when`(node.isChecked).thenReturn(checked)
        `when`(node.isCheckable).thenReturn(checkable)
        `when`(node.isVisibleToUser).thenReturn(visible)
        `when`(node.isScrollable).thenReturn(scrollable)
        `when`(node.childCount).thenReturn(childCount)
        return node
    }

    @Test
    fun `query returns null when no root window`() {
        `when`(service.rootInActiveWindow).thenReturn(null)
        assertNull(ui.query("[text=\"允许\"]"))
    }

    @Test
    fun `query finds matching node by text`() {
        val root = mockNode(className = "android.widget.FrameLayout", childCount = 1)
        val child = mockNode(text = "允许", clickable = true)
        `when`(root.getChild(0)).thenReturn(child)
        `when`(service.rootInActiveWindow).thenReturn(root)

        val result = ui.query("[text=\"允许\"]")
        assertNotNull(result)
        assertEquals("允许", result.text.toString())
    }

    @Test
    fun `query returns null for no match`() {
        val root = mockNode(className = "android.widget.FrameLayout", childCount = 1)
        val child = mockNode(text = "拒绝")
        `when`(root.getChild(0)).thenReturn(child)
        `when`(service.rootInActiveWindow).thenReturn(root)

        assertNull(ui.query("[text=\"允许\"]"))
    }

    @Test
    fun `click performs ACTION_CLICK on clickable node`() {
        val node = mockNode(clickable = true)
        `when`(node.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)
        assertTrue(ui.click(node))
        verify(node).performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    @Test
    fun `click walks up to clickable parent`() {
        val child = mockNode(clickable = false)
        val parent = mockNode(clickable = true)
        `when`(child.parent).thenReturn(parent)
        `when`(parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)
        assertTrue(ui.click(child))
        verify(parent).performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    @Test
    fun `pressBack delegates to performGlobalAction`() {
        `when`(service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)).thenReturn(true)
        assertTrue(ui.pressBack())
    }

    @Test
    fun `pressHome delegates to performGlobalAction`() {
        `when`(service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME)).thenReturn(true)
        assertTrue(ui.pressHome())
    }

    @Test
    fun `selectorCache caches parsed selector`() {
        val s1 = ui.cachedSelector("[text=\"允许\"]")
        val s2 = ui.cachedSelector("[text=\"允许\"]")
        assertTrue(s1 === s2)
    }
}
