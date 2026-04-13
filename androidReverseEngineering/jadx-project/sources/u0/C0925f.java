package u0;

import a1.C0014e;

/* renamed from: u0.f */
/* loaded from: classes.dex */
public final class C0925f extends AbstractC0920a {

    /* renamed from: d */
    public boolean f2103d;

    public C0925f(C0926g c0926g) {
        super(c0926g);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        if (this.f2089b) {
            return;
        }
        if (!this.f2103d) {
            m1387x();
        }
        this.f2089b = true;
    }

    @Override // u0.AbstractC0920a, a1.InterfaceC0029t
    /* renamed from: u */
    public final long mo69u(C0014e c0014e, long j2) {
        if (j2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j2);
        }
        if (this.f2089b) {
            throw new IllegalStateException("closed");
        }
        if (this.f2103d) {
            return -1L;
        }
        long mo69u = super.mo69u(c0014e, j2);
        if (mo69u != -1) {
            return mo69u;
        }
        this.f2103d = true;
        m1387x();
        return -1L;
    }
}
