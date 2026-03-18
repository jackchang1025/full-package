package com.vendor.rat.control.plug;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * 按 targetIndex 过滤 ListenPropResponse
 * vendor: com.guard.wallet.plug.e
 */
public final class TargetIndexPredicate implements Predicate<Object> {

    private final int targetIndex;

    public TargetIndexPredicate(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public boolean test(Object obj) {
        // ADAPT: vendor 使用 ListenPropResponse，此处保留结构但依赖尚未复刻的 VO
        // TODO: VENDOR_VERIFY - 需要 ListenPropResponse.getTargetIndex()
        return false;
    }
}
