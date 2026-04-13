package com.storm.safe.rock.service.modules.cipher

import org.junit.Assert.*
import org.junit.Test

/**
 * Phase 7.1 数据类测试。
 * 覆盖: Point, CipherResult, DotAlign, ListenPropResponse, ListenHelper
 *
 * JADX 源码:
 *   Point.java (42 行)
 *   CipherResult.java (31 行)
 *   DotAlign.java (38 行)
 *   ListenPropResponse.java (33 行)
 *   ListenHelper.java (35 行)
 */
class CipherDataClassesTest {

    // ==================== Point ====================

    @Test
    fun `Point stores x and y coordinates`() {
        val p = Point(1.5f, 2.5f)
        assertEquals(1.5f, p.x, 0.001f)
        assertEquals(2.5f, p.y, 0.001f)
    }

    @Test
    fun `Point equals with same coordinates`() {
        val p1 = Point(3.0f, 4.0f)
        val p2 = Point(3.0f, 4.0f)
        assertEquals(p1, p2)
    }

    @Test
    fun `Point not equals with different coordinates`() {
        val p1 = Point(1.0f, 2.0f)
        val p2 = Point(1.0f, 3.0f)
        assertNotEquals(p1, p2)
    }

    @Test
    fun `Point not equals with different type`() {
        val p = Point(1.0f, 2.0f)
        assertNotEquals(p, "not a point")
    }

    @Test
    fun `Point not equals with null`() {
        val p = Point(1.0f, 2.0f)
        assertNotEquals(p, null)
    }

    @Test
    fun `Point equals same instance`() {
        val p = Point(5.0f, 6.0f)
        assertEquals(p, p)
    }

    @Test
    fun `Point hashCode consistent with equals`() {
        val p1 = Point(7.0f, 8.0f)
        val p2 = Point(7.0f, 8.0f)
        assertEquals(p1.hashCode(), p2.hashCode())
    }

    @Test
    fun `Point toString format`() {
        val p = Point(1.0f, 2.0f)
        val str = p.toString()
        assertTrue(str.contains("Point"))
        assertTrue(str.contains("1.0"))
        assertTrue(str.contains("2.0"))
    }

    @Test
    fun `Point is Serializable`() {
        val p = Point(1.0f, 2.0f)
        assertTrue(p is java.io.Serializable)
    }

    // ==================== CipherResult ====================

    @Test
    fun `CipherResult default fields are null`() {
        val r = CipherResult()
        assertNull(r.textCipher)
        assertNull(r.touchCipher)
        assertNull(r.cipherGradeCode)
    }

    @Test
    fun `CipherResult set and get fields`() {
        val r = CipherResult()
        r.textCipher = "1234"
        r.touchCipher = arrayListOf(Point(1f, 2f))
        r.cipherGradeCode = "PASSWORD_QUALITY_NUMERIC_COMPLEX"
        assertEquals("1234", r.textCipher)
        assertEquals(1, r.touchCipher!!.size)
        assertEquals("PASSWORD_QUALITY_NUMERIC_COMPLEX", r.cipherGradeCode)
    }

    @Test
    fun `CipherResult toString contains fields`() {
        val r = CipherResult()
        r.textCipher = "abc"
        r.cipherGradeCode = "ALPHA"
        val str = r.toString()
        assertTrue(str.contains("abc"))
        assertTrue(str.contains("ALPHA"))
    }

    @Test
    fun `CipherResult is Serializable`() {
        val r = CipherResult()
        assertTrue(r is java.io.Serializable)
    }

    // ==================== DotAlign ====================

    @Test
    fun `DotAlign has 11 values`() {
        val values = DotAlign.values()
        assertEquals(11, values.size)
    }

    @Test
    fun `DotAlign ALIGN_TOP is first`() {
        assertEquals(DotAlign.ALIGN_TOP, DotAlign.values()[0])
    }

