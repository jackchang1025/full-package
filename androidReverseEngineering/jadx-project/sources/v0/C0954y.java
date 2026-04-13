package v0;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayDeque;
import p0.C0877s;
import s0.C0907j;

/* renamed from: v0.y */
/* loaded from: classes.dex */
public final class C0954y {

    /* renamed from: a */
    public long f2252a = 0;

    /* renamed from: b */
    public long f2253b;

    /* renamed from: c */
    public final int f2254c;

    /* renamed from: d */
    public final C0948s f2255d;

    /* renamed from: e */
    public final ArrayDeque f2256e;

    /* renamed from: f */
    public boolean f2257f;

    /* renamed from: g */
    public final C0953x f2258g;

    /* renamed from: h */
    public final C0952w f2259h;

    /* renamed from: i */
    public final C0907j f2260i;

    /* renamed from: j */
    public final C0907j f2261j;

    /* renamed from: k */
    public EnumC0931b f2262k;

    /* renamed from: l */
    public IOException f2263l;

    public C0954y(int i2, C0948s c0948s, boolean z2, boolean z3, C0877s c0877s) {
        ArrayDeque arrayDeque = new ArrayDeque();
        this.f2256e = arrayDeque;
        int i3 = 1;
        this.f2260i = new C0907j(this, i3);
        this.f2261j = new C0907j(this, i3);
        if (c0948s == null) {
            throw new NullPointerException("connection == null");
        }
        this.f2254c = i2;
        this.f2255d = c0948s;
        this.f2253b = c0948s.f2218s.m1473d();
        C0953x c0953x = new C0953x(this, c0948s.f2217r.m1473d());
        this.f2258g = c0953x;
        C0952w c0952w = new C0952w(this);
        this.f2259h = c0952w;
        c0953x.f2250e = z3;
        c0952w.f2244c = z2;
        if (c0877s != null) {
            arrayDeque.add(c0877s);
        }
        if (m1431f() && c0877s != null) {
            throw new IllegalStateException("locally-initiated streams shouldn't have headers yet");
        }
        if (!m1431f() && c0877s == null) {
            throw new IllegalStateException("remotely-initiated streams should have headers");
        }
    }

    /* renamed from: a */
    public final void m1426a() {
        boolean z2;
        boolean m1432g;
        synchronized (this) {
            C0953x c0953x = this.f2258g;
            if (!c0953x.f2250e && c0953x.f2249d) {
                C0952w c0952w = this.f2259h;
                if (c0952w.f2244c || c0952w.f2243b) {
                    z2 = true;
                    m1432g = m1432g();
                }
            }
            z2 = false;
            m1432g = m1432g();
        }
        if (z2) {
            m1428c(EnumC0931b.CANCEL, null);
        } else {
            if (m1432g) {
                return;
            }
            this.f2255d.m1409B(this.f2254c);
        }
    }

    /* renamed from: b */
    public final void m1427b() {
        C0952w c0952w = this.f2259h;
        if (c0952w.f2243b) {
            throw new IOException("stream closed");
        }
        if (c0952w.f2244c) {
            throw new IOException("stream finished");
        }
        if (this.f2262k != null) {
            IOException iOException = this.f2263l;
            if (iOException == null) {
                throw new d0(this.f2262k);
            }
        }
    }

    /* renamed from: c */
    public final void m1428c(EnumC0931b enumC0931b, IOException iOException) {
        if (m1429d(enumC0931b, iOException)) {
            this.f2255d.f2220u.m1437C(this.f2254c, enumC0931b);
        }
    }

    /* renamed from: d */
    public final boolean m1429d(EnumC0931b enumC0931b, IOException iOException) {
        synchronized (this) {
            if (this.f2262k != null) {
                return false;
            }
            if (this.f2258g.f2250e && this.f2259h.f2244c) {
                return false;
            }
            this.f2262k = enumC0931b;
            this.f2263l = iOException;
            notifyAll();
            this.f2255d.m1409B(this.f2254c);
            return true;
        }
    }

    /* renamed from: e */
    public final void m1430e(EnumC0931b enumC0931b) {
        if (m1429d(enumC0931b, null)) {
            this.f2255d.m1413F(this.f2254c, enumC0931b);
        }
    }

    /* renamed from: f */
    public final boolean m1431f() {
        return this.f2255d.f2200a == ((this.f2254c & 1) == 1);
    }

    /* renamed from: g */
    public final synchronized boolean m1432g() {
        if (this.f2262k != null) {
            return false;
        }
        C0953x c0953x = this.f2258g;
        if (c0953x.f2250e || c0953x.f2249d) {
            C0952w c0952w = this.f2259h;
            if (c0952w.f2244c || c0952w.f2243b) {
                if (this.f2257f) {
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0018 A[Catch: all -> 0x002e, TryCatch #0 {, blocks: (B:3:0x0001, B:7:0x0009, B:9:0x0018, B:10:0x001c, B:11:0x0023, B:18:0x000f), top: B:2:0x0001 }] */
    /* renamed from: h */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m1433h(C0877s c0877s, boolean z2) {
        boolean m1432g;
        synchronized (this) {
            if (this.f2257f && z2) {
                this.f2258g.getClass();
                if (z2) {
                    this.f2258g.f2250e = true;
                }
                m1432g = m1432g();
                notifyAll();
            }
            this.f2257f = true;
            this.f2256e.add(c0877s);
            if (z2) {
            }
            m1432g = m1432g();
            notifyAll();
        }
        if (m1432g) {
            return;
        }
        this.f2255d.m1409B(this.f2254c);
    }

    /* renamed from: i */
    public final void m1434i() {
        try {
            wait();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException();
        }
    }
}
