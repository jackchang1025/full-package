package u0;

import a1.C0014e;
import a1.C0018i;
import a1.C0031v;
import a1.InterfaceC0015f;
import a1.InterfaceC0028s;

/* renamed from: u0.b */
/* loaded from: classes.dex */
public final class C0921b implements InterfaceC0028s {

    /* renamed from: a */
    public final C0018i f2091a;

    /* renamed from: b */
    public boolean f2092b;

    /* renamed from: c */
    public final /* synthetic */ C0926g f2093c;

    public C0921b(C0926g c0926g) {
        this.f2093c = c0926g;
        this.f2091a = new C0018i(c0926g.f2107d.mo66a());
    }

    @Override // a1.InterfaceC0028s
    /* renamed from: a */
    public final C0031v mo66a() {
        return this.f2091a;
    }

    @Override // a1.InterfaceC0028s, java.io.Closeable, java.lang.AutoCloseable
    public final synchronized void close() {
        if (this.f2092b) {
            return;
        }
        this.f2092b = true;
        this.f2093c.f2107d.mo109s("0\r\n\r\n");
        C0926g c0926g = this.f2093c;
        C0018i c0018i = this.f2091a;
        c0926g.getClass();
        C0031v c0031v = c0018i.f28e;
        c0018i.f28e = C0031v.f71d;
        c0031v.mo130a();
        c0031v.mo131b();
        this.f2093c.f2108e = 3;
    }

    @Override // a1.InterfaceC0028s, java.io.Flushable
    public final synchronized void flush() {
        if (this.f2092b) {
            return;
        }
        this.f2093c.f2107d.flush();
    }

    @Override // a1.InterfaceC0028s
    /* renamed from: i */
    public final void mo67i(C0014e c0014e, long j2) {
        if (this.f2092b) {
            throw new IllegalStateException("closed");
        }
        if (j2 == 0) {
            return;
        }
        C0926g c0926g = this.f2093c;
        c0926g.f2107d.mo96e(j2);
        InterfaceC0015f interfaceC0015f = c0926g.f2107d;
        interfaceC0015f.mo109s("\r\n");
        interfaceC0015f.mo67i(c0014e, j2);
        interfaceC0015f.mo109s("\r\n");
    }
}
