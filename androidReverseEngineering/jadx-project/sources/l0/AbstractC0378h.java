package l0;

import com.guard.wallet.http.C0203h;
import f0.AbstractC0296q;
import f0.C0281b;
import f0.InterfaceC0290k;
import g0.InterfaceC0309a;
import g0.InterfaceC0310b;
import j0.InterfaceC0351a;
import java.util.HashMap;

/* renamed from: l0.h */
/* loaded from: classes.dex */
public abstract class AbstractC0378h extends AbstractC0296q implements InterfaceC0309a {

    /* renamed from: i */
    public String f750i;

    /* renamed from: j */
    public final C0203h f751j = new C0203h(4);

    /* renamed from: k */
    public InterfaceC0290k f752k;

    /* renamed from: l */
    public final C0377g f753l;

    /* renamed from: m */
    public final C0377g f754m;

    /* renamed from: n */
    public String f755n;

    /* renamed from: o */
    public InterfaceC0351a f756o;

    public AbstractC0378h() {
        new HashMap();
        this.f753l = new C0377g(this);
        this.f754m = new C0377g(this);
    }

    @Override // f0.AbstractC0296q, f0.InterfaceC0294o
    /* renamed from: e */
    public final boolean mo780e() {
        return ((C0281b) this.f752k).f501p;
    }

    @Override // f0.AbstractC0296q, f0.InterfaceC0294o
    /* renamed from: h */
    public final void mo783h(InterfaceC0310b interfaceC0310b) {
        ((C0281b) this.f752k).f496k = interfaceC0310b;
    }

    @Override // f0.AbstractC0296q, f0.InterfaceC0294o
    /* renamed from: k */
    public final InterfaceC0310b mo786k() {
        return ((C0281b) this.f752k).f496k;
    }

    /* renamed from: l */
    public abstract void mo947l();

    public final String toString() {
        C0203h c0203h = this.f751j;
        return c0203h == null ? super.toString() : c0203h.m398l(this.f750i);
    }
}
