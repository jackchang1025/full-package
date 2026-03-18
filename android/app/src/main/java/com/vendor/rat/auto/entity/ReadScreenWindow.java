package com.vendor.rat.auto.entity;

import androidx.annotation.NonNull;
import java.util.LinkedList;
import java.util.List;

/**
 * Vendor: com/guard/wallet/entity/ReadScreenWindow.java (67行)
 * ADAPT: extends BaseMsgBody → standalone (BaseMsgBody 属于 msg 模块)
 */
public class ReadScreenWindow {

    private String activePackage;
    private String activeWindow;
    private List<ReadScreenNodeInfo> children = new LinkedList<>();
    private int windowId;
    private String windowTitle;

    public ReadScreenWindow(String windowTitle, int windowId,
                            String activePackage, String activeWindow) {
        this.windowTitle = windowTitle;
        this.windowId = windowId;
        this.activePackage = activePackage;
        this.activeWindow = activeWindow;
    }

    public String getActivePackage() { return this.activePackage; }
    public String getActiveWindow() { return this.activeWindow; }
    public List<ReadScreenNodeInfo> getChildren() { return this.children; }
    public int getWindowId() { return this.windowId; }
    public String getWindowTitle() { return this.windowTitle; }

    public void setActivePackage(String str) { this.activePackage = str; }
    public void setActiveWindow(String str) { this.activeWindow = str; }
    public void setChildren(List<ReadScreenNodeInfo> list) { this.children = list; }
    public void setWindowId(int id) { this.windowId = id; }
    public void setWindowTitle(String str) { this.windowTitle = str; }

    @NonNull
    public String toString() {
        return "ReadScreenWindow{windowTitle='" + this.windowTitle
                + "', windowId=" + this.windowId
                + ", activePackage=" + this.activePackage
                + ", activeWindow=" + this.activeWindow
                + ", children=" + this.children + '}';
    }
}
