package com.guard.wallet.entity;

import android.graphics.Rect;
import androidx.annotation.NonNull;

public class ReadScreenNodeInfo {
    private Rect boundsInScreen;
    private Point centerInScreen;
    private String className;
    private final int depth;
    private String desc;
    private boolean focused;
    private int height;
    private String hintText;
    private final int indexInParent;
    private String packageName;
    private String paneTitle;
    private String roleDesc;
    private String text;
    private String tooltipText;
    private int width;

    public ReadScreenNodeInfo(int depth, int indexInParent) {
        this.depth = depth;
        this.indexInParent = indexInParent;
    }

    public Rect getBoundsInScreen() {
        return this.boundsInScreen;
    }

    public Point getCenterInScreen() {
        return this.centerInScreen;
    }

    public String getClassName() {
        return this.className;
    }

    public int getDepth() {
        return this.depth;
    }

    public String getDesc() {
        return this.desc;
    }

    public int getHeight() {
        return this.height;
    }

    public String getHintText() {
        return this.hintText;
    }

    public int getIndexInParent() {
        return this.indexInParent;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getPaneTitle() {
        return this.paneTitle;
    }

    public String getRoleDesc() {
        return this.roleDesc;
    }

    public String getText() {
        return this.text;
    }

    public String getTooltipText() {
        return this.tooltipText;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isFocused() {
        return this.focused;
    }

    public void setBoundsInScreen(Rect boundsInScreen) {
        this.boundsInScreen = boundsInScreen;
    }

    public void setCenterInScreen(Point centerInScreen) {
        this.centerInScreen = centerInScreen;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setPaneTitle(String paneTitle) {
        this.paneTitle = paneTitle;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTooltipText(String tooltipText) {
        this.tooltipText = tooltipText;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @NonNull
    @Override
    public String toString() {
        return "ReadScreenNodeInfo{depth=" + this.depth
                + ", indexInParent=" + this.indexInParent
                + ", boundsInScreen=" + this.boundsInScreen
                + ", centerInScreen=" + this.centerInScreen
                + ", width=" + this.width
                + ", height=" + this.height
                + ", packageName='" + this.packageName
                + "', className='" + this.className
                + "', text='" + this.text
                + "', desc='" + this.desc
                + "', hintText='" + this.hintText
                + "', tooltipText='" + this.tooltipText
                + "', paneTitle='" + this.paneTitle
                + "', roleDesc='" + this.roleDesc
                + "', focused=" + this.focused + '}';
    }
}
