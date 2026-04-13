package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;

/* loaded from: classes.dex */
public class DistanceTouchNode implements Serializable {
    private double distance;
    private UiObject touchNode;

    public DistanceTouchNode() {
    }

    public DistanceTouchNode(UiObject uiObject, double d2) {
        this.touchNode = uiObject;
        this.distance = d2;
    }

    public double getDistance() {
        return this.distance;
    }

    public UiObject getTouchNode() {
        return this.touchNode;
    }

    public void setDistance(double d2) {
        this.distance = d2;
    }

    public void setTouchNode(UiObject uiObject) {
        this.touchNode = uiObject;
    }

    @NonNull
    public String toString() {
        return "DistanceTouchNode{touchNode=" + this.touchNode + ", distance=" + this.distance + '}';
    }
}
