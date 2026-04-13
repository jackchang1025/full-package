package a1;

import java.util.concurrent.TimeUnit;

/* renamed from: a1.i */
/* loaded from: classes.dex */
public final class C0018i extends C0031v {

    /* renamed from: e */
    public C0031v f28e;

    public C0018i(C0031v c0031v) {
        if (c0031v == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.f28e = c0031v;
    }

    @Override // a1.C0031v
    /* renamed from: a */
    public final C0031v mo130a() {
        return this.f28e.mo130a();
    }

    @Override // a1.C0031v
    /* renamed from: b */
    public final C0031v mo131b() {
        return this.f28e.mo131b();
    }

    @Override // a1.C0031v
    /* renamed from: c */
    public final long mo132c() {
        return this.f28e.mo132c();
    }

    @Override // a1.C0031v
    /* renamed from: d */
    public final C0031v mo133d(long j2) {
        return this.f28e.mo133d(j2);
    }

    @Override // a1.C0031v
    /* renamed from: e */
    public final boolean mo134e() {
        return this.f28e.mo134e();
    }

    @Override // a1.C0031v
    /* renamed from: f */
    public final void mo135f() {
        this.f28e.mo135f();
    }

    @Override // a1.C0031v
    /* renamed from: g */
    public final C0031v mo136g(long j2, TimeUnit timeUnit) {
        return this.f28e.mo136g(j2, timeUnit);
    }
}
