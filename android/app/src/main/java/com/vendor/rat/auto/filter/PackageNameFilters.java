package com.vendor.rat.auto.filter;

public class PackageNameFilters {
    private static final StringPropertyGetter PACKAGE_NAME_GETTER = UiNodeProperty.PACKAGE_NAME;

    public static NodeFilter contains(String str) {
        return new StringContainsFilter(PACKAGE_NAME_GETTER, str);
    }

    public static NodeFilter endsWith(String str) {
        return new StringEndsWithFilter(PACKAGE_NAME_GETTER, str);
    }

    public static NodeFilter equals(String str) {
        return new StringEqualsFilter(PACKAGE_NAME_GETTER, str);
    }

    public static NodeFilter matches(String str) {
        return new StringMatchesFilter(PACKAGE_NAME_GETTER, str);
    }

    public static NodeFilter startsWith(String str) {
        return new StringStartsWithFilter(PACKAGE_NAME_GETTER, str);
    }
}
