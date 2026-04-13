package u0;

import a1.C0014e;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import q0.AbstractC0887c;

/* renamed from: u0.d */
/* loaded from: classes.dex */
public final class C0923d extends AbstractC0920a {

    /* renamed from: d */
    public long f2098d;

    /* renamed from: e */
    public final /* synthetic */ C0926g f2099e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0923d(C0926g c0926g, long j2) {
        super(c0926g);
        this.f2099e = c0926g;
        this.f2098d = j2;
        if (j2 == 0) {
            m1387x();
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        boolean z2;
        if (this.f2089b) {
            return;
        }
        if (this.f2098d != 0) {
            try {
                z2 = AbstractC0887c.m1320q(this, 100, TimeUnit.MILLISECONDS);
            } catch (IOException unused) {
                z2 = false;
            }
            if (!z2) {
                this.f2099e.f2105b.m1355h();
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
        long j3 = this.f2098d;
        if (j3 == 0) {
            return -1L;
        }
        long mo69u = super.mo69u(c0014e, Math.min(j3, j2));
        if (mo69u == -1) {
            this.f2099e.f2105b.m1355h();
            ProtocolException protocolException = new ProtocolException("unexpected end of stream");
            m1387x();
            throw protocolException;
        }
        long j4 = this.f2098d - mo69u;
        this.f2098d = j4;
        if (j4 == 0) {
            m1387x();
        }
        return mo69u;
    }
}
