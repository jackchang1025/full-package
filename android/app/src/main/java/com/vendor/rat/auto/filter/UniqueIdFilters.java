package com.vendor.rat.auto.filter;

public class UniqueIdFilters {
    private static final StringPropertyGetter UNIQUE_ID_GETTER = UiNodeProperty.UNIQUE_ID;

    public static NodeFilter contains(String str) {
        return new StringContainsFilter(UNIQUE_ID_GETTER, str);
    }

    public static NodeFilter endsWith(String str) {
        return new StringEndsWithFilter(UNIQUE_ID_GETTER, str);
    }

    public static NodeFilter equals(String str) {
        return new StringEqualsFilter(UNIQUE_ID_GETTER, str);
    }

    public static NodeFilter matches(String str) {
        return new StringMatchesFilter(UNIQUE_ID_GETTER, str);
    }

    public static NodeFilter startsWith(String str) {
        return new StringStartsWithFilter(UNIQUE_ID_GETTER, str);
    }
}
