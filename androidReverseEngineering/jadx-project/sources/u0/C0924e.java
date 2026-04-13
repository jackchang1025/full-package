package u0;

import a1.C0014e;
import a1.C0018i;
import a1.C0031v;
import a1.InterfaceC0028s;
import q0.AbstractC0887c;

/* renamed from: u0.e */
/* loaded from: classes.dex */
public final class C0924e implements InterfaceC0028s {

    /* renamed from: a */
    public final C0018i f2100a;

    /* renamed from: b */
    public boolean f2101b;

    /* renamed from: c */
    public final /* synthetic */ C0926g f2102c;

    public C0924e(C0926g c0926g) {
        this.f2102c = c0926g;
        this.f2100a = new C0018i(c0926g.f2107d.mo66a());
    }

    @Override // a1.InterfaceC0028s
    /* renamed from: a */
    public final C0031v mo66a() {
        return this.f2100a;
    }

    @Override // a1.InterfaceC0028s, java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        if (this.f2101b) {
            return;
        }
        this.f2101b = true;
        C0926g c0926g = this.f2102c;
        c0926g.getClass();
        C0018i c0018i = this.f2100a;
        C0031v c0031v = c0018i.f28e;
        c0018i.f28e = C0031v.f71d;
        c0031v.mo130a();
        c0031v.mo131b();
        c0926g.f2108e = 3;
    }

    @Override // a1.InterfaceC0028s, java.io.Flushable
    public final void flush() {
        if (this.f2101b) {
            return;
        }
        this.f2102c.f2107d.flush();
    }

    @Override // a1.InterfaceC0028s
    /* renamed from: i */
    public final void mo67i(C0014e c0014e, long j2) {
        if (this.f2101b) {
            throw new IllegalStateException("closed");
        }
        long j3 = c0014e.f22b;
        byte[] bArr = AbstractC0887c.f1934a;
        if ((0 | j2) < 0 || 0 > j3 || j3 - 0 < j2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.f2102c.f2107d.mo67i(c0014e, j2);
    }
}
