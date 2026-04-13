package com.guard.wallet.filter;

import com.guard.wallet.entity.UiObject;
import java.util.Objects;

/* loaded from: classes.dex */
public class WindowTitleEqualFilter implements Filter {
    private final String value;
    private final String windowTitle;

    public WindowTitleEqualFilter(String str, String str2) {
        this.windowTitle = str;
        this.value = str2;
    }

    @Override // com.guard.wallet.filter.Filter
    public Boolean filter(UiObject uiObject) {
        String str = this.windowTitle;
        return Boolean.valueOf(str != null ? str.equalsIgnoreCase(this.value) : Objects.equals(this.value, "NULL"));
    }
}
