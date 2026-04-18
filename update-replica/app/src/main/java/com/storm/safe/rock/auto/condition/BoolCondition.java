package com.storm.safe.rock.auto.condition;

// ADAPT: vendor = com.guard.wallet.condition.BoolCondition (307 行)
// 一比一复刻: 24 种布尔属性 + filterKey/filterEnabled/filterValue + toBooleanFilter()

import com.storm.safe.rock.auto.entity.UiNode;
import com.storm.safe.rock.auto.filter.BooleanFilter;
import com.storm.safe.rock.auto.filter.BooleanPropertyGetter;
import com.storm.safe.rock.auto.filter.NodeFilter;

import java.io.Serializable;

public class BoolCondition implements Serializable, NodeFilter {

    private static final String TAG = "BoolCondition";

    // vendor 原始字段
    private String filterKey;
    private boolean filterEnabled;
    private boolean filterValue;

    public BoolCondition() {}

    public BoolCondition(String filterKey, boolean filterEnabled, boolean filterValue) {
        this.filterKey = filterKey;
        this.filterEnabled = filterEnabled;
        this.filterValue = filterValue;
    }

    // ============ 向后兼容: Property 枚举 (旧 API, 引擎类使用) ============

    public enum Property {
        CLICKABLE("clickable"), ENABLED("enabled"), CHECKED("checked"),
        SCROLLABLE("scrollable"), CHECKABLE("checkable"), FOCUSABLE("focusable"),
        SELECTED("selected"), PASSWORD("password"), LONG_CLICKABLE("longClickable"),
        EDITABLE("editable"), VISIBLE_TO_USER("visibleToUser"), DISMISSABLE("dismissable"),
        MULTI_LINE("multiLine"), CONTENT_INVALID("contentInvalid"),
        CONTEXT_CLICKABLE("contextClickable"), FOCUSED("focused");

        private final String key;
        Property(String key) { this.key = key; }
        public String getKey() { return key; }
    }

    // 向后兼容构造函数 (引擎类使用)
    public BoolCondition(Property property, boolean expectedValue) {
        this.filterKey = property.getKey();
        this.filterEnabled = true;
        this.filterValue = expectedValue;
    }

    // ============ Getters/Setters (vendor 对齐) ============

    public String getFilterKey() { return filterKey; }
    public void setFilterKey(String filterKey) { this.filterKey = filterKey; }
    public boolean isFilterEnabled() { return filterEnabled; }
    public void setFilterEnabled(boolean filterEnabled) { this.filterEnabled = filterEnabled; }
    public boolean isFilterValue() { return filterValue; }
    public void setFilterValue(boolean filterValue) { this.filterValue = filterValue; }

    // ============ NodeFilter 接口 (供直接作为过滤器使用) ============

    @Override
    public boolean accept(UiNode node) {
        if (!filterEnabled || filterKey == null || filterKey.isEmpty()) return true;
        BooleanPropertyGetter getter = getPropertyGetter(filterKey);
        if (getter == null) return true;
        return getter.get(node) == filterValue;
    }

    // ============ vendor: toBooleanFilter() (行 52-301) ============

    public BooleanFilter toBooleanFilter() {
        if (!filterEnabled || filterKey == null || filterKey.isEmpty()) {
            return null;
        }
        BooleanPropertyGetter getter = getPropertyGetter(filterKey);
        if (getter != null) {
            return new BooleanFilter(getter, filterValue);
        }
        return null;
    }

    // ============ 24 种布尔属性映射 (vendor 一比一对齐) ============

    private static BooleanPropertyGetter getPropertyGetter(String key) {
        if (key == null) return null;
        switch (key) {
            case "canOpenPopup":              return node -> node.isClickable(); // ADAPT: 简化
            case "checkable":                 return UiNode::checkable;
            case "checked":                   return UiNode::checked;
            case "clickable":                 return UiNode::clickable;
            case "contentInvalid":            return UiNode::contentInvalid;
            case "contextClickable":          return UiNode::contextClickable;
            case "dismissable":               return UiNode::dismissable;
            case "editable":                  return UiNode::editable;
            case "enabled":                   return UiNode::enabled;
            case "focusable":                 return UiNode::focusable;
            case "focused":                   return UiNode::focused;
            case "heading":                   return node -> false; // TODO: VENDOR_VERIFY API 28+
            case "importantForAccessibility": return node -> true;  // TODO: VENDOR_VERIFY
            case "longClickable":             return UiNode::longClickable;
            case "multiLine":                 return UiNode::multiLine;
            case "password":                  return UiNode::password;
            case "screenReaderFocusable":     return node -> false; // TODO: VENDOR_VERIFY API 28+
            case "scrollable":                return UiNode::scrollable;
            case "selected":                  return UiNode::selected;
            case "showingHintText":           return node -> false; // TODO: VENDOR_VERIFY API 26+
            case "textEntryKey":              return node -> false; // TODO: VENDOR_VERIFY API 30+
            case "textSelectable":            return node -> false; // TODO: VENDOR_VERIFY
            case "visibleToUser":             return UiNode::visibleToUser;
            case "accessibilityFocused":      return node -> false; // TODO: VENDOR_VERIFY
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return "BoolCondition{filterKey='" + filterKey + "', filterEnabled=" + filterEnabled + ", filterValue=" + filterValue + '}';
    }
}
