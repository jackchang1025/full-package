package p000;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.google.android.material.R$attr;
import com.google.android.material.R$dimen;
import com.google.android.material.R$styleable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: dd */
/* loaded from: classes2.dex */
public abstract class AbstractC0411dd {

    /* renamed from: a0 */
    public int f55693a0;

    /* renamed from: a1 */
    public int f55694a1;

    /* renamed from: a2 */
    public int[] f55695a2;

    /* renamed from: a3 */
    public int f55696a3;

    /* renamed from: a4 */
    public int f55697a4;

    /* renamed from: a5 */
    public int f55698a5;

    public AbstractC0411dd(Context context, AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException {
        this.f55695a2 = new int[0];
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R$dimen.mtrl_progress_track_thickness);
        int[] iArr = R$styleable.BaseProgressIndicator;
        j61.m213206a0(context, attributeSet, i, i2);
        j61.m213207a1(context, attributeSet, iArr, i, i2, new int[0]);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, i2);
        this.f55693a0 = AbstractC1117qo.m214432c8(context, typedArrayObtainStyledAttributes, R$styleable.BaseProgressIndicator_trackThickness, dimensionPixelSize);
        this.f55694a1 = Math.min(AbstractC1117qo.m214432c8(context, typedArrayObtainStyledAttributes, R$styleable.BaseProgressIndicator_trackCornerRadius, 0), this.f55693a0 / 2);
        this.f55697a4 = typedArrayObtainStyledAttributes.getInt(R$styleable.BaseProgressIndicator_showAnimationBehavior, 0);
        this.f55698a5 = typedArrayObtainStyledAttributes.getInt(R$styleable.BaseProgressIndicator_hideAnimationBehavior, 0);
        if (!typedArrayObtainStyledAttributes.hasValue(R$styleable.BaseProgressIndicator_indicatorColor)) {
            this.f55695a2 = new int[]{kj1.m213567b4(context, R$attr.colorPrimary, -1)};
        } else if (typedArrayObtainStyledAttributes.peekValue(R$styleable.BaseProgressIndicator_indicatorColor).type != 1) {
            this.f55695a2 = new int[]{typedArrayObtainStyledAttributes.getColor(R$styleable.BaseProgressIndicator_indicatorColor, -1)};
        } else {
            int[] intArray = context.getResources().getIntArray(typedArrayObtainStyledAttributes.getResourceId(R$styleable.BaseProgressIndicator_indicatorColor, -1));
            this.f55695a2 = intArray;
            if (intArray.length == 0) {
                throw new IllegalArgumentException("indicatorColors cannot be empty when indicatorColor is not used.");
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(R$styleable.BaseProgressIndicator_trackColor)) {
            this.f55696a3 = typedArrayObtainStyledAttributes.getColor(R$styleable.BaseProgressIndicator_trackColor, -1);
        } else {
            this.f55696a3 = this.f55695a2[0];
            TypedArray typedArrayObtainStyledAttributes2 = context.getTheme().obtainStyledAttributes(new int[]{R.attr.disabledAlpha});
            float f = typedArrayObtainStyledAttributes2.getFloat(0, 0.2f);
            typedArrayObtainStyledAttributes2.recycle();
            this.f55696a3 = kj1.m213561a8(this.f55696a3, (int) (f * 255.0f));
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    /* renamed from: a0 */
    public abstract void mo211082a0();
}
