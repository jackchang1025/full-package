package com.guard.wallet.entity;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class NoticeRootChangedVO implements Serializable {
    private UiObject activeFastRoot;
    private String activePackageName;
    private String activeWindowClassName;

    public NoticeRootChangedVO(UiObject activeFastRoot, String activePackageName,
                               String activeWindowClassName) {
        this.activeFastRoot = activeFastRoot;
        this.activePackageName = activePackageName;
        this.activeWindowClassName = activeWindowClassName;
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

    public void setActiveFastRoot(UiObject activeFastRoot) {
        this.activeFastRoot = activeFastRoot;
    }

    public void setActivePackageName(String activePackageName) {
        this.activePackageName = activePackageName;
    }

    public void setActiveWindowClassName(String activeWindowClassName) {
        this.activeWindowClassName = activeWindowClassName;
    }

    @NonNull
    @Override
    public String toString() {
        return "NoticeRootChangedVO{activeFastRoot=" + this.activeFastRoot
                + ", activePackageName='" + this.activePackageName
                + "', activeWindowClassName='" + this.activeWindowClassName + "'}";
    }
}
