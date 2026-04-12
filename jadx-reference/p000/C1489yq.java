package p000;

import androidx.recyclerview.widget.RecyclerView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: yq */
/* loaded from: classes.dex */
public final class C1489yq extends sq0 {

    /* renamed from: a0 */
    public final /* synthetic */ C1491ys f61354a0;

    public C1489yq(C1491ys c1491ys) {
        this.f61354a0 = c1491ys;
    }

    @Override // p000.sq0
    /* renamed from: a1 */
    public final void mo211020a1(RecyclerView recyclerView, int i, int i2) {
        int iComputeHorizontalScrollOffset = recyclerView.computeHorizontalScrollOffset();
        int iComputeVerticalScrollOffset = recyclerView.computeVerticalScrollOffset();
        C1491ys c1491ys = this.f61354a0;
        int i3 = c1491ys.f61369a0;
        int iComputeVerticalScrollRange = c1491ys.f61387b8.computeVerticalScrollRange();
        int i4 = c1491ys.f61386b7;
        c1491ys.f61388b9 = iComputeVerticalScrollRange - i4 > 0 && i4 >= i3;
        int iComputeHorizontalScrollRange = c1491ys.f61387b8.computeHorizontalScrollRange();
        int i5 = c1491ys.f61385b6;
        boolean z = iComputeHorizontalScrollRange - i5 > 0 && i5 >= i3;
        c1491ys.f61389c0 = z;
        boolean z2 = c1491ys.f61388b9;
        if (!z2 && !z) {
            if (c1491ys.f61390c1 != 0) {
                c1491ys.m215309a5(0);
                return;
            }
            return;
        }
        if (z2) {
            float f = i4;
            c1491ys.f61380b1 = (int) ((((f / 2.0f) + iComputeVerticalScrollOffset) * f) / iComputeVerticalScrollRange);
            c1491ys.f61379b0 = Math.min(i4, (i4 * i4) / iComputeVerticalScrollRange);
        }
        if (c1491ys.f61389c0) {
            float f2 = iComputeHorizontalScrollOffset;
            float f3 = i5;
            c1491ys.f61383b4 = (int) ((((f3 / 2.0f) + f2) * f3) / iComputeHorizontalScrollRange);
            c1491ys.f61382b3 = Math.min(i5, (i5 * i5) / iComputeHorizontalScrollRange);
        }
        int i6 = c1491ys.f61390c1;
        if (i6 == 0 || i6 == 1) {
            c1491ys.m215309a5(1);
        }
    }
}
