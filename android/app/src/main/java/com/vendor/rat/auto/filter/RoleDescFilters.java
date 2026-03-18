package com.vendor.rat.auto.filter;

public class RoleDescFilters {
    private static final StringPropertyGetter ROLE_DESC_GETTER = UiNodeProperty.ROLE_DESC;

    public static NodeFilter contains(String str) {
        return new StringContainsFilter(ROLE_DESC_GETTER, str);
    }

    public static NodeFilter endsWith(String str) {
        return new StringEndsWithFilter(ROLE_DESC_GETTER, str);
    }

    public static NodeFilter equals(String str) {
        return new StringEqualsFilter(ROLE_DESC_GETTER, str);
    }

    public static NodeFilter matches(String str) {
        return new StringMatchesFilter(ROLE_DESC_GETTER, str);
    }

    public static NodeFilter startsWith(String str) {
        return new StringStartsWithFilter(ROLE_DESC_GETTER, str);
    }
}
