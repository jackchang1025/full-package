package s0;

import com.guard.wallet.http.C0203h;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import m0.C0401b;
import p0.C0859a;
import p0.C0875q;
import p0.C0878t;
import p0.C0879u;
import p0.e0;
import p0.m0;
import p022z.C0981d;
import q0.AbstractC0887c;
import v0.C0948s;

/* renamed from: s0.f */
/* loaded from: classes.dex */
public final class C0903f {

    /* renamed from: a */
    public final C0909l f2016a;

    /* renamed from: b */
    public final C0859a f2017b;

    /* renamed from: c */
    public final C0905h f2018c;

    /* renamed from: d */
    public final C0875q f2019d;

    /* renamed from: e */
    public C0981d f2020e;

    /* renamed from: f */
    public final C0878t f2021f;

    /* renamed from: g */
    public C0904g f2022g;

    /* renamed from: h */
    public boolean f2023h;

    /* renamed from: i */
    public m0 f2024i;

    public C0903f(C0909l c0909l, C0905h c0905h, C0859a c0859a, e0 e0Var, C0875q c0875q) {
        this.f2016a = c0909l;
        this.f2018c = c0905h;
        this.f2017b = c0859a;
        this.f2019d = c0875q;
        this.f2021f = new C0878t(c0859a, c0905h.f2046e, e0Var, c0875q);
    }

