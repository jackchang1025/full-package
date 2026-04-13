package com.guard.wallet.entity;

import androidx.annotation.NonNull;
import com.guard.wallet.msg.BaseMsgBody;
import java.util.LinkedList;
import java.util.List;

public class ReadScreenWindow extends BaseMsgBody {
    private String activePackage;
    private String activeWindow;
    private List<ReadScreenNodeInfo> children = new LinkedList<>();
    private int windowId;
    private String windowTitle;

    public ReadScreenWindow(String windowTitle, int windowId, String activePackage,
                            String activeWindow) {
        this.windowTitle = windowTitle;
        this.windowId = windowId;
        this.activePackage = activePackage;
        this.activeWindow = activeWindow;
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

    public void setActivePackage(String activePackage) {
        this.activePackage = activePackage;
    }

    public void setActiveWindow(String activeWindow) {
        this.activeWindow = activeWindow;
    }

    public void setChildren(List<ReadScreenNodeInfo> children) {
        this.children = children;
    }

    public void setWindowId(int windowId) {
        this.windowId = windowId;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }

    @NonNull
    @Override
    public String toString() {
        return "ReadScreenWindow{windowTitle='" + this.windowTitle
                + "', windowId=" + this.windowId
                + ", activePackage=" + this.activePackage
                + ", activeWindow=" + this.activeWindow
                + ", children=" + this.children + '}';
    }
}
