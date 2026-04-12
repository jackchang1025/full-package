package p000;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.PathInterpolator;
import androidx.core.R$id;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ff1 extends if1 {

    /* renamed from: a4 */
    public static final PathInterpolator f56234a4 = new PathInterpolator(0.0f, 1.1f, 0.0f, 1.0f);

    /* renamed from: a5 */
    public static final C1487yo f56235a5 = new C1487yo(0);

    /* renamed from: a6 */
    public static final DecelerateInterpolator f56236a6 = new DecelerateInterpolator();

    /* renamed from: a4 */
    public static void m212801a4(View view) {
        C0816ld c0816ldM212806a9 = m212806a9(view);
        if (c0816ldM212806a9 != null) {
            ((View) c0816ldM212806a9.f57883a3).setTranslationY(0.0f);
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                m212801a4(viewGroup.getChildAt(i));
            }
        }
    }

    /* renamed from: a5 */
    public static void m212802a5(View view, WindowInsets windowInsets, boolean z) {
        C0816ld c0816ldM212806a9 = m212806a9(view);
        if (c0816ldM212806a9 != null) {
            c0816ldM212806a9.f57882a2 = windowInsets;
            if (!z) {
                View view2 = (View) c0816ldM212806a9.f57883a3;
                int[] iArr = (int[]) c0816ldM212806a9.f57884a4;
                view2.getLocationOnScreen(iArr);
                z = true;
                c0816ldM212806a9.f57880a0 = iArr[1];
            }
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                m212802a5(viewGroup.getChildAt(i), windowInsets, z);
            }
        }
    }

    /* renamed from: a6 */
    public static void m212803a6(View view, xf1 xf1Var, List list) {
        C0816ld c0816ldM212806a9 = m212806a9(view);
        if (c0816ldM212806a9 != null) {
            c0816ldM212806a9.m213833a0(xf1Var, list);
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                m212803a6(viewGroup.getChildAt(i), xf1Var, list);
            }
        }
    }

    /* renamed from: a7 */
    public static void m212804a7(View view, C1217sc c1217sc) {
        C0816ld c0816ldM212806a9 = m212806a9(view);
        if (c0816ldM212806a9 != null) {
            View view2 = (View) c0816ldM212806a9.f57883a3;
            int[] iArr = (int[]) c0816ldM212806a9.f57884a4;
            view2.getLocationOnScreen(iArr);
            int i = c0816ldM212806a9.f57880a0 - iArr[1];
            c0816ldM212806a9.f57881a1 = i;
            view2.setTranslationY(i);
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                m212804a7(viewGroup.getChildAt(i2), c1217sc);
            }
        }
    }

    /* renamed from: a8 */
    public static WindowInsets m212805a8(View view, WindowInsets windowInsets) {
        return view.getTag(R$id.tag_on_apply_window_listener) != null ? windowInsets : view.onApplyWindowInsets(windowInsets);
    }

    /* renamed from: a9 */
    public static C0816ld m212806a9(View view) {
        Object tag = view.getTag(R$id.tag_window_insets_animation_callback);
        if (tag instanceof ef1) {
            return ((ef1) tag).f56001a0;
        }
        return null;
    }
}
