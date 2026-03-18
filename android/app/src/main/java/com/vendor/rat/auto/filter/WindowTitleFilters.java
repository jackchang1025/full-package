package com.vendor.rat.auto.filter;

public class WindowTitleFilters {
    public static NodeFilter contains(String windowTitle, String contains) {
        return new WindowTitleContainsFilter(windowTitle, contains);
    }

    public static NodeFilter endsWith(String windowTitle, String suffix) {
        return new WindowTitleEndsWithFilter(windowTitle, suffix);
    }

    public static NodeFilter equals(String windowTitle, String value) {
        return new WindowTitleEqualFilter(windowTitle, value);
    }

    public static NodeFilter matches(String windowTitle, String regex) {
        return new WindowTitleMatchesFilter(windowTitle, regex);
    }

    public static NodeFilter startsWith(String windowTitle, String prefix) {
        return new WindowTitleStartsWithFilter(windowTitle, prefix);
    }
}
