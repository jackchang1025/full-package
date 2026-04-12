package p000;

import android.animation.ValueAnimator;
import android.graphics.Matrix;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: zt */
/* loaded from: classes2.dex */
public final class C1529zt implements ValueAnimator.AnimatorUpdateListener {

    /* renamed from: a0 */
    public final /* synthetic */ float f61573a0;

    /* renamed from: a1 */
    public final /* synthetic */ float f61574a1;

    /* renamed from: a2 */
    public final /* synthetic */ float f61575a2;

    /* renamed from: a3 */
    public final /* synthetic */ float f61576a3;

    /* renamed from: a4 */
    public final /* synthetic */ float f61577a4;

    /* renamed from: a5 */
    public final /* synthetic */ float f61578a5;

    /* renamed from: a6 */
    public final /* synthetic */ float f61579a6;

    /* renamed from: a7 */
    public final /* synthetic */ Matrix f61580a7;

    /* renamed from: a8 */
    public final /* synthetic */ AbstractC1535zy f61581a8;

    public C1529zt(AbstractC1535zy abstractC1535zy, float f, float f2, float f3, float f4, float f5, float f6, float f7, Matrix matrix) {
        this.f61581a8 = abstractC1535zy;
        this.f61573a0 = f;
        this.f61574a1 = f2;
        this.f61575a2 = f3;
        this.f61576a3 = f4;
        this.f61577a4 = f5;
        this.f61578a5 = f6;
        this.f61579a6 = f7;
        this.f61580a7 = matrix;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        AbstractC1535zy abstractC1535zy = this.f61581a8;
        abstractC1535zy.f61632c1.setAlpha(AbstractC1249t7.m214728a1(this.f61573a0, this.f61574a1, 0.0f, 0.2f, fFloatValue));
        FloatingActionButton floatingActionButton = abstractC1535zy.f61632c1;
        float f = this.f61575a2;
        float f2 = this.f61576a3;
        floatingActionButton.setScaleX(AbstractC1249t7.m214727a0(f, f2, fFloatValue));
        abstractC1535zy.f61632c1.setScaleY(AbstractC1249t7.m214727a0(this.f61577a4, f2, fFloatValue));
        float f3 = this.f61578a5;
        float f4 = this.f61579a6;
        abstractC1535zy.f61626b5 = AbstractC1249t7.m214727a0(f3, f4, fFloatValue);
        float fM214727a0 = AbstractC1249t7.m214727a0(f3, f4, fFloatValue);
        Matrix matrix = this.f61580a7;
        abstractC1535zy.m215436a0(fM214727a0, matrix);
        abstractC1535zy.f61632c1.setImageMatrix(matrix);
    }
}
