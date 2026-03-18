package com.vendor.rat.auto.filter;

import com.vendor.rat.auto.entity.UiNode;

public class WindowTitleMatchesFilter implements NodeFilter {
    private final String regex;
    private final String windowTitle;

    public WindowTitleMatchesFilter(String windowTitle, String regex) {
        this.windowTitle = windowTitle;
        this.regex = regex;
    }

    @Override
    public boolean accept(UiNode node) {
        return windowTitle != null && windowTitle.matches(regex);
    }
}
