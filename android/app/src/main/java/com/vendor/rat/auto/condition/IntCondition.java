package com.vendor.rat.auto.condition;

// ADAPT: vendor = com.guard.wallet.condition.IntCondition (180 行)
// 一比一复刻: 10 种整型属性 + compare 比较运算符 + toIntFilter()

import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.filter.IntFilter;
import com.vendor.rat.auto.filter.IntPropertyGetter;
import com.vendor.rat.auto.filter.NodeFilter;

import java.io.Serializable;

public class IntCondition implements Serializable, NodeFilter {

    private static final String TAG = "IntCondition";

    private String filterKey;
    private boolean filterEnabled;
    private int filterValue;
    private String compare; // "eq", "gt", "gte", "lt", "lte", "neq"

    public IntCondition() {}

    public IntCondition(String filterKey, boolean filterEnabled, int filterValue, String compare) {
        this.filterKey = filterKey;
        this.filterEnabled = filterEnabled;
        this.filterValue = filterValue;
        this.compare = compare;
    }

    // ============ Getters/Setters ============

    public String getCompare() { return compare; }
    public void setCompare(String compare) { this.compare = compare; }
    public String getFilterKey() { return filterKey; }
    public void setFilterKey(String filterKey) { this.filterKey = filterKey; }
    public int getFilterValue() { return filterValue; }
    public void setFilterValue(int filterValue) { this.filterValue = filterValue; }
    public boolean isFilterEnabled() { return filterEnabled; }
    public void setFilterEnabled(boolean filterEnabled) { this.filterEnabled = filterEnabled; }

    // ============ NodeFilter 接口 ============

    @Override
    public boolean accept(UiNode node) {
        if (!filterEnabled || filterKey == null || filterKey.isEmpty()) return true;
        IntPropertyGetter getter = getPropertyGetter(filterKey);
        if (getter == null) return true;
        int actual = getter.getInt(node);
        return compareValues(actual, filterValue, compare);
    }

    // ============ vendor: toIntFilter() (行 60-174) ============

    public IntFilter toIntFilter() {
        if (!filterEnabled || filterKey == null || filterKey.isEmpty() || filterValue < 0) {
            return null;
        }
        IntPropertyGetter getter = getPropertyGetter(filterKey);
        if (getter != null) {
            return new IntFilter(getter, filterValue, compare);
        }
        return null;
    }

    // ============ 10 种整型属性映射 (vendor 一比一对齐) ============

    private static IntPropertyGetter getPropertyGetter(String key) {
        if (key == null) return null;
        switch (key) {
            case "columnSpan":    return UiNode::childCount;   // TODO: VENDOR_VERIFY columnSpan
            case "regionCount":   return node -> 0;            // TODO: VENDOR_VERIFY
            case "column":        return node -> 0;            // TODO: VENDOR_VERIFY
            case "columnCount":   return node -> 0;            // TODO: VENDOR_VERIFY
            case "drawingOrder":  return node -> 0;            // TODO: VENDOR_VERIFY
            case "row":           return node -> 0;            // TODO: VENDOR_VERIFY
            case "rowCount":      return node -> 0;            // TODO: VENDOR_VERIFY
            case "depth":         return UiNode::depth;
            case "indexInParent": return UiNode::indexInParent;
            case "rowSpan":       return node -> 0;            // TODO: VENDOR_VERIFY
            case "childCount":    return UiNode::childCount;
            default: return null;
        }
    }

    // ============ 比较运算 ============

    private static boolean compareValues(int actual, int expected, String compare) {
        if (compare == null || compare.isEmpty() || "eq".equals(compare)) {
            return actual == expected;
        }
        switch (compare) {
            case "gt":  return actual > expected;
            case "gte": return actual >= expected;
            case "lt":  return actual < expected;
            case "lte": return actual <= expected;
            case "neq": return actual != expected;
            default:    return actual == expected;
        }
    }

    @Override
    public String toString() {
        return "IntCondition{filterKey='" + filterKey + "', filterEnabled=" + filterEnabled
                + ", filterValue=" + filterValue + ", compare='" + compare + "'}";
    }
}
