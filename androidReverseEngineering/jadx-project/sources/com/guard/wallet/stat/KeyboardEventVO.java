package com.guard.wallet.stat;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class KeyboardEventVO implements Serializable {
    private String beforeText;
    private String editText;
    private String eventText;

    public KeyboardEventVO() {
    }

    public KeyboardEventVO(String str, String str2, String str3) {
        this.beforeText = str;
        this.editText = str2;
        this.eventText = str3;
    }

    public String getBeforeText() {
        return this.beforeText;
    }

    public String getEditText() {
        return this.editText;
    }

    public String getEventText() {
        return this.eventText;
    }

    public void setBeforeText(String str) {
        this.beforeText = str;
    }

    public void setEditText(String str) {
        this.editText = str;
    }

    public void setEventText(String str) {
        this.eventText = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("KeyboardEventStatVO{beforeText='");
        sb.append(this.beforeText);
        sb.append("', editText='");
        sb.append(this.editText);
        sb.append("', eventText='");
        return AbstractC0000a.m18n(sb, this.eventText, "'}");
    }
}
