package com.vendor.rat.auto;

import com.vendor.rat.auto.condition.BoolCondition;
import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * NodeFilter 条件过滤器单元测试
 */
public class NodeFilterTest {

    @Test
    public void testStringCondition_contains() {
        StringCondition condition = new StringCondition("允许");
        assertNotNull(condition);
        assertEquals(StringCondition.MatchType.CONTAINS, condition.getMatchType());
        assertEquals(StringCondition.Property.TEXT, condition.getProperty());
    }

    @Test
    public void testStringCondition_equals() {
        StringCondition condition = new StringCondition(
            "自启动管理", StringCondition.MatchType.EQUALS);
        assertNotNull(condition);
        assertEquals(StringCondition.MatchType.EQUALS, condition.getMatchType());
        assertEquals("自启动管理", condition.getTarget());
    }

    @Test
    public void testStringCondition_factoryMethods() {
        // textContains
        StringCondition sc1 = StringCondition.textContains("允许");
        assertEquals(StringCondition.Property.TEXT, sc1.getProperty());
        assertEquals(StringCondition.MatchType.CONTAINS, sc1.getMatchType());

        // className
        StringCondition sc2 = StringCondition.className("android.widget.Button");
        assertEquals(StringCondition.Property.CLASS_NAME, sc2.getProperty());
        assertEquals(StringCondition.MatchType.EQUALS, sc2.getMatchType());

        // viewId
        StringCondition sc3 = StringCondition.viewId("android:id/title");
        assertEquals(StringCondition.Property.VIEW_ID, sc3.getProperty());

        // descContains
        StringCondition sc4 = StringCondition.descContains("back");
        assertEquals(StringCondition.Property.CONTENT_DESCRIPTION, sc4.getProperty());
    }

    @Test
    public void testCombineFilter_and() {
        CombineFilter filter = CombineFilter.and(
            new StringCondition("允许"),
            new BoolCondition(BoolCondition.Property.CLICKABLE, true)
        );
        assertNotNull(filter);
        assertEquals(CombineFilter.Logic.AND, filter.getLogic());
        assertEquals(2, filter.getFilters().size());
    }

    @Test
    public void testCombineFilter_or() {
        CombineFilter filter = CombineFilter.or(
            new StringCondition("允许"),
            new StringCondition("开启")
        );
        assertNotNull(filter);
        assertEquals(CombineFilter.Logic.OR, filter.getLogic());
        assertEquals(2, filter.getFilters().size());
    }

    @Test
    public void testCombineFilter_convenienceMethods() {
        // textView
        CombineFilter tv = CombineFilter.textView("省电策略");
        assertNotNull(tv);
        assertEquals(2, tv.getFilters().size()); // className + text

        // button
        CombineFilter btn = CombineFilter.button("允许");
        assertNotNull(btn);
        assertEquals(2, btn.getFilters().size());

        // switchWidget
        CombineFilter sw = CombineFilter.switchWidget();
        assertNotNull(sw);

        // clickable
        CombineFilter click = CombineFilter.clickable();
        assertNotNull(click);

        // scrollable
        CombineFilter scroll = CombineFilter.scrollable();
        assertNotNull(scroll);
    }

    @Test
    public void testCombineFilter_defaultConstructor() {
        CombineFilter filter = new CombineFilter();
        assertEquals(CombineFilter.Logic.AND, filter.getLogic());
        assertTrue(filter.getFilters().isEmpty());
    }

    @Test
    public void testBoolCondition_properties() {
        BoolCondition clickable = new BoolCondition(BoolCondition.Property.CLICKABLE, true);
        assertNotNull(clickable);

        BoolCondition scrollable = new BoolCondition(BoolCondition.Property.SCROLLABLE, true);
        assertNotNull(scrollable);

        BoolCondition checkable = new BoolCondition(BoolCondition.Property.CHECKABLE, false);
        assertNotNull(checkable);
    }
}
