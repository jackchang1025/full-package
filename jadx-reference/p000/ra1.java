package p000;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class ra1 {
    /* renamed from: a0 */
    public static View.AccessibilityDelegate m214522a0(View view) {
        return view.getAccessibilityDelegate();
    }

    /* renamed from: a1 */
    public static List<Rect> m214523a1(View view) {
        return view.getSystemGestureExclusionRects();
    }

    /* renamed from: a2 */
    public static void m214524a2(View view, Context context, int[] iArr, AttributeSet attributeSet, TypedArray typedArray, int i, int i2) {
        view.saveAttributeDataForStyleable(context, iArr, attributeSet, typedArray, i, i2);
    }

    /* renamed from: a3 */
    public static void m214525a3(View view, List<Rect> list) {
        view.setSystemGestureExclusionRects(list);
    }
}
