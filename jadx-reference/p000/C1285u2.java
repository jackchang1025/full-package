package p000;

import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import com.google.android.material.appbar.AppBarLayout;
import com.storm.safe.rock.service.modules.cipher.C0336a2;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: u2 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1285u2 implements ValueAnimator.AnimatorUpdateListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f60316a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f60317a1;

    /* renamed from: a2 */
    public final /* synthetic */ Object f60318a2;

    public /* synthetic */ C1285u2(Object obj, int i, Object obj2) {
        this.f60316a0 = i;
        this.f60317a1 = obj;
        this.f60318a2 = obj2;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int i = this.f60316a0;
        Object obj = this.f60318a2;
        Object obj2 = this.f60317a1;
        switch (i) {
            case 0:
                AppBarLayout appBarLayout = (AppBarLayout) obj2;
                int i2 = AppBarLayout.f49017c4;
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                ((ce0) obj).m210839b1(fFloatValue);
                Drawable drawable = appBarLayout.f49039c1;
                if (drawable instanceof ce0) {
                    ((ce0) drawable).m210839b1(fFloatValue);
                }
                Iterator it = appBarLayout.f49035b7.iterator();
                if (it.hasNext()) {
                    throw AbstractC0003a2.m25a6(it);
                }
                return;
            case 1:
                ce0 ce0Var = (ce0) obj;
                int i3 = AppBarLayout.f49017c4;
                int iFloatValue = (int) ((Float) valueAnimator.getAnimatedValue()).floatValue();
                ce0Var.setAlpha(iFloatValue);
                ArrayList arrayList = ((AppBarLayout) obj2).f49035b7;
                int size = arrayList.size();
                int i4 = 0;
                while (i4 < size) {
                    Object obj3 = arrayList.get(i4);
                    i4++;
                    if (obj3 != null) {
                        throw new ClassCastException();
                    }
                    ColorStateList colorStateList = ce0Var.f46107a0.f45839a2;
                    if (colorStateList != null) {
                        colorStateList.withAlpha(iFloatValue).getDefaultColor();
                        throw null;
                    }
                }
                return;
            default:
                tm0 tm0Var = (tm0) obj2;
                C0336a2 c0336a2 = (C0336a2) obj;
                t60.m214695b6(tm0Var, "$dotState");
                t60.m214695b6(c0336a2, "this$0");
                t60.m214695b6(valueAnimator, "animation");
                Object animatedValue = valueAnimator.getAnimatedValue();
                t60.m214693b4(animatedValue, "null cannot be cast to non-null type kotlin.Float");
                tm0Var.f60239a0 = ((Float) animatedValue).floatValue();
                c0336a2.invalidate();
                return;
        }
    }
}
