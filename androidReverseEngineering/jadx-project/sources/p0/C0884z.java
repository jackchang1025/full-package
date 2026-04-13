package p0;

import a1.AbstractC0026q;
import a1.C0014e;
import a1.C0017h;
import a1.InterfaceC0015f;
import java.util.List;
import q0.AbstractC0887c;

/* renamed from: p0.z */
/* loaded from: classes.dex */
public final class C0884z extends AbstractC0026q {

    /* renamed from: s */
    public static final C0882x f1922s = C0882x.m1301a("multipart/mixed");

    /* renamed from: t */
    public static final C0882x f1923t;

    /* renamed from: u */
    public static final byte[] f1924u;

    /* renamed from: v */
    public static final byte[] f1925v;

    /* renamed from: w */
    public static final byte[] f1926w;

    /* renamed from: o */
    public final C0017h f1927o;

    /* renamed from: p */
    public final C0882x f1928p;

    /* renamed from: q */
    public final List f1929q;

    /* renamed from: r */
    public long f1930r = -1;

    static {
        C0882x.m1301a("multipart/alternative");
        C0882x.m1301a("multipart/digest");
        C0882x.m1301a("multipart/parallel");
        f1923t = C0882x.m1301a("multipart/form-data");
        f1924u = new byte[]{58, 32};
        f1925v = new byte[]{13, 10};
        f1926w = new byte[]{45, 45};
    }

    public C0884z(C0017h c0017h, C0882x c0882x, List list) {
        this.f1927o = c0017h;
        this.f1928p = C0882x.m1301a(c0882x + "; boundary=" + c0017h.mo128m());
        this.f1929q = AbstractC0887c.m1314k(list);
    }

    /* renamed from: W */
    public static void m1302W(StringBuilder sb, String str) {
        String str2;
        sb.append('\"');
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt == '\n') {
                str2 = "%0A";
            } else if (charAt == '\r') {
                str2 = "%0D";
            } else if (charAt != '\"') {
                sb.append(charAt);
            } else {
                str2 = "%22";
            }
            sb.append(str2);
        }
        sb.append('\"');
    }

    @Override // a1.AbstractC0026q
    /* renamed from: V */
    public final void mo194V(InterfaceC0015f interfaceC0015f) {
        m1303X(interfaceC0015f, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: X */
    public final long m1303X(InterfaceC0015f interfaceC0015f, boolean z2) {
        C0014e c0014e;
        InterfaceC0015f interfaceC0015f2;
        if (z2) {
            interfaceC0015f2 = new C0014e();
            c0014e = interfaceC0015f2;
        } else {
            c0014e = 0;
            interfaceC0015f2 = interfaceC0015f;
        }
        List list = this.f1929q;
        int size = list.size();
        long j2 = 0;
        int i2 = 0;
        while (true) {
            C0017h c0017h = this.f1927o;
            byte[] bArr = f1926w;
            byte[] bArr2 = f1925v;
            if (i2 >= size) {
                interfaceC0015f2.mo106p(bArr);
                interfaceC0015f2.mo98g(c0017h);
                interfaceC0015f2.mo106p(bArr);
                interfaceC0015f2.mo106p(bArr2);
                if (!z2) {
                    return j2;
                }
                long j3 = j2 + c0014e.f22b;
                c0014e.m113x();
                return j3;
            }
            C0883y c0883y = (C0883y) list.get(i2);
            C0877s c0877s = c0883y.f1920a;
            interfaceC0015f2.mo106p(bArr);
            interfaceC0015f2.mo98g(c0017h);
            interfaceC0015f2.mo106p(bArr2);
            if (c0877s != null) {
                int length = c0877s.f1896a.length / 2;
                for (int i3 = 0; i3 < length; i3++) {
                    interfaceC0015f2.mo109s(c0877s.m1281d(i3)).mo106p(f1924u).mo109s(c0877s.m1283f(i3)).mo106p(bArr2);
                }
            }
            AbstractC0026q abstractC0026q = c0883y.f1921b;
            C0882x mo197j = abstractC0026q.mo197j();
            if (mo197j != null) {
                interfaceC0015f2.mo109s("Content-Type: ").mo109s(mo197j.f1917a).mo106p(bArr2);
            }
            long mo196i = abstractC0026q.mo196i();
            if (mo196i != -1) {
                interfaceC0015f2.mo109s("Content-Length: ").mo110t(mo196i).mo106p(bArr2);
            } else if (z2) {
                c0014e.m113x();
                return -1L;
            }
            interfaceC0015f2.mo106p(bArr2);
            if (z2) {
                j2 += mo196i;
            } else {
                abstractC0026q.mo194V(interfaceC0015f2);
            }
            interfaceC0015f2.mo106p(bArr2);
            i2++;
        }
    }

    @Override // a1.AbstractC0026q
    /* renamed from: i */
    public final long mo196i() {
        long j2 = this.f1930r;
        if (j2 != -1) {
            return j2;
        }
        long m1303X = m1303X(null, true);
        this.f1930r = m1303X;
        return m1303X;
    }

    @Override // a1.AbstractC0026q
    /* renamed from: j */
    public final C0882x mo197j() {
        return this.f1928p;
    }
}
