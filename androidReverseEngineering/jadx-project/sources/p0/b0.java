package p0;

import a1.AbstractC0026q;
import com.guard.wallet.http.C0203h;
import f0.C0291l;
import java.net.ProxySelector;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import m0.C0401b;
import q0.AbstractC0887c;
import w0.C0966i;
import z0.C0984c;

/* loaded from: classes.dex */
public final class b0 implements Cloneable {

    /* renamed from: a */
    public final C0873o f1719a;

    /* renamed from: b */
    public final List f1720b;

    /* renamed from: c */
    public final List f1721c;

    /* renamed from: d */
    public final List f1722d;

    /* renamed from: e */
    public final List f1723e;

    /* renamed from: f */
    public final C0291l f1724f;

    /* renamed from: g */
    public final ProxySelector f1725g;

    /* renamed from: h */
    public final InterfaceC0872n f1726h;

    /* renamed from: i */
    public final SocketFactory f1727i;

    /* renamed from: j */
    public final SSLSocketFactory f1728j;

    /* renamed from: k */
    public final AbstractC0026q f1729k;

    /* renamed from: l */
    public final C0984c f1730l;

    /* renamed from: m */
    public final C0865g f1731m;

    /* renamed from: n */
    public final C0401b f1732n;

    /* renamed from: o */
    public final C0401b f1733o;

    /* renamed from: p */
    public final C0203h f1734p;

    /* renamed from: q */
    public final C0401b f1735q;

    /* renamed from: r */
    public final boolean f1736r;

    /* renamed from: s */
    public final boolean f1737s;

    /* renamed from: t */
    public final boolean f1738t;

    /* renamed from: u */
    public final int f1739u;

    /* renamed from: v */
    public final int f1740v;

    /* renamed from: w */
    public final int f1741w;

    /* renamed from: x */
    public final int f1742x;

    /* renamed from: y */
    public final int f1743y;

    /* renamed from: z */
    public static final List f1718z = AbstractC0887c.m1315l(c0.HTTP_2, c0.HTTP_1_1);

    /* renamed from: A */
    public static final List f1717A = AbstractC0887c.m1315l(C0869k.f1842e, C0869k.f1843f);

    static {
        C0875q.f1891c = new C0875q();
    }

    public b0(a0 a0Var) {
        boolean z2;
        this.f1719a = a0Var.f1693a;
        this.f1720b = a0Var.f1694b;
        List list = a0Var.f1695c;
        this.f1721c = list;
        this.f1722d = AbstractC0887c.m1314k(a0Var.f1696d);
        this.f1723e = AbstractC0887c.m1314k(a0Var.f1697e);
        this.f1724f = a0Var.f1698f;
        this.f1725g = a0Var.f1699g;
        this.f1726h = a0Var.f1700h;
        this.f1727i = a0Var.f1701i;
        Iterator it = list.iterator();
        loop0: while (true) {
            z2 = false;
            while (it.hasNext()) {
                z2 = (z2 || ((C0869k) it.next()).f1844a) ? true : z2;
            }
        }
        if (z2) {
            try {
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init((KeyStore) null);
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                if (trustManagers.length == 1) {
                    TrustManager trustManager = trustManagers[0];
                    if (trustManager instanceof X509TrustManager) {
                        X509TrustManager x509TrustManager = (X509TrustManager) trustManager;
                        try {
                            C0966i c0966i = C0966i.f2293a;
                            SSLContext mo1452i = c0966i.mo1452i();
                            mo1452i.init(null, new TrustManager[]{x509TrustManager}, null);
                            this.f1728j = mo1452i.getSocketFactory();
                            this.f1729k = c0966i.mo1449c(x509TrustManager);
                        } catch (GeneralSecurityException e2) {
                            throw new AssertionError("No System TLS", e2);
                        }
                    }
                }
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            } catch (GeneralSecurityException e3) {
                throw new AssertionError("No System TLS", e3);
            }
        }
        this.f1728j = null;
        this.f1729k = null;
        SSLSocketFactory sSLSocketFactory = this.f1728j;
        if (sSLSocketFactory != null) {
            C0966i.f2293a.mo1457f(sSLSocketFactory);
        }
        this.f1730l = a0Var.f1702j;
        AbstractC0026q abstractC0026q = this.f1729k;
        C0865g c0865g = a0Var.f1703k;
        this.f1731m = Objects.equals(c0865g.f1785b, abstractC0026q) ? c0865g : new C0865g(c0865g.f1784a, abstractC0026q);
        this.f1732n = a0Var.f1704l;
        this.f1733o = a0Var.f1705m;
        this.f1734p = a0Var.f1706n;
        this.f1735q = a0Var.f1707o;
        this.f1736r = a0Var.f1708p;
        this.f1737s = a0Var.f1709q;
        this.f1738t = a0Var.f1710r;
        this.f1739u = a0Var.f1711s;
        this.f1740v = a0Var.f1712t;
        this.f1741w = a0Var.f1713u;
        this.f1742x = a0Var.f1714v;
        this.f1743y = a0Var.f1715w;
        if (this.f1722d.contains(null)) {
            throw new IllegalStateException("Null interceptor: " + this.f1722d);
        }
        if (this.f1723e.contains(null)) {
            throw new IllegalStateException("Null network interceptor: " + this.f1723e);
        }
    }
}
