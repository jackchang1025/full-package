package p000;

import android.graphics.Bitmap;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class y40 {
    /* renamed from: a0 */
    public static Drawable m215236a0(Drawable drawable, Drawable drawable2) {
        return new AdaptiveIconDrawable(drawable, drawable2);
    }

    /* renamed from: a1 */
    public static Icon m215237a1(Bitmap bitmap) {
        return Icon.createWithAdaptiveBitmap(bitmap);
    }
}
