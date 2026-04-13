package com.guard.wallet.filter;

import a1.AbstractC0026q;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.UiObject;
import p000a.AbstractC0000a;
import p007j.C0350e;
import p016t.InterfaceC0911b;

/* loaded from: classes.dex */
public class StringContainsFilter implements Filter {
    private String contains;
    private InterfaceC0911b keyGetter;

    public StringContainsFilter(InterfaceC0911b interfaceC0911b, String str) {
        this.keyGetter = interfaceC0911b;
        this.contains = str;
    }

    @Override // com.guard.wallet.filter.Filter
    public Boolean filter(UiObject uiObject) {
        if (AbstractC0026q.m151B(this.contains)) {
            return Boolean.FALSE;
        }
        String m886f = ((C0350e) this.keyGetter).m886f(uiObject);
        return Boolean.valueOf(m886f != null && m886f.toLowerCase().contains(this.contains.toLowerCase()));
    }

    public String getContains() {
        return this.contains;
    }

    public InterfaceC0911b getKeyGetter() {
        return this.keyGetter;
    }

    public void setContains(String str) {
        this.contains = str;
    }

    public void setKeyGetter(InterfaceC0911b interfaceC0911b) {
        this.keyGetter = interfaceC0911b;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.keyGetter.toString());
        sb.append("Contains(\"");
        return AbstractC0000a.m18n(sb, this.contains, "\")");
    }
}
