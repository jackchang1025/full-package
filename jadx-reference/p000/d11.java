package p000;

import androidx.recyclerview.widget.RecyclerView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class d11 extends sq0 {

    /* renamed from: a0 */
    public boolean f55554a0 = false;

    /* renamed from: a1 */
    public final /* synthetic */ fm0 f55555a1;

    public d11(fm0 fm0Var) {
        this.f55555a1 = fm0Var;
    }

    @Override // p000.sq0
    /* renamed from: a0 */
    public final void mo211019a0(RecyclerView recyclerView, int i) {
        if (i == 0 && this.f55554a0) {
            this.f55554a0 = false;
            this.f55555a1.m212840a7();
        }
    }

    @Override // p000.sq0
    /* renamed from: a1 */
    public final void mo211020a1(RecyclerView recyclerView, int i, int i2) {
        if (i == 0 && i2 == 0) {
            return;
        }
        this.f55554a0 = true;
    }
}
