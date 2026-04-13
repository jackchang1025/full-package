package com.guard.wallet.entity;

import android.graphics.Rect;
import android.support.annotation.NonNull;

/* loaded from: classes.dex */
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

    public ReadScreenNodeInfo(int i2, int i3) {
        this.depth = i2;
        this.indexInParent = i3;
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

    public void setBoundsInScreen(Rect rect) {
        this.boundsInScreen = rect;
    }

    public void setCenterInScreen(Point point) {
        this.centerInScreen = point;
    }

    public void setClassName(String str) {
        this.className = str;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public void setFocused(boolean z2) {
        this.focused = z2;
    }

    public void setHeight(int i2) {
        this.height = i2;
    }

    public void setHintText(String str) {
        this.hintText = str;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setPaneTitle(String str) {
        this.paneTitle = str;
    }

    public void setRoleDesc(String str) {
        this.roleDesc = str;
    }

    public void setText(String str) {
        this.text = str;
    }

    public void setTooltipText(String str) {
        this.tooltipText = str;
    }

    public void setWidth(int i2) {
        this.width = i2;
    }

    @NonNull
    public String toString() {
        return "ReadScreenNodeInfo{depth=" + this.depth + ", indexInParent=" + this.indexInParent + ", boundsInScreen=" + this.boundsInScreen + ", centerInScreen=" + this.centerInScreen + ", width=" + this.width + ", height=" + this.height + ", packageName='" + this.packageName + "', className='" + this.className + "', text='" + this.text + "', desc='" + this.desc + "', hintText='" + this.hintText + "', tooltipText='" + this.tooltipText + "', paneTitle='" + this.paneTitle + "', roleDesc='" + this.roleDesc + "', focused=" + this.focused + '}';
    }
}
