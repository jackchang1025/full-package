package p000;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import androidx.core.R$id;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class la1 {
    /* renamed from: a0 */
    public static void m213801a0(WindowInsets windowInsets, View view) {
        View.OnApplyWindowInsetsListener onApplyWindowInsetsListener = (View.OnApplyWindowInsetsListener) view.getTag(R$id.tag_window_insets_animation_callback);
        if (onApplyWindowInsetsListener != null) {
            onApplyWindowInsetsListener.onApplyWindowInsets(view, windowInsets);
        }
    }

    /* renamed from: a1 */
    public static xf1 m213802a1(View view, xf1 xf1Var, Rect rect) {
        WindowInsets windowInsetsM215175a5 = xf1Var.m215175a5();
        if (windowInsetsM215175a5 != null) {
            return xf1.m215170a6(view, view.computeSystemWindowInsets(windowInsetsM215175a5, rect));
        }
        rect.setEmpty();
        return xf1Var;
    }

    /* renamed from: a2 */
    public static boolean m213803a2(View view, float f, float f2, boolean z) {
        return view.dispatchNestedFling(f, f2, z);
    }

    /* renamed from: a3 */
    public static boolean m213804a3(View view, float f, float f2) {
        return view.dispatchNestedPreFling(f, f2);
    }

    /* renamed from: a4 */
    public static boolean m213805a4(View view, int i, int i2, int[] iArr, int[] iArr2) {
        return view.dispatchNestedPreScroll(i, i2, iArr, iArr2);
    }

    /* renamed from: a5 */
    public static boolean m213806a5(View view, int i, int i2, int i3, int i4, int[] iArr) {
        return view.dispatchNestedScroll(i, i2, i3, i4, iArr);
    }

    /* renamed from: a6 */
    public static ColorStateList m213807a6(View view) {
        return view.getBackgroundTintList();
    }

    /* renamed from: a7 */
    public static PorterDuff.Mode m213808a7(View view) {
        return view.getBackgroundTintMode();
    }

    /* renamed from: a8 */
    public static float m213809a8(View view) {
        return view.getElevation();
    }

    /* renamed from: a9 */
    public static xf1 m213810a9(View view) throws IllegalAccessException, IllegalArgumentException {
        if (kf1.f57518a3 && view.isAttachedToWindow()) {
            try {
                Object obj = kf1.f57515a0.get(view.getRootView());
                if (obj != null) {
                    Rect rect = (Rect) kf1.f57516a1.get(obj);
                    Rect rect2 = (Rect) kf1.f57517a2.get(obj);
                    if (rect != null && rect2 != null) {
                        int i = Build.VERSION.SDK_INT;
                        pf1 of1Var = i >= 30 ? new of1() : i >= 29 ? new mf1() : new lf1();
                        of1Var.mo213837a4(f60.m212748a1(rect.left, rect.top, rect.right, rect.bottom));
                        of1Var.mo213838a6(f60.m212748a1(rect2.left, rect2.top, rect2.right, rect2.bottom));
                        xf1 xf1VarMo213836a1 = of1Var.mo213836a1();
                        xf1VarMo213836a1.f61102a0.mo214396b5(xf1VarMo213836a1);
                        xf1VarMo213836a1.f61102a0.mo214390a3(view.getRootView());
                        return xf1VarMo213836a1;
                    }
                }
            } catch (IllegalAccessException e) {
                e.getMessage();
            }
        }
        return null;
    }

    /* renamed from: b0 */
    public static String m213811b0(View view) {
        return view.getTransitionName();
    }

    /* renamed from: b1 */
    public static float m213812b1(View view) {
        return view.getTranslationZ();
    }

    /* renamed from: b2 */
    public static float m213813b2(View view) {
        return view.getZ();
    }

    /* renamed from: b3 */
    public static boolean m213814b3(View view) {
        return view.hasNestedScrollingParent();
    }

    /* renamed from: b4 */
    public static boolean m213815b4(View view) {
        return view.isImportantForAccessibility();
    }

    /* renamed from: b5 */
    public static boolean m213816b5(View view) {
        return view.isNestedScrollingEnabled();
    }

    /* renamed from: b6 */
    public static void m213817b6(View view, ColorStateList colorStateList) {
        view.setBackgroundTintList(colorStateList);
    }

    /* renamed from: b7 */
    public static void m213818b7(View view, PorterDuff.Mode mode) {
        view.setBackgroundTintMode(mode);
    }

    /* renamed from: b8 */
    public static void m213819b8(View view, float f) {
        view.setElevation(f);
    }

    /* renamed from: b9 */
    public static void m213820b9(View view, boolean z) {
        view.setNestedScrollingEnabled(z);
    }

    /* renamed from: c0 */
    public static void m213821c0(View view, vk0 vk0Var) {
        if (Build.VERSION.SDK_INT < 30) {
            view.setTag(R$id.tag_on_apply_window_listener, vk0Var);
        }
        if (vk0Var == null) {
            view.setOnApplyWindowInsetsListener((View.OnApplyWindowInsetsListener) view.getTag(R$id.tag_window_insets_animation_callback));
        } else {
            view.setOnApplyWindowInsetsListener(new ka1(view, vk0Var));
        }
    }

    /* renamed from: c1 */
    public static void m213822c1(View view, String str) {
        view.setTransitionName(str);
    }

    /* renamed from: c2 */
    public static void m213823c2(View view, float f) {
        view.setTranslationZ(f);
    }

    /* renamed from: c3 */
    public static void m213824c3(View view, float f) {
        view.setZ(f);
    }

    /* renamed from: c4 */
    public static boolean m213825c4(View view, int i) {
        return view.startNestedScroll(i);
    }

    /* renamed from: c5 */
    public static void m213826c5(View view) {
        view.stopNestedScroll();
    }
}
