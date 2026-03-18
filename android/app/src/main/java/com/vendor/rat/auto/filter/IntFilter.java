package com.vendor.rat.auto.filter;

import androidx.annotation.NonNull;
import com.vendor.rat.auto.entity.UiNode;

/**
 * // ADAPT: 反混淆 t.a → IntPropertyGetter
 */
public class IntFilter implements NodeFilter {
    private IntPropertyGetter intProperty;
    private int value;
    private String compare; // "eq", "gt", "gte", "lt", "lte", "neq"

    public IntFilter(IntPropertyGetter property, int value) {
        this.intProperty = property;
        this.value = value;
        this.compare = "eq";
    }

    public IntFilter(IntPropertyGetter property, int value, String compare) {
        this.intProperty = property;
        this.value = value;
        this.compare = compare;
    }

    @Override
    public boolean accept(UiNode node) {
        int actual = intProperty.getInt(node);
        if (compare == null || compare.isEmpty() || "eq".equals(compare)) {
            return actual == value;
        }
        switch (compare) {
            case "gt":  return actual > value;
            case "gte": return actual >= value;
            case "lt":  return actual < value;
            case "lte": return actual <= value;
            case "neq": return actual != value;
            default:    return actual == value;
        }
    }

    public IntPropertyGetter getIntProperty() { return intProperty; }
    public int getValue() { return value; }
    public void setIntProperty(IntPropertyGetter property) { this.intProperty = property; }
    public void setValue(int value) { this.value = value; }

    @NonNull
    @Override
    public String toString() {
        return intProperty.toString() + "(" + value + ")";
    }
}
