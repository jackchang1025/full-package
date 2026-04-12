package p000;

import android.animation.ValueAnimator;
import android.widget.TextView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ev */
/* loaded from: classes2.dex */
public final class C0470ev implements ValueAnimator.AnimatorUpdateListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f56110a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f56111a1;

    public /* synthetic */ C0470ev(int i, Object obj) {
        this.f56110a0 = i;
        this.f56111a1 = obj;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.f56110a0) {
            case 0:
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                ce0 ce0Var = ((BottomSheetBehavior) this.f56111a1).f49187a8;
                if (ce0Var != null) {
                    ce0Var.m210841b3(fFloatValue);
                    break;
                }
                break;
            case 1:
                ((CollapsingToolbarLayout) this.f56111a1).setScrimAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
                break;
            case 2:
                int iFloatValue = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 255.0f);
                C1491ys c1491ys = (C1491ys) this.f56111a1;
                c1491ys.f61371a2.setAlpha(iFloatValue);
                c1491ys.f61372a3.setAlpha(iFloatValue);
                c1491ys.f61387b8.invalidate();
                break;
            case 3:
                ((TabLayout) this.f56111a1).scrollTo(((Integer) valueAnimator.getAnimatedValue()).intValue(), 0);
                break;
            case 4:
                ((TextInputLayout) this.f56111a1).f50000g7.m211073b5(((Float) valueAnimator.getAnimatedValue()).floatValue());
                break;
            default:
                float fFloatValue2 = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                TextView textView = (TextView) this.f56111a1;
                textView.setScaleX(fFloatValue2);
                textView.setScaleY(fFloatValue2);
                break;
        }
    }
}
