package com.vendor.rat.auto.filter;

import androidx.annotation.NonNull;
import com.vendor.rat.auto.entity.UiNode;

public class StringEndsWithFilter implements NodeFilter {
    private StringPropertyGetter keyGetter;
    private String suffix;

    public StringEndsWithFilter(StringPropertyGetter getter, String suffix) {
        this.keyGetter = getter;
        this.suffix = suffix;
    }

    @Override
    public boolean accept(UiNode node) {
        if (suffix == null || suffix.isEmpty()) return false;
        String val = keyGetter.get(node);
        return val != null && val.toLowerCase().endsWith(suffix.toLowerCase());
    }

    public StringPropertyGetter getKeyGetter() { return keyGetter; }
    public String getSuffix() { return suffix; }
    public void setKeyGetter(StringPropertyGetter g) { this.keyGetter = g; }
    public void setSuffix(String s) { this.suffix = s; }

    @NonNull
    @Override
    public String toString() {
        return keyGetter.toString() + "EndsWith(\"" + suffix + "\")";
    }
}
