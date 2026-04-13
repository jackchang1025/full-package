package v0;

import a1.C0017h;
import a1.InterfaceC0028s;
import a1.InterfaceC0029t;
import com.guard.wallet.utils.AbstractC0251g;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import l0.C0387q;
import p0.C0864f;
import p0.C0875q;
import p0.C0877s;
import p0.C0879u;
import p0.InterfaceC0880v;
import p0.f0;
import p0.i0;
import p0.j0;
import q0.AbstractC0887c;
import s0.C0904g;
import s0.C0907j;
import t0.AbstractC0916e;
import t0.C0917f;
import t0.InterfaceC0913b;

/* renamed from: v0.t */
/* loaded from: classes.dex */
public final class C0949t implements InterfaceC0913b {

    /* renamed from: g */
    public static final List f2223g = AbstractC0887c.m1315l("connection", "host", "keep-alive", "proxy-connection", "te", "transfer-encoding", "encoding", "upgrade", ":method", ":path", ":scheme", ":authority");

    /* renamed from: h */
    public static final List f2224h = AbstractC0887c.m1315l("connection", "host", "keep-alive", "proxy-connection", "te", "transfer-encoding", "encoding", "upgrade");

    /* renamed from: a */
    public final InterfaceC0880v f2225a;

    /* renamed from: b */
    public final C0904g f2226b;

    /* renamed from: c */
    public final C0948s f2227c;

    /* renamed from: d */
    public volatile C0954y f2228d;

    /* renamed from: e */
    public final p0.c0 f2229e;

    /* renamed from: f */
    public volatile boolean f2230f;

    public C0949t(p0.b0 b0Var, C0904g c0904g, C0917f c0917f, C0948s c0948s) {
        this.f2226b = c0904g;
        this.f2225a = c0917f;
        this.f2227c = c0948s;
        p0.c0 c0Var = p0.c0.H2_PRIOR_KNOWLEDGE;
        this.f2229e = b0Var.f1720b.contains(c0Var) ? c0Var : p0.c0.HTTP_2;
    }

    @Override // t0.InterfaceC0913b
    /* renamed from: a */
    public final InterfaceC0029t mo1368a(j0 j0Var) {
        return this.f2228d.f2258g;
    }

    @Override // t0.InterfaceC0913b
    /* renamed from: b */
    public final InterfaceC0028s mo1369b(f0 f0Var, long j2) {
        C0954y c0954y = this.f2228d;
        synchronized (c0954y) {
            if (!c0954y.f2257f && !c0954y.m1431f()) {
                throw new IllegalStateException("reply before requesting the sink");
            }
        }
        return c0954y.f2259h;
    }

    @Override // t0.InterfaceC0913b
    /* renamed from: c */
    public final void mo1370c() {
        C0954y c0954y = this.f2228d;
        synchronized (c0954y) {
            if (!c0954y.f2257f && !c0954y.m1431f()) {
                throw new IllegalStateException("reply before requesting the sink");
            }
        }
        c0954y.f2259h.close();
    }

    @Override // t0.InterfaceC0913b
    public final void cancel() {
        this.f2230f = true;
        if (this.f2228d != null) {
            this.f2228d.m1430e(EnumC0931b.CANCEL);
        }
    }

