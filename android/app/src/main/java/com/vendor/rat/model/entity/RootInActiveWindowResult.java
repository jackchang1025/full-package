package com.vendor.rat.model.entity;

// ADAPT: vendor = com.guard.wallet.entity.RootInActiveWindowResult

import android.view.accessibility.AccessibilityNodeInfo;
import androidx.annotation.NonNull;
import java.io.Serializable;

public class RootInActiveWindowResult implements Serializable {
    private AccessibilityNodeInfo curRoot;
    private boolean isComplete;

    public RootInActiveWindowResult(AccessibilityNodeInfo curRoot, boolean isComplete) {
        this.curRoot = curRoot;
        this.isComplete = isComplete;
    }

    public AccessibilityNodeInfo getCurRoot() {
        return this.curRoot;
    }

    public boolean isComplete() {
        return this.isComplete;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public void setCurRoot(AccessibilityNodeInfo curRoot) {
        this.curRoot = curRoot;
    }

    @NonNull
    @Override
    public String toString() {
        return "RootInActiveWindowResult{curRoot=" + curRoot + ", isComplete=" + isComplete + '}';
    }
}