    /* JADX WARN: Code restructure failed: missing block: B:178:0x0228, code lost:
    
        throw new java.net.SocketException("No route to " + r12 + ":" + r11 + "; port is out of range");
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0081, code lost:
    
        if ((r2.f2326b < ((java.util.List) r2.f2327c).size()) == false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0251, code lost:
    
        if (r3.isEmpty() == false) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0253, code lost:
    
        r3.addAll((java.util.List) r2.f1905i);
        ((java.util.List) r2.f1905i).clear();
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0261, code lost:
    
        r23.f2020e = new p022z.C0981d(r3);
        r2 = true;
     */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final C0904g m1344a(int i2, int i3, int i4, int i5, boolean z2) {
        boolean z3;
        C0904g c0904g;
        Socket socket;
        Socket m1367f;
        C0904g c0904g2;
        int i6;
        boolean z4;
        m0 m0Var;
        boolean z5;
        ArrayList arrayList;
        String str;
        int i7;
        boolean contains;
        synchronized (this.f2018c) {
            C0909l c0909l = this.f2016a;
            synchronized (c0909l.f2054b) {
                z3 = c0909l.f2065m;
            }
            if (z3) {
                throw new IOException("Canceled");
            }
            this.f2023h = false;
            C0909l c0909l2 = this.f2016a;
            c0904g = c0909l2.f2061i;
            socket = null;
            m1367f = (c0904g == null || !c0904g.f2034k) ? null : c0909l2.m1367f();
            C0909l c0909l3 = this.f2016a;
            c0904g2 = c0909l3.f2061i;
            if (c0904g2 != null) {
                c0904g = null;
            } else {
                c0904g2 = null;
            }
            i6 = 1;
            if (c0904g2 == null) {
                if (this.f2018c.m1360c(this.f2017b, c0909l3, null, false)) {
                    c0904g2 = this.f2016a.f2061i;
                    m0Var = null;
                    z4 = true;
                } else {
                    m0Var = this.f2024i;
                    if (m0Var != null) {
                        this.f2024i = null;
                    } else if (m1347d()) {
                        m0Var = this.f2016a.f2061i.f2026c;
                    }
                    z4 = false;
                }
            }
            z4 = false;
            m0Var = null;
        }
        AbstractC0887c.m1307d(m1367f);
        if (c0904g != null) {
            this.f2019d.getClass();
        }
        if (z4) {
            this.f2019d.getClass();
        }
        if (c0904g2 != null) {
            return c0904g2;
        }
        if (m0Var == null) {
            C0981d c0981d = this.f2020e;
            if (c0981d != null) {
            }
            C0878t c0878t = this.f2021f;
            if (!((c0878t.f1899c < c0878t.f1898b.size()) || !((List) c0878t.f1905i).isEmpty())) {
                throw new NoSuchElementException();
            }
            ArrayList arrayList2 = new ArrayList();
            while (true) {
                if ((c0878t.f1899c < c0878t.f1898b.size() ? i6 : 0) == 0) {
                    break;
                }
                if ((c0878t.f1899c < c0878t.f1898b.size() ? i6 : 0) == 0) {
                    throw new SocketException("No route to " + ((C0859a) c0878t.f1901e).f1682a.f1910d + "; exhausted proxy configurations: " + c0878t.f1898b);
                }
                List list = c0878t.f1898b;
                int i8 = c0878t.f1899c;
                c0878t.f1899c = i8 + 1;
                Proxy proxy = (Proxy) list.get(i8);
                c0878t.f1900d = new ArrayList();
                if (proxy.type() == Proxy.Type.DIRECT || proxy.type() == Proxy.Type.SOCKS) {
                    C0879u c0879u = ((C0859a) c0878t.f1901e).f1682a;
                    str = c0879u.f1910d;
                    i7 = c0879u.f1911e;
                } else {
                    SocketAddress address = proxy.address();
                    if (!(address instanceof InetSocketAddress)) {
                        throw new IllegalArgumentException("Proxy.address() is not an InetSocketAddress: " + address.getClass());
                    }
                    InetSocketAddress inetSocketAddress = (InetSocketAddress) address;
                    InetAddress address2 = inetSocketAddress.getAddress();
                    str = address2 == null ? inetSocketAddress.getHostName() : address2.getHostAddress();
                    i7 = inetSocketAddress.getPort();
                }
                if (i7 < i6 || i7 > 65535) {
                    break;
                }
                if (proxy.type() == Proxy.Type.SOCKS) {
                    c0878t.f1900d.add(InetSocketAddress.createUnresolved(str, i7));
                } else {
                    ((C0875q) c0878t.f1904h).getClass();
                    ((C0401b) ((C0859a) c0878t.f1901e).f1683b).getClass();
                    if (str == null) {
                        throw new UnknownHostException("hostname == null");
                    }
                    try {
                        List asList = Arrays.asList(InetAddress.getAllByName(str));
                        if (asList.isEmpty()) {
                            throw new UnknownHostException(((C0859a) c0878t.f1901e).f1683b + " returned no addresses for " + str);
                        }
                        ((C0875q) c0878t.f1904h).getClass();
                        int size = asList.size();
                        for (int i9 = 0; i9 < size; i9++) {
                            c0878t.f1900d.add(new InetSocketAddress((InetAddress) asList.get(i9), i7));
                        }
                    } catch (NullPointerException e2) {
                        UnknownHostException unknownHostException = new UnknownHostException("Broken system behaviour for dns lookup of ".concat(str));
                        unknownHostException.initCause(e2);
                        throw unknownHostException;
                    }
                }
                int size2 = c0878t.f1900d.size();
                for (int i10 = 0; i10 < size2; i10++) {
                    m0 m0Var2 = new m0((C0859a) c0878t.f1901e, proxy, (InetSocketAddress) c0878t.f1900d.get(i10));
                    C0203h c0203h = (C0203h) c0878t.f1902f;
                    synchronized (c0203h) {
                        contains = ((Set) c0203h.f245e).contains(m0Var2);
                    }
                    if (contains) {
                        ((List) c0878t.f1905i).add(m0Var2);
                    } else {
                        arrayList2.add(m0Var2);
                    }
                }
                if (!arrayList2.isEmpty()) {
                    break;
                }
                i6 = 1;
            }
        }
        boolean z6 = false;
        synchronized (this.f2018c) {
            C0909l c0909l4 = this.f2016a;
            synchronized (c0909l4.f2054b) {
                z5 = c0909l4.f2065m;
            }
            if (z5) {
                throw new IOException("Canceled");
            }
            if (z6) {
                C0981d c0981d2 = this.f2020e;
                c0981d2.getClass();
                arrayList = new ArrayList((List) c0981d2.f2327c);
                if (this.f2018c.m1360c(this.f2017b, this.f2016a, arrayList, false)) {
                    c0904g2 = this.f2016a.f2061i;
                    z4 = true;
                }
            } else {
                arrayList = null;
            }
            if (!z4) {
                if (m0Var == null) {
                    C0981d c0981d3 = this.f2020e;
                    if (!(c0981d3.f2326b < ((List) c0981d3.f2327c).size())) {
                        throw new NoSuchElementException();
                    }
                    List list2 = (List) c0981d3.f2327c;
                    int i11 = c0981d3.f2326b;
                    c0981d3.f2326b = i11 + 1;
                    m0Var = (m0) list2.get(i11);
                }
                c0904g2 = new C0904g(this.f2018c, m0Var);
                this.f2022g = c0904g2;
            }
        }
        if (!z4) {
            c0904g2.m1350c(i2, i3, i4, i5, z2, this.f2019d);
            this.f2018c.f2046e.m394h(c0904g2.f2026c);
            synchronized (this.f2018c) {
                this.f2022g = null;
                if (this.f2018c.m1360c(this.f2017b, this.f2016a, arrayList, true)) {
                    c0904g2.f2034k = true;
                    socket = c0904g2.f2028e;
                    C0904g c0904g3 = this.f2016a.f2061i;
                    this.f2024i = m0Var;
                    c0904g2 = c0904g3;
                } else {
                    C0905h c0905h = this.f2018c;
                    if (!c0905h.f2047f) {
                        c0905h.f2047f = true;
                        C0905h.f2041g.execute(c0905h.f2044c);
                    }
                    c0905h.f2045d.add(c0904g2);
                    C0909l c0909l5 = this.f2016a;
                    if (c0909l5.f2061i != null) {
                        throw new IllegalStateException();
                    }
                    c0909l5.f2061i = c0904g2;
                    c0904g2.f2039p.add(new C0908k(c0909l5, c0909l5.f2058f));
                }
            }
            AbstractC0887c.m1307d(socket);
        }
        this.f2019d.getClass();
        return c0904g2;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0084 A[LOOP:0: B:1:0x0000->B:39:0x0084, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0089 A[SYNTHETIC] */
    /* renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final C0904g m1345b(int i2, int i3, int i4, int i5, boolean z2, boolean z3) {
        while (true) {
            C0904g m1344a = m1344a(i2, i3, i4, i5, z2);
            synchronized (this.f2018c) {
                try {
                    boolean z4 = true;
                    if (m1344a.f2036m == 0) {
                        if (!(m1344a.f2031h != null)) {
                            return m1344a;
                        }
                    }
                    if (!m1344a.f2028e.isClosed() && !m1344a.f2028e.isInputShutdown() && !m1344a.f2028e.isOutputShutdown()) {
                        C0948s c0948s = m1344a.f2031h;
                        if (c0948s != null) {
                            long nanoTime = System.nanoTime();
                            synchronized (c0948s) {
                                if (!c0948s.f2206g && (c0948s.f2213n >= c0948s.f2212m || nanoTime < c0948s.f2214o)) {
                                }
                            }
                            if (z4) {
                                return m1344a;
                            }
                            m1344a.m1355h();
                        } else {
                            if (z3) {
                                try {
                                    int soTimeout = m1344a.f2028e.getSoTimeout();
                                    try {
                                        m1344a.f2028e.setSoTimeout(1);
                                        if (m1344a.f2032i.mo104n()) {
                                            m1344a.f2028e.setSoTimeout(soTimeout);
                                        } else {
                                            m1344a.f2028e.setSoTimeout(soTimeout);
                                        }
                                    } catch (Throwable th) {
                                        m1344a.f2028e.setSoTimeout(soTimeout);
                                        throw th;
                                    }
                                } catch (SocketTimeoutException unused) {
                                } catch (IOException unused2) {
                                }
                            }
                            if (z4) {
                            }
                        }
                    }
                    z4 = false;
                    if (z4) {
                    }
                } finally {
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x002e, code lost:
    
        if ((r1.f2326b < ((java.util.List) r1.f2327c).size()) == false) goto L20;
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0052  */
    /* renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m1346c() {
        boolean z2;
        synchronized (this.f2018c) {
            boolean z3 = true;
            if (this.f2024i != null) {
                return true;
            }
            if (m1347d()) {
                this.f2024i = this.f2016a.f2061i.f2026c;
                return true;
            }
            C0981d c0981d = this.f2020e;
            if (c0981d != null) {
            }
            C0878t c0878t = this.f2021f;
            if (!(c0878t.f1899c < c0878t.f1898b.size()) && ((List) c0878t.f1905i).isEmpty()) {
                z2 = false;
                if (z2) {
                    z3 = false;
                }
                return z3;
            }
            z2 = true;
            if (z2) {
            }
            return z3;
        }
    }

    /* renamed from: d */
    public final boolean m1347d() {
        C0904g c0904g = this.f2016a.f2061i;
        return c0904g != null && c0904g.f2035l == 0 && AbstractC0887c.m1319p(c0904g.f2026c.f1874a.f1682a, this.f2017b.f1682a);
    }
}
