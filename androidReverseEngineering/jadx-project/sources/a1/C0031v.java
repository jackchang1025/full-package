package a1;

import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

/* renamed from: a1.v */
/* loaded from: classes.dex */
public class C0031v {

    /* renamed from: d */
    public static final C0030u f71d = new C0030u();

    /* renamed from: a */
    public boolean f72a;

    /* renamed from: b */
    public long f73b;

    /* renamed from: c */
    public long f74c;

    /* renamed from: a */
    public C0031v mo130a() {
        this.f72a = false;
        return this;
    }

    /* renamed from: b */
    public C0031v mo131b() {
        this.f74c = 0L;
        return this;
    }

    /* renamed from: c */
    public long mo132c() {
        if (this.f72a) {
            return this.f73b;
        }
        throw new IllegalStateException("No deadline");
    }

    /* renamed from: d */
    public C0031v mo133d(long j2) {
        this.f72a = true;
        this.f73b = j2;
        return this;
    }

    /* renamed from: e */
    public boolean mo134e() {
        return this.f72a;
    }

    /* renamed from: f */
    public void mo135f() {
        if (Thread.interrupted()) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("interrupted");
        }
        if (this.f72a && this.f73b - System.nanoTime() <= 0) {
            throw new InterruptedIOException("deadline reached");
        }
    }

    /* renamed from: g */
    public C0031v mo136g(long j2, TimeUnit timeUnit) {
        if (j2 < 0) {
            throw new IllegalArgumentException("timeout < 0: " + j2);
        }
        if (timeUnit == null) {
            throw new IllegalArgumentException("unit == null");
        }
        this.f74c = timeUnit.toNanos(j2);
        return this;
    }
}
