package com.guard.wallet.uisearch;

import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilterWithChild;

/**
 * 基于 CombineFilterWithChild 的滚动搜索条件。
 *
 * 根据 mode 切换 withChild / withoutChild 搜索模式：
 * - mode=0: withChild (findByCombineWithChild / findOneByCombineWithChild)
 * - mode=1: withoutChild (findByCombineWithoutChild / findOneByCombineWithoutChild)
 *
 * 固定最大滚动次数 20。
 *
 * vendor 原始路径: z/c.java (50 行)
 * vendor 字段映射: a -> mode, b -> filter
 */
public final class ChildScrollCondition implements SingleScrollCondition, MultiScrollCondition {

    /** 搜索模式: 0=withChild, 1=withoutChild */
    public final int mode;

    /** 子节点过滤条件 */
    public final CombineFilterWithChild filter;

    public ChildScrollCondition(int mode, CombineFilterWithChild filter) {
        this.mode = mode;
        this.filter = filter;
    }

    @Override
    public final int scrollCount() {
        return 20;
    }

    @Override
    public final UiObjectCollection evaluateMultiple(UiObject root) {
        switch (mode) {
            case 0:
                return root.findByCombineWithChild(filter);
            default:
                return root.findByCombineWithoutChild(filter);
        }
    }

    @Override
    public final UiObject evaluateSingle(UiObject root) {
        switch (mode) {
            case 0:
                return root.findOneByCombineWithChild(filter);
            default:
                return root.findOneByCombineWithoutChild(filter);
        }
    }
}
