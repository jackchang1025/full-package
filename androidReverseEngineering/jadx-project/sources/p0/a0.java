package p0;

import com.guard.wallet.http.C0203h;
import f0.C0291l;
import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.List;
import javax.net.SocketFactory;
import m0.C0401b;
import x0.C0971a;
import z0.C0984c;

/* loaded from: classes.dex */
public final class a0 {

    /* renamed from: g */
    public final ProxySelector f1699g;

    /* renamed from: h */
    public InterfaceC0872n f1700h;

    /* renamed from: i */
    public final SocketFactory f1701i;

    /* renamed from: j */
    public final C0984c f1702j;

    /* renamed from: k */
    public final C0865g f1703k;

    /* renamed from: l */
    public final C0401b f1704l;

    /* renamed from: m */
    public final C0401b f1705m;

    /* renamed from: n */
    public final C0203h f1706n;

    /* renamed from: o */
    public final C0401b f1707o;

    /* renamed from: p */
    public boolean f1708p;

    /* renamed from: q */
    public boolean f1709q;

    /* renamed from: r */
    public boolean f1710r;

    /* renamed from: s */
    public int f1711s;

    /* renamed from: t */
    public int f1712t;

    /* renamed from: u */
    public int f1713u;

    /* renamed from: v */
    public int f1714v;

    /* renamed from: w */
    public int f1715w;

    /* renamed from: d */
    public final ArrayList f1696d = new ArrayList();

    /* renamed from: e */
    public final ArrayList f1697e = new ArrayList();

    /* renamed from: a */
    public final C0873o f1693a = new C0873o();

    /* renamed from: b */
    public final List f1694b = b0.f1718z;

    /* renamed from: c */
    public final List f1695c = b0.f1717A;

    /* renamed from: f */
    public final C0291l f1698f = new C0291l(C0875q.f1890b);

    public a0() {
        ProxySelector proxySelector = ProxySelector.getDefault();
        this.f1699g = proxySelector;
        if (proxySelector == null) {
            this.f1699g = new C0971a();
        }
        this.f1700h = InterfaceC0872n.f1877b;
        this.f1701i = SocketFactory.getDefault();
        this.f1702j = C0984c.f2330a;
        this.f1703k = C0865g.f1783c;
        C0401b c0401b = InterfaceC0860b.f1716a;
        this.f1704l = c0401b;
        this.f1705m = c0401b;
        this.f1706n = new C0203h(8);
        this.f1707o = InterfaceC0874p.f1889c;
        this.f1708p = true;
        this.f1709q = true;
        this.f1710r = true;
        this.f1711s = 0;
        this.f1712t = 10000;
        this.f1713u = 10000;
        this.f1714v = 10000;
        this.f1715w = 0;
    }
}
