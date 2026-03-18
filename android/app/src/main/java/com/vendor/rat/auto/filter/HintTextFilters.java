package com.vendor.rat.auto.filter;

public class HintTextFilters {
    private static final StringPropertyGetter HINT_TEXT_GETTER = UiNodeProperty.HINT_TEXT;

    public static NodeFilter contains(String str) {
        return new StringContainsFilter(HINT_TEXT_GETTER, str);
    }

    public static NodeFilter endsWith(String str) {
        return new StringEndsWithFilter(HINT_TEXT_GETTER, str);
    }

    public static NodeFilter equals(String str) {
        return new StringEqualsFilter(HINT_TEXT_GETTER, str);
    }

    public static NodeFilter matches(String str) {
        return new StringMatchesFilter(HINT_TEXT_GETTER, str);
    }

    public static NodeFilter startsWith(String str) {
        return new StringStartsWithFilter(HINT_TEXT_GETTER, str);
    }
}
