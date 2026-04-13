package v0;

import a1.C0014e;
import a1.C0031v;
import a1.InterfaceC0029t;
import java.io.IOException;

/* renamed from: v0.x */
/* loaded from: classes.dex */
public final class C0953x implements InterfaceC0029t {

    /* renamed from: a */
    public final C0014e f2246a = new C0014e();

    /* renamed from: b */
    public final C0014e f2247b = new C0014e();

    /* renamed from: c */
    public final long f2248c;

    /* renamed from: d */
    public boolean f2249d;

    /* renamed from: e */
    public boolean f2250e;

    /* renamed from: f */
    public final /* synthetic */ C0954y f2251f;

    public C0953x(C0954y c0954y, long j2) {
        this.f2251f = c0954y;
        this.f2248c = j2;
    }

    @Override // a1.InterfaceC0029t
    /* renamed from: a */
    public final C0031v mo68a() {
        return this.f2251f.f2260i;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        long j2;
        synchronized (this.f2251f) {
            this.f2249d = true;
            C0014e c0014e = this.f2247b;
            j2 = c0014e.f22b;
            c0014e.m113x();
            this.f2251f.notifyAll();
        }
        if (j2 > 0) {
            this.f2251f.f2255d.m1411D(j2);
        }
        this.f2251f.m1426a();
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0078, code lost:
    
        r11 = -1;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x008d  */
    @Override // a1.InterfaceC0029t
    /* renamed from: u */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long mo69u(C0014e c0014e, long j2) {
        Throwable th;
        long mo69u;
        if (j2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j2);
        }
        while (true) {
            synchronized (this.f2251f) {
                this.f2251f.f2260i.m71i();
                try {
                    C0954y c0954y = this.f2251f;
                    if (c0954y.f2262k != null) {
                        th = c0954y.f2263l;
                        if (th == null) {
                            th = new d0(this.f2251f.f2262k);
                        }
                    } else {
                        th = null;
                    }
                    if (this.f2249d) {
                        throw new IOException("stream closed");
                    }
                    C0014e c0014e2 = this.f2247b;
                    long j3 = c0014e2.f22b;
                    if (j3 > 0) {
                        mo69u = c0014e2.mo69u(c0014e, Math.min(j2, j3));
                        C0954y c0954y2 = this.f2251f;
                        long j4 = c0954y2.f2252a + mo69u;
                        c0954y2.f2252a = j4;
                        if (th == null && j4 >= c0954y2.f2255d.f2217r.m1473d() / 2) {
                            C0954y c0954y3 = this.f2251f;
                            c0954y3.f2255d.m1414G(c0954y3.f2254c, c0954y3.f2252a);
                            this.f2251f.f2252a = 0L;
                        }
                    } else {
                        if (this.f2250e || th != null) {
                            break;
                        }
                        this.f2251f.m1434i();
                    }
                } finally {
                    this.f2251f.f2260i.m1361o();
                }
            }
            if (mo69u == -1) {
                this.f2251f.f2255d.m1411D(mo69u);
                return mo69u;
            }
            if (th == null) {
                return -1L;
            }
            throw th;
        }
        if (mo69u == -1) {
        }
    }
}
