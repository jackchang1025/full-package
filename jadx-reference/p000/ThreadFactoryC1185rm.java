package p000;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: rm */
/* loaded from: classes.dex */
public final class ThreadFactoryC1185rm implements ThreadFactory {

    /* renamed from: a0 */
    public final AtomicInteger f59793a0 = new AtomicInteger(0);

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName("arch_disk_io_" + this.f59793a0.getAndIncrement());
        return thread;
    }
}
