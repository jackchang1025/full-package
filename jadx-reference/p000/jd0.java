package p000;

import android.graphics.RectF;
import android.view.View;
import com.google.android.material.carousel.MaskableFrameLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class jd0 extends id0 {

    /* renamed from: a4 */
    public boolean f57319a4 = false;

    public jd0(MaskableFrameLayout maskableFrameLayout) {
        m213283a4(maskableFrameLayout);
    }

    /* renamed from: a4 */
    private void m213283a4(View view) {
        view.setOutlineProvider(new C0587hn(1, this));
    }

    @Override // p000.id0
    /* renamed from: a2 */
    public final void mo213154a2(MaskableFrameLayout maskableFrameLayout) {
        a01 a01Var;
        if (!((RectF) this.f56867a2).isEmpty() && (a01Var = (a01) this.f56866a1) != null) {
            this.f57319a4 = a01Var.m16a5((RectF) this.f56867a2);
        }
        maskableFrameLayout.setClipToOutline(!mo213155a3());
        if (mo213155a3()) {
            maskableFrameLayout.invalidate();
        } else {
            maskableFrameLayout.invalidateOutline();
        }
    }

    @Override // p000.id0
    /* renamed from: a3 */
    public final boolean mo213155a3() {
        return !this.f57319a4 || this.f56865a0;
    }
}
