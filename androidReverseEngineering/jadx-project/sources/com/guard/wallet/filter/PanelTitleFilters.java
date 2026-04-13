package com.guard.wallet.filter;

import p007j.C0350e;
import p016t.InterfaceC0911b;

/* loaded from: classes.dex */
public class PanelTitleFilters {
    private static final InterfaceC0911b PANEL_TITLE_GETTER = new C0350e(16);

    public static Filter contains(String str) {
        return new StringContainsFilter(PANEL_TITLE_GETTER, str);
    }

    public static Filter endsWith(String str) {
        return new StringEndsWithFilter(PANEL_TITLE_GETTER, str);
    }

    public static Filter equals(String str) {
        return new StringEqualsFilter(PANEL_TITLE_GETTER, str);
    }

    public static Filter matches(String str) {
        return new StringMatchesFilter(PANEL_TITLE_GETTER, str);
    }

    public static Filter startsWith(String str) {
        return new StringStartsWithFilter(PANEL_TITLE_GETTER, str);
    }
}
