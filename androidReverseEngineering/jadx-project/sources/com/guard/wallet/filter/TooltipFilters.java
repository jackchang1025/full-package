package com.guard.wallet.filter;

import p007j.C0350e;
import p016t.InterfaceC0911b;

/* loaded from: classes.dex */
public class TooltipFilters {
    private static final InterfaceC0911b TOOL_TIP_GETTER = new C0350e(24);

    public static Filter contains(String str) {
        return new StringContainsFilter(TOOL_TIP_GETTER, str);
    }

    public static Filter endsWith(String str) {
        return new StringEndsWithFilter(TOOL_TIP_GETTER, str);
    }

    public static Filter equals(String str) {
        return new StringEqualsFilter(TOOL_TIP_GETTER, str);
    }

    public static Filter matches(String str) {
        return new StringMatchesFilter(TOOL_TIP_GETTER, str);
    }

    public static Filter startsWith(String str) {
        return new StringStartsWithFilter(TOOL_TIP_GETTER, str);
    }
}
