package com.guard.wallet.gkd;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * GkdNodeFinder 选择器字符串正确性测试。
 * 验证通过 GkdNodeFinder.escape() 和 CombineFilterConverter 生成的选择器
 * 可以被 GKD 库正确解析。
 */
public class GkdNodeFinderTest {

    @Test
    public void testEscape_normalText() {
        assertEquals("abc", GkdNodeFinder.escape("abc"));
    }

    @Test
    public void testEscape_quotes() {
        assertEquals("a\\\"b", GkdNodeFinder.escape("a\"b"));
    }

    @Test
    public void testEscape_backslash() {
        assertEquals("a\\\\b", GkdNodeFinder.escape("a\\b"));
    }

    @Test
    public void testEscape_null() {
        assertEquals("", GkdNodeFinder.escape(null));
    }

    @Test
    public void testEscape_chineseText() {
        assertEquals("无线调试", GkdNodeFinder.escape("无线调试"));
    }

    @Test
    public void testSelectorParsing_simple() {
        li.songe.selector.Selector sel = li.songe.selector.Selector.Companion.parse("[text=\"test\"]");
        assertNotNull(sel);
    }

    @Test
    public void testSelectorParsing_className() {
        li.songe.selector.Selector sel = li.songe.selector.Selector.Companion.parse(
            "[name=\"android.widget.Switch\"][checked=true]");
        assertNotNull(sel);
    }

    @Test
    public void testSelectorParsing_contains() {
        li.songe.selector.Selector sel = li.songe.selector.Selector.Companion.parse(
            "[text*=\"无线调试\"]");
        assertNotNull(sel);
    }

    @Test
    public void testSelectorParsing_or() {
        // GKD OR syntax requires each operand wrapped in parentheses
        li.songe.selector.Selector sel = li.songe.selector.Selector.Companion.parse(
            "([text=\"允许\"]) || ([text=\"确定\"])");
        assertNotNull(sel);
    }

    @Test
    public void testSelectorParsing_descendant() {
        li.songe.selector.Selector sel = li.songe.selector.Selector.Companion.parse(
            "[clickable=true] >n [text=\"无线调试\"]");
        assertNotNull(sel);
    }

    @Test
    public void testSelectorParsing_regex() {
        li.songe.selector.Selector sel = li.songe.selector.Selector.Companion.parse(
            "[text~=\"\\\\d{6}\"]");
        assertNotNull(sel);
    }
}
