package p0;

import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import java.util.Objects;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import m0.C0401b;
import p000a.AbstractC0000a;
import q0.AbstractC0887c;
import z0.C0984c;

/* renamed from: p0.a */
/* loaded from: classes.dex */
public final class C0859a {

    /* renamed from: a */
    public final C0879u f1682a;

    /* renamed from: b */
    public final InterfaceC0874p f1683b;

    /* renamed from: c */
    public final SocketFactory f1684c;

    /* renamed from: d */
    public final InterfaceC0860b f1685d;

    /* renamed from: e */
    public final List f1686e;

    /* renamed from: f */
    public final List f1687f;

    /* renamed from: g */
    public final ProxySelector f1688g;

    /* renamed from: h */
    public final Proxy f1689h;

    /* renamed from: i */
    public final SSLSocketFactory f1690i;

    /* renamed from: j */
    public final HostnameVerifier f1691j;

    /* renamed from: k */
    public final C0865g f1692k;

    public C0859a(String str, int i2, C0401b c0401b, SocketFactory socketFactory, SSLSocketFactory sSLSocketFactory, C0984c c0984c, C0865g c0865g, C0401b c0401b2, List list, List list2, ProxySelector proxySelector) {
        C0878t c0878t = new C0878t();
        String str2 = "https";
        String str3 = sSLSocketFactory != null ? "https" : "http";
        if (str3.equalsIgnoreCase("http")) {
            str2 = "http";
        } else if (!str3.equalsIgnoreCase("https")) {
            throw new IllegalArgumentException("unexpected scheme: ".concat(str3));
        }
        c0878t.f1901e = str2;
        if (str == null) {
            throw new NullPointerException("host == null");
        }
        String m1304a = AbstractC0887c.m1304a(C0879u.m1289i(str, 0, str.length(), false));
        if (m1304a == null) {
            throw new IllegalArgumentException("unexpected host: ".concat(str));
        }
        c0878t.f1904h = m1304a;
        if (i2 <= 0 || i2 > 65535) {
            throw new IllegalArgumentException(AbstractC0000a.m11g("unexpected port: ", i2));
        }
        c0878t.f1899c = i2;
        this.f1682a = c0878t.m1284a();
        if (c0401b == null) {
            throw new NullPointerException("dns == null");
        }
        this.f1683b = c0401b;
        if (socketFactory == null) {
            throw new NullPointerException("socketFactory == null");
        }
        this.f1684c = socketFactory;
        if (c0401b2 == null) {
            throw new NullPointerException("proxyAuthenticator == null");
        }
        this.f1685d = c0401b2;
        if (list == null) {
            throw new NullPointerException("protocols == null");
        }
        this.f1686e = AbstractC0887c.m1314k(list);
        if (list2 == null) {
            throw new NullPointerException("connectionSpecs == null");
        }
        this.f1687f = AbstractC0887c.m1314k(list2);
        if (proxySelector == null) {
            throw new NullPointerException("proxySelector == null");
        }
        this.f1688g = proxySelector;
        this.f1689h = null;
        this.f1690i = sSLSocketFactory;
        this.f1691j = c0984c;
        this.f1692k = c0865g;
    }

    /* renamed from: a */
    public final boolean m1242a(C0859a c0859a) {
        return this.f1683b.equals(c0859a.f1683b) && this.f1685d.equals(c0859a.f1685d) && this.f1686e.equals(c0859a.f1686e) && this.f1687f.equals(c0859a.f1687f) && this.f1688g.equals(c0859a.f1688g) && Objects.equals(this.f1689h, c0859a.f1689h) && Objects.equals(this.f1690i, c0859a.f1690i) && Objects.equals(this.f1691j, c0859a.f1691j) && Objects.equals(this.f1692k, c0859a.f1692k) && this.f1682a.f1911e == c0859a.f1682a.f1911e;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof C0859a) {
            C0859a c0859a = (C0859a) obj;
            if (this.f1682a.equals(c0859a.f1682a) && m1242a(c0859a)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.f1692k) + ((Objects.hashCode(this.f1691j) + ((Objects.hashCode(this.f1690i) + ((Objects.hashCode(this.f1689h) + ((this.f1688g.hashCode() + ((this.f1687f.hashCode() + ((this.f1686e.hashCode() + ((this.f1685d.hashCode() + ((this.f1683b.hashCode() + ((this.f1682a.hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Address{");
        C0879u c0879u = this.f1682a;
        sb.append(c0879u.f1910d);
        sb.append(":");
        sb.append(c0879u.f1911e);
        Object obj = this.f1689h;
        if (obj != null) {
            sb.append(", proxy=");
        } else {
            sb.append(", proxySelector=");
            obj = this.f1688g;
        }
        sb.append(obj);
        sb.append("}");
        return sb.toString();
    }
}
