package com.guard.wallet.entity;

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.Objects;

public class Point implements Serializable {
    private float x;
    private float y;

    public Point() {
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Point other = (Point) obj;
        return Float.compare(other.x, this.x) == 0 && Float.compare(other.y, this.y) == 0;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    @NonNull
    @Override
    public String toString() {
        return "Point{x=" + this.x + ", y=" + this.y + '}';
    }
}
