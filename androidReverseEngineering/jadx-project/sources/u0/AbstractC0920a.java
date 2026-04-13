package u0;

import a1.C0014e;
import a1.C0018i;
import a1.C0031v;
import a1.InterfaceC0029t;
import java.io.IOException;

/* renamed from: u0.a */
/* loaded from: classes.dex */
public abstract class AbstractC0920a implements InterfaceC0029t {

    /* renamed from: a */
    public final C0018i f2088a;

    /* renamed from: b */
    public boolean f2089b;

    /* renamed from: c */
    public final /* synthetic */ C0926g f2090c;

    public AbstractC0920a(C0926g c0926g) {
        this.f2090c = c0926g;
        this.f2088a = new C0018i(c0926g.f2106c.mo68a());
    }

    @Override // a1.InterfaceC0029t
    /* renamed from: a */
    public final C0031v mo68a() {
        return this.f2088a;
    }

    @Override // a1.InterfaceC0029t
    /* renamed from: u */
    public long mo69u(C0014e c0014e, long j2) {
        C0926g c0926g = this.f2090c;
        try {
            return c0926g.f2106c.mo69u(c0014e, j2);
        } catch (IOException e2) {
            c0926g.f2105b.m1355h();
            m1387x();
            throw e2;
        }
    }

    /* renamed from: x */
    public final void m1387x() {
        C0926g c0926g = this.f2090c;
        int i2 = c0926g.f2108e;
        if (i2 == 6) {
            return;
        }
        if (i2 != 5) {
            throw new IllegalStateException("state: " + c0926g.f2108e);
        }
        C0018i c0018i = this.f2088a;
        C0031v c0031v = c0018i.f28e;
        c0018i.f28e = C0031v.f71d;
        c0031v.mo130a();
        c0031v.mo131b();
        c0926g.f2108e = 6;
    }
}
