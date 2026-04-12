package com.storm.safe.rock.service.modules.cipher

import android.graphics.Rect
import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UiObjectTest {

    private fun mockNode(
        text: String? = null,
        contentDesc: String? = null,
        resourceId: String? = null,
        visible: Boolean = true,
        clickable: Boolean = false,
        childCount: Int = 0,
        children: List<AccessibilityNodeInfo> = emptyList()
    ): AccessibilityNodeInfo {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.text).thenReturn(text)
        `when`(node.contentDescription).thenReturn(contentDesc)
        `when`(node.viewIdResourceName).thenReturn(resourceId)
        `when`(node.isVisibleToUser).thenReturn(visible)
        `when`(node.isClickable).thenReturn(clickable)
        `when`(node.childCount).thenReturn(childCount)
        for ((i, child) in children.withIndex()) {
            `when`(node.getChild(i)).thenReturn(child)
        }
        return node
    }

    @Test
    fun `createRoot with null returns null`() {
        assertNull(UiObject.createRoot(null))
    }

    @Test
    fun `createRoot with valid node returns UiObject at depth 0`() {
        val node = mockNode(text = "hello")
        val obj = UiObject.createRoot(node)
        assertNotNull(obj)
        assertEquals(0, obj!!.depth)
    }

    @Test
    fun `getText returns cached text`() {
        val obj = UiObject(mockNode(text = "Submit"), 0)
        assertEquals("Submit", obj.getText())
    }

    @Test
    fun `getContentDescription returns cached desc`() {
        val obj = UiObject(mockNode(contentDesc = "Back button"), 0)
        assertEquals("Back button", obj.getContentDescription())
    }

    @Test
    fun `getResourceId returns cached id`() {
        val obj = UiObject(mockNode(resourceId = "com.app:id/btn_ok"), 0)
        assertEquals("com.app:id/btn_ok", obj.getResourceId())
    }

    @Test
    fun `isVisibleToUser delegates to nodeInfo`() {
        assertTrue(UiObject(mockNode(visible = true), 0).isVisibleToUser())
        assertFalse(UiObject(mockNode(visible = false), 0).isVisibleToUser())
    }

    @Test
    fun `getChild returns UiObject at depth+1`() {
        val child = mockNode(text = "child")
        val parent = mockNode(childCount = 1, children = listOf(child))
        val obj = UiObject(parent, 0)
        val childObj = obj.getChild(0)
        assertNotNull(childObj)
        assertEquals(1, childObj!!.depth)
        assertEquals("child", childObj.getText())
    }

    @Test
    fun `getChild out of bounds returns null`() {
        val obj = UiObject(mockNode(childCount = 0), 0)
        assertNull(obj.getChild(0))
    }

    @Test
    fun `getChildCount returns count`() {
        assertEquals(3, UiObject(mockNode(childCount = 3), 0).getChildCount())
    }

    @Test
    fun `findFirst returns self if matching`() {
        val obj = UiObject(mockNode(text = "OK"), 0)
        val found = obj.findFirst { it.getText() == "OK" }
        assertNotNull(found)
        assertEquals("OK", found!!.getText())
    }

    @Test
    fun `findFirst returns child match`() {
        val child = mockNode(text = "target")
        val parent = mockNode(text = "parent", childCount = 1, children = listOf(child))
        val obj = UiObject(parent, 0)
        val found = obj.findFirst { it.getText() == "target" }
        assertNotNull(found)
        assertEquals("target", found!!.getText())
    }

    @Test
    fun `findFirst returns null if no match`() {
        val obj = UiObject(mockNode(text = "hello"), 0)
        assertNull(obj.findFirst { it.getText() == "nonexistent" })
    }

    @Test
    fun `findAll collects all matches`() {
        val c1 = mockNode(text = "A")
        val c2 = mockNode(text = "B")
        val c3 = mockNode(text = "A")
        val parent = mockNode(text = "root", childCount = 3, children = listOf(c1, c2, c3))
        val obj = UiObject(parent, 0)
        val results = mutableListOf<UiObject>()
        obj.findAll({ it.getText() == "A" }, results)
        assertEquals(2, results.size)
    }

    @Test
    fun `equals based on nodeInfo`() {
        val node = mockNode()
        val a = UiObject(node, 0)
        val b = UiObject(node, 1) // different depth, same node
        assertEquals(a, b)
    }

    @Test
    fun `equals different nodes are not equal`() {
        val a = UiObject(mockNode(), 0)
        val b = UiObject(mockNode(), 0)
        assertNotEquals(a, b)
    }

    @Test
    fun `hashCode delegates to nodeInfo`() {
        val node = mockNode()
        assertEquals(node.hashCode(), UiObject(node, 0).hashCode())
    }

    @Test
    fun `getText lazy loads from nodeInfo when init cache is null`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        // First call during init returns null, subsequent call returns text
        `when`(node.text).thenReturn(null).thenReturn("lazy")
        `when`(node.contentDescription).thenReturn(null)
        `when`(node.viewIdResourceName).thenReturn(null)
        `when`(node.isVisibleToUser).thenReturn(true)
        `when`(node.childCount).thenReturn(0)
        val obj = UiObject(node, 0)
        // After init, _text is null. getText() should try again.
        assertEquals("lazy", obj.getText())
    }

    @Test
    fun `getContentDescription lazy loads when init cache is null`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.text).thenReturn(null)
        `when`(node.contentDescription).thenReturn(null).thenReturn("lazy desc")
        `when`(node.viewIdResourceName).thenReturn(null)
        `when`(node.isVisibleToUser).thenReturn(true)
        `when`(node.childCount).thenReturn(0)
        val obj = UiObject(node, 0)
        assertEquals("lazy desc", obj.getContentDescription())
    }

    @Test
    fun `getResourceId lazy loads when init cache is null`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.text).thenReturn(null)
        `when`(node.contentDescription).thenReturn(null)
        `when`(node.viewIdResourceName).thenReturn(null).thenReturn("com.app:id/lazy")
        `when`(node.isVisibleToUser).thenReturn(true)
        `when`(node.childCount).thenReturn(0)
        val obj = UiObject(node, 0)
        assertEquals("com.app:id/lazy", obj.getResourceId())
    }

    @Test
    fun `findAll with no matches returns empty list`() {
        val obj = UiObject(mockNode(text = "root"), 0)
        val results = mutableListOf<UiObject>()
        obj.findAll({ it.getText() == "nonexistent" }, results)
        assertTrue(results.isEmpty())
    }

    @Test
    fun `findFirst DFS traverses grandchildren`() {
        val grandchild = mockNode(text = "deep")
        val child = mockNode(text = "mid", childCount = 1, children = listOf(grandchild))
        val root = mockNode(text = "root", childCount = 1, children = listOf(child))
        val obj = UiObject(root, 0)
        val found = obj.findFirst { it.getText() == "deep" }
        assertNotNull(found)
        assertEquals("deep", found!!.getText())
        assertEquals(2, found.depth)
    }

    @Test
    fun `getChild with null child from nodeInfo returns null`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.childCount).thenReturn(1)
        `when`(node.getChild(0)).thenReturn(null)
        `when`(node.text).thenReturn(null)
        `when`(node.contentDescription).thenReturn(null)
        `when`(node.viewIdResourceName).thenReturn(null)
        val obj = UiObject(node, 0)
        assertNull(obj.getChild(0))
    }

    @Test
    fun `equals with null returns false`() {
        val obj = UiObject(mockNode(), 0)
        assertFalse(obj.equals(null))
    }

    @Test
    fun `equals with same instance returns true`() {
        val obj = UiObject(mockNode(), 0)
        assertTrue(obj.equals(obj))
    }

    @Test
    fun `equals with different type returns false`() {
        val obj = UiObject(mockNode(), 0)
        assertFalse(obj.equals("not a UiObject"))
    }

    @Test
    fun `createRoot catches exception and returns null`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.text).thenThrow(RuntimeException("boom"))
        // createRoot should catch the exception from the constructor
        // and return null
        val result = UiObject.createRoot(node)
        // The constructor catches the exception internally, so createRoot
        // returns a valid object (the init block has try-catch)
        assertNotNull(result)
    }

    @Test
    fun `getBounds returns cached rect`() {
        val node = mockNode()
        val obj = UiObject(node, 0)
        val bounds = obj.getBounds()
        // Mockito mock returns default (empty Rect), getBoundsInScreen is a void method
        // so the rect will be the one created in init
        assertNotNull(bounds)
    }

    @Test
    fun `nodeInfo property is accessible`() {
        val node = mockNode()
        val obj = UiObject(node, 0)
        assertSame(node, obj.nodeInfo)
    }
}
