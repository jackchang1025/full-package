package com.vendor.rat.auto.filter;

import androidx.annotation.NonNull;
import com.vendor.rat.auto.condition.CombineFilter;
import java.io.Serializable;

/**
 * 向上层级 + 子过滤器组合
 */
public class CombineFilterWithUpLevel implements Serializable {
    private CombineFilter childFilter;
    private Integer upLevel;

    public CombineFilterWithUpLevel() {
    }

    public CombineFilterWithUpLevel(Integer upLevel, CombineFilter childFilter) {
        this.upLevel = upLevel;
        this.childFilter = childFilter;
    }

    public CombineFilter getChildFilter() { return childFilter; }
    public Integer getUpLevel() { return upLevel; }
    public void setChildFilter(CombineFilter f) { this.childFilter = f; }
    public void setUpLevel(Integer level) { this.upLevel = level; }

    @NonNull
    @Override
    public String toString() {
        return "CombineFilterWithUpLevel{upLevel=" + upLevel + ", childFilter=" + childFilter + '}';
    }
}
