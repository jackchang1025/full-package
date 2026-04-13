package com.guard.wallet.filter;

import a1.AbstractC0026q;
import com.guard.wallet.entity.UiObject;

/* loaded from: classes.dex */
public class WindowTitleEndsWithFilter implements Filter {
    private final String suffix;
    private final String windowTitle;

    public WindowTitleEndsWithFilter(String str, String str2) {
        this.windowTitle = str;
        this.suffix = str2;
    }

    @Override // com.guard.wallet.filter.Filter
    public Boolean filter(UiObject uiObject) {
        if (AbstractC0026q.m151B(this.suffix)) {
            return Boolean.FALSE;
        }
        String str = this.windowTitle;
        return Boolean.valueOf(str != null && str.toLowerCase().endsWith(this.suffix.toLowerCase()));
    }
}
