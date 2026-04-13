package com.guard.wallet.entity;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class DistanceTouchNode implements Serializable {
    private double distance;
    private UiObject touchNode;

    public DistanceTouchNode() {
    }

    public DistanceTouchNode(UiObject touchNode, double distance) {
        this.touchNode = touchNode;
        this.distance = distance;
    }

    public double getDistance() {
        return this.distance;
    }

    public UiObject getTouchNode() {
        return this.touchNode;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setTouchNode(UiObject touchNode) {
        this.touchNode = touchNode;
    }

    @NonNull
    @Override
    public String toString() {
        return "DistanceTouchNode{touchNode=" + this.touchNode
                + ", distance=" + this.distance + '}';
    }
}
