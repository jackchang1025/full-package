package p0;

import s0.C0902e;

/* loaded from: classes.dex */
public final class i0 {

    /* renamed from: a */
    public f0 f1812a;

    /* renamed from: b */
    public c0 f1813b;

    /* renamed from: c */
    public int f1814c;

    /* renamed from: d */
    public String f1815d;

    /* renamed from: e */
    public C0876r f1816e;

    /* renamed from: f */
    public C0864f f1817f;

    /* renamed from: g */
    public l0 f1818g;

    /* renamed from: h */
    public j0 f1819h;

    /* renamed from: i */
    public j0 f1820i;

    /* renamed from: j */
    public j0 f1821j;

    /* renamed from: k */
    public long f1822k;

    /* renamed from: l */
    public long f1823l;

    /* renamed from: m */
    public C0902e f1824m;

    public i0() {
        this.f1814c = -1;
        this.f1817f = new C0864f();
    }

    /* renamed from: b */
    public static void m1259b(String str, j0 j0Var) {
        if (j0Var.f1835g != null) {
            throw new IllegalArgumentException(str.concat(".body != null"));
        }
        if (j0Var.f1836h != null) {
            throw new IllegalArgumentException(str.concat(".networkResponse != null"));
        }
        if (j0Var.f1837i != null) {
            throw new IllegalArgumentException(str.concat(".cacheResponse != null"));
        }
        if (j0Var.f1838j != null) {
            throw new IllegalArgumentException(str.concat(".priorResponse != null"));
        }
    }

    /* renamed from: a */
    public final j0 m1260a() {
        if (this.f1812a == null) {
            throw new IllegalStateException("request == null");
        }
        if (this.f1813b == null) {
            throw new IllegalStateException("protocol == null");
        }
        if (this.f1814c >= 0) {
            if (this.f1815d != null) {
                return new j0(this);
            }
            throw new IllegalStateException("message == null");
        }
        throw new IllegalStateException("code < 0: " + this.f1814c);
    }

    public i0(j0 j0Var) {
        this.f1814c = -1;
        this.f1812a = j0Var.f1829a;
        this.f1813b = j0Var.f1830b;
        this.f1814c = j0Var.f1831c;
        this.f1815d = j0Var.f1832d;
        this.f1816e = j0Var.f1833e;
        this.f1817f = j0Var.f1834f.m1282e();
        this.f1818g = j0Var.f1835g;
        this.f1819h = j0Var.f1836h;
        this.f1820i = j0Var.f1837i;
        this.f1821j = j0Var.f1838j;
        this.f1822k = j0Var.f1839k;
        this.f1823l = j0Var.f1840l;
        this.f1824m = j0Var.f1841m;
    }
}
