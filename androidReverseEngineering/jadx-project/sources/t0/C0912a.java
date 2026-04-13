package t0;

import a1.AbstractC0021l;
import a1.AbstractC0026q;
import a1.C0023n;
import a1.C0024o;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.logging.Logger;
import p0.C0875q;
import p0.InterfaceC0881w;
import p0.f0;
import p0.i0;
import p0.j0;
import p0.k0;
import p0.l0;
import p000a.AbstractC0000a;
import q0.AbstractC0887c;
import s0.C0900c;
import s0.C0901d;
import s0.C0902e;
import s0.C0909l;

/* renamed from: t0.a */
/* loaded from: classes.dex */
public final class C0912a implements InterfaceC0881w {

    /* renamed from: a */
    public final boolean f2068a;

    public C0912a(boolean z2) {
        this.f2068a = z2;
    }

    @Override // p0.InterfaceC0881w
    /* renamed from: a */
    public final j0 mo1300a(C0917f c0917f) {
        i0 i0Var;
        boolean z2;
        i0 i0Var2;
        AbstractC0026q abstractC0026q;
        C0902e c0902e = c0917f.f2072c;
        if (c0902e == null) {
            throw new IllegalStateException();
        }
        InterfaceC0913b interfaceC0913b = c0902e.f2014d;
        C0875q c0875q = c0902e.f2012b;
        f0 f0Var = c0917f.f2074e;
        long currentTimeMillis = System.currentTimeMillis();
        try {
            c0875q.getClass();
            interfaceC0913b.mo1372e(f0Var);
            boolean m158I = AbstractC0026q.m158I(f0Var.f1778b);
            C0909l c0909l = c0902e.f2011a;
            if (!m158I || (abstractC0026q = f0Var.f1780d) == null) {
                c0909l.m1364c(c0902e, true, false, null);
                i0Var = null;
                z2 = false;
            } else {
                if ("100-continue".equalsIgnoreCase(f0Var.m1254a("Expect"))) {
                    try {
                        interfaceC0913b.mo1371d();
                        c0875q.getClass();
                        i0Var = c0902e.m1342b(true);
                        z2 = true;
                    } catch (IOException e2) {
                        c0875q.getClass();
                        c0902e.m1343c(e2);
                        throw e2;
                    }
                } else {
                    i0Var = null;
                    z2 = false;
                }
                if (i0Var == null) {
                    c0902e.f2015e = false;
                    long mo196i = abstractC0026q.mo196i();
                    c0875q.getClass();
                    C0900c c0900c = new C0900c(c0902e, interfaceC0913b.mo1369b(f0Var, mo196i), mo196i);
                    Logger logger = AbstractC0021l.f38a;
                    C0023n c0023n = new C0023n(c0900c);
                    abstractC0026q.mo194V(c0023n);
                    c0023n.close();
                } else {
                    c0909l.m1364c(c0902e, true, false, null);
                    if (!(c0902e.m1341a().f2031h != null)) {
                        interfaceC0913b.mo1375h().m1355h();
                    }
                }
            }
            try {
                interfaceC0913b.mo1370c();
                if (!z2) {
                    c0875q.getClass();
                }
                if (i0Var == null) {
                    i0Var = c0902e.m1342b(false);
                }
                i0Var.f1812a = f0Var;
                i0Var.f1816e = c0902e.m1341a().f2029f;
                i0Var.f1822k = currentTimeMillis;
                i0Var.f1823l = System.currentTimeMillis();
                j0 m1260a = i0Var.m1260a();
                int i2 = m1260a.f1831c;
                if (i2 == 100) {
                    i0 m1342b = c0902e.m1342b(false);
                    m1342b.f1812a = f0Var;
                    m1342b.f1816e = c0902e.m1341a().f2029f;
                    m1342b.f1822k = currentTimeMillis;
                    m1342b.f1823l = System.currentTimeMillis();
                    m1260a = m1342b.m1260a();
                    i2 = m1260a.f1831c;
                }
                c0875q.getClass();
                if (this.f2068a && i2 == 101) {
                    i0Var2 = new i0(m1260a);
                    i0Var2.f1818g = AbstractC0887c.f1937d;
                } else {
                    i0Var2 = new i0(m1260a);
                    try {
                        String m1265x = m1260a.m1265x("Content-Type", null);
                        long mo1373f = interfaceC0913b.mo1373f(m1260a);
                        C0901d c0901d = new C0901d(c0902e, interfaceC0913b.mo1368a(m1260a), mo1373f);
                        Logger logger2 = AbstractC0021l.f38a;
                        i0Var2.f1818g = new k0(m1265x, mo1373f, new C0024o(c0901d));
                    } catch (IOException e3) {
                        c0902e.m1343c(e3);
                        throw e3;
                    }
                }
                j0 m1260a2 = i0Var2.m1260a();
                if ("close".equalsIgnoreCase(m1260a2.f1829a.m1254a("Connection")) || "close".equalsIgnoreCase(m1260a2.m1265x("Connection", null))) {
                    interfaceC0913b.mo1375h().m1355h();
                }
                if (i2 == 204 || i2 == 205) {
                    l0 l0Var = m1260a2.f1835g;
                    if (((k0) l0Var).f1849b > 0) {
                        StringBuilder m21q = AbstractC0000a.m21q("HTTP ", i2, " had non-zero Content-Length: ");
                        m21q.append(((k0) l0Var).f1849b);
                        throw new ProtocolException(m21q.toString());
                    }
                }
                return m1260a2;
            } catch (IOException e4) {
                c0875q.getClass();
                c0902e.m1343c(e4);
                throw e4;
            }
        } catch (IOException e5) {
            c0875q.getClass();
            c0902e.m1343c(e5);
            throw e5;
        }
    }
}
