package com.vendor.rat.auto.condition;

import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.filter.NodeFilter;

/**
 * 文本条件过滤器 (模块 04)
 *
 * 支持精确匹配和包含匹配
 */
public class StringCondition implements NodeFilter {

    public enum MatchType {
        EQUALS, CONTAINS, STARTS_WITH, ENDS_WITH
    }

    private final String target;
    private final MatchType matchType;

    public StringCondition(String target) {
        this(target, MatchType.CONTAINS);
    }

    public StringCondition(String target, MatchType matchType) {
        this.target = target;
        this.matchType = matchType;
    }

    @Override
    public boolean accept(UiNode node) {
        String text = node.getText();
        if (text == null || text.isEmpty()) return false;

        switch (matchType) {
            case EQUALS: return text.equals(target);
            case CONTAINS: return text.contains(target);
            case STARTS_WITH: return text.startsWith(target);
            case ENDS_WITH: return text.endsWith(target);
            default: return false;
        }
    }
}
