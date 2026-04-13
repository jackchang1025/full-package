package t0;

import java.util.List;
import p0.InterfaceC0880v;
import p0.InterfaceC0881w;
import p0.e0;
import p0.f0;
import p0.j0;
import s0.C0902e;
import s0.C0909l;

/* renamed from: t0.f */
/* loaded from: classes.dex */
public final class C0917f implements InterfaceC0880v {

    /* renamed from: a */
    public final List f2070a;

    /* renamed from: b */
    public final C0909l f2071b;

    /* renamed from: c */
    public final C0902e f2072c;

    /* renamed from: d */
    public final int f2073d;

    /* renamed from: e */
    public final f0 f2074e;

    /* renamed from: f */
    public final e0 f2075f;

    /* renamed from: g */
    public final int f2076g;

    /* renamed from: h */
    public final int f2077h;

    /* renamed from: i */
    public final int f2078i;

    /* renamed from: j */
    public int f2079j;

    public C0917f(List list, C0909l c0909l, C0902e c0902e, int i2, f0 f0Var, e0 e0Var, int i3, int i4, int i5) {
        this.f2070a = list;
        this.f2071b = c0909l;
        this.f2072c = c0902e;
        this.f2073d = i2;
        this.f2074e = f0Var;
        this.f2075f = e0Var;
        this.f2076g = i3;
        this.f2077h = i4;
        this.f2078i = i5;
    }

    /* renamed from: a */
    public final j0 m1381a(f0 f0Var) {
        return m1382b(f0Var, this.f2071b, this.f2072c);
    }

    /* renamed from: b */
    public final j0 m1382b(f0 f0Var, C0909l c0909l, C0902e c0902e) {
        List list = this.f2070a;
        int size = list.size();
        int i2 = this.f2073d;
        if (i2 >= size) {
            throw new AssertionError();
        }
        this.f2079j++;
        C0902e c0902e2 = this.f2072c;
        if (c0902e2 != null && !c0902e2.m1341a().m1357j(f0Var.f1777a)) {
            throw new IllegalStateException("network interceptor " + list.get(i2 - 1) + " must retain the same host and port");
        }
        if (c0902e2 != null && this.f2079j > 1) {
            throw new IllegalStateException("network interceptor " + list.get(i2 - 1) + " must call proceed() exactly once");
        }
        int i3 = i2 + 1;
        C0917f c0917f = new C0917f(this.f2070a, c0909l, c0902e, i3, f0Var, this.f2075f, this.f2076g, this.f2077h, this.f2078i);
        InterfaceC0881w interfaceC0881w = (InterfaceC0881w) list.get(i2);
        j0 mo1300a = interfaceC0881w.mo1300a(c0917f);
        if (c0902e != null && i3 < list.size() && c0917f.f2079j != 1) {
            throw new IllegalStateException("network interceptor " + interfaceC0881w + " must call proceed() exactly once");
        }
        if (mo1300a == null) {
            throw new NullPointerException("interceptor " + interfaceC0881w + " returned null");
        }
        if (mo1300a.f1835g != null) {
            return mo1300a;
        }
        throw new IllegalStateException("interceptor " + interfaceC0881w + " returned a response with no body");
    }

    public final void finalize() {
        List list = this.f2070a;
        if (!list.isEmpty()) {
            list.clear();
        }
        super.finalize();
    }
}
