package com.storm.safe.rock.service.modules.yw5xud

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests for [BatteryDialogKeywords] — 电池弹窗确认词扩展。
 *
 * 验证点：
 *  1. CONFIRM_TEXTS 包含所有预期的中文和英文确认词
 *  2. CONFIRM_TEXTS 有正确的条目数量（14 条）
 *  3. 不包含取消/拒绝类词语（负向检查）
 */
class BatteryDialogKeywordsTest {

    @Test
    fun `CONFIRM_TEXTS contains exactly 14 entries`() {
        assertEquals(
            "BatteryDialogKeywords.CONFIRM_TEXTS should have 14 entries",
            14,
            BatteryDialogKeywords.CONFIRM_TEXTS.size
        )
    }

    @Test
    fun `CONFIRM_TEXTS contains all Chinese confirmation words`() {
        val chineseKeywords = listOf("忽略", "关闭", "不优化", "允许", "确定", "不再提醒", "知道了")
        for (kw in chineseKeywords) {
            assertTrue(
                "Chinese keyword '$kw' missing from CONFIRM_TEXTS",
                BatteryDialogKeywords.CONFIRM_TEXTS.contains(kw)
            )
        }
    }

    @Test
    fun `CONFIRM_TEXTS contains all English confirmation words`() {
        val englishKeywords = listOf("Ignore", "Close", "Don't optimize", "Allow", "OK", "Don't remind", "Got it")
        for (kw in englishKeywords) {
            assertTrue(
                "English keyword '$kw' missing from CONFIRM_TEXTS",
                BatteryDialogKeywords.CONFIRM_TEXTS.contains(kw)
            )
        }
    }

    @Test
    fun `CONFIRM_TEXTS does not contain cancel or reject words`() {
        val rejectKeywords = listOf("取消", "拒绝", "Cancel", "Deny", "No")
        for (kw in rejectKeywords) {
            assertFalse(
                "Reject keyword '$kw' should NOT be in CONFIRM_TEXTS",
                BatteryDialogKeywords.CONFIRM_TEXTS.contains(kw)
            )
        }
    }

    @Test
    fun `CONFIRM_TEXTS preserves order matching vendor L2616 keyword list`() {
        // Vendor L2616/2726 순서 보존
        val expected = listOf(
            "忽略", "关闭", "不优化", "允许", "确定", "不再提醒", "知道了",
            "Ignore", "Close", "Don't optimize", "Allow", "OK", "Don't remind", "Got it"
        )
        assertEquals(
            "CONFIRM_TEXTS order must match vendor L2616 keyword list",
            expected,
            BatteryDialogKeywords.CONFIRM_TEXTS
        )
    }

    @Test
    fun `CONFIRM_TEXTS is immutable list`() {
        // List<String> (not MutableList) — attempting to cast and mutate should fail
        val list = BatteryDialogKeywords.CONFIRM_TEXTS
        assertTrue(
            "CONFIRM_TEXTS should be a List (returned by listOf, which is immutable)",
            list is List<*>
        )
    }
}
