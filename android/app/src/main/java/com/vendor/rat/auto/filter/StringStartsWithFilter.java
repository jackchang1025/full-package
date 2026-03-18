package com.vendor.rat.auto.filter;

import androidx.annotation.NonNull;
import com.vendor.rat.auto.entity.UiNode;

public class StringStartsWithFilter implements NodeFilter {
    private StringPropertyGetter keyGetter;
    private String prefix;

    public StringStartsWithFilter(StringPropertyGetter getter, String prefix) {
        this.prefix = prefix;
        this.keyGetter = getter;
    }

    @Override
    public boolean accept(UiNode node) {
        if (prefix == null || prefix.isEmpty()) return false;
        String val = keyGetter.get(node);
        return val != null && val.toLowerCase().startsWith(prefix.toLowerCase());
    }

    public StringPropertyGetter getKeyGetter() { return keyGetter; }
    public String getPrefix() { return prefix; }
    public void setKeyGetter(StringPropertyGetter g) { this.keyGetter = g; }
    public void setPrefix(String s) { this.prefix = s; }

    @NonNull
    @Override
    public String toString() {
        return keyGetter.toString() + "StartsWith(\"" + prefix + "\")";
    }
}
