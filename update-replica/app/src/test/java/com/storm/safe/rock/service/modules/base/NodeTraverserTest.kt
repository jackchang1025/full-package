package com.storm.safe.rock.service.modules.base

import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NodeTraverserTest {

    private fun mockNode(
        text: String? = null,
        className: String? = null,
        resourceId: String? = null,
        clickable: Boolean = false,
        visible: Boolean = true,
        children: List<AccessibilityNodeInfo> = emptyList(),
        parent: AccessibilityNodeInfo? = null
    ): AccessibilityNodeInfo {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.text).thenReturn(text)
        `when`(node.className).thenReturn(className)
        `when`(node.viewIdResourceName).thenReturn(resourceId)
        `when`(node.isClickable).thenReturn(clickable)
        `when`(node.isVisibleToUser).thenReturn(visible)
        `when`(node.childCount).thenReturn(children.size)
        children.forEachIndexed { i, child -> `when`(node.getChild(i)).thenReturn(child) }
        if (parent != null) `when`(node.parent).thenReturn(parent)
        return node
    }

    @Test fun `findByText null root returns null`() = assertNull(NodeTraverser.findByText(null, "x"))
    @Test fun `findByText empty text returns null`() = assertNull(NodeTraverser.findByText(mockNode(), ""))

    @Test fun `findByText finds matching text`() {
        val child = mockNode(text = "Submit")
        val root = mockNode(text = "Form", children = listOf(child))
        val found = NodeTraverser.findByText(root, "Submit")
        assertNotNull(found)
        assertEquals("Submit", found!!.getText())
    }

    @Test fun `findByText case insensitive`() {
        val node = mockNode(text = "ALLOW")
        assertNotNull(NodeTraverser.findByText(node, "allow"))
    }

    @Test fun `findByClassName finds matching class`() {
        val btn = mockNode(className = "android.widget.Button")
        val root = mockNode(className = "android.widget.LinearLayout", children = listOf(btn))
        val found = NodeTraverser.findByClassName(root, "Button")
        assertNotNull(found)
    }

    @Test fun `findById finds matching resource id suffix`() {
        val node = mockNode(resourceId = "com.app:id/btn_allow")
        assertNotNull(NodeTraverser.findById(node, "btn_allow"))
    }

    @Test fun `findById ignores non-matching`() {
        val node = mockNode(resourceId = "com.app:id/btn_deny")
        assertNull(NodeTraverser.findById(node, "btn_allow"))
    }

    @Test fun `findAllByText returns all matches`() {
        val c1 = mockNode(text = "OK")
        val c2 = mockNode(text = "Cancel")
        val c3 = mockNode(text = "OK")
        val root = mockNode(children = listOf(c1, c2, c3))
        assertEquals(2, NodeTraverser.findAllByText(root, "OK").size)
    }

    @Test fun `findClickableParent walks up`() {
        val grandparent = mockNode(clickable = true)
        val parent = mockNode(clickable = false, parent = grandparent)
        val child = mockNode(clickable = false, parent = parent)
        val found = NodeTraverser.findClickableParent(child)
        assertNotNull(found)
    }

    @Test fun `findClickableParent returns null if no clickable ancestor`() {
        val node = mockNode(clickable = false)
        assertNull(NodeTraverser.findClickableParent(node))
    }

    @Test fun `bfsAll returns all nodes`() {
        val c1 = mockNode(text = "a")
        val c2 = mockNode(text = "b")
        val root = mockNode(text = "root", children = listOf(c1, c2))
        val all = NodeTraverser.bfsAll(root)
        assertEquals(3, all.size)
        assertEquals(0, all[0].depth) // root
        assertEquals(1, all[1].depth) // children
    }

    @Test fun `bfsAll null root returns empty`() = assertTrue(NodeTraverser.bfsAll(null).isEmpty())
}
