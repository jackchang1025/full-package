package com.guard.wallet.filter;

import p007j.C0350e;
import p016t.InterfaceC0911b;

/* loaded from: classes.dex */
public class HintTextFilters {
    private static final InterfaceC0911b HINT_TEXT_GETTER = new C0350e(12);

    public static Filter contains(String str) {
        return new StringContainsFilter(HINT_TEXT_GETTER, str);
    }

    public static Filter endsWith(String str) {
        return new StringEndsWithFilter(HINT_TEXT_GETTER, str);
    }

    public static Filter equals(String str) {
        return new StringEqualsFilter(HINT_TEXT_GETTER, str);
    }

    public static Filter matches(String str) {
        return new StringMatchesFilter(HINT_TEXT_GETTER, str);
    }

    public static Filter startsWith(String str) {
        return new StringStartsWithFilter(HINT_TEXT_GETTER, str);
    }
}
