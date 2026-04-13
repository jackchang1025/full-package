package com.guard.wallet.filter;

import p007j.C0350e;
import p016t.InterfaceC0911b;

/* loaded from: classes.dex */
public class RoleDescFilters {
    private static final InterfaceC0911b ROLE_DESC_GETTER = new C0350e(18);

    public static Filter contains(String str) {
        return new StringContainsFilter(ROLE_DESC_GETTER, str);
    }

    public static Filter endsWith(String str) {
        return new StringEndsWithFilter(ROLE_DESC_GETTER, str);
    }

    public static Filter equals(String str) {
        return new StringEqualsFilter(ROLE_DESC_GETTER, str);
    }

    public static Filter matches(String str) {
        return new StringMatchesFilter(ROLE_DESC_GETTER, str);
    }

    public static Filter startsWith(String str) {
        return new StringStartsWithFilter(ROLE_DESC_GETTER, str);
    }
}
