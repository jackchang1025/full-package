package p000;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.R$styleable;
import androidx.appcompat.widget.AppCompatSeekBar;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: x7 */
/* loaded from: classes.dex */
public final class C1404x7 extends C1217sc {

    /* renamed from: a4 */
    public final AppCompatSeekBar f61022a4;

    /* renamed from: a5 */
    public Drawable f61023a5;

    /* renamed from: a6 */
    public ColorStateList f61024a6;

    /* renamed from: a7 */
    public PorterDuff.Mode f61025a7;

    /* renamed from: a8 */
    public boolean f61026a8;

    /* renamed from: a9 */
    public boolean f61027a9;

    public C1404x7(AppCompatSeekBar appCompatSeekBar) {
        super(appCompatSeekBar);
        this.f61024a6 = null;
        this.f61025a7 = null;
        this.f61026a8 = false;
        this.f61027a9 = false;
        this.f61022a4 = appCompatSeekBar;
    }

    @Override // p000.C1217sc
    /* renamed from: b0 */
    public final void mo214597b0(AttributeSet attributeSet, int i) {
        super.mo214597b0(attributeSet, i);
        AppCompatSeekBar appCompatSeekBar = this.f61022a4;
        pg1 pg1VarM214255d2 = pg1.m214255d2(appCompatSeekBar.getContext(), attributeSet, R$styleable.AppCompatSeekBar, i);
        TypedArray typedArray = (TypedArray) pg1VarM214255d2.f59230a2;
        xa1.m215151b3(appCompatSeekBar, appCompatSeekBar.getContext(), R$styleable.AppCompatSeekBar, attributeSet, (TypedArray) pg1VarM214255d2.f59230a2, i);
        Drawable drawableM214278c2 = pg1VarM214255d2.m214278c2(R$styleable.AppCompatSeekBar_android_thumb);
        if (drawableM214278c2 != null) {
            appCompatSeekBar.setThumb(drawableM214278c2);
        }
        Drawable drawableM214277c1 = pg1VarM214255d2.m214277c1(R$styleable.AppCompatSeekBar_tickMark);
        Drawable drawable = this.f61023a5;
        if (drawable != null) {
            drawable.setCallback(null);
        }
        this.f61023a5 = drawableM214277c1;
        if (drawableM214277c1 != null) {
            drawableM214277c1.setCallback(appCompatSeekBar);
            AbstractC1271ts.m214779a1(drawableM214277c1, ga1.m212904a3(appCompatSeekBar));
            if (drawableM214277c1.isStateful()) {
                drawableM214277c1.setState(appCompatSeekBar.getDrawableState());
            }
            m215126b3();
        }
        appCompatSeekBar.invalidate();
        if (typedArray.hasValue(R$styleable.AppCompatSeekBar_tickMarkTintMode)) {
            this.f61025a7 = AbstractC1274tv.m214792a2(typedArray.getInt(R$styleable.AppCompatSeekBar_tickMarkTintMode, -1), this.f61025a7);
            this.f61027a9 = true;
        }
        if (typedArray.hasValue(R$styleable.AppCompatSeekBar_tickMarkTint)) {
            this.f61024a6 = pg1VarM214255d2.m214276c0(R$styleable.AppCompatSeekBar_tickMarkTint);
            this.f61026a8 = true;
        }
        pg1VarM214255d2.m214288d4();
        m215126b3();
    }

    /* renamed from: b3 */
    public final void m215126b3() {
        Drawable drawable = this.f61023a5;
        if (drawable != null) {
            if (this.f61026a8 || this.f61027a9) {
                Drawable drawableMutate = drawable.mutate();
                this.f61023a5 = drawableMutate;
                if (this.f61026a8) {
                    AbstractC1270tr.m214774a7(drawableMutate, this.f61024a6);
                }
                if (this.f61027a9) {
                    AbstractC1270tr.m214775a8(this.f61023a5, this.f61025a7);
                }
                if (this.f61023a5.isStateful()) {
                    this.f61023a5.setState(this.f61022a4.getDrawableState());
                }
            }
        }
    }

    /* renamed from: b4 */
    public final void m215127b4(Canvas canvas) {
        if (this.f61023a5 != null) {
            int max = this.f61022a4.getMax();
            if (max > 1) {
                int intrinsicWidth = this.f61023a5.getIntrinsicWidth();
                int intrinsicHeight = this.f61023a5.getIntrinsicHeight();
                int i = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1;
                int i2 = intrinsicHeight >= 0 ? intrinsicHeight / 2 : 1;
                this.f61023a5.setBounds(-i, -i2, i, i2);
                float width = ((r0.getWidth() - r0.getPaddingLeft()) - r0.getPaddingRight()) / max;
                int iSave = canvas.save();
                canvas.translate(r0.getPaddingLeft(), r0.getHeight() / 2);
                for (int i3 = 0; i3 <= max; i3++) {
                    this.f61023a5.draw(canvas);
                    canvas.translate(width, 0.0f);
                }
                canvas.restoreToCount(iSave);
            }
        }
    }
}
