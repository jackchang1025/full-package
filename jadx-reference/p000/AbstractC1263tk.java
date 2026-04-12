package p000;

import android.graphics.Rect;
import android.view.DisplayCutout;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: tk */
/* loaded from: classes.dex */
public abstract class AbstractC1263tk {
    /* renamed from: a0 */
    public static DisplayCutout m214753a0(Rect rect, List<Rect> list) {
        return new DisplayCutout(rect, list);
    }

    /* renamed from: a1 */
    public static List<Rect> m214754a1(DisplayCutout displayCutout) {
        return displayCutout.getBoundingRects();
    }

    /* renamed from: a2 */
    public static int m214755a2(DisplayCutout displayCutout) {
        return displayCutout.getSafeInsetBottom();
    }

    /* renamed from: a3 */
    public static int m214756a3(DisplayCutout displayCutout) {
        return displayCutout.getSafeInsetLeft();
    }

    /* renamed from: a4 */
    public static int m214757a4(DisplayCutout displayCutout) {
        return displayCutout.getSafeInsetRight();
    }

    /* renamed from: a5 */
    public static int m214758a5(DisplayCutout displayCutout) {
        return displayCutout.getSafeInsetTop();
    }
}
