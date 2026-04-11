package com.guard.wallet.engine;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.delegate.EngineHelper;
import com.guard.wallet.delegate.FilterHelper;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 小米/Redmi/POCO 保活引擎。
 *
 * ADAPT: Field 'r' shadows class o.r; use KEEP_ALIVE_* constants.
 * ADAPT: Field 'c' (String) shadows class o.c; use notifyPrepareConfirmLock() directly (inherited) or EngineHelper.
 * ADAPT: Class o.z shadows package o.z; use FilterHelper for CombineScrollCondition construction.
 *
 * vendor 原始路径: o/q.java
 */
public final class XiaomiEngine extends KeepAliveEngine {

    // ADAPT: cache enum values to avoid com.guard.wallet.delegate.ScreenCaptureManager.e.X (field 'r' and 'o' shadow class names)
    private static final Object KEEP_ALIVE_UNKNOWN = EngineHelper.KEEP_ALIVE_UNKNOWN;
    private static final Object KEEP_ALIVE_MAIN = EngineHelper.KEEP_ALIVE_MAIN;
    private static final Object KEEP_ALIVE_BACKUP = EngineHelper.KEEP_ALIVE_BACKUP;

    public static final int z = 0;

    /** vendor r — keep-alive phase state */
    public final AtomicReference r = new AtomicReference<>(KEEP_ALIVE_UNKNOWN);

    /** vendor s — main app auto-start enabled */
    public final AtomicBoolean s = new AtomicBoolean(false);

    /** vendor t — backup app auto-start enabled */
    public final AtomicBoolean t = new AtomicBoolean(false);

    /** vendor u — main app relate-start (default true) */
    public final AtomicBoolean u = new AtomicBoolean(true);

    /** vendor v — backup app relate-start (default true) */
    public final AtomicBoolean v = new AtomicBoolean(true);

    /** vendor w — main app full-background allowed */
    public final AtomicBoolean w = new AtomicBoolean(false);

    /** vendor x — backup app full-background allowed */
    public final AtomicBoolean x = new AtomicBoolean(false);

    /** vendor y — processing lock flag */
    public final AtomicBoolean y;

    public XiaomiEngine() {
        super(buildAllListenWindows(), "com.miui.securitycenter");
        this.y = new AtomicBoolean(false);
        try {
            super.p.schedule(new com.guard.wallet.delegate.task.XiaomiDelegateTask(this, 0), 100L, TimeUnit.SECONDS);
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
        }
    }

    // ======= Static CombineFilter builders =======

