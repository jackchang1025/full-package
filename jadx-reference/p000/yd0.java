package p000;

import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class yd0 implements vk0 {

    /* renamed from: a0 */
    public final /* synthetic */ int f61293a0;

    /* renamed from: a1 */
    public final /* synthetic */ View f61294a1;

    /* renamed from: a2 */
    public final /* synthetic */ int f61295a2;

    public yd0(View view, int i, int i2) {
        this.f61293a0 = i;
        this.f61294a1 = view;
        this.f61295a2 = i2;
    }

    @Override // p000.vk0
    /* renamed from: a6 */
    public final xf1 mo213324a6(View view, xf1 xf1Var) {
        int i = xf1Var.f61102a0.mo214391a5(7).f56155a1;
        View view2 = this.f61294a1;
        int i2 = this.f61293a0;
        if (i2 >= 0) {
            view2.getLayoutParams().height = i2 + i;
            view2.setLayoutParams(view2.getLayoutParams());
        }
        view2.setPadding(view2.getPaddingLeft(), this.f61295a2 + i, view2.getPaddingRight(), view2.getPaddingBottom());
        return xf1Var;
    }
}
