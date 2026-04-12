package p000;

import java.util.concurrent.ThreadFactory;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: kf */
/* loaded from: classes.dex */
public final /* synthetic */ class ThreadFactoryC0756kf implements ThreadFactory {

    /* renamed from: a0 */
    public final /* synthetic */ String f57513a0;

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, this.f57513a0);
        thread.setPriority(10);
        return thread;
    }
}
