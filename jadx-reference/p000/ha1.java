package p000;

import android.graphics.Rect;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class ha1 {
    /* renamed from: a0 */
    public static Rect m213015a0(View view) {
        return view.getClipBounds();
    }

    /* renamed from: a1 */
    public static boolean m213016a1(View view) {
        return view.isInLayout();
    }

    /* renamed from: a2 */
    public static void m213017a2(View view, Rect rect) {
        view.setClipBounds(rect);
    }
}
