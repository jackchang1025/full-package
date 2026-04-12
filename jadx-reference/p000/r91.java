package p000;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class r91 extends Drawable.ConstantState {

    /* renamed from: a0 */
    public final Drawable.ConstantState f59650a0;

    public r91(Drawable.ConstantState constantState) {
        this.f59650a0 = constantState;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final boolean canApplyTheme() {
        return this.f59650a0.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public int getChangingConfigurations() {
        return this.f59650a0.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable() {
        s91 s91Var = new s91();
        s91Var.f57309a0 = (VectorDrawable) this.f59650a0.newDrawable();
        return s91Var;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable(Resources resources) {
        s91 s91Var = new s91();
        s91Var.f57309a0 = (VectorDrawable) this.f59650a0.newDrawable(resources);
        return s91Var;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable(Resources resources, Resources.Theme theme) {
        s91 s91Var = new s91();
        s91Var.f57309a0 = (VectorDrawable) this.f59650a0.newDrawable(resources, theme);
        return s91Var;
    }
}
