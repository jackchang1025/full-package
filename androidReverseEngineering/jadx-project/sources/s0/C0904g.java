package s0;

import a1.AbstractC0021l;
import a1.C0023n;
import a1.C0024o;
import a1.C0031v;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Socket;
import java.net.UnknownServiceException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import l0.C0383m;
import p0.C0859a;
import p0.C0864f;
import p0.C0865g;
import p0.C0869k;
import p0.C0875q;
import p0.C0876r;
import p0.C0879u;
import p0.b0;
import p0.c0;
import p0.f0;
import p0.i0;
import p0.j0;
import p0.m0;
import p000a.AbstractC0000a;
import p022z.C0981d;
import q0.AbstractC0887c;
import t0.AbstractC0916e;
import t0.C0917f;
import t0.InterfaceC0913b;
import u0.C0923d;
import u0.C0926g;
import v0.AbstractC0936g;
import v0.AbstractC0944o;
import v0.C0942m;
import v0.C0948s;
import v0.C0949t;
import v0.C0954y;
import v0.C0955z;
import v0.EnumC0931b;
import w0.C0966i;
import z0.C0984c;

/* renamed from: s0.g */
/* loaded from: classes.dex */
public final class C0904g extends AbstractC0944o {

    /* renamed from: b */
    public final C0905h f2025b;

    /* renamed from: c */
    public final m0 f2026c;

    /* renamed from: d */
    public Socket f2027d;

    /* renamed from: e */
    public Socket f2028e;

    /* renamed from: f */
    public C0876r f2029f;

    /* renamed from: g */
    public c0 f2030g;

    /* renamed from: h */
    public C0948s f2031h;

    /* renamed from: i */
    public C0024o f2032i;

    /* renamed from: j */
    public C0023n f2033j;

    /* renamed from: k */
    public boolean f2034k;

    /* renamed from: l */
    public int f2035l;

    /* renamed from: m */
    public int f2036m;

    /* renamed from: n */
    public int f2037n;

    /* renamed from: o */
    public int f2038o = 1;

    /* renamed from: p */
    public final ArrayList f2039p = new ArrayList();

    /* renamed from: q */
    public long f2040q = Long.MAX_VALUE;

    public C0904g(C0905h c0905h, m0 m0Var) {
        this.f2025b = c0905h;
        this.f2026c = m0Var;
    }

