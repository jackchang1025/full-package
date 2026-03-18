package com.vendor.rat.auto.condition;

// ADAPT: vendor = com.guard.wallet.condition.TargetActionCondition (258 行)
// 一比一复刻: 数据类 + toGlobalActionCondition() + executeOn() 操作分发

import com.vendor.rat.auto.entity.Point;
import com.vendor.rat.auto.entity.UiNode;

import java.io.Serializable;
import java.util.List;

public class TargetActionCondition implements Serializable {

    private static final String TAG = "TargetActionCondition";

    private String delegateId;
    private String resUnique;
    private int target;
    private Integer actionType;
    private String actionName;
    private List<ActionValueCondition> values;

    public TargetActionCondition() {
        this.target = 0;
        this.actionName = "click";
    }

    public TargetActionCondition(String delegateId, String resUnique, int target,
                                  Integer actionType, String actionName,
                                  List<ActionValueCondition> values) {
        this.delegateId = delegateId;
        this.resUnique = resUnique;
        this.target = target;
        this.actionType = actionType;
        this.actionName = actionName;
        this.values = values;
    }

    // ============ Getters/Setters (vendor 对齐) ============

    public String getActionName() { return actionName; }
    public void setActionName(String v) { this.actionName = v; }
    public Integer getActionType() { return actionType; }
    public void setActionType(Integer v) { this.actionType = v; }
    public String getDelegateId() { return delegateId; }
    public void setDelegateId(String v) { this.delegateId = v; }
    public String getResUnique() { return resUnique; }
    public void setResUnique(String v) { this.resUnique = v; }
    public int getTarget() { return target; }
    public void setTarget(int v) { this.target = v; }
    public List<ActionValueCondition> getValues() { return values; }
    public void setValues(List<ActionValueCondition> v) { this.values = v; }

    public void destroy() {
        try {
            if (values != null) { values.clear(); values = null; }
        } catch (Exception ignored) {}
    }

    // ============ vendor: toGlobalActionCondition() (反编译不完整, 基于结构还原) ============

    public GlobalActionCondition toGlobalActionCondition() {
        GlobalActionCondition c = new GlobalActionCondition();
        c.setActionName(this.actionName);
        c.setTarget(this.target);
        if (values == null || values.isEmpty()) return c;

        Point point = null;
        for (ActionValueCondition avc : values) {
            if (avc == null || avc.getKey() == null) continue;
            String key = avc.getKey();
            String val = avc.getValue();
            switch (key) {
                case "value":
                    if (val != null && !val.isEmpty() && c.getValue() == null) c.setValue(avc);
                    break;
                case "repeatCount":
                    if (isNum(val)) c.setRepeatCount(Integer.valueOf(Integer.parseInt(val)));
                    break;
                case "start":
                    if (isNum(val)) c.setStart(Long.valueOf(Long.parseLong(val)));
                    break;
                case "duration":
                    if (isNum(val)) c.setDuration(Long.valueOf(Long.parseLong(val)));
                    break;
                case "x":
                    if (isNum(val)) { if (point == null) point = new Point(); point.setX(Integer.parseInt(val)); }
                    break;
                case "y":
                    if (isNum(val)) { if (point == null) point = new Point(); point.setY(Integer.parseInt(val)); }
                    break;
                default: break;
            }
        }
        if (point != null) c.setPoint(point);
        return c;
    }

    // ============ 执行操作 (vendor: UiObject.actionByName 30+ 种操作) ============

    public boolean executeOn(UiNode node) {
        if (node == null || actionName == null) return false;
        switch (actionName) {
            case "click":           return node.click();
            case "longClick":       return node.longClick();
            case "clickLeft":       return node.click(); // ADAPT: 简化
            case "clickRight":      return node.click(); // ADAPT: 简化
            case "scrollForward":   return node.scrollForward();
            case "scrollBackward":  return node.scrollBackward();
            case "scrollUp":        return node.scrollUp();
            case "scrollDown":      return node.scrollDown();
            case "scrollLeft":      return node.scrollLeft();
            case "scrollRight":     return node.scrollRight();
            case "focus":           return node.focus();
            case "clearFocus":      return node.clearFocus();
            case "select":          return node.select();
            case "copy":            return node.copy();
            case "cut":             return node.cut();
            case "paste":           return node.paste();
            case "dismiss":         return node.dismiss();
            case "expand":          return node.expand();
            case "collapse":        return node.collapse();
            case "show":            return node.show();
            case "contextClick":    return node.contextClick();
            case "accessibilityFocus":      return node.accessibilityFocus();
            case "clearAccessibilityFocus": return node.clearAccessibilityFocus();
            case "enter":           return node.performAction(16908362);
            case "setText":         return execSetText(node);
            case "setSelection":    return execSetSelection(node);
            case "setProgress":     return execSetProgress(node);
            case "scrollTo":        return execScrollTo(node);
            case "repeatClick":     return execRepeatClick(node);
            case "simulationScrollForward":  return node.scrollForward();
            case "simulationScrollBackward": return node.scrollBackward();
            default: return false;
        }
    }

    private boolean execSetText(UiNode n) {
        if (values == null) return false;
        for (ActionValueCondition a : values)
            if ("value".equals(a.getKey()) && a.getValue() != null) return n.setText(a.getValue());
        return false;
    }

    private boolean execSetSelection(UiNode n) {
        if (values == null) return false;
        int s = 0, e = 0;
        for (ActionValueCondition a : values) {
            if ("start".equals(a.getKey()) && isNum(a.getValue())) s = Integer.parseInt(a.getValue());
            if ("end".equals(a.getKey()) && isNum(a.getValue())) e = Integer.parseInt(a.getValue());
        }
        return n.setSelection(s, e);
    }

    private boolean execSetProgress(UiNode n) {
        if (values == null) return false;
        for (ActionValueCondition a : values)
            if ("value".equals(a.getKey()) && isNum(a.getValue())) return n.setProgress(Float.parseFloat(a.getValue()));
        return false;
    }

    private boolean execScrollTo(UiNode n) {
        if (values == null) return false;
        int r = 0, c = 0;
        for (ActionValueCondition a : values) {
            if ("row".equals(a.getKey()) && isNum(a.getValue())) r = Integer.parseInt(a.getValue());
            if ("column".equals(a.getKey()) && isNum(a.getValue())) c = Integer.parseInt(a.getValue());
        }
        return n.scrollTo(r, c);
    }

    private boolean execRepeatClick(UiNode n) {
        int count = 1;
        if (values != null) for (ActionValueCondition a : values)
            if ("repeatCount".equals(a.getKey()) && isNum(a.getValue())) count = Integer.parseInt(a.getValue());
        boolean ok = true;
        for (int i = 0; i < count; i++) {
            if (!n.click()) ok = false;
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
        }
        return ok;
    }

    private static boolean isNum(String s) {
        if (s == null || s.isEmpty()) return false;
        try { Double.parseDouble(s); return true; } catch (NumberFormatException e) { return false; }
    }

    @Override
    public String toString() {
        return "TargetActionCondition{delegateId='" + delegateId + "', resUnique='" + resUnique
                + "', target=" + target + ", actionType='" + actionType
                + "', actionName='" + actionName + "', values=" + values + '}';
    }
}
