package p000;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import androidx.cardview.widget.CardView;
import com.google.android.material.R$attr;
import com.google.android.material.R$id;
import com.google.android.material.R$style;
import com.google.android.material.R$styleable;
import com.google.android.material.card.MaterialCardView;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ud0 {

    /* renamed from: c4 */
    public static final double f60382c4 = Math.cos(Math.toRadians(45.0d));

    /* renamed from: c5 */
    public static final ColorDrawable f60383c5;

    /* renamed from: a0 */
    public final MaterialCardView f60384a0;

    /* renamed from: a1 */
    public final Rect f60385a1;

    /* renamed from: a2 */
    public final ce0 f60386a2;

    /* renamed from: a3 */
    public final ce0 f60387a3;

    /* renamed from: a4 */
    public int f60388a4;

    /* renamed from: a5 */
    public int f60389a5;

    /* renamed from: a6 */
    public int f60390a6;

    /* renamed from: a7 */
    public int f60391a7;

    /* renamed from: a8 */
    public Drawable f60392a8;

    /* renamed from: a9 */
    public Drawable f60393a9;

    /* renamed from: b0 */
    public ColorStateList f60394b0;

    /* renamed from: b1 */
    public ColorStateList f60395b1;

    /* renamed from: b2 */
    public a01 f60396b2;

    /* renamed from: b3 */
    public ColorStateList f60397b3;

    /* renamed from: b4 */
    public RippleDrawable f60398b4;

    /* renamed from: b5 */
    public LayerDrawable f60399b5;

    /* renamed from: b6 */
    public ce0 f60400b6;

    /* renamed from: b7 */
    public boolean f60401b7;

    /* renamed from: b8 */
    public boolean f60402b8;

    /* renamed from: b9 */
    public ValueAnimator f60403b9;

    /* renamed from: c0 */
    public final TimeInterpolator f60404c0;

    /* renamed from: c1 */
    public final int f60405c1;

    /* renamed from: c2 */
    public final int f60406c2;

    /* renamed from: c3 */
    public float f60407c3;

    static {
        f60383c5 = Build.VERSION.SDK_INT <= 28 ? new ColorDrawable() : null;
    }

    public ud0(MaterialCardView materialCardView, AttributeSet attributeSet, int i) {
        int i2 = MaterialCardView.f49283b4;
        this.f60385a1 = new Rect();
        this.f60401b7 = false;
        this.f60407c3 = 0.0f;
        this.f60384a0 = materialCardView;
        ce0 ce0Var = new ce0(materialCardView.getContext(), attributeSet, i, i2);
        this.f60386a2 = ce0Var;
        ce0Var.m210838b0(materialCardView.getContext());
        ce0Var.m210843b5();
        xg1 xg1VarM17a6 = ce0Var.f46107a0.f45837a0.m17a6();
        TypedArray typedArrayObtainStyledAttributes = materialCardView.getContext().obtainStyledAttributes(attributeSet, R$styleable.CardView, i, R$style.CardView);
        if (typedArrayObtainStyledAttributes.hasValue(R$styleable.CardView_cardCornerRadius)) {
            xg1VarM17a6.m215188b1(typedArrayObtainStyledAttributes.getDimension(R$styleable.CardView_cardCornerRadius, 0.0f));
        }
        this.f60387a3 = new ce0();
        m214839a7(xg1VarM17a6.m215177a0());
        this.f60404c0 = kg1.m213537e4(materialCardView.getContext(), R$attr.motionEasingLinearInterpolator, AbstractC1249t7.f60178a0);
        this.f60405c1 = kg1.m213536e3(materialCardView.getContext(), R$attr.motionDurationShort2, 300);
        this.f60406c2 = kg1.m213536e3(materialCardView.getContext(), R$attr.motionDurationShort1, 300);
        typedArrayObtainStyledAttributes.recycle();
    }

    /* renamed from: a1 */
    public static float m214832a1(b81 b81Var, float f) {
        if (b81Var instanceof ns0) {
            return (float) ((1.0d - f60382c4) * f);
        }
        if (b81Var instanceof C0954oj) {
            return f / 2.0f;
        }
        return 0.0f;
    }

    /* renamed from: a0 */
    public final float m214833a0() {
        b81 b81Var = this.f60396b2.f7a0;
        ce0 ce0Var = this.f60386a2;
        return Math.max(Math.max(m214832a1(b81Var, ce0Var.m210836a8()), m214832a1(this.f60396b2.f8a1, ce0Var.f46107a0.f45837a0.f12a5.mo212732a0(ce0Var.m210834a6()))), Math.max(m214832a1(this.f60396b2.f9a2, ce0Var.f46107a0.f45837a0.f13a6.mo212732a0(ce0Var.m210834a6())), m214832a1(this.f60396b2.f10a3, ce0Var.f46107a0.f45837a0.f14a7.mo212732a0(ce0Var.m210834a6()))));
    }

    /* renamed from: a2 */
    public final LayerDrawable m214834a2() {
        if (this.f60398b4 == null) {
            this.f60400b6 = new ce0(this.f60396b2);
            this.f60398b4 = new RippleDrawable(this.f60394b0, null, this.f60400b6);
        }
        if (this.f60399b5 == null) {
            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{this.f60398b4, this.f60387a3, this.f60393a9});
            this.f60399b5 = layerDrawable;
            layerDrawable.setId(2, R$id.mtrl_card_checked_layer_id);
        }
        return this.f60399b5;
    }

    /* renamed from: a3 */
    public final td0 m214835a3(Drawable drawable) {
        int iCeil;
        int i;
        if (this.f60384a0.getUseCompatPadding()) {
            int iCeil2 = (int) Math.ceil((r0.getMaxCardElevation() * 1.5f) + (m214840a8() ? m214833a0() : 0.0f));
            iCeil = (int) Math.ceil(r0.getMaxCardElevation() + (m214840a8() ? m214833a0() : 0.0f));
            i = iCeil2;
        } else {
            iCeil = 0;
            i = 0;
        }
        return new td0(drawable, iCeil, i, iCeil, i);
    }

    /* renamed from: a4 */
    public final void m214836a4(int i, int i2) {
        int iCeil;
        int iCeil2;
        int i3;
        int i4;
        if (this.f60399b5 != null) {
            MaterialCardView materialCardView = this.f60384a0;
            if (materialCardView.getUseCompatPadding()) {
                iCeil = (int) Math.ceil(((materialCardView.getMaxCardElevation() * 1.5f) + (m214840a8() ? m214833a0() : 0.0f)) * 2.0f);
                iCeil2 = (int) Math.ceil((materialCardView.getMaxCardElevation() + (m214840a8() ? m214833a0() : 0.0f)) * 2.0f);
            } else {
                iCeil = 0;
                iCeil2 = 0;
            }
            int i5 = this.f60390a6;
            int i6 = (i5 & 8388613) == 8388613 ? ((i - this.f60388a4) - this.f60389a5) - iCeil2 : this.f60388a4;
            int i7 = (i5 & 80) == 80 ? this.f60388a4 : ((i2 - this.f60388a4) - this.f60389a5) - iCeil;
            int i8 = (i5 & 8388613) == 8388613 ? this.f60388a4 : ((i - this.f60388a4) - this.f60389a5) - iCeil2;
            int i9 = (i5 & 80) == 80 ? ((i2 - this.f60388a4) - this.f60389a5) - iCeil : this.f60388a4;
            WeakHashMap weakHashMap = xa1.f61054a0;
            if (ga1.m212904a3(materialCardView) == 1) {
                i4 = i8;
                i3 = i6;
            } else {
                i3 = i8;
                i4 = i6;
            }
            this.f60399b5.setLayerInset(2, i4, i9, i3, i7);
        }
    }

    /* renamed from: a5 */
    public final void m214837a5(boolean z, boolean z2) {
        Drawable drawable = this.f60393a9;
        if (drawable != null) {
            if (!z2) {
                drawable.setAlpha(z ? v10.MASK : 0);
                this.f60407c3 = z ? 1.0f : 0.0f;
                return;
            }
            float f = z ? 1.0f : 0.0f;
            float f2 = z ? 1.0f - this.f60407c3 : this.f60407c3;
            ValueAnimator valueAnimator = this.f60403b9;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.f60403b9 = null;
            }
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.f60407c3, f);
            this.f60403b9 = valueAnimatorOfFloat;
            valueAnimatorOfFloat.addUpdateListener(new C1306un(1, this));
            this.f60403b9.setInterpolator(this.f60404c0);
            this.f60403b9.setDuration((long) ((z ? this.f60405c1 : this.f60406c2) * f2));
            this.f60403b9.start();
        }
    }

    /* renamed from: a6 */
    public final void m214838a6(Drawable drawable) {
        if (drawable != null) {
            Drawable drawableMutate = drawable.mutate();
            this.f60393a9 = drawableMutate;
            AbstractC1270tr.m214774a7(drawableMutate, this.f60395b1);
            m214837a5(this.f60384a0.f49286a9, false);
        } else {
            this.f60393a9 = f60383c5;
        }
        LayerDrawable layerDrawable = this.f60399b5;
        if (layerDrawable != null) {
            layerDrawable.setDrawableByLayerId(R$id.mtrl_card_checked_layer_id, this.f60393a9);
        }
    }

    /* renamed from: a7 */
    public final void m214839a7(a01 a01Var) {
        this.f60396b2 = a01Var;
        ce0 ce0Var = this.f60386a2;
        ce0Var.setShapeAppearanceModel(a01Var);
        ce0Var.f46128c1 = !ce0Var.f46107a0.f45837a0.m16a5(ce0Var.m210834a6());
        ce0 ce0Var2 = this.f60387a3;
        if (ce0Var2 != null) {
            ce0Var2.setShapeAppearanceModel(a01Var);
        }
        ce0 ce0Var3 = this.f60400b6;
        if (ce0Var3 != null) {
            ce0Var3.setShapeAppearanceModel(a01Var);
        }
    }

    /* renamed from: a8 */
    public final boolean m214840a8() {
        MaterialCardView materialCardView = this.f60384a0;
        if (!materialCardView.getPreventCornerOverlap()) {
            return false;
        }
        ce0 ce0Var = this.f60386a2;
        return ce0Var.f46107a0.f45837a0.m16a5(ce0Var.m210834a6()) && materialCardView.getUseCompatPadding();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x001a  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0020  */
    /* renamed from: a9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m214841a9() {
        float fM214833a0;
        MaterialCardView materialCardView = this.f60384a0;
        float cardViewRadius = 0.0f;
        if (materialCardView.getPreventCornerOverlap()) {
            ce0 ce0Var = this.f60386a2;
            if (!ce0Var.f46107a0.f45837a0.m16a5(ce0Var.m210834a6())) {
                fM214833a0 = m214833a0();
            } else if (!m214840a8()) {
                fM214833a0 = 0.0f;
            }
        }
        if (materialCardView.getPreventCornerOverlap() && materialCardView.getUseCompatPadding()) {
            cardViewRadius = (float) ((1.0d - f60382c4) * materialCardView.getCardViewRadius());
        }
        int i = (int) (fM214833a0 - cardViewRadius);
        Rect rect = this.f60385a1;
        materialCardView.f44172a2.set(rect.left + i, rect.top + i, rect.right + i, rect.bottom + i);
        og1 og1Var = materialCardView.f44174a4;
        if (!((CardView) og1Var.f58833a1).getUseCompatPadding()) {
            og1Var.m214213b5(0, 0, 0, 0);
            return;
        }
        ls0 ls0Var = (ls0) ((Drawable) og1Var.f58832a0);
        float f = ls0Var.f58167a4;
        float f2 = ls0Var.f58163a0;
        int iCeil = (int) Math.ceil(ms0.m214020a0(f, f2, r1.getPreventCornerOverlap()));
        int iCeil2 = (int) Math.ceil(ms0.m214021a1(f, f2, r1.getPreventCornerOverlap()));
        og1Var.m214213b5(iCeil, iCeil2, iCeil, iCeil2);
    }

    /* renamed from: b0 */
    public final void m214842b0() {
        boolean z = this.f60401b7;
        MaterialCardView materialCardView = this.f60384a0;
        if (!z) {
            materialCardView.setBackgroundInternal(m214835a3(this.f60386a2));
        }
        materialCardView.setForeground(m214835a3(this.f60392a8));
    }
}
