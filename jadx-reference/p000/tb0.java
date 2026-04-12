package p000;

import android.content.Context;
import androidx.work.WorkerParameters;
import androidx.work.impl.utils.futures.C0100a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class tb0 {

    /* renamed from: a0 */
    public final Context f60190a0;

    /* renamed from: a1 */
    public final WorkerParameters f60191a1;

    /* renamed from: a2 */
    public volatile boolean f60192a2;

    /* renamed from: a3 */
    public boolean f60193a3;

    public tb0(Context context, WorkerParameters workerParameters) {
        if (context == null) {
            throw new IllegalArgumentException("Application Context is null");
        }
        if (workerParameters == null) {
            throw new IllegalArgumentException("WorkerParameters is null");
        }
        this.f60190a0 = context;
        this.f60191a1 = workerParameters;
    }

    /* renamed from: a0 */
    public ob0 mo210453a0() {
        C0100a1 c0100a1 = new C0100a1();
        c0100a1.m210485a9(new IllegalStateException("Expedited WorkRequests require a ListenableWorker to provide an implementation for `getForegroundInfoAsync()`"));
        return c0100a1;
    }

    /* renamed from: a4 */
    public abstract C0100a1 mo210455a4();

    /* renamed from: a5 */
    public final void m214733a5() {
        this.f60192a2 = true;
        mo210454a2();
    }

    /* renamed from: a2 */
    public void mo210454a2() {
    }
}
