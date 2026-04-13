package com.guard.wallet.filter;

import p007j.C0350e;
import p016t.InterfaceC0911b;

/* loaded from: classes.dex */
public class TextFilters {
    private static final InterfaceC0911b TEXT_GETTER = new C0350e(23);

    public static Filter contains(String str) {
        return new StringContainsFilter(TEXT_GETTER, str);
    }

    public static Filter endsWith(String str) {
        return new StringEndsWithFilter(TEXT_GETTER, str);
    }

    public static Filter equals(String str) {
        return new StringEqualsFilter(TEXT_GETTER, str);
    }

    public static Filter matches(String str) {
        return new StringMatchesFilter(TEXT_GETTER, str);
    }

    public static Filter startsWith(String str) {
        return new StringStartsWithFilter(TEXT_GETTER, str);
    }
}
