package r0;

import a1.AbstractC0021l;
import a1.AbstractC0026q;
import a1.C0019j;
import a1.C0024o;
import com.guard.wallet.thread.C0241j;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import l0.C0383m;
import p0.C0862d;
import p0.C0864f;
import p0.C0871m;
import p0.C0875q;
import p0.C0877s;
import p0.C0879u;
import p0.C0882x;
import p0.InterfaceC0872n;
import p0.InterfaceC0881w;
import p0.c0;
import p0.f0;
import p0.i0;
import p0.j0;
import p0.k0;
import p000a.AbstractC0000a;
import q0.AbstractC0887c;
import t0.AbstractC0916e;
import t0.C0917f;

/* renamed from: r0.a */
/* loaded from: classes.dex */
public final class C0895a implements InterfaceC0881w {

    /* renamed from: a */
    public final /* synthetic */ int f1986a;

    /* renamed from: b */
    public final Object f1987b;

    public /* synthetic */ C0895a(InterfaceC0872n interfaceC0872n, int i2) {
        this.f1986a = i2;
        this.f1987b = interfaceC0872n;
    }

    /* renamed from: b */
    public static boolean m1327b(String str) {
        return "Content-Length".equalsIgnoreCase(str) || "Content-Encoding".equalsIgnoreCase(str) || "Content-Type".equalsIgnoreCase(str);
    }

    /* renamed from: c */
    public static boolean m1328c(String str) {
        return ("Connection".equalsIgnoreCase(str) || "Keep-Alive".equalsIgnoreCase(str) || "Proxy-Authenticate".equalsIgnoreCase(str) || "Proxy-Authorization".equalsIgnoreCase(str) || "TE".equalsIgnoreCase(str) || "Trailers".equalsIgnoreCase(str) || "Transfer-Encoding".equalsIgnoreCase(str) || "Upgrade".equalsIgnoreCase(str)) ? false : true;
    }

    /* renamed from: d */
    public static j0 m1329d(j0 j0Var) {
        if (j0Var == null || j0Var.f1835g == null) {
            return j0Var;
        }
        i0 i0Var = new i0(j0Var);
        i0Var.f1818g = null;
        return i0Var.m1260a();
    }

