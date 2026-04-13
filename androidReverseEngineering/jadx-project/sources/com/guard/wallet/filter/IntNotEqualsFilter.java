package com.guard.wallet.filter;

import com.guard.wallet.entity.UiObject;
import p007j.C0350e;
import p016t.InterfaceC0910a;

/* loaded from: classes.dex */
public class IntNotEqualsFilter implements Filter {
    private final InterfaceC0910a intProperty;
    private final int value;

    public IntNotEqualsFilter(InterfaceC0910a interfaceC0910a, int i2) {
        this.intProperty = interfaceC0910a;
        this.value = i2;
    }

    @Override // com.guard.wallet.filter.Filter
    public Boolean filter(UiObject uiObject) {
        return Boolean.valueOf(((C0350e) this.intProperty).m885e(uiObject) != this.value);
    }
}
