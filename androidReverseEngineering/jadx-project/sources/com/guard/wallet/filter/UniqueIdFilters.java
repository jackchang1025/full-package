package com.guard.wallet.filter;

import p007j.C0350e;
import p016t.InterfaceC0911b;

/* loaded from: classes.dex */
public class UniqueIdFilters {
    private static final InterfaceC0911b UNIQUE_ID_GETTER = new C0350e(25);

    public static Filter contains(String str) {
        return new StringContainsFilter(UNIQUE_ID_GETTER, str);
    }

    public static Filter endsWith(String str) {
        return new StringEndsWithFilter(UNIQUE_ID_GETTER, str);
    }

    public static Filter equals(String str) {
        return new StringEqualsFilter(UNIQUE_ID_GETTER, str);
    }

    public static Filter matches(String str) {
        return new StringMatchesFilter(UNIQUE_ID_GETTER, str);
    }

    public static Filter startsWith(String str) {
        return new StringStartsWithFilter(UNIQUE_ID_GETTER, str);
    }
}
