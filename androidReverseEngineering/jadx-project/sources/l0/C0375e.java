package l0;

import b0.C0078b;
import f0.C0281b;
import f0.C0299t;
import f0.InterfaceC0290k;
import g0.InterfaceC0309a;

/* renamed from: l0.e */
/* loaded from: classes.dex */
public final class C0375e implements InterfaceC0309a {

    /* renamed from: d */
    public final /* synthetic */ C0376f f744d;

    public C0375e(C0376f c0376f) {
        this.f744d = c0376f;
    }

    @Override // g0.InterfaceC0309a
    /* renamed from: a */
    public final void mo293a(Exception exc) {
        InterfaceC0309a interfaceC0309a = this.f744d.f748d;
        if (interfaceC0309a != null) {
            interfaceC0309a.mo293a(exc);
        }
    }

    /* renamed from: b */
    public final void m950b(InterfaceC0290k interfaceC0290k) {
        C0374d c0374d = new C0374d(this, interfaceC0290k);
        c0374d.f752k = interfaceC0290k;
        C0299t c0299t = new C0299t(0);
        C0281b c0281b = (C0281b) c0374d.f752k;
        c0281b.f496k = c0299t;
        c0299t.f555g = c0374d.f754m;
        c0281b.f500o = new C0078b(23);
        ((C0281b) interfaceC0290k).m791p();
    }
}
