package com.vendor.rat.auto.condition;

import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.filter.NodeFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合过滤器 (模块 04)
 *
 * 支持 AND / OR 多条件组合
 */
public class CombineFilter implements NodeFilter {

    public enum Logic { AND, OR }

    private final List<NodeFilter> filters = new ArrayList<>();
    private final Logic logic;

    public CombineFilter(Logic logic) {
        this.logic = logic;
    }

    public CombineFilter add(NodeFilter filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public boolean accept(UiNode node) {
        if (filters.isEmpty()) return true;

        if (logic == Logic.AND) {
            for (NodeFilter filter : filters) {
                if (!filter.accept(node)) return false;
            }
            return true;
        } else {
            for (NodeFilter filter : filters) {
                if (filter.accept(node)) return true;
            }
            return false;
        }
    }

    /**
     * 便捷构建: AND 组合
     */
    public static CombineFilter and(NodeFilter... filters) {
        CombineFilter combine = new CombineFilter(Logic.AND);
        for (NodeFilter f : filters) combine.add(f);
        return combine;
    }

    /**
     * 便捷构建: OR 组合
     */
    public static CombineFilter or(NodeFilter... filters) {
        CombineFilter combine = new CombineFilter(Logic.OR);
        for (NodeFilter f : filters) combine.add(f);
        return combine;
    }
}
