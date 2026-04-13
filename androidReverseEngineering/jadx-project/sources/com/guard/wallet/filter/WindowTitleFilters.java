package com.guard.wallet.filter;

/* loaded from: classes.dex */
public class WindowTitleFilters {
    public static Filter contains(String str, String str2) {
        return new WindowTitleContainsFilter(str, str2);
    }

    public static Filter endsWith(String str, String str2) {
        return new WindowTitleEndsWithFilter(str, str2);
    }

    public static Filter equals(String str, String str2) {
        return new WindowTitleEqualFilter(str, str2);
    }

    public static Filter matches(String str, String str2) {
        return new WindowTitleMatchesFilter(str, str2);
    }

    public static Filter startsWith(String str, String str2) {
        return new WindowTitleStartsWithFilter(str, str2);
    }
}
