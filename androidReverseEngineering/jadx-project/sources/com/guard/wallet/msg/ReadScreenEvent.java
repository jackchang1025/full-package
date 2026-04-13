package com.guard.wallet.msg;

import android.support.annotation.NonNull;
import com.guard.wallet.entity.Point;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes.dex */
public class ReadScreenEvent extends BaseMsgBody {
    private int eventType;
    private List<Point> points;

    public ReadScreenEvent(int i2) {
        this.points = new LinkedList();
        this.eventType = i2;
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

    public ReadScreenEvent(int i2, List<Point> list) {
        new LinkedList();
        this.eventType = i2;
        this.points = list;
    }
}
