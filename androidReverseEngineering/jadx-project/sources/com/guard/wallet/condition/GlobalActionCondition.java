package com.guard.wallet.condition;

import android.support.annotation.NonNull;
import com.guard.wallet.entity.Point;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes.dex */
public class GlobalActionCondition implements Serializable {
    private String actionName;
    private Long duration;
    private List<Point> points;
    private Integer repeatCount;
    private Long start;
    private ActionValueCondition value;

    public GlobalActionCondition() {
        this.actionName = "click";
        this.start = 10L;
        this.duration = 100L;
    }

    public String getActionName() {
        return this.actionName;
    }

    public Long getDuration() {
        return this.duration;
    }

    public List<Point> getPoints() {
        return this.points;
    }

    public Integer getRepeatCount() {
        return this.repeatCount;
    }

    public Long getStart() {
        return this.start;
    }

    public ActionValueCondition getValue() {
        return this.value;
    }

    public void setActionName(String str) {
        this.actionName = str;
    }

    public void setDuration(Long l2) {
        this.duration = l2;
    }

    public void setPoints(List<Point> list) {
        this.points = list;
    }

    public void setRepeatCount(Integer num) {
        this.repeatCount = num;
    }

    public void setStart(Long l2) {
        this.start = l2;
    }

    public void setValue(ActionValueCondition actionValueCondition) {
        this.value = actionValueCondition;
    }

    @NonNull
    public String toString() {
        return "GlobalActionCondition{actionName='" + this.actionName + "', points=" + this.points + ", start=" + this.start + ", duration=" + this.duration + ", repeatCount=" + this.repeatCount + ", value=" + this.value + '}';
    }

    public GlobalActionCondition(String str, List<Point> list, Long l2, Long l3, Integer num, ActionValueCondition actionValueCondition) {
        this.actionName = "click";
        this.start = 10L;
        this.actionName = str;
        this.points = list;
        this.start = l2;
        this.duration = l3;
        this.repeatCount = num;
        this.value = actionValueCondition;
    }
}