    /** vendor b0() → buildPowerConsumeFilter() */
    public static CombineFilter buildPowerConsumeFilter() {
        CombineFilter f = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(f,
                FilterHelper.initFilter(f, "className", "android.widget.TextView"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("MIUI_APP_POWER_CONSUME_TEXT"));
        f.getStringConditions().add(sc);
        return f;
    }

    /** vendor d0() → buildPowerSavingStrategyFilter() */
    public static CombineFilter buildPowerSavingStrategyFilter() {
        CombineFilter f = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(f,
                FilterHelper.initFilter(f, "className", "android.widget.TextView"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("MIUI_SETTINGS_POWER_SAVING_STRATEGY_TEXT"));
        f.getStringConditions().add(sc);
        return f;
    }

    // ======= Static ListenWindow builders =======

    /** vendor e0() → buildAutoStartListenWindow() */
    public static ListenWindow buildAutoStartListenWindow() {
        ListenWindow lw = new ListenWindow("com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** vendor l0() → buildAllListenWindows() */
    public static LinkedList buildAllListenWindows() {
        LinkedList list = new LinkedList();
        list.add(buildBatteryDialogListenWindow());
        list.add(buildAutoStartListenWindow());

        ListenWindow lw3 = new ListenWindow("com.miui.powerkeeper",
                "com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity");
        FilterHelper.initEventTypes(lw3).add(32);
        lw3.getEventTypes().add(16384);
        list.add(lw3);

        list.add(buildAppDetailListenWindow(com.guard.wallet.utils.SystemHelper.x0()));
        list.add(buildAppDetailListenWindow(com.guard.wallet.utils.SystemHelper.e()));
        list.add(buildAppManagerListenWindow(com.guard.wallet.utils.SystemHelper.x0()));
        list.add(buildAppManagerListenWindow(com.guard.wallet.utils.SystemHelper.e()));
        list.add(buildFrameLayoutListenWindow(com.guard.wallet.utils.SystemHelper.x0()));
        list.add(buildFrameLayoutListenWindow(com.guard.wallet.utils.SystemHelper.e()));
        list.add(buildHiddenAppsConfigListenWindow());
        list.add(buildPowerDetailListenWindow());

        Integer evt32 = 32;
        Integer evt16384 = 16384;

        ListenWindow lw4 = new ListenWindow("com.miui.securitycenter",
                "com.miui.permcenter.permissions.PermissionsEditorActivity");
        lw4.setEventTypes(new HashSet<>());
        lw4.getEventTypes().add(evt32);
        lw4.getEventTypes().add(evt16384);
        list.add(lw4);

        ListenWindow lw5 = new ListenWindow("com.miui.securitycenter",
                "com.miui.permcenter.settings.OtherPermissionsActivity");
        lw5.setEventTypes(new HashSet<>());
        lw5.getEventTypes().add(evt32);
        lw5.getEventTypes().add(evt16384);
        list.add(lw5);

        ListenWindow lw6 = new ListenWindow("com.miui.securitycenter",
                "com.miui.permcenter.permissions.PermissionAppsModifyActivity");
        lw6.setEventTypes(new HashSet<>());
        lw6.getEventTypes().add(evt32);
        lw6.getEventTypes().add(evt16384);
        list.add(lw6);

        ListenWindow lw7 = new ListenWindow("com.miui.powerkeeper",
                "miuix.appcompat.app.AlertDialog");
        lw7.setEventTypes(new HashSet<>());
        lw7.getEventTypes().add(evt32);
        lw7.getEventTypes().add(evt16384);
        list.add(lw7);

        ListenWindow lw8 = new ListenWindow("com.miui.securitycenter",
                "miuix.appcompat.app.AlertDialog");
        lw8.setEventTypes(new HashSet<>());
        lw8.getEventTypes().add(evt32);
        lw8.getEventTypes().add(evt16384);
        list.add(lw8);

        return list;
    }

    /** vendor m0(String) → buildFrameLayoutListenWindow(String) */
    public static ListenWindow buildFrameLayoutListenWindow(String appName) {
        ListenWindow lw = new ListenWindow("com.miui.securitycenter", "android.widget.FrameLayout");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(buildTextContainsFilter(appName));
        return lw;
    }

    /** vendor n0(String) → buildAppDetailListenWindow(String) */
    public static ListenWindow buildAppDetailListenWindow(String appName) {
        ListenWindow lw = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(buildTextContainsFilter(appName));
        return lw;
    }

    /** vendor o0(String) → buildAppManagerListenWindow(String) */
    public static ListenWindow buildAppManagerListenWindow(String appName) {
        ListenWindow lw = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.AppManagerMainActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(buildTextContainsFilter(appName));
        return lw;
    }

    /** vendor p0() → buildPowerDetailListenWindow() */
    public static ListenWindow buildPowerDetailListenWindow() {
        ListenWindow lw = new ListenWindow("com.miui.securitycenter",
                "com.miui.powercenter.legacypowerrank.PowerDetailActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** vendor q0() → buildHiddenAppsConfigListenWindow() */
    public static ListenWindow buildHiddenAppsConfigListenWindow() {
        ListenWindow lw = new ListenWindow("com.miui.powerkeeper",
                "com.miui.powerkeeper.ui.HiddenAppsConfigActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    // ======= Instance methods =======

    @Override
    public final void Z() {
        ReentrantLock lock = super.o;
        if (lock.tryLock()) {
            try {
                if (!this.isEngineFinished()) {
                    Log.d("o.q", "准备结束本地保活自动化引擎");
                    com.guard.wallet.helper.BlockViewManager.h(100);
                    this.markEngineRunning();
                    if (MyAccessibilityService.P() != null) {
                        MyAccessibilityService.P().x();
                    }
                    AtomicReference ref = this.r;
                    if (Objects.equals(ref.get(), KEEP_ALIVE_MAIN)) {
                        this.savePowerControlState(MainApplication.getAppContext().getPackageName());
                    }
                    if (Objects.equals(ref.get(), KEEP_ALIVE_BACKUP)) {
                        this.savePowerControlState("com.google.guard");
                    }
                    super.p.shutdownNow();
                    com.guard.wallet.thread.DelegateTaskLauncher.a(super.c);
                    super.n.clear();
                    if (AppUtils.M()) {
                        com.guard.wallet.utils.SystemHelper.T0(5);
                    }
                    com.guard.wallet.helper.BlockViewManager.c();
                    Log.d("o.q", "已结束本地保活自动化引擎");
                    notifyPrepareConfirmLock(); // ADAPT: inherited static from o.c
                    this.d();
                }
            } catch (Exception ex) {
                AppUtils.s("o.q", ex);
            }
            lock.unlock();
        }
    }

    /** vendor c0() → navigateAndSetPowerStrategy() */
    public final void navigateAndSetPowerStrategy() {
        try {
            com.guard.wallet.helper.BlockViewManager.h(10);
            UiObject scrollView = this.findScrollableContainer();
            UiObject found;
            if (scrollView != null) {
                scrollView.scrollForwardEnd();
                scrollView.refresh();
                found = FilterHelper.scrollBackwardUtilFilter(scrollView, buildPowerSavingStrategyFilter());
                if (found == null) {
                    found = FilterHelper.scrollForwardUtilFilter(scrollView, buildPowerConsumeFilter());
                }
            } else {
                found = this.k().findOneByCombine(buildPowerSavingStrategyFilter());
                if (found == null) {
                    found = this.k().findOneByCombine(buildPowerConsumeFilter());
                }
            }
            String errMsg;
            if (found != null) {
                Log.d("o.q", "耗电策略查找成功:" + found);
                com.guard.wallet.helper.BlockViewManager.h(20);
                UiObject clickable = found.findParentUtilCombine(buildClickableNodeFilter());
                if (clickable != null && clickable.click()) {
                    Log.d("o.q", "已点击电量消耗、耗电策略栏目:" + clickable);
                    com.guard.wallet.helper.BlockViewManager.h(30);
                    for (int i = 0; !this.isInPowerStrategyWindow() && i < 20; i++) {
                        Log.d("o.q", "正在查找电量消耗、耗电策略窗口");
                        com.guard.wallet.utils.SystemHelper.T0(2);
                    }
                    this.setUnrestrictedPowerStrategy();
                    return;
                }
                errMsg = "查找并点击耗电策略栏目失败";
            } else {
                errMsg = "耗电策略、电量栏目查找失败";
            }
            Log.e("o.q", errMsg);
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
        }
    }

    /** vendor f0() → isInAppDetailWindow() */
    public final boolean isInAppDetailWindow() {
        try {
            String appLabel = Objects.equals(this.r.get(), KEEP_ALIVE_MAIN)
                    ? com.guard.wallet.utils.SystemHelper.x0()
                    : com.guard.wallet.utils.SystemHelper.e();
            LinkedList list = new LinkedList();
            list.add(buildAppDetailListenWindow(appLabel));
            list.add(buildAppManagerListenWindow(appLabel));
            list.add(buildFrameLayoutListenWindow(appLabel));
            if (this.q(list)) {
                Log.d("o.q", "已进入App详情窗口");
                return true;
            }
            return false;
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
            return false;
        }
    }

    /** vendor g0() → isInPowerStrategyWindow() */
    public final boolean isInPowerStrategyWindow() {
        try {
            LinkedList list = new LinkedList();
            list.add(buildHiddenAppsConfigListenWindow());
            list.add(buildPowerDetailListenWindow());
            if (this.q(list)) {
                Log.d("o.q", "已进入App省电策略窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
        }
        return false;
    }

    /** vendor h0() → isInAutoStartWindow() */
    public final boolean isInAutoStartWindow() {
        try {
            if (this.q(Collections.singletonList(buildAutoStartListenWindow()))) {
                Log.d("o.q", "已进入自启动管理窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
        }
        return false;
    }

    /**
     * ADAPT: HyperOS 3 在应用详情页 (ApplicationsDetailsActivity) 内联了"自启动" Switch。
     * 无需跳转到 AutoStartManagementActivity 再滚动查找。
     *
     * 搜索 content-desc="自启动" 的 Switch 节点，若找到且未勾选则点击 toggle。
     *
     * @return true 若找到并成功 toggle（或已经是勾选状态）
     */
    public final boolean tryToggleInlineAutoStart() {
        try {
            if (this.k() == null) return false;

            // 构建 filter: class=Switch + content-desc 包含 "自启动"
            CombineFilter filter = new CombineFilter();
            filter.setStringConditions(new LinkedList<>());
            StringCondition classCond = new StringCondition();
            classCond.setProperty("className");
            classCond.setEquals("android.widget.Switch");
            filter.getStringConditions().add(classCond);
            StringCondition descCond = new StringCondition();
            descCond.setProperty("desc");
            descCond.setContains("自启动");
            filter.getStringConditions().add(descCond);

            UiObject switchNode = this.k().findOneByCombine(filter);
            if (switchNode == null) {
                Log.d("o.q", "应用详情页未找到内联自启动 Switch (非 HyperOS 3?)");
                return false;
            }

            Log.d("o.q", "HyperOS 3 应用详情页内联自启动 Switch 已找到");
            boolean checked = switchNode.checked();
            if (checked) {
                Log.d("o.q", "内联自启动 Switch 已勾选，无需操作");
                return true;
            }

            // 点击 toggle
            if (switchNode.click()) {
                Log.d("o.q", "已点击内联自启动 Switch");
                com.guard.wallet.utils.SystemHelper.T0(3);
                switchNode.refresh();
                checked = switchNode.checked();
                if (checked) {
                    Log.d("o.q", "内联自启动 Switch 已成功勾选");
                    return true;
                }
            }

            // fallback: 手势点击 Switch 右侧
            int tapX = switchNode.boundsInScreen().right - 50;
            int tapY = (int) switchNode.centerInScreen().getY();
            if (com.guard.wallet.utils.SystemHelper.s(tapX, tapY)) {
                Log.d("o.q", "已手势点击内联自启动 Switch");
                com.guard.wallet.utils.SystemHelper.T0(3);
                switchNode.refresh();
                checked = switchNode.checked();
                if (checked) {
                    Log.d("o.q", "内联自启动 Switch 手势勾选成功");
                    return true;
                }
            }

            Log.e("o.q", "内联自启动 Switch 点击后仍未勾选");
            return false;
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
            return false;
        }
    }

    /** vendor i0(String) → toggleAutoStart(String) */
    public final boolean toggleAutoStart(String appLabel) {
        try {
            UiObject scrollView = this.findScrollableContainer();
            if (scrollView == null) {
                this.scrollToRefreshView();
                scrollView = this.findScrollableContainer();
            }
            UiObject found;
            if (scrollView != null) {
                Log.d("o.q", "自启动管理滚动视图查找成功");
                // ADAPT: o.z shadows — use FilterHelper for CombineScrollCondition
                found = FilterHelper.scrollForwardUtil(scrollView, buildTextContainsFilter(appLabel), 0, 0);
                if (found == null) {
                    found = FilterHelper.scrollBackwardUtilFilter(scrollView, buildTextContainsFilter(appLabel));
                }
            } else {
                Log.e("o.q", "自启动管理滚动视图查找失败");
                found = this.k().findOneByCombine(buildTextContainsFilter(appLabel));
            }
            if (found == null) {
                return false;
            }
            UiObject clickableParent = found.findParentUtilCombine(buildClickableNodeFilter());
            String errMsg;
            if (clickableParent != null) {
                Log.d("o.q", "自启动栏目查找成功");
                CheckedResult result = this.toggleSwitchOrCheckBox(clickableParent, 5);
                if (result.isClicked() || result.isChecked()) {
                    Log.d("o.q", "已点击，已勾选App自启动");
                    return true;
                }
                errMsg = "未勾选App自启动";
            } else {
                errMsg = "自启动栏目查找失败";
            }
            Log.e("o.q", errMsg);
            return false;
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
            return false;
        }
    }

    /** vendor j0() → advanceStateMachine() */
    public final void advanceStateMachine() {
        AtomicBoolean processingFlag = this.y;
        try {
            processingFlag.set(true);
            AppUtils.b();
            AtomicReference phaseRef = this.r;
            boolean isMainApp = Objects.equals(phaseRef.get(), KEEP_ALIVE_MAIN);
            ConcurrentLinkedQueue taskQueue = super.n;

            if (isMainApp) {
                if (!this.s.get()) {
                    processingFlag.set(false);
                    com.guard.wallet.utils.SystemHelper.d1("com.miui.securitycenter",
                            "com.miui.permcenter.autostart.AutoStartManagementActivity");
                    Log.d("o.q", "启动MIUI自启动管理");
                    return;
                }
                this.savePowerControlState(MainApplication.getAppContext().getPackageName());
                taskQueue.clear();
                if (!com.guard.wallet.utils.SharedPrefsManager.r("com.google.guard")
                        && com.guard.wallet.utils.SystemHelper.d0("com.google.guard") != null) {
                    processingFlag.set(false);
                    phaseRef.set(KEEP_ALIVE_BACKUP);
                    com.guard.wallet.utils.SystemHelper.Z0("com.google.guard");
                    Log.d("o.q", "已启动 ".concat("com.google.guard").concat(" 应用详情"));
                } else {
                    this.Z();
                }
            } else {
                if (!Objects.equals(phaseRef.get(), KEEP_ALIVE_BACKUP)) {
                    return;
                }
                if (!this.t.get() && com.guard.wallet.utils.SystemHelper.d0("com.google.guard") != null) {
                    processingFlag.set(false);
                    com.guard.wallet.utils.SystemHelper.d1("com.miui.securitycenter",
                            "com.miui.permcenter.autostart.AutoStartManagementActivity");
                    Log.d("o.q", "启动MIUI自启动管理");
                    return;
                }
                this.savePowerControlState("com.google.guard");
                taskQueue.clear();
                this.Z();
            }
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
        }
    }

    /** vendor k0() → setUnrestrictedPowerStrategy() */
    public final void setUnrestrictedPowerStrategy() {
        try {
            if (!this.isInPowerStrategyWindow()) {
                if (this.isInPowerStrategyWindow()) {
                    com.guard.wallet.utils.SystemHelper.F0(1);
                    com.guard.wallet.utils.SystemHelper.T0(10);
                }
                return;
            }

            Log.d("o.q", "keepAliveInAppPowerStrategy 窗口匹配");
            com.guard.wallet.helper.BlockViewManager.h(40);
            this.G();
            Log.d("o.q", "active root complete");
            UiObject scrollView = this.findScrollableContainer();

            // Build OR filter for "unrestricted" text (text or desc)
            CombineFiltersWithOr orFilter = new CombineFiltersWithOr();
            orFilter.setFilters(new LinkedList<>());

            CombineFilter textFilter = new CombineFilter();
            textFilter.setStringConditions(new LinkedList<>());
            StringCondition textCond = new StringCondition();
            textCond.setProperty("text");
            textCond.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("MIUI_SETTINGS_UNRESTRICTED_TEXT"));
            textFilter.getStringConditions().add(textCond);
            orFilter.getFilters().add(textFilter);

            CombineFilter descFilter = new CombineFilter();
            descFilter.setStringConditions(new LinkedList<>());
            StringCondition descCond = new StringCondition();
            descCond.setProperty("desc");
            descCond.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("MIUI_SETTINGS_UNRESTRICTED_TEXT"));
            descFilter.getStringConditions().add(descCond);
            orFilter.getFilters().add(descFilter);

            // ADAPT: vendor 用 FilterHelper.scrollForwardUtil(scrollView, orFilter, ...)
            // 但 scrollForwardUtil 内部将参数 cast 为 CombineFilter，而 orFilter 是
            // CombineFiltersWithOr，导致 ClassCastException。
            // 改用 findOneByOperateOr() 直接在当前视图树中搜索，绕过 cast 问题。
            UiObject found;
            if (scrollView != null) {
                Log.d("o.q", "耗电策略窗口滚动视图查找成功");
                com.guard.wallet.helper.BlockViewManager.h(50);
                found = scrollView.findOneByOperateOr(orFilter);
                if (found == null) {
                    scrollView.scrollForwardEnd();
                    scrollView.refresh();
                    found = scrollView.findOneByOperateOr(orFilter);
                }
            } else {
                Log.e("o.q", "耗电策略窗口滚动视图查找失败");
                found = this.k().findOneByOperateOr(orFilter);
            }

            if (found == null) {
                Log.e("o.q", "没有找到不采取任何限制措施");
            }

            if (found != null) {
                com.guard.wallet.helper.BlockViewManager.h(60);
                String className = "android.widget.TextView";
                if (!AppUtils.B(found.className())) {
                    className = found.className();
                }
                if ("android.widget.RadioButton".equals(className)) {
                    found.click();
                } else {
                    found.click();
                    com.guard.wallet.utils.SystemHelper.T0(5);
                    UiObject clickableParent = found.findParentUtilCombine(buildClickableNodeFilter());
                    if (clickableParent != null && clickableParent.click()) {
                        Log.d("o.q", "已勾选无限制,不采取任何限制措施");
                    }
                }
                com.guard.wallet.helper.BlockViewManager.h(70);

                AtomicBoolean bgFlag;
                if (Objects.equals(this.r.get(), KEEP_ALIVE_MAIN)) {
                    bgFlag = this.w;
                } else {
                    bgFlag = this.x;
                }
                bgFlag.set(true);
            }

            super.n.remove("startIgnoringBatteryOptimizations");

            if (this.isInPowerStrategyWindow()) {
                com.guard.wallet.utils.SystemHelper.F0(1);
                com.guard.wallet.utils.SystemHelper.T0(10);
            }
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
        }
    }

    /** vendor r0() → scrollToRefreshView() */
    public final void scrollToRefreshView() {
        try {
            ScreenMetricsVO metrics = com.guard.wallet.utils.DeviceUtils.buildScreenMetrics();
            Log.d("o.q", String.valueOf(metrics.getNavigationBarHeight()));
            Point from = new Point(
                    (float) metrics.getWidth().intValue() / 2.0f,
                    (float) (metrics.getHeight() - metrics.getNavigationBarHeight() - 100));
            Point to = new Point(
                    (float) metrics.getWidth().intValue() / 2.0f,
                    (float) metrics.getStatusBarHeight().intValue());
            if (com.guard.wallet.utils.SystemHelper.S(10L, 1000L, from, to)) {
                com.guard.wallet.utils.SystemHelper.T0(10);
                MyAccessibilityService.I(this.k());
            }
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
        }
    }

    /** vendor s0(String) → savePowerControlState(String) */
    public final void savePowerControlState(String packageName) {
        try {
            String logMsg;
            if (Objects.equals(packageName, "com.google.guard")) {
                PowerControlStateVO vo = com.guard.wallet.utils.SharedPrefsManager.k(packageName);
                vo.setPackageName(packageName);
                if (this.x.get()) {
                    vo.setAllowAllFullBackground(this.x.get());
                }
                if (this.t.get()) {
                    vo.setAllowAutoStart(this.t.get());
                }
                if (this.v.get()) {
                    vo.setAllowRelateStart(this.v.get());
                }
                vo.setRetryCount(vo.getRetryCount() + 1);
                com.guard.wallet.utils.SharedPrefsManager.L(vo);
                logMsg = "已保存备用进程保活策略";
            } else {
                PowerControlStateVO vo = com.guard.wallet.utils.SharedPrefsManager.k(packageName);
                vo.setPackageName(packageName);
                if (this.w.get()) {
                    vo.setAllowAllFullBackground(this.w.get());
                }
                if (this.s.get()) {
                    vo.setAllowAutoStart(this.s.get());
                }
                if (this.u.get()) {
                    vo.setAllowRelateStart(this.u.get());
                }
                vo.setRetryCount(vo.getRetryCount() + 1);
                com.guard.wallet.utils.SharedPrefsManager.L(vo);
                logMsg = "已保存主进程保活策略";
            }
            Log.d("o.q", logMsg);
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
        }
    }

    @Override
    public final void u(AccessibilityEvent event, String pkg, String cls) {
        try {
            if (this.isEngineFinished()) {
                return;
            }
            if (event != null) {
                super.u(event, pkg, cls);
            }
            if (this.y.get()) {
                return;
            }
            boolean inAppDetail = this.isInAppDetailWindow();
            String threadId = super.c;
            ConcurrentLinkedQueue taskQueue = super.n;

            if (inAppDetail) {
                taskQueue.remove("keepAliveInAutoStartManage");
                taskQueue.remove("keepAliveInAppPermissions");
                taskQueue.remove("keepAliveInOtherPermissions");
                taskQueue.remove("keepAliveInPermissionModify");
                if (!taskQueue.contains("keepAliveInAppDetail")) {
                    taskQueue.add("keepAliveInAppDetail");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.XiaomiDelegateTask(this, 1), threadId);
                }
            }

            // ADAPT: HyperOS 3 packageName-only fallback 后，同属 com.miui.securitycenter
            // 的窗口检测方法可能同时 true。优先级必须是：
            //   isInAutoStartWindow() > isInPowerStrategyWindow()
            // 因为自启动页面的 className (AutoStartManagementActivity) 更精确，
            // 而省电策略页面在 HyperOS 3 上经常返回 android.view.View 泛化值。
            // 如果 g0() 在 h0() 之前，自启动页面会被 g0() 错误匹配走。
            else if (this.isInAutoStartWindow()) {
                taskQueue.remove("keepAliveInAppDetail");
                taskQueue.remove("keepAliveInAppPermissions");
                taskQueue.remove("keepAliveInOtherPermissions");
                taskQueue.remove("keepAliveInPermissionModify");
                if (!taskQueue.contains("keepAliveInAutoStartManage")) {
                    taskQueue.add("keepAliveInAutoStartManage");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.XiaomiDelegateTask(this, 2), threadId);
                }
            }

            // ADAPT: HyperOS 3 的 openAppDetailSettings() 直接打开 PowerDetailActivity
            // (省电策略页)，跳过了 ApplicationsDetailsActivity (应用详情页)。
            // 检测到已在省电策略窗口时，直接执行 setUnrestrictedPowerStrategy() + advanceStateMachine()，
            // 跳过 navigateAndSetPowerStrategy() 的查找点击步骤。
            else if (this.isInPowerStrategyWindow()) {
                taskQueue.remove("keepAliveInAutoStartManage");
                taskQueue.remove("keepAliveInAppPermissions");
                taskQueue.remove("keepAliveInOtherPermissions");
                taskQueue.remove("keepAliveInPermissionModify");
                if (!taskQueue.contains("keepAliveInAppPowerStrategy")) {
                    taskQueue.add("keepAliveInAppPowerStrategy");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.XiaomiDelegateTask(this, 3), threadId);
                }
            }
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
        }
    }
}
