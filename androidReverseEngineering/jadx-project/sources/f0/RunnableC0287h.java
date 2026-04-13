package f0;

import h0.InterfaceC0319a;

/* renamed from: f0.h */
/* loaded from: classes.dex */
public final class RunnableC0287h implements InterfaceC0319a, Runnable {

    /* renamed from: a */
    public final C0289j f518a;

    /* renamed from: b */
    public final Runnable f519b;

    /* renamed from: c */
    public final long f520c;

    /* renamed from: d */
    public boolean f521d;

    public RunnableC0287h(C0289j c0289j, Runnable runnable, long j2) {
        this.f518a = c0289j;
        this.f519b = runnable;
        this.f520c = j2;
    }

    @Override // h0.InterfaceC0319a
    public final boolean cancel() {
        boolean remove;
        synchronized (this.f518a) {
            remove = this.f518a.f529d.remove(this);
            this.f521d = remove;
        }
        return remove;
    }

    @Override // h0.InterfaceC0319a
    public final boolean isCancelled() {
        return this.f521d;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f519b.run();
    }
}
