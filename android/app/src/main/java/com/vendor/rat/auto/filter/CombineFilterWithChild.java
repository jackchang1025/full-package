package com.vendor.rat.auto.filter;

import androidx.annotation.NonNull;
import com.vendor.rat.auto.condition.CombineFilter;
import java.io.Serializable;

/**
 * 父子组合过滤器
 * // ADAPT: vendor CombineFilter → condition.CombineFilter
 */
public class CombineFilterWithChild implements Serializable {
    private CombineFilter childFilter;
    private CombineFilter parentFilter;

    public CombineFilterWithChild() {
    }

    public CombineFilterWithChild(CombineFilter parentFilter, CombineFilter childFilter) {
        this.parentFilter = parentFilter;
        this.childFilter = childFilter;
    }

    public CombineFilter getChildFilter() { return childFilter; }
    public CombineFilter getParentFilter() { return parentFilter; }
    public void setChildFilter(CombineFilter f) { this.childFilter = f; }
    public void setParentFilter(CombineFilter f) { this.parentFilter = f; }

    @NonNull
    @Override
    public String toString() {
        return "CombineFilterWithChild{parentFilter=" + parentFilter + ", childFilter=" + childFilter + '}';
    }
}
