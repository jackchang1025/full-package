package p000;

import android.view.View;
import com.google.android.material.carousel.MaskableFrameLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class kd0 extends id0 {
    public kd0(MaskableFrameLayout maskableFrameLayout) {
        m213493a4(maskableFrameLayout);
    }

    /* renamed from: a4 */
    private void m213493a4(View view) {
        view.setOutlineProvider(new C0587hn(2, this));
    }

    @Override // p000.id0
    /* renamed from: a2 */
    public final void mo213154a2(MaskableFrameLayout maskableFrameLayout) {
        maskableFrameLayout.setClipToOutline(!this.f56865a0);
        if (this.f56865a0) {
            maskableFrameLayout.invalidate();
        } else {
            maskableFrameLayout.invalidateOutline();
        }
    }

    @Override // p000.id0
    /* renamed from: a3 */
    public final boolean mo213155a3() {
        return this.f56865a0;
    }
}
