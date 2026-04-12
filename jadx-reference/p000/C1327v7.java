package p000;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import androidx.appcompat.R$color;
import androidx.appcompat.widget.ActionBarContextView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.WeakHashMap;
import okio.Segment;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: v7 */
/* loaded from: classes.dex */
public final class C1327v7 implements vk0, InterfaceC0857md, sf0 {

    /* renamed from: a0 */
    public final /* synthetic */ int f60589a0;

    /* renamed from: a1 */
    public final /* synthetic */ LayoutInflaterFactory2C1367w8 f60590a1;

    public /* synthetic */ C1327v7(LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8, int i) {
        this.f60589a0 = i;
        this.f60590a1 = layoutInflaterFactory2C1367w8;
    }

    @Override // p000.sf0
    /* renamed from: a0 */
    public void mo210850a0(bf0 bf0Var, boolean z) {
        C1366w7 c1366w7;
        switch (this.f60589a0) {
            case 2:
                this.f60590a1.m215022b6(bf0Var);
                break;
            default:
                bf0 bf0VarMo210698b0 = bf0Var.mo210698b0();
                int i = 0;
                boolean z2 = bf0VarMo210698b0 != bf0Var;
                if (z2) {
                    bf0Var = bf0VarMo210698b0;
                }
                LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8 = this.f60590a1;
                C1366w7[] c1366w7Arr = layoutInflaterFactory2C1367w8.f60836d7;
                int length = c1366w7Arr != null ? c1366w7Arr.length : 0;
                while (true) {
                    if (i >= length) {
                        c1366w7 = null;
                    } else {
                        c1366w7 = c1366w7Arr[i];
                        if (c1366w7 == null || c1366w7.f60788a7 != bf0Var) {
                            i++;
                        }
                    }
                }
                if (c1366w7 != null) {
                    if (!z2) {
                        layoutInflaterFactory2C1367w8.m215023b7(c1366w7, z);
                        break;
                    } else {
                        layoutInflaterFactory2C1367w8.m215021b5(c1366w7.f60781a0, c1366w7, bf0VarMo210698b0);
                        layoutInflaterFactory2C1367w8.m215023b7(c1366w7, true);
                        break;
                    }
                }
                break;
        }
    }

