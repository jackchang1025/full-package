package com.guard.wallet.filter;

import android.support.annotation.NonNull;
import com.guard.wallet.entity.UiObject;
import p000a.AbstractC0000a;
import p007j.C0350e;
import p016t.InterfaceC0910a;

/* loaded from: classes.dex */
public class IntFilter implements Filter {
    private InterfaceC0910a intProperty;
    private int value;

    public IntFilter(InterfaceC0910a interfaceC0910a, int i2) {
        this.intProperty = interfaceC0910a;
        this.value = i2;
    }

    @Override // com.guard.wallet.filter.Filter
    public Boolean filter(UiObject uiObject) {
        return Boolean.valueOf(((C0350e) this.intProperty).m885e(uiObject) == this.value);
    }

    public InterfaceC0910a getIntProperty() {
        return this.intProperty;
    }

    public int getValue() {
        return this.value;
    }

    public void setIntProperty(InterfaceC0910a interfaceC0910a) {
        this.intProperty = interfaceC0910a;
    }

    public void setValue(int i2) {
        this.value = i2;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.intProperty.toString());
        sb.append("(");
        return AbstractC0000a.m17m(sb, this.value, ")");
    }
}
