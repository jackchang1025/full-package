package p000;

import java.util.ArrayDeque;
import java.util.concurrent.Executor;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: an */
/* loaded from: classes.dex */
public final class ExecutorC0034an implements Executor {

    /* renamed from: a0 */
    public final /* synthetic */ int f43724a0;

    /* renamed from: a1 */
    public final ArrayDeque f43725a1;

    /* renamed from: a2 */
    public Runnable f43726a2;

    /* renamed from: a3 */
    public final Object f43727a3;

    /* renamed from: a4 */
    public final Executor f43728a4;

    public ExecutorC0034an(Executor executor) {
        this.f43724a0 = 1;
        this.f43728a4 = executor;
        this.f43725a1 = new ArrayDeque();
        this.f43727a3 = new Object();
    }

    /* renamed from: a0 */
    public final void m209823a0() {
        switch (this.f43724a0) {
            case 0:
                synchronized (this.f43727a3) {
                    try {
                        Runnable runnable = (Runnable) this.f43725a1.poll();
                        this.f43726a2 = runnable;
                        if (runnable != null) {
                            ((ExecutorC0101ao) this.f43728a4).execute(runnable);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
            default:
                Runnable runnable2 = (Runnable) this.f43725a1.poll();
                this.f43726a2 = runnable2;
                if (runnable2 != null) {
                    this.f43728a4.execute(runnable2);
                    return;
                }
                return;
        }
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        switch (this.f43724a0) {
            case 0:
                synchronized (this.f43727a3) {
                    try {
                        this.f43725a1.add(new RunnableC1052p1(this, 3, runnable));
                        if (this.f43726a2 == null) {
                            m209823a0();
                        }
                    } finally {
                    }
                }
                return;
            default:
                synchronized (this.f43727a3) {
                    try {
                        this.f43725a1.add(new RunnableC0884n2(this, runnable, 11, false));
                        if (this.f43726a2 == null) {
                            m209823a0();
                        }
                    } finally {
                    }
                }
                return;
        }
    }

    public ExecutorC0034an(ExecutorC0101ao executorC0101ao) {
        this.f43724a0 = 0;
        this.f43727a3 = new Object();
        this.f43725a1 = new ArrayDeque();
        this.f43728a4 = executorC0101ao;
    }
}
