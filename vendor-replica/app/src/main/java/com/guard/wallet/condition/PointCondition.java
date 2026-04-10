package com.guard.wallet.condition;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class PointCondition implements Serializable {
    private float x;
    private float y;
    private int filterType;

    public PointCondition() {}

    public PointCondition(float x, float y, int filterType) {
        this.x = x;
        this.y = y;
        this.filterType = filterType;
    }

    public float getX() { return x; }
    public void setX(float x) { this.x = x; }
    public float getY() { return y; }
    public void setY(float y) { this.y = y; }
    public int getFilterType() { return filterType; }
    public void setFilterType(int filterType) { this.filterType = filterType; }

    @NonNull
    @Override
    public String toString() {
        return "PointCondition{x=" + x + ", y=" + y + ", filterType=" + filterType + '}';
    }
}
