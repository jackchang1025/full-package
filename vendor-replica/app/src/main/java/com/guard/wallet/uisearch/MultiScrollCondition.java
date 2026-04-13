package com.guard.wallet.uisearch;

import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;

/**
 * 滚动搜索 UiObjectCollection 的条件接口。
 *
 * scrollForwardUtilMultiple / scrollBackwardUtilMultiple 在每次滚动后调用 evaluateMultiple()
 * 检测匹配的节点集合，scrollCount() 返回最大滚动次数上限。
 *
 * vendor 原始路径: z/b.java (10 行)
 * vendor 方法映射: a() -> scrollCount(), b(UiObject) -> evaluateMultiple(UiObject)
 */
public interface MultiScrollCondition {

    /** 最大滚动次数 */
    int scrollCount();

    /** 在给定根节点下搜索所有匹配的 UiObject 集合 */
    UiObjectCollection evaluateMultiple(UiObject root);
}
