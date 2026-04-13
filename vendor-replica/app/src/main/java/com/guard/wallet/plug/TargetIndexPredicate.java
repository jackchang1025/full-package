package com.guard.wallet.plug;

import com.guard.wallet.req.ListenPropResponse;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 目标索引过滤谓词 -- 按 targetIndex 过滤 ListenPropResponse。
 *
 * <p>仅保留 {@link ListenPropResponse#getTargetIndex()} 与指定值相等的响应。
 *
 * <p>vendor 原始路径: com/guard/wallet/plug/e.java
 */
public final class TargetIndexPredicate implements Predicate<ListenPropResponse> {

    /** 目标节点索引 */
    public final int targetIndex;

    public TargetIndexPredicate(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public final boolean test(ListenPropResponse response) {
        return Objects.equals(response.getTargetIndex(), this.targetIndex);
    }
}
