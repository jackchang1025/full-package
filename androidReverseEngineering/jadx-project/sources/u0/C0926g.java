package u0;

import a1.InterfaceC0015f;
import a1.InterfaceC0016g;
import a1.InterfaceC0028s;
import a1.InterfaceC0029t;
import android.support.v4.os.EnvironmentCompat;
import com.guard.wallet.utils.AbstractC0251g;
import java.io.EOFException;
import java.io.IOException;
import java.net.Proxy;
import l0.C0387q;
import com.guard.wallet.entity.BuildConfig;
import p0.C0864f;
import p0.C0875q;
import p0.C0877s;
import p0.C0879u;
import p0.b0;
import p0.c0;
import p0.f0;
import p0.i0;
import p0.j0;
import p000a.AbstractC0000a;
import q0.AbstractC0887c;
import s0.C0904g;
import t0.AbstractC0916e;
import t0.InterfaceC0913b;

/* renamed from: u0.g */
/* loaded from: classes.dex */
public final class C0926g implements InterfaceC0913b {

    /* renamed from: a */
    public final b0 f2104a;

    /* renamed from: b */
    public final C0904g f2105b;

    /* renamed from: c */
    public final InterfaceC0016g f2106c;

    /* renamed from: d */
    public final InterfaceC0015f f2107d;

    /* renamed from: e */
    public int f2108e = 0;

    /* renamed from: f */
    public long f2109f = 262144;

    public C0926g(b0 b0Var, C0904g c0904g, InterfaceC0016g interfaceC0016g, InterfaceC0015f interfaceC0015f) {
        this.f2104a = b0Var;
        this.f2105b = c0904g;
        this.f2106c = interfaceC0016g;
        this.f2107d = interfaceC0015f;
    }

    @Override // t0.InterfaceC0913b
    /* renamed from: a */
    public final InterfaceC0029t mo1368a(j0 j0Var) {
        if (!AbstractC0916e.m1377b(j0Var)) {
            return m1388i(0L);
        }
        if ("chunked".equalsIgnoreCase(j0Var.m1265x("Transfer-Encoding", null))) {
            C0879u c0879u = j0Var.f1829a.f1777a;
            if (this.f2108e == 4) {
                this.f2108e = 5;
                return new C0922c(this, c0879u);
            }
            throw new IllegalStateException("state: " + this.f2108e);
        }
        long m1376a = AbstractC0916e.m1376a(j0Var);
        if (m1376a != -1) {
            return m1388i(m1376a);
        }
        if (this.f2108e == 4) {
            this.f2108e = 5;
            this.f2105b.m1355h();
            return new C0925f(this);
        }
        throw new IllegalStateException("state: " + this.f2108e);
    }

    @Override // t0.InterfaceC0913b
    /* renamed from: b */
    public final InterfaceC0028s mo1369b(f0 f0Var, long j2) {
        if ("chunked".equalsIgnoreCase(f0Var.m1254a("Transfer-Encoding"))) {
            if (this.f2108e == 1) {
                this.f2108e = 2;
                return new C0921b(this);
            }
            throw new IllegalStateException("state: " + this.f2108e);
        }
        if (j2 == -1) {
            throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
        }
        if (this.f2108e == 1) {
            this.f2108e = 2;
            return new C0924e(this);
        }
        throw new IllegalStateException("state: " + this.f2108e);
    }

    @Override // t0.InterfaceC0913b
    /* renamed from: c */
    public final void mo1370c() {
        this.f2107d.flush();
    }

    @Override // t0.InterfaceC0913b
    public final void cancel() {
        C0904g c0904g = this.f2105b;
        if (c0904g != null) {
            AbstractC0887c.m1307d(c0904g.f2027d);
        }
    }

    @Override // t0.InterfaceC0913b
    /* renamed from: d */
    public final void mo1371d() {
        this.f2107d.flush();
    }

