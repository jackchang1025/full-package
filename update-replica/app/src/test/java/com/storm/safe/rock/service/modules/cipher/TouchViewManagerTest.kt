package com.storm.safe.rock.service.modules.cipher

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Phase 7.5 TouchViewManager + OverlayTouchListener 测试。
 *
 * JADX 源码:
 *   C0339a5.java (745 行) — TouchViewManager
 *   ViewOnTouchListenerC0338a4.java (300 行) — OverlayTouchListener
 *
 * 使用 Robolectric 以支持 android.graphics.Rect。
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class TouchViewManagerTest {

    // ==================== DigitButtonInfo ====================

    @Test
    fun `DigitButtonInfo stores digit and bounds`() {
        val rect = android.graphics.Rect(100, 200, 200, 300)
        val info = DigitButtonInfo(
            digit = 5,
            bounds = rect,
            resourceId = "com.android.systemui:id/key5",
            text = "5",
            contentDescription = "five",
            node = null
        )
        assertEquals(5, info.digit)
        assertEquals(100, info.bounds.left)
        assertEquals("5", info.text)
    }

    @Test
    fun `DigitButtonInfo with delete key has negative digit`() {
        val rect = android.graphics.Rect(0, 0, 100, 100)
        val info = DigitButtonInfo(
            digit = -1, // delete
            bounds = rect,
            resourceId = "delete",
            text = "",
            contentDescription = "删除",
            node = null
        )
        assertEquals(-1, info.digit)
    }

    // ==================== PatternBounds ====================

    @Test
    fun `PatternBounds stores screen and parent rects`() {
        val screenRect = android.graphics.Rect(0, 100, 1080, 900)
        val parentRect = android.graphics.Rect(0, 0, 1080, 800)
        val bounds = PatternBounds(screenRect, parentRect)
        assertEquals(0, bounds.boundsInScreen.left)
        assertEquals(1080, bounds.boundsInScreen.right)
    }

    // ==================== 辅助逻辑 ====================

    @Test
    fun `isDigitButton detects single digit text`() {
        assertTrue(isDigitChar("5"))
        assertTrue(isDigitChar("0"))
        assertFalse(isDigitChar(""))
        assertFalse(isDigitChar("ab"))
        assertFalse(isDigitChar("12"))
    }

    @Test
    fun `isDeleteButton detects delete by id`() {
        assertTrue(isDeleteId("com.android.systemui:id/delete_button"))
        assertTrue(isDeleteId("com.android.systemui:id/key_delete"))
        assertFalse(isDeleteId("com.android.systemui:id/key5"))
    }

    @Test
    fun `isDeleteButton detects delete by text`() {
        assertTrue(isDeleteText("删除"))
        assertTrue(isDeleteText("delete"))
        assertTrue(isDeleteText("DELETE"))
        assertFalse(isDeleteText("5"))
    }

    @Test
    fun `extractDigitFromId extracts last digit`() {
        assertEquals('0', extractLastDigitFromId("com.android.systemui:id/key0"))
        assertEquals('9', extractLastDigitFromId("com.android.systemui:id/key9"))
        assertNull(extractLastDigitFromId("com.android.systemui:id/delete"))
        assertNull(extractLastDigitFromId(""))
    }

    @Test
    fun `hitTest with rect contains point`() {
        val rect = android.graphics.Rect(100, 100, 200, 200)
        assertTrue(rect.contains(150, 150))
        assertFalse(rect.contains(50, 50))
    }

    @Test
    fun `hitTest with expanded bounds`() {
        val originalBounds = android.graphics.Rect(100, 100, 200, 200) // 100x100
        val parentBounds = android.graphics.Rect(0, 0, 300, 300) // 300x300
        // 扩展: (300-100)/2 = 100 each side
        val widthDiff = if (parentBounds.width() > originalBounds.width()) {
            (parentBounds.width() - originalBounds.width()) / 2
        } else 0
        val heightDiff = if (parentBounds.height() > originalBounds.height()) {
            (parentBounds.height() - originalBounds.height()) / 2
        } else 0
        val expanded = android.graphics.Rect(
            originalBounds.left - widthDiff,
            originalBounds.top - heightDiff,
            originalBounds.right + widthDiff,
            originalBounds.bottom + heightDiff
        )
        assertEquals(0, expanded.left)
        assertEquals(0, expanded.top)
        assertEquals(300, expanded.right)
        assertEquals(300, expanded.bottom)
    }

    @Test
    fun `EXCLUDE_PACKAGES contains system packages`() {
        assertTrue(TouchViewManager.EXCLUDE_PACKAGES.contains("android"))
        assertTrue(TouchViewManager.EXCLUDE_PACKAGES.contains("com.android.systemui"))
        assertTrue(TouchViewManager.EXCLUDE_PACKAGES.contains("com.android.launcher3"))
    }

    @Test
    fun `EXCLUDE_PACKAGES contains known launchers`() {
        assertTrue(TouchViewManager.EXCLUDE_PACKAGES.contains("com.miui.home"))
        assertTrue(TouchViewManager.EXCLUDE_PACKAGES.contains("com.huawei.android.launcher"))
        assertTrue(TouchViewManager.EXCLUDE_PACKAGES.contains("com.sec.android.app.launcher"))
    }

    @Test
    fun `MAX_RETRY_COUNT is 5`() {
        assertEquals(5, TouchViewManager.MAX_RETRY_COUNT)
    }

    // ==================== 辅助方法 ====================

    private fun isDigitChar(s: String): Boolean {
        return s.length == 1 && Character.isDigit(s[0])
    }

    private fun isDeleteId(id: String): Boolean {
        return id.contains("delete", ignoreCase = true)
    }

    private fun isDeleteText(text: String): Boolean {
        return text.equals("删除", ignoreCase = true) || text.equals("delete", ignoreCase = true)
    }

    private fun extractLastDigitFromId(id: String): Char? {
        if (id.isEmpty()) return null
        val lastChar = id.last()
        return if (Character.isDigit(lastChar)) lastChar else null
    }
}
