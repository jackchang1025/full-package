package com.vendor.rat.auto.filter;

import com.vendor.rat.auto.entity.UiNode;
import java.util.Objects;

public class WindowTitleEqualFilter implements NodeFilter {
    private final String value;
    private final String windowTitle;

    public WindowTitleEqualFilter(String windowTitle, String value) {
        this.windowTitle = windowTitle;
        this.value = value;
    }

    @Override
    public boolean accept(UiNode node) {
        return windowTitle != null ? windowTitle.equalsIgnoreCase(value) : Objects.equals(value, "NULL");
    }
}
