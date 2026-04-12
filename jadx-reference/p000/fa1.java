package p000;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeProvider;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class fa1 {
    /* renamed from: a0 */
    public static AccessibilityNodeProvider m212763a0(View view) {
        return view.getAccessibilityNodeProvider();
    }

    /* renamed from: a1 */
    public static boolean m212764a1(View view) {
        return view.getFitsSystemWindows();
    }

    /* renamed from: a2 */
    public static int m212765a2(View view) {
        return view.getImportantForAccessibility();
    }

    /* renamed from: a3 */
    public static int m212766a3(View view) {
        return view.getMinimumHeight();
    }

    /* renamed from: a4 */
    public static int m212767a4(View view) {
        return view.getMinimumWidth();
    }

    /* renamed from: a5 */
    public static ViewParent m212768a5(View view) {
        return view.getParentForAccessibility();
    }

    /* renamed from: a6 */
    public static int m212769a6(View view) {
        return view.getWindowSystemUiVisibility();
    }

    /* renamed from: a7 */
    public static boolean m212770a7(View view) {
        return view.hasOverlappingRendering();
    }

    /* renamed from: a8 */
    public static boolean m212771a8(View view) {
        return view.hasTransientState();
    }

    /* renamed from: a9 */
    public static boolean m212772a9(View view, int i, Bundle bundle) {
        return view.performAccessibilityAction(i, bundle);
    }

    /* renamed from: b0 */
    public static void m212773b0(View view) {
        view.postInvalidateOnAnimation();
    }

    /* renamed from: b1 */
    public static void m212774b1(View view, int i, int i2, int i3, int i4) {
        view.postInvalidateOnAnimation(i, i2, i3, i4);
    }

    /* renamed from: b2 */
    public static void m212775b2(View view, Runnable runnable) {
        view.postOnAnimation(runnable);
    }

    /* renamed from: b3 */
    public static void m212776b3(View view, Runnable runnable, long j) {
        view.postOnAnimationDelayed(runnable, j);
    }

    /* renamed from: b4 */
    public static void m212777b4(ViewTreeObserver viewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    /* renamed from: b5 */
    public static void m212778b5(View view) {
        view.requestFitSystemWindows();
    }

    /* renamed from: b6 */
    public static void m212779b6(View view, Drawable drawable) {
        view.setBackground(drawable);
    }

    /* renamed from: b7 */
    public static void m212780b7(View view, boolean z) {
        view.setHasTransientState(z);
    }

    /* renamed from: b8 */
    public static void m212781b8(View view, int i) {
        view.setImportantForAccessibility(i);
    }
}
