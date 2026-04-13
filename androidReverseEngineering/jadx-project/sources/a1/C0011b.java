package a1;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import s0.C0907j;

/* renamed from: a1.b */
/* loaded from: classes.dex */
public final class C0011b implements InterfaceC0029t {

    /* renamed from: a */
    public final /* synthetic */ int f11a = 1;

    /* renamed from: b */
    public final /* synthetic */ Object f12b;

    /* renamed from: c */
    public final /* synthetic */ Object f13c;

    public C0011b(C0031v c0031v, InputStream inputStream) {
        this.f12b = c0031v;
        this.f13c = inputStream;
    }

    @Override // a1.InterfaceC0029t
    /* renamed from: a */
    public final C0031v mo68a() {
        switch (this.f11a) {
            case 0:
                return (C0013d) this.f13c;
            default:
                return (C0031v) this.f12b;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        int i2 = this.f11a;
        Object obj = this.f13c;
        switch (i2) {
            case 0:
                C0013d c0013d = (C0013d) obj;
                c0013d.m71i();
                try {
                    try {
                        ((InterfaceC0029t) this.f12b).close();
                        c0013d.m73k(true);
                        return;
                    } catch (IOException e2) {
                        throw ((C0013d) obj).m72j(e2);
                    }
                } catch (Throwable th) {
                    c0013d.m73k(false);
                    throw th;
                }
            default:
                ((InputStream) obj).close();
                return;
        }
    }

    public final String toString() {
        switch (this.f11a) {
            case 0:
                return "AsyncTimeout.source(" + ((InterfaceC0029t) this.f12b) + ")";
            default:
                return "source(" + ((InputStream) this.f13c) + ")";
        }
    }

    @Override // a1.InterfaceC0029t
    /* renamed from: u */
    public final long mo69u(C0014e c0014e, long j2) {
        boolean z2 = false;
        int i2 = this.f11a;
        Object obj = this.f12b;
        Object obj2 = this.f13c;
        switch (i2) {
            case 0:
                C0013d c0013d = (C0013d) obj2;
                c0013d.m71i();
                try {
                    try {
                        long mo69u = ((InterfaceC0029t) obj).mo69u(c0014e, j2);
                        c0013d.m73k(true);
                        return mo69u;
                    } catch (IOException e2) {
                        throw ((C0013d) obj2).m72j(e2);
                    }
                } catch (Throwable th) {
                    c0013d.m73k(false);
                    throw th;
                }
            default:
                if (j2 < 0) {
                    throw new IllegalArgumentException("byteCount < 0: " + j2);
                }
                if (j2 == 0) {
                    return 0L;
                }
                try {
                    ((C0031v) obj).mo135f();
                    C0025p m83G = c0014e.m83G(1);
                    int read = ((InputStream) obj2).read(m83G.f48a, m83G.f50c, (int) Math.min(j2, 8192 - m83G.f50c));
                    if (read == -1) {
                        if (m83G.f49b == m83G.f50c) {
                            c0014e.f21a = m83G.m146a();
                            AbstractC0026q.m161L(m83G);
                        }
                        return -1L;
                    }
                    m83G.f50c += read;
                    long j3 = read;
                    c0014e.f22b += j3;
                    return j3;
                } catch (AssertionError e3) {
                    Logger logger = AbstractC0021l.f38a;
                    if (e3.getCause() != null && e3.getMessage() != null && e3.getMessage().contains("getsockname failed")) {
                        z2 = true;
                    }
                    if (z2) {
                        throw new IOException(e3);
                    }
                    throw e3;
                }
        }
    }

    public C0011b(C0907j c0907j, C0011b c0011b) {
        this.f13c = c0907j;
        this.f12b = c0011b;
    }
}
