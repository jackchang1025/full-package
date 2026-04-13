package s0;

import a1.C0014e;
import a1.C0031v;
import a1.InterfaceC0029t;
import java.io.IOException;
import java.net.ProtocolException;

/* renamed from: s0.d */
/* loaded from: classes.dex */
public final class C0901d implements InterfaceC0029t {

    /* renamed from: a */
    public final InterfaceC0029t f2005a;

    /* renamed from: b */
    public final long f2006b;

    /* renamed from: c */
    public long f2007c;

    /* renamed from: d */
    public boolean f2008d;

    /* renamed from: e */
    public boolean f2009e;

    /* renamed from: f */
    public final /* synthetic */ C0902e f2010f;

    public C0901d(C0902e c0902e, InterfaceC0029t interfaceC0029t, long j2) {
        this.f2010f = c0902e;
        if (interfaceC0029t == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.f2005a = interfaceC0029t;
        this.f2006b = j2;
        if (j2 == 0) {
            m1339y(null);
        }
    }

    @Override // a1.InterfaceC0029t
    /* renamed from: a */
    public final C0031v mo68a() {
        return this.f2005a.mo68a();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        if (this.f2009e) {
            return;
        }
        this.f2009e = true;
        try {
            m1338x();
            m1339y(null);
        } catch (IOException e2) {
            throw m1339y(e2);
        }
    }

    @Override // a1.InterfaceC0029t
    /* renamed from: u */
    public final long mo69u(C0014e c0014e, long j2) {
        if (this.f2009e) {
            throw new IllegalStateException("closed");
        }
        try {
            long mo69u = this.f2005a.mo69u(c0014e, j2);
            if (mo69u == -1) {
                m1339y(null);
                return -1L;
            }
            long j3 = this.f2007c + mo69u;
            long j4 = this.f2006b;
            if (j4 == -1 || j3 <= j4) {
                this.f2007c = j3;
                if (j3 == j4) {
                    m1339y(null);
                }
                return mo69u;
            }
            throw new ProtocolException("expected " + j4 + " bytes but received " + j3);
        } catch (IOException e2) {
            throw m1339y(e2);
        }
    }

    /* renamed from: x */
    public final void m1338x() {
        this.f2005a.close();
    }

    /* renamed from: y */
    public final IOException m1339y(IOException iOException) {
        if (this.f2008d) {
            return iOException;
        }
        this.f2008d = true;
        C0902e c0902e = this.f2010f;
        if (iOException != null) {
            c0902e.m1343c(iOException);
        }
        c0902e.f2012b.getClass();
        return c0902e.f2011a.m1364c(c0902e, false, true, iOException);
    }

    /* renamed from: z, reason: merged with bridge method [inline-methods] */
    public final String toString() {
        return C0901d.class.getSimpleName() + "(" + this.f2005a.toString() + ")";
    }
}
