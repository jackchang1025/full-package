package v0;

import a1.C0014e;
import a1.C0031v;
import a1.InterfaceC0028s;

/* renamed from: v0.w */
/* loaded from: classes.dex */
public final class C0952w implements InterfaceC0028s {

    /* renamed from: a */
    public final C0014e f2242a = new C0014e();

    /* renamed from: b */
    public boolean f2243b;

    /* renamed from: c */
    public boolean f2244c;

    /* renamed from: d */
    public final /* synthetic */ C0954y f2245d;

    public C0952w(C0954y c0954y) {
        this.f2245d = c0954y;
    }

    @Override // a1.InterfaceC0028s
    /* renamed from: a */
    public final C0031v mo66a() {
        return this.f2245d.f2261j;
    }

    @Override // a1.InterfaceC0028s, java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        synchronized (this.f2245d) {
            if (this.f2243b) {
                return;
            }
            C0954y c0954y = this.f2245d;
            if (!c0954y.f2259h.f2244c) {
                if (this.f2242a.f22b > 0) {
                    while (this.f2242a.f22b > 0) {
                        m1425x(true);
                    }
                } else {
                    c0954y.f2255d.m1412E(c0954y.f2254c, true, null, 0L);
                }
            }
            synchronized (this.f2245d) {
                this.f2243b = true;
            }
            this.f2245d.f2255d.flush();
            this.f2245d.m1426a();
        }
    }

    @Override // a1.InterfaceC0028s, java.io.Flushable
    public final void flush() {
        synchronized (this.f2245d) {
            this.f2245d.m1427b();
        }
        while (this.f2242a.f22b > 0) {
            m1425x(false);
            this.f2245d.f2255d.flush();
        }
    }

    @Override // a1.InterfaceC0028s
    /* renamed from: i */
    public final void mo67i(C0014e c0014e, long j2) {
        C0014e c0014e2 = this.f2242a;
        c0014e2.mo67i(c0014e, j2);
        while (c0014e2.f22b >= 16384) {
            m1425x(false);
        }
    }

    /* renamed from: x */
    public final void m1425x(boolean z2) {
        C0954y c0954y;
        long min;
        C0954y c0954y2;
        boolean z3;
        synchronized (this.f2245d) {
            this.f2245d.f2261j.m71i();
            while (true) {
                try {
                    c0954y = this.f2245d;
                    if (c0954y.f2253b > 0 || this.f2244c || this.f2243b || c0954y.f2262k != null) {
                        break;
                    } else {
                        c0954y.m1434i();
                    }
                } finally {
                    this.f2245d.f2261j.m1361o();
                }
            }
            c0954y.f2261j.m1361o();
            this.f2245d.m1427b();
            min = Math.min(this.f2245d.f2253b, this.f2242a.f22b);
            c0954y2 = this.f2245d;
            c0954y2.f2253b -= min;
        }
        c0954y2.f2261j.m71i();
        if (z2) {
            try {
                if (min == this.f2242a.f22b) {
                    z3 = true;
                    boolean z4 = z3;
                    C0954y c0954y3 = this.f2245d;
                    c0954y3.f2255d.m1412E(c0954y3.f2254c, z4, this.f2242a, min);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        z3 = false;
        boolean z42 = z3;
        C0954y c0954y32 = this.f2245d;
        c0954y32.f2255d.m1412E(c0954y32.f2254c, z42, this.f2242a, min);
    }
}
