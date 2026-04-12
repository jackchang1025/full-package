package p000;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: t3 */
/* loaded from: classes2.dex */
public final class C1245t3 extends Drawable.ConstantState {

    /* renamed from: a0 */
    public final /* synthetic */ int f60132a0 = 0;

    /* renamed from: a1 */
    public final Object f60133a1;

    public C1245t3(C0457ei c0457ei) {
        this.f60133a1 = c0457ei;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public boolean canApplyTheme() {
        switch (this.f60132a0) {
            case 0:
                return ((Drawable.ConstantState) this.f60133a1).canApplyTheme();
            default:
                return super.canApplyTheme();
        }
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final int getChangingConfigurations() {
        switch (this.f60132a0) {
            case 0:
                return ((Drawable.ConstantState) this.f60133a1).getChangingConfigurations();
            default:
                return 0;
        }
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable() {
        switch (this.f60132a0) {
            case 0:
                C1246t4 c1246t4 = new C1246t4(null, 0);
                Drawable drawableNewDrawable = ((Drawable.ConstantState) this.f60133a1).newDrawable();
                c1246t4.f57309a0 = drawableNewDrawable;
                drawableNewDrawable.setCallback(c1246t4.f60139a5);
                return c1246t4;
            default:
                return (C0457ei) this.f60133a1;
        }
    }

    public C1245t3(Drawable.ConstantState constantState) {
        this.f60133a1 = constantState;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public Drawable newDrawable(Resources resources) {
        switch (this.f60132a0) {
            case 0:
                C1246t4 c1246t4 = new C1246t4(null, 0);
                Drawable drawableNewDrawable = ((Drawable.ConstantState) this.f60133a1).newDrawable(resources);
                c1246t4.f57309a0 = drawableNewDrawable;
                drawableNewDrawable.setCallback(c1246t4.f60139a5);
                return c1246t4;
            default:
                return super.newDrawable(resources);
        }
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public Drawable newDrawable(Resources resources, Resources.Theme theme) {
        switch (this.f60132a0) {
            case 0:
                C1246t4 c1246t4 = new C1246t4(null, 0);
                Drawable drawableNewDrawable = ((Drawable.ConstantState) this.f60133a1).newDrawable(resources, theme);
                c1246t4.f57309a0 = drawableNewDrawable;
                drawableNewDrawable.setCallback(c1246t4.f60139a5);
                return c1246t4;
            default:
                return super.newDrawable(resources, theme);
        }
    }
}
