package f0;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: f0.g */
/* loaded from: classes.dex */
public final class ThreadFactoryC0286g implements ThreadFactory {

    /* renamed from: a */
    public final ThreadGroup f515a;

    /* renamed from: b */
    public final AtomicInteger f516b = new AtomicInteger(1);

    /* renamed from: c */
    public final String f517c;

    public ThreadFactoryC0286g(String str) {
        SecurityManager securityManager = System.getSecurityManager();
        this.f515a = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.f517c = str;
    }

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        Thread thread = new Thread(this.f515a, runnable, this.f517c + this.f516b.getAndIncrement(), 0L);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        if (thread.getPriority() != 5) {
            thread.setPriority(5);
        }
        return thread;
    }
}
