package com.guard.wallet.condition;

import com.guard.wallet.core.AppUtils;
import androidx.annotation.NonNull;
import com.guard.wallet.entity.Point;
import com.guard.wallet.utils.SharedPrefsManager;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

    public TargetActionCondition(String delegateId, String resUnique, int target, Integer actionType,
            String actionName, List<ActionValueCondition> values) {
        this.delegateId = delegateId;
        this.resUnique = resUnique;
        this.target = target;
        this.actionType = actionType;
        this.actionName = actionName;
        this.values = values;
    }

    public void destroy() {
        try {
            List<ActionValueCondition> list = this.values;
            if (list == null) {
                return;
            }
            list.clear();
            this.values = null;
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public String getDelegateId() {
        return delegateId;
    }

    public void setDelegateId(String delegateId) {
        this.delegateId = delegateId;
    }

    public String getResUnique() {
        return resUnique;
    }

    public void setResUnique(String resUnique) {
        this.resUnique = resUnique;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public List<ActionValueCondition> getValues() {
        return values;
    }

    public void setValues(List<ActionValueCondition> values) {
        this.values = values;
    }

    /**
     * Replica 扩展：供 UiObject 等取首个参数；vendor 无此方法。
     */
    public ActionValueCondition getActionValueCondition() {
        if (values == null || values.isEmpty()) {
            return null;
        }
        for (ActionValueCondition avc : values) {
            if (avc != null) {
                return avc;
            }
        }
        return null;
    }

    public GlobalActionCondition toGlobalActionCondition() {
        GlobalActionCondition gac = new GlobalActionCondition();
        gac.setActionName(this.actionName);
        List<ActionValueCondition> list = this.values;
        if (list == null || list.isEmpty()) {
            return gac;
        }

        Iterator<ActionValueCondition> it = list.iterator();
        Point xyPoint = null;

        while (it.hasNext()) {
            ActionValueCondition avc = it.next();
            if (avc == null) {
                continue;
            }
            String key = avc.getKey();
            if (key == null) {
                continue;
            }

            int branch = -1;
            switch (key) {
                case "duration":
                    branch = 0;
                    break;
                case "points":
                    branch = 1;
                    break;
                case "x":
                    branch = 2;
                    break;
                case "y":
                    branch = 3;
                    break;
                case "point":
                    branch = 4;
                    break;
                case "start":
                    branch = 5;
                    break;
                case "repeatCount":
                    branch = 6;
                    break;
                default:
                    break;
            }

            switch (branch) {
                case 0:
                    if (AppUtils.D(avc.getValue())) {
                        gac.setDuration(Long.parseLong(avc.getValue()));
                    }
                    break;
                case 1:
                    if (Objects.equals(avc.getType(), "ObjectArray") && !AppUtils.B(avc.getValue())) {
                        List<Point> parsed = SharedPrefsManager.P(avc.getValue());
                        if (parsed != null && !parsed.isEmpty()) {
                            if (gac.getPoints() == null) {
                                gac.setPoints(new LinkedList<>());
                            }
                            gac.getPoints().addAll(parsed);
                        }
                    }
                    break;
                case 2:
                    if (AppUtils.D(avc.getValue())) {
                        int xInt = Integer.parseInt(avc.getValue());
                        Point p = xyPoint;
                        if (p == null) {
                            p = new Point();
                        }
                        p.setX((float) xInt);
                        xyPoint = p;
                    }
                    break;
                case 3:
                    if (AppUtils.D(avc.getValue())) {
                        int yInt = Integer.parseInt(avc.getValue());
                        Point p = xyPoint;
                        if (p == null) {
                            p = new Point();
                        }
                        p.setY((float) yInt);
                        xyPoint = p;
                    }
                    break;
                case 4:
                    if (Objects.equals(avc.getType(), "Object") && !AppUtils.B(avc.getValue())) {
                        Point one = SharedPrefsManager.O(avc.getValue());
                        if (one != null) {
                            if (gac.getPoints() == null) {
                                gac.setPoints(new LinkedList<>());
                            }
                            gac.getPoints().add(one);
                        }
                    }
                    break;
                case 5:
                    if (AppUtils.D(avc.getValue())) {
                        gac.setStart(Long.parseLong(avc.getValue()));
                    }
                    break;
                case 6:
                    if (AppUtils.D(avc.getValue())) {
                        gac.setRepeatCount(Integer.parseInt(avc.getValue()));
                    }
                    break;
                default:
                    if (!AppUtils.B(avc.getValue()) && gac.getValue() == null) {
                        gac.setValue(avc);
                    }
                    break;
            }
        }

        if (xyPoint != null && xyPoint.getX() >= 0.0f && xyPoint.getY() >= 0.0f) {
            if (gac.getPoints() == null) {
                gac.setPoints(new LinkedList<>());
            }
            gac.getPoints().add(xyPoint);
        }

        return gac;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("TargetActionCondition{delegateId='");
        sb.append(delegateId);
        sb.append("', resUnique='");
        sb.append(resUnique);
        sb.append("', target=");
        sb.append(target);
        sb.append(", actionType='");
        sb.append(actionType);
        sb.append("', actionName='");
        sb.append(actionName);
        sb.append("', values=");
        sb.append(values);
        sb.append('}');
        return sb.toString();
    }
}
