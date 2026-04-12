package p000;

import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class li0 extends C0608i4 {

    /* renamed from: a3 */
    public final /* synthetic */ int f58003a3;

    /* renamed from: a4 */
    public final /* synthetic */ boolean f58004a4;

    /* renamed from: a5 */
    public final /* synthetic */ mi0 f58005a5;

    public li0(mi0 mi0Var, int i, boolean z) {
        this.f58005a5 = mi0Var;
        this.f58003a3 = i;
        this.f58004a4 = z;
    }

    @Override // p000.C0608i4
    /* renamed from: a3 */
    public final void mo210912a3(View view, C0748k7 c0748k7) {
        this.f56792a0.onInitializeAccessibilityNodeInfo(view, c0748k7.f57472a0);
        ui0 ui0Var = this.f58005a5.f58377a5;
        int i = this.f58003a3;
        int i2 = i;
        for (int i3 = 0; i3 < i; i3++) {
            if (ui0Var.f60431a4.mo212978a2(i3) == 2) {
                i2--;
            }
        }
        if (ui0Var.f60428a1.getChildCount() == 0) {
            i2--;
        }
        c0748k7.m213465a8(C0747k6.m213451a1(i2, 1, 1, 1, this.f58004a4, view.isSelected()));
    }
}
