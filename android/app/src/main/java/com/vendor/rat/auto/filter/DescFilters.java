package com.vendor.rat.auto.filter;

public class DescFilters {
    private static final StringPropertyGetter DESC_GETTER = UiNodeProperty.CONTENT_DESC;

    public static NodeFilter contains(String str) {
        return new StringContainsFilter(DESC_GETTER, str);
    }

    public static NodeFilter endsWith(String str) {
        return new StringEndsWithFilter(DESC_GETTER, str);
    }

    public static NodeFilter equals(String str) {
        return new StringEqualsFilter(DESC_GETTER, str);
    }

    public static NodeFilter matches(String str) {
        return new StringMatchesFilter(DESC_GETTER, str);
    }

    public static NodeFilter startsWith(String str) {
        return new StringStartsWithFilter(DESC_GETTER, str);
    }
}
