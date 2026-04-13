package com.guard.wallet.filter;

import android.support.annotation.NonNull;
import java.io.Serializable;

/* loaded from: classes.dex */
public class CombineFilterWithUpLevel implements Serializable {
    private CombineFilter childFilter;
    private Integer upLevel;

    public CombineFilterWithUpLevel() {
    }

    public CombineFilterWithUpLevel(Integer num, CombineFilter combineFilter) {
        this.upLevel = num;
        this.childFilter = combineFilter;
    }

    public CombineFilter getChildFilter() {
        return this.childFilter;
    }

    public Integer getUpLevel() {
        return this.upLevel;
    }

    public void setChildFilter(CombineFilter combineFilter) {
        this.childFilter = combineFilter;
    }

    public void setUpLevel(Integer num) {
        this.upLevel = num;
    }

    @NonNull
    public String toString() {
        return "CombineFilterWithUpLevel{upLevel=" + this.upLevel + ", childFilter=" + this.childFilter + '}';
    }
}
