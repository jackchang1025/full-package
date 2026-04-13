package u0;

import a1.C0014e;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import p0.C0879u;
import q0.AbstractC0887c;
import t0.AbstractC0916e;

/* renamed from: u0.c */
/* loaded from: classes.dex */
public final class C0922c extends AbstractC0920a {

    /* renamed from: d */
    public final C0879u f2094d;

    /* renamed from: e */
    public long f2095e;

    /* renamed from: f */
    public boolean f2096f;

    /* renamed from: g */
    public final /* synthetic */ C0926g f2097g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0922c(C0926g c0926g, C0879u c0879u) {
        super(c0926g);
        this.f2097g = c0926g;
        this.f2095e = -1L;
        this.f2096f = true;
        this.f2094d = c0879u;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        boolean z2;
        if (this.f2089b) {
            return;
        }
        if (this.f2096f) {
            try {
                z2 = AbstractC0887c.m1320q(this, 100, TimeUnit.MILLISECONDS);
            } catch (IOException unused) {
                z2 = false;
            }
            if (!z2) {
                this.f2097g.f2105b.m1355h();
                m1387x();
            }
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
        if (!this.f2096f) {
            return -1L;
        }
        long j3 = this.f2095e;
        C0926g c0926g = this.f2097g;
        if (j3 == 0 || j3 == -1) {
            if (j3 != -1) {
                c0926g.f2106c.mo102l();
            }
            try {
                this.f2095e = c0926g.f2106c.mo111v();
                String trim = c0926g.f2106c.mo102l().trim();
                if (this.f2095e < 0 || !(trim.isEmpty() || trim.startsWith(";"))) {
                    throw new ProtocolException("expected chunk size and optional extensions but was \"" + this.f2095e + trim + "\"");
                }
                if (this.f2095e == 0) {
                    this.f2096f = false;
                    AbstractC0916e.m1379d(c0926g.f2104a.f1726h, this.f2094d, c0926g.m1390k());
                    m1387x();
                }
                if (!this.f2096f) {
                    return -1L;
                }
            } catch (NumberFormatException e2) {
                throw new ProtocolException(e2.getMessage());
            }
        }
        long mo69u = super.mo69u(c0014e, Math.min(j2, this.f2095e));
        if (mo69u != -1) {
            this.f2095e -= mo69u;
            return mo69u;
        }
        c0926g.f2105b.m1355h();
        ProtocolException protocolException = new ProtocolException("unexpected end of stream");
        m1387x();
        throw protocolException;
    }
}
