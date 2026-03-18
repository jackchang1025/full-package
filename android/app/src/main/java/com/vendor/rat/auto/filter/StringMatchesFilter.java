package com.vendor.rat.auto.filter;

import androidx.annotation.NonNull;
import com.vendor.rat.auto.entity.UiNode;

public class StringMatchesFilter implements NodeFilter {
    private StringPropertyGetter keyGetter;
    private String regex;

    public StringMatchesFilter(StringPropertyGetter getter, String regex) {
        this.keyGetter = getter;
        this.regex = regex;
    }

    @Override
    public boolean accept(UiNode node) {
        String val = keyGetter.get(node);
        return val != null && val.matches(regex);
    }

    public StringPropertyGetter getKeyGetter() { return keyGetter; }
    public String getRegex() { return regex; }
    public void setKeyGetter(StringPropertyGetter g) { this.keyGetter = g; }
    public void setRegex(String s) { this.regex = s; }

    @NonNull
    @Override
    public String toString() {
        return keyGetter.toString() + "Matches(\"" + regex + "\")";
    }
}
