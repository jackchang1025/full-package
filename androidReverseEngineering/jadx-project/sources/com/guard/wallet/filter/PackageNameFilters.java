package com.guard.wallet.filter;

import p007j.C0350e;
import p016t.InterfaceC0911b;

/* loaded from: classes.dex */
public class PackageNameFilters {
    private static final InterfaceC0911b PACKAGE_NAME_GETTER = new C0350e(15);

    public static Filter contains(String str) {
        return new StringContainsFilter(PACKAGE_NAME_GETTER, str);
    }

    public static Filter endsWith(String str) {
        return new StringEndsWithFilter(PACKAGE_NAME_GETTER, str);
    }

    public static Filter equals(String str) {
        return new StringEqualsFilter(PACKAGE_NAME_GETTER, str);
    }

    public static Filter matches(String str) {
        return new StringMatchesFilter(PACKAGE_NAME_GETTER, str);
    }

    public static Filter startsWith(String str) {
        return new StringStartsWithFilter(PACKAGE_NAME_GETTER, str);
    }
}
