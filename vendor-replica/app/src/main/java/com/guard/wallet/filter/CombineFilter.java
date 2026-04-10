package com.guard.wallet.filter;

import android.util.Log;
import androidx.annotation.NonNull;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.BoundsCondition;
import com.guard.wallet.condition.IntCondition;
import com.guard.wallet.condition.PointCondition;
import com.guard.wallet.condition.StringCondition;
import java.util.List;

public class CombineFilter {
    private static final String TAG = "CombineFilter";
    private String delegateId;
    private String resUnique;
    private int target;
    private List<BoolCondition> boolConditions;
    private List<BoundsCondition> boundsConditions;
    private List<IntCondition> intConditions;
    private List<StringCondition> stringConditions;
    private List<PointCondition> pointConditions;
    private int repeatCount;

    public CombineFilter() {
        this.target = 0;
        this.repeatCount = 0;
    }

    public CombineFilter(String delegateId, String resUnique, int target,
                          List<BoolCondition> boolConditions, List<BoundsCondition> boundsConditions,
                          List<IntCondition> intConditions, List<StringCondition> stringConditions,
                          List<PointCondition> pointConditions, Integer repeatCount) {
        this.repeatCount = 0;
        this.delegateId = delegateId;
        this.resUnique = resUnique;
        this.target = target;
        this.boolConditions = boolConditions;
        this.boundsConditions = boundsConditions;
        this.intConditions = intConditions;
        this.stringConditions = stringConditions;
        this.pointConditions = pointConditions;
        if (repeatCount != null) this.repeatCount = repeatCount;
    }

    public void destroy() {
        try {
            if (boolConditions != null) { boolConditions.clear(); boolConditions = null; }
            if (boundsConditions != null) { boundsConditions.clear(); boundsConditions = null; }
            if (intConditions != null) { intConditions.clear(); intConditions = null; }
            if (stringConditions != null) { stringConditions.clear(); stringConditions = null; }
            if (pointConditions != null) { pointConditions.clear(); pointConditions = null; }
        } catch (Exception e) {
            Log.e(TAG, "destroy error", e);
        }
    }

    // Getters and Setters
    public String getDelegateId() { return delegateId; }
    public void setDelegateId(String delegateId) { this.delegateId = delegateId; }
    public String getResUnique() { return resUnique; }
    public void setResUnique(String resUnique) { this.resUnique = resUnique; }
    public int getTarget() { return target; }
    public void setTarget(int target) { this.target = target; }
    public List<BoolCondition> getBoolConditions() { return boolConditions; }
    public void setBoolConditions(List<BoolCondition> boolConditions) { this.boolConditions = boolConditions; }
    public List<BoundsCondition> getBoundsConditions() { return boundsConditions; }
    public void setBoundsConditions(List<BoundsCondition> boundsConditions) { this.boundsConditions = boundsConditions; }
    public List<IntCondition> getIntConditions() { return intConditions; }
    public void setIntConditions(List<IntCondition> intConditions) { this.intConditions = intConditions; }
    public List<StringCondition> getStringConditions() { return stringConditions; }
    public void setStringConditions(List<StringCondition> stringConditions) { this.stringConditions = stringConditions; }
    public List<PointCondition> getPointConditions() { return pointConditions; }
    public void setPointConditions(List<PointCondition> pointConditions) { this.pointConditions = pointConditions; }
    public Integer getRepeatCount() { return repeatCount; }
    public void setRepeatCount(Integer repeatCount) { this.repeatCount = repeatCount; }

    @NonNull
    @Override
    public String toString() {
        return "CombineFilter{resUnique='" + resUnique
                + "', delegateId=" + delegateId
                + ", target=" + target
                + ", boolConditions=" + boolConditions
                + ", boundsConditions=" + boundsConditions
                + ", intConditions=" + intConditions
                + ", stringConditions=" + stringConditions
                + ", pointConditions=" + pointConditions
                + ", repeatCount=" + repeatCount + '}';
    }
}
