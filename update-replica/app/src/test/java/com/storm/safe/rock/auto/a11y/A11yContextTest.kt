package com.storm.safe.rock.auto.a11y

import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class A11yContextTest {

    private fun mockNode(
        text: CharSequence? = null,
        className: CharSequence? = "android.widget.TextView",
        viewId: String? = null,
        desc: CharSequence? = null,
        clickable: Boolean = false,
        checked: Boolean = false,
        checkable: Boolean = false,
        scrollable: Boolean = false,
        visible: Boolean = true,
        editable: Boolean = false,
        focusable: Boolean = false,
        longClickable: Boolean = false,
        childCount: Int = 0,
        packageName: CharSequence? = "com.android.settings"
    ): AccessibilityNodeInfo {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.text).thenReturn(text)
        `when`(node.className).thenReturn(className)
        `when`(node.viewIdResourceName).thenReturn(viewId)
        `when`(node.contentDescription).thenReturn(desc)
        `when`(node.isClickable).thenReturn(clickable)
        `when`(node.isChecked).thenReturn(checked)
        `when`(node.isCheckable).thenReturn(checkable)
        `when`(node.isScrollable).thenReturn(scrollable)
        `when`(node.isVisibleToUser).thenReturn(visible)
        `when`(node.isEditable).thenReturn(editable)
        `when`(node.isFocusable).thenReturn(focusable)
        `when`(node.isLongClickable).thenReturn(longClickable)
        `when`(node.childCount).thenReturn(childCount)
        `when`(node.packageName).thenReturn(packageName)
        return node
    }

    @Test
    fun `getCacheAttr returns text`() {
        val ctx = A11yContext()
        val node = mockNode(text = "允许")
        assertEquals("允许", (ctx.getCacheAttr(node, "text") as CharSequence).toString())
    }

    @Test
    fun `getCacheAttr returns className as name`() {
        val ctx = A11yContext()
        val node = mockNode(className = "android.widget.Switch")
        assertEquals("android.widget.Switch", (ctx.getCacheAttr(node, "name") as CharSequence).toString())
    }

    @Test
    fun `getCacheAttr returns clickable boolean`() {
        val ctx = A11yContext()
        assertEquals(true, ctx.getCacheAttr(mockNode(clickable = true), "clickable"))
    }

    @Test
    fun `getCacheAttr returns checked boolean`() {
        val ctx = A11yContext()
        assertEquals(true, ctx.getCacheAttr(mockNode(checked = true), "checked"))
    }

    @Test
    fun `getCacheAttr returns visibleToUser`() {
        val ctx = A11yContext()
        assertEquals(false, ctx.getCacheAttr(mockNode(visible = false), "visibleToUser"))
    }

    @Test
    fun `getCacheAttr returns id`() {
        val ctx = A11yContext()
        assertEquals("com.android.settings:id/switch_widget",
            ctx.getCacheAttr(mockNode(viewId = "com.android.settings:id/switch_widget"), "id"))
    }

    @Test
    fun `getCacheAttr returns vid (short id)`() {
        val ctx = A11yContext()
        val node = mockNode(
            viewId = "com.android.settings:id/switch_widget",
            packageName = "com.android.settings"
        )
        assertEquals("switch_widget", (ctx.getCacheAttr(node, "vid") as CharSequence).toString())
    }

    @Test
    fun `getCacheAttr returns desc`() {
        val ctx = A11yContext()
        assertEquals("返回", (ctx.getCacheAttr(mockNode(desc = "返回"), "desc") as CharSequence).toString())
    }

    @Test
    fun `getCacheAttr returns null for unknown`() {
        val ctx = A11yContext()
        assertNull(ctx.getCacheAttr(mockNode(), "nonexistent"))
    }

    @Test
    fun `getCacheAttr returns childCount`() {
        val ctx = A11yContext()
        assertEquals(5, ctx.getCacheAttr(mockNode(childCount = 5), "childCount"))
    }

    @Test
    fun `transform getName returns className`() {
        val ctx = A11yContext()
        val node = mockNode(className = "android.widget.Button")
        assertEquals("android.widget.Button", ctx.transform.getName(node).toString())
    }

    @Test
    fun `transform getChildren returns child sequence`() {
        val ctx = A11yContext()
        val parent = mockNode(childCount = 2)
        val child0 = mockNode(text = "c0")
        val child1 = mockNode(text = "c1")
        `when`(parent.getChild(0)).thenReturn(child0)
        `when`(parent.getChild(1)).thenReturn(child1)

        val children = ctx.transform.getChildren(parent).toList()
        assertEquals(2, children.size)
        assertEquals("c0", children[0].text.toString())
    }

    @Test
    fun `selector matches node with text=允许`() {
        val ctx = A11yContext()
        val node = mockNode(text = "允许", className = "android.widget.Button")
        val selector = li.songe.selector.Selector.parse("[text=\"允许\"]")
        val matched = selector.match(node, ctx.transform, li.songe.selector.MatchOption.default)
        assertNotNull(matched)
    }

    @Test
    fun `selector does not match wrong text`() {
        val ctx = A11yContext()
        val node = mockNode(text = "拒绝", className = "android.widget.Button")
        val selector = li.songe.selector.Selector.parse("[text=\"允许\"]")
        val matched = selector.match(node, ctx.transform, li.songe.selector.MatchOption.default)
        assertNull(matched)
    }

    @Test
    fun `selector matches Switch by name + checked`() {
        val ctx = A11yContext()
        val node = mockNode(className = "android.widget.Switch", checked = false)
        val selector = li.songe.selector.Selector.parse("Switch[checked=false]")
        val matched = selector.match(node, ctx.transform, li.songe.selector.MatchOption.default)
        assertNotNull(matched)
    }

    @Test
    fun `selector contains text match`() {
        val ctx = A11yContext()
        val node = mockNode(text = "允许自启动管理", className = "android.widget.TextView")
        val selector = li.songe.selector.Selector.parse("[text*=\"自启动\"]")
        val matched = selector.match(node, ctx.transform, li.songe.selector.MatchOption.default)
        assertNotNull(matched)
    }
}
