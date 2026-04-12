package p000;

import android.graphics.Paint;
import android.view.Display;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class ga1 {
    /* renamed from: a0 */
    public static int m212901a0() {
        return View.generateViewId();
    }

    /* renamed from: a1 */
    public static Display m212902a1(View view) {
        return view.getDisplay();
    }

    /* renamed from: a2 */
    public static int m212903a2(View view) {
        return view.getLabelFor();
    }

    /* renamed from: a3 */
    public static int m212904a3(View view) {
        return view.getLayoutDirection();
    }

    /* renamed from: a4 */
    public static int m212905a4(View view) {
        return view.getPaddingEnd();
    }

    /* renamed from: a5 */
    public static int m212906a5(View view) {
        return view.getPaddingStart();
    }

    /* renamed from: a6 */
    public static boolean m212907a6(View view) {
        return view.isPaddingRelative();
    }

    /* renamed from: a7 */
    public static void m212908a7(View view, int i) {
        view.setLabelFor(i);
    }

    /* renamed from: a8 */
    public static void m212909a8(View view, Paint paint) {
        view.setLayerPaint(paint);
    }

    /* renamed from: a9 */
    public static void m212910a9(View view, int i) {
        view.setLayoutDirection(i);
    }

    /* renamed from: b0 */
    public static void m212911b0(View view, int i, int i2, int i3, int i4) {
        view.setPaddingRelative(i, i2, i3, i4);
    }
}
