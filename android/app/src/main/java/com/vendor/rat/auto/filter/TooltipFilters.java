package com.vendor.rat.auto.filter;

public class TooltipFilters {
    private static final StringPropertyGetter TOOLTIP_GETTER = UiNodeProperty.TOOLTIP;

    public static NodeFilter contains(String str) {
        return new StringContainsFilter(TOOLTIP_GETTER, str);
    }

    public static NodeFilter endsWith(String str) {
        return new StringEndsWithFilter(TOOLTIP_GETTER, str);
    }

    public static NodeFilter equals(String str) {
        return new StringEqualsFilter(TOOLTIP_GETTER, str);
    }

    public static NodeFilter matches(String str) {
        return new StringMatchesFilter(TOOLTIP_GETTER, str);
    }

    public static NodeFilter startsWith(String str) {
        return new StringStartsWithFilter(TOOLTIP_GETTER, str);
    }
}
