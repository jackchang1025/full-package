package s0;

import com.guard.wallet.http.C0203h;
import java.io.IOException;
import java.lang.ref.Reference;
import java.net.Proxy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLPeerUnverifiedException;
import p0.C0859a;
import p0.C0875q;
import p0.C0879u;
import p0.m0;
import p012o.RunnableC0412a;
import q0.AbstractC0887c;
import q0.ThreadFactoryC0886b;
import w0.C0966i;
import z0.C0984c;

/* renamed from: s0.h */
/* loaded from: classes.dex */
public final class C0905h {

    /* renamed from: g */
    public static final ThreadPoolExecutor f2041g;

    /* renamed from: b */
    public final long f2043b;

    /* renamed from: f */
    public boolean f2047f;

    /* renamed from: c */
    public final RunnableC0412a f2044c = new RunnableC0412a(this, 8);

    /* renamed from: d */
    public final ArrayDeque f2045d = new ArrayDeque();

    /* renamed from: e */
    public final C0203h f2046e = new C0203h(9);

    /* renamed from: a */
    public final int f2042a = 5;

    static {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        SynchronousQueue synchronousQueue = new SynchronousQueue();
        byte[] bArr = AbstractC0887c.f1934a;
        f2041g = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, timeUnit, synchronousQueue, new ThreadFactoryC0886b("OkHttp ConnectionPool", true));
    }

    public C0905h(TimeUnit timeUnit) {
        this.f2043b = timeUnit.toNanos(5L);
    }

    /* renamed from: a */
    public final void m1358a(m0 m0Var, IOException iOException) {
        if (m0Var.f1875b.type() != Proxy.Type.DIRECT) {
            C0859a c0859a = m0Var.f1874a;
            c0859a.f1688g.connectFailed(c0859a.f1682a.m1299n(), m0Var.f1875b.address(), iOException);
        }
        C0203h c0203h = this.f2046e;
        synchronized (c0203h) {
            ((Set) c0203h.f245e).add(m0Var);
        }
    }

    /* renamed from: b */
    public final int m1359b(C0904g c0904g, long j2) {
        ArrayList arrayList = c0904g.f2039p;
        int i2 = 0;
        while (i2 < arrayList.size()) {
            Reference reference = (Reference) arrayList.get(i2);
            if (reference.get() != null) {
                i2++;
            } else {
                C0966i.f2293a.mo1456n(((C0908k) reference).f2052a, "A connection to " + c0904g.f2026c.f1874a.f1682a + " was leaked. Did you forget to close a response body?");
                arrayList.remove(i2);
                c0904g.f2034k = true;
                if (arrayList.isEmpty()) {
                    c0904g.f2040q = j2 - this.f2043b;
                    return 0;
                }
            }
        }
        return arrayList.size();
    }

    /* renamed from: c */
    public final boolean m1360c(C0859a c0859a, C0909l c0909l, ArrayList arrayList, boolean z2) {
        boolean z3;
        Iterator it = this.f2045d.iterator();
        while (true) {
            boolean z4 = false;
            if (!it.hasNext()) {
                return false;
            }
            C0904g c0904g = (C0904g) it.next();
            if (z2) {
                if (!(c0904g.f2031h != null)) {
                    continue;
                }
            }
            if (c0904g.f2039p.size() < c0904g.f2038o && !c0904g.f2034k) {
                C0875q c0875q = C0875q.f1891c;
                m0 m0Var = c0904g.f2026c;
                C0859a c0859a2 = m0Var.f1874a;
                c0875q.getClass();
                if (c0859a2.m1242a(c0859a)) {
                    C0879u c0879u = c0859a.f1682a;
                    if (!c0879u.f1910d.equals(m0Var.f1874a.f1682a.f1910d)) {
                        if (c0904g.f2031h != null && arrayList != null) {
                            int size = arrayList.size();
                            int i2 = 0;
                            while (true) {
                                if (i2 >= size) {
                                    z3 = false;
                                    break;
                                }
                                m0 m0Var2 = (m0) arrayList.get(i2);
                                if (m0Var2.f1875b.type() == Proxy.Type.DIRECT && m0Var.f1875b.type() == Proxy.Type.DIRECT && m0Var.f1876c.equals(m0Var2.f1876c)) {
                                    z3 = true;
                                    break;
                                }
                                i2++;
                            }
                            if (z3) {
                                if (c0859a.f1691j == C0984c.f2330a && c0904g.m1357j(c0879u)) {
                                    try {
                                        c0859a.f1692k.m1256a(c0879u.f1910d, c0904g.f2029f.f1894c);
                                    } catch (SSLPeerUnverifiedException unused) {
                                    }
                                }
                            }
                        }
                    }
                    z4 = true;
                }
            }
            if (z4) {
                if (c0909l.f2061i != null) {
                    throw new IllegalStateException();
                }
                c0909l.f2061i = c0904g;
                c0904g.f2039p.add(new C0908k(c0909l, c0909l.f2058f));
                return true;
            }
        }
    }
}
