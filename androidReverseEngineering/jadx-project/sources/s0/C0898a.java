package s0;

import a1.AbstractC0026q;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import l0.C0383m;
import m0.C0401b;
import p0.C0859a;
import p0.C0875q;
import p0.C0878t;
import p0.C0879u;
import p0.InterfaceC0881w;
import p0.b0;
import p0.f0;
import p0.i0;
import p0.j0;
import p0.m0;
import p000a.AbstractC0000a;
import q0.AbstractC0887c;
import t0.C0917f;
import v0.C0930a;

/* renamed from: s0.a */
/* loaded from: classes.dex */
public final class C0898a implements InterfaceC0881w {

    /* renamed from: a */
    public final /* synthetic */ int f1993a;

    /* renamed from: b */
    public final b0 f1994b;

    public /* synthetic */ C0898a(b0 b0Var, int i2) {
        this.f1993a = i2;
        this.f1994b = b0Var;
    }

    /* renamed from: c */
    public static int m1331c(j0 j0Var, int i2) {
        String m1265x = j0Var.m1265x("Retry-After", null);
        if (m1265x == null) {
            return i2;
        }
        if (m1265x.matches("\\d+")) {
            return Integer.valueOf(m1265x).intValue();
        }
        return Integer.MAX_VALUE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:169:0x0198, code lost:
    
        if (r9.f1831c == 408) goto L151;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x01a1, code lost:
    
        if (m1331c(r12, 0) > 0) goto L151;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x01d1, code lost:
    
        r3 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x01c5, code lost:
    
        if (r9.f1831c == 503) goto L151;
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x01cf, code lost:
    
        if (m1331c(r12, Integer.MAX_VALUE) != 0) goto L151;
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x01e6, code lost:
    
        if (r5.equals("HEAD") == false) goto L151;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0209  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0124 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r11v0, types: [s0.l] */
    /* JADX WARN: Type inference failed for: r19v1, types: [z0.c] */
    /* JADX WARN: Type inference failed for: r19v2 */
    /* JADX WARN: Type inference failed for: r19v3 */
    /* JADX WARN: Type inference failed for: r20v0 */
    /* JADX WARN: Type inference failed for: r20v1, types: [p0.g] */
    /* JADX WARN: Type inference failed for: r20v2 */
    /* JADX WARN: Type inference failed for: r27v0, types: [s0.a] */
    /* JADX WARN: Type inference failed for: r28v0, types: [t0.f] */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.io.IOException, s0.f] */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6 */
    @Override // p0.InterfaceC0881w
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final j0 mo1300a(C0917f c0917f) {
        SSLSocketFactory sSLSocketFactory;
        Object r20;
        Object r19;
        int i2;
        boolean z2;
        C0902e c0902e;
        boolean z3;
        String m1265x;
        C0878t c0878t;
        C0879u m1284a;
        C0401b c0401b;
        Proxy proxy;
        int i3 = 0;
        switch (this.f1993a) {
            case 0:
                f0 f0Var = c0917f.f2074e;
                C0909l c0909l = c0917f.f2071b;
                boolean z4 = !f0Var.f1778b.equals("GET");
                synchronized (c0909l.f2054b) {
                    if (c0909l.f2067o) {
                        throw new IllegalStateException("released");
                    }
                    if (c0909l.f2062j != null) {
                        throw new IllegalStateException("cannot make a new request because the previous response is still open: please call response.close()");
                    }
                }
                C0903f c0903f = c0909l.f2060h;
                b0 b0Var = c0909l.f2053a;
                c0903f.getClass();
                try {
                    C0902e c0902e2 = new C0902e(c0909l, c0909l.f2055c, c0909l.f2056d, c0909l.f2060h, c0903f.m1345b(c0917f.f2076g, c0917f.f2077h, c0917f.f2078i, b0Var.f1743y, b0Var.f1738t, z4).m1354g(b0Var, c0917f));
                    synchronized (c0909l.f2054b) {
                        c0909l.f2062j = c0902e2;
                        c0909l.f2063k = false;
                        c0909l.f2064l = false;
                    }
                    return c0917f.m1382b(f0Var, c0909l, c0902e2);
                } catch (IOException e2) {
                    synchronized (c0903f.f2018c) {
                        c0903f.f2023h = true;
                        throw new C0906i(e2);
                    }
                } catch (C0906i e3) {
                    synchronized (c0903f.f2018c) {
                        c0903f.f2023h = true;
                        throw e3;
                    }
                }
            default:
                f0 f0Var2 = c0917f.f2074e;
                Object r11 = c0917f.f2071b;
                Object r5 = 0;
                j0 j0Var = null;
                f0 f0Var3 = f0Var2;
                boolean z5 = true;
                while (true) {
                    f0 f0Var4 = r11.f2059g;
                    if (f0Var4 != null) {
                        if (AbstractC0887c.m1319p(f0Var4.f1777a, f0Var3.f1777a) && r11.f2060h.m1346c()) {
                            i2 = i3;
                            synchronized (r11.f2054b) {
                                z2 = r11.f2065m;
                            }
                            if (z2) {
                                throw new IOException("Canceled");
                            }
                            r5 = 0;
                            try {
                                try {
                                    j0 m1382b = c0917f.m1382b(f0Var3, r11, null);
                                    if (j0Var != null) {
                                        i0 i0Var = new i0(m1382b);
                                        i0 i0Var2 = new i0(j0Var);
                                        i0Var2.f1818g = null;
                                        j0 m1260a = i0Var2.m1260a();
                                        if (m1260a.f1835g != null) {
                                            throw new IllegalArgumentException("priorResponse.body != null");
                                        }
                                        i0Var.f1821j = m1260a;
                                        m1382b = i0Var.m1260a();
                                    }
                                    j0Var = m1382b;
                                    C0875q.f1891c.getClass();
                                    c0902e = j0Var.f1841m;
                                    m0 m0Var = c0902e != null ? c0902e.m1341a().f2026c : null;
                                    f0 f0Var5 = j0Var.f1829a;
                                    String str = f0Var5.f1778b;
                                    b0 b0Var2 = this.f1994b;
                                    int i4 = j0Var.f1831c;
                                    if (i4 == 307 || i4 == 308) {
                                        if (!str.equals("GET")) {
                                            break;
                                        }
                                        if (b0Var2.f1737s && (m1265x = j0Var.m1265x("Location", null)) != null) {
                                            C0879u c0879u = f0Var5.f1777a;
                                            c0879u.getClass();
                                            try {
                                                c0878t = new C0878t();
                                                c0878t.m1285b(c0879u, m1265x);
                                            } catch (IllegalArgumentException unused) {
                                                c0878t = null;
                                            }
                                            m1284a = c0878t == null ? c0878t.m1284a() : null;
                                            if (m1284a != null) {
                                                if (m1284a.f1907a.equals(f0Var5.f1777a.f1907a) || b0Var2.f1736r) {
                                                    C0383m c0383m = new C0383m(f0Var5);
                                                    if (AbstractC0026q.m158I(str)) {
                                                        boolean equals = str.equals("PROPFIND");
                                                        if (!str.equals("PROPFIND")) {
                                                            c0383m.m954b("GET", null);
                                                        } else {
                                                            c0383m.m954b(str, equals ? f0Var5.f1780d : null);
                                                        }
                                                        if (!equals) {
                                                            c0383m.m955c("Transfer-Encoding");
                                                            c0383m.m955c("Content-Length");
                                                            c0383m.m955c("Content-Type");
                                                        }
                                                    }
                                                    if (!AbstractC0887c.m1319p(f0Var5.f1777a, m1284a)) {
                                                        c0383m.m955c("Authorization");
                                                    }
                                                    c0383m.f778b = m1284a;
                                                    f0Var3 = c0383m.m953a();
                                                }
                                            }
                                        }
                                        f0Var3 = null;
                                    } else {
                                        if (i4 != 401) {
                                            j0 j0Var2 = j0Var.f1838j;
                                            if (i4 == 503) {
                                                if (j0Var2 != null) {
                                                    break;
                                                }
                                                break;
                                            } else if (i4 != 407) {
                                                if (i4 == 408) {
                                                    if (b0Var2.f1738t) {
                                                        if (j0Var2 != null) {
                                                            break;
                                                        }
                                                        break;
                                                    }
                                                } else {
                                                    switch (i4) {
                                                        case 300:
                                                        case 301:
                                                        case 302:
                                                        case 303:
                                                            if (b0Var2.f1737s) {
                                                                C0879u c0879u2 = f0Var5.f1777a;
                                                                c0879u2.getClass();
                                                                c0878t = new C0878t();
                                                                c0878t.m1285b(c0879u2, m1265x);
                                                                if (c0878t == null) {
                                                                }
                                                                if (m1284a != null) {
                                                                }
                                                                break;
                                                            }
                                                            break;
                                                        default:
                                                            f0Var3 = null;
                                                            break;
                                                    }
                                                }
                                                f0Var3 = null;
                                            } else {
                                                if (m0Var != null) {
                                                    proxy = m0Var.f1875b;
                                                } else {
                                                    b0Var2.getClass();
                                                    proxy = null;
                                                }
                                                if (proxy.type() != Proxy.Type.HTTP) {
                                                    throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
                                                }
                                                c0401b = b0Var2.f1732n;
                                            }
                                        } else {
                                            c0401b = b0Var2.f1733o;
                                        }
                                        c0401b.getClass();
                                        f0Var3 = null;
                                    }
                                } catch (Throwable th) {
                                    r11.m1363b();
                                    throw th;
                                }
                            } catch (IOException e4) {
                                z5 = true;
                                if (!m1332b(e4, r11, !(e4 instanceof C0930a), f0Var3)) {
                                    throw e4;
                                }
                                r11.m1363b();
                                i3 = i2;
                            } catch (C0906i e5) {
                                z5 = true;
                                if (!m1332b(e5.f2049b, r11, false, f0Var3)) {
                                    throw e5.f2048a;
                                }
                                r11.m1363b();
                                i3 = i2;
                            }
                            if (f0Var3 == null) {
                                if (c0902e != null && c0902e.f2015e) {
                                    if (r11.f2066n) {
                                        throw new IllegalStateException();
                                    }
                                    r11.f2066n = true;
                                    r11.f2057e.m74l();
                                }
                                return j0Var;
                            }
                            AbstractC0887c.m1306c(j0Var.f1835g);
                            synchronized (r11.f2054b) {
                                z3 = r11.f2062j != null;
                            }
                            if (z3) {
                                c0902e.f2014d.cancel();
                                c0902e.f2011a.m1364c(c0902e, true, true, null);
                                z5 = true;
                                r5 = 0;
                            } else {
                                z5 = true;
                                r5 = 0;
                            }
                            i3 = i2 + 1;
                            if (i3 > 20) {
                                throw new ProtocolException(AbstractC0000a.m11g("Too many follow-up requests: ", i3));
                            }
                        } else {
                            if (r11.f2062j != null) {
                                throw new IllegalStateException();
                            }
                            if (r11.f2060h != null) {
                                r11.m1365d(r5, z5);
                                r11.f2060h = r5;
                            }
                        }
                    }
                    r11.f2059g = f0Var3;
                    C0905h c0905h = r11.f2054b;
                    C0879u c0879u3 = f0Var3.f1777a;
                    boolean equals2 = c0879u3.f1907a.equals("https");
                    b0 b0Var3 = r11.f2053a;
                    if (equals2) {
                        sSLSocketFactory = b0Var3.f1728j;
                        r19 = b0Var3.f1730l;
                        r20 = b0Var3.f1731m;
                    } else {
                        sSLSocketFactory = r5;
                        SSLSocketFactory sSLSocketFactory2 = sSLSocketFactory;
                        r20 = sSLSocketFactory2;
                        r19 = sSLSocketFactory2;
                    }
                    i2 = i3;
                    r11.f2060h = new C0903f(r11, c0905h, new C0859a(c0879u3.f1910d, c0879u3.f1911e, b0Var3.f1735q, b0Var3.f1727i, sSLSocketFactory, r19, r20, b0Var3.f1732n, b0Var3.f1720b, b0Var3.f1721c, b0Var3.f1725g), r11.f2055c, r11.f2056d);
                    synchronized (r11.f2054b) {
                    }
                }
                break;
        }
    }

    /* renamed from: b */
    public final boolean m1332b(IOException iOException, C0909l c0909l, boolean z2, f0 f0Var) {
        boolean z3;
        if (!this.f1994b.f1738t) {
            return false;
        }
        if (z2) {
            AbstractC0026q abstractC0026q = f0Var.f1780d;
            if (iOException instanceof FileNotFoundException) {
                return false;
            }
        }
        if (!(!(iOException instanceof ProtocolException) && (!(iOException instanceof InterruptedIOException) ? ((iOException instanceof SSLHandshakeException) && (iOException.getCause() instanceof CertificateException)) || (iOException instanceof SSLPeerUnverifiedException) : !((iOException instanceof SocketTimeoutException) && !z2)))) {
            return false;
        }
        C0903f c0903f = c0909l.f2060h;
        synchronized (c0903f.f2018c) {
            z3 = c0903f.f2023h;
        }
        return z3 && c0909l.f2060h.m1346c();
    }
}
