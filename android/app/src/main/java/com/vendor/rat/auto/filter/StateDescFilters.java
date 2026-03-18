package com.vendor.rat.auto.filter;

public class StateDescFilters {
    private static final StringPropertyGetter STATE_DESC_GETTER = UiNodeProperty.STATE_DESC;

    public static NodeFilter contains(String str) {
        return new StringContainsFilter(STATE_DESC_GETTER, str);
    }

    public static NodeFilter endsWith(String str) {
        return new StringEndsWithFilter(STATE_DESC_GETTER, str);
    }

    public static NodeFilter equals(String str) {
        return new StringEqualsFilter(STATE_DESC_GETTER, str);
    }

    public static NodeFilter matches(String str) {
        return new StringMatchesFilter(STATE_DESC_GETTER, str);
    }

    public static NodeFilter startsWith(String str) {
        return new StringStartsWithFilter(STATE_DESC_GETTER, str);
    }
}
