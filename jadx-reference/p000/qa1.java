package p000;

import android.view.View;
import androidx.core.R$id;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class qa1 {
    /* renamed from: a0 */
    public static void m214363a0(View view, va1 va1Var) {
        t01 t01Var = (t01) view.getTag(R$id.tag_unhandled_key_listeners);
        if (t01Var == null) {
            t01Var = new t01();
            view.setTag(R$id.tag_unhandled_key_listeners, t01Var);
        }
        Objects.requireNonNull(va1Var);
        View.OnUnhandledKeyEventListener pa1Var = new pa1();
        t01Var.put(va1Var, pa1Var);
        view.addOnUnhandledKeyEventListener(pa1Var);
    }

    /* renamed from: a1 */
    public static CharSequence m214364a1(View view) {
        return view.getAccessibilityPaneTitle();
    }

    /* renamed from: a2 */
    public static boolean m214365a2(View view) {
        return view.isAccessibilityHeading();
    }

    /* renamed from: a3 */
    public static boolean m214366a3(View view) {
        return view.isScreenReaderFocusable();
    }

    /* renamed from: a4 */
    public static void m214367a4(View view, va1 va1Var) {
        View.OnUnhandledKeyEventListener onUnhandledKeyEventListener;
        t01 t01Var = (t01) view.getTag(R$id.tag_unhandled_key_listeners);
        if (t01Var == null || (onUnhandledKeyEventListener = (View.OnUnhandledKeyEventListener) t01Var.getOrDefault(va1Var, null)) == null) {
            return;
        }
        view.removeOnUnhandledKeyEventListener(onUnhandledKeyEventListener);
    }

    /* renamed from: a5 */
    public static <T> T m214368a5(View view, int i) {
        return (T) view.requireViewById(i);
    }

    /* renamed from: a6 */
    public static void m214369a6(View view, boolean z) {
        view.setAccessibilityHeading(z);
    }

    /* renamed from: a7 */
    public static void m214370a7(View view, CharSequence charSequence) {
        view.setAccessibilityPaneTitle(charSequence);
    }

    /* renamed from: a8 */
    public static void m214371a8(View view, boolean z) {
        view.setScreenReaderFocusable(z);
    }
}
