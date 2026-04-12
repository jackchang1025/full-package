package com.storm.safe.rock.util

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class StringUtilTest {

    @Test
    fun `decrypt empty string returns empty`() {
        assertEquals("", StringUtil.decrypt(""))
    }

    @Test
    fun `decrypt which`() {
        assertEquals("which", StringUtil.decrypt("eQZwWHg="))
    }

    @Test
    fun `decrypt su`() {
        assertEquals("su", StringUtil.decrypt("ZAI="))
    }

    @Test
    fun `decrypt substrate package`() {
        assertEquals("com.saurik.substrate", StringUtil.decrypt("fhI9XJBjQ2RoeGRfc0SFYnADdkU="))
    }

    @Test
    fun `encrypt then decrypt roundtrip`() {
        val original = "test string"
        val encrypted = StringUtil.encrypt(original)
        assertNotEquals(original, encrypted)
        assertEquals(original, StringUtil.decrypt(encrypted))
    }

    @Test
    fun `encrypt then decrypt roundtrip with unicode`() {
        val original = "你好世界"
        assertEquals(original, StringUtil.decrypt(StringUtil.encrypt(original)))
    }
}
