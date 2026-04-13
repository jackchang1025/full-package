package s0;

import java.io.IOException;
import p0.C0875q;
import p0.e0;
import p0.i0;
import t0.InterfaceC0913b;
import v0.C0930a;
import v0.EnumC0931b;
import v0.d0;

/* renamed from: s0.e */
/* loaded from: classes.dex */
public final class C0902e {

    /* renamed from: a */
    public final C0909l f2011a;

    /* renamed from: b */
    public final C0875q f2012b;

    /* renamed from: c */
    public final C0903f f2013c;

    /* renamed from: d */
    public final InterfaceC0913b f2014d;

    /* renamed from: e */
    public boolean f2015e;

    public C0902e(C0909l c0909l, e0 e0Var, C0875q c0875q, C0903f c0903f, InterfaceC0913b interfaceC0913b) {
        this.f2011a = c0909l;
        this.f2012b = c0875q;
        this.f2013c = c0903f;
        this.f2014d = interfaceC0913b;
    }

    /* renamed from: a */
    public final C0904g m1341a() {
        return this.f2014d.mo1375h();
    }

    /* renamed from: b */
    public final i0 m1342b(boolean z2) {
        try {
            i0 mo1374g = this.f2014d.mo1374g(z2);
            if (mo1374g != null) {
                C0875q.f1891c.getClass();
                mo1374g.f1824m = this;
            }
            return mo1374g;
        } catch (IOException e2) {
            this.f2012b.getClass();
            m1343c(e2);
            throw e2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0023, code lost:
    
        if (r6 > 1) goto L15;
     */
    /* renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m1343c(IOException iOException) {
        C0903f c0903f = this.f2013c;
        synchronized (c0903f.f2018c) {
            c0903f.f2023h = true;
        }
        C0904g mo1375h = this.f2014d.mo1375h();
        synchronized (mo1375h.f2025b) {
            if (iOException instanceof d0) {
                EnumC0931b enumC0931b = ((d0) iOException).f2152a;
                if (enumC0931b == EnumC0931b.REFUSED_STREAM) {
                    int i2 = mo1375h.f2037n + 1;
                    mo1375h.f2037n = i2;
                } else if (enumC0931b != EnumC0931b.CANCEL) {
                    mo1375h.f2034k = true;
                    mo1375h.f2035l++;
                }
            } else {
                if (!(mo1375h.f2031h != null) || (iOException instanceof C0930a)) {
                    mo1375h.f2034k = true;
                    if (mo1375h.f2036m == 0) {
                        if (iOException != null) {
                            mo1375h.f2025b.m1358a(mo1375h.f2026c, iOException);
                        }
                        mo1375h.f2035l++;
                    }
                }
            }
        }
    }
}
