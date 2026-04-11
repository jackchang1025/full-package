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
 * ADAPT: Field 'c' (String) shadows class o.c; use W() directly (inherited) or EngineHelper.
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
        super(l0(), "com.miui.securitycenter");
        new AtomicBoolean(false);
        new AtomicBoolean(false);
        this.y = new AtomicBoolean(false);
        try {
            super.p.schedule(new com.guard.wallet.delegate.task.XiaomiDelegateTask(this, 0), 100L, TimeUnit.SECONDS);
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
        }
    }

    // ======= Static CombineFilter builders =======

    public static CombineFilter b0() {
        CombineFilter f = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(f,
                FilterHelper.initFilter(f, "className", "android.widget.TextView"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("MIUI_APP_POWER_CONSUME_TEXT"));
        f.getStringConditions().add(sc);
        return f;
    }

    public static CombineFilter d0() {
        CombineFilter f = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(f,
                FilterHelper.initFilter(f, "className", "android.widget.TextView"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("MIUI_SETTINGS_POWER_SAVING_STRATEGY_TEXT"));
        f.getStringConditions().add(sc);
        return f;
    }

    // ======= Static ListenWindow builders =======

    public static ListenWindow e0() {
        ListenWindow lw = new ListenWindow("com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    public static LinkedList l0() {
        LinkedList list = new LinkedList();
        list.add(J());
        list.add(e0());

        ListenWindow lw3 = new ListenWindow("com.miui.powerkeeper",
                "com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity");
        FilterHelper.initEventTypes(lw3).add(32);
        lw3.getEventTypes().add(16384);
        list.add(lw3);

        list.add(n0(com.guard.wallet.utils.SystemHelper.x0()));
        list.add(n0(com.guard.wallet.utils.SystemHelper.e()));
        list.add(o0(com.guard.wallet.utils.SystemHelper.x0()));
        list.add(o0(com.guard.wallet.utils.SystemHelper.e()));
        list.add(m0(com.guard.wallet.utils.SystemHelper.x0()));
        list.add(m0(com.guard.wallet.utils.SystemHelper.e()));
        list.add(q0());
        list.add(p0());

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

    public static ListenWindow m0(String appName) {
        ListenWindow lw = new ListenWindow("com.miui.securitycenter", "android.widget.FrameLayout");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(H(appName));
        return lw;
    }

    public static ListenWindow n0(String appName) {
        ListenWindow lw = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(H(appName));
        return lw;
    }

    public static ListenWindow o0(String appName) {
        ListenWindow lw = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.AppManagerMainActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(H(appName));
        return lw;
    }

    public static ListenWindow p0() {
        ListenWindow lw = new ListenWindow("com.miui.securitycenter",
                "com.miui.powercenter.legacypowerrank.PowerDetailActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    public static ListenWindow q0() {
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
                if (!this.T()) {
                    Log.d("o.q", "准备结束本地保活自动化引擎");
                    com.guard.wallet.helper.BlockViewManager.h(100);
                    this.X();
                    if (MyAccessibilityService.P() != null) {
                        MyAccessibilityService.P().x();
                    }
                    AtomicReference ref = this.r;
                    if (Objects.equals(ref.get(), KEEP_ALIVE_MAIN)) {
                        this.s0(MainApplication.getAppContext().getPackageName());
                    }
                    if (Objects.equals(ref.get(), KEEP_ALIVE_BACKUP)) {
                        this.s0("com.google.guard");
                    }
                    super.p.shutdownNow();
                    com.guard.wallet.thread.DelegateTaskLauncher.a(super.c);
                    super.n.clear();
                    if (AppUtils.M()) {
                        com.guard.wallet.utils.SystemHelper.T0(5);
                    }
                    com.guard.wallet.helper.BlockViewManager.c();
                    Log.d("o.q", "已结束本地保活自动化引擎");
                    W(); // ADAPT: inherited static from o.c
                    this.d();
                }
            } catch (Exception ex) {
                AppUtils.s("o.q", ex);
            }
            lock.unlock();
        }
    }

    public final void c0() {
        try {
            com.guard.wallet.helper.BlockViewManager.h(10);
            UiObject scrollView = this.Q();
            UiObject found;
            if (scrollView != null) {
                scrollView.scrollForwardEnd();
                scrollView.refresh();
                found = FilterHelper.scrollBackwardUtilFilter(scrollView, d0());
                if (found == null) {
                    found = FilterHelper.scrollForwardUtilFilter(scrollView, b0());
                }
            } else {
                found = this.k().findOneByCombine(d0());
                if (found == null) {
                    found = this.k().findOneByCombine(b0());
                }
            }
            String errMsg;
            if (found != null) {
                Log.d("o.q", "耗电策略查找成功:" + found);
                com.guard.wallet.helper.BlockViewManager.h(20);
                UiObject clickable = found.findParentUtilCombine(L());
                if (clickable != null && clickable.click()) {
                    Log.d("o.q", "已点击电量消耗、耗电策略栏目:" + clickable);
                    com.guard.wallet.helper.BlockViewManager.h(30);
                    for (int i = 0; !this.g0() && i < 20; i++) {
                        Log.d("o.q", "正在查找电量消耗、耗电策略窗口");
                        com.guard.wallet.utils.SystemHelper.T0(2);
                    }
                    this.k0();
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

    public final boolean f0() {
        try {
            String appLabel = Objects.equals(this.r.get(), KEEP_ALIVE_MAIN)
                    ? com.guard.wallet.utils.SystemHelper.x0()
                    : com.guard.wallet.utils.SystemHelper.e();
            LinkedList list = new LinkedList();
            list.add(n0(appLabel));
            list.add(o0(appLabel));
            list.add(m0(appLabel));
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

    public final boolean g0() {
        try {
            LinkedList list = new LinkedList();
            list.add(q0());
            list.add(p0());
            if (this.q(list)) {
                Log.d("o.q", "已进入App省电策略窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
        }
        return false;
    }

    public final boolean h0() {
        try {
            if (this.q(Collections.singletonList(e0()))) {
                Log.d("o.q", "已进入自启动管理窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
        }
        return false;
    }

    public final boolean i0(String appLabel) {
        try {
            UiObject scrollView = this.Q();
            if (scrollView == null) {
                this.r0();
                scrollView = this.Q();
            }
            UiObject found;
            if (scrollView != null) {
                Log.d("o.q", "自启动管理滚动视图查找成功");
                // ADAPT: o.z shadows — use FilterHelper for CombineScrollCondition
                found = FilterHelper.scrollForwardUtil(scrollView, H(appLabel), 0, 0);
                if (found == null) {
                    found = FilterHelper.scrollBackwardUtilFilter(scrollView, H(appLabel));
                }
            } else {
                Log.e("o.q", "自启动管理滚动视图查找失败");
                found = this.k().findOneByCombine(H(appLabel));
            }
            if (found == null) {
                return false;
            }
            UiObject clickableParent = found.findParentUtilCombine(L());
            String errMsg;
            if (clickableParent != null) {
                Log.d("o.q", "自启动栏目查找成功");
                CheckedResult result = this.O(clickableParent, 5);
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

    public final void j0() {
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
                this.s0(MainApplication.getAppContext().getPackageName());
                taskQueue.clear();
                if (!com.guard.wallet.utils.SharedPrefsManager.r("com.google.guard")
                        && com.guard.wallet.utils.SystemHelper.d0("com.google.guard") != null) {
                    processingFlag.set(false);
                    phaseRef.set(KEEP_ALIVE_BACKUP);
                    com.guard.wallet.utils.SystemHelper.Z0("com.google.guard");
                    Log.d("o.q", "已启动 ".concat("com.google.guard").concat(" 应用详情"));
                    "已启动 ".concat("com.google.guard").concat(" 应用详情");
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
                this.s0("com.google.guard");
                taskQueue.clear();
                this.Z();
            }
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
        }
    }

    public final void k0() {
        try {
            if (!this.g0()) {
                if (this.g0()) {
                    com.guard.wallet.utils.SystemHelper.F0(1);
                    com.guard.wallet.utils.SystemHelper.T0(10);
                }
                return;
            }

            Log.d("o.q", "keepAliveInAppPowerStrategy 窗口匹配");
            com.guard.wallet.helper.BlockViewManager.h(40);
            this.G();
            Log.d("o.q", "active root complete");
            UiObject scrollView = this.Q();

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

            UiObject found;
            if (scrollView != null) {
                Log.d("o.q", "耗电策略窗口滚动视图查找成功");
                com.guard.wallet.helper.BlockViewManager.h(50);
                // ADAPT: o.z shadows — use FilterHelper for CombineScrollCondition
                found = FilterHelper.scrollForwardUtil(scrollView, orFilter, 1, 0);
                if (found == null) {
                    found = FilterHelper.scrollForwardUtil(scrollView, orFilter, 1, 0);
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
                    UiObject clickableParent = found.findParentUtilCombine(L());
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

            if (this.g0()) {
                com.guard.wallet.utils.SystemHelper.F0(1);
                com.guard.wallet.utils.SystemHelper.T0(10);
            }
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
        }
    }

    public final void r0() {
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

    public final void s0(String packageName) {
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
            if (this.T()) {
                return;
            }
            if (event != null) {
                super.u(event, pkg, cls);
            }
            if (this.y.get()) {
                return;
            }
            boolean inAppDetail = this.f0();
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

            // ADAPT: HyperOS 3 的 openAppDetailSettings() 直接打开 PowerDetailActivity
            // (省电策略页)，跳过了 ApplicationsDetailsActivity (应用详情页)。
            // 检测到已在省电策略窗口时，直接执行 k0() + j0()，跳过 c0() 的查找点击步骤。
            else if (this.g0()) {
                taskQueue.remove("keepAliveInAutoStartManage");
                taskQueue.remove("keepAliveInAppPermissions");
                taskQueue.remove("keepAliveInOtherPermissions");
                taskQueue.remove("keepAliveInPermissionModify");
                if (!taskQueue.contains("keepAliveInAppPowerStrategy")) {
                    taskQueue.add("keepAliveInAppPowerStrategy");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.XiaomiDelegateTask(this, 3), threadId);
                }
            }

            // ADAPT: HyperOS 3 packageName-only fallback 后 f0() 和 h0() 可能同时 true
            // (同属 com.miui.securitycenter)。改为 else if 让 f0() 优先。
            else if (this.h0()) {
                taskQueue.remove("keepAliveInAppDetail");
                taskQueue.remove("keepAliveInAppPermissions");
                taskQueue.remove("keepAliveInOtherPermissions");
                taskQueue.remove("keepAliveInPermissionModify");
                if (!taskQueue.contains("keepAliveInAutoStartManage")) {
                    taskQueue.add("keepAliveInAutoStartManage");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.XiaomiDelegateTask(this, 2), threadId);
                }
            }
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
        }
    }
}
