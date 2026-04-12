package p000;

import android.view.View;
import java.util.Collection;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class oa1 {
    /* renamed from: a0 */
    public static void m214171a0(View view, Collection<View> collection, int i) {
        view.addKeyboardNavigationClusters(collection, i);
    }

    /* renamed from: a1 */
    public static int m214172a1(View view) {
        return view.getImportantForAutofill();
    }

    /* renamed from: a2 */
    public static int m214173a2(View view) {
        return view.getNextClusterForwardId();
    }

    /* renamed from: a3 */
    public static boolean m214174a3(View view) {
        return view.hasExplicitFocusable();
    }

    /* renamed from: a4 */
    public static boolean m214175a4(View view) {
        return view.isFocusedByDefault();
    }

    /* renamed from: a5 */
    public static boolean m214176a5(View view) {
        return view.isImportantForAutofill();
    }

    /* renamed from: a6 */
    public static boolean m214177a6(View view) {
        return view.isKeyboardNavigationCluster();
    }

    /* renamed from: a7 */
    public static View m214178a7(View view, View view2, int i) {
        return view.keyboardNavigationClusterSearch(view2, i);
    }

    /* renamed from: a8 */
    public static boolean m214179a8(View view) {
        return view.restoreDefaultFocus();
    }

    /* renamed from: a9 */
    public static void m214180a9(View view, String... strArr) {
        view.setAutofillHints(strArr);
    }

    /* renamed from: b0 */
    public static void m214181b0(View view, boolean z) {
        view.setFocusedByDefault(z);
    }

    /* renamed from: b1 */
    public static void m214182b1(View view, int i) {
        view.setImportantForAutofill(i);
    }

    /* renamed from: b2 */
    public static void m214183b2(View view, boolean z) {
        view.setKeyboardNavigationCluster(z);
    }

    /* renamed from: b3 */
    public static void m214184b3(View view, int i) {
        view.setNextClusterForwardId(i);
    }

    /* renamed from: b4 */
    public static void m214185b4(View view, CharSequence charSequence) {
        view.setTooltipText(charSequence);
    }
}
