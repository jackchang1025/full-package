package p000;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class hd1 {

    /* renamed from: a0 */
    public static final jd1 f56654a0;

    /* renamed from: a1 */
    public static final C0556gt f56655a1;

    static {
        if (Build.VERSION.SDK_INT >= 29) {
            f56654a0 = new kd1();
        } else {
            f56654a0 = new jd1();
        }
        f56655a1 = new C0556gt(Float.class, "translationAlpha", 15);
        new C0556gt(Rect.class, "clipBounds", 16);
    }

    /* renamed from: a0 */
    public static void m213026a0(View view, int i, int i2, int i3, int i4) {
        f56654a0.mo213285f5(view, i, i2, i3, i4);
    }
}
