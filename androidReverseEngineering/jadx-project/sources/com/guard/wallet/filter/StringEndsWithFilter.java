package com.guard.wallet.filter;

import a1.AbstractC0026q;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.UiObject;
import p000a.AbstractC0000a;
import p007j.C0350e;
import p016t.InterfaceC0911b;

/* loaded from: classes.dex */
public class StringEndsWithFilter implements Filter {
    private InterfaceC0911b keyGetter;
    private String suffix;

    public StringEndsWithFilter(InterfaceC0911b interfaceC0911b, String str) {
        this.keyGetter = interfaceC0911b;
        this.suffix = str;
    }

    @Override // com.guard.wallet.filter.Filter
    public Boolean filter(UiObject uiObject) {
        if (AbstractC0026q.m151B(this.suffix)) {
            return Boolean.FALSE;
        }
        String m886f = ((C0350e) this.keyGetter).m886f(uiObject);
        return Boolean.valueOf(m886f != null && m886f.toLowerCase().endsWith(this.suffix.toLowerCase()));
    }

    public InterfaceC0911b getKeyGetter() {
        return this.keyGetter;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setKeyGetter(InterfaceC0911b interfaceC0911b) {
        this.keyGetter = interfaceC0911b;
    }

    public void setSuffix(String str) {
        this.suffix = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.keyGetter.toString());
        sb.append("EndsWith(\"");
        return AbstractC0000a.m18n(sb, this.suffix, "\")");
    }
}
