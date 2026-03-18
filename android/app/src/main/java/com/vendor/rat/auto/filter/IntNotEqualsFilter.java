package com.vendor.rat.auto.filter;

import com.vendor.rat.auto.entity.UiNode;

public class IntNotEqualsFilter implements NodeFilter {
    private final IntPropertyGetter intProperty;
    private final int value;

    public IntNotEqualsFilter(IntPropertyGetter property, int value) {
        this.intProperty = property;
        this.value = value;
    }

    @Override
    public boolean accept(UiNode node) {
        return intProperty.getInt(node) != value;
    }
}
