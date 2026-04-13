package f0;

import a1.AbstractC0026q;
import com.guard.wallet.http.C0203h;
import g0.InterfaceC0309a;
import g0.InterfaceC0310b;

/* renamed from: f0.q */
/* loaded from: classes.dex */
public abstract class AbstractC0296q implements InterfaceC0310b, InterfaceC0294o {

    /* renamed from: d */
    public boolean f543d;

    /* renamed from: e */
    public InterfaceC0309a f544e;

    /* renamed from: f */
    public InterfaceC0310b f545f;

    /* renamed from: g */
    public InterfaceC0294o f546g;

    /* renamed from: h */
    public boolean f547h;

    @Override // g0.InterfaceC0310b
    /* renamed from: b */
    public void mo294b(InterfaceC0294o interfaceC0294o, C0292m c0292m) {
        if (this.f547h) {
            c0292m.m811k();
        } else {
            AbstractC0026q.m183p(this, c0292m);
        }
    }

    /* renamed from: c */
    public void mo813c(Exception exc) {
        if (this.f543d) {
            return;
        }
        this.f543d = true;
        InterfaceC0309a interfaceC0309a = this.f544e;
        if (interfaceC0309a != null) {
            interfaceC0309a.mo293a(exc);
        }
    }

    @Override // f0.InterfaceC0294o
    public final void close() {
        this.f547h = true;
        InterfaceC0294o interfaceC0294o = this.f546g;
        if (interfaceC0294o != null) {
            interfaceC0294o.close();
        }
    }

    @Override // f0.InterfaceC0294o
    /* renamed from: e */
    public boolean mo780e() {
        return this.f546g.mo780e();
    }

    @Override // f0.InterfaceC0294o
    /* renamed from: g */
    public final String mo782g() {
        InterfaceC0294o interfaceC0294o = this.f546g;
        if (interfaceC0294o == null) {
            return null;
        }
        return interfaceC0294o.mo782g();
    }

    @Override // f0.InterfaceC0294o
    /* renamed from: h */
    public void mo783h(InterfaceC0310b interfaceC0310b) {
        this.f545f = interfaceC0310b;
    }

    /* renamed from: i */
    public final void m814i(InterfaceC0294o interfaceC0294o) {
        InterfaceC0294o interfaceC0294o2 = this.f546g;
        if (interfaceC0294o2 != null) {
            interfaceC0294o2.mo783h(null);
        }
        this.f546g = interfaceC0294o;
        interfaceC0294o.mo783h(this);
        this.f546g.mo785j(new C0203h(this, 3));
    }

    @Override // f0.InterfaceC0294o
    /* renamed from: j */
    public final void mo785j(InterfaceC0309a interfaceC0309a) {
        this.f544e = interfaceC0309a;
    }

    @Override // f0.InterfaceC0294o
    /* renamed from: k */
    public InterfaceC0310b mo786k() {
        return this.f545f;
    }
}
