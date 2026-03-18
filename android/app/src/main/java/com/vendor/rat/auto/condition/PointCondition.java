package com.vendor.rat.auto.condition;

import androidx.annotation.NonNull;
import java.io.Serializable;

/**
 * Vendor: com/guard/wallet/condition/PointCondition.java (59行)
 * 坐标点条件 - 纯数据类
 */
public class PointCondition implements Serializable {

    private int filterType;
    private float x;
    private float y;

    public PointCondition() {
    }

    public PointCondition(float x, float y, int filterType) {
        this.x = x;
        this.y = y;
        this.filterType = filterType;
    }

    public int getFilterType() { return this.filterType; }
    public float getX() { return this.x; }
    public float getY() { return this.y; }

    public void setFilterType(int filterType) { this.filterType = filterType; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }

    @NonNull
    public String toString() {
        return "PointCondition{x=" + this.x
                + ", y=" + this.y
                + ", filterType=" + this.filterType + '}';
    }
}
