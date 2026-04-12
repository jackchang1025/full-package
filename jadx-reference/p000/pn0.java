package p000;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.PointerIcon;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class pn0 {
    /* renamed from: a0 */
    public static PointerIcon m214297a0(Bitmap bitmap, float f, float f2) {
        return PointerIcon.create(bitmap, f, f2);
    }

    /* renamed from: a1 */
    public static PointerIcon m214298a1(Context context, int i) {
        return PointerIcon.getSystemIcon(context, i);
    }

    /* renamed from: a2 */
    public static PointerIcon m214299a2(Resources resources, int i) {
        return PointerIcon.load(resources, i);
    }
}