    @Override // t0.InterfaceC0913b
    /* renamed from: e */
    public final void mo1372e(f0 f0Var) {
        Proxy.Type type = this.f2105b.f2026c.f1875b.type();
        StringBuilder sb = new StringBuilder();
        sb.append(f0Var.f1778b);
        sb.append(' ');
        C0879u c0879u = f0Var.f1777a;
        if (!c0879u.f1907a.equals("https") && type == Proxy.Type.HTTP) {
            sb.append(c0879u);
        } else {
            sb.append(AbstractC0251g.L0(c0879u));
        }
        sb.append(" HTTP/1.1");
        m1391l(f0Var.f1779c, sb.toString());
    }

    @Override // t0.InterfaceC0913b
    /* renamed from: f */
    public final long mo1373f(j0 j0Var) {
        if (!AbstractC0916e.m1377b(j0Var)) {
            return 0L;
        }
        if ("chunked".equalsIgnoreCase(j0Var.m1265x("Transfer-Encoding", null))) {
            return -1L;
        }
        return AbstractC0916e.m1376a(j0Var);
    }

    @Override // t0.InterfaceC0913b
    /* renamed from: g */
    public final i0 mo1374g(boolean z2) {
        int i2 = this.f2108e;
        if (i2 != 1 && i2 != 3) {
            throw new IllegalStateException("state: " + this.f2108e);
        }
        try {
            C0387q m959a = C0387q.m959a(m1389j());
            int i3 = m959a.f784e;
            i0 i0Var = new i0();
            i0Var.f1813b = (c0) m959a.f786g;
            i0Var.f1814c = i3;
            i0Var.f1815d = m959a.f785f;
            i0Var.f1817f = m1390k().m1282e();
            if (z2 && i3 == 100) {
                return null;
            }
            if (i3 == 100) {
                this.f2108e = 3;
                return i0Var;
            }
            this.f2108e = 4;
            return i0Var;
        } catch (EOFException e2) {
            C0904g c0904g = this.f2105b;
            throw new IOException(AbstractC0000a.m15k("unexpected end of stream on ", c0904g != null ? c0904g.f2026c.f1874a.f1682a.m1298m() : EnvironmentCompat.MEDIA_UNKNOWN), e2);
        }
    }

    @Override // t0.InterfaceC0913b
    /* renamed from: h */
    public final C0904g mo1375h() {
        return this.f2105b;
    }

    /* renamed from: i */
    public final C0923d m1388i(long j2) {
        if (this.f2108e == 4) {
            this.f2108e = 5;
            return new C0923d(this, j2);
        }
        throw new IllegalStateException("state: " + this.f2108e);
    }

    /* renamed from: j */
    public final String m1389j() {
        String mo107q = this.f2106c.mo107q(this.f2109f);
        this.f2109f -= mo107q.length();
        return mo107q;
    }

    /* renamed from: k */
    public final C0877s m1390k() {
        String str;
        C0864f c0864f = new C0864f();
        while (true) {
            String m1389j = m1389j();
            if (m1389j.length() == 0) {
                return new C0877s(c0864f);
            }
            C0875q.f1891c.getClass();
            int indexOf = m1389j.indexOf(":", 1);
            if (indexOf != -1) {
                str = m1389j.substring(0, indexOf);
                m1389j = m1389j.substring(indexOf + 1);
            } else {
                if (m1389j.startsWith(":")) {
                    m1389j = m1389j.substring(1);
                }
                str = BuildConfig.FLAVOR;
            }
            c0864f.m1251a(str, m1389j);
        }
    }

    /* renamed from: l */
    public final void m1391l(C0877s c0877s, String str) {
        if (this.f2108e != 0) {
            throw new IllegalStateException("state: " + this.f2108e);
        }
        InterfaceC0015f interfaceC0015f = this.f2107d;
        interfaceC0015f.mo109s(str).mo109s("\r\n");
        int length = c0877s.f1896a.length / 2;
        for (int i2 = 0; i2 < length; i2++) {
            interfaceC0015f.mo109s(c0877s.m1281d(i2)).mo109s(": ").mo109s(c0877s.m1283f(i2)).mo109s("\r\n");
        }
        interfaceC0015f.mo109s("\r\n");
        this.f2108e = 1;
    }
}
