package com.guard.wallet.filter;

import p016t.InterfaceC0910a;

/* loaded from: classes.dex */
public class IntFilters {
    public static Filter equals(InterfaceC0910a interfaceC0910a, int i2) {
        return new IntEqualsFilter(interfaceC0910a, i2);
    }

    public static Filter gt(InterfaceC0910a interfaceC0910a, int i2) {
        return new IntGreaterThanFilter(interfaceC0910a, i2);
    }

    public static Filter gte(InterfaceC0910a interfaceC0910a, int i2) {
        return new IntGreaterThanOrEqualFilter(interfaceC0910a, i2);
    }

    public static Filter lt(InterfaceC0910a interfaceC0910a, int i2) {
        return new IntLessThanFilter(interfaceC0910a, i2);
    }

    public static Filter lte(InterfaceC0910a interfaceC0910a, int i2) {
        return new IntLessThanOrEqualFilter(interfaceC0910a, i2);
    }

    public static Filter notEquals(InterfaceC0910a interfaceC0910a, int i2) {
        return new IntNotEqualsFilter(interfaceC0910a, i2);
    }
}
