package com.vendor.rat.auto.condition;

import androidx.annotation.NonNull;
import java.io.Serializable;

/**
 * Vendor: com/guard/wallet/condition/BoundsCondition.java (80行)
 * 边界条件 - 纯数据类
 */
public class BoundsCondition implements Serializable {

    private int bottom;
    private int filterType;
    private int left;
    private int right;
    private int top;

    public BoundsCondition() {
    }

    public BoundsCondition(int left, int top, int right, int bottom,
                           int filterType) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.filterType = filterType;
    }

    public int getBottom() { return this.bottom; }
    public int getFilterType() { return this.filterType; }
    public int getLeft() { return this.left; }
    public int getRight() { return this.right; }
    public int getTop() { return this.top; }

    public void setBottom(int bottom) { this.bottom = bottom; }
    public void setFilterType(int filterType) { this.filterType = filterType; }
    public void setLeft(int left) { this.left = left; }
    public void setRight(int right) { this.right = right; }
    public void setTop(int top) { this.top = top; }

    @NonNull
    public String toString() {
        return "BoundsCondition{left=" + this.left
                + ", top=" + this.top
                + ", right=" + this.right
                + ", bottom=" + this.bottom
                + ", filterType=" + this.filterType + '}';
    }
}
