package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import android.view.accessibility.AccessibilityNodeInfo;
import java.io.Serializable;

/* loaded from: classes.dex */
public class RootInActiveWindowResult implements Serializable {
    private AccessibilityNodeInfo curRoot;
    private boolean isComplete;

    public RootInActiveWindowResult(AccessibilityNodeInfo accessibilityNodeInfo, boolean z2) {
        this.curRoot = accessibilityNodeInfo;
        this.isComplete = z2;
    }

    public AccessibilityNodeInfo getCurRoot() {
        return this.curRoot;
    }

    public boolean isComplete() {
        return this.isComplete;
    }

    public void setComplete(boolean z2) {
        this.isComplete = z2;
    }

    public void setCurRoot(AccessibilityNodeInfo accessibilityNodeInfo) {
        this.curRoot = accessibilityNodeInfo;
    }

    @NonNull
    public String toString() {
        return "RootInActiveWindowResult{curRoot=" + this.curRoot + ", isComplete=" + this.isComplete + '}';
    }
}
