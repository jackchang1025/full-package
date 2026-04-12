package p000;

import android.animation.Animator;
import com.google.android.material.R$animator;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: xz */
/* loaded from: classes2.dex */
public final class C1438xz extends AbstractC0408da {

    /* renamed from: a6 */
    public boolean f61204a6;

    /* renamed from: a7 */
    public final /* synthetic */ ExtendedFloatingActionButton f61205a7;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1438xz(ExtendedFloatingActionButton extendedFloatingActionButton, C1251t9 c1251t9) {
        super(extendedFloatingActionButton, c1251t9);
        this.f61205a7 = extendedFloatingActionButton;
    }

    @Override // p000.AbstractC0408da
    /* renamed from: a2 */
    public final int mo212567a2() {
        return R$animator.mtrl_extended_fab_hide_motion_spec;
    }

    @Override // p000.AbstractC0408da
    /* renamed from: a3 */
    public final void mo212568a3() {
        super.mo212568a3();
        this.f61204a6 = true;
    }

    @Override // p000.AbstractC0408da
    /* renamed from: a4 */
    public final void mo212569a4() {
        this.f55590a3.f60186a0 = null;
        ExtendedFloatingActionButton extendedFloatingActionButton = this.f61205a7;
        extendedFloatingActionButton.f49481c0 = 0;
        if (this.f61204a6) {
            return;
        }
        extendedFloatingActionButton.setVisibility(8);
    }

    @Override // p000.AbstractC0408da
    /* renamed from: a5 */
    public final void mo212570a5(Animator animator) {
        C1251t9 c1251t9 = this.f55590a3;
        Animator animator2 = c1251t9.f60186a0;
        if (animator2 != null) {
            animator2.cancel();
        }
        c1251t9.f60186a0 = animator;
        this.f61204a6 = false;
        ExtendedFloatingActionButton extendedFloatingActionButton = this.f61205a7;
        extendedFloatingActionButton.setVisibility(0);
        extendedFloatingActionButton.f49481c0 = 1;
    }

    @Override // p000.AbstractC0408da
    /* renamed from: a6 */
    public final void mo212571a6() {
        this.f61205a7.setVisibility(8);
    }

    @Override // p000.AbstractC0408da
    /* renamed from: a7 */
    public final boolean mo212572a7() {
        int i = ExtendedFloatingActionButton.f49476d5;
        ExtendedFloatingActionButton extendedFloatingActionButton = this.f61205a7;
        if (extendedFloatingActionButton.getVisibility() == 0) {
            if (extendedFloatingActionButton.f49481c0 != 1) {
                return false;
            }
        } else if (extendedFloatingActionButton.f49481c0 == 2) {
            return false;
        }
        return true;
    }
}
