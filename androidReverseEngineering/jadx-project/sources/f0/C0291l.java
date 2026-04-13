package f0;

import g0.InterfaceC0309a;
import g0.InterfaceC0311c;
import h0.FutureC0326h;
import h0.InterfaceC0320b;
import h0.InterfaceC0325g;
import l0.AbstractC0381k;

/* renamed from: f0.l */
/* loaded from: classes.dex */
public final /* synthetic */ class C0291l implements InterfaceC0311c, InterfaceC0325g, InterfaceC0309a {

    /* renamed from: d */
    public final /* synthetic */ Object f531d;

    public /* synthetic */ C0291l(Object obj) {
        this.f531d = obj;
    }

    @Override // g0.InterfaceC0309a
    /* renamed from: a */
    public final void mo293a(Exception exc) {
        ((AbstractC0381k) this.f531d).mo946g();
    }

    @Override // h0.InterfaceC0325g
    /* renamed from: b */
    public final void mo799b(Exception exc, Object obj, C0299t c0299t) {
        InterfaceC0320b interfaceC0320b = (InterfaceC0320b) this.f531d;
        int i2 = FutureC0326h.f636i;
        interfaceC0320b.mo587a(exc, obj);
    }

    @Override // g0.InterfaceC0311c
    /* renamed from: c */
    public final void mo800c() {
        ((AbstractC0297r) this.f531d).m816e();
    }
}
