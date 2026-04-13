package com.guard.wallet.filter;

import p007j.C0350e;
import p016t.InterfaceC0911b;

/* loaded from: classes.dex */
public class ClassNameFilters {
    private static final InterfaceC0911b CLASS_NAME_GETTER = new C0350e(5);

    public static Filter contains(String str) {
        return new StringContainsFilter(CLASS_NAME_GETTER, str);
    }

    public static Filter endsWith(String str) {
        return new StringEndsWithFilter(CLASS_NAME_GETTER, str);
    }

    public static Filter equals(String str) {
        if (!str.contains(".")) {
            str = "android.widget.$className";
        }
        return new StringEqualsFilter(CLASS_NAME_GETTER, str);
    }

    public static Filter matches(String str) {
        return new StringMatchesFilter(CLASS_NAME_GETTER, str);
    }

    public static Filter startsWith(String str) {
        return new StringStartsWithFilter(CLASS_NAME_GETTER, str);
    }
}
