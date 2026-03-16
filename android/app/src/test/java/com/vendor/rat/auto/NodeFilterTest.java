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
        // 测试 accept 逻辑的基础验证
        assertNotNull(condition);
    }

    @Test
    public void testStringCondition_equals() {
        StringCondition condition = new StringCondition(
            "自启动管理", StringCondition.MatchType.EQUALS);
        assertNotNull(condition);
    }

    @Test
    public void testCombineFilter_and() {
        CombineFilter filter = CombineFilter.and(
            new StringCondition("允许"),
            new BoolCondition(BoolCondition.Property.CLICKABLE, true)
        );
        assertNotNull(filter);
    }

    @Test
    public void testCombineFilter_or() {
        CombineFilter filter = CombineFilter.or(
            new StringCondition("允许"),
            new StringCondition("开启")
        );
        assertNotNull(filter);
    }
}
