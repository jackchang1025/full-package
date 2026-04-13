package a1;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* renamed from: a1.n */
/* loaded from: classes.dex */
public final class C0023n implements InterfaceC0015f {

    /* renamed from: a */
    public final C0014e f42a = new C0014e();

    /* renamed from: b */
    public final InterfaceC0028s f43b;

    /* renamed from: c */
    public boolean f44c;

    public C0023n(InterfaceC0028s interfaceC0028s) {
        this.f43b = interfaceC0028s;
    }

    @Override // a1.InterfaceC0028s
    /* renamed from: a */
    public final C0031v mo66a() {
        return this.f43b.mo66a();
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: c */
    public final InterfaceC0015f mo94c(byte[] bArr, int i2, int i3) {
        if (this.f44c) {
            throw new IllegalStateException("closed");
        }
        this.f42a.m85I(bArr, i2, i3);
        m142x();
        return this;
    }

    @Override // a1.InterfaceC0028s, java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        InterfaceC0028s interfaceC0028s = this.f43b;
        if (this.f44c) {
            return;
        }
        try {
            C0014e c0014e = this.f42a;
            long j2 = c0014e.f22b;
            if (j2 > 0) {
                interfaceC0028s.mo67i(c0014e, j2);
            }
            th = null;
        } catch (Throwable th) {
            th = th;
        }
        try {
            interfaceC0028s.close();
        } catch (Throwable th2) {
            if (th == null) {
                th = th2;
            }
        }
        this.f44c = true;
        if (th == null) {
            return;
        }
        Charset charset = AbstractC0032w.f75a;
        throw th;
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: d */
    public final long mo95d(InterfaceC0029t interfaceC0029t) {
        long j2 = 0;
        while (true) {
            long mo69u = ((C0011b) interfaceC0029t).mo69u(this.f42a, 8192L);
            if (mo69u == -1) {
                return j2;
            }
            j2 += mo69u;
            m142x();
        }
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: e */
    public final InterfaceC0015f mo96e(long j2) {
        if (this.f44c) {
            throw new IllegalStateException("closed");
        }
        this.f42a.m88L(j2);
        m142x();
        return this;
    }

    @Override // a1.InterfaceC0015f, a1.InterfaceC0028s, java.io.Flushable
    public final void flush() {
        if (this.f44c) {
            throw new IllegalStateException("closed");
        }
        C0014e c0014e = this.f42a;
        long j2 = c0014e.f22b;
        InterfaceC0028s interfaceC0028s = this.f43b;
        if (j2 > 0) {
            interfaceC0028s.mo67i(c0014e, j2);
        }
        interfaceC0028s.flush();
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: g */
    public final InterfaceC0015f mo98g(C0017h c0017h) {
        if (this.f44c) {
            throw new IllegalStateException("closed");
        }
        this.f42a.m84H(c0017h);
        m142x();
        return this;
    }

    @Override // a1.InterfaceC0028s
    /* renamed from: i */
    public final void mo67i(C0014e c0014e, long j2) {
        if (this.f44c) {
            throw new IllegalStateException("closed");
        }
        this.f42a.mo67i(c0014e, j2);
        m142x();
    }

    @Override // java.nio.channels.Channel
    public final boolean isOpen() {
        return !this.f44c;
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: j */
    public final InterfaceC0015f mo100j(int i2) {
        if (this.f44c) {
            throw new IllegalStateException("closed");
        }
        this.f42a.m90N(i2);
        m142x();
        return this;
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: k */
    public final InterfaceC0015f mo101k(int i2) {
        if (this.f44c) {
            throw new IllegalStateException("closed");
        }
        this.f42a.m89M(i2);
        m142x();
        return this;
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: o */
    public final InterfaceC0015f mo105o(int i2) {
        if (this.f44c) {
            throw new IllegalStateException("closed");
        }
        this.f42a.m86J(i2);
        m142x();
        return this;
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: p */
    public final InterfaceC0015f mo106p(byte[] bArr) {
        if (this.f44c) {
            throw new IllegalStateException("closed");
        }
        C0014e c0014e = this.f42a;
        c0014e.getClass();
        if (bArr == null) {
            throw new IllegalArgumentException("source == null");
        }
        c0014e.m85I(bArr, 0, bArr.length);
        m142x();
        return this;
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: s */
    public final InterfaceC0015f mo109s(String str) {
        if (this.f44c) {
            throw new IllegalStateException("closed");
        }
        C0014e c0014e = this.f42a;
        c0014e.getClass();
        c0014e.m91O(str, 0, str.length());
        m142x();
        return this;
    }

    @Override // a1.InterfaceC0015f
    /* renamed from: t */
    public final InterfaceC0015f mo110t(long j2) {
        if (this.f44c) {
            throw new IllegalStateException("closed");
        }
        this.f42a.m87K(j2);
        m142x();
        return this;
    }

    public final String toString() {
        return "buffer(" + this.f43b + ")";
    }

    @Override // java.nio.channels.WritableByteChannel
    public final int write(ByteBuffer byteBuffer) {
        if (this.f44c) {
            throw new IllegalStateException("closed");
        }
        int write = this.f42a.write(byteBuffer);
        m142x();
        return write;
    }

    /* renamed from: x */
    public final InterfaceC0015f m142x() {
        if (this.f44c) {
            throw new IllegalStateException("closed");
        }
        C0014e c0014e = this.f42a;
        long j2 = c0014e.f22b;
        if (j2 == 0) {
            j2 = 0;
        } else {
            C0025p c0025p = c0014e.f21a.f54g;
            if (c0025p.f50c < 8192 && c0025p.f52e) {
                j2 -= r6 - c0025p.f49b;
            }
        }
        if (j2 > 0) {
            this.f43b.mo67i(c0014e, j2);
        }
        return this;
    }
}
