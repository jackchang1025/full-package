package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class NoticeRootChangedVO implements Serializable {
    private UiObject activeFastRoot;
    private String activePackageName;
    private String activeWindowClassName;

    public NoticeRootChangedVO(UiObject uiObject, String str, String str2) {
        this.activeFastRoot = uiObject;
        this.activePackageName = str;
        this.activeWindowClassName = str2;
    }

    public UiObject getActiveFastRoot() {
        return this.activeFastRoot;
    }

    public String getActivePackageName() {
        return this.activePackageName;
    }

    public String getActiveWindowClassName() {
        return this.activeWindowClassName;
    }

    public void setActiveFastRoot(UiObject uiObject) {
        this.activeFastRoot = uiObject;
    }

    public void setActivePackageName(String str) {
        this.activePackageName = str;
    }

    public void setActiveWindowClassName(String str) {
        this.activeWindowClassName = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("NoticeRootChangedVO{activeFastRoot=");
        sb.append(this.activeFastRoot);
        sb.append(", activePackageName='");
        sb.append(this.activePackageName);
        sb.append("', activeWindowClassName='");
        return AbstractC0000a.m18n(sb, this.activeWindowClassName, "'}");
    }
}
