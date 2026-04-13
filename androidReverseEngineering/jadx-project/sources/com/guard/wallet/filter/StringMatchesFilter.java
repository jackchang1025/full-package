package com.guard.wallet.filter;

import android.support.annotation.NonNull;
import com.guard.wallet.entity.UiObject;
import p000a.AbstractC0000a;
import p007j.C0350e;
import p016t.InterfaceC0911b;

/* loaded from: classes.dex */
public class StringMatchesFilter implements Filter {
    private InterfaceC0911b keyGetter;
    private String regex;

    public StringMatchesFilter(InterfaceC0911b interfaceC0911b, String str) {
        this.keyGetter = interfaceC0911b;
        this.regex = str;
    }

    @Override // com.guard.wallet.filter.Filter
    public Boolean filter(UiObject uiObject) {
        String m886f = ((C0350e) this.keyGetter).m886f(uiObject);
        return Boolean.valueOf(m886f != null && m886f.matches(this.regex));
    }

    public InterfaceC0911b getKeyGetter() {
        return this.keyGetter;
    }

    public String getRegex() {
        return this.regex;
    }

    public void setKeyGetter(InterfaceC0911b interfaceC0911b) {
        this.keyGetter = interfaceC0911b;
    }

    public void setRegex(String str) {
        this.regex = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.keyGetter.toString());
        sb.append("Matches(\"");
        return AbstractC0000a.m18n(sb, this.regex, "\")");
    }
}
