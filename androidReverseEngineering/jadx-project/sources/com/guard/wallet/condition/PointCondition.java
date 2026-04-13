package com.guard.wallet.condition;

import android.support.annotation.NonNull;
import com.guard.wallet.entity.Point;
import com.guard.wallet.filter.PointFilter;
import java.io.Serializable;

/* loaded from: classes.dex */
public class PointCondition implements Serializable {
    private int filterType;

    /* renamed from: x */
    private float f196x;

    /* renamed from: y */
    private float f197y;

    public PointCondition() {
    }

    public PointCondition(float f2, float f3, int i2) {
        this.f196x = f2;
        this.f197y = f3;
        this.filterType = i2;
    }

    public int getFilterType() {
        return this.filterType;
    }

    public float getX() {
        return this.f196x;
    }

    public float getY() {
        return this.f197y;
    }

    public void setFilterType(int i2) {
        this.filterType = i2;
    }

    public void setX(float f2) {
        this.f196x = f2;
    }

    public void setY(float f2) {
        this.f197y = f2;
    }

    public PointFilter toPointFilter() {
        return new PointFilter(new Point(this.f196x, this.f197y), this.filterType);
    }

    @NonNull
    public String toString() {
        return "PointCondition{x=" + this.f196x + ", y=" + this.f197y + ", filterType=" + this.filterType + '}';
    }
}
