package s0;

import com.guard.wallet.http.C0203h;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.ref.Reference;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import p0.C0875q;
import p0.b0;
import p0.e0;
import p0.f0;
import q0.AbstractC0887c;

/* renamed from: s0.l */
/* loaded from: classes.dex */
public final class C0909l {

    /* renamed from: a */
    public final b0 f2053a;

    /* renamed from: b */
    public final C0905h f2054b;

    /* renamed from: c */
    public final e0 f2055c;

    /* renamed from: d */
    public final C0875q f2056d;

    /* renamed from: e */
    public final C0907j f2057e;

    /* renamed from: f */
    public Object f2058f;

    /* renamed from: g */
    public f0 f2059g;

    /* renamed from: h */
    public C0903f f2060h;

    /* renamed from: i */
    public C0904g f2061i;

    /* renamed from: j */
    public C0902e f2062j;

    /* renamed from: k */
    public boolean f2063k;

    /* renamed from: l */
    public boolean f2064l;

    /* renamed from: m */
    public boolean f2065m;

    /* renamed from: n */
    public boolean f2066n;

    /* renamed from: o */
    public boolean f2067o;

    public C0909l(b0 b0Var, e0 e0Var) {
        C0907j c0907j = new C0907j(this, 0);
        this.f2057e = c0907j;
        this.f2053a = b0Var;
        C0875q c0875q = C0875q.f1891c;
        C0203h c0203h = b0Var.f1734p;
        c0875q.getClass();
        this.f2054b = (C0905h) c0203h.f245e;
        this.f2055c = e0Var;
        this.f2056d = (C0875q) b0Var.f1724f.f531d;
        c0907j.mo136g(b0Var.f1739u, TimeUnit.MILLISECONDS);
    }

    /* renamed from: a */
    public final void m1362a() {
        C0902e c0902e;
        C0904g c0904g;
        synchronized (this.f2054b) {
            this.f2065m = true;
            c0902e = this.f2062j;
            C0903f c0903f = this.f2060h;
            if (c0903f == null || (c0904g = c0903f.f2022g) == null) {
                c0904g = this.f2061i;
            }
        }
        if (c0902e != null) {
            c0902e.f2014d.cancel();
        } else if (c0904g != null) {
            AbstractC0887c.m1307d(c0904g.f2027d);
        }
    }

    /* renamed from: b */
    public final void m1363b() {
        synchronized (this.f2054b) {
            if (this.f2067o) {
                throw new IllegalStateException();
            }
            this.f2062j = null;
        }
    }

    /* renamed from: c */
    public final IOException m1364c(C0902e c0902e, boolean z2, boolean z3, IOException iOException) {
        boolean z4;
        synchronized (this.f2054b) {
            C0902e c0902e2 = this.f2062j;
            if (c0902e != c0902e2) {
                return iOException;
            }
            boolean z5 = true;
            if (z2) {
                z4 = !this.f2063k;
                this.f2063k = true;
            } else {
                z4 = false;
            }
            if (z3) {
                if (!this.f2064l) {
                    z4 = true;
                }
                this.f2064l = true;
            }
            if (this.f2063k && this.f2064l && z4) {
                c0902e2.m1341a().f2036m++;
                this.f2062j = null;
            } else {
                z5 = false;
            }
            return z5 ? m1365d(iOException, false) : iOException;
        }
    }

    /* renamed from: d */
    public final IOException m1365d(IOException iOException, boolean z2) {
        C0904g c0904g;
        Socket m1367f;
        boolean z3;
        synchronized (this.f2054b) {
            if (z2) {
                if (this.f2062j != null) {
                    throw new IllegalStateException("cannot release connection while it is in use");
                }
            }
            c0904g = this.f2061i;
            m1367f = (c0904g != null && this.f2062j == null && (z2 || this.f2067o)) ? m1367f() : null;
            if (this.f2061i != null) {
                c0904g = null;
            }
            z3 = this.f2067o && this.f2062j == null;
        }
        AbstractC0887c.m1307d(m1367f);
        if (c0904g != null) {
            this.f2056d.getClass();
        }
        if (z3) {
            if (!this.f2066n && this.f2057e.m74l()) {
                InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
                if (iOException != null) {
                    interruptedIOException.initCause(iOException);
                }
                iOException = interruptedIOException;
            }
            this.f2056d.getClass();
        }
        return iOException;
    }

    /* renamed from: e */
    public final IOException m1366e(IOException iOException) {
        synchronized (this.f2054b) {
            this.f2067o = true;
        }
        return m1365d(iOException, false);
    }

    /* renamed from: f */
    public final Socket m1367f() {
        int size = this.f2061i.f2039p.size();
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                i2 = -1;
                break;
            }
            if (((Reference) this.f2061i.f2039p.get(i2)).get() == this) {
                break;
            }
            i2++;
        }
        if (i2 == -1) {
            throw new IllegalStateException();
        }
        C0904g c0904g = this.f2061i;
        c0904g.f2039p.remove(i2);
        this.f2061i = null;
        if (c0904g.f2039p.isEmpty()) {
            c0904g.f2040q = System.nanoTime();
            C0905h c0905h = this.f2054b;
            c0905h.getClass();
            if (c0904g.f2034k || c0905h.f2042a == 0) {
                c0905h.f2045d.remove(c0904g);
                z2 = true;
            } else {
                c0905h.notifyAll();
            }
            if (z2) {
                return c0904g.f2028e;
            }
        }
        return null;
    }
}
