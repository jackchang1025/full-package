package com.vendor.rat.auto.condition;

import com.vendor.rat.auto.entity.Point;
import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.List;

/**
 * Vendor: com/guard/wallet/condition/GlobalActionCondition.java (87行)
 * 全局动作条件 - 纯数据类
 */
public class GlobalActionCondition implements Serializable {

    private String actionName;
    private Long duration;
    private List<Point> points;
    private Integer repeatCount;
    private Long start;
    private ActionValueCondition value;
    private Point point;
    private int target;

    public GlobalActionCondition() {
        this.actionName = "click";
        this.start = 10L;
        this.duration = 100L;
    }

    public GlobalActionCondition(String actionName, List<Point> points,
                                  Long start, Long duration,
                                  Integer repeatCount,
                                  ActionValueCondition value) {
        this.actionName = "click";
        this.start = 10L;
        this.actionName = actionName;
        this.points = points;
        this.start = start;
        this.duration = duration;
        this.repeatCount = repeatCount;
        this.value = value;
    }

    public String getActionName() { return this.actionName; }
    public Long getDuration() { return this.duration; }
    public List<Point> getPoints() { return this.points; }
    public Integer getRepeatCount() { return this.repeatCount; }
    public Long getStart() { return this.start; }
    public ActionValueCondition getValue() { return this.value; }

    public void setActionName(String actionName) { this.actionName = actionName; }
    public void setDuration(Long duration) { this.duration = duration; }
    public void setPoints(List<Point> points) { this.points = points; }
    public void setRepeatCount(Integer repeatCount) { this.repeatCount = repeatCount; }
    public void setStart(Long start) { this.start = start; }
    public void setValue(ActionValueCondition value) { this.value = value; }
    public Point getPoint() { return this.point; }
    public void setPoint(Point point) { this.point = point; }
    public int getTarget() { return this.target; }
    public void setTarget(int target) { this.target = target; }

    @NonNull
    public String toString() {
        return "GlobalActionCondition{actionName='" + this.actionName
                + "', points=" + this.points
                + ", start=" + this.start
                + ", duration=" + this.duration
                + ", repeatCount=" + this.repeatCount
                + ", value=" + this.value + '}';
    }
}
