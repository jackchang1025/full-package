package com.guard.wallet.filter;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes.dex */
public class CombineFiltersWithOr implements Serializable {
    private String delegateId;
    private List<CombineFilter> filters;
    private String resUnique;
    private int target;

    public CombineFiltersWithOr() {
        this.target = 0;
    }

    public String getDelegateId() {
        return this.delegateId;
    }

    public List<CombineFilter> getFilters() {
        return this.filters;
    }

    public String getResUnique() {
        return this.resUnique;
    }

    public int getTarget() {
        return this.target;
    }

    public void setDelegateId(String str) {
        this.delegateId = str;
    }

    public void setFilters(List<CombineFilter> list) {
        this.filters = list;
    }

    public void setResUnique(String str) {
        this.resUnique = str;
    }

    public void setTarget(int i2) {
        this.target = i2;
    }

    @NonNull
    public String toString() {
        return "CombineFiltersWithOr{delegateId='" + this.delegateId + "', resUnique='" + this.resUnique + "', target=" + this.target + ", filters=" + this.filters + '}';
    }

    public CombineFiltersWithOr(String str, String str2, int i2, List<CombineFilter> list) {
        this.delegateId = str;
        this.resUnique = str2;
        this.target = i2;
        this.filters = list;
    }

    public CombineFiltersWithOr(List<CombineFilter> list) {
        this.target = 0;
        this.filters = list;
    }
}
