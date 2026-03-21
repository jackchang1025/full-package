package com.vendor.rat.auto.engine;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.filter.NodeFilter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * AutoEngine 通用 CombineFilter 构建器测试
 * 对应 vendor o/c.java a0(), K(), L(), U(), V(), H()
 */
@RunWith(MockitoJUnitRunner.class)
public class AutoEngineCombineFilterBuilderTest {

    private UiNode mockNode(String className, boolean clickable, boolean scrollable) {
        UiNode node = mock(UiNode.class);
        when(node.className()).thenReturn(className);
        when(node.isClickable()).thenReturn(clickable);
        when(node.clickable()).thenReturn(clickable);
        when(node.isScrollable()).thenReturn(scrollable);
        when(node.scrollable()).thenReturn(scrollable);
        return node;
    }

    // ============ buildSwitchFilter (vendor c.java a0() :451-455) ============

    @Test
    public void testBuildSwitchFilter_notNull() {
        CombineFilter filter = AutoEngine.buildSwitchFilter();
        assertNotNull(filter);
    }

    // ============ buildClickableLinearLayoutFilter (vendor c.java K() :74-80) ============

    @Test
    public void testBuildClickableLinearLayoutFilter_notNull() {
        CombineFilter filter = AutoEngine.buildClickableLinearLayoutFilter();
        assertNotNull(filter);
    }

    // ============ buildClickableFilter (vendor c.java L() :82-87) ============

    @Test
    public void testBuildClickableFilter_notNull() {
        CombineFilter filter = AutoEngine.buildClickableFilter();
        assertNotNull(filter);
    }

    // ============ buildLinearLayoutFilter (vendor c.java U() :384-388) ============

    @Test
    public void testBuildLinearLayoutFilter_notNull() {
        // 注意: vendor U() 是 className=LinearLayout, 不是 scrollable!
        CombineFilter filter = AutoEngine.buildLinearLayoutFilter();
        assertNotNull(filter);
    }

    // ============ buildScrollableOrFilters (vendor c.java V() :390-429) ============

    @Test
    public void testBuildScrollableOrFilters_fourFilters() {
        NodeFilter[] filters = AutoEngine.buildScrollableOrFilters();
        assertNotNull(filters);
        assertEquals(4, filters.length);
    }

    // ============ buildTextViewContainsFilter (vendor c.java H() :45-53) ============

    @Test
    public void testBuildTextViewContainsFilter_notNull() {
        CombineFilter filter = AutoEngine.buildTextViewContainsFilter("电池");
        assertNotNull(filter);
    }

    @Test
    public void testBuildTextViewContainsFilter_nullText_returnsNull() {
        CombineFilter filter = AutoEngine.buildTextViewContainsFilter(null);
        assertNull(filter);
    }

    @Test
    public void testBuildTextViewContainsFilter_emptyText_returnsNull() {
        CombineFilter filter = AutoEngine.buildTextViewContainsFilter("");
        assertNull(filter);
    }
}
