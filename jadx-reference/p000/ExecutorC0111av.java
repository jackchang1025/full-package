package p000;

import java.util.concurrent.Executor;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: av */
/* loaded from: classes.dex */
public final /* synthetic */ class ExecutorC0111av implements Executor {

    /* renamed from: a0 */
    public final /* synthetic */ int f45645a0;

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        switch (this.f45645a0) {
            case 0:
                C0112aw.m210524f5().f45650c6.f59797c7.execute(runnable);
                break;
            default:
                runnable.run();
                break;
        }
    }
}
