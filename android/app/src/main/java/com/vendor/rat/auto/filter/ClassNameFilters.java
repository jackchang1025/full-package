package com.vendor.rat.auto.filter;

public class ClassNameFilters {
    private static final StringPropertyGetter CLASS_NAME_GETTER = UiNodeProperty.CLASS_NAME;

    public static NodeFilter contains(String str) {
        return new StringContainsFilter(CLASS_NAME_GETTER, str);
    }

    public static NodeFilter endsWith(String str) {
        return new StringEndsWithFilter(CLASS_NAME_GETTER, str);
    }

    public static NodeFilter equals(String str) {
        if (!str.contains(".")) {
            str = "android.widget." + str;
        }
        return new StringEqualsFilter(CLASS_NAME_GETTER, str);
    }

    public static NodeFilter matches(String str) {
        return new StringMatchesFilter(CLASS_NAME_GETTER, str);
    }

    public static NodeFilter startsWith(String str) {
        return new StringStartsWithFilter(CLASS_NAME_GETTER, str);
    }
}
