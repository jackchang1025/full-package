package com.guard.wallet.condition;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import com.guard.wallet.filter.BoundsFilter;
import java.io.Serializable;

/* loaded from: classes.dex */
public class BoundsCondition implements Serializable {
    private int bottom;
    private int filterType;
    private int left;
    private int right;
    private int top;

    public BoundsCondition() {
    }

    public BoundsCondition(int i2, int i3, int i4, int i5, int i6) {
        this.left = i2;
        this.top = i3;
        this.right = i4;
        this.bottom = i5;
        this.filterType = i6;
    }

    public int getBottom() {
        return this.bottom;
    }

    public int getFilterType() {
        return this.filterType;
    }

    public int getLeft() {
        return this.left;
    }

    public int getRight() {
        return this.right;
    }

    public int getTop() {
        return this.top;
    }

    public void setBottom(int i2) {
        this.bottom = i2;
    }

    public void setFilterType(int i2) {
        this.filterType = i2;
    }

    public void setLeft(int i2) {
        this.left = i2;
    }

    public void setRight(int i2) {
        this.right = i2;
    }

    public void setTop(int i2) {
        this.top = i2;
    }

    public BoundsFilter toBoundsFilter() {
        int i2 = this.filterType;
        if (i2 == 0 || i2 == 1 || i2 == 2) {
            return new BoundsFilter(new Rect(this.left, this.top, this.right, this.bottom), this.filterType);
        }
        return null;
    }

    @NonNull
    public String toString() {
        return "BoundsCondition{left=" + this.left + ", top=" + this.top + ", right=" + this.right + ", bottom=" + this.bottom + ", filterType=" + this.filterType + '}';
    }
}
