package a1;

import java.io.IOException;
import java.io.OutputStream;
import s0.C0907j;

/* renamed from: a1.a */
/* loaded from: classes.dex */
public final class C0010a implements InterfaceC0028s {

    /* renamed from: a */
    public final /* synthetic */ int f8a = 0;

    /* renamed from: b */
    public final /* synthetic */ Object f9b;

    /* renamed from: c */
    public final /* synthetic */ Object f10c;

    public C0010a(C0907j c0907j, C0010a c0010a) {
        this.f10c = c0907j;
        this.f9b = c0010a;
    }

    @Override // a1.InterfaceC0028s
    /* renamed from: a */
    public final C0031v mo66a() {
        switch (this.f8a) {
            case 0:
                return (C0013d) this.f10c;
            default:
                return (C0031v) this.f9b;
        }
    }

    @Override // a1.InterfaceC0028s, java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        int i2 = this.f8a;
        Object obj = this.f10c;
        switch (i2) {
            case 0:
                C0013d c0013d = (C0013d) obj;
                c0013d.m71i();
                try {
                    try {
                        ((InterfaceC0028s) this.f9b).close();
                        c0013d.m73k(true);
                        return;
                    } catch (IOException e2) {
                        throw ((C0013d) obj).m72j(e2);
                    }
                } catch (Throwable th) {
                    c0013d.m73k(false);
                    throw th;
                }
            default:
                ((OutputStream) obj).close();
                return;
        }
    }

    @Override // a1.InterfaceC0028s, java.io.Flushable
    public final void flush() {
        int i2 = this.f8a;
        Object obj = this.f10c;
        switch (i2) {
            case 0:
                C0013d c0013d = (C0013d) obj;
                c0013d.m71i();
                try {
                    try {
                        ((InterfaceC0028s) this.f9b).flush();
                        c0013d.m73k(true);
                        return;
                    } catch (IOException e2) {
                        throw ((C0013d) obj).m72j(e2);
                    }
                } catch (Throwable th) {
                    c0013d.m73k(false);
                    throw th;
                }
            default:
                ((OutputStream) obj).flush();
                return;
        }
    }

    @Override // a1.InterfaceC0028s
    /* renamed from: i */
    public final void mo67i(C0014e c0014e, long j2) {
        int i2 = this.f8a;
        Object obj = this.f9b;
        Object obj2 = this.f10c;
        switch (i2) {
            case 0:
                AbstractC0032w.m200a(c0014e.f22b, 0L, j2);
                while (j2 > 0) {
                    C0025p c0025p = c0014e.f21a;
                    long j3 = 0;
                    while (true) {
                        if (j3 < 65536) {
                            j3 += c0025p.f50c - c0025p.f49b;
                            if (j3 >= j2) {
                                j3 = j2;
                            } else {
                                c0025p = c0025p.f53f;
                            }
                        }
                    }
                    C0013d c0013d = (C0013d) obj2;
                    c0013d.m71i();
                    try {
                        try {
                            ((InterfaceC0028s) obj).mo67i(c0014e, j3);
                            j2 -= j3;
                            c0013d.m73k(true);
                        } catch (IOException e2) {
                            throw ((C0013d) obj2).m72j(e2);
                        }
                    } catch (Throwable th) {
                        c0013d.m73k(false);
                        throw th;
                    }
                }
                return;
            default:
                AbstractC0032w.m200a(c0014e.f22b, 0L, j2);
                while (j2 > 0) {
                    ((C0031v) obj).mo135f();
                    C0025p c0025p2 = c0014e.f21a;
                    int min = (int) Math.min(j2, c0025p2.f50c - c0025p2.f49b);
                    ((OutputStream) obj2).write(c0025p2.f48a, c0025p2.f49b, min);
                    int i3 = c0025p2.f49b + min;
                    c0025p2.f49b = i3;
                    long j4 = min;
                    j2 -= j4;
                    c0014e.f22b -= j4;
                    if (i3 == c0025p2.f50c) {
                        c0014e.f21a = c0025p2.m146a();
                        AbstractC0026q.m161L(c0025p2);
                    }
                }
                return;
        }
    }

    public final String toString() {
        switch (this.f8a) {
            case 0:
                return "AsyncTimeout.sink(" + ((InterfaceC0028s) this.f9b) + ")";
            default:
                return "sink(" + ((OutputStream) this.f10c) + ")";
        }
    }

    public C0010a(C0907j c0907j, OutputStream outputStream) {
        this.f9b = c0907j;
        this.f10c = outputStream;
    }
}
