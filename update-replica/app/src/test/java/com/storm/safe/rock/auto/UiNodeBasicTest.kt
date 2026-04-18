package com.storm.safe.rock.auto

import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.auto.entity.UiNode
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UiNodeBasicTest {
    @Test fun `createRoot wraps nodeInfo`() {
        val info = AccessibilityNodeInfo.obtain()
        info.text = "test"
        val node = UiNode.createRoot(info)
        assertNotNull(node)
        assertEquals("test", node.text)
    }

    @Test fun `getText returns empty string when no text`() {
        val info = AccessibilityNodeInfo.obtain()
        val node = UiNode.createRoot(info)
        assertEquals("", node.text)
    }

    @Test fun `isClickable delegates`() {
        val info = AccessibilityNodeInfo.obtain()
        info.isClickable = true
        assertTrue(UiNode.createRoot(info).isClickable)
    }

    @Test fun `findOneByCombine returns null on empty tree`() {
        val node = UiNode.createRoot(AccessibilityNodeInfo.obtain())
        assertNull(node.findOneByCombine { it.text == "missing" })
    }

    @Test fun `className returns empty string not null`() {
        val node = UiNode.createRoot(AccessibilityNodeInfo.obtain())
        assertEquals("", node.className)
    }
}
