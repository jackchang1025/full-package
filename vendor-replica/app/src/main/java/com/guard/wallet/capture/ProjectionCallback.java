package com.guard.wallet.capture;

import android.media.projection.MediaProjection.Callback;

/**
 * MediaProjection 回调 -- 内容缩放/可见性/停止事件。
 *
 * vendor 原始路径: x/c.java (23 行)
 */
public final class ProjectionCallback extends Callback {
    @Override
    public final void onCapturedContentResize(int width, int height) {
        super.onCapturedContentResize(width, height);
    }

    @Override
    public final void onCapturedContentVisibilityChanged(boolean isVisible) {
        super.onCapturedContentVisibilityChanged(isVisible);
    }

    @Override
    public final void onStop() {
        super.onStop();
    }
}
