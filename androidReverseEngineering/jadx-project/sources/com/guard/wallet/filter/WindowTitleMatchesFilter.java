package com.guard.wallet.filter;

import com.guard.wallet.entity.UiObject;

/* loaded from: classes.dex */
public class WindowTitleMatchesFilter implements Filter {
    private final String regex;
    private final String windowTitle;

    public WindowTitleMatchesFilter(String str, String str2) {
        this.windowTitle = str;
        this.regex = str2;
    }

    @Override // com.guard.wallet.filter.Filter
    public Boolean filter(UiObject uiObject) {
        String str = this.windowTitle;
        return Boolean.valueOf(str != null && str.matches(this.regex));
    }
}
