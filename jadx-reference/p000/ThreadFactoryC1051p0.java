package p000;

import java.util.concurrent.ThreadFactory;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: p0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ThreadFactoryC1051p0 implements ThreadFactory {

    /* renamed from: a0 */
    public final /* synthetic */ int f59133a0;

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        switch (this.f59133a0) {
            case 0:
                Thread thread = new Thread(runnable, "ActivityMonitor-FileIO");
                thread.setDaemon(true);
                return thread;
            default:
                Thread thread2 = new Thread(runnable, "BitmapCompression");
                thread2.setPriority(5);
                return thread2;
        }
    }
}
