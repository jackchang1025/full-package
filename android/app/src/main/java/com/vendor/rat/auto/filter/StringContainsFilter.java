package com.vendor.rat.auto.filter;

import androidx.annotation.NonNull;
import com.vendor.rat.auto.entity.UiNode;

public class StringContainsFilter implements NodeFilter {
    private StringPropertyGetter keyGetter;
    private String contains;

    public StringContainsFilter(StringPropertyGetter getter, String contains) {
        this.keyGetter = getter;
        this.contains = contains;
    }

    @Override
    public boolean accept(UiNode node) {
        if (contains == null || contains.isEmpty()) return false;
        String val = keyGetter.get(node);
        return val != null && val.toLowerCase().contains(contains.toLowerCase());
    }

    public String getContains() { return contains; }
    public StringPropertyGetter getKeyGetter() { return keyGetter; }
    public void setContains(String s) { this.contains = s; }
    public void setKeyGetter(StringPropertyGetter g) { this.keyGetter = g; }

    @NonNull
    @Override
    public String toString() {
        return keyGetter.toString() + "Contains(\"" + contains + "\")";
    }
}
