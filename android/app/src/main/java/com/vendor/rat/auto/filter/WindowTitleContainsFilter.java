package com.vendor.rat.auto.filter;

import com.vendor.rat.auto.entity.UiNode;

public class WindowTitleContainsFilter implements NodeFilter {
    private final String contains;
    private final String windowTitle;

    public WindowTitleContainsFilter(String windowTitle, String contains) {
        this.windowTitle = windowTitle;
        this.contains = contains;
    }

    @Override
    public boolean accept(UiNode node) {
        if (contains == null || contains.isEmpty()) return false;
        return windowTitle != null && windowTitle.toLowerCase().contains(contains.toLowerCase());
    }
}
