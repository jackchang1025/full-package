package o1;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: o1.b */
/* loaded from: classes.dex */
public final class ThreadFactoryC0448b implements ThreadFactory {

    /* renamed from: a */
    public final ThreadFactory f1054a = Executors.defaultThreadFactory();

    /* renamed from: b */
    public final AtomicInteger f1055b = new AtomicInteger(1);

    /* renamed from: c */
    public final String f1056c = "WebSocketConnectionLostChecker";

    /* renamed from: d */
    public final boolean f1057d;

    public ThreadFactoryC0448b(boolean z2) {
        this.f1057d = z2;
    }

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        Thread newThread = this.f1054a.newThread(runnable);
        newThread.setDaemon(this.f1057d);
        newThread.setName(this.f1056c + "-" + this.f1055b);
        return newThread;
    }
}
