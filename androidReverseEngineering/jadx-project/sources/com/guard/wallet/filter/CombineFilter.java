package com.guard.wallet.filter;

import a1.AbstractC0026q;
import android.support.annotation.NonNull;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.BoundsCondition;
import com.guard.wallet.condition.IntCondition;
import com.guard.wallet.condition.PointCondition;
import com.guard.wallet.condition.StringCondition;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import p008k.C0356a;

/* loaded from: classes.dex */
public class CombineFilter {
    private static final String TAG = "CombineFilter";
    private List<BoolCondition> boolConditions;
    private List<BoundsCondition> boundsConditions;
    private String delegateId;
    private List<IntCondition> intConditions;
    private List<PointCondition> pointConditions;
    private int repeatCount;
    private String resUnique;
    private List<StringCondition> stringConditions;
    private int target;

    public CombineFilter() {
        this.target = 0;
        this.repeatCount = 0;
    }

    public void destroy() {
        try {
            List<BoolCondition> list = this.boolConditions;
            if (list != null) {
                list.clear();
                this.boolConditions = null;
            }
            List<BoundsCondition> list2 = this.boundsConditions;
            if (list2 != null) {
                list2.clear();
                this.boundsConditions = null;
            }
            List<IntCondition> list3 = this.intConditions;
            if (list3 != null) {
                list3.clear();
                this.intConditions = null;
            }
            List<StringCondition> list4 = this.stringConditions;
            if (list4 != null) {
                list4.clear();
                this.stringConditions = null;
            }
            List<PointCondition> list5 = this.pointConditions;
            if (list5 != null) {
                list5.clear();
                this.pointConditions = null;
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
        }
    }

    public List<BoolCondition> getBoolConditions() {
        return this.boolConditions;
    }

    public List<BoundsCondition> getBoundsConditions() {
        return this.boundsConditions;
    }

    public String getDelegateId() {
        return this.delegateId;
    }

    public List<IntCondition> getIntConditions() {
        return this.intConditions;
    }

    public List<PointCondition> getPointConditions() {
        return this.pointConditions;
    }

    public Integer getRepeatCount() {
        return Integer.valueOf(this.repeatCount);
    }

    public String getResUnique() {
        return this.resUnique;
    }

    public List<StringCondition> getStringConditions() {
        return this.stringConditions;
    }

    public int getTarget() {
        return this.target;
    }

    public void setBoolConditions(List<BoolCondition> list) {
        this.boolConditions = list;
    }

    public void setBoundsConditions(List<BoundsCondition> list) {
        this.boundsConditions = list;
    }

    public void setDelegateId(String str) {
        this.delegateId = str;
    }

    public void setIntConditions(List<IntCondition> list) {
        this.intConditions = list;
    }

    public void setPointConditions(List<PointCondition> list) {
        this.pointConditions = list;
    }

    public void setRepeatCount(Integer num) {
        this.repeatCount = num.intValue();
    }

    public void setResUnique(String str) {
        this.resUnique = str;
    }

    public void setStringConditions(List<StringCondition> list) {
        this.stringConditions = list;
    }

    public void setTarget(int i2) {
        this.target = i2;
    }

    public C0356a toGlobalSelector(String str) {
        BoundsFilter boundsFilter;
        PointFilter pointFilter;
        C0356a c0356a = new C0356a();
        try {
            List<BoolCondition> list = this.boolConditions;
            if (list != null && !list.isEmpty()) {
                Iterator<BoolCondition> it = this.boolConditions.iterator();
                while (it.hasNext()) {
                    c0356a = c0356a.m913c(it.next());
                }
            }
            List<IntCondition> list2 = this.intConditions;
            if (list2 != null && !list2.isEmpty()) {
                Iterator<IntCondition> it2 = this.intConditions.iterator();
                while (it2.hasNext()) {
                    c0356a.m936z(it2.next());
                }
            }
            List<StringCondition> list3 = this.stringConditions;
            if (list3 != null && !list3.isEmpty()) {
                for (StringCondition stringCondition : this.stringConditions) {
                    if (stringCondition != null && !AbstractC0026q.m151B(stringCondition.getProperty())) {
                        if (Objects.equals(stringCondition.getProperty(), "WINDOW_TITLE")) {
                            c0356a.m910W(str, stringCondition);
                        } else {
                            c0356a = c0356a.m893F(stringCondition);
                        }
                    }
                }
            }
            List<PointCondition> list4 = this.pointConditions;
            if (list4 != null && !list4.isEmpty()) {
                for (PointCondition pointCondition : this.pointConditions) {
                    if (pointCondition != null && (pointFilter = pointCondition.toPointFilter()) != null) {
                        c0356a.m911a(pointFilter);
                    }
                }
            }
            List<BoundsCondition> list5 = this.boundsConditions;
            if (list5 != null && !list5.isEmpty()) {
                for (BoundsCondition boundsCondition : this.boundsConditions) {
                    if (boundsCondition != null && (boundsFilter = boundsCondition.toBoundsFilter()) != null) {
                        c0356a.m911a(boundsFilter);
                    }
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
        }
        return c0356a;
    }

    @NonNull
    public String toString() {
        return "CombineFilter{resUnique='" + this.resUnique + "', delegateId=" + this.delegateId + ", target=" + this.target + ", boolConditions=" + this.boolConditions + ", boundsConditions=" + this.boundsConditions + ", intConditions=" + this.intConditions + ", stringConditions=" + this.stringConditions + ", pointConditions=" + this.pointConditions + ", repeatCount=" + this.repeatCount + '}';
    }

    public CombineFilter(String str, String str2, int i2, List<BoolCondition> list, List<BoundsCondition> list2, List<IntCondition> list3, List<StringCondition> list4, List<PointCondition> list5, Integer num) {
        this.repeatCount = 0;
        this.delegateId = str;
        this.resUnique = str2;
        this.target = i2;
        this.boolConditions = list;
        this.boundsConditions = list2;
        this.intConditions = list3;
        this.stringConditions = list4;
        this.pointConditions = list5;
        this.repeatCount = num.intValue();
    }
}
