package com.vendor.rat.auto.filter;

import com.vendor.rat.auto.entity.UiNode;

public class WindowTitleStartsWithFilter implements NodeFilter {
    private final String prefix;
    private final String windowTitle;

    public WindowTitleStartsWithFilter(String windowTitle, String prefix) {
        this.windowTitle = windowTitle;
        this.prefix = prefix;
    }

    @Override
    public boolean accept(UiNode node) {
        if (prefix == null || prefix.isEmpty()) return false;
        return windowTitle != null && windowTitle.toLowerCase().startsWith(prefix.toLowerCase());
    }
}
