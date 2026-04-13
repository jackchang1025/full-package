package p0;

import java.io.Closeable;
import s0.C0902e;

/* loaded from: classes.dex */
public final class j0 implements Closeable {

    /* renamed from: a */
    public final f0 f1829a;

    /* renamed from: b */
    public final c0 f1830b;

    /* renamed from: c */
    public final int f1831c;

    /* renamed from: d */
    public final String f1832d;

    /* renamed from: e */
    public final C0876r f1833e;

    /* renamed from: f */
    public final C0877s f1834f;

    /* renamed from: g */
    public final l0 f1835g;

    /* renamed from: h */
    public final j0 f1836h;

    /* renamed from: i */
    public final j0 f1837i;

    /* renamed from: j */
    public final j0 f1838j;

    /* renamed from: k */
    public final long f1839k;

    /* renamed from: l */
    public final long f1840l;

    /* renamed from: m */
    public final C0902e f1841m;

    public j0(i0 i0Var) {
        this.f1829a = i0Var.f1812a;
        this.f1830b = i0Var.f1813b;
        this.f1831c = i0Var.f1814c;
        this.f1832d = i0Var.f1815d;
        this.f1833e = i0Var.f1816e;
        C0864f c0864f = i0Var.f1817f;
        c0864f.getClass();
        this.f1834f = new C0877s(c0864f);
        this.f1835g = i0Var.f1818g;
        this.f1836h = i0Var.f1819h;
        this.f1837i = i0Var.f1820i;
        this.f1838j = i0Var.f1821j;
        this.f1839k = i0Var.f1822k;
        this.f1840l = i0Var.f1823l;
        this.f1841m = i0Var.f1824m;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        l0 l0Var = this.f1835g;
        if (l0Var == null) {
            throw new IllegalStateException("response is not eligible for a body and must not be closed");
        }
        l0Var.close();
    }

    public final String toString() {
        return "Response{protocol=" + this.f1830b + ", code=" + this.f1831c + ", message=" + this.f1832d + ", url=" + this.f1829a.f1777a + '}';
    }

    /* renamed from: x */
    public final String m1265x(String str, String str2) {
        String m1280c = this.f1834f.m1280c(str);
        return m1280c != null ? m1280c : str2;
    }
}
