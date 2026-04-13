package l0;

import f0.C0281b;
import g0.InterfaceC0309a;

/* renamed from: l0.a */
/* loaded from: classes.dex */
public final class C0371a implements InterfaceC0309a {

    /* renamed from: d */
    public final /* synthetic */ C0374d f731d;

    public /* synthetic */ C0371a(C0374d c0374d) {
        this.f731d = c0374d;
    }

    @Override // g0.InterfaceC0309a
    /* renamed from: a */
    public final void mo293a(Exception exc) {
        C0374d c0374d = this.f731d;
        ((C0281b) c0374d.f752k).m791p();
        if (exc != null) {
            c0374d.mo813c(exc);
        } else {
            c0374d.f741w = true;
            c0374d.mo947l();
        }
    }
}
