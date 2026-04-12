package p000;

import android.view.View;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class xe1 extends kj1 {

    /* renamed from: a6 */
    public final /* synthetic */ int f61077a6;

    /* renamed from: a7 */
    public final /* synthetic */ ze1 f61078a7;

    public /* synthetic */ xe1(ze1 ze1Var, int i) {
        this.f61077a6 = i;
        this.f61078a7 = ze1Var;
    }

    @Override // p000.oc1
    /* renamed from: a0 */
    public final void mo212658a0() {
        View view;
        int i = this.f61077a6;
        ze1 ze1Var = this.f61078a7;
        switch (i) {
            case 0:
                if (ze1Var.f61530c0 && (view = ze1Var.f61522b2) != null) {
                    view.setTranslationY(0.0f);
                    ze1Var.f61519a9.setTranslationY(0.0f);
                }
                ze1Var.f61519a9.setVisibility(8);
                ze1Var.f61519a9.setTransitioning(false);
                ze1Var.f61535c5 = null;
                eo0 eo0Var = ze1Var.f61526b6;
                if (eo0Var != null) {
                    eo0Var.m212714b8(ze1Var.f61525b5);
                    ze1Var.f61525b5 = null;
                    ze1Var.f61526b6 = null;
                }
                ActionBarOverlayLayout actionBarOverlayLayout = ze1Var.f61518a8;
                if (actionBarOverlayLayout != null) {
                    WeakHashMap weakHashMap = xa1.f61054a0;
                    ja1.m213282a2(actionBarOverlayLayout);
                    break;
                }
                break;
            default:
                ze1Var.f61535c5 = null;
                ze1Var.f61519a9.requestLayout();
                break;
        }
    }
}
