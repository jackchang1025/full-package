package com.guard.wallet.filter;

import p007j.C0350e;
import p016t.InterfaceC0911b;

/* loaded from: classes.dex */
public class StateDescFilters {
    private static final InterfaceC0911b STATE_DESC_GETTER = new C0350e(22);

    public static Filter contains(String str) {
        return new StringContainsFilter(STATE_DESC_GETTER, str);
    }

    public static Filter endsWith(String str) {
        return new StringEndsWithFilter(STATE_DESC_GETTER, str);
    }

    public static Filter equals(String str) {
        return new StringEqualsFilter(STATE_DESC_GETTER, str);
    }

    public static Filter matches(String str) {
        return new StringMatchesFilter(STATE_DESC_GETTER, str);
    }

    public static Filter startsWith(String str) {
        return new StringStartsWithFilter(STATE_DESC_GETTER, str);
    }
}