    @Override // t0.InterfaceC0913b
    /* renamed from: d */
    public final void mo1371d() {
        this.f2227c.flush();
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00dd A[Catch: all -> 0x0183, TryCatch #0 {, blocks: (B:30:0x009f, B:32:0x00a6, B:33:0x00ab, B:35:0x00af, B:37:0x00c5, B:39:0x00cd, B:43:0x00d7, B:45:0x00dd, B:46:0x00e6, B:88:0x017d, B:89:0x0182), top: B:29:0x009f, outer: #1 }] */
    @Override // t0.InterfaceC0913b
    /* renamed from: e */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo1372e(f0 f0Var) {
        int i2;
        C0954y c0954y;
        boolean z2;
        if (this.f2228d != null) {
            return;
        }
        boolean z3 = f0Var.f1780d != null;
        C0877s c0877s = f0Var.f1779c;
        ArrayList arrayList = new ArrayList((c0877s.f1896a.length / 2) + 4);
        arrayList.add(new C0932c(C0932c.f2136f, f0Var.f1778b));
        C0017h c0017h = C0932c.f2137g;
        C0879u c0879u = f0Var.f1777a;
        arrayList.add(new C0932c(c0017h, AbstractC0251g.L0(c0879u)));
        String m1254a = f0Var.m1254a("Host");
        if (m1254a != null) {
            arrayList.add(new C0932c(C0932c.f2139i, m1254a));
        }
        arrayList.add(new C0932c(C0932c.f2138h, c0879u.f1907a));
        int length = c0877s.f1896a.length / 2;
        for (int i3 = 0; i3 < length; i3++) {
            String lowerCase = c0877s.m1281d(i3).toLowerCase(Locale.US);
            if (!f2223g.contains(lowerCase) || (lowerCase.equals("te") && c0877s.m1283f(i3).equals("trailers"))) {
                arrayList.add(new C0932c(lowerCase, c0877s.m1283f(i3)));
            }
        }
        C0948s c0948s = this.f2227c;
        boolean z4 = !z3;
        synchronized (c0948s.f2220u) {
            synchronized (c0948s) {
                if (c0948s.f2205f > 1073741823) {
                    c0948s.m1410C(EnumC0931b.REFUSED_STREAM);
                }
                if (c0948s.f2206g) {
                    throw new C0930a();
                }
                i2 = c0948s.f2205f;
                c0948s.f2205f = i2 + 2;
                c0954y = new C0954y(i2, c0948s, z4, false, null);
                if (z3 && c0948s.f2216q != 0 && c0954y.f2253b != 0) {
                    z2 = false;
                    if (c0954y.m1432g()) {
                        c0948s.f2202c.put(Integer.valueOf(i2), c0954y);
                    }
                }
                z2 = true;
                if (c0954y.m1432g()) {
                }
            }
            C0955z c0955z = c0948s.f2220u;
            synchronized (c0955z) {
                if (c0955z.f2269e) {
                    throw new IOException("closed");
                }
                c0955z.f2270f.m1403d(arrayList);
                long j2 = c0955z.f2267c.f22b;
                int min = (int) Math.min(c0955z.f2268d, j2);
                long j3 = min;
                byte b = j2 == j3 ? (byte) 4 : (byte) 0;
                c0955z.m1442z(i2, min, (byte) 1, z4 ? (byte) (b | 1) : b);
                c0955z.f2265a.mo67i(c0955z.f2267c, j3);
                if (j2 > j3) {
                    c0955z.m1439E(i2, j2 - j3);
                }
            }
        }
        if (z2) {
            C0955z c0955z2 = c0948s.f2220u;
            synchronized (c0955z2) {
                if (c0955z2.f2269e) {
                    throw new IOException("closed");
                }
                c0955z2.f2265a.flush();
            }
        }
        this.f2228d = c0954y;
        if (this.f2230f) {
            this.f2228d.m1430e(EnumC0931b.CANCEL);
            throw new IOException("Canceled");
        }
        C0907j c0907j = this.f2228d.f2260i;
        long j4 = ((C0917f) this.f2225a).f2077h;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        c0907j.mo136g(j4, timeUnit);
        this.f2228d.f2261j.mo136g(((C0917f) this.f2225a).f2078i, timeUnit);
    }

    @Override // t0.InterfaceC0913b
    /* renamed from: f */
    public final long mo1373f(j0 j0Var) {
        return AbstractC0916e.m1376a(j0Var);
    }

    @Override // t0.InterfaceC0913b
    /* renamed from: g */
    public final i0 mo1374g(boolean z2) {
        C0877s c0877s;
        C0954y c0954y = this.f2228d;
        synchronized (c0954y) {
            c0954y.f2260i.m71i();
            while (c0954y.f2256e.isEmpty() && c0954y.f2262k == null) {
                try {
                    c0954y.m1434i();
                } catch (Throwable th) {
                    c0954y.f2260i.m1361o();
                    throw th;
                }
            }
            c0954y.f2260i.m1361o();
            if (c0954y.f2256e.isEmpty()) {
                IOException iOException = c0954y.f2263l;
                if (iOException != null) {
                    throw iOException;
                }
                throw new d0(c0954y.f2262k);
            }
            c0877s = (C0877s) c0954y.f2256e.removeFirst();
        }
        p0.c0 c0Var = this.f2229e;
        ArrayList arrayList = new ArrayList(20);
        int length = c0877s.f1896a.length / 2;
        C0387q c0387q = null;
        for (int i2 = 0; i2 < length; i2++) {
            String m1281d = c0877s.m1281d(i2);
            String m1283f = c0877s.m1283f(i2);
            if (m1281d.equals(":status")) {
                c0387q = C0387q.m959a("HTTP/1.1 " + m1283f);
            } else if (!f2224h.contains(m1281d)) {
                C0875q.f1891c.getClass();
                arrayList.add(m1281d);
                arrayList.add(m1283f.trim());
            }
        }
        if (c0387q == null) {
            throw new ProtocolException("Expected ':status' header not present");
        }
        i0 i0Var = new i0();
        i0Var.f1813b = c0Var;
        i0Var.f1814c = c0387q.f784e;
        i0Var.f1815d = c0387q.f785f;
        String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
        C0864f c0864f = new C0864f();
        Collections.addAll(c0864f.f1776a, strArr);
        i0Var.f1817f = c0864f;
        if (z2) {
            C0875q.f1891c.getClass();
            if (i0Var.f1814c == 100) {
                return null;
            }
        }
        return i0Var;
    }

    @Override // t0.InterfaceC0913b
    /* renamed from: h */
    public final C0904g mo1375h() {
        return this.f2226b;
    }
}
