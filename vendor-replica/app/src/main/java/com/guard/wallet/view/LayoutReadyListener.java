/**
 * OnGlobalLayoutListener 实现，布局完成后回调视图工具方法。
 * vendor 原始路径: e0/h.java
 */
package com.guard.wallet.view;

import android.view.View;
import android.view.ViewTreeObserver;

public final class LayoutReadyListener implements ViewTreeObserver.OnGlobalLayoutListener {
    public final View targetView;

    public LayoutReadyListener(View targetView) {
        this.targetView = targetView;
    }

    @Override
    public final void onGlobalLayout() {
        com.guard.wallet.utils.WindowUtils.setSkipScreenshot(this.targetView);
    }
}
