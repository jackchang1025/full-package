package com.storm.safe.rock.auto.support;

import com.storm.safe.rock.auto.condition.BoolCondition;
import com.storm.safe.rock.auto.condition.CombineFilter;
import com.storm.safe.rock.auto.condition.StringCondition;
import com.storm.safe.rock.auto.filter.NodeFilter;

/**
 * 通用 CombineFilter 构建器 — 从 AutoEngine 提取的纯静态方法
 * 对齐 vendor o/c.java a0(), K(), L(), U(), V(), H()
 */
public final class FilterBuilders {

    private FilterBuilders() {}

    /**
     * Switch 过滤器
     * 对应 vendor: o/c.java a0() 行 451-455
     */
    public static CombineFilter switchWidget() {
        return CombineFilter.switchWidget();
    }

    /**
     * clickable LinearLayout 过滤器
     * 对应 vendor: o/c.java K() 行 74-80
     */
    public static CombineFilter clickableLinearLayout() {
        return CombineFilter.and(
                StringCondition.className("android.widget.LinearLayout"),
                new BoolCondition(BoolCondition.Property.CLICKABLE, true)
        );
    }

    /**
     * clickable 任意控件过滤器
     * 对应 vendor: o/c.java L() 行 82-87
     */
    public static CombineFilter clickable() {
        return CombineFilter.clickable();
    }

    /**
     * LinearLayout 过滤器 (注意: 不是 scrollable!)
     * 对应 vendor: o/c.java U() 行 384-388
     */
    public static CombineFilter linearLayout() {
        return CombineFilter.and(
                StringCondition.className("android.widget.LinearLayout")
        );
    }

    /**
     * scrollable OR 过滤器 (RecyclerView/ListView/ScrollView/任意scrollable)
     * 对应 vendor: o/c.java V() 行 390-429
     * ADAPT: vendor 返回 CombineFiltersWithOr, replica 返回 NodeFilter[]
     */
    public static NodeFilter[] scrollableOrFilters() {
        // Filter1: RecyclerView + scrollable
        NodeFilter f1 = CombineFilter.and(
                StringCondition.className("androidx.recyclerview.widget.RecyclerView"),
                new BoolCondition(BoolCondition.Property.SCROLLABLE, true)
        );
        // Filter2: ListView + scrollable
        NodeFilter f2 = CombineFilter.and(
                StringCondition.className("android.widget.ListView"),
                new BoolCondition(BoolCondition.Property.SCROLLABLE, true)
        );
        // Filter3: ScrollView + scrollable
        NodeFilter f3 = CombineFilter.and(
                StringCondition.className("android.widget.ScrollView"),
                new BoolCondition(BoolCondition.Property.SCROLLABLE, true)
        );
        // Filter4: 任意 scrollable
        NodeFilter f4 = CombineFilter.scrollable();
        return new NodeFilter[]{f1, f2, f3, f4};
    }

    /**
     * TextView + text.contains 过滤器
     * 对应 vendor: o/c.java H(String) 行 45-53
     */
    public static CombineFilter textViewContains(String text) {
        if (text == null || text.isEmpty()) return null;
        return CombineFilter.and(
                StringCondition.className("android.widget.TextView"),
                StringCondition.textContains(text)
        );
    }
}
