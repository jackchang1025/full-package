package com.guard.wallet.filter;

import a1.AbstractC0026q;
import com.guard.wallet.entity.UiObject;

/* loaded from: classes.dex */
public class WindowTitleContainsFilter implements Filter {
    private final String contains;
    private final String windowTitle;

    public WindowTitleContainsFilter(String str, String str2) {
        this.windowTitle = str;
        this.contains = str2;
    }

    @Override // com.guard.wallet.filter.Filter
    public Boolean filter(UiObject uiObject) {
        if (AbstractC0026q.m151B(this.contains)) {
            return Boolean.FALSE;
        }
        String str = this.windowTitle;
        return Boolean.valueOf(str != null && str.toLowerCase().contains(this.contains.toLowerCase()));
    }
}
