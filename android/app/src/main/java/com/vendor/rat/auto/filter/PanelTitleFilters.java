package com.vendor.rat.auto.filter;

public class PanelTitleFilters {
    private static final StringPropertyGetter PANEL_TITLE_GETTER = UiNodeProperty.PANEL_TITLE;

    public static NodeFilter contains(String str) {
        return new StringContainsFilter(PANEL_TITLE_GETTER, str);
    }

    public static NodeFilter endsWith(String str) {
        return new StringEndsWithFilter(PANEL_TITLE_GETTER, str);
    }

    public static NodeFilter equals(String str) {
        return new StringEqualsFilter(PANEL_TITLE_GETTER, str);
    }

    public static NodeFilter matches(String str) {
        return new StringMatchesFilter(PANEL_TITLE_GETTER, str);
    }

    public static NodeFilter startsWith(String str) {
        return new StringStartsWithFilter(PANEL_TITLE_GETTER, str);
    }
}
