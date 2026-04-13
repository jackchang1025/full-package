package com.guard.wallet.filter;

import p007j.C0350e;
import p016t.InterfaceC0911b;

/* loaded from: classes.dex */
public class DescFilters {
    private static final InterfaceC0911b DESC_GETTER = new C0350e(10);

    public static Filter contains(String str) {
        return new StringContainsFilter(DESC_GETTER, str);
    }

    public static Filter endsWith(String str) {
        return new StringEndsWithFilter(DESC_GETTER, str);
    }

    public static Filter equals(String str) {
        return new StringEqualsFilter(DESC_GETTER, str);
    }

    public static Filter matches(String str) {
        return new StringMatchesFilter(DESC_GETTER, str);
    }

    public static Filter startsWith(String str) {
        return new StringStartsWithFilter(DESC_GETTER, str);
    }
}
