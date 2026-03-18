package com.vendor.rat.auto.entity;

import androidx.annotation.NonNull;
import java.io.Serializable;

/**
 * Vendor: com/guard/wallet/entity/DistanceTouchNode.java (39行)
 * ADAPT: UiObject → UiNode
 */
public class DistanceTouchNode implements Serializable {

    private double distance;
    private UiNode touchNode;

    public DistanceTouchNode() {
    }

    public DistanceTouchNode(UiNode touchNode, double distance) {
        this.touchNode = touchNode;
        this.distance = distance;
    }

    public double getDistance() { return this.distance; }
    public UiNode getTouchNode() { return this.touchNode; }

    public void setDistance(double distance) { this.distance = distance; }
    public void setTouchNode(UiNode touchNode) { this.touchNode = touchNode; }

    @NonNull
    public String toString() {
        return "DistanceTouchNode{touchNode=" + this.touchNode
                + ", distance=" + this.distance + '}';
    }
}
