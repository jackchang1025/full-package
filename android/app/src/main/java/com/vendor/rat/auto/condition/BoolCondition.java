package com.vendor.rat.auto.condition;

import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.filter.NodeFilter;

/**
 * 布尔条件过滤器 (模块 04)
 *
 * 检查节点的布尔属性（clickable, enabled, checked 等）
 */
public class BoolCondition implements NodeFilter {

    public enum Property {
        CLICKABLE, ENABLED, CHECKED, SCROLLABLE, CHECKABLE
    }

    private final Property property;
    private final boolean expectedValue;

    public BoolCondition(Property property, boolean expectedValue) {
        this.property = property;
        this.expectedValue = expectedValue;
    }

    @Override
    public boolean accept(UiNode node) {
        boolean actual;
        switch (property) {
            case CLICKABLE: actual = node.isClickable(); break;
            case ENABLED: actual = node.isEnabled(); break;
            case CHECKED: actual = node.isChecked(); break;
            case SCROLLABLE: actual = node.isScrollable(); break;
            case CHECKABLE: actual = node.isCheckable(); break;
            default: return false;
        }
        return actual == expectedValue;
    }
}