    @Override // v0.AbstractC0944o
    /* renamed from: a */
    public final void mo1348a(C0948s c0948s) {
        int i2;
        synchronized (this.f2025b) {
            try {
                synchronized (c0948s) {
                    C0981d c0981d = c0948s.f2218s;
                    i2 = (c0981d.f2326b & 16) != 0 ? ((int[]) c0981d.f2327c)[4] : Integer.MAX_VALUE;
                }
                this.f2038o = i2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // v0.AbstractC0944o
    /* renamed from: b */
    public final void mo1349b(C0954y c0954y) {
        c0954y.m1428c(EnumC0931b.REFUSED_STREAM, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00d6 A[ORIG_RETURN, RETURN] */
    /* renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m1350c(int i2, int i3, int i4, int i5, boolean z2, C0875q c0875q) {
        boolean z3;
        m0 m0Var;
        int i6;
        if (this.f2030g != null) {
            throw new IllegalStateException("already connected");
        }
        C0859a c0859a = this.f2026c.f1874a;
        List list = c0859a.f1687f;
        C0899b c0899b = new C0899b(list);
        if (c0859a.f1690i == null) {
            if (!list.contains(C0869k.f1843f)) {
                throw new C0906i(new UnknownServiceException("CLEARTEXT communication not enabled for client"));
            }
            String str = this.f2026c.f1874a.f1682a.f1910d;
            if (!C0966i.f2293a.mo1454l(str)) {
                throw new C0906i(new UnknownServiceException(AbstractC0000a.m16l("CLEARTEXT communication to ", str, " not permitted by network security policy")));
            }
        } else if (c0859a.f1686e.contains(c0.H2_PRIOR_KNOWLEDGE)) {
            throw new C0906i(new UnknownServiceException("H2_PRIOR_KNOWLEDGE cannot be used with HTTPS"));
        }
        C0906i c0906i = null;
        do {
            z3 = false;
            try {
                m0 m0Var2 = this.f2026c;
                if (m0Var2.f1874a.f1690i != null && m0Var2.f1875b.type() == Proxy.Type.HTTP) {
                    m1352e(i2, i3, i4, c0875q);
                    if (this.f2027d == null) {
                        m0Var = this.f2026c;
                        if (!(m0Var.f1874a.f1690i == null && m0Var.f1875b.type() == Proxy.Type.HTTP) && this.f2027d == null) {
                            throw new C0906i(new ProtocolException("Too many tunnel connections attempted: 21"));
                        }
                        if (this.f2031h == null) {
                            synchronized (this.f2025b) {
                                C0948s c0948s = this.f2031h;
                                synchronized (c0948s) {
                                    C0981d c0981d = c0948s.f2218s;
                                    i6 = (c0981d.f2326b & 16) != 0 ? ((int[]) c0981d.f2327c)[4] : Integer.MAX_VALUE;
                                }
                                this.f2038o = i6;
                            }
                            return;
                        }
                        return;
                    }
                } else {
                    m1351d(i2, i3, c0875q);
                }
                m1353f(c0899b, i5, c0875q);
                InetSocketAddress inetSocketAddress = this.f2026c.f1876c;
                c0875q.getClass();
                m0Var = this.f2026c;
                if (!(m0Var.f1874a.f1690i == null && m0Var.f1875b.type() == Proxy.Type.HTTP)) {
                }
                if (this.f2031h == null) {
                }
            } catch (IOException e2) {
                AbstractC0887c.m1307d(this.f2028e);
                AbstractC0887c.m1307d(this.f2027d);
                this.f2028e = null;
                this.f2027d = null;
                this.f2032i = null;
                this.f2033j = null;
                this.f2029f = null;
                this.f2030g = null;
                this.f2031h = null;
                InetSocketAddress inetSocketAddress2 = this.f2026c.f1876c;
                c0875q.getClass();
                if (c0906i == null) {
                    c0906i = new C0906i(e2);
                } else {
                    IOException iOException = c0906i.f2048a;
                    Method method = AbstractC0887c.f1943j;
                    if (method != null) {
                        try {
                            method.invoke(iOException, e2);
                        } catch (IllegalAccessException | InvocationTargetException unused) {
                        }
                    }
                    c0906i.f2049b = e2;
                }
                if (!z2) {
                    throw c0906i;
                }
                c0899b.f1998d = true;
                if (c0899b.f1997c && !(e2 instanceof ProtocolException) && !(e2 instanceof InterruptedIOException) && ((!(e2 instanceof SSLHandshakeException) || !(e2.getCause() instanceof CertificateException)) && !(e2 instanceof SSLPeerUnverifiedException))) {
                    z3 = e2 instanceof SSLException;
                }
            }
        } while (z3);
        throw c0906i;
    }

    /* renamed from: d */
    public final void m1351d(int i2, int i3, C0875q c0875q) {
        m0 m0Var = this.f2026c;
        Proxy proxy = m0Var.f1875b;
        InetSocketAddress inetSocketAddress = m0Var.f1876c;
        this.f2027d = (proxy.type() == Proxy.Type.DIRECT || proxy.type() == Proxy.Type.HTTP) ? m0Var.f1874a.f1684c.createSocket() : new Socket(proxy);
        c0875q.getClass();
        this.f2027d.setSoTimeout(i3);
        try {
            C0966i.f2293a.mo1451h(this.f2027d, inetSocketAddress, i2);
            try {
                this.f2032i = new C0024o(AbstractC0021l.m140b(this.f2027d));
                this.f2033j = new C0023n(AbstractC0021l.m139a(this.f2027d));
            } catch (NullPointerException e2) {
                if ("throw with null exception".equals(e2.getMessage())) {
                    throw new IOException(e2);
                }
            }
        } catch (ConnectException e3) {
            ConnectException connectException = new ConnectException("Failed to connect to " + inetSocketAddress);
            connectException.initCause(e3);
            throw connectException;
        }
    }

    /* renamed from: e */
    public final void m1352e(int i2, int i3, int i4, C0875q c0875q) {
        C0383m c0383m = new C0383m();
        m0 m0Var = this.f2026c;
        C0879u c0879u = m0Var.f1874a.f1682a;
        if (c0879u == null) {
            throw new NullPointerException("url == null");
        }
        c0383m.f778b = c0879u;
        c0383m.m954b("CONNECT", null);
        C0859a c0859a = m0Var.f1874a;
        ((C0864f) c0383m.f779c).m1253c("Host", AbstractC0887c.m1313j(c0859a.f1682a, true));
        ((C0864f) c0383m.f779c).m1253c("Proxy-Connection", "Keep-Alive");
        ((C0864f) c0383m.f779c).m1253c("User-Agent", "android okhttp3");
        f0 m953a = c0383m.m953a();
        i0 i0Var = new i0();
        i0Var.f1812a = m953a;
        i0Var.f1813b = c0.HTTP_1_1;
        i0Var.f1814c = 407;
        i0Var.f1815d = "Preemptive Authenticate";
        i0Var.f1818g = AbstractC0887c.f1937d;
        i0Var.f1822k = -1L;
        i0Var.f1823l = -1L;
        i0Var.f1817f.m1253c("Proxy-Authenticate", "OkHttp-Preemptive");
        i0Var.m1260a();
        c0859a.f1685d.getClass();
        m1351d(i2, i3, c0875q);
        String str = "CONNECT " + AbstractC0887c.m1313j(m953a.f1777a, true) + " HTTP/1.1";
        C0024o c0024o = this.f2032i;
        C0926g c0926g = new C0926g(null, null, c0024o, this.f2033j);
        C0031v mo68a = c0024o.mo68a();
        long j2 = i3;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        mo68a.mo136g(j2, timeUnit);
        this.f2033j.mo66a().mo136g(i4, timeUnit);
        c0926g.m1391l(m953a.f1779c, str);
        c0926g.mo1370c();
        i0 mo1374g = c0926g.mo1374g(false);
        mo1374g.f1812a = m953a;
        j0 m1260a = mo1374g.m1260a();
        long m1376a = AbstractC0916e.m1376a(m1260a);
        if (m1376a != -1) {
            C0923d m1388i = c0926g.m1388i(m1376a);
            AbstractC0887c.m1320q(m1388i, Integer.MAX_VALUE, timeUnit);
            m1388i.close();
        }
        int i5 = m1260a.f1831c;
        if (i5 != 200) {
            if (i5 != 407) {
                throw new IOException(AbstractC0000a.m11g("Unexpected response code for CONNECT: ", i5));
            }
            c0859a.f1685d.getClass();
            throw new IOException("Failed to authenticate with proxy");
        }
        if (!this.f2032i.f45a.mo104n() || !this.f2033j.f42a.mo104n()) {
            throw new IOException("TLS tunnel buffered too many bytes!");
        }
    }

    /* renamed from: f */
    public final void m1353f(C0899b c0899b, int i2, C0875q c0875q) {
        SSLSocket sSLSocket;
        m0 m0Var = this.f2026c;
        C0859a c0859a = m0Var.f1874a;
        SSLSocketFactory sSLSocketFactory = c0859a.f1690i;
        c0 c0Var = c0.HTTP_1_1;
        if (sSLSocketFactory == null) {
            c0 c0Var2 = c0.H2_PRIOR_KNOWLEDGE;
            if (!c0859a.f1686e.contains(c0Var2)) {
                this.f2028e = this.f2027d;
                this.f2030g = c0Var;
                return;
            } else {
                this.f2028e = this.f2027d;
                this.f2030g = c0Var2;
                m1356i(i2);
                return;
            }
        }
        c0875q.getClass();
        C0859a c0859a2 = m0Var.f1874a;
        SSLSocketFactory sSLSocketFactory2 = c0859a2.f1690i;
        C0879u c0879u = c0859a2.f1682a;
        try {
            try {
                sSLSocket = (SSLSocket) sSLSocketFactory2.createSocket(this.f2027d, c0879u.f1910d, c0879u.f1911e, true);
            } catch (AssertionError e2) {
                e = e2;
            }
        } catch (Throwable th) {
            th = th;
            sSLSocket = null;
        }
        try {
            C0869k m1333a = c0899b.m1333a(sSLSocket);
            String str = c0879u.f1910d;
            boolean z2 = m1333a.f1845b;
            if (z2) {
                C0966i.f2293a.mo1445g(sSLSocket, str, c0859a2.f1686e);
            }
            sSLSocket.startHandshake();
            SSLSession session = sSLSocket.getSession();
            C0876r m1276a = C0876r.m1276a(session);
            boolean verify = c0859a2.f1691j.verify(str, session);
            List list = m1276a.f1894c;
            if (verify) {
                c0859a2.f1692k.m1256a(str, list);
                String mo1446j = z2 ? C0966i.f2293a.mo1446j(sSLSocket) : null;
                this.f2028e = sSLSocket;
                this.f2032i = new C0024o(AbstractC0021l.m140b(sSLSocket));
                this.f2033j = new C0023n(AbstractC0021l.m139a(this.f2028e));
                this.f2029f = m1276a;
                if (mo1446j != null) {
                    c0Var = c0.m1243a(mo1446j);
                }
                this.f2030g = c0Var;
                C0966i.f2293a.mo1458a(sSLSocket);
                if (this.f2030g == c0.HTTP_2) {
                    m1356i(i2);
                    return;
                }
                return;
            }
            if (list.isEmpty()) {
                throw new SSLPeerUnverifiedException("Hostname " + str + " not verified (no certificates)");
            }
            X509Certificate x509Certificate = (X509Certificate) list.get(0);
            throw new SSLPeerUnverifiedException("Hostname " + str + " not verified:\n    certificate: " + C0865g.m1255b(x509Certificate) + "\n    DN: " + x509Certificate.getSubjectDN().getName() + "\n    subjectAltNames: " + C0984c.m1475a(x509Certificate));
        } catch (AssertionError e3) {
            e = e3;
            if (!AbstractC0887c.m1317n(e)) {
                throw e;
            }
            throw new IOException(e);
        } catch (Throwable th2) {
            th = th2;
            if (sSLSocket != null) {
                C0966i.f2293a.mo1458a(sSLSocket);
            }
            AbstractC0887c.m1307d(sSLSocket);
            throw th;
        }
    }

    /* renamed from: g */
    public final InterfaceC0913b m1354g(b0 b0Var, C0917f c0917f) {
        if (this.f2031h != null) {
            return new C0949t(b0Var, this, c0917f, this.f2031h);
        }
        Socket socket = this.f2028e;
        int i2 = c0917f.f2077h;
        socket.setSoTimeout(i2);
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        this.f2032i.mo68a().mo136g(i2, timeUnit);
        this.f2033j.mo66a().mo136g(c0917f.f2078i, timeUnit);
        return new C0926g(b0Var, this, this.f2032i, this.f2033j);
    }

    /* renamed from: h */
    public final void m1355h() {
        synchronized (this.f2025b) {
            this.f2034k = true;
        }
    }

    /* renamed from: i */
    public final void m1356i(int i2) {
        this.f2028e.setSoTimeout(0);
        C0942m c0942m = new C0942m();
        Socket socket = this.f2028e;
        String str = this.f2026c.f1874a.f1682a.f1910d;
        C0024o c0024o = this.f2032i;
        C0023n c0023n = this.f2033j;
        c0942m.f2182a = socket;
        c0942m.f2183b = str;
        c0942m.f2184c = c0024o;
        c0942m.f2185d = c0023n;
        c0942m.f2186e = this;
        c0942m.f2187f = i2;
        C0948s c0948s = new C0948s(c0942m);
        this.f2031h = c0948s;
        C0955z c0955z = c0948s.f2220u;
        synchronized (c0955z) {
            if (c0955z.f2269e) {
                throw new IOException("closed");
            }
            if (c0955z.f2266b) {
                Logger logger = C0955z.f2264g;
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine(String.format(">> CONNECTION %s", AbstractC0936g.f2163a.mo122f()));
                }
                c0955z.f2265a.mo106p((byte[]) AbstractC0936g.f2163a.f25a.clone());
                c0955z.f2265a.flush();
            }
        }
        C0955z c0955z2 = c0948s.f2220u;
        C0981d c0981d = c0948s.f2217r;
        synchronized (c0955z2) {
            if (c0955z2.f2269e) {
                throw new IOException("closed");
            }
            c0955z2.m1442z(0, Integer.bitCount(c0981d.f2326b) * 6, (byte) 4, (byte) 0);
            int i3 = 0;
            while (i3 < 10) {
                if (((1 << i3) & c0981d.f2326b) != 0) {
                    c0955z2.f2265a.mo100j(i3 == 4 ? 3 : i3 == 7 ? 4 : i3);
                    c0955z2.f2265a.mo101k(((int[]) c0981d.f2327c)[i3]);
                }
                i3++;
            }
            c0955z2.f2265a.flush();
        }
        if (c0948s.f2217r.m1473d() != 65535) {
            c0948s.f2220u.m1438D(0, r0 - 65535);
        }
        new Thread(c0948s.f2221v).start();
    }

    /* renamed from: j */
    public final boolean m1357j(C0879u c0879u) {
        int i2 = c0879u.f1911e;
        C0879u c0879u2 = this.f2026c.f1874a.f1682a;
        if (i2 != c0879u2.f1911e) {
            return false;
        }
        String str = c0879u.f1910d;
        if (str.equals(c0879u2.f1910d)) {
            return true;
        }
        C0876r c0876r = this.f2029f;
        return c0876r != null && C0984c.m1477c(str, (X509Certificate) c0876r.f1894c.get(0));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Connection{");
        m0 m0Var = this.f2026c;
        sb.append(m0Var.f1874a.f1682a.f1910d);
        sb.append(":");
        sb.append(m0Var.f1874a.f1682a.f1911e);
        sb.append(", proxy=");
        sb.append(m0Var.f1875b);
        sb.append(" hostAddress=");
        sb.append(m0Var.f1876c);
        sb.append(" cipherSuite=");
        C0876r c0876r = this.f2029f;
        sb.append(c0876r != null ? c0876r.f1893b : "none");
        sb.append(" protocol=");
        sb.append(this.f2030g);
        sb.append('}');
        return sb.toString();
    }
}
