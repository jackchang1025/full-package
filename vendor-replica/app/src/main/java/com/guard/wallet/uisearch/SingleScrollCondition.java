package com.guard.wallet.uisearch;

import com.guard.wallet.entity.UiObject;

/**
 * 滚动搜索单个 UiObject 的条件接口。
 *
 * scrollForwardUtil / scrollBackwardUtil 在每次滚动后调用 evaluateSingle() 检测目标节点，
 * scrollCount() 返回最大滚动次数上限。
 *
 * vendor 原始路径: z/a.java (9 行)
 * vendor 方法映射: a() -> scrollCount(), c(UiObject) -> evaluateSingle(UiObject)
 */
public interface SingleScrollCondition {

    /** 最大滚动次数 */
    int scrollCount();

    /** 在给定根节点下搜索单个匹配的 UiObject */
    UiObject evaluateSingle(UiObject root);
}
