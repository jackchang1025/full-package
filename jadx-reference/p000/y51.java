package p000;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.HashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class y51 extends s71 {
    @Override // p000.s71
    /* renamed from: a2 */
    public final void mo210780a2(y71 y71Var) {
        View view = y71Var.f61263a1;
        if (view instanceof TextView) {
            y71Var.f61262a0.put("android:textscale:scale", Float.valueOf(((TextView) view).getScaleX()));
        }
    }

    @Override // p000.s71
    /* renamed from: a5 */
    public final void mo210782a5(y71 y71Var) {
        View view = y71Var.f61263a1;
        if (view instanceof TextView) {
            y71Var.f61262a0.put("android:textscale:scale", Float.valueOf(((TextView) view).getScaleX()));
        }
    }

    @Override // p000.s71
    /* renamed from: a9 */
    public final Animator mo212988a9(ViewGroup viewGroup, y71 y71Var, y71 y71Var2) {
        if (y71Var == null || y71Var2 == null || !(y71Var.f61263a1 instanceof TextView)) {
            return null;
        }
        View view = y71Var2.f61263a1;
        if (!(view instanceof TextView)) {
            return null;
        }
        TextView textView = (TextView) view;
        HashMap map = y71Var.f61262a0;
        HashMap map2 = y71Var2.f61262a0;
        float fFloatValue = map.get("android:textscale:scale") != null ? ((Float) map.get("android:textscale:scale")).floatValue() : 1.0f;
        float fFloatValue2 = map2.get("android:textscale:scale") != null ? ((Float) map2.get("android:textscale:scale")).floatValue() : 1.0f;
        if (fFloatValue == fFloatValue2) {
            return null;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(fFloatValue, fFloatValue2);
        valueAnimatorOfFloat.addUpdateListener(new C0470ev(5, textView));
        return valueAnimatorOfFloat;
    }
}
