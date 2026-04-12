package p000;

import android.os.Process;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class mr0 extends Thread {

    /* renamed from: a0 */
    public final int f58394a0;

    public mr0(Runnable runnable) {
        super(runnable, "fonts-androidx");
        this.f58394a0 = 10;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() throws SecurityException, IllegalArgumentException {
        Process.setThreadPriority(this.f58394a0);
        super.run();
    }
}
