package p000;

import java.util.concurrent.ThreadPoolExecutor;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: wh */
/* loaded from: classes.dex */
public final class C1376wh extends cq0 {

    /* renamed from: b0 */
    public final /* synthetic */ cq0 f60932b0;

    /* renamed from: b1 */
    public final /* synthetic */ ThreadPoolExecutor f60933b1;

    public C1376wh(cq0 cq0Var, ThreadPoolExecutor threadPoolExecutor) {
        this.f60932b0 = cq0Var;
        this.f60933b1 = threadPoolExecutor;
    }

    @Override // p000.cq0
    /* renamed from: c5 */
    public final void mo212507c5(Throwable th) {
        ThreadPoolExecutor threadPoolExecutor = this.f60933b1;
        try {
            this.f60932b0.mo212507c5(th);
        } finally {
            threadPoolExecutor.shutdown();
        }
    }

    @Override // p000.cq0
    /* renamed from: c9 */
    public final void mo212511c9(x31 x31Var) {
        ThreadPoolExecutor threadPoolExecutor = this.f60933b1;
        try {
            this.f60932b0.mo212511c9(x31Var);
        } finally {
            threadPoolExecutor.shutdown();
        }
    }
}
