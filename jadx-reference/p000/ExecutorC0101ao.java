package p000;

import java.util.concurrent.Executor;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ao */
/* loaded from: classes.dex */
public final class ExecutorC0101ao implements Executor {

    /* renamed from: a0 */
    public final /* synthetic */ int f45593a0;

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        switch (this.f45593a0) {
            case 0:
                new Thread(runnable).start();
                break;
            default:
                runnable.run();
                break;
        }
    }
}
