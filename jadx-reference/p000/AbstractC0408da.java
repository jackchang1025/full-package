package p000;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: da */
/* loaded from: classes2.dex */
public abstract class AbstractC0408da {

    /* renamed from: a0 */
    public final Context f55587a0;

    /* renamed from: a1 */
    public final ExtendedFloatingActionButton f55588a1;

    /* renamed from: a2 */
    public final ArrayList f55589a2 = new ArrayList();

    /* renamed from: a3 */
    public final C1251t9 f55590a3;

    /* renamed from: a4 */
    public yg0 f55591a4;

    /* renamed from: a5 */
    public yg0 f55592a5;

    public AbstractC0408da(ExtendedFloatingActionButton extendedFloatingActionButton, C1251t9 c1251t9) {
        this.f55588a1 = extendedFloatingActionButton;
        this.f55587a0 = extendedFloatingActionButton.getContext();
        this.f55590a3 = c1251t9;
    }

    /* renamed from: a0 */
    public AnimatorSet mo212565a0() {
        yg0 yg0Var = this.f55592a5;
        if (yg0Var == null) {
            if (this.f55591a4 == null) {
                this.f55591a4 = yg0.m215281a1(this.f55587a0, mo212567a2());
            }
            yg0Var = this.f55591a4;
            yg0Var.getClass();
        }
        return m212566a1(yg0Var);
    }

    /* renamed from: a1 */
    public final AnimatorSet m212566a1(yg0 yg0Var) {
        ArrayList arrayList = new ArrayList();
        boolean zM215286a6 = yg0Var.m215286a6("opacity");
        ExtendedFloatingActionButton extendedFloatingActionButton = this.f55588a1;
        if (zM215286a6) {
            arrayList.add(yg0Var.m215283a3("opacity", extendedFloatingActionButton, View.ALPHA));
        }
        if (yg0Var.m215286a6("scale")) {
            arrayList.add(yg0Var.m215283a3("scale", extendedFloatingActionButton, View.SCALE_Y));
            arrayList.add(yg0Var.m215283a3("scale", extendedFloatingActionButton, View.SCALE_X));
        }
        if (yg0Var.m215286a6("width")) {
            arrayList.add(yg0Var.m215283a3("width", extendedFloatingActionButton, ExtendedFloatingActionButton.f49477d6));
        }
        if (yg0Var.m215286a6("height")) {
            arrayList.add(yg0Var.m215283a3("height", extendedFloatingActionButton, ExtendedFloatingActionButton.f49478d7));
        }
        if (yg0Var.m215286a6("paddingStart")) {
            arrayList.add(yg0Var.m215283a3("paddingStart", extendedFloatingActionButton, ExtendedFloatingActionButton.f49479d8));
        }
        if (yg0Var.m215286a6("paddingEnd")) {
            arrayList.add(yg0Var.m215283a3("paddingEnd", extendedFloatingActionButton, ExtendedFloatingActionButton.f49480d9));
        }
        if (yg0Var.m215286a6("labelOpacity")) {
            arrayList.add(yg0Var.m215283a3("labelOpacity", extendedFloatingActionButton, new C0396cz(this)));
        }
        AnimatorSet animatorSet = new AnimatorSet();
        t60.m214718e2(animatorSet, arrayList);
        return animatorSet;
    }

    /* renamed from: a2 */
    public abstract int mo212567a2();

    /* renamed from: a3 */
    public void mo212568a3() {
        this.f55590a3.f60186a0 = null;
    }

    /* renamed from: a4 */
    public abstract void mo212569a4();

    /* renamed from: a5 */
    public abstract void mo212570a5(Animator animator);

    /* renamed from: a6 */
    public abstract void mo212571a6();

    /* renamed from: a7 */
    public abstract boolean mo212572a7();
}