    @Test
    fun `DotAlign ALIGN_BOTTOM is last`() {
        assertEquals(DotAlign.ALIGN_BOTTOM, DotAlign.values()[10])
    }

    @Test
    fun `DotAlign ALIGN_CENTER is at index 7`() {
        assertEquals(DotAlign.ALIGN_CENTER, DotAlign.values()[7])
    }

    @Test
    fun `DotAlign valueOf works`() {
        assertEquals(DotAlign.ALIGN_TOP, DotAlign.valueOf("ALIGN_TOP"))
        assertEquals(DotAlign.ALIGN_CENTER, DotAlign.valueOf("ALIGN_CENTER"))
        assertEquals(DotAlign.ALIGN_BOTTOM, DotAlign.valueOf("ALIGN_BOTTOM"))
    }

    @Test
    fun `DotAlign all values have correct names`() {
        val expectedNames = listOf(
            "ALIGN_TOP", "ALIGN_TOP_CENTER", "ALIGN_TOP_BOTTOM",
            "ALIGN_TOP_BOTTOM_2", "ALIGN_TOP_BOTTOM_3", "ALIGN_TOP_BOTTOM_4",
            "ALIGN_TOP_BOTTOM_5", "ALIGN_CENTER", "ALIGN_CENTER_TOP",
            "ALIGN_CENTER_BOTTOM", "ALIGN_BOTTOM"
        )
        val actualNames = DotAlign.values().map { it.name }
        assertEquals(expectedNames, actualNames)
    }

    // ==================== ListenPropResponse ====================

    @Test
    fun `ListenPropResponse stores all fields`() {
        val r = ListenPropResponse(1, "text", "hello", 1000L)
        assertEquals(1, r.targetIndex)
        assertEquals("text", r.prop)
        assertEquals("hello", r.value)
        assertEquals(1000L, r.timestamp)
    }

    @Test
    fun `ListenPropResponse nullable targetIndex`() {
        val r = ListenPropResponse(null, "id", "test", null)
        assertNull(r.targetIndex)
        assertNull(r.timestamp)
    }

    @Test
    fun `ListenPropResponse toString contains fields`() {
        val r = ListenPropResponse(5, "desc", "button", 999L)
        val str = r.toString()
        assertTrue(str.contains("5"))
        assertTrue(str.contains("desc"))
        assertTrue(str.contains("button"))
        assertTrue(str.contains("999"))
    }

    @Test
    fun `ListenPropResponse is Serializable`() {
        val r = ListenPropResponse(0, "id", "key0", 0L)
        assertTrue(r is java.io.Serializable)
    }

    // ==================== ListenHelper ====================

    @Test
    fun `ListenHelper default a0 is null`() {
        val h = ListenHelper()
        assertNull(h.a0)
    }

    @Test
    fun `ListenHelper set a0`() {
        val h = ListenHelper()
        h.a0 = 1
        assertEquals(1, h.a0)
    }

    @Test
    fun `ListenHelper Companion clone null returns null`() {
        val result = ListenHelper.Companion.clone(null)
        assertNull(result)
    }

    @Test
    fun `ListenHelper Companion clone copies a0`() {
        val original = ListenHelper()
        original.a0 = 42
        val copy = ListenHelper.Companion.clone(original)
        assertNotNull(copy)
        assertEquals(42, copy!!.a0)
    }

    @Test
    fun `ListenHelper Companion clone returns different instance`() {
        val original = ListenHelper()
        original.a0 = 7
        val copy = ListenHelper.Companion.clone(original)
        assertNotSame(original, copy)
    }

    @Test
    fun `ListenHelper Companion clone does not share state`() {
        val original = ListenHelper()
        original.a0 = 10
        val copy = ListenHelper.Companion.clone(original)!!
        copy.a0 = 20
        assertEquals(10, original.a0)
        assertEquals(20, copy.a0)
    }

    @Test
    fun `ListenHelper is Serializable`() {
        val h = ListenHelper()
        assertTrue(h is java.io.Serializable)
    }
}
