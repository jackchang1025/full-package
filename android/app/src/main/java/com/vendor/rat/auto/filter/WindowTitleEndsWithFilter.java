package com.vendor.rat.auto.filter;

import com.vendor.rat.auto.entity.UiNode;

public class WindowTitleEndsWithFilter implements NodeFilter {
    private final String suffix;
    private final String windowTitle;

    public WindowTitleEndsWithFilter(String windowTitle, String suffix) {
        this.windowTitle = windowTitle;
        this.suffix = suffix;
    }

    @Override
    public boolean accept(UiNode node) {
        if (suffix == null || suffix.isEmpty()) return false;
        return windowTitle != null && windowTitle.toLowerCase().endsWith(suffix.toLowerCase());
    }
}
