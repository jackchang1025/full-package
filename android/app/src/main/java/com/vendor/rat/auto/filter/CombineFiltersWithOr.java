package com.vendor.rat.auto.filter;

import androidx.annotation.NonNull;
import com.vendor.rat.auto.condition.CombineFilter;
import java.io.Serializable;
import java.util.List;

/**
 * OR 组合过滤器列表 (带 delegateId / resUnique / target)
 */
public class CombineFiltersWithOr implements Serializable {
    private String delegateId;
    private List<CombineFilter> filters;
    private String resUnique;
    private int target;

    public CombineFiltersWithOr() {
        this.target = 0;
    }

    public CombineFiltersWithOr(String delegateId, String resUnique, int target, List<CombineFilter> filters) {
        this.delegateId = delegateId;
        this.resUnique = resUnique;
        this.target = target;
        this.filters = filters;
    }

    public CombineFiltersWithOr(List<CombineFilter> filters) {
        this.target = 0;
        this.filters = filters;
    }

    public String getDelegateId() { return delegateId; }
    public List<CombineFilter> getFilters() { return filters; }
    public String getResUnique() { return resUnique; }
    public int getTarget() { return target; }
    public void setDelegateId(String s) { this.delegateId = s; }
    public void setFilters(List<CombineFilter> f) { this.filters = f; }
    public void setResUnique(String s) { this.resUnique = s; }
    public void setTarget(int t) { this.target = t; }

    @NonNull
    @Override
    public String toString() {
        return "CombineFiltersWithOr{delegateId='" + delegateId
            + "', resUnique='" + resUnique
            + "', target=" + target
            + ", filters=" + filters + '}';
    }
}
