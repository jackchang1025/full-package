package li.songe.selector

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SelectorSmokeTest {

    @Test
    fun `parse simple text selector`() {
        val selector = Selector.parse("[text=\"允许\"]")
        assertNotNull(selector)
        assertEquals("[text=\"允许\"]", selector.toString())
    }

    @Test
    fun `parse contains text selector`() {
        val selector = Selector.parse("[text*=\"自启动\"]")
        assertNotNull(selector)
        assertTrue(selector.toString().contains("自启动"))
    }

    @Test
    fun `parse class name selector`() {
        val selector = Selector.parse("Switch[checked=false]")
        assertNotNull(selector)
        assertTrue(selector.toString().contains("Switch"))
    }

    @Test
    fun `parse sibling selector with plus operator`() {
        val selector = Selector.parse("[text=\"允许自启动\"] + Switch")
        assertNotNull(selector)
    }

    @Test
    fun `parse ancestor selector with greater than operator`() {
        val selector = Selector.parse("LinearLayout > [text=\"电池\"]")
        assertNotNull(selector)
    }

    @Test
    fun `parse descendant selector with double less than`() {
        val selector = Selector.parse("[text=\"允许\"] <<n [vid=\"content\"]")
        assertNotNull(selector)
    }

    @Test
    fun `parse compound selector with AND`() {
        val selector = Selector.parse("[text=\"允许\"][clickable=true][visibleToUser=true]")
        assertNotNull(selector)
    }

    @Test
    fun `parseOrNull returns null for invalid syntax`() {
        val selector = Selector.parseOrNull("[[[invalid")
        assertNull(selector)
    }

    @Test
    fun `parse at-mark target selector`() {
        val selector = Selector.parse("@Switch[checked=false] + [text=\"允许自启动\"]")
        assertNotNull(selector)
    }

    @Test
    fun `fastQueryList extracts id and text fast queries`() {
        val selector = Selector.parse("[vid=\"switch_widget\"]")
        assertNotNull(selector)
        assertTrue(selector.fastQueryList.isNotEmpty())
    }
}
