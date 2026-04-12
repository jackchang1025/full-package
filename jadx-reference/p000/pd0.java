package p000;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import com.google.android.material.R$attr;
import com.google.android.material.button.MaterialButton;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class pd0 {

    /* renamed from: a0 */
    public final MaterialButton f59196a0;

    /* renamed from: a1 */
    public a01 f59197a1;

    /* renamed from: a2 */
    public int f59198a2;

    /* renamed from: a3 */
    public int f59199a3;

    /* renamed from: a4 */
    public int f59200a4;

    /* renamed from: a5 */
    public int f59201a5;

    /* renamed from: a6 */
    public int f59202a6;

    /* renamed from: a7 */
    public int f59203a7;

    /* renamed from: a8 */
    public PorterDuff.Mode f59204a8;

    /* renamed from: a9 */
    public ColorStateList f59205a9;

    /* renamed from: b0 */
    public ColorStateList f59206b0;

    /* renamed from: b1 */
    public ColorStateList f59207b1;

    /* renamed from: b2 */
    public ce0 f59208b2;

    /* renamed from: b6 */
    public boolean f59212b6;

    /* renamed from: b8 */
    public RippleDrawable f59214b8;

    /* renamed from: b9 */
    public int f59215b9;

    /* renamed from: b3 */
    public boolean f59209b3 = false;

    /* renamed from: b4 */
    public boolean f59210b4 = false;

    /* renamed from: b5 */
    public boolean f59211b5 = false;

    /* renamed from: b7 */
    public boolean f59213b7 = true;

    public pd0(MaterialButton materialButton, a01 a01Var) {
        this.f59196a0 = materialButton;
        this.f59197a1 = a01Var;
    }

    /* renamed from: a0 */
    public final l01 m214246a0() {
        RippleDrawable rippleDrawable = this.f59214b8;
        if (rippleDrawable == null || rippleDrawable.getNumberOfLayers() <= 1) {
            return null;
        }
        return this.f59214b8.getNumberOfLayers() > 2 ? (l01) this.f59214b8.getDrawable(2) : (l01) this.f59214b8.getDrawable(1);
    }

    /* renamed from: a1 */
    public final ce0 m214247a1(boolean z) {
        RippleDrawable rippleDrawable = this.f59214b8;
        if (rippleDrawable == null || rippleDrawable.getNumberOfLayers() <= 0) {
            return null;
        }
        return (ce0) ((LayerDrawable) ((InsetDrawable) this.f59214b8.getDrawable(0)).getDrawable()).getDrawable(!z ? 1 : 0);
    }

    /* renamed from: a2 */
    public final void m214248a2(a01 a01Var) {
        this.f59197a1 = a01Var;
        if (m214247a1(false) != null) {
            m214247a1(false).setShapeAppearanceModel(a01Var);
        }
        if (m214247a1(true) != null) {
            m214247a1(true).setShapeAppearanceModel(a01Var);
        }
        if (m214246a0() != null) {
            m214246a0().setShapeAppearanceModel(a01Var);
        }
    }

    /* renamed from: a3 */
    public final void m214249a3(int i, int i2) {
        WeakHashMap weakHashMap = xa1.f61054a0;
        MaterialButton materialButton = this.f59196a0;
        int iM212906a5 = ga1.m212906a5(materialButton);
        int paddingTop = materialButton.getPaddingTop();
        int iM212905a4 = ga1.m212905a4(materialButton);
        int paddingBottom = materialButton.getPaddingBottom();
        int i3 = this.f59200a4;
        int i4 = this.f59201a5;
        this.f59201a5 = i2;
        this.f59200a4 = i;
        if (!this.f59210b4) {
            m214250a4();
        }
        ga1.m212911b0(materialButton, iM212906a5, (paddingTop + i) - i3, iM212905a4, (paddingBottom + i2) - i4);
    }

    /* renamed from: a4 */
    public final void m214250a4() {
        ce0 ce0Var = new ce0(this.f59197a1);
        MaterialButton materialButton = this.f59196a0;
        ce0Var.m210838b0(materialButton.getContext());
        AbstractC1270tr.m214774a7(ce0Var, this.f59205a9);
        PorterDuff.Mode mode = this.f59204a8;
        if (mode != null) {
            AbstractC1270tr.m214775a8(ce0Var, mode);
        }
        float f = this.f59203a7;
        ColorStateList colorStateList = this.f59206b0;
        ce0Var.m210846b8(f);
        ce0Var.m210845b7(colorStateList);
        ce0 ce0Var2 = new ce0(this.f59197a1);
        ce0Var2.setTint(0);
        float f2 = this.f59203a7;
        int iM213568b5 = this.f59209b3 ? kj1.m213568b5(materialButton, R$attr.colorSurface) : 0;
        ce0Var2.m210846b8(f2);
        ce0Var2.m210845b7(ColorStateList.valueOf(iM213568b5));
        ce0 ce0Var3 = new ce0(this.f59197a1);
        this.f59208b2 = ce0Var3;
        AbstractC1270tr.m214773a6(ce0Var3, -1);
        RippleDrawable rippleDrawable = new RippleDrawable(b81.m210594e5(this.f59207b1), new InsetDrawable((Drawable) new LayerDrawable(new Drawable[]{ce0Var2, ce0Var}), this.f59198a2, this.f59200a4, this.f59199a3, this.f59201a5), this.f59208b2);
        this.f59214b8 = rippleDrawable;
        materialButton.setInternalBackground(rippleDrawable);
        ce0 ce0VarM214247a1 = m214247a1(false);
        if (ce0VarM214247a1 != null) {
            ce0VarM214247a1.m210839b1(this.f59215b9);
            ce0VarM214247a1.setState(materialButton.getDrawableState());
        }
    }

    /* renamed from: a5 */
    public final void m214251a5() {
        ce0 ce0VarM214247a1 = m214247a1(false);
        ce0 ce0VarM214247a12 = m214247a1(true);
        if (ce0VarM214247a1 != null) {
            float f = this.f59203a7;
            ColorStateList colorStateList = this.f59206b0;
            ce0VarM214247a1.m210846b8(f);
            ce0VarM214247a1.m210845b7(colorStateList);
            if (ce0VarM214247a12 != null) {
                float f2 = this.f59203a7;
                int iM213568b5 = this.f59209b3 ? kj1.m213568b5(this.f59196a0, R$attr.colorSurface) : 0;
                ce0VarM214247a12.m210846b8(f2);
                ce0VarM214247a12.m210845b7(ColorStateList.valueOf(iM213568b5));
            }
        }
    }
}
