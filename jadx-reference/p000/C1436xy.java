package p000;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.text.TextUtils;
import android.view.ViewGroup;
import com.google.android.material.R$animator;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: xy */
/* loaded from: classes2.dex */
public final class C1436xy extends AbstractC0408da {

    /* renamed from: a6 */
    public final InterfaceC1451yb f61201a6;

    /* renamed from: a7 */
    public final boolean f61202a7;

    /* renamed from: a8 */
    public final /* synthetic */ ExtendedFloatingActionButton f61203a8;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1436xy(ExtendedFloatingActionButton extendedFloatingActionButton, C1251t9 c1251t9, InterfaceC1451yb interfaceC1451yb, boolean z) {
        super(extendedFloatingActionButton, c1251t9);
        this.f61203a8 = extendedFloatingActionButton;
        this.f61201a6 = interfaceC1451yb;
        this.f61202a7 = z;
    }

    @Override // p000.AbstractC0408da
    /* renamed from: a0 */
    public final AnimatorSet mo212565a0() {
        yg0 yg0Var = this.f55592a5;
        if (yg0Var == null) {
            if (this.f55591a4 == null) {
                this.f55591a4 = yg0.m215281a1(this.f55587a0, mo212567a2());
            }
            yg0Var = this.f55591a4;
            yg0Var.getClass();
        }
        boolean zM215286a6 = yg0Var.m215286a6("width");
        InterfaceC1451yb interfaceC1451yb = this.f61201a6;
        ExtendedFloatingActionButton extendedFloatingActionButton = this.f61203a8;
        if (zM215286a6) {
            PropertyValuesHolder[] propertyValuesHolderArrM215284a4 = yg0Var.m215284a4("width");
            propertyValuesHolderArrM215284a4[0].setFloatValues(extendedFloatingActionButton.getWidth(), interfaceC1451yb.mo214263a7());
            yg0Var.m215287a7("width", propertyValuesHolderArrM215284a4);
        }
        if (yg0Var.m215286a6("height")) {
            PropertyValuesHolder[] propertyValuesHolderArrM215284a42 = yg0Var.m215284a4("height");
            propertyValuesHolderArrM215284a42[0].setFloatValues(extendedFloatingActionButton.getHeight(), interfaceC1451yb.getHeight());
            yg0Var.m215287a7("height", propertyValuesHolderArrM215284a42);
        }
        if (yg0Var.m215286a6("paddingStart")) {
            PropertyValuesHolder[] propertyValuesHolderArrM215284a43 = yg0Var.m215284a4("paddingStart");
            PropertyValuesHolder propertyValuesHolder = propertyValuesHolderArrM215284a43[0];
            WeakHashMap weakHashMap = xa1.f61054a0;
            propertyValuesHolder.setFloatValues(ga1.m212906a5(extendedFloatingActionButton), interfaceC1451yb.mo214258a2());
            yg0Var.m215287a7("paddingStart", propertyValuesHolderArrM215284a43);
        }
        if (yg0Var.m215286a6("paddingEnd")) {
            PropertyValuesHolder[] propertyValuesHolderArrM215284a44 = yg0Var.m215284a4("paddingEnd");
            PropertyValuesHolder propertyValuesHolder2 = propertyValuesHolderArrM215284a44[0];
            WeakHashMap weakHashMap2 = xa1.f61054a0;
            propertyValuesHolder2.setFloatValues(ga1.m212905a4(extendedFloatingActionButton), interfaceC1451yb.mo214256a0());
            yg0Var.m215287a7("paddingEnd", propertyValuesHolderArrM215284a44);
        }
        if (yg0Var.m215286a6("labelOpacity")) {
            PropertyValuesHolder[] propertyValuesHolderArrM215284a45 = yg0Var.m215284a4("labelOpacity");
            boolean z = this.f61202a7;
            propertyValuesHolderArrM215284a45[0].setFloatValues(z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
            yg0Var.m215287a7("labelOpacity", propertyValuesHolderArrM215284a45);
        }
        return m212566a1(yg0Var);
    }

    @Override // p000.AbstractC0408da
    /* renamed from: a2 */
    public final int mo212567a2() {
        return this.f61202a7 ? R$animator.mtrl_extended_fab_change_size_expand_motion_spec : R$animator.mtrl_extended_fab_change_size_collapse_motion_spec;
    }

    @Override // p000.AbstractC0408da
    /* renamed from: a4 */
    public final void mo212569a4() {
        this.f55590a3.f60186a0 = null;
        ExtendedFloatingActionButton extendedFloatingActionButton = this.f61203a8;
        extendedFloatingActionButton.f49491d0 = false;
        extendedFloatingActionButton.setHorizontallyScrolling(false);
        ViewGroup.LayoutParams layoutParams = extendedFloatingActionButton.getLayoutParams();
        if (layoutParams == null) {
            return;
        }
        InterfaceC1451yb interfaceC1451yb = this.f61201a6;
        layoutParams.width = interfaceC1451yb.mo214264a8().width;
        layoutParams.height = interfaceC1451yb.mo214264a8().height;
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
        boolean z = this.f61202a7;
        ExtendedFloatingActionButton extendedFloatingActionButton = this.f61203a8;
        extendedFloatingActionButton.f49490c9 = z;
        extendedFloatingActionButton.f49491d0 = true;
        extendedFloatingActionButton.setHorizontallyScrolling(true);
    }

    @Override // p000.AbstractC0408da
    /* renamed from: a6 */
    public final void mo212571a6() {
        ExtendedFloatingActionButton extendedFloatingActionButton = this.f61203a8;
        boolean z = this.f61202a7;
        extendedFloatingActionButton.f49490c9 = z;
        ViewGroup.LayoutParams layoutParams = extendedFloatingActionButton.getLayoutParams();
        if (layoutParams == null) {
            return;
        }
        if (!z) {
            extendedFloatingActionButton.f49494d3 = layoutParams.width;
            extendedFloatingActionButton.f49495d4 = layoutParams.height;
        }
        InterfaceC1451yb interfaceC1451yb = this.f61201a6;
        layoutParams.width = interfaceC1451yb.mo214264a8().width;
        layoutParams.height = interfaceC1451yb.mo214264a8().height;
        int iMo214258a2 = interfaceC1451yb.mo214258a2();
        int paddingTop = extendedFloatingActionButton.getPaddingTop();
        int iMo214256a0 = interfaceC1451yb.mo214256a0();
        int paddingBottom = extendedFloatingActionButton.getPaddingBottom();
        WeakHashMap weakHashMap = xa1.f61054a0;
        ga1.m212911b0(extendedFloatingActionButton, iMo214258a2, paddingTop, iMo214256a0, paddingBottom);
        extendedFloatingActionButton.requestLayout();
    }

    @Override // p000.AbstractC0408da
    /* renamed from: a7 */
    public final boolean mo212572a7() {
        ExtendedFloatingActionButton extendedFloatingActionButton = this.f61203a8;
        return this.f61202a7 == extendedFloatingActionButton.f49490c9 || extendedFloatingActionButton.getIcon() == null || TextUtils.isEmpty(extendedFloatingActionButton.getText());
    }
}
