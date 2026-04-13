package com.guard.wallet.uisearch;

import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import java.util.ArrayList;

/**
 * 多条件评估器 — 根据构造模式分发到 CombineFilter 或 CombineFiltersWithOr 搜索。
 *
 * 模式说明：
 * - mode=0: CombineFilter 搜索 (data = CombineFilter)
 * - mode=1: CombineFiltersWithOr 搜索 (data = CombineFiltersWithOr)
 * - mode=2: CombineFiltersWithOr via ArrayList (data = ArrayList)
 * - mode=3: int 数组滚动数据模式 (data = int[10])
 *
 * vendor 原始路径: z/d.java (119 行)
 * vendor 字段映射: a -> mode, b -> scrollFlags, c -> data
 */
public final class CombineScrollCondition implements SingleScrollCondition, MultiScrollCondition {

    /** 条件模式: 0=CombineFilter, 1=CombineFiltersWithOr, 2=ArrayList, 3=int[] */
    public final int mode;

    /** 滚动标志位 / 最大滚动次数 */
    public int scrollFlags;

    /** 数据载体: CombineFilter | CombineFiltersWithOr | ArrayList | int[] */
    public final Object data;

    /** Mode 3: 滚动数据 (int[10] 数组) */
    public CombineScrollCondition() {
        this.mode = 3;
        this.data = new int[10];
    }

    /** Mode 2: CombineFiltersWithOr via ArrayList */
    public CombineScrollCondition(ArrayList var1) {
        this.mode = 2;
        this.scrollFlags = 0;
        this.data = var1;
    }

    /** Mode 0: CombineFilter */
    public CombineScrollCondition(CombineFilter var1) {
        this.mode = 0;
        this.scrollFlags = 0;
        this.data = var1;
    }

    /** Mode 1: CombineFiltersWithOr */
    public CombineScrollCondition(CombineFiltersWithOr var1) {
        this.mode = 1;
        this.scrollFlags = 0;
        this.data = var1;
    }

    /** Synthetic constructor: (Object, int) — mode=i2, scrollFlags=20 */
    public CombineScrollCondition(Object obj, int i2) {
        this.mode = i2;
        this.scrollFlags = 20;
        this.data = obj;
    }

    /** Synthetic constructor: (Object, int, int) — mode=i3, scrollFlags=i2 */
    public CombineScrollCondition(Object obj, int i2, int i3) {
        this.mode = i3;
        this.scrollFlags = i2;
        this.data = obj;
    }

    @Override
    public final int scrollCount() {
        switch (this.mode) {
            case 0:
                return this.scrollFlags;
            default:
                return this.scrollFlags;
        }
    }

    @Override
    public final UiObjectCollection evaluateMultiple(UiObject root) {
        int var2 = this.mode;
        Object var3 = this.data;
        switch (var2) {
            case 0:
                return root.findByCombine((CombineFilter) var3);
            default:
                return root.findByOperateOr((CombineFiltersWithOr) var3);
        }
    }

    @Override
    public final UiObject evaluateSingle(UiObject root) {
        int var2 = this.mode;
        Object var3 = this.data;
        switch (var2) {
            case 0:
                return root.findOneByCombine((CombineFilter) var3);
            default:
                return root.findOneByOperateOr((CombineFiltersWithOr) var3);
        }
    }

    /** Returns the scroll max-search-swipe value from int array slot 7, or 65535 if unset. */
    public final int maxSearchSwipes() {
        int var1;
        if ((this.scrollFlags & 128) != 0) {
            var1 = ((int[]) this.data)[7];
        } else {
            var1 = 65535;
        }
        return var1;
    }

    /** Sets a value in the int array at the given index, updating the bitmask. */
    public final void setScrollData(int var1, int var2) {
        if (var1 >= 0) {
            int[] var3 = (int[]) this.data;
            if (var1 < var3.length) {
                this.scrollFlags |= 1 << var1;
                var3[var1] = var2;
            }
        }
    }
}
