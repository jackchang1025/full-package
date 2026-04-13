package s0;

import a1.C0014e;
import a1.C0031v;
import a1.InterfaceC0028s;
import java.io.IOException;
import java.net.ProtocolException;

/* renamed from: s0.c */
/* loaded from: classes.dex */
public final class C0900c implements InterfaceC0028s {

    /* renamed from: a */
    public final InterfaceC0028s f1999a;

    /* renamed from: b */
    public boolean f2000b;

    /* renamed from: c */
    public final long f2001c;

    /* renamed from: d */
    public long f2002d;

    /* renamed from: e */
    public boolean f2003e;

    /* renamed from: f */
    public final /* synthetic */ C0902e f2004f;

    public C0900c(C0902e c0902e, InterfaceC0028s interfaceC0028s, long j2) {
        this.f2004f = c0902e;
        if (interfaceC0028s == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.f1999a = interfaceC0028s;
        this.f2001c = j2;
    }

    /* renamed from: A, reason: merged with bridge method [inline-methods] */
    public final String toString() {
        return C0900c.class.getSimpleName() + "(" + this.f1999a.toString() + ")";
    }

    @Override // a1.InterfaceC0028s
    /* renamed from: a */
    public final C0031v mo66a() {
        return this.f1999a.mo66a();
    }

    @Override // a1.InterfaceC0028s, java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        if (this.f2003e) {
            return;
        }
        this.f2003e = true;
        long j2 = this.f2001c;
        if (j2 != -1 && this.f2002d != j2) {
            throw new ProtocolException("unexpected end of stream");
        }
        try {
            m1335x();
            m1336y(null);
        } catch (IOException e2) {
            throw m1336y(e2);
        }
    }

    @Override // a1.InterfaceC0028s, java.io.Flushable
    public final void flush() {
        try {
            m1337z();
        } catch (IOException e2) {
            throw m1336y(e2);
        }
    }

    @Override // a1.InterfaceC0028s
    /* renamed from: i */
    public final void mo67i(C0014e c0014e, long j2) {
        if (this.f2003e) {
            throw new IllegalStateException("closed");
        }
        long j3 = this.f2001c;
        if (j3 == -1 || this.f2002d + j2 <= j3) {
            try {
                this.f1999a.mo67i(c0014e, j2);
                this.f2002d += j2;
                return;
            } catch (IOException e2) {
                throw m1336y(e2);
            }
        }
        throw new ProtocolException("expected " + j3 + " bytes but received " + (this.f2002d + j2));
    }

    /* renamed from: x */
    public final void m1335x() {
        this.f1999a.close();
    }

    /* renamed from: y */
    public final IOException m1336y(IOException iOException) {
        if (this.f2000b) {
            return iOException;
        }
        this.f2000b = true;
        C0902e c0902e = this.f2004f;
        if (iOException != null) {
            c0902e.m1343c(iOException);
        }
        c0902e.f2012b.getClass();
        return c0902e.f2011a.m1364c(c0902e, true, false, iOException);
    }

    /* renamed from: z */
    public final void m1337z() {
        this.f1999a.flush();
    }
}
