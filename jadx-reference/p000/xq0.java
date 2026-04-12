package p000;

import androidx.recyclerview.widget.RecyclerView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class xq0 extends iq0 {

    /* renamed from: a0 */
    public final /* synthetic */ RecyclerView f61170a0;

    public xq0(RecyclerView recyclerView) {
        this.f61170a0 = recyclerView;
    }

    @Override // p000.iq0
    /* renamed from: a0 */
    public final void mo209779a0() {
        RecyclerView recyclerView = this.f61170a0;
        recyclerView.m210349a8(null);
        recyclerView.f45306f2.f45601a5 = true;
        recyclerView.m210384e6(true);
        if (recyclerView.f45257a3.m214343a5()) {
            return;
        }
        recyclerView.requestLayout();
    }
}
