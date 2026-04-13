package com.guard.wallet.filter;

import android.support.annotation.NonNull;
import com.guard.wallet.entity.UiObject;
import java.util.Objects;
import p000a.AbstractC0000a;
import p007j.C0350e;
import p016t.InterfaceC0911b;

/* loaded from: classes.dex */
public class StringEqualsFilter implements Filter {
    private InterfaceC0911b keyGetter;
    private String value;

    public StringEqualsFilter(InterfaceC0911b interfaceC0911b, String str) {
        this.keyGetter = interfaceC0911b;
        this.value = str;
    }

    @Override // com.guard.wallet.filter.Filter
    public Boolean filter(UiObject uiObject) {
        String m886f = ((C0350e) this.keyGetter).m886f(uiObject);
        return Boolean.valueOf(m886f != null ? m886f.equalsIgnoreCase(this.value) : Objects.equals(this.value, "NULL"));
    }

    public InterfaceC0911b getKeyGetter() {
        return this.keyGetter;
    }

    public String getValue() {
        return this.value;
    }

    public void setKeyGetter(InterfaceC0911b interfaceC0911b) {
        this.keyGetter = interfaceC0911b;
    }

    public void setValue(String str) {
        this.value = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.keyGetter.toString());
        sb.append("(\"");
        return AbstractC0000a.m18n(sb, this.value, "\")");
    }
}
