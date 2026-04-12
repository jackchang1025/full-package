package p000;

import android.graphics.drawable.Drawable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class ms0 extends Drawable {

    /* renamed from: a0 */
    public static final double f58396a0 = Math.cos(Math.toRadians(45.0d));

    /* renamed from: a0 */
    public static float m214020a0(float f, float f2, boolean z) {
        if (!z) {
            return f;
        }
        return (float) (((1.0d - f58396a0) * f2) + f);
    }

    /* renamed from: a1 */
    public static float m214021a1(float f, float f2, boolean z) {
        if (!z) {
            return f * 1.5f;
        }
        return (float) (((1.0d - f58396a0) * f2) + (f * 1.5f));
    }
}
