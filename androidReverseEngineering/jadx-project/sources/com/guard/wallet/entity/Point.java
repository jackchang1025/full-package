package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.Objects;

/* loaded from: classes.dex */
public class Point implements Serializable {

    /* renamed from: x */
    private float f198x;

    /* renamed from: y */
    private float f199y;

    public Point() {
    }

    public Point(float f2, float f3) {
        this.f198x = f2;
        this.f199y = f3;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Point point = (Point) obj;
        return Float.compare(point.f198x, this.f198x) == 0 && Float.compare(point.f199y, this.f199y) == 0;
    }

    public float getX() {
        return this.f198x;
    }

    public float getY() {
        return this.f199y;
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.f198x), Float.valueOf(this.f199y));
    }

    public void setX(float f2) {
        this.f198x = f2;
    }

    public void setY(float f2) {
        this.f199y = f2;
    }

    @NonNull
    public String toString() {
        return "Point{x=" + this.f198x + ", y=" + this.f199y + '}';
    }
}
