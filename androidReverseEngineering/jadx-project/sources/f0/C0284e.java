package f0;

import java.util.PriorityQueue;

/* renamed from: f0.e */
/* loaded from: classes.dex */
public final class C0284e extends Thread {

    /* renamed from: a */
    public final /* synthetic */ C0305z f512a;

    /* renamed from: b */
    public final /* synthetic */ PriorityQueue f513b;

    /* renamed from: c */
    public final /* synthetic */ C0289j f514c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0284e(C0289j c0289j, String str, C0305z c0305z, PriorityQueue priorityQueue) {
        super(str);
        this.f514c = c0289j;
        this.f512a = c0305z;
        this.f513b = priorityQueue;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        C0289j c0289j = this.f514c;
        try {
            ThreadLocal threadLocal = C0289j.f525h;
            threadLocal.set(c0289j);
            C0289j.m793a(c0289j, this.f512a, this.f513b);
            threadLocal.remove();
        } catch (Throwable th) {
            C0289j.f525h.remove();
            throw th;
        }
    }
}
