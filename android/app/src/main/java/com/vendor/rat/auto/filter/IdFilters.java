package com.vendor.rat.auto.filter;

public class IdFilters {
    private static final StringPropertyGetter ID_GETTER = UiNodeProperty.RESOURCE_ID;

    public static NodeFilter contains(String str) {
        return new StringContainsFilter(ID_GETTER, str);
    }

    public static NodeFilter endsWith(String str) {
        return new StringEndsWithFilter(ID_GETTER, str);
    }

    public static NodeFilter equals(String str) {
        return new StringEqualsFilter(ID_GETTER, str);
    }

    public static NodeFilter matches(String str) {
        return new StringMatchesFilter(ID_GETTER, str);
    }

    public static NodeFilter startsWith(String str) {
        return new StringStartsWithFilter(ID_GETTER, str);
    }
}