    @Override // p000.vk0
    /* renamed from: a6 */
    public xf1 mo213324a6(View view, xf1 xf1Var) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean z;
        boolean z2;
        boolean z3;
        xf1 xf1VarMo213836a1 = xf1Var;
        int iM215174a3 = xf1VarMo213836a1.m215174a3();
        LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8 = this.f60590a1;
        Context context = layoutInflaterFactory2C1367w8.f60809b0;
        int iM215174a32 = xf1VarMo213836a1.m215174a3();
        ActionBarContextView actionBarContextView = layoutInflaterFactory2C1367w8.f60820c1;
        if (actionBarContextView == null || !(actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            z = false;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutInflaterFactory2C1367w8.f60820c1.getLayoutParams();
            if (layoutInflaterFactory2C1367w8.f60820c1.isShown()) {
                if (layoutInflaterFactory2C1367w8.f60853f4 == null) {
                    layoutInflaterFactory2C1367w8.f60853f4 = new Rect();
                    layoutInflaterFactory2C1367w8.f60854f5 = new Rect();
                }
                Rect rect = layoutInflaterFactory2C1367w8.f60853f4;
                Rect rect2 = layoutInflaterFactory2C1367w8.f60854f5;
                rect.set(xf1VarMo213836a1.m215172a1(), xf1VarMo213836a1.m215174a3(), xf1VarMo213836a1.m215173a2(), xf1VarMo213836a1.m215171a0());
                ViewGroup viewGroup = layoutInflaterFactory2C1367w8.f60825c6;
                Method method = id1.f56869a0;
                if (method != null) {
                    try {
                        method.invoke(viewGroup, rect, rect2);
                    } catch (Exception unused) {
                    }
                }
                int i = rect.top;
                int i2 = rect.left;
                int i3 = rect.right;
                ViewGroup viewGroup2 = layoutInflaterFactory2C1367w8.f60825c6;
                WeakHashMap weakHashMap = xa1.f61054a0;
                xf1 xf1VarM213954a0 = ma1.m213954a0(viewGroup2);
                int iM215172a1 = xf1VarM213954a0 == null ? 0 : xf1VarM213954a0.m215172a1();
                int iM215173a2 = xf1VarM213954a0 == null ? 0 : xf1VarM213954a0.m215173a2();
                if (marginLayoutParams.topMargin == i && marginLayoutParams.leftMargin == i2 && marginLayoutParams.rightMargin == i3) {
                    z3 = false;
                } else {
                    marginLayoutParams.topMargin = i;
                    marginLayoutParams.leftMargin = i2;
                    marginLayoutParams.rightMargin = i3;
                    z3 = true;
                }
                if (i <= 0 || layoutInflaterFactory2C1367w8.f60827c8 != null) {
                    View view2 = layoutInflaterFactory2C1367w8.f60827c8;
                    if (view2 != null) {
                        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
                        int i4 = marginLayoutParams2.height;
                        int i5 = marginLayoutParams.topMargin;
                        if (i4 != i5 || marginLayoutParams2.leftMargin != iM215172a1 || marginLayoutParams2.rightMargin != iM215173a2) {
                            marginLayoutParams2.height = i5;
                            marginLayoutParams2.leftMargin = iM215172a1;
                            marginLayoutParams2.rightMargin = iM215173a2;
                            layoutInflaterFactory2C1367w8.f60827c8.setLayoutParams(marginLayoutParams2);
                        }
                    }
                } else {
                    View view3 = new View(context);
                    layoutInflaterFactory2C1367w8.f60827c8 = view3;
                    view3.setVisibility(8);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, marginLayoutParams.topMargin, 51);
                    layoutParams.leftMargin = iM215172a1;
                    layoutParams.rightMargin = iM215173a2;
                    layoutInflaterFactory2C1367w8.f60825c6.addView(layoutInflaterFactory2C1367w8.f60827c8, -1, layoutParams);
                }
                View view4 = layoutInflaterFactory2C1367w8.f60827c8;
                boolean z4 = view4 != null;
                if (z4 && view4.getVisibility() != 0) {
                    View view5 = layoutInflaterFactory2C1367w8.f60827c8;
                    view5.setBackgroundColor((fa1.m212769a6(view5) & Segment.SIZE) != 0 ? AbstractC0871mq.m214015a0(context, R$color.abc_decor_view_status_guard_light) : AbstractC0871mq.m214015a0(context, R$color.abc_decor_view_status_guard));
                }
                if (!layoutInflaterFactory2C1367w8.f60832d3 && z4) {
                    iM215174a32 = 0;
                }
                z = z4;
                z2 = z3;
            } else if (marginLayoutParams.topMargin != 0) {
                marginLayoutParams.topMargin = 0;
                z = false;
                z2 = true;
            } else {
                z = false;
                z2 = false;
            }
            if (z2) {
                layoutInflaterFactory2C1367w8.f60820c1.setLayoutParams(marginLayoutParams);
            }
        }
        View view6 = layoutInflaterFactory2C1367w8.f60827c8;
        if (view6 != null) {
            view6.setVisibility(z ? 0 : 8);
        }
        if (iM215174a3 != iM215174a32) {
            int iM215172a12 = xf1VarMo213836a1.m215172a1();
            int iM215173a22 = xf1VarMo213836a1.m215173a2();
            int iM215171a0 = xf1VarMo213836a1.m215171a0();
            int i6 = Build.VERSION.SDK_INT;
            pf1 of1Var = i6 >= 30 ? new of1(xf1VarMo213836a1) : i6 >= 29 ? new mf1(xf1VarMo213836a1) : new lf1(xf1VarMo213836a1);
            of1Var.mo213838a6(f60.m212748a1(iM215172a12, iM215174a32, iM215173a22, iM215171a0));
            xf1VarMo213836a1 = of1Var.mo213836a1();
        }
        WeakHashMap weakHashMap2 = xa1.f61054a0;
        WindowInsets windowInsetsM215175a5 = xf1VarMo213836a1.m215175a5();
        if (windowInsetsM215175a5 == null) {
            return xf1VarMo213836a1;
        }
        WindowInsets windowInsetsM213281a1 = ja1.m213281a1(view, windowInsetsM215175a5);
        return !windowInsetsM213281a1.equals(windowInsetsM215175a5) ? xf1.m215170a6(view, windowInsetsM213281a1) : xf1VarMo213836a1;
    }

    @Override // p000.sf0
    /* renamed from: b6 */
    public boolean mo210851b6(bf0 bf0Var) {
        Window.Callback callback;
        switch (this.f60589a0) {
            case 2:
                Window.Callback callback2 = this.f60590a1.f60810b1.getCallback();
                if (callback2 != null) {
                    callback2.onMenuOpened(108, bf0Var);
                    break;
                }
                break;
            default:
                if (bf0Var == bf0Var.mo210698b0()) {
                    LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8 = this.f60590a1;
                    if (layoutInflaterFactory2C1367w8.f60830d1 && (callback = layoutInflaterFactory2C1367w8.f60810b1.getCallback()) != null && !layoutInflaterFactory2C1367w8.f60841e2) {
                        callback.onMenuOpened(108, bf0Var);
                        break;
                    }
                }
                break;
        }
        return true;
    }
}
