package a1;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

/* renamed from: a1.d */
/* loaded from: classes.dex */
public class C0013d extends C0031v {

    /* renamed from: h */
    public static final long f14h;

    /* renamed from: i */
    public static final long f15i;

    /* renamed from: j */
    public static C0013d f16j;

    /* renamed from: e */
    public boolean f17e;

    /* renamed from: f */
    public C0013d f18f;

    /* renamed from: g */
    public long f19g;

    static {
        long millis = TimeUnit.SECONDS.toMillis(60L);
        f14h = millis;
        f15i = TimeUnit.MILLISECONDS.toNanos(millis);
    }

    /* renamed from: h */
    public static C0013d m70h() {
        C0013d c0013d = f16j.f18f;
        long nanoTime = System.nanoTime();
        if (c0013d == null) {
            C0013d.class.wait(f14h);
            if (f16j.f18f != null || System.nanoTime() - nanoTime < f15i) {
                return null;
            }
            return f16j;
        }
        long j2 = c0013d.f19g - nanoTime;
        if (j2 > 0) {
            long j3 = j2 / 1000000;
            C0013d.class.wait(j3, (int) (j2 - (1000000 * j3)));
            return null;
        }
        f16j.f18f = c0013d.f18f;
        c0013d.f18f = null;
        return c0013d;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0065 A[Catch: all -> 0x0072, TRY_LEAVE, TryCatch #0 {, blocks: (B:11:0x0017, B:13:0x001b, B:14:0x002a, B:17:0x0032, B:18:0x003e, B:19:0x004a, B:20:0x004f, B:22:0x0053, B:27:0x005d, B:29:0x0065, B:35:0x0044, B:36:0x006c, B:37:0x0071), top: B:10:0x0017 }] */
    /* renamed from: i */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m71i() {
        C0013d c0013d;
        C0013d c0013d2;
        if (this.f17e) {
            throw new IllegalStateException("Unbalanced enter/exit");
        }
        long j2 = this.f74c;
        boolean z2 = this.f72a;
        if (j2 != 0 || z2) {
            this.f17e = true;
            synchronized (C0013d.class) {
                if (f16j == null) {
                    f16j = new C0013d();
                    new C0012c().start();
                }
                long nanoTime = System.nanoTime();
                if (j2 != 0 && z2) {
                    j2 = Math.min(j2, mo132c() - nanoTime);
                } else if (j2 == 0) {
                    if (!z2) {
                        throw new AssertionError();
                    }
                    this.f19g = mo132c();
                    long j3 = this.f19g - nanoTime;
                    c0013d = f16j;
                    while (true) {
                        c0013d2 = c0013d.f18f;
                        if (c0013d2 != null || j3 < c0013d2.f19g - nanoTime) {
                            break;
                        } else {
                            c0013d = c0013d2;
                        }
                    }
                    this.f18f = c0013d2;
                    c0013d.f18f = this;
                    if (c0013d == f16j) {
                        C0013d.class.notify();
                    }
                }
                this.f19g = j2 + nanoTime;
                long j32 = this.f19g - nanoTime;
                c0013d = f16j;
                while (true) {
                    c0013d2 = c0013d.f18f;
                    if (c0013d2 != null) {
                        break;
                        break;
                    }
                    c0013d = c0013d2;
                }
                this.f18f = c0013d2;
                c0013d.f18f = this;
                if (c0013d == f16j) {
                }
            }
        }
    }

    /* renamed from: j */
    public final IOException m72j(IOException iOException) {
        return !m74l() ? iOException : mo75m(iOException);
    }

    /* renamed from: k */
    public final void m73k(boolean z2) {
        if (m74l() && z2) {
            throw mo75m(null);
        }
    }

    /* renamed from: l */
    public final boolean m74l() {
        if (!this.f17e) {
            return false;
        }
        this.f17e = false;
        synchronized (C0013d.class) {
            C0013d c0013d = f16j;
            while (c0013d != null) {
                C0013d c0013d2 = c0013d.f18f;
                if (c0013d2 == this) {
                    c0013d.f18f = this.f18f;
                    this.f18f = null;
                    return false;
                }
                c0013d = c0013d2;
            }
            return true;
        }
    }

    /* renamed from: m */
    public InterruptedIOException mo75m(IOException iOException) {
        InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException != null) {
            interruptedIOException.initCause(iOException);
        }
        return interruptedIOException;
    }

    /* renamed from: n */
    public void mo76n() {
    }
}
