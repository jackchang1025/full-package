package com.guard.wallet.filter;

import a1.AbstractC0026q;
import com.guard.wallet.entity.UiObject;

/* loaded from: classes.dex */
public class WindowTitleStartsWithFilter implements Filter {
    private final String prefix;
    private final String windowTitle;

    public WindowTitleStartsWithFilter(String str, String str2) {
        this.windowTitle = str;
        this.prefix = str2;
    }

    @Override // com.guard.wallet.filter.Filter
    public Boolean filter(UiObject uiObject) {
        if (AbstractC0026q.m151B(this.prefix)) {
            return Boolean.FALSE;
        }
        String str = this.windowTitle;
        return Boolean.valueOf(str != null && str.toLowerCase().startsWith(this.prefix.toLowerCase()));
    }
}
