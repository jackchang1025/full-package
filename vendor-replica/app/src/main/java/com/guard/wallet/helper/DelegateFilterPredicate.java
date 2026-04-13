package com.guard.wallet.helper;

import android.util.Log;
import java.util.concurrent.Future;
import java.util.function.Predicate;

/**
 * 委托过滤谓词。
 *
 * <p>根据类型码执行不同过滤操作：
 * case 0 — 回收 AccessibilityNodeInfo；
 * case 1/default — 取消 Future 任务。</p>
 *
 * <p>vendor 原始类: {@code com.guard.wallet.helper.b}</p>
 */
public final class DelegateFilterPredicate implements Predicate<Object> {
    public final int a;

    public DelegateFilterPredicate(int a) { this.a = a; }

    @Override
    public final boolean test(Object obj) {
        switch (a) {
            case 0:
                // recycle AccessibilityNodeInfo
                if (obj != null) {
                    try {
                        obj.getClass().getMethod("recycle").invoke(obj);
                    } catch (Exception ignored) {}
                }
                return true;
            case 1:
                return cancelFuture((Future<?>) obj);
            default:
                return cancelFuture((Future<?>) obj);
        }
    }

    private boolean cancelFuture(Future<?> future) {
        try {
            future.cancel(true);
        } catch (Exception e) {
            Log.e("DelegateTaskLauncher", "cancel error", e);
        }
        return true;
    }
}
