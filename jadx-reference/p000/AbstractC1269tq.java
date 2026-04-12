package p000;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.InsetDrawable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: tq */
/* loaded from: classes.dex */
public abstract class AbstractC1269tq {
    /* renamed from: a0 */
    public static int m214762a0(Drawable drawable) {
        return drawable.getAlpha();
    }

    /* renamed from: a1 */
    public static Drawable m214763a1(DrawableContainer.DrawableContainerState drawableContainerState, int i) {
        return drawableContainerState.getChild(i);
    }

    /* renamed from: a2 */
    public static Drawable m214764a2(InsetDrawable insetDrawable) {
        return insetDrawable.getDrawable();
    }

    /* renamed from: a3 */
    public static boolean m214765a3(Drawable drawable) {
        return drawable.isAutoMirrored();
    }

    /* renamed from: a4 */
    public static void m214766a4(Drawable drawable, boolean z) {
        drawable.setAutoMirrored(z);
    }
}
