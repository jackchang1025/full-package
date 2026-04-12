package p000;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.widget.ImageView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class h50 {
    /* renamed from: a0 */
    public static ColorStateList m212995a0(ImageView imageView) {
        return imageView.getImageTintList();
    }

    /* renamed from: a1 */
    public static PorterDuff.Mode m212996a1(ImageView imageView) {
        return imageView.getImageTintMode();
    }

    /* renamed from: a2 */
    public static void m212997a2(ImageView imageView, ColorStateList colorStateList) {
        imageView.setImageTintList(colorStateList);
    }

    /* renamed from: a3 */
    public static void m212998a3(ImageView imageView, PorterDuff.Mode mode) {
        imageView.setImageTintMode(mode);
    }
}
