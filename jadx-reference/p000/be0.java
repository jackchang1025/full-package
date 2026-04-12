package p000;

import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public class be0 extends Drawable.ConstantState {

    /* renamed from: a0 */
    public a01 f45837a0;

    /* renamed from: a1 */
    public C1357vz f45838a1;

    /* renamed from: a2 */
    public ColorStateList f45839a2;

    /* renamed from: a3 */
    public ColorStateList f45840a3;

    /* renamed from: a4 */
    public ColorStateList f45841a4;

    /* renamed from: a5 */
    public PorterDuff.Mode f45842a5;

    /* renamed from: a6 */
    public Rect f45843a6;

    /* renamed from: a7 */
    public final float f45844a7;

    /* renamed from: a8 */
    public float f45845a8;

    /* renamed from: a9 */
    public float f45846a9;

    /* renamed from: b0 */
    public int f45847b0;

    /* renamed from: b1 */
    public float f45848b1;

    /* renamed from: b2 */
    public float f45849b2;

    /* renamed from: b3 */
    public int f45850b3;

    /* renamed from: b4 */
    public int f45851b4;

    /* renamed from: b5 */
    public int f45852b5;

    /* renamed from: b6 */
    public final int f45853b6;

    /* renamed from: b7 */
    public Paint.Style f45854b7;

    public be0(a01 a01Var) {
        this.f45839a2 = null;
        this.f45840a3 = null;
        this.f45841a4 = null;
        this.f45842a5 = PorterDuff.Mode.SRC_IN;
        this.f45843a6 = null;
        this.f45844a7 = 1.0f;
        this.f45845a8 = 1.0f;
        this.f45847b0 = v10.MASK;
        this.f45848b1 = 0.0f;
        this.f45849b2 = 0.0f;
        this.f45850b3 = 0;
        this.f45851b4 = 0;
        this.f45852b5 = 0;
        this.f45853b6 = 0;
        this.f45854b7 = Paint.Style.FILL_AND_STROKE;
        this.f45837a0 = a01Var;
        this.f45838a1 = null;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final int getChangingConfigurations() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public Drawable newDrawable() {
        ce0 ce0Var = new ce0(this);
        ce0Var.f46111a4 = true;
        return ce0Var;
    }

    public be0(be0 be0Var) {
        this.f45839a2 = null;
        this.f45840a3 = null;
        this.f45841a4 = null;
        this.f45842a5 = PorterDuff.Mode.SRC_IN;
        this.f45843a6 = null;
        this.f45844a7 = 1.0f;
        this.f45845a8 = 1.0f;
        this.f45847b0 = v10.MASK;
        this.f45848b1 = 0.0f;
        this.f45849b2 = 0.0f;
        this.f45850b3 = 0;
        this.f45851b4 = 0;
        this.f45852b5 = 0;
        this.f45853b6 = 0;
        this.f45854b7 = Paint.Style.FILL_AND_STROKE;
        this.f45837a0 = be0Var.f45837a0;
        this.f45838a1 = be0Var.f45838a1;
        this.f45846a9 = be0Var.f45846a9;
        this.f45839a2 = be0Var.f45839a2;
        this.f45840a3 = be0Var.f45840a3;
        this.f45842a5 = be0Var.f45842a5;
        this.f45841a4 = be0Var.f45841a4;
        this.f45847b0 = be0Var.f45847b0;
        this.f45844a7 = be0Var.f45844a7;
        this.f45852b5 = be0Var.f45852b5;
        this.f45850b3 = be0Var.f45850b3;
        this.f45845a8 = be0Var.f45845a8;
        this.f45848b1 = be0Var.f45848b1;
        this.f45849b2 = be0Var.f45849b2;
        this.f45851b4 = be0Var.f45851b4;
        this.f45853b6 = be0Var.f45853b6;
        this.f45854b7 = be0Var.f45854b7;
        if (be0Var.f45843a6 != null) {
            this.f45843a6 = new Rect(be0Var.f45843a6);
        }
    }
}
