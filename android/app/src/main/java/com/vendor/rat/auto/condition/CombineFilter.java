package com.vendor.rat.auto.condition;

import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.filter.NodeFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合过滤器 (模块 04)
 *
 * 支持 AND / OR 多条件组合
 * 提供常用控件的便捷构建方法
 *
 * 基于逆向分析: com/guard/wallet/filter/CombineFilter.java
 */
public class CombineFilter implements NodeFilter {

    public enum Logic { AND, OR }

    private final List<NodeFilter> filters = new ArrayList<>();
    private final Logic logic;

    public CombineFilter() {
        this(Logic.AND);
    }

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

    // ============ 便捷构建: 逻辑组合 ============

    /**
     * AND 组合
     */
    public static CombineFilter and(NodeFilter... filters) {
        CombineFilter combine = new CombineFilter(Logic.AND);
        for (NodeFilter f : filters) combine.add(f);
        return combine;
    }

    /**
     * OR 组合
     */
    public static CombineFilter or(NodeFilter... filters) {
        CombineFilter combine = new CombineFilter(Logic.OR);
        for (NodeFilter f : filters) combine.add(f);
        return combine;
    }

    // ============ 便捷构建: 常用控件 ============

    /**
     * 匹配 TextView + 文本包含
     * 基于逆向: a.a.c(combineFilter, "className", "android.widget.TextView") + text
     */
    public static CombineFilter textView(String text) {
        return and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textContains(text)
        );
    }

    /**
     * 匹配 TextView + 文本精确
     */
    public static CombineFilter textViewExact(String text) {
        return and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textEquals(text)
        );
    }

    /**
     * 匹配 Button + 文本
     * 基于逆向: className=android.widget.Button + text
     */
    public static CombineFilter button(String text) {
        return and(
            StringCondition.className("android.widget.Button"),
            StringCondition.textContains(text)
        );
    }

    /**
     * 匹配 Switch 控件
     */
    public static CombineFilter switchWidget() {
        return and(
            StringCondition.className("android.widget.Switch")
        );
    }

    /**
     * 匹配 CheckBox 控件
     */
    public static CombineFilter checkBox() {
        return and(
            StringCondition.className("android.widget.CheckBox")
        );
    }

    /**
     * 匹配可点击节点
     */
    public static CombineFilter clickable() {
        return and(
            new BoolCondition(BoolCondition.Property.CLICKABLE, true)
        );
    }

    /**
     * 匹配可滚动节点
     */
    public static CombineFilter scrollable() {
        return and(
            new BoolCondition(BoolCondition.Property.SCROLLABLE, true)
        );
    }

    /**
     * 匹配可选中 + 文本包含
     */
    public static CombineFilter checkableWithText(String text) {
        return and(
            new BoolCondition(BoolCondition.Property.CHECKABLE, true),
            StringCondition.textContains(text)
        );
    }

    /**
     * 匹配 ScrollView
     */
    public static CombineFilter scrollView() {
        return or(
            StringCondition.className("android.widget.ScrollView"),
            StringCondition.className("android.widget.ListView"),
            StringCondition.className("androidx.recyclerview.widget.RecyclerView")
        );
    }

    // ============ Lifecycle ============

    /**
     * Vendor: CombineFilter.destroy() - clears all filters
     */
    public void destroy() {
        filters.clear();
    }

    /**
     * Vendor: CombineFilter.toGlobalSelector(Object) -> obfuscated k.a
     * TODO: VENDOR_VERIFY - returns Object since vendor type is obfuscated
     */
    public Object toGlobalSelector(Object param) {
        return null;
    }

    // ============ Getters ============

    public List<NodeFilter> getFilters() { return filters; }
    public Logic getLogic() { return logic; }
}
