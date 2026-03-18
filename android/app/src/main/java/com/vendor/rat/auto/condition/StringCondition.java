package com.vendor.rat.auto.condition;

import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.filter.NodeFilter;

/**
 * 文本条件过滤器 (模块 04)
 *
 * 支持对节点的不同属性(text/className/viewId/contentDescription)做精确/包含/前缀/后缀匹配
 *
 * 基于逆向分析: com/guard/wallet/condition/StringCondition.java
 * 原始类使用 property 字段区分匹配的属性
 */
public class StringCondition implements NodeFilter {

    /**
     * 匹配类型
     */
    public enum MatchType {
        EQUALS, CONTAINS, STARTS_WITH, ENDS_WITH
    }

    /**
     * 要匹配的节点属性
     */
    public enum Property {
        TEXT, CLASS_NAME, VIEW_ID, CONTENT_DESCRIPTION
    }

    private final String target;
    private final MatchType matchType;
    private final Property property;

    // ============ 构造器 ============

    public StringCondition(String target) {
        this(Property.TEXT, target, MatchType.CONTAINS);
    }

    public StringCondition(String target, MatchType matchType) {
        this(Property.TEXT, target, matchType);
    }

    public StringCondition(Property property, String target, MatchType matchType) {
        this.property = property;
        this.target = target;
        this.matchType = matchType;
    }

    // ============ 静态工厂方法 ============

    /**
     * 文本包含匹配 (默认)
     */
    public static StringCondition textContains(String text) {
        return new StringCondition(Property.TEXT, text, MatchType.CONTAINS);
    }

    /**
     * 文本精确匹配
     */
    public static StringCondition textEquals(String text) {
        return new StringCondition(Property.TEXT, text, MatchType.EQUALS);
    }

    /**
     * 类名精确匹配
     */
    public static StringCondition className(String className) {
        return new StringCondition(Property.CLASS_NAME, className, MatchType.EQUALS);
    }

    /**
     * 类名包含匹配
     */
    public static StringCondition classNameContains(String className) {
        return new StringCondition(Property.CLASS_NAME, className, MatchType.CONTAINS);
    }

    /**
     * View ID 精确匹配
     */
    public static StringCondition viewId(String viewId) {
        return new StringCondition(Property.VIEW_ID, viewId, MatchType.EQUALS);
    }

    /**
     * View ID 包含匹配
     */
    public static StringCondition viewIdContains(String viewId) {
        return new StringCondition(Property.VIEW_ID, viewId, MatchType.CONTAINS);
    }

    /**
     * ContentDescription 包含匹配
     */
    public static StringCondition descContains(String desc) {
        return new StringCondition(Property.CONTENT_DESCRIPTION, desc, MatchType.CONTAINS);
    }

    /**
     * ContentDescription 精确匹配
     */
    public static StringCondition descEquals(String desc) {
        return new StringCondition(Property.CONTENT_DESCRIPTION, desc, MatchType.EQUALS);
    }

    // ============ NodeFilter ============

    @Override
    public boolean accept(UiNode node) {
        if (node == null) return false;

        String value = getPropertyValue(node);
        if (value == null || value.isEmpty()) return false;

        switch (matchType) {
            case EQUALS:      return value.equals(target);
            case CONTAINS:    return value.contains(target);
            case STARTS_WITH: return value.startsWith(target);
            case ENDS_WITH:   return value.endsWith(target);
            default:          return false;
        }
    }

    /**
     * 根据 property 提取节点对应的属性值
     */
    private String getPropertyValue(UiNode node) {
        switch (property) {
            case TEXT:                return node.getText();
            case CLASS_NAME:          return node.getClassName();
            case VIEW_ID:             return node.getViewIdResourceName();
            case CONTENT_DESCRIPTION: return node.getContentDescription();
            default:                  return "";
        }
    }

    // ============ Getters ============

    public String getTarget() { return target; }
    public MatchType getMatchType() { return matchType; }
    public Property getProperty() { return property; }
}
