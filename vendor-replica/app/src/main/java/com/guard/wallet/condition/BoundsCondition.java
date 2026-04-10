package com.guard.wallet.condition;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class BoundsCondition implements Serializable {
    private int left;
    private int top;
    private int right;
    private int bottom;
    private int filterType;

    public BoundsCondition() {}

    public BoundsCondition(int left, int top, int right, int bottom, int filterType) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.filterType = filterType;
    }

    public int getLeft() { return left; }
    public void setLeft(int left) { this.left = left; }
    public int getTop() { return top; }
    public void setTop(int top) { this.top = top; }
    public int getRight() { return right; }
    public void setRight(int right) { this.right = right; }
    public int getBottom() { return bottom; }
    public void setBottom(int bottom) { this.bottom = bottom; }
    public int getFilterType() { return filterType; }
    public void setFilterType(int filterType) { this.filterType = filterType; }

    @NonNull
    @Override
    public String toString() {
        return "BoundsCondition{left=" + left + ", top=" + top + ", right=" + right
                + ", bottom=" + bottom + ", filterType=" + filterType + '}';
    }
}
