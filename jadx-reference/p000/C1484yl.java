package p000;

import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.appcompat.widget.ActionMenuView;
import com.google.android.material.tabs.TabLayout;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: yl */
/* loaded from: classes2.dex */
public final class C1484yl implements ValueAnimator.AnimatorUpdateListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f61340a0;

    /* renamed from: a1 */
    public final View f61341a1;

    /* renamed from: a2 */
    public final View f61342a2;

    /* renamed from: a3 */
    public final Object f61343a3;

    public C1484yl(ActionMenuView actionMenuView, ActionMenuView actionMenuView2) {
        this.f61340a0 = 0;
        this.f61341a1 = actionMenuView;
        this.f61342a2 = actionMenuView2;
        this.f61343a3 = new float[2];
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int i = this.f61340a0;
        Object obj = this.f61343a3;
        switch (i) {
            case 0:
                float[] fArr = (float[]) obj;
                AbstractC1117qo.m214409a2(((Float) valueAnimator.getAnimatedValue()).floatValue(), fArr);
                View view = this.f61341a1;
                if (view != null) {
                    view.setAlpha(fArr[0]);
                }
                View view2 = this.f61342a2;
                if (view2 != null) {
                    view2.setAlpha(fArr[1]);
                    break;
                }
                break;
            default:
                w41 w41Var = (w41) obj;
                float animatedFraction = valueAnimator.getAnimatedFraction();
                TabLayout tabLayout = w41Var.f60772a1;
                View view3 = this.f61341a1;
                if (view3 == null || view3.getWidth() <= 0) {
                    Drawable drawable = tabLayout.f49901a8;
                    drawable.setBounds(-1, drawable.getBounds().top, -1, tabLayout.f49901a8.getBounds().bottom);
                } else {
                    tabLayout.f49917c4.mo212812a6(tabLayout, view3, this.f61342a2, animatedFraction, tabLayout.f49901a8);
                }
                WeakHashMap weakHashMap = xa1.f61054a0;
                fa1.m212773b0(w41Var);
                break;
        }
    }

    public C1484yl(w41 w41Var, View view, View view2) {
        this.f61340a0 = 1;
        this.f61343a3 = w41Var;
        this.f61341a1 = view;
        this.f61342a2 = view2;
    }
}
