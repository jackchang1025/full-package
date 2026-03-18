package com.vendor.rat.auto.filter;

import androidx.annotation.NonNull;
import com.vendor.rat.auto.entity.UiNode;
import java.util.Objects;

public class StringEqualsFilter implements NodeFilter {
    private StringPropertyGetter keyGetter;
    private String value;

    public StringEqualsFilter(StringPropertyGetter getter, String value) {
        this.keyGetter = getter;
        this.value = value;
    }

    @Override
    public boolean accept(UiNode node) {
        String val = keyGetter.get(node);
        return val != null ? val.equalsIgnoreCase(value) : Objects.equals(value, "NULL");
    }

    public StringPropertyGetter getKeyGetter() { return keyGetter; }
    public String getValue() { return value; }
    public void setKeyGetter(StringPropertyGetter g) { this.keyGetter = g; }
    public void setValue(String s) { this.value = s; }

    @NonNull
    @Override
    public String toString() {
        return keyGetter.toString() + "(\"" + value + "\")";
    }
}
