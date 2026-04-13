package com.guard.wallet.filter;

import android.support.annotation.NonNull;
import java.io.Serializable;

/* loaded from: classes.dex */
public class CombineFilterWithChild implements Serializable {
    private CombineFilter childFilter;
    private CombineFilter parentFilter;

    public CombineFilterWithChild() {
    }

    public CombineFilterWithChild(CombineFilter combineFilter, CombineFilter combineFilter2) {
        this.parentFilter = combineFilter;
        this.childFilter = combineFilter2;
    }

    public CombineFilter getChildFilter() {
        return this.childFilter;
    }

    public CombineFilter getParentFilter() {
        return this.parentFilter;
    }

    public void setChildFilter(CombineFilter combineFilter) {
        this.childFilter = combineFilter;
    }

    public void setParentFilter(CombineFilter combineFilter) {
        this.parentFilter = combineFilter;
    }

    @NonNull
    public String toString() {
        return "CombineFilterWithChild{parentFilter=" + this.parentFilter + ", childFilter=" + this.childFilter + '}';
    }
}