    @Override // p0.InterfaceC0881w
    /* renamed from: a */
    public final j0 mo1300a(C0917f c0917f) {
        boolean z2;
        boolean z3;
        int i2 = this.f1986a;
        Object obj = this.f1987b;
        switch (i2) {
            case 0:
                AbstractC0000a.m27w(obj);
                System.currentTimeMillis();
                f0 f0Var = c0917f.f2074e;
                C0241j c0241j = new C0241j(f0Var, null);
                if (((f0) c0241j.f387e) != null) {
                    C0862d c0862d = f0Var.f1782f;
                    if (c0862d == null) {
                        c0862d = C0862d.m1244a(f0Var.f1779c);
                        f0Var.f1782f = c0862d;
                    }
                    if (c0862d.f1764j) {
                        c0241j = new C0241j(null, null);
                    }
                }
                f0 f0Var2 = (f0) c0241j.f387e;
                j0 j0Var = (j0) c0241j.f388f;
                AbstractC0000a.m27w(obj);
                if (f0Var2 == null && j0Var == null) {
                    i0 i0Var = new i0();
                    i0Var.f1812a = c0917f.f2074e;
                    i0Var.f1813b = c0.HTTP_1_1;
                    i0Var.f1814c = 504;
                    i0Var.f1815d = "Unsatisfiable Request (only-if-cached)";
                    i0Var.f1818g = AbstractC0887c.f1937d;
                    i0Var.f1822k = -1L;
                    i0Var.f1823l = System.currentTimeMillis();
                    return i0Var.m1260a();
                }
                if (f0Var2 == null) {
                    j0Var.getClass();
                    i0 i0Var2 = new i0(j0Var);
                    j0 m1329d = m1329d(j0Var);
                    if (m1329d != null) {
                        i0.m1259b("cacheResponse", m1329d);
                    }
                    i0Var2.f1820i = m1329d;
                    return i0Var2.m1260a();
                }
                j0 m1381a = c0917f.m1381a(f0Var2);
                if (j0Var != null) {
                    if (m1381a.f1831c == 304) {
                        i0 i0Var3 = new i0(j0Var);
                        ArrayList arrayList = new ArrayList(20);
                        C0877s c0877s = j0Var.f1834f;
                        int length = c0877s.f1896a.length / 2;
                        int i3 = 0;
                        while (true) {
                            C0877s c0877s2 = m1381a.f1834f;
                            if (i3 >= length) {
                                int length2 = c0877s2.f1896a.length / 2;
                                for (int i4 = 0; i4 < length2; i4++) {
                                    String m1281d = c0877s2.m1281d(i4);
                                    if (!m1327b(m1281d) && m1328c(m1281d)) {
                                        C0875q c0875q = C0875q.f1891c;
                                        String m1283f = c0877s2.m1283f(i4);
                                        c0875q.getClass();
                                        arrayList.add(m1281d);
                                        arrayList.add(m1283f.trim());
                                    }
                                }
                                String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
                                C0864f c0864f = new C0864f();
                                Collections.addAll(c0864f.f1776a, strArr);
                                i0Var3.f1817f = c0864f;
                                i0Var3.f1822k = m1381a.f1839k;
                                i0Var3.f1823l = m1381a.f1840l;
                                j0 m1329d2 = m1329d(j0Var);
                                if (m1329d2 != null) {
                                    i0.m1259b("cacheResponse", m1329d2);
                                }
                                i0Var3.f1820i = m1329d2;
                                j0 m1329d3 = m1329d(m1381a);
                                if (m1329d3 != null) {
                                    i0.m1259b("networkResponse", m1329d3);
                                }
                                i0Var3.f1819h = m1329d3;
                                i0Var3.m1260a();
                                m1381a.f1835g.close();
                                AbstractC0000a.m27w(obj);
                                throw null;
                            }
                            String m1281d2 = c0877s.m1281d(i3);
                            String m1283f2 = c0877s.m1283f(i3);
                            if ((!"Warning".equalsIgnoreCase(m1281d2) || !m1283f2.startsWith("1")) && (m1327b(m1281d2) || !m1328c(m1281d2) || c0877s2.m1280c(m1281d2) == null)) {
                                C0875q.f1891c.getClass();
                                arrayList.add(m1281d2);
                                arrayList.add(m1283f2.trim());
                            }
                            i3++;
                        }
                    } else {
                        AbstractC0887c.m1306c(j0Var.f1835g);
                    }
                }
                i0 i0Var4 = new i0(m1381a);
                j0 m1329d4 = m1329d(j0Var);
                if (m1329d4 != null) {
                    i0.m1259b("cacheResponse", m1329d4);
                }
                i0Var4.f1820i = m1329d4;
                j0 m1329d5 = m1329d(m1381a);
                if (m1329d5 != null) {
                    i0.m1259b("networkResponse", m1329d5);
                }
                i0Var4.f1819h = m1329d5;
                j0 m1260a = i0Var4.m1260a();
                AbstractC0000a.m27w(obj);
                return m1260a;
            default:
                f0 f0Var3 = c0917f.f2074e;
                f0Var3.getClass();
                C0383m c0383m = new C0383m(f0Var3);
                AbstractC0026q abstractC0026q = f0Var3.f1780d;
                if (abstractC0026q != null) {
                    C0882x mo197j = abstractC0026q.mo197j();
                    if (mo197j != null) {
                        ((C0864f) c0383m.f779c).m1253c("Content-Type", mo197j.f1917a);
                    }
                    long mo196i = abstractC0026q.mo196i();
                    if (mo196i != -1) {
                        ((C0864f) c0383m.f779c).m1253c("Content-Length", Long.toString(mo196i));
                        c0383m.m955c("Transfer-Encoding");
                    } else {
                        ((C0864f) c0383m.f779c).m1253c("Transfer-Encoding", "chunked");
                        c0383m.m955c("Content-Length");
                    }
                }
                String m1254a = f0Var3.m1254a("Host");
                C0879u c0879u = f0Var3.f1777a;
                if (m1254a == null) {
                    z2 = false;
                    ((C0864f) c0383m.f779c).m1253c("Host", AbstractC0887c.m1313j(c0879u, false));
                } else {
                    z2 = false;
                }
                if (f0Var3.m1254a("Connection") == null) {
                    ((C0864f) c0383m.f779c).m1253c("Connection", "Keep-Alive");
                }
                if (f0Var3.m1254a("Accept-Encoding") == null && f0Var3.m1254a("Range") == null) {
                    ((C0864f) c0383m.f779c).m1253c("Accept-Encoding", "gzip");
                    z3 = true;
                } else {
                    z3 = z2;
                }
                InterfaceC0872n interfaceC0872n = (InterfaceC0872n) obj;
                List mo295d = interfaceC0872n.mo295d(c0879u);
                if (!mo295d.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    int size = mo295d.size();
                    for (int i5 = 0; i5 < size; i5++) {
                        if (i5 > 0) {
                            sb.append("; ");
                        }
                        C0871m c0871m = (C0871m) mo295d.get(i5);
                        sb.append(c0871m.f1865a);
                        sb.append('=');
                        sb.append(c0871m.f1866b);
                    }
                    ((C0864f) c0383m.f779c).m1253c("Cookie", sb.toString());
                }
                if (f0Var3.m1254a("User-Agent") == null) {
                    ((C0864f) c0383m.f779c).m1253c("User-Agent", "android okhttp3");
                }
                j0 m1381a2 = c0917f.m1381a(c0383m.m953a());
                AbstractC0916e.m1379d(interfaceC0872n, c0879u, m1381a2.f1834f);
                i0 i0Var5 = new i0(m1381a2);
                i0Var5.f1812a = f0Var3;
                if (z3 && "gzip".equalsIgnoreCase(m1381a2.m1265x("Content-Encoding", null)) && AbstractC0916e.m1377b(m1381a2)) {
                    C0019j c0019j = new C0019j(((k0) m1381a2.f1835g).f1850c);
                    C0864f m1282e = m1381a2.f1834f.m1282e();
                    m1282e.m1252b("Content-Encoding");
                    m1282e.m1252b("Content-Length");
                    ArrayList arrayList2 = m1282e.f1776a;
                    String[] strArr2 = (String[]) arrayList2.toArray(new String[arrayList2.size()]);
                    C0864f c0864f2 = new C0864f();
                    Collections.addAll(c0864f2.f1776a, strArr2);
                    i0Var5.f1817f = c0864f2;
                    String m1265x = m1381a2.m1265x("Content-Type", null);
                    Logger logger = AbstractC0021l.f38a;
                    i0Var5.f1818g = new k0(m1265x, -1L, new C0024o(c0019j));
                }
                return i0Var5.m1260a();
        }
    }
}
