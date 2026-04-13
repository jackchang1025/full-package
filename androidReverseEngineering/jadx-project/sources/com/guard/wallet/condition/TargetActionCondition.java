package com.guard.wallet.condition;

import a1.AbstractC0026q;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.Point;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class TargetActionCondition implements Serializable {
    private static final String TAG = "com.guard.wallet.condition.TargetActionCondition";
    private String actionName;
    private Integer actionType;
    private String delegateId;
    private String resUnique;
    private int target;
    private List<ActionValueCondition> values;

    public TargetActionCondition() {
        this.target = 0;
        this.actionName = "click";
    }

    public void destroy() {
        try {
            List<ActionValueCondition> list = this.values;
            if (list != null) {
                list.clear();
                this.values = null;
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
        }
    }

    public String getActionName() {
        return this.actionName;
    }

    public Integer getActionType() {
        return this.actionType;
    }

    public String getDelegateId() {
        return this.delegateId;
    }

    public String getResUnique() {
        return this.resUnique;
    }

    public int getTarget() {
        return this.target;
    }

    public List<ActionValueCondition> getValues() {
        return this.values;
    }

    public void setActionName(String str) {
        this.actionName = str;
    }

    public void setActionType(Integer num) {
        this.actionType = num;
    }

    public void setDelegateId(String str) {
        this.delegateId = str;
    }

    public void setResUnique(String str) {
        this.resUnique = str;
    }

    public void setTarget(int i2) {
        this.target = i2;
    }

    public void setValues(List<ActionValueCondition> list) {
        this.values = list;
    }

    public GlobalActionCondition toGlobalActionCondition() {
        List m695P;
        Point m694O;
        GlobalActionCondition globalActionCondition = new GlobalActionCondition();
        globalActionCondition.setActionName(this.actionName);
        List<ActionValueCondition> list = this.values;
        if (list != null && !list.isEmpty()) {
            Point point = null;
            for (ActionValueCondition actionValueCondition : this.values) {
                if (actionValueCondition != null) {
                    String key = actionValueCondition.getKey();
                    key.getClass();
                    switch (key) {
                        case "duration":
                            if (AbstractC0026q.m153D(actionValueCondition.getValue())) {
                                globalActionCondition.setDuration(Long.valueOf(Long.parseLong(actionValueCondition.getValue())));
                                break;
                            } else {
                                break;
                            }
                        case "points":
                            if (Objects.equals(actionValueCondition.getType(), "ObjectArray") && !AbstractC0026q.m151B(actionValueCondition.getValue()) && (m695P = AbstractC0252h.m695P(actionValueCondition.getValue())) != null && !m695P.isEmpty()) {
                                if (globalActionCondition.getPoints() == null) {
                                    globalActionCondition.setPoints(new LinkedList());
                                }
                                globalActionCondition.getPoints().addAll(m695P);
                                break;
                            }
                            break;
                        case "x":
                            if (AbstractC0026q.m153D(actionValueCondition.getValue())) {
                                int parseInt = Integer.parseInt(actionValueCondition.getValue());
                                if (point == null) {
                                    point = new Point();
                                }
                                point.setX(parseInt);
                                break;
                            } else {
                                break;
                            }
                        case "y":
                            if (AbstractC0026q.m153D(actionValueCondition.getValue())) {
                                int parseInt2 = Integer.parseInt(actionValueCondition.getValue());
                                if (point == null) {
                                    point = new Point();
                                }
                                point.setY(parseInt2);
                                break;
                            } else {
                                break;
                            }
                        case "point":
                            if (Objects.equals(actionValueCondition.getType(), "Object") && !AbstractC0026q.m151B(actionValueCondition.getValue()) && (m694O = AbstractC0252h.m694O(actionValueCondition.getValue())) != null) {
                                if (globalActionCondition.getPoints() == null) {
                                    globalActionCondition.setPoints(new LinkedList());
                                }
                                globalActionCondition.getPoints().add(m694O);
                                break;
                            }
                            break;
                        case "start":
                            if (AbstractC0026q.m153D(actionValueCondition.getValue())) {
                                globalActionCondition.setStart(Long.valueOf(Long.parseLong(actionValueCondition.getValue())));
                                break;
                            } else {
                                break;
                            }
                        case "repeatCount":
                            if (AbstractC0026q.m153D(actionValueCondition.getValue())) {
                                globalActionCondition.setRepeatCount(Integer.valueOf(Integer.parseInt(actionValueCondition.getValue())));
                                break;
                            } else {
                                break;
                            }
                        default:
                            if (!AbstractC0026q.m151B(actionValueCondition.getValue()) && globalActionCondition.getValue() == null) {
                                globalActionCondition.setValue(actionValueCondition);
                                break;
                            }
                            break;
                    }
                }
            }
            if (point != null && point.getX() >= 0.0f && point.getY() >= 0.0f) {
                if (globalActionCondition.getPoints() == null) {
                    globalActionCondition.setPoints(new LinkedList());
                }
                globalActionCondition.getPoints().add(point);
            }
        }
        return globalActionCondition;
    }

    @NonNull
    public String toString() {
        return "TargetActionCondition{delegateId='" + this.delegateId + "', resUnique='" + this.resUnique + "', target=" + this.target + ", actionType='" + this.actionType + "', actionName='" + this.actionName + "', values=" + this.values + '}';
    }

    public TargetActionCondition(String str, String str2, int i2, Integer num, String str3, List<ActionValueCondition> list) {
        this.delegateId = str;
        this.resUnique = str2;
        this.target = i2;
        this.actionType = num;
        this.actionName = str3;
        this.values = list;
    }
}
