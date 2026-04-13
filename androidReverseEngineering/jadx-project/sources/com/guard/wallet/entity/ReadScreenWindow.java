package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import com.guard.wallet.msg.BaseMsgBody;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes.dex */
public class ReadScreenWindow extends BaseMsgBody {
    private String activePackage;
    private String activeWindow;
    private List<ReadScreenNodeInfo> children = new LinkedList();
    private int windowId;
    private String windowTitle;

    public ReadScreenWindow(String str, int i2, String str2, String str3) {
        this.windowTitle = str;
        this.windowId = i2;
        this.activePackage = str2;
        this.activeWindow = str3;
    }

    public String getActivePackage() {
        return this.activePackage;
    }

    public String getActiveWindow() {
        return this.activeWindow;
    }

    public List<ReadScreenNodeInfo> getChildren() {
        return this.children;
    }

    public int getWindowId() {
        return this.windowId;
    }

    public String getWindowTitle() {
        return this.windowTitle;
    }

    public void setActivePackage(String str) {
        this.activePackage = str;
    }

    public void setActiveWindow(String str) {
        this.activeWindow = str;
    }

    public void setChildren(List<ReadScreenNodeInfo> list) {
        this.children = list;
    }

    public void setWindowId(int i2) {
        this.windowId = i2;
    }

    public void setWindowTitle(String str) {
        this.windowTitle = str;
    }

    @NonNull
    public String toString() {
        return "ReadScreenWindow{windowTitle='" + this.windowTitle + "', windowId=" + this.windowId + ", activePackage=" + this.activePackage + ", activeWindow=" + this.activeWindow + ", children=" + this.children + '}';
    }
}
