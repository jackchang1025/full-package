package p000;

import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class ia1 {
    /* renamed from: a0 */
    public static int m213140a0(View view) {
        return view.getAccessibilityLiveRegion();
    }

    /* renamed from: a1 */
    public static boolean m213141a1(View view) {
        return view.isAttachedToWindow();
    }

    /* renamed from: a2 */
    public static boolean m213142a2(View view) {
        return view.isLaidOut();
    }

    /* renamed from: a3 */
    public static boolean m213143a3(View view) {
        return view.isLayoutDirectionResolved();
    }

    /* renamed from: a4 */
    public static void m213144a4(ViewParent viewParent, View view, View view2, int i) {
        viewParent.notifySubtreeAccessibilityStateChanged(view, view2, i);
    }

    /* renamed from: a5 */
    public static void m213145a5(View view, int i) {
        view.setAccessibilityLiveRegion(i);
    }

    /* renamed from: a6 */
    public static void m213146a6(AccessibilityEvent accessibilityEvent, int i) {
        accessibilityEvent.setContentChangeTypes(i);
    }
}
