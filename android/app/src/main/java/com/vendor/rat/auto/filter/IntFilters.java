package com.vendor.rat.auto.filter;

public class IntFilters {
    public static NodeFilter equals(IntPropertyGetter property, int value) {
        return new IntEqualsFilter(property, value);
    }

    public static NodeFilter gt(IntPropertyGetter property, int value) {
        return new IntGreaterThanFilter(property, value);
    }

    public static NodeFilter gte(IntPropertyGetter property, int value) {
        return new IntGreaterThanOrEqualFilter(property, value);
    }

    public static NodeFilter lt(IntPropertyGetter property, int value) {
        return new IntLessThanFilter(property, value);
    }

    public static NodeFilter lte(IntPropertyGetter property, int value) {
        return new IntLessThanOrEqualFilter(property, value);
    }

    public static NodeFilter notEquals(IntPropertyGetter property, int value) {
        return new IntNotEqualsFilter(property, value);
    }
}
