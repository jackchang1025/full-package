package com.vendor.rat.auto.entity;

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Vendor: com/guard/wallet/entity/Point.java (59行)
 */
public class Point implements Serializable {

    private float x;
    private float y;

    public Point() {
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Point point = (Point) obj;
        return Float.compare(point.x, this.x) == 0
                && Float.compare(point.y, this.y) == 0;
    }

    public float getX() { return this.x; }
    public float getY() { return this.y; }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.x), Float.valueOf(this.y));
    }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }

    @NonNull
    public String toString() {
        return "Point{x=" + this.x + ", y=" + this.y + '}';
    }
}
