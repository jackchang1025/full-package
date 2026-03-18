package com.vendor.rat.auto.filter;

public class TextFilters {
    private static final StringPropertyGetter TEXT_GETTER = UiNodeProperty.TEXT;

    public static NodeFilter contains(String str) {
        return new StringContainsFilter(TEXT_GETTER, str);
    }

    public static NodeFilter endsWith(String str) {
        return new StringEndsWithFilter(TEXT_GETTER, str);
    }

    public static NodeFilter equals(String str) {
        return new StringEqualsFilter(TEXT_GETTER, str);
    }

    public static NodeFilter matches(String str) {
        return new StringMatchesFilter(TEXT_GETTER, str);
    }

    public static NodeFilter startsWith(String str) {
        return new StringStartsWithFilter(TEXT_GETTER, str);
    }
}
