package com.vendor.rat.model.entity;

// ADAPT: vendor = com.guard.wallet.entity.NoticeRootChangedVO
// ADAPT: UiObject → com.vendor.rat.auto.entity.UiNode

import androidx.annotation.NonNull;
import com.vendor.rat.auto.entity.UiNode;
import java.io.Serializable;

public class NoticeRootChangedVO implements Serializable {
    private UiNode activeFastRoot;
    private String activePackageName;
    private String activeWindowClassName;

    public NoticeRootChangedVO(UiNode activeFastRoot, String activePackageName,
                               String activeWindowClassName) {
        this.activeFastRoot = activeFastRoot;
        this.activePackageName = activePackageName;
        this.activeWindowClassName = activeWindowClassName;
    }

    public UiNode getActiveFastRoot() {
        return this.activeFastRoot;
    }

    public String getActivePackageName() {
        return this.activePackageName;
    }

    public String getActiveWindowClassName() {
        return this.activeWindowClassName;
    }

    public void setActiveFastRoot(UiNode activeFastRoot) {
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
        return "NoticeRootChangedVO{activeFastRoot=" + activeFastRoot
                + ", activePackageName='" + activePackageName
                + "', activeWindowClassName='" + activeWindowClassName + "'}";
    }
}
