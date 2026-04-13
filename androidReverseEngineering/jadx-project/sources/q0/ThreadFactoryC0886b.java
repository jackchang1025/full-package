package q0;

import java.util.concurrent.ThreadFactory;

/* renamed from: q0.b */
/* loaded from: classes.dex */
public final /* synthetic */ class ThreadFactoryC0886b implements ThreadFactory {

    /* renamed from: a */
    public final /* synthetic */ String f1932a;

    /* renamed from: b */
    public final /* synthetic */ boolean f1933b;

    public /* synthetic */ ThreadFactoryC0886b(String str, boolean z2) {
        this.f1932a = str;
        this.f1933b = z2;
    }

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, this.f1932a);
        thread.setDaemon(this.f1933b);
        return thread;
    }
}
