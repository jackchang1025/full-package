package p000;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: kq */
/* loaded from: classes2.dex */
public final class ThreadFactoryC0792kq implements ThreadFactory {

    /* renamed from: a0 */
    public final AtomicInteger f57705a0 = new AtomicInteger(0);

    /* renamed from: a1 */
    public final /* synthetic */ boolean f57706a1;

    public ThreadFactoryC0792kq(boolean z) {
        this.f57706a1 = z;
    }

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        StringBuilder sbM37b8 = AbstractC0003a2.m37b8(this.f57706a1 ? "WM.task-" : "androidx.work-");
        sbM37b8.append(this.f57705a0.incrementAndGet());
        return new Thread(runnable, sbM37b8.toString());
    }
}
