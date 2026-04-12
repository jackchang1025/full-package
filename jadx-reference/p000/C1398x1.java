package p000;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: x1 */
/* loaded from: classes.dex */
public final class C1398x1 {

    /* renamed from: a1 */
    public static final PorterDuff.Mode f60988a1 = PorterDuff.Mode.SRC_IN;

    /* renamed from: a2 */
    public static C1398x1 f60989a2;

    /* renamed from: a0 */
    public sr0 f60990a0;

    /* renamed from: a0 */
    public static synchronized C1398x1 m215095a0() {
        try {
            if (f60989a2 == null) {
                m215097a3();
            }
        } catch (Throwable th) {
            throw th;
        }
        return f60989a2;
    }

    /* renamed from: a2 */
    public static synchronized PorterDuffColorFilter m215096a2(int i, PorterDuff.Mode mode) {
        return sr0.m214659a4(i, mode);
    }

    /* renamed from: a3 */
    public static synchronized void m215097a3() {
        if (f60989a2 == null) {
            C1398x1 c1398x1 = new C1398x1();
            f60989a2 = c1398x1;
            c1398x1.f60990a0 = sr0.m214658a1();
            sr0 sr0Var = f60989a2.f60990a0;
            C1397x0 c1397x0 = new C1397x0();
            synchronized (sr0Var) {
                sr0Var.f60071a4 = c1397x0;
            }
        }
    }

    /* renamed from: a4 */
    public static void m215098a4(Drawable drawable, t61 t61Var, int[] iArr) {
        PorterDuff.Mode mode = sr0.f60064a5;
        int[] state = drawable.getState();
        int[] iArr2 = AbstractC1274tv.f60282a0;
        if (drawable.mutate() == drawable) {
            if ((drawable instanceof LayerDrawable) && drawable.isStateful()) {
                drawable.setState(new int[0]);
                drawable.setState(state);
            }
            boolean z = t61Var.f60177a3;
            if (!z && !t61Var.f60176a2) {
                drawable.clearColorFilter();
                return;
            }
            PorterDuffColorFilter porterDuffColorFilterM214659a4 = null;
            ColorStateList colorStateList = z ? t61Var.f60174a0 : null;
            PorterDuff.Mode mode2 = t61Var.f60176a2 ? t61Var.f60175a1 : sr0.f60064a5;
            if (colorStateList != null && mode2 != null) {
                porterDuffColorFilterM214659a4 = sr0.m214659a4(colorStateList.getColorForState(iArr, 0), mode2);
            }
            drawable.setColorFilter(porterDuffColorFilterM214659a4);
        }
    }

    /* renamed from: a1 */
    public final synchronized Drawable m215099a1(Context context, int i) {
        return this.f60990a0.m214661a2(context, i);
    }
}
