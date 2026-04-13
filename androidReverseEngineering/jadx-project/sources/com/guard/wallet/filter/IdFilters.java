package com.guard.wallet.filter;

import p007j.C0350e;
import p016t.InterfaceC0911b;

/* loaded from: classes.dex */
public class IdFilters {
    private static final InterfaceC0911b ID_GETTER = new C0350e(13);

    public static Filter contains(String str) {
        return new StringContainsFilter(ID_GETTER, str);
    }

    public static Filter endsWith(String str) {
        return new StringEndsWithFilter(ID_GETTER, str);
    }

    public static Filter equals(String str) {
        return new StringEqualsFilter(ID_GETTER, str);
    }

    public static Filter matches(String str) {
        return new StringMatchesFilter(ID_GETTER, str);
    }

    public static Filter startsWith(String str) {
        return new StringStartsWithFilter(ID_GETTER, str);
    }
}
